package com.unicsul.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicacao de cadastro de alunos da Unicsul.
 * Cada campus e representado por uma arvore binaria de busca (BST)
 * onde os nomes dos alunos sao armazenados em ordem alfabetica.
 */
@SpringBootApplication
public class UnicsulApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnicsulApplication.class, args);
    }
}
