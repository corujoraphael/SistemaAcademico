package academico;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Aluno {
    private int codigo;
    private String nome;
    private String cpf;
    
    public Aluno(){
        this.codigo = -1;
        this.nome = "";
        this.cpf = "";
    }
    
    public Aluno(int cod, String nome, String cpf){
        this.codigo = cod;
        this.nome = nome;
        this.cpf = cpf;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public void preencher(int codigo){
        Scanner ler = new Scanner(System.in);
        this.codigo = codigo;
        System.out.print("Digite o nome: ");
        this.nome = ler.nextLine();
        System.out.print("Digite o CPF: ");
        this.cpf = ler.nextLine();
    }
    
    public void preencher(int codigo, String nome, String cpf){
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
    }
    
    public void imprimir(){
        System.out.println("Código: " + this.codigo);
        System.out.println("Nome: " + this.nome);
        System.out.println("CPF:  " + this.cpf);
        System.out.println("------------------------------------------");
    }
    
    public String csv(){
        return (String) (this.codigo+";"+this.nome+";"+this.cpf);
    }
    
    public String cabecalho(){
        return "CODIGO;NOME;CPF";
    }
    
    public void carregarCsv(String texto){
        String[] parametros = FileManager.getParametros(texto, ";");
        this.codigo = Integer.parseInt(parametros[0]);
        this.nome = parametros[1];
        this.cpf = parametros[2];
    }
    
    public void gravarBanco(){
        PreparedStatement ps = null;
        try{
            ps = Persistencia.conexao().prepareStatement("INSERT INTO aluno (nome, cpf ) value (?,?)");
            ps.setString(1, this.nome);
            ps.setString(2, this.cpf);
            
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println("Não foi possível executar o comando SQL");
        }
    }
    
    
}
