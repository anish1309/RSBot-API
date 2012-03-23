package org.powerbot.game.api.methods.widget;

import org.powerbot.game.bot.Bot;

/**
 * @author Timer
 */
public class Camera {
	public static int getX() {
		final Bot bot = Bot.resolve();
		return bot.getClient().getCamPosX() * bot.multipliers.GLOBAL_CAMPOSX;
	}

	public static int getY() {
		final Bot bot = Bot.resolve();
		return bot.getClient().getCamPosY() * bot.multipliers.GLOBAL_CAMPOSY;
	}

	public static int getZ() {
		final Bot bot = Bot.resolve();
		return bot.getClient().getCamPosZ() * bot.multipliers.GLOBAL_CAMPOSZ;
	}

	public static int getYaw() {
		final Bot bot = Bot.resolve();
		return bot.getClient().getCameraYaw() * bot.multipliers.GLOBAL_CAMERAYAW;
	}

	public static int getPitch() {
		final Bot bot = Bot.resolve();
		return bot.getClient().getCameraPitch() * bot.multipliers.GLOBAL_CAMERAPITCH;
	}
}
