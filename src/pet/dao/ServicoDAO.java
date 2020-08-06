package pet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pet.model.Servico;

public class ServicoDAO extends DAO<Servico> {

	@Override
	public boolean create(Servico servico) {

		boolean retorno = false;
		Connection conn = getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO servico ");
		sql.append("	(nome, descricao, preco, reserva) ");
		sql.append("VALUES ");
		sql.append("	( ? , ? , ? , ? ) ");

		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, servico.getNome());
			stat.setString(2, servico.getDescricao());
			if (servico.getPreco() != null)
				stat.setFloat(3, servico.getPreco());
			else
				stat.setNull(3, java.sql.Types.FLOAT);

			if (servico.getReserva() != null)
				stat.setInt(4, servico.getReserva());
			else
				stat.setNull(4, java.sql.Types.INTEGER);

			stat.execute();

			conn.commit();

			System.out.println("Inclusão realizada com sucesso.");

			retorno = true;

		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return retorno;
	}

	@Override
	public boolean update(Servico servico) {
		boolean retorno = false;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE servico ");
		sql.append("	SET nome=?, descricao=?, preco=?, reserva=? ");
		sql.append("WHERE ");
		sql.append("	id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, servico.getNome());
			stat.setString(2, servico.getDescricao());
			stat.setFloat(3, servico.getPreco());
			stat.setInt(4, servico.getReserva());
			stat.setInt(5, servico.getId());
			
			stat.execute();
			
			conn.commit();

			System.out.println("Alteração realizada com sucesso.");
			
			retorno = true;

		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return retorno;		
		
	}

	@Override
	public boolean delete(int id) {
		boolean retorno = false;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM servico ");
		sql.append("WHERE ");
		sql.append("	id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			stat.execute();
			
			conn.commit();

			System.out.println("Remoção realizada com sucesso.");
			
			retorno = true;

		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return retorno;
	}

	@Override
	public List<Servico> findAll() {
		List<Servico> listaServico = new ArrayList<Servico>();
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append(" 	id, nome, descricao, preco, reserva ");
		sql.append("FROM ");
		sql.append("	servico ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			
			ResultSet rs = stat.executeQuery();
			
			Servico servico = null;
			
			while(rs.next()) {
				servico = new Servico();
				servico.setId(rs.getInt("id"));
				servico.setNome(rs.getString("nome"));
				servico.setDescricao(rs.getString("descricao"));
				servico.setPreco(rs.getFloat("preco"));
				servico.setReserva(rs.getInt("reserva"));
				listaServico.add(servico);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return listaServico;
	}

	public List<Servico> findByNome(String nome) {
		List<Servico> listaServico = new ArrayList<Servico>();
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append(" 	id, nome, descricao, preco, reserva ");
		sql.append("FROM ");
		sql.append("	servico ");
		sql.append("WHERE ");
		sql.append("	nome ilike ? ");
		sql.append("ORDER BY nome ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, "%" + nome  + "%");
			
			ResultSet rs = stat.executeQuery();
			
			Servico servico = null;
			
			while(rs.next()) {
				servico = new Servico();
				servico.setId(rs.getInt("id"));
				servico.setNome(rs.getString("nome"));
				servico.setDescricao(rs.getString("descricao"));
				servico.setPreco(rs.getFloat("preco"));
				servico.setReserva(rs.getInt("reserva"));
				listaServico.add(servico);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return listaServico;
	}
	
	@Override
	public Servico findById(int id) {
		Servico servico = null;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append(" 	id, nome, descricao, preco, reserva ");
		sql.append("FROM ");
		sql.append("	servico ");
		sql.append("WHERE ");
		sql.append("	id = ? ");

		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				servico = new Servico();
				servico.setId(rs.getInt("id"));
				servico.setNome(rs.getString("nome"));
				servico.setDescricao(rs.getString("descricao"));
				servico.setPreco(rs.getFloat("preco"));
				servico.setReserva(rs.getInt("reserva"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return servico;
	}

}
