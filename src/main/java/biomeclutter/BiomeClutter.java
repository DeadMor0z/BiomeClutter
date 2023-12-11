package biomeclutter;

import biomeclutter.listener.GeneratedIslandFloraListener;
import biomeclutter.registry.ItemModRegistry;
import biomeclutter.registry.MobModRegistry;
import biomeclutter.registry.ObjectModRegistry;
import necesse.engine.GameEvents;
import necesse.engine.events.worldGeneration.GeneratedIslandFloraEvent;
import necesse.engine.modLoader.annotations.ModEntry;

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
