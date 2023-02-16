package models;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
	//criando variavies encapsuladas para acesso ao banco de dados
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://10.26.49.104:3306/dbgames";
	private String user = "dba";
	private String password = "123@senac";
	
	/**
	 * Metodo responsavel por abrir uma conexao com  o banco
	 * @return
	 */
	
	public Connection conectar() {
		//Criar um objeto
		Connection con = null;
		//tratamento de exceções
		try {
			// logica principal para abrir a conexão 
			//Uso do driver
			Class.forName(driver);
			//obter os paramentros da conexao (informacoes do servidor
			con = DriverManager.getConnection(url, user, password); //conectar!!!
			//retornar a conexao ("abrir a porta da geladeria")
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

}