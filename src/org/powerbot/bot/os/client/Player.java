package org.powerbot.bot.os.client;

public interface Player extends Actor {
	public int getCombatLevel();

	public String getName();

	public int getTeam();

	public PlayerComposite getComposite();
}