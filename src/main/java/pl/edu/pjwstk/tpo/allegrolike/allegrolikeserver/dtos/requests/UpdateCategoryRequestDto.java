package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests;

import jakarta.validation.constraints.NotNull;

public class UpdateCategoryRequestDto {

    @NotNull
    private String name;

    private Long parentCategoryId;

    public UpdateCategoryRequestDto(String name, Long parentCategoryId) {
        this.name = name;
        this.parentCategoryId = parentCategoryId;
    }

    public UpdateCategoryRequestDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
