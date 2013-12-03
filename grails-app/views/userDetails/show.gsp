<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="layout" content="main" />
		<title>User Show</title>
	</head>
	
	<body>
		<h1>User</h1>
		<header>Show User</header>
		
		<form action="update">		 
				<div class ="fieldcontain">
				<label>Name :</label>${userDetailsInstance.firstName.concat(" ").concat("${userDetailsInstance.lastName}")}
				</div>
				<div class ="fieldcontain">
				<label>User ID :</label>${fieldValue(bean: userDetailsInstance, field: "user.username")}
				</div>
				<div class ="fieldcontain">
				<label>User Alias:</label> ${userDetailsInstance.userAlias}
				</div>
				<div class ="fieldcontain">
				<label>E-mail:</label> ${userDetailsInstance.email}
				</div>
				<div class ="fieldcontain">
				<label>Phone No :</label> ${userDetailsInstance.mobilePhoneNo}
				</div>
		</form>
		
			<g:form>
				<fieldset>
					<div class="fieldcontain">
					<g:hiddenField name="id" value="${userDetailsInstance?.id}" />
					<g:actionSubmit class="edit" action="edit" value="Ubah"/>
					<g:actionSubmit class="delete" action="delete" value="Hapus" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					<g:actionSubmit value="Kembali" action="list"/>
					</div>
				</fieldset>
			</g:form>
	
	</body>