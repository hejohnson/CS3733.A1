package deuces;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;

public class TestDealCardMove extends TestCase {
	public void testDealing() {
		Deuces deuces = new Deuces();
		GameWindow gw = Main.generateWindow(deuces, Deck.OrderByRank);
		
		Card topCard = deuces.deck.peek();
		DealCardMove dcm = new DealCardMove(deuces.deck, deuces.waste);
		
		assertTrue(dcm.valid(deuces));
		dcm.doMove(deuces);
		assertEquals(95, deuces.deck.count());
		assertEquals(topCard, deuces.waste.peek());
		int cardsLeft = deuces.getNumLeft().getValue();
		assertEquals(95, cardsLeft);
		dcm.undo(deuces);
		assertEquals(96, deuces.deck.count());
		assertEquals(topCard, deuces.deck.peek());
	}
}
