<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"              %>
<form action="" method="post">
	<s:hidden name="id"/>	
	<label for="name">Nome</label>
	<s:textfield name="name"/>	
	<label for="email">E-mail</label>
	<s:textfield name="email"/>	
	<label for="document">CPF</label>
	<s:textfield name="document"/>	
	<label for="gender">Sexo</label>
	<select name="gender">
		<option value="masculino">Masculino</option>
		<option value="feminino">Feminino</option>
	</select>	
	<label for="birth">Data de nascimento</label>
	<s:textfield name="birth"/>	
	<label for="address">Endereço</label>
	<s:textfield name="address"/>	
	<label for="cep">CEP</label>
	<s:textfield name="cep"/>	
	<s:submit value="Enviar"/>		
</form>