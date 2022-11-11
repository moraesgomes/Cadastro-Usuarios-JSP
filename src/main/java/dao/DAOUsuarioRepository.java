package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {
	
	private Connection connection;
	
	public DAOUsuarioRepository() {
		
		connection = SingleConnectionBanco.getConnection();
		
	}
	
	public ModelLogin gravarUsuario(ModelLogin objeto) throws SQLException {
		
		/*Insere novo usuário*/
		
		if (objeto.isNovo()) {

			String sql = "insert into model_login(login,senha,nome,email) values(?,?,?,?);";
			PreparedStatement prepareSql = connection.prepareStatement(sql);

			prepareSql.setString(1, objeto.getLogin());
			prepareSql.setString(2, objeto.getSenha());
			prepareSql.setString(3, objeto.getNome());
			prepareSql.setString(4, objeto.getEmail());

			prepareSql.execute();

			connection.commit();

		/*Atualiza usuário ja existente*/	
		
		}else {
			
			String sql = "update model_login SET login=?, senha=?,nome=?,email=? WHERE id= "+objeto.getId()+";";
			PreparedStatement prepareSql = connection.prepareStatement(sql);
			
			prepareSql.setString(1, objeto.getLogin());
			prepareSql.setString(2,objeto.getSenha());
			prepareSql.setString(3, objeto.getNome());
			prepareSql.setString(4, objeto.getEmail());
			
			prepareSql.executeUpdate();
			
			connection.commit();
			
		}
		
		return this.consultaUsuario(objeto.getLogin());
		
		
		
	}
	
	public ModelLogin  consultaUsuario(String login) throws SQLException {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper('"+login+"') ";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
		}
		
		return modelLogin;
		
	}
	
	public boolean validarLogin(String login) throws Exception{
		
		String sql = "select count(1) > 0 as existe from model_login where upper(login) = upper('"+login+"') ";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		resultado.next();
	    return resultado.getBoolean("existe");
		
	
	}
	
	public void deletarUser(String idUser) throws Exception {
		
		String sql = "delete from model_login where id= ?;";
		PreparedStatement prepareSql = connection.prepareStatement(sql);
		prepareSql.setLong(1,Long.parseLong(idUser));
        prepareSql.executeUpdate();
        connection.commit();
		
	}

}
