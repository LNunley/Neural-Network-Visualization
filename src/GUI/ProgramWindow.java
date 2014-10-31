package GUI;


import Algorithm.NeuralNetwork;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by lfnunley on 9/27/2014.
 */

public class ProgramWindow {
  private JPanel mainPanel;
  private JSplitPane mainSplitPane;
  private JPanel webPanel;
  private JPanel controlPanel;

  private JLabel lblControls;

  private JLabel lblLearningRate;
  private JSlider learningRateSlider;
  private JLabel lblHiddenNodes;
  private JSlider hiddenNodesSlider;
  private JLabel lblEpoch;
  private JSlider epochSlider;

  private JButton btnTrain;
  private JProgressBar epochBar;
  private JList<String> fileList;
  private JScrollPane fileListScrollPane;
  private JTabbedPane controlTabs;
  private JPanel trainTab;
  private JPanel testTab;
  private JTextPane outputTextPane;
  private JPanel imageGrid;
  private JTextPane imageOutput;
  private JLabel lblPlaceholder;
  private JScrollPane imageOutputScrollPanel;
  private int currSelectionIndex;

  // This is a temporary fix until I can save topologies
  private NeuralNetwork algorithmCopy;

  public class algorithmWorker extends SwingWorker<String, Integer> {
    private int epochs;
    private int hiddenNodes;
    private double learningRate;
    private NeuralNetwork algorithm;

    algorithmWorker(int a_epochs, int a_hiddenNodes, double a_learningRate){
      epochBar.setMaximum(a_epochs);
      epochBar.setValue(0);

      epochs = a_epochs;
      hiddenNodes = a_hiddenNodes;
      learningRate = a_learningRate;
    }

    @Override
    protected String doInBackground() throws Exception {
      // Simulate doing something useful.
      List<Integer> layers = new ArrayList<Integer>();
      layers.add(15);
      layers.add(hiddenNodes);
      layers.add(10);
      /// TODO: Make this dynamic

      algorithm = new NeuralNetwork(layers, learningRate);

      for (int i = 0; i <= epochs; i++) {
        algorithm.network.runEpoch(algorithm.epoch);

        // The type we pass to publish() is determined
        // by the second template parameter.
        publish(i);
      }

      // Here we can return some object of whatever type
      // we specified for the first template parameter.
      // (in this case we're auto-boxing 'true').
      String output = "";
      for(String s : algorithm.verify)
        output += algorithm.network.getResult(s) + "\n";
      return output;
    }

    // Can safely update the GUI from this method.
    protected void done() {

      String status;
      try {
        // Retrieve the return value of doInBackground.
        status = get();
        btnTrain.setEnabled(true);
        algorithmCopy = algorithm;

        for(Layer l : algorithm.network.layers)
          System.out.println(l);
      } catch (InterruptedException e) {
        // This is thrown if the thread's interrupted.
      } catch (ExecutionException e) {
        // This is thrown if we throw an exception
        // from doInBackground.
      }
    }

    // Can safely update the GUI from this method.
    protected void process(List<Integer> chunks) {
      // Here we receive the values that we publish().
      // They may come grouped in chunks.
      int mostRecentValue = chunks.get(chunks.size() - 1);

      epochBar.setValue(mostRecentValue);
    }
  }


  public ProgramWindow() {
    // Set object parameters
    epochBar.setMinimum(0);
    epochSlider.setValue(1000);
    currSelectionIndex = -1;

    // Set up the filename list
    File imageFolder = new File("./image-files/");
    File[] listOfFiles = imageFolder.listFiles();
    ArrayList<String> fileNames = new  ArrayList<String>();
    for(File f : listOfFiles)
      if(f.getName().endsWith(".mg"))
        fileNames.add(f.getName());
    Collections.sort(fileNames);
    DefaultListModel<String> fileListModel = new DefaultListModel<String>();
    for(String s : fileNames)
      fileListModel.addElement(s);
    fileList.setModel(fileListModel);
    fileList.setDragEnabled(false);

    // Create the Image Grid
    GridLayout imageGridLayout = new GridLayout(5, 3);
    imageGrid.setLayout(imageGridLayout);
    for(int i = 0; i < 15; i++)
      imageGrid.add(new JPanel());
    fileList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){
          // First find out which file we're looking at
          String fileName = "./image-files/" + fileList.getModel().getElementAt(fileList.getSelectedIndex());
          // Now read the file and set the colors
          List<Double> fileContents = new NetworkManager(1, 15, 1, 10, .1).readImageFile(fileName);
          for(int i = 0; i < 15; i++)
            imageGrid.getComponent(i).setBackground(fileContents.get(i) == .1 ? Color.white : Color.black);
          // Finally run the file through the current network
          try{
            imageOutput.setText(algorithmCopy.network.getResult(fileName));
            imageOutput.setCaretPosition(0);
          }catch(Exception ex){
            System.out.println(ex.getMessage());
          }
        }
      }
    });

    // Change listeners for sliders
    learningRateSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        lblLearningRate.setText("Learning Rate: " + String.valueOf((float)learningRateSlider.getValue() / 100.f));
      }
    });
    hiddenNodesSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        lblHiddenNodes.setText("Hidden Nodes: " + String.valueOf(hiddenNodesSlider.getValue()));
      }
    });
    epochSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        lblEpoch.setText("Epochs: " + String.valueOf(epochSlider.getValue()));
      }
    });

    // Training button code
    final PropertyChangeListener taskFinish = new PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())){
          System.out.println("TEST");
          int progress = (Integer) evt.getNewValue();
          epochBar.setValue(progress);
        }
      }
    };
    btnTrain.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnTrain.setEnabled(false);
        epochBar.setMaximum(epochSlider.getValue());
        epochBar.setValue(0);

        algorithmWorker worker = new algorithmWorker(epochSlider.getValue(), hiddenNodesSlider.getValue(),
            (double)learningRateSlider.getValue() / 100.d);
        worker.execute();
      }
    });
  }


  public static void main(String[] args) {
    JFrame frame = new JFrame("NetworkGUI");
    frame.setContentPane(new ProgramWindow().mainPanel);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Make it look nice
    try {
      UIManager.setLookAndFeel(
          UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }

    frame.pack();
    frame.setVisible(true);
  }

  public void setWebPanel(JPanel webPanel) {
    this.webPanel = webPanel;
  }

  private void createUIComponents() {
    // TODO: place custom component creation code here
  }
}
