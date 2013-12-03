<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
		<script type="text/javascript">
		function cekInputAngka(ob) {
			var invalidChars = /[^0-9]/gi
		  	if(invalidChars.test(ob.value)) {
		            ob.value = ob.value.replace(invalidChars,"");
		    }
		}
		</script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="layout" content="main" />
		<title>Edit User</title>
	</head>
	<body>
	
		<g:hasErrors bean="${userDetailsInstance}">
			<div class="validationerror">
				<g:renderErrors bean="${userDetailsInstance}" as="list"/>
			</div>
		</g:hasErrors>
		
		<h1>User</h1>
		<header>Edit User</header>
		<g:form method="post" >
				<g:hiddenField name="id" value="${userDetailsInstance?.id}" />
				<g:hiddenField name="version" value="${userDetailsInstance?.version}" />
				<fieldset class="form">
					<div class="fieldcontain ${hasErrors(bean: userDetailsInstance, field: 'firstName', 'error')} ">
						<label for="firstName"><g:message code="userDetails.firstName.label" default="First Name" /></label>
							<g:textField name="firstName" value="${userDetailsInstance?.firstName}"/>
					</div>
					
					<div class="fieldcontain ${hasErrors(bean: userDetailsInstance, field: 'lastName', 'error')} ">
						<label for="lastName"><g:message code="userDetails.lastName.label" default="Last Name" /></label>
							<g:textField name="lastName" value="${userDetailsInstance?.lastName}" />
					</div>
					
					<div class="fieldcontain">
						<label for="userId"><g:message code="userDetails.user.label" default="User Id" /></label>
							${fieldValue(bean: userDetailsInstance, field: "user.username")}
					</div>
					
					<div class="fieldcontain ${hasErrors(bean: userDetailsInstance, field: 'userAlias', 'error')} ">
						<label for="userAlias"><g:message code="userDetails.userAlias.label" default="User Alias" /></label>
							<g:textField name="userAlias" value="${userDetailsInstance?.userAlias}" />
					</div>
					
					<div class="fieldcontain ${hasErrors(bean: userDetailsInstance, field: 'email', 'error')} ">
						<label for="email"><g:message code="userDetails.email.label" default="E-mail" /></label>
							<g:textField name="email" value="${userDetailsInstance?.email}" />
					</div>
					
					<div class="fieldcontain ${hasErrors(bean: userDetailsInstance, field: 'mobilePhoneNo', 'error')} ">
						<label for="mobilePhoneNo"><g:message code="userDetails.mobilePhoneNo.label" default="Phone No." /></label>
							<g:textField name="mobilePhoneNo" value="${userDetailsInstance?.mobilePhoneNo}" onkeyup="cekInputAngka(this)"/>
					</div>
				</fieldset>

				<fieldset>
					<g:actionSubmit action="update" value="Ubah" />
					<g:actionSubmit action="delete" value="Hapus" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Apakah anda yakin data ingin dihapus?')}');" />
					<g:actionSubmit value="Kembali" action="show"/>
				</fieldset>
				
			</g:form>
	</body>
</html>