package ru.austeretony.commands.commands;

import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class CommandYell extends CommandBase {

	/*
	 * Выводит сообщение игрока, выкрашенное в красный цвет для привлечения внимания. Команда доступна всем.
	 * 
	 * Использование:
	 * 
	 * /yell <сообщение>
	 */
	
	public static final String 
	NAME = "yell",
	USAGE = "/yell <message>";
	
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
			
			if (args.length == 0) {
										
				throw new WrongUsageException(this.getCommandUsage(commandSender));//Если само сообщение не написано - выбросить исключение.
			}		
			
			EntityPlayer player = this.getCommandSenderAsPlayer(commandSender);		
									
			IChatComponent message = this.func_147176_a(commandSender, args, 0, !(commandSender instanceof EntityPlayer));//Сборка сообщения.
			
			ChatComponentTranslation fullMessage = new ChatComponentTranslation(player.getDisplayName() + " " + I18n.format("commands.yell.yelling") + ": ");//Префикс сообщения "<Игрок> кричит: ".
			
			fullMessage.appendSibling(message);//Сцепление.
					
			fullMessage.getChatStyle().setColor(EnumChatFormatting.RED);//В красный цвет.
						
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(fullMessage);//Отправка сообщения всем игрокам на сервере.
		}
	}
}
