package br.senac.tads.dsw.gerenciador_jogos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PromptController {

    private static final String CRIACAO = "Classe criada em 2026-11-22 16:04 pelo agente de IA Claude (Anthropic), modelo Claude Sonnet 5.5";

    @GetMapping(value = "/prompt", produces = "text/plain;charset=UTF-8")
    public String obterPrompt() throws IOException {
        Path arquivo = Path.of("PROMPT.md");
        if (Files.exists(arquivo)) {
            return Files.readString(arquivo, StandardCharsets.UTF_8);
        }
        return CRIACAO;
    }
}
