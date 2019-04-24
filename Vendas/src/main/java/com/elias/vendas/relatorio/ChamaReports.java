package com.elias.vendas.relatorio;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.stream.FileImageInputStream;
import javax.swing.ImageIcon;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.elias.vendas.util.HibernateUtil;
import com.itextpdf.text.pdf.codec.Base64.InputStream;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ChamaReports {

	@SuppressWarnings("deprecation")
	public void relatorioVenda() {
		try {

			Map<String, Object> parametros = new HashMap<>();
			//Image logo = new ImageIcon(getClass().getResource("/resources/images/sale.png")).getImage();
			String caminho = Faces.getRealPath("/reports/r_geral_vendas.jasper");


			parametros.put("CODVENDA", 1);
			//parametros.put("LOGO", caminhoImagemBrasao);

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

			// Users/elias/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/Vendas/reports/r_comp_venda.jasper
			String caminho = Faces.getRealPath("/reports/r_comp_venda.jasper");
		//	Image logo = new ImageIcon(getClass().getResource("/resources/images/sale.png")).getImage();
			
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("CODVENDA", id);
		//	parametros.put("LOGO", logo);

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

			// Users/elias/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/Vendas/reports/r_comp_venda.jasper
			String caminho = Faces.getRealPath("/reports/r_geral_tipo_venda.jasper");
		//	Image logo = new ImageIcon(getClass().getResource("/resources/images/sale.png")).getImage();
			
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("TIPOVENDA", tipo);
			//parametros.put("LOGO", logo);

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