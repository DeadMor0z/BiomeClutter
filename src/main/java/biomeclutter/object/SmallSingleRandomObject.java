package biomeclutter.object;

import necesse.engine.network.server.ServerClient;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.shader.WaveShader;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.gameObject.GameObject;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class SmallSingleRandomObject extends GameObject {
    public boolean isWeaveApplicable;
    public GameTexture texture;
    protected final GameRandom drawRandom;
    public int weaveTime;
    public float weaveAmount;
    public float weaveHeight;
    public float waveHeightOffset;

    public SmallSingleRandomObject(Rectangle collision, int weaveTime, float weaveAmount, float weaveHeight, float waveHeightOffset) {
        super(collision);
        this.displayMapTooltip = true;
        this.drawDamage = false;
        this.isLightTransparent = true;
        this.drawRandom = new GameRandom();
        this.isWeaveApplicable = weaveTime > 0;
        this.weaveTime = weaveTime;
        this.weaveAmount = weaveAmount;
        this.weaveHeight = weaveHeight;
        this.waveHeightOffset = waveHeightOffset;
    }

    public SmallSingleRandomObject(Rectangle collision) {
        this(collision, 0, 0, 0, 0);
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
        final WaveShader.WaveState waveState = GameResources.waveShader.setupGrassWave(level, tileX, tileY, this.weaveTime, this.weaveAmount, this.weaveHeight, 2, this.waveHeightOffset, this.drawRandom, this.getTileSeed(tileX, tileY, 0));
        synchronized(this.drawRandom) {
            sprite = this.drawRandom.seeded(this.getTileSeed(tileX, tileY)).nextInt(this.texture.getWidth() / 32);
        }

        drawY += randomYOffset;
        final TextureDrawOptions options = this.texture.initDraw().sprite(sprite, 0, 32, this.texture.getHeight()).light(light).pos(drawX, drawY - this.texture.getHeight() + 32);
        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            public int getSortY() {
                return 16 + randomYOffset;
            }

            public void draw(TickManager tickManager) {
                if (waveState != null && isWeaveApplicable) {
                    waveState.start();
                }

                options.draw();
                if (waveState != null && isWeaveApplicable) {
                    waveState.end();
                }
            }
        });
    }

    public boolean onDamaged(Level level, int x, int y, int damage, ServerClient client, boolean showEffect, int mouseX, int mouseY) {
        boolean out = super.onDamaged(level, x, y, damage, client, showEffect, mouseX, mouseY);
        if (showEffect) {
            level.makeGrassWeave(x, y, this.weaveTime, true);
        }

        return out;
    }

    public LootTable getLootTable(Level level, int tileX, int tileY) {
        return new LootTable();
    }
}
