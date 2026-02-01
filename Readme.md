# ☕ API Tarefas Kids - Backend

![Status](https://img.shields.io/badge/status-Em_Desenvolvimento-orange)
![Java](https://img.shields.io/badge/Java-17-red?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.2-green?logo=spring)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue?logo=docker)

> **O cérebro do sistema Tarefas Kids.** Esta API gerencia usuários, autenticação e o fluxo de tarefas para o aplicativo móvel e o painel administrativo web.

🔗 **Acesse o App Android aqui:** [763NTiago/tarefas-kids](https://github.com/763NTiago/tarefas-kids)

---

## 📋 Sobre o Projeto

Este é o backend responsável por toda a lógica de negócios do ecossistema Tarefas Kids. Ele fornece endpoints RESTful para o aplicativo móvel e serve páginas HTML (Thymeleaf) para o gerenciamento dos pais.

### Funcionalidades Principais
* 🔐 **Autenticação Dupla:** Suporte para login via API (App) e Sessão Web (Painel Admin).
* 👨‍👩‍👧 **Gestão de Famílias:** Cadastro de responsáveis e dependentes.
* 📊 **Painel Administrativo:** Interface web para criar e gerenciar usuários.
* 🗄️ **Banco de Dados Híbrido:** H2 (Memória) para desenvolvimento rápido e PostgreSQL para produção (Docker).

---

## 🛠️ Tecnologias

* **Linguagem:** Java 17
* **Framework:** Spring Boot 3
* **Segurança:** Spring Security (BCrypt)
* **Banco de Dados:** PostgreSQL & H2 Database
* **Infraestrutura:** Docker & Docker Compose
* **Proxy:** Caddy Server

---

## 🚀 Como Rodar

### Pré-requisitos
* Docker & Docker Compose OU Java JDK 17

### Opção A: Via Docker (Recomendado)
Simula o ambiente de produção com PostgreSQL.

```bash
docker-compose up --build