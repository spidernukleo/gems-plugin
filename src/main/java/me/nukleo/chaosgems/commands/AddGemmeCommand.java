package me.nukleo.chaosgems.commands;

import me.nukleo.chaosgems.ChaosGems;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import me.nukleo.chaosgems.utils.*;

import java.io.File;
import java.io.IOException;

public class AddGemmeCommand implements CommandExecutor {
    private ChaosGems plugin;
    private File playerData, messages;
    public AddGemmeCommand(ChaosGems plugin, File playerData, File messages){
        this.plugin=plugin;
        this.playerData=playerData;
        this.messages=messages;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("addgemme")){
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerData);
            FileConfiguration messagesConfig=YamlConfiguration.loadConfiguration(messages);
            if(sender instanceof Player){
                Player player=(Player) sender;
                String message=messagesConfig.getString("errori.only-console");
                player.sendMessage(metodiUtili.colora(message));
            }
            else{
                if(args.length!=2){
                    String message = messagesConfig.getString("errori.addgemme.missing-args");
                    System.out.println(metodiUtili.colora(message));
                }
                else{
                    Player target= Bukkit.getPlayer(args[0]);
                    String amount=args[1];
                    String uuid=target.getUniqueId().toString();
                    int currGems = playerConfig.getInt("players."+uuid+".gems",0);
                    playerConfig.set("players."+uuid+".gems", currGems+Integer.parseInt(amount));
                    try{
                        playerConfig.save(playerData);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    String message=messagesConfig.getString("messaggi.addgemme.ricevute");
                    message=message.replace("%gemme%", amount);
                    target.sendMessage(metodiUtili.colora(message));
                }
            }
        }
        return true;
    }
}
