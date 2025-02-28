package Code;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.*;

public class Window extends Canvas {

    public static JFrame frame;
    public static JPanel panel = new JPanel();
    
    public Window (int width, int height, String title, Game game){
        frame = new JFrame(title);
        Dimension dimension = new Dimension(width+15, height+15);
        frame.setPreferredSize(dimension);
        frame.setMaximumSize(dimension);
        frame.setMinimumSize(dimension);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();


        
    }
}
