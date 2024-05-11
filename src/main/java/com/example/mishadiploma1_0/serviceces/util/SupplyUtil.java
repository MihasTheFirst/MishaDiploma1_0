package com.example.mishadiploma1_0.serviceces.util;

import com.example.mishadiploma1_0.entity.Measure;

import java.util.ArrayList;
import java.util.List;

public class SupplyUtil {
    public static Measure getMeasure (String measure) {
        return switch (measure) {
            case "kg" -> Measure.KG;
            case "g" -> Measure.G;
            case "pcs" -> Measure.PCS;
            case "l" -> Measure.L;
            case "ml" -> Measure.ML;
            default -> null;
        };
    }

    public static List<Measure> getMeasures (List <String> measures) {
        List<Measure> measureList = new ArrayList<>();
        for (String measureString: measures) {
            Measure measure = getMeasure(measureString);
            measureList.add(measure);
        }
        return measureList;
    }
}
