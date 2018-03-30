package ru.austeretony.tilesfirst.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCounter extends TileEntity {

	private int count;
	
	public int getCount() {
		
		return this.count;
	}
	
	public void incrementCount() {
		
		this.count++;
		
		this.markDirty();
	}
	
	public void decrementCount() {
		
		this.count--;
		
		this.markDirty();
	}
	
	@Override
    public boolean canUpdate() {
    	
        return false;
    }

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		
		tagCompound.setInteger("count", this.count);
		
		super.writeToNBT(tagCompound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		
		this.count = tagCompound.getInteger("count");
		
		super.readFromNBT(tagCompound);
	}
}
