package biomeclutter.object;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.util.GameRandom;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.lootTable.LootTable;
import necesse.level.gameObject.GameObject;
import necesse.level.gameObject.TreeObject;
import necesse.level.maps.Level;

import java.awt.*;

public class FallenTreeTrunkObject extends GameObject {

    protected TreeObject type;
    protected String textureName;
    public GameTexture texture;
    protected final GameRandom drawRandom;

    protected FallenTreeTrunkObject(Rectangle collision, TreeObject type, String textureName, Color mapColor) {
        super(collision);
        this.type = type;
        this.textureName = textureName;
        this.mapColor = mapColor;
        this.toolType = ToolType.AXE;
        this.toolTier = type.toolTier;
        this.displayMapTooltip = true;
        this.drawDamage = false;
        this.isLightTransparent = true;
        this.drawRandom = new GameRandom();
    }

    public GameMessage getNewLocalization() {
        return new LocalMessage("object", "fallentreetrunk", "tree", this.type.getNewLocalization());
    }

    public void loadTextures() {
        super.loadTextures();
        this.texture = GameTexture.fromFile("objects/" + this.textureName);
    }

    public int getRandomYOffset(int tileX, int tileY) {
        synchronized(this.drawRandom) {
            return (int)((this.drawRandom.seeded(this.getTileSeed(tileX, tileY, 1)).nextFloat() * 2.0F - 1.0F) * 8.0F) - 4;
        }
    }

    protected Rectangle getCollision(Level level, int x, int y, int rotation) {
        Rectangle collision = super.getCollision(level, x, y, rotation);
        collision.y += this.getRandomYOffset(x, y);
        return collision;
    }

    public LootTable getLootTable(Level level, int tileX, int tileY) {
        return new LootTable();
    }

    public static void registerTreeBark(TreeObject type, String textureName, Color color) {
        FallenTreeTrunk1Object o1 = new FallenTreeTrunk1Object(type, textureName, color);
        FallenTreeTrunk2Object o2 = new FallenTreeTrunk2Object(type, textureName, color);
        FallenTreeTrunk3Object o3 = new FallenTreeTrunk3Object(type, textureName, color);
        int id1 = ObjectRegistry.registerObject(textureName, o1, 0.0F, true);
        int id2 = ObjectRegistry.registerObject(textureName + "m", o2, 0.0F, false);
        int id3 = ObjectRegistry.registerObject(textureName + "r", o3, 0.0F, false);
        o1.setCounterIDs(id1, id2, id3);
        o2.setCounterIDs(id1, id2, id3);
        o3.setCounterIDs(id1, id2, id3);
    }

    public static void registerTreeBark(TreeObject type, String textureName) {
        registerTreeBark(type, textureName, new Color(86, 69, 40));
    }
}
