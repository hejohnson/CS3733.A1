package hejohnson;

import ks.common.games.Solitaire;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.MultiDeck;
import ks.common.model.Pile;

/*
 * Move card from the top of the deck to face up on the waste pile
 * @author hejohnson
 */

public class DealCardMove extends Move {
	MultiDeck deck;
	Column waste;
	
	DealCardMove (MultiDeck deck, Column waste) {
		this.deck = deck;
		this.waste = waste;
	}

	@Override
	public boolean doMove(Solitaire game) {
		if (valid(game)) {
			waste.add(deck.get());
		}
		game.updateNumberCardsLeft(-1);
		return valid(game);
	}

	@Override
	public boolean undo(Solitaire game) {
		deck.add(waste.get());
		game.updateNumberCardsLeft(1);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return !deck.empty();
	}
}
