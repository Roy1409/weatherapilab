// data model
public class WeatherModel {
    private double tempC;
    private double tempF;
    private String condition;
    private String iconURL;
    private String location;
    private double mintempc;
    private double mintempf;
    private double maxtempc;
    private double maxtempf;
    private String date;

    public WeatherModel(double tempC, double tempF, String condition, String iconURL, String location) {
        this.tempC = tempC;
        this.tempF = tempF;
        this.condition = condition;
        this.iconURL = iconURL;
        this.location = location;
    }

    public WeatherModel (double mintempC,double mintempf,double maxtempC,double maxtempf, String location,String date) {
        this.mintempc=mintempC;
        this.mintempf=mintempf;
        this.maxtempc=maxtempC;
        this.maxtempf=maxtempf;
        this.location=location;
        this.date=date;
    }

    public double getTempC() {
        return tempC;

    }

    public double getMaxtempc() {
        return maxtempc;
    }

    public double getMintempc() {
        return mintempc;
    }

    public double getMintempf() {
        return mintempf;
    }

    public double getMaxtempf() {
        return maxtempf;
    }

    public String getDate() {
        return date;
    }

    public double getTempF() {
        return tempF;
    }

    public String getCondition() {
        return condition;
    }

    public String getIconURL() {
        return iconURL;
    }

    public String getLocation() {
        return location;
    }
}
