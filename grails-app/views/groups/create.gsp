<%@page import="org.dom4j.bean.BeanAttribute"%>
<%@page import="org.apache.commons.collections.map.ListOrderedMap.ValuesView"%>
<%@page import="user_module.UserDetails"%>
<%@page import="user_module.User"%>
<html>
	<head>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
		
		<style type="text/css">
			#hiddenBlock {
    		display: none;
			}
			#cancel {
			display:none;
			}
		</style>
		
		<script language="javascript">
        function addRow(tableID) {
            var table = document.getElementById(tableID);
            var rowCount = table.rows.length;
            var row = table.insertRow(rowCount);
            var colCount = table.rows[0].cells.length;
            for(var i=0; i<colCount; i++) {
 
                var newcell = row.insertCell(i);
 
                newcell.innerHTML = table.rows[0].cells[i].innerHTML;
                //alert(newcell.childNodes);
                switch(newcell.childNodes[0].type) {
                    case "text":
                            newcell.childNodes[0].value = "";
                            break;
                    case "checkbox":
                            newcell.childNodes[0].checked = false;
                            break;
                    case "select-one":
                            newcell.childNodes[0].selectedIndex = 0;
                            break;
                }
            }
        }
 
        function deleteRow(tableID) {
            try {
            var table = document.getElementById(tableID);
            var rowCount = table.rows.length;
            for(var i=0; i<rowCount; i++) {
                var row = table.rows[i];
                var chkbox = row.cells[0].childNodes[0];
                if(null != chkbox && true == chkbox.checked) {
                    if(rowCount <= 1) {
                        alert("Tidak bisa hapus semua, jika ingin membatalkan, pilihlah \"Cancel Add Member\" ");
                        break;
                    }
                    table.deleteRow(i);
                    rowCount--;
                    i--;
                }
            }
            }catch(e) {
                alert(e);
            }
        }
    	</script>
    	<g:javascript src="jquery.js"/>
  		<script type="text/javascript">
    		$(function(){
      			$("#name").blur(function(){
        			if($(this).length > 0) {
		         	 var url = "${createLink(controller:'groups', action:'checkAvailable1')}"
          			$.getJSON(url, {id:$(this).val()}, function(json){
            			if(!json.available) {
              				$("#name").css("border", "1px solid red");
              					alert("Group dengan nama tersebut telah ada, coba lagi");
              					$("#somehiddendiv").html("Duplikasi Nama Group").show();
            			}
          			});
        			}
      			});
    		});
    	</script>
    	<script type="text/javascript">
    	function validateForm(){
        	//usernya harus di sizenya dulu, lalu di cari jumlahnya untuk melakukan looping untuk mendapatkan value masing2 user yg di submit
			var userArray = new Array();
			var elements = new Array();
			var x=document.forms["form1"]["user"].length;
			//alert("jumlah user nih : "+x);
			for(i=0;i<x;i++){
				var users=document.forms["form1"]["user"][i].value;
				//alert("nilai Id user : "+users);
				userArray[i] = users;
				var key=userArray[i].toString();
				if(!elements[key]){
					elements[key]=1;
					if(elements[key]==null||elements[key]==''){
						return true;
						}
					}
				else{
					alert("There are duplicacy in member selection, please try again");
					return false;
					}
			}
			//alert("Member verification success, press \"Ok\" button to continue");
			return true;
        	}

    	function checkName(){
			var i =document.forms["form1"]["name"].value;
			if(i==null||i==''){
					alert("Groups must have a name, type name for the new Group first");
					return false;
				}
        	}
    	</script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="layout" content="main" />
		<title>Create Group</title>
	</head>
	
	<body>
	<g:if test="${flash.toUser}">
			<div class="validationerror">
				${flash.toUser}
			</div>
		</g:if>
			<g:form name="form1" action="save" class="inputform" action="save" onsubmit="return validateForm()" methode="post">
					<fieldset>
						<h1>Group Add</h1><br>
						<div class="fieldcontain">
							<label>Name :</label><g:textField name="name" value="${name}" />
						</div>
						<div class="fieldcontain">
							<label>Description :</label><g:textArea name="description" id="description" value="" cols="40" rows="10"/>
						</div>
					</fieldset>
					
		<div class="fieldcontain">
			<fieldset>	
			<input type="button" name="addUser" id="add" value="Add Member" onclick="$('#hiddenBlock').show() && $('#cancel').show() && $('#add').hide()" />
			<input type="button" value="Cancel Add Member" id="cancel" onclick="$('#hiddenBlock').hide() && $('#cancel').hide() && $('#add').show() && deleteRow('dataTable')"/>
			</fieldset>	
		</div>	
		
		<div id="hiddenBlock">	
			<input type="button" value="More member" onclick="addRow('dataTable')" />
   			<input type="button" value="cancel member" onclick="deleteRow('dataTable')" />
    		
    		<table id="dataTable">
         		<tr>
            		<td><input type="checkbox" name="chk"/>${" "}Cancel add Member?</td>
            		<td><g:select id="combobox" name="user" from="${UserDetails.list()}" optionKey="userId" value="user" optionValue="${{it.user.username +'-'+it.firstName+' '+it.lastName}}" noSelection="${['':'--Pilih User--']}" /></td>
        		</tr>
    		</table>
		</div>
		<fieldset>
			<g:submitButton name="save" value="Create" onclick="return checkName()"/>
			<g:actionSubmit action="list" value="Back"/>
		</fieldset>	
		</g:form>			
	</body>
</html>