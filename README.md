# 📦 ControleEstoque-20240329-M-firmo

## 🎯 Desafio: API REST de Controle de Estoque com Spring Boot

Este projeto estende um módulo de controle de estoque existente, implementando novas funcionalidades de Venda. O foco principal é a **lógica transacional** que valida o estoque de múltiplos produtos e realiza o *rollback* da transação inteira caso algum item tenha estoque insuficiente.

---

## 🛠️ Requisitos de Implementação

### 1. Modelagem de Entidades e Relacionamentos

As seguintes entidades foram adicionadas e configuradas, respeitando os relacionamentos exigidos:

* **Cliente:** Entidade para registrar informações básicas (nome, email, etc.).
* **Venda:** Entidade que representa uma transação de venda.
    * **Relacionamento 1:N:** `Cliente` (1) pode ter muitas `Vendas` (N).
    * **Tabela Intermediária (M:N):** Utilizada para relacionar `Venda` com `Produto`. Esta tabela (`ItemVenda` ou similar) armazena a `quantidade` vendida e o `preço unitário` do produto no momento da venda.

---

### 2. Lógica de Negócios e Endpoints

#### 2.1. Endpoints CRUD para Cliente

* Implementação dos seguintes endpoints REST e a lógica associada:
    * **CRUD** (Create, Read, Update, Delete) de Clientes.

#### 2.2. Endpoint de Registro de Venda

* **Endpoint:** `/api/vendas` (ou similar)
* **Funcionalidade:** Recebe dados da venda, incluindo o ID do cliente, lista de itens (ID do produto e quantidade).

#### 2.3. Lógica Crítica de Estoque

* **Lógica:**
    1.  **Verificação:** Checa se a `quantidade` desejada é **menor ou igual** à `quantidade disponível` no Estoque do Produto.
    2.  **Baixa de Estoque:** Se for suficiente, a quantidade vendida é subtraída do estoque do produto correspondente.
    3.  **Rollback:** Se a quantidade for insuficiente para **qualquer item da venda**, a **transação inteira** é revertida (rollback), e uma resposta de erro (HTTP 400 Bad Request ou similar) é retornada, informando o produto com estoque insuficiente.

---

## 🚀 Como Inicializar e Executar

### Pré-requisitos

* **Java Development Kit (JDK):** Versão 17 ou superior.
* **Apache Maven:** Para gerenciamento de dependências.

### 1. Clonar o Repositório

```bash
git clone [https://github.com/M-firmo/ControleEstoque-20240329-.git](https://github.com/M-firmo/ControleEstoque-20240329-.git)
cd ControleEstoque-20240329-