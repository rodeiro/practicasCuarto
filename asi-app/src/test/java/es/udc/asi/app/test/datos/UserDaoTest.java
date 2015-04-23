package es.udc.asi.app.test.datos;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import es.udc.asi.app.model.util.*;
import es.udc.asi.app.model.user.*;

	//private static SqlUserDao userDao = null;
	
public class UserDaoTest {
	
	
	ApplicationContext context = 
  		new ClassPathXmlApplicationContext("Spring-Module.xml");

	SqlUserDao userDAO = (SqlUserDao) context.getBean("UserDAO");
	//SqlUserDao auserDAO = (SqlUserDao) context.getBean("AbstractUserDAO");
	
	
	
	

	@Test
	public void testAddUserAndFindUser() throws InstanceNotFoundException {
		
		
		User addedUser = null;
		addedUser = userDAO.create(new User("Jorge", "Mejuto", "Ulloa", "koki", "Pass", "Kokinho"));

		User foundUser = userDAO.find(addedUser.getLogin());

		assertEquals(addedUser, foundUser);

		// Clear Database
		userDAO.remove(addedUser.getUserId());

	}
	
	
	

	@Test
	public void testUpdateUser() throws InstanceNotFoundException {
		
		
		User addedUser = null;
		addedUser = userDAO.create(new User("Jorge", "Mejuto", "Ulloa", "koki", "Pass", "Kokinho"));

		addedUser.setNombre("Fernando");
		userDAO.update(addedUser);

		User foundUser = userDAO.find(addedUser.getLogin());
		
		assertEquals(foundUser.getNombre(), "Fernando");

		// Clear Database
		userDAO.remove(addedUser.getUserId());

	}
	
	
	
	@Test(expected = InstanceNotFoundException.class)
	public void testFindNoUser() throws InstanceNotFoundException {	
			userDAO.find("Spring");
		
	}
	
	
	
	
	
}
	