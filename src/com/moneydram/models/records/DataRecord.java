package com.moneydram.models.records;

import java.util.Map;

public record DataRecord(
    String baseCode,
    String targetCode,
    double conversionRate,
    double conversionResult
) {
}
