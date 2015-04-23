package es.udc.asi.app.test.modelo;

import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import es.udc.asi.app.model.util.*;
import es.udc.asi.app.model.user.*;
import es.udc.asi.app.model.userservice.UserService;

	//private static SqlUserDao userDao = null;
	
public class UserTest {
	
	
	ApplicationContext context = 
  		new ClassPathXmlApplicationContext("Spring-Module.xml");

	SqlUserDao userDAO = (SqlUserDao) context.getBean("UserDAO");
	UserService userSer = (UserService) context.getBean("UserService");

	
	
	

	@Test
	public void testAddUserAndFindUser() throws InstanceNotFoundException, SQLException {
		
		
		User addedUser = null;

		addedUser = userSer.registrar(new User("Jorge", "Mejuto", "Ulloa", "koki", "Pass", "Kokinho"));
		User foundUser = userDAO.find(addedUser.getLogin());
		
		assertEquals(addedUser, foundUser);

		// Clear Database
		userSer.darBaja(addedUser.getUserId());

	}
	
	
	@Test
	public void testAutenticar() throws InstanceNotFoundException, SQLException{
		
		User addedUser = userDAO.create(new User("Jorge", "Mejuto", "Ulloa", "koki", "Pass", "Kokinho"));
		
		//Work
		assertEquals(true,userSer.autenticar("koki", "Pass"));
		//Clear
		userSer.darBaja(addedUser.getUserId());
		
	}
	


	@Test
	public void testUpdateUser() throws InstanceNotFoundException, SQLException {
		
		
		User addedUser = null;
		addedUser = userDAO.create(new User("Jorge", "Mejuto", "Ulloa", "koki", "Pass", "Kokinho"));

		addedUser.setNombre("Fernando");
		userSer.actualizar(addedUser);

		User foundUser = userDAO.find(addedUser.getLogin());
		
		assertEquals(foundUser.getNombre(), "Fernando");

		// Clear Database
		userSer.darBaja(addedUser.getUserId());
	}
	
	
	
	@Test(expected = InstanceNotFoundException.class)
	public void testFindNoUser() throws InstanceNotFoundException, SQLException {	
			userSer.autenticar("tirso", "Pass");
	}
	
	
	
	
	
}