package com.unicsul.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Arvore binaria de busca (BST) que armazena os nomes dos alunos
 * de um determinado campus.
 *
 * A escolha por uma BST (e nao apenas uma arvore binaria qualquer)
 * permite que o percurso "em ordem" (in-order) devolva os nomes
 * ja organizados em ordem alfabetica, atendendo ao requisito de
 * listagem ordenada, alem de tornar a busca mais eficiente.
 */
public class CampusTree {

    private final Campus campus;
    private StudentNode raiz;
    private int quantidade;

    public CampusTree(Campus campus) {
        this.campus = campus;
    }

    public Campus getCampus() {
        return campus;
    }

    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Insere um novo aluno na arvore, respeitando a ordem alfabetica.
     * @return true se inseriu, false se o nome ja existia NESTA arvore.
     */
    public boolean inserir(String nome) {
        if (contem(nome)) {
            return false;
        }
        raiz = inserirRecursivo(raiz, nome);
        quantidade++;
        return true;
    }

    private StudentNode inserirRecursivo(StudentNode atual, String nome) {
        if (atual == null) {
            return new StudentNode(nome);
        }
        int comparacao = normalizar(nome).compareTo(normalizar(atual.getNome()));
        if (comparacao < 0) {
            atual.setEsquerda(inserirRecursivo(atual.getEsquerda(), nome));
        } else if (comparacao > 0) {
            atual.setDireita(inserirRecursivo(atual.getDireita(), nome));
        }
        // se for igual, nao faz nada (evita duplicidade dentro do mesmo campus)
        return atual;
    }

    /**
     * Verifica se um nome ja esta cadastrado nesta arvore (case-insensitive).
     */
    public boolean contem(String nome) {
        return buscarNodo(raiz, nome) != null;
    }

    private StudentNode buscarNodo(StudentNode atual, String nome) {
        if (atual == null) {
            return null;
        }
        int comparacao = normalizar(nome).compareTo(normalizar(atual.getNome()));
        if (comparacao == 0) {
            return atual;
        }
        return comparacao < 0
                ? buscarNodo(atual.getEsquerda(), nome)
                : buscarNodo(atual.getDireita(), nome);
    }

    /**
     * Retorna todos os nomes cadastrados neste campus em ordem alfabetica,
     * usando percurso em ordem (in-order traversal) da arvore binaria.
     */
    public List<String> listarEmOrdem() {
        List<String> nomes = new ArrayList<>();
        percorrerEmOrdem(raiz, nomes);
        return nomes;
    }

    private void percorrerEmOrdem(StudentNode atual, List<String> nomes) {
        if (atual == null) {
            return;
        }
        percorrerEmOrdem(atual.getEsquerda(), nomes);
        nomes.add(atual.getNome());
        percorrerEmOrdem(atual.getDireita(), nomes);
    }

    private String normalizar(String texto) {
        return texto == null ? "" : texto.trim().toUpperCase();
    }
}
