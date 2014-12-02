package Algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by lfnunley on 11/11/2014.
 */
public class NeuronPool {
  private HashMap<Integer, Neuron> neurons;
  private HashMap<Integer, Feed> feeds;

  // In order of the input, value is the id key for the neuron which takes file input
  private ArrayList<Integer> inputIDs;

  private int neuronCounter;
  private int feedCounter;
  private int biasID;
  private Random rand;

  /**
   * Initializes member variables
   */
  public NeuronPool(){
    // initialize values
    neuronCounter = 0; // bias will be 0
    feedCounter = -1; // first feed will be 0
    rand = new Random();
    neurons = new HashMap<Integer, Neuron>();
    feeds = new HashMap<Integer, Feed>();
    inputIDs = new ArrayList<Integer>();

    // create bias
    neurons.put(neuronCounter, new Neuron(0));
    biasID = 0;
  }

  /**
   * Adds a neuron to the pool with no inputs or outputs except for the bias
   * @return ID of the created neuron
   */
  public int addNeuron(){
    neuronCounter++;
    neurons.put(neuronCounter, new Neuron(neuronCounter));

    // add feed to bias
    addFeed(biasID, neuronCounter);

    return neuronCounter;
  }

  public int addFeed(int senderID, int receiverID){
    feedCounter++;
    feeds.put(feedCounter, new Feed(senderID, receiverID, (4 * rand.nextDouble()) - 2)); // w = {-2, 2}
    return feedCounter;
  }
}
