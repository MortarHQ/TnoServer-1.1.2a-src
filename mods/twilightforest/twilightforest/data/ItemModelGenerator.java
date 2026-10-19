package twilightforest.data;

import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.ItemModelGenerators.TrimModelData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelFile.ExistingModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.ItemLayerModelBuilder;
import net.neoforged.neoforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import twilightforest.TwilightForestMod;
import twilightforest.beanification.Autowired;
import twilightforest.data.custom.TravellersGearItemModelBuilder;
import twilightforest.enums.extensions.TFItemDisplayContextEnumExtension;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFEntities;
import twilightforest.init.TFItems;
import twilightforest.item.Experiment115Item;
import twilightforest.item.HollowLogItem;

public class ItemModelGenerator extends ItemModelProvider {
   @Autowired
   private static TFItemDisplayContextEnumExtension itemDisplayContextEnumExtension;

   public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
      super(output, "twilightforest", existingFileHelper);
   }

   protected void registerModels() {
      for (DeferredHolder<Item, ?> item : TFEntities.SPAWN_EGGS.getEntries()) {
         if (item.get() instanceof SpawnEggItem) {
            ((ItemModelBuilder)this.getBuilder(item.getId().getPath()))
               .parent(this.getExistingFile(ResourceLocation.withDefaultNamespace("item/template_spawn_egg")));
         }
      }

      this.toBlock((Block)TFBlocks.TOWERWOOD.get());
      this.toBlock((Block)TFBlocks.ENCASED_TOWERWOOD.get());
      this.toBlock((Block)TFBlocks.CRACKED_TOWERWOOD.get());
      this.toBlock((Block)TFBlocks.MOSSY_TOWERWOOD.get());
      this.toBlock((Block)TFBlocks.INFESTED_TOWERWOOD.get());
      this.toBlock((Block)TFBlocks.CARMINITE_BUILDER.get());
      this.toBlock((Block)TFBlocks.ANTIBUILDER.get());
      this.toBlock((Block)TFBlocks.GHAST_TRAP.get());
      this.toBlock((Block)TFBlocks.VANISHING_BLOCK.get());
      this.toBlock((Block)TFBlocks.LOCKED_VANISHING_BLOCK.get());
      this.toBlock((Block)TFBlocks.REAPPEARING_BLOCK.get());
      this.toBlock((Block)TFBlocks.CARMINITE_REACTOR.get());
      this.toBlockModel((Block)TFBlocks.FAKE_GOLD.get(), ResourceLocation.withDefaultNamespace("block/gold_block"));
      this.toBlockModel((Block)TFBlocks.FAKE_DIAMOND.get(), ResourceLocation.withDefaultNamespace("block/diamond_block"));
      this.toBlock((Block)TFBlocks.STRONGHOLD_SHIELD.get());
      this.toBlock((Block)TFBlocks.TROPHY_PEDESTAL.get());
      this.toBlockModel((Block)TFBlocks.AURORA_BLOCK.get(), TwilightForestMod.prefix("block/aurora_block_0"));
      this.toBlock((Block)TFBlocks.AURORA_PILLAR.get());
      this.toBlock((Block)TFBlocks.AURORA_SLAB.get());
      this.toBlock((Block)TFBlocks.AURORALIZED_GLASS.get());
      this.toBlock((Block)TFBlocks.UNDERBRICK.get());
      this.toBlock((Block)TFBlocks.CRACKED_UNDERBRICK.get());
      this.toBlock((Block)TFBlocks.MOSSY_UNDERBRICK.get());
      this.toBlock((Block)TFBlocks.UNDERBRICK_FLOOR.get());
      this.toBlock((Block)TFBlocks.GREEN_THORNS.get());
      this.toBlock((Block)TFBlocks.BROWN_THORNS.get());
      this.toBlock((Block)TFBlocks.BURNT_THORNS.get());
      this.generated(TFBlocks.THORN_ROSE.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.THORN_ROSE.getId().getPath()));
      this.toBlockModel((Block)TFBlocks.THORN_LEAVES.get(), ResourceLocation.withDefaultNamespace("block/oak_leaves"));
      this.toBlockModel((Block)TFBlocks.BEANSTALK_LEAVES.get(), ResourceLocation.withDefaultNamespace("block/azalea_leaves"));
      this.toBlock((Block)TFBlocks.DEADROCK.get());
      this.toBlock((Block)TFBlocks.CRACKED_DEADROCK.get());
      this.toBlock((Block)TFBlocks.WEATHERED_DEADROCK.get());
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(TFBlocks.TROLLSTEINN.getId().getPath()))
            .parent(this.getExistingFile(ResourceLocation.withDefaultNamespace("block/cube_all"))))
         .texture("all", TwilightForestMod.prefix("block/trollsteinn"));
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(TFBlocks.TROLLSTEINN.getId().getPath() + "_light"))
            .parent(this.getExistingFile(ResourceLocation.withDefaultNamespace("block/cube_all"))))
         .texture("all", TwilightForestMod.prefix("block/trollsteinn_light"));
      this.toBlock((Block)TFBlocks.WISPY_CLOUD.get());
      this.toBlock((Block)TFBlocks.FLUFFY_CLOUD.get());
      this.toBlock((Block)TFBlocks.RAINY_CLOUD.get());
      this.toBlock((Block)TFBlocks.SNOWY_CLOUD.get());
      float giant = 4.0F;
      ItemModelBuilder giant_block = (ItemModelBuilder)((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(
               "giant_block_base", ResourceLocation.withDefaultNamespace("block/cube")
            ))
            .transforms()
            .transform(ItemDisplayContext.GROUND)
            .translation(0.0F, 3.0F, 0.0F)
            .scale(0.25F * giant)
            .end()
            .transform(ItemDisplayContext.FIXED)
            .scale(0.5F * giant * 0.625F)
            .end()
            .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
            .rotation(75.0F, 45.0F, 0.0F)
            .translation(0.0F, 2.5F * giant, 0.0F)
            .scale(0.375F * giant)
            .end()
            .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
            .rotation(0.0F, 45.0F, 0.0F)
            .translation(0.0F, -2.5F * giant, -2.5F * giant)
            .scale(0.4F * giant)
            .end()
            .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
            .rotation(1.0F, 225.0F, 0.0F)
            .translation(0.0F, -2.5F * giant, -2.5F * giant)
            .scale(0.4F * giant)
            .end()
            .end())
         .element()
         .allFaces(
            (direction, faceBuilder) -> faceBuilder.uvs(0.0F, 0.0F, 16.0F, 16.0F)
               .texture(direction.getAxis() == Axis.Y ? "#top" : "#all")
               .tintindex(0)
               .cullface(direction)
               .end()
               .end()
         )
         .end();
      ItemModelBuilder gui_giant = (ItemModelBuilder)((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(
               "giant_block_gui", ResourceLocation.withDefaultNamespace("block/cube")
            ))
            .transforms()
            .transform(ItemDisplayContext.GUI)
            .rotation(30.0F, 45.0F, 0.0F)
            .scale(0.625F)
            .end()
            .end())
         .element()
         .allFaces(
            (direction, faceBuilder) -> faceBuilder.uvs(0.0F, 0.0F, 4.0F, 4.0F)
               .texture(direction.getAxis() == Axis.Y ? "#top" : "#all")
               .tintindex(0)
               .cullface(direction)
               .end()
               .end()
         )
         .end();
      this.toGiantModel((Block)TFBlocks.GIANT_COBBLESTONE.get(), ResourceLocation.withDefaultNamespace("block/cobblestone"), giant_block, gui_giant);
      this.toGiantModel(
         (Block)TFBlocks.GIANT_LOG.get(),
         ResourceLocation.withDefaultNamespace("block/oak_log"),
         ResourceLocation.withDefaultNamespace("block/oak_log_top"),
         giant_block,
         gui_giant
      );
      this.toGiantModel((Block)TFBlocks.GIANT_LEAVES.get(), ResourceLocation.withDefaultNamespace("block/oak_leaves"), giant_block, gui_giant);
      this.toGiantModel((Block)TFBlocks.GIANT_OBSIDIAN.get(), ResourceLocation.withDefaultNamespace("block/obsidian"), giant_block, gui_giant);
      ItemModelBuilder giant_tool = (ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(
            "giant_tool_base", ResourceLocation.withDefaultNamespace("item/generated")
         ))
         .transforms()
         .transform(ItemDisplayContext.GROUND)
         .translation(0.0F, 2.0F, 0.0F)
         .scale(2.5F)
         .end()
         .transform(ItemDisplayContext.HEAD)
         .rotation(0.0F, 180.0F, 0.0F)
         .translation(0.0F, 13.0F, 7.0F)
         .scale(5.0F)
         .end()
         .transform(ItemDisplayContext.FIXED)
         .rotation(0.0F, 180.0F, 0.0F)
         .scale(5.0F)
         .end()
         .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
         .rotation(0.0F, -90.0F, 55.0F)
         .translation(-0.1F, 24.0F, -5.5F)
         .scale(4.25F)
         .end()
         .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
         .rotation(0.0F, 90.0F, -55.0F)
         .translation(-0.1F, 24.0F, -3.5F)
         .scale(4.25F)
         .end()
         .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
         .rotation(0.0F, -90.0F, 25.0F)
         .translation(1.13F, 3.2F, 1.13F)
         .scale(1.7F)
         .end()
         .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
         .rotation(0.0F, 90.0F, -25.0F)
         .translation(1.13F, 3.2F, 1.13F)
         .scale(1.7F)
         .end()
         .end();
      this.toGiantItemModel(TFItems.GIANT_PICKAXE, ResourceLocation.withDefaultNamespace("item/stone_pickaxe"), giant_tool, 7, 2);
      this.toGiantItemModel(TFItems.GIANT_SWORD, ResourceLocation.withDefaultNamespace("item/stone_sword"), giant_tool, 3, 5);
      this.toBlock((Block)TFBlocks.UBEROUS_SOIL.get());
      this.toBlock((Block)TFBlocks.HUGE_STALK.get());
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(TFBlocks.HUGE_MUSHGLOOM.getId().getPath()))
            .parent(this.getExistingFile(ResourceLocation.withDefaultNamespace("block/cube_all"))))
         .texture("all", TwilightForestMod.prefix("block/huge_gloom_cap"));
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(TFBlocks.HUGE_MUSHGLOOM_STEM.getId().getPath()))
            .parent(this.getExistingFile(ResourceLocation.withDefaultNamespace("block/cube_all"))))
         .texture("all", TwilightForestMod.prefix("block/huge_mushgloom_stem"));
      this.generated(TFBlocks.TROLLVIDR.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.TROLLVIDR.getId().getPath()));
      this.generated(TFBlocks.UNRIPE_TROLLBER.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.UNRIPE_TROLLBER.getId().getPath()));
      this.generated(TFBlocks.TROLLBER.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.TROLLBER.getId().getPath()));
      this.toBlock((Block)TFBlocks.SLIDER.get());
      this.generated(TFBlocks.HUGE_LILY_PAD.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.HUGE_LILY_PAD.getId().getPath()));
      this.generated(TFBlocks.HUGE_WATER_LILY.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.HUGE_WATER_LILY.getId().getPath()));
      this.toBlock((Block)TFBlocks.CASTLE_BRICK.get());
      this.toBlock((Block)TFBlocks.WORN_CASTLE_BRICK.get());
      this.toBlock((Block)TFBlocks.CRACKED_CASTLE_BRICK.get());
      this.toBlock((Block)TFBlocks.CASTLE_ROOF_TILE.get());
      this.toBlock((Block)TFBlocks.MOSSY_CASTLE_BRICK.get());
      this.toBlock((Block)TFBlocks.THICK_CASTLE_BRICK.get());
      this.toBlock((Block)TFBlocks.ENCASED_CASTLE_BRICK_PILLAR.get());
      this.toBlock((Block)TFBlocks.ENCASED_CASTLE_BRICK_TILE.get());
      this.toBlock((Block)TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get());
      this.toBlock((Block)TFBlocks.BOLD_CASTLE_BRICK_TILE.get());
      this.toBlock((Block)TFBlocks.CASTLE_BRICK_STAIRS.get());
      this.toBlock((Block)TFBlocks.WORN_CASTLE_BRICK_STAIRS.get());
      this.toBlock((Block)TFBlocks.CRACKED_CASTLE_BRICK_STAIRS.get());
      this.toBlock((Block)TFBlocks.MOSSY_CASTLE_BRICK_STAIRS.get());
      this.toBlock((Block)TFBlocks.ENCASED_CASTLE_BRICK_STAIRS.get());
      this.toBlock((Block)TFBlocks.BOLD_CASTLE_BRICK_STAIRS.get());
      this.toBlock((Block)TFBlocks.PINK_CASTLE_DOOR.get());
      this.toBlock((Block)TFBlocks.YELLOW_CASTLE_DOOR.get());
      this.toBlock((Block)TFBlocks.BLUE_CASTLE_DOOR.get());
      this.toBlock((Block)TFBlocks.VIOLET_CASTLE_DOOR.get());
      this.toBlockModel((Block)TFBlocks.YELLOW_CASTLE_RUNE_BRICK.get(), "castle_rune_inventory");
      this.toBlockModel((Block)TFBlocks.VIOLET_CASTLE_RUNE_BRICK.get(), "castle_rune_inventory");
      this.toBlockModel((Block)TFBlocks.PINK_CASTLE_RUNE_BRICK.get(), "castle_rune_inventory");
      this.toBlockModel((Block)TFBlocks.BLUE_CASTLE_RUNE_BRICK.get(), "castle_rune_inventory");
      this.forcefield(TFBlocks.PINK_FORCE_FIELD.getId().getPath(), TwilightForestMod.prefix("block/forcefield_white"));
      this.forcefield(TFBlocks.BLUE_FORCE_FIELD.getId().getPath(), TwilightForestMod.prefix("block/forcefield_white"));
      this.forcefield(TFBlocks.GREEN_FORCE_FIELD.getId().getPath(), TwilightForestMod.prefix("block/forcefield_white"));
      this.forcefield(TFBlocks.VIOLET_FORCE_FIELD.getId().getPath(), TwilightForestMod.prefix("block/forcefield_white"));
      this.forcefield(TFBlocks.ORANGE_FORCE_FIELD.getId().getPath(), TwilightForestMod.prefix("block/forcefield_white"));
      this.toBlock((Block)TFBlocks.CINDER_LOG.get());
      this.toBlock((Block)TFBlocks.CINDER_WOOD.get());
      this.toBlockModel((Block)TFBlocks.CINDER_FURNACE.get(), ResourceLocation.withDefaultNamespace("block/furnace"));
      ModelFile think115 = this.generated("item/think115", TwilightForestMod.prefix("item/think115"));
      ModelFile fullBlockSprinkle = this.getExistingFile(TwilightForestMod.prefix("block/experiment115_8_8_regenerating"));
      this.generated(TFBlocks.EXPERIMENT_115.getId().getPath(), TwilightForestMod.prefix("item/experiment_115"))
         .override()
         .predicate(Experiment115Item.THINK, 1.0F)
         .model(think115)
         .end()
         .override()
         .predicate(Experiment115Item.FULL, 1.0F)
         .model(fullBlockSprinkle)
         .end();
      this.toBlockModel((Block)TFBlocks.TWILIGHT_PORTAL_MINIATURE_STRUCTURE.get(), "miniature/portal");
      this.toBlockModel((Block)TFBlocks.NAGA_COURTYARD_MINIATURE_STRUCTURE.get(), "miniature/naga_courtyard");
      this.toBlockModel((Block)TFBlocks.LICH_TOWER_MINIATURE_STRUCTURE.get(), "miniature/lich_tower");
      this.toBlockModel((Block)TFBlocks.MINOTAUR_LABYRINTH_MINIATURE_STRUCTURE.get(), "miniature/labyrinth");
      this.toBlockModel((Block)TFBlocks.DARK_TOWER_MINIATURE_STRUCTURE.get(), "miniature/dark_tower");
      this.toBlock((Block)TFBlocks.KNIGHTMETAL_BLOCK.get());
      this.toBlock((Block)TFBlocks.IRONWOOD_BLOCK.get());
      this.toBlock((Block)TFBlocks.FIERY_BLOCK.get());
      this.toBlock((Block)TFBlocks.ARCTIC_FUR_BLOCK.get());
      this.toBlock((Block)TFBlocks.STEELEAF_BLOCK.get());
      this.toBlock((Block)TFBlocks.CARMINITE_BLOCK.get());
      this.toBlock((Block)TFBlocks.MAZESTONE.get());
      this.toBlock((Block)TFBlocks.MAZESTONE_BRICK.get());
      this.toBlock((Block)TFBlocks.CUT_MAZESTONE.get());
      this.toBlock((Block)TFBlocks.DECORATIVE_MAZESTONE.get());
      this.toBlock((Block)TFBlocks.CRACKED_MAZESTONE.get());
      this.toBlock((Block)TFBlocks.MOSSY_MAZESTONE.get());
      this.toBlock((Block)TFBlocks.MAZESTONE_MOSAIC.get());
      this.toBlock((Block)TFBlocks.MAZESTONE_BORDER.get());
      this.toBlock((Block)TFBlocks.MAZE_SLIME_BLOCK.get());
      ModelFile bundle = this.generated("red_thread_bundle", TwilightForestMod.prefix("item/red_thread_bundle_0"));
      ModelFile spool = this.generated("red_thread_spool", TwilightForestMod.prefix("item/red_thread_bundle_1"));
      ModelFile large_spool = this.generated("red_thread_large_spool", TwilightForestMod.prefix("item/red_thread_bundle_2"));
      this.singleTex(TFBlocks.RED_THREAD)
         .override()
         .predicate(TwilightForestMod.prefix("size"), 0.25F)
         .model(bundle)
         .end()
         .override()
         .predicate(TwilightForestMod.prefix("size"), 0.5F)
         .model(spool)
         .end()
         .override()
         .predicate(TwilightForestMod.prefix("size"), 1.0F)
         .model(large_spool)
         .end();
      this.toBlock((Block)TFBlocks.HEDGE.get());
      this.toBlock((Block)TFBlocks.ROOT_BLOCK.get());
      this.toBlock((Block)TFBlocks.LIVEROOT_BLOCK.get());
      this.toBlock((Block)TFBlocks.MANGROVE_ROOT.get());
      this.toBlock((Block)TFBlocks.UNCRAFTING_TABLE.get());
      this.toBlockModel((Block)TFBlocks.NAGA_BOSS_SPAWNER.get(), TwilightForestMod.prefix("block/boss_spawner"));
      this.toBlockModel((Block)TFBlocks.LICH_BOSS_SPAWNER.get(), TwilightForestMod.prefix("block/boss_spawner"));
      this.toBlockModel((Block)TFBlocks.HYDRA_BOSS_SPAWNER.get(), TwilightForestMod.prefix("block/boss_spawner"));
      this.toBlockModel((Block)TFBlocks.UR_GHAST_BOSS_SPAWNER.get(), TwilightForestMod.prefix("block/boss_spawner"));
      this.toBlockModel((Block)TFBlocks.KNIGHT_PHANTOM_BOSS_SPAWNER.get(), TwilightForestMod.prefix("block/boss_spawner"));
      this.toBlockModel((Block)TFBlocks.SNOW_QUEEN_BOSS_SPAWNER.get(), TwilightForestMod.prefix("block/boss_spawner"));
      this.toBlockModel((Block)TFBlocks.MINOSHROOM_BOSS_SPAWNER.get(), TwilightForestMod.prefix("block/boss_spawner"));
      this.toBlockModel((Block)TFBlocks.ALPHA_YETI_BOSS_SPAWNER.get(), TwilightForestMod.prefix("block/boss_spawner"));
      this.toBlockModel((Block)TFBlocks.FINAL_BOSS_BOSS_SPAWNER.get(), TwilightForestMod.prefix("block/boss_spawner"));
      this.toBlockModel((Block)TFBlocks.FIREFLY_JAR.get(), TwilightForestMod.prefix("item/mason_jar"));
      this.toBlock((Block)TFBlocks.FIREFLY_SPAWNER.get());
      this.toBlockModel((Block)TFBlocks.CICADA_JAR.get(), TwilightForestMod.prefix("item/mason_jar"));
      this.generated(TFBlocks.MOSS_PATCH.getId().getPath(), TwilightForestMod.prefix("block/patch/moss"));
      this.generated(TFBlocks.MAYAPPLE.getId().getPath(), TwilightForestMod.prefix("block/mayapple"));
      this.bushBlock((Block)TFBlocks.IRON_OREBERRY.get());
      this.bushBlock((Block)TFBlocks.GOLD_OREBERRY.get());
      this.bushBlock((Block)TFBlocks.COPPER_OREBERRY.get());
      this.bushBlock((Block)TFBlocks.ESSENCE_OREBERRY.get());
      this.bushBlock((Block)TFBlocks.RASPBERRY_BUSH.get());
      this.bushBlock((Block)TFBlocks.BLUEBERRY_BUSH.get());
      this.bushBlock((Block)TFBlocks.BLACKBERRY_BUSH.get());
      this.bushBlock((Block)TFBlocks.MALOBERRY_BUSH.get());
      this.bushBlock((Block)TFBlocks.BLIGHTBERRY_BUSH.get());
      this.bushBlock((Block)TFBlocks.DUSKBERRY_BUSH.get());
      this.bushBlock((Block)TFBlocks.SKYBERRY_BUSH.get());
      this.bushBlock((Block)TFBlocks.STINGBERRY_BUSH.get());
      this.generated(TFBlocks.CLOVER_PATCH.getId().getPath(), TwilightForestMod.prefix("block/patch/clover"));
      this.generated(TFBlocks.FIDDLEHEAD.getId().getPath(), TwilightForestMod.prefix("block/fiddlehead"));
      this.generated(TFBlocks.MUSHGLOOM.getId().getPath(), TwilightForestMod.prefix("block/mushgloom"), TwilightForestMod.prefix("block/mushgloom_head"));
      this.generated(TFBlocks.TORCHBERRY_PLANT.getId().getPath(), TwilightForestMod.prefix("block/torchberry_plant"));
      this.generated(TFBlocks.ROOT_STRAND.getId().getPath(), TwilightForestMod.prefix("block/root_strand"));
      this.generated(TFBlocks.FALLEN_LEAVES.getId().getPath(), ResourceLocation.withDefaultNamespace("block/oak_leaves"));
      this.toBlock((Block)TFBlocks.SMOKER.get());
      this.toBlock((Block)TFBlocks.FIRE_JET.get());
      this.toBlock((Block)TFBlocks.ENCASED_SMOKER.get());
      this.toBlock((Block)TFBlocks.ENCASED_FIRE_JET.get());
      this.toBlock((Block)TFBlocks.NAGASTONE.get());
      this.toBlock((Block)TFBlocks.NAGASTONE_HEAD.get());
      this.toBlock((Block)TFBlocks.NAGASTONE_PILLAR.get());
      this.toBlock((Block)TFBlocks.MOSSY_NAGASTONE_PILLAR.get());
      this.toBlock((Block)TFBlocks.CRACKED_NAGASTONE_PILLAR.get());
      this.toBlock((Block)TFBlocks.ETCHED_NAGASTONE.get());
      this.toBlock((Block)TFBlocks.MOSSY_ETCHED_NAGASTONE.get());
      this.toBlock((Block)TFBlocks.CRACKED_ETCHED_NAGASTONE.get());
      this.toBlock((Block)TFBlocks.NAGASTONE_STAIRS_LEFT.get());
      this.toBlock((Block)TFBlocks.NAGASTONE_STAIRS_RIGHT.get());
      this.toBlock((Block)TFBlocks.CRACKED_NAGASTONE_STAIRS_LEFT.get());
      this.toBlock((Block)TFBlocks.CRACKED_NAGASTONE_STAIRS_RIGHT.get());
      this.toBlock((Block)TFBlocks.MOSSY_NAGASTONE_STAIRS_LEFT.get());
      this.toBlock((Block)TFBlocks.MOSSY_NAGASTONE_STAIRS_RIGHT.get());
      this.toBlockModel((Block)TFBlocks.SPIRAL_BRICKS.get(), TwilightForestMod.prefix("block/spiral_bricks/z_spiral_bottom_right"));
      this.toBlock((Block)TFBlocks.TWISTED_STONE.get());
      this.toBlockModel((Block)TFBlocks.TWISTED_STONE_PILLAR.get(), TwilightForestMod.prefix("block/pillar/pillar_inventory"));
      this.toBlock((Block)TFBlocks.BOLD_STONE_PILLAR.get());
      this.toBlock((Block)TFBlocks.CANOPY_BOOKSHELF.get());
      this.generated(TFBlocks.ROPE.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.ROPE.getId().getPath()));
      this.toBlock((Block)TFBlocks.CANOPY_WINDOW.value());
      this.generated(TFBlocks.CANOPY_WINDOW_PANE.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.CANOPY_WINDOW.getId().getPath()));
      this.toBlockModel((Block)TFBlocks.TERRORCOTTA_ARCS.get(), TwilightForestMod.prefix("block/terrorcotta_arcs"));
      this.toBlockModel((Block)TFBlocks.TERRORCOTTA_LINES.get(), TwilightForestMod.prefix("block/terrorcotta_lines_0"));
      this.toBlockModel((Block)TFBlocks.TERRORCOTTA_CURVES.get(), TwilightForestMod.prefix("block/terrorcotta_curves_90"));
      this.carpet(TFBlocks.CORONATION_CARPET.getRegisteredName(), TFBlocks.CORONATION_CARPET.getId().withPrefix("block/"));
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.OAK_BANISTER.getId().toString(), TwilightForestMod.prefix("item/banister_item")))
         .texture("texture", "minecraft:block/oak_planks");
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.SPRUCE_BANISTER.getId().toString(), TwilightForestMod.prefix("item/banister_item")))
         .texture("texture", "minecraft:block/spruce_planks");
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.BIRCH_BANISTER.getId().toString(), TwilightForestMod.prefix("item/banister_item")))
         .texture("texture", "minecraft:block/birch_planks");
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.JUNGLE_BANISTER.getId().toString(), TwilightForestMod.prefix("item/banister_item")))
         .texture("texture", "minecraft:block/jungle_planks");
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.ACACIA_BANISTER.getId().toString(), TwilightForestMod.prefix("item/banister_item")))
         .texture("texture", "minecraft:block/acacia_planks");
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.DARK_OAK_BANISTER.getId().toString(), TwilightForestMod.prefix("item/banister_item")))
         .texture("texture", "minecraft:block/dark_oak_planks");
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.CRIMSON_BANISTER.getId().toString(), TwilightForestMod.prefix("item/banister_item")))
         .texture("texture", "minecraft:block/crimson_planks");
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.WARPED_BANISTER.getId().toString(), TwilightForestMod.prefix("item/banister_item")))
         .texture("texture", "minecraft:block/warped_planks");
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.VANGROVE_BANISTER.getId().toString(), TwilightForestMod.prefix("item/banister_item")))
         .texture("texture", "minecraft:block/mangrove_planks");
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.BAMBOO_BANISTER.getId().toString(), TwilightForestMod.prefix("item/banister_item")))
         .texture("texture", "minecraft:block/bamboo_planks");
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.CHERRY_BANISTER.getId().toString(), TwilightForestMod.prefix("item/banister_item")))
         .texture("texture", "minecraft:block/cherry_planks");
      this.woodBlock((Block)TFBlocks.OAK_DRYING_RACK.get(), "rack/oak");
      this.woodBlock((Block)TFBlocks.SPRUCE_DRYING_RACK.get(), "rack/spruce");
      this.woodBlock((Block)TFBlocks.BIRCH_DRYING_RACK.get(), "rack/birch");
      this.woodBlock((Block)TFBlocks.JUNGLE_DRYING_RACK.get(), "rack/jungle");
      this.woodBlock((Block)TFBlocks.ACACIA_DRYING_RACK.get(), "rack/acacia");
      this.woodBlock((Block)TFBlocks.DARK_OAK_DRYING_RACK.get(), "rack/dark_oak");
      this.woodBlock((Block)TFBlocks.CRIMSON_DRYING_RACK.get(), "rack/crimson");
      this.woodBlock((Block)TFBlocks.WARPED_DRYING_RACK.get(), "rack/warped");
      this.woodBlock((Block)TFBlocks.VANGROVE_DRYING_RACK.get(), "rack/vanilla_mangrove");
      this.woodBlock((Block)TFBlocks.BAMBOO_DRYING_RACK.get(), "rack/bamboo");
      this.woodBlock((Block)TFBlocks.CHERRY_DRYING_RACK.get(), "rack/cherry");
      this.toBlock((Block)TFBlocks.TWILIGHT_OAK_LOG.get());
      this.toBlock((Block)TFBlocks.STRIPPED_TWILIGHT_OAK_LOG.get());
      this.toBlock((Block)TFBlocks.TWILIGHT_OAK_WOOD.get());
      this.toBlock((Block)TFBlocks.STRIPPED_TWILIGHT_OAK_WOOD.get());
      this.toBlock((Block)TFBlocks.TWILIGHT_OAK_LEAVES.get());
      this.toBlock((Block)TFBlocks.RAINBOW_OAK_LEAVES.get());
      this.generated(TFBlocks.RAINBOW_OAK_SAPLING.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.RAINBOW_OAK_SAPLING.getId().getPath()));
      this.generated(TFBlocks.TWILIGHT_OAK_SAPLING.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.TWILIGHT_OAK_SAPLING.getId().getPath()));
      this.woodBlock((Block)TFBlocks.TWILIGHT_OAK_PLANKS.get(), "planks/twilight_oak");
      this.woodBlock((Block)TFBlocks.TWILIGHT_OAK_STAIRS.get(), "stairs/twilight_oak");
      this.woodBlock((Block)TFBlocks.TWILIGHT_OAK_SLAB.get(), "slab/twilight_oak");
      this.woodenButton((Block)TFBlocks.TWILIGHT_OAK_BUTTON.get(), "twilight_oak");
      this.woodenFence((Block)TFBlocks.TWILIGHT_OAK_FENCE.get(), "twilight_oak");
      this.woodBlock((Block)TFBlocks.TWILIGHT_OAK_GATE.get(), "fence_gate/twilight_oak");
      this.woodBlock((Block)TFBlocks.TWILIGHT_OAK_PLATE.get(), "pressure_plate/twilight_oak");
      this.woodBlockModel((Block)TFBlocks.TWILIGHT_OAK_TRAPDOOR.get(), "twilight_oak_trapdoor_bottom", "trapdoor/twilight_oak");
      this.singleTex(TFItems.TWILIGHT_OAK_SIGN);
      this.singleTex(TFItems.TWILIGHT_OAK_HANGING_SIGN);
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.TWILIGHT_OAK_BANISTER.getId().toString(), TwilightForestMod.prefix("item/banister_item")))
         .texture("texture", "block/wood/planks_twilight_oak_0");
      this.generated(TFBlocks.TWILIGHT_OAK_DOOR.getId().getPath(), TwilightForestMod.prefix("item/" + TFBlocks.TWILIGHT_OAK_DOOR.getId().getPath()));
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.TWILIGHT_OAK_CHEST.getId().toString(), "item/chest"))
         .texture("particle", TwilightForestMod.prefix("block/wood/planks_twilight_oak_0"));
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.TWILIGHT_OAK_TRAPPED_CHEST.getId().toString(), "item/chest"))
         .texture("particle", TwilightForestMod.prefix("block/wood/planks_twilight_oak_0"));
      this.woodBlock((Block)TFBlocks.TWILIGHT_OAK_DRYING_RACK.get(), "rack/twilight_oak");
      this.toBlock((Block)TFBlocks.CANOPY_LOG.get());
      this.toBlock((Block)TFBlocks.STRIPPED_CANOPY_LOG.get());
      this.toBlock((Block)TFBlocks.CANOPY_WOOD.get());
      this.toBlock((Block)TFBlocks.STRIPPED_CANOPY_WOOD.get());
      this.toBlock((Block)TFBlocks.CANOPY_LEAVES.get());
      this.generated(TFBlocks.CANOPY_SAPLING.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.CANOPY_SAPLING.getId().getPath()));
      this.woodBlock((Block)TFBlocks.CANOPY_PLANKS.get(), "planks/canopy");
      this.woodBlock((Block)TFBlocks.CANOPY_STAIRS.get(), "stairs/canopy");
      this.woodBlock((Block)TFBlocks.CANOPY_SLAB.get(), "slab/canopy");
      this.woodenButton((Block)TFBlocks.CANOPY_BUTTON.get(), "canopy");
      this.woodenFence((Block)TFBlocks.CANOPY_FENCE.get(), "canopy");
      this.woodBlock((Block)TFBlocks.CANOPY_GATE.get(), "fence_gate/canopy");
      this.woodBlock((Block)TFBlocks.CANOPY_PLATE.get(), "pressure_plate/canopy");
      this.woodBlockModel((Block)TFBlocks.CANOPY_TRAPDOOR.get(), "canopy_trapdoor_bottom", "trapdoor/canopy");
      this.singleTex(TFItems.CANOPY_SIGN);
      this.singleTex(TFItems.CANOPY_HANGING_SIGN);
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.CANOPY_BANISTER.getId().toString(), TwilightForestMod.prefix("item/banister_item")))
         .texture("texture", "block/wood/planks_canopy_0");
      this.generated(TFBlocks.CANOPY_DOOR.getId().getPath(), TwilightForestMod.prefix("item/" + TFBlocks.CANOPY_DOOR.getId().getPath()));
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.CANOPY_CHEST.getId().toString(), "item/chest"))
         .texture("particle", TwilightForestMod.prefix("block/wood/planks_canopy_0"));
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.CANOPY_TRAPPED_CHEST.getId().toString(), "item/chest"))
         .texture("particle", TwilightForestMod.prefix("block/wood/planks_canopy_0"));
      this.woodBlock((Block)TFBlocks.CANOPY_DRYING_RACK.get(), "rack/canopy");
      this.toBlock((Block)TFBlocks.MANGROVE_LOG.get());
      this.toBlock((Block)TFBlocks.STRIPPED_MANGROVE_LOG.get());
      this.toBlock((Block)TFBlocks.MANGROVE_WOOD.get());
      this.toBlock((Block)TFBlocks.STRIPPED_MANGROVE_WOOD.get());
      this.toBlock((Block)TFBlocks.MANGROVE_LEAVES.get());
      this.generated(TFBlocks.MANGROVE_SAPLING.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.MANGROVE_SAPLING.getId().getPath()));
      this.woodBlock((Block)TFBlocks.MANGROVE_PLANKS.get(), "planks/mangrove");
      this.woodBlock((Block)TFBlocks.MANGROVE_STAIRS.get(), "stairs/mangrove");
      this.woodBlock((Block)TFBlocks.MANGROVE_SLAB.get(), "slab/mangrove");
      this.woodenButton((Block)TFBlocks.MANGROVE_BUTTON.get(), "mangrove");
      this.woodenFence((Block)TFBlocks.MANGROVE_FENCE.get(), "mangrove");
      this.woodBlock((Block)TFBlocks.MANGROVE_GATE.get(), "fence_gate/mangrove");
      this.woodBlock((Block)TFBlocks.MANGROVE_PLATE.get(), "pressure_plate/mangrove");
      this.woodBlockModel((Block)TFBlocks.MANGROVE_TRAPDOOR.get(), "mangrove_trapdoor_bottom", "trapdoor/mangrove");
      this.singleTex(TFItems.MANGROVE_SIGN);
      this.singleTex(TFItems.MANGROVE_HANGING_SIGN);
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.MANGROVE_BANISTER.getId().toString(), TwilightForestMod.prefix("item/banister_item")))
         .texture("texture", "block/wood/planks_mangrove_0");
      this.generated(TFBlocks.MANGROVE_DOOR.getId().getPath(), TwilightForestMod.prefix("item/" + TFBlocks.MANGROVE_DOOR.getId().getPath()));
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.MANGROVE_CHEST.getId().toString(), "item/chest"))
         .texture("particle", TwilightForestMod.prefix("block/wood/planks_mangrove_0"));
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.MANGROVE_TRAPPED_CHEST.getId().toString(), "item/chest"))
         .texture("particle", TwilightForestMod.prefix("block/wood/planks_mangrove_0"));
      this.woodBlock((Block)TFBlocks.MANGROVE_DRYING_RACK.get(), "rack/mangrove");
      this.toBlock((Block)TFBlocks.DARK_LOG.get());
      this.toBlock((Block)TFBlocks.STRIPPED_DARK_LOG.get());
      this.toBlock((Block)TFBlocks.DARK_WOOD.get());
      this.toBlock((Block)TFBlocks.STRIPPED_DARK_WOOD.get());
      this.toBlock((Block)TFBlocks.DARK_LEAVES.get());
      this.generated(TFBlocks.DARKWOOD_SAPLING.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.DARKWOOD_SAPLING.getId().getPath()));
      this.woodBlock((Block)TFBlocks.DARK_PLANKS.get(), "planks/darkwood");
      this.woodBlock((Block)TFBlocks.DARK_STAIRS.get(), "stairs/darkwood");
      this.woodBlock((Block)TFBlocks.DARK_SLAB.get(), "slab/darkwood");
      this.woodenButton((Block)TFBlocks.DARK_BUTTON.get(), "darkwood");
      this.woodenFence((Block)TFBlocks.DARK_FENCE.get(), "darkwood");
      this.woodBlock((Block)TFBlocks.DARK_GATE.get(), "fence_gate/darkwood");
      this.woodBlock((Block)TFBlocks.DARK_PLATE.get(), "pressure_plate/darkwood");
      this.woodBlockModel((Block)TFBlocks.DARK_TRAPDOOR.get(), "darkwood_trapdoor_bottom", "trapdoor/darkwood");
      this.singleTex(TFItems.DARK_SIGN);
      this.singleTex(TFItems.DARK_HANGING_SIGN);
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.DARK_BANISTER.getId().toString(), TwilightForestMod.prefix("item/banister_item")))
         .texture("texture", "block/wood/planks_darkwood_0");
      this.generated(TFBlocks.DARK_DOOR.getId().getPath(), TwilightForestMod.prefix("item/" + TFBlocks.DARK_DOOR.getId().getPath()));
      this.generated(TFBlocks.HOLLOW_OAK_SAPLING.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.HOLLOW_OAK_SAPLING.getId().getPath()));
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.DARK_CHEST.getId().toString(), "item/chest"))
         .texture("particle", TwilightForestMod.prefix("block/wood/planks_darkwood_0"));
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.DARK_TRAPPED_CHEST.getId().toString(), "item/chest"))
         .texture("particle", TwilightForestMod.prefix("block/wood/planks_darkwood_0"));
      this.woodBlock((Block)TFBlocks.DARK_DRYING_RACK.get(), "rack/darkwood");
      this.toBlock((Block)TFBlocks.TIME_LOG.get());
      this.toBlock((Block)TFBlocks.STRIPPED_TIME_LOG.get());
      this.toBlock((Block)TFBlocks.TIME_WOOD.get());
      this.toBlock((Block)TFBlocks.STRIPPED_TIME_WOOD.get());
      this.toBlock((Block)TFBlocks.TIME_LOG_CORE.get());
      this.toBlock((Block)TFBlocks.TIME_LEAVES.get());
      this.generated(TFBlocks.TIME_SAPLING.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.TIME_SAPLING.getId().getPath()));
      this.woodBlock((Block)TFBlocks.TIME_PLANKS.get(), "planks/time");
      this.woodBlock((Block)TFBlocks.TIME_STAIRS.get(), "stairs/time");
      this.woodBlock((Block)TFBlocks.TIME_SLAB.get(), "slab/time");
      this.woodenButton((Block)TFBlocks.TIME_BUTTON.get(), "time");
      this.woodenFence((Block)TFBlocks.TIME_FENCE.get(), "time");
      this.woodBlock((Block)TFBlocks.TIME_GATE.get(), "fence_gate/time");
      this.woodBlock((Block)TFBlocks.TIME_PLATE.get(), "pressure_plate/time");
      this.woodBlockModel((Block)TFBlocks.TIME_TRAPDOOR.get(), "time_trapdoor_bottom", "trapdoor/time");
      this.singleTex(TFItems.TIME_SIGN);
      this.singleTex(TFItems.TIME_HANGING_SIGN);
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.TIME_BANISTER.getId().toString(), TwilightForestMod.prefix("item/banister_item")))
         .texture("texture", "block/wood/planks_time_0");
      this.generated(TFBlocks.TIME_DOOR.getId().getPath(), TwilightForestMod.prefix("item/" + TFBlocks.TIME_DOOR.getId().getPath()));
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.TIME_CHEST.getId().toString(), "item/chest"))
         .texture("particle", TwilightForestMod.prefix("block/wood/planks_time_0"));
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.TIME_TRAPPED_CHEST.getId().toString(), "item/chest"))
         .texture("particle", TwilightForestMod.prefix("block/wood/planks_time_0"));
      this.woodBlock((Block)TFBlocks.TIME_DRYING_RACK.get(), "rack/time");
      this.toBlock((Block)TFBlocks.TRANSFORMATION_LOG.get());
      this.toBlock((Block)TFBlocks.STRIPPED_TRANSFORMATION_LOG.get());
      this.toBlock((Block)TFBlocks.TRANSFORMATION_WOOD.get());
      this.toBlock((Block)TFBlocks.STRIPPED_TRANSFORMATION_WOOD.get());
      this.toBlock((Block)TFBlocks.TRANSFORMATION_LOG_CORE.get());
      this.toBlock((Block)TFBlocks.TRANSFORMATION_LEAVES.get());
      this.generated(TFBlocks.TRANSFORMATION_SAPLING.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.TRANSFORMATION_SAPLING.getId().getPath()));
      this.woodBlock((Block)TFBlocks.TRANSFORMATION_PLANKS.get(), "planks/trans");
      this.woodBlock((Block)TFBlocks.TRANSFORMATION_STAIRS.get(), "stairs/trans");
      this.woodBlock((Block)TFBlocks.TRANSFORMATION_SLAB.get(), "slab/trans");
      this.woodenButton((Block)TFBlocks.TRANSFORMATION_BUTTON.get(), "trans");
      this.woodenFence((Block)TFBlocks.TRANSFORMATION_FENCE.get(), "trans");
      this.woodBlock((Block)TFBlocks.TRANSFORMATION_GATE.get(), "fence_gate/trans");
      this.woodBlock((Block)TFBlocks.TRANSFORMATION_PLATE.get(), "pressure_plate/trans");
      this.woodBlockModel((Block)TFBlocks.TRANSFORMATION_TRAPDOOR.get(), "trans_trapdoor_bottom", "trapdoor/trans");
      this.singleTex(TFItems.TRANSFORMATION_SIGN);
      this.singleTex(TFItems.TRANSFORMATION_HANGING_SIGN);
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.TRANSFORMATION_BANISTER.getId().toString(), TwilightForestMod.prefix("item/banister_item")))
         .texture("texture", "block/wood/planks_trans_0");
      this.generated(TFBlocks.TRANSFORMATION_DOOR.getId().getPath(), TwilightForestMod.prefix("item/" + TFBlocks.TRANSFORMATION_DOOR.getId().getPath()));
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.TRANSFORMATION_CHEST.getId().toString(), "item/chest"))
         .texture("particle", TwilightForestMod.prefix("block/wood/planks_trans_0"));
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.TRANSFORMATION_TRAPPED_CHEST.getId().toString(), "item/chest"))
         .texture("particle", TwilightForestMod.prefix("block/wood/planks_trans_0"));
      this.woodBlock((Block)TFBlocks.TRANSFORMATION_DRYING_RACK.get(), "rack/trans");
      this.toBlock((Block)TFBlocks.MINING_LOG.get());
      this.toBlock((Block)TFBlocks.STRIPPED_MINING_LOG.get());
      this.toBlock((Block)TFBlocks.MINING_WOOD.get());
      this.toBlock((Block)TFBlocks.STRIPPED_MINING_WOOD.get());
      this.toBlock((Block)TFBlocks.MINING_LOG_CORE.get());
      this.toBlock((Block)TFBlocks.MINING_LEAVES.get());
      this.generated(TFBlocks.MINING_SAPLING.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.MINING_SAPLING.getId().getPath()));
      this.woodBlock((Block)TFBlocks.MINING_PLANKS.get(), "planks/mine");
      this.woodBlock((Block)TFBlocks.MINING_STAIRS.get(), "stairs/mine");
      this.woodBlock((Block)TFBlocks.MINING_SLAB.get(), "slab/mine");
      this.woodenButton((Block)TFBlocks.MINING_BUTTON.get(), "mine");
      this.woodenFence((Block)TFBlocks.MINING_FENCE.get(), "mine");
      this.woodBlock((Block)TFBlocks.MINING_GATE.get(), "fence_gate/mine");
      this.woodBlock((Block)TFBlocks.MINING_PLATE.get(), "pressure_plate/mine");
      this.woodBlockModel((Block)TFBlocks.MINING_TRAPDOOR.get(), "mine_trapdoor_bottom", "trapdoor/mine");
      this.singleTex(TFItems.MINING_SIGN);
      this.singleTex(TFItems.MINING_HANGING_SIGN);
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.MINING_BANISTER.getId().toString(), TwilightForestMod.prefix("item/banister_item")))
         .texture("texture", "block/wood/planks_mine_0");
      this.generated(TFBlocks.MINING_DOOR.getId().getPath(), TwilightForestMod.prefix("item/" + TFBlocks.MINING_DOOR.getId().getPath()));
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.MINING_CHEST.getId().toString(), "item/chest"))
         .texture("particle", TwilightForestMod.prefix("block/wood/planks_mine_0"));
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.MINING_TRAPPED_CHEST.getId().toString(), "item/chest"))
         .texture("particle", TwilightForestMod.prefix("block/wood/planks_mine_0"));
      this.woodBlock((Block)TFBlocks.MINING_DRYING_RACK.get(), "rack/mine");
      this.toBlock((Block)TFBlocks.SORTING_LOG.get());
      this.toBlock((Block)TFBlocks.STRIPPED_SORTING_LOG.get());
      this.toBlock((Block)TFBlocks.SORTING_WOOD.get());
      this.toBlock((Block)TFBlocks.STRIPPED_SORTING_WOOD.get());
      this.toBlock((Block)TFBlocks.SORTING_LOG_CORE.get());
      this.toBlock((Block)TFBlocks.SORTING_LEAVES.get());
      this.generated(TFBlocks.SORTING_SAPLING.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.SORTING_SAPLING.getId().getPath()));
      this.woodBlock((Block)TFBlocks.SORTING_PLANKS.get(), "planks/sort");
      this.woodBlock((Block)TFBlocks.SORTING_STAIRS.get(), "stairs/sort");
      this.woodBlock((Block)TFBlocks.SORTING_SLAB.get(), "slab/sort");
      this.woodenButton((Block)TFBlocks.SORTING_BUTTON.get(), "sort");
      this.woodenFence((Block)TFBlocks.SORTING_FENCE.get(), "sort");
      this.woodBlock((Block)TFBlocks.SORTING_GATE.get(), "fence_gate/sort");
      this.woodBlock((Block)TFBlocks.SORTING_PLATE.get(), "pressure_plate/sort");
      this.woodBlockModel((Block)TFBlocks.SORTING_TRAPDOOR.get(), "sort_trapdoor_bottom", "trapdoor/sort");
      this.singleTex(TFItems.SORTING_SIGN);
      this.singleTex(TFItems.SORTING_HANGING_SIGN);
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.SORTING_BANISTER.getId().toString(), TwilightForestMod.prefix("item/banister_item")))
         .texture("texture", "block/wood/planks_sort_0");
      this.generated(TFBlocks.SORTING_DOOR.getId().getPath(), TwilightForestMod.prefix("item/" + TFBlocks.SORTING_DOOR.getId().getPath()));
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.SORTING_CHEST.getId().toString(), "item/chest"))
         .texture("particle", TwilightForestMod.prefix("block/wood/planks_sort_0"));
      ((ItemModelBuilder)this.withExistingParent(TFBlocks.SORTING_TRAPPED_CHEST.getId().toString(), "item/chest"))
         .texture("particle", TwilightForestMod.prefix("block/wood/planks_sort_0"));
      this.woodBlock((Block)TFBlocks.SORTING_DRYING_RACK.get(), "rack/sort");
      ResourceLocation templateTrophy = TwilightForestMod.prefix("item/template_trophy");
      this.withExistingParent(TFItems.NAGA_TROPHY.getId().toString(), templateTrophy);
      this.withExistingParent(TFItems.LICH_TROPHY.getId().toString(), templateTrophy);
      this.withExistingParent(TFItems.MINOSHROOM_TROPHY.getId().toString(), templateTrophy);
      ((ItemModelBuilder)this.withExistingParent(TFItems.HYDRA_TROPHY.getId().toString(), templateTrophy))
         .transforms()
         .transform(itemDisplayContextEnumExtension.JARRED)
         .translation(0.0F, 0.0F, 1.0F)
         .rotation(0.0F, 180.0F, 0.0F)
         .end()
         .end();
      ((ItemModelBuilder)this.withExistingParent(TFItems.KNIGHT_PHANTOM_TROPHY.getId().toString(), templateTrophy))
         .transforms()
         .transform(itemDisplayContextEnumExtension.JARRED)
         .scale(0.85F)
         .rotation(0.0F, 180.0F, 0.0F)
         .end()
         .end();
      ((ItemModelBuilder)this.withExistingParent(TFItems.UR_GHAST_TROPHY.getId().toString(), templateTrophy))
         .transforms()
         .transform(ItemDisplayContext.HEAD)
         .scale(1.6F)
         .translation(0.0F, 18.0F, 0.0F)
         .rotation(180.0F, 0.0F, 180.0F)
         .end()
         .transform(ItemDisplayContext.FIXED)
         .translation(0.0F, 14.0F, -2.0F)
         .rotation(0.0F, 180.0F, 0.0F)
         .end()
         .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
         .scale(0.5F)
         .translation(0.0F, 4.0F, 3.6F)
         .rotation(45.0F, 135.0F, 0.0F)
         .end()
         .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
         .translation(0.0F, 11.0F, 0.0F)
         .rotation(0.0F, 180.0F, 0.0F)
         .end()
         .transform(itemDisplayContextEnumExtension.JARRED)
         .scale(0.85F)
         .translation(0.0F, 14.0F, 0.0F)
         .rotation(0.0F, 180.0F, 0.0F)
         .end()
         .end();
      ((ItemModelBuilder)this.withExistingParent(TFItems.ALPHA_YETI_TROPHY.getId().toString(), templateTrophy))
         .transforms()
         .transform(ItemDisplayContext.HEAD)
         .scale(1.5F)
         .translation(0.0F, 2.0F, 0.0F)
         .rotation(180.0F, 0.0F, 180.0F)
         .end()
         .transform(itemDisplayContextEnumExtension.JARRED)
         .translation(0.0F, 0.0F, 1.0F)
         .rotation(0.0F, 180.0F, 0.0F)
         .end()
         .end();
      this.withExistingParent(TFItems.SNOW_QUEEN_TROPHY.getId().toString(), templateTrophy);
      ((ItemModelBuilder)this.withExistingParent(TFItems.QUEST_RAM_TROPHY.getId().toString(), templateTrophy))
         .transforms()
         .transform(itemDisplayContextEnumExtension.JARRED)
         .scale(0.85F)
         .rotation(0.0F, 180.0F, 0.0F)
         .end()
         .end();
      this.withExistingParent(TFItems.MYSTIC_CROWN.getId().toString(), templateTrophy);
      this.withExistingParent(TFItems.CREEPER_SKULL_CANDLE.getId().toString(), TwilightForestMod.prefix("item/template_skull_candle"));
      this.withExistingParent(TFItems.PIGLIN_SKULL_CANDLE.getId().toString(), TwilightForestMod.prefix("item/template_skull_candle"));
      this.withExistingParent(TFItems.PLAYER_SKULL_CANDLE.getId().toString(), TwilightForestMod.prefix("item/template_skull_candle"));
      this.withExistingParent(TFItems.SKELETON_SKULL_CANDLE.getId().toString(), TwilightForestMod.prefix("item/template_skull_candle"));
      this.withExistingParent(TFItems.WITHER_SKELETON_SKULL_CANDLE.getId().toString(), TwilightForestMod.prefix("item/template_skull_candle"));
      this.withExistingParent(TFItems.ZOMBIE_SKULL_CANDLE.getId().toString(), TwilightForestMod.prefix("item/template_skull_candle"));
      this.hollowLog(TFItems.HOLLOW_OAK_LOG);
      this.hollowLog(TFItems.HOLLOW_SPRUCE_LOG);
      this.hollowLog(TFItems.HOLLOW_BIRCH_LOG);
      this.hollowLog(TFItems.HOLLOW_JUNGLE_LOG);
      this.hollowLog(TFItems.HOLLOW_ACACIA_LOG);
      this.hollowLog(TFItems.HOLLOW_DARK_OAK_LOG);
      this.hollowLog(TFItems.HOLLOW_CRIMSON_STEM);
      this.hollowLog(TFItems.HOLLOW_WARPED_STEM);
      this.hollowLog(TFItems.HOLLOW_VANGROVE_LOG);
      this.hollowLog(TFItems.HOLLOW_CHERRY_LOG);
      this.hollowLog(TFItems.HOLLOW_TWILIGHT_OAK_LOG);
      this.hollowLog(TFItems.HOLLOW_CANOPY_LOG);
      this.hollowLog(TFItems.HOLLOW_MANGROVE_LOG);
      this.hollowLog(TFItems.HOLLOW_DARK_LOG);
      this.hollowLog(TFItems.HOLLOW_TIME_LOG);
      this.hollowLog(TFItems.HOLLOW_TRANSFORMATION_LOG);
      this.hollowLog(TFItems.HOLLOW_MINING_LOG);
      this.hollowLog(TFItems.HOLLOW_SORTING_LOG);
      this.singleTex(TFItems.NAGA_SCALE);
      this.singleTex(TFItems.NAGA_CHESTPLATE);
      this.singleTex(TFItems.NAGA_LEGGINGS);
      this.singleTexTool(TFItems.TWILIGHT_SCEPTER);
      this.singleTexTool(TFItems.LIFEDRAIN_SCEPTER);
      this.singleTexTool(TFItems.ZOMBIE_SCEPTER);
      this.singleTexTool(TFItems.FORTIFICATION_SCEPTER);
      this.exanimateEssence(TFItems.EXANIMATE_ESSENCE);
      this.singleTex(TFItems.WROUGHT_IRON_BAR);
      this.singleTex(TFItems.MAGIC_PAINTING);
      ModelFile active = this.generated("ore_meter_active", TwilightForestMod.prefix("item/ore_meter_active"));
      this.singleTex(TFItems.ORE_METER).override().predicate(TwilightForestMod.prefix("active"), 1.0F).model(active).end();
      this.singleTex(TFItems.FILLED_MAGIC_MAP);
      this.singleTex(TFItems.FILLED_MAZE_MAP);
      this.singleTex(TFItems.FILLED_ORE_MAP);
      this.singleTex(TFItems.RAVEN_FEATHER);
      this.singleTex(TFItems.MAZE_SLIME_BALL);
      this.singleTex(TFItems.MAGIC_MAP_FOCUS);
      this.singleTex(TFItems.MAZE_MAP_FOCUS);
      this.singleTex(TFItems.LIVEROOT);
      this.singleTex(TFItems.RAW_IRONWOOD);
      this.singleTex(TFItems.IRONWOOD_INGOT);
      this.trimmedArmor(TFItems.IRONWOOD_HELMET);
      this.trimmedArmor(TFItems.IRONWOOD_CHESTPLATE);
      this.trimmedArmor(TFItems.IRONWOOD_LEGGINGS);
      this.trimmedArmor(TFItems.IRONWOOD_BOOTS);
      this.singleTexTool(TFItems.IRONWOOD_SWORD);
      this.singleTexTool(TFItems.IRONWOOD_PICKAXE);
      this.singleTexTool(TFItems.IRONWOOD_AXE);
      this.singleTexTool(TFItems.IRONWOOD_SHOVEL);
      this.singleTexTool(TFItems.IRONWOOD_HOE);
      this.singleTexFullbright(TFItems.TORCHBERRIES);
      this.singleTex(TFItems.RASPBERRY);
      this.singleTex(TFItems.BLUEBERRY);
      this.singleTex(TFItems.BLACKBERRY);
      this.singleTex(TFItems.MALOBERRY);
      this.singleTex(TFItems.BLIGHTBERRY);
      this.singleTex(TFItems.DUSKBERRY);
      this.singleTex(TFItems.SKYBERRY);
      this.singleTex(TFItems.STINGBERRY);
      ((ItemModelBuilder)((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(TFItems.BERRY_MEDLEY.getId().getPath(), "item/generated"))
               .texture("particle", TwilightForestMod.prefix("item/berry_medley")))
            .texture("layer0", ResourceLocation.withDefaultNamespace("item/bowl")))
         .texture("layer1", TwilightForestMod.prefix("item/berry_medley"));
      ((ItemModelBuilder)((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(TFItems.MOSS_SOUP.getId().getPath(), "item/generated"))
               .texture("particle", TwilightForestMod.prefix("block/patch/moss")))
            .texture("layer0", ResourceLocation.withDefaultNamespace("item/bowl")))
         .texture("layer1", TwilightForestMod.prefix("item/moss_soup"));
      this.singleTex(TFItems.SHIKA_SENBEI);
      this.singleTex(TFItems.MONSTER_JERKY);
      this.singleTex(TFItems.BEEF_JERKY);
      this.singleTex(TFItems.CHICKEN_JERKY);
      this.singleTex(TFItems.PORK_JERKY);
      this.singleTex(TFItems.MUTTON_JERKY);
      this.singleTex(TFItems.RABBIT_JERKY);
      this.singleTex(TFItems.COD_JERKY);
      this.singleTex(TFItems.SALMON_JERKY);
      this.singleTex(TFItems.TROPICAL_FISH_JERKY);
      this.singleTex(TFItems.FUGU_JERKY);
      this.singleTex(TFItems.RAW_VENISON);
      this.singleTex(TFItems.COOKED_VENISON);
      this.singleTex(TFItems.VENISON_JERKY);
      this.singleTex(TFItems.TREATED_LEATHER);
      this.singleTex(TFItems.TANNED_LEATHER);
      this.singleTex(TFItems.HYDRA_CHOP);
      this.singleTex(TFItems.TANNIN);
      this.singleTex(TFItems.FIERY_BLOOD);
      this.singleTex(TFItems.FIERY_TEARS);
      this.singleTexFullbright(TFItems.FIERY_INGOT);
      this.trimmedFullbrightArmor(TFItems.FIERY_HELMET);
      this.trimmedFullbrightArmor(TFItems.FIERY_CHESTPLATE);
      this.trimmedFullbrightArmor(TFItems.FIERY_LEGGINGS);
      this.trimmedFullbrightArmor(TFItems.FIERY_BOOTS);
      this.singleTexFullbrightTool(TFItems.FIERY_SWORD);
      this.singleTexFullbrightTool(TFItems.FIERY_PICKAXE);
      this.singleTex(TFItems.STEELEAF_INGOT);
      this.trimmedArmor(TFItems.STEELEAF_HELMET);
      this.trimmedArmor(TFItems.STEELEAF_CHESTPLATE);
      this.trimmedArmor(TFItems.STEELEAF_LEGGINGS);
      this.trimmedArmor(TFItems.STEELEAF_BOOTS);
      this.singleTexTool(TFItems.STEELEAF_SWORD);
      this.singleTexTool(TFItems.STEELEAF_PICKAXE);
      this.singleTexTool(TFItems.STEELEAF_AXE);
      this.singleTexTool(TFItems.STEELEAF_SHOVEL);
      this.singleTexTool(TFItems.STEELEAF_HOE);
      this.travellersTex(TFItems.TRAVELLERS_GOGGLES, TFItems.TRAVELLERS_VEST, TFItems.TRAVELLERS_WINGS, TFItems.TRAVELLERS_BOOTS);
      this.singleTex(TFItems.TRAVELLERS_BELT);
      this.singleTex(TFItems.TRAVELLERS_GLOVES);
      this.singleTexTool(TFItems.DIAMOND_MINOTAUR_AXE);
      this.singleTexTool(TFItems.GOLDEN_MINOTAUR_AXE);
      this.singleTexTool(TFItems.MAZEBREAKER_PICKAXE);
      this.singleTex(TFItems.TRANSFORMATION_POWDER);
      this.singleTex(TFItems.RAW_MEEF);
      this.singleTex(TFItems.COOKED_MEEF);
      this.singleTex(TFItems.MEEF_JERKY);
      this.singleTex(TFItems.GELATINOUS_SLIME_DROP);
      this.singleTex(TFItems.GELATINOUS_MAZE_SLIME_DROP);
      this.singleTex(TFItems.MEEF_STROGANOFF);
      this.singleTex(TFItems.MAZE_WAFER);
      this.singleTex(TFItems.MAGIC_MAP);
      this.singleTex(TFItems.MAZE_MAP);
      this.singleTex(TFItems.ORE_MAP);
      ModelFile magnetPull1 = this.specialTool("ore_magnet_pulling_1", TwilightForestMod.prefix("item/ore_magnet_pulling_1"));
      ModelFile magnetPull2 = this.specialTool("ore_magnet_pulling_2", TwilightForestMod.prefix("item/ore_magnet_pulling_2"));
      this.specialTool(TFItems.ORE_MAGNET)
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("pulling"), 1.0F)
         .predicate(ResourceLocation.withDefaultNamespace("pull"), 0.5F)
         .model(magnetPull1)
         .end()
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("pulling"), 1.0F)
         .predicate(ResourceLocation.withDefaultNamespace("pull"), 1.0F)
         .model(magnetPull2)
         .end();
      this.crumbleHorn(TFItems.CRUMBLE_HORN);
      this.singleTexTool(TFItems.PEACOCK_FEATHER_FAN);
      this.moonwormQueen(TFItems.MOONWORM_QUEEN);
      this.singleTex(TFItems.CHARM_OF_KEEPING_1);
      this.singleTex(TFItems.CHARM_OF_KEEPING_2);
      this.singleTex(TFItems.CHARM_OF_KEEPING_3);
      this.singleTex(TFItems.CHARM_OF_LIFE_1);
      this.singleTex(TFItems.CHARM_OF_LIFE_2);
      this.singleTex(TFItems.IRON_BERRY);
      this.singleTex(TFItems.GOLD_BERRY);
      this.singleTex(TFItems.COPPER_BERRY);
      this.singleTexFullbright(TFItems.ESSENCE_BERRY);
      this.singleTex(TFItems.COPPER_NUGGET);
      this.singleTexFullbright(TFItems.TOWER_KEY);
      this.generated(
         TFItems.BORER_ESSENCE.getId().getPath(),
         TwilightForestMod.prefix("item/" + TFItems.BORER_ESSENCE.getId().getPath()),
         TwilightForestMod.prefix("item/borer_essence_particles")
      );
      this.buildItem(TFItems.CARMINITE.getId().getPath(), "item/generated", 7, TwilightForestMod.prefix("item/" + TFItems.CARMINITE.getId().getPath()));
      this.singleTex(TFItems.ARMOR_SHARD);
      this.singleTex(TFItems.ARMOR_SHARD_CLUSTER);
      this.singleTex(TFItems.KNIGHTMETAL_INGOT);
      this.trimmedArmor(TFItems.KNIGHTMETAL_HELMET);
      this.trimmedArmor(TFItems.KNIGHTMETAL_CHESTPLATE);
      this.trimmedArmor(TFItems.KNIGHTMETAL_LEGGINGS);
      this.trimmedArmor(TFItems.KNIGHTMETAL_BOOTS);
      this.singleTexTool(TFItems.KNIGHTMETAL_SWORD);
      this.singleTexTool(TFItems.KNIGHTMETAL_PICKAXE);
      this.singleTexTool(TFItems.KNIGHTMETAL_AXE);
      this.singleTex(TFItems.KNIGHTMETAL_RING);
      this.trimmedArmor(TFItems.PHANTOM_HELMET);
      this.trimmedArmor(TFItems.PHANTOM_CHESTPLATE);
      this.lamp(TFItems.LAMP_OF_CINDERS);
      this.singleTex(TFItems.POCKET_WATCH);
      this.singleTex(TFItems.EMPERORS_CLOTH);
      this.singleTex(TFItems.FOUR_LEAF_CLOVER);
      this.singleTex(TFItems.CROWN_SPLINTER);
      this.singleTex(TFItems.ALPHA_YETI_FUR);
      this.singleTex(TFItems.YETI_HELMET);
      this.trimmedArmor(TFItems.YETI_CHESTPLATE);
      this.trimmedArmor(TFItems.YETI_LEGGINGS);
      this.trimmedArmor(TFItems.YETI_BOOTS);
      this.singleTex(TFItems.ICE_BOMB);
      this.singleTex(TFItems.ARCTIC_FUR);
      this.trimmedLayeredArmor(TFItems.ARCTIC_HELMET);
      this.trimmedLayeredArmor(TFItems.ARCTIC_CHESTPLATE);
      this.trimmedLayeredArmor(TFItems.ARCTIC_LEGGINGS);
      this.trimmedLayeredArmor(TFItems.ARCTIC_BOOTS);
      this.singleTex(TFItems.MAGIC_BEANS);
      ModelFile triplePulling0 = this.bowItem("triple_bow_pulling_0", TwilightForestMod.prefix("item/triple_bow_pulling_0"));
      ModelFile triplePulling1 = this.bowItem("triple_bow_pulling_1", TwilightForestMod.prefix("item/triple_bow_pulling_1"));
      ModelFile triplePulling2 = this.bowItem("triple_bow_pulling_2", TwilightForestMod.prefix("item/triple_bow_pulling_2"));
      this.bowTex(TFItems.TRIPLE_BOW, triplePulling0, triplePulling1, triplePulling2);
      ModelFile seekerPulling0 = this.bowItem("seeker_bow_pulling_0", TwilightForestMod.prefix("item/seeker_bow_pulling_0"));
      ModelFile seekerPulling1 = this.bowItem("seeker_bow_pulling_1", TwilightForestMod.prefix("item/seeker_bow_pulling_1"));
      ModelFile seekerPulling2 = this.bowItem("seeker_bow_pulling_2", TwilightForestMod.prefix("item/seeker_bow_pulling_2"));
      this.bowTex(TFItems.SEEKER_BOW, seekerPulling0, seekerPulling1, seekerPulling2);
      ModelFile icePulling0 = this.bowItem(
         "ice_bow_pulling_0", TwilightForestMod.prefix("item/ice_bow_solid_pulling_0"), TwilightForestMod.prefix("item/ice_bow_clear_pulling_0")
      );
      ModelFile icePulling1 = this.bowItem(
         "ice_bow_pulling_1", TwilightForestMod.prefix("item/ice_bow_solid_pulling_1"), TwilightForestMod.prefix("item/ice_bow_clear_pulling_1")
      );
      ModelFile icePulling2 = this.bowItem(
         "ice_bow_pulling_2", TwilightForestMod.prefix("item/ice_bow_solid_pulling_2"), TwilightForestMod.prefix("item/ice_bow_clear_pulling_2")
      );
      this.iceBowTex(icePulling0, icePulling1, icePulling2);
      ModelFile enderPulling0 = this.bowItem("ender_bow_pulling_0", TwilightForestMod.prefix("item/ender_bow_pulling_0"));
      ModelFile enderPulling1 = this.bowItem("ender_bow_pulling_1", TwilightForestMod.prefix("item/ender_bow_pulling_1"));
      ModelFile enderPulling2 = this.bowItem("ender_bow_pulling_2", TwilightForestMod.prefix("item/ender_bow_pulling_2"));
      this.bowTex(TFItems.ENDER_BOW, enderPulling0, enderPulling1, enderPulling2);
      this.tool(TFItems.ICE_SWORD.getId().getPath(), TwilightForestMod.prefix("item/ice_sword_solid"), TwilightForestMod.prefix("item/ice_sword_clear"));
      this.tool(TFItems.GLASS_SWORD.getId().getPath(), TwilightForestMod.prefix("item/glass_sword_solid"), TwilightForestMod.prefix("item/glass_sword_clear"));
      ModelFile chainThrown = this.tool("block_and_chain_thrown", TwilightForestMod.prefix("item/block_and_chain_thrown"));
      this.singleTexTool(TFItems.BLOCK_AND_CHAIN).override().predicate(TwilightForestMod.prefix("thrown"), 1.0F).model(chainThrown).end();
      ModelFile cubeThrown = this.tool("cube_of_annihilation_thrown", TwilightForestMod.prefix("item/cube_of_annihilation_thrown"));
      this.singleTexTool(TFItems.CUBE_OF_ANNIHILATION).override().predicate(TwilightForestMod.prefix("thrown"), 1.0F).model(cubeThrown).end();
      this.singleTex(TFItems.CUBE_TALISMAN);
      ModelFile full = this.phaseTex("moon_dial_full", TwilightForestMod.prefix("item/moon_dial/full"));
      ModelFile waning_gib = this.phaseTex("moon_dial_waning_gib", TwilightForestMod.prefix("item/moon_dial/waning_gibbous"));
      ModelFile quarter3 = this.phaseTex("moon_dial_quarter3", TwilightForestMod.prefix("item/moon_dial/third_quarter"));
      ModelFile waning_cres = this.phaseTex("moon_dial_waning_cres", TwilightForestMod.prefix("item/moon_dial/waning_cresent"));
      ModelFile unlit = this.phaseTex("moon_dial_new", TwilightForestMod.prefix("item/moon_dial/new"));
      ModelFile waxing_cres = this.phaseTex("moon_dial_waxing_cres", TwilightForestMod.prefix("item/moon_dial/waxing_cresent"));
      ModelFile quarter1 = this.phaseTex("moon_dial_quarter1", TwilightForestMod.prefix("item/moon_dial/first_quarter"));
      ModelFile waxing_gib = this.phaseTex("moon_dial_waxing_gib", TwilightForestMod.prefix("item/moon_dial/waxing_gibbous"));
      this.phaseTex(TFItems.MOON_DIAL.getId().getPath(), TwilightForestMod.prefix("item/moon_dial/full"))
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("phase"), 0.0F)
         .model(full)
         .end()
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("phase"), 0.125F)
         .model(waning_gib)
         .end()
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("phase"), 0.25F)
         .model(quarter3)
         .end()
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("phase"), 0.375F)
         .model(waning_cres)
         .end()
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("phase"), 0.5F)
         .model(unlit)
         .end()
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("phase"), 0.625F)
         .model(waxing_cres)
         .end()
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("phase"), 0.75F)
         .model(quarter1)
         .end()
         .override()
         .predicate(ResourceLocation.withDefaultNamespace("phase"), 0.875F)
         .model(waxing_gib)
         .end();
      ModelFile fill1 = this.generated(
         "brittle_flask_0", TwilightForestMod.prefix("item/brittle_potion_flask_1"), TwilightForestMod.prefix("item/brittle_potion_flask_labelled")
      );
      ModelFile fill2 = this.generated(
         "brittle_flask_1", TwilightForestMod.prefix("item/brittle_potion_flask_2"), TwilightForestMod.prefix("item/brittle_potion_flask_labelled")
      );
      ModelFile fill3 = this.generated(
         "brittle_flask_2", TwilightForestMod.prefix("item/brittle_potion_flask_3"), TwilightForestMod.prefix("item/brittle_potion_flask_labelled")
      );
      ModelFile splintered = this.generated("brittle_flask_splintered", TwilightForestMod.prefix("item/brittle_potion_flask_splintered"));
      ModelFile fill1_splintered = this.generated(
         "brittle_flask_0_splintered",
         TwilightForestMod.prefix("item/brittle_potion_flask_1"),
         TwilightForestMod.prefix("item/brittle_potion_flask_splintered")
      );
      ModelFile fill2_splintered = this.generated(
         "brittle_flask_1_splintered",
         TwilightForestMod.prefix("item/brittle_potion_flask_2"),
         TwilightForestMod.prefix("item/brittle_potion_flask_splintered")
      );
      ModelFile fill3_splintered = this.generated(
         "brittle_flask_2_splintered",
         TwilightForestMod.prefix("item/brittle_potion_flask_3"),
         TwilightForestMod.prefix("item/brittle_potion_flask_splintered")
      );
      ModelFile damaged = this.generated("brittle_flask_damaged", TwilightForestMod.prefix("item/brittle_potion_flask_damaged"));
      ModelFile fill1_damaged = this.generated(
         "brittle_flask_0_damaged", TwilightForestMod.prefix("item/brittle_potion_flask_1"), TwilightForestMod.prefix("item/brittle_potion_flask_damaged")
      );
      ModelFile fill2_damaged = this.generated(
         "brittle_flask_1_damaged", TwilightForestMod.prefix("item/brittle_potion_flask_2"), TwilightForestMod.prefix("item/brittle_potion_flask_damaged")
      );
      ModelFile fill3_damaged = this.generated(
         "brittle_flask_2_damaged", TwilightForestMod.prefix("item/brittle_potion_flask_3"), TwilightForestMod.prefix("item/brittle_potion_flask_damaged")
      );
      this.generated(TFItems.BRITTLE_FLASK.getId().getPath(), TwilightForestMod.prefix("block/blank"), TwilightForestMod.prefix("item/brittle_potion_flask"))
         .override()
         .predicate(TwilightForestMod.prefix("potion_level"), 1.0F)
         .model(fill1)
         .end()
         .override()
         .predicate(TwilightForestMod.prefix("potion_level"), 2.0F)
         .model(fill2)
         .end()
         .override()
         .predicate(TwilightForestMod.prefix("potion_level"), 3.0F)
         .model(fill3)
         .end()
         .override()
         .predicate(TwilightForestMod.prefix("potion_level"), 0.0F)
         .predicate(TwilightForestMod.prefix("breakage"), 1.0F)
         .model(splintered)
         .end()
         .override()
         .predicate(TwilightForestMod.prefix("potion_level"), 1.0F)
         .predicate(TwilightForestMod.prefix("breakage"), 1.0F)
         .model(fill1_splintered)
         .end()
         .override()
         .predicate(TwilightForestMod.prefix("potion_level"), 2.0F)
         .predicate(TwilightForestMod.prefix("breakage"), 1.0F)
         .model(fill2_splintered)
         .end()
         .override()
         .predicate(TwilightForestMod.prefix("potion_level"), 3.0F)
         .predicate(TwilightForestMod.prefix("breakage"), 1.0F)
         .model(fill3_splintered)
         .end()
         .override()
         .predicate(TwilightForestMod.prefix("potion_level"), 0.0F)
         .predicate(TwilightForestMod.prefix("breakage"), 2.0F)
         .model(damaged)
         .end()
         .override()
         .predicate(TwilightForestMod.prefix("potion_level"), 1.0F)
         .predicate(TwilightForestMod.prefix("breakage"), 2.0F)
         .model(fill1_damaged)
         .end()
         .override()
         .predicate(TwilightForestMod.prefix("potion_level"), 2.0F)
         .predicate(TwilightForestMod.prefix("breakage"), 2.0F)
         .model(fill2_damaged)
         .end()
         .override()
         .predicate(TwilightForestMod.prefix("potion_level"), 3.0F)
         .predicate(TwilightForestMod.prefix("breakage"), 2.0F)
         .model(fill3_damaged)
         .end();
      ModelFile gfill1 = this.generated(
         "greater_flask_0", TwilightForestMod.prefix("item/greater_potion_flask_1"), TwilightForestMod.prefix("item/greater_potion_flask")
      );
      ModelFile gfill2 = this.generated(
         "greater_flask_1", TwilightForestMod.prefix("item/greater_potion_flask_2"), TwilightForestMod.prefix("item/greater_potion_flask")
      );
      ModelFile gfill3 = this.generated(
         "greater_flask_2", TwilightForestMod.prefix("item/greater_potion_flask_3"), TwilightForestMod.prefix("item/greater_potion_flask")
      );
      this.generated(TFItems.GREATER_FLASK.getId().getPath(), TwilightForestMod.prefix("block/blank"), TwilightForestMod.prefix("item/greater_potion_flask"))
         .override()
         .predicate(TwilightForestMod.prefix("potion_level"), 1.0F)
         .model(gfill1)
         .end()
         .override()
         .predicate(TwilightForestMod.prefix("potion_level"), 2.0F)
         .model(gfill2)
         .end()
         .override()
         .predicate(TwilightForestMod.prefix("potion_level"), 3.0F)
         .model(gfill3)
         .end();
      this.singleTex(TFItems.MUSIC_DISC_FINDINGS);
      this.singleTex(TFItems.MUSIC_DISC_HOME);
      this.singleTex(TFItems.MUSIC_DISC_MAKER);
      this.singleTex(TFItems.MUSIC_DISC_MOTION);
      this.singleTex(TFItems.MUSIC_DISC_RADIANCE);
      this.singleTex(TFItems.MUSIC_DISC_STEPS);
      this.singleTex(TFItems.MUSIC_DISC_SUPERSTITIOUS);
      this.singleTex(TFItems.MUSIC_DISC_THREAD);
      this.singleTex(TFItems.MUSIC_DISC_WAYFARER);
      this.singleTex(TFItems.TWILIGHT_OAK_BOAT);
      this.singleTex(TFItems.CANOPY_BOAT);
      this.singleTex(TFItems.MANGROVE_BOAT);
      this.singleTex(TFItems.DARK_BOAT);
      this.singleTex(TFItems.TIME_BOAT);
      this.singleTex(TFItems.TRANSFORMATION_BOAT);
      this.singleTex(TFItems.MINING_BOAT);
      this.singleTex(TFItems.SORTING_BOAT);
      this.chestBoat(TFItems.TWILIGHT_OAK_CHEST_BOAT);
      this.chestBoat(TFItems.CANOPY_CHEST_BOAT);
      this.chestBoat(TFItems.MANGROVE_CHEST_BOAT);
      this.chestBoat(TFItems.DARK_CHEST_BOAT);
      this.chestBoat(TFItems.TIME_CHEST_BOAT);
      this.chestBoat(TFItems.TRANSFORMATION_CHEST_BOAT);
      this.chestBoat(TFItems.MINING_CHEST_BOAT);
      this.chestBoat(TFItems.SORTING_CHEST_BOAT);
      this.generated(TFItems.NAGA_BANNER_PATTERN.getId().getPath(), TwilightForestMod.prefix("item/tf_banner_pattern"));
      this.generated(TFItems.LICH_BANNER_PATTERN.getId().getPath(), TwilightForestMod.prefix("item/tf_banner_pattern"));
      this.generated(TFItems.MINOSHROOM_BANNER_PATTERN.getId().getPath(), TwilightForestMod.prefix("item/tf_banner_pattern"));
      this.generated(TFItems.HYDRA_BANNER_PATTERN.getId().getPath(), TwilightForestMod.prefix("item/tf_banner_pattern"));
      this.generated(TFItems.KNIGHT_PHANTOM_BANNER_PATTERN.getId().getPath(), TwilightForestMod.prefix("item/tf_banner_pattern"));
      this.generated(TFItems.UR_GHAST_BANNER_PATTERN.getId().getPath(), TwilightForestMod.prefix("item/tf_banner_pattern"));
      this.generated(TFItems.ALPHA_YETI_BANNER_PATTERN.getId().getPath(), TwilightForestMod.prefix("item/tf_banner_pattern"));
      this.generated(TFItems.SNOW_QUEEN_BANNER_PATTERN.getId().getPath(), TwilightForestMod.prefix("item/tf_banner_pattern"));
      this.generated(TFItems.QUEST_RAM_BANNER_PATTERN.getId().getPath(), TwilightForestMod.prefix("item/tf_banner_pattern"));
      this.generated("trophy", TwilightForestMod.prefix("item/trophy"));
      this.generated("trophy_minor", TwilightForestMod.prefix("item/trophy_minor"));
      this.generated("trophy_quest", TwilightForestMod.prefix("item/trophy_quest"));
      this.generated("shield", TwilightForestMod.prefix("item/lich_shield_frame"), TwilightForestMod.prefix("item/lich_shield_fill"));
      this.toBlock((Block)TFBlocks.SINISTER_SPAWNER.value());
      this.tool(TFItems.STALE_BREAD.getId().getPath(), ResourceLocation.withDefaultNamespace("item/bread"));
   }

   private ItemModelBuilder fullbright(String name, ResourceLocation... layers) {
      return this.buildItem(name, "item/generated", 15, layers);
   }

   private ItemModelBuilder fullbrightTool(String name, ResourceLocation... layers) {
      return this.buildItem(name, "item/handheld", 15, layers);
   }

   private ItemModelBuilder generated(String name, ResourceLocation... layers) {
      return this.buildItem(name, "item/generated", 0, layers);
   }

   private ItemModelBuilder tool(String name, ResourceLocation... layers) {
      return this.buildItem(name, "item/handheld", 0, layers);
   }

   private ItemModelBuilder buildItem(String name, String parent, int emissivity, ResourceLocation... layers) {
      ItemModelBuilder builder = (ItemModelBuilder)this.withExistingParent(name, parent);

      for (int i = 0; i < layers.length; i++) {
         builder = (ItemModelBuilder)builder.texture("layer" + i, layers[i]);
      }

      if (emissivity > 0) {
         builder = (ItemModelBuilder)((ItemLayerModelBuilder)builder.customLoader(ItemLayerModelBuilder::begin))
            .emissive(emissivity, emissivity, new int[]{0})
            .renderType("minecraft:translucent", new int[]{0})
            .end();
      }

      return builder;
   }

   private ItemModelBuilder forcefield(String name, ResourceLocation... layers) {
      ItemModelBuilder builder = (ItemModelBuilder)this.withExistingParent(name, "item/generated");

      for (int i = 0; i < layers.length; i++) {
         builder = (ItemModelBuilder)builder.texture("layer" + i, layers[i]);
      }

      return (ItemModelBuilder)((ItemLayerModelBuilder)builder.customLoader(ItemLayerModelBuilder::begin))
         .emissive(15, 15, new int[]{0})
         .renderType("minecraft:translucent", new int[]{0})
         .end();
   }

   private ItemModelBuilder singleTexFullbright(DeferredHolder<Item, ? extends Item> item) {
      return this.fullbright(item.getId().getPath(), TwilightForestMod.prefix("item/" + item.getId().getPath()));
   }

   private ItemModelBuilder singleTexFullbrightTool(DeferredHolder<Item, ? extends Item> item) {
      return this.fullbrightTool(item.getId().getPath(), TwilightForestMod.prefix("item/" + item.getId().getPath()));
   }

   private ItemModelBuilder singleTexTool(DeferredHolder<Item, ? extends Item> item) {
      return this.tool(item.getId().getPath(), TwilightForestMod.prefix("item/" + item.getId().getPath()));
   }

   private ItemModelBuilder singleTex(DeferredHolder<?, ?> item) {
      return this.generated(item.getId().getPath(), TwilightForestMod.prefix("item/" + item.getId().getPath()));
   }

   private ItemModelBuilder chestBoat(DeferredHolder<?, ?> item) {
      return this.generated(
         item.getId().getPath(), ResourceLocation.withDefaultNamespace("item/oak_chest_boat"), TwilightForestMod.prefix("item/" + item.getId().getPath())
      );
   }

   private ItemModelBuilder specialTool(DeferredHolder<Item, ? extends Item> item) {
      return this.specialTool(item.getId().getPath(), TwilightForestMod.prefix("item/" + item.getId().getPath()));
   }

   private ItemModelBuilder specialTool(String name, ResourceLocation... layers) {
      ItemModelBuilder builder = (ItemModelBuilder)this.withExistingParent(name, TwilightForestMod.prefix("item/special_handheld"));

      for (int i = 0; i < layers.length; i++) {
         builder = (ItemModelBuilder)builder.texture("layer" + i, layers[i]);
      }

      return builder;
   }

   private ItemModelBuilder bowItem(String name, ResourceLocation... layers) {
      ItemModelBuilder builder = (ItemModelBuilder)this.withExistingParent(name, "item/bow");

      for (int i = 0; i < layers.length; i++) {
         builder = (ItemModelBuilder)builder.texture("layer" + i, layers[i]);
      }

      return builder;
   }

   private void bowTex(DeferredHolder<Item, Item> item, ModelFile pull0, ModelFile pull1, ModelFile pull2) {
      this.bowItem(item.getId().getPath(), TwilightForestMod.prefix("item/" + item.getId().getPath()))
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

   private void iceBowTex(ModelFile pull0, ModelFile pull1, ModelFile pull2) {
      this.bowItem(TFItems.ICE_BOW.getId().getPath(), TwilightForestMod.prefix("item/ice_bow_solid"), TwilightForestMod.prefix("item/ice_bow_clear"))
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

   private ItemModelBuilder phaseTex(String name, ResourceLocation... layers) {
      ItemModelBuilder builder = (ItemModelBuilder)this.withExistingParent(name, "twilightforest:item/moon_dial_template");

      for (int i = 0; i < layers.length; i++) {
         builder = (ItemModelBuilder)builder.texture("layer" + i, layers[i]);
      }

      return builder;
   }

   private void woodenButton(Block button, String variant) {
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(BuiltInRegistries.BLOCK.getKey(button).getPath()))
            .parent(this.getExistingFile(this.mcLoc("block/button_inventory"))))
         .texture("texture", "block/wood/planks_" + variant + "_0");
   }

   private void woodenFence(Block fence, String variant) {
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(BuiltInRegistries.BLOCK.getKey(fence).getPath()))
            .parent(this.getExistingFile(this.mcLoc("block/fence_inventory"))))
         .texture("texture", "block/wood/planks_" + variant + "_0");
   }

   private void hollowLog(DeferredHolder<Item, HollowLogItem> hollowLog) {
      ((ItemModelBuilder)this.getBuilder(BuiltInRegistries.ITEM.getKey(((HollowLogItem)hollowLog.get()).asItem()).getPath()))
         .parent(new ExistingModelFile(TwilightForestMod.prefix("block/" + hollowLog.getId().getPath() + "_horizontal"), this.existingFileHelper));
   }

   private void toBlock(Block b) {
      this.toBlockModel(b, BuiltInRegistries.BLOCK.getKey(b).getPath());
   }

   private void bushBlock(Block b) {
      this.toBlockModel(b, BuiltInRegistries.BLOCK.getKey(b).getPath() + "_0");
   }

   private void woodBlock(Block b, String variant) {
      this.woodBlockModel(b, BuiltInRegistries.BLOCK.getKey(b).getPath(), variant);
   }

   private void toBlockModel(Block b, String model) {
      this.toBlockModel(b, TwilightForestMod.prefix("block/" + model));
   }

   private void woodBlockModel(Block b, String model, String variant) {
      this.toBlockModel(b, TwilightForestMod.prefix("block/wood/" + variant + "/" + model));
   }

   private void toBlockModel(Block b, ResourceLocation model) {
      this.withExistingParent(BuiltInRegistries.BLOCK.getKey(b).getPath(), model);
   }

   private void toGiantModel(Block b, ResourceLocation model, ItemModelBuilder base, ItemModelBuilder gui) {
      this.toGiantModel(b, model, model, base, gui);
   }

   private void toGiantModel(Block b, ResourceLocation model, ResourceLocation top, ItemModelBuilder base, ItemModelBuilder gui) {
      String name = BuiltInRegistries.BLOCK.getKey(b).getPath();
      ((SeparateTransformsModelBuilder)((ItemModelBuilder)this.withExistingParent(name, model)).customLoader(SeparateTransformsModelBuilder::begin))
         .base(
            (ItemModelBuilder)((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(name + "_base", base.getLocation())).texture("all", model))
               .texture("top", top)
         )
         .perspective(
            ItemDisplayContext.GUI,
            (ItemModelBuilder)((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(name + "_gui", gui.getLocation())).texture("all", model))
               .texture("top", top)
         )
         .end();
   }

   private void toGiantItemModel(DeferredHolder<Item, Item> item, ResourceLocation parent, ItemModelBuilder base, int x, int y) {
      String name = item.getId().getPath();
      ItemModelBuilder gui = (ItemModelBuilder)((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(name + "_gui")).texture("all", parent))
         .element()
         .from(0.0F, 0.0F, 0.0F)
         .to(16.0F, 16.0F, 0.0F)
         .face(Direction.SOUTH)
         .texture("#all")
         .uvs(x, y, x + 8, y + 8)
         .tintindex(0)
         .end()
         .end();
      ((SeparateTransformsModelBuilder)((ItemModelBuilder)this.withExistingParent(name, parent)).customLoader(SeparateTransformsModelBuilder::begin))
         .base((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(name + "_base", base.getLocation())).texture("layer0", parent))
         .perspective(ItemDisplayContext.GUI, (ItemModelBuilder)gui.texture("all", parent))
         .end();
   }

   private void trimmedArmor(DeferredHolder<Item, ArmorItem> armor) {
      ItemModelBuilder base = this.singleTex(armor);

      for (TrimModelData trim : ItemModelGenerators.GENERATED_TRIM_MODELS) {
         String material = trim.name();
         String name = armor.getId().getPath() + "_" + material + "_trim";
         ModelFile trimModel = ((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(name, this.mcLoc("item/generated")))
               .texture("layer0", TwilightForestMod.prefix("item/" + armor.getId().getPath())))
            .texture("layer1", this.mcLoc("trims/items/" + ((ArmorItem)armor.get()).getType().getName() + "_trim_" + material));
         base.override().predicate(ResourceLocation.withDefaultNamespace("trim_type"), trim.itemModelIndex()).model(trimModel).end();
      }
   }

   private void trimmedFullbrightArmor(DeferredHolder<Item, ArmorItem> armor) {
      ItemModelBuilder base = this.singleTexFullbright(armor);

      for (TrimModelData trim : ItemModelGenerators.GENERATED_TRIM_MODELS) {
         String material = trim.name();
         String name = armor.getId().getPath() + "_" + material + "_trim";
         ModelFile trimModel = ((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(name, this.mcLoc("item/generated")))
               .texture("layer0", TwilightForestMod.prefix("item/" + armor.getId().getPath())))
            .texture("layer1", this.mcLoc("trims/items/" + ((ArmorItem)armor.get()).getType().getName() + "_trim_" + material));
         base.override().predicate(ResourceLocation.withDefaultNamespace("trim_type"), trim.itemModelIndex()).model(trimModel).end();
      }

      ((ItemLayerModelBuilder)base.customLoader(ItemLayerModelBuilder::begin))
         .emissive(15, 15, new int[]{0})
         .renderType("minecraft:translucent", new int[]{0})
         .end();
   }

   private void trimmedLayeredArmor(DeferredHolder<Item, ArmorItem> armor) {
      ItemModelBuilder base = this.generated(
         armor.getId().getPath(),
         TwilightForestMod.prefix("item/" + armor.getId().getPath()),
         TwilightForestMod.prefix("item/" + armor.getId().getPath() + "_0")
      );

      for (TrimModelData trim : ItemModelGenerators.GENERATED_TRIM_MODELS) {
         String material = trim.name();
         String name = armor.getId().getPath() + "_" + material + "_trim";
         ModelFile trimModel = ((ItemModelBuilder)((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(name, this.mcLoc("item/generated")))
                  .texture("layer0", TwilightForestMod.prefix("item/" + armor.getId().getPath())))
               .texture("layer1", TwilightForestMod.prefix("item/" + armor.getId().getPath() + "_0")))
            .texture("layer2", this.mcLoc("trims/items/" + ((ArmorItem)armor.get()).getType().getName() + "_trim_" + material));
         base.override().predicate(ResourceLocation.withDefaultNamespace("trim_type"), trim.itemModelIndex()).model(trimModel).end();
      }
   }

   private void crumbleHorn(DeferredHolder<Item, Item> item) {
      String name = item.getId().getPath();
      ItemModelBuilder tooting = (ItemModelBuilder)((ItemModelBuilder)this.withExistingParent("tooting_" + name, this.mcLoc("item/tooting_goat_horn")))
         .texture("layer0", TwilightForestMod.prefix("item/" + name));
      ((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(name, this.mcLoc("item/goat_horn")))
            .texture("layer0", TwilightForestMod.prefix("item/" + name)))
         .override()
         .predicate(TwilightForestMod.prefix("tooting"), 1.0F)
         .model(tooting);
   }

   private void lamp(DeferredHolder<Item, Item> item) {
      String name = item.getId().getPath();
      ((ItemModelBuilder)this.tool(name).texture("layer0", TwilightForestMod.prefix("item/" + name)))
         .transforms()
         .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
         .rotation(0.0F, -90.0F, 0.0F)
         .translation(0.0F, -0.5F, -3.0F)
         .scale(0.65F)
         .end()
         .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
         .rotation(0.0F, 90.0F, 0.0F)
         .translation(0.0F, -0.5F, -3.0F)
         .scale(0.65F)
         .end()
         .end();
   }

   private void moonwormQueen(DeferredHolder<Item, Item> item) {
      String name = item.getId().getPath();
      ModelFile alt = this.fullbrightTool(name + "_alt", TwilightForestMod.prefix("item/" + name + "_alt"))
         .transforms()
         .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
         .rotation(0.0F, -90.0F, 55.0F)
         .translation(0.0F, 2.0F, -1.0F)
         .scale(0.85F)
         .end()
         .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
         .rotation(0.0F, 90.0F, -55.0F)
         .translation(0.0F, 2.0F, -1.0F)
         .scale(0.85F)
         .end()
         .end();
      ((ItemModelBuilder)this.singleTexFullbrightTool(item)
            .transforms()
            .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
            .rotation(0.0F, -90.0F, 55.0F)
            .translation(0.0F, 2.0F, -1.0F)
            .scale(0.85F)
            .end()
            .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
            .rotation(0.0F, 90.0F, -55.0F)
            .translation(0.0F, 2.0F, -1.0F)
            .scale(0.85F)
            .end()
            .end())
         .override()
         .predicate(TwilightForestMod.prefix("alt"), 1.0F)
         .model(alt)
         .end();
   }

   @SafeVarargs
   private void travellersTex(DeferredItem<? extends Item>... items) {
      for (DeferredItem<? extends Item> item : items) {
         String id = item.getId().getPath();
         String modifierID = id.replace("travellers_", "");
         ((TravellersGearItemModelBuilder)((ItemModelBuilder)((ItemModelBuilder)((ItemModelBuilder)this.withExistingParent(id, "neoforge:item/default"))
                     .texture("base", TwilightForestMod.prefix("item/" + id)))
                  .texture("broken", TwilightForestMod.prefix("item/" + id + "_broken")))
               .customLoader(
                  (parent, helper) -> TravellersGearItemModelBuilder.begin(
                     parent, "travellers_modifiers/" + modifierID + "/", "travellers_modifiers/" + modifierID + "/broken/", helper
                  )
               ))
            .end();
      }
   }

   private ItemModelBuilder exanimateEssence(DeferredItem<Item> item) {
      ItemModelBuilder builder = (ItemModelBuilder)this.withExistingParent(item.getId().getPath(), "item/generated");
      builder = (ItemModelBuilder)builder.texture("layer0", TwilightForestMod.prefix("item/exanimate_powder"));
      builder = (ItemModelBuilder)builder.texture("layer1", TwilightForestMod.prefix("item/exanimate_flames"));
      return (ItemModelBuilder)((ItemLayerModelBuilder)builder.customLoader(ItemLayerModelBuilder::begin)).emissive(15, 15, new int[]{1}).end();
   }

   public String getName() {
      return "TwilightForest Item and BlockItem models";
   }
}
