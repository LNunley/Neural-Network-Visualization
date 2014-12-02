package Window;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by lfnunley on 11/26/14.
 */
public class GraphicsPanel extends JPanel{

  class GraphicNode{
    public int width = 25;
    public int height = 25;

    public int id;
    public double sum;
    public double x;
    public double y;

    GraphicNode(int a_id, double a_sum){
      id = a_id;
      sum = a_sum;
    }
  }

  private ArrayList<GraphicNode> graphicNodes;

  public GraphicsPanel(){
    graphicNodes = new ArrayList<GraphicNode>();
    graphicNodes.add(new GraphicNode(0, 40));
    graphicNodes.get(0).x = 100;
    graphicNodes.get(0).y = 249;

    graphicNodes.add(new GraphicNode(1, 50));
    graphicNodes.get(1).x = 101;
    graphicNodes.get(1).y = 250;

    graphicNodes.add(new GraphicNode(3, 50));
    graphicNodes.get(2).x = 102;
    graphicNodes.get(2).y = 251;

    setBorder(BorderFactory.createLineBorder(Color.black));
    setPreferredSize(new Dimension(600, 600));
    setVisible(true);
    setSize(new Dimension(600, 600));
  }

  public void updateModel(){
    for(GraphicNode n: graphicNodes){
      n.x += (300.d / (n.x)) - (300.d / (getWidth() - n.x));
      n.y += (300.d / (n.y)) - (300.d / (getHeight() - n.y));


      for(GraphicNode m: graphicNodes){
        if(m.id != n.id) {
          if(m.x == n.x)
            m.x += new Random().nextInt() * 4 - 2;
          m.x += (15.d / (m.x - n.x));
          if (m.y == n.y)
            m.y += new Random().nextInt() * 2 - 2;
          m.y += (15.d / (m.y - n.y));
        }
      }
    }
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);

    g.setColor(Color.RED);

    for(GraphicNode n : graphicNodes)
      g.drawOval((int)n.x, (int)n.y, n.width, n.height);
  }
}
