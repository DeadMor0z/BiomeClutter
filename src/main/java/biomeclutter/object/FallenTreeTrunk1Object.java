package biomeclutter.object;

import necesse.engine.tickManager.TickManager;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.gameObject.*;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import necesse.level.maps.multiTile.MultiTile;
import necesse.level.maps.multiTile.StaticMultiTile;

import java.awt.*;
import java.util.List;

public class FallenTreeTrunk1Object extends FallenTreeTrunkObject {

    protected int counterIDMiddle;
    protected int counterIDRight;

    protected FallenTreeTrunk1Object(TreeObject type, String textureName, Color mapColor) {
        super(new Rectangle(14, 14, 18, 10), type, textureName, mapColor);
    }

    protected void setCounterIDs(int id1, int id2, int id3) {
        this.counterIDMiddle = id2;
        this.counterIDRight = id3;
    }

    public MultiTile getMultiTile(int rotation) {
        return new StaticMultiTile(0, 0, 3, 1, true, this.getID(), this.counterIDMiddle, this.counterIDRight);
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
}
