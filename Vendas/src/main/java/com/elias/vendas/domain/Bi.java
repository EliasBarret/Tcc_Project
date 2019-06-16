package com.elias.vendas.domain;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.elias.vendas.util.HibernateUtil;

public class Bi{
	
	private LineChartModel dateModelFucionario;
	private List<Funcionario> funcionarios;
	private String funcionario;
	
	public LineChartModel getDateModelFucionario() {
		return dateModelFucionario;
	}

	public void setDateModelFucionario(LineChartModel dateModelFucionario) {
		this.dateModelFucionario = dateModelFucionario;
	}
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}
	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	public String getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}
	
	@PostConstruct
	public void listarGrafico() {
		graficoLinhaDatass();
	}
	
	public void graficoLinhaDatass() {	
		
	String codigo = getFuncionario();	
	
		if(codigo.isEmpty() || codigo.equals(null)) {
			return;
		}else {
			codigo = codigo.substring(19, 20);
			System.out.println(codigo);
		}
	
	dateModelFucionario = new LineChartModel();
    LineChartSeries series1 = new LineChartSeries();
	
	Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
	Transaction tx = null;
      try{
         tx = sessao.beginTransaction();
         String sql = "	SELECT  venda.horario as datas, venda.valorTotal as valor,  "+
			          "			item.funcionario_codigo as funcionario   "+
			          "			  from banco.itemvenda item, 			 "+ 
			          "				   banco.produto prod, 				 "+ 
			          "				   banco.Funcionario func, 			 "+
			          "				   banco.Pessoa p, 					 "+
			          "			       banco.venda 						 "+
			          "			where  prod.codigo = item.produto_codigo "+
			          "			  and  item.venda_codigo = venda.codigo  "+
			          "			  and  func.codigo = item.funcionario_codigo "+ 
			          "			  and  p.codigo = func.codigo 			 "+
			          "			  and  func.codigo = "+codigo+";";
         SQLQuery query = sessao.createSQLQuery(sql);
         query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
         List data = query.list();

         for(Object object : data)  {
            Map row = (Map)object;
            
            series1.setLabel("Evolução Venda");
			series1.set(row.get("datas"), (Number) row.get("valor"));
            
         }
         tx.commit();
        
        dateModelFucionario.addSeries(series1);
        dateModelFucionario.setZoom(true);
        dateModelFucionario.setTitle("Gráfico de Produtividade");
        dateModelFucionario.setLegendPosition("e");
		DateAxis axis = new DateAxis("Data da Venda");
        axis.setTickAngle(-50);
        axis.setTickFormat("%b %#d, %y");
        
        dateModelFucionario.getAxes().put(AxisType.X, axis);
       
 		
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			sessao.close();
		}
	}
}
