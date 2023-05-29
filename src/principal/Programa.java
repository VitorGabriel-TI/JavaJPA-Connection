package principal;

import java.util.List;

import jakarta.persistence.*;
import principal.modelos.Pessoa;

public class Programa {
	
	static EntityManagerFactory emf;
	static EntityManager em;

	public static void main(String[] args) {
		
		//Configuração do JPA
		emf = Persistence.createEntityManagerFactory("ex_mysql");
		em = emf.createEntityManager();
		
		
		//Criação do objeto pessoa
		Pessoa p = new Pessoa("Ana", "55555");		
		salvar(p);
		
		//Buscar pessoa pelo ID
		Pessoa p2 = buscarPorId(2);
		
		System.out.println();
		System.out.println("---------------");
		
		System.out.println("Id: " + p2.getId() 
		        + " \nNome: " + p2.getNome() 
		        + " \nCPF: " + p2.getCpf());
		
		System.out.println("---------------");
		System.out.println();
		
		System.out.println("LISTA DE PESSOAS");
		//Listar pessoas no banco
		List<Pessoa> lista = listar();
		for (Pessoa pessoa : lista) {
			System.out.println("Id: " + pessoa.getId() 
			                 + " Nome: " + pessoa.getNome() 
			                 + " CPF: " + pessoa.getCpf());
		}
		
		System.out.println("---------------");
		System.out.println();
		
		//Atualizar os dados de uma pessoa
		p2.setNome("João Paulo");
		atualizar(p2);
		
		
		//Excluir pessoa do banco de dados
		apagar(2);
		
		//Encerrar a conexão
		em.close();
		emf.close();	
	}
	
	public static Integer salvar(Pessoa pessoa) {
		//Gravação da pessoa no banco de dados
		em.getTransaction().begin();
		em.persist(pessoa);
		em.getTransaction().commit();
		return pessoa.getId();
	}
	
	public static Pessoa buscarPorId(Integer id) {
		Pessoa pessoa = em.find(Pessoa.class, id);
		return pessoa;
	}
	
	public static List<Pessoa> listar(){
		List<Pessoa> lista = em.createQuery("from Pessoa", Pessoa.class)
				               .getResultList();
		return lista;
	}
	
	public static Integer atualizar(Pessoa pessoa) {
		//Atualização da pessoa no banco de dados
		em.getTransaction().begin();
		em.merge(pessoa);
		em.getTransaction().commit();
		return pessoa.getId();
	}
	
	public static void apagar(Integer id) {
		Pessoa p = em.find(Pessoa.class, id);
		em.getTransaction().begin();
		em.remove(p);
		em.getTransaction().commit();
	}
	
	
}
