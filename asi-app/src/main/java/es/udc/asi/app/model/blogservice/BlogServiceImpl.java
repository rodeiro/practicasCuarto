package es.udc.asi.app.model.blogservice;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import es.udc.asi.app.model.blog.Blog;
import es.udc.asi.app.model.blog.SqlBlogDao;
import es.udc.asi.app.model.entrada.Entrada;
import es.udc.asi.app.model.entrada.SqlEntradaDao;
import es.udc.asi.app.model.blog.SqlBlogDao;
import es.udc.asi.app.model.user.SqlUserDao;
import es.udc.asi.app.model.user.User;
import es.udc.asi.app.model.util.InstanceNotFoundException;
import javax.sql.DataSource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT, readOnly=false)


public class BlogServiceImpl implements BlogService {

	private DataSource dataSource;
	private SqlBlogDao blogDao;
	private SqlEntradaDao entradaDao;
	 
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void setBlogDAO(SqlBlogDao blogDao) {
		this.blogDao = blogDao;
	}
	
	public void setEntradaDAO(SqlEntradaDao entradaDao) {
		this.entradaDao = entradaDao;
	}
	
	@Override
	public List<Blog> ConsultarBlogs() throws InstanceNotFoundException, SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			/* Do work. */
			List<Blog> Blogs = blogDao.findAll();		
			
			
			return Blogs;

		} catch (SQLException e) {			
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			conn.rollback();
			throw e;
		}
	}

	@Override
	public List<Entrada> VerListaEntradas(Long blogId) throws InstanceNotFoundException, SQLException {
				Connection conn = null;
				try {
					conn = dataSource.getConnection();

					/* Do work. */
					List<Entrada> Entradas = entradaDao.findEntradasBlog(blogId);		
					
					
					return Entradas;

				} catch (SQLException e) {
					throw new RuntimeException(e);
				} catch (RuntimeException e) {					
					throw e;
				}
			}

	@Override
	public Entrada VerEntrada(Long entradaId) throws InstanceNotFoundException, SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			
			/* Do work. */
			Entrada entrada = entradaDao.findEntrada(entradaId);		
			return entrada;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			throw e;
		}
	}

		@Override
		public Blog VerBlog(Long blogId) throws InstanceNotFoundException, SQLException{
			Connection conn = null;
			try{
				conn = dataSource.getConnection();
				
				Blog blog = blogDao.find(blogId);
				
				return blog;

			}catch (SQLException e) {
				throw new RuntimeException(e);
			} catch (RuntimeException e) {
				throw e;
			}
			
		}
	
	
}
