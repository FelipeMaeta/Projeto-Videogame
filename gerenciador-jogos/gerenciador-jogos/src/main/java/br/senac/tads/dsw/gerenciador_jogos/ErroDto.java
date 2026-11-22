package br.senac.tads.dsw.gerenciador_jogos;

import java.util.List;

public record ErroDto(String mensagem, List<String> erros) {
}
