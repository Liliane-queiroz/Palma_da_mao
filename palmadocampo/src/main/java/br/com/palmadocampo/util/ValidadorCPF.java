package br.com.palmadocampo.util;

public class ValidadorCPF {

    /* Verifica se um CPF é válido pelo cálculo dos dígitos verificadores.
       Recebe o CPF em qualquer formato (com ou sem pontos/traço). */
    public static boolean validar(String cpf) {
        if (cpf == null) {
            return false;
        }

        // Remove tudo que não for número (pontos, traços, espaços)
        String numeros = cpf.replaceAll("[^0-9]", "");

        // CPF tem que ter exatamente 11 dígitos
        if (numeros.length() != 11) {
            return false;
        }

        // Rejeita CPFs com todos os dígitos iguais (000..., 111..., etc.)
        // São matematicamente válidos mas são inválidos por regra
        if (numeros.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int primeiroDigito = calcularDigito(numeros, 9, 10);
        // Calcula o segundo dígito verificador
        int segundoDigito = calcularDigito(numeros, 10, 11);

        // Compara os dígitos calculados com os que estão no CPF
        return primeiroDigito == Character.getNumericValue(numeros.charAt(9))
            && segundoDigito == Character.getNumericValue(numeros.charAt(10));
    }

    /* Calcula um dígito verificador do CPF.
       quantidade = quantos dígitos usar; pesoInicial = peso do primeiro dígito. */
    private static int calcularDigito(String numeros, int quantidade, int pesoInicial) {
        int soma = 0;
        int peso = pesoInicial;

        for (int i = 0; i < quantidade; i++) {
            soma += Character.getNumericValue(numeros.charAt(i)) * peso;
            peso--;
        }

        int resto = soma % 11;
        return (resto < 2) ? 0 : (11 - resto);
    }
}