package hejohnson;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.MultiDeck;
import ks.common.model.Pile;
import ks.common.model.Stack;

/*
 * Move card from the top of the deck to face up on the waste pile
 * @author hejohnson
 */

public class MoveToTableauMove extends Move {
	Column draggedCards;
	Column destination;
	Column source;
	int numCardsMoved;
	
	MoveToTableauMove (Column source, Column draggedCards, Column destination) {
		this.draggedCards = draggedCards;
		this.source = source;
		this.destination = destination;
		this.numCardsMoved = draggedCards.count();
	}

	@Override
	public boolean doMove(Solitaire game) {
		if (valid(game)) {
			destination.push(draggedCards);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean undo(Solitaire game) {
		Stack flipper = new Stack();
		for (int i = 0; i<numCardsMoved; i++) {
			flipper.add(destination.get());
		}
		for (int i = 0; i<numCardsMoved; i++) {
			source.add(flipper.get());
		}
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		if(destination.empty()){
			return true;
		}
		return destination.suit()==draggedCards.suit() && (destination.rank()==(draggedCards.rank()+draggedCards.count()) || (destination.rank()==Card.ACE && (draggedCards.rank()+draggedCards.count())-1 == Card.KING));
	}
}
