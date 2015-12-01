/*
 * Copyright (c) 2000-2013 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.dacharyaitan;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jdraw.framework.DrawCommandHandler;
import jdraw.framework.DrawModel;
import jdraw.framework.DrawModelEvent;
import jdraw.framework.DrawModelEvent.Type;
import jdraw.framework.DrawModelListener;
import jdraw.framework.Figure;
import jdraw.std.EmptyDrawCommandHandler;

/**
 * Provide a standard behavior for the drawing model. This class initially does
 * not implement the methods in a proper way. It is part of the course
 * assignments to do so.
 * 
 * @author Dinesh Acharya and Isabelle Tan
 *
 */
public class MyDrawModel implements DrawModel {
	private List<Figure> figures = new ArrayList<Figure>();
	private List<DrawModelListener> listeners = new ArrayList<DrawModelListener>();

	@Override
	public void addFigure(Figure f) {
		figures.add(f);
		RectListener listener = new RectListener(this);
		f.addFigureListener(listener);
		DrawModelEvent event = new DrawModelEvent(this, f, Type.FIGURE_ADDED);
		fire(event);
	}

	@Override
	public Iterable<Figure> getFigures() {
		return figures;
	}

	@Override
	public void removeFigure(Figure f) {
		figures.remove(f);
		DrawModelEvent event = new DrawModelEvent(this, f, Type.FIGURE_REMOVED);
		fire(event);
	}

	@Override
	public void addModelChangeListener(DrawModelListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeModelChangeListener(DrawModelListener listener) {
		listeners.remove(listener);
	}

	// Fire an event, i.e. notify all listeners
	public void fire(DrawModelEvent e) {
		for (DrawModelListener listener : listeners) {
			listener.modelChanged(e);
		}
	}

	/**
	 * The draw command handler. Initialized here with a dummy implementation.
	 */
	// TODO initialize with your implementation of the undo/redo-assignment.
	private DrawCommandHandler handler = new EmptyDrawCommandHandler();

	/**
	 * Retrieve the draw command handler in use.
	 * 
	 * @return the draw command handler.
	 */
	public DrawCommandHandler getDrawCommandHandler() {
		return handler;
	}

	@Override
	public void setFigureIndex(Figure f, int index) {
		if (figures.contains(f)) {
			figures.remove(f);
			figures.add(index, f);
			DrawModelEvent event = new DrawModelEvent(this, f, Type.DRAWING_CHANGED);
			fire(event);
		} else {
			System.out.println(
					"The figure" + f.toString() + " does not exist in the list of figures yet, and cannot be moved");
		}
	}

	@Override
	public void removeAllFigures() {
		figures.clear();
		DrawModelEvent event = new DrawModelEvent(this, null, Type.DRAWING_CLEARED);
		fire(event);
	}

}
