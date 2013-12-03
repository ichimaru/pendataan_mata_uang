<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		
		<style type="text/css">
			#hiddenBlock {
    		display: none;
			}
			#cancel {
			display:none;
			}
			#submitUser{
			display:none;
			}
		</style>
		
		
		<meta http-equiv="Content-Type"
		content="text/html; charset=UTF-8"/>
		<link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'main.css')}" />
		<meta name="layout" content="main"/>
		<title>Edit Group Member</title>
	</head>
	<body>
	<h1>Group Member</h1>
	<h1>Edit</h1>
	<g:form name="groupMember">
		<table>
			<thead>
				<tr>
				<g:sortableColumn property="username" title="${message(code: 'groups.user.label', default: 'Username')}"/>
				<th>Action Status</th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${guId}" status="i" var="g">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td>
							<g:hiddenField name="groupUser.r${i}.id" value="${g?.id}"/>
							${g?.user}
						</td>
						<td>
							<p> delete this user? </p><g:checkBox name="deleteUser.s${i}" value="Y" checked="false" />
						</td>
					</tr>
				</g:each>
			</tbody>
			<tfoot>
		</table>
		<fieldset>
			<g:actionSubmit action="updateMulti" value="Save all Update"/>
			<g:actionSubmit value="Back" action="list"/>
		</fieldset>
		</g:form>
		
		<g:form action="submitUser">
			<fieldset>		
			<div class="fieldcontain">
				<input type="button" name="addUser" value="ADD" onclick="$('#hiddenBlock').show() && $('#cancel').show() && $('#submitUser').show()" />
				<g:hiddenField name="groupsInstance" value="${groupsInstance}"/>
				<g:actionSubmit value="Submit User" action="submitUser" id="submitUser" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
				<input type="button" value="Cancel" id="cancel" onclick="$('#hiddenBlock').hide() && $('#cancel').hide() && $('#submitUser').hide()"/>
				<label></label><g:select id="hiddenBlock" name="user" from="${userDetailsInstance}" optionKey="userId" value="" optionValue="${{it.user.username +'-'+it.firstName+' '+it.lastName}}" noSelection="${['0':'--Pilih User--']}" />
			</div>						
			</fieldset>			
		</g:form>
					
		
		
		
	</body>
	
</html>




