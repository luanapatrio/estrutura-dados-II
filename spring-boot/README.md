# Unicsul - Cadastro de Alunos por Campus (Árvore Binária)

Aplicação Java + Spring Boot + Thymeleaf + Bootstrap que atende aos requisitos do trabalho:

1. **Árvore binária**: cada um dos 7 campus da Unicsul (Anália Franco, Guarulhos, Liberdade,
   Paulista, São Miguel, Santo Amaro e Villa Lobos) é representado por uma **árvore binária de
   busca (BST)** própria (`CampusTree`), onde cada nó (`StudentNode`) guarda o nome de um aluno.
   O percurso **em ordem (in-order)** dessa árvore devolve os nomes já em **ordem alfabética**,
   o que atende diretamente ao requisito de listagem ordenada.
2. **Telas gráficas**: como o requisito pede telas gráficas e o uso de Spring Boot + Bootstrap,
   a interface é uma aplicação web (rodando localmente no navegador do Windows) com páginas
   Thymeleaf estilizadas em Bootstrap 5, funcionando como as "telas" do sistema (menu, cadastro,
   localização e listagem).

## Estrutura do projeto

```
unicsul-app/
├── pom.xml
└── src/main/java/com/unicsul/app/
    ├── UnicsulApplication.java        # classe main (Spring Boot)
    ├── model/
    │   ├── Campus.java                # enum com os 7 campus
    │   ├── StudentNode.java           # nó da árvore binária
    │   └── CampusTree.java            # árvore binária de busca (inserir/buscar/listar)
    ├── service/
    │   └── SchoolService.java         # regras de negócio (cadastro sem duplicidade, busca, listagem)
    └── controller/
        └── MenuController.java        # controller das telas (menu, cadastrar, localizar, listar)
└── src/main/resources/
    ├── application.properties
    ├── static/css/custom.css
    └── templates/
        ├── fragments.html   # cabeçalho/menu de navegação compartilhado (Bootstrap)
        ├── index.html       # tela do menu principal
        ├── cadastrar.html   # tela "Cadastrar novo aluno"
        ├── localizar.html   # tela "Localizar aluno"
        └── listar.html      # tela "Listar alunos do campus"
```

## Regras implementadas

- **Cadastrar novo aluno**: escolhe-se o campus e digita-se o nome. Antes de inserir, o sistema
  verifica se o nome já existe em **qualquer** um dos 7 campus (todas as árvores); se existir,
  o cadastro é bloqueado e uma mensagem de erro é exibida. Caso contrário, o nome é inserido na
  árvore binária do campus escolhido.
- **Localizar aluno**: o nome digitado é procurado em todas as árvores (todos os campus), uma a
  uma, até ser encontrado. Se não for encontrado em nenhuma, a tela informa "Aluno não localizado".
- **Listar alunos do campus**: seleciona-se o campus desejado e o sistema percorre a árvore binária
  daquele campus em ordem (in-order), retornando os nomes já organizados alfabeticamente.

## Como executar

Pré-requisitos: **Java 17+** e **Maven** instalados (ou use a IDE de sua preferência, como
IntelliJ IDEA ou Eclipse/STS, que já reconhecem o `pom.xml`).

Via terminal, dentro da pasta `unicsul-app`:

```bash
mvn spring-boot:run
```

Depois, no navegador (Windows, Linux ou Mac), acesse:

```
http://localhost:8080
```

Você verá o menu principal com as três opções (Cadastrar, Localizar, Listar).

## Observação sobre "modo gráfico (Windows)"

O enunciado pede telas em modo gráfico rodando no Windows e também pede o uso de
Spring Boot + Bootstrap para estilização — por isso a interface gráfica foi implementada como uma
aplicação **web** (Spring Boot + Thymeleaf + Bootstrap), que é executada e visualizada normalmente
no Windows através do navegador, sem necessidade de bibliotecas gráficas desktop (como Swing/JavaFX).
Se o professor exigir especificamente uma janela desktop (Swing), a lógica das árvores binárias
(`Campus`, `StudentNode`, `CampusTree`, `SchoolService`) pode ser reaproveitada 100% — bastaria
trocar apenas a camada de `controller`/`templates` por uma `JFrame` com os mesmos três botões de menu.
