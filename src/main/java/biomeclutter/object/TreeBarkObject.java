package biomeclutter.object;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.gameObject.*;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import necesse.level.maps.multiTile.MultiTile;
import necesse.level.maps.multiTile.StaticMultiTile;

import java.awt.*;
import java.util.List;

public class TreeBarkObject extends GameObject {
    protected TreeObject type;
    protected int counterIDMiddle;
    protected int counterIDRight;
    protected String textureName;
    public GameTexture texture;
    protected final GameRandom drawRandom;

    protected TreeBarkObject(TreeObject type, String textureName, Color mapColor) {
        super(new Rectangle(14, 14, 18, 10));
        this.type = type;
        this.textureName = textureName;
        this.mapColor = mapColor;
        this.toolType = ToolType.AXE;
        this.toolTier = type.toolTier;
        this.displayMapTooltip = true;
        this.drawDmg = false;
        this.isLightTransparent = true;
        this.drawRandom = new GameRandom();
    }

    protected void setCounterIDs(int id1, int id2, int id3) {
        this.counterIDMiddle = id2;
        this.counterIDRight = id3;
    }

    public GameMessage getNewLocalization() {
        return new LocalMessage("object", "treebark", "tree", this.type.getNewLocalization());
    }

    public MultiTile getMultiTile(int rotation) {
        return new StaticMultiTile(0, 0, 3, 1, true, this.getID(), this.counterIDMiddle, this.counterIDRight);
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

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        final int randomYOffset = this.getRandomYOffset(tileX, tileY);
        int sprite;
        synchronized(this.drawRandom) {
            sprite = this.drawRandom.seeded(this.getTileSeed(tileX, tileY)).nextInt(this.texture.getHeight() / 32);
        }

        drawY += randomYOffset;
        final TextureDrawOptions options = this.texture.initDraw().sprite(0, sprite, 32, 32).light(light).pos(drawX, drawY - 32 + 32);
        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            public int getSortY() {
                return 16 + randomYOffset;
            }

            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }

    public LootTable getLootTable(Level level, int tileX, int tileY) {
        return this.type.logStringID != null ? new LootTable(LootItem.between(this.type.logStringID, 1, 5).splitItems(2)) : new LootTable();
    }

    public static void registerTreeBark(TreeObject type, String textureName, Color color) {
        TreeBarkObject o1 = new TreeBarkObject(type, textureName, color);
        TreeBark2Object o2 = new TreeBark2Object(type, textureName, color);
        TreeBark3Object o3 = new TreeBark3Object(type, textureName, color);
        int id1 = ObjectRegistry.registerObject(textureName, o1, 0.0F, true);
        int id2 = ObjectRegistry.registerObject(textureName + "m", o2, 0.0F, false);
        int id3 = ObjectRegistry.registerObject(textureName + "r", o3, 0.0F, false);
        o1.setCounterIDs(id1, id2, id3);
        o2.setCounterIDs(id1, id2, id3);
        o3.setCounterIDs(id1, id2, id3);
    }
}
