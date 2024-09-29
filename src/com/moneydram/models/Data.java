package com.moneydram.models;

import com.moneydram.models.records.DataRecord;

import java.util.Map;

public class Data {
  private String baseCode;
  private String targetCode;
  private double conversionResult;

  public Data(DataRecord record) {
    this.baseCode = record.baseCode();
    this.targetCode = record.targetCode();
    this.conversionResult = record.conversionResult();
  }

  @Override
  public String toString() {
    return String.format("""
            Resultado de conversion:
            - %s -> %s
            - Total: %.2f (%s)
            """,
        this.renamedCoin(this.getBaseCode()),
        this.renamedCoin(this.getTargetCode()),
        this.getConversionResult(),
        this.getTargetCode()
    );
  }

  public String renamedCoin(String coin) {
    Map<String, String> coinsCode = Map.of(
        "ARS", "Peso Argentino",
        "BRL", "Real Brazileño",
        "BOB", "Boliviano",
        "CLP", "Peso Chileno",
        "COP", "Peso Colombiano",
        "USD", "Dolar Estadounidense"
    );

    return coinsCode.get(coin);
  }

  public String getBaseCode() {
    return baseCode;
  }
  public String getTargetCode() {
    return targetCode;
  }
  public double getConversionResult() {
    return conversionResult;
  }
}
