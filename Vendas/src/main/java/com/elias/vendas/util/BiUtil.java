package com.elias.vendas.util;

import com.elias.vendas.bean.RelatorioItensVendidosBean;
import com.elias.vendas.bean.RelatorioVendasBean;

public class BiUtil {
	
	RelatorioItensVendidosBean itens = new RelatorioItensVendidosBean();
	RelatorioVendasBean vendas = new RelatorioVendasBean();
	
	public void chamaGrafico() {
		vendas.listarGrafico();
		vendas.listarGrafico();	
	}

}
