package assignment1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;  
import javax.swing.JOptionPane;

public class ex2 {
	
	private final static String url = "jdbc:postgresql://localhost:5432/database";
    private final static String user = "postgres";
    private final static String password = "notsoeasy";
    
	public static void main(String[] args) {
		Connection conn = null;
		String[] preq = new String[100];
		int head=1;
		String cid = JOptionPane.showInputDialog(null,"Enter course id");
		preq[0]= cid;
		boolean check=true;
        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement("Select prereq_id from prereq where course_id=?");
            pstmt.setString(1, cid);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()) {
            	preq[head]=rset.getString(1);
            	head++;
            }
            for(int i=1;preq[i]!=null;i++)
            {
            	pstmt.setString(1,preq[i]);
            	ResultSet temprset = pstmt.executeQuery();
            	while(temprset.next()) {
            		check=true;
            		String id = temprset.getString(1);
            		for(int j=0;j<head;j++) {
            			if(id.equals(preq[j])) check=false;
            		}
            		if (check) {
                	preq[head]=id;
                	head++;
                	
            		}
                }
            }
            PreparedStatement pstmt2 = conn.prepareStatement("Select course_id,title from course where course_id=?");
            pstmt2.setString(1, preq[0]);
            ResultSet rset2 = pstmt2.executeQuery();
            ResultSetMetaData rsmd = rset2.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			for(int j=1;j<=columnsNumber;j++) {
				 if (j > 1) System.out.print(",  ");
				System.out.print(rsmd.getColumnName(j));
			}
			System.out.println();
            for(int i=1;preq[i]!=null;i++)
            {
            	pstmt2.setString(1, preq[i]);
                ResultSet temprset2 = pstmt2.executeQuery();
    			
    			while (temprset2.next()) {
    				for (int j = 1; j <= columnsNumber; j++) {
    			        if (j > 1) System.out.print(",  ");
    			        String columnValue = temprset2.getString(j);
    			        System.out.print(columnValue);
    			    }
    			    System.out.println("");
    			}
            }
			pstmt.close();
			conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}

}