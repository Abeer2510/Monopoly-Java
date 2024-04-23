import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JOptionPane;

public class Player {
    public String name;

    public boolean inJail;
    public boolean getOutChance;
    public boolean getOutComm;
    public int jailturns;


    public int money;
    public int position;
    public int playerNum;
    public HashSet<Integer> properties;
    public HashSet<Integer> railroads;
    public HashSet<Integer> utlities;
    


    public Player(int startingCash, String newName, int playerNum) {
        name = newName;
        money = startingCash;

        inJail = false;
        jailturns = 0;
        getOutChance = false;
        getOutComm = false;

        position = 0;
        this.playerNum = playerNum;
        
        properties = new HashSet<>();
        railroads = new HashSet<>();
        utlities = new HashSet<>();
    }

    // Setter method for name
    public void setName(String newName) {
        name = newName;
    }

    // Setter method for inJail
    public void setInJail(boolean newInJail) {
        inJail = newInJail;
    }

    // Setter method for money
    public void setMoney(int newMoney) {
        money = newMoney;
    }

    // Setter method for position
    public void setPosition(int newPosition) {
        position = newPosition;
    }

    public static ArrayList<Player> makePlayers(){

        boolean validInput = false;
        int playerCount = 0;
        int startingAmount = 0;
        ArrayList<Player> PlayerList = new ArrayList<>();
        PlayerList.add(null);
        

        //First we get the number of players
        while (!validInput) {
            String input = JOptionPane.showInputDialog(null, "How many players 2,3 or 4?");
            try {
                playerCount = Integer.parseInt(input);
                if (playerCount >= 2 && playerCount <= 4) {
                    validInput = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Player count must be 2, 3, or 4");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Player count must be 2, 3, or 4");
            }
        }


        //We get the starting amount
        validInput = false;
        while (!validInput) {
            String input = JOptionPane.showInputDialog(null, "Enter a starting cash");
            try {
                startingAmount = Integer.parseInt(input);
                if (startingAmount >= 100 && startingAmount <= 5000) {
                    validInput = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Starting amount must be a number between 100 and 5000");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Starting amount must be a number between 100 and 5000");
            }
        }

        String allNames = "";
        int currentPlayer = 1;
        while(currentPlayer <= playerCount){
            String input = JOptionPane.showInputDialog(null, "Name of player: " + currentPlayer);
            if(input.length() == 0){
                JOptionPane.showMessageDialog(null, "Enter a valid name");
            }
            else{
                if (currentPlayer == playerCount) {
                    allNames += " and ";
                }
                else if(currentPlayer == 1){}
                else{
                    allNames += ", ";
                }
                
                allNames += input;
                Player player = new Player(startingAmount, input, currentPlayer);
                PlayerList.add(player);
                currentPlayer++;
            }
        }

        JOptionPane.showMessageDialog(null, "Creating a game with " + playerCount + 
                                    " players and $" + startingAmount +" starting cash. Good luck " + allNames + ".");

        return PlayerList;
    }
}