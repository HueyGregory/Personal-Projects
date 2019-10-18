package JUnit;

import org.junit.Test;
import taxigame.main.GameLoop;

public class RunMain{

    @Test
    public void runGame() {
        System.out.println("Starting");
        GameLoop.main(null);
        System.out.println("Finished");
    }
}
