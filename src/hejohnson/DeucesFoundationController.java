package hejohnson;


import java.awt.event.MouseEvent;

import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.BuildablePileView;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.FanPileView;
import ks.common.view.PileView;
import ks.common.view.Widget;

public class DeucesFoundationController extends java.awt.event.MouseAdapter {
	/** The Klondike Game. */
	Deuces game;

	/** The specific Foundation pileView being controlled. */
	FanPileView dest;
	/**
	 * FoundationController constructor comment.
	 */
	public DeucesFoundationController(Deuces game, FanPileView dest) {
		super();

		this.game = game;
		this.dest = dest;
	}
	/**
	 * Coordinate reaction to the completion of a Drag Event.
	 * <p>
	 * A bit of a challenge to construct the appropriate move, because cards
	 * can be dragged both from the WastePile (as a CardView object) and the 
	 * BuildablePileView (as a ColumnView).
	 * @param me java.awt.event.MouseEvent
	 */
	public void mouseReleased(MouseEvent me) {
		Container c = game.getContainer();

		/** Return if there is no card being dragged chosen. */
		Widget draggingWidget = c.getActiveDraggingObject();
		if (draggingWidget == Container.getNothingBeingDragged()) {
			System.err.println ("FoundationController::mouseReleased() unexpectedly found nothing being dragged.");
			c.releaseDraggingObject();		
			return;
		}

		/** Recover the from BuildablePile OR waste Pile */
		Widget src = c.getDragSource();
		if (src == null) {
			System.err.println ("FoundationController::mouseReleased(): somehow no dragSource in container.");
			c.releaseDraggingObject();
			return;
		}

		Column destination = (Column) dest.getModelElement();
		Column source = (Column) src.getModelElement();

		//ColumnView columnView = (ColumnView) draggingWidget;
		Column col = (Column) draggingWidget.getModelElement();
		if (col == null) {
			System.err.println ("FoundationController::mouseReleased(): somehow ColumnView model element is null.");
			c.releaseDraggingObject();			
			return;
		}

		
		Move m = new MoveToFoundationMove (source, col, destination);

		if (m.doMove (game)) {
			// Success
			game.pushMove (m);
		} else {
			src.returnWidget (draggingWidget);
		}

		// release the dragging object, (this will reset dragSource)
		c.releaseDraggingObject();
		
		// finally repaint
		c.repaint();
		
		game.refreshWidgets();
	}
}
