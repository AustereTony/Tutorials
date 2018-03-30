package ru.austeretony.tilesfirst.tiles;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
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
		this.ownerName = player.getName();//Ник для информативности.
		
		this.markDirty();
	}
	
	public UUID getOwnerUUID() {
		
		return UUID.fromString(this.owner_UUID);
	}
	
	public String getOwnerName() {
		
		return this.ownerName;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		
		tagCompound.setInteger("count", this.count);
		
		tagCompound.setString("ownerUUID", this.owner_UUID);		
		tagCompound.setString("ownerName", this.ownerName);
	
		return super.writeToNBT(tagCompound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		
		this.count = tagCompound.getInteger("count");
		
		this.owner_UUID = tagCompound.getString("ownerUUID");
		this.ownerName = tagCompound.getString("ownerName");
		
		super.readFromNBT(tagCompound);
	}
	
	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity packet) {
		
		super.onDataPacket(networkManager, packet);
		
		this.handleUpdateTag(packet.getNbtCompound());
	}
}
