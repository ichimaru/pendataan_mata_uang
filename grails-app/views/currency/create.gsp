<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<g:javascript src="jquery.js"/>
  <script type="text/javascript">
    // load domain
    $(function(){
      // set onblur event handler
      $("#sym").blur(function(){
        // jika ada simbol yg diinput ini == #sym akan dicek
        if($(this).length > 0) {
          // pakai create link agar tidak menghardcode
          var url = "${createLink(controller:'currency', action:'checkAvailable')}"
          // async ajax request pass username entered as id parameter
          $.getJSON(url, {id:$(this).val()}, function(json){
            if(!json.available) {
              // highlight field so user knows there's a problem
              $("#sym").css("border", "1px solid red");
              // maybe throw up an alert box
              alert("Mata Uang Dengan Simbol Tersebut Sudah Ada");
              // populate a hidden div on page and fill and display it..
              $("#somehiddendiv").html("Duplikasi Simbol").show();
            }
          });
        }
      });
    });
    </script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>Masukkan Data Uang Baru</title>
</head>
	<body>
		<g:if test="${flash.toUser}">
			<div class="validationerror">
				${flash.toUser}
			</div>
		</g:if>		
		<g:hasErrors bean="${currencyInstance}">
			<div class="validationerror">
				<g:renderErrors bean="${currencyInstance}" as="list"/>
			</div>
		</g:hasErrors>
		
			<g:form action="save" class="inputform">
					<fieldset>
						<div class="fieldcontain ${hasErrors(bean: currencyInstance, field: 'sym', 'error')} " >
							<label for="sym"><g:message code="currency.sym.label" default="Simbol" /></label>
							<g:textField name="sym" value="${simbol}" />
						</div>
						<div class="fieldcontain ${hasErrors(bean: currencyInstance, field: 'name', 'error')} " >
							<label for="name"><g:message code="currency.name.label" default="Nama" /></label>
							<g:textField name="name" value="${nama}" />
						</div>
						<div class="fieldcontain ${hasErrors(bean:currencyInstance, field:'isTransactional','error') }" >
							<!-- script di bawah merupakan script untuk membuat checkbox, nama checkbox adalah isTransactional bernilai "Y" apabila di cek -->
							<label></label><g:checkBox name="isTransactional" value="Y"/><nobr> Dapat Melakukan Transaksi</nobr>
						</div>	
					</fieldset>
			
					<fieldset>
						<g:actionSubmit action="save" name="save" value="Buat"/>
						<g:actionSubmit action="list" value="Kembali"/>
					</fieldset>
			</g:form>
	</body>
</html>