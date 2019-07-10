package hw3;

import java.util.Random;

/**
 * RandomHello selects a random greeting to display to the user.
 */
public class RandomHello {

    /**
     * Uses a RandomHello object to print
     * a random greeting to the console.
     */
    public static void main(String[] argv) {
        RandomHello randomHello = new RandomHello();
        System.out.println(randomHello.getGreeting());
    }

    /**
     * @return a random greeting from a list of five different greetings.
     */
    public String getGreeting() {
		Random randomGenerator = new Random();
		String[] greetings = new String[5];
		greetings[0] = "Good morning.";
		greetings[1] = "My name is Bryan. What's your name?";
		greetings[2] = "Hi, how was your day?";
		greetings[3] = "Howdy.";
		greetings[4] = "How's it going?";
		return greetings[randomGenerator.nextInt(5)];
    }
}