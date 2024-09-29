package com.moneydram.main;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moneydram.models.Conversion;
import com.moneydram.models.Data;
import com.moneydram.models.TextUI;
import com.moneydram.models.records.DataRecord;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    String EXIT = "salir";
    String ResetLOOP = "reset";

    Dotenv env = Dotenv.load();
    String API_URI = env.get("API_URI");

    Conversion conversion = new Conversion(API_URI);

    System.out.println("""
        
        Bienvenido a MoneyDram tu casa de cambio virtual.
        - Peso Argentino    (ARS)
        - Peso Chileno      (CLP)
        - Boliviano         (BOB)
        - Dolar USA         (USD)
        - Real Brazileño    (BRL)
        - Peso Colombiano   (COP)
        
        - Escribe "salir" para finalizar sesión.
        """);

    while (true) {
      String fromCoin;
      String toCoin;
      String amount;

      //Desde:
      TextUI fromCoinUI = new TextUI(
          false,
          "Que moneda deseas cambiar: ",
          "La moneda que has introducido no es correcta.",
          conversion);
      fromCoin = fromCoinUI.inputUI();
      if (fromCoin.equals(EXIT)) break;
      if (fromCoin.equals(ResetLOOP)) continue;

      //A:
      TextUI toCoinUI = new TextUI(
          false,
          "A que moneda vas cambiar: ",
          "La moneda que has introducido no es correcta.",
          conversion);
      toCoin = toCoinUI.inputUI();
      if (toCoin.equals(EXIT)) break;
      if (toCoin.equals(ResetLOOP)) continue;

      //Monto:
      TextUI amountUI = new TextUI(
          true,
          "Monto a convertir: ",
          "No has colocado un monto correcto.",
          conversion);
      amount = amountUI.inputUI();
      if (amount.equals(EXIT)) break;
      if (amount.equals(ResetLOOP)) continue;

      try {
        String result = conversion.toConvertion(fromCoin, toCoin, amount);
        System.out.println(result);
      } catch (NullPointerException e) {
        System.out.println("""
            Los sentimos, no hemos podido procesar tu conversión monetaria.
            Es posible que debas verificar tu conexión a internet.
            """);
        break;
      }

      Scanner keyboard = new Scanner(System.in);
      System.out.println("¿Deseas ver el historial de conversiones? (Si)");
      String historialSelect = keyboard.nextLine();
      if (historialSelect.equals("si")) {
        conversion.showConversionHistorial();
      }
    }
    System.out.println("\nSesión cerrada exitosamente.\r");
  }
}
