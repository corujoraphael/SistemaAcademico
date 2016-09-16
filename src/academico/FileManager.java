package academico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
        
    public static void salvarNovoArquivo(String path, String text){
        File file = new File(path);
        try{
            FileWriter escrever = new FileWriter(file);
            escrever.write(text);
            escrever.flush();
            escrever.close();
        }catch(IOException e){
            System.out.println("Ocorreu algum erro! "+e);
        }
    }
    
    public static void adddInArquivo(String path, String text){
        File file = new File(path);
        try{
            FileWriter escrever = new FileWriter(file, true);
            escrever.write(text);
            escrever.flush();
            escrever.close();
        }catch(IOException e){
            System.out.println("Ocorreu algum erro! "+e);
        }
    }
    
    public static String loadArquivo(String path){
        File file = new File(path);
        String lido = "";
        try{
            FileReader escrever = new FileReader(file);
            BufferedReader buffer = new BufferedReader(escrever);
            String aux = buffer.readLine();
            while(aux != null){
                lido += aux;
                aux = buffer.readLine();
                lido +=  "\n";
            }
            buffer.close();
        }catch(IOException e){
            System.out.println("Ocorreu algum erro! "+e);
        }
        return lido;
    }
    
    public static String[] getParametros(String texto, String delimiter){
        return texto.split(delimiter);       
    }
}
