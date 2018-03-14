package ru.austeretony.commands.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

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
	 * /cleanse p - удаление активных эффектов (только для опов)
	 */
	
	public static final String 
	NAME = "cleanse",
	USAGE = "/cleanse <i - inventory, a - armor, h - hotbar, p - potions>";
	
	@Override
	public String getName() {
		
		return this.NAME;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		
		return this.USAGE;
	}
	
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
    	
        return true;//Доступна всем
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
			
		if (sender instanceof EntityPlayer) {
											
			if (args.length == 0 || args.length > 1) {
										
				throw new WrongUsageException(this.getUsage(sender));//Если аргументы не указаны или указано больше одного - выбросить исключение с указанием шаблона использования
			}
			
			EntityPlayer player = this.getCommandSenderAsPlayer(sender);
			
			TextComponentTranslation chatMessage;
			
			switch (args[0]) {
			
				case "i"://Очистка инвентаря 
					
					player.inventory.clear();
					
					chatMessage = new TextComponentTranslation("commands.cleanse.inventory");//"Инвентарь очищен"
					
					break;
					
				case "a"://Удаление брони
					
					player.inventory.armorInventory.clear();
					
					chatMessage = new TextComponentTranslation("commands.cleanse.armor");//...
					
					break;
					
				case "h"://Очистка пояса
					
					for (int i = 0; i < 9; i++) {
						
						player.inventory.removeStackFromSlot(i);
					}
					
					chatMessage = new TextComponentTranslation("commands.cleanse.hotbar");
					
					break;
					
				case "p"://Снятие эффектов
					
					if (server.getPlayerList().canSendCommands(player.getGameProfile())) {//Только опам
						
						player.clearActivePotions();	
					
						chatMessage = new TextComponentTranslation("commands.cleanse.potions");
					}
					
					else {
						
						//Для остальных формировка стандартного сообщения о нехватке прав
						chatMessage = new TextComponentTranslation("commands.generic.permission");
						
						chatMessage.getStyle().setColor(TextFormatting.RED);
					}
					
					break;
					
				default:
					
					throw new WrongUsageException(this.getUsage(sender));//Если передан неверный аргумент - выбросить исключение с указанием шаблона использования
			}
			
			player.sendMessage(chatMessage);//Вывод сообщения в чат
		}
	}
}
