
const formulario = document.querySelector('form');
const senha = document.getElementById('senha');
const confirmarSenha = document.getElementById('confirmarSenha');
const telefone = document.getElementById('telefone');
const identificador = document.getElementById('identificador');
const codigo = document.getElementById('codigo');

// Mostrar e ocultar senhas

document.querySelectorAll('.olho').forEach(function (botao) {

    botao.addEventListener('click', function () {

        const idCampo = botao.getAttribute('aria-controls');
        const campo = document.getElementById(idCampo);

        const mostrar = campo.type === 'password';

        campo.type = mostrar ? 'text' : 'password';

        botao.setAttribute('aria-pressed', mostrar);

        botao.setAttribute(
            'aria-label',
            mostrar ? 'Ocultar senha' : 'Mostrar senha'
        );

    });

});

// Validar confirmação de senha

function validarSenhas() {

    if (!senha || !confirmarSenha) return;

    if (senha.value !== confirmarSenha.value) {
        confirmarSenha.setCustomValidity('As senhas precisam ser iguais.');
    } else {
        confirmarSenha.setCustomValidity('');
    }

}

// Validar formato do telefone

function telefoneValido(valor) {

    const formato = /^(\(\d{2}\)|\d{2})\s?\d{4,5}-?\d{4}$/;

    return formato.test(valor.trim());

}

function validarTelefone() {

    if (!telefone) return;

    telefone.setCustomValidity(
        telefoneValido(telefone.value)
            ? ''
            : 'Digite um telefone válido com DDD.'
    );

}

// Validar e-mail ou telefone da recuperação

function validarIdentificador() {

    if (!identificador) return;

    const valor = identificador.value.trim();
    const formatoEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    const valido = formatoEmail.test(valor) || telefoneValido(valor);

    identificador.setCustomValidity(
        valido ? '' : 'Digite um e-mail ou telefone válido.'
    );

}

// Código provisório para testar a interface

function validarCodigo() {

    if (!codigo) return;

    codigo.setCustomValidity(
        codigo.value === '123456'
            ? ''
            : 'Código incorreto. Utilize 123456 para testar.'
    );

}

// Validações compartilhadas

function validarCampos() {

    validarSenhas();
    validarTelefone();
    validarIdentificador();
    validarCodigo();

}

// Envio dos formulários

if (formulario) {

    formulario.addEventListener('input', validarCampos);

    formulario.addEventListener('submit', function (evento) {

        evento.preventDefault();

        validarCampos();

        if (!formulario.reportValidity()) return;

        switch (formulario.id) {

            case 'login':

                document.querySelector('.aviso').textContent =
                    'Login real depende do back-end.';

                break;

            case 'cadastro':

                window.location.href = 'paracomecar.html';

                break;

            case 'recuperacao':

                if (identificador.value.includes('@')) {
                    window.location.href = 'codigo-email.html';
                } else {
                    window.location.href = 'codigo-sms.html';
                }

                break;

            case 'verificacao':

                window.location.href = 'nova-senha.html';

                break;

            case 'novaSenha':

                window.location.href = 'senha-alterada.html';

                break;

        }

    });

    const botaoEnviar = formulario.querySelector('button[type="submit"]');

    if (botaoEnviar) {
        botaoEnviar.disabled = false;
    }

}
