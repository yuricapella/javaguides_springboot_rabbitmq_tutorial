# springboot-rabbitmq-tutorial

## Visão Geral

Este projeto demonstra uma arquitetura multi-módulo Maven com microserviços Java Spring Boot realizando comunicação assíncrona via RabbitMQ. A estrutura permite experimentação prática de envio e consumo de mensagens em diferentes padrões (simples string, objetos JSON), com todos os módulos integrados e configuração centralizada de fila, exchange e autenticação do RabbitMQ.

## Estrutura dos Módulos

```
springboot-rabbitmq-tutorial/
├── pom.xml
├── tutorial-service/    # Serviço exemplo: envio/consumo string e JSON (User)
├── order-service/       # Order API: envia OrderEvent para dois consumidores
├── stock-service/       # Consumer: recebe OrderEvent (fila order)
├── email-service/       # Consumer: recebe OrderEvent (fila email)
```

## Requisitos

- Java 21+
- Maven 3.8+
- RabbitMQ rodando localmente na porta 5672 e 15672
    - Usuário: `teste`
    - Senha: `teste`
    - Exemplo para subir o RabbitMQ via Docker:

      ```bash
      docker run -d --name rabbitmq \
        -p 5672:5672 \
        -p 15672:15672 \
        -e RABBITMQ_DEFAULT_USER=teste \
        -e RABBITMQ_DEFAULT_PASS=teste \
        rabbitmq:3-management
      ```
- (Opcional) Postman/Insomnia para testar REST APIs

## Como rodar

1. Clone o projeto:
   ```bash
   git clone https://github.com/<seu-usuario>/springboot-rabbitmq-tutorial.git
   cd springboot-rabbitmq-tutorial
   ```
2. Compile todos os módulos:
   ```bash
   ./mvnw clean install
   ```
3. Abra na IDE ou rode cada serviço individualmente:
   - Tutorial service:
     ```bash
     ./mvnw spring-boot:run -pl tutorial-service
     ```
   - Order service:
     ```bash
     ./mvnw spring-boot:run -pl order-service
     ```
   - Stock service:
     ```bash
     ./mvnw spring-boot:run -pl stock-service
     ```
   - Email service:
     ```bash
     ./mvnw spring-boot:run -pl email-service
     ```

   > **Obs:** Start o RabbitMQ antes dos serviços.

## Endpoints Disponíveis

### tutorial-service (porta `8080`)

- **GET** `/api/v1/publish?message=...`  
  Envia mensagem string para fila.

- **POST** `/api/v1/publish`  
  Envia objeto User em formato JSON (exemplo de payload):
  ```json
  {
    "id": 1,
    "firstName": "yuri",
    "lastName": "capella"
  }
  ```

### order-service (porta `8083`)

- **POST** `/api/v1/orders`  
  Envia Order e propaga evento para stock e email:
  ```json
  {
    "name": "teste 2",
    "quantity": 2,
    "price": "5500"
  }
  ```

### stock-service (porta `8081`)
- Não possui endpoint:
    - Apenas consome OrderEvent da fila `order`

### email-service (porta `8082`)
- Não possui endpoint:
    - Apenas consome OrderEvent da fila `email`

## Fluxo Resumido

1. O _order-service_ publica um `OrderEvent` em duas filas: `order` e `email`.
2. _stock-service_ consome os eventos da fila `order`.
3. _email-service_ consome os eventos da fila `email`.
4. O _tutorial-service_ demonstra envios e recebimento tanto de mensagens string quanto JSON.

## Configuração dos Serviços

Todos os serviços usam a seguinte configuração para acessar o RabbitMQ:

```properties
spring.rabbitmq.username=teste
spring.rabbitmq.password=teste
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
```

## Observações e Dicas

- Certifique-se de que os **DTOs Order e OrderEvent estão idênticos em todos os módulos**.
- As filas e exchanges são criadas automaticamente conforme anotação/configuração de cada serviço.
- Apenas o tutorial-service oferece endpoints REST para enviar/receber string e JSON diretamente.
- O sistema é facilmente expandido para novos consumidores/produtores adicionando novas filas/bindings.

---

Projeto de referência para testes e estudo de mensageria, microserviços Spring Boot e RabbitMQ.
```
