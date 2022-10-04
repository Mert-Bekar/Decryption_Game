import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Assignment_03 {
    public static void main(String[] args) {

        //Gathering all Strings in one array
        String[] paragraphs = createLibrary();

        //Initiating and creating truncated arrays
        String[] truncated = new String[paragraphs.length];
        createTruncated(paragraphs,truncated);

        //Initiating and creating an ArrayList for letters
        ArrayList<Character> alphabet = new ArrayList<>();
        createAlphabet(alphabet);

        //Initiating encryption hash-map
        HashMap<String,String> encryptionMap = new HashMap<>();

        //Initiating a Hash-map for storing player moves and generating 'Key' values
        HashMap<String,String> playerMap = new HashMap<>();

        //Original Paragraph
        String chosenParagraph;

        //First encrypted version of original
        String encryptedParagraph;

        //This version can be affected by players
        String decryptedParagraph;



        //===========GAME STARTS HERE=================
        //Choosing mode and paragraph
        chosenParagraph = paragraphs[gameMode(truncated)];

        //Encrypting
        createEncryptionMap(alphabet,encryptionMap,chosenParagraph);

        //Populate playerMap with encrypted letters
        populatePlayerMap(playerMap,encryptionMap);

        //Initiating encryption
        encryptedParagraph = encryption(chosenParagraph,encryptionMap);

        //Initiating paragraph which solved by
        decryptedParagraph = reArrangeParagraph(encryptedParagraph,playerMap);
        divide();
        instructions();
        int helpCount = 1;
        double milliSec = System.currentTimeMillis();
        int timeStart = (int) (milliSec/1000);
        int timeEnd;
        do {
            System.out.println(decryptedParagraph);
            printKeyValue(playerMap);
            helpCount = playerInput(playerMap, encryptionMap, helpCount);
            milliSec = System.currentTimeMillis();
            timeEnd = (int) (milliSec/1000);
            decryptedParagraph = reArrangeParagraph(encryptedParagraph, playerMap);
        }while (!decryptedParagraph.equalsIgnoreCase(chosenParagraph));

        int score = 1000 + (timeStart - timeEnd);
        System.out.println("GAME IS OVER");
        System.out.println("The paragraph was:\n" + "\n" + chosenParagraph);
        System.out.println();
        System.out.println("SCORE: " + score);
    }

    //Prints artistic pattern
    public static void divide(){
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
        System.out.println("********************************************************************************************************************");
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
        System.out.println();
    }


    //Store for individual paragraphs
    public static String[] createLibrary(){
        //Individual paragraph strings
        String strTolkien = "All that is gold does not glitter,\n" +
                "Not all those who wander are lost;\n" +
                "The old that is strong does not wither,\n" +
                "Deep roots are not reached by the frost.\n" +
                "\n" +
                "From the ashes a fire shall be woken,\n" +
                "A light from the shadows shall spring;\n" +
                "Renewed shall be blade that was broken,\n" +
                "The crownless again shall be king.";


        String strTolkien2 = "Three Rings for the Elven-kings under the sky,\n" +
                "Seven for the Dwarf-lords in their halls of stone,\n" +
                "Nine for Mortal Men doomed to die,\n" +
                "One for the Dark Lord on his dark throne\n" +
                "In the Land of Mordor where the Shadows lie.\n" +
                "One Ring to rule them all, One Ring to find them,\n" +
                "One Ring to bring them all, and in the darkness bind them,\n" +
                "In the Land of Mordor where the Shadows lie.";


        String strTolkien3 ="Arise now, arise, Riders of Théoden!\n" +
                "Dire deeds awake: dark is it eastward.\n" +
                "Let horse be bridled, horn be sounded!\n" +
                "Forth Eorlingas!\n" +
                "\n" +
                "Arise, arise, Riders of Théoden!\n" +
                "Fell deeds awake: fire and slaughter!\n" +
                "Spear shall be shaken, shield be splintered,\n" +
                "a sword-day, a red day, ere the sun rises!\n" +
                "Ride now, ride now! Ride to Gondor!";


        String strKafka = "As Gregor Samsa awoke one morning from uneasy dreams he found himself transformed\n" +
                "in his bed into a gigantic insect. He was lying on his hard, as it were armor-plated,\n" +
                " back and when he lifted his head a little he could see his dome-like brown belly divided\n" +
                " into stiff arched segments on top of which the bed quilt could hardly keep in position\n" +
                " and was about to slide off completely. His numerous legs, which were pitifully thin\n" +
                " compared to the rest of his bulk, waved helplessly before his eyes.";


        String strEdgar = "Once upon a midnight dreary, as I pondered weak and weary,\n" +
                "\n" +
                "Over many a quaint and curious volume of forgotten lore\n" +
                "\n" +
                "While I nodded, nearly napping, suddenly there came a tapping,\n" +
                "\n" +
                "As of someone gently rapping, rapping at my chamber door.";


        String strCSLewis = "To love at all is to be vulnerable.\n" +
                "Love anything and your heart will be wrung and possibly broken.\n" +
                "If you want to make sure of keeping it intact you must give it to no one,\n" +
                "not even an animal. Wrap it carefully round with hobbies and little luxuries;\n" +
                "avoid all entanglements. Lock it up safe in the casket or coffin of your\n" +
                "selfishness. But in that casket, safe, dark, motionless, airless, it will change.\n" +
                "It will not be broken; it will become unbreakable, impenetrable, irredeemable.\n" +
                "To love is to be vulnerable.";

        String strTest = "The quick brown fox jumps over the lazy dog.";    //TODO:TESTING ADDED

        String[] paragraphs = {strTolkien, strTolkien2, strTolkien3, strKafka, strEdgar, strCSLewis,strTest};

        return paragraphs;

    }


    //Creating truncated versions of paragraphs
    public static void createTruncated(String[] paragraphs, String[] truncated){
        String[] firstSentences = new String[paragraphs.length];

        for (int i = 0; i < firstSentences.length; i++){
            String[] tmp;
            tmp = paragraphs[i].split("\n");
            firstSentences[i] = tmp[0];
        }


        for (int i = 0; i < paragraphs.length; i++){
            int count = 0;
            if (firstSentences[i].length() < 50) truncated[i] = firstSentences[i];
            else{
                do {
                    if (firstSentences[i].charAt(50-count) == ' '){
                        truncated[i] = firstSentences[i].substring(0,50-count);
                        break;
                    }else{
                        count++;
                    }
                }while(count < 50);
            }
        }
    }



    public static void populatePlayerMap(HashMap<String,String> playerMap, HashMap<String,String> encryptionMap){
        for (String value:encryptionMap.values()) {
            playerMap.put(value,"");
        }
    }


    //Choosing a paragraph based on game mode
    public static int gameMode(String[] truncated){
        Random rnd = new Random();
        int choice;

        if (controlGameModeInput()){
            choice = rnd.nextInt(6);
        }else{
            choice = testModeChoices(truncated);
        }
        return choice;
    }


    //Controling input of Game Mode & returning the chosen mode
    public static boolean controlGameModeInput(){
        Scanner input = new Scanner(System.in);
        System.out.println("Choose your game mode:");
        System.out.println("1.Normal Mode\n2.Test Mode");
        char choice = input.next().charAt(0);

        do {
            if (choice == '1') return true;
            else if (choice == '2') return false;
            else{
                System.out.println("Invalid input detected. Please enter '1' for normal mode, '2' for test mode.");
                choice = input.next().charAt(0);
            }
        }while(true);
    }


    //Printing the choices for Test Mode
    public static int testModeChoices(String[] truncated){
        Scanner input = new Scanner(System.in);
        boolean isValid;
        int count;
        do {
            count = 1;
            System.out.println("Please choose one of the following phrases: ");
            System.out.println("********************************************");
            for (String str : truncated) {
                System.out.print(count++ + ".");
                System.out.println(str);
                System.out.println();
            }

            count = input.nextInt();
            isValid = controlTestInput(count);
        }while(!isValid);

        return (count-1);
    }


    //Controlling input of Test Mode choice
    public static boolean controlTestInput(int number){
        if (number == 1 || number == 2 || number == 3 || number == 4 || number == 5 || number == 6 || number == 7){ //TODO:TESTING ADDED
            return true;
        }else{
            System.out.println("You entered wrong input, please try again: ");
            return false;
        }
    }


    //Generating uppercase alphabet
    public static void createAlphabet(ArrayList<Character> alphabet){
        for (int i = 'A'; i <= 'Z'; i++){
            alphabet.add((char) i);
        }
    }


    //Generating random encryption map
    public static void createEncryptionMap(ArrayList<Character> alphabet, HashMap<String, String> encryption, String chosenParagraph){
        Random rnd = new Random();
        int tmp;
        boolean isOver;

        for (char letter:alphabet) {
            if (chosenParagraph.toUpperCase().contains(Character.toString(letter))){
                isOver = false;
                do {
                    tmp = rnd.nextInt(alphabet.size());
                    if (letter != alphabet.get(tmp) && !encryption.containsValue(Character.toString((char)(alphabet.get(tmp)+32)))){
                        encryption.put(Character.toString(letter),Character.toString((char)(alphabet.get(tmp) + 32)));
                        isOver = true;
                    }
                }while (!isOver);

            }
        }
    }


    //Encrypting the chosen paragraph
    public static String encryption(String chosenParagraph, HashMap<String,String> encryptionMap){
        String hiddenParagraph = chosenParagraph.toUpperCase();
        for (String key: encryptionMap.keySet()) {
           hiddenParagraph = hiddenParagraph.replaceAll(key,encryptionMap.get(key));
        }
        return hiddenParagraph;
    }


    //Printing the instructions
    public static void instructions(){
        System.out.println("Enter two letters: First one is the one you wanted to change, Second is what you wanted to change to:");
        System.out.println("(type help to get valid pair of letters up to 5 times, type info to see this again, and type reset to.. you know what it will do)");
        System.out.println();
    }


    //Taking input and passing to relevant methods
    public static int playerInput(HashMap<String,String> playerMap, HashMap<String,String> encryptionMap, int helpCount){
        Scanner input = new Scanner(System.in);
        String playerIn;
        do{
            System.out.println("Enter your letters: ");
            playerIn = input.nextLine();
        }while(playerIn.length() == 0);


        //dividing the turns with some pattern
        divide();

        if (playerIn.equalsIgnoreCase("help")){
            if (helpCount <= 5){
                help(playerMap,encryptionMap);
                helpCount++;
                return helpCount;
            } else {
                System.out.println("*****You used all the help, I know you could do it.*****");
                System.out.println();
            }
        }else if (playerIn.equalsIgnoreCase("reset")){
            reset(playerMap);
        }else if (playerIn.equalsIgnoreCase("info")){
            instructions();
        }else{
            char[] inputArr = playerIn.toCharArray();
            resolveInput(inputArr, playerMap);
        }
        return helpCount;
    }


    //Resolving 2 char player input for delete, assign or reassign
    public static void resolveInput(char[] inputArr, HashMap<String,String> playerMap){
        char key,value;
            if (inputArr.length == 1){
                value = inputArr[0];
                removeValue(playerMap,value);
            }else{
                key= inputArr[0];
                value = inputArr[1];
                if (playerMap.containsValue(Character.toString(value).toUpperCase())) removeValue(playerMap,value);
                playerMap.put(Character.toString(key),Character.toString(value).toUpperCase());
            }
    }


    //Removing a 'Value' with unknown 'Key', from map
    public static void removeValue(HashMap<String,String> playerMap, char value){
        String key = getKeyFromValue(playerMap,value);
        playerMap.put(key,"");
    }


    //Search and return a map 'Key' from a map 'Value'
    public static String getKeyFromValue(HashMap<String,String > map, char value){
        for (String key:map.keySet()) {
            if (Character.toString(value).toUpperCase().equals(map.get(key))){
                return key;
            }
        }
        return null;
    }
    public static String getKeyFromValue(HashMap<String,String > map, String value){
        for (String key:map.keySet()) {
            if (value.equalsIgnoreCase(map.get(key))){
                return key;
            }
        }
        return null;
    }


    //Resetting the previous pairings
    public static void reset(HashMap<String,String> playerMap){
        for (String key: playerMap.keySet()) {
            playerMap.put(key,"");
        }
    }


    //Helping player by giving them a randomly chose letter and it's pair
    public static void help(HashMap<String,String> playerMap, HashMap<String,String> encryptionMap) {
        Random rnd = new Random();
        String letter;

        ArrayList<String> encryptedLett = new ArrayList<>();

        for (String key:playerMap.keySet()) {
            if (playerMap.get(key).equals("")){
                encryptedLett.add(key);
            }
        }

        ArrayList<String> keysArray = new ArrayList<>(encryptionMap.keySet());

        if (encryptedLett.size() != 0){
            letter = encryptedLett.get(rnd.nextInt(encryptedLett.size()));
            playerMap.put(letter,getKeyFromValue(encryptionMap,letter));
        }else{
        do {
            letter = keysArray.get(rnd.nextInt(keysArray.size()));
            System.out.println(letter + " == " + encryptionMap.get(letter));
        }while(playerMap.get(encryptionMap.get(letter)).equals(letter));
        playerMap.put(encryptionMap.get(letter),letter);
        }
    }


    //Re-arranging original(computer generated) encrypted String with player inputs
    public static String reArrangeParagraph(String encryptedParagraph, HashMap<String,String> playerMap){
        for (String key:playerMap.keySet()) {
            if (!playerMap.get(key).equals("")) encryptedParagraph = encryptedParagraph.replaceAll(key,playerMap.get(key));
        }
        return  encryptedParagraph;
    }


    //Printing player affected letters
    public static void printKeyValue(HashMap<String,String> playerMap){
        System.out.println("--------------------PAIRS---------------------");
        int count = 0;
        for (String key:playerMap.keySet()) {
            if (!playerMap.get(key).equals("")){
                System.out.print(key +">" + playerMap.get(key) + "  ");
                count++;
            }
            if (count == 20) System.out.println();
        }
        System.out.println();
    }
}
