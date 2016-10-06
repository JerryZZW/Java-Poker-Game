package PJ4;
import java.util.*;

/*
 * Ref: http://en.wikipedia.org/wiki/Video_poker
 *      http://www.freeslots.com/poker.htm
 *
 * Short Description and Poker rules:
 *
 * Video poker is also known as draw poker. 
 * The dealer uses a 52-card deck, which is played fresh after each playerHand. 
 * The player is dealt one five-card poker playerHand. 
 * After the first draw, which is automatic, you may hold any of the cards and draw 
 * again to replace the cards that you haven't chosen to hold. 
 * Your cards are compared to a table of winning combinations. 
 * The object is to get the best possible combination so that you earn the highest 
 * payout on the bet you placed. 
 *
 * Winning Combinations
 *  
 * 1. Jacks or Better: a pair pays out only if the cards in the pair are Jacks, 
 * 	Queens, Kings, or Aces. Lower pairs do not pay out. 
 * 2. Two Pair: two sets of pairs of the same card denomination. 
 * 3. Three of a Kind: three cards of the same denomination. 
 * 4. Straight: five consecutive denomination cards of different suit. 
 * 5. Flush: five non-consecutive denomination cards of the same suit. 
 * 6. Full House: a set of three cards of the same denomination plus 
 * 	a set of two cards of the same denomination. 
 * 7. Four of a kind: four cards of the same denomination. 
 * 8. Straight Flush: five consecutive denomination cards of the same suit. 
 * 9. Royal Flush: five consecutive denomination cards of the same suit, 
 * 	starting from 10 and ending with an ace
 *
 */


/* This is the main poker game class.
 * It uses Decks and Card objects to implement poker game.
 * Please do not modify any data fields or defined methods
 * You may add new data fields and methods
 * Note: You must implement defined methods
 */


public class VideoPoker {

    // default constant values
    private static final int startingBalance=100;
    private static final int numberOfCards=5;

    // default constant payout value and playerHand types
    private static final int[] multipliers={1,2,3,5,6,9,25,50,250};
    private static final String[] goodHandTypes={ 
	  "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	", 
	  "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

    // must use only one deck
    private static final Decks oneDeck = new Decks(1);

    // holding current poker 5-card hand, balance, bet    
    private List<Card> playerHand;
    private int playerBalance;
    private int playerBet;

    /** default constructor, set balance = startingBalance */
    public VideoPoker()
    {
	this(startingBalance);
    }

    /** constructor, set given balance */
    public VideoPoker(int balance)
    {
	this.playerBalance= balance;
    }

    /** This display the payout table based on multipliers and goodHandTypes arrays */
    private void showPayoutTable()
    { 
	System.out.println("\n\n");
	System.out.println("Payout Table   	      Multiplier   ");
	System.out.println("=======================================");
	int size = multipliers.length;
	for (int i=size-1; i >= 0; i--) {
		System.out.println(goodHandTypes[i]+"\t|\t"+multipliers[i]);
	}
	System.out.println("\n\n");
    }

    /** Check current playerHand using multipliers and goodHandTypes arrays
     *  Must print yourHandType (default is "Sorry, you lost") at the end of function.
     *  This can be checked by testCheckHands() and main() method.
     */
    private void checkHands()
    {
	// implement this method!
        
        int rank = 0;
        String ranks;
        
        //check goodHandTypes
        if (isRoyalPair() == true) {
            rank = 1;
        }
        if (isTwoPair() == true) {
            rank = 2;
        }
        if (isThreeOfAKind() == true) {
            rank = 3;
        }
        if (isStraight() == true) {
            rank = 4;
        }
        if (isFlush() == true) {
            rank = 5;
        }
        if (isFullHouse() == true) {
            rank = 6;
        }
        if (isFourOfAKind() == true) {
            rank = 7;
        }
        if (isStraightFlush() == true) {
            rank = 8;
        }
        if (isRoyalFlush() == true) {
            rank = 9;
        }
        
        //display the result of playerHand
        rank -= 1;
        if (rank < 0) {
            ranks = "Sorry, you lost!";

        } else {
            ranks = goodHandTypes[rank];
        }

        System.out.println("" + ranks + "!");

        //update playerBalance according to the goodHandTypes
        switch (ranks) {
           case "Royal Pair":
                this.playerBalance += (this.playerBet * multipliers[0]);
                break;
            case "Two Pairs":
               this.playerBalance += (this.playerBet * multipliers[1]);
                break;
            case "Three of a Kind":
               this.playerBalance += (this.playerBet * multipliers[2]);
                break;
           case "Straight":
               this.playerBalance += (this.playerBet * multipliers[3]);
                break;
            case "Flush	":
               this.playerBalance += (this.playerBet * multipliers[4]);
                break;
            case "Full House":
                this.playerBalance += (this.playerBet * multipliers[5]);
               break;
            case "Four of a Kind":
                this.playerBalance += (this.playerBet * multipliers[6]);
               break;
            case "Straight Flush":
                this.playerBalance += (this.playerBet * multipliers[7]);
                break;
            case "Royal Flush":
                this.playerBalance += (this.playerBet * multipliers[8]);
                break;
            default:
                break;
        }
        
    }


    /*************************************************
     *   add other private methods here ....
     *
     *************************************************/
    
    //sort playerHand
    private List<Card> sortedPlayerHand() {
        List<Card> sortedHand = new ArrayList<Card>();
        for (int i = 0; i < playerHand.size(); i++) {
            sortedHand.add(playerHand.get(i));
        }
        
        Collections.sort(sortedHand, new Comparator<Card>() {
            @Override
            public int compare(Card card1, Card card2) {
                return card1.getRank() - card2.getRank();
            }
        });
        
        return sortedHand;
    }
    
    //check if goodHandType is straight
    private boolean isStraight() {
        int countRank = 0;
        int countSuit = 0;
        for (int i = 0; i < (numberOfCards - 1); i++) {
            if ((sortedPlayerHand().get(i).getRank() + 1) == sortedPlayerHand().get(i + 1).getRank()) {
                countRank++;
                if (sortedPlayerHand().get(i).getSuit() != sortedPlayerHand().get(i + 1).getSuit()) {
                    countSuit++;
                }
            } 
        }
        
        if (countRank == 4 && countSuit > 0) {
            return true;
        }
        else {
            return false;
        }
    }
    
    //check if goodHandType is flush
    private boolean isFlush() {
        int countRank = 0;
        int countSuit = 0;
        for (int i = 0; i < (numberOfCards - 1); i++) {
            if (sortedPlayerHand().get(i).getSuit() == sortedPlayerHand().get(i + 1).getSuit()) {
                countSuit++;
                if ((sortedPlayerHand().get(i).getRank() + 1) != sortedPlayerHand().get(i + 1).getRank()) {
                    countRank++;
                }
            }
        }
        
        if (countSuit == 4 && countRank > 0) {
            return true;
        }
        else {
            return false;
        }
    }
    
    //check if goodHandType is straight flush
    private boolean isStraightFlush() {
        int count = 0;
        for (int i = 0; i < (numberOfCards - 1); i++) {
            if ((sortedPlayerHand().get(i).getRank() + 1) == sortedPlayerHand().get(i + 1).getRank()) {
                if (sortedPlayerHand().get(i).getSuit() == sortedPlayerHand().get(i + 1).getSuit()) {
                    count++;
                }
            }
        }
        
        if (count == 4) {
            return true;
        }
        else {
            return false;
        }
    }
    
    //check if goodHandType is royal flush
    private boolean isRoyalFlush() {
        int count = 0;
        if ((sortedPlayerHand().get(0).getRank() == 1) && (sortedPlayerHand().get(1).getRank() == 10) && (sortedPlayerHand().get(2).getRank() == 11) && (sortedPlayerHand().get(3).getRank() == 12) && (sortedPlayerHand().get(4).getRank() == 13)) {
            for (int i = 0; i < (numberOfCards - 1); i++) {
                if (sortedPlayerHand().get(i).getSuit() == sortedPlayerHand().get(i + 1).getSuit()) {
                    count++;
                }
            }
        }
        
        if(count == 4) {
            return true;
        }
        else {
            return false;
        }
    }
    
    //check if goodHandType is four of a kind
    private boolean isFourOfAKind() {
        int count = 0;
        for (int i = 0; i < (numberOfCards - 1); i++) {
            if (sortedPlayerHand().get(i).getRank() == sortedPlayerHand().get(i + 1).getRank()) {
                count++;
            }
        }
        
        if (count == 3 && isFullHouse() == false) {
            return true;
        }
        else {
            return false;
        }
    }
    
    //check if goodHandType is full house
    private boolean isFullHouse() {
        int count = 0;
        List<Integer> Rank = new ArrayList<Integer>();
        
        for (int i = 0; i < (numberOfCards - 1); i++) {
            if (sortedPlayerHand().get(i).getRank() == sortedPlayerHand().get(i + 1).getRank()) {
                Rank.add(sortedPlayerHand().get(i).getRank());
                count++;
            }
        }
        
        if (count == 3 && (Rank.get(0) == Rank.get(1) && Rank.get(1) != Rank.get(2))) {
            return true;
        }
        else {
            return false;
        }
    }
    
    //check if goodHandType is three of a kind
    private boolean isThreeOfAKind() {
        int count = 0;
        for (int i = 0; i < (numberOfCards - 1); i++) {
            if (sortedPlayerHand().get(i).getRank() == sortedPlayerHand().get(i + 1).getRank()) {
                count++;
            }
        }
        
        if (count == 2 && isTwoPair() == false) {
            return true;
        }
        else {
            return false;
        }
    }
    
    //check if goodHandType is two pair
    private boolean isTwoPair() {
        int rank1 = 0;
        int rank2 = 0;
        for (int i = 0; i < (numberOfCards - 1); i++) {
            if (sortedPlayerHand().get(i).getRank() == sortedPlayerHand().get(i + 1).getRank()) {
                if (rank1 == 0) {
                    rank1 = sortedPlayerHand().get(i).getRank();
                }
                if (rank2 == 0 && rank1 != sortedPlayerHand().get(i).getRank()) {
                    rank2 = sortedPlayerHand().get(i).getRank();
                }
            }
        }
        
        if (rank1 != rank2 && rank2 != 0) {
            return true;
        }
        else {
            return false;
        }
        
    }
    
    //check if goodHandType is royal pair
    public boolean isRoyalPair() {
        int count = 0;
        for (int i = 0; i < (numberOfCards - 1); i++) {
            if (sortedPlayerHand().get(i).getRank() == sortedPlayerHand().get(i + 1).getRank()) {
                if ((sortedPlayerHand().get(i).getRank() == 11) || (sortedPlayerHand().get(i).getRank() == 12) || (sortedPlayerHand().get(i).getRank() == 13) || (sortedPlayerHand().get(i).getRank() == 1)) {
                    count++;
                }
            }
        }
        
        if (count == 1) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public void play() 
    {
    /** The main algorithm for single player poker game 
     *
     * Steps:
     * 		showPayoutTable()
     *
     * 		++	
     * 		show balance, get bet 
     *		verify bet value, update balance
     *		reset deck, shuffle deck, 
     *		deal cards and display cards
     *		ask for positions of cards to keep  
     *          get positions in one input line
     *		update cards
     *		check hands, display proper messages
     *		update balance if there is a payout
     *		if balance = O:
     *			end of program 
     *		else
     *			ask if the player wants to play a new game
     *			if the answer is "no" : end of program
     *			else : showPayoutTable() if user wants to see it
     *			goto ++
     */


        // implement this method!
        //showPayoutTable()
        boolean newGame = true;
        List<Card> keepCard = new ArrayList<Card>();
        System.out.println("******************************************************************************************");
        showPayoutTable();
        System.out.println();
        System.out.println();
        
        //continue the loop until balance == 0 or users want to stop the game 
        while (playerBalance > 0 && newGame == true) {
            int count = 0;
            int countError = 0;
            boolean newTable = true;
            
            //show balance, get bet
            System.out.println("-----------------------------------");
            System.out.println("Balance:$" + playerBalance);
            
            //verify bet value, update balance
            while (true) {
                System.out.print("Enter bet:");
                Scanner userInput = new Scanner(System.in);
                if (userInput.hasNextInt()) {
                    playerBet = userInput.nextInt();
                    if (playerBet > 0 && playerBet <= playerBalance) {
                        playerBalance = playerBalance - playerBet;
                        break;
                    }
                    else {
                        System.out.println("Please enter a postive integer which is not greater than " + playerBalance + "!");
                    }
                }
                else {
                    System.out.println("Please enter a postive integer which is not greater than " + playerBalance + "!");
                }
            }
            
            //reset deck, shuffle deck
            oneDeck.reset();
            oneDeck.shuffle();
            
            //deal cards and display cards
            try {
                playerHand = oneDeck.deal(numberOfCards);
                System.out.println("Hand:" + playerHand);
            } catch (PlayingCardException e) {
                System.out.println(e.getMessage());
            }
            
            //get positions in one input line and verify the userInput
            do {
                System.out.print("Enter positions of cards to keep :");
                Scanner userInput2 = new Scanner(System.in);
                String positions = userInput2.nextLine();
                userInput2 = new Scanner(positions);
                userInput2 = userInput2.useDelimiter("\\s*");
                while (userInput2.hasNext()) {
                    if (userInput2.hasNextInt()) {
                        int index = userInput2.nextInt();
                        if (index <= 5 && index > 0) {
                            keepCard.add(playerHand.get(index - 1));
                            count++;
                            countError = 0;
                        }
                        else {
                            System.out.println("Try again! You can only enter at most 5 positive integers which are not greater than 5!");
                            keepCard.clear();
                            countError++;
                            break;
                        }
                    }
                    else {
                        System.out.println("Try again! You can only enter at most 5 positive integers which are not greater than 5!");
                        keepCard.clear();
                        countError++;
                        break;
                    }
                }
                
                if (count == 0 && userInput2.hasNext() == false && countError == 0) {
                    break;
                }
                else if (count <= 5 && userInput2.hasNext() == false && countError == 0) {
                    break;
                }
                else {
                    System.out.println("Try again! You can only enter at most 5 positive integers which are not greater than 5!");
                    keepCard.clear();
                    countError++;
                }
            } while (countError > 0);
            
            //update cards
            playerHand = keepCard;
            try {
                playerHand.addAll(oneDeck.deal(5 - count));
            } catch (PlayingCardException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Hand:" + playerHand);
            
            //check hands, display proper messages
            checkHands();
            System.out.println();
            
            //if balance = O: end of program
            if (playerBalance == 0) {
                System.out.println("Your balance is 0");
                System.out.println("Bye!");
                break;
            }
            //else: ask if the player wants to play a new game and check the userInput
            else {
                while (newTable == true) {
                    System.out.println("Your balance:$" + playerBalance + ", one more game (y or n)?");
                    Scanner userInput3 = new Scanner(System.in);
                    if (userInput3.hasNext()) {
                        String YorN = userInput3.next();
                        switch (YorN) {
                            case "y":
                                while (newTable == true) {
                                    System.out.println();//showPayoutTable() if user wants to see it and check the userInput
                                    System.out.println("Want to see payout table (y or n)?");
                                    Scanner userInput4 = new Scanner(System.in);
                                    if (userInput4.hasNext()) {
                                        String Y_or_N = userInput4.next();
                                        switch (Y_or_N) {
                                            case "y":
                                                showPayoutTable();
                                                newTable = false;
                                                break;
                                            case "n":
                                                newTable = false;
                                                break;
                                            default:
                                                System.out.println();
                                                System.out.println("Please enter 'y' or 'n'");
                                                break;
                                        }
                                    }
                                    else {
                                        System.out.println();
                                        System.out.println("Please enter 'y' or 'n'");
                                    }
                                }   
                                break;
                            case "n"://if the answer is "no" : end of program
                                newGame = false;
                                newTable = false;
                                break;
                            default:
                                System.out.println();   
                                System.out.println("Please enter 'y' or 'n'");
                                break;
                        }
                    } else {
                        System.out.println();
                        System.out.println("Please enter 'y' or 'n'");
                    }
                }
            }
            
            keepCard.clear();
            playerHand.clear();
        }
    }



    /*************************************************
     *   Do not modify methods below
    /*************************************************


    /** testCheckHands() is used to test checkHands() method 
     *  checkHands() should print your current hand type
     */ 

    private void testCheckHands()
    {
      	try {
    		playerHand = new ArrayList<Card>();

		// set Royal Flush
		playerHand.add(new Card(4,1));
		playerHand.add(new Card(4,10));
		playerHand.add(new Card(4,12));
		playerHand.add(new Card(4,11));
		playerHand.add(new Card(4,13));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Straight Flush
		playerHand.set(0,new Card(4,9));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Straight
		playerHand.set(4, new Card(2,8));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Flush 
		playerHand.set(4, new Card(4,5));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	", 
	 	// "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

		// set Four of a Kind
		playerHand.clear();
		playerHand.add(new Card(4,8));
		playerHand.add(new Card(1,8));
		playerHand.add(new Card(4,12));
		playerHand.add(new Card(2,8));
		playerHand.add(new Card(3,8));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Three of a Kind
		playerHand.set(4, new Card(4,11));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Full House
		playerHand.set(2, new Card(2,11));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Two Pairs
		playerHand.set(1, new Card(2,9));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Royal Pair
		playerHand.set(0, new Card(2,3));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// non Royal Pair
		playerHand.set(2, new Card(4,3));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");
      	}
      	catch (Exception e)
      	{
		System.out.println(e.getMessage());
      	}
    }


    /* Run testCheckHands() */
    public static void main(String args[]) 
    {
	VideoPoker pokergame = new VideoPoker();
	pokergame.testCheckHands();
    }
}
