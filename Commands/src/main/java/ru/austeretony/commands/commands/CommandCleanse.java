package ru.austeretony.commands.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandCleanse extends CommandBase {

	/*
	 * При вызове с определённым аргументом очищает конкретную часть инвентаря. Команда доступна всем, однако очистка эффектов только для опов.
	 * 
	 * Использование:
	 * 
	 * /cleanse <arg>
	 * 
	 * /cleanse i - очистка всего инвентаря,
	 * /cleanse a - очистка брони,
	 * /cleanse h - очистка пояса,
	 * /cleanse p - удаление активных эффектов (только для опов).
	 */
	
	public static final String 
	NAME = "cleanse",
	USAGE = "/cleanse <i - inventory, a - armor, h - hotbar, p - potions>";

	@Override
	public String getCommandName() {
		
		return this.NAME;
	}

	@Override
	public String getCommandUsage(ICommandSender commandSender) {
		
		return this.USAGE;
	}
	
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender commandSender) {
    	
		return true;    	
    }

	@Override
	public void processCommand(ICommandSender commandSender, String[] args) {

		if (commandSender instanceof EntityPlayer) {
			
			if (args.length == 0 || args.length > 1) {
										
				throw new WrongUsageException(this.getCommandUsage(commandSender));//Если аргументы не указаны или указано больше одного - выбросить исключение с указанием шаблона использования.
			}
			
			EntityPlayer player = this.getCommandSenderAsPlayer(commandSender);
			
			ChatComponentTranslation chatMessage;
			
			switch (args[0]) {
			
				case "i"://Очистка инвентаря.
					
					player.inventory.clearInventory(null, - 1);
					
					chatMessage = new ChatComponentTranslation("commands.cleanse.inventory");//"Инвентарь очищен".
					
					break;
					
				case "a"://Удаление брони.
					
					for (int i = 0; i < 4; i++) {
						
						player.inventory.armorInventory[i] = null;
					}
					
					chatMessage = new ChatComponentTranslation("commands.cleanse.armor");//...
					
					break;
					
				case "h"://Очистка пояса.
					
					for (int i = 0; i < 9; i++) {
						
						player.inventory.setInventorySlotContents(i, null);
					}
					
					chatMessage = new ChatComponentTranslation("commands.cleanse.hotbar");
					
					break;
					
				case "p"://Снятие эффектов.
					
					if (MinecraftServer.getServer().getConfigurationManager().func_152596_g(player.getGameProfile())) {//Только опам или если в мире активны читы.
						
						player.clearActivePotions();	
					
						chatMessage = new ChatComponentTranslation("commands.cleanse.potions");
					}
					
					else {
						
						//Для остальных формировка стандартного сообщения о нехватке прав
						chatMessage = new ChatComponentTranslation("commands.generic.permission");
						
						chatMessage.getChatStyle().setColor(EnumChatFormatting.RED);
					}
					
					break;
					
				default:
					
					throw new WrongUsageException(this.getCommandUsage(commandSender));//Если передан неверный аргумент - выбросить исключение с указанием шаблона использования.
			}
			
			player.addChatMessage(chatMessage);//Вывод сообщения в чат.
		}
	}
}
