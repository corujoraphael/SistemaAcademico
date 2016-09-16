package academico;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Academico {

    private ArrayList<Aluno> alunos;
    private ArrayList<Curso> cursos;
    private ArrayList<Matricula> matriculas;
    private int idAluno;
    private int idCurso;
    private int idMatricula;

    /*public Academico(int tamAlunos, int tamCursos, int tamMatricula) {
        this.alunos = new Aluno[tamAlunos];
        this.cursos = new Curso[tamCursos];
        this.matriculas = new Matricula[tamMatricula];
    }*/
    
    public Academico(){
        alunos = new ArrayList();
        cursos = new ArrayList();
        matriculas = new ArrayList();
    }

    public ArrayList getAlunos() {
        return alunos;
    }

    public ArrayList getCursos() {
        return cursos;
    }

    public ArrayList getMatriculas() {
        return matriculas;
    }

    public void setAlunos(ArrayList alunos) {
        this.alunos = alunos;
    }

    public void setCursos(ArrayList cursos) {
        this.cursos = cursos;
    }

    public void setMatriculas(ArrayList matriculas) {
        this.matriculas = matriculas;
    }

    public void menu() {
        Scanner ler = new Scanner(System.in);
        String opcao;
        do {
            System.out.println("***** Sistema Acadêmico *****");
            System.out.println("1 - Cadastrar Aluno");
            System.out.println("2 - Cadastrar Curso");
            System.out.println("3 - Matricular");
            System.out.println("4 - Imprimir Alunos");
            System.out.println("5 - Imprimir Cursos");
            System.out.println("6 - Imprimir Matriculas");
            System.out.println("7 - Salvar dados");
            System.out.println("8 - Carregar dados");
            System.out.println("9 - Carregar do banco");
            System.out.println("------------------------------");
            System.out.print("Digite uma opção: ");
            opcao = ler.next();
            switch (opcao) {
                case "0":
                    break;
                case "1":
                    this.addAluno();
                    break;
                case "2":
                    this.addCurso();
                    break;
                case "3":
                    this.matricular();
                    break;
                case "4":
                    this.imprimeAlunos();
                    break;
                case "5":
                    this.imprimeCursos();
                    break;
                case "6":
                    this.imprimirMatricula();
                    break;
                case "7":
                    salvarDados("alunos.txt", "matriculas.txt", "cursos.txt");
                    break;
                case "8":
                    carregarDados("alunos.txt", "matriculas.txt", "cursos.txt");
                    break;
                case "9":
                    loadAlunoBD();
                    loadCursoBD();
                    loadMatriculaBD();
                    break;
                default:
                    System.out.println("Digite uma opção válida!");
            }
        } while (!opcao.equals("0"));
    }

    public boolean existsAluno(int idAluno) {
        return this.alunos.stream().anyMatch((aluno) -> (aluno != null && aluno.getCodigo() == idAluno));
    }

    public boolean existsCurso(int idCurso) {
        return this.cursos.stream().anyMatch((curso) -> (curso != null && curso.getCodigo() == idCurso));
    }

    public void matricular() {
        Scanner ler = new Scanner(System.in);
        System.out.print("Digite o código do curso: ");
        int idCursoLido = ler.nextInt();
        if (existsCurso(idCursoLido)) {
            System.out.print("Digite o código do aluno: ");
            int idAlunoLido = ler.nextInt();
            if (existsAluno(idAlunoLido)) {
                Matricula aux = new Matricula();
                aux.preencher(idCursoLido, idAlunoLido, this.idMatricula);
                this.matriculas.add(aux);
                FileManager.adddInArquivo("matricula.txt", aux.csv());
                aux.gravarBanco();
            } else {
                System.out.println("Aluno não cadastrado!");
            }
        } else {
            System.out.println("Curso não cadastrado!");
        }
    }

    public void imprimirMatricula() {
        for (Matricula matricula : this.matriculas) {
            System.out.println("Número da matricula: " + matricula.getNumeroMatricula());
            System.out.println("Nome do aluno: " + alunos.get(matricula.getCodAluno()).getNome());
            System.out.println("Nome do curso: " + cursos.get(matricula.getCodCurso()).getNome());
            System.out.println("------------------------------------------");
        }
    }

    public void addAluno() {
        Aluno aux = new Aluno();
        aux.preencher(idAluno++);
        aux.gravarBanco();
        this.alunos.add(aux);
    }

    public void addCurso() {
        Curso aux = new Curso();
        aux.preencher(idCurso++);
        this.cursos.add(aux);
        aux.gravarBanco();
    }

    public void imprimeAlunos() {
        this.alunos.stream().filter((aluno) -> (aluno != null)).forEach((aluno) -> {
            aluno.imprimir();
        });
    }

    public void imprimeCursos() {
        this.cursos.stream().filter((curso) -> (curso != null)).forEach((curso) -> {
            curso.imprimir();
        });
    }
    
    public String alunosCsv(){
        String alunoCsv = "ID\r\n"+this.idAluno+"\r\n";
        if (this.alunos.size() > 0){
            alunoCsv += this.alunos.get(0).cabecalho()+"\r\n";
            for (Aluno aluno:this.alunos){
                alunoCsv+= aluno.csv()+"\r\n";
            }
        }
        return alunoCsv;
    }
    
    public String matriculasCsv(){
        String matriculasCsv = "ID\r\n"+this.idMatricula+"\r\n";
        if (this.matriculas.size() > 0){
            matriculasCsv += this.matriculas.get(0).cabecalho()+"\r\n";
            for (Matricula matricula:this.matriculas){
                matriculasCsv+= matricula.csv()+"\r\n";
            }
        }
        return matriculasCsv;
    }
    
    public String cursosCsv(){
        String cursosCsv = "ID\r\n"+this.idCurso+"\r\n";
        if (this.cursos.size() > 0){
            cursosCsv += this.cursos.get(0).cabecalho()+"\r\n";
            for (Curso curso:this.cursos){
                cursosCsv+= curso.csv()+"\r\n";
            }
        }
        return cursosCsv;
    }
    
    public void salvarDados(String alunosPath, String matriculaPath, String cursosPath){
        FileManager.salvarNovoArquivo(alunosPath, alunosCsv());
        FileManager.salvarNovoArquivo(matriculaPath, matriculasCsv());
        FileManager.salvarNovoArquivo(cursosPath, cursosCsv());
    }
    
    public void carregarAlunos(String listaAlunos){
        String[] dados = FileManager.getParametros(listaAlunos, "\n");
        this.idAluno = Integer.parseInt(dados[1]);
        for (int i = 3; i<dados.length;i++){
            Aluno aux = new Aluno();
            aux.carregarCsv(dados[i]);
            this.alunos.add(aux);
        }
    }
    
    public void carregarCursos(String listaCursos){
        String[] dados = FileManager.getParametros(listaCursos, "\n");
        this.idCurso = Integer.parseInt(dados[1]);
        for (int i = 3; i<dados.length;i++){
            Curso aux = new Curso();
            aux.carregarCsv(dados[i]);
            this.cursos.add(aux);
        }
    }
    
    public void carregarMatriculas(String listaMatriculas){
        String[] dados = FileManager.getParametros(listaMatriculas, "\n");
        this.idMatricula = Integer.parseInt(dados[1]);
        for (int i = 3; i<dados.length;i++){
            Matricula aux = new Matricula();
            aux.carregarCsv(dados[i]);
            this.matriculas.add(aux);
        }
    }
    
    public void carregarDados(String alunosPath, String matriculaPath, String cursosPath){
        carregarAlunos(FileManager.loadArquivo("alunos.txt"));
        carregarCursos(FileManager.loadArquivo("cursos.txt"));
        carregarMatriculas(FileManager.loadArquivo("matriculas.txt"));
    }
    
    public void loadAlunoBD(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = Persistencia.conexao().prepareStatement("SELECT * FROM aluno");
            rs = ps.executeQuery();
            while(rs.next()){
                Aluno aux = new Aluno();
                aux.preencher(rs.getInt("codigo"), rs.getString("nome"), rs.getString("cpf"));
                this.alunos.add(aux);
            }
        }catch(SQLException e){
            System.out.println("Não foi possível executar o comando SQL! "+e);
        }
    }
    
    public void loadMatriculaBD(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = Persistencia.conexao().prepareStatement("SELECT * FROM matricula");
            rs = ps.executeQuery();
            while(rs.next()){
                Matricula aux = new Matricula();
                aux.preencher(rs.getInt("numeroMatricula"), rs.getInt("codAluno"), rs.getInt("codCurso"), rs.getInt("data"));
                this.matriculas.add(aux);
            }
        }catch(SQLException e){
            System.out.println("Não foi possível executar o comando SQL! "+e);
        }
    }
    
    public void loadCursoBD(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = Persistencia.conexao().prepareStatement("SELECT * FROM curso");
            rs = ps.executeQuery();
            while(rs.next()){
                Curso aux = new Curso();
                aux.preencher(rs.getInt("codigo"), rs.getString("nome"), rs.getInt("cargaHoraria"));
                this.cursos.add(aux);
            }
        }catch(SQLException e){
            System.out.println("Não foi possível executar o comando SQL! "+e);
        }
    }
}
