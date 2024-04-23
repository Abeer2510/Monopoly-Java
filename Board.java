
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Board {

    Board(){}
    
    public static ArrayList<String> makeBoard(){
        ArrayList<String> boardNames = new ArrayList<>();

        // Adding space names based on Monopoly board layout
        boardNames.add("Go");
        boardNames.add("Property");
        boardNames.add("Community Chest");
        boardNames.add("Property");
        boardNames.add("Income Tax");
        boardNames.add("Railroad");
        boardNames.add("Property");
        boardNames.add("Chance");
        boardNames.add("Property");
        boardNames.add("Property");
        boardNames.add("Jail (Just Visiting)");
        boardNames.add("Property");
        boardNames.add("Utility");
        boardNames.add("Property");
        boardNames.add("Property");
        boardNames.add("Railroad");
        boardNames.add("Property");
        boardNames.add("Community Chest");
        boardNames.add("Property");
        boardNames.add("Property");
        boardNames.add("Free Parking");
        boardNames.add("Property");
        boardNames.add("Chance");
        boardNames.add("Property");
        boardNames.add("Property");
        boardNames.add("Railroad");
        boardNames.add("Property");
        boardNames.add("Property");
        boardNames.add("Utility");
        boardNames.add("Property");
        boardNames.add("Go to Jail");
        boardNames.add("Property");
        boardNames.add("Property");
        boardNames.add("Community Chest");
        boardNames.add("Property");
        boardNames.add("Railroad");
        boardNames.add("Chance");
        boardNames.add("Property");
        boardNames.add("Luxury Tax");
        boardNames.add("Property");

        return boardNames;
    }

    public static HashMap<Integer, Utility> makeUtilities() {
        HashMap<Integer, Utility> utilities = new HashMap<>();
    
        utilities.put(12, new Utility("Electric Company", 12));
        utilities.put(28, new Utility("Water Works", 28));
    
        return utilities;
    }

    public static HashMap<Integer, Property> makeProperties(){

        HashMap<Integer, Property> board = new HashMap<>();

        // Define rent arrays for each property
        int[] mediterraneanAveRents = {2, 4, 10, 30, 90, 160, 250};
        int[] balticAveRents = {4, 8, 20, 60, 180, 320, 450};
        int[] orientalAveRents = {6, 12, 30, 90, 270, 400, 550};
        int[] vermontAveRents = {6, 12, 30, 90, 270, 400, 550};
        int[] connecticutAveRents = {8, 16, 40, 100, 300, 450, 600};
        int[] stCharlesPlaceRents = {10, 20, 50, 150, 450, 625, 750};
        int[] statesAveRents = {10, 20, 50, 150, 450, 625, 750};
        int[] virginiaAveRents = {12, 24, 60, 180, 500, 700, 900};
        int[] stJamesPlaceRents = {14, 28, 70, 200, 550, 750, 950};
        int[] tennesseeAveRents = {14, 28, 70, 200, 550, 750, 950};
        int[] newYorkAveRents = {16, 32, 80, 220, 600, 800, 1000};
        int[] kentuckyAveRents = {18, 36, 90, 250, 700, 875, 1050};
        int[] indianaAveRents = {18, 36, 90, 250, 700, 875, 1050};
        int[] illinoisAveRents = {20, 40, 100, 300, 750, 925, 1100};
        int[] atlanticAveRents = {22, 44, 110, 330, 800, 975, 1150};
        int[] ventnorAveRents = {22, 44, 110, 330, 800, 975, 1150};
        int[] marvinGardensRents = {24, 48, 120, 360, 850, 1025, 1200};
        int[] pacificAveRents = {26, 52, 130, 390, 900, 1100, 1275};
        int[] northCarolinaAveRents = {26, 52, 130, 390, 900, 1100, 1275};
        int[] pennsylvaniaAveRents = {28, 56, 150, 450, 1000, 1200, 1400};
        int[] parkPlaceRents = {35, 70, 175, 500, 1100, 1300, 1500};
        int[] boardwalkRents = {50, 100, 200, 600, 1400, 1700, 2000};

        // Add properties to the board with rent arrays
        board.put(1, new Property("Mediterranean Avenue", "Brown", 60, mediterraneanAveRents, 1));
        board.put(3, new Property("Baltic Avenue", "Brown", 60, balticAveRents, 3));
        board.put(6, new Property("Oriental Avenue", "Light Blue", 100, orientalAveRents, 6));
        board.put(8, new Property("Vermont Avenue", "Light Blue", 100, vermontAveRents, 8));
        board.put(9, new Property("Connecticut Avenue", "Light Blue", 120, connecticutAveRents, 9));
        board.put(11, new Property("St. Charles Place", "Purple", 140, stCharlesPlaceRents, 11));
        board.put(13, new Property("States Avenue", "Purple", 140, statesAveRents, 13));
        board.put(14, new Property("Virginia Avenue", "Purple", 160, virginiaAveRents, 14));
        board.put(16, new Property("St. James Place", "Orange", 180, stJamesPlaceRents, 16));
        board.put(18, new Property("Tennessee Avenue", "Orange", 180, tennesseeAveRents, 18));
        board.put(19, new Property("New York Avenue", "Orange", 200, newYorkAveRents, 19));
        board.put(21, new Property("Kentucky Avenue", "Red", 220, kentuckyAveRents, 21));
        board.put(23, new Property("Indiana Avenue", "Red", 220, indianaAveRents, 23));
        board.put(24, new Property("Illinois Avenue", "Red", 240, illinoisAveRents, 24));
        board.put(26, new Property("Atlantic Avenue", "Yellow", 260, atlanticAveRents, 26));
        board.put(27, new Property("Ventnor Avenue", "Yellow", 260, ventnorAveRents, 27));
        board.put(29, new Property("Marvin Gardens", "Yellow", 280, marvinGardensRents, 29));
        board.put(31, new Property("Pacific Avenue", "Green", 300, pacificAveRents, 31));
        board.put(32, new Property("North Carolina Avenue", "Green", 300, northCarolinaAveRents, 32));
        board.put(34, new Property("Pennsylvania Avenue", "Green", 320, pennsylvaniaAveRents, 34));
        board.put(37, new Property("Park Place", "Blue", 350, parkPlaceRents, 37));
        board.put(39, new Property("Boardwalk", "Blue", 400, boardwalkRents, 39));
        
    
        return board;
    }

    public static HashMap<Integer, Railroad> makeRails() {
        HashMap<Integer, Railroad> rails = new HashMap<>();
    
        // Define rent arrays for each railroad property
        int[] readingRrRents = {25, 50, 100, 200};
        int[] pennsylvaniaRrRents = {25, 50, 100, 200};
        int[] bORrRents = {25, 50, 100, 200};
        int[] shortLineRrRents = {25, 50, 100, 200};

        // Add railroad properties to the rails map
        rails.put(5, new Railroad("Reading Railroad", readingRrRents, 5));
        rails.put(15, new Railroad("Pennsylvania Railroad", pennsylvaniaRrRents, 15));
        rails.put(25, new Railroad("B. & O. Railroad", bORrRents, 25));
        rails.put(35, new Railroad("Short Line Railroad", shortLineRrRents, 35));
    
        return rails;
    }

    public static Queue<CommunityChestEvent> makeCommEvents(){
        ArrayList<CommunityChestEvent> communityChestEvents = new ArrayList<>();

        // Generate and add all CommunityChestEvents to the ArrayList
        //communityChestEvents.add(new CommunityChestEvent("Bank Error", "Bank error in your favor. Collect $200.", 200, -1));
        //communityChestEvents.add(new CommunityChestEvent("Doctor's Fee", "Pay doctor's fee of $50.", -50, -1));
        communityChestEvents.add(new CommunityChestEvent("Get Out of Jail Free", "Get out of Jail Free. This card may be kept until needed or sold.", 0, -1));
        //communityChestEvents.add(new CommunityChestEvent("It's Your Birthday", "Receive $10 from every player.", 10, -1));
        //communityChestEvents.add(new CommunityChestEvent("Life Insurance Matures", "Life insurance matures. Collect $100.", 100, -1));
        //communityChestEvents.add(new CommunityChestEvent("Hospital Fees", "Pay hospital fees of $100.", -100, -1));
        //communityChestEvents.add(new CommunityChestEvent("School Fees", "Pay school fees of $50.", -50, -1));
        // communityChestEvents.add(new CommunityChestEvent("Receive Consultancy Fee", "Receive consultancy fee of $25.", 25, -1));
         communityChestEvents.add(new CommunityChestEvent("Street Repairs", "You are assessed for street repairs: Pay $40 per house and $115 per hotel you own.", 0, -1));
        // communityChestEvents.add(new CommunityChestEvent("Second Prize in Beauty Contest", "You have won second prize in a beauty contest. Collect $10.", 10, -1));
        // communityChestEvents.add(new CommunityChestEvent("Inheritance", "You inherit $100.", 100, -1));
        // communityChestEvents.add(new CommunityChestEvent("Holiday Fund Matures", "Holiday fund matures. Collect $100.", 100, -1));
        // communityChestEvents.add(new CommunityChestEvent("Income Tax Refund", "Income tax refund. Collect $20.", 20, -1));
        // communityChestEvents.add(new CommunityChestEvent("Sale of Stock", "From sale of stock you get $50.", 50, -1));
        // communityChestEvents.add(new CommunityChestEvent("Advance to Go", "Advance to Go (Collect $200).", 0, 0));
        communityChestEvents.add(new CommunityChestEvent("Go to Jail", "Go directly to Jail. Do not pass Go, do not collect $200.", 0, 30));

        // Shuffle the ArrayList to randomize the order
        Collections.shuffle(communityChestEvents);

        // Create a Queue to store the shuffled events
        Queue<CommunityChestEvent> eventsQueue = new LinkedList<>(communityChestEvents);

        return eventsQueue;
    }

    public static Queue<ChanceEvent> makeChanceEvents() {
        ArrayList<ChanceEvent> chanceEvents = new ArrayList<>();
    
        // Generate and add all ChanceEvents to the ArrayList
        chanceEvents.add(new ChanceEvent("Advance to Go", "Collect $200", 0, 0));
        chanceEvents.add(new ChanceEvent("Advance to Illinois Avenue", "Get money if you also pass Go", 0, 24));
        chanceEvents.add(new ChanceEvent("Advance to St. Charles Place", "Get money if you also pass Go", 0, 11));
        chanceEvents.add(new ChanceEvent("Advance token to nearest Utility", "Get money if you also pass go. \nIf unowned, you may buy it from the Bank. \nIf owned, throw dice and pay owner a total ten times the amount thrown.", 0, 0));
        chanceEvents.add(new ChanceEvent("Advance token to nearest Railroad", "Get money if you also pass Go. \nPay owner twice the rental to which they are otherwise entitled. \nIf Railroad is unowned, you may buy it from the Bank.", 0, 0));
        chanceEvents.add(new ChanceEvent("Bank pays you dividend of $50", "Bank pays you dividend of $50.", 50, -1));
        chanceEvents.add(new ChanceEvent("Get out of Jail Free", "This card may be kept until needed or sold.", 0, -1));
        chanceEvents.add(new ChanceEvent("Go Back 3 Spaces", "Go Back 3 Spaces.", 0, 0));
        chanceEvents.add(new ChanceEvent("Go to Jail", "Go directly to Jail. Do not pass Go, do not collect $200.", 0, 30));
        chanceEvents.add(new ChanceEvent("Make general repairs on all your property", "For each house pay $25, for each hotel $100.", 0, -1));
        chanceEvents.add(new ChanceEvent("Pay poor tax of $15", "Pay $15.", -15, -1));
        chanceEvents.add(new ChanceEvent("Take a trip to Reading Railroad", "Take a trip to Reading Railroad.", 0, 5));
        chanceEvents.add(new ChanceEvent("Take a walk on the Boardwalk", "Take a walk on the Boardwalk.", 0, 39));
        chanceEvents.add(new ChanceEvent("You have been elected chairman of the board", "Pay each player $50.", -0, -1));
        chanceEvents.add(new ChanceEvent("Your building and loan matures", "Collect $150.", 150, -1));
    
        // Shuffle the ArrayList to randomize the order
        Collections.shuffle(chanceEvents);
    
        // Create a Queue to store the shuffled events
        Queue<ChanceEvent> eventsQueue = new LinkedList<>(chanceEvents);
    
        return eventsQueue;
    }


    public static void printBoard(){
        String theBoard = "<pre style=\"font-family: 'Courier New', Courier, monospace; white-space: pre-wrap; line-height: 1;\">\n" +
        "    +---------+-----------+------------+-----------+------------+-----------+-----------+----------+-----------+-----------+---------+\n" +
        "    |    Go   | Property  | Comm Chest | Property  | Income Tax | Railroad  | Property  |  Chance  | Property  | Property  |   Jail   |\n" +
        "    +---------+-----------+------------+-----------+------------+-----------+-----------+----------+-----------+-----------+----------+\n" +
        "    | Property|           |            |           |            |           |           |          |           |           | Property |\n" +
        "    +---------+-----------+------------+-----------+------------+-----------+-----------+----------+-----------+-----------+----------+\n" +
        "    |Luxry Tax|           |            |           |            |           |           |          |           |           | Utility  |\n" +
        "    +---------+-----------+------------+-----------+------------+-----------+-----------+----------+-----------+-----------+----------+\n" +
        "    | Property|           |            |           |            |           |           |          |           |           | Property |\n" +
        "    +---------+-----------+------------+-----------+------------+-----------+-----------+----------+-----------+-----------+----------+\n" +
        "    | Chance  |           |            |           |            |           |           |          |           |           | Property |\n" +
        "    +---------+-----------+------------+-----------+------------+-----------+-----------+----------+-----------+-----------+----------+\n" +
        "    | Railroad|           |            |           |            |           |           |          |           |           | Railroad |\n" +
        "    +---------+-----------+------------+-----------+------------+-----------+-----------+----------+-----------+-----------+----------+\n" +
        "    | Property|           |            |           |            |           |           |          |           |           | Property |\n" +
        "    +---------+-----------+------------+-----------+------------+-----------+-----------+----------+-----------+-----------+----------+\n" +
        "    |Com Chest|           |            |           |            |           |           |          |           |           | Com Chest|\n" +
        "    +---------+-----------+------------+-----------+------------+-----------+-----------+----------+-----------+-----------+----------+\n" +
        "    | Property|           |            |           |            |           |           |          |           |           | Property |\n" +
        "    +---------+-----------+------------+-----------+------------+-----------+-----------+----------+-----------+-----------+----------+\n" +
        "    | Propery |           |            |           |            |           |           |          |           |           | Property |\n" +
        "    +---------+-----------+------------+-----------+------------+-----------+-----------+----------+-----------+-----------+----------+\n" +
        "    |Go 2 Jail| Property  |   Utility  | Property  |  Property  | Railroad  | Property  | Property |  Chance   | Property  | Free Park|\n" +
        "    +---------+-----------+------------+-----------+------------+-----------+-----------+----------+-----------+-----------+----------+\n" +
        "</pre>";
        JFrame frame = new JFrame("HTML Message Pane Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create an HTML-enabled JEditorPane
        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html"); // Set content type to HTML
        editorPane.setText("<html><body>" +theBoard+ "</body></html>"); // Set HTML content

        // Show the message pane with HTML content
        JOptionPane.showMessageDialog(frame, editorPane);

        frame.pack();
        frame.setVisible(true);
    }
}

