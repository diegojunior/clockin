# clockin - Ponto Eletrônico

Rest API de simulação de batida de ponto

## Inicio

### Pre-requisitos

Esta aplicação necessita de ter o java instalado.


## Executando a aplicação

Para executar os testes unitários, deve-se executar o seguinte comando na raiz do projeto:

```
mvn clean test
```
## Subindo a aplicação

Para subir a aplicação, deve-se executar o comando na raiz do projeto:
```
mvn spring-boot:run
```

### Acessando a aplicação

Os endpoints da api para testar no curl estão disponíveis pela uri base:
http://localhost:8080/clockin/v1/usuarios

http://localhost:8080/clockin/v1/pontos

A base de dados utilizada pelo teste é o H2 e os dados ja de usuário e senha estão salvos no application.properties
Por padrão os seguintes dados estao listados abaixo:

```
spring.datasource.username=sa
spring.datasource.password=password
```

Using the functions of the application will only be possible after the database is initiated.

## Swagger de acesso a documentação da API.
Você pode acessar aqui:
http://localhost:8080/clockin/swagger-ui.html


### Curl de exemplo de testes da API

## Usuários
Para cadastrar os usuários:
```
curl -X POST "http://localhost:8080/clockin/v1/usuarios" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"id\":1,\"nome\":\"diego\",\"cpf\":\"123.123.567-98\",\"email\":\"diego@teste.com.br\",\"dataCadastro\":\"20-02-2020\"}"
```

Para listar os usuários:
```
curl -X GET "http://localhost:8080/clockin/v1/usuarios" -H "accept: application/json"

```

Para editar um usuário existente:
```
curl -X PUT "http://localhost:8080/clockin/v1/usuarios/1" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"id\":1,\"nome\":\"lala\",\"cpf\":\"333.333.333-44\",\"email\":\"string\",\"dataCadastro\":\"19-02-2020\"}"

```
Para consultar um usuário:
```
curl -X GET "http://localhost:8080/clockin/v1/usuarios/1" -H "accept: application/json"

```

## Pontos

Para inserir uma batida de ponto, deve-se inserir:

```
curl -X POST "http://localhost:8080/clockin/v1/pontos" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"usuario\":1}"

```

Para gerar o relatório das horas do usuário:

```
curl -X GET "http://localhost:8080/clockin/v1/pontos/1" -H "accept: application/json"

```
