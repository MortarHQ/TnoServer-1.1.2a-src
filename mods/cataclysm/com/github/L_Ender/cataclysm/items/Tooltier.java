package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.init.ModItems;
import java.util.Locale;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.SimpleTier;

public class Tooltier {
   public static final Tier ANCIENT_METAL = new SimpleTier(
      BlockTags.create(prefix("needs_ancient_metal_tool")),
      750,
      8.0F,
      2.0F,
      25,
      () -> Ingredient.of(new ItemLike[]{(ItemLike)ModItems.ANCIENT_METAL_INGOT.get()})
   );
   public static final Tier BLACK_STEEL = new SimpleTier(
      BlockTags.create(prefix("needs_black_steel_tool")), 750, 8.0F, 2.0F, 25, () -> Ingredient.of(new ItemLike[]{(ItemLike)ModItems.BLACK_STEEL_INGOT.get()})
   );
   public static final Tier MONSTROSITY = new SimpleTier(
      BlockTags.create(prefix("needs_monstrosity_tool")), 2800, 9.0F, 4.0F, 25, () -> Ingredient.of(new ItemLike[]{(ItemLike)ModItems.MONSTROUS_HORN.get()})
   );

   private static ResourceLocation prefix(String name) {
      return ResourceLocation.fromNamespaceAndPath("cataclysm", name.toLowerCase(Locale.ROOT));
   }
}
