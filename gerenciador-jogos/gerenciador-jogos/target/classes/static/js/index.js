const listaJogos = document.getElementById('lista-jogos');
const dialogoExclusao = document.getElementById('dialogo-exclusao');
const textoExclusao = document.getElementById('texto-exclusao');
let idParaExcluir = null;

function criarElemento(tag, texto, classe) {
    const elemento = document.createElement(tag);
    if (texto !== undefined) {
        elemento.textContent = texto;
    }
    if (classe) {
        elemento.className = classe;
    }
    return elemento;
}

function criarCartaoJogo(jogo) {
    const cartao = criarElemento('article', undefined, 'jogo');

    const info = document.createElement('div');
    const titulo = criarElemento('h2', jogo.titulo);
    info.appendChild(titulo);
    if (!jogo.disponivel) {
        info.appendChild(criarElemento('span', 'INDISPONÍVEL', 'etiqueta-indisponivel'));
    }
    info.appendChild(criarElemento('p', 'Ano de lançamento: ' + jogo.anoLancamento));
    info.appendChild(criarElemento('p', 'Plataforma: ' + jogo.plataformaNome));
    if (jogo.observacoes) {
        info.appendChild(criarElemento('p', jogo.observacoes, 'observacoes'));
    }

    const acoes = criarElemento('div', undefined, 'acoes');
    const alterar = criarElemento('a', 'Alterar', 'botao azul');
    alterar.href = 'form.html?id=' + jogo.id;
    const excluir = criarElemento('button', 'Excluir', 'botao vermelho');
    excluir.type = 'button';
    excluir.addEventListener('click', () => abrirConfirmacao(jogo.id));
    acoes.appendChild(alterar);
    acoes.appendChild(excluir);

    cartao.appendChild(info);
    cartao.appendChild(acoes);
    return cartao;
}

function renderizarJogos(jogos) {
    listaJogos.textContent = '';
    if (jogos.length === 0) {
        listaJogos.appendChild(criarElemento('p', 'Nenhum jogo cadastrado.', 'vazio'));
        return;
    }
    for (const jogo of jogos) {
        listaJogos.appendChild(criarCartaoJogo(jogo));
    }
}

async function carregarJogos() {
    try {
        const resposta = await fetch('/api/jogos');
        if (!resposta.ok) {
            mostrarErroDaResposta('mensagem', await lerJson(resposta));
            return;
        }
        renderizarJogos(await resposta.json());
    } catch (e) {
        mostrarMensagem('mensagem', 'Não foi possível carregar a lista de jogos.', 'erro');
    }
}

function abrirConfirmacao(id) {
    idParaExcluir = id;
    textoExclusao.textContent = 'Deseja excluir o jogo ID ' + id + '?';
    dialogoExclusao.showModal();
}

async function confirmarExclusao() {
    const id = idParaExcluir;
    dialogoExclusao.close();
    try {
        const resposta = await fetch('/api/jogos/' + id, { method: 'DELETE' });
        if (resposta.ok) {
            mostrarMensagem('mensagem', 'Jogo excluído com sucesso!', 'sucesso');
        } else {
            mostrarErroDaResposta('mensagem', await lerJson(resposta));
        }
    } catch (e) {
        mostrarMensagem('mensagem', 'Não foi possível excluir o jogo.', 'erro');
    }
    await carregarJogos();
}

document.getElementById('btn-sim').addEventListener('click', confirmarExclusao);
document.getElementById('btn-nao').addEventListener('click', () => dialogoExclusao.close());

exibirMensagemGuardada('mensagem');
carregarJogos();
