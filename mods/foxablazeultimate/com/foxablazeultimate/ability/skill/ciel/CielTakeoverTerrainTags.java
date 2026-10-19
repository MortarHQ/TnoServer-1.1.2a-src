package com.foxablazeultimate.ability.skill.ciel;

import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class CielTakeoverTerrainTags {
   public static final TagKey<Item> PILLAR_DIRT = tag("ciel_takeover_pillar_dirt");
   public static final TagKey<Item> PILLAR_STONE = tag("ciel_takeover_pillar_stone");
   public static final TagKey<Item> PILLAR_WOOD = tag("ciel_takeover_pillar_wood");
   public static final List<TagKey<Item>> PILLAR_PRIORITY = List.of(PILLAR_DIRT, PILLAR_STONE, PILLAR_WOOD);

   private CielTakeoverTerrainTags() {
   }

   private static TagKey<Item> tag(String path) {
      return ItemTags.create(ResourceLocation.fromNamespaceAndPath("foxablazeultimate", path));
   }
}
