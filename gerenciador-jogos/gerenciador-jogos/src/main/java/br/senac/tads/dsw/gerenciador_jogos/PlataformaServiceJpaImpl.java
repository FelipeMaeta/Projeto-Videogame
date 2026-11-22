package br.senac.tads.dsw.gerenciador_jogos;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.senac.tads.dsw.gerenciador_jogos.entidade.PlataformaEntity;
import br.senac.tads.dsw.gerenciador_jogos.repositorio.PlataformaRepository;

@Service
public class PlataformaServiceJpaImpl implements PlataformaService {

    private final PlataformaRepository plataformaRepository;

    public PlataformaServiceJpaImpl(PlataformaRepository plataformaRepository) {
        this.plataformaRepository = plataformaRepository;
    }

    private PlataformaDto paraDto(PlataformaEntity entity) {
        return new PlataformaDto(entity.getId(), entity.getNome());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlataformaDto> listar() {
        return plataformaRepository.findAllByOrderByNomeAsc().stream()
                .map(this::paraDto)
                .toList();
    }

    @Override
    @Transactional
    public PlataformaDto incluir(PlataformaDto dto) {
        String nome = dto.nome().trim();
        if (plataformaRepository.existsByNomeIgnoreCase(nome)) {
            throw new RegraNegocioException("Já existe uma plataforma cadastrada com este nome");
        }
        PlataformaEntity entity = new PlataformaEntity();
        entity.setNome(nome);
        return paraDto(plataformaRepository.save(entity));
    }
}
