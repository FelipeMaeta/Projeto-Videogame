package br.senac.tads.dsw.gerenciador_jogos;

import java.util.List;

public interface JogoService {

    List<JogoDto> listar();

    JogoDto obter(Integer id);

    JogoDto incluir(JogoDto jogo);

    JogoDto alterar(Integer id, JogoDto jogo);

    void remover(Integer id);
}
