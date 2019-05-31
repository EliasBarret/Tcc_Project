package com.elias.vendas.relatorio;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.elias.vendas.util.HibernateUtil;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ChamaReports {

	@SuppressWarnings("deprecation")
	public void relatorioVenda() {
		try {

			Map<String, Object> parametros = new HashMap<>();
			String caminho = Faces.getRealPath("/reports/r_geral_vendas.jasper");

			parametros.put("CODVENDA", 1);

			Connection conexao = HibernateUtil.getConnection();

			JasperPrint print = JasperFillManager.fillReport(caminho, parametros, conexao);
			JasperViewer view = new JasperViewer(print, false);
			view.show();
		} catch (Exception e) {
			e.printStackTrace();
			Messages.addGlobalError("Erro ao Gerar relatório");
		}

	}

	@SuppressWarnings("deprecation")
	public void relatorioVenda(int id) {
		try {

			String caminho = Faces.getRealPath("/reports/r_comp_venda.jasper");
			
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("CODVENDA", id);

			Connection conexao = HibernateUtil.getConnection();

			JasperPrint print = JasperFillManager.fillReport(caminho, parametros, conexao);
			JasperViewer view = new JasperViewer(print, false);
			view.show();

		} catch (Exception e) {
			e.printStackTrace();
			Messages.addGlobalError("Erro ao Gerar relatório");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void relatorioVenda(String tipo) {

		try {

			String caminho = Faces.getRealPath("/reports/r_geral_tipo_venda.jasper");
			
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("TIPOVENDA", tipo);

			Connection conexao = HibernateUtil.getConnection();

			JasperPrint print = JasperFillManager.fillReport(caminho, parametros, conexao);
			JasperViewer view = new JasperViewer(print, false);
			view.show();

		} catch (Exception e) {
			e.printStackTrace();
			Messages.addGlobalError("Erro ao Gerar relatório");
		}

	}
}