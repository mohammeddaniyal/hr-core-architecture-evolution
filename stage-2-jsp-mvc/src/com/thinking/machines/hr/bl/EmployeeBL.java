package com.thinking.machines.hr.bl;
import com.thinking.machines.hr.dl.*;
import com.thinking.machines.hr.beans.*;
import java.util.*;
import java.text.*;
public class EmployeeBL
{
public List<EmployeeBean> getAll()
{
List<EmployeeBean> employees=new ArrayList<>();
try
{
List<EmployeeDTO> dlEmployees=new EmployeeDAO().getAll();
EmployeeBean employee;
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
for(EmployeeDTO dlEmployee:dlEmployees)
{
employee=new EmployeeBean();
employee.setEmployeeId(dlEmployee.getEmployeeId());
employee.setName(dlEmployee.getName());
employee.setDesignationCode(dlEmployee.getDesignationCode());
employee.setDesignation(dlEmployee.getDesignation());
employee.setDateOfBirth(simpleDateFormat.format(dlEmployee.getDateOfBirth()));
employee.setGender(dlEmployee.getGender());
employee.setIsIndian(dlEmployee.getIsIndian());
employee.setBasicSalary(dlEmployee.getBasicSalary().toPlainString());
employee.setPANNumber(dlEmployee.getPANNumber());
employee.setAadharCardNumber(dlEmployee.getAadharCardNumber());
employees.add(employee);
}
}catch(Exception exception)
{
System.out.println(exception.getMessage());
}
return employees;
}
}