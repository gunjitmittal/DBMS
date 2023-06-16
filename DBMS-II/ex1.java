package assignment1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ex1 {
	
	private final static String url = "jdbc:postgresql://localhost:5432/database";
    private final static String user = "postgres";
    private final static String password = "notsoeasy";
    
    public void table_k(String table, int k, Statement stmt) {
    	ResultSet rset;
		try {
			rset = stmt.executeQuery("select * from "+table+" LIMIT "+k);
			DBTablePrinter obj = new DBTablePrinter();
			obj.printResultSet(rset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
	public static void main(String[] args) {
        Connection conn = null;
        ex1 obj = new ex1();
        try {
            conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            Scanner sc= new Scanner(System.in); 
            System.out.print("Enter table name: ");  
            String table = sc.nextLine();  
            System.out.print("Enter row count: ");  
            int k = sc.nextInt();  
			obj.table_k(table , k, stmt);
			stmt.close();
			conn.close();
			sc.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}

}
