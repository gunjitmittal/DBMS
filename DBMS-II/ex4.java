package assignment1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.sql.Statement;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.ResultSetMetaData;
import javax.swing.JOptionPane;
public class ex4 {
	
	private final static String url = "jdbc:postgresql://localhost:5432/database";
    private final static String user = "postgres";
    private final static String password = "notsoeasy";

    public float cgpa (String roll) {
		int total=1;
		int obtained=0;
		float CG = 0;
    	try {
			Connection conn1 = DriverManager.getConnection(url, user, password);
			PreparedStatement pstmt = conn1.prepareStatement("Select course_id,grade from takes where id=?");
			PreparedStatement pstmt2 = conn1.prepareStatement("Select credits from course where course_id=?");
			pstmt.setString(1, roll);
			ResultSet rset = pstmt.executeQuery();
            while(rset.next()) {
            	String grade = rset.getString(2);
            	String course = rset.getString(1);
            	pstmt2.setString(1,course);
            	ResultSet rset2 = pstmt2.executeQuery();
            	rset2.next();
            	int credit = rset2.getInt(1);
            	if(grade.equals("A+")) obtained+=10*credit;
            	else if(grade.equals("A ")) obtained+=9*credit;
            	else if(grade.equals("A-")) obtained+=8*credit;
            	else if(grade.equals("B+")) obtained+=7*credit;
            	else if(grade.equals("B ")) obtained+=6*credit;
            	else if(grade.equals("B-")) obtained+=5*credit;
            	else if(grade.equals("C+")) obtained+=4*credit;
            	else if(grade.equals("C ")) obtained+=3*credit;
            	else if(grade.equals("C-")) obtained+=2*credit;
            	total+=credit;
            }
            if(total>1)total--;
            CG = (float)obtained/(float)total;
            pstmt.close();
            pstmt2.close();
            conn1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return CG;
    }
	public static void main(String[] args) {
		String roll = JOptionPane.showInputDialog(null,"Enter Roll no. : ");
		ex4 obj = new ex4();
		float cgpa = obj.cgpa(roll);
        if(cgpa>0) System.out.println(cgpa);
        else System.out.println("Wrong Roll no.");


	}

}
