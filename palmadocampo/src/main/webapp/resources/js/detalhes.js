let indiceAtual = 0;

function mostrarFoto(indice) {
    if (fotos.length === 0) return;
    indiceAtual = (indice + fotos.length) % fotos.length;

    document.getElementById('imagem-principal').src = fotos[indiceAtual];
    document.getElementById('imagem-fundo').src = fotos[indiceAtual];

    document.querySelectorAll('.miniatura').forEach(function (mini, i) {
        if (i === indiceAtual) {
            mini.classList.add('miniatura-ativa');
        } else {
            mini.classList.remove('miniatura-ativa');
        }
    });
}

function trocarFoto(direcao) {
    mostrarFoto(indiceAtual + direcao);
}

document.addEventListener('DOMContentLoaded', function () {
    if (typeof fotos !== 'undefined' && fotos.length <= 1) {
        document.querySelectorAll('.seta-foto').forEach(function (seta) {
            seta.style.display = 'none';
        });
    }
});