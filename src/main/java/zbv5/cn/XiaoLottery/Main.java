package zbv5.cn.XiaoLottery;

import org.bukkit.plugin.java.JavaPlugin;

import zbv5.cn.XiaoLottery.util.PluginUtil;

public class Main extends JavaPlugin
{
    private static Main instance;

    public static Main getInstance()
    {
        return instance;
    }

    @Override
    public void onEnable()
    {
        instance = this;
        PluginUtil.LoadPlugin();
    }

    @Override
    public void onDisable()
    {
        PluginUtil.DisablePlugin();
    }

}
