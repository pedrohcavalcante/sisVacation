package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import database.GerenciadorConexao;

public abstract class GerericDAO<T> implements InterfaceDAO<T> {

	@Override
	public List<T> buscarTodos() {
		return query("SELECT * FROM " + getNomeTabela());
	}

	protected void queryInsert(Map<String, Object> map) throws SQLException {
		String sql = " INSERT INTO " + getNomeTabela() + " (";
		
		Set<String> campos = map.keySet();
		Object[] camposArray = campos.toArray();
		
		for(int i = 0; i < camposArray.length; i++) {
			sql += camposArray[i];
			if(i < camposArray.length - 1) {
				sql += ",";
			}
		}
		
		sql += ") ";
		
		sql += " VALUES ( ";
		
		Collection<Object> values = map.values();
		Object[] valuesArray = values.toArray();
		
		for(int i = 0; i < valuesArray.length; i++) {
			Object object = valuesArray[i];
			
			if(object instanceof String) {
				sql += "'" + object + "'";
			} else {
				sql += valuesArray[i];
			}
			
			if(i < valuesArray.length - 1) {
				sql += ",";
			}
		}
		
		sql += " ) ";
		System.out.println("print da query " + sql);
		
		Connection conexaoBanco = GerenciadorConexao.getConexao();
		PreparedStatement query = conexaoBanco.prepareStatement(sql);
		query.executeUpdate();

	}
	
	protected void queryUpdate(Map<String, Object> map, Object ...objects) throws SQLException{
		String sql = "UPDATE " + getNomeTabela() + " SET ";
		
		Set<String> campos = map.keySet();
		Object[] camposArray = campos.toArray();
		Collection<Object> values = map.values();
		Object[] valuesArray = values.toArray();
		
		for(int i = 0; i < camposArray.length; i++) {
			sql += camposArray[i];
			
			if (valuesArray[i].getClass().getSimpleName().equals("Integer")) {
				sql += " = " + valuesArray[i];
			}else {
				sql += " = '" + valuesArray[i] + "'";
			}
			
			if(i < camposArray.length - 1) {
				sql += ",";
			}
		}
		
		sql += " WHERE id = ? ";
		
		System.out.println("print da query " + sql);
		
		Connection conexaoBanco = GerenciadorConexao.getConexao();
		PreparedStatement query = conexaoBanco.prepareStatement(sql);
		if(objects != null) {
			Integer contador = objects.length;
			for(Object object : objects) {
				query.setObject(contador, object);
				contador++;
			}
		}
		System.out.println("query = " + query);
		query.executeUpdate();
		
		
	}
	
	protected T queryForOne(String sql, Object ...objects) {
		
		
		Connection conexaoBanco = GerenciadorConexao.getConexao();
		T entidade = null;
		try {
			PreparedStatement query = conexaoBanco.prepareStatement(sql);
			
			
			if(objects != null) {
				Integer contador = objects.length;
				for(Object object : objects) {
					query.setObject(contador, object);
					contador++;
				}
			}
			
			query.setMaxRows(1);
			System.out.println("String query" + query);
			ResultSet set = query.executeQuery();
			set.next();
			entidade = toEntity(set);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("String sql queryforone: " + sql);
		
		return entidade;
	}
	
	protected List<T> query(String sql, Object ...objects){
		List<T> result = new ArrayList<T>();
		Connection conexaoBanco = GerenciadorConexao.getConexao();
		
		try {
			PreparedStatement query = conexaoBanco.prepareStatement(sql);
			
			
			if(objects != null) {
				Integer contador = 1;
				for(Object object : objects) {
					query.setObject(contador, object);
					contador++;
				}
			}
			
			ResultSet set = query.executeQuery();
			while(set.next()) {
				T entidade = toEntity(set);
				result.add(entidade);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Print da Query query: " + sql);
		return result;
	}
	
	protected abstract T toEntity(ResultSet set) throws SQLException;
	
	protected abstract String getNomeTabela();

}
