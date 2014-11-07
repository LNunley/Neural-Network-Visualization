package Algorithm;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import nu.xom.*; // XOM XML Library

/**
 * Created by lfnunley on 10/30/2014.
 */
public class NeuralNetwork {
  private ArrayList<Layer> layers;
  private Double learningRate;
  private Neuron bias;

  /**
   * Creates Neural Network from parameters
   * @param a_layerPopulations Each element, starting from 0, represents a layer with that element's value of nodes
   * @param a_learningRate Learning rate, 0 > x >= 1
   */
  public NeuralNetwork(List<Integer> a_layerPopulations, double a_learningRate){
    // Populate Layers
    layers = new ArrayList<Layer>();
    for(int i : a_layerPopulations)
      layers.add(new Layer(i));

    learningRate = a_learningRate;

    // Link layer nodes
    for(int i = 0; i < layers.size() - 1; i++)
      layers.get(i).linkLayer(layers.get(i + 1));

    // Create and link bias
    bias = new Neuron();
    for(Layer l : layers)
      l.linkNode(bias);
  }

  /**
   * Loads Neural Network from file
   * @param a_fileName Relative path to file
   */
  public NeuralNetwork(String a_fileName){

  }

  public void saveToFile(String a_fileName){
    Element root = new Element("Fibonacci_Numbers");

    BigInteger low = BigInteger.ONE;
    BigInteger high = BigInteger.ONE;

    for(int i = 0; i < 100; i++){
      Element fibonacci = new Element("fibonacci");
      fibonacci.appendChild(low.toString());
      root.appendChild(fibonacci);

      BigInteger temp = high;
      high = high.add(low);
      low = temp;
    }

    Document doc = new Document(root);
    try {
      Serializer serializer = new Serializer(System.out, "ISO-8859-1");
      serializer.setIndent(4);
      serializer.setMaxLength(64);
      serializer.write(doc);
    }
    catch (IOException ex) {
      System.err.println(ex);
    }
  }
}
