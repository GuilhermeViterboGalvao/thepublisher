<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" 											 %>
<style type="text/css">
	.info { float: left; width: 100%; margin: 0 0 2% 0; }
	.info img { float: left; width: 100%; }
	.info .text { float: left; width: 98%; padding: 1%; background-color: #2b2b2b; }
	.info .text p { float: left; width: 100%; font-size: 1em; line-height: 1.4em; font-weight: 600; color: #fff; }
	.info .text .click { float: left; width: 100%; font-size: 0.8em; line-height: 1.2em; font-weight: 600; color: #fff; text-align: center; margin: 1.5% 0; }
	.info .text .click a { color: red; text-decoration: none; }
	
	.errors { float: left; width: 100%; margin: 1% 0; }
	.errors ul { float: left; width: 100%; }
	.errors ul li { float: left; width: 100%; }
	.errors ul li span { float: left; width: 100%; font-size: 1.2em; line-height: 1.4em; color: red; text-align: center; }
	
	form { float: left; width: 100%; }
	form input[type="text"],
	form input[type="password"] { float: left; width: 98%; border: 0.01em solid #000; border-radius: 5px; margin: 0 0 1.5% 0; padding: 1%; font-size: 1em; }
	form .checkbox { float: left; width: 100%; margin: 0 0 1.5% 0; }
	form .checkbox input[type="checkbox"] { float: left; width: 2%; border: none; margin: 0.4% 0; }
	form .checkbox span { float: left; width: 98%; font-size: 1em; line-height: 1.2em; }
	form label { float: left; width: 100%; font-size: 1.2em; line-height: 1.4em; margin: 0 0 1% 0; }
	form input[type="submit"] { float: left; width: 100%; padding: 1%; font-size: 1em; font-weight: 600; border: 0.01em solid #000; border-radius: 5px; margin: 1% 0; background-color: #ffefa3; }
	
	.message { float: left; width: 100%; margin: 10% 0; }
	.message p { float: left; width: 100%; font-size: 1em; line-height: 1.4em; font-weight: 800; text-align: center; }
</style>
<div class="info">
	<img alt="banner.jpg" src="/skins/tatame/promocoes/2016/minotauro-lenda-do-mma/img/banner.jpg"/>
	<div class="text">
		<p>Cadastre seus dados no formulário abaixo e responda:</p>
		<p>Por que Rodrigo Minotauro é considerado uma lenda do MMA nacional?</p>
		<p></p>
		<p>Será escolhido o top 10 das respostas mais criativas. As duas melhores respostas</p>
		<p>levam um relógio EWC e os oito restantes um Almanaque Combate. Seja criativo!</p>
		<p class="click">confira o regulamento do concurso <a href="/promocoes/2016/minotauro-lenda-do-mma/regulamento">aqui</a></p>
	</div>
</div>
<s:if test="success">
	<div class="message">
		<p>Cadastro concluído com sucesso!</p>
	</div>
</s:if>
<s:else>
	<s:if test="hasFieldErrors()">
		<div class="errors">
			<s:fielderror/>
		</div>
	</s:if>
	<form action="/promotion" method="post">
		<input type="text" name="name" value="${name}" placeholder="Nome"/>
		<input type="text" name="document" value="${document}" placeholder="CPF"/>
		<input type="text" name="email" value="${email}" placeholder="E-mail"/>
		<input type="password" name="password" value="${password}" placeholder="Senha"/>
		<input type="password" name="password2" value="${password2}" placeholder="Confirme senha"/>
		<label for="answer">Por que Rodrigo Minotauro é considerado uma lenda do MMA nacional?</label>
		<input type="text" name="answer" value="${answer}" placeholder="Escreva sua resposta"/>
		<div class="checkbox">
			<input id="agree" type="checkbox" name="agree"/>
			<span>Concordo com o regulamento do concurso.</span>
		</div>
		<div class="checkbox">
			<input id="confirm" type="checkbox" name="confirm"/>
			<span>Aceito receber ofertas e promoções especiais do Clube TATAME e parceiros via e-mail.</span>
		</div>	
		<input id="submit" type="submit" value="Enviar"/>	
	</form>
	<script type="text/javascript">
		document.getElementById("submit").onclick = function() {
			if (!document.getElementById("agree").checked) {
				alert("Você precisa concordar com o regulamento do concurso.");
				return false;
			}
			return true;		
		};
	</script>	
</s:else>