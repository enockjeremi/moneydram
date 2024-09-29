package com.moneydram.models;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moneydram.models.records.DataRecord;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Conversion {
  private final String apiURl;
  private List<String> historial = new ArrayList<>();
  Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

  public Conversion(String apiURl) {
    this.apiURl = apiURl;
  }

  private String encodeValue(String value) {
    return URLEncoder.encode(value, StandardCharsets.UTF_8);
  }

  public String toConvertion(String fromCoin, String toCoin, String amount) {
    String convertionString = apiURl + "/pair/" + encodeValue(fromCoin) + "/" + encodeValue(toCoin) + "/" + amount;
    System.out.println("\nCambiando moneda...\n");

    try {
      HttpClient client = HttpClient.newBuilder().build();
      HttpRequest request = HttpRequest.newBuilder(URI.create(convertionString)).build();
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      String json = response.body();

      DataRecord dataRecord = gson.fromJson(json, DataRecord.class);
      Data data = new Data(dataRecord);
      this.addConversionHistorial(data.getBaseCode(), data.getTargetCode(), data.getConversionResult());

      return data.toString();
    } catch (InterruptedException | IOException e) {
      return "Los sentimos hay un problema con la conecion a la API.";
    }
  }

  public void addConversionHistorial(String base, String target, double result) {
    historial.add(String.format("""
        %s -> %s
        - Total: %.2f (%s)
        """, base, target, result, target));
  }

  public void showConversionHistorial() {
    int i = 1;
    for (String item : historial) {
      System.out.println(i + ": " + item);
      i++;
    }
  }

  public boolean verifyCoin(String coinToVerify) {
    String[] coinCode = {"ARS", "CLP", "BOB", "USD", "BRL", "COP"};
    return Arrays.stream(coinCode).noneMatch(coin -> coin.equals(coinToVerify.toUpperCase()));
  }

  public boolean isNumber(String num) {
    try {
      Double.valueOf(num);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }


}
