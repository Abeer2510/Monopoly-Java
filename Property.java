
import java.util.HashMap;

import javax.swing.JOptionPane;

public class Property{
    public String name;
    public String color;
    public int cost;
    public int owner;
    public int[] rentArray;
    public int rentLevel;

    public int mortgageValue;
    public int unMortgageValue;
    public boolean mortgaged;
    
    public boolean colorSet;
    public int location;

    // Static HashMap to map property colors to hex values
    private static final HashMap<String, String> colorHexMap = new HashMap<>();

    public Property(String name, String color, int cost, int[] rents, int location) {
        this.location = location;
        this.name = name;
        this.color = color;
        this.cost = cost;
        rentArray = rents;
        owner = -1;
        rentLevel = 0;
        mortgaged = false;
        colorSet = false;
        mortgageValue = cost/2;
        unMortgageValue = mortgageValue + mortgageValue/10;

    }

    static {
    // Initialize the colorHexMap
        colorHexMap.put("Brown", "#8B4513");
        colorHexMap.put("Light Blue", "#ADD8E6");
        colorHexMap.put("Purple", "#800080");
        colorHexMap.put("Orange", "#FFA500");
        colorHexMap.put("Yellow", "#FFFF00");
        colorHexMap.put("Red", "#FF0000");
        colorHexMap.put("Green", "#008000");
        colorHexMap.put("Blue", "#0000FF");
    }

    public void handleUnowned(Player player){
        String[] options = { "Buy", "Dont Buy" };

        int choice = -1;
        
        while (true) {
            String message = "<html><body style='width: 250px; padding: 10px; border: 2px solid #000; background-color: "+ hexify()+";'>" +
            "<div style='text-align: center;'><h1 style='margin: 0;'>" + player.name + "'s Turn</h1></div>" +
            "<hr style='border-top: 2px dashed #000;'>" +
            "<div><p><b>Position:</b> " + player.position + "</p>" +
            "<p><b>Owner: UNOWNED</b></p>" +
            "<p><b>Money:</b> $" + player.money + "</p></div>" +
            "<div style='background-color: #fff; padding: 5px; margin-top: 10px;'>" +
            "<p><b>Property Name:</b> " + this.name + "</p>" +
            "<p><b>Property Color:</b> " + this.color + "</p>" +
            "<p><b>Property Cost:</b> $" + this.cost + "</p></div>" +
            "</body></html>";
    
            choice = JOptionPane.showOptionDialog(null,
                message,
                "Will you buy?",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        
            if (choice == JOptionPane.CLOSED_OPTION) {
                continue;
            }
            if (choice == 0){
                if(player.money < this.cost){
                    JOptionPane.showMessageDialog(null,  "You dont have enough money to buy " + this.name);
                    continue;
                }
                else{
                    player.setMoney(player.money - this.cost);
                    this.owner = player.playerNum;
                    player.properties.add(this.location);
                    JOptionPane.showMessageDialog(null,  "Congrats!! \n\n You now own " + this.name+ 
                    "\n\n You have $" + player.money + " remaining" );
                    break;
                } 
            }
            if(choice == 1){
                break;
            }
        }
    }

    public void handleOwned(Player player, Player owner){
        //if owned by me
        if(player.playerNum == owner.playerNum){
            String[] options = { "Nice" };

            String message = "<html><body style='width: 250px; padding: 10px; border: 2px solid #000; background-color: "+ hexify()+";'>" +
            "<div style='text-align: center;'><h1 style='margin: 0;'>" + player.name + "'s Turn</h1></div>" +
            "<hr style='border-top: 2px dashed #000;'>" +
            "<div><p><b>Position:</b> " + player.position + "</p>" +
            "<p><b>Money:</b> $" + player.money + "</p></div>" +
            "<div style='background-color: #fff; padding: 5px; margin-top: 10px;'>" +
            "<p><b>Property Name:</b> " + this.name + "</p>" +
            "<p><b>Owner: Player: " + player.playerNum + " - " + player.name + " (YOU)</b></p>" +
            "</body></html>";
    
            JOptionPane.showOptionDialog(null,
                message,
                "You own this already",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        }
        //someone else owns. Pay rent
        else{
            int rent = rentArray[rentLevel];

            String[] options = { "Drats!" };

            String message = "<html><body style='width: 250px; padding: 10px; border: 2px solid #000; background-color: "+ hexify()+";'>" +
            "<div style='text-align: center;'><h1 style='margin: 0;'>" + player.name + "'s Turn</h1></div>" +
            "<hr style='border-top: 2px dashed #000;'>" +
            "<div><p><b>Position:</b> " + player.position + "</p>" +
            "<p><b>Money:</b> $" + player.money + "</p></div>" +
            "<div style='background-color: #fff; padding: 5px; margin-top: 10px;'>" +
            "<p><b>Property Name:</b> " + this.name + "</p>" +
            "<p><b>Owner: Player " + owner.playerNum + " ) " + owner.name + "</b></p>" +
            "<p><b>RENT: $" + rent + "</b></p>" +
            "</body></html>";
    
            JOptionPane.showOptionDialog(null,
                message,
                "Pay: $" + rent,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);


            player.setMoney(player.money - rent);
            owner.setMoney(owner.money + rent);
            JOptionPane.showMessageDialog(null,  
            player.name +  " : $" + (player.money + rent) + " ---> $" + player.money + "\n\n" +
            owner.name +  " : $" + (owner.money - rent) + " ---> $" + owner.money);
        }

    }

    public String hexify(){
        return colorHexMap.get(color);
    }

    public String HTMLfy(){
        return 
        "<div style='width: calc(50% - 20px); margin: 10px;'>"+
        "<div style='width: 250px; padding: 5px; border: 2px solid #000; background-color: "+ hexify() + "; margin-right: 10px;'>" +
                "<div style='text-align: center;'><h2 style='margin: 0;'>" + name + "</h2></div>" +
                "<hr style='border-top: 2px dashed #000;'>" +
                "<div style='background-color: #fff; padding: 5px; margin-top: 10px;'>" +
                "<p><b>Property Color:</b> " + color + "</p>" +
                "<p><b>Property Cost:</b> $" + cost + "</p>" +
                "<p><b>Rent with Color Set:</b> $" + rentArray[rentLevel] + "</p>" +
                "</div></div></div>";
    }
}
