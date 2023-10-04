package Logica;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Desarrollo {

    private static List<String> listaDatos = new ArrayList<>();
    private static Map<String, Integer> tiquetesPreferidos = new HashMap<>();
    private static int Maximo = 0;
    private static int numeroTiquete = 1;

    private String edad;
    private String preferencia;

    public Desarrollo(String edad, String preferencia, int asuntos) {
        this.edad = edad;
        this.preferencia = preferencia;
    }

    public static void MostrarDatos(JSpinner spnConsultas, JComboBox cbxPreferencias, JTextField txtEdad, JTable tblResultados) {
        
        String edad = txtEdad.getText();
        String preferencia = cbxPreferencias.getSelectedItem().toString();
        int asuntos = Integer.parseInt(spnConsultas.getValue().toString());
        
        String tiquete = Tiquetes(edad, preferencia, asuntos);
        listaDatos.add(tiquete);

       
        Maximo++;

        txtEdad.setText("");

        
        llenarTabla(Maximo, asuntos, edad, preferencia, tiquete, tblResultados);

       
        if (Maximo == 15) {
            JOptionPane.showMessageDialog(null, "Se ha alcanzado el límite de 15 tickets");
        }
    }

   
    private static String Tiquetes(String edad, String preferencia, int asuntos) {
       
        String prefer = Prioridades(edad, preferencia, asuntos);

      
        int numeroTiquete = tiquetesPreferidos.getOrDefault(prefer, 0) + 1;

      
        tiquetesPreferidos.put(prefer, numeroTiquete);

        
        return prefer + numeroTiquete;
    }

   
    private static String Prioridades(String edad, String preferencia, int asuntos) {
        
        if (Integer.parseInt(edad) >= 65) {
            return "A";
        }

        
        if (asuntos >= 2) {
            return "C";
        }

        
        if (preferencia != null && (preferencia.equalsIgnoreCase("Embarazada") || preferencia.equalsIgnoreCase("Niño en brazos"))) {
            return "B";
        }

        // En cualquier otro caso
        return "D";
    }

    
    public static void llenarTabla(int cantidadTiquetes, int spnConsultas, String edad, String preferencia, String tiquete, JTable tblResultados) {
        
        DefaultTableModel dato = dato(tblResultados);

       
        dato.addRow(new Object[]{cantidadTiquetes, spnConsultas, edad, preferencia, tiquete});
    }

    
    private static DefaultTableModel dato(JTable tblResultados) {
        DefaultTableModel dato;
        if (tblResultados.getModel() instanceof DefaultTableModel) {
            dato = (DefaultTableModel) tblResultados.getModel();
        } else {
            dato = new DefaultTableModel();
            tblResultados.setModel(dato);

            
            dato.addColumn("Cantidad");
            dato.addColumn("Edad");
            dato.addColumn("Preferencia");
            dato.addColumn("Tiquete");
        }
        return dato;
    }



    public static void tiquets(JTable tblcajas) {
        DefaultTableModel model = model(tblcajas);

        // Verificar que hay tiquetes disponibles antes de llenar la tabla
        if (listaDatos.size() > 0) {
            Random random = new Random();

            for (String tiquete : listaDatos) {
                int tiempoTramite = random.nextInt(24) + 2; 
                int caja = random.nextInt(4) + 1; 
                model.addRow(new Object[]{tiquete, "Caja " + caja, tiempoTramite + " minutos"});
            }
        }
    }


    private static DefaultTableModel model(JTable tblcajas) {
        DefaultTableModel model;
        if (tblcajas.getModel() instanceof DefaultTableModel) {
            model = (DefaultTableModel) tblcajas.getModel();
        } else {
            model = new DefaultTableModel();
            tblcajas.setModel(model);

          
            model.addColumn("Tiquete");
            model.addColumn("Caja");
            model.addColumn("Tiempo en Caja");
        }
        return model;
    }
public static void mostrarResultado(JTable tblcajas, JTextArea txtArea) {
    DefaultTableModel model = (DefaultTableModel) tblcajas.getModel();
    int totalClientes = model.getRowCount();
    int[] clientesAtendidosPorCaja = new int[4];

    for (int i = 0; i < totalClientes; i++) {
        String cajaStr = model.getValueAt(i, 4).toString();
        int caja = Integer.parseInt(cajaStr.substring(cajaStr.length() - 1)) - 1; // Obtener el número de caja
        clientesAtendidosPorCaja[caja]++;
    }

    // Cantidad de clientes atendidos por caja cajero
    StringBuilder resultado = new StringBuilder("Cantidad de clientes atendidos por caja cajero:\n");
    for (int i = 0; i < clientesAtendidosPorCaja.length; i++) {
        resultado.append("Caja ").append(i + 1).append(": ").append(clientesAtendidosPorCaja[i]).append(" clientes\n");
    }

    txtArea.setText(resultado.toString());
}


    /**
     * @return the edad
     */
    public String getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(String edad) {
        this.edad = edad;
    }

    /**
     * @return the preferencia
     */
    public String getPreferencia() {
        return preferencia;
    }

    /**
     */
    public void setPreferencia(String preferencia) {
        this.preferencia = preferencia;
    }

}
