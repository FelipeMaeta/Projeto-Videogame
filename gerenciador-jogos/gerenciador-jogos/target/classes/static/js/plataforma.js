const formularioPlataforma = document.getElementById('formulario-plataforma');
const campoNome = document.getElementById('nome');
const botaoSalvarPlataforma = document.getElementById('btn-salvar');

async function salvarPlataforma(evento) {
    evento.preventDefault();
    esconderMensagem('mensagem');
    botaoSalvarPlataforma.disabled = true;

    try {
        const resposta = await fetch('/api/plataformas', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ nome: campoNome.value })
        });
        if (resposta.ok) {
            guardarMensagemDeSucesso('Plataforma cadastrada com sucesso!');
            window.location.href = 'index.html';
            return;
        }
        mostrarErroDaResposta('mensagem', await lerJson(resposta));
    } catch (e) {
        mostrarMensagem('mensagem', 'Não foi possível salvar a plataforma.', 'erro');
    }
    botaoSalvarPlataforma.disabled = false;
}

formularioPlataforma.addEventListener('submit', salvarPlataforma);
