package use_case.weather;
import java.time.LocalDate;

public class WeatherInputData {
    private final String city;
    private final LocalDate selectedDate;

    public WeatherInputData(String city, LocalDate selectedDate){
        this.city = city;
        this.selectedDate = selectedDate;
    }
    public String getCity() {
        return city;
    }
    public LocalDate getSelectedDate() {
        return selectedDate;
    }
}
