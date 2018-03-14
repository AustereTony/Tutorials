package ru.austeretony.commands.commands;

import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

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
	public String getName() {
		
		return this.NAME;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		
		return this.USAGE;
	}
	
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
    	
        return true;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
			
		if (sender instanceof EntityPlayer) {
											
			if (args.length == 0) {
										
				throw new WrongUsageException(this.getUsage(sender));//Если само сообщение не написано - выбросить исключение
			}		
			
			EntityPlayer player = this.getCommandSenderAsPlayer(sender);		
									
			ITextComponent message = this.getChatComponentFromNthArg(sender, args, 0, !(sender instanceof EntityPlayer));//Сборка сообщения
			
			TextComponentString fullMessage = new TextComponentString(player.getDisplayNameString() + " " + I18n.format("commands.yell.yelling") + ": ");//Префикс сообщения "<Игрок> кричит: "
			
			fullMessage.appendSibling(message);//Сцепление
					
			fullMessage.getStyle().setColor(TextFormatting.RED);//В красный цвет
						
			server.getPlayerList().sendMessage(fullMessage);//Отправка сообщения всем игрокам на сервере
		}
	}
}
