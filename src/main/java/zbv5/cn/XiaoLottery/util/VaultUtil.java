package zbv5.cn.XiaoLottery.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;
import zbv5.cn.XiaoLottery.Main;

public class VaultUtil
{
	private static Economy econ = null;

	static
	{
		RegisteredServiceProvider economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null) {
			econ = (Economy)economyProvider.getProvider();
		}
	}
	public static Economy getEconomy()
	{
		return econ;
	}


	public static double getMoney(Player p)
	{
		if(econ != null)
		{
			if(!econ.hasAccount(p))
			{
				econ.createPlayerAccount(p);
			}
			return econ.getBalance(p);
		}
		return 0;
	}

	public static boolean takeMoney(Player p, double money)
	{
		if((econ != null) && (money > 0))
		{
			if(!econ.hasAccount(p))
			{
				econ.createPlayerAccount(p);
			}
			if(getMoney(p) >= money)
			{
				econ.withdrawPlayer(p,money);
				return true;
			}
		}
		return false;
	}
	public static void giveMoney(Player p, double money)
	{
		if((econ != null) && (money > 0))
		{
			if(!econ.hasAccount(p))
			{
				econ.createPlayerAccount(p);
			}
			econ.depositPlayer(p,money);
		}
	}
}