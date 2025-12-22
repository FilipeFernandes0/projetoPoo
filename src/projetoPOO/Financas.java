package projetoPOO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Financas implements Serializable {
	private  ArrayList<Receita> listaReceitas;
	
	public Financas() {
		this.listaReceitas=new ArrayList<>();
	}
	
	//Adicionar uma receita
	public void adicionarReceita(Receita r) {
		listaReceitas.add(r);
		System.out.println("Receita adicionada: " + r.getDescricao());
	}
	
	public ArrayList<Receita> getListaReceitas() {
		return listaReceitas;
	}
	
	//Remover uma receita
	public void eliminarReceita(Receita r) {
		if(listaReceitas.remove(r)) {
			System.out.println("Receita removida: " + r.getDescricao());
		}
	}
	
	//Atualizar uma receita (mudar o que lá está)
	public void atualizarReceita(Receita antiga, Receita nova) {
		int posicao = listaReceitas.indexOf(antiga);
		if(posicao>=0) {
			listaReceitas.set(posicao, nova);
			System.out.println("Receita atualizada de (" + antiga.getDescricao() + ") para (" + nova.getDescricao() + ")");
		}
	}
	
	//Guardar
	public void guardarDados(String nomeFicheiro) {
		try {
			FileOutputStream fileOut = new FileOutputStream(nomeFicheiro);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			System.out.println("Dados guardados em " + nomeFicheiro);
		}catch(IOException i) {
			System.out.println("ERRO (dados não guardados) :" + i.getMessage());
		}
	}
	
	//Carregar o que foi guardado
	public static Financas carregarDados(String nomeFicheiro) {
		try {
			FileInputStream fileIn = new FileInputStream(nomeFicheiro);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Financas g = (Financas) in.readObject();
			in.close();
			fileIn.close();
			return g;
		}catch (IOException | ClassNotFoundException e) {
			System.out.println("Erro (Ficheiro não encontrado)");
			return new Financas();
		}
	}
}