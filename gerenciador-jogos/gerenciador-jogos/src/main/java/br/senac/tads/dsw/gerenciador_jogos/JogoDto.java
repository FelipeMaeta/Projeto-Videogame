package br.senac.tads.dsw.gerenciador_jogos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record JogoDto(
        Integer id,

        @NotBlank(message = "O título é obrigatório")
        @Size(max = 150, message = "O título deve ter no máximo 150 caracteres")
        String titulo,

        @NotNull(message = "O ano de lançamento é obrigatório")
        @Min(value = 1950, message = "O ano de lançamento deve ser maior ou igual a 1950")
        Integer anoLancamento,

        @NotNull(message = "A plataforma é obrigatória")
        Integer plataformaId,

        String plataformaNome,

        @NotNull(message = "A disponibilidade é obrigatória")
        Boolean disponivel,

        @Size(max = 500, message = "As observações devem ter no máximo 500 caracteres")
        String observacoes) {
}
