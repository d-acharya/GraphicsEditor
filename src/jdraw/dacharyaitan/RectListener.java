/**
 * This class can be registered as a listener to a rectangle.
 */
package jdraw.dacharyaitan;

import jdraw.framework.DrawModel;
import jdraw.framework.DrawModelEvent;
import jdraw.framework.DrawModelEvent.Type;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureListener;

/**
 * @author Isabelle
 *
 */
public class RectListener implements FigureListener {
	private MyDrawModel model;
	
	public RectListener(MyDrawModel model){
		this.model = model;
	}
	
	@Override
	public void figureChanged(FigureEvent e) {
		DrawModelEvent event = new DrawModelEvent(model, e.getFigure(), Type.FIGURE_CHANGED);
		model.fire(event);
	}

}
