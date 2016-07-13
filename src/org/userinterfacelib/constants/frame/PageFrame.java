package org.userinterfacelib.constants.frame;

public class PageFrame {
	private PageNodeFrame head = null;
	private PageNodeFrame tail = null;
	private int size = 0;
	
	public PageNodeFrame add(String title){
		PageNodeFrame frame;
		if(head == null){
			frame = new PageNodeFrame(title, 1);
			head = frame;
			tail = frame;
		}else{
			frame = new PageNodeFrame(title, tail.getIndex() + 1);
			frame.setPrevious(tail);
			tail.setNext(frame);
		}
		size++;
		return frame;
	}
	
	public boolean remove(PageNodeFrame frame){
		return remove(frame, head);
	}
	
	private boolean remove(PageNodeFrame target, PageNodeFrame cur){
		if(cur == null){
			return false;
		}else if(target.equals(cur)){
			if(cur.getPrevious() != null)
				cur.getPrevious().setNext(cur.getNext());
			if(cur.getNext() != null)
				cur.getNext().setPrevious(cur.getPrevious());
			cur.setNext(null);
			cur.setPrevious(null);
			System.gc();
			size--;
			return true;
		}else{
			return remove(target, cur.getNext());
		}
	}

	public PageNodeFrame getHead() {
		return head;
	}

	public PageNodeFrame getTail() {
		return tail;
	}
}
