package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.RidgelineCategoryDataDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.SalesDataDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.DashboardService;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*") // Allow all origins for simplicity, configure properly for production
public class DashboardController {

    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/sales")
    public ResponseEntity<List<SalesDataDto>> getSalesData(@RequestParam String periodType) {
        List<SalesDataDto> salesData = dashboardService.getSalesData(periodType);
        return ResponseEntity.ok(salesData);
    }

    @GetMapping("/category-trends")
    public ResponseEntity<List<RidgelineCategoryDataDto>> getCategoryTrendData() {
        List<RidgelineCategoryDataDto> trendData = dashboardService.getCategoryTrendData();
        return ResponseEntity.ok(trendData);
    }
} 