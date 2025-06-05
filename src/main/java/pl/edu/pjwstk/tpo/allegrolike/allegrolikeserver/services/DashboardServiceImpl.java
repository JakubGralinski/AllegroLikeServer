package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services;

import org.springframework.stereotype.Service;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.RidgelineCategoryDataDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.RidgelinePointDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.SalesDataDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Override
    public List<SalesDataDto> getSalesData(String periodType) {
        // Mock data similar to sampleDataSets in AdminDashboard.tsx
        Map<String, List<SalesDataDto>> mockSalesData = new HashMap<>();
        mockSalesData.put("monthly", Arrays.asList(
                new SalesDataDto("2024-01", 1200),
                new SalesDataDto("2024-02", 1900),
                new SalesDataDto("2024-03", 1500),
                new SalesDataDto("2024-04", 2100),
                new SalesDataDto("2024-05", 1800)
        ));
        mockSalesData.put("quarterly", Arrays.asList(
                new SalesDataDto("2023-Q3", 4500),
                new SalesDataDto("2023-Q4", 5200),
                new SalesDataDto("2024-Q1", 4800),
                new SalesDataDto("2024-Q2", 5100)
        ));
        mockSalesData.put("yearly", Arrays.asList(
                new SalesDataDto("2020", 15000),
                new SalesDataDto("2021", 18000),
                new SalesDataDto("2022", 22000),
                new SalesDataDto("2023", 25000),
                new SalesDataDto("2024", 28000)
        ));
        mockSalesData.put("seasonal", Arrays.asList(
                new SalesDataDto("2024-01", 800),
                new SalesDataDto("2024-02", 1200),
                new SalesDataDto("2024-03", 1500),
                new SalesDataDto("2024-04", 1800),
                new SalesDataDto("2024-05", 2100),
                new SalesDataDto("2024-06", 2400),
                new SalesDataDto("2024-07", 2800),
                new SalesDataDto("2024-08", 2600),
                new SalesDataDto("2024-09", 2200),
                new SalesDataDto("2024-10", 1900),
                new SalesDataDto("2024-11", 1600),
                new SalesDataDto("2024-12", 1300)
        ));
        return mockSalesData.getOrDefault(periodType, new ArrayList<>());
    }

    @Override
    public List<RidgelineCategoryDataDto> getCategoryTrendData() {
        // Mock data similar to ridgelineData in AdminDashboard.tsx
        List<RidgelineCategoryDataDto> mockTrendData = new ArrayList<>();

        mockTrendData.add(new RidgelineCategoryDataDto("Electronics", Arrays.asList(
                new RidgelinePointDto(1, 10), new RidgelinePointDto(2, 30), new RidgelinePointDto(3, 20),
                new RidgelinePointDto(4, 40), new RidgelinePointDto(5, 25), new RidgelinePointDto(6, 35),
                new RidgelinePointDto(7, 30), new RidgelinePointDto(8, 45), new RidgelinePointDto(9, 20),
                new RidgelinePointDto(10, 15)
        )));
        mockTrendData.add(new RidgelineCategoryDataDto("Fashion", Arrays.asList(
                new RidgelinePointDto(1, 5), new RidgelinePointDto(2, 15), new RidgelinePointDto(3, 10),
                new RidgelinePointDto(4, 25), new RidgelinePointDto(5, 20), new RidgelinePointDto(6, 30),
                new RidgelinePointDto(7, 25), new RidgelinePointDto(8, 35), new RidgelinePointDto(9, 10),
                new RidgelinePointDto(10, 5)
        )));
        mockTrendData.add(new RidgelineCategoryDataDto("Home", Arrays.asList(
                new RidgelinePointDto(1, 8), new RidgelinePointDto(2, 18), new RidgelinePointDto(3, 12),
                new RidgelinePointDto(4, 28), new RidgelinePointDto(5, 22), new RidgelinePointDto(6, 32),
                new RidgelinePointDto(7, 28), new RidgelinePointDto(8, 38), new RidgelinePointDto(9, 12),
                new RidgelinePointDto(10, 8)
        )));
        mockTrendData.add(new RidgelineCategoryDataDto("Toys", Arrays.asList(
                new RidgelinePointDto(1, 3), new RidgelinePointDto(2, 8), new RidgelinePointDto(3, 6),
                new RidgelinePointDto(4, 12), new RidgelinePointDto(5, 10), new RidgelinePointDto(6, 15),
                new RidgelinePointDto(7, 12), new RidgelinePointDto(8, 18), new RidgelinePointDto(9, 6),
                new RidgelinePointDto(10, 3)
        )));

        return mockTrendData;
    }
} 