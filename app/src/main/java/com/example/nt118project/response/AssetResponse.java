package com.example.nt118project.response;

import com.google.gson.annotations.SerializedName;

public class AssetResponse {
    @SerializedName("attributes")
    private Attributes attributes;

    public Attributes getAttributes() {
        return attributes;
    }

    public static class Attributes {
        @SerializedName("temperature")
        private MeasurementDouble temperature;
        @SerializedName("manufacturer")
        private MeasurementString manufacturer;

        @SerializedName("rainfall")
        private MeasurementDouble rainfall;

        @SerializedName("humidity")
        private MeasurementInt humidity;

        @SerializedName("windDirection")
        private MeasurementInt windDirection;

        @SerializedName("windSpeed")
        private MeasurementDouble windSpeed;

        @SerializedName("place")
        private MeasurementString place;

        public MeasurementDouble getTemperature() {
                return temperature;
            }
        public MeasurementString getManufacturer() {
            return manufacturer;
        }

        public MeasurementDouble getRainfall() {
            return rainfall;
        }

        public MeasurementInt getHumidity() {
            return humidity;
        }

        public MeasurementInt getWindDirection() {
            return windDirection;
        }

        public MeasurementDouble getWindSpeed() {
            return windSpeed;
        }

        public MeasurementString getPlace() {
            return place;
        }
    }

    public static class MeasurementDouble {
        @SerializedName("value")
        private double value;

        public double getValue() {
            return value;
        }
    }

    public static class MeasurementInt {
        @SerializedName("value")
        private int value;

        public int getValue() {
            return value;
        }
    }

     public static class MeasurementString {
        @SerializedName("value")
        private String value;

        public String getValue() {
            return value;
        }
    }

    public static class Location {
        @SerializedName("type")
        private String type;

        @SerializedName("coordinates")
        private double[] coordinates;

        public String getType() {
            return type;
        }

        public double[] getCoordinates() {
            return coordinates;
        }
    }
}
