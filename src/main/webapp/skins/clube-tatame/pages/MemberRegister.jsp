<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"              								 %>
<style type="text/css">
	.erro-messages { float: left; width: 50%; margin: 2.5% 25% 0 25%; }
	.erro-messages .error { float: left; width: 100%; }
	.erro-messages .error li { float: left; width: 100%; }
	.erro-messages .error li span { float: left; width: 100%; text-align: center; color: red; font-size: 1.2em; line-height: 1.4em; margin: 0 0 1% 0; }
	
	.success { float: left; width: 50%; margin: 2.5% 25% 0 25%; }
	.success label { float: left; width: 100%; font-size: 1.2em; line-height: 1.4em; text-align: center; }
	
	.boxs { margin: 0 auto; width: 100%; max-width: 944px; }
	
	.content { float: left; width: 100%; height: auto; background-color: #f6f5f5; margin: 3% 0 0; }
	
	.form { float:left; width: 55%; padding: 2% 5%; }
	.form  .title { float: left; font-size: 1.2em; text-transform: uppercase;  }
	.form form { float: left; width: 57%; margin: 7% 0; }
	.form form input[type="text"],
	.form form input[type="email"],
	.form form input[type="password"] { float: left; width: 98%; margin: 0 0 6% 0; border: 1px solid #999999; font-size: 0.7em; padding: 1% 2%; line-height: 1.1em; border-radius: 14px; }
	.form form input[type="submit"] { float: left; padding: 1.5% 2%; background-color: #ffefa3; font-size: 0.7em; margin: 8% 0; text-align: center; font-weight: 600; color: #000; border: 0.1em solid #414142; border-radius: 5px; text-transform: uppercase; }
	
	.info { float:left; width: 25%; padding: 2% 4%; border-left: 1px solid #000; }
	.info .premium { float: left; text-align: center; }
	.info .premium img { width: 100px; }
	.info .premium p { font-size: 0.8em; line-height: 1.4em; }
	
	.info .important { float:left; font-size: 0.9em; margin: 8% 0; text-align: center; line-height: 1.3em }
	
	.info .free { float: left; text-align: center; }
	.info .free img { width: 100px; }
	.info .free p { font-size: 0.8em; line-height: 1.4em; }
	
	.grey { color: #787878; }
	
	@media screen and (max-width: 800px) {
		.erro-messages { width: 98%; margin: 5% 1% 0 1%; }
		.erro-messages .error li span { font-size: 1em; line-height: 1.2em; }
		.success { width: 98%; margin: 5% 1% 0 1%; }
		.success label { font-size: 1em; line-height: 1.2em; }	
		
		.boxs { max-width: none; }
		
		.form { width: 100%; padding: 0; }
		.form form { width: 90%; margin: 5% 4%; }
		.form  .title { width: 96%; margin: 2%; text-align: center; line-height: 1.2em; }
		.form form input[type="submit"] { width: 100%; } 
		
		.info { display: none; }
	}
	
	@media screen and (max-width: 414px) {
		.form  .title { font-size: 1em;  }
	}
</style>
<s:if test="hasFieldErrors() && !fromMenu">
	<div class="erro-messages">
		<s:fielderror cssClass="error"/>
	</div>
</s:if>
<s:if test="createWithSuccess">
	<div class="success">
		<label>Dados enviados com sucesso. Para ativar seu cadastro, acesse seu e-mail, abra o e-mail de confirmação e clique no link de confirmação.</label>
	</div>
</s:if>
<s:else>
	<div class="boxs">
		<div class="content">
			<div class="form">
				<span class="title">faça seu cadastro grátis no clube tatame</span>
			
				<form action="/clube/memberRegister" method="post">
					<input type="text" name="name" placeholder="nome completo"/>
					<input type="text" name="document" placeholder="CPF (apenas n&uacute;meros sem traços e pontos)"/>
					<input type="text" name="email" placeholder="e-mail" />
					<input type="password" name="password" placeholder="senha"/>
					<input type="password" name="password2" placeholder="confirme sua senha"/>					
					<input type="submit" value="Avançar"/>
				</form>
			</div>
			<div class="info">
				<div class="premium">
					<img src="/skins/clube-tatame/img/black-belt.png">
					<p>MEMBRO PREMIUM</p>
					<p class="grey">Receba a revista TATAME em casa e ganhe descontos nos produtos da loja virtual, academias e eventos.</p>
				</div>
				
				<span class="important">Garanta já a sua condição de membro PREMIUM e comece a desfrutar em poucos minutos!</span>
				
				<div class="free">
					<img src="/skins/clube-tatame/img/white-belt.png">
					<p>MEMBRO FREE</p>
					<p class="grey">Plano básico. Receba e-mail com as ofertas da loja e com descontos na inscrição de eventos.</p>
				</div>
			</div>
		</div>
	</div>
</s:else>