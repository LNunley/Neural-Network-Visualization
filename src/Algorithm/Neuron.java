package Algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by lfnunley on 10/30/14.
 */
public class Neuron {
  public ArrayList<Neuron> outputs;

  public int id;

  private int inputSum;
  private int output;

  /**
   * Initializes variables
   * @param a_id The id of the neuron
   */
  public Neuron(int a_id){
    outputs = new ArrayList<Neuron>();

    inputSum = 0;
    output = 0;

    id = a_id;
  }

 /* public void linkNeuron(Neuron a_Neuron){
    inputs.put(a_Neuron, new Feed((4 * new Random().nextDouble()) - 2, 0));

    //outputs.add(a_Neuron);
    a_Neuron.receiveInputLink(this);
  }*/

  public void receiveInputLink(Neuron a_Neuron){
    outputs.add(a_Neuron);
    //inputs.put(a_Neuron, new Feed((4 * new Random().nextDouble()) - 2, 0));
  }
}
