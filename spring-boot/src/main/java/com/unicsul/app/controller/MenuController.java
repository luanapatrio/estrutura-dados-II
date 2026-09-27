package com.unicsul.app.controller;

import com.unicsul.app.model.Campus;
import com.unicsul.app.service.SchoolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controller responsavel pelas telas graficas (web) do sistema:
 * menu principal, cadastro de aluno, localizacao de aluno e
 * listagem de alunos por campus.
 */
@Controller
public class MenuController {

    private final SchoolService schoolService;

    public MenuController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @ModelAttribute("campusList")
    public Campus[] campusList() {
        return Campus.values();
    }

    // ---------- MENU PRINCIPAL ----------
    @GetMapping("/")
    public String menu() {
        return "index";
    }

    // ---------- OPCAO 1: CADASTRAR NOVO ALUNO ----------
    @GetMapping("/cadastrar")
    public String telaCadastro() {
        return "cadastrar";
    }

    @PostMapping("/cadastrar")
    public String processarCadastro(@RequestParam("campus") Campus campus,
                                     @RequestParam("nome") String nome,
                                     Model model) {
        SchoolService.ResultadoCadastro resultado = schoolService.cadastrarAluno(campus, nome);
        model.addAttribute("sucesso", resultado.sucesso);
        model.addAttribute("mensagem", resultado.mensagem);
        return "cadastrar";
    }

    // ---------- OPCAO 2: LOCALIZAR ALUNO ----------
    @GetMapping("/localizar")
    public String telaLocalizar() {
        return "localizar";
    }

    @PostMapping("/localizar")
    public String processarLocalizar(@RequestParam("nome") String nome, Model model) {
        SchoolService.ResultadoBusca resultado = schoolService.localizarAluno(nome);
        model.addAttribute("pesquisado", true);
        model.addAttribute("nomeBuscado", nome);
        model.addAttribute("encontrado", resultado.encontrado);
        if (resultado.encontrado) {
            model.addAttribute("campusEncontrado", resultado.campus.getNomeExibicao());
        }
        return "localizar";
    }

    // ---------- OPCAO 3: LISTAR ALUNOS DO CAMPUS ----------
    @GetMapping("/listar")
    public String telaListar() {
        return "listar";
    }

    @PostMapping("/listar")
    public String processarListar(@RequestParam("campus") Campus campus, Model model) {
        List<String> alunos = schoolService.listarAlunosDoCampus(campus);
        model.addAttribute("campusSelecionado", campus);
        model.addAttribute("alunos", alunos);
        model.addAttribute("consultado", true);
        return "listar";
    }
}
