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
		<meta http-equiv="Content-Type"
		content="text/html; charset=UTF-8"/>
		<link rel="stylesheet" 
			href="${createLinkTo(dir: 'css', file: 'main.css')}" />
		<meta name="layout" content="main"/>
		<title>Bank Limit</title>
	</head>
	<body>
	<h1>Bank Limit</h1>
	<h1>Edit Bank Limit</h1>
	<g:form name="bankLimit" action="updateMulti">
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
						<td>
							<g:hiddenField name="bankLimit.r${i}.id" value="${bankLimitInstance?.id}"/>
							${bankLimitInstance.currency.sym}
						</td>
						<td>
							<g:textField name="bankLimit.r${i}.dayLimit" value="${bankLimitInstance?.dayLimit}" onkeyup="cekInputAngka(this) && cekLength(this)"/>
						</td>
					</tr>
				</g:each>
			</tbody>
		</table>
		<fieldset>
			<g:actionSubmit action="updateMulti" value="Save" />
			<g:actionSubmit value="Back" action="show"/>
		</fieldset>
		</g:form>
	
	</body>
	
</html>




