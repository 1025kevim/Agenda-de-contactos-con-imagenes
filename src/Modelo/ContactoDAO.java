package Modelo;

import conexion.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactoDAO {
    private Connection conexion;
    
    public ContactoDAO() {
        try {
            conexion = ConexionDB.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean crearContacto(Contacto contacto) {
        String sql = "INSERT INTO contactos (nombre, telefono, email, imagen) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, contacto.getNombre());
            ps.setString(2, contacto.getTelefono());
            ps.setString(3, contacto.getEmail());
            
            if (contacto.getImagen() != null) {
                ps.setBytes(4, contacto.getImagen());
            } else {
                ps.setNull(4, Types.BLOB);
            }
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        contacto.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Contacto> obtenerContactos() {
        List<Contacto> contactos = new ArrayList<>();
        String sql = "SELECT id, nombre, telefono, email FROM contactos";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Contacto contacto = new Contacto();
                contacto.setId(rs.getInt("id"));
                contacto.setNombre(rs.getString("nombre"));
                contacto.setTelefono(rs.getString("telefono"));
                contacto.setEmail(rs.getString("email"));
                
                contactos.add(contacto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return contactos;
    }
    
    public byte[] obtenerImagenContacto(int id) {
        String sql = "SELECT imagen FROM contactos WHERE id = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBytes("imagen");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}