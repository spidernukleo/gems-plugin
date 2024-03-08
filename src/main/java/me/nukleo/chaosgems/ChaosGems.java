package me.nukleo.chaosgems;


import org.bukkit.plugin.java.JavaPlugin;
import me.nukleo.chaosgems.commands.*;
import me.nukleo.chaosgems.utils.gemmeExpasion;

import java.io.File;


public final class ChaosGems extends JavaPlugin {
    private File messages, playerData;
    @Override
    public void onEnable() {
        messages = new File(getDataFolder(), "messages.yml");
        if (!messages.exists()) {
            saveResource("messages.yml", false);
        }
        playerData = new File(getDataFolder(), "playerData.yml");
        if (!playerData.exists()) {
            saveResource("playerData.yml", false);
        }
        saveDefaultConfig();
        getCommand("gems").setExecutor(new GemmeCommand(this,playerData, messages));
        getCommand("addgemme").setExecutor(new AddGemmeCommand(this,playerData,messages));
        getCommand("gemmereload").setExecutor(new gemmereload(this,playerData,messages));
        getCommand("removegemme").setExecutor(new RemoveGemmeCommand(this,playerData,messages));
        getCommand("shopgemme").setExecutor(new ShopGemmeCommand(this,playerData,messages));
        new gemmeExpasion(this,playerData).register();
    }
}
