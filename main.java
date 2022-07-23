import javax.swing.JFrame;

class Main {
    public static void main (String[] args) {

        /* Fenster konfigurieren */
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("QuadTree");

        /* Fensterinhalt erstellen und hinzufügen */
        window.add(new MyPanel());
        window.pack();

        /* Fenster öffnen */
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        
    }
}