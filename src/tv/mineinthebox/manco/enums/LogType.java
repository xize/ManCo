package tv.mineinthebox.manco.enums;

import org.bukkit.ChatColor;

import tv.mineinthebox.manco.ManCo;

public enum LogType {

	INFO(""),
	SEVERE(ChatColor.RED + "[severe]"),
	DEBUG(ChatColor.BLUE+"[debug]");
	
	private final String name;
	private final String pname = ChatColor.GREEN + "["+ManCo.getPlugin().getName()+"]: " + ChatColor.WHITE;
	
	private LogType(String s) {
		this.name = s;
	}
	
	/**
	 * @author xize
	 * @param returns the prefix of the enum
	 * @return String
	 */
	public String getPrefix() {
		return name+pname;
	}
	
}
