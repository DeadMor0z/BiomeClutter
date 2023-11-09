package biomeclutter.entity.ai.leave;

import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobHitCooldowns;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.AINode;
import necesse.entity.mobs.ai.behaviourTree.AINodeResult;
import necesse.entity.mobs.ai.behaviourTree.Blackboard;
import necesse.entity.mobs.ai.behaviourTree.decorators.MoveTaskAINode;
import necesse.entity.mobs.ai.behaviourTree.util.TargetFinderDistance;

import java.awt.*;

public class CollisionDirectionalAINode<T extends Mob> extends MoveTaskAINode<T> {

    public GameDamage damage;
    public int knockback;
    public long nextPathFindTime;
    public int searchDistance;
    public MobHitCooldowns hitCooldowns;
    public Direction direction;

    public CollisionDirectionalAINode(GameDamage damage, int knockback, int searchDistance, Direction direction) {
        this.hitCooldowns = new MobHitCooldowns();
        this.damage = damage;
        this.knockback = knockback;
        this.searchDistance = searchDistance;
        this.direction = direction;
    }

    @Override
    protected void onRootSet(AINode<T> root, T mob, Blackboard<T> blackboard) {
        if (this.searchDistance == 0) {
            this.searchDistance = 2;
        }

        blackboard.onEvent("resetPathTime", (e) -> {
            this.nextPathFindTime = 0L;
        });
    }

    @Override
    public void init(T t, Blackboard<T> blackboard) {
    }

    @Override
    public AINodeResult tick(T mob, Blackboard<T> blackboard) {
        new TargetFinderDistance<>(searchDistance).streamMobsAndPlayersInRange(mob.getPositionPoint(), mob).filter((m) -> m != null && m != mob)
                .forEach((entry) -> {
                    if (this.damage != null && entry.getCollision().intersects(mob.getCollision()) && this.hitCooldowns.canHit(entry) && entry.canBeHit(mob)) {
                        this.hitCooldowns.startCooldown(entry);
                        Mob from = mob;
                        PlayerMob serverPlayer = mob.getFollowingServerPlayer();
                        if (serverPlayer != null) {
                            from = serverPlayer;
                        }

                        entry.isServerHit(this.damage, entry.x - from.x, entry.y - from.y, (float)this.knockback, mob);
                    }
                });
        return super.tick(mob, blackboard);
    }

    @Override
    public AINodeResult tickNode(T mob, Blackboard<T> blackboard) {
        if (mob.getLevel().isLiquidTile(mob.getTileX(), mob.getTileY()) || mob.getLevel().isSolidTile(mob.getTileX() + 1, mob.getTileY())) {
            mob.setHealth(0);
            return null;
        }
        if (!blackboard.mover.isMoving() || !blackboard.mover.isCurrentlyMovingFor(this)) {
            Point nextPoint = findNewPosition(mob, mob.getTileX(), mob.getTileY());
            if (nextPoint != null) {

                this.moveToTileTask(nextPoint.x, nextPoint.y, null, (path) -> {
                    path.moveIfWithin(-1, -1, null);
                    return AINodeResult.SUCCESS;
                });
            }
        }
        return null;
    }

    private Point findNewPosition(Mob mob, int tileX, int tileY) {
        switch (this.direction) {
            case UP: return findUpPosition(mob, tileX, tileY, 300 - tileY);
            case DOWN: return findDownPosition(mob, tileX, tileY);
            case LEFT: return findLeftPosition(mob, tileX, tileY);
            default: return findRightPosition(mob, tileX, tileY, 300 - tileX);
        }
    }

    private Point findRightPosition(Mob mob, int tileX, int tileY, int searchRadius) {
        for(int x = 0; x <= searchRadius; ++x) {
            Point lp = new Point(tileX + x, tileY);
            if ((mob.getLevel().isSolidTile(lp.x, lp.y) || mob.getLevel().isLiquidTile(lp.x, lp.y)) && lp.x > tileX) {
                return new Point(lp.x, lp.y);
            }
        }
        return null;
    }

    private Point findLeftPosition(Mob mob, int tileX, int tileY) {
        for(int x = 0; x <= tileX; ++x) {
            Point lp = new Point(tileX - x, tileY);
            if ((mob.getLevel().isSolidTile(lp.x, lp.y) || mob.getLevel().isLiquidTile(lp.x, lp.y)) && lp.x > tileX) {
                return new Point(lp.x, lp.y);
            }
        }
        return null;
    }

    private Point findUpPosition(Mob mob, int tileX, int tileY, int searchRadius) {
        for(int y = 0; y <= searchRadius; ++y) {
            Point lp = new Point(tileX + y, tileY + y);
            if ((mob.getLevel().isSolidTile(lp.x, lp.y) || mob.getLevel().isLiquidTile(lp.x, lp.y)) && lp.x > tileX) {
                return new Point(lp.x, lp.y);
            }
        }
        return null;
    }

    private Point findDownPosition(Mob mob, int tileX, int tileY) {
        for(int y = 0; y <= tileY; ++y) {
            Point lp = new Point(tileX + y, tileY - y);
            if ((mob.getLevel().isSolidTile(lp.x, lp.y) || mob.getLevel().isLiquidTile(lp.x, lp.y)) && lp.x > tileX) {
                return new Point(lp.x, lp.y);
            }
        }
        return null;
    }

    public enum Direction {
        UP,DOWN,LEFT,RIGHT;

        Direction() {}
    }
}
