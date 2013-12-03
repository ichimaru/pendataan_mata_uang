<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
		<meta http-equiv="Content-Type"
		content="text/html; charset=UTF-8"/>
		<link rel="stylesheet" 
			href="${createLinkTo(dir: 'css', file: 'main.css')}" />
		<meta name="layout" content="main"/>
		<title>Groups Show</title>
	</head>
	<body>
	<h1>Groups</h1>
	<h1>Show Group</h1>
	<br><br>
	<g:form>
			<div class ="fieldcontain">
				<label>Group name :</label> ${groupsInstance.name}
			</div>
			<div class ="fieldcontain">
				<label>Description :</label> ${groupsInstance.description}
			</div>
			<fieldset>
					<div class="fieldcontain">
					<g:hiddenField name="id" value="${groupsInstance?.id}" />
					<g:hiddenField name="guId" value="${guId}"/>
					<g:actionSubmit class="edit" action="editGroup" value="Update Group Info"/>
					<%--<g:actionSubmit class="edit" action="editMember" value="Update Member Info"/>--%>
					
					<g:actionSubmit class="delete" action="delete" value="Hapus" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					<g:actionSubmit value="Kembali" action="list"/>
					</div>
			</fieldset>
	</g:form>
	<g:form>		
			<table>
				<tr>
					<g:sortableColumn property="username" title="${message(code: 'groups.user.label', default: 'Username')}"/>
					<g:sortableColumn property="name" title="${message(code: 'groups.user.label', default: 'Nama')}"/>
				</tr>
					<g:each in="${guId}"status="i" var="g">
				<tr>
					<td>
						${g?.user} - ${c[i].firstName} ${c[i].lastName} 
					</td>
				</tr>
					</g:each>
			</table>
	</g:form>
	
	</body>
</html>