/**
 * @author Pedro H B Cavalcante
 */
package DAO;

import java.util.List;

public interface InterfaceDAO<T> {
	
	/**
	 * lista com todos os dados
	 * @return list
	 */
	public List<T> buscarTodos();
	
	
	/**
	 * um dos dados escolhidos
	 * @return void
	 */
	public T buscar();
	
	/**
	 * atualiza um registro
	 * @return void
	 */
	public T atualizar();
	
	/**
	 * insere um novo registro no banco
	 * @return void
	 */
	public T inserir();
	
	/**
	 * remove um dado do banco
	 * @return void
	 */
	public T remover();
	
}
