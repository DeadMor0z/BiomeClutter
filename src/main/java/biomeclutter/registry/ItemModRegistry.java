package biomeclutter.registry;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.ItemRegistry;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.item.Item;
import necesse.inventory.item.placeableItem.consumableItem.food.FoodConsumableItem;
import necesse.level.maps.levelData.settlementData.settler.Settler;

public class ItemModRegistry {

    public static void registerItems() {
        ItemRegistry.registerItem("sweetberry", (new FoodConsumableItem(100, Item.Rarity.NORMAL, Settler.FOOD_SIMPLE, 10, 240, new ModifierValue<>(BuffModifiers.ATTACK_SPEED, 0.02F))).addGlobalIngredient("anycompostable", "anyfruit").setItemCategory("consumable", "rawfood"), 4.0F, true);
    }
}
