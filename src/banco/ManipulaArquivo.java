/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author gusta
 */
public class ManipulaArquivo {

    private String nomeArq;
    private BufferedReader arqLeitura;
    private BufferedWriter arqSaida;

    public ManipulaArquivo(String nomeArq) {
        this.nomeArq = nomeArq;
    }

    public String lerLinha() throws IOException {
        String linha = null;
        try {
            linha = this.arqLeitura.readLine();
        }
        catch(FileNotFoundException e){
            System.out.println("Arquivo não encontrado");
        }
        catch (EOFException e) {
            System.err.println("Fim do arquivo alcançado.");
            this.arqLeitura.close();
        }
        return linha;
    }

    public void gravarLinhaTexto(String linha) throws IOException {
        if (arqSaida == null) {
            throw new IOException("Arquivo não está aberto para gravação.");
        }
        int posicao = 0;
        while ((linha.length() - posicao) > 255) {
            arqSaida.write(linha.substring(posicao, posicao + 255));
            arqSaida.newLine();
            posicao += 255;
        }
        arqSaida.write(linha.substring(posicao));
        arqSaida.newLine();
    }

    public void abreArqLeitura() throws IOException {
        arqLeitura = new BufferedReader(new FileReader(nomeArq));
    }

    public void abreArqGravacao() throws IOException {
        arqSaida = new BufferedWriter(new FileWriter(nomeArq));
    }

    public void fechaArqLeitura() throws IOException {
        if (arqLeitura != null) {
            arqLeitura.close();
        }
    }
    public void fechaArqGravacao() throws IOException {
        if (arqSaida != null) {
            arqSaida.close();
        }
    }
}
