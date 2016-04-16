package hejohnson;

import java.awt.event.MouseEvent;

import hejohnson.DealCardMove;
import hejohnson.Deuces;
import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;

public class TestDeuces extends KSTestCase {
	Deuces deuces;
	protected void setUp() {
		deuces = new Deuces();
		GameWindow gw = Main.generateWindow(deuces, Deck.OrderByRank);
	}
	
	public void testDeckController() {
		Card topCard = deuces.deck.peek();
		MouseEvent pr = createPressed (deuces, deuces.deckView, 0, 0);
		deuces.deckView.getMouseManager().handleMouseEvent(pr);
		assertEquals(85, deuces.deck.count());
		assertEquals(85, deuces.getNumLeft().getValue());
		assertEquals (topCard, deuces.waste.peek());
		assertTrue (deuces.undoMove());
		assertTrue (deuces.waste.empty());
		assertEquals(86, deuces.deck.count());
		assertEquals(topCard, deuces.deck.peek());
	}
	
	public void testTableausAndFoundationsController() {
		deuces.tableaus[0].removeAll();
		deuces.tableaus[1].removeAll();
		deuces.waste.removeAll();
		deuces.tableaus[0].add(new Card(Card.FIVE, Card.CLUBS));
		deuces.tableaus[1].add(new Card(Card.FOUR, Card.CLUBS));
		deuces.waste.add(new Card(Card.THREE, Card.CLUBS));
		
		MouseEvent pr = createPressed (deuces, deuces.wasteView, 0, 0);
		deuces.wasteView.getMouseManager().handleMouseEvent(pr);
		MouseEvent rl = createReleased (deuces, deuces.tableauViews[1], 0, 0);
		deuces.tableauViews[1].getMouseManager().handleMouseEvent(rl);
		
		assertTrue(deuces.waste.empty());
		assertEquals(2, deuces.tableaus[1].count());
		
		pr = createPressed(deuces, deuces.tableauViews[1], 0, 0);
		deuces.tableauViews[1].getMouseManager().handleMouseEvent(pr);
		rl = createReleased(deuces, deuces.tableauViews[0], 0, 0);
		deuces.tableauViews[0].getMouseManager().handleMouseEvent(rl);
		
		assertTrue(deuces.tableaus[1].empty());
		assertEquals(3, deuces.tableaus[0].count());
		
		pr = createPressed (deuces, deuces.tableauViews[0], 0, 0);
		deuces.tableauViews[0].getMouseManager().handleMouseEvent(pr);
		rl = createReleased (deuces, deuces.foundationViews[3], 0, 0);
		deuces.foundationViews[3].getMouseManager().handleMouseEvent(rl);
		
		assertTrue(deuces.tableaus[0].empty());
		assertEquals(4, deuces.foundations[3].count());
	
		assertEquals(11, deuces.getScoreValue());
		
		assertTrue(deuces.undoMove());
		
		assertEquals(8, deuces.getScoreValue());
		assertEquals(3, deuces.tableaus[0].count());
		assertEquals(1, deuces.foundations[1].count());
		
		assertTrue(deuces.undoMove());
		assertEquals(2, deuces.tableaus[1].count());
		assertEquals(1, deuces.tableaus[0].count());
		
		assertTrue(deuces.undoMove());
		assertEquals(1, deuces.tableaus[1].count());
		assertEquals(1, deuces.waste.count());

	}
	
}
