package Algorithm;

/**
 * Created by lfnunley on 10/31/14.
 */
public class Feed {
  private double weight;
  private double input;

  /**
   * Assigns variables and creates object
   * @param a_weight Weight of the input
   * @param a_input Value of the input
   */
  public Feed(double a_weight, double a_input){
    weight = a_weight;
    input = a_input;
  }

  /**
   * Gives the product of weight and input
   * @return The product of weight and input
   */
  public double getProduct(){
    return (weight * input);
  }

  /**
   * Sets the input to the passed value
   * @param a_input The new value for input
   */
  public void setInput(double a_input){
    input = a_input;
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
  public double getInput() { return input; };
}
