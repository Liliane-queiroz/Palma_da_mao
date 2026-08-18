document.addEventListener('DOMContentLoaded', function() {
	const btnMenuProdutor = document.querySelector('.btn-menu-produtor');
	const dropdownMenu = document.getElementById('dropdownMenuProdutor');
	const btnFecharMenu = document.getElementById('btnFecharMenu');
	const menuOverlay = document.getElementById('menuOverlay');

	// Abre/fecha o menu ao clicar no botão
	if (btnMenuProdutor) {
		btnMenuProdutor.addEventListener('click', function() {
			dropdownMenu.classList.toggle('ativo');
			menuOverlay.classList.toggle('ativo');
			btnMenuProdutor.classList.toggle('ativo');
		});
	}

	// Fecha o menu ao clicar no X
	if (btnFecharMenu) {
		btnFecharMenu.addEventListener('click', function() {
			dropdownMenu.classList.remove('ativo');
			menuOverlay.classList.remove('ativo');
			btnMenuProdutor.classList.remove('ativo');
		});
	}

	// Fecha o menu ao clicar no overlay
	if (menuOverlay) {
		menuOverlay.addEventListener('click', function() {
			dropdownMenu.classList.remove('ativo');
			menuOverlay.classList.remove('ativo');
			btnMenuProdutor.classList.remove('ativo');
		});
	}

	// Fecha o menu ao clicar em um link (exceto "Sair")
	const menuLinks = document.querySelectorAll('.menu-item:not(.menu-item-sair)');
	menuLinks.forEach(link => {
		link.addEventListener('click', function() {
			dropdownMenu.classList.remove('ativo');
			menuOverlay.classList.remove('ativo');
			btnMenuProdutor.classList.remove('ativo');
		});
	});
});