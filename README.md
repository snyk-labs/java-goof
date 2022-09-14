## Laboratório DevSecOps
 Laboratório DevSecOps em containers com: 
    - java Goof, 
    - sonar,
    - OWASP Juice Shop

### Requisistos mínimos:

- [Docker](http://pje.wiki.tjdft.jus.br/index.php/Docker) 
- [Docker Compose](http://pje.wiki.tjdft.jus.br/index.php/Docker_Compose)


### Uso
- Dentro da pasta clonada executar: 
    - ```cd .devcontainer```
    - ```docker-compose up```

### Extensões recomendadas
- [Docker](https://marketplace.visualstudio.com/items?itemName=ms-azuretools.vscode-docker)
- [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)

### Java Goof 

-This is a collection of Java demo apps that are vulnerable in different ways.

It's divided into modules, each one having its own README:

* [Todolist Goof](todolist-goof/README.md)
* [Log4Shell Goof](log4shell-goof/README.md)
* [Quickstart for running both Todolist with Log4Shell in Kubernetes](README-K8S.md)

### Sonar
- http://localhost:9000
- Usuário e senha inicial: **admin**
- Para entrar no shell do container java-goof execute:
    ```shell
    cd .devcontainer
    docker-compose exec java-goof bash
    ```

### OWASP Juice Shop
- http://localhost:3000 