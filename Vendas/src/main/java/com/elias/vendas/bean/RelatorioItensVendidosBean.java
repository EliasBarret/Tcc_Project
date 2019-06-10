package com.elias.vendas.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


import org.omnifaces.util.Messages;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;

import com.elias.vendas.dao.ItemVendaDAO;
import com.elias.vendas.dao.VendaDAO;
import com.elias.vendas.domain.GraficoItemVenda;
import com.elias.vendas.domain.ItemVenda;
import com.elias.vendas.domain.Venda;





@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RelatorioItensVendidosBean implements Serializable{

	private List<ItemVenda> itens;
	private Date dataInicio = new Date(System.currentTimeMillis());
	private Date dataFim  = new Date(System.currentTimeMillis());
	private PieChartModel pieModel;


	

	public PieChartModel getPieModel() {
		return pieModel;
	}


	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}


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




	public List<ItemVenda> getItens() {
		return itens;
	}


	public void setItens(List<ItemVenda> itens) {
		this.itens = itens;
	}


	//preenche uma lista com registro de estados
	@PostConstruct // essa anotation diz que o metodo tem que disparar no momento em que a tela é criada 
	public void listar(){
	
		try{
			ItemVendaDAO itemVendaDAO =  new ItemVendaDAO();
			Calendar dataAtual = Calendar.getInstance();
			dataAtual.add(Calendar.DAY_OF_MONTH, -1);
			itens = itemVendaDAO.listarPorData(dataAtual.getTime(), dataFim);
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os estados");
			erro.printStackTrace();
		}
		
	}
	
	
	public void listarPorData(){

		try{
			ItemVendaDAO itemVendaDAO =  new ItemVendaDAO();
			itens = itemVendaDAO.listarPorData(dataInicio, dataFim);
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os estados");
			erro.printStackTrace();
		}
		
	}
	
	public void listarGrafico() {
		ItemVendaDAO dao;
		List<ItemVenda> lista;
		try {
			dao = new ItemVendaDAO();
			lista  = dao.listar();
			graficarItens(lista);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void graficarItens(List<ItemVenda> lista) {
		pieModel = new PieChartModel();
		
		for (ItemVenda item: lista) {
			pieModel.set(item.getProduto().toString(), item.getQuantidade());
		}
		
		pieModel.setTitle("Grafico de produtos Mais Vendidos");
		pieModel.setLegendPosition("e");
		pieModel.setFill(false);
		pieModel.setShowDataLabels(true);
		pieModel.setDiameter(150);
		
	}	
}