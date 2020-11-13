package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.*;


public class Controller implements Initializable {
    @FXML
    private MenuBar menu;

    @FXML
    private Menu application;

    @FXML
    private MenuItem start;

    @FXML
    private MenuItem load;

    @FXML
    private MenuItem exit;

    @FXML
    private Menu details;

    @FXML
    private MenuItem d_gates;

    @FXML
    private MenuItem d_flights;

    @FXML
    private MenuItem d_delayed;

    @FXML
    private MenuItem d_holding;

    @FXML
    private MenuItem d_nextDepartures;

    @FXML
    private Label arrived_1;

    @FXML
    private Label available_1;

    @FXML
    private Label departing_1;

    @FXML
    private Label total_revenew;

    @FXML
    private Label time_label;

    @FXML
    private Label gates_2;

    @FXML
    private Label freight_2;

    @FXML
    private Label zone_a;

    @FXML
    private Label zone_b;

    @FXML
    private Label zone_c;

    @FXML
    private Label general_parking;

    @FXML
    private Label long_term;

    @FXML
    private ComboBox<String> text_kind_flight;

    @FXML
    private ComboBox<String> text_type_airplane;

    @FXML
    private TextField text_city;


    @FXML
    private TextField text_departure_time;

    @FXML
    private Button submit_button;

    @FXML
    private TextField text_flight_id;

    @FXML
    private TextArea console;
    private PrintStream ps;
    /*
    @FXML
    private Label messages_title;

    @FXML
    private Label message_label_1;

    @FXML
    private Label message_label_2;

     */

    int minutes = 0;

    Parking[] our_park = new Parking[7];
    Hashtable<Integer, Integer> costs = new Hashtable<Integer, Integer>();

    ArrayList<Flight> our_flights = new ArrayList<Flight>();
    ArrayList<Gates> our_gates = new ArrayList<Gates>();
    int different_parking_spaces;
    int total_parking_available;

    int number_parked = 0;
    int number_holding = 0;
    int number_landing = 0;
    int departing_10_minutes = 0;

    int total_cost =0;

    int number_of_files=10;

    Hashtable<String, Flight> hash = new Hashtable<String, Flight>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ps = new PrintStream(new Console(console));
        System.setOut(ps);
        System.setErr(ps);

        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("The application is starting...");
                init("SCENARIO-ID");
            }
        });

        d_flights.setOnAction(e -> AlertBoxFlights.display("Details of Flights", our_flights));

        d_gates.setOnAction(e -> AlertBoxGates.display("Details of all Gates (parking spaces)", our_flights, hash, our_gates));

        d_holding.setOnAction(e -> AlertBoxHolding.display("Holding Flights", our_flights));

        d_delayed.setOnAction(e -> AlertBoxDelayed.display("Delayed Flights", our_flights, this.minutes));

        d_nextDepartures.setOnAction(e -> AlertBoxNextDeparture.display("Next Departures in 10 minutes", our_flights, this.minutes));

        exit.setOnAction(e -> Platform.exit());

        text_kind_flight.getItems().clear();
        text_kind_flight.getItems().addAll("Passenger", "Freight", "Private");
        text_kind_flight.getSelectionModel().select("Passenger");

        text_type_airplane.getItems().clear();
        text_type_airplane.getItems().addAll("Single Motor", "Turboprop", "Jet");
        text_type_airplane.getSelectionModel().select("Single Motor");

        submit_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //System.out.println("submit button is clicked");
                boolean submitted = submit_flight();
                AlertBoxSubmitFlight.display("Submit a flight", submitted);
                text_flight_id.clear();
                text_city.clear();
                text_departure_time.clear();
                text_kind_flight.getSelectionModel().select("Passenger");
                text_type_airplane.getSelectionModel().select("Single Motor");

            }
        });

        load.setOnAction(e -> {
            String result = AlertBoxLoad.display("Select Scenario-ID to load");
            //System.out.println(result);
            String param = String.valueOf(result);
            System.out.println("The application is starting...");
            init(param);
        });

    }

    /**Initialize all project structure based on the appropriate files and the timer
     *
     * @param id the SCENARIO-ID for the files airport and setup
     */

    public void init(String id){
        //Clear everything
        our_flights.removeAll(our_flights);
        our_gates.removeAll(our_gates);
        costs.clear();
        hash.clear();
        this.minutes = 0;
        this.different_parking_spaces=0;
        this.total_parking_available=0;

        this.number_parked = 0;
        this.number_holding = 0;
        this.number_landing = 0;
        this.departing_10_minutes = 0;

        this.total_cost=0;

        //INITIALIZE TIMER
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5000), e -> increaseTimer()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        different_parking_spaces = readairport(id) + 1;
        if (different_parking_spaces < 0) {
            System.out.println("ERROR: The airport scenario file must have a maximum of 7 lines!");
        } else {
            System.out.println("This airport has " + different_parking_spaces + " different type(s) of parking spaces.");
            if (different_parking_spaces > 0) Arrays.sort(our_park);
            readflights(id);
            inform_section_2();
            inform_section_1();
        }
    }

    /**Initialize the array of the objects of Parking (info for parking type spaces)
     *
     * @param id the SCENARIO-ID for the file airport
     * @return the number of different types of parking or -2 if there are more than 7 different types which is wrong
     */

    public int readairport(String id) {
        String line = "";
        String splitBy = ",";
        int i = -1;
        try {
            BufferedReader br = new BufferedReader(new FileReader("airport_" + id +".txt"));
            while ((line = br.readLine()) != null) {
                String[] help = line.split(splitBy);
                i++;
                if (i == 7) {
                    return -2;
                }
                int type = Integer.parseInt(help[0]);
                int total_number = Integer.parseInt(help[1]);
                our_park[i] = new Parking(type, total_number, Integer.parseInt(help[2]), help[3], 0);
                our_park[i].setMax_time(type);
                our_park[i].setType_flight(type);
                our_park[i].setType_airplane(type);
                total_parking_available += total_number;
                String pre = our_park[i].getPre_parking();
                for (int j = 0; j < total_number; j++) {
                    Gates g = new Gates(pre + String.valueOf(j), "Empty", "", "");
                    our_gates.add(g);
                }
                costs.put(type, our_park[i].getCost_parking());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**Initialize the ArrayList of flights (read the first flights and call check_flight for each one)
     *
     * @param id the SCENARIO-ID for the file setup
     */
    public void readflights(String id) {
        String line = "";
        String splitBy = ",";
        int number_flights_file = 0, flights_parked = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("setup_" + id +".txt"));
            while ((line = br.readLine()) != null) {
                String[] help = line.split(splitBy);
                Flight fl = new Flight(help[0].trim(), help[1].trim(), Integer.parseInt(help[2].trim()), Integer.parseInt(help[3].trim()), Integer.parseInt(help[4].trim()));
                our_flights.add(fl);
                fl.setType_airplane_string(fl.getType_airplane());
                fl.setType_flight_string(fl.getType_flight());
                number_flights_file++;
                fl.setSituation_flight("");
                if (check_flight(fl, "PARKED")) {
                    flights_parked++;
                    this.number_parked++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**Check for a flight if there is enough parking space
     *
     * @param flight the flight about to be parked
     * @param state LANDING if the flight isn't contained in the setup file and PARKED if the flight is contained in the setup file (so its state will be immediately changed to PARKED)
     * @return true if the flight finds a parking space (PARKED or LANDING), false if the flight becomes HOLDING
     */
    public boolean check_flight(Flight flight, String state) {

        for (int i = 0; i < different_parking_spaces; i++) {

            //Check capacity and time
            if (our_park[i].getCurrent_number() < our_park[i].getTotal_number() && flight.getDeparting_time() <= our_park[i].getMax_time()) {
                int flight_airplane = flight.getType_airplane();
                String parking_airplane = our_park[i].getType_airplane();

                //Check type o airplane
                if ((flight_airplane == 1 && (parking_airplane == "SINGLEMOTORS" || parking_airplane == "ALL")) ||
                        (flight_airplane == 2 && (parking_airplane == "TURBOPROP_OR_JET" || parking_airplane == "ALL")) ||
                        (flight_airplane == 3 && (parking_airplane == "TURBOPROP_OR_JET" || parking_airplane == "ALL"))) {
                    int flight_type = flight.getType_flight();
                    String parking_type_flight = our_park[i].getType_flight();

                    //Check type of flight
                    if ((flight_type == 1 && (parking_type_flight == "PASSENGERS" || parking_type_flight == "ALL")) ||
                            (flight_type == 2 && (parking_type_flight == "FREIGHT" || parking_type_flight == "ALL" || parking_type_flight == "FREIGHT_OR_PERSONAL")) ||
                            (flight_type == 3 && (parking_type_flight == "ALL" || parking_type_flight == "FREIGHT_OR_PERSONAL"))) {

                        //So there is a parking space
                        flight.setSituation_flight(state);
                        int current = our_park[i].getCurrent_number();
                        our_park[i].setCurrent_number(current + 1);
                        total_parking_available--;
                        //Save the final parking place of the flight
                        flight.setFinal_type_parking(our_park[i].getType_parking());
                        flight.setFinal_pre_parking(our_park[i].getPre_parking());
                        flight.setFinal_number_parking(current + 1);
                        flight.setParked_pos();
                        hash.put(flight.getParked_pos(), flight);

                        //in case our flight gets parked
                        if (state == "PARKED") {
                            flight.setParking_time(minutes);
                            flight.setRandom_departure_time();
                            int time_should = flight.getParking_time() + flight.getDeparting_time();
                            //System.out.println(flight.getFlight_id() + "  *  "+ flight.getRandom_departure_time() + "  *  " + time_should);
                            System.out.println("The flight with ID "+ flight.getFlight_id() + " is now parked.");

                            int hours = time_should/60;
                            int min = time_should%60;
                            if (hours < 10){
                                if (min < 10) System.out.println("The flight with ID "+ flight.getFlight_id()+" will leave at 0" + hours + ":0" + min);
                                else System.out.println("The flight with ID "+ flight.getFlight_id()+" will leave at 0" + hours + ":" + min);
                            }
                            else{
                                if (min < 10) System.out.println("The flight with ID "+ flight.getFlight_id()+" will leave at " + hours + ":0" + min);
                                else System.out.println("The flight with ID "+ flight.getFlight_id()+" will leave at " + hours + ":" + min);
                            }
                        }

                        //in case our flight gets landing state
                        if (state == "LANDING") {
                            flight.setLanding_time();
                            this.number_landing++;
                            System.out.println("The flight with ID "+ flight.getFlight_id() + " is landing.");
                            System.out.println("The landing time for the flight is " + flight.getLanding_time()+ " minutes.");
                        }

                        return true;
                    }
                }
            }
        }
        //if the flight gets situation HOLDING for the first time (first contact)
        if (flight.getSituation_flight() == "") {
            this.number_holding++;
            flight.setFirst_time(String.valueOf(this.minutes));
            System.out.println("There is not enough parking space for the flight with ID "+ flight.getFlight_id() + " .");
            System.out.println("The flight with ID "+ flight.getFlight_id() + " is holding.");
        }

        flight.setSituation_flight("HOLDING");
        return false;
    }

    /**Reconstructs the middle left section of the application's screen
     *
     */
    public void inform_section_2() {
        for (int i = 0; i < different_parking_spaces; i++) {
            int type = our_park[i].getType_parking();
            int total = our_park[i].getTotal_number();
            int current = our_park[i].getCurrent_number();
            if (type == 1) gates_2.setText("Gates:  " + current + " / " + total);
            if (type == 2) freight_2.setText("Freight Gates:  " + current + " / " + total);
            if (type == 3) zone_a.setText("Zone A:  " + current + " / " + total);
            if (type == 4) zone_b.setText("Zone B:  " + current + " / " + total);
            if (type == 5) zone_c.setText("Zone C:  " + current + " / " + total);
            if (type == 6) general_parking.setText("General Parking Space:  " + current + " / " + total);
            if (type == 7) long_term.setText("Long Term:  " + current + " / " + total);
        }
    }
    /**Reconstructs the upper section of the application's screen
     *
     */
    public void inform_section_1() {
        available_1.setText("Available Parking Spaces:  " + this.total_parking_available);
        arrived_1.setText("Arrived Flights:  " + this.our_flights.size());
        total_revenew.setText("Total Revenew:  " + this.total_cost);
        departing_1.setText("Departing Flights in 10 minutes:  "+ this.departing_10_minutes);
        if (this.minutes <10) time_label.setText("Time: 00:0" + this.minutes);
        else if(this.minutes < 60) time_label.setText("Time: 00:" + this.minutes);
        else {
            int hours = this.minutes/60;
            int min = this.minutes%60;
            if (hours < 10){
                if (min < 10) time_label.setText("Time: 0" + hours + ":0" + min);
                else time_label.setText("Time: 0" + hours + ":" + min);
            }
            else{
                if (min < 10) time_label.setText("Time: " + hours + ":0" + min);
                else time_label.setText("Time: " + hours + ":" + min);
            }
        }
    }

    /**Every minute we check if a flight is leaving, if a holding flight can become parked, if a landing flight is time to become parked
     *
     */
    public void increaseTimer() {
        this.minutes++;
        boolean newfree = checkForDepartures();
        if(newfree && this.number_holding >= 0) {
            check_the_holding();
        }
        if (number_landing >= 0) check_the_landing();
        inform_section_2();
        inform_section_1();
    }

    /**check for every parked flight if it is time to leave
     *
     * @return true if one flight or more left, false if no flight left
     */
    public boolean checkForDepartures() {
        //Set<String> keys = hash.keySet();
        boolean departed = false;

        Set<String> keys = hash.keySet();
        Iterator it = keys.iterator();

        while(it.hasNext()){
            String key = (String) it.next();
            Flight flight = hash.get(key);

            //check if the flight is leaving in 10 minutes
            int should_leave_time = flight.getDeparting_time() + flight.getParking_time();
            if(should_leave_time - this.minutes <= 10 && flight.isCheck_for_10_min()==false) {
                flight.setCheck_for_10_min(true);
                departing_10_minutes++;
            }

            if (flight.getRandom_departure_time() == this.minutes) {
                departed = true; //definitely a flight has leaved
                it.remove();
                this.number_parked--;

                int type_of_parking = flight.getFinal_type_parking();
                int cost_of_parking = costs.get(type_of_parking);

                for(int j=0; j<different_parking_spaces; j++){
                    if(type_of_parking == our_park[j].getType_parking()){
                        int current_places = our_park[j].getCurrent_number();
                        our_park[j].setCurrent_number(current_places - 1);
                    }

                }

                int diff;

                //if the flight left earlier
                if (this.minutes < should_leave_time) {
                    diff = should_leave_time - this.minutes;
                    if (diff >= 25) {
                        this.total_cost += 0.8 * cost_of_parking; //discount 20%
                        System.out.println("The flight with ID "+ flight.getFlight_id() + " is leaving earlier.");
                        System.out.println("The flight with ID "+ flight.getFlight_id() + " got 20% discount and payed "+ 0.8*cost_of_parking + ".");
                    }
                    else if (diff >= 10) {
                        this.total_cost += 0.9 * cost_of_parking;//discount 10%
                        System.out.println("The flight with ID "+ flight.getFlight_id() + " is leaving earlier.");
                        System.out.println("The flight with ID "+ flight.getFlight_id() + " got 10% discount and payed "+ 0.9*cost_of_parking + ".");
                    }
                    else {
                        this.total_cost += cost_of_parking;
                        System.out.println("The flight with ID "+ flight.getFlight_id() + " is leaving earlier.");
                        System.out.println("The flight with ID "+ flight.getFlight_id() + " did not get a discount and payed "+ cost_of_parking + ".");
                    }
                }
                else if (this.minutes == should_leave_time){
                    this.total_cost += cost_of_parking;
                    System.out.println("The flight with ID "+ flight.getFlight_id() + " is leaving.");
                    System.out.println("The flight with ID "+ flight.getFlight_id() + " did not get a discount and payed "+ cost_of_parking + ".");
                }
                else{
                    this.total_cost += cost_of_parking;
                    System.out.println("The flight with ID "+ flight.getFlight_id() + " is leaving with a delay.");
                    System.out.println("The flight with ID "+ flight.getFlight_id() + " did not get a discount and payed "+ cost_of_parking + ".");
                }

                //A parking space gets free
                this.total_parking_available++;


                our_flights.remove(flight);
                departing_10_minutes--;
            }
        }
        return departed;
    }

    /**check for every holding flight if it can become parked
     *
     */
    public void check_the_holding(){
        //this way priority is considered because even holding flights enter the arraylist in order
        for (Flight flight: our_flights){
            if(flight.getSituation_flight() == "HOLDING") {
                boolean became_landing = check_flight(flight, "LANDING");

                //if flight becomes landing decrease the total number of holding
                if (became_landing) this.number_holding--;
            }
        }
    }

    /**check for every landing flight if it is time to become parked
     *
     */
    public void check_the_landing(){
        for(Flight flight: our_flights){
            if(flight.getSituation_flight() == "LANDING"){
                int remaining_time = flight.getLanding_time() - 1;
                if (remaining_time == 0) {
                    flight.setSituation_flight("PARKED");
                    flight.setParking_time(minutes);
                    this.number_parked++;
                    this.number_landing--;
                    System.out.println("The flight with ID "+ flight.getFlight_id() + " is now parked.");
                }
                else flight.setLanding_time(remaining_time);
            }
        }
    }

    /**Reads the elements of the input in the middle right section of the application's screen and tries to create a new flight
     *
     * @return true if all the input elements were right and a new flight was created
     */
    public boolean submit_flight(){
        if (!text_flight_id.getText().toString().trim().equals("") &&
            !text_city.getText().toString().equals("") &&
            !text_departure_time.getText().toString().trim().equals("")){
            String flight_id = text_flight_id.getText().replaceAll("\\s+", "");
            String flight_city = text_city.getText().toString();
            String time = text_departure_time.getText().toString().trim();
            int departure_time;

            String type_fl = text_kind_flight.getValue();

            int type_flight=0;
            if (type_fl == "Passenger") type_flight = 1;
            else if (type_fl == "Freight") type_flight = 2;
            else if (type_fl == "Private") type_flight = 3;


            String type_pl = text_type_airplane.getValue();

            int type_airplane=0;
            if(type_pl == "Single Motor") type_airplane = 1;
            else if(type_pl == "Turboprop") type_airplane = 2;
            else if(type_pl == "Jet") type_airplane = 3;

            if (time.matches("[0-9]*")){
                departure_time = Integer.valueOf(time);
            }
            else return false;

            Flight flight = new Flight(flight_id, flight_city, type_flight, type_airplane, departure_time);
            flight.setType_airplane_string(flight.getType_airplane());
            flight.setType_flight_string(flight.getType_flight());
            flight.setSituation_flight("");
            our_flights.add(flight);
            check_flight(flight, "LANDING");
            return true;
        }
        else{
            //System.out.println("you have to complete all the fields");
            return  false;
        }

    }

    public class Console extends OutputStream {
        private TextArea console;

        public Console(TextArea console) {
            this.console = console;
        }

        public void appendText(String valueOf) {
            Platform.runLater(() -> console.appendText(valueOf));
        }

        public void write(int b) throws IOException {
            appendText(String.valueOf((char)b));
        }
    }


}