package it.polito.tdp.PremierLeague.model;

public class Adiacenza implements Comparable<Adiacenza>{
	Match m1;
	Match m2;
	int peso;
	public Adiacenza(Match m1, Match m2, int peso) {
		super();
		this.m1 = m1;
		this.m2 = m2;
		this.peso = peso;
	}
	public Match getM1() {
		return m1;
	}
	public void setM1(Match m1) {
		this.m1 = m1;
	}
	public Match getM2() {
		return m2;
	}
	public void setM2(Match m2) {
		this.m2 = m2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Adiacenza arg0) {
		// TODO Auto-generated method stub
		if (this.peso>arg0.peso)
			return -1;
		if (this.peso<arg0.peso)
			return 1;
		return 0;
	}
	@Override
	public String toString() {
		return "["+m1.getMatchID()+"]"+m1.getTeamHomeNAME()+"-"+m1.getTeamAwayNAME()+"vs."+"["+m2.getMatchID()+"]"+m2.getTeamHomeNAME()+"-"+m2.getTeamAwayNAME()+"("+peso+")";
	}
	
	
	

}
