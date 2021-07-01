package fr.gonzyui;

import fr.gonzyui.commands.CleanMOTD;
import fr.gonzyui.listeners.ProxyPingListener;
import fr.gonzyui.utils.ConfigurationUtil;
import fr.gonzyui.variables.Messages;
import fr.gonzyui.variables.Variables;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Main extends Plugin {
    public void onEnable() {
        ConfigurationUtil configurationUtil = new ConfigurationUtil(this);
        configurationUtil.createConfiguration("%datafolder%/config.yml");
        configurationUtil.createConfiguration("%datafolder%/messages.yml");
        ProxyServer proxy = getProxy();
        Variables variables = new Variables(configurationUtil);
        Messages messages = new Messages(configurationUtil);
        PluginManager pm = proxy.getPluginManager();
        pm.registerListener(this, (Listener)new ProxyPingListener(variables));
        pm.registerCommand(this, (Command)new CleanMOTD("cleanmotd", variables, messages));
        Objects.requireNonNull(variables);
        proxy.getScheduler().schedule(this, variables::clearPinged, variables.getCacheTime(), variables.getCacheTime(), TimeUnit.MILLISECONDS);
    }
}
