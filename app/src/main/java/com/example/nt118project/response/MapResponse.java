package com.example.nt118project.response;



import java.util.Map;

public class MapResponse {
    private Map<String, MapData> additionalProps;

    public Map<String, MapData> getAdditionalProps() {
        return additionalProps;
    }

    public static class MapData {
        private double[] center;
        private double[] bounds;
        private int zoom;
        private int minZoom;
        private int maxZoom;
        private boolean boxZoom;
        private String geocodeUrl;
        private GeoJson geoJson;

        public double[] getCenter() {
            return center;
        }

        public double[] getBounds() {
            return bounds;
        }

        public int getZoom() {
            return zoom;
        }

        public int getMinZoom() {
            return minZoom;
        }

        public int getMaxZoom() {
            return maxZoom;
        }

        public boolean isBoxZoom() {
            return boxZoom;
        }

        public String getGeocodeUrl() {
            return geocodeUrl;
        }

        public GeoJson getGeoJson() {
            return geoJson;
        }
    }

    public static class GeoJson {
        private Map<String, Object> source;
        private Layer[] layers;

        public Map<String, Object> getSource() {
            return source;
        }

        public Layer[] getLayers() {
            return layers;
        }
    }

    public static class Layer {
        // Define layer properties here
    }
}

