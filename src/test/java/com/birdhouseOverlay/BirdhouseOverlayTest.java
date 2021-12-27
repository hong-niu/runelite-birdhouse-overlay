package com.birdhouseOverlay;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class BirdhouseOverlayTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BirdhouseOverlayPlugin.class);
		RuneLite.main(args);

	}
}