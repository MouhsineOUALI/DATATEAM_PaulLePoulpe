package dataParser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtractPlayerData {

	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/dataminingdb";
	
	static final String USER = "root";
	static final String PASS = "root";
	
	static final String [] englandTeams = {"arsenal","avilla","bourne","chelsea",
											"cpalace","everton","leicester","liverpool",
											"mancity","manutd","newcas","norwich","southam",
											"stoke","sunder","swansea","tottenha","watford",
											"wba","westham"};	
	static int id=1;
	
	public static void main(String[] args) throws SQLException, Exception {
		
		
		String urlTeam;
		for(int i=0;i<englandTeams.length;i++){
			urlTeam="http://www.footballsquads.co.uk/eng/2015-2016/faprem/"+englandTeams[i]+".htm";
			extractData(urlTeam,englandTeams[i]);
		}
	}
	
	public static void extractData(String url,String teamName) throws SQLException, Exception{
		
		Document doc;
		Statement stmt = null;
		Statement stmt2 = null;
		try {
			doc = Jsoup
					.connect(url)
					.get();
			// get all links
			Elements links = doc.select("tr");
			
			stmt = getConnection().createStatement();
			stmt2 = getConnection().createStatement();
			String sql;
			
			
			
			ResultSet rs = stmt2.executeQuery("SELECT id FROM equipe WHERE Name = '"+teamName+"'");
			int id_team = 0;
			while(rs.next()){
		         id_team  = rs.getInt("id");
			}
			
			for (Element link : links) {
				
				if( !link.child(0).attr("colspan").equals("9")){
					
				if(link.child(1).text().length() !=0){
					
					if(!link.child(1).text().equals("Name")){
						String name=link.child(1).text();
						String prevClub=link.child(8).text();
						
						if(link.child(1).text().contains("'")){
							String [] pbQuote = name.split("'");
							name = pbQuote[0]+pbQuote[1];
						}
						else if(prevClub.contains("'")){
							String [] pbQuote = name.split("'");
							prevClub = pbQuote[0];
						}
						else{
							;
						}
				sql = "INSERT INTO joueurs"
						+ "(ID, NAME, NATIONALITE, POSITION, HEIGHT, WEIGHT, DATEBIRTH, PREVCLUB, TEAM_ID) " + "VALUES"
						+ "("+id+",'"+name+"','"+link.child(2).text()+"','"
								+ ""+link.child(3).text()+"','"+link.child(4).text()+"','"+link.child(5).text()+
								"','"+link.child(6).text()+"','"+prevClub+"','"+id_team+"')";
				

				// execute insert SQL stetement
				stmt.executeUpdate(sql);
				
				System.out.println(id + sql);
				id++;
				}}
				}
				else{
					;
				}
			}
			
			System.out.println("#### FIN D'ENREGISTREMENT");
			stmt.close();
			stmt2.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Connection getConnection() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Driver O.K");
		Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
		return conn;
	}

}
