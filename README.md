
<a id="readme-top"></a>

[![Stargazers][stars-shield]][stars-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project


The Task Manager API is a project and task management system that allows for the creation, editing, deletion, and viewing of projects, tasks, and related files. The API provides functionalities for manipulating user, project, task, and file data.

## About The Project
O projeto segue a estrutura MVC (Model-View-Controller):
* __Controller:__ Responsável por gerenciar as requisições HTTP e direcioná-las para os serviços apropriados.
* __Service:__ Contém a lógica de negócio.
* __Domain:__ Modelos de dados que representam as entidades do sistema.
* __Repository:__ Interfaces para comunicação com o banco de dados.

## About The Project

### File Controller
Este controlador é responsável por operações relacionadas a arquivos.

* Upload de Arquivo para Tarefa
  
```sh
  PATCH /api/file/task/{taskId}
  ```
* Descrição: Faz upload de um arquivo relacionado a uma tarefa específica.
* Parâmetros:
  * taskId: ID da tarefa (UUID)
  * file: Arquivo (MultipartFile)
* Resposta: Retorna os dados do arquivo carregado.
* Códigos de Resposta:
  * 200 OK: Upload bem-sucedido.
  * 400 Bad Request: Falha no upload.

* Excluir Arquivo
  
  ```sh
  DELETE /api/file/{fileId}
  ```

* Descrição: Faz upload de um arquivo relacionado a uma tarefa específica.
* Parâmetros:
  * fileId: ID do arquivo (UUID)
* Resposta: Nenhum conteúdo (204 No Content) se a exclusão for bem-sucedida.
* Códigos de Resposta:
  * 204 No Content: Exclusão bem-sucedida.
  * 400 Bad Request: Falha no upload.

### Project Controller
Este controlador gerencia projetos e seus membros.

* Criar Projeto

```sh
POST /api/project
```

* Descrição: Cria um novo projeto.
* Corpo da Requisição: ProjectDTO com os detalhes do projeto.
* Resposta: Detalhes do projeto criado.
* Códigos de Resposta:
  * 201 Created: Projeto criado com sucesso.

* Listar Projetos
  
  ```sh
  GET /api/project
  ```

* Descrição: Retorna uma lista de projetos, com paginação e filtro por título.
* Parâmetros:
  * page: Número da página (opcional, padrão: 0)
  * size: Tamanho da página (opcional, padrão: 10)
  * title: Filtro por título (opcional)
* Corpo da Requisição: ProjectDTO com os detalhes do projeto.
* Resposta: Lista de ProjectResponseDTO

* Listar Projetos
  
  ```sh
  GET /api/project/{projectId}
  ```

* Descrição: Retorna detalhes de um projeto específico.
* Parâmetros:
  * projectId: ID do projeto (UUID)
* Resposta: ProjectDetailDTO

* Atualizar Projeto
  
  ```sh
  PUT /api/project/{projectId}
  ```

* Descrição: Atualiza um projeto existente
* Parâmetros:
  * projectId: ID do projeto (UUID)
* Corpo da Requisição: ProjectDTO
* Resposta: ProjectResponseDTO atualizado

* Excluir Projeto
  
  ```sh
  DELETE /api/project/{projectId}
  ```

* Descrição: Remove um projeto pelo ID.
* Parâmetros:
  * projectId: ID do projeto (UUID)
* Resposta: Nenhum conteúdo (204 No Content)

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* npm
  ```sh
  npm install npm@latest -g
  ```

### Installation

_Below is an example of how you can instruct your audience on installing and setting up your app. This template doesn't rely on any external dependencies or services._

1. Get a free API Key at [https://example.com](https://example.com)
2. Clone the repo
   ```sh
   git clone https://github.com/github_username/repo_name.git
   ```
3. Install NPM packages
   ```sh
   npm install
   ```
4. Enter your API in `config.js`
   ```js
   const API_KEY = 'ENTER YOUR API';
   ```
5. Change git remote url to avoid accidental pushes to base project
   ```sh
   git remote set-url origin github_username/repo_name
   git remote -v # confirm the changes
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [x] Add Changelog
- [x] Add back to top links
- [ ] Add Additional Templates w/ Examples
- [ ] Add "components" document to easily copy & paste sections of the readme
- [ ] Multi-language Support
    - [ ] Chinese
    - [ ] Spanish

See the [open issues](https://github.com/othneildrew/Best-README-Template/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>





<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/othneildrew/Best-README-Template/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/othneildrew/Best-README-Template/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/othneildrew/Best-README-Template/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/othneildrew/Best-README-Template/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/davi-brantes/
[product-screenshot]: images/screenshot.png
[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white
[Next-url]: https://nextjs.org/
[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/
[Vue.js]: https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D
[Vue-url]: https://vuejs.org/
[Angular.io]: https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white
[Angular-url]: https://angular.io/
[Svelte.dev]: https://img.shields.io/badge/Svelte-4A4A55?style=for-the-badge&logo=svelte&logoColor=FF3E00
[Svelte-url]: https://svelte.dev/
[Laravel.com]: https://img.shields.io/badge/Laravel-FF2D20?style=for-the-badge&logo=laravel&logoColor=white
[Laravel-url]: https://laravel.com
[Bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com
[JQuery.com]: https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white
[JQuery-url]: https://jquery.com 
