package biomeclutter.listener;

import necesse.engine.GameEventListener;
import necesse.engine.events.worldGeneration.GeneratedIslandFloraEvent;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.GameRandom;
import necesse.level.maps.biomes.forest.ForestSurfaceLevel;
import necesse.level.maps.biomes.snow.SnowSurfaceLevel;
import necesse.level.maps.biomes.swamp.SwampSurfaceLevel;

public class GeneratedIslandFloraListener extends GameEventListener<GeneratedIslandFloraEvent> {

    @Override
    public void onEvent(GeneratedIslandFloraEvent event) {
        if (event.level instanceof ForestSurfaceLevel) {
            event.islandGeneration.generateObjects(ObjectRegistry.getObjectID("fallenoaktrunk"), TileRegistry.grassID, GameRandom.globalRandom.getFloatBetween(0.001F, 0.002F));
            event.islandGeneration.generateObjects(ObjectRegistry.getObjectID("fallensprucetrunk"), TileRegistry.grassID, GameRandom.globalRandom.getFloatBetween(0.0005F, 0.001F));
        } else if (event.level instanceof SnowSurfaceLevel) {
            event.islandGeneration.generateObjects(ObjectRegistry.getObjectID("fallenpinetrunk"), TileRegistry.grassID, GameRandom.globalRandom.getFloatBetween(0.001F, 0.003F));

        } else if (event.level instanceof SwampSurfaceLevel) {
            event.islandGeneration.generateObjects(ObjectRegistry.getObjectID("fallenwillowtrunk"), TileRegistry.grassID, GameRandom.globalRandom.getFloatBetween(0.001F, 0.003F));
        }
    }
}
