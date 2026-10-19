package io.github.manasmods.tensura_neb.neoforge.data;

import io.github.manasmods.tensura.registry.item.TensuraMaterialItems;
import io.github.manasmods.tensura.registry.item.TensuraMobDropItems;
import io.github.manasmods.tensura_neb.registry.NebEntityTypes;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

public class NebEntityLootProvider extends EntityLootSubProvider {
   public NebEntityLootProvider(Provider lookup) {
      super(FeatureFlags.REGISTRY.allFlags(), lookup);
   }

   public void generate() {
      this.add(
         (EntityType)NebEntityTypes.RIMURU_OGRE_FIGHT.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(LootItem.lootTableItem((ItemLike)TensuraMobDropItems.SLIME_CORE.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
            )
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TensuraMobDropItems.SLIME_CHUNK.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(10.0F, 20.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TensuraMaterialItems.HIPOKUTE_FLOWER.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F)))
                  )
            )
      );
      this.add((EntityType)NebEntityTypes.RIMURU_CLONE.get(), LootTable.lootTable());
      this.add((EntityType)NebEntityTypes.CARRION.get(), LootTable.lootTable());
      this.add(
         (EntityType)NebEntityTypes.LUMINOUS_VALENTINE.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TensuraMobDropItems.ZANE_BLOOD.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                  )
            )
      );
      this.add((EntityType)NebEntityTypes.VELDORA.get(), LootTable.lootTable());
   }

   @NotNull
   protected Stream<EntityType<?>> getKnownEntityTypes() {
      return BuiltInRegistries.ENTITY_TYPE.stream().filter(type -> BuiltInRegistries.ENTITY_TYPE.getKey(type).getNamespace().equals("tensura_neb"));
   }
}
