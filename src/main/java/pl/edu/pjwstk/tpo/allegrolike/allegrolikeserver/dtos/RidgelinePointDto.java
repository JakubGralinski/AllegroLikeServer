package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos;

public class RidgelinePointDto {
    private double x;
    private double y;

    public RidgelinePointDto(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
} 