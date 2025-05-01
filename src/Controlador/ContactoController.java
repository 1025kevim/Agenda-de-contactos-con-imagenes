package Controllador;

import Modelo.Contacto;
import Modelo.ContactoDAO;
import java.util.List;

public class ContactoController {
    private ContactoDAO dao = new ContactoDAO();
    
    public boolean agregarContacto(Contacto contacto) {
        return dao.crearContacto(contacto);
    }
    
    public List<Contacto> obtenerTodosContactos() {
        return dao.obtenerContactos();
    }
    
    public byte[] obtenerImagenContacto(int id) {
        return dao.obtenerImagenContacto(id);
    }
}