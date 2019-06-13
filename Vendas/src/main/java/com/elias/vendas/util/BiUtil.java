package com.elias.vendas.util;

import java.util.List;

import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.elias.vendas.bean.BiBean;
import com.elias.vendas.dao.VendaDAO;
import com.elias.vendas.domain.Venda;

public class BiUtil extends BiBean{

	private LineChartModel lineModel;

	public void listarGrafico() {
		VendaDAO dao;
		List<Venda> lista;
		try {
			dao = new VendaDAO();
			lista  = dao.listar();
			
			graficoLinha(lista);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
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
}
