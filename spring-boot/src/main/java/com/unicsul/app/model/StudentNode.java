package com.unicsul.app.model;

/**
 * No da arvore binaria de busca. Cada no guarda o nome de um aluno
 * e referencias para os filhos esquerdo e direito.
 */
public class StudentNode {

    private final String nome;
    private StudentNode esquerda;
    private StudentNode direita;

    public StudentNode(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public StudentNode getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(StudentNode esquerda) {
        this.esquerda = esquerda;
    }

    public StudentNode getDireita() {
        return direita;
    }

    public void setDireita(StudentNode direita) {
        this.direita = direita;
    }
}
