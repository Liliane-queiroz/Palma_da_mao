// ===== SETAS DA BARRA DE CATEGORIAS =====
// Faz a barra de categorias deslizar pros lados ao clicar nas setas,
// e mostra/esconde cada seta conforme a posição do deslize.

// Pega os elementos da página pelo id (o crachá único de cada um)
const esteira = document.getElementById('esteiraCategorias');   // a "fita" que segura os links e desliza
const setaEsquerda = document.getElementById('setaEsquerda');  
const setaDireita = document.getElementById('setaDireita');     

// Quantos pixels a esteira desliza a cada clique numa seta
const distancia = 300;

// Quando clica na seta DIREITA: empurra a esteira 300px pra direita
setaDireita.addEventListener('click', function () {
    // scrollBy = "role a partir de onde está"; left positivo = pra direita
    // behavior: 'smooth' = deslize animado, em vez de pular seco
    esteira.scrollBy({ left: distancia, behavior: 'smooth' });
});

// Quando clica na seta ESQUERDA: empurra 300px pra esquerda
setaEsquerda.addEventListener('click', function () {
    // left negativo = pra esquerda
    esteira.scrollBy({ left: -distancia, behavior: 'smooth' });
});

// Decide quais setas ficam visíveis conforme a posição atual da esteira
function atualizarSetas() {
    // scrollLeft = quanto a esteira já foi deslizada a partir do começo (0 = início)
    // Se está tudo no começo, não faz sentido ter seta esquerda -> esconde
    if (esteira.scrollLeft <= 0) {
        setaEsquerda.classList.add('escondida');    // adiciona a classe que esconde (display:none no CSS)
    } else {
        setaEsquerda.classList.remove('escondida'); // tira a classe -> a seta reaparece
    }

    // Verifica se chegou no fim da esteira:
    // posição deslizada (scrollLeft) + largura visível (clientWidth)
    // alcança a largura total do conteúdo (scrollWidth)?
    // O "- 1" é uma folguinha pra evitar erro de arredondamento de pixel
    const chegouNoFim = esteira.scrollLeft + esteira.clientWidth >= esteira.scrollWidth - 1;

    if (chegouNoFim) {
        setaDireita.classList.add('escondida');     // no fim, esconde a seta direita
    } else {
        setaDireita.classList.remove('escondida');
    }
}

// Roda a função toda vez que a esteira é deslizada (pra atualizar as setas em tempo real)
esteira.addEventListener('scroll', atualizarSetas);

// Roda a função uma vez quando a página termina de carregar
// (pra já começar com a seta esquerda escondida, já que scrollLeft é 0 no início)
window.addEventListener('load', atualizarSetas);