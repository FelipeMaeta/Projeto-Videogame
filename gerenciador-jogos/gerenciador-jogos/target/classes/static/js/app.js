function mostrarMensagem(idElemento, texto, tipo, listaErros) {
    const elemento = document.getElementById(idElemento);
    elemento.className = 'mensagem ' + tipo;
    elemento.textContent = '';

    const paragrafo = document.createElement('div');
    paragrafo.textContent = texto;
    elemento.appendChild(paragrafo);

    if (listaErros && listaErros.length > 0) {
        const ul = document.createElement('ul');
        for (const erro of listaErros) {
            const li = document.createElement('li');
            li.textContent = erro;
            ul.appendChild(li);
        }
        elemento.appendChild(ul);
    }
}

function esconderMensagem(idElemento) {
    const elemento = document.getElementById(idElemento);
    elemento.className = 'mensagem oculto';
    elemento.textContent = '';
}

function mostrarErroDaResposta(idElemento, dados) {
    const texto = (dados && dados.mensagem) ? dados.mensagem : 'Ocorreu um erro inesperado. Tente novamente.';
    const erros = (dados && dados.erros) ? dados.erros : [];
    mostrarMensagem(idElemento, texto, 'erro', erros);
}

async function lerJson(resposta) {
    try {
        return await resposta.json();
    } catch (e) {
        return null;
    }
}

function guardarMensagemDeSucesso(texto) {
    sessionStorage.setItem('mensagemSucesso', texto);
}

function exibirMensagemGuardada(idElemento) {
    const texto = sessionStorage.getItem('mensagemSucesso');
    if (texto) {
        sessionStorage.removeItem('mensagemSucesso');
        mostrarMensagem(idElemento, texto, 'sucesso');
    }
}
