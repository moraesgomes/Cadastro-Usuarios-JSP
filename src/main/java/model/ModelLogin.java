package model;

import java.io.Serializable;

public class ModelLogin implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String login;
	private String senha;
	
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getSenha() {
		return senha;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	

}
