import javax.swing.JOptionPane;

public class Utility {
    public String name;
    public int cost;
    public int owner;
    public int rentLevel;

    public int mortgageValue;
    public int unMortgageValue;
    public boolean mortgaged;
    
    public int location;
    

    public Utility(String name, int location) {
        this.name = name;
        cost = 150; 
        owner = -1; 
        rentLevel = 4; 
        mortgaged = false; 
        this.location = location;
        mortgageValue = cost/2;
        unMortgageValue = mortgageValue + mortgageValue/10;
    }


    public void handleUnowned(Player player){
        String[] options = { "Buy", "Dont Buy" };

        int choice = -1;
        
        while (true) {
            String message = "<html><body style='width: 250px; padding: 10px; border: 2px solid #000; background-color: #00CED1;'>" +
            "<div style='text-align: center;'><h1 style='margin: 0;'>" + player.name + "'s Turn</h1></div>" +
            "<hr style='border-top: 2px dashed #000;'>" +
            "<div><p><b>Position:</b> " + player.position + "</p>" +
            "<p><b>Owner: UNOWNED</b></p>" +
            "<p><b>Money:</b> $" + player.money + "</p></div>" +
            "<div style='background-color: #fff; padding: 5px; margin-top: 10px;'>" +
            "<p><b>Property Name:</b> " + this.name + "</p>" +
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
                    player.utlities.add(this.location);
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

    public void handleOwned(Player player, Player owner, int dice){
        //if owned by me
        if(player.playerNum == owner.playerNum){
            String[] options = { "Nice" };

            String message = "<html><body style='width: 250px; padding: 10px; border: 2px solid #000; background-color: #00CED1;'>" +
            "<div style='text-align: center;'><h1 style='margin: 0;'>" + player.name + "'s Turn</h1></div>" +
            "<hr style='border-top: 2px dashed #000;'>" +
            "<div><p><b>Position:</b> " + player.position + "</p>" +
            "<p><b>Money:</b> $" + player.money + "</p></div>" +
            "<div style='background-color: #fff; padding: 5px; margin-top: 10px;'>" +
            "<p><b>Property Name:</b> " + this.name + "</p>" +
            "<p><b>Owner: Player: " + player.playerNum + " ) " + player.name + " (YOU)</b></p>" +
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
            int rent = 4*dice;

            String[] options = { "Drats!" };

            String message = "<html><body style='width: 250px; padding: 10px; border: 2px solid #000; background-color: #00CED1;'>" +
            "<div style='text-align: center;'><h1 style='margin: 0;'>" + player.name + "'s Turn</h1></div>" +
            "<hr style='border-top: 2px dashed #000;'>" +
            "<div><p><b>Position:</b> " + player.position + "</p>" +
            "<p><b>Money:</b> $" + player.money + "</p></div>" +
            "<div style='background-color: #fff; padding: 5px; margin-top: 10px;'>" +
            "<p><b>Property Name:</b> " + this.name + "</p>" +
            "<p><b>Owner: Player " + owner.playerNum + " ) " + owner.name + "</b></p>" +
            "<p><b>RENT: (" + rentLevel + " x " + dice + ")  =  $" + rent + "</b></p>" +
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
}