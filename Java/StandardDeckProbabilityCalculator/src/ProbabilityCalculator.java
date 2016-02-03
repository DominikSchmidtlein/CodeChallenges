import java.util.ArrayList;

public class ProbabilityCalculator {
	
	/**
	 * The number of total possible hands
	 */
	private int iterations;
	/**
	 * The number of hands that match the desired combination
	 */
	private int correctHands;
	
	private ArrayList<Card> deck;
	
	private enum Rank {ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING}
	private enum Suit {HEARTS, CLUBS, DIAMONDS, SPADES}
	
	/**
	 * A card from a standard 52 card deck with a rank and a suit
	 * @author domin_2o9sb4z
	 *
	 */
	private class Card {
		public Rank rank;
		public Suit suit;
		
		public Card(Rank rank, Suit suit){
			this.rank = rank;
			this.suit = suit;
		}
	}

	/**
	 * Calculates the probability that there are "cardCount" cards with rank
	 * matching "rank", from a hand of "numberOfCardsInHand" card hand.
	 * @param rank the rank of the desired card
	 * @param cardCount the number of cards that must match
	 * @param numberOfCardsInHand the total number of cards in the hand
	 * @param hand an array of all cards in the hand
	 */
	private void calculateProbability(Rank rank, int cardCount, int numberOfCardsInHand, ArrayList<Card> hand){
		if(numberOfCardsInHand == 0){
			iterations ++;
			if(countCards(rank, hand) == cardCount)
				correctHands ++;
			return;
		}
		for(Card card : deck){
			if(hand.contains(card))
				continue;
			ArrayList<Card> cards = new ArrayList<>(hand);
			cards.add(card);
			calculateProbability(rank, cardCount, numberOfCardsInHand - 1, cards);
		}
	}
	
	/**
	 * Counts the number of cards in the hand that have the given rank
	 * @param rank the desired rank of card to compare with
	 * @param hand an array of cards, can have matching rank, or not
	 * @return the number of cards in the hand whose rank matches input
	 */
	private int countCards(Rank rank, ArrayList<Card> hand){
		int count = 0;
		for(Card r : hand)
			if(r.rank == rank)
				count ++;
		return count;
	}
	
	/**
	 * Creates a standard 52 card deck.
	 * @return a standard 52 card deck
	 */
	private ArrayList<Card> generateDeck(){
		ArrayList<Card> cards = new ArrayList<>(52);
		for(Rank rank: Rank.values())
			for(Suit suit : Suit.values())
				cards.add(new Card(rank, suit));
		return cards;
				
	}
	
	public ProbabilityCalculator(){
		this.deck = generateDeck();
	}
	
	public static void main(String[] args) {
		ProbabilityCalculator calc = new ProbabilityCalculator();
		calc.calculateProbability(Rank.KING, 2, 3, new ArrayList<Card>());
		System.out.println("Hands: " + calc.correctHands);
		System.out.println("Iterations: " + calc.iterations);
		System.out.println("Probability: " + calc.correctHands * 1.0/calc.iterations);
	}
	
}
