package fr.gonzyui.commands;

import fr.gonzyui.variables.Messages;
import fr.gonzyui.variables.Variables;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CleanMOTD extends Command {
    private final Variables variables;

    private final Messages messages;

    public CleanMOTD(String string, Variables variables, Messages messages) {
        super(string);
        this.messages = messages;
        this.variables = variables;
    }

    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("motd.admin")) {
            if (args.length < 1 || args[0].equalsIgnoreCase("help")) {
                sender.sendMessage((BaseComponent)new TextComponent(this.messages.getUsage()));
            } else if (args[0].equalsIgnoreCase("reload")) {
                this.variables.reloadConfig();
                this.messages.reload();
                sender.sendMessage((BaseComponent)new TextComponent(this.messages.getReload()));
            } else {
                sender.sendMessage((BaseComponent)new TextComponent(this.messages.getUnknownCommand()));
            }
        } else {
            sender.sendMessage((BaseComponent)new TextComponent(this.messages.getNoPerm()));
        }
    }
}
