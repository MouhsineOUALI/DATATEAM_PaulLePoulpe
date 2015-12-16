package Entity;

public class TeamVar {
	private String equipe;
	private float probaWinAway;
	private float probaWinHome;
	private int fiveLastMatchB;
	private float moyenAge;
    private int moyenClassement;
	private float forceAttaqueH;
	private float forceAttaqueA;
	private float forceDeffH;
	private float forceDeffA;
	private int year;
	private int resultat;
	
	public String getEquipe() {
		return equipe;
	}
	public void setEquipe(String equipe) {
		this.equipe = equipe;
	}
	
	public int getMoyenClassement() {
		return moyenClassement;
	}
	public void setMoyenClassement(int moyenClassement) {
		this.moyenClassement = moyenClassement;
	}
	public float getProbaWinAway() {
		return probaWinAway;
	}
	public void setProbaWinAway(float probaWinAway) {
		this.probaWinAway = probaWinAway;
	}
	public float getProbaWinHome() {
		return probaWinHome;
	}
	public void setProbaWinHome(float probaWinHome) {
		this.probaWinHome = probaWinHome;
	}
	
	public float getMoyenAge() {
		return moyenAge;
	}
	public void setMoyenAge(float moyenAge) {
		this.moyenAge = moyenAge;
	}
	
	public float getForceAttaqueH() {
		return forceAttaqueH;
	}
	public void setForceAttaqueH(float forceAttaqueH) {
		this.forceAttaqueH = forceAttaqueH;
	}
	public float getForceAttaqueA() {
		return forceAttaqueA;
	}
	public void setForceAttaqueA(float forceAttaqueA) {
		this.forceAttaqueA = forceAttaqueA;
	}
	public float getForceDeffH() {
		return forceDeffH;
	}
	public void setForceDeffH(float forceDeffH) {
		this.forceDeffH = forceDeffH;
	}
	public float getForceDeffA() {
		return forceDeffA;
	}
	public void setForceDeffA(float forceDeffA) {
		this.forceDeffA = forceDeffA;
	}
	public int getFiveLastMatchB() {
		return fiveLastMatchB;
	}
	public void setFiveLastMatchB(int fiveLastMatchB) {
		this.fiveLastMatchB = fiveLastMatchB;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getResultat() {
		return resultat;
	}
	public void setResultat(int resultat) {
		this.resultat = resultat;
	}
	@Override
	public String toString() {
		return "TeamVar [equipe=" + equipe + ", probaWinAway=" + probaWinAway + ", probaWinHome=" + probaWinHome
				+ ", fiveLastMatchB=" + fiveLastMatchB + ", moyenAge=" + moyenAge + ", moyenClassement="
				+ moyenClassement + ", forceAttaqueH=" + forceAttaqueH + ", forceAttaqueA=" + forceAttaqueA
				+ ", forceDeffH=" + forceDeffH + ", forceDeffA=" + forceDeffA + ", year=" + year + ", resultat="
				+ resultat + "]";
	}
	
	
	
}
