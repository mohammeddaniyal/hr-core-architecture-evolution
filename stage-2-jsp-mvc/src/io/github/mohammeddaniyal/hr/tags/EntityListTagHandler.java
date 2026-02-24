package io.github.mohammeddaniyal.hr.tags;
import java.util.*;
import javax.servlet.jsp.tagext.*;
import io.github.mohammeddaniyal.hr.dl.*;
import io.github.mohammeddaniyal.hr.beans.*;
import javax.servlet.jsp.*;
import java.lang.reflect.*;
public class EntityListTagHandler extends BodyTagSupport
{
private List list;
private int index;
private String populatorClass;
private String populatorMethod;
private String name;
public EntityListTagHandler()
{
reset();
}
private void reset()
{
populatorClass="";
populatorMethod="";
name="";
index=0;
if(list!=null)
{
list.clear();
list=null;
}
}
public void setPopulatorClass(String populatorClass)
{
this.populatorClass=populatorClass;
}
public String getPopulatorClass()
{
return this.populatorClass;
}
public void setPopulatorMethod(String populatorMethod)
{
this.populatorMethod=populatorMethod;
}
public String getPopulatorMethod()
{
return this.populatorMethod;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public int doStartTag()
{
try
{
if(name==null || name.length()==0)  return super.SKIP_BODY;
Class c=Class.forName(this.populatorClass);
Object object=c.newInstance();
Class []parameters=new Class[0];
Method m=c.getMethod(this.populatorMethod,parameters);
this.list=(List)m.invoke(object);
if(list==null) return super.SKIP_BODY;
if(list.size()==0) return super.SKIP_BODY;
index=0;
Object bean=list.get(index);
pageContext.setAttribute(this.name,bean,PageContext.REQUEST_SCOPE);
pageContext.setAttribute("serialNumber",index+1,pageContext.REQUEST_SCOPE);
return super.EVAL_BODY_INCLUDE;
}catch(Throwable t)
{
//some logging act should be done later on (in next styles)
return super.SKIP_BODY;
}
}
public int doAfterBody()
{
index++;
if(list.size()==index) return super.SKIP_BODY;
Object bean;
bean=list.get(index);
pageContext.setAttribute(name,bean,PageContext.REQUEST_SCOPE);
pageContext.setAttribute("serialNumber",index+1,PageContext.REQUEST_SCOPE);
return super.EVAL_BODY_AGAIN;
}
public int doEndTag()
{
reset();
return super.EVAL_PAGE;
}
}
