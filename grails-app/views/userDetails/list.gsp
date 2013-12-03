<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
		<meta http-equiv="Content-Type"
		content="text/html; charset=UTF-8"/>
		<link rel="stylesheet" 
			href="${createLinkTo(dir: 'css', file: 'main.css')}" />
		<meta name="layout" content="main"/>
		<title>USER LIST</title>
	</head>
	<body>
	<h1>User List</h1>
    	<g:form action="list">
    		<div class ="fieldcontain">
        		<label for="userId">User ID:</label><input type="search" name="userId" id="userId" value="${userId}"/>
        	</div>
        	<div class ="fieldcontain">
        		<label for="name">Name :</label> <input type="search" name="name" id="name" value="${name}"/>
        	</div>
        	<div class="fieldcontain">
        		<label></label><g:actionSubmit value="SEARCH" action="list" />
        		<nobr><g:actionSubmit value="ADD" action="create"/></nobr>
        	</div>
        </g:form>
        <br><br>
			<table>
					<thead>
					<tr>
						<g:if test="${!userId && !nama}">
							<g:sortableColumn property="user" title ="${message(code: 'userDetails.user.label', default: 'User ID')}"/>
							<g:sortableColumn property="name" title ="${message(code: 'userDetails.name.label', default: 'Name')}"/>
						</g:if>
						<g:else>
							<g:sortableColumn property="user" title ="${message(code: 'userDetails.user.label', default: 'User ID')}" params="[userId:userId,name:name]"/>
							<g:sortableColumn property="name" title ="${message(code: 'userDetails.user.label', default: 'User ID')}" params="[userId:userId,name:name]"/>
						</g:else>
					</tr>
					</thead>
					<tbody>
						<g:each in="${userDetailsInstanceList}"status="i" var="userDetailsInstance">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							<tr class="userDetailsInstanceList">
								<td>
									<g:link action="show" id="${userDetailsInstance.id}">${fieldValue(bean: userDetailsInstance, field: "user.username")}</g:link>
								</td>
								<td>
									${userDetailsInstance.firstName.concat(" ").concat("${userDetailsInstance.lastName}")}
								</td>
							</tr>
						</g:each>
					</tbody>
			</table>
				<div class="pagination">
					<g:if test="${!userId && !name}">
 						<g:paginate controller="userDetails" action="list" total="${userDetailsInstanceTotal}"/>
 					</g:if>
 					<g:else>
 						<g:paginate total="${userDetailsInstanceTotal}" params="[sym:simbol,name:nama]"/>
 					</g:else>
				</div>
	</body>
</html>