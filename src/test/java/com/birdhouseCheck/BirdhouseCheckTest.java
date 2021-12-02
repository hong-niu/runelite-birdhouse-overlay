package com.birdhouseCheck;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class BirdhouseCheckTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BirdhouseCheckPlugin.class);
		RuneLite.main(args);
	}
}