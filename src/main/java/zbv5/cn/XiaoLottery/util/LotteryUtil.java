package zbv5.cn.XiaoLottery.util;

import zbv5.cn.XiaoLottery.Main;
import zbv5.cn.XiaoLottery.lang.Lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

public class LotteryUtil
{
    public static int DrawTime = 600;
    public static int runDrawTime = 600;
    public static double TicketCost = 100;
    public static int MaxTickets = 600;
    public static boolean OnlineBroadcast = false;
    public static boolean BuyBroadcast = false;
    public static List<Integer> RemindTimes = new ArrayList<Integer>();

    public static HashMap<String, Integer> BuyList = new HashMap<String, Integer>();

    public static double Money = 0;

    public static String Mode = "Random";

    public static void BuyTicket(Player p, int Ticket)
    {
        if(Ticket <= 0)
        {
            PrintUtil.PrintCommandSender(p,Lang.FailBuy_NullInteger);
            return;
        }
        int New_Ticket = Ticket;
        if(BuyList.containsKey(p.getName()))
        {
            New_Ticket = New_Ticket  + BuyList.get(p.getName());
        }
        if(New_Ticket <= MaxTickets)
        {
            double NeedMoney = Ticket * TicketCost;
            if(VaultUtil.takeMoney(p, NeedMoney))
            {
                BuyList.put(p.getName(),New_Ticket);
                Money = Money + NeedMoney;
                PrintUtil.PrintCommandSender(p,Lang.SuccessBuy.replace("{money}",String.valueOf(NeedMoney)).replace("{amount}",String.valueOf(Ticket)));
                if(BuyBroadcast)
                {
                    PrintUtil.PrintBroadcast(Lang.BuyBroadcast.replace("{player}",p.getName()).replace("{amount}",String.valueOf(Ticket)));
                }
            } else {
                PrintUtil.PrintCommandSender(p,Lang.FailBuy_Money.replace("{money}",String.valueOf(NeedMoney)).replace("{amount}",String.valueOf(Ticket)).replace("{player_money}",String.valueOf(VaultUtil.getMoney(p))));
            }
        } else {
            PrintUtil.PrintCommandSender(p,Lang.FailBuy_Max.replace("{amount}",String.valueOf(MaxTickets)));
        }
    }

    public static void Draw(String hack)
    {
        if(BuyList.isEmpty())
        {
            if((OnlineBroadcast) && (!Main.getInstance().getServer().getOnlinePlayers().isEmpty()))
            {
                PrintUtil.PrintBroadcast(Lang.NullBuy);
            }
            if((OnlineBroadcast) && (!BuyList.isEmpty()) && (Main.getInstance().getServer().getOnlinePlayers().isEmpty()))
            {
                PrintUtil.PrintBroadcast(Lang.NullBuy);
            }
            if(!OnlineBroadcast)
            {
                PrintUtil.PrintBroadcast(Lang.NullBuy);
            }
        } else {
            int i = BuyList.size();
            String Winner = "";
            if(i == 1)
            {
                Winner = BuyList.keySet().iterator().next();
            }
            if(i > 1)
            {
                if(Mode.equalsIgnoreCase("Random"))
                {
                    int luck = (int)(Math.random()*i);
                    int set = 0;
                    for(String name:BuyList.keySet())
                    {
                        if(luck == set)
                        {
                            Winner = name;
                            break;
                        }
                        set ++;
                    }
                } else {
                    List<String> list = new ArrayList<String>();
                    int tickets = 0;
                    for(String name:BuyList.keySet())
                    {
                        int start = tickets + 1 ;
                        tickets = tickets + BuyList.get(name);
                        list.add(name+"/"+start+"/"+tickets);
                    }
                    int luck = (int)(Math.random()*tickets)+1;

                    for(String l:list)
                    {
                        String[] f = l.split("/");
                        String playername = f[0];
                        int a = Integer.parseInt(f[1]);
                        int b = Integer.parseInt(f[2]);
                        if( (a <= luck) && (b >= luck))
                        {
                            Winner = playername;
                            break;
                        }
                    }
                }
            }
            if(hack != null)
            {
                Winner = hack;
            }

            Player p = Main.getInstance().getServer().getPlayer(Winner);
            if(p != null)
            {
                PrintUtil.PrintBroadcast(Lang.DrawBroadcast.replace("{player}",p.getName()).replace("{money}",String.valueOf(Money)));
                VaultUtil.giveMoney(p, Money);
            } else {
                PrintUtil.PrintBroadcast(Lang.DrawBroadcast.replace("{player}",Winner).replace("{money}",String.valueOf(Money)));
                String format = Winner+","+Money;
                FileUtil.task.add(format);
            }
        }
        Money = 0;
        BuyList.clear();
        runDrawTime = DrawTime;
    }

    public static void run()
    {
        runDrawTime = DrawTime;

        Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable()
        {
            public void run()
            {
                if(runDrawTime > 0)
                {
                    runDrawTime = runDrawTime - 1;
                    for(int RemindTime:RemindTimes)
                    {
                        if(runDrawTime == RemindTime)
                        {
                            if((OnlineBroadcast) && (!Main.getInstance().getServer().getOnlinePlayers().isEmpty()))
                            {
                                PrintUtil.PrintBroadcast(Lang.RemindBroadcast.replace("{time}",Integer.toString(runDrawTime)).replace("{money}",String.valueOf(Money)));
                            }
                            if(!OnlineBroadcast)
                            {
                                PrintUtil.PrintBroadcast(Lang.RemindBroadcast.replace("{time}",Integer.toString(runDrawTime)).replace("{money}",String.valueOf(Money)));
                            }
                        }
                    }
                    if(runDrawTime == 0)
                    {
                        Draw(null);
                    }
                }
            }
        }, 0L, 20L);
    }

    public static int getBuyTicket(Player p)
    {
        if(BuyList.containsKey(p.getName()))
        {
            return BuyList.get(p.getName());
        }
        return 0;
    }

    public static void BackUp(boolean start)
    {
        if(start)
        {
            if(!FileUtil.Backup.isEmpty())
            {
                PrintUtil.PrintConsole("&e> &a加载备份记录");
                for(String dates:FileUtil.Backup)
                {
                    String[] date = dates.split(",");
                    if(date.length == 2)
                    {
                        String PlayerName = date[0];
                        int tick = Integer.parseInt(date[1]);
                        double money = tick *TicketCost;
                        if(BuyList.containsKey(PlayerName))
                        {
                            tick = BuyList.get(PlayerName) + tick;
                        }
                        BuyList.put(PlayerName,tick);
                        Money = Money + money;
                    }
                }
                FileUtil.Backup.clear();
                FileUtil.save();
            } else {
                PrintUtil.PrintConsole("&e> &3无备份记录");
            }
        } else {
            if(!BuyList.isEmpty())
            {
                PrintUtil.PrintConsole("&e> &a保存本期购买记录");
                for(String name:BuyList.keySet())
                {
                    String format = name+","+BuyList.get(name);
                    if(!FileUtil.Backup.isEmpty())
                    {
                        FileUtil.Backup.clear();
                    }
                    FileUtil.Backup.add(format);
                }
                FileUtil.save();
            } else {
                PrintUtil.PrintConsole("&e> &c无购买记录");
            }
        }
    }
}