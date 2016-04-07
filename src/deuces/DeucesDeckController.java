package deuces;

import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.games.Solitaire;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.MultiDeck;

public class DeucesDeckController extends SolitaireReleasedAdapter {
	Solitaire game;
	MultiDeck deck;
	Column waste;
	
	public DeucesDeckController(Solitaire game, MultiDeck deck, Column waste) {
		super(game);
		this.game = game;
		this.deck = deck;
		this.waste = waste;
	}
	
	public void mousePressed(java.awt.event.MouseEvent me) {
		Move m = new DealCardMove(deck, waste);
		if (m.doMove(game)) {
			game.pushMove(m);
			game.refreshWidgets();
		}
	}
	
}
