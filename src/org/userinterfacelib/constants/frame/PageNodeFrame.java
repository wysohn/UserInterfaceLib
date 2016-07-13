package org.userinterfacelib.constants.frame;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.userinterfacelib.constants.button.Button;
import org.userinterfacelib.constants.button.FirstButton;
import org.userinterfacelib.constants.button.LastButton;
import org.userinterfacelib.constants.button.NextButton;
import org.userinterfacelib.constants.button.PageButton;
import org.userinterfacelib.constants.button.PreviousButton;

/**
 * Linked list style
 * @author wysohn
 *
 */
public class PageNodeFrame extends Frame {
	private final int index;
	private PageNodeFrame previous = null;
	private PageNodeFrame next = null;
	
	public PageNodeFrame(String name, int index) {
		super(name+" Pg."+index, ChestSize.SIX);
		this.index = index;
		
		//fill line 5 with dummies
		for(int i=0;i<9;i++){
			Button button = new Button(this, Button.getIndex(4, i),
					new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData())) {
			};
			ItemMeta IM = button.getIS().getItemMeta();
			IM.setDisplayName(ChatColor.RED+"X");
			button.getIS().setItemMeta(IM);
			this.setButton(button);
		}
		
		//fill line 6
		//0,3,5,8 - glass
		Button[] btns = new Button[9];
		btns[0] = new Button(this, Button.getIndex(5, 0), new ItemStack(Material.STAINED_GLASS_PANE)){};
		btns[1] = new FirstButton(this, Button.getIndex(5, 1));
		btns[2] = new PreviousButton(this, Button.getIndex(5, 2));
		btns[3] = new Button(this, Button.getIndex(5, 3), new ItemStack(Material.STAINED_GLASS_PANE)){};
		btns[4] = new PageButton(this, Button.getIndex(5, 4));
		btns[5] = new Button(this, Button.getIndex(5, 5), new ItemStack(Material.STAINED_GLASS_PANE)){};
		btns[6] = new NextButton(this, Button.getIndex(5, 6));
		btns[7] = new LastButton(this, Button.getIndex(5, 7));
		btns[8] = new Button(this, Button.getIndex(5, 8), new ItemStack(Material.STAINED_GLASS_PANE)){};
		for(Button btn : btns){
			this.setButton(btn);
		}
	}

	public int getIndex() {
		return index;
	}

	public PageNodeFrame getPrevious() {
		return previous;
	}

	public PageNodeFrame getNext() {
		return next;
	}
	
	void setPrevious(PageNodeFrame previous) {
		this.previous = previous;
	}

	void setNext(PageNodeFrame next) {
		this.next = next;
	}

	public static PageNodeFrame getFirst(PageNodeFrame current){
		Validate.notNull(current);
		
		if(current.previous == null) 
			return current;
		else
			return getFirst(current.previous);
	}

	/**
	 * 
	 * @param current
	 * @return lastPage
	 */
	public static PageNodeFrame getLast(PageNodeFrame current){
		Validate.notNull(current);
		
		if(current.next == null)
			return current;
		else
			return getLast(current.next);
	}
	
}
