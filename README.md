# 🎫 Ticket Validation

Projeto desenvolvido para fins acadêmicos, com o objetivo de criar uma API distribuída capaz de realizar a **validação de boletos bancários**. A aplicação foi construída utilizando **Java Spring Boot**, integrada a um banco de dados MySQL hospedado na AWS, e testada via **Postman**.

---

## 🚀 Objetivo do Projeto

O objetivo deste projeto é simular um sistema de validação de tickets que pode ser utilizado em eventos, sistemas de acesso ou ambientes que necessitam de controle de entrada. O sistema permite cadastrar e validar tickets, garantindo consistência e comunicação adequada entre os componentes distribuídos.

---

## 🛠️ Tecnologias Utilizadas

### **Backend**

- **Java Spring Boot** – Framework principal para construção da API
- **Spring Data JPA** – Mapeamento das entidades e comunicação com o banco
- **Spring Web** – Exposição dos endpoints REST

### **Banco de Dados**

- **AWS RDS (MySQL)** – Instância de banco de dados em nuvem
- **MySQL Workbench** – Utilizado para modelagem, criação das classes e execução de queries

### **Testes**

- **Postman** – Para validação dos endpoints e testes da API

---

## 🗂️ Arquitetura do Sistema

O projeto segue o padrão MVC (Model–View–Controller), um dos design patterns mais utilizados no desenvolvimento de aplicações.

Como se trata de uma API, não há a camada de View. Portanto, utilizamos:

- Model – Representação das entidades e do banco de dados

- Controller – Camada responsável por receber as requisições e direcionar o fluxo

Além disso, incluímos também:

- Service – Onde ficam as regras de negócio

- Repository – Responsável pela comunicação direta com o banco via JPA

Essa abordagem mantém o código organizado, desacoplado e de fácil manutenção.

---

## 📦 Funcionalidades Implementadas

- Cadastro de boletos
- Busca de boletos por ID ou outros atributos
- Validação de boletos existentes
- Retorno de mensagens apropriadas para cada caso (válido / inválido / inexistente)
- Persistência em banco MySQL hospedado na AWS

---

## 🔌 Endpoints Principais

VERIFICAR

### **Criar ticket**

```
POST /tickets
```

### **Listar tickets**

```
GET /tickets
```

### **Buscar ticket por ID**

```
GET /tickets/{id}
```

### **Validar ticket**

```
POST /tickets/validate
```

---

## 🧪 Testes com Postman

Utilizamos o Postman para:

- Enviar requisições REST
- Testar o fluxo completo de cadastro e validação
- Simular erros e garantir a robustez da API

Foi criada uma coleção de testes contendo os endpoints principais.

---

## ☁️ AWS RDS

Criamos uma instância MySQL na AWS para armazenar os dados dos tickets. Durante o desenvolvimento:

- Configuramos o security group para permitir o acesso da aplicação
- Criamos o schema utilizado no projeto
- Validamos a conexão com o Spring Boot

---

## 🧱 Modelagem e Classes

A modelagem do banco foi feita utilizando o **MySQL Workbench**, e as classes correspondentes foram geradas manualmente no Spring Boot, representando:

- Entidades
- Relacionamentos
- Atributos do ticket

---

## 👩‍💻👨‍💻 Equipe

Projeto desenvolvido por:

- **Amanda Hellen**
- **Felipe Josué**

---

## 📚 Finalidade Acadêmica

Este projeto foi desenvolvido exclusivamente para fins de estudo na disciplina **Sistemas Distribuídos e Mobile**, com foco em:

- Arquiteturas distribuídas
- Comunicação entre serviços
- Persistência em banco remoto
- Boas práticas de desenvolvimento backend

---

## 📝 Licença

Este projeto é destinado a fins acadêmicos e está aberto para estudos e melhorias.
