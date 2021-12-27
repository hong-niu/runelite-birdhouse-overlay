package com.birdhouseCheck;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.Objects;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;
import static net.runelite.api.VarPlayer.*;


@Slf4j
class BirdhouseColoringOverlay extends Overlay {

    private final Client client;
    private final BirdhouseCheckPlugin plugin;
    private final BirdhouseCheckConfig config;

    private static final int MAX_DISTANCE = 2350;

    @Inject
    private BirdhouseColoringOverlay(Client client, BirdhouseCheckPlugin plugin, BirdhouseCheckConfig config, ModelOutlineRenderer modelOutlineRenderer)
    {

        this.plugin = plugin;
        this.client = client;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_SCENE);

    }
    @Override
    public Dimension render(Graphics2D graphics) {

        Color unbuiltColor = config.getUnbuiltColor();
        Color unseededColor = config.getUnseededColor();
        Color seededColor = config.getSeededColor();

        if (!Objects.isNull(plugin.getMeadowNorth())) {
            int state = client.getVar(BIRD_HOUSE_MEADOW_NORTH);
            if (state == 0) {
                renderObject(plugin.getMeadowNorth(), graphics, unbuiltColor);
                log.debug("meadowNORTH=RED");
            } else if (state % 3 == 0) {
                renderObject(plugin.getMeadowNorth(), graphics, seededColor);
                log.debug("meadowNORTH=GREEN");
            } else {
                renderObject(plugin.getMeadowNorth(), graphics, unseededColor);
                log.debug("meadowNORTH=YELLOW");
            }
        }

        if (!Objects.isNull(plugin.getMeadowSouth())) {
            int state2 = client.getVar(BIRD_HOUSE_MEADOW_SOUTH);
            if (state2 == 0) {
                renderObject(plugin.getMeadowSouth(), graphics, unbuiltColor);
                log.debug("meadowSOUTH=RED");
            } else if (state2 % 3 == 0) {
                renderObject(plugin.getMeadowSouth(), graphics, seededColor);
                log.debug("meadowSOUTH=GREEN");
            } else {
                renderObject(plugin.getMeadowSouth(), graphics, unseededColor);
                log.debug("meadowSOUTH=YELLOW");
            }
        }


        if (!Objects.isNull(plugin.getValleyNorth())) {
            int state3 = client.getVar(BIRD_HOUSE_VALLEY_NORTH);
            if (state3 == 0) {
                renderObject(plugin.getValleyNorth(), graphics, unbuiltColor);
                log.debug("valleyNORTH=RED");
            } else if (state3 % 3 == 0) {
                renderObject(plugin.getValleyNorth(), graphics, seededColor);
                log.debug("valleyNORTH=GREEN");
            }else {
                renderObject(plugin.getValleyNorth(), graphics, unseededColor);
                log.debug("valleyNORTH=YELLOW");
            }
        }

        if (!Objects.isNull(plugin.getValleySouth())) {
            int state4 = client.getVar(BIRD_HOUSE_VALLEY_SOUTH);
            if (state4 == 0) {
                renderObject(plugin.getValleySouth(), graphics, unbuiltColor);
                log.debug("valleySOUTH=RED");
            } else if (state4 % 3 == 0) {
                renderObject(plugin.getValleySouth(), graphics, seededColor);
                log.debug("valleySOUTH=GREEN");
            } else {
                renderObject(plugin.getValleySouth(), graphics, unseededColor);
                log.debug("valleySOUTH=YELLOW");
            }
        }

        return null;

    }

    private void renderObject(GameObject object, Graphics2D graphics, Color color) {
        LocalPoint localLocation = client.getLocalPlayer().getLocalLocation();
        Point mousePosition = client.getMouseCanvasPosition();

        LocalPoint location = object.getLocalLocation();

        if (localLocation.distanceTo(location) <= MAX_DISTANCE) {
            Shape objectClickbox = object.getClickbox();
            if (objectClickbox != null) {

                if (objectClickbox.contains(mousePosition.getX(), mousePosition.getY())) {
                    graphics.setColor(color.darker());
                    log.debug("Setting Clickbox - Darker");
                }
                else {
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
