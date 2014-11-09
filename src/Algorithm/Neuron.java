package Algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by lfnunley on 10/30/14.
 */
public class Neuron {
  public HashMap<Neuron, Feed> inputs;
  public ArrayList<Neuron> outputs;

  private static int idCounter = 0;
  public int id;

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

    id = idCounter++;
  }

  public Neuron(int a_id){
    inputs = new HashMap<Neuron, Feed>();
    outputs = new ArrayList<Neuron>();

    inputSum = 0;
    output = 0;

    id = a_id;
    if(id >= idCounter)
      idCounter = id + 1;
  }

  public void linkNeuron(Neuron a_Neuron){
    inputs.put(a_Neuron, new Feed((4 * new Random().nextDouble()) - 2, 0));

    //outputs.add(a_Neuron);
    a_Neuron.receiveInputLink(this);
  }

  public void receiveInputLink(Neuron a_Neuron){
    outputs.add(a_Neuron);
    //inputs.put(a_Neuron, new Feed((4 * new Random().nextDouble()) - 2, 0));
  }
}
