package biomeclutter;

import biomeclutter.listener.GeneratedIslandFloraListener;
import biomeclutter.object.FallenTreeTrunk1Object;
import biomeclutter.object.FallenTreeTrunkObject;
import biomeclutter.registry.ItemModRegistry;
import biomeclutter.registry.MobModRegistry;
import biomeclutter.registry.ObjectModRegistry;
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

        ItemModRegistry.registerItems();
        ObjectModRegistry.registerObjects();
        MobModRegistry.registerMobs();

        this.addListeners();
    }

    public void initResources() {
        ModResources.Textures.load();
    }

    public void postInit() {
    }

    public void addListeners() {
        GameEvents.addListener(GeneratedIslandFloraEvent.class, new GeneratedIslandFloraListener());
    }

}
