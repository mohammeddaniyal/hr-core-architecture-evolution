package com.thinking.machines.hr.dl;
import java.sql.*;
public class AdministratorDAO
{
public AdministratorDTO getByUsername(String username) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select * from administrator where username=?");
preparedStatement.setString(1,username);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid username : "+username);
}
AdministratorDTO administrator;
administrator=new AdministratorDTO();
administrator.setUsername(resultSet.getString("username").trim());
administrator.setPassword(resultSet.getString("password").trim());
resultSet.close();
preparedStatement.close();
connection.close();
return administrator;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
}