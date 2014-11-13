package Algorithm;

/**
 * Created by lfnunley on 10/31/14.
 */
public class Feed {
  private int senderID;
  private int receiverID;

  private double weight;
  private double inputValue;

  /**
   * Assigns variables and creates object
   *
   * @param a_senderID ID of the sending neuron
   * @param a_receiverID ID of the recieving neuron
   * @param a_weight Weight of the input
   */
  public Feed(int a_senderID, int a_receiverID, double a_weight){
    inputValue = 0;

    weight = a_weight;
    senderID = a_senderID;
    receiverID = a_receiverID;
  }

  /**
   * Gives the product of weight and input
   * @return The product of weight and input
   */
  public double getProduct(){
    return (weight * inputValue);
  }

  /**
   * Sets the input to the passed value
   * @param a_input The new value for input
   */
  public void setInput(double a_input){
    inputValue = a_input;
  }

  /**
   * Sets the weight to the passed value
   * @param a_weight The new value for weight
   */
  public void setWeight(double a_weight){
    weight = a_weight;
  }

  /**
   * @return The value of weight
   */
  public double getWeight() { return weight; };

  /**
   * @return The value of input
   */
  public double getInputValue() { return inputValue; };
}
