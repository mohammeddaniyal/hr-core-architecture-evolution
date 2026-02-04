package com.thinking.machines.hr.dl;
import java.sql.*;
import java.util.*;
public class DesignationDAO
{
public void add(DesignationDTO designation) throws DAOException
{
String title=designation.getTitle();
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation : "+title+" exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into designation (title) value(?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,title);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int code=resultSet.getInt(1);
designation.setCode(code); 
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public void update(DesignationDTO designation) throws DAOException
{
try
{
int code=designation.getCode();
String title=designation.getTitle();
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Designation Code : "+code);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select * from designation where title=? and code!=?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation : "+title+" exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update designation set title=? where code=?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
preparedStatement.executeUpdate();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}

public void deleteByCode(int code) throws DAOException
{
try
{

Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Designation Code : "+code);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select gender from employee where designation_code=?");
preparedStatement.setInt(1,code);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Cannot delete designation as it has been allocated to an employee");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from designation where code=?");
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}

public List<DesignationDTO> getAll() throws DAOException
{
List<DesignationDTO> designations;
designations=new LinkedList<>();
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select * from designation order by title");
DesignationDTO designation;
int code;
String title;
while(resultSet.next())
{
code=resultSet.getInt("code");
title=resultSet.getString("title").trim();
designation=new DesignationDTO();
designation.setCode(code);
designation.setTitle(title);
designations.add(designation);
}
resultSet.close();
statement.close();
connection.close();
return designations;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public boolean designationCodeExists(int code) throws DAOException
{
try
{
boolean exists;
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,code);
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
public DesignationDTO getByCode(int code) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Designation code : "+code);
}
DesignationDTO designation;
designation=new DesignationDTO();
designation.setCode(resultSet.getInt("code"));
designation.setTitle(resultSet.getString("title").trim());
resultSet.close();
preparedStatement.close();
connection.close();
return designation;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
}