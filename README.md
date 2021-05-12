
<!-- PROJECT LOGO -->
<br />
<p align="center">
  <h3 align="center">Message Schuduler</h3>

  <p align="center">
    A simple message scheduler application
  </p>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Conteudos</h2></summary>
  <ol>
    <li>
      <a href="#sobre-o-projeto">Sobre o projeto</a>
      <ul>
        <li><a href="#construido-com">Construido com</a></li>
      </ul>
    </li>
    <li>
      <a href="#comecando">Comecando</a>
      <ul>
        <li><a href="#pre-requisitos">Pre-requisitos</a></li>
        <li><a href="#instalacao">Instalacao</a></li>
      </ul>
    </li>
    <li><a href="#uso">Usage</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contato">Contato</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## Sobre o projeto

A arquitetura/estrutura do projeto foi escrita baseada no conceito de arquitetura Hexagonal, no modelo proposto por Tom Hombergs, que pode ser consultada em [Hexagonal Architecture with Java and Spring](https://reflectoring.io/spring-hexagonal/).

A API do projeto foi desenvolvida no modelo RESTFul e documentada com o Swagger. 

A suite de testes ficou divida em testes unitarios com Mockito e JUnit 5, e os testes integrados rodam utilizando o Testcontainers.

### Construido com

* [Java 11]()
* [Docker]()
* [SpringBoot]()
* [JUnit 5]()
* [Testcontainers]()

<!-- GETTING STARTED -->
## Comecando

Para obter uma cópia local, siga estas etapas simples.

### Pre-requisitos

* Maven 3.6
* Java 11

### Instalacao

1. Clone o repositorio
   ```sh
   $ git clone https://github.com/joaoseidel/message.git
   ```
2. Instalando com maven
   ```sh
   $ mvn clean install
   ```

<!-- USAGE EXAMPLES -->
## Uso

1. Rodando o projeto com docker compose
```sh
$ docker compose up -d
```
2. Apos a conclusao do comando acima, pode-se acessar a pagina do SwaggerUI acessando a seguinte URL: http://localhost:8080/swagger-ui.html

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.



<!-- CONTACT -->
## Contato

Joao Seidel - [@website](https://joaoseidel.io/)

Project Link: [https://github.com/joaoseidel/message](https://github.com/joaoseidel/message)



<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements

* [Hexagonal Architecture](https://reflectoring.io/spring-hexagonal/)
