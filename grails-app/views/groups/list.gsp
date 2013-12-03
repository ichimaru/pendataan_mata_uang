<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
		<meta http-equiv="Content-Type"
		content="text/html; charset=UTF-8"/>
		<link rel="stylesheet" 
			href="${createLinkTo(dir: 'css', file: 'main.css')}" />
		<meta name="layout" content="main"/>
		<title>GROUP LIST</title>
	</head>
	<body>
	<h1>Group list</h1>
    	<g:form action="list">
    		<div class ="fieldcontain">
        		<label for="name">Name :</label><input type="search" name="name" id="name" value="${name}"/>
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
						<g:if test="${!name}">
							<g:sortableColumn property="name" title ="${message(code: 'groups.name.label', default: 'Name')}"/>
							<g:sortableColumn property="description" title ="${message(code: 'groups.description.label', default: 'Description')}"/>
						</g:if>
						<g:else>
							<g:sortableColumn property="name" title ="${message(code: 'groups.name.label', default: 'Name')}" params="[name:name]"/>
							<g:sortableColumn property="description" title ="${message(code: 'groups.description.label', default: 'Description')}" params="[name:name]"/>
						</g:else>
					</tr>
					</thead>
					<tbody>
						<g:each in="${groupsInstanceList}"status="i" var="g">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							<tr class="groupsUserInstanceList">
								<td>
									<g:link action="show" id="${g.id}">${g.name}</g:link>
								</td>
								<td>
									${g.description}
								</td>
							</tr>
						</g:each>
					</tbody>
			</table>
				<div class="pagination">
					<g:if test="${!name}">
 						<g:paginate controller="groups" action="list" total="${groupsInstanceTotal}"/>
 					</g:if>
 					<g:else>
 						<g:paginate total="${groupsInstanceTotal}" params="[name:name]"/>
 					</g:else>
				</div>
	</body>
</html>