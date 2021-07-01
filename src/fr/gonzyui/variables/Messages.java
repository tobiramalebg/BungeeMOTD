package fr.gonzyui.variables;

import fr.gonzyui.utils.ConfigurationUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;

public class Messages {
    private final ConfigurationUtil configurationUtil;

    private String reload;

    private String usage;

    private String unknownCommand;

    private String noPerm;

    public Messages(ConfigurationUtil configurationUtil) {
        this.configurationUtil = configurationUtil;
        reload();
    }

    public void reload() {
        Configuration messages = this.configurationUtil.getConfiguration("%datafolder%/messages.yml");
        this.reload = ChatColor.translateAlternateColorCodes('&', messages.getString("messages.reload"));
        this.usage = ChatColor.translateAlternateColorCodes('&', messages.getString("messages.usage"));
        this.unknownCommand = ChatColor.translateAlternateColorCodes('&', messages.getString("messages.unknowncommand"));
        this.noPerm = ChatColor.translateAlternateColorCodes('&', messages.getString("messages.noperm"));
    }

    public String getReload() {
        return this.reload;
    }

    public String getUsage() {
        return this.usage;
    }

    public String getUnknownCommand() {
        return this.unknownCommand;
    }

    public String getNoPerm() {
        return this.noPerm;
    }
}
