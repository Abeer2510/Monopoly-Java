import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PropertyInformationPanel extends JPanel {
    public PropertyInformationPanel(ArrayList<Property> properties, ArrayList<Railroad> railroads, ArrayList<Utility> utilities, Player player) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Vertical BoxLayout for separate rows

        // Create a panel for property cards with FlowLayout (wrap)
        JPanel propertyPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        propertyPanel.setBorder(BorderFactory.createTitledBorder("Properties"));

        // Property cards
        for(Property property: properties){
            propertyPanel.add(createPropertyCard(property, player));
        }
        
       

        // Create a panel for railroad cards with FlowLayout (wrap)
        JPanel railPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        railPanel.setBorder(BorderFactory.createTitledBorder("Railroads"));

        // Railroad cards
        for(Railroad railroad: railroads){
            railPanel.add(createRailCard(railroad, player));
        }
        

        // Create a panel for utility cards with FlowLayout (wrap)
        JPanel utilityPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        utilityPanel.setBorder(BorderFactory.createTitledBorder("Utilities"));

        // Utility cards
        for(Utility utility: utilities){
            utilityPanel.add(createUtilityCard(utility, player));
        }

        // Create the title label with a bigger font size
        JLabel titleLabel = new JLabel("Click to mortgage or unmortgage");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font size to 20 and make it bold
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label horizontally


        // Add the property and utility panels to the main panel
        add(titleLabel);
        add(propertyPanel);
        add(railPanel);
        add(utilityPanel);
    }

    public JButton createPropertyCard(Property property, Player player) {
        JButton cardPanel = new JButton();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));

        if(!property.mortgaged){
            cardPanel.setBackground(Color.decode(property.hexify()));
        }
        else{
            cardPanel.setBackground(Color.decode("#5D6D7E"));
        }
        

        JLabel nameLabel = new JLabel("Property Name: " + property.name);
        JLabel costLabel = new JLabel("Property Cost: $" + property.cost);

        JLabel rentLabel = new JLabel("Current Rent: $" + property.rentArray[property.rentLevel]);
        if(property.mortgaged){
            rentLabel.setText("Current Rent: $----");
        }

        JLabel mortgageLabel = new JLabel("Mortgage Value: $" + property.mortgageValue);
        if(property.mortgaged){
            mortgageLabel.setText("Unmortgage Cost: $" + property.unMortgageValue);
        }

        JLabel isMortgagedLabel = new JLabel("-------COLLECTING RENT-------");
        if(property.mortgaged){
            isMortgagedLabel.setText("-----------MORTGAGED-----------");
        }

        cardPanel.add(nameLabel);
        cardPanel.add(costLabel);
        cardPanel.add(rentLabel);
        cardPanel.add(mortgageLabel);
        cardPanel.add(isMortgagedLabel);

        cardPanel.addActionListener(e -> {

            if(!property.mortgaged){
                int choice = JOptionPane.showOptionDialog(null,
                        "Are you sure you want to mortage?\n\n you will gain $" + property.mortgageValue + "\n\n"
                        + "Money: $" + player.money + " --> $" + (player.money+property.mortgageValue) + " ?", "Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, new String[] { "Yes", "No" }, "Yes");

                if (choice == JOptionPane.YES_OPTION) {
                    System.out.println("User will mortgage");
                    property.mortgaged = true;
                    player.setMoney(player.money+property.mortgageValue);

                    cardPanel.setBackground(Color.decode("#5D6D7E"));
                    mortgageLabel.setText("Unmortgage Cost: $" + property.unMortgageValue);
                    rentLabel.setText("Current Rent: $----");
                    isMortgagedLabel.setText("-----------MORTGAGED-----------");
                } 
            }
            else{
                int choice = JOptionPane.showOptionDialog(null,
                        "Are you sure you want to unmortage?\n\n you will lose $" + property.unMortgageValue + "\n\n"
                        + "Money: $" + player.money + " --> $" + (player.money-property.unMortgageValue) + " ?", "Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, new String[] { "Yes", "No" }, "Yes");

                if (choice == JOptionPane.YES_OPTION) {
                    System.out.println("User will unmortgage");
                    property.mortgaged = false;
                    player.setMoney(player.money-property.unMortgageValue);

                    cardPanel.setBackground(Color.decode(property.hexify()));
                    mortgageLabel.setText("Mortgage Value: $" + property.mortgageValue);
                    rentLabel.setText("Current Rent: $" + property.rentArray[property.rentLevel]);
                    isMortgagedLabel.setText("-------COLLECTING RENT-------");
                } 
            }

        });

        return cardPanel;
    }

    public JButton createUtilityCard(Utility utility, Player player) {
        JButton cardPanel = new JButton();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
    
        if (!utility.mortgaged) {
            cardPanel.setBackground(Color.decode("#00CED1"));
        } else {
            cardPanel.setBackground(Color.decode("#5D6D7E"));
        }
    
        JLabel nameLabel = new JLabel("Utility Name: " + utility.name);
        JLabel costLabel = new JLabel("Utility Cost: $" + utility.cost);
    
        JLabel rentLabel = new JLabel("Current Rent: $" + utility.rentLevel);
        if (utility.mortgaged) {
            rentLabel.setText("Current Rent: $----");
        }
    
        JLabel mortgageLabel = new JLabel("Mortgage Value: $" + utility.mortgageValue);
        if (utility.mortgaged) {
            mortgageLabel.setText("Unmortgage Cost: $" + utility.unMortgageValue);
        }
    
        JLabel isMortgagedLabel = new JLabel("-------COLLECTING RENT-------");
        if (utility.mortgaged) {
            isMortgagedLabel.setText("-----------MORTGAGED-----------");
        }
    
        cardPanel.add(nameLabel);
        cardPanel.add(costLabel);
        cardPanel.add(rentLabel);
        cardPanel.add(mortgageLabel);
        cardPanel.add(isMortgagedLabel);
    
        cardPanel.addActionListener(e -> {
    
            if (!utility.mortgaged) {
                int choice = JOptionPane.showOptionDialog(null,
                        "Are you sure you want to mortgage?\n\nYou will gain $" + utility.mortgageValue + "\n\n"
                                + "Money: $" + player.money + " --> $" + (player.money + utility.mortgageValue) + " ?",
                        "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        new String[] { "Yes", "No" }, "Yes");
    
                if (choice == JOptionPane.YES_OPTION) {
                    System.out.println("User will mortgage");
                    utility.mortgaged = true;
                    player.setMoney(player.money + utility.mortgageValue);
    
                    cardPanel.setBackground(Color.decode("#5D6D7E"));
                    mortgageLabel.setText("Unmortgage Cost: $" + utility.unMortgageValue);
                    rentLabel.setText("Current Rent: $----");
                    isMortgagedLabel.setText("-----------MORTGAGED-----------");
                }
            } else {
                int choice = JOptionPane.showOptionDialog(null,
                        "Are you sure you want to unmortgage?\n\nYou will lose $" + utility.unMortgageValue + "\n\n"
                                + "Money: $" + player.money + " --> $" + (player.money - utility.unMortgageValue) + " ?",
                        "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        new String[] { "Yes", "No" }, "Yes");
    
                if (choice == JOptionPane.YES_OPTION) {
                    System.out.println("User will unmortgage");
                    utility.mortgaged = false;
                    player.setMoney(player.money - utility.unMortgageValue);
    
                    cardPanel.setBackground(Color.decode("#00CED1"));
                    mortgageLabel.setText("Mortgage Value: $" + utility.mortgageValue);
                    rentLabel.setText("Current Rent: $" + utility.rentLevel);
                    isMortgagedLabel.setText("-------COLLECTING RENT-------");
                }
            }
    
        });
    
        return cardPanel;
    }


    public JButton createRailCard(Railroad railroad, Player player) {
        JButton cardPanel = new JButton();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));

        if(!railroad.mortgaged){
            cardPanel.setBackground(Color.decode("#c9bbc8"));
        }
        else{
            cardPanel.setBackground(Color.decode("#5D6D7E"));
        }
        

        JLabel nameLabel = new JLabel("Property Name: " + railroad.name);
        JLabel costLabel = new JLabel("Property Cost: $" + railroad.cost);

        JLabel rentLabel = new JLabel("Current Rent: $" + railroad.rentArray[railroad.rentLevel]);
        if(railroad.mortgaged){
            rentLabel.setText("Current Rent: $----");
        }

        JLabel mortgageLabel = new JLabel("Mortgage Value: $" + railroad.mortgageValue);
        if(railroad.mortgaged){
            mortgageLabel.setText("Unmortgage Cost: $" + railroad.unMortgageValue);
        }

        JLabel isMortgagedLabel = new JLabel("-------COLLECTING RENT-------");
        if(railroad.mortgaged){
            isMortgagedLabel.setText("-----------MORTGAGED-----------");
        }

        cardPanel.add(nameLabel);
        cardPanel.add(costLabel);
        cardPanel.add(rentLabel);
        cardPanel.add(mortgageLabel);
        cardPanel.add(isMortgagedLabel);

        cardPanel.addActionListener(e -> {

            if(!railroad.mortgaged){
                int choice = JOptionPane.showOptionDialog(null,
                        "Are you sure you want to mortage?\n\n you will gain $" + railroad.mortgageValue + "\n\n"
                        + "Money: $" + player.money + " --> $" + (player.money+ railroad.mortgageValue) + " ?", "Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, new String[] { "Yes", "No" }, "Yes");

                if (choice == JOptionPane.YES_OPTION) {
                    System.out.println("User will mortgage");
                    railroad.mortgaged = true;
                    player.setMoney(player.money+ railroad.mortgageValue);

                    cardPanel.setBackground(Color.decode("#5D6D7E"));
                    mortgageLabel.setText("Unmortgage Cost: $" +  railroad.unMortgageValue);
                    rentLabel.setText("Current Rent: $----");
                    isMortgagedLabel.setText("-----------MORTGAGED-----------");
                } 
            }
            else{
                int choice = JOptionPane.showOptionDialog(null,
                        "Are you sure you want to unmortage?\n\n you will lose $" +  railroad.unMortgageValue + "\n\n"
                        + "Money: $" + player.money + " --> $" + (player.money- railroad.unMortgageValue) + " ?", "Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, new String[] { "Yes", "No" }, "Yes");

                if (choice == JOptionPane.YES_OPTION) {
                    System.out.println("User will unmortgage");
                    railroad.mortgaged = false;
                    player.setMoney(player.money- railroad.unMortgageValue);

                    cardPanel.setBackground(Color.decode( "#c9bbc8"));
                    mortgageLabel.setText("Mortgage Value: $" +  railroad.mortgageValue);
                    rentLabel.setText("Current Rent: $" +  railroad.rentArray[ railroad.rentLevel]);
                    isMortgagedLabel.setText("-------COLLECTING RENT-------");
                } 
            }

        });

        return cardPanel;
    }
}