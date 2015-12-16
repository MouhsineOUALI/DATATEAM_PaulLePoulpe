package dataParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonParseTest {

	static Statement stmt = null;
	static Statement stmt2 = null;
	static int id=1;
	
	static String [] fileNames = {"epl-92-93.json","epl-93-94.json","epl-94-95.json","epl-95-96.json"
			,"epl-96-97.json","epl-97-98.json","epl-98-99.json","epl-99-00.json"
			,"epl-00-01.json","epl-01-02.json","epl-02-03.json","epl-03-04.json"
			,"epl-04-05.json","epl-05-06.json","epl-06-07.json","epl-07-08.json"
			,"epl-08-09.json","epl-09-10.json","epl-10-11.json","epl-11-12.json"
			,"epl-12-13.json","epl-13-14.json","epl-14-15.json"};
	
	public static void main(String[] args) throws SQLException, Exception {

		  stmt = ExtractPlayerData.getConnection().createStatement();
		try {
			// read the json file
			for (int j = 0; j < fileNames.length; j++) {
				
				FileReader reader = new FileReader("DataFile/"+fileNames[j]);
				JSONParser jsonParser = new JSONParser();
				JSONArray jsonObject = (JSONArray) jsonParser.parse(reader);
				String sql;
				
				String[] aS = fileNames[j].split("-");
				
			
			for (int i = 0; i < jsonObject.size(); i++) {
				
				JSONObject a = (JSONObject) jsonObject.get(i);
				Long rank =(Long) a.get("rank");
				String team =(String) a.get("team");
				Long played =(Long) a.get("played");
				Long wins =(Long) a.get("wins");
				Long draws =(Long) a.get("draws");
				Long losses =(Long) a.get("losses");
				Long goals_for =(Long) a.get("goals-for");
				Long goals_against =(Long) a.get("goals-against");
				String goals_dff =(String) a.get("goals-dff").toString();
				String points =(String) a.get("points").toString();
				
				String [] x = aS[2].split(".");
				sql = "INSERT INTO classement"
						+ "(ID, ANNEE, RANK, TEAM, PLAYED, WINS, DRAWS, LOSSES, GOALSFOR, GOALSAGAINST, GOALSDFF, POINTS)"
						+ "VALUES"
						+ "("+id+",'"+aS[1]+aS[2].charAt(0)+aS[2].charAt(1)+
						"','"+rank+"','"+team+"','"
								+ ""+played+
								"','"+wins+"','"+draws+
								"','"+losses+"','"+goals_for+"','"+goals_against+"','"+goals_dff+"','"+points+"')";
				System.out.println(sql);
				//stmt.executeUpdate(sql);
				id++;
			}
			}
			stmt.close();
			
			/**************************************************/
			id = 1;
			FileReader reader = new FileReader("DataFile/2015-results.json");
			JSONParser jsonParser2 = new JSONParser();
			JSONArray jsonObject2 = (JSONArray) jsonParser2.parse(reader);
			String sql2 = null;
			int aa = 7594;
            for (int i = 0; i < jsonObject2.size(); i++) {
				
				JSONObject a = (JSONObject) jsonObject2.get(i);
				
				String date =(String) a.get("date");
				String h_team =(String) a.get("home");
				String a_team =(String) a.get("away");
				String score =(String) a.get("score");
				
				sql2 = "INSERT INTO result2015"
						+ "(ID, DATE, H_TEAM, A_TEAM, SCORE)"
						+ "VALUES"
						+ "("+aa+",'"+"2015"+
						"','"+h_team+"','"+a_team+"','"
								+ ""+score+
								"')";
				aa++;
				
				System.out.println(sql2);
				stmt2.executeUpdate(sql2);
				
			}


		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

	}

}