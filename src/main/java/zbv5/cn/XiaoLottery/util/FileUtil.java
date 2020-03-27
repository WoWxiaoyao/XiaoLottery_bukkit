package zbv5.cn.XiaoLottery.util;

import zbv5.cn.XiaoLottery.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileUtil
{
    public static YamlConfiguration lang;
    public static YamlConfiguration config;
    public static List<String> Backup = new ArrayList<String>();
    public static List<String> task = new ArrayList<String>();

    public static void LoadFile()
    {
        try
        {
            File Config_Yml = new File(Main.getInstance().getDataFolder(), "config.yml");
            if (!Config_Yml.exists())
            {
                Main.getInstance().saveResource("config.yml", false);
            }
            config = YamlConfiguration.loadConfiguration(Config_Yml);

            LotteryUtil.DrawTime = config.getInt("DrawTime");

            LotteryUtil.MaxTickets = config.getInt("MaxTickets");

            if(LotteryUtil.MaxTickets < 0)
            {
                LotteryUtil.MaxTickets = 2147483647;
            }
            
            if(config.getString("Mode").equalsIgnoreCase("Random"))
            {
            	LotteryUtil.Mode = "Random";
            	PrintUtil.PrintConsole("&e> &a当前开奖模式设置为:&6众生平等");
            } else {
            	LotteryUtil.Mode = "More";
            	PrintUtil.PrintConsole("&e> &a当前开奖模式设置为:&6按票分配");
            }
            
            LotteryUtil.RemindTimes = config.getIntegerList("RemindTimes");
            LotteryUtil.OnlineBroadcast = config.getBoolean("OnlineBroadcast");
            LotteryUtil.BuyBroadcast = config.getBoolean("BuyBroadcast");
            LotteryUtil.TicketCost = config.getDouble("TicketCost");
            if(LotteryUtil.TicketCost < 0)
            {
                LotteryUtil.MaxTickets = 2147483647;
            }
            Backup = config.getStringList("Backup");
            task = config.getStringList("task");

            File Lang_Yml = new File(Main.getInstance().getDataFolder(), "lang.yml");
            if (!Lang_Yml.exists())
            {
                Main.getInstance().saveResource("lang.yml", false);
            }
            lang = YamlConfiguration.loadConfiguration(Lang_Yml);
            PrintUtil.PrintConsole("&a&l√ &a配置文件加载完成.");
        }
        catch (Exception e)
        {
            PrintUtil.PrintConsole("&c&l× &4加载配置文件出现问题,请检查服务器.");
            e.printStackTrace();
        }
    }
    
    public static void save()
    {
    	try
        {
    		File Config_Yml = new File(Main.getInstance().getDataFolder(), "config.yml");
    		if (!Config_Yml.exists())
            {
                Main.getInstance().saveResource("config.yml", false);
            }
    		FileConfiguration config = YamlConfiguration.loadConfiguration(Config_Yml);
            config.set("Backup", Backup);
            config.set("task", task);
            config.save(Config_Yml);

            config = YamlConfiguration.loadConfiguration(Config_Yml);
        }
    	catch (Exception e)
        {
            PrintUtil.PrintConsole("&c&l× &4config保存出现问题");
            e.printStackTrace();
        }
    }
}
