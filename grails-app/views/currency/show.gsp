<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="layout" content="main" />
		<title>Mata Uang</title>
	</head>
	
	<body>
		<h1>Tampilkan Mata Uang</h1>
		
		<form action="update">		 
				<div class ="fieldcontain">
				<label>Simbol:</label> ${currencyInstance.sym }
				</div>
				<div class ="fieldcontain">
				<label>Nama	:</label> ${currencyInstance.name }
				</div>
				<div class ="fieldcontain">
				<label>Dapat melakukan Transaksi :</label> ${currencyInstance.isTransactional }
				</div>
				<div class ="fieldcontain">
				<label>Tanggal Dibuat :</label> ${currencyInstance.dateCreated }
				</div>
				<div class ="fieldcontain">
				<label>Terakhir Diperbaharui :</label> ${currencyInstance.lastUpdated }
				</div>
		</form>
		
			<g:form>
				<fieldset>
					<div class="fieldcontain">
					<g:hiddenField name="id" value="${currencyInstance?.id}" />
					<g:actionSubmit class="edit" action="edit" value="Ubah"/>
					<g:actionSubmit class="delete" action="delete" value="Hapus" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					<g:actionSubmit value="Kembali" action="list"/>
					</div>
				</fieldset>
			</g:form>
	
	</body>