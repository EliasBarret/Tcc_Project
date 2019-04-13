package com.elias.vendas.relatorio;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.jdbc.ReturningWork;
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

	@SuppressWarnings("deprecation")
	public void relatorio(int id) {
		try {
			
			
		     //Users/elias/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/Vendas/reports/r_comp_venda.jasper
			String caminho = Faces.getRealPath("/reports/r_comp_venda.jasper");
			
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("CODVENDA", id);
			
			Connection conexao = HibernateUtil.getConnection();

			JasperPrint print = JasperFillManager.fillReport(caminho, parametros, conexao);
			JasperViewer view = new JasperViewer(print, false);
			view.show();

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Gerar relatório");
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}