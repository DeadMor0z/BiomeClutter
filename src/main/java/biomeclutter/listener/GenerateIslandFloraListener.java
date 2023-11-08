package biomeclutter.listener;

import necesse.engine.GameEventListener;
import necesse.engine.events.worldGeneration.GenerateIslandFloraEvent;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.level.maps.biomes.forest.ForestSurfaceLevel;
import necesse.level.maps.biomes.snow.SnowSurfaceLevel;
import necesse.level.maps.biomes.swamp.SwampSurfaceLevel;

public class GenerateIslandFloraListener extends GameEventListener<GenerateIslandFloraEvent> {

    @Override
    public void onEvent(GenerateIslandFloraEvent event) {
        if (event.level instanceof ForestSurfaceLevel) {
            event.islandGeneration.generateObjects(ObjectRegistry.getObjectID("oakbark"), TileRegistry.grassID, 0.002F);
            event.islandGeneration.generateObjects(ObjectRegistry.getObjectID("sprucebark"), TileRegistry.grassID, 0.001F);
        } else if (event.level instanceof SnowSurfaceLevel) {
            event.islandGeneration.generateObjects(ObjectRegistry.getObjectID("pinebark"), TileRegistry.grassID, 0.003F);

        } else if (event.level instanceof SwampSurfaceLevel) {
            event.islandGeneration.generateObjects(ObjectRegistry.getObjectID("willowbark"), TileRegistry.grassID, 0.003F);
        }
    }
}
