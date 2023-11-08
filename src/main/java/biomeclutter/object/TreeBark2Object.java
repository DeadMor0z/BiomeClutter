package biomeclutter.object;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
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
import necesse.level.gameObject.GameObject;
import necesse.level.gameObject.RockObject;
import necesse.level.gameObject.TreeObject;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import necesse.level.maps.multiTile.MultiTile;
import necesse.level.maps.multiTile.StaticMultiTile;

import java.awt.*;
import java.util.List;

public class TreeBark2Object extends GameObject {

    protected TreeObject type;
    protected int counterIDLeft;
    protected int counterIDRight;
    protected String textureName;
    public GameTexture texture;
    protected final GameRandom drawRandom;

    public TreeBark2Object(TreeObject type, String textureName, Color mapColor) {
        super(new Rectangle(0, 14, 32, 10));
        this.type = type;
        this.textureName = textureName;
        this.mapColor = mapColor;
        this.toolType = ToolType.AXE;
        this.toolTier = type.toolTier;
        this.drawDmg = false;
        this.isLightTransparent = true;
        this.drawRandom = new GameRandom();
    }

    protected void setCounterIDs(int id1, int id2, int id3) {
        this.counterIDLeft = id1;
        this.counterIDRight = id3;
    }

    public GameMessage getNewLocalization() {
        return new LocalMessage("object", "treebark", "tree", this.type.getNewLocalization());
    }

    public MultiTile getMultiTile(int rotation) {
        return new StaticMultiTile(1, 0, 3, 1, false, this.counterIDLeft, this.getID(), this.counterIDRight);
    }

    public void loadTextures() {
        super.loadTextures();
        this.texture = GameTexture.fromFile("objects/" + this.textureName);
    }

    public int getRandomYOffset(int tileX, int tileY) {
        synchronized(this.drawRandom) {
            return (int)((this.drawRandom.seeded(this.getTileSeed(tileX - 1, tileY, 1)).nextFloat() * 2.0F - 1.0F) * 8.0F) - 4;
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
            sprite = this.drawRandom.seeded(this.getTileSeed(tileX - 1, tileY)).nextInt(this.texture.getHeight() / 32);
        }

        drawY += randomYOffset;
        final TextureDrawOptions options = this.texture.initDraw().sprite( 1, sprite, 32, 32).light(light).pos(drawX, drawY);
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
        return new LootTable();
    }

}
