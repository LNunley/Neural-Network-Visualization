package Window;

import Algorithm.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lfnunley on 11/25/14.
 */
public class ProgramWindow extends JFrame{
    private GraphicsPanel graphicsPanel;
    private Timer timer;

    public ProgramWindow(){
        super();
        setSize(600, 800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        graphicsPanel = new GraphicsPanel();
        add(graphicsPanel);
        pack();

        timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                graphicsPanel.updateModel();
                graphicsPanel.repaint();
            }
        });

        timer.start();
    }

    public void paint(Graphics g){
        graphicsPanel.updateModel();
        graphicsPanel.paint(g);
    }

}
