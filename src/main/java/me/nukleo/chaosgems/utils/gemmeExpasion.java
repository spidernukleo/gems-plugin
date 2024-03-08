package me.nukleo.chaosgems.utils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.nukleo.chaosgems.ChaosGems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;


public class gemmeExpasion extends PlaceholderExpansion{

    private ChaosGems plugin;
    private File playerData;

    public gemmeExpasion(ChaosGems plugin, File playerData){
        this.plugin=plugin;
        this.playerData=playerData;
    }
    @Override
    public @NotNull String getIdentifier() {
        return "ChaosGems";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Nukleo";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if(player==null){
            return "";
        }
        if(params.equals("playergemme")){
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerData);
            String uuid=player.getUniqueId().toString();
            int currGems= playerConfig.getInt("players."+uuid+".gems",0);
            return Integer.toString(currGems);
        }
        return params;
    }
}
