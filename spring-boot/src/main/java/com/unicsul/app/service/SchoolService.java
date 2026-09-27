package com.unicsul.app.service;

import com.unicsul.app.model.Campus;
import com.unicsul.app.model.CampusTree;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Servico responsavel por manter uma arvore binaria para cada campus
 * e por aplicar as regras de negocio do trabalho:
 *  - cadastrar aluno (impedindo nomes duplicados em qualquer campus);
 *  - localizar aluno em todos os campus;
 *  - listar alunos de um campus em ordem alfabetica.
 */
@Service
public class SchoolService {

    private final Map<Campus, CampusTree> arvoresPorCampus = new EnumMap<>(Campus.class);

    @PostConstruct
    public void inicializarArvores() {
        for (Campus campus : Campus.values()) {
            arvoresPorCampus.put(campus, new CampusTree(campus));
        }
    }

    public static class ResultadoCadastro {
        public final boolean sucesso;
        public final String mensagem;

        public ResultadoCadastro(boolean sucesso, String mensagem) {
            this.sucesso = sucesso;
            this.mensagem = mensagem;
        }
    }

    public static class ResultadoBusca {
        public final boolean encontrado;
        public final Campus campus;

        public ResultadoBusca(boolean encontrado, Campus campus) {
            this.encontrado = encontrado;
            this.campus = campus;
        }
    }

    /**
     * Cadastra um aluno no campus informado, desde que o nome nao
     * exista em NENHUM dos campus (regra do enunciado).
     */
    public ResultadoCadastro cadastrarAluno(Campus campus, String nomeAluno) {
        if (nomeAluno == null || nomeAluno.trim().isEmpty()) {
            return new ResultadoCadastro(false, "Informe o nome do aluno.");
        }

        String nome = nomeAluno.trim();

        Optional<Campus> campusExistente = localizarCampusDoAluno(nome);
        if (campusExistente.isPresent()) {
            return new ResultadoCadastro(false,
                    "Já existe um aluno cadastrado com o nome \"" + nome + "\" no campus "
                            + campusExistente.get().getNomeExibicao() + ".");
        }

        boolean inserido = arvoresPorCampus.get(campus).inserir(nome);
        if (inserido) {
            return new ResultadoCadastro(true,
                    "Aluno \"" + nome + "\" cadastrado com sucesso no campus " + campus.getNomeExibicao() + ".");
        }
        return new ResultadoCadastro(false, "Não foi possível cadastrar o aluno.");
    }

    /**
     * Procura o aluno em todos os campus ate encontra-lo.
     */
    public ResultadoBusca localizarAluno(String nomeAluno) {
        Optional<Campus> campus = localizarCampusDoAluno(nomeAluno);
        return campus.map(c -> new ResultadoBusca(true, c))
                .orElseGet(() -> new ResultadoBusca(false, null));
    }

    private Optional<Campus> localizarCampusDoAluno(String nomeAluno) {
        if (nomeAluno == null || nomeAluno.trim().isEmpty()) {
            return Optional.empty();
        }
        for (Map.Entry<Campus, CampusTree> entrada : arvoresPorCampus.entrySet()) {
            if (entrada.getValue().contem(nomeAluno.trim())) {
                return Optional.of(entrada.getKey());
            }
        }
        return Optional.empty();
    }

    /**
     * Lista os alunos de um campus em ordem alfabetica (percurso em ordem da BST).
     */
    public List<String> listarAlunosDoCampus(Campus campus) {
        return arvoresPorCampus.get(campus).listarEmOrdem();
    }

    public int quantidadeAlunos(Campus campus) {
        return arvoresPorCampus.get(campus).getQuantidade();
    }
}
