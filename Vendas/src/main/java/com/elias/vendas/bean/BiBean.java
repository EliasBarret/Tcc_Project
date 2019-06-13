package com.elias.vendas.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

import com.elias.vendas.dao.VendaDAO;
import com.elias.vendas.domain.Venda;
import com.elias.vendas.util.BiUtil;
import com.elias.vendas.util.HibernateUtil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class BiBean implements Serializable{
	
	HorizontalBarChartModel model;
	private BarChartModel barra;
	
	//vendas
	private PieChartModel pieModel; 
	private PieChartModel pieModelVendedor;
	private PieChartModel pieModelCliente;
	private LineChartModel lineModel;
	private LineChartModel dateModel;
	private LineChartModel lineCliente;
	
	public BarChartModel getBarra() {
		return barra;
	}
	public void setBarra(BarChartModel barra) {
		this.barra = barra;
	}
	public PieChartModel getPieModel() {
		return pieModel;
	}
	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}
	public LineChartModel getLineModel() {
		return lineModel;
	}
	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}
	public PieChartModel getPieModelVendedor() {
		return pieModelVendedor;
	}
	public void setPieModelVendedor(PieChartModel pieModelVendedor) {
		this.pieModelVendedor = pieModelVendedor;
	}
	public LineChartModel getDateModel() {
		return dateModel;
	}
	public void setDateModel(LineChartModel dateModel) {
		this.dateModel = dateModel;
	}
	public PieChartModel getPieModelCliente() {
		return pieModelCliente;
	}
	public void setPieModelCliente(PieChartModel pieModelCliente) {
		this.pieModelCliente = pieModelCliente;
	}
	public LineChartModel getLineCliente() {
		return lineCliente;
	}
	public void setLineCliente(LineChartModel lineCliente) {
		this.lineCliente = lineCliente;
	}

	
	@PostConstruct
	public void listarGrafico() {
		VendaDAO dao;
		List<Venda> lista;
		try {
			dao = new VendaDAO();
			lista  = dao.listar();
			//graficar(lista);
			//graficoLinha(lista);
			rankingVendaProduto();
			rankingVendaVendedor();
			rankingVendaCliente();
			graficoLinhaData(lista);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void graficar(List<Venda> lista) {
		pieModel = new PieChartModel();
		
		for (Venda ven: lista) {
			pieModel.set(ven.getCliente().toString(), ven.getValorTotal());
			
		}
		
		pieModel.setTitle("Grafico de Venda por Cliente");
		pieModel.setLegendPosition("e");
		pieModel.setFill(false);
		pieModel.setShowDataLabels(true);
		pieModel.setDiameter(150);

	}
	
//	public void graficoLinha(List<Venda> lista) {
//		int i=0;
//		lineModel = new LineChartModel();
//		LineChartSeries series1 = new LineChartSeries();
//		
//		for (Venda ven: lista) {
//			series1.setLabel("Venda");
//			series1.set(i, ven.getValorTotal());
//			
//			i++;
//		}
//		
//		lineModel.addSeries(series1);
//		lineModel.setTitle("Grafico de evolucao das vendas");
//		lineModel.setLegendPosition("e");
//	}
	

	public void rankingVendaProduto() {
		int i=0;
		pieModel = new PieChartModel();
		
		
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction tx = null;
	      try{
	         tx = sessao.beginTransaction();
	         String sql = "SELECT prod.descricao as produto, " + 
					    "count(*) as quantidade " + 
					    "from   banco.itemvenda item, " + 
					    "banco.produto prod " + 
					    "where  prod.codigo = item.produto_codigo " + 
						"GROUP by produto "+
					    "order by quantidade desc " +
					    " LIMIT 5 ";
	         SQLQuery query = sessao.createSQLQuery(sql);
	         query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
	         List data = query.list();

	         for(Object object : data)  {
	            Map row = (Map)object;
//	            System.out.print("Nome: " + row.get("produto")); 
//	            System.out.println(", Quantidade Vendida: " + row.get("quantidade")); 
	            
	            pieModel.set(row.get("produto").toString().toUpperCase(), (Number) row.get("quantidade"));
	            
	            i++; 
	         }
	         tx.commit();
	        
	         System.out.println(i);
	         
	        pieModel.setTitle("Top 5 Produtos Mais Vendidos");
	 		pieModel.setLegendPosition("e");
	 		pieModel.setFill(false);
	 		pieModel.setShowDataLabels(true);
	 		pieModel.setDiameter(150);
	 		
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         sessao.close(); 
	      }
}
	public void rankingVendaVendedor() {
		int i=0;
		pieModelVendedor = new PieChartModel();
		
		
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction tx = null;
	      try{
	         tx = sessao.beginTransaction();
	         String sql = 	"SELECT p.nome as nome, "+
	        		 		"count(*) as quantidade "+
			        		" from banco.itemvenda item, "+
			        		"   	   banco.produto prod, "+
			        		"   	   banco.Funcionario func, "+
			        		"   	   banco.Pessoa p "+
		        		  " where  prod.codigo = item.produto_codigo "+
		        		  "   and  func.codigo = item.funcionario_codigo "+
		        		  "   and  p.codigo = func.codigo "+
		        		  " GROUP by nome "+
		        		  " order by quantidade desc "+
		        		  " LIMIT 5 ";	         
	         SQLQuery query = sessao.createSQLQuery(sql);
	         query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
	         List data = query.list();

	         for(Object object : data)  {
	            Map row = (Map)object;
//	            System.out.print("Nome: " + row.get("nome")); 
//	            System.out.println(", Quantidade Vendida: " + row.get("quantidade")); 
	            
	            pieModelVendedor.set(row.get("nome").toString(), (Number) row.get("quantidade"));
	            
	            i++; 
	         }
	         tx.commit();
	        
	         System.out.println(i);
	         
	         pieModelVendedor.setTitle("Top 5 Melhores Vendedores");
	         pieModelVendedor.setLegendPosition("e");
	         pieModelVendedor.setFill(false);
	         pieModelVendedor.setShowDataLabels(true);
	         pieModelVendedor.setDiameter(150);
	 		
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         sessao.close(); 
	      }
	}
	
	public void graficoLinhaData(List<Venda> lista) {
		
		dateModel = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
		
		for (Venda ven: lista) {
			series1.setLabel("Venda");
			series1.set(ven.getHorario(), ven.getValorTotal());
		}
		
		dateModel.addSeries(series1);
		dateModel.setZoom(true);
		dateModel.setTitle("Grafico de Evolucao de vendas");
		dateModel.setLegendPosition("e");
		DateAxis axis = new DateAxis("Data da Venda");
        axis.setTickAngle(-50);
        axis.setTickFormat("%b %#d, %y");
        
        dateModel.getAxes().put(AxisType.X, axis);
	}
	
		public void graficoLinhaDatas() {
		
		dateModel = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
		
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction tx = null;
	      try{
	         tx = sessao.beginTransaction();
	         String sql = 	"SELECT p.nome as nome, "+
	        		 		"count(*) as quantidade "+
			        		" from banco.itemvenda item, "+
			        		"   	   banco.produto prod, "+
			        		"   	   banco.Funcionario func, "+
			        		"   	   banco.Pessoa p "+
		        		  " where  prod.codigo = item.produto_codigo "+
		        		  "   and  func.codigo = item.funcionario_codigo "+
		        		  "   and  p.codigo = func.codigo "+
		        		  " GROUP by nome "+
		        		  " order by quantidade desc "+
		        		  " LIMIT 5 ";	         
	         SQLQuery query = sessao.createSQLQuery(sql);
	         query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
	         List data = query.list();

	         for(Object object : data)  {
	            Map row = (Map)object;
	            
	           // pieModelVendedor.set(row.get("nome").toString(), (Number) row.get("quantidade"));
	            
	         }
	         tx.commit();
	        
	     	dateModel.addSeries(series1);
			dateModel.setZoom(true);
			dateModel.setTitle("Grafico de Evolucao de vendas");
			dateModel.setLegendPosition("e");
			DateAxis axis = new DateAxis("Data da Venda");
	        axis.setTickAngle(-50);
	        axis.setTickFormat("%b %#d, %y");
	        
	        dateModel.getAxes().put(AxisType.X, axis);
	       
	 		
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         sessao.close(); 
	      }
	}
		public void rankingVendaCliente() {
			
			pieModelCliente = new PieChartModel();

			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			Transaction tx = null;
		      try{
		         tx = sessao.beginTransaction();
		         String sql = "select pessoa.nome as nome, " + 
		         		"			count(*) as codigo  " + 
		         		"	from venda, " + 
		         		"		 pessoa, " + 
		         		"		 cliente " + 
		         		"   where venda.cliente_codigo = cliente.codigo " + 
		         		"   and pessoa.codigo = cliente.pessoa_codigo " + 
		         		" group by nome " + 
		         		" order by codigo desc " + 
		         		" limit 5 ";

		         SQLQuery query = sessao.createSQLQuery(sql);
		         query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		         List data = query.list();

		         for(Object object : data)  {
		            Map row = (Map)object;
		            
		            pieModelCliente.set(row.get("nome").toString().toUpperCase(), (Number) row.get("codigo"));
		         }
		         tx.commit();
		       
		         pieModelCliente.setTitle("Top 5 Melhores Clientes");
		         pieModelCliente.setLegendPosition("e");
		         pieModelCliente.setFill(false);
		         pieModelCliente.setShowDataLabels(true);
		         pieModelCliente.setDiameter(150);
		 		
		      }catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }finally {
		         sessao.close(); 
		      }
	}
		
}
