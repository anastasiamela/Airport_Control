package sample;

public class Parking implements Comparable<Parking> {

    private int type_parking;
    private int total_number;
    private int cost_parking;
    private String pre_parking;
    private int current_number;
    private int max_time;
    private String type_airplane;
    private String type_flight;


    public String getType_airplane() {
        return type_airplane;
    }

    public void setType_airplane(int type) {
        if (type == 1)  this.type_airplane = "TURBOPROP_OR_JET";
        if (type == 2)  this.type_airplane = "TURBOPROP_OR_JET";
        if (type == 3)  this.type_airplane = "TURBOPROP_OR_JET";
        if (type == 4)  this.type_airplane = "TURBOPROP_OR_JET";
        if (type == 5)  this.type_airplane = "SINGLEMOTORS";
        if (type == 6)  this.type_airplane = "ALL";
        if (type == 7)  this.type_airplane = "ALL";
    }

    public String getType_flight() {
        return type_flight;
    }

    public void setType_flight(int type) {
        if (type == 1)  this.type_flight = "PASSENGERS";
        if (type == 2)  this.type_flight = "FREIGHT";
        if (type == 3)  this.type_flight = "PASSENGERS";
        if (type == 4)  this.type_flight = "ALL";
        if (type == 5)  this.type_flight = "ALL";
        if (type == 6)  this.type_flight = "ALL";
        if (type == 7)  this.type_flight = "FREIGHT_OR_PERSONAL";
    }

    public Parking() {
    }

    public int getMax_time() {
        return max_time;
    }

    public void setMax_time(int type) {
        if (type== 1)  this.max_time = 45;
        if (type== 2)  this.max_time = 90;
        if (type== 3)  this.max_time = 90;
        if (type== 4)  this.max_time = 120;
        if (type== 5)  this.max_time = 180;
        if (type== 6)  this.max_time = 240;
        if (type== 7)  this.max_time = 600;
    }

    public Parking(int type_parking, int total_number, int cost_parking, String pre_parking, int current_number) {
        this.type_parking = type_parking;
        this.total_number = total_number;
        this.cost_parking = cost_parking;
        this.pre_parking = pre_parking;
        this.current_number = current_number;
    }


    public int getType_parking() {
        return type_parking;
    }

    public void setType_parking(int type_parking) {
        this.type_parking = type_parking;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public int getCost_parking() {
        return cost_parking;
    }

    public void setCost_parking(int cost_parking) {
        this.cost_parking = cost_parking;
    }

    public String getPre_parking() {
        return pre_parking;
    }

    public void setPre_parking(String pre_parking) {
        this.pre_parking = pre_parking;
    }

    public int getCurrent_number() {
        return current_number;
    }

    public void setCurrent_number(int current_number) {
        this.current_number = current_number;
    }
    public int compareTo(Parking compareParking){
        int compareMaxTime = ((Parking) compareParking).getMax_time();

        //ascending order
        return this.max_time - compareMaxTime;

        //descending order
        //return compareCost - this.cost_parking;
    }
}
