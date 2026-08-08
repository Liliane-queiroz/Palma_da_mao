const arquivoInput = document.getElementById('arquivo');
const previewContainer = document.getElementById('preview-fotos');
const MAX_FOTOS = 5;
const MAX_TAMANHO = 5 * 1024 * 1024; // 5MB

// Array pra guardar as fotos selecionadas
let fotosSelecionadas = [];

arquivoInput.addEventListener('change', function(event) {
    const arquivos = Array.from(event.target.files);
    
    // Validar quantidade
    if (arquivos.length > MAX_FOTOS) {
        mostrarErro(`Máximo ${MAX_FOTOS} fotos permitidas!`);
        arquivoInput.value = '';
        return;
    }

    // Validar tamanho de cada arquivo
    for (let arquivo of arquivos) {
        if (arquivo.size > MAX_TAMANHO) {
            mostrarErro(`Arquivo "${arquivo.name}" ultrapassa 5 MB!`);
            arquivoInput.value = '';
            return;
        }
    }

    // Se passou nas validações
    fotosSelecionadas = arquivos;
    mostrarSucesso(`${arquivos.length} foto(s) selecionada(s)`);
    renderizarPreview();
});

function renderizarPreview() {
    previewContainer.innerHTML = '';

    fotosSelecionadas.forEach((arquivo, index) => {
        const reader = new FileReader();

        reader.onload = function(e) {
            const div = document.createElement('div');
            div.className = 'preview-item';
            
            div.innerHTML = `
                <img src="${e.target.result}" alt="Preview ${index + 1}">
                <button type="button" class="remove-btn" onclick="removerFoto(${index})">×</button>
            `;

            previewContainer.appendChild(div);
        };

        reader.readAsDataURL(arquivo);
    });
}

function removerFoto(index) {
    fotosSelecionadas.splice(index, 1);
    
    // Atualizar o input file
    const dataTransfer = new DataTransfer();
    fotosSelecionadas.forEach(arquivo => {
        dataTransfer.items.add(arquivo);
    });
    arquivoInput.files = dataTransfer.files;

    renderizarPreview();
    
    if (fotosSelecionadas.length === 0) {
        limparMensagens();
    }
}

function mostrarErro(mensagem) {
    const msg = document.createElement('div');
    msg.className = 'error-message';
    msg.textContent = mensagem;
    
    previewContainer.innerHTML = '';
    previewContainer.appendChild(msg);
}

function mostrarSucesso(mensagem) {
    const msg = document.createElement('div');
    msg.className = 'success-message';
    msg.textContent = mensagem;
    
    if (previewContainer.querySelector('.error-message')) {
        previewContainer.innerHTML = '';
    }
    previewContainer.appendChild(msg);
}

function limparMensagens() {
    const msg = previewContainer.querySelector('.success-message');
    if (msg) msg.remove();
}