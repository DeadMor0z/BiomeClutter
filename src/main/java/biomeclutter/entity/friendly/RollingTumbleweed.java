package biomeclutter.entity.friendly;

import biomeclutter.ModResources;
import biomeclutter.entity.ai.leave.CollisionDirectionalAINode;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.friendly.critters.CritterMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.lootTable.LootTable;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class RollingTumbleweed extends CritterMob {

    public RollingTumbleweed() {
        super();
        this.setSpeed(30.0F);
        this.setFriction(1F);
        this.collision = new Rectangle(-10, -7, 20, 14);
        this.hitBox = new Rectangle(-14, -12, 28, 24);
        this.selectBox = new Rectangle(-15, -32, 30, 40);
    }

    @Override
    public GameMessage getLocalization() {
        return new LocalMessage("mobs", "rollingtumbleweed");
    }

    @Override
    protected void playDeathSound() {
        SoundManager.playSound(GameResources.crack, SoundEffect.effect(this).volume(1.5F));
    }

    @Override
    public void serverTick() {
        super.serverTick();
        this.huntJob = null;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        for(int i = 0; i < 4; ++i) {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), ModResources.Textures.rollingtumbleweed, i, 2, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new CollisionDirectionalAINode<>(new GameDamage(20F), 0, 2, CollisionDirectionalAINode.Direction.RIGHT));
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 26;
        boolean inLiquid = this.inLiquid(x, y);
        int spriteX;
        if (inLiquid) {
            spriteX = GameUtils.getAnim(this.getWorldEntity().getTime(), 2, 1000);
        } else {
            spriteX = GameUtils.getAnim(this.getWorldEntity().getTime(), 7, 700);
        }

        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        final DrawOptions options = ModResources.Textures.rollingtumbleweed.initDraw().sprite(spriteX, inLiquid ? 1 : 0, 32).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
        if (!inLiquid) {
            TextureDrawOptions shadow = ModResources.Textures.rollingtumbleweed_shadow.initDraw().sprite(spriteX, 0, 32).light(light).pos(drawX, drawY);
            tileList.add((tm) -> shadow.draw());
        }

    }

    public LootTable getLootTable() {
        return new LootTable();
    }
}
