package hejohnson;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.MultiDeck;
import ks.common.model.Pile;

/*
 * Move card from the top of the deck to face up on the waste pile
 * @author hejohnson
 */

public class MoveToFoundationMove extends Move {
	Column draggedCards;
	Column destination;
	Column source;
	
	MoveToFoundationMove (Column source, Column draggedCards, Column destination) {
		this.draggedCards = draggedCards;
		this.source = source;
		this.destination = destination;
	}

	@Override
	public boolean doMove(Solitaire game) {
		if (valid(game)) {
			for (int i = 0; i<draggedCards.count(); i++) {
				destination.add(draggedCards.get());
			} 
			//destination.push(draggedCards);
			game.updateScore(draggedCards.count());
			return true;
		}
		
		return false;
	}

	@Override
	public boolean undo(Solitaire game) {
		for (int i = 0; i<draggedCards.count(); i++) {
			source.add(destination.get());
			game.updateScore(-1);
		}
		
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return (destination.suit()==draggedCards.suit() && ((destination.rank()+1==draggedCards.rank()) || (draggedCards.rank()==Card.ACE && destination.rank()==Card.KING)));
	}
}
