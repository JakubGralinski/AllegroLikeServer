package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos;

import java.util.List;
import java.util.Map;

public class ChartDataDTO {
    private List<String> labels;
    private List<DatasetDTO> datasets;

    public ChartDataDTO() {
    }

    public ChartDataDTO(List<String> labels, List<DatasetDTO> datasets) {
        this.labels = labels;
        this.datasets = datasets;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<DatasetDTO> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<DatasetDTO> datasets) {
        this.datasets = datasets;
    }

    public static class DatasetDTO {
        private String label;
        private List<Double> data;
        private String backgroundColor;
        private String borderColor;
        private boolean fill;

        public DatasetDTO() {
        }

        public DatasetDTO(String label, List<Double> data, String backgroundColor, String borderColor, boolean fill) {
            this.label = label;
            this.data = data;
            this.backgroundColor = backgroundColor;
            this.borderColor = borderColor;
            this.fill = fill;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public List<Double> getData() {
            return data;
        }

        public void setData(List<Double> data) {
            this.data = data;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public String getBorderColor() {
            return borderColor;
        }

        public void setBorderColor(String borderColor) {
            this.borderColor = borderColor;
        }

        public boolean isFill() {
            return fill;
        }

        public void setFill(boolean fill) {
            this.fill = fill;
        }
    }
} 