/*
 * Author: Surender Kumar
 * Contact: oksuren@gmail.com
 * Mobile: +91 - 9604433613
 */

import java.util.*;

class Card implements Comparable<Card> {
	/* Class representing the a card in the deck. */
        int face; /* Represents the face-value of the card. */
        char suit; /* Represents the first letter of suit-name that the card belongs to. */

        public static final Comparator<Card> CardComparator = new Comparator<Card>() {
                @Override
                public int compare(Card c1, Card c2) {
                        return c1.face - c2.face;
                }
        };

	Card (int face, char suit) {
		this.face = face;
		this.suit = suit;
        }

        Card (Card c) {
                this.face = c.face;
                this.suit = c.suit;
        }

        public void displayCard () {
                System.out.print(""+this.face+""+this.suit);
        }

	/* Following method will help each player maintain their cards sorted. */
        @Override
        public int compareTo(Card c) {
                return this.face - c.face;
        }

}

class Player {
	ArrayList <Card> handCards;

	Player () {
		handCards = new ArrayList<Card>();
	}

	public void displayPlayerCards () {
                /* Displays all the cards in the hands of the player. */
                int i;
                for (i = 0; i < this.handCards.size() - 1; ++i) {
                        (this.handCards.get(i)).displayCard();
                        System.out.print(", ");
                }
                (this.handCards.get(i)).displayCard();
                System.out.println("");
        }

	/*
	 * Win strategy-1
	 * As mentioned in the problem description, a player wins if he/she...
	 * - has all 4 cards with same face value from different suits and
	 * - has remaining all 3 with same face value, ofcourse each from different suit.
	 */
	public boolean isWinStrategy1 () {
		int i;
		boolean p = false;
		for (i = 0; i < this.handCards.size() - 4; ++i) {
			if ((this.handCards.get(i).face == this.handCards.get(i+1).face) &&
			    (this.handCards.get(i).face == this.handCards.get(i+2).face) &&
			    (this.handCards.get(i).face == this.handCards.get(i+3).face)) { 
				p = true;
				break;
			}
		}

		if (p) {
			if ((i == 0) &&
			    (this.handCards.get(i+4).face == this.handCards.get(i+5).face) &&
			    (this.handCards.get(i+4).face == this.handCards.get(i+6).face)) {
				return true;
			} else if (i == 3) {
				if ((this.handCards.get(0).face == this.handCards.get(1).face) &&
                            	    (this.handCards.get(0).face == this.handCards.get(2).face)) {
					return true;
				}
			}
		}
		return false;
	}

	/*
         * Win strategy-2
         * As mentioned in the problem description, a player wins if he/she...
         * - has 4 cards from the same suit with sequential face value and
         * - has remaining all 3 from same suit with sequntial face values.
         */
        public boolean isWinStrategy2 () {
		int i;
                boolean p = false;
                for (i = 0; i < this.handCards.size() - 4; ++i) {
			if ((this.handCards.get(i).face + 1 == this.handCards.get(i+1).face)   &&
			    (this.handCards.get(i).suit == this.handCards.get(i+1).suit)       &&
			    (this.handCards.get(i+1).face + 1 == this.handCards.get(i+2).face) &&
			    (this.handCards.get(i+1).suit == this.handCards.get(i+2).suit)     &&
			    (this.handCards.get(i+2).face + 1 == this.handCards.get(i+3).face)) {
				p = true;
				break;
			}
		}

		if (p) {
                        if ((i == 0) &&
                            (this.handCards.get(i+4).face + 1 == this.handCards.get(i+5).face) &&
			    (this.handCards.get(i+4).suit == this.handCards.get(i+5).suit)     &&
                            (this.handCards.get(i+5).face + 1 == this.handCards.get(i+6).face) &&
			    (this.handCards.get(i+5).suit == this.handCards.get(i+6).suit)) {
                                return true;
                        } else if (i == 3) {
				if ((this.handCards.get(0).face + 1 == this.handCards.get(1).face) &&
			    	    (this.handCards.get(i+4).suit == this.handCards.get(i+5).suit) &&
                                    (this.handCards.get(1).face + 1 == this.handCards.get(2).face) &&
				    (this.handCards.get(i+5).suit == this.handCards.get(i+6).suit)) {
                                	return true;
				}
                        }
                }
                return false;
	}
}

class Deck {
	ArrayList<Card> deck;
	ArrayList<Card> discardPileOfCards;

	Deck () {
		/* Lets form a deck. */
		deck = new ArrayList<Card>();
		discardPileOfCards = new ArrayList<Card>();
		int i;
                for (i = 1; i < 14; ++i) {
                        (this.deck).add(new Card(i, 'D'));
                }

                for (i = 1; i < 14; ++i) {
                        (this.deck).add(new Card(i, 'H'));
                }

                for (i = 1; i < 14; ++i) {
                        (this.deck).add(new Card(i, 'S'));
                }

                for (i = 1; i < 14; ++i) {
                        (this.deck).add(new Card(i, 'C'));
                }
	}

        public void displayDeck() {
                System.out.print("Deck: ");
                int i;
                for (i = 0; i < this.deck.size() - 1; ++i) {
                        (this.deck.get(i)).displayCard();
                        System.out.print(", ");
                }
                (this.deck.get(i)).displayCard();
                System.out.println("");
        }

	/*
         * Following method allow to shuffle the deck.
         */
        public void shuffleTheDeck () {
                Collections.shuffle(this.deck);
        }

        /*
         * Following method allow hands to draw a card during the game.
         */
        public Card drawTopOfDeck () {
                Card topOfDeck = this.deck.get(0);
                this.deck.remove(0);
                return topOfDeck;
        }

        /*
         * Following method allow hands of varying sizes to be
         * dealt to a varying number of players.
         */
	public void dealPlayersCards (Player[] p, int dealSize) {
		int i, j;

		for (i = 0; i < p.length; ++i) {
			for (j = 0; j < dealSize; ++j) {
				(p[i].handCards).add(this.deck.get(0));
				this.deck.remove(0);
			}
		}
	}

}

public class Rummy1 {
	public static void main(String[] args) {
		int i, noOfPlayers = 0, turn = 0;
		if((args.length == 0) ||
		   (noOfPlayers = Integer.parseInt(args[0])) != 2 && noOfPlayers != 4) {
			System.out.println("I am sorry! I offers 2/4 players, please restart the game with valid number of players.");
			System.exit(0);
		}
		ArrayList<Player> players_list = new ArrayList<Player>();
		for (i = 0; i < noOfPlayers; ++i) {
			players_list.add(new Player());
		}
		Player[] players = {};
		players = players_list.toArray(players);
		Deck D = new Deck();
		D.shuffleTheDeck();

		D.dealPlayersCards(players, 7);

		while (!((players[turn].isWinStrategy1()) ||
			(players[turn].isWinStrategy2()))) {
			if (D.deck.size() != 0) {
				System.out.println("Turn: "+(turn+1));
				System.out.print("Before move: ");
				players[turn].displayPlayerCards();
				players[turn].handCards.add(D.drawTopOfDeck());
				Collections.sort(players[turn].handCards, Card.CardComparator);
				D.discardPileOfCards.add(players[turn].handCards.get(0));
				players[turn].handCards.remove(0);
				System.out.print("After move: ");
				players[turn].displayPlayerCards();
				System.out.println("");
				turn = (turn + 1)%(noOfPlayers);
			} else {
				System.out.println("Game Draw! Let's redistribute cards...");
				System.out.println("");
				for (i = 0; i < noOfPlayers; ++i) {
					D.deck.addAll(players[i].handCards);
					players[i].handCards.clear();
				}
				D.deck.addAll(D.discardPileOfCards);
				D.discardPileOfCards.clear();
				D.shuffleTheDeck();
				D.dealPlayersCards(players, 7);
			}
		}
		System.out.println("Winner is player: "+(turn+1));
		players[turn].displayPlayerCards();
	}
}
