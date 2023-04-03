package homework.HW1.Model;


import java.time.LocalDateTime;

public class AirCondition {

    private Double lon;

    private Double lat;

    private Double CO;

    private Double NO;

    private Double NO2;

    private Double O3;

    private Double SO2;

    private Double PM2_5;

    private Double PM10;

    private Double NH3;

    private LocalDateTime LocalDateTime;

    public AirCondition(Double lat, Double lon, LocalDateTime LocalDateTime, Double CO, Double NO, Double NO2, Double o3, Double SO2, Double PM2_5, Double PM10, Double NH3) {
        this.lat = lat;
        this.lon = lon;
        this.CO = CO;
        this.NO = NO;
        this.NO2 = NO2;
        O3 = o3;
        this.SO2 = SO2;
        this.PM2_5 = PM2_5;
        this.PM10 = PM10;
        this.NH3 = NH3;
        this.LocalDateTime = LocalDateTime;
    }

    public AirCondition(){  // empty constructor

    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public LocalDateTime getLocalDateTime(){ return this.LocalDateTime;}

    public void setLocalDateTime(LocalDateTime LocalDateTime) { this.LocalDateTime = LocalDateTime; }

    public Double getCO() {
        return CO;
    }

    public void setCO(Double CO) {
        this.CO = CO;
    }

    public Double getNO() {
        return NO;
    }

    public void setNO(Double NO) {
        this.NO = NO;
    }

    public Double getNO2() {
        return NO2;
    }

    public void setNO2(Double NO2) {
        this.NO2 = NO2;
    }

    public Double getO3() {
        return O3;
    }

    public void setO3(Double o3) {
        O3 = o3;
    }

    public Double getSO2() {
        return SO2;
    }

    public void setSO2(Double SO2) {
        this.SO2 = SO2;
    }

    public Double getPM2_5() {
        return PM2_5;
    }

    public void setPM2_5(Double PM2_5) {
        this.PM2_5 = PM2_5;
    }

    public Double getPM10() {
        return PM10;
    }

    public void setPM10(Double PM10) {
        this.PM10 = PM10;
    }

    public Double getNH3() {
        return NH3;
    }

    public void setNH3(Double NH3) {
        this.NH3 = NH3;
    }

    @Override
    public String toString() {
        return "AirCondition{" +
                "lon=" + lon +
                ", lat=" + lat +
                ", CO=" + CO +
                ", NO=" + NO +
                ", NO2=" + NO2 +
                ", O3=" + O3 +
                ", SO2=" + SO2 +
                ", PM2_5=" + PM2_5 +
                ", PM10=" + PM10 +
                ", NH3=" + NH3 +
                ", LocalDateTime=" + LocalDateTime +
                "} \n";
    }
}
