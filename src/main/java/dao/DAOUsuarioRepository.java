package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {
	
	private Connection connection;
	
	public DAOUsuarioRepository() {
		
		connection = SingleConnectionBanco.getConnection();
		
	}
	
	public ModelLogin gravarUsuario(ModelLogin objeto, Long userlogado) throws SQLException {
		
		/*Insere novo usuário*/
		
		if (objeto.isNovo()) {

			String sql = "insert into model_login(login,senha,nome,email,usuario_id,perfil,sexo) values(?,?,?,?,?,?,?);";
			PreparedStatement prepareSql = connection.prepareStatement(sql);

			prepareSql.setString(1, objeto.getLogin());
			prepareSql.setString(2, objeto.getSenha());
			prepareSql.setString(3, objeto.getNome());
			prepareSql.setString(4, objeto.getEmail());
			prepareSql.setLong(5, userlogado);
			prepareSql.setString(6, objeto.getPerfil());
			prepareSql.setString(7, objeto.getSexo());

			prepareSql.execute();

			connection.commit();

		/*Atualiza usuário ja existente*/	
		
		}else {
			
			String sql = "update model_login SET login=?, senha=?,nome=?,email=?,perfil=?,sexo=? WHERE id= "+objeto.getId()+";";
			PreparedStatement prepareSql = connection.prepareStatement(sql);
			
			prepareSql.setString(1, objeto.getLogin());
			prepareSql.setString(2,objeto.getSenha());
			prepareSql.setString(3, objeto.getNome());
			prepareSql.setString(4, objeto.getEmail());
			prepareSql.setString(5, objeto.getPerfil());
			prepareSql.setString(6, objeto.getSexo());
			
			prepareSql.executeUpdate();
			
			connection.commit();
			
		}
		
		return this.consultaUsuario(objeto.getLogin(),userlogado);
		
		
		
	}
	
public List<ModelLogin>consultaUsuarioList(Long userLogado) throws Exception{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retorno.add(modelLogin);
			
		}
		
		
		return retorno;
	}
	
	public List<ModelLogin>consultaUsuarioList(String nome , Long userLogado) throws Exception{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retorno.add(modelLogin);
			
		}
		
		
		return retorno;
	}
	
	
	  public ModelLogin  consultaUsuarioLogado(String login) throws SQLException {
			
			ModelLogin modelLogin = new ModelLogin();
			
			String sql = "select * from model_login where upper(login) = upper('"+login+"') " ;
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {
				
				modelLogin.setId(resultado.getLong("id"));
				modelLogin.setEmail(resultado.getString("email"));
				modelLogin.setLogin(resultado.getString("login"));
				modelLogin.setNome(resultado.getString("nome"));
				modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
				modelLogin.setPerfil(resultado.getString("perfil"));
				modelLogin.setSexo(resultado.getString("sexo"));
			}
			
			return modelLogin;
			
		}
			
	
  public ModelLogin  consultaUsuario(String login) throws SQLException {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper('"+login+"')  and useradmin is false" ;
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
		}
		
		return modelLogin;
		
	}
	
	public ModelLogin  consultaUsuario(String login, Long userLogado) throws SQLException {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper('"+login+"')  and useradmin is false and usuario_id = " + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
		}
		
		return modelLogin;
		
	}
	
   public ModelLogin  consultaUsuarioID(String id , Long userLogado) throws SQLException {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where id = ?  and useradmin is false and usuario_id= ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
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
		
		String sql = "delete from model_login where id= ?  and useradmin is false;";
		PreparedStatement prepareSql = connection.prepareStatement(sql);
		prepareSql.setLong(1,Long.parseLong(idUser));
        prepareSql.executeUpdate();
        connection.commit();
		
	}

}
