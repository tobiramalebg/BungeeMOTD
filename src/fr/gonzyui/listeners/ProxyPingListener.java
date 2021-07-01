package fr.gonzyui.listeners;

import fr.gonzyui.variables.Variables;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class ProxyPingListener implements Listener {
    private final Variables variables;

    public ProxyPingListener(Variables variables) {
        this.variables = variables;
    }

    @EventHandler(priority = 64)
    public void onProxyPing(ProxyPingEvent e) {
        ServerPing response = e.getResponse();
        if (response == null || (e instanceof Cancellable && ((Cancellable)e).isCancelled()))
            return;
        ServerPing.Players players = response.getPlayers();
        int onlinePlayers = players.getOnline();
        int maxPlayers = players.getMax();
        if (this.variables.isFakePlayersEnabled()) {
            onlinePlayers += this.variables.getFakePlayersAmount(onlinePlayers);
            players.setOnline(onlinePlayers);
        }
        if (this.variables.isMaxPlayersEnabled()) {
            maxPlayers = this.variables.isMaxPlayersJustOneMore() ? (onlinePlayers + 1) : this.variables.getMaxPlayers();
            players.setMax(maxPlayers);
        }
        if (this.variables.isMotdEnabled())
            response.setDescriptionComponent((BaseComponent)new TextComponent(this.variables.getMOTD(maxPlayers, onlinePlayers)));
        if (this.variables.isProtocolEnabled()) {
            UUID fakeUUID = new UUID(0L, 0L);
            String[] sampleString = this.variables.getSample(maxPlayers, onlinePlayers);
            ServerPing.PlayerInfo[] sample = new ServerPing.PlayerInfo[sampleString.length];
            for (int i = 0; i < sampleString.length; i ++) {
                sample[i] = new ServerPing.PlayerInfo(sampleString[i], fakeUUID);
            players.setSample(sample);
            }
            if (this.variables.isCacheEnabled()) {
                String address = e.getConnection().getAddress().getHostString();
                if (this.variables.hasPinged(address)) {
                    response.setFavicon("");
                } else {
                    this.variables.addPinged(address);
                }
            }
        }
    }
}
