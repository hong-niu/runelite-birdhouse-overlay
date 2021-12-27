package com.birdhouseOverlay;

import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup("Birdhouse Overlay")
public interface BirdhouseOverlayConfig extends Config
{
	@Alpha
	@ConfigItem(
			position = 1,
			keyName = "emptyColor",
			name = "Unbuilt Color",
			description = "Color overlay of unbuilt birdhouse"
	)
	default Color getUnbuiltColor()
	{
		return Color.RED;
	}
	@Alpha
	@ConfigItem(
			position = 2,
			keyName = "unseededColor",
			name = "Unseeded Color",
			description = "Color overlay of unseeded birdhouse"
	)
	default Color getUnseededColor()
	{
		return Color.YELLOW;
	}
	@Alpha
	@ConfigItem(
			position = 3,
			keyName = "seededColor",
			name = "Seeded Color",
			description = "Color overlay of unseeded birdhouse"
	)
	default Color getSeededColor()
	{
		return Color.GREEN;
	}
}
