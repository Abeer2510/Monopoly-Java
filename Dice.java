
import java.util.Random;

public class Dice {
    private Random random;

    public Dice() {
        random = new Random();
    }

    public Object[] roll() {
        int die1 = random.nextInt(6) + 1;
        int die2 = random.nextInt(6) + 1;

        Object[] RET = new Object[2];

        RET[0] = die1 + die2;
        RET[1] = die1 == die2;

        return RET;
    }

}