package pet.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import pet.model.Entity;

public abstract class DAO<T extends Entity<T>> {
	
	public abstract boolean create(T entity);
	public abstract boolean update(T entity);
	public abstract boolean delete(int id);
	public abstract List<T> findAll();
	public abstract T findById(int id);

	protected static Connection getConnection() {
		Connection conn = null;
		try {
			// registrando o driver do postgres
			Class.forName("org.postgresql.Driver");
			// estabelecendo uma conexao com o banco
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pet", "topicos1", "123456");
			// obrigando a trabalhar com commit e rollback
			conn.setAutoCommit(false);
	
		} catch (ClassNotFoundException e) {
			System.out.println("Erro ao registrar o driver do postgres.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Erro ao conectar no banco de dados.");
			e.printStackTrace();
		}
		return conn;
	}
	
	protected void rollback(Connection conn) {
		if (conn != null) {
			try {
				if (!conn.isClosed())
					conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	protected void closeStatement(PreparedStatement stat) {
		if (stat != null) {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	protected void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}