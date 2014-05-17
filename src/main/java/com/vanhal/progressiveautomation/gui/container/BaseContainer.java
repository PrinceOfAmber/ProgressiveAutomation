package com.vanhal.progressiveautomation.gui.container;

import com.vanhal.progressiveautomation.entities.BaseTileEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

public class BaseContainer extends Container {
	protected BaseTileEntity entity;
	
	protected int lastProgress = -1;
	
	public BaseContainer(BaseTileEntity inEntity) {
		entity = inEntity;
	}
	
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	/* Add the players Inventory */
	public void addPlayerInventory(InventoryPlayer inv) {
		addPlayerInventory(inv, 8, 84);
	}
	
	public void addPlayerInventory(InventoryPlayer inv, int x, int y) {
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
					this.addSlotToContainer(new Slot(inv, j + (i+1)*9, x + j*18, y + i*18));
				}
		}
		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(inv, i, 8 + i*18, 142));
		}
	}
	
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (Object o : this.crafters){
			ICrafting i = (ICrafting) o;
			
			if (entity.getProgress() != lastProgress) {
				lastProgress = entity.getProgress();
				i.sendProgressBarUpdate(this, 1, lastProgress);
			}
			
			sendUpdates(i);
		}
	}
	
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int i, int value) {
		super.updateProgressBar(i, value);
		if (i==1) {
			entity.setProgress(value);
		}
	}

	public void sendUpdates(ICrafting i) {
		
	}
}
