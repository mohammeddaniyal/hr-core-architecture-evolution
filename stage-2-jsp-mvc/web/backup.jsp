<tr style='cursor:pointer' onclick='selectEmployee(this,"${cool.employeeId}")'>
<td style='text-align:right'>${serialNumber}</td>
<td>${cool.employeeId}</td>
<td>${cool.name}</td>
<td>${cool.designation}</td>
<td style='text-align:center'><a href='/styleone/editEmployee?employeeId=${cool.employeeId}'>Edit</a></td>
<td style='text-align:center'><a href='/styleone/confirmDeleteEmployee?employeeId=${cool.employeeId}'>Delete</a></td>
</tr>
