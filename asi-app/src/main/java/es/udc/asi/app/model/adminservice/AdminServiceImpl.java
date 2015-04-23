package es.udc.asi.app.model.adminservice;



import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.udc.asi.app.model.blog.Blog;
import es.udc.asi.app.model.blog.SqlBlogDao;
import es.udc.asi.app.model.entrada.Entrada;
import es.udc.asi.app.model.entrada.SqlEntradaDao;
/*import main.java.es.udc.asi.app.model.user.SqlUserDao;
import main.java.es.udc.asi.app.model.user.JdbcSqlUserDao;
import main.java.es.udc.asi.app.model.user.User;*/
import es.udc.asi.app.model.util.InstanceNotFoundException;



@Service
@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT, readOnly=false)

public class AdminServiceImpl implements AdminService {

	

	private DataSource dataSource;
	//private SqlUserDao userDao;
	private SqlBlogDao blogDao;
	private SqlEntradaDao entradaDao;
	 
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
/*	public void setUserDAO(SqlUserDao userDao) {
		this.userDao = userDao;
	}*/


	public void setBlogDAO(SqlBlogDao blogDao) {
		this.blogDao = blogDao;
	}
	
	public void setEntradaDAO(SqlEntradaDao entradaDao){
		this.entradaDao = entradaDao;
	}
	
	
	
	@Override
	public Blog CrearBlog(Blog blog) throws SQLException {
		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();


			/* Do work. */
			Blog createdBlog = blogDao.create(blog);
			

			
			return createdBlog;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			throw e;
		}	
		
	}
	
	

	@Override
	public void ModificarBlog(Blog blog) throws SQLException,
			InstanceNotFoundException {

	//	Connection conn = null;
		try {
		//	conn = dataSource.getConnection();


			/* Do work. */
			blogDao.update(blog);
			

	//	} catch (SQLException e) {
		//	throw new RuntimeException(e);
		} catch (RuntimeException e) {
			throw e;
		}		
			
	}
	
	
	

	@Override
	public void BorrarBlog(Long blogId) throws SQLException,
			InstanceNotFoundException {
		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			/* Do work. */
			blogDao.remove(blogId);;


		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			throw e;
		}					
	}
	
	

	@Override
	public Entrada CrearEntrada(Entrada entrada) throws SQLException {
		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			/* Do work. */
			Entrada createdEntrada = entradaDao.create(entrada);

			return createdEntrada;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			throw e;
		}	
		
		
	}
	
	

	@Override
	public void ModificarEntrada(Entrada entrada) throws SQLException,
			InstanceNotFoundException {
		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			/* Do work. */
			entradaDao.update(entrada);


		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			throw e;
		}		
	}

	
	
	@Override
	public void BorrarEntrada(Long entradaId) throws SQLException,
			InstanceNotFoundException {
		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			/* Do work. */
			entradaDao.remove(entradaId);;


		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			throw e;
		}			
		
	}

	
	
	@Override
	public List<Blog> ListarMisBlogs(long userId) throws SQLException,
			InstanceNotFoundException {
			
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			/* Do work. */
		List <Blog> blogs =	blogDao.findBlogsUsuario(userId);

			
			return blogs;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			throw e;
		}			
		
		
		
	}

	
	
	@Override
	public List<Entrada> ListarMisEntradas(long userId) throws SQLException,
			InstanceNotFoundException {

		Connection conn = null;
		try {
			conn = dataSource.getConnection();


			/* Do work. */
		List <Entrada> entradas =	entradaDao.findEntradasUsuario(userId);

			
			return entradas;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			throw e;
		}			
		
	}
	
	

	@Override
	public void VotarEntrada(long userId, long entradaId, boolean voto)
			throws SQLException, InstanceNotFoundException {
		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			/* Do work. */
			entradaDao.vote(entradaId, userId, voto);

			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			throw e;
		}					
	}

	
	
	@Override
	 public Long crearTipo(String nombre, String mime, String archivo)
   		throws FileNotFoundException{
		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			/* Do work. */
			Long tipo = entradaDao.crearTipo(nombre, mime, archivo);

			return tipo;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			throw e;
		}					
	}
	
	
}