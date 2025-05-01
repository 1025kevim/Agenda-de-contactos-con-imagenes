package Vista;

import Modelo.Contacto;
import Controllador.ContactoController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Dashboard extends JFrame {
    private JTable tablaContactos;
    private ContactoController controller;

    public Dashboard() {
        setTitle("Agenda de Contactos");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        controller = new ContactoController();
        
        // Tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Teléfono");
        model.addColumn("Email");
        
        tablaContactos = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tablaContactos);
        
        // Botones
        JButton btnAgregar = new JButton("Agregar Contacto");
        btnAgregar.addActionListener(e -> {
            new FormularioContacto(this).setVisible(true);
            actualizarTabla();
        });
        
        JButton btnVerImagen = new JButton("Ver Imagen");
        btnVerImagen.addActionListener(e -> verImagen());
        
        // Panel de botones
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAgregar);
        btnPanel.add(btnVerImagen);
        
        // Layout
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
        
        actualizarTabla();
        setLocationRelativeTo(null);
    }
    
    private void actualizarTabla() {
        DefaultTableModel model = (DefaultTableModel) tablaContactos.getModel();
        model.setRowCount(0);
        
        List<Contacto> contactos = controller.obtenerTodosContactos();
        for (Contacto c : contactos) {
            model.addRow(new Object[]{c.getId(), c.getNombre(), c.getTelefono(), c.getEmail()});
        }
    }
    
    private void verImagen() {
        int fila = tablaContactos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un contacto");
            return;
        }
        
        int id = (int) tablaContactos.getValueAt(fila, 0);
        byte[] imagenBytes = controller.obtenerImagenContacto(id);
        
        if (imagenBytes != null) {
            JDialog dialog = new JDialog(this, "Imagen del Contacto", true);
            JLabel lblImagen = new JLabel(new ImageIcon(imagenBytes));
            dialog.add(lblImagen);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Este contacto no tiene imagen");
        }
    }
}