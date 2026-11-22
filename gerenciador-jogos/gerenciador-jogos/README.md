# Gerenciador de Jogos de Videogames

Atividade final de DSW (TADS Senac, Turma 3B Noturno, 2026-2).

## Como executar

    mvn spring-boot:run

ou executar a classe `GerenciadorJogosApplication` pela IDE. Depois acessar:

- Telas: http://localhost:8080/
- Console do H2: http://localhost:8080/h2-console (JDBC URL `jdbc:h2:mem:videogames`, usuário `sa`, senha vazia)
- Registro de prompts de IA: http://localhost:8080/prompt

## Endpoints REST

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | /api/jogos | Lista os jogos |
| GET | /api/jogos/{id} | Obtém um jogo |
| POST | /api/jogos | Inclui um jogo |
| PUT | /api/jogos/{id} | Altera um jogo |
| DELETE | /api/jogos/{id} | Exclui um jogo |
| GET | /api/plataformas | Lista as plataformas |
| POST | /api/plataformas | Cadastra uma plataforma |
