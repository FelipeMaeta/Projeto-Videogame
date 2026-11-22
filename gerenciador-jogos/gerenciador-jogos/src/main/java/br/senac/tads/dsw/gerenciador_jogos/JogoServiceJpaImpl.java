package br.senac.tads.dsw.gerenciador_jogos;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.senac.tads.dsw.gerenciador_jogos.entidade.JogoEntity;
import br.senac.tads.dsw.gerenciador_jogos.entidade.PlataformaEntity;
import br.senac.tads.dsw.gerenciador_jogos.repositorio.JogoRepository;
import br.senac.tads.dsw.gerenciador_jogos.repositorio.PlataformaRepository;

@Service
public class JogoServiceJpaImpl implements JogoService {

    private final JogoRepository jogoRepository;
    private final PlataformaRepository plataformaRepository;

    public JogoServiceJpaImpl(JogoRepository jogoRepository, PlataformaRepository plataformaRepository) {
        this.jogoRepository = jogoRepository;
        this.plataformaRepository = plataformaRepository;
    }

    private JogoDto paraDto(JogoEntity entity) {
        return new JogoDto(
                entity.getId(),
                entity.getTitulo(),
                entity.getAnoLancamento(),
                entity.getPlataforma().getId(),
                entity.getPlataforma().getNome(),
                entity.getDisponivel(),
                entity.getObservacoes());
    }

    private void copiarDados(JogoDto dto, JogoEntity entity) {
        PlataformaEntity plataforma = plataformaRepository.findById(dto.plataformaId())
                .orElseThrow(() -> new RegraNegocioException("A plataforma selecionada não existe"));
        String observacoes = dto.observacoes();

        entity.setTitulo(dto.titulo().trim());
        entity.setAnoLancamento(dto.anoLancamento());
        entity.setDisponivel(dto.disponivel());
        entity.setObservacoes(observacoes == null || observacoes.isBlank() ? null : observacoes.trim());
        entity.setPlataforma(plataforma);
    }

    private JogoEntity buscarEntity(Integer id) {
        return jogoRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Jogo " + id + " não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<JogoDto> listar() {
        return jogoRepository.findAllByOrderByIdAsc().stream()
                .map(this::paraDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public JogoDto obter(Integer id) {
        return paraDto(buscarEntity(id));
    }

    @Override
    @Transactional
    public JogoDto incluir(JogoDto dto) {
        JogoEntity entity = new JogoEntity();
        copiarDados(dto, entity);
        return paraDto(jogoRepository.save(entity));
    }

    @Override
    @Transactional
    public JogoDto alterar(Integer id, JogoDto dto) {
        JogoEntity entity = buscarEntity(id);
        copiarDados(dto, entity);
        return paraDto(jogoRepository.save(entity));
    }

    @Override
    @Transactional
    public void remover(Integer id) {
        jogoRepository.delete(buscarEntity(id));
    }
}
