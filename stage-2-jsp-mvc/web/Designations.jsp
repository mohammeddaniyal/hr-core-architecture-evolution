<%@taglib uri='/WEB-INF/mytags/tmtags.tld' prefix='tm' %>
<tm:Module name='DESIGNATION' />
<jsp:include page='/MasterPageTopSection.jsp' />
<h2>Designations</h2>
<table border='1'>
<thead>
<tr>
<th colspan='4' style='text-align:right;padding:5px'>
<a href='/stage2/DesignationAddForm.jsp'>Add new Designation</a>
</th>
</tr>
<tr>
<th style='width:60px;text-align:center'>S.No</th>
<th style='width:200px;text-align:center'>Designation</th>
<th style='width:100px;text-align:center'>Edit</th>
<th style='width:100px;text-align:center'>Delete</th>
</tr>
</thead>
<tbody>
<tm:EntityList populatorClass='io.github.mohammeddaniyal.hr.bl.DesignationBL' populatorMethod='getAll' name='cool'>
<tr>
<td style='text-align:right'>${serialNumber}</td>
<td>${cool.title}</td>
<td style='text-align:center'><a href='/stage2/editDesignation?code=${cool.code}'>Edit</a></td>
<td style='text-align:center'><a href='/stage2/confirmDeleteDesignation?code=${cool.code}'>Delete</a></td>
</tr>
</tm:EntityList>
</tbody>
</table>
<jsp:include page='/MasterPageBottomSection.jsp' />