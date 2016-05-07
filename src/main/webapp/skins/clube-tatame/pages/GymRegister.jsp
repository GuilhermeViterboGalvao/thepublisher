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
	
	.content { float: left; width: 100%; height: auto; background-color: #f6f5f5; margin: 0; }
	
	.title-page { float: left; text-transform: uppercase; width: 100%; text-align: center; font-size: 2em; margin: 2% 0; }
	
	.sub-title-page { width: 38%; margin: 0 auto; }
	.sub-title-page span { float: left; width: 100%; font-size: 1.1em; margin: 4% 0; line-height: 1.3em; text-align: center; }
	
	.form { float:left; width: 55%; padding: 2% 5%; }
	.form  .title { float: left; font-size: 1.2em; width: 100%;  }
	.form  .sub-title { float: left; font-size: 1.2em; width: 100%; margin: 5% 0; }
	.form form { float: left; width: 57%; margin: 0; }
	.form form input[type="text"],
	.form form input[type="email"],
	.form form input[type="password"] { float: left; width: 96%; margin: 0 0 6% 0; border: 1px solid #999999; font-size: 0.7em; padding: 1% 2%; line-height: 1.1em; border-radius: 14px; }
	.form form input[type="submit"] { float: left; padding: 1.5% 2%; background-color: #ffefa3; font-size: 0.7em; margin: 8% 0; text-align: center; font-weight: 600; color: #000; border: 0.1em solid #414142; border-radius: 5px; text-transform: uppercase; }
	.form form input.city { width: 60%; margin-right: 2%; }
	.form form input.state{ width: 20%; float: right; }
	.form form .sub-title { margin: 8% 0; }
	
	
	.info { float:left; width: 25%; padding: 0 4% 2%; border-left: 1px solid #000; margin-top: 2%; font-weight:  }
	.info .premium { float: left; text-align: center; margin-top: 3em; }
	.info .premium img { width: 100px; }
	.info .premium p { font-size: 0.8em; line-height: 1.4em; }
	
	.info .free { float: left; text-align: center; }
	.info .free img { width: 100px; }
	.info .free p { font-size: 0.8em; line-height: 1.4em; }
	
	.grey { color: #787878; }
	
	.sub-info { width: 100%; max-width: 255px; float: left; margin: 4% 3%; text-align: center; }
	.sub-info img { margin-bottom: 5%; }
	.sub-info span { font-size: 0.9em; width: 100%; float: left; line-height: 1.5em; }
	
	.register-header { float: left; width: 100%; margin: 2% 0; text-align: center; }
	
	@media screen and (max-width: 800px) {
		.erro-messages { width: 98%; margin: 5% 1% 0 1%; }
		.erro-messages .error li span { font-size: 1em; line-height: 1.2em; }
		.success { width: 98%; margin: 5% 1% 0 1%; }
		.success label { font-size: 1em; line-height: 1.2em; }	
		
		.boxs { max-width: none; }
		
		.form { width: 100%; padding: 0; }
		.form form { width: 90%; margin: 5% 4%; }
		.form  .title { width: 96%; margin: 2%; text-align: center; line-height: 1.2em; }
		.form  .sub-title { width: 96%; margin: 2%; text-align: center; line-height: 1.2em; }
		.form form input[type="submit"] { width: 100%; } 
		
		.info { display: none; }
		
		.sub-info { display: none; }
		
		.title-page { font-size: 1.5em;  }
		
		.sub-title-page { width: 100%; margin: 0;  }
		.sub-title-page span { font-size: 1em; margin: 2% 0; }
		
		.register-header img { width: 100%; }
	}
	
	@media screen and (max-width: 414px) {
		.form  .title { font-size: 1em;  }
		.form  .sub-title { font-size: 1em;  }
		
		.title-page { font-size: 1em;  }
		
		.sub-title-page span{ font-size: 0.8em;  }
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
	<div class="register-header">
		<img src="/skins/clube-tatame/img/header-register-gym.jpg">
	</div>

	<div class="boxs">
		<div class="content">
			<div class="form">
				<span class="title">CADASTRE SUA ACADEMIA NO CLUBE TATAME</span>
				<span class="sub-title grey">Dados da empresa</span>
			
				<form action="/clube/gymRegister" method="post">
					<input type="text" name="name" placeholder="nome fantasia"/>
					<input type="text" name="document" placeholder="CNPJ (apenas n&uacute;meros sem traços e pontos)"/>
					<input type="text" name="address" placeholder="endereço comercial"/>
					<input type="text" name="cep" placeholder="CEP"/>
					<input type="text" name="city" class="city" placeholder="cidade"/>
					<input type="text" name="state" class="state" placeholder="UF"/>
					<input type="text" name="site" placeholder="site da empresa"/>
					<input type="text" name="email" placeholder="e-mail" />
					<input type="password" name="password" placeholder="senha"/>
					<input type="password" name="password2" placeholder="confirme sua senha"/>
					
					<span class="sub-title grey">Dados do responsável</span>
					
					<input type="text" name="contact" placeholder="nome"/>
					<input type="text" name="phone" placeholder="telefone"/>
									
					<input type="submit" value="Avançar"/>
				</form>
			</div>
			<div class="info">
				<div class="free">
					<img src="/skins/clube-tatame/img/white-belt.png">
					<p>ACADEMIA FREE</p>
					<p class="grey">Plano básico. Apareça na busca feito pelo cliente e seja encontrado. Cadastre-se ao lado gratuitamente.</p>
				</div>
				
				<div class="premium">
					<img src="/skins/clube-tatame/img/black-belt.png">
					<p>ACADEMIA PREMIUM</p>
					<p class="grey">Tenha destaque na pesquisa e ganhe uma página com fotos, detalhes, links e redes sociais.</p>
				</div>
			</div>
		</div>
	</div>
	
	<span class="title-page">torne-se uma academia premium</span>
	
	<div class="sub-title-page">
		<span>Entre em contato com nossos representantes: clube@tatame.com.br</span>
	</div>
	
	<div class="boxs">
		<div class="sub-info">
			<img src="/skins/clube-tatame/img/icon-people.png">
			<span>DESTAQUE</span>
			<span class="grey">Chegando ao seu vigésimo ano no mercado, a Revista TATAME, líder disparado no segmento de luta no Brasil, é mundialmente.</span>
		</div>
		<div class="sub-info">
			<img src="/skins/clube-tatame/img/icon-kimono.png">
			<span>EVENTOS</span>
			<span class="grey">TATAME, líder disparado no segmento de luta no Brasil, é mundialmente respeitada e considerada referência nada disso.</span>
		</div>
		<div class="sub-info">
			<img src="/skins/clube-tatame/img/icon-weight-black.png">
			<span>ACADEMIAS</span>
			<span class="grey">América Latina seu pioneirismo e imparcialidade fazem dela um produto sem igual é mundialmente respeitonsino mercado.​</span>
		</div>
	</div>
</s:else>