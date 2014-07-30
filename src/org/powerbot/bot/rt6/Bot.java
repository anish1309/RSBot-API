package org.powerbot.bot.rt6;

import java.awt.Component;
import java.lang.reflect.Field;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.powerbot.bot.Reflector;
import org.powerbot.gui.BotChrome;
import org.powerbot.misc.GoogleAnalytics;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Constants;

public final class Bot extends org.powerbot.script.Bot<ClientContext> {
	public Bot(final BotChrome chrome) {
		super(chrome, new EventDispatcher());
	}

	@Override
	protected ClientContext newContext() {
		return ClientContext.newContext(this);
	}

	@Override
	protected Map<String, byte[]> getClasses() {
		final Component o0 = chrome.target.get();

		for (final Field f1 : o0.getClass().getDeclaredFields()) {
			final boolean a1 = f1.isAccessible();
			f1.setAccessible(true);
			if (Class.class.equals(f1.getType())) {
				Object o1 = null;
				while (o1 == null) {
					try {
						o1 = f1.get(o0);
					} catch (final IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				}
			}
			f1.setAccessible(a1);
		}

		for (final Field f1 : o0.getClass().getDeclaredFields()) {
			final boolean a1 = f1.isAccessible();
			f1.setAccessible(true);
			final Object o1;
			try {
				o1 = f1.get(o0);
			} catch (final IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			f1.setAccessible(a1);
			if (o1 == null) {
				continue;
			}
			final List<Field> f1x = Reflector.getFields(o1.getClass());
			Hashtable<String, byte[]> v0 = null;
			Hashtable<String, Class<?>> v1 = null;
			ProtectionDomain v2 = null;

			for (final Field f2 : f1x) {
				final Class<?> c2x = f2.getType();
				if (!c2x.equals(Object.class)) {
					continue;
				}

				final boolean a2 = f2.isAccessible();
				f2.setAccessible(true);
				Object o2 = null;
				while (o2 == null) {
					try {
						o2 = f2.get(o1);
					} catch (final IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				}
				f2.setAccessible(a2);
				final Class<?> c2 = o2.getClass();

				if (c2.equals(Hashtable.class)) {
					final Hashtable x = (Hashtable) o2;
					if (x.keys().nextElement().getClass().equals(String.class)) {
						final Class<?> c3 = x.elements().nextElement().getClass();
						if (c3.equals(byte[].class)) {
							@SuppressWarnings("unchecked")
							final Hashtable<String, byte[]> xs = x;
							v0 = xs;
						} else if (c3.getClass().equals(Class.class)) {
							@SuppressWarnings("unchecked")
							final Hashtable<String, Class<?>> xs = x;
							v1 = xs;
						}
					}
				} else if (c2.equals(ProtectionDomain.class)) {
					v2 = (ProtectionDomain) o2;
				}
			}

			if (v0 != null && v1 != null && v2 != null) {
				final Map<String, byte[]> z;
				synchronized (v0) {
					z = new HashMap<String, byte[]>(v0);
				}

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (ctx.game == null) {
					return;
				}
				final int s = ctx.game.clientState();
				if (s == Constants.GAME_INDEX_LOGIN_SCREEN || s == Constants.GAME_INDEX_LOGGING_IN) {
					final org.powerbot.script.rt6.Component e = ctx.widgets.component(Constants.LOGIN_WIDGET, Constants.LOGIN_WIDGET_LOGIN_ERROR);
					if (e.visible()) {
						String m = null;
						final String txt = e.text().toLowerCase();

								if (txt.contains("your ban will be lifted in")) {
									m = "ban";
								} else if (txt.contains("account has been disabled")) {
									m = "disabled";
								}

								if (m != null) {
									GoogleAnalytics.getInstance().pageview("scripts/0/login/" + m, txt);
								}
							}
						}
					}
				}, 6000, 3000);
				return z;
			}
		}

		return null;
	}

	public final class SafeMode implements Runnable {
		@Override
		public void run() {
			try {
				Thread.sleep(600);
			} catch (final InterruptedException ignored) {
			}
			log.info("Requesting safe mode");
			ctx.input.send("s");
		}
	}
}
