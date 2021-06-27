package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	private PremierLeagueDAO dao;
	private Graph<Match, DefaultWeightedEdge> grafo;
	private Map<Integer, Match> idMap;
	int max;
	List<Match> collegamento;
	
	public Model () {
		dao=new PremierLeagueDAO();
		idMap=new HashMap<>();	
	}
	
	public void creaGrafo (int mese, int minuti) {
		grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		dao.listAllMatches(idMap);
		Graphs.addAllVertices(grafo, dao.getVertici(idMap, mese));
		for (Adiacenza a:dao.getArchi(idMap, minuti)) {
			if (grafo.vertexSet().contains(a.getM1()) && grafo.vertexSet().contains(a.getM2()))
					Graphs.addEdgeWithVertices(grafo, a.getM1(), a.getM2(), a.getPeso());
		}
		
	}
	
	public int getNumeroVertici () {
		return grafo.vertexSet().size();
	}
	
	public int getNumeroArchi () {
		return grafo.edgeSet().size();
	}
	
	public List<Adiacenza> getBestCouple () {
		List<Adiacenza> couple=new ArrayList<>();
		for (DefaultWeightedEdge e:grafo.edgeSet()) {
			couple.add(new Adiacenza(grafo.getEdgeSource(e), grafo.getEdgeTarget(e), (int) grafo.getEdgeWeight(e)));
		}
		Collections.sort(couple);
		List<Adiacenza> result=new ArrayList<>();
		int max=couple.get(0).getPeso();
		for (Adiacenza a:couple) {
			if(a.getPeso()==max) {
				result.add(a);
			}
		}
		
		return result;
	}
	
	public Set<Match> getMatch () {
		return grafo.vertexSet();
	}
	
	public void collegamento(Match m1, Match m2) {
		max=0;
		int peso=0;
		List<Match> parziale=new ArrayList<>();
		parziale.add(m1);
		calcola(peso, parziale, m2);
	}
	
	private void calcola (int peso, List<Match> parziale, Match destinazione) {
		if (parziale.get(parziale.size()-1).equals(destinazione)) {
			if (peso>max) {
				max=peso;
				collegamento=new ArrayList<>(parziale);
			}
			return ;
		} else {
			for (Match m:Graphs.successorListOf(grafo, parziale.get(parziale.size()-1))) {
				if(!parziale.contains(m)) {
					DefaultWeightedEdge e=grafo.getEdge(parziale.get(parziale.size()-1), m);
					int peso1=peso+(int) grafo.getEdgeWeight(e);
					List<Match> parziale1=new ArrayList<>(parziale);
					parziale1.add(m);
					calcola(peso1, parziale1, destinazione);
				}
			}
		}
	}

	public int getMax() {
		return max;
	}

	public List<Match> getCollegamento() {
		return collegamento;
	}
	
}
