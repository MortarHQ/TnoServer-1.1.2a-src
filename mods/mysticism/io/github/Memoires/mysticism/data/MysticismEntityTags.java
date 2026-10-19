package io.github.Memoires.mysticism.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class MysticismEntityTags {
   public static TagKey<EntityType<?>> CANNOT_BE_SHADOWS = modTag("cannot_be_shadows");
   public static TagKey<EntityType<?>> IS_WARRIOR = modTag("is_warrior");
   public static TagKey<EntityType<?>> IS_THIEF = modTag("is_thief");
   public static TagKey<EntityType<?>> IS_BRAWLER = modTag("is_brawler");
   public static TagKey<EntityType<?>> IS_KNIGHT = modTag("is_knight");
   public static TagKey<EntityType<?>> IS_MAGE = modTag("is_mage");
   public static TagKey<EntityType<?>> IS_ARCHER = modTag("is_archer");
   public static TagKey<EntityType<?>> IS_BEAST = modTag("is_beast");
   public static TagKey<EntityType<?>> IS_BIRD = modTag("is_bird");
   public static TagKey<EntityType<?>> IS_DRAGON = modTag("is_dragon");
   public static TagKey<EntityType<?>> IS_INSECT = modTag("is_insect");
   public static TagKey<EntityType<?>> IS_SLIME = modTag("is_slime");
   public static TagKey<EntityType<?>> IS_SHADOW = modTag("is_shadow");

   static TagKey<EntityType<?>> modTag(String name) {
      return create(ResourceLocation.fromNamespaceAndPath("mysticism", name));
   }

   static TagKey<EntityType<?>> create(ResourceLocation name) {
      return TagKey.create(Registries.ENTITY_TYPE, name);
   }
}
