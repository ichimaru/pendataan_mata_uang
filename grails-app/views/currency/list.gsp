<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
		<meta http-equiv="Content-Type"
		content="text/html; charset=UTF-8"/>
		<link rel="stylesheet" 
			href="${createLinkTo(dir: 'css', file: 'main.css')}" />
		<meta name="layout" content="main"/>
		<title>Daftar Mata Uang</title>
	</head>
	<body>
	
    	<g:form action="list">
    		<div class ="fieldcontain">
        		<label for="sym">Simbol :</label><input type="search" name="sym" id="sym" value="${simbol}"/>
        	</div>
        	<div class ="fieldcontain">
        		<label for="name">Nama :</label> <input type="search" name="name" id="name" value="${nama}"/>
        	</div>
        	<div class="fieldcontain">
        		<label></label><g:actionSubmit value="Cari" action="list" />
        		<nobr><g:actionSubmit value="Tambah" action="create"/></nobr>
        	</div>
        </g:form>
        <br><br>
			<table>
					<thead>
					<tr>
						<g:if test="${!simbol && !nama}">
							<g:sortableColumn property="sym" title="${message(code: 'currency.sym.label', default: 'Simbol')}"/>
							<g:sortableColumn property="name" title ="${message(code: 'currency.name.label', default: 'Nama Mata Uang')}"/>
							<g:sortableColumn property="isTransactional" title="${message(code: 'currency.isTransactional.label', default: 'Dapat Melakukan Transaksi')}"/>
						</g:if>
						<g:else>
							<g:sortableColumn property="sym" title="${message(code: 'currency.sym.label', default: 'Simbol')}" params="[sym:simbol,name:nama]" />
  							<g:sortableColumn property="name" title="${message(code: 'currency.name.label', default: 'Nama Mata Uang')}" params="[sym:simbol,name:nama]"/>
  							<g:sortableColumn property="isTransactional" title="${message(code: 'currency.isTransactional.label', default: 'Dapat Melakukan Transaksi')}" params="[sym:simbol,name:nama]"/>
						</g:else>
					</tr>
					</thead>
					<tbody>
						<g:each in="${currencyInstanceList}"status="i" var="c">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							<tr class="currencyInstanceList">
							<g:if test="${c.deleteFlag=='N'}">
								<td>
									<g:link action="show" id="${c.id}"> ${c.sym} </g:link>
								</td>
								<td>
									${c?.name}
								</td>
								<td>
									${c?.isTransactional}
								</td>
							</g:if>
							</tr>
						</g:each>
					</tbody>
			</table>
				<div class="pagination">
					<g:if test="${!simbol && !nama}">
 						<g:paginate controller="currency" action="list" total="${currencyInstanceTotal}"/>
 					</g:if>
 					<g:else>
 						<g:paginate total="${currencyInstanceTotal}" params="[sym:simbol,name:nama]"/>
 					</g:else>
				</div>
	</body>
</html>