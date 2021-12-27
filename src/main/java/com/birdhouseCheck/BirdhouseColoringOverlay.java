package com.birdhouseCheck;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
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
    /*
        //always works even on log in
        int state = client.getVar(BIRD_HOUSE_MEADOW_NORTH);
        if (state == 0) {
            renderObject(plugin.getYewBirdhouse1(), graphics, Color.RED);
            log.info("meadowNORTH=RED");
        }
        else if (state % 3 == 1 ) {
            renderObject(plugin.getYewBirdhouse1(), graphics, Color.YELLOW);
            log.info("meadowNORTH=YELLOW");
        }
        else if (state % 3 == 0 ){
            renderObject(plugin.getYewBirdhouse1(), graphics, Color.GREEN);
            log.info("meadowNORTH=GREEN");
        }
        //works when walk to it
        int state2 = client.getVar(BIRD_HOUSE_MEADOW_SOUTH);
        if (state2 == 0) {
            renderObject(plugin.getYewBirdhouse2(), graphics, Color.RED);
            log.info("meadowSOUTH=RED");
        }
        else if (state2 % 3 == 1 ) {
            renderObject(plugin.getYewBirdhouse2(), graphics, Color.YELLOW);
            log.info("meadowSOUTH=YELLOW");
        }
        else if (state2 % 3 == 0 ){
            renderObject(plugin.getYewBirdhouse2(), graphics, Color.GREEN);
            log.info("meadowSOUTH=RED");
        }

     */

        int state3 = client.getVar(BIRD_HOUSE_VALLEY_NORTH);
        if (state3 == 0) {
            renderObject(plugin.getYewBirdhouse3(), graphics, Color.RED);
            log.info("valleyNORTH=RED");
        }
        else if (state3 % 3 == 1 ) {
            renderObject(plugin.getYewBirdhouse3(), graphics, Color.YELLOW);
            log.info("valleyNORTH=YELLOW");
        }
        else if (state3 % 3 == 0 ){
            renderObject(plugin.getYewBirdhouse3(), graphics, Color.GREEN);
            log.info("valleyNORTH=GREEN");
        }

        int state4 = client.getVar(BIRD_HOUSE_VALLEY_SOUTH);
        if (state4 == 0) {
            renderObject(plugin.getYewBirdhouse4(), graphics, Color.RED);
            log.info("valleySOUTH=RED");
        }
        else if (state4 % 3 == 1 ) {
            renderObject(plugin.getYewBirdhouse4(), graphics, Color.YELLOW);
            log.info("valleySOUTH=YELLOW");
        }
        else if (state4 % 3 == 0 ){
            renderObject(plugin.getYewBirdhouse4(), graphics, Color.GREEN);
            log.info("valleySOUTH=GREEN");
        }


        return null;

    }

    private void renderObject(GameObject object, Graphics2D graphics, Color color)
    {
        LocalPoint localLocation = client.getLocalPlayer().getLocalLocation();
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
                }
                else
                {
                    graphics.setColor(color);
                }
                graphics.draw(objectClickbox);
                graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 20));
                graphics.fill(objectClickbox);
            }
        }
    }
}
