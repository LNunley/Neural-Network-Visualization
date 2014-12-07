package Window;

import Algorithm.Feed;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
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
  private HashMap<Integer, GraphicNode> graphicNodes;
  //private ArrayList<GraphicNode> graphicNodes;
  private ArrayList<Feed> feeds;

  public GraphicsPanel(){
    graphicNodes = new HashMap<Integer, GraphicNode>();
    feeds = new ArrayList<Feed>();

    for(int i = 0; i < 9; i++) {
      graphicNodes.put(i, new GraphicNode(i, 40));
      graphicNodes.get(i).x = new Random().nextInt(500) + 1;
      graphicNodes.get(i).y = new Random().nextInt(500) + 1;
    }

    feeds.add(new Feed(0, 3, .6));
    feeds.add(new Feed(0, 4, -.2));
    feeds.add(new Feed(0, 5, -.9));
    feeds.add(new Feed(1, 3, .5));
    feeds.add(new Feed(1, 4, .5));
    feeds.add(new Feed(1, 5, .5));
    feeds.add(new Feed(2, 3, .5));
    feeds.add(new Feed(2, 4, .5));
    feeds.add(new Feed(2, 5, .5));
    feeds.add(new Feed(3, 6, .5));
    feeds.add(new Feed(3, 7, .5));
    feeds.add(new Feed(3, 8, .5));
    feeds.add(new Feed(4, 6, .5));
    feeds.add(new Feed(4, 7, .5));
    feeds.add(new Feed(4, 8, .5));
    feeds.add(new Feed(5, 6, .5));
    feeds.add(new Feed(5, 7, .5));
    feeds.add(new Feed(5, 8, .5));



    setBorder(BorderFactory.createLineBorder(Color.black));
    setPreferredSize(new Dimension(600, 600));
    setVisible(true);
    setSize(new Dimension(600, 600));
  }

  public void updateModel(){
    for(GraphicNode n: graphicNodes.values()){
      n.x += (1000.d / (n.x + .1)) - (1000.d / (getWidth() - n.x + .1));
      n.y += (1000.d / (n.y + .1)) - (1000.d / (getHeight() - n.y + .1));


      for(GraphicNode m: graphicNodes.values()){
        if(m.id != n.id) {
          if(m.x == n.x)
            m.x += (new Random().nextInt() % 2) * 4 - 2;
          if (m.y == n.y)
            m.y += (new Random().nextInt() % 2) * 4 - 2;

          double distx = m.x - n.x;
          double disty = m.y - n.y;


          double len = Math.sqrt(Math.pow(distx, 2) + Math.pow(disty, 2));

          // Normalize
          distx /= len;
          disty /= len;

          m.x += (distx * (100.d / len));
          m.y += (disty * (100.d / len));

          //m.x += (distx * (1.d / len) * 10);
          //m.y += (disty * (1.d / len) * 10);


          if(m.x < 0)
            m.x = 5;
          if(m.x > this.getWidth())
            m.x = this.getWidth() - 5;
          if(m.y < 0)
            m.y = 5;
          if(m.y > this.getHeight())
            m.y = this.getHeight() - 5;
        }
      }
    }

    for(Feed f : feeds){
      GraphicNode sender = graphicNodes.get(f.getSenderID());
      GraphicNode receiver = graphicNodes.get(f.getReceiverID());

      double distx = sender.x - receiver.x;
      double disty = sender.y - receiver.y;


      double len = Math.sqrt(Math.pow(distx, 2) + Math.pow(disty, 2));

      // Normalize
      distx /= len;
      disty /= len;

      sender.x -= (distx * .25);
      sender.y -= (disty * .25);
      receiver.x += (distx * .25);
      receiver.y += (disty * .25);
    }
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D)g;

    g2.setColor(Color.BLACK);
    for(GraphicNode n : graphicNodes.values())
      g2.fillOval((int)(n.x - (.5 * n.width)), (int)(n.y - (.5 * n.height)), n.width, n.height);

    for(Feed f : feeds){
      GraphicNode sender = graphicNodes.get(f.getSenderID());
      GraphicNode receiver = graphicNodes.get(f.getReceiverID());
      g2.setStroke(new BasicStroke((float)Math.min(Math.abs(f.getWeight()) * 5, 5.d)));
      g2.setColor(f.getWeight() > 0 ? Color.GREEN : Color.RED);
      g2.drawLine((int) sender.x, (int) sender.y, (int) receiver.x, (int) receiver.y);

    }
  }
}
