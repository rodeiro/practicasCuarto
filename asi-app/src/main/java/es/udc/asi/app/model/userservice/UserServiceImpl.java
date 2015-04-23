package es.udc.asi.app.model.userservice;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.udc.asi.app.model.blog.SqlBlogDao;
import es.udc.asi.app.model.user.SqlUserDao;
import es.udc.asi.app.model.user.JdbcSqlUserDao;
import es.udc.asi.app.model.user.User;
import es.udc.asi.app.model.util.InstanceNotFoundException;


@Service
@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT, readOnly=false)

public class UserServiceImpl implements UserService {

	private DataSource dataSource;
	private SqlUserDao userDao;
	 
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void setUserDAO(SqlUserDao userDao) {
		this.userDao = userDao;
	}

	
	@Override	
	public User registrar(User usuario) throws SQLException {
		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			/* Do work. */
			
			User createdUser = userDao.create(usuario);
			

			
			return createdUser;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			throw e;
		}
	}

	@Override
	public void actualizar(User usuario) throws InstanceNotFoundException, SQLException {
		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			/* Do work. */
			userDao.update(usuario);


		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			throw e;
		}		
	}

	@Override
	public void darBaja(Long userId) throws InstanceNotFoundException, SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			/* Do work. */
			userDao.remove(userId);;
			
			

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			throw e;
		}			
	}

	@Override
	public boolean autenticar(String login, String password)
			throws InstanceNotFoundException, SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			/* Do work. */
			User usuario = userDao.find(login);
			if (usuario.getPassword().equals(password)){
				return true;
			}else {
				return false;
			}
			
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	

	@Override
	public void cerrarSesion(Long userId) {
		// No hace nada en este nivel
		
	}
	
		
	

	

}