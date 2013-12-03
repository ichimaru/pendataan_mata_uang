<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
			<meta name="layout" content="main" />
				<title>Masukkan Data Uang Baru</title>
	</head>

	<body>
		<g:hasErrors bean="${currencyInstance}">
			<div class="validationerror">
		<g:renderErrors bean="${currencyInstance}" as="list"/>
			</div>
		</g:hasErrors>
	
		<g:form method="post" >
				<g:hiddenField name="id" value="${currencyInstance?.id}" />
				<g:hiddenField name="version" value="${currencyInstance?.version}" />
				<fieldset class="form">
					<div class="fieldcontain ${hasErrors(bean: currencyInstance, field: 'sym', 'error')} ">
						<label for="sym">
							<g:message code="currency.sym.label" default="Simbol" />
						</label>
							<g:textField name="sym" value="${currencyInstance?.sym}" readonly="readonly"/>
					</div>
					
					<div class="fieldcontain ${hasErrors(bean: currencyInstance, field: 'name', 'error')} ">
						<label for="name">
							<g:message code="currency.name.label" default="Nama" />
						</label>
							<g:textField name="name" value="${currencyInstance?.name}" />
					</div>
					<div class="fieldcontain ${hasErrors(bean:currencyInstance, field:'isTransactional','error') }">
						<label></label><g:checkBox name="isTransactional" value="Ya"/> <nobr> Dapat Melakukan Transaksi</nobr>
						
					</div>
				</fieldset>

				<fieldset class="buttons">
					<g:actionSubmit class="save" action="update" value="Ubah" />
					<g:actionSubmit class="delete" action="delete" value="Hapus" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Apakah anda yakin data ingin dihapus?')}');" />
					<g:actionSubmit value="Kembali" action="show"/>
				</fieldset>
			</g:form>
	
	</body>