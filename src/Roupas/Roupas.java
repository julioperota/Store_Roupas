package Roupas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Essa classe descreve o funcionamento de um armário
 * @author julio
 * @see Roupas.Menu
 * @see Roupas.Conexao
 */

public class Roupas {
	private int id;
	private String tipo;
	private int quantidade;
	private String cor;
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public int getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}


	public String getCor() {
		return cor;
	}


	public void setCor(String cor) {
		this.cor = cor;
	}


	public boolean cadastrarRoupas(String tipo, int quantidade, String cor) {
		// Define a conexão
		igualaID();
		int id = this.id;
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			// Define a consulta
			String sql = "insert into armario set id=?, tipo=?, quantidade=?, cor=?;";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql);
			// Define os parâmetros da consulta
			ps.setInt(1, id);
			ps.setString(2, tipo);
			ps.setInt(3, quantidade); 
			ps.setString(4, cor); 
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0) {
				return false;
			}
			igualaID();
			return true;
		} catch (SQLException erro) {
			System.out.println("Erro ao cadastrar roupa: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	
	
	public boolean consultarRoupas(int id) {
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			String sql = "select * from armario where id=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, id); 
			ResultSet rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) {
				return false;
			} else {
				while (rs.next()) {
					this.id = rs.getInt("id");
					this.tipo = rs.getString("tipo");
					this.quantidade = rs.getInt("quantidade");
					this.cor = rs.getString("cor");
				}
				return true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar o armario: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	
	public boolean atualizarRoupas(int id, String cor, String tipo, int quantidade) {
		if (!consultarRoupas(id))
			return false;
		else {
			// Define a conexão
			Connection conexao = null;
			try {
				// Define a conexão
				conexao = Conexao.conectaBanco();
				// Define a consulta
				String sql = "update armario set tipo=?, quantidade=?, cor=? where armario.id=?";
				// Prepara a consulta
				PreparedStatement ps = conexao.prepareStatement(sql);
				// Define os parâmetros da atualização
				ps.setString(1, tipo);
				ps.setInt(2, quantidade);
				ps.setString(3, cor);
				ps.setInt(4, id);
				int totalRegistrosAfetados = ps.executeUpdate();
				if (totalRegistrosAfetados == 0)
					System.out.println("Não foi feita a atualização!");
				else
					System.out.println("Atualização realizada!");
				return true;
			} catch (SQLException erro) {
				System.out.println("Erro ao atualizar a armario: " + erro.toString());
				return false;
			} finally {
				Conexao.fechaConexao(conexao);
			}
		}
	}
	
	public boolean deletarRoupas(int id) {
		// Define a conexão
		igualaID();
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			// Define a consulta
			String sql = "delete from armario where id=?;";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql);
			// Define os parâmetros da consulta
			ps.setInt(1, id);
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0) {
				System.out.println("Não foi feita remoção!");
				return false;
			}
			System.out.println("Remoção realizada!");
			igualaID();
			return true;
		} catch (SQLException erro) {
			System.out.println("Erro ao remover roupa: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	
	public void igualaID() {
		Connection conexao = null;
		conexao = Conexao.conectaBanco();
		try {
			String sql = "select max(id) from armario";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				this.id= rs.getInt("max(id)") + 1;
			}
		}catch (SQLException erro) {
			System.out.println("Erro ao igualar ID: " + erro.toString());
		}	finally{
			Conexao.fechaConexao(conexao);
		}
	}
	
	public boolean checaTabela() {
		int check=0;
		Connection conexao = null;
		conexao = Conexao.conectaBanco();
		try {
			String sql = "select max(id) from armario";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				check = rs.getInt("max(id)");
			}
		if(check!=0) {
			return true;
		}
		else {
			return false;
		}
		}catch (SQLException erro) {
			System.out.println("Erro ao checar tabela: " + erro.toString());
			return false;
		}	finally{
			Conexao.fechaConexao(conexao);
		}
	}
}
