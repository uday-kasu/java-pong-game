import javax.swing.JFrame;

public class GameFrame extends JFrame {

    GamePanel panel;

    public GameFrame() {
        panel = new GamePanel();
        this.add(panel);

        this.setTitle("Pong Game");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        // 🔥 Important: Give focus to panel for keyboard input
        panel.requestFocusInWindow();
    }
}