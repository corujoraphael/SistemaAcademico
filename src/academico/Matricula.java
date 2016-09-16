package academico;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Matricula {
    private int numeroMatricula;
    private long dataMatricula;
    private int codAluno;
    private int codCurso;
    
    public Matricula(){
        this.codAluno = -1;
        this.codCurso = -1;
        this.dataMatricula = -1;
        this.numeroMatricula = -1;
    }
    
    public Matricula(int num, int codAluno, int codCurso){
        this.codAluno = codAluno;
        this.codCurso = codCurso;
        Date data = new Date(); 
        this.dataMatricula = data.getTime();
        this.numeroMatricula = num;
    }
    
    public void preencher(int num, int codAluno, int codCurso, long dataMatricula){
        this.codAluno = codAluno;
        this.codCurso = codCurso;
        this.dataMatricula = dataMatricula;
        this.numeroMatricula = num;
    }
            

    public int getNumeroMatricula() {
        return numeroMatricula;
    }

    public long getDataMatricula() {
        return dataMatricula;
    }

    public int getCodAluno() {
        return codAluno;
    }

    public int getCodCurso() {
        return codCurso;
    }

    public void setNumeroMatricula(int numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public void setDataMatricula(long dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public void setCodAluno(int codAluno) {
        this.codAluno = codAluno;
    }

    public void setCodCurso(int codCurso) {
        this.codCurso = codCurso;
    }
    
    public void preencher(int idAluno, int idCurso, int numMatricula){
        this.codAluno = idAluno;
        this.codCurso = idCurso;
        Date data = new Date();
        this.dataMatricula = data.getTime();
        this.numeroMatricula = numMatricula;        
    }
    
    public String csv(){
        return (String) (this.codAluno+";"+this.codCurso+";"+this.dataMatricula+";"+this.numeroMatricula);
    }
    
    public String cabecalho(){
        return "CODALUNO;CODCURSO;DATAMATRICULA;NUMEROMATRICULA";
    }
    
    public void carregarCsv(String texto){
        String[] parametros = FileManager.getParametros(texto, ";");
        this.codAluno = Integer.parseInt(parametros[0]);
        this.codCurso = Integer.parseInt(parametros[1]);
        this.dataMatricula = Long.parseLong(parametros[2]);
        this.numeroMatricula = Integer.parseInt(parametros[3]);
    }
    
    public void gravarBanco(){
        PreparedStatement ps = null;
        try{
            ps = Persistencia.conexao().prepareStatement("INSERT INTO matricula (nuemroMatricula, codAluno, codCurso, dataMatricula ) value (?,?,?,?)");
            ps.setInt(1, this.numeroMatricula);
            ps.setInt(2, this.codAluno);
            ps.setInt(3, this.codCurso);
            ps.setLong(4, this.dataMatricula);
            
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println("Não foi possível executar o comando SQL");
        }
    }
    
}
