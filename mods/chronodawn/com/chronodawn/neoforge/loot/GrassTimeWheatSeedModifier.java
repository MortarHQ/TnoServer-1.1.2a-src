package com.chronodawn.neoforge.loot;

import com.chronodawn.registry.ModItems;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class GrassTimeWheatSeedModifier extends LootModifier {
   public static final MapCodec<GrassTimeWheatSeedModifier> CODEC = RecordCodecBuilder.mapCodec(
      inst -> codecStart(inst).apply(inst, GrassTimeWheatSeedModifier::new)
   );
   private static final ResourceKey<Level> CHRONO_DAWN_DIMENSION = ResourceKey.create(
      Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("chronodawn", "chronodawn")
   );

   public GrassTimeWheatSeedModifier(LootItemCondition[] conditions) {
      super(conditions);
   }

   protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
      Level level = context.getLevel();
      if (level.dimension() != CHRONO_DAWN_DIMENSION) {
         return generatedLoot;
      }

      ObjectArrayList<ItemStack> modifiedLoot = new ObjectArrayList();
      ObjectListIterator var5 = generatedLoot.iterator();

      while (var5.hasNext()) {
         ItemStack stack = (ItemStack)var5.next();
         if (stack.is(Items.WHEAT_SEEDS)) {
            ItemStack timeWheatSeeds = new ItemStack((ItemLike)ModItems.TIME_WHEAT_SEEDS.get(), stack.getCount());
            modifiedLoot.add(timeWheatSeeds);
         } else {
            modifiedLoot.add(stack);
         }
      }

      return modifiedLoot;
   }

   public MapCodec<? extends IGlobalLootModifier> codec() {
      return CODEC;
   }
}
