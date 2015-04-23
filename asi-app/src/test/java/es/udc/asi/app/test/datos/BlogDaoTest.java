package es.udc.asi.app.test.datos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import es.udc.asi.app.model.util.*;
import es.udc.asi.app.model.blog.*;
import es.udc.asi.app.model.user.*;

	
public class BlogDaoTest {
	
	
	ApplicationContext context = 
  		new ClassPathXmlApplicationContext("Spring-Module.xml");

	SqlBlogDao blogDAO = (SqlBlogDao) context.getBean("BlogDAO");
	SqlUserDao userDAO = (SqlUserDao) context.getBean("UserDAO");

	
	
	
	/*

	@Test
	public void testAddBlogAndFindBlog() throws InstanceNotFoundException {
		
		//Crear usuario
		
		User addedUser=userDAO.create(new User("John", "Maven", "Springed", "johnms", "pass", "mavenS"));
		
		//Blog
		Blog addedBlog = null;
		Calendar fecha = Calendar.getInstance();
		addedBlog = blogDAO.create(new Blog(addedUser.getUserId(), "Cocina", fecha));

		Blog foundBlog = blogDAO.find(addedBlog.getBlogId());
		
		assertEquals(addedBlog, foundBlog);

		// Clear Database

		userDAO.remove(addedUser.getUserId());

	}
	
	
	
	@Test
	public void testUpdateBlog() throws InstanceNotFoundException {
		
		//Crear usuario
		
		User addedUser=userDAO.create(new User("Tom", "Hardy", "Springed", "th", "pass", "TH"));
		
		//Blog
		Blog addedBlog = null;
		Calendar fecha = Calendar.getInstance();
		addedBlog = blogDAO.create(new Blog(addedUser.getUserId(), "Cine", fecha));

		addedBlog.setTitulo("Moda");
		blogDAO.update(addedBlog);
		
		Blog foundBlog = blogDAO.find(addedBlog.getBlogId());
		
		assertEquals("Moda", foundBlog.getTitulo());

		// Clear Database

		userDAO.remove(addedUser.getUserId());

	}
	
	
	
	@Test
	public void testFindAllBlogs() throws InstanceNotFoundException {
		
		//Crear usuario
		
		User addedUser=userDAO.create(new User("Matt", "Damon", "Smith", "Mathew", "pass", "MD"));
		
		//Blog

		Calendar fecha = Calendar.getInstance();
		Blog blog1 = blogDAO.create(new Blog(addedUser.getUserId(), "Cine", fecha));

		Calendar fecha2 = Calendar.getInstance();
		Blog blog2 = blogDAO.create(new Blog(addedUser.getUserId(), "Cocina", fecha2));
		
	
		Calendar fecha3 = Calendar.getInstance();
		Blog blog3 = blogDAO.create(new Blog(addedUser.getUserId(), "Moda", fecha3));
		
		
		List<Blog> foundBlogs = new ArrayList<Blog>();
		
		foundBlogs = blogDAO.findAll();
		
		assertEquals(3, foundBlogs.size());

		// Clear Database
		blogDAO.remove(blog1.getBlogId());
		blogDAO.remove(blog2.getBlogId());
		blogDAO.remove(blog3.getBlogId());
		userDAO.remove(addedUser.getUserId());

	}
	
	
	
	

	@Test
	public void testFindBlogsUser() throws InstanceNotFoundException {
		
		//Crear usuario
		
		User addedUser=userDAO.create(new User("Leo", "Dicaprio", "Wilhelm", "Leo", "pass", "MD"));
		User addedUser2 =userDAO.create(new User("Will", "Smith", "Smith", "Will", "pass", "W."));
		//Blog

		Calendar fecha = Calendar.getInstance();
		Blog blog1 = blogDAO.create(new Blog(addedUser.getUserId(), "Cine", fecha));

		Calendar fecha2 = Calendar.getInstance();
		Blog blog2 = blogDAO.create(new Blog(addedUser.getUserId(), "Cocina", fecha2));
		
	
		Calendar fecha3 = Calendar.getInstance();
		Blog blog3 = blogDAO.create(new Blog(addedUser2.getUserId(), "Moda", fecha3));
		
		
		List<Blog> foundBlogs1 = new ArrayList<Blog>();
		
		foundBlogs1 = blogDAO.findBlogsUsuario(addedUser.getUserId());
		
		assertEquals(2, foundBlogs1.size());
		
		List<Blog> foundBlogs2 = new ArrayList<Blog>();
		
		foundBlogs2 = blogDAO.findBlogsUsuario(addedUser2.getUserId());
		
		assertEquals(1, foundBlogs2.size());

		// Clear Database
		blogDAO.remove(blog1.getBlogId());
		blogDAO.remove(blog2.getBlogId());
		blogDAO.remove(blog3.getBlogId());
		userDAO.remove(addedUser.getUserId());
		userDAO.remove(addedUser2.getUserId());

	}
	*/
	
	@Test(expected = InstanceNotFoundException.class)
	public void testFindNoUser() throws InstanceNotFoundException {	
		
		User addedUser=userDAO.create(new User("Leo", "Dicaprio", "Wilhelm", "Leo", "pass", "MD"));

		Calendar fecha3 = Calendar.getInstance();
		Blog blog = blogDAO.create(new Blog(addedUser.getUserId(), "Inform�tica", fecha3));
		
		
		blogDAO.remove(blog.getBlogId());
		try {blogDAO.find(blog.getBlogId());} 
		finally {
			userDAO.remove(addedUser.getUserId());}
		

	}
	
}
	