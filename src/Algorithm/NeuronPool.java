package Algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lfnunley on 11/11/2014.
 */
public class NeuronPool {
  private HashMap<Integer, Neuron> neurons;
  private ArrayList<Feed> feeds;
  private int idCounter;
  private int biasID;

  /**
   * Initializes member variables
   */
  public NeuronPool(){
    idCounter = -1; // first neuron will be 0
    neurons = new HashMap<Integer, Neuron>();
    feeds = new ArrayList<Feed>();
    biasID = addNeuron();
  }

  /**
   * Adds a neuron to the pool with no inputs or outputs except for the bias
   * @return ID of the created neuron
   */
  public int addNeuron(){
    idCounter++;
    neurons.put(idCounter, new Neuron(idCounter));
    return idCounter;
  }
}
