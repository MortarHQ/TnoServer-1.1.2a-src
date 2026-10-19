package com.foxablazeultimate.loot;

import com.foxablazeultimate.registry.FoxAblazeUltimateLootModifiers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Arrays;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class AddWisdomCrystalLootModifier extends LootModifier {
   public static final MapCodec<AddWisdomCrystalLootModifier> CODEC = RecordCodecBuilder.mapCodec(
      inst -> inst.group(
            LootItemCondition.DIRECT_CODEC.listOf().fieldOf("conditions").forGetter(m -> Arrays.asList(m.conditions)),
            ResourceLocation.CODEC.fieldOf("item").forGetter(m -> BuiltInRegistries.ITEM.getKey(m.item))
         )
         .apply(
            inst,
            (conditions, itemId) -> new AddWisdomCrystalLootModifier(conditions.toArray(LootItemCondition[]::new), (Item)BuiltInRegistries.ITEM.get(itemId))
         )
   );
   private final Item item;

   public AddWisdomCrystalLootModifier(LootItemCondition[] conditions, Item item) {
      super(conditions);
      this.item = item;
   }

   protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
      if (this.item != null && this.item != Items.AIR) {
         generatedLoot.add(new ItemStack(this.item));
      }

      return generatedLoot;
   }

   public MapCodec<? extends IGlobalLootModifier> codec() {
      return (MapCodec<? extends IGlobalLootModifier>)FoxAblazeUltimateLootModifiers.ADD_WISDOM_CRYSTAL.get();
   }
}
