package zbv5.cn.XiaoLottery.lang;

import zbv5.cn.XiaoLottery.util.FileUtil;
import zbv5.cn.XiaoLottery.util.PrintUtil;

public class Lang
{
    public static String Prefix = "&6[&bXiaoLottery&6]";

    public static String NoPermission = "{prefix}&c你没有权限这样做";
    public static String SuccessReload = "{prefix}&a重载完成!";
    public static String FailReload = "{prefix}&c重载失败!请前往控制台查看报错.";
    public static String Executed= "{prefix}&a执行成功";
    public static String NoInteger = "&c输入值非整数.";
    public static String NeedPlayer = "{prefix}&c只有玩家才能执行该操作.";
    public static String NullCommand = "{prefix}&c未知指令.";
    public static String NullBuy = "{prefix}&c本轮没有玩家购买彩票.";
    public static String RemindBroadcast = "{prefix}&e开奖时间剩余:&a{time}秒&e,目前奖池金额:&6{money}";
    public static String SuccessBuy = "{prefix}&a你成功花费了&e{money}&a购买了&6{amount}张&a彩票";
    public static String BuyBroadcast = "{prefix}&a玩家{player}&a购买了&6{amount}张&a彩票";
    public static String FailBuy_NullInteger = "{prefix}&c购买数量异常.";
    public static String FailBuy_Money = "{prefix}&c购买&6{amount}张&c彩票需要&e{money}&c,你只有&7{player_money}";
    public static String FailBuy_Max = "{prefix}&c最多可以买&6{amount}张&c彩票";
    public static String DrawBroadcast = "{prefix}&a祝贺本期彩票得主{player}在奖池中赢得了 &e{money}";
    public static void LoadLang()
    {
        try
        {
            Prefix = FileUtil.lang.getString("Prefix");
            NoPermission = FileUtil.lang.getString("NoPermission");
            SuccessReload = FileUtil.lang.getString("SuccessReload");
            FailReload = FileUtil.lang.getString("FailReload");
            Executed = FileUtil.lang.getString("Executed");
            NoInteger = FileUtil.lang.getString("NoInteger");
            NeedPlayer = FileUtil.lang.getString("NeedPlayer");
            NullCommand = FileUtil.lang.getString("NullCommand");
            NullBuy = FileUtil.lang.getString("NullBuy");
            RemindBroadcast = FileUtil.lang.getString("RemindBroadcast");
            SuccessBuy = FileUtil.lang.getString("SuccessBuy");
            BuyBroadcast = FileUtil.lang.getString("BuyBroadcast");
            FailBuy_NullInteger = FileUtil.lang.getString("FailBuy_NullInteger");
            FailBuy_Money = FileUtil.lang.getString("FailBuy_Money");
            FailBuy_Max = FileUtil.lang.getString("FailBuy_Max");
            DrawBroadcast = FileUtil.lang.getString("DrawBroadcast");
            PrintUtil.PrintConsole("&a&l√ &a语言文件加载完成.");
        }
        catch (Exception e)
        {
            PrintUtil.PrintConsole("&c&l× &4读取语言文件出现问题,请检查服务器.");
            e.printStackTrace();
        }
    }
}
