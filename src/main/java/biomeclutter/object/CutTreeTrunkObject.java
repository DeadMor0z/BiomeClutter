package biomeclutter.object;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.gameObject.TreeObject;
import necesse.level.maps.Level;

import java.awt.*;

public class CutTreeTrunkObject extends SmallSingleRandomObject {
    protected TreeObject type;
    protected String textureName;

    public CutTreeTrunkObject(TreeObject type, String textureName, Color mapColor) {
        super(new Rectangle(4, 16, 24, 12));
        this.type = type;
        this.textureName = textureName;
        this.mapColor = mapColor;
        this.toolTier = type.toolTier;
        this.toolType = ToolType.AXE;
    }

    public GameMessage getNewLocalization() {
        return new LocalMessage("object", "cuttreetrunk", "tree", this.type.getNewLocalization());
    }

    public void loadTextures() {
        super.loadTextures();
        this.texture = GameTexture.fromFile("objects/" + this.textureName);
    }

    public LootTable getLootTable(Level level, int tileX, int tileY) {
        return this.type.logStringID != null ? new LootTable(LootItem.between(this.type.logStringID, 1, 5).splitItems(2)) : new LootTable();
    }

    public boolean isValid(Level level, int x, int y) {
        return !level.isShore(x, y);
    }
}
