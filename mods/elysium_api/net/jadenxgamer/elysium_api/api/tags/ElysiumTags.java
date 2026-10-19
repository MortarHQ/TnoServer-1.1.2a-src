package net.jadenxgamer.elysium_api.api.tags;

import net.jadenxgamer.elysium_api.ElysiumAPI;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;

public class ElysiumTags {
   public static class Blocks {
      public static final TagKey<Block> NON_SOLID_FIRE_SUPPORT = createBlockTag("non_solid_fire_support");
      public static final TagKey<Block> FUNGUS_PLANTABLE_ON = createBlockTag("fungus_plantable_on");
      public static final TagKey<Block> ROOTS_PLANTABLE_ON = createBlockTag("roots_plantable_on");
      public static final TagKey<Block> NETHER_SPROUTS_PLANTABLE_ON = createBlockTag("nether_sprouts_plantable_on");
      public static final TagKey<Block> NETHER_WART_PLANTABLE_ON = createBlockTag("nether_wart_plantable_on");
      public static final TagKey<Block> MULTILAYER_JIGSAW_NON_SOLID_VALID = createBlockTag("multilayer_jigsaw_non_solid_valid");

      private static TagKey<Block> createBlockTag(String name) {
         return TagKey.create(Registries.BLOCK, ElysiumAPI.elysiumPath(name));
      }
   }

   public static class DamageTypes {
      public static final TagKey<DamageType> CANT_DAMAGE_ARMOR = createDamageTypeTag("cant_damage_armor");

      private static TagKey<DamageType> createDamageTypeTag(String name) {
         return TagKey.create(Registries.DAMAGE_TYPE, ElysiumAPI.elysiumPath(name));
      }
   }

   public static class Enchantments {
      public static final TagKey<Enchantment> DISABLED = createEnchantmentTag("disabled");

      private static TagKey<Enchantment> createEnchantmentTag(String name) {
         return TagKey.create(Registries.ENCHANTMENT, ElysiumAPI.elysiumPath(name));
      }
   }

   public static class EntityTypes {
      public static final TagKey<EntityType<?>> PIGLINS_AFRAID_OF = createEntityTypeTag("piglins_afraid_of");

      private static TagKey<EntityType<?>> createEntityTypeTag(String name) {
         return TagKey.create(Registries.ENTITY_TYPE, ElysiumAPI.elysiumPath(name));
      }
   }
}
