package com.birdhouseOverlay;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.Objects;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;

import static net.runelite.api.VarPlayer.BIRD_HOUSE_MEADOW_NORTH;
import static net.runelite.api.VarPlayer.BIRD_HOUSE_MEADOW_SOUTH;
import static net.runelite.api.VarPlayer.BIRD_HOUSE_VALLEY_NORTH;
import static net.runelite.api.VarPlayer.BIRD_HOUSE_VALLEY_SOUTH;

@Slf4j
class BirdhouseColoringOverlay extends Overlay {

    private final Client client;
    private final BirdhouseOverlayConfig config;
    private final BirdhouseOverlayPlugin plugin;

    private static final int MAX_DISTANCE = 2350;

    @Inject
    private BirdhouseColoringOverlay(Client client, BirdhouseOverlayPlugin plugin, BirdhouseOverlayConfig config, ModelOutlineRenderer modelOutlineRenderer)
    {
        this.plugin = plugin;
        this.client = client;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        GameObject meadowNorth = plugin.getMeadowNorth();
        GameObject meadowSouth = plugin.getMeadowSouth();
        GameObject valleyNorth = plugin.getValleyNorth();
        GameObject valleySouth = plugin.getValleySouth();

        if (!Objects.isNull(meadowNorth))
        {
            int meadowNorthState = client.getVar(BIRD_HOUSE_MEADOW_NORTH);
            birdhouseStateRenderer(meadowNorth, meadowNorthState, graphics);
        }

        if (!Objects.isNull(meadowSouth))
        {
            int meadowSouthState = client.getVar(BIRD_HOUSE_MEADOW_SOUTH);
            birdhouseStateRenderer(meadowSouth, meadowSouthState, graphics);
        }

        if (!Objects.isNull(valleyNorth))
        {
            int valleyNorthState = client.getVar(BIRD_HOUSE_VALLEY_NORTH);
            birdhouseStateRenderer(valleyNorth, valleyNorthState, graphics);
        }

        if (!Objects.isNull(valleySouth))
        {
            int valleySouthState = client.getVar(BIRD_HOUSE_VALLEY_SOUTH);
            birdhouseStateRenderer(valleySouth, valleySouthState, graphics);
        }

        return null;
    }

    private void birdhouseStateRenderer(GameObject birdhouse, int state, Graphics2D graphics)
    {
        Color unbuiltColor = config.getUnbuiltColor();
        Color unseededColor = config.getUnseededColor();
        Color seededColor = config.getSeededColor();

        if (state == 0)
        {
            renderObject(birdhouse, graphics, unbuiltColor);
            log.debug(birdhouse + "rendered RED");
        }
        else if (state % 3 == 0)
        {
            renderObject(birdhouse, graphics, seededColor);
            log.debug(birdhouse + "rendered GREEN");
        }
        else
        {
            renderObject(birdhouse, graphics, unseededColor);
            log.debug(birdhouse + "rendered YELLOW");
        }
    }

    private void renderObject(GameObject object, Graphics2D graphics, Color color)
    {
        LocalPoint localLocation = client.getLocalPlayer().getLocalLocation();
        if (Objects.isNull(localLocation))
        {
            return;
        }

        Point mousePosition = client.getMouseCanvasPosition();
        LocalPoint location = object.getLocalLocation();

        if (localLocation.distanceTo(location) <= MAX_DISTANCE)
        {
            Shape objectClickbox = object.getClickbox();
            if (objectClickbox != null)
            {
                if (objectClickbox.contains(mousePosition.getX(), mousePosition.getY()))
                {
                    graphics.setColor(color.darker());
                    log.debug("Setting Clickbox - Darker");
                }
                else
                {
                    graphics.setColor(color);
                    log.debug("Setting Clickbox - Normal");
                }

                graphics.draw(objectClickbox);
                graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 20));
                graphics.fill(objectClickbox);
            }
        }
    }
}
