package com.birdhouseOverlay;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import static net.runelite.api.NullObjectID.NULL_30565;
import static net.runelite.api.NullObjectID.NULL_30566;
import static net.runelite.api.NullObjectID.NULL_30567;
import static net.runelite.api.NullObjectID.NULL_30568;

@Slf4j
@PluginDescriptor(
	name = "Birdhouse Overlay"
)
public class BirdhouseOverlayPlugin extends Plugin
{
	private static final int MEADOW_NORTH = NULL_30565;
	private static final int MEADOW_SOUTH = NULL_30566;
	private static final int VALLEY_NORTH = NULL_30567;
	private static final int VALLEY_SOUTH = NULL_30568;

	@Inject
	private Client client;

	@Inject
	private BirdhouseOverlayConfig config;

	@Inject
	private BirdhouseColoringOverlay overlay;

	@Getter(AccessLevel.PACKAGE)
	private GameObject meadowNorth;
	@Getter(AccessLevel.PACKAGE)
	private GameObject meadowSouth;
	@Getter(AccessLevel.PACKAGE)
	private GameObject valleyNorth;
	@Getter(AccessLevel.PACKAGE)
	private GameObject valleySouth;

	@Inject
	private OverlayManager overlayManager;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		meadowNorth = null;
		meadowSouth = null;
		valleyNorth = null;
		valleySouth = null;
	}

	@Provides
	BirdhouseOverlayConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BirdhouseOverlayConfig.class);
	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event)
	{
		GameObject gameObject = event.getGameObject();

		switch (gameObject.getId())
		{
			case MEADOW_NORTH:
				meadowNorth = gameObject;
				break;
			case MEADOW_SOUTH:
				meadowSouth = gameObject;
				break;
			case VALLEY_NORTH:
				valleyNorth = gameObject;
				break;
			case VALLEY_SOUTH:
				valleySouth = gameObject;
				break;
		}
	}

	@Subscribe
	public void onGameObjectDespawned(GameObjectDespawned event)
	{
		GameObject gameObject = event.getGameObject();
		switch (gameObject.getId())
		{
			case MEADOW_NORTH:
				meadowNorth = null;
				break;
			case MEADOW_SOUTH:
				meadowSouth = null;
				break;
			case VALLEY_NORTH:
				valleyNorth = null;
				break;
			case VALLEY_SOUTH:
				valleySouth = null;
				break;

		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOADING)
		{
			meadowNorth = null;
			meadowSouth = null;
			valleyNorth = null;
			valleySouth = null;
		}
	}

}
