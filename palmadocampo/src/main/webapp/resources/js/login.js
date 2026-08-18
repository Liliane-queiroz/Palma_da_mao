document.addEventListener('DOMContentLoaded', function() {
	const toggleSenha = document.getElementById('toggleSenha');
	const campoSenha = document.getElementById('senha');

	if (toggleSenha && campoSenha) {
		toggleSenha.addEventListener('click', function() {
			const tipoAtual = campoSenha.getAttribute('type');
			
			if (tipoAtual === 'password') {
				campoSenha.setAttribute('type', 'text');
				toggleSenha.classList.remove('bi-eye-slash');
				toggleSenha.classList.add('bi-eye');
			} else {
				campoSenha.setAttribute('type', 'password');
				toggleSenha.classList.remove('bi-eye');
				toggleSenha.classList.add('bi-eye-slash');
			}
		});
	}
});