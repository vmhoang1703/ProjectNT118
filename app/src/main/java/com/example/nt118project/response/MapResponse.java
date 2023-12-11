package com.example.nt118project.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MapResponse {
    private Options options;
    private String attribution;
    private String format;
    private String type;
    private List<String> tiles;

    // Các phương thức getter và setter ở đây

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTiles() {
        return tiles;
    }

    public void setTiles(List<String> tiles) {
        this.tiles = tiles;
    }

    public static class Options {
        private Default defaultOption;

        // Các phương thức getter và setter ở đây

        public Default getDefault() {
            return defaultOption;
        }

        public void setDefault(Default defaultOption) {
            this.defaultOption = defaultOption;
        }
        public static class Default {
            private List<Double> center;
            private List<Double> bounds;
            private int zoom;
            private int minZoom;
            private int maxZoom;
            private boolean boxZoom;
            private String geocodeUrl;

            // Các phương thức getter và setter ở đây

            public List<Double> getCenter() {
                return center;
            }

            public void setCenter(List<Double> center) {
                this.center = center;
            }

            public List<Double> getBounds() {
                return bounds;
            }

            public void setBounds(List<Double> bounds) {
                this.bounds = bounds;
            }

            public int getZoom() {
                return zoom;
            }

            public void setZoom(int zoom) {
                this.zoom = zoom;
            }

            public int getMinZoom() {
                return minZoom;
            }

            public void setMinZoom(int minZoom) {
                this.minZoom = minZoom;
            }

            public int getMaxZoom() {
                return maxZoom;
            }

            public void setMaxZoom(int maxZoom) {
                this.maxZoom = maxZoom;
            }

            public boolean isBoxZoom() {
                return boxZoom;
            }

            public void setBoxZoom(boolean boxZoom) {
                this.boxZoom = boxZoom;
            }

            public String getGeocodeUrl() {
                return geocodeUrl;
            }

            public void setGeocodeUrl(String geocodeUrl) {
                this.geocodeUrl = geocodeUrl;
            }
        }
    }
}

