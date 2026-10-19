package artifacts.neoforge.data;

import artifacts.config.value.Value;
import artifacts.loot.ConfigValueChance;
import artifacts.loot.IsAprilFools;
import artifacts.registry.ModDataComponents;
import artifacts.registry.ModItems;
import artifacts.registry.ModLootTables;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class EntityEquipment {
   private final LootTables lootTables;
   private final Set<EntityType<?>> entityTypes = new HashSet<>();

   public EntityEquipment(LootTables lootTables) {
      this.lootTables = lootTables;
   }

   public void addLootTables() {
      this.entityTypes.clear();
      this.addItems(EntityType.ZOMBIE, (Item)ModItems.COWBOY_HAT.value(), (Item)ModItems.BUNNY_HOPPERS.value(), (Item)ModItems.SCARF_OF_INVISIBILITY.value());
      this.addItems(EntityType.HUSK, (Item)ModItems.VAMPIRIC_GLOVE.value(), (Item)ModItems.THORN_PENDANT.value());
      this.addItems(EntityType.DROWNED, (Item)ModItems.SNORKEL.value(), (Item)ModItems.FLIPPERS.value());
      this.addEquipment(
         EntityType.SKELETON,
         LootPool.lootPool()
            .add(LootTables.item((Item)ModItems.NIGHT_VISION_GOGGLES.value(), 1))
            .add(LootTables.drinkingHat(1))
            .add(LootTables.item((Item)ModItems.FLAME_PENDANT.value(), 1))
      );
      this.addItems(EntityType.STRAY, (Item)ModItems.SNOWSHOES.value(), (Item)ModItems.STEADFAST_SPIKES.value());
      this.addItems(EntityType.WITHER_SKELETON, (Item)ModItems.FIRE_GAUNTLET.value(), (Item)ModItems.ANTIDOTE_VESSEL.value());
      this.addItems(EntityType.PIGLIN, (Item)ModItems.GOLDEN_HOOK.value(), (Item)ModItems.UNIVERSAL_ATTRACTOR.value(), (Item)ModItems.OBSIDIAN_SKULL.value());
      this.addItems(
         EntityType.ZOMBIFIED_PIGLIN, (Item)ModItems.GOLDEN_HOOK.value(), (Item)ModItems.UNIVERSAL_ATTRACTOR.value(), (Item)ModItems.OBSIDIAN_SKULL.value()
      );
      this.addItems(EntityType.PIGLIN_BRUTE, (Item)ModItems.ONION_RING.value(), (Item)ModItems.STRIDER_SHOES.value());
      this.lootTables
         .addLootTable(ModLootTables.entityEquipmentLootTable(EntityType.GHAST).location().getPath(), this::ghastLoot, LootContextParamSets.ALL_PARAMS);
      this.entityTypes.add(EntityType.GHAST);
      if (!this.entityTypes.equals(ModLootTables.ENTITY_EQUIPMENT.keySet())) {
         throw new IllegalStateException(Sets.symmetricDifference(this.entityTypes, ModLootTables.ENTITY_EQUIPMENT.keySet()).toString());
      }
   }

   private Builder ghastLoot(Provider registries) {
      net.minecraft.world.level.storage.loot.LootPool.Builder pool = LootPool.lootPool();

      for (Item item : List.of(
         (Item)ModItems.ANGLERS_HAT.value(),
         (Item)ModItems.COWBOY_HAT.value(),
         (Item)ModItems.VILLAGER_HAT.value(),
         (Item)ModItems.NIGHT_VISION_GOGGLES.value(),
         (Item)ModItems.SNORKEL.value()
      )) {
         pool.add(LootTables.item(item, 1));
      }

      pool.apply(
            new net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction.Builder()
               .withEnchantment(registries.holderOrThrow(Enchantments.VANISHING_CURSE), ConstantValue.exactly(1.0F))
         )
         .apply(SetComponentsFunction.setComponent(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, false));
      return LootTable.lootTable().withPool(pool.when(IsAprilFools.builder()));
   }

   public void addItems(EntityType<?> entityType, Item... items) {
      if (!ModLootTables.ENTITY_EQUIPMENT.containsKey(entityType)) {
         throw new IllegalArgumentException("Missing entity equipment entity: %s".formatted(BuiltInRegistries.ENTITY_TYPE.getKey(entityType)));
      }

      net.minecraft.world.level.storage.loot.LootPool.Builder pool = LootPool.lootPool();

      for (Item item : items) {
         if (item == ModItems.SCARF_OF_INVISIBILITY.value()) {
            pool.add(LootTables.item(item, 1).apply(SetComponentsFunction.setComponent(ModDataComponents.HIDE_WHEN_INVISIBLE.get(), Value.of(false))));
         } else {
            pool.add(LootTables.item(item, 1));
         }
      }

      this.addEquipment(entityType, pool);
   }

   public void addEquipment(EntityType<?> entityType, net.minecraft.world.level.storage.loot.LootPool.Builder pool) {
      this.entityTypes.add(entityType);
      Builder builder = LootTable.lootTable();
      builder.withPool(pool.when(ConfigValueChance.entityEquipmentChance()));
      this.lootTables
         .addLootTable(ModLootTables.entityEquipmentLootTable(entityType).location().getPath(), provider -> builder, LootContextParamSets.ALL_PARAMS);
   }
}
