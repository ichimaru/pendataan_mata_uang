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
    	<script type="text/javascript">
    	function validateForm(){
        	//usernya harus di sizenya dulu, lalu di cari jumlahnya untuk melakukan looping untuk mendapatkan value masing2 user yg di submit
			var userArray = new Array();
			var elements = new Array();
			var x=document.forms["form1"]["user"].length;
			//alert("jumlah user nih : "+x);
			for(i=0;i<x;i++){
				var users=document.forms["form1"]["user"][i].value;
				alert("nilai Id user : "+users);
				userArray[i] = users;
				var key=userArray[i].toString();
				alert("nilai user Array"+userArray)
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
				<title>Update Group Info</title>
	</head>
	<body>
		<g:form name="form1"  action="updateMulti" onsubmit="return validateForm()" method="post">
			<g:hiddenField id="id" name="id" value="${groupsInstance?.id}" />
			<g:hiddenField id="version" name="version" value="${groupsInstance?.version}" />
			<fieldset class="form">
					<div class="fieldcontain ${hasErrors(bean: groupsInstance, field: 'name', 'error')} ">
						<label for="name">
							<g:message code="groups.name.label" default="Name" />
						</label>
							<g:textField name="name" value="${groupsInstance?.name}"/>
					</div>
					<div class="fieldcontain ${hasErrors(bean: groupsInstance, field: 'description', 'error')} ">
						<label for="name">
							<g:message code="groups.description.label" default="Description" />
						</label>
							<g:textField name="description" value="${groupsInstance?.description}" />
					</div>
			</fieldset>

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
									<p> delete this user? </p><g:checkBox name="deleteUser.s${i}" value="Y" checked="false" />${" "}confirmation
								</td>
							</tr>
						</g:each>
					</tbody>
				</table>
		
		<div class="fieldcontain">
			<fieldset>	
			<input type="button" name="addUser" id="add" value="Add Member" onclick="$('#hiddenBlock').show() && $('#cancel').show() && $('#add').hide()" />
			<input type="button" value="Cancel Add Member" id="cancel" onclick="$('#hiddenBlock').hide() && $('#cancel').hide() && $('#add').show()"/>
			</fieldset>	
		</div>	
		
		<div id="hiddenBlock">	
			<fieldset>
			<input type="button" value="More member" onclick="addRow('dataTable')" />
   			<input type="button" value="cancel member" onclick="deleteRow('dataTable')" />
   			</fieldset>
   			
    		<table id="dataTable">
         		<tr>
            		<td><input type="checkbox" name="chk"/>${" "}Cancel add Member?</td>
            		<td><g:select id="combobox" name="user" from="${userDetailsInstance}" optionKey="userId" value="" optionValue="${{it.user.username +'-'+it.firstName+' '+it.lastName}}" noSelection="${['':'--Pilih User--']}" /></td>
        		</tr>
    		</table>
		</div>
		
		<fieldset>
				<g:submitButton name="updateMulti" value="Save all Update" onclick="return checkName()"/>
				<g:actionSubmit value="Back" action="list"/>
			</fieldset>
			</g:form>
	</body>
	
</html>

<%-- <g:form action="submitUser">
			<fieldset>		
			<div class="fieldcontain">
				<input type="button" name="addUser" id="add" value="ADD" onclick="$('#hiddenBlock').show() && $('#cancel').show() && $('#submitUser').show() && $('#add').hide()" />
				<g:hiddenField name="groupsInstance" value="${groupsI}"/>
				<g:actionSubmit value="Submit User" action="submitUser" id="submitUser" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
				<input type="button" value="Cancel" id="cancel" onclick="$('#hiddenBlock').hide() && $('#cancel').hide() && $('#submitUser').hide() && $('#add').show()"/>
				<g:select id="hiddenBlock" name="user" from="${userDetailsInstance}" optionKey="userId" value="" optionValue="${{it.user.username +'-'+it.firstName+' '+it.lastName}}" noSelection="${['0':'--Pilih User--']}" />
			</div>						
			</fieldset>			
		</g:form> --%>


