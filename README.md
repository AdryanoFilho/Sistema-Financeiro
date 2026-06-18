# Sistema Financeiro — Back-End Spring Boot
**Prof. Junio Figueiredo | Curso Spring Boot**

---

## O que é o projeto

API REST para registrar lançamentos financeiros (RECEITA e DESPESA).
Possui CRUD completo de **Lançamentos**, **Pessoas** e **Categorias**.

---

## Tecnologias utilizadas

| Tecnologia | Para que serve |
|---|---|
| Spring Boot 3 | Framework principal |
| Spring Web | Criar endpoints REST |
| Spring Data JPA | Acessar o banco via JPA/Hibernate |
| Flyway | Controlar migrações do banco |
| Bean Validation | Validar campos obrigatórios |
| Lombok | Gerar getters/construtores automaticamente |
| MySQL | Banco de dados |

---

## Como rodar o projeto

### 1. Crie o banco de dados no MySQL

```sql
CREATE DATABASE financeiro;
```

### 2. Configure a senha no application.properties

```
spring.datasource.password=SUA_SENHA_AQUI
```

### 3. Execute o projeto

```bash
mvn spring-boot:run
```

O Flyway vai criar as tabelas e inserir os dados automaticamente.

---

## Estrutura de pastas

```
src/main/java/br/com/financeiro/
├── SistemaFinanceiroApplication.java   ← ponto de entrada
├── controller/
│   ├── CategoriaController.java        ← endpoints /categorias
│   ├── PessoaController.java           ← endpoints /pessoas
│   ├── LancamentoController.java       ← endpoints /lancamentos
│   └── TratadorDeErros.java            ← tratamento global de erros
├── model/
│   ├── Categoria.java                  ← entidade JPA
│   ├── Pessoa.java                     ← entidade JPA
│   ├── Lancamento.java                 ← entidade JPA
│   ├── Endereco.java                   ← @Embeddable (sem tabela própria)
│   ├── TipoLancamento.java             ← enum RECEITA / DESPESA
│   └── Dados*.java                     ← DTOs (Records)
└── repository/
    ├── CategoriaRepository.java
    ├── PessoaRepository.java
    └── LancamentoRepository.java

src/main/resources/
├── application.properties
└── db/migration/
    ├── V1__criar_tabela_categoria.sql
    ├── V2__criar_tabela_pessoa.sql
    ├── V3__criar_tabela_lancamento.sql
    └── V4__inserir_dados_iniciais.sql
```

---

## Endpoints disponíveis

### CATEGORIAS

| Método | URL | O que faz |
|---|---|---|
| POST | /categorias | Cadastra nova categoria |
| GET | /categorias | Lista todas (paginado) |
| GET | /categorias/{codigo} | Busca por código |
| DELETE | /categorias/{codigo} | Exclui categoria |

### PESSOAS

| Método | URL | O que faz |
|---|---|---|
| POST | /pessoas | Cadastra nova pessoa |
| GET | /pessoas | Lista pessoas ativas (paginado) |
| GET | /pessoas/{codigo} | Busca por código |
| PUT | /pessoas/{codigo} | Atualiza dados |
| DELETE | /pessoas/{codigo} | **Inativa** (exclusão lógica) |
| PUT | /pessoas/{codigo}/ativo | Reativa pessoa inativa |

### LANÇAMENTOS

| Método | URL | O que faz |
|---|---|---|
| POST | /lancamentos | Cadastra novo lançamento |
| GET | /lancamentos | Lista todos (paginado) |
| GET | /lancamentos/{codigo} | Busca por código |
| PUT | /lancamentos/{codigo} | Atualiza lançamento |
| DELETE | /lancamentos/{codigo} | Exclui lançamento |

---

## Exemplos de JSON para o Insomnia

### Cadastrar Categoria
```json
POST /categorias
{
  "nome": "Imposto"
}
```

### Cadastrar Pessoa
```json
POST /pessoas
{
  "nome": "Zé Maria",
  "endereco": {
    "logradouro": "Rua das Silvas",
    "numero": "25",
    "bairro": "Valentina",
    "cep": "58028-30",
    "cidade": "Joao Pessoa",
    "estado": "Paraiba"
  }
}
```

### Cadastrar Lançamento
```json
POST /lancamentos
{
  "descricao": "Faculdade",
  "dataVencimento": "2024-11-10",
  "valor": 500,
  "tipo": "DESPESA",
  "categoria": { "codigo": 5 },
  "pessoa": { "codigo": 1 }
}
```

### Atualizar Pessoa
```json
PUT /pessoas/1
{
  "nome": "João Silva Atualizado"
}
```

### Paginação e Ordenação
```
GET /lancamentos?page=0&size=5&sort=dataVencimento,asc
GET /pessoas?page=0&size=10&sort=nome
GET /categorias?page=0&size=20&sort=nome
```

---

## Conceitos importantes explicados

### Por que @Transactional?
Quando fazemos `pessoa.atualizar(dados)` dentro de um método `@Transactional`, o JPA rastreia as mudanças no objeto. Ao final do método, o Spring faz automaticamente o `UPDATE` no banco — sem precisar chamar `repository.save()`.

### Por que BigDecimal para valor?
`double` e `float` têm erros de arredondamento em ponto flutuante.
`BigDecimal` é preciso para dinheiro: 100.10 + 200.20 = 300.30 exatamente.

### O que é exclusão lógica?
Em vez de `DELETE FROM pessoa WHERE codigo = 1`, apenas fazemos `UPDATE pessoa SET ativo = false WHERE codigo = 1`. O registro continua no banco. Isso preserva o histórico de lançamentos relacionados àquela pessoa.

### O que é @Embedded / @Embeddable?
`Endereco` não tem tabela própria. Com `@Embeddable`, os campos do endereço ficam na mesma tabela da entidade que o usa (`@Embedded`). A tabela `pessoa` contém logradouro, numero, bairro etc.

### Por que DTOs (Records)?
Nunca exponha a entidade JPA diretamente na API. DTOs controlam exatamente quais campos o cliente vê/envia, evitam serialização circular e protegem contra sobrescrita de campos que não deveriam ser editados.

### FetchType.LAZY vs EAGER
`LAZY` (padrão): o JPA só busca a Categoria/Pessoa do lançamento quando você chamar `getLancamento().getCategoria()`. Economiza queries desnecessárias.
`EAGER`: busca tudo de uma vez, mesmo que você não precise.
