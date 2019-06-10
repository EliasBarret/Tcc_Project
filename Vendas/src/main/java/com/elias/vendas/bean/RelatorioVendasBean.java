package com.elias.vendas.bean;



import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Messages;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

import com.elias.vendas.dao.VendaDAO;
import com.elias.vendas.domain.Venda;
import com.elias.vendas.relatorio.ChamaReports;
import com.elias.vendas.util.BiUtil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RelatorioVendasBean implements Serializable{

	private List<Venda> vendas;
	HorizontalBarChartModel model;
	private BarChartModel barra;
	private PieChartModel pieModel;
	private LineChartModel lineModel;
	private Date dataInicio = new Date(System.currentTimeMillis());
	private Date dataFim  = new Date(System.currentTimeMillis());


	

	public Date getDataInicio() {
		return dataInicio;
	}


	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}


	public Date getDataFim() {
		return dataFim;
	}


	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}


	public HorizontalBarChartModel getModel() {
		return model;
	}


	public void setModel(HorizontalBarChartModel model) {
		this.model = model;
	}

	public List<Venda> getVendas() {
		return vendas;
	}


	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}
	
	public PieChartModel getPieModel() {
		return pieModel;
	}
	
	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}
	public BarChartModel getBarra() {
		return barra;
	}


	public void setBarra(BarChartModel barra) {
		this.barra = barra;
	}
	public LineChartModel getLineModel() {
		return lineModel;
	}


	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}

	//preenche uma lista com registro de estados
	@PostConstruct // essa anotation diz que o metodo tem que disparar no momento em que a tela é criada 
	public void listar(){
		model = new HorizontalBarChartModel();
		try{
			VendaDAO vendaDAO =  new VendaDAO();
			Calendar dataAtual = Calendar.getInstance();
			dataAtual.add(Calendar.DAY_OF_MONTH, -1);
			
			vendas = vendaDAO.listarPorData(dataAtual.getTime(), dataFim);
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as vendas");
			erro.printStackTrace();
		}
	}
	
	public void listarPorData(){
		model = new HorizontalBarChartModel();
		try{
	
			VendaDAO vendaDAO =  new VendaDAO();
			
			vendas= null;
			vendas = vendaDAO.listarPorData(dataInicio, dataFim);
		
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os estados");
			erro.printStackTrace();
		}
	
	}

	public void relatorioVenda(){
		try {
			ChamaReports instance = new ChamaReports();
			String test = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
			
			if (test == null) {
				instance.relatorioVenda();
			}else {
				int codigo = Integer.parseInt(test);
				instance.relatorioVenda(codigo);
			}	
			
		}catch (RuntimeException erro){
			Messages.addGlobalError("Ocorreu um erro ao gerar relatório");
			erro.printStackTrace();
		}
	}
	
	public void listarGrafico() {
		VendaDAO dao;
		List<Venda> lista;
		try {
			dao = new VendaDAO();
			lista  = dao.listar();
			graficar(lista);
			graficoLinha(lista);
			
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




}