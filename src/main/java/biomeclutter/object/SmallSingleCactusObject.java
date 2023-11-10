package biomeclutter.object;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.TileRegistry;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.maps.Level;

import java.awt.*;

public class SmallSingleCactusObject extends SmallSingleRandomObject {

    public SmallSingleCactusObject() {
        super(new Rectangle(4, 16, 24, 12));
        this.mapColor = new Color(101, 189, 75);
        this.toolType = ToolType.AXE;
    }

    public GameMessage getNewLocalization() {
        return new LocalMessage("object", "surfacecactussmall");
    }

    public void loadTextures() {
        super.loadTextures();
        this.texture = GameTexture.fromFile("objects/surfacecactussmall");
    }

    public LootTable getLootTable(Level level, int tileX, int tileY) {
        return new LootTable(LootItem.between("cactussapling", 1, 2));
    }

    public boolean isValid(Level level, int x, int y) {
        return !level.isShore(x, y) && level.getTileID(x, y) == TileRegistry.getTileID("sandtile");
    }
}
