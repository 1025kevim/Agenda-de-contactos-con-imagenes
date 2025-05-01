package Vista;

import Modelo.Contacto;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class FormularioContacto extends JDialog {
    private JTextField txtNombre, txtTelefono, txtEmail;
    private JButton btnGuardar, btnSeleccionar;
    private JLabel lblImagen;
    private File imagenSeleccionada;

    public FormularioContacto(JFrame parent) {
        super(parent, "Nuevo Contacto", true);
        setSize(400, 500);
        
        // Panel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Formulario
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        txtNombre = new JTextField(20);
        txtTelefono = new JTextField(20);
        txtEmail = new JTextField(20);
        
        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("Teléfono:"));
        formPanel.add(txtTelefono);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(txtEmail);

        // Selección de imagen
        JPanel imagenPanel = new JPanel(new BorderLayout());
        btnSeleccionar = new JButton("Seleccionar Imagen");
        lblImagen = new JLabel("", JLabel.CENTER);
        lblImagen.setPreferredSize(new Dimension(200, 200));
        
        btnSeleccionar.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "png", "jpeg"));
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                imagenSeleccionada = fileChooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(imagenSeleccionada.getPath());
                Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                lblImagen.setIcon(new ImageIcon(img));
            }
        });

        imagenPanel.add(btnSeleccionar, BorderLayout.NORTH);
        imagenPanel.add(lblImagen, BorderLayout.CENTER);

        // Botón guardar
        btnGuardar = new JButton("Guardar Contacto");
        btnGuardar.addActionListener(e -> guardarContacto());

        // Ensamblar interfaz
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(imagenPanel, BorderLayout.CENTER);
        panel.add(btnGuardar, BorderLayout.SOUTH);
        
        add(panel);
        setLocationRelativeTo(parent);
    }

    private void guardarContacto() {
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();

        if (nombre.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y teléfono son obligatorios");
            return;
        }

        try {
            byte[] imagenBytes = null;
            if (imagenSeleccionada != null) {
                try (InputStream is = new FileInputStream(imagenSeleccionada)) {
                    imagenBytes = is.readAllBytes();
                }
            }

            Contacto contacto = new Contacto(nombre, telefono, email, imagenBytes);
            ContactoController controller = new ContactoController();
            
            if (controller.agregarContacto(contacto)) {
                JOptionPane.showMessageDialog(this, "Contacto guardado!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}