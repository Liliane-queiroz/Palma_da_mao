// Máscara de CPF: formata o campo enquanto o usuário digita (123.456.789-09)

const campoCpf = document.getElementById('cpf');

if (campoCpf) {
    campoCpf.addEventListener('input', function () {
        let valor = campoCpf.value.replace(/\D/g, '');
        valor = valor.slice(0, 11);
        valor = valor.replace(/(\d{3})(\d)/, '$1.$2');
        valor = valor.replace(/(\d{3})(\d)/, '$1.$2');
        valor = valor.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
        campoCpf.value = valor;
    });
}


// ===== Máscara de telefone: (69) 99999-9999 =====
// Formata visualmente enquanto digita, mas envia só números pro banco.

const campoTelefone = document.getElementById('telefone');

if (campoTelefone) {
    campoTelefone.addEventListener('input', function () {
        // Só os dígitos, joga fora o resto
        let numeros = campoTelefone.value.replace(/\D/g, '');
        // Limita a 11 (DDD + 9 dígitos)
        numeros = numeros.substring(0, 11);

        let formatado = numeros;
        if (numeros.length > 2 && numeros.length <= 7) {
            formatado = '(' + numeros.substring(0, 2) + ') ' + numeros.substring(2);
        } else if (numeros.length > 7) {
            formatado = '(' + numeros.substring(0, 2) + ') '
                    + numeros.substring(2, 7) + '-' + numeros.substring(7);
        }
        campoTelefone.value = formatado;
    });

    // Antes de enviar o formulário, limpa a máscara: só números vão pro banco
    const formulario = campoTelefone.closest('form');
    if (formulario) {
        formulario.addEventListener('submit', function () {
            campoTelefone.value = campoTelefone.value.replace(/\D/g, '');
        });
    }
}