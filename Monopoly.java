
import java.util.*;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;


public class Monopoly{
    public static HashMap<Integer, Property> Properties = new HashMap<>();
    public static ArrayList<String> BOARD = new ArrayList<>();
    public static ArrayList<Player> PlayerList = new ArrayList<>();
    public static Queue<CommunityChestEvent> commEventsQueue = new LinkedList<>();
    public static Queue<ChanceEvent> chanceEventsQueue = new LinkedList<>();
    public static HashMap<Integer, Railroad> Railroads = new HashMap<>();
    public static HashMap<Integer, Utility> Utilities = new HashMap<>();
    public static boolean pause;


    public static void printProperties(Player player){

        //Get properties, utilities, railroads
        ArrayList<Property> playerProperties = new ArrayList<>();
        for(int index: player.properties){
            playerProperties.add(Properties.get(index));
        }

        ArrayList<Railroad> playerRails = new ArrayList<>();
        for(int index: player.railroads){
            playerRails.add(Railroads.get(index));
        }

        ArrayList<Utility> playerUtilities = new ArrayList<>();
        for(int index: player.utlities){
            playerUtilities.add(Utilities.get(index));
        }


        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Property Information");
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Prevent default close operation
            frame.setSize(850, 600);
            PropertyInformationPanel propertyPanel = 
                                        new PropertyInformationPanel(playerProperties, playerRails, playerUtilities, player);
            frame.getContentPane().add(propertyPanel);
            frame.setVisible(true);
            
            // Add a button to close the JFrame
            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(e -> {
                frame.dispose(); // Close the JFrame when the button is clicked
                pause = false;
            });
            frame.getContentPane().add(closeButton, BorderLayout.SOUTH); // Add the button to the bottom of the JFrame


        });

    }

    public static int options(Player player, int doubles){
        // Array of options
        String[] options = { "Roll", "See the Board", "See Properties" };


        int choice = -1;
        
        while (choice != 0) {
            String messege = player.name +"'s Turn" + "\nPosition: " + player.position + "\nMoney: " + player.money + "\n";
            if(doubles > 0){
                messege = "YOU ROLLED DOUBLES LAST TURN! ROLL AGAIN \n\n" + messege;
            }
            choice = JOptionPane.showOptionDialog(null, 
            messege
            , "Option Menu", JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
            // Deal with all options other than ROLL
            if (choice == JOptionPane.CLOSED_OPTION) {
                choice = -1;
            }
            if (choice == 1){
                Board.printBoard();
            }
            if( choice == 2){
                pause = true;
                printProperties(player);
                while(pause == true){
                    System.out.print("");
                }
            }
        }
        return choice;
    }

    public static void postOptions(Player player){
        String[] options = { "End Turn", "See the Board", "See Properties" };

        int choice = -1;
        
        while (choice != 0) {
            String messege = player.name +": Any other actions?" + "\nPosition: " 
                                                                + player.position + "\nMoney: " + player.money + "\n";
            choice = JOptionPane.showOptionDialog(null, 
            messege
            , "Option Menu", JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
            // Deal with all options other than ROLL
            if (choice == JOptionPane.CLOSED_OPTION) {
                choice = -1;
            }
            if (choice == 1){
                Board.printBoard();
            }
            if( choice == 2){
                pause = true;
                printProperties(player);
                while(pause == true){
                    System.out.print("");
                }
            }
        }

    }


    //Handles property, chance, comm chest, Go, Free parking, tax
    public static boolean handleLand(Player player,int newPosition, String type, int roll ){

        boolean doubleCancel = false;

        //HANDLE PROPERTY
        if(type.equals("Property")){
            Property property = Properties.get(newPosition);

            //deal with unowned
            if(property.owner == -1){
                property.handleUnowned(player);
            }
            //deal with owned
            else{
                property.handleOwned(player, PlayerList.get(property.owner));
            }
        }

        //HANDLE INCOME TAX
        if(type.equals("Income Tax")){
            player.setMoney(player.money - 200);
            JOptionPane.showMessageDialog(null,  player.name +" lost $200!" + "\n\n Remaining Money: $" + player.money);
        }

        //HANDLE LUXURY TAX
        if(type.equals("Luxury Tax")){
            player.setMoney(player.money - 100);
            JOptionPane.showMessageDialog(null,  player.name +" lost $100!" + "\n\n Remaining Money: $" + player.money);
        }

        //HANDLE GO
        if(type.equals("Go")){
            player.setMoney(player.money + 200);
            JOptionPane.showMessageDialog(null,  "Nice you landed on Go!\n\n" 
                                                            + player.name +" gained $200!" + "\n\n Money: $" + player.money);
        }

        //HANDLE FREE PARKING
        if(type.equals("Free Parking")){
            JOptionPane.showMessageDialog(null,  player.name +" is SAFE!" + "\n\n Money: $" + player.money);
        }

        //HANDLE GO TO JAIL
        if(type.equals("Go to Jail")){
            goJail(player);
            System.out.println("Doubles Cancelled");
            doubleCancel = true;
        }

        //HANDLE COMMUNITY CHEST
        if(type.equals("Community Chest")){
            CommunityChestEvent event = commEventsQueue.poll();

            JOptionPane.showMessageDialog(null, event.name + "\n\n" + event.description);
            //money
            if(event.gain != 0){
                player.setMoney(player.money + event.gain);
                if(event.gain > 0){
                    JOptionPane.showMessageDialog(null,  
                    "Nice!\n\n" + player.name +" gained $" + event.gain + "!\n\n Money: $" + player.money);
                }
                else{
                    JOptionPane.showMessageDialog(null,  
                    "Unfortunate!\n\n" + player.name +" lost $" + -event.gain + "!\n\n Money: $" + player.money);
                }
            }
            //jail or go
            if(event.location != -1){
                int newLocation = event.location;
                String newType = BOARD.get(newLocation);
                player.setPosition(newLocation);
                doubleCancel = handleLand(player, newLocation, newType, roll);
            }
            //birthday
            if(event.name.equals("It's Your Birthday")){
                int birthdayGain = (PlayerList.size() - 1)*(10);
                player.setMoney(player.money + birthdayGain);

                JOptionPane.showMessageDialog(null,  
                    "Nice!\n" + player.name +" gained $" + birthdayGain + 
                    "!\n\nUnfortunate! Everyone else lost $10" + "!\n\n Money: $" + player.money);
            }
            //Get Out of Jail
            if(event.name.equals("Get Out of Jail Free")){
                JOptionPane.showMessageDialog(null, 
                player.name + " has gained a community get out of jail. \n\n Use it later if you are in jail \n or sell it for $50");
                player.getOutComm = true;
            }
            //put back on queue
            if(!event.name.equals("Get Out of Jail Free")){
                commEventsQueue.add(event);
            }

        }

        //HANDLE CHANCE
        if(type.equals("Chance")){
            ChanceEvent event = chanceEventsQueue.poll();

            JOptionPane.showMessageDialog(null, event.name + "\n\n" + event.description);
            //money
            if(event.gain != 0){
                player.setMoney(player.money + event.gain);
                if(event.gain > 0){
                    JOptionPane.showMessageDialog(null,  
                    "Nice!\n\n" + player.name +" gained $" + event.gain + "!\n\n Money: $" + player.money);
                }
                else{
                    JOptionPane.showMessageDialog(null,  
                    "Unfortunate!\n\n" + player.name +" lost $" + -event.gain + "!\n\n Money: $" + player.money);
                }
            }
            //location change
            if(event.location != -1){
                int newLocation = event.location;
                //deal with nearest utility
                if(event.name.equals("Advance token to nearest Utility")){
                    if(newPosition == 22){
                        newLocation = 28;
                    }
                    else{
                        newLocation = 12;
                    }
                }
                //deal with nearest railroad
                if(event.name.equals("Advance token to nearest Railroad")){
                    if(newPosition == 22){
                        newLocation = 25;
                    }
                    else if(newPosition == 36){
                        newLocation = 5;
                    }
                    else{
                        newLocation = 15;
                    }
                }
                //deal with 3 back
                if(event.name.equals("Go Back 3 Spaces")){
                    newLocation = newPosition - 3;
                }

                //jail
                if(!event.name.equals("Go to Jail")){
                    if(newLocation < newPosition){
                        JOptionPane.showMessageDialog(null,  
                        player.name +" Is now in jail");
                    }
                }
                //reRoute
                String newType = BOARD.get(newLocation);
                player.setPosition(newLocation);
                doubleCancel = handleLand(player, newLocation, newType, roll);
            }
            //chairman
            if(event.name.equals("You have been elected chairman of the board")){
                int chairmanLoss = (PlayerList.size() - 2)*(50);
                player.setMoney(player.money - chairmanLoss);

                JOptionPane.showMessageDialog(null,  
                    "Unfortunate!\n" + player.name +" lost $" + chairmanLoss + 
                    "!\n\nNice! Everyone else gained $50" + "!\n\n Money: $" + player.money);
            }
            if(event.name.equals("Get Out of Jail Free")){
                JOptionPane.showMessageDialog(null, 
                player.name + " has gained a chance get out of jail. \n\n Use it later if you are in jail \n or sell it for $50");
                player.getOutComm = true;
            }
            //put back on queue
            if(!event.name.equals("Get Out of Jail Free")){
                chanceEventsQueue.add(event);
            }

        }

        //HANDLE RAILROADS
        if(type.equals("Railroad")){
            Railroad rail = Railroads.get(newPosition);
            
            //deal with unowned
            if(rail.owner == -1){
                rail.handleUnowned(player);
            }
            //deal with owned
            else{
                rail.handleOwned(player, PlayerList.get(rail.owner));
            }
        }

        //HANDLE Utility
        if(type.equals("Utility")){
            Utility utility = Utilities.get(newPosition);

            // Deal with unowned
            if (utility.owner == -1) {
                utility.handleUnowned(player);
            } 
            // Deal with owned
            else {
                utility.handleOwned(player, PlayerList.get(utility.owner), roll);
            }

        }

        return doubleCancel;


    }


    public static void main(String[] args){
        System.out.println("Welcome to Monopoly");

        //Set players, starting cash
        PlayerList = Player.makePlayers();
        int playerCount = PlayerList.size() - 1;

        //Make BOARD
        BOARD = Board.makeBoard();

        //Make gloabal arrays to store board componenets
        commEventsQueue = Board.makeCommEvents();
        chanceEventsQueue = Board.makeChanceEvents();
        Properties = Board.makeProperties();
        Railroads = Board.makeRails();
        Utilities = Board.makeUtilities();

        pause = false;

        Dice Dice = new Dice();


        int doublesCount = 0;
        String doublesString = "";
        for(int i = 0; i < 20; i++){
            Player currentPlayer = PlayerList.get(i%playerCount + 1);


            if(currentPlayer.inJail){
                doublesCount = 0;
                if(!inJail(currentPlayer)){
                    continue;
                }
                else{
                    currentPlayer.inJail = false;
                }
            }

            int choice = options(currentPlayer, doublesCount);

            //Handle the user's choice
            if (choice == 0) {

                Object[] outcome= Dice.roll();
                int roll = (int)outcome[0];
                boolean doubles = (boolean)outcome[1];

                int newPosition = (currentPlayer.position + roll)%40;
                String type = BOARD.get(newPosition);

                if(doubles && doublesCount == 0){
                    doublesCount++;
                    doublesString = "DOUBLES!! ROLL AGAIN AFTER THIS TURN \n\n";
                }
                else if(doubles && doublesCount == 1){
                    doublesCount++;
                    doublesString = "DOUBLES AGAIN. TWICE IN A ROW. 1 MORE AND ITS JAIL!! \n\n";
                }
                //THREE DOUBLES
                else if(doubles && doublesCount == 2){
                    doublesCount=0;
                    doublesString = "3 DOUBLES = JAIL!! \n\n";
                    JOptionPane.showMessageDialog(null, doublesString + "\n\n" + currentPlayer.name + " is now in jail.");
                    goJail(currentPlayer);
                    //Next persons turn
                    continue;
                }
                else{
                    doublesCount = 0;
                    doublesString = "";
                }

                //Pass GO
                if(currentPlayer.position + roll > 40){
                    currentPlayer.setMoney(currentPlayer.money + 200);
                    JOptionPane.showMessageDialog(null,  
                    "Nice! You passed Go along the way\n\n" + currentPlayer.name +" gained $200!" + 
                    "\n\n Money: $" + currentPlayer.money);
                }

                currentPlayer.setPosition(newPosition);
                if(type.equals("Go") || type.equals("Free Parking") || type.equals("Income Tax") 
                        || type.equals("Luxury Tax") || type.equals("Jail (Just Visitng)") 
                        || type.equals("Go to Jail")){
                            JOptionPane.showMessageDialog(null,  doublesString + "Dice Roll: " + roll + 
                            "\nNew position: " + newPosition +
                            "\n\n\nYou have landed on " + type + "!");
                }
                else{
                    JOptionPane.showMessageDialog(null,  doublesString + "Dice Roll: " + roll + 
                    "\nNew position: " + newPosition + "\n\n\nYou have landed on a " + type + "!");
                }


                boolean cancel = handleLand(currentPlayer, newPosition, type, roll);
                System.out.println("HandleLandOutput:" + cancel);
                if(cancel){
                    doublesCount = 0;
                    continue;
                }

                if(doubles)i--;

                if(!doubles){
                    postOptions(currentPlayer);
                }
            } 
        }
    }

    public static void goJail(Player player) {
        player.inJail = true;
        player.jailturns = 0;
        player.position = 10;

        String[] options = { "End Turn", "See the Board", "See Properties", "Leave Jail" };

        int choice = -1;
        
        while (choice != 0) {
            String messege = player.name +" is IN JAIL. Any other actions?" + "\nPosition: " 
                                                                + "JAIL" + "\nMoney: " + player.money + "\n";
            choice = JOptionPane.showOptionDialog(null, messege
            , "Option Menu", JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
            // Deal with all options other than ROLL
            if (choice == JOptionPane.CLOSED_OPTION) {
                choice = -1;
            }
            if (choice == 1){
                Board.printBoard();
            }
            if( choice == 2){
                pause = true;
                printProperties(player);
                while(pause == true){
                    System.out.print("");
                }
            }
            if( choice == 3){
                boolean out = leaveJail(player);
                System.out.println("LeaveJail output: " + out);
                if(out == true){
                    player.inJail = false;
                    postOptions(player);
                    return;
                }
            }
        }
        
    }

    private static boolean leaveJail(Player player) {
        String[] options = { "Pay $50", "Use Get Out of Jail", "Cancel" };

        int choice = -1;
        
        while(choice != 2){
            String messege = "How will you get out of jail?";
            choice = JOptionPane.showOptionDialog(null, messege
            , "Option Menu", JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
            if (choice == 2) {
                return false;
            }
            if (choice == 1){
                boolean out = checkGetOut(player);
                System.out.println("checkGetOut returned " + out);
                if(out == true)return true;
            }
            if( choice == 0){
                JOptionPane.showMessageDialog(null, player.name + "'s Money: $" + player.money + " --> " + (player.money - 50) );
                player.setMoney(player.money - 50);
                System.out.println("Paid 50");
                return true;

            } 
        }
        return false;
    }

    private static boolean checkGetOut(Player player) {
        String[] options = { "Get out of jail community chest", "Get out of jail chance card", "cancel" };

        int choice = -1;

        while(choice != 2){
            String messege = "Which get out of jail?";
            choice = JOptionPane.showOptionDialog(null, messege
            , "Option Menu", JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if(choice == 0){
                if(player.getOutComm == true){
                    player.getOutComm = false;
                    JOptionPane.showMessageDialog(null, player.name + " has successfully left jail\n\n"  +
                        "The get out of jail community card has been added back to the community chest queue stack");
                    commEventsQueue.add(new CommunityChestEvent("Get Out of Jail Free",
                             "Get out of Jail Free. This card may be kept until needed or sold.", 0, -1));
                    return true;
                }
                else{
                    JOptionPane.showMessageDialog(null, 
                    player.name + " does not have a get out of jail free community chest card");
                }
            }
            else if(choice == 1){
                if(player.getOutChance == true){
                    player.getOutChance = false;
                    JOptionPane.showMessageDialog(null, player.name + " has successfully left jail\n\n"  +
                        "The get out of jail chance card has been added back to the chance stack");
                    chanceEventsQueue.add(new ChanceEvent("Get out of Jail Free", 
                                    "This card may be kept until needed or sold.", 0, -1));
                    return true;
                }
                else{
                    JOptionPane.showMessageDialog(null, 
                    player.name + " does not have a get out of jail free chance card");
                }
            }
        }
        return false;
        

    }

    private static boolean inJail(Player player) {
        String[] options = { "Roll", "See the Board", "See Properties", "Pay 50", "Use Get out of Jail" };

        int choice = -1;
        player.jailturns++; 
        
        while (true) {
            String messege = player.name +" is IN JAIL. What will you do?" + "\nPosition: " 
                                                + "JAIL" + "\nMoney: " + player.money + "\n\n" + "TURN: " + player.jailturns;
            choice = JOptionPane.showOptionDialog(null, messege
            , "Option Menu", JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
            // Deal with all options
            if(choice == 0){
                Dice Dice = new Dice();
                Object[] outcome= Dice.roll();
                int roll = (int)outcome[0];
                boolean doubles = (boolean)outcome[1];

                if(!doubles){
                    JOptionPane.showMessageDialog(null, player.name + " did NOT ROLL DOUBLES!!");
                    postOptions(player);
                    return false;
                }
                else{ 
                    JOptionPane.showMessageDialog(null, player.name + " rolled DOUDLES!!\n\nRoll: " + roll );
                    player.inJail = false;
                    player.setPosition(player.position + roll);
                    String type = BOARD.get(player.position);
                    handleLand(player, player.position, type, roll);
                    postOptions(player);
                    return false;
                }
            }
            if (choice == 1){
                Board.printBoard();
            }
            if( choice == 2){
                pause = true;
                printProperties(player);
                while(pause == true){
                    System.out.print("");
                }
            }
            if( choice == 3){
                JOptionPane.showMessageDialog(null, player.name + "'s Money: $" + player.money + " --> " + (player.money - 50) );
                player.setMoney(player.money - 50);
                System.out.println("Paid 50");
                break;
            }
            if( choice == 4){
                boolean out = checkGetOut(player);
                System.out.println("checkGetOut returned " + out);
                if(out == true)break;
            }

        }

        return true;
    }
}