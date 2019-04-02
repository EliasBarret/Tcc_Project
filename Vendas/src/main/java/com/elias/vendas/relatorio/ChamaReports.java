package com.elias.vendas.relatorio;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Hibernate;
import org.omnifaces.util.Faces;

import com.elias.vendas.util.HibernateUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ChamaReports {

	public void relatorio() {
		try {
			String caminho = Faces.getRealPath("/relatorio/r_comp_venda.jasper");
			Map<String, Object> parametros = new HashMap<String, Object>();

			parametros.put("codVenda", 2);

			Connection conexao = HibernateUtil.getConexao();
			JasperPrint print = JasperFillManager.fillReport(caminho, parametros, conexao);
			JasperViewer.viewReport(print, false);

		} catch (JRException e) {
			e.printStackTrace();
		}

	}
}
