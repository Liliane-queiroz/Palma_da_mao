package br.com.palmadocampo.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoFactory {

    private static final Properties propriedades = new Properties();

    static {
        try (InputStream input = ConexaoFactory.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new RuntimeException("Arquivo db.properties não encontrado em src/main/resources");
            }

            propriedades.load(input);
            Class.forName(propriedades.getProperty("db.driver"));

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler db.properties", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver do MySQL não encontrado no classpath", e);
        }
    }

    private ConexaoFactory() {
    }

    public static Connection getConexao() throws SQLException {
        String url = propriedades.getProperty("db.url");
        String usuario = propriedades.getProperty("db.user");
        String senha = propriedades.getProperty("db.password");

        return DriverManager.getConnection(url, usuario, senha);
    }
}