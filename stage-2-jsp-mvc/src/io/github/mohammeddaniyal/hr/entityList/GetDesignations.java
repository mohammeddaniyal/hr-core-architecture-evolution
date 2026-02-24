package io.github.mohammeddaniyal.hr.entityList;
import io.github.mohammeddaniyal.hr.dl.*;
import io.github.mohammeddaniyal.hr.beans.*;
import java.util.*;
public class GetDesignations
{
public List<DesignationBean> getDesignations()
{
List<DesignationBean> designations=new ArrayList<>();
try
{
List<DesignationDTO> dlDesignations=null;
try
{
dlDesignations=new DesignationDAO().getAll();
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
DesignationBean designation;
for(DesignationDTO dlDesignation:dlDesignations)
{
designation=new DesignationBean();
designation.setCode(dlDesignation.getCode());
designation.setTitle(dlDesignation.getTitle());
designations.add(designation);
}
}catch(Exception exception)
{
System.out.println(exception);
}
return designations;
}
}