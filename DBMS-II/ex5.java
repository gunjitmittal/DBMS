package assignment1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;  
public class ex5 {
	
	private final static String url = "jdbc:postgresql://localhost:5432/database";
    private final static String user = "postgres";
    private final static String password = "notsoeasy";
    
	public static void main(String[] args) {
		Connection conn = null;
		Scanner sc = new Scanner(System.in);
        try {
			conn = DriverManager.getConnection(url, user, password);
			DBTablePrinter obj = new DBTablePrinter();
			ex4 obj1 = new ex4();
			Statement stmt = conn.createStatement();
			Statement stmt2 = conn.createStatement();
			ResultSet rset00 = stmt2.executeQuery("SELECT column_name \n"
					+ "FROM information_schema.columns \n"
					+ "WHERE table_name='student' and column_name='cgpa'");
			if(rset00.next() == false) {
				PreparedStatement pstmt01 = conn.prepareStatement("Update student set cgpa=? where id=?");
				stmt2.executeUpdate("Alter table student add column cgpa numeric"); 
				ResultSet rset = stmt2.executeQuery("Select id from student");
				while(rset.next()) {
					String roll = rset.getString(1);
					float cg = obj1.cgpa(roll);
					pstmt01.setFloat(1, cg);
					pstmt01.setString(2, roll);
					pstmt01.executeUpdate();
				}
			}
			System.out.println("Enter choice");
			int choice = sc.nextInt();
			sc.nextLine();
			switch(choice) {
			case(1):
				PreparedStatement pstmt = conn.prepareStatement("Select id,name,cgpa, RANK () OVER (ORDER BY cgpa DESC) rank from student limit ?");
				System.out.println("Enter k");
				int k = sc.nextInt();
				pstmt.setInt(1, k);
				ResultSet rset2 = pstmt.executeQuery();
				obj.printResultSet(rset2);
				break;
			case(2):
				PreparedStatement pstmt2 = conn.prepareStatement("Select id,name,cgpa, RANK () OVER (ORDER BY cgpa DESC) rank from student where dept_name=? limit ?");
				
				System.out.println("Enter Department name");
				String dept = sc.nextLine();
				System.out.println("Enter k");
				int k1 = sc.nextInt();
				pstmt2.setInt(2, k1);
				pstmt2.setString(1,dept);
				ResultSet rset3 = pstmt2.executeQuery();
				obj.printResultSet(rset3);
				break;
			case(3):
				PreparedStatement pstmt3 = conn.prepareStatement("Select student.id,student.name,student.cgpa, RANK () OVER (ORDER BY cgpa DESC) rank from student,takes where student.id=takes.id and takes.course_id=? limit ?");
				System.out.println("Enter course id");
				String c_id = sc.nextLine();
				System.out.println("Enter k");
				int k2 = sc.nextInt();
				pstmt3.setInt(2, k2);
				pstmt3.setString(1,c_id);
				ResultSet rset4 = pstmt3.executeQuery();
				obj.printResultSet(rset4);
			default:
				break;
			}
			stmt2.executeUpdate("Alter table student drop column cgpa");
            stmt.close();
            conn.close();
            sc.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}