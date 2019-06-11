package com.elias.vendas.bean;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.elias.vendas.dao.UsuarioDAO;
import com.elias.vendas.domain.Pessoa;
import com.elias.vendas.domain.Usuario;
import com.elias.vendas.util.HibernateUtil;


@ManagedBean
@SessionScoped
public class AutenticacaoBean {
	private Usuario usuario;
	private Usuario usuarioLogado;
	private String usuarioLogadoNome;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
	
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	public String getUsuarioLogadoNome() {
		return usuarioLogadoNome;
	}

	public void setUsuarioLogadoNome(String usuarioLogadoNome) {
		this.usuarioLogadoNome = usuarioLogadoNome;
	}

	@PostConstruct
	public void iniciar() {
		usuario = new Usuario();
		usuario.setPessoa(new Pessoa());
		
//		if(usuario.equals(null)) {
//			
//			usuarioLogadoNome = usuarioLogado.getPessoa().getNome();
//			
//		}
	}

	public void autenticar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioLogado = usuarioDAO.autenticar(usuario.getPessoa().getCpf(), usuario.getSenha());
			
			if(usuarioLogado == null){
				Messages.addGlobalError("CPF e/ou senha incorretos");
				return;
			}

			//exibeUsuarioAtual();
			Faces.redirect("./pages/inicio.xhtml");
			usuarioLogadoNome = usuarioLogado.getPessoa().getNome();
			
		} catch (IOException erro) {
			erro.printStackTrace();
			Messages.addGlobalError(erro.getMessage());
		}
	}
	
	public void sair(){	

		
		try {
			//System.out.println("entrou");
			usuario = new Usuario();
			usuario.setPessoa(new Pessoa());
			
			usuarioLogado = usuario;
			Faces.setSessionAttribute("autenticacaoBean", null);
			Faces.redirect("./pages/autenticacao.xhtml");

		} catch (IOException erro) {
			erro.printStackTrace();
			Messages.addGlobalError(erro.getMessage());
		}
	}
	
	public void exibeUsuarioAtual() {
		
		usuarioLogadoNome = usuarioLogado.getPessoa().getNome();
	}

}
