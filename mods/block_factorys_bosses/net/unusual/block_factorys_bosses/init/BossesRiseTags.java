package net.unusual.block_factorys_bosses.init;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.unusual.block_factorys_bosses.BossesRise;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseTags {
   public static class Biomes {
      public static final TagKey<Biome> DRAGON_TOWER = TagKey.create(Registries.BIOME, BossesRise.prefix("dragon_tower"));
      public static final TagKey<Biome> SANDWORM_NEST = TagKey.create(Registries.BIOME, BossesRise.prefix("sandworm_nest"));
      public static final TagKey<Biome> UNDERWORLD_ARENA = TagKey.create(Registries.BIOME, BossesRise.prefix("underworld_arena"));
      public static final TagKey<Biome> YETI_HIDEOUT = TagKey.create(Registries.BIOME, BossesRise.prefix("yeti_hideout"));
      public static final TagKey<Biome> KRAKEN_SHIP = TagKey.create(Registries.BIOME, BossesRise.prefix("kraken_ship"));
   }

   public static class Blocks {
      public static final TagKey<Block> UNDERWORLD_ARENA_VALID_SPAWN = BlockTags.create(BossesRise.prefix("underworld_arena_valid_spawn"));
      public static final TagKey<Block> BONE_REMAINS = BlockTags.create(BossesRise.prefix("bone_remains"));
      public static final TagKey<Block> SHIP_PLANKS = BlockTags.create(BossesRise.prefix("ship_planks"));
      public static final TagKey<Block> PROTECTION_EXEMPT = BlockTags.create(BossesRise.prefix("protection_exempt"));
   }

   public static class EntityTypes {
      public static final TagKey<EntityType<?>> BF_BOSS = TagKey.create(Registries.ENTITY_TYPE, BossesRise.prefix("bf_boss"));
      public static final TagKey<EntityType<?>> PIRATE = TagKey.create(Registries.ENTITY_TYPE, BossesRise.prefix("pirate"));
   }

   public static class Items {
      public static final TagKey<Item> BONE_REMAINS = ItemTags.create(BossesRise.prefix("bone_remains"));
      public static final TagKey<Item> GLOWING_LOOT = ItemTags.create(BossesRise.prefix("glowing_loot"));
      public static final TagKey<Item> SHIP_PLANKS = ItemTags.create(BossesRise.prefix("ship_planks"));
   }
}
