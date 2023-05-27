/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco;

/**
 *
 * @author gusta
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

/**
 *
 * @author a2459132
 */
public class CriarBanco {
    private String driver;
    private String banco;
    private String user;
    private String senha;
    private String url;
    private Connection con;
    /**
     *
     * @throws java.lang.ClassNotFoundException
     */
    public CriarBanco()throws ClassNotFoundException {
        this("com.mysql.cj.jdbc.Driver",
                "MERCEARIA",
                "root",
                "root",
                "jdbc:mysql://localhost/");
    }
    /**
     *
     * @param driver
     * @param banco
     * @param user
     * @param senha
     * @param url
     * @throws ClassNotFoundException
     */
    public CriarBanco(String driver, String banco, String user, String senha,
            String url)throws ClassNotFoundException {
        
        this.driver = driver;
        this.banco = banco;
        this.user = user;
        this.senha = senha;
        this.url = url;
        Class.forName(driver);
    }
    public void setCon() throws SQLException{
        con = DriverManager.getConnection(url, user, senha);
        
    }
    public void setCon(String banco) throws SQLException {
        con = DriverManager.getConnection(url + getBanco(), user,
                senha);
    }
    
    public void criarBaseDados()throws SQLException {
        setCon();
        Statement sessao = getCon().createStatement();
        String sql = "CREATE DATABASE " + getBanco();
        try{
            sessao.executeUpdate(sql);
        }catch(SQLException e){
            throw new SQLException("erro na criacao do banco\n"+sql);
        }
    }
   
    
    public void updateBanco(String sql) throws SQLException {
        
        
        setCon("MERCEARIA");
            
        Statement sessao = getCon().createStatement();
        try {
            
            sessao.executeUpdate(sql);
        } catch (SQLException e) {
            throw new SQLException("erro na execução da SQL\n"
                    + sql);
        }
    }

    
    public void criarTabelas(String nomeArq) throws FileNotFoundException, SQLException, IOException {
        
        ManipulaArquivo arquivo = new ManipulaArquivo(nomeArq);
        arquivo.abreArqLeitura();
        
        String linha;
        String linhaFim = "";
        while((linha = arquivo.lerLinha())!= null) {
            linhaFim += linha;
        }
        updateBanco(linhaFim);
          
    }
    
    public static void main(String[] args) {
        try {
            CriarBanco appCria = new CriarBanco();
            appCria.criarBaseDados();
            
            appCria.criarTabelas("MERCEARIA.txt");
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Problemas com o driver " +ex.getMessage());
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
           System.out.println("Arquivo não encontrado");
        }
    }
    
    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return the banco
     */
    public String getBanco() {
        return banco;
    }

    /**
     * @param banco the banco to set
     */
    public void setBanco(String banco) {
        this.banco = banco;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the con
     */
    public Connection getCon() {
        return con;
    }
}
