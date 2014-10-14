package com.csound.wizard.model;

import java.util.Iterator;

import org.apache.commons.collections4.queue.CircularFifoQueue;

public class RecentQueue<T> extends CircularFifoQueue<T> {
	private static final long serialVersionUID = 1L;

	public RecentQueue(int n) {
		super(n);		
	}
	
	@Override
	public boolean add(T item) {
		removeDublicates(item);	
		return super.add(item);		
	}
	
	private void removeDublicates(T item) {
		Iterator<T> it = iterator();		
		while (it.hasNext()) {			
			if (it.next().equals(item)) {
				it.remove();
			}
		}		
	}
}
