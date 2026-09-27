package com.unicsul.app.model;

/**
 * Enumeracao dos campus da Unicsul exigidos pelo trabalho.
 * Cada campus tera sua propria arvore binaria de alunos (CampusTree).
 */
public enum Campus {

    ANALIA_FRANCO("Anália Franco"),
    GUARULHOS("Guarulhos"),
    LIBERDADE("Liberdade"),
    PAULISTA("Paulista"),
    SAO_MIGUEL("São Miguel"),
    SANTO_AMARO("Santo Amaro"),
    VILLA_LOBOS("Villa Lobos");

    private final String nomeExibicao;

    Campus(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    public String getNomeExibicao() {
        return nomeExibicao;
    }
}
