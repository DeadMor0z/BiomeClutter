package biomeclutter.patch;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.level.maps.Level;
import necesse.level.maps.biomes.MobSpawnTable;
import necesse.level.maps.biomes.desert.DesertBiome;
import net.bytebuddy.asm.Advice;

public class MobSpawnPatch {

    @ModMethodPatch(target = DesertBiome.class, name = "getCritterSpawnTable", arguments = {Level.class})
    static class SpawnRollingTumbleweedPatch {

        @Advice.OnMethodExit
        static void onExit(@Advice.Return(readOnly = false)MobSpawnTable table, @Advice.Argument(0) Level level) {
            if (!level.isCave) {
                table.add(80, "rollingtumbleweed");
            }
        }
    }

}
