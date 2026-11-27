
const interesses = document.querySelectorAll('.interesse');
const pesquisa = document.getElementById('pesquisa');
const contador = document.getElementById('contador');

interesses.forEach(function (botao) {

    botao.setAttribute('aria-pressed', 'false');

    botao.addEventListener('click', function () {

        const selecionados = document.querySelectorAll('.interesse.selecionado');

        if (!botao.classList.contains('selecionado') && selecionados.length >= 5) {
            return;
        }

        botao.classList.toggle('selecionado');

        botao.setAttribute(
            'aria-pressed',
            botao.classList.contains('selecionado')
        );

        const quantidade = document.querySelectorAll('.interesse.selecionado').length;

        contador.textContent = quantidade + ' de 5 selecionados';

    });

});

pesquisa.addEventListener('input', function () {

    const texto = pesquisa.value.toLowerCase().trim();

    interesses.forEach(function (botao) {

        const nome = botao.textContent.toLowerCase();

        botao.hidden = !nome.includes(texto);

    });

});
