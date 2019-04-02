package com.elias.vendas.util;

import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.service.ServiceRegistry;

import com.mysql.jdbc.Connection;

public class HibernateUtil {
	private static SessionFactory fabricaDeSessoes = criarFabricaDeSessoes();;
	
	public static SessionFactory getFabricaDeSessoes() {
		return fabricaDeSessoes;
	}
	
	public static Connection getConexao(){
		Session sessao = fabricaDeSessoes.openSession();
		
		
		Connection conexao = sessao.doReturningWork(new ReturningWork<Connection>() {
			/*@Override
			public Connection execute(Connection conn) throws SQLException{
				
				return conn;
			}*/

			@Override
			public Connection execute(java.sql.Connection connection) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
		});
		return conexao;
	}
	
	private static SessionFactory criarFabricaDeSessoes() {
		try {
			Configuration configuracao = new Configuration().configure();
			
			ServiceRegistry registro = new StandardServiceRegistryBuilder().applySettings(configuracao.getProperties()).build();
			SessionFactory fabrica = configuracao.buildSessionFactory(registro);
			
			return fabrica;
		} catch (Throwable ex) {
			System.err.println("A fábrica de sessões não pode ser criada." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
}
