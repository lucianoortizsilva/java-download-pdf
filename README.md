### Stack
- Java
- Maven
- Spring Boot
- Liquibase 
- H2

### O que é ?
Dois Endpoints para download de arquivo PDF. \
Um endpoint faz download do arquivo físico, o outro faz download do arquivo em Base64 \
Como exemplo, será gerado dados sobre a Copa do Mundo FIFA.

### Como rodar ?
- Execute o comando **`mvn clean package`**
- Execute o comando **`mvn spring-boot:run`** (Para iniciar e gerar um model via JPA)
- Execute o comando **`mvn liquibase:update`** (Para popular dados do model)

> **GET** http://localhost:8080/copas/download/pdf    

![](https://github.com/lucianoortizsilva/java-download-pdf/blob/master/src/main/resources/static/github/download-pdf.jpg)

<hr>

> **GET** http://localhost:8080/copas/download/base64

![](https://github.com/lucianoortizsilva/java-download-pdf/blob/master/src/main/resources/static/github/download-base64.jpg)
