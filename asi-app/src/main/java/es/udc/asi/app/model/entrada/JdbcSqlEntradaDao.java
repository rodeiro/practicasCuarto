package es.udc.asi.app.model.entrada;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
//import sun.tools.java.Type;
import es.udc.asi.app.model.util.InstanceNotFoundException;

//import java.sql.Date;
//import main.java.es.udc.asi.app.model.util.InstanceNotFoundException;



public class JdbcSqlEntradaDao implements SqlEntradaDao {



	private DataSource dataSource;
	 
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	@Override
	public Long crearTipo(String nombre, String mime, String archivo)
			throws FileNotFoundException{
		

		/* Create "queryString". */
		String queryString = "INSERT INTO TipoEnlace"
				+ " (nombre, tipoMime, Icono)"
				+ " VALUES (?, ?, ?)";

		
		Connection conn = null;

		try  {

			conn = dataSource.getConnection();
			
			PreparedStatement preparedStatement = conn.prepareStatement(
					queryString, Statement.RETURN_GENERATED_KEYS);

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setString(i++, nombre);
			preparedStatement.setString(i++, mime);
				File imagen = new File(archivo);
				FileInputStream icono = new FileInputStream(imagen);
				preparedStatement.setBinaryStream(i++, icono, (int)imagen.length());
			
			


			/* Execute query. */
			preparedStatement.executeUpdate();

			/* Get generated identifier. */
			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			if (!resultSet.next()) {
				throw new SQLException(
						"JDBC driver did not return generated key.");
			}
			Long tipoId = resultSet.getLong(1);
			/* Return entrada. */
			return tipoId;

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
	public void removeTipo(Long tipoId)
			throws InstanceNotFoundException{
		

		/* Create "queryString". */
		String queryString = "DELETE FROM TipoEnlace WHERE tipoID = ? ";
			

		
		Connection conn = null;

		try  {

			conn = dataSource.getConnection();
			
			PreparedStatement preparedStatement = conn.prepareStatement(
					queryString, Statement.RETURN_GENERATED_KEYS);

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, tipoId);
			
			/* Execute query. */
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(tipoId,
						Entrada.class.getName());
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
	public Entrada create(Entrada entrada) {


		if (entrada.getTipo() == 0) {
			entrada.setTipoId(entrada.getTipoId());
			entrada.setUrl("no");
		}else{
			entrada.setTexto("no");
		}
		
		
		/* Create "queryString". */
		String queryString = "INSERT INTO Entrada"
				+ " (blogId, titulo, fechaPubli, tipo, texto, tipoId, url)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";

		
		Connection conn = null;

		try  {

			conn = dataSource.getConnection();
			
			PreparedStatement preparedStatement = conn.prepareStatement(
					queryString, Statement.RETURN_GENERATED_KEYS);

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, entrada.getBlog());
			preparedStatement.setString(i++, entrada.getTitulo());
			Timestamp fecha =  new Timestamp(entrada.getFechaPublicacion().getTime().getTime());
			preparedStatement.setTimestamp(i++, fecha);
			preparedStatement.setInt(i++, entrada.getTipo()); 
			preparedStatement.setString(i++, entrada.getTexto());
			if (entrada.getTipoId() == null){
				preparedStatement.setNull(i++, java.sql.Types.BIGINT);
			}else{
				preparedStatement.setLong(i++, entrada.getTipoId());
			}
			preparedStatement.setString(i++, entrada.getUrl());



			/* Execute query. */
			preparedStatement.executeUpdate();

			/* Get generated identifier. */
			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			if (!resultSet.next()) {
				throw new SQLException(
						"JDBC driver did not return generated key.");
			}
			Long entradaId = resultSet.getLong(1);

			/* Return entrada. */
			return new Entrada(entradaId, entrada.getBlog(),entrada.getTitulo(),
					entrada.getFechaPublicacion(), entrada.getTipo(),entrada.getTexto(),
					entrada.getUrl(),entrada.getTipoId());

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
	public Entrada findEntrada(Long entradaId)
			throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "SELECT blogId, titulo, fechaPubli, tipo, texto,"
				+ "tipoId, url FROM Entrada WHERE entradaId = ?";

		
		Connection conn = null;

		try  {
			conn = dataSource.getConnection();

			PreparedStatement preparedStatement = conn.prepareStatement(queryString);

			
			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, entradaId.longValue());

			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				throw new InstanceNotFoundException(entradaId,
						Entrada.class.getName());
			}

			/* Get results. */
			i = 1;

			Long blogP = resultSet.getLong(i++);
			String titulo = resultSet.getString(i++);
			Calendar fecha = Calendar.getInstance();
			fecha.setTime(resultSet.getTimestamp(i++));
			Integer tipo = resultSet.getInt(i++);
			String text = resultSet.getString(i++);
			Long tipoEn = resultSet.getLong(i++);
			String urlEn = resultSet.getString(i++);



			/* Return entrada. */
			
			return new Entrada(entradaId, blogP, titulo, fecha, tipo,
					text, urlEn, tipoEn);

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
	public void update(Entrada entrada)
			throws InstanceNotFoundException {
		
		
		/* Create "queryString". */
		String queryString = "UPDATE Entrada"
				+ " SET blogId = ?, titulo = ?, fechaPubli = ?, tipo= ?,"
				+ "texto = ?, tipoId = ?, url = ?"
				+ " WHERE entradaId = ?";

		Connection conn = null;

		try  {
			conn = dataSource.getConnection();

			PreparedStatement preparedStatement = conn.prepareStatement(queryString);

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++,entrada.getBlog());
			preparedStatement.setString(i++,entrada.getTitulo());	
			Timestamp fecha = new Timestamp(entrada.getFechaPublicacion().getTime().getTime());
			preparedStatement.setTimestamp(i++, fecha);
			preparedStatement.setInt(i++, entrada.getTipo());
			preparedStatement.setString(i++,entrada.getTexto());
			if (entrada.getTipoId() == null){
				preparedStatement.setNull(i++, java.sql.Types.BIGINT);
			}else{
				preparedStatement.setLong(i++, entrada.getTipoId());
			}
			preparedStatement.setString(i++,entrada.getUrl());
			preparedStatement.setLong(i++, entrada.getEntradaId());
			
			/* Execute query. */
			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(entrada.getEntradaId(),
						Entrada.class.getName());
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
	public void remove(Long entradaId)
			throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "DELETE FROM Entrada WHERE" + " entradaId = ?";

		Connection conn = null;

		try  {
			conn = dataSource.getConnection();

			PreparedStatement preparedStatement = conn.prepareStatement(queryString);

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, entradaId);

			/* Execute query. */
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(entradaId,
						Entrada.class.getName());
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
	public List<Entrada> findEntradasBlog(Long blog) {
		/* Create "queryString". */

		String queryString = "SELECT * from Entrada WHERE blogId = ?";

		Connection conn = null;

		try  {
			conn = dataSource.getConnection();

			PreparedStatement preparedStatement = conn.prepareStatement(queryString);
			
			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, blog.longValue());
			
			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			/* Read entradas. */
			List<Entrada> entradas = new ArrayList<Entrada>();

			while (resultSet.next()) {

				i = 1;
				Long entradaId = new Long(resultSet.getLong(i++));
				Long blogId = new Long(resultSet.getLong(i++));
				String titulo = resultSet.getString(i++);
				
				Calendar fecha = Calendar.getInstance();
				fecha.setTime(resultSet.getTimestamp(i++));
				int tipo = resultSet.getInt(i++);
				String texto = resultSet.getString(i++);
				Long tipoEn = new Long(resultSet.getLong(i++));
				String urlEn = resultSet.getString(i++);
				
				
				
				entradas.add(new Entrada(entradaId,blogId,titulo, fecha, tipo,
						texto, urlEn, tipoEn));
						

			}

			/* Return entradas. */
			return entradas;

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
	public List<Entrada> findEntradasUsuario(Long usuario) 
			throws InstanceNotFoundException{
		/* Create "queryString". */

		String queryString = "SELECT entradaId, Entrada.blogId,Entrada.titulo,"
				+ " fechaPubli, tipo, texto, tipoId, url from Blog inner join Entrada"
				+ " on Entrada.blogId = Blog.blogId WHERE userId = ?";

		Connection conn = null;

		try  {

			conn = dataSource.getConnection();

			PreparedStatement preparedStatement = conn.prepareStatement(queryString);
			
			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, usuario);
			
			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			/* Read ofertas. */
			List<Entrada> entradas = new ArrayList<Entrada>();

			while (resultSet.next()) {

				i = 1;
				Long entradaId = new Long(resultSet.getLong(i++));
				Long blogId = new Long(resultSet.getLong(i++));
				String titulo = resultSet.getString(i++);
				
				Calendar fecha = Calendar.getInstance();
				fecha.setTime(resultSet.getTimestamp(i++));
				int tipo = resultSet.getInt(i++);
				String texto = resultSet.getString(i++);
				Long tipoEn = new Long(resultSet.getLong(i++));
				String urlEn = resultSet.getString(i++);
				
				
				
				entradas.add(new Entrada(entradaId,blogId,titulo, fecha, tipo,
						texto, urlEn, tipoEn));
						

			}

			/* Return entradas. */
			return entradas;

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
	public void vote(Long entradaId, Long userId, Boolean voto)
      throws InstanceNotFoundException{
      	
      	
      	/* Create "queryString". */
    		String queryString = "INSERT INTO Votar"
    				+ " (userId, entradaId, voto)"
    				+ " VALUES (?, ?, ?)";
    		
    		
    		Connection conn = null;
    		
    		try  {
    			
    			conn = dataSource.getConnection();

    			PreparedStatement preparedStatement = conn.prepareStatement(queryString);

    			/* Fill "preparedStatement". */
    			int i = 1;
    			preparedStatement.setLong(i++, userId);
    			preparedStatement.setLong(i++, entradaId);

    			preparedStatement.setBoolean(i++, voto);

    			/* Execute query. */
    			int votacion = preparedStatement.executeUpdate();

    			/* Get generated identifier. */

    					 if (votacion == 0) {
    							throw new InstanceNotFoundException(entradaId,
    									Entrada.class.getName());
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
	public void removeVote(Long userId, Long entradaId)
			throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "DELETE FROM Votar WHERE" + " userId = ? AND entradaId = ?";

		Connection conn = null;

		try  {
			conn = dataSource.getConnection();

			PreparedStatement preparedStatement = conn.prepareStatement(queryString);

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, userId);
			preparedStatement.setLong(i++, entradaId);

			/* Execute query. */
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(entradaId,
						Entrada.class.getName());
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
	
	
	
	
}