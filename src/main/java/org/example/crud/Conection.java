package org.example.crud;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Conection {
    private static final String UBICACION_WALLET =
            "C:/Users/cfern/IdeaProjects/Crud_Jeringas/src/main/resources/wallet";
    private static final String JDBC_URL = "jdbc:oracle:thin:@crud_high";
    private static final String USER     = "ADMIN";
    private static final String PASS     = "Labatayafinal44";

    static {
        System.setProperty("oracle.net.tns_admin", UBICACION_WALLET);
    }

    /** Devuelve una conexión a Oracle */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.OracleDriver");
        return DriverManager.getConnection(JDBC_URL, USER, PASS);
    }

    /** Inserta un usuario */
    public static boolean insertarUsuario(String id, String nombre, String contrasenia) {
        String sql = "INSERT INTO USUARIOS (ID, NOMBRE, CONTRASENIA) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, nombre);
            pstmt.setString(3, contrasenia);

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Obtiene todos los usuarios */
    public static List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM USUARIOS";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("ID"), // o el nombre real de la columna de ID
                        rs.getString("NOMBRE"),
                        rs.getString("CONTRASENIA"),
                        );
                usuarios.add(usuario);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}

