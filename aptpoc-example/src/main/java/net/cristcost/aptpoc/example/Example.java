package net.cristcost.aptpoc.example;

import net.cristcost.aptpoc.processor.ConvertToJavascript;

@ConvertToJavascript
public class Example {

  public static int sum(int a, int b) {
    return a + b;
  }

  public static int multiply(int a, int b) {
    return a * b;
  }

  // uncomment this method generates a waring on the annotation processor
  protected static int divide(int a, int b) {
    return a / b;
  }

  // uncomment this method to generate an error on the annotation processor
  // public int substract(int a, int b) {
  // return a / b;
  // }
}
