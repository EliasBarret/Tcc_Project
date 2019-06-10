package com.elias.vendas.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.model.chart.BarChartModel;
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
	private PieChartModel pieModel;
	private LineChartModel lineModel;
	
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
	
	
	public void listarGrafico() {
		VendaDAO dao;
		List<Venda> lista;
		try {
			dao = new VendaDAO();
			lista  = dao.listar();
			//graficar(lista);
			graficoLinha(lista);
			rankingVendaProduto();
			
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
	
	public void graficoLinha(List<Venda> lista) {
		int i=0;
		lineModel = new LineChartModel();
		LineChartSeries series1 = new LineChartSeries();
		
		for (Venda ven: lista) {
			series1.setLabel("Venda");
			series1.set(i, ven.getValorTotal());
			
			i++;
		}
		
		lineModel.addSeries(series1);
		lineModel.setTitle("Grafico de evolucao das vendas");
		lineModel.setLegendPosition("e");
	}
	
	public void chamaBI() {
		BiUtil bi = new BiUtil();
		
		bi.chamaGrafico();
	}

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
					    "order by quantidade desc ";
	         SQLQuery query = sessao.createSQLQuery(sql);
	         query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
	         List data = query.list();

	         for(Object object : data)  {
	            Map row = (Map)object;
	            System.out.print("Nome: " + row.get("produto")); 
	            System.out.println(", Quantidade Vendida: " + row.get("quantidade")); 
	            
	            pieModel.set(row.get("produto").toString(), (Number) row.get("quantidade"));
	            
	            i++; 
	         }
	         tx.commit();
	        
	         System.out.println(i);
	         
	        pieModel.setTitle("Grafico de Venda por Cliente");
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

}
