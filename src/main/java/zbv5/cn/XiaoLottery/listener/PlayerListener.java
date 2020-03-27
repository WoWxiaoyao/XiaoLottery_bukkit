package zbv5.cn.XiaoLottery.listener;

import zbv5.cn.XiaoLottery.Main;
import zbv5.cn.XiaoLottery.util.FileUtil;
import zbv5.cn.XiaoLottery.util.PrintUtil;
import zbv5.cn.XiaoLottery.util.VaultUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener
{
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        final Player p = e.getPlayer();
        if(FileUtil.task.isEmpty())  return;
        for(final String task:FileUtil.task)
        {
            if(task.contains(p.getName()))
            {

                Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable()
                {
                    public void run()
                    {
                        if(p.isOnline())
                        {
                            String[] format = task.split(",");
                            if(format.length == 2)
                            {
                                double money = Double.parseDouble(format[1]);
                                VaultUtil.giveMoney(p,money);
                                FileUtil.task.remove(task);
                                FileUtil.save();
                                PrintUtil.PrintConsole("&e> &a为玩家"+p.getName()+"补偿"+money+" &7(彩票系统)");
                            } else {
                                PrintUtil.PrintConsole("&e> &c为玩家"+p.getName()+"补偿失败,玩家已离线 &7(彩票系统)");
                            }
                        }
                    }
                }, 600);


            }
        }
    }
}
