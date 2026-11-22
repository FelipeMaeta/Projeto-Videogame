package br.senac.tads.dsw.gerenciador_jogos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PlataformaDto(
        Integer id,

        @NotBlank(message = "O nome da plataforma é obrigatório")
        @Size(max = 60, message = "O nome da plataforma deve ter no máximo 60 caracteres")
        String nome) {
}
