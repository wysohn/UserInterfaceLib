package org.userinterfacelib.constants.frame;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.userinterfacelib.constants.button.Button;
import org.userinterfacelib.constants.button.FirstButton;
import org.userinterfacelib.constants.button.LastButton;
import org.userinterfacelib.constants.button.NextButton;
import org.userinterfacelib.constants.button.PageButton;
import org.userinterfacelib.constants.button.PreviousButton;

import net.md_5.bungee.api.ChatColor;

/**
 * Linked list style
 * @author wysohn
 *
 */
public class PageNodeFrame extends Frame {
	public final PageFrame manager;
	
	private final int index;
	private PageNodeFrame previous = null;
	private PageNodeFrame next = null;
	
	public PageNodeFrame(PageFrame manager, String name, int index) {
		super(name+" Pg."+index, ChestSize.SIX);
		this.manager = manager;
		this.index = index;
		
		//fill line 6
		//0,3,5,8 - glass
		setButton(Button.getIndex(5, 0), new Button(this, new ItemStack(Material.STAINED_GLASS_PANE)){});
		setButton(Button.getIndex(5, 1), new FirstButton(this));
		setButton(Button.getIndex(5, 2), new PreviousButton(this));
		setButton(Button.getIndex(5, 3), new Button(this, new ItemStack(Material.STAINED_GLASS_PANE)){});
		setButton(Button.getIndex(5, 4), new PageButton(this).updateDisplayName(ChatColor.GREEN+"Pg. "+index));
		setButton(Button.getIndex(5, 5), new Button(this, new ItemStack(Material.STAINED_GLASS_PANE)){});
		setButton(Button.getIndex(5, 6), new NextButton(this));
		setButton(Button.getIndex(5, 7), new LastButton(this));
		setButton(Button.getIndex(5, 8), new Button(this, new ItemStack(Material.STAINED_GLASS_PANE)){});
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
	
}
