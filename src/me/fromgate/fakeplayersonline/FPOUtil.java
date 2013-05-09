/*
 * FakePlayersOnline, Minecraft bukkit plugin
 * (c)2012, 2013, fromgate, fromgate@gmail.com
 * http://dev.bukkit.org/server-mods/fakeplayers/
 *
 * This file is part of FakePlayersOnline.
 *
 * FakePlayersOnline is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FakePlayersOnline is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FakePlayersOnline. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package me.fromgate.fakeplayersonline;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class FPOUtil extends FGUtilCore implements Listener{
	FakePlayersOnline plg;

	public FPOUtil(FakePlayersOnline plg, boolean vcheck, boolean savelng, String lng,
			String devbukkitname, String version_name, String plgcmd, String px) {
		super(plg, vcheck, savelng, lng, devbukkitname, version_name, plgcmd, px);
		this.plg = plg;
		initMSG();
		initCmd();
		if (savelng) this.SaveMSG();
		
	}

	private void initCmd(){
		//addCmd("test", "config", "hlp_testcmd", "/fpo test");
		addCmd("help", "config", "hlp_thishelp", "/fpo help");
		addCmd("add", "config", "hlp_helpadd", "/fpo add <fakeplayer>");
		addCmd("del", "config", "hlp_helpdel", "/fpo del <fakeplayer>");
		addCmd("list", "config", "hlp_helplist", "/fpo list");
		addCmd("real", "config", "hlp_helpreal", "/fpo real");
		addCmd("fake", "config", "hlp_helpfake", "/fpo fake");
		addCmd("npc", "config", "hlp_helpnpc", "/fpo npc");
		addCmd("slots", "config", "hlp_maxplayers", "/fpo slots [fake reserved slots]");
		addCmd("listcmd", "config", "hlp_fakelistcmd", "/fpo listcmd [command1,command2..]");
		addCmd("serverlist", "config", "hlp_serverlist", "/fpo serverlist");
		addCmd("online", "config", "hlp_online", "/fpo online [fake players online]");
		addCmd("motd", "config", "hlp_motd", "/fpo motd");
		addCmd("cfg", "config", "hlp_helpcfg", "/fpo cfg");
	}

	private void initMSG(){
		addMSG ("msg_fplist", "Fake players: %1%");
		addMSG ("msg_fplistempty", "No fake players in list!");
		addMSG ("msg_realstatus", "Overriding real player list");
		addMSG ("msg_fakestatus", "Show fake players");
		addMSG ("msg_npcstatus", "Show NPCs");
		addMSG ("msg_fakeadded", "Fake player added: %1%");
		addMSG ("msg_fakeremoved", "Fake player removed: %1%");
		addMSG ("msg_fakeunknown", "Fake player not found: %1%");
		addMSG ("hlp_helpadd", "%1% - add fake player to the list");
		addMSG ("hlp_helpdel", "%1% - remove fake player from the list");
		addMSG ("hlp_helplist", "%1% - display current fake player list");
		addMSG ("hlp_helpreal", "%1% - toggle overriding normal players in list");
		addMSG ("hlp_helpfake", "%1% - toggle displaying fakes in player list");
		addMSG ("hlp_helpnpc", "%1% - toggle displaying NPC in player list");
		addMSG ("hlp_helpcfg", "%1% - display current configuration");
		addMSG ("msg_configuration", "Configuration");
		addMSG ("msg_cfgreal", "Overriding real players list: %1%");
		addMSG ("msg_cfgfake", "Display fake players: %1%, in list: %2%");
		addMSG ("msg_cfgnpc", "Display NPC: %1%, in list: %2%");
		addMSG ("msg_citizenserror", "Citizens plugin not found (is it installed?)");
		addMSG ("msg_fakelistcmd", "There are %1%/%2% players online:");
		addMSG ("msg_motd", "MOTD is set to: %1%");
		addMSG ("msg_wrongfixedonline", "Failed to set the parameter (Wrong value: %1%)");
		addMSG ("msg_fixedonline", "Fixed online counter is set to: %1%");
		addMSG ("msg_listcmdalias", "Aliases of ther list command: %1%");
		addMSG ("msg_fakemotd", "Fake motd is");
		addMSG ("msg_fixedseverlist", "Fixed online counter is");
		addMSG ("msg_fakeserverlist", "Faking the server info in multiplayer menu");
		addMSG ("msg_fakelistcmdoverride", "Override list command");
		addMSG ("hlp_fakelistcmd", "%1% - toogle using the /list command overriding, or define /list command aliases");
		addMSG ("hlp_serverlist", "%1% - toggle fake online info at server list menu");
		addMSG ("hlp_online", "%1% - toggle faking the fixed online counter or set it's value");
		addMSG ("hlp_motd", "%1% - toggle using the alternative MOTD or set it's value");
		addMSG ("msg_maxplayers", "Reserved slot faking is");
		addMSG ("msg_maxplayersslot", "Reserved slot counter is set to %1%");
		addMSG ("hlp_maxplayers", "%1% - toggle faking the reserved slots counter or set it's value");
	}

	protected void showCfg(Player p){
		printMsg(p, "&6&l"+des.getName()+" v"+des.getVersion()+" &r&6| "+getMSG("msg_configuration",'6'));
		printMSG(p, "msg_cfgfake",EnDis(plg.fake),plg.fakeplayers.size());
		printMSG(p, "msg_cfgnpc",EnDis(plg.npc),plg.npclist.size());
		printEnDis(p, "msg_fakelistcmdoverride",plg.listcmdoverride);
		printMSG(p, "msg_listcmdalias", plg.listcmd);
		printEnDis(p, "msg_fakeserverlist",plg.fake_serverlist);
		printEnDis(p, "msg_maxplayers",plg.fakemaxplayersenable);
		printMSG(p, "msg_maxplayersslot", plg.fakemaxplayers);
		printEnDis(p, "msg_fixedseverlist",plg.fixedseverlist);
		printMSG(p, "msg_fixedonline", plg.fixedonline);
		printEnDis(p, "msg_fakemotd",plg.fakemotd);
		printMSG(p, "msg_motd", plg.motd);
	}
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onJoin (PlayerJoinEvent event){
		plg.u.UpdateMsg(event.getPlayer());
		plg.refreshOnlineList();
	}
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onQuit (PlayerQuitEvent event){
		Bukkit.getScheduler().runTaskLater(plg, new Runnable(){
			@Override
			public void run() {
				plg.refreshOnlineList();
			}
		}, 1);
	}
	
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void onListCommand (PlayerCommandPreprocessEvent event){
		if (!plg.listcmdoverride) return;
		String [] ln = plg.listcmd.replace(" ", "").split(",");
		for (String cmd : ln)
			if (event.getMessage().startsWith("/"+cmd)){
				String str = "";
				for (int i = 0; i<plg.showlist.size();i++)
					str= str+", "+plg.showlist.get(i);
				str=str.replaceFirst(", ","");
				printMSG(event.getPlayer(),"msg_fakelistcmd",plg.getPlayersOnline(),plg.getMaxPlayers());
				printMsg(event.getPlayer(),str);
				event.setCancelled(true);
				break;
			}
	}
	
}