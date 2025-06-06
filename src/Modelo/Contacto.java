package Modelo;

public class Contacto {
    private int id;
    private String nombre;
    private String telefono;
    private String email;
    private byte[] imagen;
    
    public Contacto() {}
    
    public Contacto(String nombre, String telefono, String email, byte[] imagen) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.imagen = imagen;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public byte[] getImagen() { return imagen; }
    public void setImagen(byte[] imagen) { this.imagen = imagen; }
}