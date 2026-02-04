package com.thinking.machines.hr.dl;
import java.util.*;
import java.math.*;
import java.sql.*;
public class EmployeeDAO 
{
public List<EmployeeDTO> getAll() throws DAOException
{
List<EmployeeDTO> employees=new LinkedList<EmployeeDTO>();
try
{
Connection connection=DAOConnection.getConnection();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select employee.id,employee.name,employee.designation_code,designation.title,employee.date_of_birth,employee.gender,employee.is_indian,employee.basic_salary,employee.pan_number,employee.aadhar_card_number from employee inner join designation on employee.designation_code=designation.code order by employee.name");
EmployeeDTO employeeDTO;
int id;
String name;
int designationCode;
String designation;
java.sql.Date dateOfBirth;
BigDecimal basicSalary;
boolean isIndian;
String gender;
String panNumber;
String aadharCardNumber;

while(resultSet.next())
{
id=resultSet.getInt("id");
name=resultSet.getString("name").trim();
designationCode=resultSet.getInt("designation_code");
designation=resultSet.getString("title").trim();
dateOfBirth=resultSet.getDate("date_of_birth");
gender=resultSet.getString("gender");
isIndian=resultSet.getBoolean("is_indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
panNumber=resultSet.getString("pan_number").trim();
aadharCardNumber=resultSet.getString("aadhar_card_number").trim();

employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+id);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDesignation(designation);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
employees.add(employeeDTO);
}
resultSet.close();
statement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return employees;
}
public void add(EmployeeDTO employeeDTO) throws DAOException
{
try
{
String panNumber=employeeDTO.getPANNumber();
String aadharCardNumber=employeeDTO.getAadharCardNumber();
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select id from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("PAN number : "+panNumber+" exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select id from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Aadhar card number : "+aadharCardNumber+" exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into employee (name,designation_code,date_of_birth,gender,is_indian,basic_salary,pan_number,aadhar_card_number) values(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,employeeDTO.getName());
preparedStatement.setInt(2,employeeDTO.getDesignationCode());
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
java.sql.Date sqlDate=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDate);
preparedStatement.setString(4,employeeDTO.getGender());
preparedStatement.setBoolean(5,employeeDTO.getIsIndian());
preparedStatement.setBigDecimal(6,employeeDTO.getBasicSalary());
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
employeeDTO.setEmployeeId("A"+resultSet.getInt(1));
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public void update(EmployeeDTO employeeDTO) throws DAOException
{
try
{
String employeeId=employeeDTO.getEmployeeId();
int actualEmployeeId=0;
try
{
actualEmployeeId=Integer.parseInt(employeeId.substring(1));
}catch(Exception exception)
{
throw new DAOException("Invalid employee id : "+employeeId);
}
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select gender from employee where id=?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid employee id : "+employeeId);
}
resultSet.close();
preparedStatement.close();
String panNumber=employeeDTO.getPANNumber();
String aadharCardNumber=employeeDTO.getAadharCardNumber();
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=? and id<>?");
preparedStatement.setString(1,panNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("PAN number : "+panNumber+" exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=? and id<>?");
preparedStatement.setString(1,aadharCardNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Aadhar card number : "+aadharCardNumber+" exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update employee set name=?,designation_code=?,date_of_birth=?,gender=?,is_indian=?,basic_salary=?,pan_number=?,aadhar_card_number=? where id=?");
preparedStatement.setString(1,employeeDTO.getName());
preparedStatement.setInt(2,employeeDTO.getDesignationCode());
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
java.sql.Date sqlDate=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDate);
preparedStatement.setString(4,employeeDTO.getGender());
preparedStatement.setBoolean(5,employeeDTO.getIsIndian());
preparedStatement.setBigDecimal(6,employeeDTO.getBasicSalary());
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.setInt(9,actualEmployeeId);
preparedStatement.executeUpdate();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public boolean employeeIdExists(String employeeId) throws DAOException
{
try
{
int actualEmployeeId=0;
try
{
actualEmployeeId=Integer.parseInt(employeeId.substring(1));
}catch(Exception exception)
{
throw new DAOException("Invalid employee id : "+employeeId);
}
boolean exists=false;
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select gender from employee where id=?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet=preparedStatement.executeQuery();
exists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return exists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}




public boolean panNumberExists(String panNumber) throws DAOException
{
try
{
boolean exists;
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet=preparedStatement.executeQuery();
exists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return exists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}



public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
try
{
boolean exists;
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
ResultSet resultSet=preparedStatement.executeQuery();
exists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return exists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public void deleteByEmployeeId(String employeeId) throws DAOException
{
int actualEmployeeId=0;
try
{
actualEmployeeId=Integer.parseInt(employeeId.substring(1));
}catch(Exception exception)
{
throw new DAOException("Invalid employee id : "+employeeId);
}
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select gender from employee where id=?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid employee id : "+employeeId);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from employee where id=?");
preparedStatement.setInt(1,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}

public EmployeeDTO getByEmployeeId(String employeeId) throws DAOException
{
int actualEmployeeId=0;
EmployeeDTO employeeDTO=null;
try
{
actualEmployeeId=Integer.parseInt(employeeId.substring(1));
}catch(Exception exception)
{
throw new DAOException("Invalid employee id : "+employeeId);
}
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select employee.id,employee.name,employee.designation_code,designation.title,employee.date_of_birth,employee.gender,employee.is_indian,employee.basic_salary,employee.pan_number,employee.aadhar_card_number from employee inner join designation on employee.designation_code=designation.code and employee.id=?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid employee id : "+employeeId);
}

int id;
String name;
int designationCode;
String designation;
java.sql.Date dateOfBirth;
BigDecimal basicSalary;
boolean isIndian;
String gender;
String panNumber;
String aadharCardNumber;

id=resultSet.getInt("id");
name=resultSet.getString("name").trim();
designationCode=resultSet.getInt("designation_code");
designation=resultSet.getString("title").trim();
dateOfBirth=resultSet.getDate("date_of_birth");
gender=resultSet.getString("gender");
isIndian=resultSet.getBoolean("is_indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
panNumber=resultSet.getString("pan_number").trim();
aadharCardNumber=resultSet.getString("aadhar_card_number").trim();

employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+id);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDesignation(designation);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return employeeDTO;
}

public EmployeeDTO getByPANNumber(String panNumber) throws DAOException
{
EmployeeDTO employeeDTO=null;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select employee.id,employee.name,employee.designation_code,designation.title,employee.date_of_birth,employee.gender,employee.is_indian,employee.basic_salary,employee.pan_number,employee.aadhar_card_number from employee inner join designation on employee.designation_code=designation.code and pan_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid PAN number : "+panNumber);
}

int id;
String name;
int designationCode;
String designation;
java.sql.Date dateOfBirth;
BigDecimal basicSalary;
boolean isIndian;
String gender;
String aadharCardNumber;

id=resultSet.getInt("id");
name=resultSet.getString("name").trim();
designationCode=resultSet.getInt("designation_code");
designation=resultSet.getString("title").trim();
dateOfBirth=resultSet.getDate("date_of_birth");
gender=resultSet.getString("gender");
isIndian=resultSet.getBoolean("is_indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
panNumber=resultSet.getString("pan_number").trim();
aadharCardNumber=resultSet.getString("aadhar_card_number").trim();

employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+id);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDesignation(designation);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return employeeDTO;
}

public EmployeeDTO getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
EmployeeDTO employeeDTO=null;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select employee.id,employee.name,employee.designation_code,designation.title,employee.date_of_birth,employee.gender,employee.is_indian,employee.basic_salary,employee.pan_number,employee.aadhar_card_number from employee inner join designation on employee.designation_code=designation.code and aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Aadhar card number : "+aadharCardNumber);
}

int id;
String name;
int designationCode;
String designation;
java.sql.Date dateOfBirth;
BigDecimal basicSalary;
boolean isIndian;
String gender;
String panNumber;

id=resultSet.getInt("id");
name=resultSet.getString("name").trim();
designationCode=resultSet.getInt("designation_code");
designation=resultSet.getString("title").trim();
dateOfBirth=resultSet.getDate("date_of_birth");
gender=resultSet.getString("gender");
isIndian=resultSet.getBoolean("is_indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
panNumber=resultSet.getString("pan_number").trim();
aadharCardNumber=resultSet.getString("aadhar_card_number").trim();

employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+id);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDesignation(designation);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return employeeDTO;
}


}