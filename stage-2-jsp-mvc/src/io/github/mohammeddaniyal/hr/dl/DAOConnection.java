package io.github.mohammeddaniyal.hr.dl;
import java.sql.*;
public class DAOConnection
{
private DAOConnection(){}
static public Connection getConnection() throws DAOException
{
Connection connection=null;
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
String url= System.getenv("DB_URL");
String username=System.getenv("DB_USERNAME");
String password=System.getenv("DB_PASSWORD");
if(url==null || username==null || password==null)
{
    throw new DAOException("Database environment variables not set.");
}
connection=DriverManager.getConnection(url, username, password);
return connection;
}catch(Exception exception)
{
    exception.printStackTrace();
throw new DAOException(exception.getMessage());
}
}
}