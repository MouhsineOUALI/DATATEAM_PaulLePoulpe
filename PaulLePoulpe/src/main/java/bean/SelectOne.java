package bean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.taglibs.standard.lang.jstl.EqualityOperator;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import Entity.TeamVar;
import algorhithmes.CalculProba;
import dao.ExtractFromDB;
import dataParser.ExtractPlayerData;
 
@ManagedBean
@ApplicationScoped
public class SelectOne implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  List<String> teams  = new ArrayList<String>();
	private List<TeamVar> teamsVar = new ArrayList<TeamVar>();
	private TeamVar selectedTeam;
	private TeamVar selectedTeam2;
	private List<Integer> twoTeam = new ArrayList<Integer>();
	private float [][] matriceLine = new float[22][49]; //19equie ==> 38 (home et away) + nbre de variables ==> 9 +1(date)
	private float [][] matriceVar = new float[22][11];
	public static  float [] resultActuel = new float[11];
	private String equipeChoisi;
	private String equipeChoisi2;
	static String resultatFinal;
	static String equipeHome;
	private float moyenAge2;
	static boolean decisionFinale = false;
	static int decision;
	static boolean isEgale = false;
	static String percent ="";
	static int size =0;
	static CalculProba calculProba = new CalculProba();
	   @PostConstruct
	   public void init() {
	   teams = new ArrayList<String>();
	   teamsVar = new ArrayList<TeamVar>();
	   selectedTeam = new TeamVar();
	   selectedTeam2 = new TeamVar();
	   teams.add("");
	   }
	   // action lors de l'action 1 (choisir le premier equipe)
	public void teamchoised(AjaxBehaviorEvent e) throws SQLException, Exception{
		   System.out.println("Home Team ::"+equipeChoisi);
		   equipeHome = equipeChoisi;
		   teamsVar.clear();
		   teams =ExtractFromDB.getTeamsAll();
		   try {
			   
			size = 2*getTeams().size();
			
			int size2=teams.size();
			for (int i = 0; i < 22; i++) {
				
			if(twoTeam.isEmpty()){
				for (int j = 0; j < size; j++) {
					twoTeam.add(0);
					matriceLine[i][j] = 0;
					}
			}
			else{
			for (int j = 0; j < size; j++) {
				if(j%2 == 0 && twoTeam.get(j) == 1 ){
					twoTeam.set(j, 0);
					matriceLine[i][j] = 0;
				}
			}
			}
			for (int j = 0; j < size2; j++) {
				if(teams.get(j).equals(equipeChoisi)){
					twoTeam.set(2*j, 1);
					matriceLine[i][2*j] = 1;
				}
			}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		   
	   }
	   public void teamchoised2(AjaxBehaviorEvent e) throws SQLException, Exception{
		   // action lors de l'action 2 (choisir le deuxieme equipe)
		   System.out.println("Away Team :"+equipeChoisi2);
		   teams = ExtractFromDB.getTeamsAll();
		   teamsVar = new ArrayList<TeamVar>();
		   float [] res = new float[3];
		   float [] resD = new float[2];
		   try {
			   for (int i = 1993; i < 2016; i++) {
				selectedTeam = new TeamVar();
			    String d = String.valueOf(i);
				float probaWinAway = calculProba.probOfWinAway(equipeChoisi,equipeChoisi2,d);
				float probaWinHome = calculProba.probOfWinHome(equipeChoisi,equipeChoisi2,d);
				int nbre = calculProba.lastFiveMatchAway(equipeChoisi,equipeChoisi2,d);
				boolean b = calculProba.isLastMatchWin(equipeChoisi);
				moyenAge2 = calculProba.ageMoyen(equipeChoisi); 
				res = calculProba.forceAttaqueHome_Home(equipeChoisi, equipeChoisi2, d);
				float forceAttaqueH = res[0];
				float forceAttaqueA = res[1];
				resD = calculProba.forceDeffHome_Home(equipeChoisi, equipeChoisi2, d);
				float forceDeffA =  resD[0];
				float forceDeffH =  resD[1];
				String part1;
				String part2;
				part2 = d.substring(2, 4);
				int k = i-1;
				String saison = String.valueOf(k);
				part1 = saison.substring(2, 4);
				int moyenClassement = calculProba.moyenClass(equipeChoisi2,part1+part2); // 1993 == > 9394
				moyenClassement = 20 -  moyenClassement;
				
				
				if(!d.equals("2015")){
				selectedTeam.setEquipe(equipeChoisi);
				selectedTeam.setProbaWinHome(probaWinHome);
				matriceVar[i-1993][0] = probaWinHome;
				selectedTeam.setProbaWinAway(probaWinAway);
				matriceVar[i-1993][1] = probaWinAway;
				selectedTeam.setFiveLastMatchB(nbre);
				matriceVar[i-1993][2] = nbre;
				selectedTeam.setMoyenAge(moyenAge2);
				matriceVar[i-1993][3] = moyenAge2;
				selectedTeam.setMoyenClassement(moyenClassement);
				matriceVar[i-1993][4] = moyenClassement;
				selectedTeam.setForceAttaqueH(forceAttaqueH);
				matriceVar[i-1993][5] = forceAttaqueH;
				selectedTeam.setForceAttaqueA(forceAttaqueA);
				matriceVar[i-1993][6] = forceAttaqueA;
				selectedTeam.setForceDeffH(forceDeffH);
				matriceVar[i-1993][7] = forceDeffH;
				selectedTeam.setForceDeffA(forceDeffA);
				matriceVar[i-1993][8] = forceDeffA;
				selectedTeam.setYear(Integer.valueOf(d));
				selectedTeam.setResultat((int)res[2]);
				matriceVar[i-1993][9] = res[2];
				matriceVar[i-1993][10] = Integer.valueOf(d);
				
				teamsVar.add(selectedTeam);
				}
				else{
					    resultActuel[0] = probaWinHome;
					    resultActuel[1] = probaWinAway;
						resultActuel[2] = nbre;
						resultActuel[3] = moyenAge2;
						resultActuel[4] = moyenClassement;
						resultActuel[5] = forceAttaqueH;
						resultActuel[6] = forceAttaqueA;
						resultActuel[7] = forceDeffH;
						resultActuel[8] = forceDeffA;
						resultActuel[9] = res[2];
						resultActuel[10] = Integer.valueOf(d);
				}
			   }
			   
				int size2=teams.size();
				
				for (int i = 0; i < 22; i++) {
					
				for (int j = 0; j < size-1; j++) {
					if(j%2 != 0 && twoTeam.get(j) == 1 ){
						twoTeam.set(j, 0);
						matriceLine[i][j]=0;
					}
				}
				for (int j = 0; j < size2/2; j++) {
					if(teams.get(j).equals(equipeChoisi2)){
						matriceLine[i][2*j+1]=1;
					}
				}
				
				for (int j = size; j < 49; j++) {
					matriceLine[i][j] = matriceVar[i][j-size];
			}
				}
				// appel au fonction chargé de prediction
				 predicate(matriceLine);
				 
				 ExtractPlayerData.getConnection().close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	   }
	   
    public static void predicate(float[][] matrice) throws Exception{
    	decision = 0;
    	//génération du fichier input
    	File predicteFile = new File("predicate.txt");
		FileWriter fwpr = new FileWriter(predicteFile.getAbsoluteFile());
		BufferedWriter bwpr = new BufferedWriter(fwpr);
		bwpr.write("@relation predicate\n");
		bwpr.write("\n");
		bwpr.write("@attribute isHome {home,away}\n");
		bwpr.write("@attribute victoireHome numeric\n");
		bwpr.write("@attribute victoireAway numeric\n");
		bwpr.write("@attribute nbreButLastMatch numeric\n");
		bwpr.write("@attribute ageMoyen numeric\n");
		bwpr.write("@attribute classement numeric\n");
		bwpr.write("@attribute forceAttaqueEquiHome numeric\n");
		bwpr.write("@attribute forceAttaqueEquiAway numeric\n");
		bwpr.write("@attribute forceDefEquiHome numeric\n");
		bwpr.write("@attribute forceDefEquiaway numeric\n");
		bwpr.write("@attribute result {gagne,perd,egalite}\n");
		bwpr.write("\n");
		bwpr.write("@data\n");
    	System.out.println("");
    	int res = 0;
    	boolean isHome = false;
		 for (int i = 0; i < 22; i++) {
		 for (int j = 0; j < 38; j++) {
				 if(j%2 == 1){
					 isHome = true;
				 }else{
				 }
		 }
		 if(isHome){
		 bwpr.write("home");
		 }
		 else{
			 bwpr.write("away");
		 }
		 bwpr.write(",");
		 for (int j = 38; j < 48; j++) {
			 if(j == 47){
				 res = (int)matrice[i][j];
				 if(res == 0){
					 bwpr.write("egalite");
				 }
				 else if(res == 1){
					 bwpr.write("gagne");
				 }
				 else{
					 bwpr.write("perd");
				 }
			 }else{
				 bwpr.write(""+matrice[i][j]+",");
			 }
		 }
		 bwpr.write("\n");
		 }
		 bwpr.close();
		  
		 // Appel au fonction de prédiction
		 resultatFinal = WekaTest.predicate("predicate.txt");
		 
		 // condition actuelle dans le tableau resultActuel[]
		 
		 System.out.println("resultat actuel à comparer avec le model:");
		 for (int i = 0; i < resultActuel.length; i++) {
			System.out.print(" "+resultActuel[i]);
		 }
		 
		 System.out.println("");
		 System.out.println("");
		 
		 System.out.println("victoireHome "+resultActuel[0]);
		 System.out.println("victoireAway "+resultActuel[1]);
		 System.out.println("nbreButLastMatch "+resultActuel[2]);
		 System.out.println("ageMoyen "+resultActuel[3]);
		 System.out.println("classement "+resultActuel[4]);
		 System.out.println("forceAttaqueEquiHome "+resultActuel[5]);
		 System.out.println("forceAttaqueEquiAway "+resultActuel[6]);
		 System.out.println("forceDefEquiaway "+resultActuel[7]);
		 System.out.println("forceDefEquiHome "+resultActuel[8]);
		 System.out.println("result XXX");
		 System.out.println("");
		 System.out.println("---------------------------");
		 System.out.println("Resultat du API : "+resultatFinal);
		 
		 String [] SplitRes = resultatFinal.split("\n");
		 
		  float AttributeG =0;
		  float AttributeP = 0;
		  float AttributeE = 0;
		  float _One = 0;
		  
			for (int i = 0; i < SplitRes.length; i++) {
				
			if (SplitRes[i].contains(":")) {
				
		    String [] r1Table = SplitRes[i].split(":");
		 
		 
		 String egalite ="";
		 String perd = "";
		 String gagne = "";
		 
		 if (r1Table [1].contains("egalite")) {
			 egalite = r1Table [1];
		 }
		 
		 
		 if (r1Table [1].contains("perd")) {
			 perd =r1Table [1];
		 }
		 
		 
		 if (r1Table [1].contains("gagne")) {
			 gagne =r1Table [1];
		 }
		 
		 
		 String [] egalTab = egalite.split("egalite ");
		 String [] perdlTab = perd.split("perd");
		 String [] gagneTab = gagne.split("gagne ");
		 
		 if (perdlTab.length>1) {
			 perd = perdlTab[1];
		 }
		 if (egalTab.length>1) {
		 egalite = egalTab[1];
		 }
		 if (gagneTab.length>1) {
		 gagne = gagneTab[1];
		 }
		 if(egalite.length()>2)
		 egalite = egalite.replace("(", "");
		 egalite = egalite.replace(")", "");
		 egalite = egalite.replace(" ", "");
		 egalite = egalite.replace("/", "");
		 perd = perd.replace("(", "");
		 perd = perd.replace(")", "");
		 perd = perd.replace(" ", "");
		 perd = perd.replace("/", "");
		 
		 gagne = gagne.replace("(", "");
		 gagne = gagne.replace(")", "");
		 gagne = gagne.replace(" ", "");
		 gagne = gagne.replace("/", "");
		 
			}
		 }
			
		//génération du fichier input de notre API
		Runtime.getRuntime().exec(new String[] {"open", "predicate.txt"});
    	File outFile = new File("matrice.txt");
		FileWriter fw = new FileWriter(outFile.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("");
    	System.out.println("");
		 for (int i = 0; i < 22; i++) {
			 bw.write("(");
		 for (int j = 0; j < 49; j++) {
			 
			 if(j<38){
				 bw.write(""+(int)matrice[i][j]);
				 bw.write(",");
			 }
			 else if(j == 48){
				 bw.write(""+(int)matrice[i][j]);
				 bw.write(",");
				 
			 }
			 else if(j == 47){
				 if(matrice[i][j] == 0){
					 AttributeE = AttributeE + 1;
				 }else if(matrice[i][j] == 1){
					 AttributeG = AttributeG+1;
				 }else if(matrice[i][j] == -1){
					 AttributeP = AttributeP +1;
				 }
				 
			 }
			 else{
				 bw.write(""+matrice[i][j]);
				 bw.write(",");
			 }
		 }
		 bw.write(")");
		 bw.write("\n");
		 }
		 _One = Math.max(AttributeG, Math.max(AttributeE, AttributeP));
			if (_One == AttributeG) {
				  decision = 1;
			}else if(_One == AttributeP){
				decision = -1;
			}else{
				 decision = 3;
			}
			_One = 0;
		 bw.close();
		 //génération du matrice
		 Runtime.getRuntime().exec(new String[] {"open", "matrice.txt"});
		 
    }
	public String getEquipeChoisi() {
		return equipeChoisi;
	}
	public void setEquipeChoisi(String equipeChoisi) {
		this.equipeChoisi = equipeChoisi;
	}
	public String getEquipeChoisi2() {
		return equipeChoisi2;
	}
	public void setEquipeChoisi2(String equipeChoisi2) {
		this.equipeChoisi2 = equipeChoisi2;
	}
	public List<String> getTeams() throws SQLException, Exception {
		return ExtractFromDB.getTeamsAll();
	}
	public void setTeams(List<String> teams) {
		this.teams = teams;
	}
	public List<TeamVar> getTeamsVar() {
		return teamsVar;
	}
	public void setTeamsVar(List<TeamVar> teamsVar) {
		this.teamsVar = teamsVar;
	}
	public TeamVar getSelectedTeam() {
		return selectedTeam;
	}
	public void setSelectedTeam(TeamVar selectedTeam) {
		this.selectedTeam = selectedTeam;
	}
	public TeamVar getSelectedTeam2() {
		return selectedTeam2;
	}
	public void setSelectedTeam2(TeamVar selectedTeam2) {
		this.selectedTeam2 = selectedTeam2;
	}
	public List<Integer> getTwoTeam() {
		return twoTeam;
	}
	public void setTwoTeam(List<Integer> twoTeam) {
		this.twoTeam = twoTeam;
	}
	public float[][] getMatriceLine() {
		return matriceLine;
	}
	public void setMatriceLine(float[][] matriceLine) {
		this.matriceLine = matriceLine;
	}
	public float[][] getMatriceVar() {
		return matriceVar;
	}
	public void setMatriceVar(float[][] matriceVar) {
		this.matriceVar = matriceVar;
	}
	public static String getEquipeHome() {
		return equipeHome;
	}
	public static void setEquipeHome(String equipeHome) {
		SelectOne.equipeHome = equipeHome;
	}
	public static boolean isEgale() {
		return isEgale;
	}
	public static void setEgale(boolean isEgale) {
		SelectOne.isEgale = isEgale;
	}
	public static boolean isDecisionFinale() {
		return decisionFinale;
	}
	public static void setDecisionFinale(boolean decisionFinale) {
		SelectOne.decisionFinale = decisionFinale;
	}
	public static int getDecision() {
		return decision;
	}
	public static void setDecision(int decision) {
		SelectOne.decision = decision;
	}
	public static String getPercent() {
		return percent;
	}
	public static void setPercent(String percent) {
		SelectOne.percent = percent;
	}
	public static String getResultatFinal() {
		return resultatFinal;
	}
	public static void setResultatFinal(String resultatFinal) {
		SelectOne.resultatFinal = resultatFinal;
	}
	
	
}