package biomeclutter;

import biomeclutter.listener.GeneratedIslandFloraListener;
import biomeclutter.object.FallenTreeTrunk1Object;
import biomeclutter.object.FallenTreeTrunkObject;
import necesse.engine.GameEvents;
import necesse.engine.events.worldGeneration.GeneratedIslandFloraEvent;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.ObjectRegistry;
import necesse.level.gameObject.TreeObject;

import java.awt.*;

@ModEntry
public class BiomeClutter {

    public void init() {
        System.out.println("BiomeClutter was enabled");

        FallenTreeTrunkObject.registerTreeBark((TreeObject) ObjectRegistry.getObject("oaktree"), "fallenoaktrunk");
        FallenTreeTrunkObject.registerTreeBark((TreeObject) ObjectRegistry.getObject("sprucetree"), "fallensprucetrunk");
        FallenTreeTrunkObject.registerTreeBark((TreeObject) ObjectRegistry.getObject("willowtree"), "fallenwillowtrunk");
        FallenTreeTrunkObject.registerTreeBark((TreeObject) ObjectRegistry.getObject("pinetree"), "fallenpinetrunk");

        GameEvents.addListener(GeneratedIslandFloraEvent.class, new GeneratedIslandFloraListener());
    }

    public void initResources() {
    }

    public void postInit() {
    }

}
