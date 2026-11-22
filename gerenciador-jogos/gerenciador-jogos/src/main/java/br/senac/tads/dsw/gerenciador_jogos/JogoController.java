package br.senac.tads.dsw.gerenciador_jogos;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/jogos")
public class JogoController {

    private final JogoService jogoService;

    public JogoController(JogoService jogoService) {
        this.jogoService = jogoService;
    }

    @GetMapping
    public List<JogoDto> listar() {
        return jogoService.listar();
    }

    @GetMapping("/{id}")
    public JogoDto obter(@PathVariable("id") Integer id) {
        return jogoService.obter(id);
    }

    @PostMapping
    public ResponseEntity<JogoDto> incluir(@RequestBody @Valid JogoDto jogo) {
        JogoDto salvo = jogoService.incluir(jogo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.id())
                .toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @PutMapping("/{id}")
    public JogoDto alterar(@PathVariable("id") Integer id, @RequestBody @Valid JogoDto jogo) {
        return jogoService.alterar(id, jogo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable("id") Integer id) {
        jogoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
