package com.example.nt118project.response.Asset;

import com.google.gson.annotations.SerializedName;
public class Attributes {
    @SerializedName("sunIrradiance")
    private sunIrradiance sunIrradiance;
    @SerializedName("rainfall")
    private rainfall rainfall;
    @SerializedName("uVIndex")
    private uVIndex uVIndex;
    @SerializedName("PM25")
    private PM25 PM25;
    @SerializedName("sunAzimuth")
    private sunAzimuth sunAzimuth;
    @SerializedName("CO2")
    private CO2 CO2;
    @SerializedName("AQI_Predict")
    private AQI_Predict AQI_Predict;
    @SerializedName("sunZenith")
    private sunZenith sunZenith;
    @SerializedName("AQI")
    private AQI AQI;
    @SerializedName("PM10")
    private PM10 PM10;

    public com.example.nt118project.response.Asset.AQI getAQI() {
        return AQI;
    }

    public com.example.nt118project.response.Asset.AQI_Predict getAQI_Predict() {
        return AQI_Predict;
    }

    public com.example.nt118project.response.Asset.CO2 getCO2() {
        return CO2;
    }

    public com.example.nt118project.response.Asset.PM10 getPM10() {
        return PM10;
    }

    public com.example.nt118project.response.Asset.PM25 getPM25() {
        return PM25;
    }

    public com.example.nt118project.response.Asset.rainfall getRainfall() {
        return rainfall;
    }

    public com.example.nt118project.response.Asset.sunAzimuth getSunAzimuth() {
        return sunAzimuth;
    }

    public com.example.nt118project.response.Asset.sunIrradiance getSunIrradiance() {
        return sunIrradiance;
    }

    public com.example.nt118project.response.Asset.sunZenith getSunZenith() {
        return sunZenith;
    }

    public com.example.nt118project.response.Asset.uVIndex getuVIndex() {
        return uVIndex;
    }
    public double calculateAQI() {

        double aqi;

        // Weighted factors for each pollutant
        double pm25Weight = 0.3;
        double pm10Weight = 0.5;
        double co2Weight = 0.2;

        // Calculate AQI
        aqi = (pm25Weight * PM25.getValue()) +
                (pm10Weight * PM10.getValue()) +
                (co2Weight * CO2.getValue());

        return aqi;

    }
}
