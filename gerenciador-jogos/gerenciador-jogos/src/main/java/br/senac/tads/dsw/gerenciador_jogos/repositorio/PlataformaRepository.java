package br.senac.tads.dsw.gerenciador_jogos.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.senac.tads.dsw.gerenciador_jogos.entidade.PlataformaEntity;

@Repository
public interface PlataformaRepository extends JpaRepository<PlataformaEntity, Integer> {

    boolean existsByNomeIgnoreCase(String nome);

    List<PlataformaEntity> findAllByOrderByNomeAsc();
}
