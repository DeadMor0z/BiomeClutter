package biomeclutter;

import biomeclutter.listener.GenerateIslandFloraListener;
import biomeclutter.object.TreeBarkObject;
import necesse.engine.GameEvents;
import necesse.engine.events.worldGeneration.GenerateIslandFloraEvent;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.ObjectRegistry;
import necesse.level.gameObject.TreeObject;

import java.awt.*;

@ModEntry
public class BiomeClutter {

    public void init() {
        System.out.println("BiomeClutter was enabled");

        TreeBarkObject.registerTreeBark((TreeObject) ObjectRegistry.getObject("oaktree"), "oakbark", new Color(86, 69, 40));
        TreeBarkObject.registerTreeBark((TreeObject) ObjectRegistry.getObject("sprucetree"), "sprucebark", new Color(86, 69, 40));
        TreeBarkObject.registerTreeBark((TreeObject) ObjectRegistry.getObject("willowtree"), "willowbark", new Color(86, 69, 40));
        TreeBarkObject.registerTreeBark((TreeObject) ObjectRegistry.getObject("pinetree"), "pinebark", new Color(86, 69, 40));

        GameEvents.addListener(GenerateIslandFloraEvent.class, new GenerateIslandFloraListener());
    }

    public void initResources() {
    }

    public void postInit() {
    }

}
