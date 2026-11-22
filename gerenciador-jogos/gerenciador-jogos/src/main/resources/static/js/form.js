const parametros = new URLSearchParams(window.location.search);
const idJogo = parametros.get('id');

const formulario = document.getElementById('formulario-jogo');
const campoTitulo = document.getElementById('titulo');
const campoAno = document.getElementById('ano');
const campoPlataforma = document.getElementById('plataforma');
const campoObservacoes = document.getElementById('observacoes');
const botaoSalvar = document.getElementById('btn-salvar');

function criarOpcao(valor, texto) {
    const opcao = document.createElement('option');
    opcao.value = valor;
    opcao.textContent = texto;
    return opcao;
}

async function carregarPlataformas() {
    const resposta = await fetch('/api/plataformas');
    if (!resposta.ok) {
        throw new Error('Falha ao carregar plataformas');
    }
    const plataformas = await resposta.json();
    campoPlataforma.textContent = '';
    campoPlataforma.appendChild(criarOpcao('', 'Selecione'));
    for (const plataforma of plataformas) {
        campoPlataforma.appendChild(criarOpcao(plataforma.id, plataforma.nome));
    }
    if (plataformas.length === 0) {
        document.getElementById('aviso-plataformas').classList.remove('oculto');
    }
}

async function carregarJogo() {
    const resposta = await fetch('/api/jogos/' + idJogo);
    if (!resposta.ok) {
        mostrarErroDaResposta('mensagem', await lerJson(resposta));
        botaoSalvar.disabled = true;
        return;
    }
    const jogo = await resposta.json();
    campoTitulo.value = jogo.titulo;
    campoAno.value = jogo.anoLancamento;
    campoPlataforma.value = jogo.plataformaId;
    document.getElementById(jogo.disponivel ? 'disponivel-sim' : 'disponivel-nao').checked = true;
    campoObservacoes.value = jogo.observacoes ? jogo.observacoes : '';
}

function montarCorpo() {
    return {
        titulo: campoTitulo.value,
        anoLancamento: campoAno.value === '' ? null : Number(campoAno.value),
        plataformaId: campoPlataforma.value === '' ? null : Number(campoPlataforma.value),
        disponivel: document.getElementById('disponivel-sim').checked,
        observacoes: campoObservacoes.value
    };
}

async function salvar(evento) {
    evento.preventDefault();
    esconderMensagem('mensagem');
    botaoSalvar.disabled = true;

    const url = idJogo ? '/api/jogos/' + idJogo : '/api/jogos';
    const metodo = idJogo ? 'PUT' : 'POST';

    try {
        const resposta = await fetch(url, {
            method: metodo,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(montarCorpo())
        });
        if (resposta.ok) {
            guardarMensagemDeSucesso(idJogo ? 'Jogo alterado com sucesso!' : 'Jogo cadastrado com sucesso!');
            window.location.href = 'index.html';
            return;
        }
        mostrarErroDaResposta('mensagem', await lerJson(resposta));
    } catch (e) {
        mostrarMensagem('mensagem', 'Não foi possível salvar o jogo.', 'erro');
    }
    botaoSalvar.disabled = false;
}

async function iniciar() {
    if (idJogo) {
        document.getElementById('titulo-formulario').textContent = 'Alterar jogo - ID ' + idJogo;
    }
    try {
        await carregarPlataformas();
        if (idJogo) {
            await carregarJogo();
        }
    } catch (e) {
        mostrarMensagem('mensagem', 'Não foi possível carregar os dados da tela.', 'erro');
    }
}

formulario.addEventListener('submit', salvar);
iniciar();
