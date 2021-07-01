package fr.gonzyui.variables;

import fr.gonzyui.utils.ConfigurationUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;

import java.util.Collection;
import java.util.HashSet;

public class Variables {
    private final ConfigurationUtil configurationUtil;

    private Collection<String> pinged = new HashSet<>();

    private String[] motdMotds;

    private String[] sampleSamples;

    private int cacheTime;

    private int maxPlayers;

    private int fakePlayersAmount;

    private boolean motdEnabled;

    private boolean sampleEnabled;

    private boolean protocolEnabled;

    private boolean cacheEnabled;

    private boolean maxPlayersJustOneMore;

    private boolean maxPlayersEnabled;

    private boolean fakePlayersEnabled;

    private String protocol;

    private String fakePlayersMode;

    public Variables(ConfigurationUtil configurationUtil) {
        this.configurationUtil = configurationUtil;
        reloadConfig();
    }

    public void reloadConfig() {
        Configuration configuration = this.configurationUtil.getConfiguration("%datafolder%/config.yml");
        this.motdEnabled = configuration.getBoolean("motd.enabled");
        this.motdMotds = (String[]) configuration.getStringList("motd.motds").toArray((Object[]) new String[0]);
        this.sampleEnabled = configuration.getBoolean("sample.enabled");
        this.sampleSamples = (String[]) configuration.getStringList("sample.samples").toArray((Object[]) new String[0]);
        this.protocolEnabled = configuration.getBoolean("protocol.enabled");
        this.protocol = configuration.getString("protocol.mode");
        this.cacheEnabled = configuration.getBoolean("cache.enabled");
        this.cacheTime = configuration.getInt("cache.time");
        this.maxPlayersEnabled = configuration.getBoolean("maxplayers.enabled");
        this.maxPlayers = configuration.getInt("maxplayers.maxplayers");
        this.maxPlayersJustOneMore = configuration.getBoolean("maxplayers.justonemore");
        this.fakePlayersEnabled = configuration.getBoolean("fakeplayers.enabled");
        this.fakePlayersAmount = configuration.getInt("fakeplayers.amount");
        this.fakePlayersMode = configuration.getString("fakeplayers.mode");
        for (int i = 0; i < this.motdMotds.length; i++) {
            String motd = this.motdMotds[i];
            if (motd.contains("%centered%"))
                this.motdMotds[i] = replaceCentered(motd.replace("%centered%", ""));
        }
    }

    private String replaceCentered(String string) {
        return string;
    }

    public boolean isMotdEnabled() {
        return this.motdEnabled;
    }

    public String getMOTD(int maxPlayers, int onlinePlayers) {
        return ChatColor.translateAlternateColorCodes('&',
                this.motdMotds[(int) Math.floor(Math.random() * this.motdMotds.length)]
                        .replace("%maxplayers%", String.valueOf(maxPlayers))
                        .replace("%onlineplayers%", String.valueOf(onlinePlayers)));
    }

    public boolean isSampleEnabled() {
        return this.sampleEnabled;
    }

    public String[] getSample(int maxPlayers, int onlinePlayers) {
        return ChatColor.translateAlternateColorCodes('&',
                this.sampleSamples[(int) Math.floor(Math.random() * this.sampleSamples.length)]
                        .replace("%maxplayers%", String.valueOf(maxPlayers))
                        .replace("%onlineplayers%", String.valueOf(onlinePlayers))).split("\n");
    }

    public boolean isProtocolEnabled() {
        return this.protocolEnabled;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public boolean isCacheEnabled() {
        return this.cacheEnabled;
    }

    public int getCacheTime() {
        return this.cacheTime;
    }

    public boolean isMaxPlayersEnabled() {
        return this.maxPlayersEnabled;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public boolean isMaxPlayersJustOneMore() {
        return this.maxPlayersJustOneMore;
    }

    public boolean isFakePlayersEnabled() {
        return this.fakePlayersEnabled;
    }

    public int getFakePlayersAmount(int players) {
        String str;
        switch ((str = this.fakePlayersMode).hashCode()) {
            case -1884956477:
                if (!str.equals("RANDOM"))
                    break;
                return (int)(Math.floor(Math.random() * this.fakePlayersAmount) + 1.0D);
            case -1839152530:
                if (!str.equals("STATIC"))
                    break;
                return this.fakePlayersAmount;
            case 1147347117:
                if (!str.equals("DIVISION"))
                    break;
                return players / this.fakePlayersAmount;
        }
        return 0;
    }
    public boolean hasPinged(String name) {
        return this.pinged.contains(name);
    }

    public void addPinged(String name) {
        this.pinged.add(name);
    }

    public void clearPinged() {
        this.pinged.clear();
    }
}
