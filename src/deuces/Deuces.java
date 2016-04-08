package deuces;

import java.awt.Dimension;

import ks.client.gamefactory.GameWindow;
import ks.launcher.Main;
import ks.common.controller.SolitaireMouseMotionAdapter;
import ks.common.games.Solitaire;
import ks.common.games.SolitaireUndoAdapter;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.MultiDeck;
import ks.common.model.Pile;
import ks.common.model.Stack;
import ks.common.view.CardImages;
import ks.common.view.ColumnView;
import ks.common.view.DeckView;
import ks.common.view.FanPileView;
import ks.common.view.IntegerView;
import ks.common.view.PileView;

public class Deuces extends Solitaire{
	
	MultiDeck deck;
	Column waste;
	Pile foundations[];
	Column tableaus[];
	
	DeckView deckView;
	FanPileView wasteView;
	PileView foundationViews[];
	ColumnView tableauViews[];
	
	IntegerView scoreView, numLeftView;
	
	private void initializeModel(int seed) {
		
		foundations = new Pile[8];
		tableaus = new Column[10];
		
		for (int i = 0; i<8; i++) {
			Pile found = new Pile("foundation"+i);
			foundations[i] = found;
			addModelElement(found);
		}
		
		for (int i = 0; i<10; i++) {
			Column tab = new Column("tableau"+i);
			tableaus[i] = tab;
			addModelElement(tab);
		}
		
		/**
		 * Create deck, then iterate through it to extract just the two's and insert them into the foundations. The extra cards are then reinserted into the deck
		 */
		deck = new MultiDeck(2);
		deck.create(seed);
		addModelElement(deck);
		
		Stack notTwos = new Stack();
		int currFoundation = 0;
		for (int i = 0; i < 104; i++) {
			Card c = deck.get();
			if (c.getRank() == Card.TWO) {
				foundations[currFoundation].add(c);
				currFoundation++;
			} else {
				notTwos.add(c);
			}
		}
		deck.push(notTwos);
		
		waste = new Column("waste");
		addModelElement(waste);
	}
	
	private void initializeView() {
		CardImages ci = getCardImages();
		int w = ci.getWidth();
		int h = ci.getHeight();
		int o = ci.getOverlap();
		
		deckView = new DeckView(deck);
		deckView.setBounds(260 + 10*w, 20, w, h);
		addViewWidget(deckView);
		
		foundationViews = new PileView[8];
		tableauViews = new ColumnView[10];
		
		for (int i = 0; i<8; i++) {
			PileView foundView = new PileView(foundations[i]);
			foundView.setBounds((20+(w+20)*(i+1)), 20, w, h);
			foundationViews[i] = foundView;
			addViewWidget(foundView);
		}
		
		for (int i = 0; i<10; i++) {
			ColumnView tabView = new ColumnView(tableaus[i]);
			tabView.setBounds((20+(w+20)*i), 40+h, w, h+12*o);
			tableauViews[i] = tabView;
			addViewWidget(tabView);
		}
		
		wasteView = new FanPileView(1, waste);
		wasteView.setBounds(260+10*w, 40+h, w, h);
		addViewWidget(wasteView);
		
		scoreView = new IntegerView(score);
		scoreView.setBounds(20, 20, w, h);
		addViewWidget(scoreView);
		
		numLeftView = new IntegerView(numLeft);
		numLeftView.setBounds(9*w+200, 20, 100, h);
		addViewWidget(numLeftView);
	}
	
	void initializeControllers() {
		deckView.setMouseAdapter(new DeucesDeckController(this, deck, waste));
		deckView.setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
		deckView.setUndoAdapter(new SolitaireUndoAdapter(this));
		
		wasteView.setMouseAdapter(new DeucesWastePileController(this, wasteView));
		wasteView.setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
		wasteView.setUndoAdapter(new SolitaireUndoAdapter(this));
	};
	
	@Override
	public boolean hasWon() {
		return false;
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Deuces";
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		initializeModel(getSeed());
		initializeView();
		initializeControllers();
		
		updateScore(0);
		updateNumberCardsLeft(96);

	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension (1110, 600);
	}
	
	/** Code to launch solitaire variation. */
	public static void main (String []args) {
		// Seed is to ensure we get the same initial cards every time.
		GameWindow gw = Main.generateWindow(new Deuces(), Deck.OrderByRank);
		gw.setVisible(true);
	}
	
}
