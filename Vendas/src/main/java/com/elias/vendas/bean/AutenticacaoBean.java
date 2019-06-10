package com.elias.vendas.bean;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.elias.vendas.dao.UsuarioDAO;
import com.elias.vendas.domain.Pessoa;
import com.elias.vendas.domain.Usuario;


@ManagedBean
@SessionScoped
public class AutenticacaoBean {
	private Usuario usuario;
	private Usuario usuarioLogado;

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

	@PostConstruct
	public void iniciar() {
		usuario = new Usuario();
		usuario.setPessoa(new Pessoa());
		//exibeUsuarioAtual();
		//String usuariologadofront = usuarioLogado.getPessoa().toString();
	}

	public void autenticar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioLogado = usuarioDAO.autenticar(usuario.getPessoa().getCpf(), usuario.getSenha());
			
			if(usuarioLogado == null){
				Messages.addGlobalError("CPF e/ou senha incorretos");
				return;
			}
			
			
//			Messages.addGlobalError("Seja Bem Vindo: " + getUsuarioLogado().getPessoa().getNome());
//			Messages.addFlashGlobalError("Seja Bem Vindo: " + getUsuarioLogado().getPessoa().getNome());
			Faces.redirect("./pages/inicio.xhtml");
			
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
		String usuarioExibe = null;
		try {
			
//			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
//			usuarioExibe = autenticacaoBean.getUsuarioLogado().toString();
			
			Messages.addGlobalInfo("parcelas referentes a esta venda, foram excluidas!");
			
		}catch (Exception e) {
			e.printStackTrace();
			Messages.addGlobalError(e.getMessage());
		}
	//	return usuarioExibe;
	}
}
