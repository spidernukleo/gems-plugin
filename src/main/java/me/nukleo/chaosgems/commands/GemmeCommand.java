package me.nukleo.chaosgems.commands;

import me.nukleo.chaosgems.ChaosGems;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import me.nukleo.chaosgems.utils.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;


public class GemmeCommand implements CommandExecutor {
    private File messages, playerData;
    private ChaosGems plugin;
    public GemmeCommand(ChaosGems plugin, File playerData, File messages){
        this.plugin=plugin;
        this.playerData=playerData;
        this.messages=messages;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("gems")){
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerData);
            FileConfiguration messagesConfig=YamlConfiguration.loadConfiguration(messages);
            if(sender instanceof Player){
                Player player=(Player) sender;
                if(args.length==0) {
                    String uuid=player.getUniqueId().toString();
                    int currGems= playerConfig.getInt("players."+uuid+".gems",0);
                    String message=messagesConfig.getString("messaggi.gemme.currGemme");
                    message=message.replace("%gemme%", Integer.toString(currGems));
                    player.sendMessage(metodiUtili.colora(message));
                }
                else {
                    if(player.hasPermission("chaosgems.gemmeothers")){
                        Player target= Bukkit.getPlayer(args[0]);
                        if(target!=null) {
                            String uuid=target.getUniqueId().toString();
                            int currGems = playerConfig.getInt("players."+uuid+".gems",0);
                            String message = messagesConfig.getString("messaggi.gemme.targetcurrGemme");
                            message = message.replace("%gemme%", Integer.toString(currGems));
                            message = message.replace("%player%", target.getName());
                            player.sendMessage(metodiUtili.colora(message));
                        }
                        else{
                            String message=messagesConfig.getString("errori.player-not-found");
                            player.sendMessage(metodiUtili.colora(message));
                        }
                    }
                    else{
                        String message=messagesConfig.getString("errori.no-permission");
                        player.sendMessage(metodiUtili.colora(message));
                    }
                }
            }
            else{
                if(args.length==0){
                    String message=messagesConfig.getString("errori.gemme.missing-args");
                    System.out.println(metodiUtili.colora(message));
                }
                else{
                    Player target= Bukkit.getPlayer(args[0]);
                    if(target!=null) {
                        String uuid=target.getUniqueId().toString();
                        int currGems = playerConfig.getInt("players."+uuid+".gems",0);
                        String message = messagesConfig.getString("messaggi.gemme.targetcurrGemme");
                        message = message.replace("%gemme%", Integer.toString(currGems));
                        message = message.replace("%player%", target.getName());
                        System.out.println(metodiUtili.colora(message));
                    }
                    else{
                        String message=messagesConfig.getString("errori.player-not-found");
                        System.out.println(metodiUtili.colora(message));
                    }
                }
            }
        }
        return true;
    }
}
