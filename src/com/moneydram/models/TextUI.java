package com.moneydram.models;

import java.util.Scanner;

public class TextUI {
  String mainText;
  String errorText;
  Scanner keyboard = new Scanner(System.in);
  Conversion conversion;
  boolean isAmount;

  public TextUI(boolean isAmount, String mainText, String errorText, Conversion conversion) {
    this.mainText = mainText;
    this.errorText = errorText;
    this.conversion = conversion;
    this.isAmount = isAmount;
  }

  public String inputUI() {
    System.out.println(mainText);
    String coin = keyboard.nextLine();
    if (coin.equals("salir")) return "salir";
    if (!isAmount) {
      if (conversion.verifyCoin(coin)) {
        System.out.println(errorText + "\n");
        return "reset";
      }
    } else {
      if (!conversion.isNumber(coin)) {
        System.out.println("No has colocado un monto correcto.\n");
        return "reset";
      }
    }
    return coin;
  }
}
