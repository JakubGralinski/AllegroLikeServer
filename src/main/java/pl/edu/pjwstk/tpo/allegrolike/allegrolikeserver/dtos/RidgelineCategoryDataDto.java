package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos;

import java.util.List;

public class RidgelineCategoryDataDto {
    private String category;
    private List<RidgelinePointDto> values;

    public RidgelineCategoryDataDto(String category, List<RidgelinePointDto> values) {
        this.category = category;
        this.values = values;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<RidgelinePointDto> getValues() {
        return values;
    }

    public void setValues(List<RidgelinePointDto> values) {
        this.values = values;
    }
} 