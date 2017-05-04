import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by Kolja on 04.05.2017.
 */
public class Main {


        public static void main(String[] args) {



            try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_softserve", "root", "root")){



                Class.forName("com.mysql.jdbc.Driver");

                Statement st = connection.createStatement();

                st.execute("CREATE TABLE SOMETEST2(ID INT auto_increment PRIMARY KEY, NAME VARCHAR(30));");

            }catch (Exception e){

            }

        }


}
