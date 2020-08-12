package pet.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pet.model.ItemVenda;
import pet.model.Servico;
import pet.model.TipoUsuario;
import pet.model.Usuario;
import pet.model.Venda;

public class VendaDAO extends DAO<Venda> {

	@Override
	public boolean create(Venda venda) {

		boolean retorno = false;
		Connection conn = getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO public.venda");
		sql.append(" (data, idusuario) ");
		sql.append("VALUES ");
		sql.append(" (?, ?) ");

		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stat.setDate(1, Date.valueOf(venda.getData()));
			stat.setInt(2, venda.getUsuario().getId());

			stat.execute();

			// obtendo o id gerado pela tabela do banco de dados
			ResultSet rs = stat.getGeneratedKeys();
			rs.next();
			venda.setId(rs.getInt("id"));
			// inserindo os itens de venda

			// ItemVendaDAO dao = new ItemVendaDAO();
			for (ItemVenda itemVenda : venda.getListaItemVenda()) {
				// informando quem eh o pai da crianca
				itemVenda.setVenda(venda);
				// salvando no banco de dados
//				if (dao.create(itemVenda) == false) {
//					throw new Exception("Erro ao incluir um item de venda");
//				}
				if (createItemVenda(itemVenda, conn) == false) {
					throw new Exception("Erro ao incluir um item de venda");
				}

			}

			conn.commit();

			System.out.println("Inclusão realizada com sucesso.");

			retorno = true;

		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return retorno;

	}

	//
	private boolean createItemVenda(ItemVenda itemVenda, Connection conn) {

		boolean retorno = false;
//		Connection conn = getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO public.itemVenda ");
		sql.append("	(valor, idvenda, idservico) ");
		sql.append("VALUES ");
		sql.append("	(?, ?, ?) ");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());

			stat.setFloat(1, itemVenda.getValor());
			stat.setInt(2, itemVenda.getVenda().getId());
			stat.setInt(3, itemVenda.getServico().getId());
			stat.execute();

			if (atualizarEstoque(itemVenda.getServico(), conn) == false) {
				throw new Exception("Erro ao atualizar o estoque");
			}
//			conn.commit();

			retorno = true;
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			// closeConnection(conn);
		}
		return retorno;

	}

	private boolean atualizarEstoque(Servico servico, Connection conn) {

		boolean retorno = false;
//		Connection conn = getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE servico SET reserva = reserva -1 ");
		sql.append("WHERE id = ? ");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());

			stat.setInt(1, servico.getId());

			stat.execute();

//			conn.commit();

			retorno = true;
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			// closeConnection(conn);
		}
		return retorno;

	}

	public List<Venda> findByUsuario(int idUsuario) {
		List<Venda> listaVenda = new ArrayList<Venda>();
		Connection conn = getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  v.id, ");
		sql.append("  v.data, ");
		sql.append("  u.id as idusuario, ");
		sql.append("  u.nome, ");
		sql.append("  u.login,  ");
		sql.append("  u.senha, ");
		sql.append("  u.email, ");
		sql.append("  u.tipousuario, ");
		sql.append("  u.datanascimento ");
		sql.append("FROM ");
		sql.append("  public.venda v, ");
		sql.append("  public.usuario u ");
		sql.append("WHERE ");
		sql.append("  v.idusuario = u.id AND ");
		sql.append("  u.id = ? ");

		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());

			stat.setInt(1, idUsuario);

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getInt("id"));
				venda.setData(rs.getDate("data").toLocalDate());
				venda.setUsuario(new Usuario());
				venda.getUsuario().setId(rs.getInt("idusuario"));
				venda.getUsuario().setNome(rs.getString("nome"));
				venda.getUsuario().setLogin(rs.getString("login"));
				venda.getUsuario().setSenha(rs.getString("senha"));
				venda.getUsuario().setEmail(rs.getString("email"));
				venda.getUsuario().setTipoUsuario(TipoUsuario.valueOf(rs.getInt("tipousuario")));
				Date data = rs.getDate("datanascimento");
				venda.getUsuario().setDataNascimento(data == null ? null : data.toLocalDate());
				// e os itens de venda?!!?
				ItemVendaDAO dao = new ItemVendaDAO();
				venda.setListaItemVenda(dao.findByVenda(venda));
				listaVenda.add(venda);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return listaVenda;
	}

	@Override
	public List<Venda> findAll() {
		List<Venda> listaVenda = new ArrayList<Venda>();
		Connection conn = getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  v.id, ");
		sql.append("  v.data, ");
		sql.append("  u.id as idusuario, ");
		sql.append("  u.nome, ");
		sql.append("  u.login,  ");
		sql.append("  u.senha, ");
		sql.append("  u.email, ");
		sql.append("  u.tipousuario, ");
		sql.append("  u.datanascimento ");
		sql.append("FROM ");
		sql.append("  public.venda v, ");
		sql.append("  public.usuario u ");
		sql.append("WHERE ");
		sql.append("  v.idusuario = u.id ");

		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getInt("id"));
				venda.setData(rs.getDate("data").toLocalDate());
				venda.setUsuario(new Usuario());
				venda.getUsuario().setId(rs.getInt("idusuario"));
				venda.getUsuario().setNome(rs.getString("nome"));
				venda.getUsuario().setLogin(rs.getString("login"));
				venda.getUsuario().setSenha(rs.getString("senha"));
				venda.getUsuario().setEmail(rs.getString("email"));
				venda.getUsuario().setTipoUsuario(TipoUsuario.valueOf(rs.getInt("tipousuario")));
				Date data = rs.getDate("datanascimento");
				venda.getUsuario().setDataNascimento(data == null ? null : data.toLocalDate());

				ItemVendaDAO dao = new ItemVendaDAO();
				venda.setListaItemVenda(dao.findByVenda(venda));
				listaVenda.add(venda);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return listaVenda;
	}

	@Override
	public Venda findById(int id) {
		Venda venda = null;
		Connection conn = getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  v.id, ");
		sql.append("  v.data, ");
		sql.append("  u.idusuario, ");
		sql.append("  u.nome, ");
		sql.append("  u.login,  ");
		sql.append("  u.senha, ");
		sql.append("  u.email, ");
		sql.append("  u.tipousuario, ");
		sql.append("  u.datanascimento ");
		sql.append("FROM ");
		sql.append("  public.venda v, ");
		sql.append("  public.usuario u ");
		sql.append("WHERE ");
		sql.append("  v.idusuario = u.id AND ");
		sql.append("  u.id = ? ");

		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				venda = new Venda();
				venda.setId(rs.getInt("id"));
				venda.setData(rs.getDate("data").toLocalDate());
				venda.setUsuario(new Usuario());
				venda.getUsuario().setId(rs.getInt("idusuario"));
				venda.getUsuario().setNome(rs.getString("nome"));
				venda.getUsuario().setLogin(rs.getString("login"));
				venda.getUsuario().setSenha(rs.getString("senha"));
				venda.getUsuario().setEmail(rs.getString("email"));
				venda.getUsuario().setTipoUsuario(TipoUsuario.valueOf(rs.getInt("tipousuario")));
				Date data = rs.getDate("datanascimento");
				venda.getUsuario().setDataNascimento(data == null ? null : data.toLocalDate());
			}

		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return venda;
	}

	@Override
	public boolean update(Venda venda) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

}
