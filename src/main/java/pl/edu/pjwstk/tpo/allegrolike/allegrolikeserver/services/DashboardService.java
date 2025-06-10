package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.SalesDataDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.RidgelineCategoryDataDto;

import java.util.List;

public interface DashboardService {
    List<SalesDataDto> getSalesData(String periodType);
    List<RidgelineCategoryDataDto> getCategoryTrendData();
} 