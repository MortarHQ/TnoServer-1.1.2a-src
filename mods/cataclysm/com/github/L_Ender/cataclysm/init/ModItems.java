package com.github.L_Ender.cataclysm.init;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.client.CustomRarity.CMRarity;
import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.config.ConfigHolder;
import com.github.L_Ender.cataclysm.items.Ancient_Spear;
import com.github.L_Ender.cataclysm.items.Armortier;
import com.github.L_Ender.cataclysm.items.Astrape;
import com.github.L_Ender.cataclysm.items.Athame;
import com.github.L_Ender.cataclysm.items.Azure_sea_Shield;
import com.github.L_Ender.cataclysm.items.Black_Steel_Targe;
import com.github.L_Ender.cataclysm.items.Blessed_Amethyst_Crab_Meat;
import com.github.L_Ender.cataclysm.items.Bloom_Stone_Pauldrons;
import com.github.L_Ender.cataclysm.items.Bone_Reptile_Armor;
import com.github.L_Ender.cataclysm.items.Brontes;
import com.github.L_Ender.cataclysm.items.Bulwark_of_the_flame;
import com.github.L_Ender.cataclysm.items.CataclysmSkullItem;
import com.github.L_Ender.cataclysm.items.Cataclysm_Armor;
import com.github.L_Ender.cataclysm.items.Cataclysm_Weapon;
import com.github.L_Ender.cataclysm.items.Ceraunus;
import com.github.L_Ender.cataclysm.items.Coral_Bardiche;
import com.github.L_Ender.cataclysm.items.Coral_Spear;
import com.github.L_Ender.cataclysm.items.Cursed_bow;
import com.github.L_Ender.cataclysm.items.Cursium_Armor;
import com.github.L_Ender.cataclysm.items.Cursium_ChestPlate;
import com.github.L_Ender.cataclysm.items.DungeonEyeItem;
import com.github.L_Ender.cataclysm.items.Gauntlet_of_Bulwark;
import com.github.L_Ender.cataclysm.items.Gauntlet_of_Guard;
import com.github.L_Ender.cataclysm.items.Gauntlet_of_Maelstrom;
import com.github.L_Ender.cataclysm.items.Ignitium_Armor;
import com.github.L_Ender.cataclysm.items.Ignitium_Elytra_ChestPlate;
import com.github.L_Ender.cataclysm.items.Infernal_forge;
import com.github.L_Ender.cataclysm.items.ItemInventoryOnly;
import com.github.L_Ender.cataclysm.items.Laser_Gatling;
import com.github.L_Ender.cataclysm.items.Meat_Shredder;
import com.github.L_Ender.cataclysm.items.ModFishBucket;
import com.github.L_Ender.cataclysm.items.ModJukeboxSongs;
import com.github.L_Ender.cataclysm.items.ModTemplate;
import com.github.L_Ender.cataclysm.items.ModernRemantBucket;
import com.github.L_Ender.cataclysm.items.Monstrous_Helm;
import com.github.L_Ender.cataclysm.items.Necklace_Of_The_Desert;
import com.github.L_Ender.cataclysm.items.Netherite_Effigy;
import com.github.L_Ender.cataclysm.items.Remnant_Skull;
import com.github.L_Ender.cataclysm.items.Sandstorm_In_A_Bottle;
import com.github.L_Ender.cataclysm.items.Soul_Render;
import com.github.L_Ender.cataclysm.items.Strange_Key;
import com.github.L_Ender.cataclysm.items.The_Annihilator;
import com.github.L_Ender.cataclysm.items.The_Immolator;
import com.github.L_Ender.cataclysm.items.The_Incinerator;
import com.github.L_Ender.cataclysm.items.Tidal_Claws;
import com.github.L_Ender.cataclysm.items.Tooltier;
import com.github.L_Ender.cataclysm.items.Void_Assault_SHoulder_Weapon;
import com.github.L_Ender.cataclysm.items.Void_Scatter_Arrow_Item;
import com.github.L_Ender.cataclysm.items.Void_core;
import com.github.L_Ender.cataclysm.items.Void_forge;
import com.github.L_Ender.cataclysm.items.Wither_Assault_SHoulder_Weapon;
import com.github.L_Ender.cataclysm.items.Wrath_of_the_desert;
import com.github.L_Ender.cataclysm.items.CuriosItem.AttributeContainer;
import com.github.L_Ender.cataclysm.items.CuriosItem.Blazing_Grips;
import com.github.L_Ender.cataclysm.items.CuriosItem.Chitin_Claw;
import com.github.L_Ender.cataclysm.items.CuriosItem.CuriosItem;
import com.github.L_Ender.cataclysm.items.CuriosItem.Sticky_Gloves;
import com.github.L_Ender.cataclysm.items.CuriosItem.Sturdy_Boots;
import com.github.L_Ender.cataclysm.items.CuriosItem.Unbreakable_Skull;
import com.github.L_Ender.cataclysm.util.AttributeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public class ModItems {
   public static final Items ITEMS = DeferredRegister.createItems("cataclysm");
   public static final DeferredItem<BlockItem> ENDERITE_BLOCK = ITEMS.register(
      "enderite_block", () -> new BlockItem((Block)ModBlocks.ENDERRITE_BLOCK.get(), new Properties().fireResistant().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<BlockItem> WITHERITE_BLCOK = ITEMS.register(
      "witherite_block", () -> new BlockItem((Block)ModBlocks.WITHERITE_BLOCK.get(), new Properties().fireResistant().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<BlockItem> IGNITIUM_BLOCK = ITEMS.register(
      "ignitium_block", () -> new BlockItem((Block)ModBlocks.IGNITIUM_BLOCK.get(), new Properties().fireResistant().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<BlockItem> ANCIENT_METAL_BLOCK = ITEMS.register(
      "ancient_metal_block", () -> new BlockItem((Block)ModBlocks.ANCIENT_METAL_BLOCK.get(), new Properties().fireResistant().rarity(Rarity.UNCOMMON))
   );
   public static final DeferredItem<BlockItem> CURSIUM_BLOCK = ITEMS.register(
      "cursium_block", () -> new BlockItem((Block)ModBlocks.CURSIUM_BLOCK.get(), new Properties().fireResistant().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<BlockItem> DUNGEON_BLOCK = ITEMS.register(
      "dungeon_block", () -> new BlockItem((Block)ModBlocks.DUNGEON_BLOCK.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> POLISHED_SANDSTONE = ITEMS.register(
      "polished_sandstone", () -> new BlockItem((Block)ModBlocks.POLISHED_SANDSTONE.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> POLISHED_END_STONE = ITEMS.register(
      "polished_end_stone", () -> new BlockItem((Block)ModBlocks.POLISHED_END_STONE.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> POLISHED_END_STONE_SLAB = ITEMS.register(
      "polished_end_stone_slab", () -> new BlockItem((Block)ModBlocks.POLISHED_END_STONE_SLAB.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> POLISHED_END_STONE_STAIRS = ITEMS.register(
      "polished_end_stone_stairs", () -> new BlockItem((Block)ModBlocks.POLISHED_END_STONE_STAIRS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CHISELED_END_STONE_BRICKS = ITEMS.register(
      "chiseled_end_stone_bricks", () -> new BlockItem((Block)ModBlocks.CHISELED_END_STONE_BRICKS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> END_STONE_PILLAR = ITEMS.register(
      "end_stone_pillar", () -> new BlockItem((Block)ModBlocks.END_STONE_PILLAR.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> PURPUR_TILES = ITEMS.register(
      "purpur_tiles", () -> new BlockItem((Block)ModBlocks.PURPUR_TILES.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> VOID_PURPUR_TILES = ITEMS.register(
      "void_purpur_tiles", () -> new BlockItem((Block)ModBlocks.VOID_PURPUR_TILES.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> PURPUR_TILE_PILLAR = ITEMS.register(
      "purpur_tile_pillar", () -> new BlockItem((Block)ModBlocks.PURPUR_TILE_PILLAR.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> PURPUR_TILE_SLAB = ITEMS.register(
      "purpur_tile_slab", () -> new BlockItem((Block)ModBlocks.PURPUR_TILE_SLAB.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> PURPUR_TILE_STAIRS = ITEMS.register(
      "purpur_tile_stairs", () -> new BlockItem((Block)ModBlocks.PURPUR_TILE_STAIRS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> PURPUR_TILE_WALL = ITEMS.register(
      "purpur_tile_wall", () -> new BlockItem((Block)ModBlocks.PURPUR_TILE_WALL.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> VOID_INFUSED_END_STONE_BRICKS = ITEMS.register(
      "void_infused_end_stone_bricks", () -> new BlockItem((Block)ModBlocks.VOID_INFUSED_END_STONE_BRICKS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> VOID_STONE = ITEMS.register(
      "void_stone", () -> new BlockItem((Block)ModBlocks.VOID_STONE.get(), new Properties().fireResistant())
   );
   public static final DeferredItem<BlockItem> VOID_CRYSTAL = ITEMS.register(
      "void_crystal", () -> new BlockItem((Block)ModBlocks.VOID_CRYSTAL.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> VOID_LANTERN_BLOCK = ITEMS.register(
      "void_lantern_block", () -> new BlockItem((Block)ModBlocks.VOID_LANTERN_BLOCK.get(), new Properties().fireResistant())
   );
   public static final DeferredItem<BlockItem> OBSIDIAN_BRICKS = ITEMS.register(
      "obsidian_bricks", () -> new BlockItem((Block)ModBlocks.OBSIDIAN_BRICKS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> POLISHED_OBSIDIAN = ITEMS.register(
      "polished_obsidian", () -> new BlockItem((Block)ModBlocks.POLISHED_OBSIDIAN.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> POLISHED_OBSIDIAN_SLAB = ITEMS.register(
      "polished_obsidian_slab", () -> new BlockItem((Block)ModBlocks.POLISHED_OBSIDIAN_SLAB.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> POLISHED_OBSIDIAN_STAIRS = ITEMS.register(
      "polished_obsidian_stairs", () -> new BlockItem((Block)ModBlocks.POLISHED_OBSIDIAN_STAIRS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> POLISHED_OBSIDIAN_WALL = ITEMS.register(
      "polished_obsidian_wall", () -> new BlockItem((Block)ModBlocks.POLISHED_OBSIDIAN_WALL.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> OBSIDIAN_PILLAR = ITEMS.register(
      "obsidian_pillar", () -> new BlockItem((Block)ModBlocks.OBSIDIAN_PILLAR.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CHISELED_OBSIDIAN_BRICKS = ITEMS.register(
      "chiseled_obsidian_bricks", () -> new BlockItem((Block)ModBlocks.CHISELED_OBSIDIAN_BRICKS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> OBSIDIAN_BRICK_SLAB = ITEMS.register(
      "obsidian_brick_slab", () -> new BlockItem((Block)ModBlocks.OBSIDIAN_BRICK_SLAB.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> OBSIDIAN_BRICK_STAIRS = ITEMS.register(
      "obsidian_brick_stairs", () -> new BlockItem((Block)ModBlocks.OBSIDIAN_BRICK_STAIRS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> OBSIDIAN_FENCE = ITEMS.register(
      "obsidian_fence", () -> new BlockItem((Block)ModBlocks.OBSIDIAN_FENCE.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> OBSIDIAN_BRICK_WALL = ITEMS.register(
      "obsidian_brick_wall", () -> new BlockItem((Block)ModBlocks.OBSIDIAN_BRICK_WALL.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CHISELED_PURPUR_BLOCK = ITEMS.register(
      "chiseled_purpur_block", () -> new BlockItem((Block)ModBlocks.CHISELED_PURPUR_BLOCK.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> PURPUR_WALL = ITEMS.register(
      "purpur_wall", () -> new BlockItem((Block)ModBlocks.PURPUR_WALL.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> PURPUR_VOID_RUNE_TRAP_BLOCK = ITEMS.register(
      "purpur_void_rune_trap_block", () -> new BlockItem((Block)ModBlocks.PURPUR_VOID_RUNE_TRAP_BLOCK.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> END_STONE_TELEPORT_TRAP_BRICKS = ITEMS.register(
      "end_stone_teleport_trap_bricks", () -> new BlockItem((Block)ModBlocks.END_STONE_TELEPORT_TRAP_BRICKS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> OBSIDIAN_EXPLOSION_TRAP_BRICKS = ITEMS.register(
      "obsidian_explosion_trap_bricks", () -> new BlockItem((Block)ModBlocks.OBSIDIAN_EXPLOSION_TRAP_BRICKS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> SANDSTONE_POISON_DART_TRAP = ITEMS.register(
      "sandstone_poison_dart_trap", () -> new BlockItem((Block)ModBlocks.SANDSTONE_POISON_DART_TRAP.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> SANDSTONE_IGNITE_TRAP = ITEMS.register(
      "sandstone_ignite_trap", () -> new BlockItem((Block)ModBlocks.SANDSTONE_IGNITE_TRAP.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> SANDSTONE_FALLING_TRAP = ITEMS.register(
      "sandstone_falling_trap", () -> new BlockItem((Block)ModBlocks.SANDSTONE_FALLING_TRAP.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CHORUS_STEM = ITEMS.register(
      "chorus_stem", () -> new BlockItem((Block)ModBlocks.CHORUS_STEM.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CHORUS_PLANKS = ITEMS.register(
      "chorus_planks", () -> new BlockItem((Block)ModBlocks.CHORUS_PLANKS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CHORUS_SLAB = ITEMS.register(
      "chorus_slab", () -> new BlockItem((Block)ModBlocks.CHORUS_SLAB.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CHORUS_STAIRS = ITEMS.register(
      "chorus_stairs", () -> new BlockItem((Block)ModBlocks.CHORUS_STAIRS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CHORUS_FENCE = ITEMS.register(
      "chorus_fence", () -> new BlockItem((Block)ModBlocks.CHORUS_FENCE.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CHORUS_TRAPDOOR = ITEMS.register(
      "chorus_trapdoor", () -> new BlockItem((Block)ModBlocks.CHORUS_TRAPDOOR.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> PRISMARINE_BRICK_FENCE = ITEMS.register(
      "prismarine_brick_fence", () -> new BlockItem((Block)ModBlocks.PRISMARINE_BRICK_FENCE.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> QUARTZ_BRICK_WALL = ITEMS.register(
      "quartz_brick_wall", () -> new BlockItem((Block)ModBlocks.QUARTZ_BRICK_WALL.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> PRISMARINE_BRICK_WALL = ITEMS.register(
      "prismarine_brick_wall", () -> new BlockItem((Block)ModBlocks.PRISMARINE_BRICK_WALL.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> STONE_PILLAR = ITEMS.register(
      "stone_pillar", () -> new BlockItem((Block)ModBlocks.STONE_PILLAR.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CHISELED_STONE_BRICK_PILLAR = ITEMS.register(
      "chiseled_stone_brick_pillar", () -> new BlockItem((Block)ModBlocks.CHISELED_STONE_BRICK_PILLAR.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> STONE_TILES = ITEMS.register(
      "stone_tiles", () -> new BlockItem((Block)ModBlocks.STONE_TILES.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> STONE_TILE_SLAB = ITEMS.register(
      "stone_tile_slab", () -> new BlockItem((Block)ModBlocks.STONE_TILE_SLAB.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> STONE_TILE_STAIRS = ITEMS.register(
      "stone_tile_stairs", () -> new BlockItem((Block)ModBlocks.STONE_TILE_STAIRS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> STONE_TILE_WALL = ITEMS.register(
      "stone_tile_wall", () -> new BlockItem((Block)ModBlocks.STONE_TILE_WALL.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> BLACKSTONE_PILLAR = ITEMS.register(
      "blackstone_pillar", () -> new BlockItem((Block)ModBlocks.BLACKSTONE_PILLAR.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE = ITEMS.register(
      "azure_seastone", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_SLAB = ITEMS.register(
      "azure_seastone_slab", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_SLAB.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_STAIRS = ITEMS.register(
      "azure_seastone_stairs", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_STAIRS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_WALL = ITEMS.register(
      "azure_seastone_wall", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_WALL.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_FENCE = ITEMS.register(
      "azure_seastone_fence", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_FENCE.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_TILES = ITEMS.register(
      "azure_seastone_tiles", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_TILES.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CHISELED_AZURE_SEASTONE = ITEMS.register(
      "chiseled_azure_seastone", () -> new BlockItem((Block)ModBlocks.CHISELED_AZURE_SEASTONE.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_BRICKS = ITEMS.register(
      "azure_seastone_bricks", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_BRICKS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_BRICK_SLAB = ITEMS.register(
      "azure_seastone_brick_slab", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_BRICK_SLAB.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_BRICK_STAIRS = ITEMS.register(
      "azure_seastone_brick_stairs", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_BRICK_STAIRS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_BRICK_WALL = ITEMS.register(
      "azure_seastone_brick_wall", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_BRICK_WALL.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_MURAL_EMPTY = ITEMS.register(
      "azure_seastone_mural_empty", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_MURAL_EMPTY.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_MURAL_URCHINKIN = ITEMS.register(
      "azure_seastone_mural_urchinkin", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_MURAL_URCHINKIN.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_MURAL_CINDARIA = ITEMS.register(
      "azure_seastone_mural_cindaria", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_MURAL_CINDARIA.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_MURAL_HIPPOCAMTUS = ITEMS.register(
      "azure_seastone_mural_hippocamtus", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_MURAL_HIPPOCAMTUS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_MURAL_CLAWDIAN = ITEMS.register(
      "azure_seastone_mural_clawdian", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_MURAL_CLAWDIAN.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_MURAL_THUNDER = ITEMS.register(
      "azure_seastone_mural_thunder", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_MURAL_THUNDER.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_MURAL_SEA = ITEMS.register(
      "azure_seastone_mural_sea", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_MURAL_SEA.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_MURAL_UNDERWORLD = ITEMS.register(
      "azure_seastone_mural_underworld", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_MURAL_UNDERWORLD.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_MURAL_HARVEST = ITEMS.register(
      "azure_seastone_mural_harvest", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_MURAL_HARVEST.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_MURAL_SMITHING = ITEMS.register(
      "azure_seastone_mural_smithing", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_MURAL_SMITHING.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_MURAL_WISDOM = ITEMS.register(
      "azure_seastone_mural_wisdom", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_MURAL_WISDOM.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_URCHINKIN = ITEMS.register(
      "curved_azure_seastone_urchinkin", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_URCHINKIN.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_CINDARIA_1 = ITEMS.register(
      "curved_azure_seastone_cindaria_1", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_CINDARIA_1.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_CINDARIA_2 = ITEMS.register(
      "curved_azure_seastone_cindaria_2", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_CINDARIA_2.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_CINDARIA_3 = ITEMS.register(
      "curved_azure_seastone_cindaria_3", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_CINDARIA_3.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_CINDARIA_4 = ITEMS.register(
      "curved_azure_seastone_cindaria_4", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_CINDARIA_4.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_HIPPOCAMTUS_1 = ITEMS.register(
      "curved_azure_seastone_hippocamtus_1", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_HIPPOCAMTUS_1.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_HIPPOCAMTUS_2 = ITEMS.register(
      "curved_azure_seastone_hippocamtus_2", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_HIPPOCAMTUS_2.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_HIPPOCAMTUS_3 = ITEMS.register(
      "curved_azure_seastone_hippocamtus_3", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_HIPPOCAMTUS_3.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_HIPPOCAMTUS_4 = ITEMS.register(
      "curved_azure_seastone_hippocamtus_4", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_HIPPOCAMTUS_4.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_CLAWDIAN_1 = ITEMS.register(
      "curved_azure_seastone_clawdian_1", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_CLAWDIAN_1.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_CLAWDIAN_2 = ITEMS.register(
      "curved_azure_seastone_clawdian_2", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_CLAWDIAN_2.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_CLAWDIAN_3 = ITEMS.register(
      "curved_azure_seastone_clawdian_3", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_CLAWDIAN_3.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_CLAWDIAN_4 = ITEMS.register(
      "curved_azure_seastone_clawdian_4", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_CLAWDIAN_4.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_SCYLLA_1 = ITEMS.register(
      "curved_azure_seastone_scylla_1", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_SCYLLA_1.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_SCYLLA_2 = ITEMS.register(
      "curved_azure_seastone_scylla_2", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_SCYLLA_2.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_SCYLLA_3 = ITEMS.register(
      "curved_azure_seastone_scylla_3", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_SCYLLA_3.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_SCYLLA_4 = ITEMS.register(
      "curved_azure_seastone_scylla_4", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_SCYLLA_4.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_SCYLLA_5 = ITEMS.register(
      "curved_azure_seastone_scylla_5", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_SCYLLA_5.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_SCYLLA_6 = ITEMS.register(
      "curved_azure_seastone_scylla_6", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_SCYLLA_6.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_SCYLLA_7 = ITEMS.register(
      "curved_azure_seastone_scylla_7", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_SCYLLA_7.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_SCYLLA_8 = ITEMS.register(
      "curved_azure_seastone_scylla_8", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_SCYLLA_8.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CURVED_SEASTONE_SCYLLA_9 = ITEMS.register(
      "curved_azure_seastone_scylla_9", () -> new BlockItem((Block)ModBlocks.CURVED_SEASTONE_SCYLLA_9.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> POLISHED_AZURE_SEASTONE = ITEMS.register(
      "polished_azure_seastone", () -> new BlockItem((Block)ModBlocks.POLISHED_AZURE_SEASTONE.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> POLISHED_AZURE_SEASTONE_SLAB = ITEMS.register(
      "polished_azure_seastone_slab", () -> new BlockItem((Block)ModBlocks.POLISHED_AZURE_SEASTONE_SLAB.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> POLISHED_AZURE_SEASTONE_STAIRS = ITEMS.register(
      "polished_azure_seastone_stairs", () -> new BlockItem((Block)ModBlocks.POLISHED_AZURE_SEASTONE_STAIRS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> POLISHED_AZURE_SEASTONE_WALL = ITEMS.register(
      "polished_azure_seastone_wall", () -> new BlockItem((Block)ModBlocks.POLISHED_AZURE_SEASTONE_WALL.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_PILLAR = ITEMS.register(
      "azure_seastone_pillar", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_PILLAR.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> AZURE_SEASTONE_PILLAR_WALL = ITEMS.register(
      "azure_seastone_pillar_wall", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_PILLAR_WALL.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CHISELED_AZURE_SEASTONE_PILLAR = ITEMS.register(
      "chiseled_azure_seastone_pillar", () -> new BlockItem((Block)ModBlocks.CHISELED_AZURE_SEASTONE_PILLAR.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> CHISELED_AZURE_SEASTONE_PILLAR_WALL = ITEMS.register(
      "chiseled_azure_seastone_pillar_wall", () -> new BlockItem((Block)ModBlocks.CHISELED_AZURE_SEASTONE_PILLAR_WALL.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> FROSTED_STONE_BRICKS = ITEMS.register(
      "frosted_stone_bricks", () -> new BlockItem((Block)ModBlocks.FROSTED_STONE_BRICKS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> FROSTED_STONE_BRICK_SLAB = ITEMS.register(
      "frosted_stone_brick_slab", () -> new BlockItem((Block)ModBlocks.FROSTED_STONE_BRICK_SLAB.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> FROSTED_STONE_BRICK_STAIRS = ITEMS.register(
      "frosted_stone_brick_stairs", () -> new BlockItem((Block)ModBlocks.FROSTED_STONE_BRICK_STAIRS.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> FROSTED_STONE_BRICK_WALL = ITEMS.register(
      "frosted_stone_brick_wall", () -> new BlockItem((Block)ModBlocks.FROSTED_STONE_BRICK_WALL.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> BLACK_STEEL_BLOCK = ITEMS.register(
      "black_steel_block", () -> new BlockItem((Block)ModBlocks.BLACK_STEEL_BLOCK.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> BLACK_STEEL_FENCE = ITEMS.register(
      "black_steel_fence", () -> new BlockItem((Block)ModBlocks.BLACK_STEEL_FENCE.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> BLACK_STEEL_WALL = ITEMS.register(
      "black_steel_wall", () -> new BlockItem((Block)ModBlocks.BLACK_STEEL_WALL.get(), new Properties())
   );
   public static final DeferredItem<BlockItem> POINTED_ICICLE = ITEMS.register(
      "pointed_icicle", () -> new BlockItem((Block)ModBlocks.POINTED_ICICLE.get(), new Properties())
   );
   public static final DeferredItem<Item> WITHERITE_INGOT = ITEMS.register(
      "witherite_ingot", () -> new Item(new Properties().fireResistant().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<Item> ENDERITE_INGOT = ITEMS.register(
      "enderite_ingot", () -> new ItemInventoryOnly(new Properties().fireResistant().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<Item> ANCIENT_METAL_INGOT = ITEMS.register("ancient_metal_ingot", () -> new Item(new Properties().rarity(Rarity.UNCOMMON)));
   public static final DeferredItem<Item> ANCIENT_METAL_NUGGET = ITEMS.register(
      "ancient_metal_nugget", () -> new Item(new Properties().rarity(Rarity.UNCOMMON))
   );
   public static final DeferredItem<Item> BLACK_STEEL_INGOT = ITEMS.register("black_steel_ingot", () -> new Item(new Properties()));
   public static final DeferredItem<Item> BLACK_STEEL_NUGGET = ITEMS.register("black_steel_nugget", () -> new Item(new Properties()));
   public static final DeferredItem<Item> LACRIMA = ITEMS.register("lacrima", () -> new Item(new Properties().rarity(Rarity.UNCOMMON)));
   public static final DeferredItem<Item> ESSENCE_OF_THE_STORM = ITEMS.register(
      "essence_of_the_storm", () -> new Item(new Properties().fireResistant().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<Item> IGNITIUM_INGOT = ITEMS.register(
      "ignitium_ingot", () -> new Item(new Properties().fireResistant().rarity((Rarity)CMRarity.IGNITIUM_PROXY.getValue()))
   );
   public static final DeferredItem<Item> CURSIUM_INGOT = ITEMS.register(
      "cursium_ingot", () -> new Item(new Properties().fireResistant().rarity((Rarity)CMRarity.MALEDICTUS_PROXY.getValue()))
   );
   public static final DeferredItem<Item> IGNITIUM_UPGARDE_SMITHING_TEMPLATE = ITEMS.register(
      "ignitium_upgrade_smithing_template", () -> ModTemplate.createignitiumUpgradeTemplate()
   );
   public static final DeferredItem<Item> CURSIUM_UPGARDE_SMITHING_TEMPLATE = ITEMS.register(
      "cursium_upgrade_smithing_template", () -> ModTemplate.createcursiumUpgradeTemplate()
   );
   public static final DeferredItem<Item> CHAIN_OF_SOUL_BINDING = ITEMS.register("chain_of_soul_binding", () -> new ItemInventoryOnly(new Properties()));
   public static final DeferredItem<Item> CORAL_SPEAR = ITEMS.register(
      "coral_spear", () -> new Coral_Spear(new Properties().durability(110).attributes(Coral_Spear.createAttributes()))
   );
   public static final DeferredItem<Item> CORAL_BARDICHE = ITEMS.register(
      "coral_bardiche", () -> new Coral_Bardiche(new Properties().durability(160).attributes(Coral_Bardiche.createAttributes()))
   );
   public static final DeferredItem<Item> ATHAME = ITEMS.register(
      "athame", () -> new Athame(new Properties().durability(250).attributes(Athame.createAttributes()))
   );
   public static final DeferredItem<Item> KHOPESH = ITEMS.register(
      "khopesh", () -> new SwordItem(Tooltier.ANCIENT_METAL, new Properties().attributes(SwordItem.createAttributes(Tooltier.ANCIENT_METAL, 3, -2.4F)))
   );
   public static final DeferredItem<Item> BLACK_STEEL_SWORD = ITEMS.register(
      "black_steel_sword", () -> new SwordItem(Tooltier.BLACK_STEEL, new Properties().attributes(SwordItem.createAttributes(Tooltier.BLACK_STEEL, 3, -2.4F)))
   );
   public static final DeferredItem<Item> BLACK_STEEL_SHOVEL = ITEMS.register(
      "black_steel_shovel",
      () -> new ShovelItem(Tooltier.BLACK_STEEL, new Properties().attributes(ShovelItem.createAttributes(Tooltier.BLACK_STEEL, 1.5F, -3.0F)))
   );
   public static final DeferredItem<Item> BLACK_STEEL_PICKAXE = ITEMS.register(
      "black_steel_pickaxe",
      () -> new PickaxeItem(Tooltier.BLACK_STEEL, new Properties().attributes(PickaxeItem.createAttributes(Tooltier.BLACK_STEEL, 1.0F, -2.8F)))
   );
   public static final DeferredItem<Item> BLACK_STEEL_AXE = ITEMS.register(
      "black_steel_axe", () -> new AxeItem(Tooltier.BLACK_STEEL, new Properties().attributes(AxeItem.createAttributes(Tooltier.BLACK_STEEL, 6.0F, -3.1F)))
   );
   public static final DeferredItem<Item> BLACK_STEEL_HOE = ITEMS.register(
      "black_steel_hoe", () -> new HoeItem(Tooltier.BLACK_STEEL, new Properties().attributes(HoeItem.createAttributes(Tooltier.BLACK_STEEL, -2.0F, -1.0F)))
   );
   public static final DeferredItem<Item> BLACK_STEEL_TARGE = ITEMS.register("black_steel_targe", () -> new Black_Steel_Targe(new Properties().durability(840)));
   public static final DeferredItem<Item> AZURE_SEA_SHIELD = ITEMS.register("azure_sea_shield", () -> new Azure_sea_Shield(new Properties().durability(514)));
   public static final DeferredItem<Item> BULWARK_OF_THE_FLAME = ITEMS.register(
      "bulwark_of_the_flame", () -> new Bulwark_of_the_flame(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant())
   );
   public static final DeferredItem<Item> GAUNTLET_OF_GUARD = ITEMS.register(
      "gauntlet_of_guard",
      () -> new Gauntlet_of_Guard(
         new Properties()
            .stacksTo(1)
            .rarity(Rarity.EPIC)
            .fireResistant()
            .attributes(
               Cataclysm_Weapon.createAttributes(
                  10.0F,
                  -2.4F,
                  new AttributeContainer(Attributes.ARMOR, 3.0, Operation.ADD_VALUE),
                  new AttributeContainer(Attributes.ARMOR_TOUGHNESS, 3.0, Operation.ADD_VALUE),
                  new AttributeContainer(Attributes.KNOCKBACK_RESISTANCE, 0.15F, Operation.ADD_VALUE)
               )
            )
      )
   );
   public static final DeferredItem<Item> GAUNTLET_OF_BULWARK = ITEMS.register(
      "gauntlet_of_bulwark",
      () -> new Gauntlet_of_Bulwark(
         new Properties()
            .stacksTo(1)
            .rarity(Rarity.EPIC)
            .fireResistant()
            .attributes(
               Cataclysm_Weapon.createAttributes(
                  10.0F,
                  -2.4F,
                  new AttributeContainer(Attributes.ARMOR, 3.0, Operation.ADD_VALUE),
                  new AttributeContainer(Attributes.ARMOR_TOUGHNESS, 3.0, Operation.ADD_VALUE),
                  new AttributeContainer(Attributes.KNOCKBACK_RESISTANCE, 0.15F, Operation.ADD_VALUE)
               )
            )
      )
   );
   public static final DeferredItem<Item> GAUNTLET_OF_MAELSTROM = ITEMS.register(
      "gauntlet_of_maelstrom",
      () -> new Gauntlet_of_Maelstrom(
         new Properties()
            .stacksTo(1)
            .rarity(Rarity.EPIC)
            .fireResistant()
            .attributes(
               Cataclysm_Weapon.createAttributes(
                  10.0F,
                  -2.4F,
                  new AttributeContainer(Attributes.ARMOR, 3.0, Operation.ADD_VALUE),
                  new AttributeContainer(Attributes.ARMOR_TOUGHNESS, 3.0, Operation.ADD_VALUE),
                  new AttributeContainer(Attributes.KNOCKBACK_RESISTANCE, 0.15F, Operation.ADD_VALUE)
               )
            )
      )
   );
   public static final DeferredItem<Item> THE_INCINERATOR = ITEMS.register(
      "the_incinerator",
      () -> new The_Incinerator(
         new Properties()
            .stacksTo(1)
            .rarity(Rarity.EPIC)
            .fireResistant()
            .attributes(Cataclysm_Weapon.createAttributes(13.0F, -2.7F, new AttributeContainer(Attributes.ENTITY_INTERACTION_RANGE, 2.0, Operation.ADD_VALUE)))
      )
   );
   public static final DeferredItem<Item> BELT_OF_BEGINNER = ITEMS.register(
      "belt_of_beginner", () -> new CuriosItem(new Properties().stacksTo(1)).withSlotModifier("talisman", 2)
   );
   public static final DeferredItem<Item> BELT_OF_MONSTROSITY = ITEMS.register(
      "belt_of_monstrosity", () -> new CuriosItem(new Properties().stacksTo(1)).withSlotModifier("talisman", 2)
   );
   public static final DeferredItem<Item> BLAZING_GRIPS = ITEMS.register(
      "blazing_grips", () -> new Blazing_Grips(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant())
   );
   public static final DeferredItem<Item> CHITIN_CLAW = ITEMS.register(
      "chitin_claw",
      () -> new Chitin_Claw(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant())
         .withAttributes(
            "hands",
            new AttributeContainer(Attributes.ENTITY_INTERACTION_RANGE, 0.25, Operation.ADD_VALUE),
            new AttributeContainer(Attributes.BLOCK_INTERACTION_RANGE, 1.0, Operation.ADD_VALUE)
         )
   );
   public static final DeferredItem<Item> RING_OF_GRUDGED = ITEMS.register(
      "ring_of_grudged",
      () -> new CuriosItem(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant())
         .withAttributes("rings", new AttributeContainer(ModAttribute.ADDITIONAL_CRITICAL_DAMAGE, 10.0, Operation.ADD_VALUE))
   );
   public static final DeferredItem<Item> BERSERKER_SOUL_AMULET = ITEMS.register(
      "berserker_soul_amulet",
      () -> new CuriosItem(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant())
         .withAttributes(
            "necklace",
            new AttributeContainer(Attributes.ATTACK_DAMAGE, 0.1, Operation.ADD_MULTIPLIED_TOTAL),
            new AttributeContainer(Attributes.ARMOR, -0.25, Operation.ADD_MULTIPLIED_TOTAL)
         )
   );
   public static final DeferredItem<Item> VITALITY_ANKH = ITEMS.register(
      "vitality_ankh",
      () -> new CuriosItem(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant())
         .withAttributes("necklace", new AttributeContainer(ModAttribute.NATURE_HEAL, 20.0, Operation.ADD_VALUE))
   );
   public static final DeferredItem<Item> UNBREAKABLE_SKULL = ITEMS.register(
      "unbreakable_skull",
      () -> new Unbreakable_Skull(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant())
         .withAttributes(
            "talisman",
            new AttributeContainer(Attributes.ARMOR, 1.0, Operation.ADD_VALUE),
            new AttributeContainer(Attributes.ARMOR_TOUGHNESS, 1.0, Operation.ADD_VALUE)
         )
   );
   public static final DeferredItem<Item> STURDY_BOOTS = ITEMS.register(
      "sturdy_boots",
      () -> new Sturdy_Boots(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON))
         .withAttributes("feet", new AttributeContainer(Attributes.STEP_HEIGHT, 0.5, Operation.ADD_VALUE))
   );
   public static final DeferredItem<Item> CURSED_BOW = ITEMS.register(
      "cursed_bow", () -> new Cursed_bow(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant())
   );
   public static final DeferredItem<Item> WRATH_OF_THE_DESERT = ITEMS.register(
      "wrath_of_the_desert", () -> new Wrath_of_the_desert(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant())
   );
   public static final DeferredItem<Item> SOUL_RENDER = ITEMS.register(
      "soul_render",
      () -> new Soul_Render(
         new Properties()
            .stacksTo(1)
            .fireResistant()
            .rarity(Rarity.EPIC)
            .attributes(
               Cataclysm_Weapon.createAttributes(
                  14.0F,
                  -2.9F,
                  new AttributeContainer(Attributes.ENTITY_INTERACTION_RANGE, 2.0, Operation.ADD_VALUE),
                  new AttributeContainer(Attributes.BLOCK_INTERACTION_RANGE, 2.0, Operation.ADD_VALUE)
               )
            )
      )
   );
   public static final DeferredItem<Item> THE_ANNIHILATOR = ITEMS.register(
      "the_annihilator",
      () -> new The_Annihilator(
         new Properties()
            .stacksTo(1)
            .fireResistant()
            .rarity(Rarity.EPIC)
            .attributes(
               Cataclysm_Weapon.createAttributes(6.5F, -2.4F, new AttributeContainer(ModAttribute.ADDITIONAL_CRITICAL_DAMAGE, 75.0, Operation.ADD_VALUE))
            )
      )
   );
   public static final DeferredItem<Item> ASTRAPE = ITEMS.register(
      "astrape",
      () -> new Astrape(
         new Properties()
            .stacksTo(1)
            .rarity(Rarity.EPIC)
            .fireResistant()
            .attributes(Cataclysm_Weapon.createAttributes(9.5F, -2.6F, new AttributeContainer(Attributes.ENTITY_INTERACTION_RANGE, 2.0, Operation.ADD_VALUE)))
      )
   );
   public static final DeferredItem<Item> CERAUNUS = ITEMS.register(
      "ceraunus",
      () -> new Ceraunus(
         new Properties()
            .stacksTo(1)
            .rarity(Rarity.EPIC)
            .fireResistant()
            .attributes(Cataclysm_Weapon.createAttributes(15.0F, -3.3F, new AttributeContainer(Attributes.ENTITY_INTERACTION_RANGE, 1.0, Operation.ADD_VALUE)))
      )
   );
   public static final DeferredItem<Item> BRONTES = ITEMS.register(
      "brontes",
      () -> new Brontes(
         Tooltier.MONSTROSITY, new Properties().fireResistant().rarity(Rarity.EPIC).attributes(PickaxeItem.createAttributes(Tooltier.MONSTROSITY, 8.0F, -3.0F))
      )
   );
   public static final DeferredItem<Item> THE_IMMOLATOR = ITEMS.register(
      "the_immolator",
      () -> new The_Immolator(
         new Properties()
            .stacksTo(1)
            .fireResistant()
            .rarity(Rarity.EPIC)
            .attributes(
               Cataclysm_Weapon.createAttributes(7.5F, -2.4F, new AttributeContainer(ModAttribute.ADDITIONAL_CRITICAL_DAMAGE, 60.0, Operation.ADD_VALUE))
            )
      )
   );
   public static final DeferredItem<Item> MEAT_SHREDDER = ITEMS.register(
      "meat_shredder",
      () -> new Meat_Shredder(new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC).attributes(Cataclysm_Weapon.createAttributes(7.5F, -2.6F)))
   );
   public static final DeferredItem<Item> LASER_GATLING = ITEMS.register(
      "laser_gatling", () -> new Laser_Gatling(new Properties().stacksTo(1).fireResistant().durability(50).rarity(Rarity.EPIC))
   );
   public static final DeferredItem<Item> WITHER_ASSULT_SHOULDER_WEAPON = ITEMS.register(
      "wither_assault_shoulder_weapon", () -> new Wither_Assault_SHoulder_Weapon(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant())
   );
   public static final DeferredItem<Item> VOID_ASSULT_SHOULDER_WEAPON = ITEMS.register(
      "void_assault_shoulder_weapon", () -> new Void_Assault_SHoulder_Weapon(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant())
   );
   public static final DeferredItem<Item> VOID_FORGE = ITEMS.register(
      "void_forge",
      () -> new Void_forge(
         Tooltier.MONSTROSITY, new Properties().fireResistant().rarity(Rarity.EPIC).attributes(PickaxeItem.createAttributes(Tooltier.MONSTROSITY, 8.0F, -3.0F))
      )
   );
   public static final DeferredItem<Item> TIDAL_CLAWS = ITEMS.register(
      "tidal_claws",
      () -> new Tidal_Claws(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant().attributes(Cataclysm_Weapon.createAttributes(7.0F, -2.4F)))
   );
   public static final DeferredItem<Item> INFERNAL_FORGE = ITEMS.register(
      "infernal_forge",
      () -> new Infernal_forge(
         Tooltier.MONSTROSITY, new Properties().fireResistant().rarity(Rarity.EPIC).attributes(PickaxeItem.createAttributes(Tooltier.MONSTROSITY, 8.0F, -3.0F))
      )
   );
   public static final DeferredItem<Item> SANDSTORM_IN_A_BOTTLE = ITEMS.register(
      "sandstorm_in_a_bottle", () -> new Sandstorm_In_A_Bottle(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant())
   );
   public static final DeferredItem<Item> ANCIENT_SPEAR = ITEMS.register(
      "ancient_spear",
      () -> new Ancient_Spear(
         new Properties()
            .stacksTo(1)
            .rarity(Rarity.EPIC)
            .fireResistant()
            .durability(1800)
            .attributes(Cataclysm_Weapon.createAttributes(8.5F, -2.6F, new AttributeContainer(Attributes.ENTITY_INTERACTION_RANGE, 2.0, Operation.ADD_VALUE)))
      )
   );
   public static final DeferredItem<Item> STICKY_GLOVES = ITEMS.register("sticky_gloves", () -> new Sticky_Gloves(new Properties().stacksTo(1)));
   public static final DeferredItem<Item> REMNANT_SKULL = ITEMS.register(
      "remnant_skull", () -> new Remnant_Skull(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant())
   );
   public static final DeferredItem<Item> NETHERITE_EFFIGY = ITEMS.register(
      "netherite_effigy", () -> new Netherite_Effigy(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant())
   );
   public static final DeferredItem<Item> VOID_SCATTER_ARROW = ITEMS.register(
      "void_scatter_arrow", () -> new Void_Scatter_Arrow_Item(new Properties().fireResistant())
   );
   public static final DeferredItem<Item> VOID_SHARD = ITEMS.register("void_shard", () -> new ItemInventoryOnly(new Properties().fireResistant()));
   public static final DeferredItem<Item> BLAZING_BONE = ITEMS.register("blazing_bone", () -> new ItemInventoryOnly(new Properties().fireResistant()));
   public static final DeferredItem<Item> KOBOLETON_BONE = ITEMS.register("koboleton_bone", () -> new Item(new Properties()));
   public static final DeferredItem<Item> VOID_JAW = ITEMS.register("void_jaw", () -> new Item(new Properties().fireResistant()));
   public static final DeferredItem<Item> VOID_CORE = ITEMS.register(
      "void_core", () -> new Void_core(new Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON))
   );
   public static final DeferredItem<Item> CRYSTALLIZED_CORAL_FRAGMENTS = ITEMS.register("crystallized_coral_fragments", () -> new Item(new Properties()));
   public static final DeferredItem<Item> CRYSTALLIZED_CORAL = ITEMS.register("crystallized_coral", () -> new Item(new Properties()));
   public static final DeferredItem<Item> CORAL_CHUNK = ITEMS.register("coral_chunk", () -> new Item(new Properties()));
   public static final DeferredItem<Item> ABYSSAL_SACRIFICE = ITEMS.register(
      "abyssal_sacrifice", () -> new Item(new Properties().fireResistant().rarity(Rarity.RARE))
   );
   public static final DeferredItem<Item> NECKLACE_OF_THE_DESERT = ITEMS.register(
      "necklace_of_the_desert", () -> new Necklace_Of_The_Desert(new Properties().fireResistant().rarity(Rarity.RARE))
   );
   public static final DeferredItem<Item> STRANGE_KEY = ITEMS.register(
      "strange_key", () -> new Strange_Key(new Properties().fireResistant().rarity(Rarity.RARE))
   );
   public static final DeferredItem<Item> APTRGANGR_HEAD = ITEMS.register(
      "aptrgangr_head",
      () -> new CataclysmSkullItem((Block)ModBlocks.APTRGANGR_HEAD.get(), (Block)ModBlocks.APTRGANGR_WALL_HEAD.get(), new Properties().rarity(Rarity.UNCOMMON))
   );
   public static final DeferredItem<Item> DRAUGR_HEAD = ITEMS.register(
      "draugr_head",
      () -> new CataclysmSkullItem((Block)ModBlocks.DRAUGR_HEAD.get(), (Block)ModBlocks.DRAUGR_WALL_HEAD.get(), new Properties().rarity(Rarity.UNCOMMON))
   );
   public static final DeferredItem<Item> KOBOLEDIATOR_SKULL = ITEMS.register(
      "kobolediator_skull",
      () -> new CataclysmSkullItem(
         (Block)ModBlocks.KOBOLEDIATOR_SKULL.get(), (Block)ModBlocks.KOBOLEDIATOR_WALL_SKULL.get(), new Properties().rarity(Rarity.UNCOMMON)
      )
   );
   public static final DeferredItem<Item> BONE_REPTILE_HELMET = ITEMS.register(
      "bone_reptile_helmet",
      () -> new Bone_Reptile_Armor(
         Armortier.BONE_REPTILE,
         Type.HELMET,
         new Properties().attributes(Cataclysm_Armor.createAttributes(Armortier.BONE_REPTILE, Type.HELMET)).durability(Type.HELMET.getDurability(35))
      )
   );
   public static final DeferredItem<Item> BONE_REPTILE_CHESTPLATE = ITEMS.register(
      "bone_reptile_chestplate",
      () -> new Bone_Reptile_Armor(
         Armortier.BONE_REPTILE,
         Type.CHESTPLATE,
         new Properties().attributes(Cataclysm_Armor.createAttributes(Armortier.BONE_REPTILE, Type.CHESTPLATE)).durability(Type.CHESTPLATE.getDurability(35))
      )
   );
   public static final DeferredItem<Item> IGNITIUM_HELMET = ITEMS.register(
      "ignitium_helmet",
      () -> new Ignitium_Armor(
         Armortier.IGNITIUM,
         Type.HELMET,
         new Properties()
            .fireResistant()
            .rarity(Rarity.EPIC)
            .attributes(Cataclysm_Armor.createAttributes(Armortier.IGNITIUM, Type.HELMET))
            .durability(Type.HELMET.getDurability(45))
      )
   );
   public static final DeferredItem<Item> IGNITIUM_CHESTPLATE = ITEMS.register(
      "ignitium_chestplate",
      () -> new Ignitium_Armor(
         Armortier.IGNITIUM,
         Type.CHESTPLATE,
         new Properties()
            .fireResistant()
            .rarity(Rarity.EPIC)
            .attributes(Cataclysm_Armor.createAttributes(Armortier.IGNITIUM, Type.CHESTPLATE))
            .durability(Type.CHESTPLATE.getDurability(45))
      )
   );
   public static final DeferredItem<Item> IGNITIUM_ELYTRA_CHESTPLATE = ITEMS.register(
      "ignitium_elytra_chestplate",
      () -> new Ignitium_Elytra_ChestPlate(
         new Properties()
            .fireResistant()
            .rarity(Rarity.EPIC)
            .attributes(Cataclysm_Armor.createAttributes(Armortier.IGNITIUM, Type.CHESTPLATE))
            .durability(Type.CHESTPLATE.getDurability(45)),
         Armortier.IGNITIUM
      )
   );
   public static final DeferredItem<Item> IGNITIUM_LEGGINGS = ITEMS.register(
      "ignitium_leggings",
      () -> new Ignitium_Armor(
         Armortier.IGNITIUM,
         Type.LEGGINGS,
         new Properties()
            .fireResistant()
            .rarity(Rarity.EPIC)
            .attributes(Cataclysm_Armor.createAttributes(Armortier.IGNITIUM, Type.LEGGINGS))
            .durability(Type.LEGGINGS.getDurability(45))
      )
   );
   public static final DeferredItem<Item> IGNITIUM_BOOTS = ITEMS.register(
      "ignitium_boots",
      () -> new Ignitium_Armor(
         Armortier.IGNITIUM,
         Type.BOOTS,
         new Properties()
            .fireResistant()
            .rarity(Rarity.EPIC)
            .attributes(Cataclysm_Armor.createAttributes(Armortier.IGNITIUM, Type.BOOTS))
            .durability(Type.BOOTS.getDurability(45))
      )
   );
   public static final DeferredItem<Item> CURSIUM_HELMET = ITEMS.register(
      "cursium_helmet",
      () -> new Cursium_Armor(
         Armortier.CURSIUM,
         Type.HELMET,
         new Properties()
            .fireResistant()
            .rarity(Rarity.EPIC)
            .attributes(Cataclysm_Armor.createAttributes(Armortier.CURSIUM, Type.HELMET))
            .durability(Type.HELMET.getDurability(45))
      )
   );
   public static final DeferredItem<Item> CURSIUM_CHESTPLATE = ITEMS.register(
      "cursium_chestplate",
      () -> new Cursium_ChestPlate(
         Armortier.CURSIUM,
         Type.CHESTPLATE,
         new Properties()
            .fireResistant()
            .rarity(Rarity.EPIC)
            .attributes(Cataclysm_Armor.createAttributes(Armortier.CURSIUM, Type.CHESTPLATE))
            .durability(Type.CHESTPLATE.getDurability(45))
      )
   );
   public static final DeferredItem<Item> CURSIUM_LEGGINGS = ITEMS.register(
      "cursium_leggings",
      () -> new Cursium_Armor(
         Armortier.CURSIUM,
         Type.LEGGINGS,
         new Properties()
            .fireResistant()
            .rarity(Rarity.EPIC)
            .attributes(Cataclysm_Armor.createAttributes(Armortier.CURSIUM, Type.LEGGINGS))
            .durability(Type.LEGGINGS.getDurability(45))
      )
   );
   public static final DeferredItem<Item> CURSIUM_BOOTS = ITEMS.register(
      "cursium_boots",
      () -> new Cursium_Armor(
         Armortier.CURSIUM,
         Type.BOOTS,
         new Properties()
            .fireResistant()
            .rarity(Rarity.EPIC)
            .attributes(Cataclysm_Armor.createAttributes(Armortier.CURSIUM, Type.BOOTS))
            .durability(Type.BOOTS.getDurability(45))
      )
   );
   public static final DeferredItem<Item> MONSTROUS_HORN = ITEMS.register(
      "monstrous_horn", () -> new Item(new Properties().fireResistant().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<Item> LAVA_POWER_CELL = ITEMS.register(
      "lava_power_cell", () -> new Item(new Properties().fireResistant().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<Item> MONSTROUS_HELM = ITEMS.register(
      "monstrous_helm",
      () -> new Monstrous_Helm(
         ArmorMaterials.NETHERITE,
         Type.HELMET,
         new Properties()
            .fireResistant()
            .attributes(Cataclysm_Armor.createAttributes(ArmorMaterials.NETHERITE, Type.HELMET))
            .durability(Type.HELMET.getDurability(45))
            .rarity(Rarity.EPIC)
      )
   );
   public static final DeferredItem<Item> BLOOM_STONE_PAULDRONS = ITEMS.register(
      "bloom_stone_pauldrons",
      () -> new Bloom_Stone_Pauldrons(
         Armortier.CRAB,
         Type.CHESTPLATE,
         new Properties()
            .fireResistant()
            .rarity(Rarity.UNCOMMON)
            .attributes(
               Cataclysm_Armor.createAttributes(Armortier.CRAB, Type.CHESTPLATE, new AttributeContainer(ModAttribute.NATURE_HEAL, 15.0, Operation.ADD_VALUE))
            )
            .durability(Type.CHESTPLATE.getDurability(30))
      )
   );
   public static final DeferredItem<Item> BURNING_ASHES = ITEMS.register("burning_ashes", () -> new Item(new Properties().fireResistant().rarity(Rarity.RARE)));
   public static final DeferredItem<Item> DYING_EMBER = ITEMS.register("dying_ember", () -> new Item(new Properties().fireResistant().rarity(Rarity.UNCOMMON)));
   public static final DeferredItem<Item> MUSIC_DISC_NETHERITE_MONSTROSITY = ITEMS.register(
      "music_disc_netherite_monstrosity",
      () -> new Item(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant().jukeboxPlayable(ModJukeboxSongs.MONSTROSITY_THEME))
   );
   public static final DeferredItem<Item> MUSIC_DISC_ENDER_GUARDIAN = ITEMS.register(
      "music_disc_ender_guardian",
      () -> new Item(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant().jukeboxPlayable(ModJukeboxSongs.ENDERGUARDIAN_THEME))
   );
   public static final DeferredItem<Item> MUSIC_DISC_IGNIS = ITEMS.register(
      "music_disc_ignis", () -> new Item(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant().jukeboxPlayable(ModJukeboxSongs.IGNIS_THEME))
   );
   public static final DeferredItem<Item> MUSIC_DISC_THE_HARBINGER = ITEMS.register(
      "music_disc_the_harbinger",
      () -> new Item(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant().jukeboxPlayable(ModJukeboxSongs.HARBINGER_THEME))
   );
   public static final DeferredItem<Item> MUSIC_DISC_THE_LEVIATHAN = ITEMS.register(
      "music_disc_the_leviathan",
      () -> new Item(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant().jukeboxPlayable(ModJukeboxSongs.LEVIATHAN_THEME))
   );
   public static final DeferredItem<Item> MUSIC_DISC_ANCIENT_REMNANT = ITEMS.register(
      "music_disc_ancient_remnant",
      () -> new Item(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant().jukeboxPlayable(ModJukeboxSongs.REMNANT_THEME))
   );
   public static final DeferredItem<Item> MUSIC_DISC_MALEDICTUS = ITEMS.register(
      "music_disc_maledictus",
      () -> new Item(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant().jukeboxPlayable(ModJukeboxSongs.MALEDICTUS_THEME))
   );
   public static final DeferredItem<Item> MUSIC_DISC_SCYLLA = ITEMS.register(
      "music_disc_scylla", () -> new Item(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant().jukeboxPlayable(ModJukeboxSongs.SCYLLA_THEME))
   );
   public static final DeferredItem<Item> MUSIC_DISC_THE_CATACLYSM_FARER = ITEMS.register(
      "music_disc_the_cataclysmfarer",
      () -> new Item(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant().jukeboxPlayable(ModJukeboxSongs.THE_CATACLYSM_FARER))
   );
   public static final DeferredItem<Item> MECH_EYE = ITEMS.register(
      "mech_eye", () -> new DungeonEyeItem(new Properties().fireResistant(), ModTag.EYE_OF_MECH_LOCATED, 255, 51, 0)
   );
   public static final DeferredItem<Item> FLAME_EYE = ITEMS.register(
      "flame_eye", () -> new DungeonEyeItem(new Properties().fireResistant(), ModTag.EYE_OF_FLAME_LOCATED, 252, 149, 0)
   );
   public static final DeferredItem<Item> VOID_EYE = ITEMS.register(
      "void_eye", () -> new DungeonEyeItem(new Properties().fireResistant(), ModTag.EYE_OF_RUINED_LOCATED, 186, 149, 186)
   );
   public static final DeferredItem<Item> MONSTROUS_EYE = ITEMS.register(
      "monstrous_eye", () -> new DungeonEyeItem(new Properties().fireResistant(), ModTag.EYE_OF_MONSTROUS_LOCATED, 90, 87, 90)
   );
   public static final DeferredItem<Item> ABYSS_EYE = ITEMS.register(
      "abyss_eye", () -> new DungeonEyeItem(new Properties().fireResistant(), ModTag.EYE_OF_ABYSS_LOCATED, 33, 22, 43)
   );
   public static final DeferredItem<Item> DESERT_EYE = ITEMS.register(
      "desert_eye", () -> new DungeonEyeItem(new Properties().fireResistant(), ModTag.EYE_OF_DESERT_LOCATED, 247, 168, 64)
   );
   public static final DeferredItem<Item> CURSED_EYE = ITEMS.register(
      "cursed_eye", () -> new DungeonEyeItem(new Properties().fireResistant(), ModTag.EYE_OF_CURSE_LOCATED, 26, 107, 89)
   );
   public static final DeferredItem<Item> STORM_EYE = ITEMS.register(
      "storm_eye", () -> new DungeonEyeItem(new Properties().fireResistant(), ModTag.EYE_OF_STORM_LOCATED, 99, 194, 224)
   );
   public static final DeferredItem<Item> LIONFISH = ITEMS.register(
      "lionfish",
      () -> new Item(
         new Properties()
            .food(
               new Builder()
                  .nutrition(1)
                  .saturationModifier(0.1F)
                  .effect(new MobEffectInstance(MobEffects.POISON, 60, 0), 1.0F)
                  .effect(new MobEffectInstance(MobEffects.CONFUSION, 60, 0), 1.0F)
                  .effect(new MobEffectInstance(MobEffects.WATER_BREATHING, 4800, 0), 1.0F)
                  .build()
            )
      )
   );
   public static final DeferredItem<Item> AMETHYST_CRAB_MEAT = ITEMS.register(
      "amethyst_crab_meat",
      () -> new Item(
         new Properties()
            .food(new Builder().nutrition(6).saturationModifier(1.2F).effect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 1.0F).build())
      )
   );
   public static final DeferredItem<Item> BLESSED_AMETHYST_CRAB_MEAT = ITEMS.register(
      "blessed_amethyst_crab_meat",
      () -> new Blessed_Amethyst_Crab_Meat(
         new Properties()
            .rarity(Rarity.EPIC)
            .food(
               new Builder()
                  .nutrition(6)
                  .saturationModifier(1.2F)
                  .effect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 1.0F)
                  .effect(new MobEffectInstance(ModEffect.EFFECTBLESSING_OF_AMETHYST, 1800, 0), 1.0F)
                  .alwaysEdible()
                  .build()
            )
      )
   );
   public static final DeferredItem<Item> AMETHYST_CRAB_SHELL = ITEMS.register("amethyst_crab_shell", () -> new Item(new Properties()));
   public static final DeferredItem<Item> LIONFISH_SPIKE = ITEMS.register("lionfish_spike", () -> new ItemInventoryOnly(new Properties()));
   public static final DeferredItem<Item> URCHIN_SPIKE = ITEMS.register("urchin_spike", () -> new ItemInventoryOnly(new Properties()));
   public static final DeferredItem<Item> BLOOD_CLOT = ITEMS.register("blood_clot", () -> new ItemInventoryOnly(new Properties()));
   public static final DeferredItem<Item> THE_BABY_LEVIATHAN_BUCKET = ITEMS.register(
      "the_baby_leviathan_bucket", () -> new ModFishBucket((EntityType<?>)ModEntities.THE_BABY_LEVIATHAN.get(), Fluids.WATER, new Properties().fireResistant())
   );
   public static final DeferredItem<Item> MODERN_REMNANT_BUCKET = ITEMS.register(
      "modern_remnant_bucket", () -> new ModernRemantBucket((EntityType<?>)ModEntities.MODERN_REMNANT.get(), Fluids.EMPTY, new Properties().fireResistant())
   );
   public static final DeferredItem<Item> NETHERITE_MINISTROSITY_BUCKET = ITEMS.register(
      "netherite_ministrosity_bucket",
      () -> new ModernRemantBucket((EntityType<?>)ModEntities.NETHERITE_MINISTROSITY.get(), Fluids.EMPTY, new Properties().fireResistant())
   );
   public static final DeferredItem<SpawnEggItem> ENDER_GOLEM_SPAWN_EGG = ITEMS.register(
      "ender_golem_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.ENDER_GOLEM.get(), 2759234, 10572798, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> NETHERITE_MONSTROSITY_SPAWN_EGG = ITEMS.register(
      "netherite_monstrosity_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.NETHERITE_MONSTROSITY.get(), 5065037, 16024866, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> NETHERITE_MINISTROSITY_SPAWN_EGG = ITEMS.register(
      "netherite_ministrosity_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.NETHERITE_MINISTROSITY.get(), 7039083, 12738305, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> ENDER_GUARDIAN_SPAWN_EGG = ITEMS.register(
      "ender_guardian_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.ENDER_GUARDIAN.get(), 2759234, 9725844, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> ENDERMAPTERA_SPAWN_EGG = ITEMS.register(
      "endermaptera_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.ENDERMAPTERA.get(), 2759234, 7237230, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> IGNIS_SPAWN_EGG = ITEMS.register(
      "ignis_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.IGNIS.get(), 16167425, 16579584, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> IGNITED_REVENANT_SPAWN_EGG = ITEMS.register(
      "ignited_revenant_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.IGNITED_REVENANT.get(), 4672845, 16579584, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> IGNITED_BERSERKER_SPAWN_EGG = ITEMS.register(
      "ignited_berserker_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.IGNITED_BERSERKER.get(), 4672845, 16579584, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> THE_WATCHER_SPAWN_EGG = ITEMS.register(
      "the_watcher_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.THE_WATCHER.get(), 7568523, 15219515, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> THE_PROWLER_SPAWN_EGG = ITEMS.register(
      "the_prowler_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.THE_PROWLER.get(), 1974305, 6827554, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> THE_HARBINGER_SPAWN_EGG = ITEMS.register(
      "the_harbinger_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.THE_HARBINGER.get(), 1974305, 11412276, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> THE_LEVIATHAN_SPAWN_EGG = ITEMS.register(
      "the_leviathan_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.THE_LEVIATHAN.get(), 1379867, 6619391, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> THE_BABY_LEVIATHAN_SPAWN_EGG = ITEMS.register(
      "the_baby_leviathan_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.THE_BABY_LEVIATHAN.get(), 3285313, 9060095, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> DEEPLING_SPAWN_EGG = ITEMS.register(
      "deepling_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.DEEPLING.get(), 1583676, 12250612, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> DEEPLING_BRUTE_SPAWN_EGG = ITEMS.register(
      "deepling_brute_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.DEEPLING_BRUTE.get(), 1583676, 6619391, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> DEEPLING_ANGLER_SPAWN_EGG = ITEMS.register(
      "deepling_angler_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.DEEPLING_ANGLER.get(), 1583676, 10016994, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> DEEPLING_PRIEST_SPAWN_EGG = ITEMS.register(
      "deepling_priest_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.DEEPLING_PRIEST.get(), 1583676, 532564, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> DEEPLING_WARLOCK_SPAWN_EGG = ITEMS.register(
      "deepling_warlock_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.DEEPLING_WARLOCK.get(), 1583676, 14051992, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> LIONFISH_SPAWN_EGG = ITEMS.register(
      "lionfish_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.LIONFISH.get(), 10016994, 1583676, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> CORAL_GOLEM_SPAWN_EGG = ITEMS.register(
      "coral_golem_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.CORAL_GOLEM.get(), 2180004, 10756655, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> CORALSSUS_SPAWN_EGG = ITEMS.register(
      "coralssus_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.CORALSSUS.get(), 4156645, 15592524, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> AMETHYST_CRAB_SPAWN_EGG = ITEMS.register(
      "amethyst_crab_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.AMETHYST_CRAB.get(), 6579300, 8018869, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> KOBOLETON_SPAWN_EGG = ITEMS.register(
      "koboleton_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.KOBOLETON.get(), 12038550, 14778627, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> KOBOLEDIATOR_SPAWN_EGG = ITEMS.register(
      "kobolediator_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.KOBOLEDIATOR.get(), 12038550, 9722673, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> WADJET_SPAWN_EGG = ITEMS.register(
      "wadjet_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.WADJET.get(), 12038550, 14399594, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> ANCIENT_REMNANT_SPAWN_EGG = ITEMS.register(
      "ancient_remnant_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.ANCIENT_REMNANT.get(), 12038550, 6827554, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> MODERN_REMNANT_SPAWN_EGG = ITEMS.register(
      "modern_remnant_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.MODERN_REMNANT.get(), 12038550, 14404775, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> MALEDICTUS_SPAWN_EGG = ITEMS.register(
      "maledictus_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.MALEDICTUS.get(), 3789490, 9722673, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> APTRGANGR_SPAWN_EGG = ITEMS.register(
      "aptrgangr_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.APTRGANGR.get(), 3744022, 15263716, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> ELITE_DRAUGR_SPAWN_EGG = ITEMS.register(
      "elite_draugr_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.ELITE_DRAUGR.get(), 3744022, 4465432, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> ROYAL_DRAUGR_SPAWN_EGG = ITEMS.register(
      "royal_draugr_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.ROYAL_DRAUGR.get(), 3744022, 9722673, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> DRAUGR_SPAWN_EGG = ITEMS.register(
      "draugr_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.DRAUGR.get(), 3744022, 2828325, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> SCYLLA_SPAWN_EGG = ITEMS.register(
      "scylla_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.SCYLLA.get(), 8429999, 4150939, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> CLAWDIAN_SPAWN_EGG = ITEMS.register(
      "clawdian_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.CLAWDIAN.get(), 9119261, 13198662, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> HIPPOCAMTUS_SPAWN_EGG = ITEMS.register(
      "hippocamtus_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.HIPPOCAMTUS.get(), 8294315, 16771470, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> CINDARIA_SPAWN_EGG = ITEMS.register(
      "cindaria_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.CINDARIA.get(), 13604351, 6482605, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> OCTOHOST_SPAWN_EGG = ITEMS.register(
      "octohost_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.DROWNED_HOST.get(), 9433559, 7550243, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> SYMBIOCTO_SPAWN_EGG = ITEMS.register(
      "symbiocto_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.SYMBIOCTO.get(), 7550243, 15378785, new Properties())
   );
   public static final DeferredItem<SpawnEggItem> URCHINKIN_SPAWN_EGG = ITEMS.register(
      "urchinkin_spawn_egg", () -> new SpawnEggItem((EntityType)ModEntities.URCHINKIN.get(), 0, 2822226, new Properties())
   );
   public static final DeferredItem<BlockItem> ALTAR_OF_VOID = ITEMS.register(
      "altar_of_void", () -> new BlockItem((Block)ModBlocks.ALTAR_OF_VOID.get(), new Properties().fireResistant().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<BlockItem> ALTAR_OF_FIRE = ITEMS.register(
      "altar_of_fire", () -> new BlockItem((Block)ModBlocks.ALTAR_OF_FIRE.get(), new Properties().fireResistant().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<BlockItem> ALTAR_OF_AMETHYST = ITEMS.register(
      "altar_of_amethyst", () -> new BlockItem((Block)ModBlocks.ALTAR_OF_AMETHYST.get(), new Properties().fireResistant().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<BlockItem> ALTAR_OF_ABYSS = ITEMS.register(
      "altar_of_abyss", () -> new BlockItem((Block)ModBlocks.ALTAR_OF_ABYSS.get(), new Properties().fireResistant().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<BlockItem> CURSED_TOMBSTONE = ITEMS.register(
      "cursed_tombstone", () -> new BlockItem((Block)ModBlocks.CURSED_TOMBSTONE.get(), new Properties().fireResistant().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<BlockItem> EMP = ITEMS.register(
      "emp", () -> new BlockItem((Block)ModBlocks.EMP.get(), new Properties().fireResistant().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<BlockItem> ABYSSAL_EGG = ITEMS.register(
      "abyssal_egg", () -> new BlockItem((Block)ModBlocks.ABYSSAL_EGG.get(), new Properties().fireResistant().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<BlockItem> MECHANICAL_FUSION_ANVIL = ITEMS.register(
      "mechanical_fusion_anvil", () -> new BlockItem((Block)ModBlocks.MECHANICAL_FUSION_ANVIL.get(), new Properties().fireResistant().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<BlockItem> DOOR_OF_SEAL = ITEMS.register(
      "door_of_seal", () -> new BlockItem((Block)ModBlocks.DOOR_OF_SEAL.get(), new Properties().fireResistant().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<BlockItem> GODDESS_STATUE = ITEMS.register(
      "goddess_statue", () -> new BlockItem((Block)ModBlocks.GODDESS_STATUE.get(), new Properties().fireResistant().rarity(Rarity.EPIC))
   );
   public static final DeferredItem<BlockItem> BOSS_RESPAWNER = ITEMS.register(
      "boss_respawner", () -> new BlockItem((Block)ModBlocks.BOSS_RESPAWNER.get(), new Properties().fireResistant().rarity(Rarity.EPIC))
   );

   public static void initDispenser() {
      DispenserBlock.registerProjectileBehavior((ItemLike)VOID_SCATTER_ARROW.get());
      DispenseItemBehavior dispenseItemBehavior = new DefaultDispenseItemBehavior() {
         private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

         public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
            DispensibleContainerItem dispensibleContainerItem = (DispensibleContainerItem)itemStack.getItem();
            BlockPos blockPos = blockSource.pos().relative((Direction)blockSource.state().getValue(DispenserBlock.FACING));
            Level level = blockSource.level().getLevel();
            if (dispensibleContainerItem.emptyContents(null, level, blockPos, null)) {
               dispensibleContainerItem.checkExtraContent(null, level, itemStack, blockPos);
               return new ItemStack(net.minecraft.world.item.Items.BUCKET);
            } else {
               return this.defaultDispenseItemBehavior.dispense(blockSource, itemStack);
            }
         }
      };
      DispenserBlock.registerBehavior((ItemLike)THE_BABY_LEVIATHAN_BUCKET.get(), dispenseItemBehavior);
      DispenserBlock.registerBehavior((ItemLike)MODERN_REMNANT_BUCKET.get(), dispenseItemBehavior);
   }

   public static void modifyComponents(ModifyDefaultComponentsEvent event) {
      if (!ConfigHolder.COMMON_SPEC.isLoaded()) {
         Cataclysm.LOGGER.error("Could not modify default components due to config not being loaded yet");
      } else {
         event.modify((ItemLike)MONSTROUS_HELM.get(), builder -> builder.set(DataComponents.UNBREAKABLE, new Unbreakable(true)));
         event.modify(
            (ItemLike)IGNITIUM_HELMET.get(),
            builder -> {
               builder.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
               AttributeUtils.mergeAttributes(
                  builder,
                  (Item)IGNITIUM_HELMET.get(),
                  Cataclysm_Armor.createArmorAttributes(
                     Armortier.IGNITIUM,
                     (float)CMCommonConfig.IgnitiumArmor.armorMultiplier,
                     (float)CMCommonConfig.IgnitiumArmor.toughness,
                     (float)CMCommonConfig.IgnitiumArmor.knockbackResistance,
                     Type.HELMET
                  )
               );
            }
         );
         event.modify(
            (ItemLike)IGNITIUM_CHESTPLATE.get(),
            builder -> {
               builder.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
               AttributeUtils.mergeAttributes(
                  builder,
                  (Item)IGNITIUM_CHESTPLATE.get(),
                  Cataclysm_Armor.createArmorAttributes(
                     Armortier.IGNITIUM,
                     (float)CMCommonConfig.IgnitiumArmor.armorMultiplier,
                     (float)CMCommonConfig.IgnitiumArmor.toughness,
                     (float)CMCommonConfig.IgnitiumArmor.knockbackResistance,
                     Type.CHESTPLATE
                  )
               );
            }
         );
         event.modify(
            (ItemLike)IGNITIUM_ELYTRA_CHESTPLATE.get(),
            builder -> {
               builder.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
               AttributeUtils.mergeAttributes(
                  builder,
                  (Item)IGNITIUM_ELYTRA_CHESTPLATE.get(),
                  Cataclysm_Armor.createArmorAttributes(
                     Armortier.IGNITIUM,
                     (float)CMCommonConfig.IgnitiumArmor.armorMultiplier,
                     (float)CMCommonConfig.IgnitiumArmor.toughness,
                     (float)CMCommonConfig.IgnitiumArmor.knockbackResistance,
                     Type.CHESTPLATE
                  )
               );
            }
         );
         event.modify(
            (ItemLike)IGNITIUM_LEGGINGS.get(),
            builder -> {
               builder.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
               AttributeUtils.mergeAttributes(
                  builder,
                  (Item)IGNITIUM_LEGGINGS.get(),
                  Cataclysm_Armor.createArmorAttributes(
                     Armortier.IGNITIUM,
                     (float)CMCommonConfig.IgnitiumArmor.armorMultiplier,
                     (float)CMCommonConfig.IgnitiumArmor.toughness,
                     (float)CMCommonConfig.IgnitiumArmor.knockbackResistance,
                     Type.LEGGINGS
                  )
               );
            }
         );
         event.modify(
            (ItemLike)IGNITIUM_BOOTS.get(),
            builder -> {
               builder.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
               AttributeUtils.mergeAttributes(
                  builder,
                  (Item)IGNITIUM_BOOTS.get(),
                  Cataclysm_Armor.createArmorAttributes(
                     Armortier.IGNITIUM,
                     (float)CMCommonConfig.IgnitiumArmor.armorMultiplier,
                     (float)CMCommonConfig.IgnitiumArmor.toughness,
                     (float)CMCommonConfig.IgnitiumArmor.knockbackResistance,
                     Type.BOOTS
                  )
               );
            }
         );
         event.modify(
            (ItemLike)CURSIUM_HELMET.get(),
            builder -> {
               builder.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
               AttributeUtils.mergeAttributes(
                  builder,
                  (Item)CURSIUM_HELMET.get(),
                  Cataclysm_Armor.createArmorAttributes(
                     Armortier.CURSIUM,
                     (float)CMCommonConfig.CursiumArmor.armorMultiplier,
                     (float)CMCommonConfig.CursiumArmor.toughness,
                     (float)CMCommonConfig.CursiumArmor.knockbackResistance,
                     Type.HELMET
                  )
               );
            }
         );
         event.modify(
            (ItemLike)CURSIUM_CHESTPLATE.get(),
            builder -> {
               builder.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
               AttributeUtils.mergeAttributes(
                  builder,
                  (Item)CURSIUM_CHESTPLATE.get(),
                  Cataclysm_Armor.createArmorAttributes(
                     Armortier.CURSIUM,
                     (float)CMCommonConfig.CursiumArmor.armorMultiplier,
                     (float)CMCommonConfig.CursiumArmor.toughness,
                     (float)CMCommonConfig.CursiumArmor.knockbackResistance,
                     Type.CHESTPLATE
                  )
               );
            }
         );
         event.modify(
            (ItemLike)CURSIUM_LEGGINGS.get(),
            builder -> {
               builder.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
               AttributeUtils.mergeAttributes(
                  builder,
                  (Item)CURSIUM_LEGGINGS.get(),
                  Cataclysm_Armor.createArmorAttributes(
                     Armortier.CURSIUM,
                     (float)CMCommonConfig.CursiumArmor.armorMultiplier,
                     (float)CMCommonConfig.CursiumArmor.toughness,
                     (float)CMCommonConfig.CursiumArmor.knockbackResistance,
                     Type.LEGGINGS
                  )
               );
            }
         );
         event.modify(
            (ItemLike)CURSIUM_BOOTS.get(),
            builder -> {
               builder.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
               AttributeUtils.mergeAttributes(
                  builder,
                  (Item)CURSIUM_BOOTS.get(),
                  Cataclysm_Armor.createArmorAttributes(
                     Armortier.CURSIUM,
                     (float)CMCommonConfig.CursiumArmor.armorMultiplier,
                     (float)CMCommonConfig.CursiumArmor.toughness,
                     (float)CMCommonConfig.CursiumArmor.knockbackResistance,
                     Type.BOOTS
                  )
               );
            }
         );
         event.modify(
            (ItemLike)BONE_REPTILE_HELMET.get(),
            builder -> AttributeUtils.mergeAttributes(
               builder,
               (Item)BONE_REPTILE_HELMET.get(),
               Cataclysm_Armor.createArmorAttributes(
                  Armortier.BONE_REPTILE,
                  (float)CMCommonConfig.BoneReptileArmor.armorMultiplier,
                  (float)CMCommonConfig.BoneReptileArmor.toughness,
                  (float)CMCommonConfig.BoneReptileArmor.knockbackResistance,
                  Type.HELMET
               )
            )
         );
         event.modify(
            (ItemLike)BONE_REPTILE_CHESTPLATE.get(),
            builder -> AttributeUtils.mergeAttributes(
               builder,
               (Item)BONE_REPTILE_CHESTPLATE.get(),
               Cataclysm_Armor.createArmorAttributes(
                  Armortier.BONE_REPTILE,
                  (float)CMCommonConfig.BoneReptileArmor.armorMultiplier,
                  (float)CMCommonConfig.BoneReptileArmor.toughness,
                  (float)CMCommonConfig.BoneReptileArmor.knockbackResistance,
                  Type.CHESTPLATE
               )
            )
         );
         event.modify(
            (ItemLike)BLOOM_STONE_PAULDRONS.get(),
            builder -> AttributeUtils.mergeAttributes(
               builder,
               (Item)BLOOM_STONE_PAULDRONS.get(),
               Cataclysm_Armor.createArmorAttributes(
                  Armortier.CRAB,
                  (float)CMCommonConfig.BloomStoneArmor.armorMultiplier,
                  (float)CMCommonConfig.BloomStoneArmor.toughness,
                  (float)CMCommonConfig.BloomStoneArmor.knockbackResistance,
                  Type.CHESTPLATE
               )
            )
         );
         event.modify(
            (ItemLike)GAUNTLET_OF_BULWARK.get(),
            builder -> AttributeUtils.mergeAttributes(
               builder,
               (Item)GAUNTLET_OF_BULWARK.get(),
               Cataclysm_Weapon.createAttributes(
                  -1.0F + (float)CMCommonConfig.GauntletOfBulwark.attackDamage, -4.0F + (float)CMCommonConfig.GauntletOfBulwark.attackSpeed
               )
            )
         );
         event.modify(
            (ItemLike)GAUNTLET_OF_GUARD.get(),
            builder -> AttributeUtils.mergeAttributes(
               builder,
               (Item)GAUNTLET_OF_GUARD.get(),
               Cataclysm_Weapon.createAttributes(
                  -1.0F + (float)CMCommonConfig.GauntletOfGuard.attackDamage, -4.0F + (float)CMCommonConfig.GauntletOfGuard.attackSpeed
               )
            )
         );
         event.modify(
            (ItemLike)GAUNTLET_OF_MAELSTROM.get(),
            builder -> AttributeUtils.mergeAttributes(
               builder,
               (Item)GAUNTLET_OF_MAELSTROM.get(),
               Cataclysm_Weapon.createAttributes(
                  -1.0F + (float)CMCommonConfig.GauntletOfMaelstrom.attackDamage, -4.0F + (float)CMCommonConfig.GauntletOfMaelstrom.attackSpeed
               )
            )
         );
         event.modify(
            (ItemLike)MEAT_SHREDDER.get(),
            builder -> AttributeUtils.mergeAttributes(
               builder,
               (Item)MEAT_SHREDDER.get(),
               Cataclysm_Weapon.createAttributes(
                  -1.0F + (float)CMCommonConfig.MeatShredder.attackDamage, -4.0F + (float)CMCommonConfig.MeatShredder.attackSpeed
               )
            )
         );
         event.modify(
            (ItemLike)SOUL_RENDER.get(),
            builder -> AttributeUtils.mergeAttributes(
               builder,
               (Item)SOUL_RENDER.get(),
               Cataclysm_Weapon.createAttributes(-1.0F + (float)CMCommonConfig.SoulRender.attackDamage, -4.0F + (float)CMCommonConfig.SoulRender.attackSpeed)
            )
         );
         event.modify(
            (ItemLike)CERAUNUS.get(),
            builder -> AttributeUtils.mergeAttributes(
               builder,
               (Item)CERAUNUS.get(),
               Cataclysm_Weapon.createAttributes(-1.0F + (float)CMCommonConfig.Ceraunus.attackDamage, -4.0F + (float)CMCommonConfig.Ceraunus.attackSpeed)
            )
         );
         event.modify(
            (ItemLike)THE_ANNIHILATOR.get(),
            builder -> AttributeUtils.mergeAttributes(
               builder,
               (Item)THE_ANNIHILATOR.get(),
               Cataclysm_Weapon.createAttributes(-1.0F + (float)CMCommonConfig.Annihilator.attackDamage, -4.0F + (float)CMCommonConfig.Annihilator.attackSpeed)
            )
         );
         event.modify(
            (ItemLike)THE_IMMOLATOR.get(),
            builder -> AttributeUtils.mergeAttributes(
               builder,
               (Item)THE_IMMOLATOR.get(),
               Cataclysm_Weapon.createAttributes(-1.0F + (float)CMCommonConfig.Immolator.attackDamage, -4.0F + (float)CMCommonConfig.Immolator.attackSpeed)
            )
         );
         event.modify(
            (ItemLike)ASTRAPE.get(),
            builder -> AttributeUtils.mergeAttributes(
               builder,
               (Item)ASTRAPE.get(),
               Cataclysm_Weapon.createAttributes(-1.0F + (float)CMCommonConfig.Astrape.attackDamage, -4.0F + (float)CMCommonConfig.Astrape.attackSpeed)
            )
         );
         event.modify(
            (ItemLike)THE_INCINERATOR.get(),
            builder -> AttributeUtils.mergeAttributes(
               builder,
               (Item)THE_INCINERATOR.get(),
               Cataclysm_Weapon.createAttributes(-1.0F + (float)CMCommonConfig.Incinerator.attackDamage, -4.0F + (float)CMCommonConfig.Incinerator.attackSpeed)
            )
         );
         event.modify(
            (ItemLike)INFERNAL_FORGE.get(),
            builder -> AttributeUtils.mergeAttributes(
               builder,
               (Item)INFERNAL_FORGE.get(),
               PickaxeItem.createAttributes(
                  Tooltier.MONSTROSITY, -4.0F + (float)CMCommonConfig.InfernalForge.attackDamage, -4.0F + (float)CMCommonConfig.InfernalForge.attackSpeed
               )
            )
         );
         event.modify(
            (ItemLike)TIDAL_CLAWS.get(),
            builder -> AttributeUtils.mergeAttributes(
               builder,
               (Item)TIDAL_CLAWS.get(),
               Cataclysm_Weapon.createAttributes(-1.0F + (float)CMCommonConfig.TidalClaws.attackDamage, -4.0F + (float)CMCommonConfig.TidalClaws.attackSpeed)
            )
         );
         event.modify(
            (ItemLike)BRONTES.get(),
            builder -> AttributeUtils.mergeAttributes(
               builder,
               (Item)BRONTES.get(),
               PickaxeItem.createAttributes(
                  Tooltier.MONSTROSITY, -4.0F + (float)CMCommonConfig.Brontes.attackDamage, -4.0F + (float)CMCommonConfig.Brontes.attackSpeed
               )
            )
         );
         event.modify(
            (ItemLike)VOID_FORGE.get(),
            builder -> AttributeUtils.mergeAttributes(
               builder,
               (Item)VOID_FORGE.get(),
               PickaxeItem.createAttributes(
                  Tooltier.MONSTROSITY, -4.0F + (float)CMCommonConfig.VoidForge.attackDamage, -4.0F + (float)CMCommonConfig.VoidForge.attackSpeed
               )
            )
         );
         event.modify(
            (ItemLike)ANCIENT_SPEAR.get(),
            builder -> AttributeUtils.mergeAttributes(
               builder,
               (Item)ANCIENT_SPEAR.get(),
               Cataclysm_Weapon.createAttributes(
                  -1.0F + (float)CMCommonConfig.AncientSpear.attackDamage, -4.0F + (float)CMCommonConfig.AncientSpear.attackSpeed
               )
            )
         );
      }
   }
}
