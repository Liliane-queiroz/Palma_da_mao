// Máscara de CPF: formata o campo enquanto o usuário digita (123.456.789-09)

const campoCpf = document.getElementById('cpf');

campoCpf.addEventListener('input', function () {
    // Remove tudo que não for número
    let valor = campoCpf.value.replace(/\D/g, '');

    // Limita a 11 dígitos (tamanho do CPF)
    valor = valor.slice(0, 11);

    // Coloca o ponto depois do 3º dígito
    valor = valor.replace(/(\d{3})(\d)/, '$1.$2');
    // Coloca o ponto depois do 6º dígito
    valor = valor.replace(/(\d{3})(\d)/, '$1.$2');
    // Coloca o traço antes dos 2 últimos dígitos
    valor = valor.replace(/(\d{3})(\d{1,2})$/, '$1-$2');

    campoCpf.value = valor;
});