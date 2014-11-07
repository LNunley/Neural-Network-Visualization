package Algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by lfnunley on 10/30/14.
 */
public class Neuron {
  private HashMap<Neuron, Feed> inputs;
  private ArrayList<Neuron> outputs;

  private int inputSum;
  private int output;

  /**
   * Initializes variables
   */
  public Neuron(){
    inputs = new HashMap<Neuron, Feed>();
    outputs = new ArrayList<Neuron>();

    inputSum = 0;
    output = 0;
  }

  public void linkNeuron(Neuron a_Neuron){
    outputs.add(a_Neuron);
    a_Neuron.receiveInputLink(this);
  }

  public void receiveInputLink(Neuron a_Neuron){
    inputs.put(a_Neuron, new Feed((4 * new Random().nextDouble()) - 2, 0));
  }
}
