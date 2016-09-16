package academico;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Persistencia {
    
    private static Connection conn = null;
    
    private Persistencia(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Academico", "root", "" );
            System.out.println("OK!");
        }catch(ClassNotFoundException e){
            System.out.println("Não foi possível encontrar o driver especificado. "+e);
        }catch(SQLException ex){
            System.out.println("Não foi possível conectar ao banco de dados. "+ex);
        }
    }
    
    public static Connection conexao(){
        if (conn==null)
            new Persistencia();
        return conn;
    }
}
