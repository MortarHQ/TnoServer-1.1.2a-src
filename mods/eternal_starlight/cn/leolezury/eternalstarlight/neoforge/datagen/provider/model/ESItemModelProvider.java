package cn.leolezury.eternalstarlight.neoforge.datagen.provider.model;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.ItemModelGenerators.TrimModelData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ESItemModelProvider extends ItemModelProvider {
   public ESItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
      super(output, "eternal_starlight", existingFileHelper);
   }

   protected void registerModels() {
      this.basicItem(ESItems.BOARWARF_SPAWN_EGG.get());
      this.basicItem(ESItems.ASTRAL_GOLEM_SPAWN_EGG.get());
      this.basicItem(ESItems.GLEECH_SPAWN_EGG.get());
      this.basicItem(ESItems.LONESTAR_SKELETON_SPAWN_EGG.get());
      this.basicItem(ESItems.NIGHTFALL_SPIDER_SPAWN_EGG.get());
      this.basicItem(ESItems.SEEKER_SPAWN_EGG.get());
      this.basicItem(ESItems.THIRST_WALKER_SPAWN_EGG.get());
      this.basicItem(ESItems.CRETEOR_SPAWN_EGG.get());
      this.basicItem(ESItems.TINY_CRETEOR_SPAWN_EGG.get());
      this.basicItem(ESItems.STRANGHOUL_SPAWN_EGG.get());
      this.basicItem(ESItems.ENT_SPAWN_EGG.get());
      this.basicItem(ESItems.RATLIN_SPAWN_EGG.get());
      this.basicItem(ESItems.ZOMBIFIED_RATLIN_SPAWN_EGG.get());
      this.basicItem(ESItems.SHADOW_SNAIL_SPAWN_EGG.get());
      this.basicItem(ESItems.YETI_SPAWN_EGG.get());
      this.basicItem(ESItems.AURORA_DEER_SPAWN_EGG.get());
      this.basicItem(ESItems.CRYSTALLIZED_MOTH_SPAWN_EGG.get());
      this.basicItem(ESItems.SHIMMER_LACEWING_SPAWN_EGG.get());
      this.basicItem(ESItems.STARFIRE_BIRD_SPAWN_EGG.get());
      this.basicItem(ESItems.GRIMSTONE_GOLEM_SPAWN_EGG.get());
      this.basicItem(ESItems.AETHERSENT_GOLEM_SPAWN_EGG.get());
      this.basicItem(ESItems.ROOKFISH_SPAWN_EGG.get());
      this.basicItem(ESItems.LUMINOFISH_SPAWN_EGG.get());
      this.basicItem(ESItems.LUMINARIS_SPAWN_EGG.get());
      this.basicItem(ESItems.TWILIGHT_GAZE_SPAWN_EGG.get());
      this.basicItem(ESItems.THE_GATEKEEPER_SPAWN_EGG.get());
      this.basicItem(ESItems.STARLIGHT_GOLEM_SPAWN_EGG.get());
      this.basicItem(ESItems.FREEZE_SPAWN_EGG.get());
      this.basicItem(ESItems.PERMAFROST_SPAWN_EGG.get());
      this.basicItem(ESItems.LUNAR_MONSTROSITY_SPAWN_EGG.get());
      this.basicItem(ESItems.TANGLED_SPAWN_EGG.get());
      this.basicItem(ESItems.TANGLED_SKULL_SPAWN_EGG.get());
      this.flatBlockTexture(ESItems.LUNAR_SAPLING.get());
      this.block(ESItems.LUNAR_LEAVES.get());
      this.block(ESItems.LUNAR_LOG.get());
      this.block(ESItems.LUNAR_WOOD.get());
      this.block(ESItems.LUNAR_PLANKS.get());
      this.block(ESItems.STRIPPED_LUNAR_LOG.get());
      this.block(ESItems.STRIPPED_LUNAR_WOOD.get());
      this.basicItem(ESItems.LUNAR_DOOR.get());
      this.trapdoor(ESItems.LUNAR_TRAPDOOR.get());
      this.block(ESItems.LUNAR_PRESSURE_PLATE.get());
      this.button(ESItems.LUNAR_BUTTON.get(), ESItems.LUNAR_PLANKS.get());
      this.fence(ESItems.LUNAR_FENCE.get(), ESItems.LUNAR_PLANKS.get());
      this.block(ESItems.LUNAR_FENCE_GATE.get());
      this.block(ESItems.LUNAR_SLAB.get());
      this.block(ESItems.LUNAR_STAIRS.get());
      this.block(ESItems.DEAD_LUNAR_LOG.get());
      this.block(ESItems.RED_CRYSTALLIZED_LUNAR_LOG.get());
      this.block(ESItems.BLUE_CRYSTALLIZED_LUNAR_LOG.get());
      this.basicItem(ESItems.LUNAR_SIGN.get());
      this.basicItem(ESItems.LUNAR_HANGING_SIGN.get());
      this.basicItem(ESItems.LUNAR_BOAT.get());
      this.basicItem(ESItems.LUNAR_CHEST_BOAT.get());
      this.flatBlockTexture(ESItems.NORTHLAND_SAPLING.get());
      this.block(ESItems.NORTHLAND_LEAVES.get());
      this.block(ESItems.NORTHLAND_LOG.get());
      this.block(ESItems.NORTHLAND_WOOD.get());
      this.block(ESItems.NORTHLAND_PLANKS.get());
      this.block(ESItems.STRIPPED_NORTHLAND_LOG.get());
      this.block(ESItems.STRIPPED_NORTHLAND_WOOD.get());
      this.basicItem(ESItems.NORTHLAND_DOOR.get());
      this.trapdoor(ESItems.NORTHLAND_TRAPDOOR.get());
      this.block(ESItems.NORTHLAND_PRESSURE_PLATE.get());
      this.button(ESItems.NORTHLAND_BUTTON.get(), ESItems.NORTHLAND_PLANKS.get());
      this.fence(ESItems.NORTHLAND_FENCE.get(), ESItems.NORTHLAND_PLANKS.get());
      this.block(ESItems.NORTHLAND_FENCE_GATE.get());
      this.block(ESItems.NORTHLAND_SLAB.get());
      this.block(ESItems.NORTHLAND_STAIRS.get());
      this.basicItem(ESItems.NORTHLAND_SIGN.get());
      this.basicItem(ESItems.NORTHLAND_HANGING_SIGN.get());
      this.basicItem(ESItems.NORTHLAND_BOAT.get());
      this.basicItem(ESItems.NORTHLAND_CHEST_BOAT.get());
      this.flatBlockTexture(ESItems.BANYIN_SAPLING.get());
      this.block(ESItems.BANYIN_LEAVES.get());
      this.block(ESItems.BANYIN_LOG.get());
      this.block(ESItems.BANYIN_WOOD.get());
      this.block(ESItems.BANYIN_PLANKS.get());
      this.block(ESItems.STRIPPED_BANYIN_LOG.get());
      this.block(ESItems.STRIPPED_BANYIN_WOOD.get());
      this.basicItem(ESItems.BANYIN_DOOR.get());
      this.trapdoor(ESItems.BANYIN_TRAPDOOR.get());
      this.block(ESItems.BANYIN_PRESSURE_PLATE.get());
      this.button(ESItems.BANYIN_BUTTON.get(), ESItems.BANYIN_PLANKS.get());
      this.fence(ESItems.BANYIN_FENCE.get(), ESItems.BANYIN_PLANKS.get());
      this.block(ESItems.BANYIN_FENCE_GATE.get());
      this.block(ESItems.BANYIN_SLAB.get());
      this.block(ESItems.BANYIN_STAIRS.get());
      this.basicItem(ESItems.BANYIN_SIGN.get());
      this.basicItem(ESItems.BANYIN_HANGING_SIGN.get());
      this.basicItem(ESItems.BANYIN_BOAT.get());
      this.basicItem(ESItems.BANYIN_CHEST_BOAT.get());
      this.block(ESItems.BANYIN_ROOTS.get());
      this.block(ESItems.MUDDY_BANYIN_ROOTS.get());
      this.flatBlockTexture(ESItems.SCARLET_SAPLING.get());
      this.block(ESItems.SCARLET_LEAVES.get());
      this.layeredBlock(ESItems.SCARLET_LEAVES_PILE.get());
      this.block(ESItems.SCARLET_LOG.get());
      this.block(ESItems.SCARLET_WOOD.get());
      this.block(ESItems.SCARLET_PLANKS.get());
      this.block(ESItems.STRIPPED_SCARLET_LOG.get());
      this.block(ESItems.STRIPPED_SCARLET_WOOD.get());
      this.basicItem(ESItems.SCARLET_DOOR.get());
      this.trapdoor(ESItems.SCARLET_TRAPDOOR.get());
      this.block(ESItems.SCARLET_PRESSURE_PLATE.get());
      this.button(ESItems.SCARLET_BUTTON.get(), ESItems.SCARLET_PLANKS.get());
      this.fence(ESItems.SCARLET_FENCE.get(), ESItems.SCARLET_PLANKS.get());
      this.block(ESItems.SCARLET_FENCE_GATE.get());
      this.block(ESItems.SCARLET_SLAB.get());
      this.block(ESItems.SCARLET_STAIRS.get());
      this.basicItem(ESItems.SCARLET_SIGN.get());
      this.basicItem(ESItems.SCARLET_HANGING_SIGN.get());
      this.basicItem(ESItems.SCARLET_BOAT.get());
      this.basicItem(ESItems.SCARLET_CHEST_BOAT.get());
      this.flatBlockTexture(ESItems.TORREYA_SAPLING.get());
      this.block(ESItems.TORREYA_LEAVES.get());
      this.block(ESItems.TORREYA_LOG.get());
      this.block(ESItems.TORREYA_WOOD.get());
      this.block(ESItems.TORREYA_PLANKS.get());
      this.block(ESItems.STRIPPED_TORREYA_LOG.get());
      this.block(ESItems.STRIPPED_TORREYA_WOOD.get());
      this.basicItem(ESItems.TORREYA_DOOR.get());
      this.trapdoor(ESItems.TORREYA_TRAPDOOR.get());
      this.block(ESItems.TORREYA_PRESSURE_PLATE.get());
      this.button(ESItems.TORREYA_BUTTON.get(), ESItems.TORREYA_PLANKS.get());
      this.fence(ESItems.TORREYA_FENCE.get(), ESItems.TORREYA_PLANKS.get());
      this.block(ESItems.TORREYA_FENCE_GATE.get());
      this.block(ESItems.TORREYA_SLAB.get());
      this.block(ESItems.TORREYA_STAIRS.get());
      this.basicItem(ESItems.TORREYA_SIGN.get());
      this.basicItem(ESItems.TORREYA_HANGING_SIGN.get());
      this.basicItem(ESItems.TORREYA_BOAT.get());
      this.basicItem(ESItems.TORREYA_CHEST_BOAT.get());
      this.flatBlockTexture(ESItems.TORREYA_VINES.get());
      this.basicItem(ESItems.TORREYA_CAMPFIRE.get());
      this.basicItem(ESItems.JINGLESTEM_SAPLING.get());
      this.flatBlockTexture(ESItems.HANGING_ALGALEAVES.get());
      this.flatBlockTexture(ESItems.ALGALEAVES.get());
      this.block(ESItems.JINGLESTEM_LOG.get());
      this.block(ESItems.JINGLESTEM_WOOD.get());
      this.block(ESItems.JINGLESTEM_PLANKS.get());
      this.block(ESItems.STRIPPED_JINGLESTEM_LOG.get());
      this.block(ESItems.STRIPPED_JINGLESTEM_WOOD.get());
      this.basicItem(ESItems.JINGLESTEM_DOOR.get());
      this.trapdoor(ESItems.JINGLESTEM_TRAPDOOR.get());
      this.block(ESItems.JINGLESTEM_PRESSURE_PLATE.get());
      this.button(ESItems.JINGLESTEM_BUTTON.get(), ESItems.JINGLESTEM_PLANKS.get());
      this.fence(ESItems.JINGLESTEM_FENCE.get(), ESItems.JINGLESTEM_PLANKS.get());
      this.block(ESItems.JINGLESTEM_FENCE_GATE.get());
      this.block(ESItems.JINGLESTEM_SLAB.get());
      this.block(ESItems.JINGLESTEM_STAIRS.get());
      this.basicItem(ESItems.JINGLESTEM_SIGN.get());
      this.basicItem(ESItems.JINGLESTEM_HANGING_SIGN.get());
      this.basicItem(ESItems.JINGLESTEM_RAFT.get());
      this.basicItem(ESItems.JINGLESTEM_CHEST_RAFT.get());
      this.basicItem(ESItems.JINGLESTEM_CRISP.get());
      this.basicItem(ESItems.JINGLESTEM_SANDWICH.get());
      this.flatBlockTexture(ESItems.CRADLEWOOD_SAPLING.get());
      this.block(ESItems.CRADLEWOOD_LEAVES.get());
      this.block(ESItems.CRADLEWOOD_LOG.get());
      this.block(ESItems.CRADLEWOOD_WOOD.get());
      this.block(ESItems.CRADLEWOOD_PLANKS.get());
      this.block(ESItems.STRIPPED_CRADLEWOOD_LOG.get());
      this.block(ESItems.STRIPPED_CRADLEWOOD_WOOD.get());
      this.basicItem(ESItems.CRADLEWOOD_DOOR.get());
      this.trapdoor(ESItems.CRADLEWOOD_TRAPDOOR.get());
      this.block(ESItems.CRADLEWOOD_PRESSURE_PLATE.get());
      this.button(ESItems.CRADLEWOOD_BUTTON.get(), ESItems.CRADLEWOOD_PLANKS.get());
      this.fence(ESItems.CRADLEWOOD_FENCE.get(), ESItems.CRADLEWOOD_PLANKS.get());
      this.block(ESItems.CRADLEWOOD_FENCE_GATE.get());
      this.block(ESItems.CRADLEWOOD_SLAB.get());
      this.block(ESItems.CRADLEWOOD_STAIRS.get());
      this.basicItem(ESItems.CRADLEWOOD_SIGN.get());
      this.basicItem(ESItems.CRADLEWOOD_HANGING_SIGN.get());
      this.basicItem(ESItems.CRADLEWOOD_BOAT.get());
      this.basicItem(ESItems.CRADLEWOOD_CHEST_BOAT.get());
      this.block(ESItems.GRIMSTONE.get());
      this.block(ESItems.GRIMSTONE_SLAB.get());
      this.block(ESItems.GRIMSTONE_STAIRS.get());
      this.wall(ESItems.GRIMSTONE_WALL.get(), ESItems.GRIMSTONE.get());
      this.block(ESItems.COBBLED_GRIMSTONE.get());
      this.block(ESItems.COBBLED_GRIMSTONE_SLAB.get());
      this.block(ESItems.COBBLED_GRIMSTONE_STAIRS.get());
      this.wall(ESItems.COBBLED_GRIMSTONE_WALL.get(), ESItems.COBBLED_GRIMSTONE.get());
      this.block(ESItems.GRIMSTONE_BRICKS.get());
      this.block(ESItems.GRIMSTONE_BRICK_SLAB.get());
      this.block(ESItems.GRIMSTONE_BRICK_STAIRS.get());
      this.wall(ESItems.GRIMSTONE_BRICK_WALL.get(), ESItems.GRIMSTONE_BRICKS.get());
      this.block(ESItems.CRACKED_GRIMSTONE_BRICKS.get());
      this.block(ESItems.POLISHED_GRIMSTONE.get());
      this.block(ESItems.POLISHED_GRIMSTONE_SLAB.get());
      this.block(ESItems.POLISHED_GRIMSTONE_STAIRS.get());
      this.wall(ESItems.POLISHED_GRIMSTONE_WALL.get(), ESItems.POLISHED_GRIMSTONE.get());
      this.block(ESItems.GRIMSTONE_TILES.get());
      this.block(ESItems.GRIMSTONE_TILE_SLAB.get());
      this.block(ESItems.GRIMSTONE_TILE_STAIRS.get());
      this.wall(ESItems.GRIMSTONE_TILE_WALL.get(), ESItems.GRIMSTONE_TILES.get());
      this.block(ESItems.CRACKED_GRIMSTONE_TILES.get());
      this.block(ESItems.POLISHED_GRIMSTONE_TILES.get());
      this.block(ESItems.POLISHED_GRIMSTONE_TILE_SLAB.get());
      this.block(ESItems.POLISHED_GRIMSTONE_TILE_STAIRS.get());
      this.wall(ESItems.POLISHED_GRIMSTONE_TILE_WALL.get(), ESItems.POLISHED_GRIMSTONE_TILES.get());
      this.block(ESItems.CHISELED_GRIMSTONE.get());
      this.block(ESItems.GLOWING_GRIMSTONE.get());
      this.block(ESItems.VOIDSTONE.get());
      this.block(ESItems.VOIDSTONE_SLAB.get());
      this.block(ESItems.VOIDSTONE_STAIRS.get());
      this.wall(ESItems.VOIDSTONE_WALL.get(), ESItems.VOIDSTONE.get());
      this.block(ESItems.COBBLED_VOIDSTONE.get());
      this.block(ESItems.COBBLED_VOIDSTONE_SLAB.get());
      this.block(ESItems.COBBLED_VOIDSTONE_STAIRS.get());
      this.wall(ESItems.COBBLED_VOIDSTONE_WALL.get(), ESItems.COBBLED_VOIDSTONE.get());
      this.block(ESItems.VOIDSTONE_BRICKS.get());
      this.block(ESItems.VOIDSTONE_BRICK_SLAB.get());
      this.block(ESItems.VOIDSTONE_BRICK_STAIRS.get());
      this.wall(ESItems.VOIDSTONE_BRICK_WALL.get(), ESItems.VOIDSTONE_BRICKS.get());
      this.block(ESItems.CRACKED_VOIDSTONE_BRICKS.get());
      this.block(ESItems.POLISHED_VOIDSTONE.get());
      this.block(ESItems.POLISHED_VOIDSTONE_SLAB.get());
      this.block(ESItems.POLISHED_VOIDSTONE_STAIRS.get());
      this.wall(ESItems.POLISHED_VOIDSTONE_WALL.get(), ESItems.POLISHED_VOIDSTONE.get());
      this.block(ESItems.VOIDSTONE_TILES.get());
      this.block(ESItems.VOIDSTONE_TILE_SLAB.get());
      this.block(ESItems.VOIDSTONE_TILE_STAIRS.get());
      this.wall(ESItems.VOIDSTONE_TILE_WALL.get(), ESItems.VOIDSTONE_TILES.get());
      this.block(ESItems.CRACKED_VOIDSTONE_TILES.get());
      this.block(ESItems.CHISELED_VOIDSTONE.get());
      this.block(ESItems.GLOWING_VOIDSTONE.get());
      this.block(ESItems.ETERNAL_ICE.get());
      this.block(ESItems.THIN_ETERNAL_ICE.get());
      this.block(ESItems.ETERNAL_ICE_BRICKS.get());
      this.block(ESItems.ETERNAL_ICE_BRICK_SLAB.get());
      this.block(ESItems.ETERNAL_ICE_BRICK_STAIRS.get());
      this.wall(ESItems.ETERNAL_ICE_BRICK_WALL.get(), ESItems.ETERNAL_ICE_BRICKS.get());
      this.basicItem(ESItems.ETERNAL_ICE_LANTERN.get());
      this.block(ESItems.HAZE_ICE.get());
      this.block(ESItems.HAZE_ICE_BRICKS.get());
      this.block(ESItems.HAZE_ICE_BRICK_SLAB.get());
      this.block(ESItems.HAZE_ICE_BRICK_STAIRS.get());
      this.wall(ESItems.HAZE_ICE_BRICK_WALL.get(), ESItems.HAZE_ICE_BRICKS.get());
      this.basicItem(ESItems.HAZE_ICE_LANTERN.get());
      this.block(ESItems.REINFORCED_ICE.get());
      this.basicItem(ESItems.REINFORCED_ICE_PANE.get(), this.blockTextureFromItem(ESItems.REINFORCED_ICE.get()));
      this.basicItem(ESItems.ICICLE.get(), this.blockTextureFromItem(ESItems.ICICLE.get()).withSuffix("_tip"));
      this.layeredBlock(ESItems.ASHEN_SNOW.get());
      this.basicItem(ESItems.ASHEN_SNOWBALL.get());
      this.basicItem(ESItems.FROZEN_BOMB.get());
      this.block(ESItems.NEBULAITE.get());
      this.block(ESItems.NEBULAITE_BRICKS.get());
      this.block(ESItems.NEBULAITE_BRICK_SLAB.get());
      this.block(ESItems.NEBULAITE_BRICK_STAIRS.get());
      this.wall(ESItems.NEBULAITE_BRICK_WALL.get(), ESItems.NEBULAITE_BRICKS.get());
      this.block(ESItems.CHISELED_NEBULAITE_BRICKS.get());
      this.basicItem(ESItems.STARCORE.get());
      this.block(ESItems.STARCORE_BLOCK.get());
      this.block(ESItems.BLAZING_STARCORE_BLOCK.get());
      this.block(ESItems.STARCORE_LIGHT.get());
      this.block(ESItems.GRIMSTONE_STARCORE_ORE.get());
      this.block(ESItems.VOIDSTONE_STARCORE_ORE.get());
      this.block(ESItems.ETERNAL_ICE_STARCORE_ORE.get());
      this.block(ESItems.HAZE_ICE_STARCORE_ORE.get());
      this.basicItem(ESItems.CINDER_BRICK.get());
      this.block(ESItems.RADIANITE.get());
      this.block(ESItems.RADIANITE_SLAB.get());
      this.block(ESItems.RADIANITE_STAIRS.get());
      this.wall(ESItems.RADIANITE_WALL.get(), ESItems.RADIANITE.get());
      this.block(ESItems.COBBLED_RADIANITE.get());
      this.block(ESItems.COBBLED_RADIANITE_SLAB.get());
      this.block(ESItems.COBBLED_RADIANITE_STAIRS.get());
      this.wall(ESItems.COBBLED_RADIANITE_WALL.get(), ESItems.COBBLED_RADIANITE.get());
      this.block(ESItems.RADIANITE_PILLAR.get());
      this.block(ESItems.POLISHED_RADIANITE.get());
      this.block(ESItems.POLISHED_RADIANITE_SLAB.get());
      this.block(ESItems.POLISHED_RADIANITE_STAIRS.get());
      this.wall(ESItems.POLISHED_RADIANITE_WALL.get(), ESItems.POLISHED_RADIANITE.get());
      this.block(ESItems.RADIANITE_BRICKS.get());
      this.block(ESItems.RADIANITE_BRICK_SLAB.get());
      this.block(ESItems.RADIANITE_BRICK_STAIRS.get());
      this.wall(ESItems.RADIANITE_BRICK_WALL.get(), ESItems.RADIANITE_BRICKS.get());
      this.block(ESItems.CHISELED_RADIANITE.get());
      this.basicItem(ESItems.FLARE_BRICK.get());
      this.block(ESItems.FLARE_BRICKS.get());
      this.block(ESItems.FLARE_BRICK_SLAB.get());
      this.block(ESItems.FLARE_BRICK_STAIRS.get());
      this.wall(ESItems.FLARE_BRICK_WALL.get(), ESItems.FLARE_BRICKS.get());
      this.block(ESItems.CUT_FLARE_BRICKS.get());
      this.block(ESItems.CUT_FLARE_BRICK_SLAB.get());
      this.block(ESItems.CUT_FLARE_BRICK_STAIRS.get());
      this.wall(ESItems.CUT_FLARE_BRICK_WALL.get(), ESItems.CUT_FLARE_BRICKS.get());
      this.block(ESItems.FLARE_TILES.get());
      this.block(ESItems.FLARE_TILE_SLAB.get());
      this.block(ESItems.FLARE_TILE_STAIRS.get());
      this.wall(ESItems.FLARE_TILE_WALL.get(), ESItems.FLARE_TILES.get());
      this.block(ESItems.CUT_FLARE_TILES.get());
      this.block(ESItems.CUT_FLARE_TILE_SLAB.get());
      this.block(ESItems.CUT_FLARE_TILE_STAIRS.get());
      this.wall(ESItems.CUT_FLARE_TILE_WALL.get(), ESItems.CUT_FLARE_TILES.get());
      this.block(ESItems.CHISELED_FLARE_PILLAR.get());
      this.block(ESItems.FLARE_BRICKS.get());
      this.block(ESItems.FLARE_BRICK_SLAB.get());
      this.block(ESItems.FLARE_BRICK_STAIRS.get());
      this.wall(ESItems.FLARE_BRICK_WALL.get(), ESItems.FLARE_BRICKS.get());
      this.block(ESItems.CHISELED_FLARE_PILLAR.get());
      this.block(ESItems.STELLAGMITE.get());
      this.block(ESItems.STELLAGMITE_SLAB.get());
      this.block(ESItems.STELLAGMITE_STAIRS.get());
      this.wall(ESItems.STELLAGMITE_WALL.get(), ESItems.STELLAGMITE.get());
      this.block(ESItems.MOLTEN_STELLAGMITE.get());
      this.block(ESItems.MOLTEN_STELLAGMITE_SLAB.get());
      this.block(ESItems.MOLTEN_STELLAGMITE_STAIRS.get());
      this.wall(ESItems.MOLTEN_STELLAGMITE_WALL.get(), ESItems.MOLTEN_STELLAGMITE.get());
      this.block(ESItems.POLISHED_STELLAGMITE.get());
      this.block(ESItems.POLISHED_STELLAGMITE_SLAB.get());
      this.block(ESItems.POLISHED_STELLAGMITE_STAIRS.get());
      this.wall(ESItems.POLISHED_STELLAGMITE_WALL.get(), ESItems.POLISHED_STELLAGMITE.get());
      this.block(ESItems.ABYSSLATE.get());
      this.block(ESItems.POLISHED_ABYSSLATE.get());
      this.block(ESItems.POLISHED_ABYSSLATE_SLAB.get());
      this.block(ESItems.POLISHED_ABYSSLATE_STAIRS.get());
      this.wall(ESItems.POLISHED_ABYSSLATE_WALL.get(), ESItems.POLISHED_ABYSSLATE.get());
      this.block(ESItems.POLISHED_ABYSSLATE_BRICKS.get());
      this.block(ESItems.POLISHED_ABYSSLATE_BRICK_SLAB.get());
      this.block(ESItems.POLISHED_ABYSSLATE_BRICK_STAIRS.get());
      this.wall(ESItems.POLISHED_ABYSSLATE_BRICK_WALL.get(), ESItems.POLISHED_ABYSSLATE_BRICKS.get());
      this.block(ESItems.CHISELED_POLISHED_ABYSSLATE.get());
      this.block(ESItems.ABYSSAL_MAGMA_BLOCK.get());
      this.block(ESItems.ABYSSAL_GEYSER.get());
      this.block(ESItems.THERMABYSSLATE.get());
      this.block(ESItems.POLISHED_THERMABYSSLATE.get());
      this.block(ESItems.POLISHED_THERMABYSSLATE_SLAB.get());
      this.block(ESItems.POLISHED_THERMABYSSLATE_STAIRS.get());
      this.wall(ESItems.POLISHED_THERMABYSSLATE_WALL.get(), ESItems.POLISHED_THERMABYSSLATE.get());
      this.block(ESItems.POLISHED_THERMABYSSLATE_BRICKS.get());
      this.block(ESItems.POLISHED_THERMABYSSLATE_BRICK_SLAB.get());
      this.block(ESItems.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get());
      this.wall(ESItems.POLISHED_THERMABYSSLATE_BRICK_WALL.get(), ESItems.POLISHED_THERMABYSSLATE_BRICKS.get());
      this.block(ESItems.CHISELED_POLISHED_THERMABYSSLATE.get());
      this.block(ESItems.THERMABYSSAL_MAGMA_BLOCK.get());
      this.block(ESItems.THERMABYSSAL_GEYSER.get());
      this.block(ESItems.CRYOBYSSLATE.get());
      this.block(ESItems.POLISHED_CRYOBYSSLATE.get());
      this.block(ESItems.POLISHED_CRYOBYSSLATE_SLAB.get());
      this.block(ESItems.POLISHED_CRYOBYSSLATE_STAIRS.get());
      this.wall(ESItems.POLISHED_CRYOBYSSLATE_WALL.get(), ESItems.POLISHED_CRYOBYSSLATE.get());
      this.block(ESItems.POLISHED_CRYOBYSSLATE_BRICKS.get());
      this.block(ESItems.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get());
      this.block(ESItems.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get());
      this.wall(ESItems.POLISHED_CRYOBYSSLATE_BRICK_WALL.get(), ESItems.POLISHED_CRYOBYSSLATE_BRICKS.get());
      this.block(ESItems.CHISELED_POLISHED_CRYOBYSSLATE.get());
      this.block(ESItems.CRYOBYSSAL_MAGMA_BLOCK.get());
      this.block(ESItems.CRYOBYSSAL_GEYSER.get());
      this.block(ESItems.NIGHTFALL_MUD.get());
      this.block(ESItems.GLOWING_NIGHTFALL_MUD.get());
      this.block(ESItems.PACKED_NIGHTFALL_MUD.get());
      this.block(ESItems.NIGHTFALL_MUD_BRICKS.get());
      this.block(ESItems.NIGHTFALL_MUD_BRICK_SLAB.get());
      this.block(ESItems.NIGHTFALL_MUD_BRICK_STAIRS.get());
      this.wall(ESItems.NIGHTFALL_MUD_BRICK_WALL.get(), ESItems.NIGHTFALL_MUD_BRICKS.get());
      this.block(ESItems.TWILIGHT_SAND.get());
      this.block(ESItems.TWILIGHT_SANDSTONE.get());
      this.block(ESItems.TWILIGHT_SANDSTONE_SLAB.get());
      this.block(ESItems.TWILIGHT_SANDSTONE_STAIRS.get());
      this.wall(ESItems.TWILIGHT_SANDSTONE_WALL.get(), ESItems.TWILIGHT_SANDSTONE.get());
      this.block(ESItems.CUT_TWILIGHT_SANDSTONE.get());
      this.block(ESItems.CUT_TWILIGHT_SANDSTONE_SLAB.get());
      this.block(ESItems.CUT_TWILIGHT_SANDSTONE_STAIRS.get());
      this.wall(ESItems.CUT_TWILIGHT_SANDSTONE_WALL.get(), ESItems.CUT_TWILIGHT_SANDSTONE.get());
      this.block(ESItems.CHISELED_TWILIGHT_SANDSTONE.get());
      this.block(ESItems.DUSTED_GRAVEL.get());
      this.basicItem(ESItems.DUSTED_SHARD.get());
      this.block(ESItems.DUSTED_BRICKS.get());
      this.block(ESItems.DUSTED_BRICK_SLAB.get());
      this.block(ESItems.DUSTED_BRICK_STAIRS.get());
      this.wall(ESItems.DUSTED_BRICK_WALL.get(), ESItems.DUSTED_BRICKS.get());
      this.block(ESItems.MOSSY_DUSTED_GRAVEL.get());
      this.block(ESItems.GLOWING_MOSSY_DUSTED_GRAVEL.get());
      this.withExistingParent(
         this.name(ESItems.SUSPICIOUS_DUSTED_GRAVEL.get()), this.modLoc("block/" + this.name(ESItems.SUSPICIOUS_DUSTED_GRAVEL.get()) + "_0")
      );
      this.block(ESItems.DIMSLAG.get());
      this.withExistingParent(this.name(ESItems.SUSPICIOUS_DIMSLAG.get()), this.modLoc("block/" + this.name(ESItems.SUSPICIOUS_DIMSLAG.get()) + "_0"));
      this.flatBlockTexture(ESItems.STARLIGHT_FLOWER.get());
      this.flatBlockTexture(ESItems.AUREATE_FLOWER.get());
      this.flatBlockTexture(ESItems.CONEBLOOM.get());
      this.flatBlockTexture(ESItems.NIGHTFAN.get());
      this.flatBlockTexture(ESItems.PINK_ROSE.get());
      this.flatBlockTexture(ESItems.STARLIGHT_TORCHFLOWER.get());
      this.basicItem(ESItems.NIGHTFAN_BUSH.get(), this.blockTextureFromItem(ESItems.NIGHTFAN_BUSH.get()).withSuffix("_top"));
      this.basicItem(ESItems.PINK_ROSE_BUSH.get(), this.blockTextureFromItem(ESItems.PINK_ROSE_BUSH.get()).withSuffix("_top"));
      this.flatBlockTexture(ESItems.NIGHT_SPROUTS.get());
      this.flatBlockTexture(ESItems.GLOWING_NIGHT_SPROUTS.get());
      this.flatBlockTexture(ESItems.SMALL_NIGHT_SPROUTS.get());
      this.flatBlockTexture(ESItems.SMALL_GLOWING_NIGHT_SPROUTS.get());
      this.flatBlockTexture(ESItems.LUNAR_GRASS.get());
      this.flatBlockTexture(ESItems.GLOWING_LUNAR_GRASS.get());
      this.flatBlockTexture(ESItems.CRESCENT_GRASS.get());
      this.flatBlockTexture(ESItems.GLOWING_CRESCENT_GRASS.get());
      this.flatBlockTexture(ESItems.PARASOL_GRASS.get());
      this.flatBlockTexture(ESItems.GLOWING_PARASOL_GRASS.get());
      this.flatBlockTexture(ESItems.LUNAR_BUSH.get());
      this.flatBlockTexture(ESItems.GLOWING_LUNAR_BUSH.get());
      this.basicItem(ESItems.TALL_CRESCENT_GRASS.get(), this.blockTextureFromItem(ESItems.TALL_CRESCENT_GRASS.get()).withSuffix("_top"));
      this.basicItem(ESItems.TALL_GLOWING_CRESCENT_GRASS.get(), this.blockTextureFromItem(ESItems.TALL_GLOWING_CRESCENT_GRASS.get()).withSuffix("_top"));
      this.basicItem(ESItems.LUNAR_REED.get(), this.blockTextureFromItem(ESItems.LUNAR_REED.get()).withSuffix("_top"));
      this.flatBlockTexture(ESItems.WHISPERBLOOM.get());
      this.flatBlockTexture(ESItems.GLADESPIKE.get());
      this.flatBlockTexture(ESItems.VIVIDSTALK.get());
      this.basicItem(ESItems.TALL_GLADESPIKE.get(), this.blockTextureFromItem(ESItems.TALL_GLADESPIKE.get()).withSuffix("_top"));
      this.flatBlockTexture(ESItems.MOONLIGHT_BUSH.get());
      this.basicItem(ESItems.GLINTGRASS.get(), this.blockTextureFromItem(ESItems.GLINTGRASS.get()).withSuffix("_top"));
      this.flatBlockTexture(ESItems.GLOWING_MUSHROOM.get());
      this.cubeAll(ESItems.GLOWING_MUSHROOM_BLOCK.get());
      this.cubeAll(ESItems.GLOWING_MUSHROOM_STEM.get());
      this.flatBlockTexture(ESItems.SHINING_MUSHROOM.get());
      this.cubeAll(ESItems.SHINING_MUSHROOM_BLOCK.get());
      this.cubeAll(ESItems.SHINING_MUSHROOM_STEM.get());
      this.basicItem(ESItems.LUNAR_BERRIES.get());
      this.flatBlockTexture(ESItems.CAVE_MOSS.get());
      this.block(ESItems.CAVE_MOSS_BLOCK.get());
      this.block(ESItems.CAVE_MOSS_CARPET.get());
      this.flatBlockTexture(ESItems.BOULDERSHROOM.get());
      this.cubeAll(ESItems.BOULDERSHROOM_BLOCK.get());
      this.cubeAll(ESItems.BOULDERSHROOM_STEM.get());
      this.flatBlockTexture(ESItems.BOULDERSHROOM_ROOTS.get());
      this.basicItem(ESItems.BOULDERSHROOM_STEW.get());
      this.flatBlockTexture(ESItems.SWAMP_ROSE.get());
      this.flatBlockTexture(ESItems.FANTABUD.get());
      this.flatBlockTexture(ESItems.GREEN_FANTABUD.get());
      this.flatBlockTexture(ESItems.FANTAFERN.get());
      this.flatBlockTexture(ESItems.GREEN_FANTAFERN.get());
      this.flatBlockTexture(ESItems.FANTAGRASS.get());
      this.flatBlockTexture(ESItems.GREEN_FANTAGRASS.get());
      this.basicItem(ESItems.HANGING_FANTAGRASS.get());
      this.flatBlockTexture(ESItems.ORANGE_SCARLET_BUD.get());
      this.flatBlockTexture(ESItems.PURPLE_SCARLET_BUD.get());
      this.flatBlockTexture(ESItems.RED_SCARLET_BUD.get());
      this.flatBlockTexture(ESItems.SCARLET_GRASS.get());
      this.flatBlockTexture(ESItems.MAUVE_FERN.get());
      this.flatBlockTexture(ESItems.WITHERED_STARLIGHT_FLOWER.get());
      this.flatBlockTexture(ESItems.AMARAMBER_GRASS.get());
      this.flatBlockTexture(ESItems.AMARAMBER_GRASS_BUSH.get());
      this.flatBlockTexture(ESItems.GLOOMCANDLE_ROOT.get());
      this.block(ESItems.RED_STARLIGHT_CRYSTAL_BLOCK.get());
      this.block(ESItems.BLUE_STARLIGHT_CRYSTAL_BLOCK.get());
      this.flatBlockTexture(ESItems.RED_STARLIGHT_CRYSTAL_CLUSTER.get());
      this.flatBlockTexture(ESItems.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get());
      this.basicItem(ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get());
      this.basicItem(ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get());
      this.block(ESItems.RED_STARLIGHT_CRYSTAL_LANTERN.get());
      this.block(ESItems.BLUE_STARLIGHT_CRYSTAL_LANTERN.get());
      this.flatBlockTexture(ESItems.DEAD_LUNAR_BUSH.get());
      this.flatBlockTexture(ESItems.DESERT_AMETHYSIA.get());
      this.flatBlockTexture(ESItems.WITHERED_DESERT_AMETHYSIA.get());
      this.flatBlockTexture(ESItems.SUNSET_THORNBLOOM.get());
      this.flatBlockTexture(ESItems.AMETHYSIA_GRASS.get());
      this.block(ESItems.LUNARIS_CACTUS.get());
      this.block(ESItems.LUNARIS_CACTUS_GEL_BLOCK.get());
      this.basicItem(ESItems.LUNARIS_CACTUS_FRUIT.get());
      this.basicItem(ESItems.LUNARIS_CACTUS_GEL.get());
      this.block(ESItems.CARVED_LUNARIS_CACTUS_FRUIT.get());
      this.block(ESItems.LUNARIS_CACTUS_FRUIT_LANTERN.get());
      this.flatBlockTexture(ESItems.BLOOMING_RED_STARLIGHT_CRYSTAL_CLUSTER.get());
      this.flatBlockTexture(ESItems.BLOOMING_BLUE_STARLIGHT_CRYSTAL_CLUSTER.get());
      this.flatBlockTexture(ESItems.RED_CRYSTALFLEUR.get());
      this.flatBlockTexture(ESItems.BLUE_CRYSTALFLEUR.get());
      this.flatBlockTexture(ESItems.RED_CRYSTALFLEUR_VINE.get());
      this.flatBlockTexture(ESItems.BLUE_CRYSTALFLEUR_VINE.get());
      this.block(ESItems.RED_CRYSTAL_MOSS_BLOCK.get());
      this.block(ESItems.BLUE_CRYSTAL_MOSS_BLOCK.get());
      this.block(ESItems.RED_CRYSTAL_MOSS_CARPET.get());
      this.block(ESItems.BLUE_CRYSTAL_MOSS_CARPET.get());
      this.flatBlockTexture(ESItems.FIRE_ORCHID.get());
      this.flatBlockTexture(ESItems.BLAZEBANK_GRASS.get());
      this.flatBlockTexture(ESItems.MOONLIGHT_LILY_PAD.get());
      this.flatBlockTexture(ESItems.STARLIT_LILY_PAD.get());
      this.flatBlockTexture(ESItems.MOONLIGHT_DUCKWEED.get());
      this.basicItem(ESItems.ABYSSAL_FRUIT.get());
      this.block(ESItems.ORBFLORA.get());
      this.block(ESItems.ORBFLORA_LIGHT.get());
      this.basicItem(ESItems.SPIRAL_KELP.get());
      this.flatBlockTexture(ESItems.SEA_ROSA.get());
      this.basicItem(ESItems.WICK_GRASS.get(), this.blockTextureFromItem(ESItems.WICK_GRASS.get()).withSuffix("_top"));
      this.flatBlockTexture(ESItems.LUMENSTEM.get());
      this.flatBlockTexture(ESItems.MARIMOLD.get());
      this.block(ESItems.MARIMOLD_BLOCK.get());
      this.cubeAll(ESItems.MARIMOLD_STEM.get());
      this.basicItem(ESItems.CIRCULUSH.get());
      this.basicItem(ESItems.STONETT.get());
      this.basicItem(ESItems.LUMINIS.get());
      this.basicItem(ESItems.GLOWLIS.get());
      this.basicItem(ESItems.GLOREED.get());
      this.basicItem(ESItems.STARLIGHT_SEAGRASS.get());
      this.flatBlockTexture(ESItems.JINGLING_PICKLE.get());
      this.flatBlockTexture(ESItems.DEAD_TENTACLES_CORAL.get());
      this.flatBlockTexture(ESItems.TENTACLES_CORAL.get());
      this.flatBlockTexture(ESItems.DEAD_TENTACLES_CORAL_FAN.get());
      this.flatBlockTexture(ESItems.TENTACLES_CORAL_FAN.get());
      this.block(ESItems.DEAD_TENTACLES_CORAL_BLOCK.get());
      this.block(ESItems.TENTACLES_CORAL_BLOCK.get());
      this.flatBlockTexture(ESItems.DEAD_GOLDEN_CORAL.get());
      this.flatBlockTexture(ESItems.GOLDEN_CORAL.get());
      this.flatBlockTexture(ESItems.DEAD_GOLDEN_CORAL_FAN.get());
      this.flatBlockTexture(ESItems.GOLDEN_CORAL_FAN.get());
      this.block(ESItems.DEAD_GOLDEN_CORAL_BLOCK.get());
      this.block(ESItems.GOLDEN_CORAL_BLOCK.get());
      this.flatBlockTexture(ESItems.DEAD_CRYSTALLUM_CORAL.get());
      this.flatBlockTexture(ESItems.CRYSTALLUM_CORAL.get());
      this.flatBlockTexture(ESItems.DEAD_CRYSTALLUM_CORAL_FAN.get());
      this.flatBlockTexture(ESItems.CRYSTALLUM_CORAL_FAN.get());
      this.block(ESItems.DEAD_CRYSTALLUM_CORAL_BLOCK.get());
      this.block(ESItems.CRYSTALLUM_CORAL_BLOCK.get());
      this.cubeAll(ESItems.VELVETUMOSS.get());
      this.basicItem(ESItems.VELVETUMOSS_BALL.get());
      this.flatBlockTexture(ESItems.VELVETUMOSS_VILLI.get());
      this.block(ESItems.RED_VELVETUMOSS.get());
      this.flatBlockTexture(ESItems.RED_VELVETUMOSS_VILLI.get());
      this.flatBlockTexture(ESItems.RED_VELVETUMOSS_FLOWER.get());
      this.flatBlockTexture(ESItems.CRYSTALLIZED_LUNAR_GRASS.get());
      this.flatBlockTexture(ESItems.RED_CRYSTAL_ROOTS.get());
      this.flatBlockTexture(ESItems.BLUE_CRYSTAL_ROOTS.get());
      this.basicItem(ESItems.TWILVEWRYM_HERB.get(), this.blockTextureFromItem(ESItems.TWILVEWRYM_HERB.get()).withSuffix("_top"));
      this.basicItem(ESItems.STELLAFLY_BUSH.get(), this.blockTextureFromItem(ESItems.STELLAFLY_BUSH.get()).withSuffix("_top"));
      this.basicItem(ESItems.GLIMMERFLY_BUSH.get(), this.blockTextureFromItem(ESItems.GLIMMERFLY_BUSH.get()).withSuffix("_top"));
      this.flatBlockTexture(ESItems.GOLDEN_GRASS.get());
      this.flatBlockTexture(ESItems.TALL_GOLDEN_GRASS.get());
      this.block(ESItems.NIGHTFALL_DIRT.get());
      this.block(ESItems.NIGHTFALL_FARMLAND.get());
      this.block(ESItems.NIGHTFALL_DIRT_PATH.get());
      this.block(ESItems.NIGHTFALL_GRASS_BLOCK.get());
      this.block(ESItems.NIGHTFALL_PODZOL.get());
      this.block(ESItems.TENACIOUS_NIGHTFALL_GRASS_BLOCK.get());
      this.block(ESItems.FANTASY_GRASS_BLOCK.get());
      this.block(ESItems.FANTASY_GRASS_CARPET.get());
      this.block(ESItems.GOLDEN_GRASS_BLOCK.get());
      this.basicItem(ESItems.CRINOA_SEEDS.get());
      this.basicItem(ESItems.CRINOA.get());
      this.block(ESItems.CRINOA_BALE.get());
      this.basicItem(ESItems.CRINOA_BALL.get());
      this.basicItem(ESItems.CRINOA_PORRIDGE.get());
      this.basicItem(ESItems.NOCTURNAL_MILLET_SEEDS.get());
      this.basicItem(ESItems.NOCTURNAL_MILLET.get());
      this.basicItem(ESItems.FORGOTTEN_NOCTURNAL_MILLET.get());
      this.basicItem(ESItems.POPPED_NOCTURNAL_MILLET_BUCKET.get());
      this.basicItem(ESItems.ROASTED_FORGOTTEN_NOCTURNAL_MILLET.get());
      this.basicItem(ESItems.CRETEOR_HIDE.get());
      this.basicItem(ESItems.RAW_AETHERSENT.get());
      this.basicItem(ESItems.AETHERSENT_INGOT.get());
      this.basicItem(ESItems.AETHERSENT_NUGGET.get());
      this.block(ESItems.RAW_AETHERSENT_BLOCK.get());
      this.block(ESItems.AETHERSENT_BLOCK.get());
      this.basicItem(ESItems.AETHERSENT_ARROW.get());
      this.handheld(ESItems.RAGE_OF_STARS.get());
      this.bow(ESItems.STARFALL_LONGBOW.get());
      this.armorWithTrim((ArmorItem)ESItems.AETHERSENT_HOOD.get());
      this.basicItem(ESItems.AETHERSENT_CAPE.get());
      this.basicItem(ESItems.AETHERSENT_BOTTOMS.get());
      this.basicItem(ESItems.AETHERSENT_BOOTS.get());
      this.galacticQuiver(ESItems.GALACTIC_QUIVER.get());
      this.basicItem(ESItems.AETHERSTRIKE_ROCKET.get());
      this.block(ESItems.SPRINGSTONE.get());
      this.block(ESItems.SPRINGSTONE_SLAB.get());
      this.block(ESItems.SPRINGSTONE_STAIRS.get());
      this.wall(ESItems.SPRINGSTONE_WALL.get(), ESItems.SPRINGSTONE.get());
      this.block(ESItems.SPRINGSTONE_BRICKS.get());
      this.block(ESItems.SPRINGSTONE_BRICK_SLAB.get());
      this.block(ESItems.SPRINGSTONE_BRICK_STAIRS.get());
      this.wall(ESItems.SPRINGSTONE_BRICK_WALL.get(), ESItems.SPRINGSTONE_BRICKS.get());
      this.block(ESItems.POLISHED_SPRINGSTONE.get());
      this.block(ESItems.POLISHED_SPRINGSTONE_SLAB.get());
      this.block(ESItems.POLISHED_SPRINGSTONE_STAIRS.get());
      this.wall(ESItems.POLISHED_SPRINGSTONE_WALL.get(), ESItems.POLISHED_SPRINGSTONE.get());
      this.block(ESItems.CHISELED_SPRINGSTONE.get());
      this.block(ESItems.THERMAL_SPRINGSTONE.get());
      this.block(ESItems.THERMAL_SPRINGSTONE_SLAB.get());
      this.block(ESItems.THERMAL_SPRINGSTONE_STAIRS.get());
      this.wall(ESItems.THERMAL_SPRINGSTONE_WALL.get(), ESItems.THERMAL_SPRINGSTONE.get());
      this.block(ESItems.THERMAL_SPRINGSTONE_BRICKS.get());
      this.block(ESItems.THERMAL_SPRINGSTONE_BRICK_SLAB.get());
      this.block(ESItems.THERMAL_SPRINGSTONE_BRICK_STAIRS.get());
      this.wall(ESItems.THERMAL_SPRINGSTONE_BRICK_WALL.get(), ESItems.THERMAL_SPRINGSTONE_BRICKS.get());
      this.basicItem(ESItems.THERMAL_SPRINGSTONE_INGOT.get());
      this.handheld(ESItems.THERMAL_SPRINGSTONE_SWORD.get());
      this.handheld(ESItems.THERMAL_SPRINGSTONE_PICKAXE.get());
      this.handheld(ESItems.THERMAL_SPRINGSTONE_AXE.get());
      this.handheld(ESItems.THERMAL_SPRINGSTONE_HOE.get());
      this.handheld(ESItems.THERMAL_SPRINGSTONE_SHOVEL.get());
      this.largeHandheld(ESItems.THERMAL_SPRINGSTONE_SCYTHE.get());
      this.inventoryHandheld(ESItems.THERMAL_SPRINGSTONE_SCYTHE.get());
      this.inventoryHandheld(ESItems.THERMAL_SPRINGSTONE_HAMMER.get());
      this.basicItem(ESItems.THERMAL_SPRINGSTONE_HELMET.get());
      this.basicItem(ESItems.THERMAL_SPRINGSTONE_CHESTPLATE.get());
      this.basicItem(ESItems.THERMAL_SPRINGSTONE_LEGGINGS.get());
      this.basicItem(ESItems.THERMAL_SPRINGSTONE_BOOTS.get());
      this.block(ESItems.GLACITE.get());
      this.basicItem(ESItems.GLACITE_SHARD.get());
      this.block(ESItems.GLACITE_BLOCK.get());
      this.basicItem(ESItems.GLACITE_ARROW.get());
      this.handheld(ESItems.GLACITE_SWORD.get());
      this.handheld(ESItems.GLACITE_PICKAXE.get());
      this.handheld(ESItems.GLACITE_AXE.get());
      this.handheld(ESItems.GLACITE_HOE.get());
      this.handheld(ESItems.GLACITE_SHOVEL.get());
      this.largeHandheld(ESItems.GLACITE_SCYTHE.get());
      this.inventoryHandheld(ESItems.GLACITE_SCYTHE.get());
      this.armorWithTrim((ArmorItem)ESItems.GLACITE_HELMET.get());
      this.armorWithTrim((ArmorItem)ESItems.GLACITE_CHESTPLATE.get());
      this.armorWithTrim((ArmorItem)ESItems.GLACITE_LEGGINGS.get());
      this.armorWithTrim((ArmorItem)ESItems.GLACITE_BOOTS.get());
      this.block(ESItems.GRIMSTONE_STARLIT_DIAMOND_ORE.get());
      this.block(ESItems.VOIDSTONE_STARLIT_DIAMOND_ORE.get());
      this.block(ESItems.ETERNAL_ICE_STARLIT_DIAMOND_ORE.get());
      this.block(ESItems.HAZE_ICE_STARLIT_DIAMOND_ORE.get());
      this.basicItem(ESItems.STARLIT_DIAMOND.get());
      this.block(ESItems.STARLIT_DIAMOND_BLOCK.get());
      this.handheld(ESItems.STARLIT_DIAMOND_SWORD.get());
      this.handheld(ESItems.STARLIT_DIAMOND_PICKAXE.get());
      this.handheld(ESItems.STARLIT_DIAMOND_AXE.get());
      this.handheld(ESItems.STARLIT_DIAMOND_HOE.get());
      this.handheld(ESItems.STARLIT_DIAMOND_SHOVEL.get());
      this.basicItem(ESItems.STARLIT_DIAMOND_HELMET.get());
      this.basicItem(ESItems.STARLIT_DIAMOND_CHESTPLATE.get());
      this.basicItem(ESItems.STARLIT_DIAMOND_LEGGINGS.get());
      this.basicItem(ESItems.STARLIT_DIAMOND_BOOTS.get());
      this.block(ESItems.GRIMSTONE_DEEPSILVER_ORE.get());
      this.block(ESItems.VOIDSTONE_DEEPSILVER_ORE.get());
      this.block(ESItems.ETERNAL_ICE_DEEPSILVER_ORE.get());
      this.block(ESItems.HAZE_ICE_DEEPSILVER_ORE.get());
      this.block(ESItems.NIGHTFALL_MUD_DEEPSILVER_ORE.get());
      this.block(ESItems.PACKED_NIGHTFALL_MUD_DEEPSILVER_ORE.get());
      this.basicItem(ESItems.RAW_DEEPSILVER.get());
      this.basicItem(ESItems.DEEPSILVER_INGOT.get());
      this.basicItem(ESItems.DEEPSILVER_NUGGET.get());
      this.block(ESItems.RAW_DEEPSILVER_BLOCK.get());
      this.block(ESItems.DEEPSILVER_BLOCK.get());
      this.block(ESItems.DEEPSILVER_GRATE.get());
      this.flatBlockTexture(ESItems.DEEPSILVER_BARS.get());
      this.handheld(ESItems.DEEPSILVER_SWORD.get());
      this.handheld(ESItems.DEEPSILVER_PICKAXE.get());
      this.handheld(ESItems.DEEPSILVER_AXE.get());
      this.handheld(ESItems.DEEPSILVER_HOE.get());
      this.handheld(ESItems.DEEPSILVER_SHOVEL.get());
      this.handheld(ESItems.DEEPSILVER_SICKLE.get());
      this.brush(ESItems.DEEPSILVER_BRUSH.get());
      this.armorWithTrim((ArmorItem)ESItems.DEEPSILVER_HELMET.get());
      this.armorWithTrim((ArmorItem)ESItems.DEEPSILVER_CHESTPLATE.get());
      this.basicItem(ESItems.DEEPSILVER_LEGGINGS.get());
      this.basicItem(ESItems.DEEPSILVER_BOOTS.get());
      this.basicItem(ESItems.UNREALIUM_INGOT.get());
      this.basicItem(ESItems.UNREALIUM_NUGGET.get());
      this.block(ESItems.UNREALIUM_BLOCK.get());
      this.flatBlockTexture(ESItems.UNREALIUM_BARS.get());
      this.handheld(ESItems.UNREALIUM_SWORD.get());
      this.handheld(ESItems.UNREALIUM_PICKAXE.get());
      this.handheld(ESItems.UNREALIUM_AXE.get());
      this.handheld(ESItems.UNREALIUM_HOE.get());
      this.handheld(ESItems.UNREALIUM_SHOVEL.get());
      this.handheld(ESItems.UNREALIUM_SICKLE.get());
      this.crossbow(ESItems.UNREALIUM_CROSSBOW.get());
      this.basicItem(ESItems.UNREALIUM_HELMET.get());
      this.basicItem(ESItems.UNREALIUM_CHESTPLATE.get());
      this.basicItem(ESItems.UNREALIUM_LEGGINGS.get());
      this.basicItem(ESItems.UNREALIUM_BOOTS.get());
      this.block(ESItems.GRIMSTONE_MALARITE_ORE.get());
      this.block(ESItems.VOIDSTONE_MALARITE_ORE.get());
      this.block(ESItems.NIGHTFALL_MUD_MALARITE_ORE.get());
      this.block(ESItems.PACKED_NIGHTFALL_MUD_MALARITE_ORE.get());
      this.basicItem(ESItems.MALARITE.get());
      this.block(ESItems.MALARITE_BLOCK.get());
      this.basicItem(ESItems.MALARITE_ARROW.get());
      this.handheld(ESItems.MALARITE_SWORD.get());
      this.handheld(ESItems.MALARITE_PICKAXE.get());
      this.handheld(ESItems.MALARITE_AXE.get());
      this.handheld(ESItems.MALARITE_HOE.get());
      this.handheld(ESItems.MALARITE_SHOVEL.get());
      this.handheld(ESItems.MALARITE_SICKLE.get());
      this.inventoryHandheld(ESItems.MALARITE_SPEAR.get());
      this.basicItem(ESItems.PUNGENCY_FRUIT_SEEDS.get());
      this.basicItem(ESItems.PUNGENCY_FRUIT.get());
      this.basicItem(ESItems.PUNGENCY_STEW.get());
      this.basicItem(ESItems.SILVER_PUNGENCY_FRUIT.get());
      this.block(ESItems.TEAR_BOMB.get());
      this.basicItem(ESItems.TEAR_BOMB_MINECART.get());
      this.basicItem(ESItems.PUNGENCY_FRUIT_UPGRADE_SMITHING_TEMPLATE.get());
      this.handheld(ESItems.PUNGENCY_FRUIT_AXE.get());
      this.inventoryHandheld(ESItems.PUNGENCY_FRUIT_SPEAR.get());
      this.inventoryHandheld(ESItems.SEEDS_LAUNCHER.get());
      this.basicItem(ESItems.DRYING_RACK.get());
      this.basicItem(ESItems.ROTTEN_FLESH_JERKY.get());
      this.block(ESItems.OAK_STARFIRE_BIRD_AVIARY.get());
      this.block(ESItems.SPRUCE_STARFIRE_BIRD_AVIARY.get());
      this.block(ESItems.BIRCH_STARFIRE_BIRD_AVIARY.get());
      this.block(ESItems.ACACIA_STARFIRE_BIRD_AVIARY.get());
      this.block(ESItems.CHERRY_STARFIRE_BIRD_AVIARY.get());
      this.block(ESItems.JUNGLE_STARFIRE_BIRD_AVIARY.get());
      this.block(ESItems.DARK_OAK_STARFIRE_BIRD_AVIARY.get());
      this.block(ESItems.CRIMSON_STARFIRE_BIRD_AVIARY.get());
      this.block(ESItems.WARPED_STARFIRE_BIRD_AVIARY.get());
      this.block(ESItems.MANGROVE_STARFIRE_BIRD_AVIARY.get());
      this.block(ESItems.BAMBOO_STARFIRE_BIRD_AVIARY.get());
      this.block(ESItems.STARFIRE_BIRD_NEST.get());
      this.block(ESItems.LUNAR_STARFIRE_BIRD_AVIARY.get());
      this.block(ESItems.NORTHLAND_STARFIRE_BIRD_AVIARY.get());
      this.block(ESItems.BANYIN_STARFIRE_BIRD_AVIARY.get());
      this.block(ESItems.SCARLET_STARFIRE_BIRD_AVIARY.get());
      this.block(ESItems.TORREYA_STARFIRE_BIRD_AVIARY.get());
      this.block(ESItems.JINGLESTEM_STARFIRE_BIRD_AVIARY.get());
      this.block(ESItems.CRADLEWOOD_STARFIRE_BIRD_AVIARY.get());
      this.basicItem(ESItems.STARFIRE_BIRD_EGG.get());
      this.basicItem(ESItems.STARFIRE.get());
      this.basicItem(ESItems.STARFIRE_UPGRADE_SMITHING_TEMPLATE.get());
      this.handheld(ESItems.STARFIRE_SWORD.get());
      this.handheld(ESItems.STARFIRE_PICKAXE.get());
      this.handheld(ESItems.STARFIRE_AXE.get());
      this.handheld(ESItems.STARFIRE_HOE.get());
      this.handheld(ESItems.STARFIRE_SHOVEL.get());
      this.largeHandheld(ESItems.STARFIRE_SCYTHE.get());
      this.inventoryHandheld(ESItems.STARFIRE_SCYTHE.get());
      this.inventoryHandheld(ESItems.STARFIRE_HAMMER.get());
      this.crossbow(ESItems.STARFIRE_CROSSBOW.get());
      this.block(ESItems.RAW_FLOWGLAZE.get());
      this.block(ESItems.FLOWGLAZE.get());
      this.basicItem(ESItems.FLOWGLAZE_PANE.get(), this.blockTextureFromItem(ESItems.FLOWGLAZE.get()));
      this.block(ESItems.FLOWGLAZE_BRICKS.get());
      this.block(ESItems.FLOWGLAZE_BRICK_SLAB.get());
      this.block(ESItems.FLOWGLAZE_BRICK_STAIRS.get());
      this.wall(ESItems.FLOWGLAZE_BRICK_WALL.get(), ESItems.FLOWGLAZE_BRICKS.get());
      this.basicItem(ESItems.FLOWGLAZE_UPGRADE_SMITHING_TEMPLATE.get());
      this.handheld(ESItems.FLOWGLAZE_SWORD.get());
      this.handheld(ESItems.FLOWGLAZE_PICKAXE.get());
      this.handheld(ESItems.FLOWGLAZE_AXE.get());
      this.handheld(ESItems.FLOWGLAZE_HOE.get());
      this.handheld(ESItems.FLOWGLAZE_SHOVEL.get());
      this.largeHandheld(ESItems.FLOWGLAZE_SCYTHE.get());
      this.inventoryHandheld(ESItems.FLOWGLAZE_SCYTHE.get());
      this.bow(ESItems.FLOWGLAZE_BOW.get());
      this.block(ESItems.GRIMSTONE_SALTPETER_ORE.get());
      this.block(ESItems.VOIDSTONE_SALTPETER_ORE.get());
      this.block(ESItems.ETERNAL_ICE_SALTPETER_ORE.get());
      this.block(ESItems.HAZE_ICE_SALTPETER_ORE.get());
      this.block(ESItems.SALTPETER_BLOCK.get());
      this.basicItem(ESItems.SALTPETER_POWDER.get());
      this.basicItem(ESItems.SALTPETER_MATCHBOX.get());
      this.basicItem(ESItems.RAW_AMARAMBER.get());
      this.basicItem(ESItems.AMARAMBER_INGOT.get());
      this.basicItem(ESItems.AMARAMBER_NUGGET.get());
      this.block(ESItems.RAW_AMARAMBER_BLOCK.get());
      this.basicItem(ESItems.AMARAMBER_LANTERN.get());
      this.basicItem(ESItems.AMARAMBER_CANDLE.get());
      this.block(ESItems.AMARAMBER_BRICKS.get());
      this.block(ESItems.AMARAMBER_BRICK_SLAB.get());
      this.block(ESItems.AMARAMBER_BRICK_STAIRS.get());
      this.wall(ESItems.AMARAMBER_BRICK_WALL.get(), ESItems.AMARAMBER_BRICKS.get());
      this.block(ESItems.TORREYA_TILES.get());
      this.block(ESItems.TORREYA_TILE_SLAB.get());
      this.block(ESItems.TORREYA_TILE_STAIRS.get());
      this.wall(ESItems.TORREYA_TILE_WALL.get(), ESItems.TORREYA_TILES.get());
      this.basicItem(ESItems.AMARAMBER_ARROW.get());
      this.handheld(ESItems.AMARAMBER_SWORD.get());
      this.handheld(ESItems.AMARAMBER_PICKAXE.get());
      this.handheld(ESItems.AMARAMBER_AXE.get());
      this.handheld(ESItems.AMARAMBER_HOE.get());
      this.handheld(ESItems.AMARAMBER_SHOVEL.get());
      this.handheld(ESItems.AMARAMBER_SICKLE.get());
      this.whip(ESItems.CANDLASH.get());
      this.basicItem(ESItems.AMARAMBER_MASK.get());
      this.basicItem(ESItems.AMARAMBER_CHESTPLATE.get());
      this.basicItem(ESItems.ETHER_BUCKET.get());
      this.block(ESItems.THIOQUARTZ_BLOCK.get());
      this.block(ESItems.BUDDING_THIOQUARTZ.get());
      this.flatBlockTexture(ESItems.THIOQUARTZ_CLUSTER.get());
      this.basicItem(ESItems.THIOQUARTZ_SHARD.get());
      this.basicItem(ESItems.THIOQUARTZ_ARROW.get());
      this.basicItem(ESItems.ETHERIC_EYE.get());
      this.block(ESItems.TOXITE.get());
      this.block(ESItems.TOXITE_SLAB.get());
      this.block(ESItems.TOXITE_STAIRS.get());
      this.wall(ESItems.TOXITE_WALL.get(), ESItems.TOXITE.get());
      this.block(ESItems.TOXITE_BRICKS.get());
      this.block(ESItems.TOXITE_BRICK_SLAB.get());
      this.block(ESItems.TOXITE_BRICK_STAIRS.get());
      this.wall(ESItems.TOXITE_BRICK_WALL.get(), ESItems.TOXITE_BRICKS.get());
      this.block(ESItems.POLISHED_TOXITE.get());
      this.block(ESItems.POLISHED_TOXITE_SLAB.get());
      this.block(ESItems.POLISHED_TOXITE_STAIRS.get());
      this.wall(ESItems.POLISHED_TOXITE_WALL.get(), ESItems.POLISHED_TOXITE.get());
      this.block(ESItems.CHISELED_TOXITE.get());
      this.basicItem(ESItems.ALCHEMIST_MASK.get());
      this.basicItem(ESItems.ALCHEMIST_ROBE.get());
      this.block(ESItems.GRIMSTONE_REDSTONE_ORE.get());
      this.block(ESItems.VOIDSTONE_REDSTONE_ORE.get());
      this.block(ESItems.ETERNAL_ICE_REDSTONE_ORE.get());
      this.block(ESItems.HAZE_ICE_REDSTONE_ORE.get());
      this.basicItem(ESItems.ROOKFISH_BUCKET.get());
      this.basicItem(ESItems.ROOKFISH.get());
      this.basicItem(ESItems.COOKED_ROOKFISH.get());
      this.handheld(ESItems.ROOKFISH_SKEWER.get());
      this.basicItem(ESItems.ROOKFISH_AIR_SAC.get());
      this.basicItem(ESItems.AIR_SAC_ARROW.get());
      this.basicItem(ESItems.AIR_SAC_MASK.get());
      this.armorWithTrim((ArmorItem)ESItems.AIR_SAC_BOOTS.get());
      this.basicItem(ESItems.LUMINOFISH_BUCKET.get());
      this.basicItem(ESItems.LUMINOFISH.get());
      this.basicItem(ESItems.COOKED_LUMINOFISH.get());
      this.basicItem(ESItems.LUMINARIS_BUCKET.get());
      this.basicItem(ESItems.LUMINARIS.get());
      this.basicItem(ESItems.COOKED_LUMINARIS.get());
      this.basicItem(ESItems.STARLIT_PAINTING.get());
      this.block(ESItems.WHITE_YETI_FUR.get());
      this.block(ESItems.ORANGE_YETI_FUR.get());
      this.block(ESItems.MAGENTA_YETI_FUR.get());
      this.block(ESItems.LIGHT_BLUE_YETI_FUR.get());
      this.block(ESItems.YELLOW_YETI_FUR.get());
      this.block(ESItems.LIME_YETI_FUR.get());
      this.block(ESItems.PINK_YETI_FUR.get());
      this.block(ESItems.GRAY_YETI_FUR.get());
      this.block(ESItems.LIGHT_GRAY_YETI_FUR.get());
      this.block(ESItems.CYAN_YETI_FUR.get());
      this.block(ESItems.PURPLE_YETI_FUR.get());
      this.block(ESItems.BLUE_YETI_FUR.get());
      this.block(ESItems.BROWN_YETI_FUR.get());
      this.block(ESItems.GREEN_YETI_FUR.get());
      this.block(ESItems.RED_YETI_FUR.get());
      this.block(ESItems.BLACK_YETI_FUR.get());
      this.block(ESItems.WHITE_YETI_FUR_CARPET.get());
      this.block(ESItems.ORANGE_YETI_FUR_CARPET.get());
      this.block(ESItems.MAGENTA_YETI_FUR_CARPET.get());
      this.block(ESItems.LIGHT_BLUE_YETI_FUR_CARPET.get());
      this.block(ESItems.YELLOW_YETI_FUR_CARPET.get());
      this.block(ESItems.LIME_YETI_FUR_CARPET.get());
      this.block(ESItems.PINK_YETI_FUR_CARPET.get());
      this.block(ESItems.GRAY_YETI_FUR_CARPET.get());
      this.block(ESItems.LIGHT_GRAY_YETI_FUR_CARPET.get());
      this.block(ESItems.CYAN_YETI_FUR_CARPET.get());
      this.block(ESItems.PURPLE_YETI_FUR_CARPET.get());
      this.block(ESItems.BLUE_YETI_FUR_CARPET.get());
      this.block(ESItems.BROWN_YETI_FUR_CARPET.get());
      this.block(ESItems.GREEN_YETI_FUR_CARPET.get());
      this.block(ESItems.RED_YETI_FUR_CARPET.get());
      this.block(ESItems.BLACK_YETI_FUR_CARPET.get());
      this.handheld(ESItems.AURORA_DEER_ANTLER.get());
      this.basicItem(ESItems.AURORA_DEER_STEAK.get());
      this.basicItem(ESItems.COOKED_AURORA_DEER_STEAK.get());
      this.basicItem(ESItems.RATLIN_MEAT.get());
      this.basicItem(ESItems.COOKED_RATLIN_MEAT.get());
      this.basicItem(ESItems.SHADOW_SNAIL_MEAT.get());
      this.basicItem(ESItems.COOKED_SHADOW_SNAIL_MEAT.get());
      this.basicItem(ESItems.SHADOW_SNAIL_SHELL.get());
      this.basicItem(ESItems.SHADOW_SNAIL_SHELL_POWDER.get());
      this.basicItem(ESItems.SHADOW_SNAIL_PIE.get());
      this.basicItem(ESItems.SHADOW_ESCARGOT.get());
      this.basicItem(ESItems.FROZEN_TUBE.get());
      this.basicItem(ESItems.SHIVERING_GEL.get());
      this.basicItem(ESItems.SONAR_BOMB.get());
      this.basicItem(ESItems.NIGHTFALL_SPIDER_EYE.get());
      this.basicItem(ESItems.SEEKER_TENTACLE.get());
      this.whip(ESItems.TENTACLE_SPIKE.get());
      this.templateSkull(ESItems.TANGLED_SKULL.get());
      this.basicItem(ESItems.GLEECH_EGG.get());
      this.shatteredSword(ESItems.SHATTERED_SWORD.get());
      this.basicItem(ESItems.SHATTERED_SWORD_BLADE.get());
      this.basicItem(ESItems.TOOTH_OF_HUNGER.get());
      this.daggerOfHunger(ESItems.DAGGER_OF_HUNGER.get());
      this.basicItem(ESItems.VORACIOUS_ARROW.get());
      this.block(ESItems.TOOTH_OF_HUNGER_TILES.get());
      this.block(ESItems.TOOTH_OF_HUNGER_TILE_SLAB.get());
      this.block(ESItems.TOOTH_OF_HUNGER_TILE_STAIRS.get());
      this.wall(ESItems.TOOTH_OF_HUNGER_TILE_WALL.get(), ESItems.TOOTH_OF_HUNGER_TILES.get());
      this.block(ESItems.CHISELED_TOOTH_OF_HUNGER_TILES.get());
      this.block(ESItems.CRYSTALBORN_CATALYST.get());
      this.block(ESItems.CRYSTALLIZED_SAND.get());
      this.basicItem(ESItems.BATTLEAXE_PENDANT.get());
      this.basicItem(ESItems.WARHAMMER_PENDANT.get());
      this.basicItem(ESItems.BUTTERFLY_WINGS_AMULET.get());
      this.basicItem(ESItems.FUNGUS_AMULET.get());
      this.basicItem(ESItems.PEARL_NECKLACE.get());
      this.basicItem(ESItems.CRESCENT_PENDANT.get());
      this.block(ESItems.THE_GATEKEEPER_SPAWNER.get());
      this.block(ESItems.STARLIGHT_GOLEM_SPAWNER.get());
      this.block(ESItems.PERMAFROST_SPAWNER.get());
      this.block(ESItems.LUNAR_MONSTROSITY_SPAWNER.get());
      this.basicItem(ESItems.SOLAR_EGG.get());
      this.basicItem(ESItems.LOOT_BAG.get());
      this.basicItem(ESItems.STARLIGHT_SILVER_COIN.get());
      this.basicItem(ESItems.BOOK.get());
      this.basicItem(ESItems.SEEKING_EYE.get());
      this.handheld(ESItems.GLISTERING_SWORD.get());
      this.greatsword(ESItems.GLISTERING_GREATSWORD.get());
      this.greatswordInventory(ESItems.GLISTERING_GREATSWORD.get());
      this.inventoryHandheld(ESItems.GLISTERING_MORNING_STAR.get());
      this.bow(ESItems.GLISTERING_BOW.get());
      this.basicItem(ESItems.GOLEM_STEEL_INGOT.get());
      this.basicItem(ESItems.OXIDIZED_GOLEM_STEEL_INGOT.get());
      this.basicItem(ESItems.GOLEM_STEEL_NUGGET.get());
      this.basicItem(ESItems.OXIDIZED_GOLEM_STEEL_NUGGET.get());
      this.whip(ESItems.COLDSNAP.get());
      this.handheld(ESItems.ENERGY_SWORD.get());
      this.greatsword(ESItems.GOLEM_STEEL_GREATSWORD.get());
      this.greatswordInventory(ESItems.GOLEM_STEEL_GREATSWORD.get());
      this.crossbow(ESItems.MECHANICAL_CROSSBOW.get());
      this.handheld(ESItems.ENERGY_BOOMERANG.get());
      this.handheld(ESItems.UNDERMINER.get());
      this.greatsword(ESItems.CRYSTAL_GREATSWORD.get());
      this.greatswordInventory(ESItems.CRYSTAL_GREATSWORD.get());
      this.crossbow(ESItems.CRYSTAL_CROSSBOW.get());
      this.block(ESItems.GOLEM_STEEL_BLOCK.get());
      this.otherBlock(ESItems.WAXED_GOLEM_STEEL_BLOCK.get(), ESItems.GOLEM_STEEL_BLOCK.get());
      this.block(ESItems.OXIDIZED_GOLEM_STEEL_BLOCK.get());
      this.block(ESItems.GOLEM_STEEL_SLAB.get());
      this.otherBlock(ESItems.WAXED_GOLEM_STEEL_SLAB.get(), ESItems.GOLEM_STEEL_SLAB.get());
      this.block(ESItems.OXIDIZED_GOLEM_STEEL_SLAB.get());
      this.block(ESItems.GOLEM_STEEL_STAIRS.get());
      this.otherBlock(ESItems.WAXED_GOLEM_STEEL_STAIRS.get(), ESItems.GOLEM_STEEL_STAIRS.get());
      this.block(ESItems.OXIDIZED_GOLEM_STEEL_STAIRS.get());
      this.block(ESItems.GOLEM_STEEL_TILES.get());
      this.otherBlock(ESItems.WAXED_GOLEM_STEEL_TILES.get(), ESItems.GOLEM_STEEL_TILES.get());
      this.block(ESItems.OXIDIZED_GOLEM_STEEL_TILES.get());
      this.block(ESItems.GOLEM_STEEL_TILE_SLAB.get());
      this.otherBlock(ESItems.WAXED_GOLEM_STEEL_TILE_SLAB.get(), ESItems.GOLEM_STEEL_TILE_SLAB.get());
      this.block(ESItems.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get());
      this.block(ESItems.GOLEM_STEEL_TILE_STAIRS.get());
      this.otherBlock(ESItems.WAXED_GOLEM_STEEL_TILE_STAIRS.get(), ESItems.GOLEM_STEEL_TILE_STAIRS.get());
      this.block(ESItems.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get());
      this.block(ESItems.GOLEM_STEEL_GRATE.get());
      this.otherBlock(ESItems.WAXED_GOLEM_STEEL_GRATE.get(), ESItems.GOLEM_STEEL_GRATE.get());
      this.block(ESItems.OXIDIZED_GOLEM_STEEL_GRATE.get());
      this.block(ESItems.GOLEM_STEEL_PILLAR.get());
      this.otherBlock(ESItems.WAXED_GOLEM_STEEL_PILLAR.get(), ESItems.GOLEM_STEEL_PILLAR.get());
      this.block(ESItems.OXIDIZED_GOLEM_STEEL_PILLAR.get());
      this.flatBlockTexture(ESItems.GOLEM_STEEL_BARS.get());
      this.basicItem(ESItems.WAXED_GOLEM_STEEL_BARS.get(), this.blockTextureFromItem(ESItems.GOLEM_STEEL_BARS.get()));
      this.flatBlockTexture(ESItems.OXIDIZED_GOLEM_STEEL_BARS.get());
      this.block(ESItems.CHISELED_GOLEM_STEEL_BLOCK.get());
      this.otherBlock(ESItems.WAXED_CHISELED_GOLEM_STEEL_BLOCK.get(), ESItems.CHISELED_GOLEM_STEEL_BLOCK.get());
      this.block(ESItems.OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK.get());
      this.block(ESItems.GOLEM_STEEL_JET.get());
      this.otherBlock(ESItems.WAXED_GOLEM_STEEL_JET.get(), ESItems.GOLEM_STEEL_JET.get());
      this.block(ESItems.OXIDIZED_GOLEM_STEEL_JET.get());
      this.block(ESItems.GOLEM_STEEL_CRATE.get());
      this.basicItem(ESItems.ENERGY_TRANSMITTER.get());
      this.block(ESItems.ACCUMULATOR.get());
      this.otherBlock(ESItems.MECHANICAL_SPAWNER.get(), Items.SPAWNER);
      this.basicItem(ESItems.ALLOY_FURNACE.get());
      this.basicItem(ESItems.WAXED_ALLOY_FURNACE.get(), this.itemTexture(ESItems.ALLOY_FURNACE.get()));
      this.basicItem(ESItems.OXIDIZED_ALLOY_FURNACE.get());
      this.block(ESItems.ENERGY_BLOCK.get());
      this.basicItem(ESItems.TENACIOUS_PETAL.get());
      this.basicItem(ESItems.TENACIOUS_VINE.get());
      this.basicItem(ESItems.SOUL_DEW.get());
      this.basicItem(ESItems.SOULIT_SPECTATOR.get());
      this.crossbow(ESItems.WILTED_CROSSBOW.get());
      this.bow(ESItems.MOONRING_BOW.get());
      this.greatsword(ESItems.MOONRING_GREATSWORD.get());
      this.greatswordInventory(ESItems.MOONRING_GREATSWORD.get());
      this.largeHandheld(ESItems.PETAL_SCYTHE.get());
      this.inventoryHandheld(ESItems.PETAL_SCYTHE.get());
      this.handheld(ESItems.WAND_OF_TELEPORTATION.get());
      this.whip(ESItems.CHAIN_OF_SOULS.get());
      this.inventoryHandheld(ESItems.CRESCENT_SPEAR.get());
      this.block(ESItems.SHADEGRIEVE.get());
      this.block(ESItems.BLOOMING_SHADEGRIEVE.get());
      this.basicItem(ESItems.LUNAR_VINE.get());
      this.block(ESItems.LUNAR_MOSAIC.get());
      this.block(ESItems.LUNAR_MOSAIC_SLAB.get());
      this.block(ESItems.LUNAR_MOSAIC_STAIRS.get());
      this.fence(ESItems.LUNAR_MOSAIC_FENCE.get(), ESItems.LUNAR_MOSAIC.get());
      this.block(ESItems.LUNAR_MOSAIC_FENCE_GATE.get());
      this.block(ESItems.LUNAR_MAT.get());
      this.handheld(ESItems.GRAVITY_PICKAXE.get());
      this.block(ESItems.DUSK_GLASS.get());
      this.block(ESItems.DUSK_LIGHT.get());
      this.block(ESItems.REINFORCED_DUSK_LIGHT.get());
      this.cubeAll(ESItems.DUSK_EMITTER.get());
      this.block(ESItems.DUSK_LOCKBOX.get());
      this.block(ESItems.FLARE_SPAWNER.get());
      this.block(ESItems.ECLIPSE_CORE.get());
      this.basicItem(ESItems.BROKEN_DOOMEDEN_BONE.get());
      this.greatsword(ESItems.BONEMORE.get());
      this.greatswordInventory(ESItems.BONEMORE.get());
      this.bow(ESItems.BOW_OF_BLOOD.get());
      this.handheld(ESItems.LIVING_ARM.get());
      this.flatBlockTexture(ESItems.DOOMED_TORCH.get());
      this.flatBlockTexture(ESItems.DOOMED_REDSTONE_TORCH.get());
      this.basicItem(ESItems.DOOMEDEN_CARRION.get());
      this.handheld(ESItems.ROTTEN_HAM.get());
      this.basicItem(ESItems.EYE_OF_DOOM.get());
      this.basicItem(ESItems.DOOMEDEN_RAG.get());
      this.handheld(ESItems.FLESH_GRINDER.get());
      this.inventoryHandheld(ESItems.DOOMEDEN_RAPIER.get());
      this.block(ESItems.DOOMEDEN_BRICKS.get());
      this.block(ESItems.DOOMEDEN_BRICK_SLAB.get());
      this.block(ESItems.DOOMEDEN_BRICK_STAIRS.get());
      this.wall(ESItems.DOOMEDEN_BRICK_WALL.get(), ESItems.DOOMEDEN_BRICKS.get());
      this.block(ESItems.POLISHED_DOOMEDEN_BRICKS.get());
      this.block(ESItems.POLISHED_DOOMEDEN_BRICK_SLAB.get());
      this.block(ESItems.POLISHED_DOOMEDEN_BRICK_STAIRS.get());
      this.wall(ESItems.POLISHED_DOOMEDEN_BRICK_WALL.get(), ESItems.POLISHED_DOOMEDEN_BRICKS.get());
      this.block(ESItems.DOOMEDEN_TILES.get());
      this.block(ESItems.DOOMEDEN_TILE_SLAB.get());
      this.block(ESItems.DOOMEDEN_TILE_STAIRS.get());
      this.wall(ESItems.DOOMEDEN_TILE_WALL.get(), ESItems.DOOMEDEN_TILES.get());
      this.block(ESItems.CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
      this.block(ESItems.CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
      this.block(ESItems.DOOMEDEN_LIGHT.get());
      this.block(ESItems.DOOMEDEN_KEYHOLE.get());
      this.block(ESItems.REDSTONE_DOOMEDEN_KEYHOLE.get());
      this.inventoryModel(ESItems.ORB_OF_PROPHECY.get());
      this.block(ESItems.STELLAR_RACK.get());
      this.block(ESItems.ENCHANTED_GRIMSTONE_BRICKS.get());
      this.basicItem(ESItems.MANA_CRYSTAL.get());
      this.basicItem(ESItems.TERRA_CRYSTAL.get());
      this.basicItem(ESItems.WIND_CRYSTAL.get());
      this.basicItem(ESItems.WATER_CRYSTAL.get());
      this.basicItem(ESItems.LUNAR_CRYSTAL.get());
      this.basicItem(ESItems.BLAZE_CRYSTAL.get());
      this.basicItem(ESItems.LIGHT_CRYSTAL.get());
      this.basicItem(ESItems.MANA_CRYSTAL_SHARD.get());
      this.basicItem(ESItems.KEEPER_ARMOR_TRIM_SMITHING_TEMPLATE.get());
      this.basicItem(ESItems.FORGE_ARMOR_TRIM_SMITHING_TEMPLATE.get());
      this.basicItem(ESItems.BLOOMING_ARMOR_TRIM_SMITHING_TEMPLATE.get());
      this.basicItem(ESItems.TWINING_ARMOR_TRIM_SMITHING_TEMPLATE.get());
      this.basicItem(ESItems.MUSIC_DISC_WHISPER_OF_THE_STARS.get());
      this.basicItem(ESItems.MUSIC_DISC_DUSK_O_EREYESTERDAY.get());
      this.basicItem(ESItems.MUSIC_DISC_TRANQUILITY.get());
      this.basicItem(ESItems.MUSIC_DISC_NEST.get());
      this.basicItem(ESItems.MUSIC_DISC_POSTERITY.get());
      this.basicItem(ESItems.MUSIC_DISC_THE_THORNY_REIGN.get());
      this.basicItem(ESItems.MUSIC_DISC_PROFUNDITY.get());
      this.basicItem(ESItems.MUSIC_DISC_WAILING_WELL.get());
      this.basicItem(ESItems.MUSIC_DISC_STARS_SHINING_UPON_THE_SEA.get());
      this.basicItem(ESItems.MUSIC_DISC_OPTIMIZED_OPTION.get());
      this.basicItem(ESItems.MUSIC_DISC_MECHANICAL_FOSSIL.get());
      this.basicItem(ESItems.MUSIC_DISC_FAKE_LIGHT.get());
      this.basicItem(ESItems.MUSIC_DISC_TRANQUILITY_II.get());
      this.basicItem(ESItems.MUSIC_DISC_ATLANTIS.get());
      this.basicItem(ESItems.MUSIC_DISC_SACRED_DESERT.get());
      this.basicItem(ESItems.MUSIC_DISC_SPIRIT.get());
      this.basicItem(ESItems.MUSIC_DISC_ETHER_RAIN.get());
      this.basicItem(ESItems.MUSIC_DISC_BRISK.get());
      this.basicItem(ESItems.MUSIC_DISC_MOONLIGHT.get());
      this.basicItem(ESItems.BLOSSOM_OF_STARS.get());
   }

   private void armorWithTrim(ArmorItem armor) {
      ItemModelBuilder armorBuilder = this.basicItem(armor);

      for (TrimModelData trimModelData : ItemModelGenerators.GENERATED_TRIM_MODELS) {
         ModelFile trimModel = ((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(
                  this.name(armor) + "_" + trimModelData.name() + "_trim", this.mcLoc("item/generated")
               ))
               .texture("layer0", this.modLoc("item/" + this.name(armor))))
            .texture("layer1", this.mcLoc("trims/items/" + armor.getType().getName() + "_trim_" + trimModelData.name()));
         armorBuilder.override().predicate(ResourceLocation.withDefaultNamespace("trim_type"), trimModelData.itemModelIndex()).model(trimModel).end();
      }
   }

   private void greatsword(Item item) {
      ModelFile blocking = ((ItemModelBuilder)this.withExistingParent(this.name(item) + "_blocking", EternalStarlight.id("item/large_handheld_blocking")))
         .texture("layer0", this.itemTexture(item));
      ((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(this.name(item), EternalStarlight.id("item/large_handheld")))
            .texture("layer0", this.itemTexture(item)))
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("blocking"), 1.0F)
         .model(blocking)
         .end();
   }

   private void greatswordInventory(Item item) {
      ((ItemModelBuilder)this.withExistingParent(this.name(item) + "_blocking_inventory", ResourceLocation.withDefaultNamespace("item/handheld")))
         .texture("layer0", this.itemTexture(item).withSuffix("_inventory"));
      ((ItemModelBuilder)this.withExistingParent(this.name(item) + "_inventory", ResourceLocation.withDefaultNamespace("item/handheld")))
         .texture("layer0", this.itemTexture(item).withSuffix("_inventory"));
   }

   private void whip(Item item) {
      ModelFile extendedModel = ((ItemModelBuilder)this.withExistingParent(this.name(item) + "_extended", EternalStarlight.id("item/gui_only")))
         .texture("layer0", this.itemTexture(item));
      ((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(this.name(item), "item/handheld")).texture("layer0", this.itemTexture(item)))
         .override()
         .predicate(EternalStarlight.id("extended"), 1.0F)
         .model(extendedModel)
         .end();
   }

   private void shatteredSword(Item item) {
      ModelFile noBladeModel = ((ItemModelBuilder)this.withExistingParent(this.name(item) + "_no_blade", "item/handheld"))
         .texture("layer0", this.itemTexture(item).withSuffix("_no_blade"));
      ((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(this.name(item), "item/handheld")).texture("layer0", this.itemTexture(item)))
         .override()
         .predicate(EternalStarlight.id("no_blade"), 1.0F)
         .model(noBladeModel)
         .end();
   }

   private void galacticQuiver(Item item) {
      ModelFile arrowsModel = ((ItemModelBuilder)this.withExistingParent(this.name(item) + "_arrows", "item/generated"))
         .texture("layer0", this.itemTexture(item).withSuffix("_arrows"));
      ((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(this.name(item), "item/generated")).texture("layer0", this.itemTexture(item)))
         .override()
         .predicate(EternalStarlight.id("arrows"), 1.0F)
         .model(arrowsModel)
         .end();
   }

   private void daggerOfHunger(Item item) {
      ModelFile normal = ((ItemModelBuilder)this.withExistingParent(this.name(item) + "_normal", "item/handheld")).texture("layer0", this.itemTexture(item));
      ModelFile saturated = ((ItemModelBuilder)this.withExistingParent(this.name(item) + "_saturated", "item/handheld"))
         .texture("layer0", this.itemTexture(item).withSuffix("_saturated"));
      ((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(this.name(item), "item/handheld"))
            .texture("layer0", this.itemTexture(item).withSuffix("_hungry")))
         .override()
         .predicate(EternalStarlight.id("hunger_state"), 0.5F)
         .model(normal)
         .end()
         .override()
         .predicate(EternalStarlight.id("hunger_state"), 1.0F)
         .model(saturated)
         .end();
   }

   private void crossbow(Item item) {
      ModelFile pull0 = ((ItemModelBuilder)this.withExistingParent(this.name(item) + "_pulling_0", "item/crossbow"))
         .texture("layer0", this.itemTexture(item).withSuffix("_pulling_0"));
      ModelFile pull1 = ((ItemModelBuilder)this.withExistingParent(this.name(item) + "_pulling_1", "item/crossbow"))
         .texture("layer0", this.itemTexture(item).withSuffix("_pulling_1"));
      ModelFile pull2 = ((ItemModelBuilder)this.withExistingParent(this.name(item) + "_pulling_2", "item/crossbow"))
         .texture("layer0", this.itemTexture(item).withSuffix("_pulling_2"));
      ModelFile arrow = ((ItemModelBuilder)this.withExistingParent(this.name(item) + "_arrow", "item/crossbow"))
         .texture("layer0", this.itemTexture(item).withSuffix("_arrow"));
      ModelFile firework = ((ItemModelBuilder)this.withExistingParent(this.name(item) + "_firework", "item/crossbow"))
         .texture("layer0", this.itemTexture(item).withSuffix("_firework"));
      ((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(this.name(item), "item/crossbow"))
            .texture("layer0", this.itemTexture(item).withSuffix("_standby")))
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("pulling"), 1.0F)
         .model(pull0)
         .end()
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("pulling"), 1.0F)
         .predicate(ResourceLocation.withDefaultNamespace("pull"), 0.58F)
         .model(pull1)
         .end()
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("pulling"), 1.0F)
         .predicate(ResourceLocation.withDefaultNamespace("pull"), 1.0F)
         .model(pull2)
         .end()
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("charged"), 1.0F)
         .model(arrow)
         .end()
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("charged"), 1.0F)
         .predicate(ResourceLocation.withDefaultNamespace("firework"), 1.0F)
         .model(firework)
         .end();
   }

   private void bow(Item item) {
      ModelFile pull0 = ((ItemModelBuilder)this.withExistingParent(this.name(item) + "_pulling_0", "item/bow"))
         .texture("layer0", this.itemTexture(item).withSuffix("_pulling_0"));
      ModelFile pull1 = ((ItemModelBuilder)this.withExistingParent(this.name(item) + "_pulling_1", "item/bow"))
         .texture("layer0", this.itemTexture(item).withSuffix("_pulling_1"));
      ModelFile pull2 = ((ItemModelBuilder)this.withExistingParent(this.name(item) + "_pulling_2", "item/bow"))
         .texture("layer0", this.itemTexture(item).withSuffix("_pulling_2"));
      ((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(this.name(item), "item/bow")).texture("layer0", this.itemTexture(item)))
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("pulling"), 1.0F)
         .model(pull0)
         .end()
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("pulling"), 1.0F)
         .predicate(ResourceLocation.withDefaultNamespace("pull"), 0.65F)
         .model(pull1)
         .end()
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("pulling"), 1.0F)
         .predicate(ResourceLocation.withDefaultNamespace("pull"), 0.9F)
         .model(pull2)
         .end();
   }

   private void brush(Item item) {
      ModelFile brush0 = ((ItemModelBuilder)this.withExistingParent(this.name(item) + "_brushing_0", "item/brush_brushing_0"))
         .texture("layer0", this.itemTexture(item));
      ModelFile brush1 = ((ItemModelBuilder)this.withExistingParent(this.name(item) + "_brushing_1", "item/brush_brushing_1"))
         .texture("layer0", this.itemTexture(item));
      ModelFile brush2 = ((ItemModelBuilder)this.withExistingParent(this.name(item) + "_brushing_2", "item/brush_brushing_2"))
         .texture("layer0", this.itemTexture(item));
      ((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(this.name(item), "item/brush")).texture("layer0", this.itemTexture(item)))
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("brushing"), 0.25F)
         .model(brush0)
         .end()
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("brushing"), 0.5F)
         .model(brush1)
         .end()
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("brushing"), 0.75F)
         .model(brush2)
         .end();
   }

   private void trapdoor(Item item) {
      this.withExistingParent(this.name(item), this.modLoc("block/" + this.name(item) + "_bottom"));
   }

   private void button(Item button, Item material) {
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(this.name(button))).parent(this.getExistingFile(this.mcLoc("block/button_inventory"))))
         .texture("texture", this.blockTextureFromItem(material));
   }

   private void fence(Item fence, Item planks) {
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(this.name(fence))).parent(this.getExistingFile(this.mcLoc("block/fence_inventory"))))
         .texture("texture", this.blockTextureFromItem(planks));
   }

   private void wall(Item wall, Item stone) {
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(this.name(wall))).parent(this.getExistingFile(this.mcLoc("block/wall_inventory"))))
         .texture("wall", this.blockTextureFromItem(stone));
   }

   private void cubeAll(Item cube) {
      this.cubeAll(cube, this.blockTextureFromItem(cube));
   }

   private void cubeAll(Item cube, ResourceLocation texture) {
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(this.name(cube))).parent(this.getExistingFile(this.mcLoc("block/cube_all"))))
         .texture("all", texture);
   }

   private void templateSkull(Item skull) {
      ((ItemModelBuilder)this.getBuilder(this.name(skull))).parent(this.getExistingFile(this.mcLoc("item/template_skull")));
   }

   private void block(Item item) {
      this.withExistingParent(this.name(item), this.modLoc("block/" + this.name(item)));
   }

   private void otherBlock(Item item, Item other) {
      this.withExistingParent(this.name(item), ResourceLocation.fromNamespaceAndPath(this.key(other).getNamespace(), "block/" + this.name(other)));
   }

   private void layeredBlock(Item item) {
      this.withExistingParent(this.name(item), this.modLoc("block/" + this.name(item) + "_height2"));
   }

   private void flatBlockTexture(Item item) {
      this.basicItem(item, this.blockTextureFromItem(item));
   }

   private void basicItem(Item item, ResourceLocation texture) {
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(item.toString())).parent(new UncheckedModelFile("item/generated"))).texture("layer0", texture);
   }

   private void largeHandheld(Item item) {
      this.handheld(item, this.itemTexture(item), true);
   }

   private void handheld(Item item) {
      this.handheld(item, this.itemTexture(item), false);
   }

   private ItemModelBuilder handheld(Item item, ResourceLocation texture, boolean large) {
      return (ItemModelBuilder)((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(item.toString()))
            .parent(large ? new UncheckedModelFile("eternal_starlight:item/large_handheld") : new UncheckedModelFile("item/handheld")))
         .texture("layer0", texture);
   }

   private void inventoryHandheld(Item item) {
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(item.toString() + "_inventory")).parent(new UncheckedModelFile("item/handheld")))
         .texture("layer0", this.itemTexture(item) + "_inventory");
   }

   private void inventoryModel(Item item) {
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(item.toString() + "_inventory")).parent(new UncheckedModelFile("item/generated")))
         .texture("layer0", this.itemTexture(item) + "_inventory");
   }

   public ResourceLocation blockTexture(Block block) {
      ResourceLocation name = this.key(block);
      return this.texture(name, "block");
   }

   public ResourceLocation blockTextureFromItem(Item item) {
      ResourceLocation name = this.key(item);
      return this.texture(name, "block");
   }

   public ResourceLocation itemTexture(Item item) {
      ResourceLocation name = this.key(item);
      return this.texture(name, "item");
   }

   public ResourceLocation texture(ResourceLocation key, String prefix) {
      return ResourceLocation.fromNamespaceAndPath(key.getNamespace(), prefix + "/" + key.getPath());
   }

   private ResourceLocation key(Block block) {
      return BuiltInRegistries.BLOCK.getKey(block);
   }

   private String name(Block block) {
      return this.key(block).getPath();
   }

   private ResourceLocation key(Item item) {
      return BuiltInRegistries.ITEM.getKey(item);
   }

   private String name(Item item) {
      return this.key(item).getPath();
   }
}
