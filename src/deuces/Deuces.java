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
	Pile foundation1, foundation2, foundation3, foundation4, foundation5, foundation6, foundation7, foundation8;
	Column tableau1, tableau2, tableau3, tableau4, tableau5, tableau6, tableau7, tableau8, tableau9, tableau10;
	Column waste;
	Pile foundations[];
	
	DeckView deckView;
	PileView foundationView1, foundationView2, foundationView3, foundationView4, foundationView5, foundationView6, foundationView7, foundationView8;
	ColumnView tableauView1, tableauView2, tableauView3, tableauView4, tableauView5, tableauView6, tableauView7, tableauView8, tableauView9, tableauView10;
	FanPileView wasteView;
	
	IntegerView scoreView, numLeftView;
	
	private void initializeModel(int seed) {
		
		foundations = new Pile[8];
		
		foundation1 = new Pile("foundation1");
		foundations[0] = foundation1;
		addModelElement(foundation1);
		foundation2 = new Pile("foundation2");
		foundations[1] = foundation2;
		addModelElement(foundation2);
		foundation3 = new Pile("foundation3");
		foundations[2] = foundation3;
		addModelElement(foundation3);
		foundation4 = new Pile("foundation4");
		foundations[3] = foundation4;
		addModelElement(foundation4);
		foundation5 = new Pile("foundation5");
		foundations[4] = foundation5;
		addModelElement(foundation5);
		foundation6 = new Pile("foundation6");
		foundations[5] = foundation6;
		addModelElement(foundation6);
		foundation7 = new Pile("foundation7");
		foundations[6] = foundation7;
		addModelElement(foundation7);
		foundation8 = new Pile("foundation8");
		foundations[7] = foundation8;
		addModelElement(foundation8);
		
		tableau1 = new Column("tableau1");
		addModelElement(tableau1);
		tableau2 = new Column("tableau2");
		addModelElement(tableau2);
		tableau3 = new Column("tableau3");
		addModelElement(tableau3);
		tableau4 = new Column("tableau4");
		addModelElement(tableau4);
		tableau5 = new Column("tableau5");
		addModelElement(tableau5);
		tableau6 = new Column("tableau6");
		addModelElement(tableau6);
		tableau7 = new Column("tableau7");
		addModelElement(tableau7);
		tableau8 = new Column("tableau8");
		addModelElement(tableau8);
		tableau9 = new Column("tableau9");
		addModelElement(tableau7);
		tableau10 = new Column("tableau10");
		addModelElement(tableau8);
		
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
		
		foundationView1 = new PileView(foundation1);
		foundationView1.setBounds(40+w, 20, w, h);
		addViewWidget(foundationView1);
		foundationView2 = new PileView(foundation2);
		foundationView2.setBounds(60+2*w, 20, w, h);
		addViewWidget(foundationView2);
		foundationView3 = new PileView(foundation3);
		foundationView3.setBounds(80+3*w, 20, w, h);
		addViewWidget(foundationView3);
		foundationView4 = new PileView(foundation4);
		foundationView4.setBounds(100+4*w, 20, w, h);
		addViewWidget(foundationView4);
		foundationView5 = new PileView(foundation5);
		foundationView5.setBounds(120+5*w, 20, w, h);
		addViewWidget(foundationView5);
		foundationView6 = new PileView(foundation6);
		foundationView6.setBounds(140+6*w, 20, w, h);
		addViewWidget(foundationView6);
		foundationView7 = new PileView(foundation7);
		foundationView7.setBounds(160+7*w, 20, w, h);
		addViewWidget(foundationView7);
		foundationView8 = new PileView(foundation8);
		foundationView8.setBounds(180+8*w, 20, w, h);
		addViewWidget(foundationView8);
		
		tableauView1 = new ColumnView(tableau1);
		tableauView1.setBounds(20, 40+h, w, h+12*o);
		addViewWidget(tableauView1);
		tableauView2 = new ColumnView(tableau2);
		tableauView2.setBounds(40+w, 40+h, w, h+12*o);
		addViewWidget(tableauView2);
		tableauView3 = new ColumnView(tableau3);
		tableauView3.setBounds(60+2*w, 40+h, w, h+12*o);
		addViewWidget(tableauView3);
		tableauView4 = new ColumnView(tableau4);
		tableauView4.setBounds(80+3*w, 40+h, w, h+12*o);
		addViewWidget(tableauView4);
		tableauView5 = new ColumnView(tableau5);
		tableauView5.setBounds(100+4*w, 40+h, w, h+12*o);
		addViewWidget(tableauView5);
		tableauView6 = new ColumnView(tableau6);
		tableauView6.setBounds(120+5*w, 40+h, w, h+12*o);
		addViewWidget(tableauView6);
		tableauView7 = new ColumnView(tableau7);
		tableauView7.setBounds(140+6*w, 40+h, w, h+12*o);
		addViewWidget(tableauView7);
		tableauView8 = new ColumnView(tableau8);
		tableauView8.setBounds(160+7*w, 40+h, w, h+12*o);
		addViewWidget(tableauView8);
		tableauView9 = new ColumnView(tableau9);
		tableauView9.setBounds(180+8*w, 40+h, w, h+12*o);
		addViewWidget(tableauView9);
		tableauView10 = new ColumnView(tableau10);
		tableauView10.setBounds(200+9*w, 40+h, w, h+12*o);
		addViewWidget(tableauView10);
		
		wasteView = new FanPileView(1, waste);
		wasteView.setBounds(260+10*w, 40+h, w, h);
		addViewWidget(wasteView);
	}
	
	void initializeControllers() {
		deckView.setMouseAdapter(new DeucesDeckController(this, deck, waste));
		deckView.setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
		deckView.setUndoAdapter(new SolitaireUndoAdapter(this));
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
