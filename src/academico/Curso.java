package academico;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Curso {
    private int codigo;
    private String nome;
    private int cargaHoraria;
    
    public Curso(){
        this.codigo = -1;
        this.nome = "";
        this.cargaHoraria = -1;
    }
    
    public Curso(int cod, String nome, int cargaHoraria){
        this.codigo = cod;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
    
    public void preencher(int codigo, String nome, int ch){
        this.codigo = codigo;
        this.nome = nome;
        this.cargaHoraria = ch;
    }
    
    public void preencher(int codigo){
        Scanner ler = new Scanner(System.in);
        this.codigo = codigo;
        System.out.print("Digite o nome: ");
        this.nome = ler.nextLine();
        System.out.print("Digite a carga horária: ");
        this.cargaHoraria = ler.nextInt();
    }
    
    public void imprimir(){
        System.out.println("Código: " + this.codigo);
        System.out.println("Nome: " + this.nome);
        System.out.println("Carga Horária: " + this.cargaHoraria);
        System.out.println("------------------------------------------");
    }
    
    public String csv(){
        return (String) (this.codigo+";"+this.nome+";"+this.cargaHoraria);
    }
    
    public String cabecalho(){
        return "CODIGO;NOME;CARGAHORARIA";
    }
    
    public void carregarCsv(String texto){
        String[] parametros = FileManager.getParametros(texto, ";");
        this.codigo = Integer.parseInt(parametros[0]);
        this.nome = parametros[1];
        this.cargaHoraria = Integer.parseInt(parametros[2]);
    }
    
    public void gravarBanco(){
        PreparedStatement ps = null;
        try{
            ps = Persistencia.conexao().prepareStatement("INSERT INTO curso (nome, cargaHoraria ) value (?,?)");
            ps.setString(1, this.nome);
            ps.setInt(2, this.cargaHoraria);
            
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println("Não foi possível executar o comando SQL");
        }
    }
}
