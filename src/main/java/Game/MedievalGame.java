package Game;

import java.io.*;
import java.util.Scanner;

import static Game.Art.homeScreen;

public class MedievalGame {

  /* instance Variables */
  private Player player;

  // Main method
  public static void main(String[] args) {

    Scanner console = new Scanner(System.in);
    MedievalGame game = new MedievalGame();

    game.player = game.start(console);

    //game.addDelay(500);
    System.out.println("\nLet's take a quick look at you to make sure you're ready to head out the door.");
    System.out.println(game.player);

    //game.addDelay(1000);
    System.out.println("\nWell, you're off to a good start, let's get your game saved so we don't lose it.");
    game.save();

    //game.addDelay(2000);
    System.out.println("We just saved your game...");
    System.out.println("Now we are going to try to load your character to make sure the save worked...");

    //game.addDelay(1000);
    System.out.println("Deleting character...");
    String charName = game.player.getName();
    game.player = null;

    //game.addDelay(1500);
    game.player = game.load(charName, console);
    System.out.println("Loading character...");

    //game.addDelay(2000);
    System.out.println("Now let's print out your character again to make sure everything loaded:");

    //game.addDelay(500);
    System.out.println(game.player);
  }

  /* Instance Methods */
  private String getSaveFile() {
    return player.getName() + ".svr";
  }

  private Player start(Scanner console) {
    Player player = null;
    homeScreen();

    System.out.println("would you like to load a saved game? y/n");

    while (player == null) {
      String userInput = console.nextLine();
      if (userInput.equals("y")) {
        System.out.println("Enter the name you used for the game you want loaded");
        player = load(console.nextLine(), console);
      } else if (userInput.equals("n")) {
        System.out.print("Enter your name : ");
        userInput = console.nextLine();
        player = new Player(userInput);
      }
    }
    return player;
  }

  private void save() {
    String fileName = getSaveFile();
    try {
      FileOutputStream saveFile = new FileOutputStream(fileName);
      ObjectOutputStream playerStream = new ObjectOutputStream(saveFile);
      playerStream.writeObject(player);
    } catch (IOException e) {
      System.out.printf("IOException: could not save game to file %s%n", player.getName());
    }
  }

  private Player load(String playerName, Scanner console) {
    Player loadedPlayer;
    try {
      String filename = playerName + ".svr";
      FileInputStream saveFile = new FileInputStream(filename);
      ObjectInputStream saveReader = new ObjectInputStream(saveFile);
      loadedPlayer = (Player) saveReader.readObject();
    } catch (Exception e) {
      System.out.println("Error loading save file, new file will be created");
      loadedPlayer = new Player(playerName);
    }
    return loadedPlayer;
  }
}
/*
  // Adds a delay to the console, so it seems like the computer is "thinking"
  // or "responding" like a human, not instantly like a computer.
  private void addDelay(int time) {
    try {
      Thread.sleep(time);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}*/
