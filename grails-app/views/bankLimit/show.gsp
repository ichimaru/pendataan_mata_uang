<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
		<meta http-equiv="Content-Type"
		content="text/html; charset=UTF-8"/>
		<link rel="stylesheet" 
			href="${createLinkTo(dir: 'css', file: 'main.css')}" />
		<meta name="layout" content="main"/>
		<title>Bank Limit</title>
	</head>
	<body>
	<h1>Bank Limit</h1>
	<h1>Show Bank Limit</h1>
	<br><br>
	<g:form>
		<table>
			<thead>
				<tr>
					<th><g:message code="bankLimit.currency.label" default="Currency" /></th>
					<g:sortableColumn property="dayLimit" title ="${message(code: 'bankLimit.dayLimit.label', default: 'Limit/Day')}"/>
				</tr>
			</thead>
			<tbody>
				<g:each in="${bankLimitInstanceList}" status="i" var="bankLimitInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td>${fieldValue(bean: bankLimitInstance, field: "currency.sym")}</td>
						<td>${fieldValue(bean: bankLimitInstance, field: "dayLimit")},00</td>
					</tr>
				</g:each>
			</tbody>
		</table>
		<div class="fieldcontain">
		<fieldset>
				<g:actionSubmit action="edit" value="Edit" />
		</fieldset>
		</div>
		<div class="pagination">
				<g:paginate total="${bankLimitInstanceTotal}" />
			</div>
			</g:form>
	</body>
</html>