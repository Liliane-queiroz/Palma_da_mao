// ===== MODAL DE DELETAR =====

let produtoIdParaDeletar = null;

// Abre o modal quando clica no botão deletar
document.querySelectorAll('.btn-deletar').forEach(botao => {
	botao.addEventListener('click', function(e) {
		e.preventDefault();
		produtoIdParaDeletar = this.getAttribute('data-id');
		document.getElementById('modal-deletar').style.display = 'flex';
	});
});

// Fecha o modal ao clicar "Cancelar"
document.getElementById('btn-cancelar-deletar').addEventListener('click', function() {
	document.getElementById('modal-deletar').style.display = 'none';
	produtoIdParaDeletar = null;
});

// Fecha o modal ao clicar fora dele
document.getElementById('modal-deletar').addEventListener('click', function(e) {
	if (e.target === this) {
		this.style.display = 'none';
		produtoIdParaDeletar = null;
	}
});

// Confirma a deletação e chama o backend
document.getElementById('btn-confirmar-deletar').addEventListener('click', function() {
	if (!produtoIdParaDeletar) {
		alert('Erro: ID do produto não encontrado');
		return;
	}

	// Faz a requisição AJAX POST pro Servlet
	fetch(window.location.pathname.replace('/meus-anuncios', '') + '/deletar-produto', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded'
		},
		body: 'produtoId=' + produtoIdParaDeletar
	})
	.then(response => response.json())
	.then(dados => {
		if (dados.sucesso) {
			// Mostra notificação de sucesso
			mostrarNotificacao(dados.mensagem, 'sucesso');
			
			// Recarrega depois de 1.5 segundos (tempo pra ver a notificação)
			setTimeout(() => {
				location.reload();
			}, 1500);
		} else {
			mostrarNotificacao('Erro: ' + dados.erro, 'erro');
		}
	})
	.catch(erro => {
		console.error('Erro na requisição:', erro);
		mostrarNotificacao('Erro ao deletar anúncio', 'erro');
	});
	});

	// ===== FUNÇÃO DE NOTIFICAÇÃO =====
	function mostrarNotificacao(mensagem, tipo) {
		// Remove notificação anterior se existir
		const notificacaoAnterior = document.querySelector('.notificacao');
		if (notificacaoAnterior) {
			notificacaoAnterior.remove();
		}

		// Cria o elemento da notificação
		const notificacao = document.createElement('div');
		notificacao.className = 'notificacao ' + tipo;
		notificacao.textContent = mensagem;

		// Adiciona ao topo da página
		document.body.insertBefore(notificacao, document.body.firstChild);

		// Remove após 3 segundos
		setTimeout(() => {
			notificacao.classList.add('fade-out');
			setTimeout(() => {
				notificacao.remove();
			}, 300);
		}, 3000);
	}
