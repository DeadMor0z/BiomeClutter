package biomeclutter.listener;

import necesse.engine.GameEventListener;
import necesse.engine.events.worldGeneration.GeneratedIslandFloraEvent;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.GameRandom;
import necesse.level.maps.biomes.desert.DesertSurfaceLevel;
import necesse.level.maps.biomes.forest.ForestSurfaceLevel;
import necesse.level.maps.biomes.plains.PlainsSurfaceLevel;
import necesse.level.maps.biomes.snow.SnowSurfaceLevel;
import necesse.level.maps.biomes.swamp.SwampSurfaceLevel;

public class GeneratedIslandFloraListener extends GameEventListener<GeneratedIslandFloraEvent> {

    @Override
    public void onEvent(GeneratedIslandFloraEvent event) {
        if (event.level instanceof ForestSurfaceLevel || event.level instanceof PlainsSurfaceLevel) {
            event.islandGeneration.generateObjects(ObjectRegistry.getObjectID("fallenoaktrunk"), TileRegistry.grassID, GameRandom.globalRandom.getFloatBetween(0.001F, 0.002F));
            event.islandGeneration.generateObjects(ObjectRegistry.getObjectID("fallensprucetrunk"), TileRegistry.grassID, GameRandom.globalRandom.getFloatBetween(0.0005F, 0.001F));

            event.islandGeneration.generateObjects(ObjectRegistry.getObjectID("cutoaktrunk"), TileRegistry.grassID, GameRandom.globalRandom.getFloatBetween(0.0002F, 0.0005F));
            event.islandGeneration.generateObjects(ObjectRegistry.getObjectID("cutsprucetrunk"), TileRegistry.grassID, GameRandom.globalRandom.getFloatBetween(0.0001F, 0.0003F));
        } else if (event.level instanceof SnowSurfaceLevel) {
            event.islandGeneration.generateObjects(ObjectRegistry.getObjectID("fallenpinetrunk"), TileRegistry.snowID, GameRandom.globalRandom.getFloatBetween(0.001F, 0.003F));
            event.islandGeneration.generateObjects(ObjectRegistry.getObjectID("cutpinetrunk"), TileRegistry.snowID, GameRandom.globalRandom.getFloatBetween(0.0002F, 0.0005F));

            event.islandGeneration.generateFruitGrowerVeins("sweetberrybush", 0.04F, 8, 10, 0.1F, TileRegistry.snowID);

        } else if (event.level instanceof SwampSurfaceLevel) {
            event.islandGeneration.generateObjects(ObjectRegistry.getObjectID("fallenwillowtrunk"), TileRegistry.swampGrassID, GameRandom.globalRandom.getFloatBetween(0.001F, 0.003F));
            event.islandGeneration.generateObjects(ObjectRegistry.getObjectID("cutwillowtrunk"), TileRegistry.swampGrassID, GameRandom.globalRandom.getFloatBetween(0.0002F, 0.0005F));
        } else if (event.level instanceof DesertSurfaceLevel) {
            event.islandGeneration.generateObjects(ObjectRegistry.getObjectID("surfacecactussmall"), TileRegistry.sandID, GameRandom.globalRandom.getFloatBetween(0.001F, 0.002F));
        }
    }
}
