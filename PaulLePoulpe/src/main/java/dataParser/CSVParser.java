package dataParser;

import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class CSVParser {
	
	static Statement stmt = null;
	
	private final static char SEPARATOR = ',';
	static List<String[]> data = new ArrayList<String[]>();
	static int id =0;
	public static void main(String[] args) throws SQLException, Exception {
		
		 stmt = ExtractPlayerData.getConnection().createStatement();
		 
		 
		    String sql2 = "SELECT MAX(ID) FROM result2015";
			ResultSet resultat = stmt.executeQuery(sql2); 
			
			while(resultat.next()){ 
				id = resultat.getInt(1);
				}
		 
		        File file = new File("DataFile/E2014.csv");
		        FileReader fr = new FileReader(file);

		        CSVReader csvReader = new CSVReader(fr, SEPARATOR);
		        String[] nextLine = null;
		        int i = 0;
		        String sql = "";
		        while ((nextLine = csvReader.readNext()) != null) {
		        	 if(i>0){
		        	 String [] oneData = nextLine;  
		        	 String nom = oneData[1];
		        	 String homeTeam = oneData[2];
		        	 System.out.println(" home team "+homeTeam);
		        	 String awayTeam = oneData[3];
		        	 System.out.println(" away team "+awayTeam);
		        	 String score = oneData[4]+"-"+oneData[5];
		        	 System.out.println(" score "+score);
		        	 
		        	 sql = "INSERT INTO result2015"
								+ "(ID, DATE, H_TEAM, A_TEAM, SCORE)"
								+ "VALUES"
								+ "("+id+",'"+"2014"+
								"','"+homeTeam+"','"+awayTeam+"','"
										+ ""+score+
										"')";
		        	 stmt.executeUpdate(sql);
		        	 }
		        	 i++;
		        	 id++;
		        }
		        
		       
		        
		        

	}

}
