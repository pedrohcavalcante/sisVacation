/**
 * @author Pedro H B Cavalcante
 * @version 1.0
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GerenciadorConexao {
	
	private static Connection conexao;
	
	public static Connection getConexao() {
		if (conexao == null){
			String username = "root";
			String password = "root";
			System.out.println(">>>>> Entrando com user " + username);
			String url = "jdbc:mysql://localhost:3306/sisVacation?useTimezone=true&serverTimezone=UTC&useSSL=false";
			try{
				System.out.println(">>>>> Entrando no try");
				Class.forName("com.mysql.cj.jdbc.Driver");
				conexao = DriverManager.getConnection(url, username, password);
				System.out.println("conectou");
			}catch (SQLException e){
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return conexao;
	}
	public static void fechaConexao(Connection conexao) {
		
		if(conexao != null) {
			try {
				System.out.println("Fechando conexao...");
				conexao.close();
			} catch (SQLException e) {			
				System.out.println(e.getMessage());
			}	
		}	
	}
	
	public static void main(String[] args) {
		
		GerenciadorConexao.getConexao();
	}
}
