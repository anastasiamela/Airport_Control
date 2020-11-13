package sample;

import java.util.Random;

public class Flight {
    private String flight_id;
    private String city;
    private int type_flight;
    private int type_airplane;
    private int departing_time; //how much should the flight be parked
    private String situation_flight = "";
    private int final_type_parking;
    private String final_pre_parking;
    private int final_number_parking;
    private String parked_pos = "";
    private String type_flight_string;
    private String type_airplane_string;
    private String first_time; //time of first contact
    private int parking_time; //when the flight gets parked
    private int random_departure_time;//when the flight will leave
    private int landing_time;
    private boolean check_for_10_min = false;

    public boolean isCheck_for_10_min() {
        return check_for_10_min;
    }

    public void setCheck_for_10_min(boolean check_for_10_min) {
        this.check_for_10_min = check_for_10_min;
    }

    public int getLanding_time() {
        return landing_time;
    }

    public void setLanding_time() {
        if (this.type_airplane == 1) this.landing_time = 6;
        else if (this.type_airplane == 2) this.landing_time = 4;
        else this.landing_time = 2;
    }

    public void setLanding_time(int landing_time) {
        this.landing_time = landing_time;
    }

    public int getRandom_departure_time() {
        return random_departure_time;
    }

    public void setRandom_departure_time() {
        int k = this.departing_time;

        //the flight must be parked in order to leave
        //so the min depends on the type of the airplane
        int min;
        if(this.type_airplane== 1) min = -k + 6;
        else if(this.type_airplane == 2) min = -k + 4;
        else min = -k + 2;

        int max = k;

        int help = k + getRandomNumberInRange(min, max); //How long will be parked finally
        this.random_departure_time = this.parking_time + help; //app decides when the flight will leave (this is the final time in minutes)
    }

    public int getParking_time() {
        return parking_time;
    }

    public void setParking_time(int parking_time) {
        this.parking_time = parking_time;
    }

    public String getFirst_time() {
        return first_time;
    }

    public void setFirst_time(String first_time) {
        this.first_time = first_time;
    }

    public String getType_flight_string() {
        return type_flight_string;
    }

    public void setType_flight_string(int type) {
        if(type == 1) this.type_flight_string = "PASSENGER";
        else if(type == 2) this.type_flight_string = "FREIGHT";
        else if(type == 3) this.type_flight_string = "PRIVATE";
    }

    public String getType_airplane_string() {
        return type_airplane_string;
    }

    public void setType_airplane_string(int type) {
        if(type==1) this.type_airplane_string = "Single Motor";
        else if(type==2) this.type_airplane_string = "Turboprop";
        else if(type==3) this.type_airplane_string = "Jet";
    }

    public String getFinal_pre_parking() {
        return final_pre_parking;
    }

    public void setFinal_pre_parking(String final_pre_parking) {
        this.final_pre_parking = final_pre_parking;
    }

    public String getParked_pos() {
        return parked_pos;
    }

    public void setParked_pos() {
        this.parked_pos = this.final_pre_parking + String.valueOf(this.final_number_parking);
    }

    public int getFinal_type_parking() {
        return final_type_parking;
    }

    public void setFinal_type_parking(int final_type_parking) {
        this.final_type_parking = final_type_parking;
    }

    public int getFinal_number_parking() {
        return final_number_parking;
    }

    public void setFinal_number_parking(int final_number_parking) {
        this.final_number_parking = final_number_parking;
    }

    public String getSituation_flight() {
        return situation_flight;
    }

    public void setSituation_flight(String situation_flight) {
        this.situation_flight = situation_flight;
    }

    public Flight() {
    }

    public Flight(String flight_id, String city, int type_flight, int type_airplane, int departing_time) {
        this.flight_id = flight_id;
        this.city = city;
        this.type_flight = type_flight;
        this.type_airplane = type_airplane;
        this.departing_time = departing_time;
    }

    public String getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(String flight_id) {
        this.flight_id = flight_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getType_flight() {
        return type_flight;
    }

    public void setType_flight(int type_flight) {
        this.type_flight = type_flight;
    }

    public int getType_airplane() {
        return type_airplane;
    }

    public void setType_airplane(int type_airplane) {
        this.type_airplane = type_airplane;
    }

    public int getDeparting_time() {
        return departing_time;
    }

    public void setDeparting_time(int departing_time) {
        this.departing_time = departing_time;
    }

    public int getRandomNumberInRange(int min, int max){
        if (min >= max){
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max-min) + 1) + min;
    }

}
