package deuces;

import ks.common.games.Solitaire;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.MultiDeck;
import ks.common.model.Pile;

/*
 * Move card from the top of the deck to face up on the waste pile
 * @author hejohnson
 */

public class MoveToTableauMove extends Move {
	Column draggedCards;
	Column destination;
	Column source;
	
	MoveToTableauMove (Column source, Column draggedCards, Column destination) {
		this.draggedCards = draggedCards;
		this.source = source;
		this.destination = destination;
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
		for (int i = 0; i<draggedCards.count(); i++) {
			source.add(destination.get());
		}
		
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		if(destination.empty()){
			return true;
		}
		return destination.suit()==draggedCards.suit() && destination.rank()==(draggedCards.rank()+draggedCards.count());
	}
}
