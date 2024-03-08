package me.nukleo.chaosgems.commands;

import me.nukleo.chaosgems.ChaosGems;
import me.nukleo.chaosgems.utils.metodiUtili;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class gemmereload implements CommandExecutor {
    private ChaosGems plugin;
    private File playerData, messages;

    public gemmereload(ChaosGems plugin, File playerData, File messages){
        this.plugin=plugin;
        this.playerData=playerData;
        this.messages=messages;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("gemmereload")){
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerData);
            FileConfiguration messagesConfig=YamlConfiguration.loadConfiguration(messages);
            if(sender instanceof Player){
                Player player=(Player) sender;
                this.plugin.reloadConfig();
                String message=messagesConfig.getString("messaggi.reload");
                player.sendMessage(metodiUtili.colora(message));
            }
            else{
                this.plugin.reloadConfig();
                String message=messagesConfig.getString("messaggi.reload");
                System.out.println(metodiUtili.colora(message));
            }
        }
        return true;
    }
}
