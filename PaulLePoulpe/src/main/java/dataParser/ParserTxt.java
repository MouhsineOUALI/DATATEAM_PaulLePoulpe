package dataParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ParserTxt {
	
	static Statement stmt = null;
	static Statement stmt2 = null;
	static int id = 0;
	
	public static void main(String[] args) throws SQLException, Exception {
		String sql2 = null;
		stmt = ExtractPlayerData.getConnection().createStatement();
		
		sql2 = "SELECT MAX(ID) FROM result2015";
		ResultSet resultat = stmt2.executeQuery(sql2); 
		
		while(resultat.next()){ 
			id = resultat.getInt(1);
			}
		id=1;
		String sql = null;
		for (int i = 1993; i <2013 ; i++) {
			
			
		
		FileReader input = new FileReader("DataFile/premierleague"+i+".txt");
		BufferedReader bufRead = new BufferedReader(input);
		String myLine = null;

		while ( (myLine = bufRead.readLine()) != null)
		{    
			
			if (myLine.contains("15.00")) {
				String[] array2 = myLine.split("	");
				
                id++;
				String date = i+"";
				String h_team =(String) array2[3];
				String a_team =(String)array2[5];
				String score =(String) array2[4];
				
				h_team = h_team.replace('\'', ' ');
				a_team = a_team.replace('\'', ' ');
					
				
				sql = "INSERT INTO result2015"
						+ "(ID, DATE, H_TEAM, A_TEAM, SCORE)"
						+ "VALUES"
						+ "("+id+",'"+i+
						"','"+h_team+"','"+a_team+"','"
								+ ""+score+
								"')";
				System.out.println(sql);
				//stmt.executeUpdate(sql);
			}
		    
	}
		}
}
}