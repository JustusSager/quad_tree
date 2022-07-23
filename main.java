import javax.swing.JFrame;

class Main {
    public static void main (String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("QuadTree");

        window.add(new MyPanel());
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        
    }
}