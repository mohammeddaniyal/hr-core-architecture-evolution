<jsp:useBean id='administratorBean' scope='request' class='io.github.mohammeddaniyal.hr.beans.AdministratorBean' />
<jsp:setProperty name='administratorBean' property='*' />
<jsp:forward page='/login' />