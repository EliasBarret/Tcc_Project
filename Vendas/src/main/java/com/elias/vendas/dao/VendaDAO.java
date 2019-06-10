package com.elias.vendas.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.elias.vendas.domain.ItemVenda;
import com.elias.vendas.domain.Venda;
import com.elias.vendas.util.HibernateUtil;

public class VendaDAO extends GenericDAO<Venda>{
	
	
	Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
	
	
	//Operação de listar para as entidades
	@SuppressWarnings("unchecked")
	public List listarPorData(Date dataInicio, Date dataFim){

	
		
		try{
			//captura os critérios de consulta
			Criteria consulta = sessao.createCriteria(Venda.class);
			

			consulta.add(Restrictions.ge("horario",dataInicio));// coloca restrições de busca
		     
		    consulta.add( Restrictions.le("horario",dataFim));
			
			// joga os resultados numa lista
			List resultado = consulta.list();
			// retorna o resultado
			return resultado;	
		}catch(RuntimeException erro){
			throw erro;//display error message
		}finally {
			//sessao.close();//libera recursos 
		}	
	}
	
	public void rankingVendaProduto() {
	      Transaction tx = null;
	      try{
	         tx = sessao.beginTransaction();
	         String sql = "SELECT prod.descricao as produto, " + 
					    "count(*) as quantidade " + 
					    "from   banco.itemvenda item, " + 
					    "banco.produto prod " + 
					    "where  prod.codigo = item.produto_codigo " + 
						"GROUP by produto "+
					    "order by quantidade desc ";
	         SQLQuery query = sessao.createSQLQuery(sql);
	         query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
	         List data = query.list();

	         for(Object object : data)  {
	            Map row = (Map)object;
	            System.out.print("Nome: " + row.get("produto")); 
	            System.out.println(", Salario: " + row.get("quantidade")); 
	         }
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         sessao.close(); 
	      }
	}

}
