package es.udc.asi.app.model.blog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.sql.DataSource;
import es.udc.asi.app.model.util.InstanceNotFoundException;



public class JdbcSqlBlogDao implements SqlBlogDao {

	
	private DataSource dataSource;
	 
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	@Override
	public Blog create(Blog blog) {

		/* Create "queryString". */
		String queryString = "INSERT INTO Blog"
				+ " (userId, titulo, fechaCreacion)"
				+ " VALUES (?, ?, ?)";

		Connection conn = null;
		try  {
			conn = dataSource.getConnection();
			
			PreparedStatement preparedStatement = conn.prepareStatement(
					queryString, Statement.RETURN_GENERATED_KEYS);
			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, blog.getUserId());
			preparedStatement.setString(i++, blog.getTitulo());
			Timestamp date = blog.getFechaCreacion() != null ? new Timestamp(
					blog.getFechaCreacion().getTime().getTime()) : null;
            preparedStatement.setTimestamp(i++, date);
			/* Execute query. */
			preparedStatement.executeUpdate();

			/* Get generated identifier. */
			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			if (!resultSet.next()) {
				throw new SQLException(
						"JDBC driver did not return generated key.");
			}
			Long blogId = resultSet.getLong(1);

			/* Return blog. */
			return new Blog(blogId, blog.getUserId(),blog.getTitulo(), blog.getFechaCreacion());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	
	

	public Blog find(Long blogId)
			throws InstanceNotFoundException {
		
		/* Create "queryString". */
        String queryString = "SELECT blogId, userId, titulo, " +
        		"fechaCreacion FROM Blog WHERE blogId = ?";

        Connection conn = null;
        try  {

        	conn = dataSource.getConnection();
        	PreparedStatement preparedStatement = conn.prepareStatement(queryString);
            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, blogId.longValue());

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(blogId,
                        Blog.class.getName());
            }

            /* Get results. */
            i = 2;
            Long userId = resultSet.getLong(i++);
            String titulo = resultSet.getString(i++);            
            Calendar fechaCreacion = Calendar.getInstance();
            fechaCreacion.setTime(resultSet.getTimestamp(i++));
            

            /* Return blog. */
            return new Blog(blogId, userId, titulo, fechaCreacion);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
    			if (conn != null) {
    				try {
    					conn.close();
    				} catch (SQLException e) {}
    			}
    		}
	}
	
	
	
	
	

	@Override
	public void update(Blog blog)
			throws InstanceNotFoundException {

		/* Create "queryString". */
        String queryString = "UPDATE Blog"
                + " SET userId= ?, titulo = ?, fechaCreacion = ? WHERE blogId = ?";

        Connection conn = null;
        try  {
         	conn = dataSource.getConnection();
        	PreparedStatement preparedStatement = conn.prepareStatement(queryString);

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, blog.getUserId());
            preparedStatement.setString(i++, blog.getTitulo());            
            Timestamp date = blog.getFechaCreacion() != null ? new Timestamp(
      					blog.getFechaCreacion().getTime().getTime()) : null;
            preparedStatement.setTimestamp(i++, date);
            preparedStatement.setLong(i++, blog.getBlogId());


            /* Execute query. */
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new InstanceNotFoundException(blog.getBlogId(),
                        Blog.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
    			if (conn != null) {
    				try {
    					conn.close();
    				} catch (SQLException e) {}
    			}
    		}
		
	}
	
	
	
	@Override
	public void remove(Long blogId)
			throws InstanceNotFoundException {
		/* Create "queryString". */
        String queryString = "DELETE FROM Blog WHERE" + " blogId = ?";

        Connection conn = null;
        try  {
        	conn = dataSource.getConnection();
        	PreparedStatement preparedStatement = conn.prepareStatement(queryString);

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, blogId);

            /* Execute query. */
            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(blogId,
                        Blog.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
    			if (conn != null) {
    				try {
    					conn.close();
    				} catch (SQLException e) {}
    			}
    		}
		
	}
	
	
	

	@Override
	public List<Blog> findAll() {
		/* Create "queryString". */

		String queryString = "SELECT * from Blog";

    Connection conn = null;

		try  {
    	conn = dataSource.getConnection();

			PreparedStatement preparedStatement = conn.prepareStatement(queryString);
			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			/* Read blogs. */
			List<Blog> blogs = new ArrayList<Blog>();

			while (resultSet.next()) {

				int i = 1;
				Long blogId = new Long(resultSet.getLong(i++));
				Long userId = new Long(resultSet.getLong(i++));
				String titulo = resultSet.getString(i++);
				Calendar fechaCreacion = Calendar.getInstance();
				fechaCreacion.setTime(resultSet.getTimestamp(i++));				
				blogs.add(new Blog(blogId,userId, titulo, fechaCreacion));

			}

			/* Return blogs. */
			return blogs;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	
	
	
	@Override
	public List<Blog> findBlogsUsuario(Long usuario)
	throws InstanceNotFoundException{
		/* Create "queryString". */

		String queryString = "SELECT * from Blog WHERE userId = ?";
   
		Connection conn = null;

		try  {
    	conn = dataSource.getConnection();

			PreparedStatement preparedStatement = conn.prepareStatement(queryString);
			
		  /* Fill "preparedStatement". */
      int i = 1;
      preparedStatement.setLong(i++, usuario);
			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			/* Read blogs. */
			List<Blog> blogs = new ArrayList<Blog>();

			while (resultSet.next()) {

				i = 1;
				Long blogId = new Long(resultSet.getLong(i++));
				Long userId = new Long(resultSet.getLong(i++));
				String titulo = resultSet.getString(i++);
				Calendar fechaCreacion = Calendar.getInstance();
				fechaCreacion.setTime(resultSet.getTimestamp(i++));				
				blogs.add(new Blog(blogId,userId, titulo, fechaCreacion));

			}

			/* Return blogs. */
			return blogs;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	
}