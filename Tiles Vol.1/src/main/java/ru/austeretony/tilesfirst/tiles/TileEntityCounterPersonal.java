package ru.austeretony.tilesfirst.tiles;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCounterPersonal extends TileEntity {

	private int count;
	
	private String owner_UUID, ownerName;
	
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
	
	public void setOwner(EntityPlayer player) {
		
		this.owner_UUID = player.getPersistentID().toString();//Уникальный идентификатор для точного определения владельца.
		this.ownerName = player.getDisplayName();//Ник для информативности.
		
		this.markDirty();
	}
	
	public UUID getOwnerUUID() {
		
		return UUID.fromString(this.owner_UUID);
	}
	
	public String getOwnerName() {
		
		return this.ownerName;
	}
	
	@Override
    public boolean canUpdate() {
    	
        return false;
    }

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		
		tagCompound.setInteger("count", this.count);
		
		tagCompound.setString("ownerUUID", this.owner_UUID);		
		tagCompound.setString("ownerName", this.ownerName);
	
		super.writeToNBT(tagCompound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		
		this.count = tagCompound.getInteger("count");
		
		this.owner_UUID = tagCompound.getString("ownerUUID");
		this.ownerName = tagCompound.getString("ownerName");
		
		super.readFromNBT(tagCompound);
	}
	
	@Override
	 public Packet getDescriptionPacket() {
		
		NBTTagCompound tagCompound = new NBTTagCompound();
		
		this.writeToNBT(tagCompound);
		
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 3, tagCompound);
	 }
	 	 
	 @Override
	 public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet) {
		 
		 NBTTagCompound tagCompound = packet.func_148857_g();
		 
		 this.readFromNBT(tagCompound);
	 }
}
