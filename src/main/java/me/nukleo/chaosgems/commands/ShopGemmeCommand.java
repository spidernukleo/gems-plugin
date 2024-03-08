package me.nukleo.chaosgems.commands;

import me.nukleo.chaosgems.ChaosGems;
import me.nukleo.chaosgems.utils.metodiUtili;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ShopGemmeCommand implements CommandExecutor {
    private File messages, playerData;
    private ChaosGems plugin;
    public ShopGemmeCommand(ChaosGems plugin, File playerData, File messages){
        this.plugin=plugin;
        this.playerData=playerData;
        this.messages=messages;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(command.getName().equalsIgnoreCase("shopgemme")){
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerData);
            FileConfiguration messagesConfig=YamlConfiguration.loadConfiguration(messages);
            if(sender instanceof Player){
                Player player=(Player) sender;
                String message=messagesConfig.getString("errori.only-console");
                player.sendMessage(metodiUtili.colora(message));
            }
            else{
                if(args.length!=2){
                    String message = messagesConfig.getString("errori.shopgemme.missing-args");
                    System.out.println(metodiUtili.colora(message));
                }
                else{
                    Player target= Bukkit.getPlayer(args[0]);
                    String shop=args[1];
                    String uuid=target.getUniqueId().toString();
                    int currGems = playerConfig.getInt("players."+uuid+".gems",0);
                    int costo=this.plugin.getConfig().getInt("shop."+shop+".costo");
                    if(currGems>=costo){
                        String execute=this.plugin.getConfig().getString("shop."+shop+".command");
                        String nome=target.getName();
                        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "removegemme "+nome+" "+costo);
                        execute=execute.replace("%player%", nome);
                        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), execute);
                    }
                    else{
                        String message = messagesConfig.getString("errori.shopgemme.not-enough");
                        target.sendMessage(metodiUtili.colora(message));
                    }
                }
            }
        }
        return true;
    }
}
