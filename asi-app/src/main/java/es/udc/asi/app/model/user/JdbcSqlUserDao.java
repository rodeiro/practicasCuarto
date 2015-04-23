package es.udc.asi.app.model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
//import java.sql.Timestamp;
//import java.sql.Date;
import es.udc.asi.app.model.util.InstanceNotFoundException;

public class JdbcSqlUserDao implements SqlUserDao {

	
	private DataSource dataSource;
	 
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public User create( User user) {
		
		/* Create "queryString". */
		String queryString = "INSERT INTO Usuario"
				+ " (nombre, apellido1, apellido2,"
				+ " login, password, nombrePantalla)"
				+ " VALUES (?, ?, ?, ? ,? ,?)";
		
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(
					queryString, Statement.RETURN_GENERATED_KEYS);
			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setString(i++, user.getNombre());
			preparedStatement.setString(i++, user.getApellido1());
			preparedStatement.setString(i++, user.getApellido2());
			preparedStatement.setString(i++, user.getLogin());
			preparedStatement.setString(i++, user.getPassword());
			preparedStatement.setString(i++, user.getNombreEnPantalla());

			/* Execute query. */
			preparedStatement.executeUpdate();

			/* Get generated identifier. */
			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			if (!resultSet.next()) {
				throw new SQLException(
						"JDBC driver did not return generated key.");
			}
			Long userId = resultSet.getLong(1);
			/* Return user. */
			return new User(userId, user.getNombre(),
					user.getApellido1(), user.getApellido2(),
					user.getLogin(), user.getPassword(),
					user.getNombreEnPantalla());

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
	public User find(String login)
			throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "SELECT userId, nombre, apellido1, apellido2,"
				+ " login, password, nombrePantalla "
				+ "FROM Usuario WHERE login = ?";

		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(queryString);

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setString(i++, login);

			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				throw new InstanceNotFoundException(login,
						User.class.getName());
			}

			/* Get results. */
			i = 1;
			
			
			Long usuario = new Long (resultSet.getLong(i++));
			String nombre = resultSet.getString(i++);
			String apellido1 = resultSet.getString(i++);
			String apellido2 = resultSet.getString(i++);
			//String login = resultSet.getString(i++);
			i++;
			String password = resultSet.getString(i++);
			String nombreEnPantalla = resultSet.getString(i++);
			
			
			/* Return usuario. */
			
			return new User(usuario, nombre, apellido1, apellido2, login,
					password, nombreEnPantalla);

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
	public void update(User user)
			throws InstanceNotFoundException {
		
		
	
		/* Create "queryString". */
		String queryString = "UPDATE Usuario"
				+ " SET nombre = ?, apellido1 = ?, apellido2 = ?, login= ?,"
				+ "password = ?, nombrePantalla = ?"
				+ " WHERE userId = ?";

		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn
					.prepareStatement(queryString);

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setString(i++, user.getNombre());
			preparedStatement.setString(i++, user.getApellido1());
			preparedStatement.setString(i++, user.getApellido2());
			preparedStatement.setString(i++, user.getLogin());
			preparedStatement.setString(i++, user.getPassword());
			preparedStatement.setString(i++, user.getNombreEnPantalla());
			preparedStatement.setLong(i++, user.getUserId());

			/* Execute query. */
			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(user.getUserId(),
						User.class.getName());
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
	public void remove(Long userId)
			throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "DELETE FROM Usuario WHERE" + " userId = ?";

		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(queryString);

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, userId);

			/* Execute query. */
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(userId,
						User.class.getName());
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