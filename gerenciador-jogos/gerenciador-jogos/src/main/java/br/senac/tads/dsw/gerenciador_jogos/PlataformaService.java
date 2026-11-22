package br.senac.tads.dsw.gerenciador_jogos;

import java.util.List;

public interface PlataformaService {

    List<PlataformaDto> listar();

    PlataformaDto incluir(PlataformaDto plataforma);
}
