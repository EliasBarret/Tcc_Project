package com.elias.vendas.relatorio;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.elias.vendas.util.HibernateUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.view.JasperViewer;

public class ChamaReports {

	public void relatorio() {
		
		Session sessao  = HibernateUtil.getFabricaDeSessoes().openSession();
		
		
			
			sessao.doWork(new Work(){
				public void execute(Connection conexao) {
				
				try {
					String caminho = Faces.getRealPath("/relatorio/r_comp_venda.jasper");
					Map<String, Object> parametros = new HashMap<String, Object>();
					
					parametros.put("codVenda", 2);
					
					//Connection conexao = HibernateUtil.getConexao();
					//Connection conexao = HibernateUtil.getFabricaDeSessoes();
					
					JasperPrint print = JasperFillManager.fillReport(caminho, parametros, conexao);
					//JasperPrintManager.printReport(print, true);
					//JasperViewer.viewReport(print, false);
					//JasperExportManager.exportReportToPdfFile(print,“relatorio/RelatorioUser.pdf”);
					JasperExportManager.exportReportToPdfFile(print,"/relatorio/Relatorio.pdf");
				}catch (Exception e) {
					Messages.addGlobalError("Erro ao Gerar relatório");
					e.printStackTrace();
					// TODO: handle exception
				}
			}
		});
		
}}