package sample;

public class Gates {
    private String gateId;
    private String state = "Empty";
    private String flightId = "";
    private String departure = "";

    public Gates() {
    }

    public Gates(String gateId, String state, String flightId, String departure) {
        this.gateId = gateId;
        this.state = state;
        this.flightId = flightId;
        this.departure = departure;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getGateId() {
        return gateId;
    }

    public void setGateId(String gateId) {
        this.gateId = gateId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


}
