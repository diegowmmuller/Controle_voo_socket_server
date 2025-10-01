# Projeto: Sistema de Controle de Voos com Servidor Multithread

---

## Descrição do Projeto

Este projeto implementa um **sistema de gerenciamento de voos** em Java, permitindo que clientes verifiquem o status de assentos e realizem reservas em tempo real através de uma conexão TCP. O servidor utiliza **ServerSocket** e cria uma **thread separada para cada cliente**, garantindo múltiplos atendimentos simultâneos.

O projeto possui as seguintes funcionalidades:

- Cadastro e controle de voos e assentos.
- Consulta do status de assentos.
- Marcação de assentos disponíveis.
- Servidor TCP que responde a queries de clientes.
- Resposta padronizada para status de assentos.
---

## Estrutura do Projeto

src/  
├── br.study  
│ ├── Main.java  
├── br.study.model  
│ ├── ControladorVoo.java  
│ ├── Voo.java  
│ ├── Assento.java  
├── br.study.server  
│ ├── Servidor.java
---
**Métodos principais:**

- `procurarVoo(String codigoVoo)` — retorna o voo pelo código, ou `null` se inválido.
- `verificarStatus(String codigoVoo, int numeroAssento)` — retorna um código de status:
    - `0`: assento disponível
    - `1`: assento indisponível
    - `2`: assento inexistente
    - `3`: voo inexistente
- `marcarVoo(String codigoVoo, int numeroAssento)` — marca o assento se disponível e retorna:
    - `4`: marcação realizada com sucesso
    - ou um dos códigos de erro acima.
- `validarCodigoVoo(String codigoVoo)` — verifica se o código do voo existe.
- `separarCodigoVoo(String codigoVoo)` — retorna o índice do voo na lista (para uso interno).

**Fluxo interno:**

1. `ControladorVoo` inicializa os voos de `"A1"` a `"A10"`.
2. Cada voo cria 50 assentos numerados de 1 a 50.
3. Métodos de verificação e marcação ajustam índices e validam entradas.