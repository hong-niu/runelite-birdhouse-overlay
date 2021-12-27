package com.birdhouseCheck;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.GroundObject;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import static net.runelite.api.NullObjectID.*;
import static net.runelite.api.ObjectID.*;


@Slf4j
@PluginDescriptor(
	name = "BirdhouseIndicator"
)
public class BirdhouseCheckPlugin extends Plugin
{

	private static final int YEW_BIRDHOUSE1 = NULL_30565;
	private static final int YEW_BIRDHOUSE2 = NULL_30566;
	private static final int YEW_BIRDHOUSE3 = NULL_30567;
	private static final int YEW_BIRDHOUSE4 = NULL_30568;



	@Inject
	private Client client;

	@Inject
	private BirdhouseCheckConfig config;

	@Inject
	private BirdhouseColoringOverlay overlay;

	@Getter(AccessLevel.PACKAGE)
	private GameObject yewBirdhouse1;
	@Getter(AccessLevel.PACKAGE)
	private GameObject yewBirdhouse2;
	@Getter(AccessLevel.PACKAGE)
	private GameObject yewBirdhouse3;
	@Getter(AccessLevel.PACKAGE)
	private GameObject yewBirdhouse4;

	@Inject
	private OverlayManager overlayManager;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
		log.info("starting up! 69");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("shutdown");
		overlayManager.remove(overlay);
		yewBirdhouse1 = null;
		yewBirdhouse2 = null;
		yewBirdhouse3 = null;
		yewBirdhouse4 = null;
	}

	@Provides
	BirdhouseCheckConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BirdhouseCheckConfig.class);
	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event)
	{
		GameObject gameObject = event.getGameObject();
		log.info("onGameObjectSpawned");

		switch (gameObject.getId())
		{

			case YEW_BIRDHOUSE1:
				yewBirdhouse1 = gameObject;
				break;
			case YEW_BIRDHOUSE2:
				yewBirdhouse2 = gameObject;
				break;
			case YEW_BIRDHOUSE3:
				yewBirdhouse3 = gameObject;
				break;
			case YEW_BIRDHOUSE4:
				yewBirdhouse4 = gameObject;
				break;
		}
	}

	@Subscribe
	public void onGameObjectDespawned(GameObjectDespawned event)
	{
		GameObject gameObject = event.getGameObject();
		log.info("onGameObjectDespawned");
		switch (gameObject.getId())
		{

			case YEW_BIRDHOUSE1:
				yewBirdhouse1 = null;
				break;

			case YEW_BIRDHOUSE2:
				yewBirdhouse2 = null;
				break;

			case YEW_BIRDHOUSE3:
				yewBirdhouse3 = null;
				break;

			case YEW_BIRDHOUSE4:
				yewBirdhouse4 = null;
				break;


		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		log.info("onGameStateChanged");
		if (event.getGameState() == GameState.LOADING) {
			yewBirdhouse1 = null;
			yewBirdhouse2 = null;
			yewBirdhouse3 = null;
			yewBirdhouse4 = null;
		}
	}

	/*
	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}
	*/

	@Subscribe
	public void onGameTick(GameTick event)
	{
	}

	@Subscribe
	public void onVarbitChanged(VarbitChanged event) {

	}

}
