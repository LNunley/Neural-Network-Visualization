package Algorithm;

import java.util.ArrayList;
import java.util.List;

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
}
