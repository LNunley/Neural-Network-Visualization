package Algorithm;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
      layers.add(new Layer(i, (layers.size() == 0)));

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
    layers = new ArrayList<Layer>();

    File file = new File(a_fileName);

    try{
      Builder parser = new Builder();
      Document doc = parser.build(file);
      System.err.println("Success");

      Element root = doc.getRootElement();
      learningRate = Double.valueOf(root.getAttribute("learning_rate").getValue());

      Elements eLayers = root.getFirstChildElement("layers").getChildElements("layer");

      for(int i = 0; i < eLayers.size(); i++)
        layers.add(new Layer(Boolean.valueOf(eLayers.get(i).getAttribute("input_layer").getValue()),
                   Integer.valueOf(eLayers.get(i).getAttribute("id").getValue())));
      /// TODO: Add neurons, link neurons

    }
    catch (ParsingException ex){
      System.err.println("Malformed document");
    }
    catch (IOException ex){
      System.err.println("Could not read file");
    }
  }

  public void saveToFile(String a_fileName){
    Element root = new Element("network");
    root.addAttribute(new Attribute("learning_rate", String.valueOf(learningRate)));

    Element eBias = new Element("bias");
    eBias.addAttribute(new Attribute("id", String.valueOf(bias.id)));
    root.appendChild(eBias);

    Element eNeurons = new Element("neurons");

    // Iterate through all neurons first, only way to do this is through layers
    for(Layer l : layers)
      for(Neuron n : l.neurons){
        Element eNeuron = new Element("neuron");

        eNeuron.addAttribute(new Attribute("id", String.valueOf(n.id)));

        Element eLayer = new Element("layer");
        eLayer.addAttribute(new Attribute("id", String.valueOf(l.id)));
        eNeuron.appendChild(eLayer);


        Element eInputs = new Element("inputs");
        for(Map.Entry<Neuron, Feed> entry : n.inputs.entrySet()){
          Element eInput = new Element("input");

          eInput.addAttribute(new Attribute("id", String.valueOf(entry.getKey().id)));
          eInput.addAttribute(new Attribute("weight", String.valueOf(entry.getValue().getWeight())));

          eInputs.appendChild(eInput);
        }
        eNeuron.appendChild(eInputs);

        eNeurons.appendChild(eNeuron);
      }

    root.appendChild(eNeurons);

    Element eLayers = new Element("layers");
    for(Layer l : layers){
      Element eLayer = new Element("layer");
      eLayer.addAttribute(new Attribute("id", String.valueOf(l.id)));
      eLayer.addAttribute(new Attribute("input_layer", String.valueOf(l.inputLayer)));

      Element eLayerNeurons = new Element("neurons");
      for(Neuron n : l.neurons){
        Element neuron = new Element("neuron");
        neuron.addAttribute(new Attribute("id", String.valueOf(n.id)));
        eLayerNeurons.appendChild(neuron);
      }

      eLayer.appendChild(eLayerNeurons);
      eLayers.appendChild(eLayer);
    }

    root.appendChild(eLayers);

    Document doc = new Document(root);
    try{
      OutputStream fOut = new FileOutputStream(a_fileName);
      PrintWriter fileOut = new PrintWriter(a_fileName);

      Serializer serializer = new Serializer(fOut, "UTF-8");
      serializer.setIndent(2);
      serializer.setMaxLength(64);
      serializer.write(doc);
    }
    catch (IOException ex) {
      System.err.println(ex);
    }
  }
}
