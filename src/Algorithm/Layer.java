package Algorithm;

import java.util.ArrayList;

/**
 * Created by lfnunley on 10/30/14.
 */
public class Layer {
  public ArrayList<Neuron> neurons;

  public boolean inputLayer;

  private static int idCounter = 0;
  public int id;

  /**
   * Creates Layer from parameters
   * @param a_numNodes Number of nodes in the layer
   */
  public Layer(int a_numNodes, boolean a_inputLayer){
    id = idCounter++;
    inputLayer = a_inputLayer;

    neurons = new ArrayList<Neuron>();
    for(int i = 0; i < a_numNodes; i++)
      neurons.add(new Neuron());
  }

  public Layer(boolean a_inputLayer, int a_id){
    id = a_id;
    if(idCounter <= id)
      idCounter = id + 1;

    inputLayer = a_inputLayer;

    neurons = new ArrayList<Neuron>();
  }

  /**
   * Links the output of this layer to the given layer
   * @param a_layer The layer which receives the output of this layer
   */
  public void linkLayer(Layer a_layer){
    for(Neuron n : neurons)
       a_layer.linkNode(n);
  }

  /**
   * Links the output of the given node to each node in this layer
   * @param a_node The node which sends its output
   */
  public void linkNode(Neuron a_node){
    for(Neuron n : neurons)
      n.linkNeuron(a_node);
  }
}
