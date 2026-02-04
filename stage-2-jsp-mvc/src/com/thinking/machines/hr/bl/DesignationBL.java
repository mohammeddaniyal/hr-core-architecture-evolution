package com.thinking.machines.hr.bl;
import com.thinking.machines.hr.dl.*;
import com.thinking.machines.hr.beans.*;
import java.util.*;
public class DesignationBL
{
public List<DesignationBean> getAll()
{
List<DesignationBean> designations=new ArrayList<>();
try
{
List<DesignationDTO> dlDesignations=new DesignationDAO().getAll();
DesignationBean designation;
for(DesignationDTO dlDesignation:dlDesignations)
{
designation=new DesignationBean();
designation.setCode(dlDesignation.getCode());
designation.setTitle(dlDesignation.getTitle());
designations.add(designation);
}
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());// to be changed after testing
}
return designations;
}
}