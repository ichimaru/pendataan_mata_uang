<%@page import="org.bouncycastle.bcpg.UserIDPacket"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
		<g:javascript src="jquery.js"/>
	 	<script type="text/javascript">
    		$(function(){
      		$("#users").blur(function(){
        	if($(this).length > 0) {
          		var url = "${createLink(controller:'userDetails', action:'checkAvailable')}"          
          		$.getJSON(url, {id:$(this).val()}, function(json){
            		if(!json.available) {
              			$("#users").css("border", "1px solid red");
              			alert("User Id telah digunakan, coba lagi");
              			$("#somehiddendiv").html("user id tidak valid").show();
            		}
          		});
        	}
      		});
   			});
    	</script>
    	<script type="text/javascript">
    	
    	</script>
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
		<title>Create User</title>
	</head>
	<body>
		<g:if test="${flash.toUser}">
			<div class="validationerror">
				${flash.toUser}
			</div>
		</g:if>
		<g:hasErrors bean="${userDetailsInstance}">
			<div class="validationerror">
				<g:renderErrors bean="${userDetailsInstance}" as="list"/>
			</div>
		</g:hasErrors>
		<g:form name="form1" action="save" class="inputform" methode="post">
			<fieldset>
				<div class="fieldcontain ${hasErrors(bean: userDetailsInstance, field: 'firstName', 'error')} " >
					<label for ="firstName"><g:message code="userDetails.firstName.label" default="First Name"/></label>
					<g:textField name="firstName" value="${firstName}"/>
				</div>
				
				<div class="fieldcontain ${hasErrors(bean: userDetailsInstance, field: 'lastName', 'error')} " >
					<label for ="lastName"><g:message code="userDetails.lastName.label" default="Last Name"/></label>
					<g:textField name="lastName" value="${lastName}"/>
				</div>
				
				<div class="fieldcontain ${hasErrors(bean: userDetailsInstance, field: 'user', 'error')} " >
					<label for="user"><g:message code="userDetails.user.label" default="User ID"/></label>
					<g:textField name="users" value="${users}"/>
				</div>
				
				<div class="fieldcontain ${hasErrors(bean: userDetailsInstance, field: 'userAlias', 'error')} " >
					<label for="userAlias"><g:message code="userDetails.userAlias.label" default="User Alias"/></label>
					<g:textField name="userAlias" value="${userAlias}"/>
				</div>
				
				<div class="fieldcontain ${hasErrors(bean: userDetailsInstance, field: 'email', 'error')} " >
					<label for="email"><g:message code="userDetails.email.label" default="Email"/></label>
					<g:textField name="email" value="${email}"/>
				</div>
				
				<div class="fieldcontain ${hasErrors(bean: userDetailsInstance, field: 'mobilePhoneNo', 'error')} " >
					<label for="mobilePhoneNo"><g:message code="userDetails.mobilePhoneNo.label" default="Mobile Phone No"/></label>
					<g:textField name="mobilePhoneNo" value="${mobilePhoneNo}" onkeyup="cekInputAngka(this)"/>
				</div>
			</fieldset>
			
			<fieldset>
				<g:submitButton name="save" value="Buat"/>
				<g:actionSubmit action="list" value="Kembali"/>
			</fieldset>
		</g:form>
	</body>
</html>