package zbv5.cn.XiaoLottery.command;

import java.util.regex.Pattern;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import zbv5.cn.XiaoLottery.lang.Lang;
import zbv5.cn.XiaoLottery.util.LotteryUtil;
import zbv5.cn.XiaoLottery.util.PluginUtil;
import zbv5.cn.XiaoLottery.util.PrintUtil;

public class CpCommand implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args)
	  {
		if ((args.length == 0) || (args[0].equalsIgnoreCase("help")) || (args[0].equalsIgnoreCase("?")))
        {
			PrintUtil.PrintCommandSender(sender,"&6==== [&bXiaoLottery&6] ====");
            PrintUtil.PrintCommandSender(sender,"&6/"+lable+" &7- &b打开彩票购买页面");
            PrintUtil.PrintCommandSender(sender,"&6/"+lable+"&a info &7- &b查询彩票信息");
            PrintUtil.PrintCommandSender(sender,"&6/"+lable+"&a buy &e<数量>&7- &b为该玩家手上的物品附魔");
            PrintUtil.PrintCommandSender(sender,"&6/"+lable+"&a draw&7- &b立刻开奖");
            if(sender.hasPermission("Lottery.admin"))
            {
                PrintUtil.PrintCommandSender(sender,"&6/"+lable+"&a draw &e<玩家ID>&7- &b黑幕开奖");
            }
            PrintUtil.PrintCommandSender(sender,"&6/"+lable+"&c reload &7- &4重载插件配置");
            return true;
        }
		
		if(args[0].equalsIgnoreCase("buy"))
        {
            if(!sender.hasPermission("Lottery.buy"))
            {
                PrintUtil.PrintCommandSender(sender, Lang.NoPermission);
                return false;
            }
            if(args.length == 2)
            {
                if(sender instanceof Player)
                {
                    Pattern pattern = Pattern.compile("[0-9]*");
                    if(!(pattern.matcher(args[1]).matches()))
                    {
                        PrintUtil.PrintCommandSender(sender,Lang.NoInteger);
                        return false;
                    }
                    Player p = (Player)sender;
                    LotteryUtil.BuyTicket(p,Integer.parseInt(args[1]));
                    return true;
                } else {
                    PrintUtil.PrintCommandSender(sender,Lang.NeedPlayer);
                }
            }
            PrintUtil.PrintCommandSender(sender,"{prefix}&c正确方式: /"+lable+" buy <数量>");
            return false;
        }
		if(args[0].equalsIgnoreCase("info"))
        {
			if(!sender.hasPermission("Lottery.info"))
            {
                PrintUtil.PrintCommandSender(sender, Lang.NoPermission);
                return false;
            }
			PrintUtil.PrintCommandSender(sender,"&6==== [&bXiaoLottery&6] ====");
			PrintUtil.PrintCommandSender(sender,"&e距离开奖还有: &a"+LotteryUtil.runDrawTime+"秒");
			PrintUtil.PrintCommandSender(sender,"&e目前奖金池: &a"+LotteryUtil.Money);
			PrintUtil.PrintCommandSender(sender,"&e目前参与人数: &a"+LotteryUtil.BuyList.size());
			return true;
        }
		if(!sender.hasPermission("Lottery.admin"))
        {
            PrintUtil.PrintCommandSender(sender, Lang.NoPermission);
            return false;
        }
        if(args[0].equalsIgnoreCase("draw"))
        {
            if(args.length == 2)
            {
                LotteryUtil.Draw(args[1]);
            } else {
                LotteryUtil.Draw(null);
            }
            PrintUtil.PrintCommandSender(sender, Lang.Executed);
            return true;
        }
        if(args[0].equalsIgnoreCase("reload"))
        {
            try
            {
                PluginUtil.reloadLoadPlugin();
                PrintUtil.PrintCommandSender(sender, Lang.SuccessReload);
                return true;
            } catch (Exception e)
            {
                PrintUtil.PrintCommandSender(sender,Lang.FailReload);
                e.printStackTrace();
            }
            return false;
        }
		
		PrintUtil.PrintCommandSender(sender, Lang.NullCommand);
		return false;
	  }

}
