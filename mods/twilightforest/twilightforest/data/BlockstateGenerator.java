package twilightforest.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.annotation.Nonnull;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.GlazedTerracottaBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel.Builder;
import net.neoforged.neoforge.client.model.generators.ModelBuilder.FaceRotation;
import net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder.PartBuilder;
import net.neoforged.neoforge.client.model.generators.loaders.CompositeModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.block.BanisterBlock;
import twilightforest.block.BinaryRotatedBlock;
import twilightforest.block.BuilderBlock;
import twilightforest.block.CandelabraBlock;
import twilightforest.block.CarminiteReactorBlock;
import twilightforest.block.CastleDoorBlock;
import twilightforest.block.CinderFurnaceBlock;
import twilightforest.block.ClimbableHollowLogBlock;
import twilightforest.block.DirectionalRotatedPillarBlock;
import twilightforest.block.DryingRackBlock;
import twilightforest.block.EncasedSmokerBlock;
import twilightforest.block.Experiment115Block;
import twilightforest.block.FallenLeavesBlock;
import twilightforest.block.FireJetBlock;
import twilightforest.block.GhastTrapBlock;
import twilightforest.block.HorizontalHollowLogBlock;
import twilightforest.block.HugeLilyPadBlock;
import twilightforest.block.IronLadderBlock;
import twilightforest.block.LockedVanishingBlock;
import twilightforest.block.NagastoneBlock;
import twilightforest.block.RopeBlock;
import twilightforest.block.SliderBlock;
import twilightforest.block.SnowLoggable;
import twilightforest.block.SpecialMagicLogBlock;
import twilightforest.block.SpiralBrickBlock;
import twilightforest.block.TFPortalBlock;
import twilightforest.block.TorchberryPlantBlock;
import twilightforest.block.TranslucentBuiltBlock;
import twilightforest.block.TrophyPedestalBlock;
import twilightforest.block.UncraftingTableBlock;
import twilightforest.block.VanishingBlock;
import twilightforest.block.VerticalHollowLogBlock;
import twilightforest.block.WallPillarBlock;
import twilightforest.block.WroughtIronFenceBlock;
import twilightforest.client.model.block.aurorablock.NoiseVaryingModelBuilder;
import twilightforest.client.model.block.carpet.RoyalRagsBuilder;
import twilightforest.client.model.block.connected.ConnectedTextureBuilder;
import twilightforest.client.model.block.forcefield.ForceFieldModel;
import twilightforest.client.model.block.forcefield.ForceFieldModelBuilder;
import twilightforest.client.model.block.giantblock.GiantBlockBuilder;
import twilightforest.client.renderer.block.JarRenderer;
import twilightforest.data.helpers.BlockModelBuilders;
import twilightforest.enums.BlockLoggingEnum;
import twilightforest.enums.Diagonals;
import twilightforest.enums.FireJetVariant;
import twilightforest.enums.HollowLogVariants;
import twilightforest.enums.HugeLilypadPiece;
import twilightforest.enums.NagastoneVariant;
import twilightforest.enums.TowerDeviceVariant;
import twilightforest.init.TFBlocks;

public class BlockstateGenerator extends BlockModelBuilders {
   @Nullable
   private static ModelFile[][] abstractBushStates;

   public BlockstateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
      super(output, exFileHelper);
   }

   protected void registerStatesAndModels() {
      this.tintedAndFlipped((Block)TFBlocks.TOWERWOOD.get());
      this.simpleBlock(
         (Block)TFBlocks.ENCASED_TOWERWOOD.get(),
         this.cubeAllTinted(TFBlocks.ENCASED_TOWERWOOD.getId().getPath(), TFBlocks.ENCASED_TOWERWOOD.getId().getPath())
      );
      this.simpleBlock(
         (Block)TFBlocks.CRACKED_TOWERWOOD.get(),
         ConfiguredModel.builder()
            .modelFile(this.cubeAllTinted(TFBlocks.CRACKED_TOWERWOOD.getId().getPath(), TFBlocks.CRACKED_TOWERWOOD.getId().getPath()))
            .nextModel()
            .modelFile(this.cubeAllTinted(TFBlocks.CRACKED_TOWERWOOD.getId().getPath() + "_flipped", TFBlocks.CRACKED_TOWERWOOD.getId().getPath(), true))
            .nextModel()
            .modelFile(this.cubeAllTinted(TFBlocks.CRACKED_TOWERWOOD.getId().getPath() + "_alt", TFBlocks.CRACKED_TOWERWOOD.getId().getPath() + "_alt"))
            .nextModel()
            .modelFile(
               this.cubeAllTinted(TFBlocks.CRACKED_TOWERWOOD.getId().getPath() + "_alt_flipped", TFBlocks.CRACKED_TOWERWOOD.getId().getPath() + "_alt", true)
            )
            .build()
      );
      this.tintedAndFlipped((Block)TFBlocks.MOSSY_TOWERWOOD.get());
      this.tintedAndFlipped((Block)TFBlocks.INFESTED_TOWERWOOD.get());
      this.builtinEntity((Block)TFBlocks.FIREFLY.get(), "block/blank");
      this.builtinEntity((Block)TFBlocks.MOONWORM.get(), "block/blank");
      this.builtinEntity((Block)TFBlocks.CICADA.get(), "block/blank");
      this.builtinEntity((Block)TFBlocks.REACTOR_DEBRIS.get(), "block/blank");
      this.builtinEntity((Block)TFBlocks.RED_THREAD.get(), "block/blank");
      this.builtinEntity((Block)TFBlocks.BRAZIER.get(), "block/wood/planks_canopy_0");
      ModelFile portalModel = this.models().getExistingFile(TwilightForestMod.prefix("block/twilight_portal"));
      ModelFile portalOverlayModel = this.models().getExistingFile(TwilightForestMod.prefix("block/twilight_portal_barrier"));
      ((PartBuilder)((PartBuilder)this.getMultipartBuilder((Block)TFBlocks.TWILIGHT_PORTAL.get()).part().modelFile(portalModel).addModel())
            .end()
            .part()
            .modelFile(portalOverlayModel)
            .addModel())
         .condition(TFPortalBlock.DISALLOW_RETURN, new Boolean[]{true})
         .end();
      this.getVariantBuilder((Block)TFBlocks.EXPERIMENT_115.get())
         .forAllStates(
            state -> {
               int bitesTaken = (Integer)state.getValue(Experiment115Block.BITES_TAKEN);
               String basePath = String.format("block/experiment115_%d_8", 8 - bitesTaken);
               ModelFile model;
               if ((Boolean)state.getValue(Experiment115Block.REGENERATE)) {
                  model = ((BlockModelBuilder)this.models().withExistingParent(basePath + "_regenerating", TwilightForestMod.prefix(basePath)))
                     .texture("top_2", "block/experiment115/experiment115_sprinkle");
               } else {
                  model = this.models().getExistingFile(TwilightForestMod.prefix(basePath));
               }

               return ConfiguredModel.builder().modelFile(model).build();
            }
         );
      MultiPartBlockStateBuilder ironLadder = this.getMultipartBuilder((Block)TFBlocks.IRON_LADDER.get());
      ModelFile ironLadderLeft = this.models().getExistingFile(TwilightForestMod.prefix("block/iron_ladder_left"));
      ModelFile ironLadderLeftConnected = this.models().getExistingFile(TwilightForestMod.prefix("block/iron_ladder_left_connection"));
      ModelFile ironLadderRight = this.models().getExistingFile(TwilightForestMod.prefix("block/iron_ladder_right"));
      ModelFile ironLadderRightConnected = this.models().getExistingFile(TwilightForestMod.prefix("block/iron_ladder_right_connection"));

      for (Direction d : Plane.HORIZONTAL) {
         int rotY = switch (d) {
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
            default -> 0;
         };
         ((PartBuilder)ironLadder.part().modelFile(ironLadderLeft).rotationY(rotY).addModel())
            .condition(LadderBlock.FACING, new Direction[]{d})
            .condition(IronLadderBlock.LEFT, new Boolean[]{false})
            .end();
         ((PartBuilder)ironLadder.part().modelFile(ironLadderLeftConnected).rotationY(rotY).addModel())
            .condition(LadderBlock.FACING, new Direction[]{d})
            .condition(IronLadderBlock.LEFT, new Boolean[]{true})
            .end();
         ((PartBuilder)ironLadder.part().modelFile(ironLadderRight).rotationY(rotY).addModel())
            .condition(LadderBlock.FACING, new Direction[]{d})
            .condition(IronLadderBlock.RIGHT, new Boolean[]{false})
            .end();
         ((PartBuilder)ironLadder.part().modelFile(ironLadderRightConnected).rotationY(rotY).addModel())
            .condition(LadderBlock.FACING, new Direction[]{d})
            .condition(IronLadderBlock.RIGHT, new Boolean[]{true})
            .end();
      }

      ((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)this.getMultipartBuilder((Block)TFBlocks.ROPE.value())
                     .part()
                     .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("block/rope_y")))
                     .addModel())
                  .condition(RopeBlock.Y, new Boolean[]{true})
                  .end()
                  .part()
                  .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("block/rope_x")))
                  .addModel())
               .condition(RopeBlock.X, new Boolean[]{true})
               .end()
               .part()
               .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("block/rope_z")))
               .addModel())
            .condition(RopeBlock.Z, new Boolean[]{true})
            .end()
            .part()
            .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("block/rope_knot")))
            .addModel())
         .useOr()
         .nestedGroup()
         .condition(RopeBlock.X, new Boolean[]{true})
         .condition(RopeBlock.Y, new Boolean[]{true})
         .end()
         .nestedGroup()
         .condition(RopeBlock.Y, new Boolean[]{true})
         .condition(RopeBlock.Z, new Boolean[]{true})
         .end()
         .nestedGroup()
         .condition(RopeBlock.Z, new Boolean[]{true})
         .condition(RopeBlock.X, new Boolean[]{true})
         .end()
         .end();
      this.simpleBlock(
         (Block)TFBlocks.CANOPY_WINDOW.value(),
         ((BlockModelBuilder)this.models().cubeAll(this.name((Block)TFBlocks.CANOPY_WINDOW.value()), this.blockTexture((Block)TFBlocks.CANOPY_WINDOW.value())))
            .renderType(CUTOUT)
      );
      this.paneBlockWithRenderType(
         (IronBarsBlock)TFBlocks.CANOPY_WINDOW_PANE.value(),
         TFBlocks.CANOPY_WINDOW.getId().withPrefix("block/"),
         TFBlocks.CANOPY_WINDOW.getId().withPrefix("block/"),
         CUTOUT
      );
      this.towerBlocks();
      this.simpleBlock((Block)TFBlocks.FAKE_GOLD.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/gold_block")));
      this.simpleBlock((Block)TFBlocks.FAKE_DIAMOND.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/diamond_block")));
      this.simpleBlock((Block)TFBlocks.NAGA_TROPHY.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.LICH_TROPHY.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.MINOSHROOM_TROPHY.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.HYDRA_TROPHY.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.ALPHA_YETI_TROPHY.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.SNOW_QUEEN_TROPHY.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.KNIGHT_PHANTOM_TROPHY.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.UR_GHAST_TROPHY.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.QUEST_RAM_TROPHY.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.NAGA_WALL_TROPHY.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.LICH_WALL_TROPHY.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.MINOSHROOM_WALL_TROPHY.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.HYDRA_WALL_TROPHY.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.ALPHA_YETI_WALL_TROPHY.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.SNOW_QUEEN_WALL_TROPHY.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.KNIGHT_PHANTOM_WALL_TROPHY.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.UR_GHAST_WALL_TROPHY.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.QUEST_RAM_WALL_TROPHY.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.ZOMBIE_SKULL_CANDLE.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.ZOMBIE_WALL_SKULL_CANDLE.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.SKELETON_SKULL_CANDLE.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.SKELETON_WALL_SKULL_CANDLE.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.WITHER_SKELE_SKULL_CANDLE.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock(
         (Block)TFBlocks.WITHER_SKELE_WALL_SKULL_CANDLE.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull"))
      );
      this.simpleBlock((Block)TFBlocks.CREEPER_SKULL_CANDLE.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.CREEPER_WALL_SKULL_CANDLE.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.PLAYER_SKULL_CANDLE.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.PLAYER_WALL_SKULL_CANDLE.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.PIGLIN_SKULL_CANDLE.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.simpleBlock((Block)TFBlocks.PIGLIN_WALL_SKULL_CANDLE.get(), this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
      this.getVariantBuilder((Block)TFBlocks.SPIRAL_BRICKS.get())
         .forAllStatesExcept(
            state -> ConfiguredModel.builder()
               .modelFile(
                  this.models()
                     .getExistingFile(
                        TwilightForestMod.prefix(
                           "block/spiral_bricks/"
                              + ((Axis)state.getValue(SpiralBrickBlock.AXIS_FACING)).getName()
                              + "_spiral_"
                              + ((Diagonals)state.getValue(SpiralBrickBlock.DIAGONAL)).getSerializedName()
                        )
                     )
               )
               .build(),
            new Property[]{BlockStateProperties.WATERLOGGED}
         );
      ModelFile shieldModel = this.models()
         .cubeTop(
            TFBlocks.STRONGHOLD_SHIELD.getId().getPath(), TwilightForestMod.prefix("block/shield_outside"), TwilightForestMod.prefix("block/shield_inside")
         );
      this.getVariantBuilder((Block)TFBlocks.STRONGHOLD_SHIELD.get())
         .forAllStates(
            state -> {
               Direction dir = (Direction)state.getValue(BlockStateProperties.FACING);
               return ConfiguredModel.builder()
                  .uvLock(true)
                  .modelFile(shieldModel)
                  .rotationX(dir == Direction.DOWN ? 180 : (dir.getAxis().isHorizontal() ? 90 : 0))
                  .rotationY(dir.getAxis().isVertical() ? 0 : (int)dir.toYRot() % 360)
                  .build();
            }
         );
      this.trophyPedestal();
      this.auroraBlocks();
      this.slider();
      this.simpleBlock((Block)TFBlocks.UNDERBRICK.get());
      this.simpleBlock((Block)TFBlocks.CRACKED_UNDERBRICK.get());
      this.simpleBlock((Block)TFBlocks.MOSSY_UNDERBRICK.get());
      this.simpleBlock((Block)TFBlocks.UNDERBRICK_FLOOR.get());
      this.thorns();
      this.thornRose();
      this.simpleBlock(
         (Block)TFBlocks.THORN_LEAVES.get(), this.models().withExistingParent("thorn_leaves", ResourceLocation.withDefaultNamespace("block/oak_leaves"))
      );
      this.simpleBlock(
         (Block)TFBlocks.BEANSTALK_LEAVES.get(),
         this.models().withExistingParent("beanstalk_leaves", ResourceLocation.withDefaultNamespace("block/azalea_leaves"))
      );
      this.simpleBlock(
         (Block)TFBlocks.HOLLOW_OAK_SAPLING.get(),
         ((BlockModelBuilder)this.models().cross(TFBlocks.HOLLOW_OAK_SAPLING.getId().getPath(), this.blockTexture((Block)TFBlocks.HOLLOW_OAK_SAPLING.get())))
            .renderType(CUTOUT)
      );
      ModelFile deadrock = this.models().cubeAll(TFBlocks.DEADROCK.getId().getPath(), this.blockTexture((Block)TFBlocks.DEADROCK.get()));
      this.simpleBlock(
         (Block)TFBlocks.DEADROCK.get(),
         ConfiguredModel.builder()
            .modelFile(deadrock)
            .nextModel()
            .rotationY(180)
            .modelFile(deadrock)
            .nextModel()
            .rotationY(90)
            .modelFile(deadrock)
            .nextModel()
            .rotationY(270)
            .modelFile(deadrock)
            .build()
      );
      ModelFile deadrockCracked = this.models().cubeAll(TFBlocks.CRACKED_DEADROCK.getId().getPath(), this.blockTexture((Block)TFBlocks.CRACKED_DEADROCK.get()));
      this.allRotations((Block)TFBlocks.CRACKED_DEADROCK.get(), deadrockCracked);
      ModelFile deadrockWeathered = this.models()
         .cubeAll(TFBlocks.WEATHERED_DEADROCK.getId().getPath(), this.blockTexture((Block)TFBlocks.WEATHERED_DEADROCK.get()));
      this.allRotations((Block)TFBlocks.WEATHERED_DEADROCK.get(), deadrockWeathered);
      this.perFaceBlock(
         (Block)TFBlocks.TROLLSTEINN.get(),
         this.blockTexture((Block)TFBlocks.TROLLSTEINN.get()),
         TwilightForestMod.prefix("block/" + TFBlocks.TROLLSTEINN.getId().getPath() + "_light")
      );
      this.simpleBlockWithRenderType((Block)TFBlocks.WISPY_CLOUD.get(), TRANSLUCENT);
      this.simpleBlock((Block)TFBlocks.FLUFFY_CLOUD.get());
      this.simpleBlock((Block)TFBlocks.RAINY_CLOUD.get());
      this.simpleBlock((Block)TFBlocks.SNOWY_CLOUD.get());
      this.simpleBlock(
         (Block)TFBlocks.GIANT_COBBLESTONE.get(),
         ((GiantBlockBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                        .withExistingParent(TFBlocks.GIANT_COBBLESTONE.getId().getPath(), ResourceLocation.withDefaultNamespace("block/block")))
                     .texture("particle", this.blockTexture(Blocks.COBBLESTONE)))
                  .texture("all", this.blockTexture(Blocks.COBBLESTONE)))
               .customLoader(GiantBlockBuilder::begin))
            .parentBlock(Blocks.COBBLESTONE)
            .end()
      );
      this.simpleBlock(
         (Block)TFBlocks.GIANT_LOG.get(),
         ((GiantBlockBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                                       .withExistingParent(TFBlocks.GIANT_LOG.getId().getPath(), ResourceLocation.withDefaultNamespace("block/block")))
                                    .texture("particle", this.blockTexture(Blocks.OAK_LOG)))
                                 .texture("north", this.blockTexture(Blocks.OAK_LOG)))
                              .texture("south", this.blockTexture(Blocks.OAK_LOG)))
                           .texture("west", this.blockTexture(Blocks.OAK_LOG)))
                        .texture("east", this.blockTexture(Blocks.OAK_LOG)))
                     .texture("up", this.blockTexture(Blocks.OAK_LOG) + "_top"))
                  .texture("down", this.blockTexture(Blocks.OAK_LOG) + "_top"))
               .customLoader(GiantBlockBuilder::begin))
            .parentBlock(Blocks.OAK_LOG)
            .end()
      );
      this.simpleBlock(
         (Block)TFBlocks.GIANT_LEAVES.get(),
         ((GiantBlockBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                           .withExistingParent(TFBlocks.GIANT_LEAVES.getId().getPath(), ResourceLocation.withDefaultNamespace("block/block")))
                        .renderType(CUTOUT_MIPPED))
                     .texture("particle", this.blockTexture(Blocks.OAK_LEAVES)))
                  .texture("all", this.blockTexture(Blocks.OAK_LEAVES)))
               .customLoader(GiantBlockBuilder::begin))
            .parentBlock(Blocks.OAK_LEAVES)
            .end()
      );
      this.simpleBlock(
         (Block)TFBlocks.GIANT_OBSIDIAN.get(),
         ((GiantBlockBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                        .withExistingParent(TFBlocks.GIANT_OBSIDIAN.getId().getPath(), ResourceLocation.withDefaultNamespace("block/block")))
                     .texture("particle", this.blockTexture(Blocks.OBSIDIAN)))
                  .texture("all", this.blockTexture(Blocks.OBSIDIAN)))
               .customLoader(GiantBlockBuilder::begin))
            .parentBlock(Blocks.OBSIDIAN)
            .end()
      );
      this.simpleBlock(
         (Block)TFBlocks.UBEROUS_SOIL.get(),
         ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(TFBlocks.UBEROUS_SOIL.getId().getPath(), "block/template_farmland"))
               .texture("top", this.blockTexture((Block)TFBlocks.UBEROUS_SOIL.get())))
            .texture("dirt", this.blockTexture((Block)TFBlocks.UBEROUS_SOIL.get()))
      );
      this.axisBlock(
         (RotatedPillarBlock)TFBlocks.HUGE_STALK.get(), TwilightForestMod.prefix("block/huge_stalk"), TwilightForestMod.prefix("block/huge_stalk_top")
      );
      this.builtinEntity((Block)TFBlocks.BEANSTALK_GROWER.get(), "block/blank");
      this.perFaceBlock(
         (Block)TFBlocks.HUGE_MUSHGLOOM.get(), TwilightForestMod.prefix("block/huge_gloom_inside"), TwilightForestMod.prefix("block/huge_gloom_cap")
      );
      this.perFaceBlock(
         (Block)TFBlocks.HUGE_MUSHGLOOM_STEM.get(), TwilightForestMod.prefix("block/huge_gloom_inside"), TwilightForestMod.prefix("block/huge_mushgloom_stem")
      );
      this.simpleBlock(
         (Block)TFBlocks.TROLLVIDR.get(),
         ((BlockModelBuilder)this.models().cross(TFBlocks.TROLLVIDR.getId().getPath(), this.blockTexture((Block)TFBlocks.TROLLVIDR.get()))).renderType(CUTOUT)
      );
      this.simpleBlock(
         (Block)TFBlocks.UNRIPE_TROLLBER.get(),
         ((BlockModelBuilder)this.models().cross(TFBlocks.UNRIPE_TROLLBER.getId().getPath(), this.blockTexture((Block)TFBlocks.UNRIPE_TROLLBER.get())))
            .renderType(CUTOUT)
      );
      ModelFile trollber = ((BlockModelBuilder)this.make2layerCross(TFBlocks.TROLLBER.getId().getPath(), CUTOUT, 1, 10)
            .texture("cross", this.blockTexture((Block)TFBlocks.TROLLBER.get())))
         .texture("cross2", TwilightForestMod.prefix("block/" + TFBlocks.TROLLBER.getId().getPath() + "_glow"));
      this.simpleBlock((Block)TFBlocks.TROLLBER.get(), trollber);
      this.lilyPad((Block)TFBlocks.HUGE_LILY_PAD.get());
      this.simpleBlock(
         (Block)TFBlocks.HUGE_WATER_LILY.get(),
         ((BlockModelBuilder)this.models().cross(TFBlocks.HUGE_WATER_LILY.getId().getPath(), this.blockTexture((Block)TFBlocks.HUGE_WATER_LILY.get())))
            .renderType(CUTOUT)
      );
      this.simpleBlock((Block)TFBlocks.CASTLE_BRICK.get());
      this.simpleBlock((Block)TFBlocks.WORN_CASTLE_BRICK.get());
      this.simpleBlock((Block)TFBlocks.CRACKED_CASTLE_BRICK.get());
      this.allRotations((Block)TFBlocks.CASTLE_ROOF_TILE.get(), this.cubeAll((Block)TFBlocks.CASTLE_ROOF_TILE.get()));
      this.simpleBlock((Block)TFBlocks.MOSSY_CASTLE_BRICK.get());
      this.simpleBlock((Block)TFBlocks.THICK_CASTLE_BRICK.get());
      this.rotationallyCorrectColumn((Block)TFBlocks.ENCASED_CASTLE_BRICK_PILLAR.get());
      this.rotationallyCorrectColumn((Block)TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get());
      this.simpleBlock(
         (Block)TFBlocks.ENCASED_CASTLE_BRICK_TILE.get(),
         this.models()
            .cubeAll(
               TFBlocks.ENCASED_CASTLE_BRICK_TILE.getId().getPath(),
               TwilightForestMod.prefix("block/" + TFBlocks.ENCASED_CASTLE_BRICK_PILLAR.getId().getPath() + "_end")
            )
      );
      this.simpleBlock((Block)TFBlocks.BOLD_CASTLE_BRICK_TILE.get());
      this.stairsBlock((StairBlock)TFBlocks.CASTLE_BRICK_STAIRS.get(), TwilightForestMod.prefix("block/" + TFBlocks.CASTLE_BRICK_STAIRS.getId().getPath()));
      this.stairsBlock((StairBlock)TFBlocks.WORN_CASTLE_BRICK_STAIRS.get(), TwilightForestMod.prefix("block/" + TFBlocks.WORN_CASTLE_BRICK.getId().getPath()));
      this.stairsBlock(
         (StairBlock)TFBlocks.CRACKED_CASTLE_BRICK_STAIRS.get(), TwilightForestMod.prefix("block/" + TFBlocks.CRACKED_CASTLE_BRICK_STAIRS.getId().getPath())
      );
      this.stairsBlock(
         (StairBlock)TFBlocks.MOSSY_CASTLE_BRICK_STAIRS.get(), TwilightForestMod.prefix("block/" + TFBlocks.MOSSY_CASTLE_BRICK_STAIRS.getId().getPath())
      );
      this.bisectedStairsBlock(
         TFBlocks.ENCASED_CASTLE_BRICK_STAIRS,
         TwilightForestMod.prefix("block/encased_castle_brick_pillar_h"),
         TwilightForestMod.prefix("block/castleblock_tile"),
         TwilightForestMod.prefix("block/" + TFBlocks.CASTLE_ROOF_TILE.getId().getPath())
      );
      this.stairsBlock(
         (StairBlock)TFBlocks.BOLD_CASTLE_BRICK_STAIRS.get(), TwilightForestMod.prefix("block/" + TFBlocks.BOLD_CASTLE_BRICK_TILE.getId().getPath())
      );
      ConfiguredModel[] runeBrickModels = new ConfiguredModel[8];

      for (int i = 0; i < runeBrickModels.length; i++) {
         runeBrickModels[i] = new ConfiguredModel(
            ((CompositeModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent("castle_rune_brick_" + i, "block/block"))
                     .texture("particle", TwilightForestMod.prefix("block/castle_brick")))
                  .customLoader(CompositeModelBuilder::begin))
               .child(
                  "brick",
                  (BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent("castle_rune_bricks", "block/cube_all"))
                     .texture("all", TwilightForestMod.prefix("block/castle_brick"))
               )
               .child(
                  "runes",
                  (BlockModelBuilder)this.makeEmissiveBlockAll("castle_runes_" + i, CUTOUT, 15)
                     .texture("all", TwilightForestMod.prefix("block/castleblock_magic_" + i))
               )
               .end()
         );
      }

      ((BlockModelBuilder)this.make2LayerCubeAllSidesSame("castle_rune_inventory", CUTOUT, 0, 15, false)
            .texture("all", TwilightForestMod.prefix("block/castle_brick")))
         .texture("all2", TwilightForestMod.prefix("block/castleblock_magic_0"));
      this.simpleBlock((Block)TFBlocks.YELLOW_CASTLE_RUNE_BRICK.get(), runeBrickModels);
      this.simpleBlock((Block)TFBlocks.VIOLET_CASTLE_RUNE_BRICK.get(), runeBrickModels);
      this.simpleBlock((Block)TFBlocks.PINK_CASTLE_RUNE_BRICK.get(), runeBrickModels);
      this.simpleBlock((Block)TFBlocks.BLUE_CASTLE_RUNE_BRICK.get(), runeBrickModels);
      this.logBlock((RotatedPillarBlock)TFBlocks.CINDER_LOG.get());
      this.simpleBlock(
         (Block)TFBlocks.CINDER_WOOD.get(),
         this.models().cubeAll(TFBlocks.CINDER_WOOD.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.CINDER_LOG.getId().getPath()))
      );
      ModelFile furnaceOff = this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/furnace"));
      ModelFile furnaceOn = this.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/furnace_on"));
      this.horizontalBlock((Block)TFBlocks.CINDER_FURNACE.get(), state -> state.getValue(CinderFurnaceBlock.LIT) ? furnaceOn : furnaceOff);
      this.castleDoor((Block)TFBlocks.YELLOW_CASTLE_DOOR.get());
      this.castleDoor((Block)TFBlocks.VIOLET_CASTLE_DOOR.get());
      this.castleDoor((Block)TFBlocks.PINK_CASTLE_DOOR.get());
      this.castleDoor((Block)TFBlocks.BLUE_CASTLE_DOOR.get());
      this.simpleBlockExisting((Block)TFBlocks.KNIGHTMETAL_BLOCK.get());
      this.simpleBlock((Block)TFBlocks.IRONWOOD_BLOCK.get());
      this.simpleBlockExisting((Block)TFBlocks.FIERY_BLOCK.get());
      this.simpleBlock(
         (Block)TFBlocks.ARCTIC_FUR_BLOCK.get(),
         ((ConnectedTextureBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                        .withExistingParent(TFBlocks.ARCTIC_FUR_BLOCK.getId().getPath(), "block/block"))
                     .texture("overlay_texture", this.blockTexture((Block)TFBlocks.ARCTIC_FUR_BLOCK.get())))
                  .texture("overlay_connected", this.blockTexture((Block)TFBlocks.ARCTIC_FUR_BLOCK.get()).withSuffix("_ctm")))
               .customLoader(ConnectedTextureBuilder::begin))
            .connectsTo((Block)TFBlocks.ARCTIC_FUR_BLOCK.get())
            .end()
      );
      ModelFile steeleafBlock = this.models()
         .cubeAll(TFBlocks.STEELEAF_BLOCK.getId().getPath(), TwilightForestMod.prefix("block/" + TFBlocks.STEELEAF_BLOCK.getId().getPath()));
      this.allRotations((Block)TFBlocks.STEELEAF_BLOCK.get(), steeleafBlock);
      ModelFile carminiteBlock = ((BlockModelBuilder)this.make2LayerCubeAllSidesSame(TFBlocks.CARMINITE_BLOCK.getId().getPath(), SOLID, 4, 7, true)
            .texture("all", TwilightForestMod.prefix("block/" + TFBlocks.CARMINITE_BLOCK.getId().getPath())))
         .texture("all2", TwilightForestMod.prefix("block/" + TFBlocks.CARMINITE_BLOCK.getId().getPath() + "_overlay"));
      this.allRotations((Block)TFBlocks.CARMINITE_BLOCK.get(), carminiteBlock);
      this.horizontalBlock(
         (Block)TFBlocks.TWILIGHT_PORTAL_MINIATURE_STRUCTURE.get(), this.models().getExistingFile(TwilightForestMod.prefix("block/miniature/portal"))
      );
      this.horizontalBlock(
         (Block)TFBlocks.NAGA_COURTYARD_MINIATURE_STRUCTURE.get(), this.models().getExistingFile(TwilightForestMod.prefix("block/miniature/naga_courtyard"))
      );
      this.horizontalBlock(
         (Block)TFBlocks.LICH_TOWER_MINIATURE_STRUCTURE.get(), this.models().getExistingFile(TwilightForestMod.prefix("block/miniature/lich_tower"))
      );
      this.horizontalBlock(
         (Block)TFBlocks.MINOTAUR_LABYRINTH_MINIATURE_STRUCTURE.get(), this.models().getExistingFile(TwilightForestMod.prefix("block/miniature/labyrinth"))
      );
      this.horizontalBlock(
         (Block)TFBlocks.DARK_TOWER_MINIATURE_STRUCTURE.get(), this.models().getExistingFile(TwilightForestMod.prefix("block/miniature/dark_tower"))
      );
      this.mazestone();
      this.simpleBlock(
         (Block)TFBlocks.HEDGE.get(),
         ConfiguredModel.builder()
            .weight(10)
            .modelFile(this.models().cubeAll(TFBlocks.HEDGE.getId().getPath(), this.blockTexture((Block)TFBlocks.HEDGE.get())))
            .nextModel()
            .weight(1)
            .modelFile(
               this.models()
                  .cubeAll(TFBlocks.HEDGE.getId().getPath() + "_rose", TwilightForestMod.prefix("block/" + TFBlocks.HEDGE.getId().getPath() + "_rose"))
            )
            .build()
      );
      ModelFile bigSpawner = ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent("boss_spawner", "block/spawner"))
            .texture("all", TwilightForestMod.prefix("block/boss_spawner")))
         .renderType(CUTOUT);
      this.simpleBlock((Block)TFBlocks.NAGA_BOSS_SPAWNER.get(), bigSpawner);
      this.simpleBlock((Block)TFBlocks.LICH_BOSS_SPAWNER.get(), bigSpawner);
      this.simpleBlock((Block)TFBlocks.HYDRA_BOSS_SPAWNER.get(), bigSpawner);
      this.simpleBlock((Block)TFBlocks.UR_GHAST_BOSS_SPAWNER.get(), bigSpawner);
      this.simpleBlock((Block)TFBlocks.KNIGHT_PHANTOM_BOSS_SPAWNER.get(), bigSpawner);
      this.simpleBlock((Block)TFBlocks.SNOW_QUEEN_BOSS_SPAWNER.get(), bigSpawner);
      this.simpleBlock((Block)TFBlocks.MINOSHROOM_BOSS_SPAWNER.get(), bigSpawner);
      this.simpleBlock((Block)TFBlocks.ALPHA_YETI_BOSS_SPAWNER.get(), bigSpawner);
      this.simpleBlock((Block)TFBlocks.FINAL_BOSS_BOSS_SPAWNER.get(), bigSpawner);
      BlockModelBuilder masonJar = this.makeJar(TFBlocks.MASON_JAR.getId().getPath());
      this.simpleBlock((Block)TFBlocks.MASON_JAR.get(), masonJar);
      this.simpleBlock((Block)TFBlocks.CICADA_JAR.get(), masonJar);
      this.simpleBlock((Block)TFBlocks.FIREFLY_JAR.get(), masonJar);
      this.simpleBlockExisting((Block)TFBlocks.FIREFLY_SPAWNER.get());
      ResourceLocation jarLid = TwilightForestMod.prefix("jar_lid");

      for (JarRenderer.LidResource lid : (List)JarRenderer.LID_LOCATION_LIST.get()) {
         ResourceLocation item = lid.resourceLocation();
         String name = item.getPath();
         if (lid.lid() == Items.PUMPKIN) {
            ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent("block/lid/" + name, jarLid)).texture("1", "minecraft:block/pumpkin_top"))
               .texture("2", "minecraft:block/pumpkin_side");
         } else {
            if (lid.customPath() != null) {
               name = lid.customPath();
            }

            ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent("block/lid/" + name, jarLid))
                  .texture("1", item.getNamespace() + ":block/" + item.getPath() + "_top"))
               .texture("2", item.getNamespace() + ":block/" + item.getPath());
         }
      }

      this.registerBush((Block)TFBlocks.IRON_OREBERRY.get());
      this.registerBush((Block)TFBlocks.GOLD_OREBERRY.get());
      this.registerBush((Block)TFBlocks.COPPER_OREBERRY.get());
      this.registerBush((Block)TFBlocks.ESSENCE_OREBERRY.get(), 3, 3);
      this.registerBush((Block)TFBlocks.RASPBERRY_BUSH.get());
      this.registerBush((Block)TFBlocks.BLUEBERRY_BUSH.get());
      this.registerBush((Block)TFBlocks.BLACKBERRY_BUSH.get());
      this.registerBush((Block)TFBlocks.MALOBERRY_BUSH.get());
      this.registerBush((Block)TFBlocks.BLIGHTBERRY_BUSH.get());
      this.registerBush((Block)TFBlocks.DUSKBERRY_BUSH.get());
      this.registerBush((Block)TFBlocks.SKYBERRY_BUSH.get());
      this.registerBush((Block)TFBlocks.STINGBERRY_BUSH.get());
      this.registerPlantBlocks();
      this.simpleBlock((Block)TFBlocks.ROOT_BLOCK.get());
      this.simpleBlock((Block)TFBlocks.LIVEROOT_BLOCK.get());
      this.simpleBlock((Block)TFBlocks.MANGROVE_ROOT.get());
      ModelFile glowing = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.make2LayerCubeNoBottom(
                                          TFBlocks.UNCRAFTING_TABLE.getId().getPath() + "_glowing", TRANSLUCENT, 0, 15, true
                                       )
                                       .texture("top", TwilightForestMod.prefix("block/uncrafting_top")))
                                    .texture("north", TwilightForestMod.prefix("block/uncrafting_side")))
                                 .texture("south", TwilightForestMod.prefix("block/uncrafting_side")))
                              .texture("west", TwilightForestMod.prefix("block/uncrafting_side")))
                           .texture("east", TwilightForestMod.prefix("block/uncrafting_side")))
                        .texture("bottom", ResourceLocation.withDefaultNamespace("block/jungle_planks")))
                     .texture("top2", TwilightForestMod.prefix("block/uncrafting_glow")))
                  .texture("north2", TwilightForestMod.prefix("block/uncrafting_glow_side")))
               .texture("south2", TwilightForestMod.prefix("block/uncrafting_glow_side")))
            .texture("west2", TwilightForestMod.prefix("block/uncrafting_glow_side")))
         .texture("east2", TwilightForestMod.prefix("block/uncrafting_glow_side"));
      ModelFile notglowing = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.makeCubeWithTopLayer(
                              TFBlocks.UNCRAFTING_TABLE.getId().getPath(), TRANSLUCENT, 0, 15
                           )
                           .texture("top", TwilightForestMod.prefix("block/uncrafting_top")))
                        .texture("north", TwilightForestMod.prefix("block/uncrafting_side")))
                     .texture("south", TwilightForestMod.prefix("block/uncrafting_side")))
                  .texture("west", TwilightForestMod.prefix("block/uncrafting_side")))
               .texture("east", TwilightForestMod.prefix("block/uncrafting_side")))
            .texture("bottom", ResourceLocation.withDefaultNamespace("block/jungle_planks")))
         .texture("top2", TwilightForestMod.prefix("block/uncrafting_glow"));
      this.getVariantBuilder((Block)TFBlocks.UNCRAFTING_TABLE.get())
         .forAllStates(s -> ConfiguredModel.builder().modelFile(s.getValue(UncraftingTableBlock.POWERED) ? glowing : notglowing).build());
      this.registerSmokersAndJets();
      this.axisBlock(
         (RotatedPillarBlock)TFBlocks.TWISTED_STONE.get(),
         TwilightForestMod.prefix("block/twisted_stone_side"),
         TwilightForestMod.prefix("block/twisted_stone_end")
      );
      this.axisBlock(
         (RotatedPillarBlock)TFBlocks.BOLD_STONE_PILLAR.get(),
         TwilightForestMod.prefix("block/stone_pillar_side"),
         TwilightForestMod.prefix("block/stone_pillar_end")
      );
      this.simpleBlock(
         (Block)TFBlocks.CANOPY_BOOKSHELF.get(),
         ConfiguredModel.builder()
            .weight(3)
            .modelFile(
               this.models()
                  .cubeColumn(
                     "canopy_bookshelf", TwilightForestMod.prefix("block/wood/bookshelf_canopy"), TwilightForestMod.prefix("block/wood/planks_canopy_0")
                  )
            )
            .nextModel()
            .modelFile(
               this.models()
                  .cubeColumn(
                     "canopy_bookshelf_1", TwilightForestMod.prefix("block/wood/bookshelf_canopy_1"), TwilightForestMod.prefix("block/wood/planks_canopy_0")
                  )
            )
            .nextModel()
            .modelFile(
               this.models()
                  .cubeColumn(
                     "canopy_bookshelf_2", TwilightForestMod.prefix("block/wood/bookshelf_canopy_2"), TwilightForestMod.prefix("block/wood/planks_canopy_0")
                  )
            )
            .nextModel()
            .modelFile(
               this.models()
                  .cubeColumn(
                     "canopy_bookshelf_3", TwilightForestMod.prefix("block/wood/bookshelf_canopy_3"), TwilightForestMod.prefix("block/wood/planks_canopy_0")
                  )
            )
            .build()
      );
      MultiPartBlockStateBuilder builder = this.getMultipartBuilder((Block)TFBlocks.CHISELED_CANOPY_BOOKSHELF.get());
      List.of(Pair.of(Direction.NORTH, 0), Pair.of(Direction.EAST, 90), Pair.of(Direction.SOUTH, 180), Pair.of(Direction.WEST, 270))
         .forEach(
            pair -> {
               Direction direction = (Direction)pair.getFirst();
               ((PartBuilder)builder.part()
                     .uvLock(true)
                     .rotationY((Integer)pair.getSecond())
                     .modelFile(
                        ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent("chiseled_canopy_bookshelf", "chiseled_bookshelf"))
                              .texture("top", TwilightForestMod.prefix("block/wood/chiseled_canopy_bookshelf_top")))
                           .texture("side", TwilightForestMod.prefix("block/wood/chiseled_canopy_bookshelf_side"))
                     )
                     .addModel())
                  .condition(HorizontalDirectionalBlock.FACING, new Direction[]{direction})
                  .end();
               List.of(
                     Pair.of(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_0_OCCUPIED, "_slot_top_left"),
                     Pair.of(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_1_OCCUPIED, "_slot_top_mid"),
                     Pair.of(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_2_OCCUPIED, "_slot_top_right"),
                     Pair.of(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_3_OCCUPIED, "_slot_bottom_left"),
                     Pair.of(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_4_OCCUPIED, "_slot_bottom_mid"),
                     Pair.of(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_5_OCCUPIED, "_slot_bottom_right")
                  )
                  .forEach(
                     pair1 -> {
                        ((PartBuilder)builder.part()
                              .uvLock(true)
                              .rotationY((Integer)pair.getSecond())
                              .modelFile(
                                 ((BlockModelBuilder)this.models()
                                       .withExistingParent(
                                          "chiseled_canopy_bookshelf_occupied" + (String)pair1.getSecond(),
                                          "template_chiseled_bookshelf" + (String)pair1.getSecond()
                                       ))
                                    .texture("texture", TwilightForestMod.prefix("block/wood/chiseled_canopy_bookshelf_occupied"))
                              )
                              .addModel())
                           .nestedGroup()
                           .condition(HorizontalDirectionalBlock.FACING, new Direction[]{direction})
                           .condition((Property)pair1.getFirst(), new Boolean[]{true})
                           .end();
                        ((PartBuilder)builder.part()
                              .uvLock(true)
                              .rotationY((Integer)pair.getSecond())
                              .modelFile(
                                 ((BlockModelBuilder)this.models()
                                       .withExistingParent(
                                          "chiseled_canopy_bookshelf_empty" + (String)pair1.getSecond(),
                                          "template_chiseled_bookshelf" + (String)pair1.getSecond()
                                       ))
                                    .texture("texture", TwilightForestMod.prefix("block/wood/chiseled_canopy_bookshelf_empty"))
                              )
                              .addModel())
                           .nestedGroup()
                           .condition(HorizontalDirectionalBlock.FACING, new Direction[]{direction})
                           .condition((Property)pair1.getFirst(), new Boolean[]{false})
                           .end();
                     }
                  );
            }
         );
      ((ItemModelBuilder)this.itemModels().getBuilder("chiseled_canopy_bookshelf"))
         .parent(
            this.models()
               .orientable(
                  "chiseled_canopy_bookshelf_inventory",
                  TwilightForestMod.prefix("block/wood/chiseled_canopy_bookshelf_side"),
                  TwilightForestMod.prefix("block/wood/chiseled_canopy_bookshelf_empty"),
                  TwilightForestMod.prefix("block/wood/chiseled_canopy_bookshelf_top")
               )
         );
      ((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)this.getMultipartBuilder(
                                                                  (Block)TFBlocks.WROUGHT_IRON_FENCE.get()
                                                               )
                                                               .part()
                                                               .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("wrought_iron_fence_post")))
                                                               .addModel())
                                                            .condition(
                                                               WroughtIronFenceBlock.POST,
                                                               new WroughtIronFenceBlock.PostState[]{WroughtIronFenceBlock.PostState.POST}
                                                            )
                                                            .end()
                                                            .part()
                                                            .modelFile(
                                                               this.models().getExistingFile(TwilightForestMod.prefix("wrought_iron_fence_post_capped"))
                                                            )
                                                            .addModel())
                                                         .condition(
                                                            WroughtIronFenceBlock.POST,
                                                            new WroughtIronFenceBlock.PostState[]{WroughtIronFenceBlock.PostState.CAPPED}
                                                         )
                                                         .end()
                                                         .part()
                                                         .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("wrought_iron_fence_full")))
                                                         .addModel())
                                                      .condition(
                                                         WroughtIronFenceBlock.NORTH_FENCE,
                                                         new WroughtIronFenceBlock.FenceSide[]{WroughtIronFenceBlock.FenceSide.FULL}
                                                      )
                                                      .end()
                                                      .part()
                                                      .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("wrought_iron_fence_top")))
                                                      .addModel())
                                                   .condition(
                                                      WroughtIronFenceBlock.NORTH_FENCE,
                                                      new WroughtIronFenceBlock.FenceSide[]{WroughtIronFenceBlock.FenceSide.TOP}
                                                   )
                                                   .end()
                                                   .part()
                                                   .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("wrought_iron_fence_middle")))
                                                   .addModel())
                                                .condition(
                                                   WroughtIronFenceBlock.NORTH_FENCE,
                                                   new WroughtIronFenceBlock.FenceSide[]{WroughtIronFenceBlock.FenceSide.MIDDLE}
                                                )
                                                .end()
                                                .part()
                                                .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("wrought_iron_fence_bottom")))
                                                .addModel())
                                             .condition(
                                                WroughtIronFenceBlock.NORTH_FENCE,
                                                new WroughtIronFenceBlock.FenceSide[]{WroughtIronFenceBlock.FenceSide.BOTTOM}
                                             )
                                             .end()
                                             .part()
                                             .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("wrought_iron_fence_full")))
                                             .rotationY(90)
                                             .addModel())
                                          .condition(
                                             WroughtIronFenceBlock.EAST_FENCE, new WroughtIronFenceBlock.FenceSide[]{WroughtIronFenceBlock.FenceSide.FULL}
                                          )
                                          .end()
                                          .part()
                                          .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("wrought_iron_fence_top")))
                                          .rotationY(90)
                                          .addModel())
                                       .condition(WroughtIronFenceBlock.EAST_FENCE, new WroughtIronFenceBlock.FenceSide[]{WroughtIronFenceBlock.FenceSide.TOP})
                                       .end()
                                       .part()
                                       .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("wrought_iron_fence_middle")))
                                       .rotationY(90)
                                       .addModel())
                                    .condition(WroughtIronFenceBlock.EAST_FENCE, new WroughtIronFenceBlock.FenceSide[]{WroughtIronFenceBlock.FenceSide.MIDDLE})
                                    .end()
                                    .part()
                                    .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("wrought_iron_fence_bottom")))
                                    .rotationY(90)
                                    .addModel())
                                 .condition(WroughtIronFenceBlock.EAST_FENCE, new WroughtIronFenceBlock.FenceSide[]{WroughtIronFenceBlock.FenceSide.BOTTOM})
                                 .end()
                                 .part()
                                 .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("wrought_iron_fence_full")))
                                 .rotationY(180)
                                 .addModel())
                              .condition(WroughtIronFenceBlock.SOUTH_FENCE, new WroughtIronFenceBlock.FenceSide[]{WroughtIronFenceBlock.FenceSide.FULL})
                              .end()
                              .part()
                              .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("wrought_iron_fence_top")))
                              .rotationY(180)
                              .addModel())
                           .condition(WroughtIronFenceBlock.SOUTH_FENCE, new WroughtIronFenceBlock.FenceSide[]{WroughtIronFenceBlock.FenceSide.TOP})
                           .end()
                           .part()
                           .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("wrought_iron_fence_middle")))
                           .rotationY(180)
                           .addModel())
                        .condition(WroughtIronFenceBlock.SOUTH_FENCE, new WroughtIronFenceBlock.FenceSide[]{WroughtIronFenceBlock.FenceSide.MIDDLE})
                        .end()
                        .part()
                        .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("wrought_iron_fence_bottom")))
                        .rotationY(180)
                        .addModel())
                     .condition(WroughtIronFenceBlock.SOUTH_FENCE, new WroughtIronFenceBlock.FenceSide[]{WroughtIronFenceBlock.FenceSide.BOTTOM})
                     .end()
                     .part()
                     .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("wrought_iron_fence_full")))
                     .rotationY(270)
                     .addModel())
                  .condition(WroughtIronFenceBlock.WEST_FENCE, new WroughtIronFenceBlock.FenceSide[]{WroughtIronFenceBlock.FenceSide.FULL})
                  .end()
                  .part()
                  .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("wrought_iron_fence_top")))
                  .rotationY(270)
                  .addModel())
               .condition(WroughtIronFenceBlock.WEST_FENCE, new WroughtIronFenceBlock.FenceSide[]{WroughtIronFenceBlock.FenceSide.TOP})
               .end()
               .part()
               .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("wrought_iron_fence_middle")))
               .rotationY(270)
               .addModel())
            .condition(WroughtIronFenceBlock.WEST_FENCE, new WroughtIronFenceBlock.FenceSide[]{WroughtIronFenceBlock.FenceSide.MIDDLE})
            .end()
            .part()
            .modelFile(this.models().getExistingFile(TwilightForestMod.prefix("wrought_iron_fence_bottom")))
            .rotationY(270)
            .addModel())
         .condition(WroughtIronFenceBlock.WEST_FENCE, new WroughtIronFenceBlock.FenceSide[]{WroughtIronFenceBlock.FenceSide.BOTTOM})
         .end();
      this.registerCoronationCarpet();
      this.registerWoodBlocks();
      this.registerNagastone();
      this.registerForceFields();
      this.simpleBlock(
         (Block)TFBlocks.POTTED_TWILIGHT_OAK_SAPLING.get(),
         ((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(TFBlocks.POTTED_TWILIGHT_OAK_SAPLING.getId().getPath(), "block/flower_pot_cross"))
               .renderType(CUTOUT))
            .texture("plant", this.blockTexture((Block)TFBlocks.TWILIGHT_OAK_SAPLING.get()))
      );
      this.simpleBlock(
         (Block)TFBlocks.POTTED_CANOPY_SAPLING.get(),
         ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(TFBlocks.POTTED_CANOPY_SAPLING.getId().getPath(), "block/flower_pot_cross"))
               .renderType(CUTOUT))
            .texture("plant", this.blockTexture((Block)TFBlocks.CANOPY_SAPLING.get()))
      );
      this.simpleBlock(
         (Block)TFBlocks.POTTED_MANGROVE_SAPLING.get(),
         ((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(TFBlocks.POTTED_MANGROVE_SAPLING.getId().getPath(), TwilightForestMod.prefix("block/potted_larger_sapling")))
               .renderType(CUTOUT))
            .texture("plant", this.blockTexture((Block)TFBlocks.MANGROVE_SAPLING.get()))
      );
      this.simpleBlock(
         (Block)TFBlocks.POTTED_DARKWOOD_SAPLING.get(),
         ((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(TFBlocks.POTTED_DARKWOOD_SAPLING.getId().getPath(), TwilightForestMod.prefix("block/potted_larger_sapling")))
               .renderType(CUTOUT))
            .texture("plant", this.blockTexture((Block)TFBlocks.DARKWOOD_SAPLING.get()))
      );
      this.simpleBlock(
         (Block)TFBlocks.POTTED_HOLLOW_OAK_SAPLING.get(),
         ((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(TFBlocks.POTTED_HOLLOW_OAK_SAPLING.getId().getPath(), "block/flower_pot_cross"))
               .renderType(CUTOUT))
            .texture("plant", this.blockTexture((Block)TFBlocks.HOLLOW_OAK_SAPLING.get()))
      );
      this.simpleBlock(
         (Block)TFBlocks.POTTED_RAINBOW_OAK_SAPLING.get(),
         ((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(TFBlocks.POTTED_RAINBOW_OAK_SAPLING.getId().getPath(), "block/flower_pot_cross"))
               .renderType(CUTOUT))
            .texture("plant", this.blockTexture((Block)TFBlocks.RAINBOW_OAK_SAPLING.get()))
      );
      this.simpleBlock(
         (Block)TFBlocks.POTTED_TIME_SAPLING.get(),
         ((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(TFBlocks.POTTED_TIME_SAPLING.getId().getPath(), TwilightForestMod.prefix("block/potted_larger_sapling")))
               .renderType(CUTOUT))
            .texture("plant", this.blockTexture((Block)TFBlocks.TIME_SAPLING.get()))
      );
      this.simpleBlock(
         (Block)TFBlocks.POTTED_TRANSFORMATION_SAPLING.get(),
         ((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(TFBlocks.POTTED_TRANSFORMATION_SAPLING.getId().getPath(), "block/flower_pot_cross"))
               .renderType(CUTOUT))
            .texture("plant", this.blockTexture((Block)TFBlocks.TRANSFORMATION_SAPLING.get()))
      );
      this.simpleBlock(
         (Block)TFBlocks.POTTED_MINING_SAPLING.get(),
         ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(TFBlocks.POTTED_MINING_SAPLING.getId().getPath(), "block/flower_pot_cross"))
               .renderType(CUTOUT))
            .texture("plant", this.blockTexture((Block)TFBlocks.MINING_SAPLING.get()))
      );
      this.simpleBlock(
         (Block)TFBlocks.POTTED_SORTING_SAPLING.get(),
         ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(TFBlocks.POTTED_SORTING_SAPLING.getId().getPath(), "block/flower_pot_cross"))
               .renderType(CUTOUT))
            .texture("plant", this.blockTexture((Block)TFBlocks.SORTING_SAPLING.get()))
      );
      this.simpleBlock((Block)TFBlocks.POTTED_MAYAPPLE.get(), this.models().getExistingFile(TwilightForestMod.prefix("block/potted_mayapple")));
      this.simpleBlock(
         (Block)TFBlocks.POTTED_FIDDLEHEAD.get(),
         ((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(TFBlocks.POTTED_FIDDLEHEAD.getId().getPath(), "block/tinted_flower_pot_cross"))
               .renderType(CUTOUT))
            .texture("plant", this.blockTexture((Block)TFBlocks.POTTED_FIDDLEHEAD.get()))
      );
      this.simpleBlock(
         (Block)TFBlocks.POTTED_MUSHGLOOM.get(),
         ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(TFBlocks.POTTED_MUSHGLOOM.getId().getPath(), "block/flower_pot_cross"))
               .renderType(CUTOUT))
            .texture("plant", this.blockTexture((Block)TFBlocks.POTTED_MUSHGLOOM.get()))
      );
      this.simpleBlock(
         (Block)TFBlocks.POTTED_THORN.get(),
         ((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(TFBlocks.POTTED_THORN.getId().getPath(), TwilightForestMod.prefix("block/potted_thorn_template")))
               .texture("thorn_top", TwilightForestMod.prefix("block/brown_thorns_top")))
            .texture("thorn_side", TwilightForestMod.prefix("block/brown_thorns_side"))
      );
      this.simpleBlock(
         (Block)TFBlocks.POTTED_GREEN_THORN.get(),
         ((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(TFBlocks.POTTED_GREEN_THORN.getId().getPath(), TwilightForestMod.prefix("block/potted_thorn_template")))
               .texture("thorn_top", TwilightForestMod.prefix("block/green_thorns_top")))
            .texture("thorn_side", TwilightForestMod.prefix("block/green_thorns_side"))
      );
      this.simpleBlock(
         (Block)TFBlocks.POTTED_DEAD_THORN.get(),
         ((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(TFBlocks.POTTED_DEAD_THORN.getId().getPath(), TwilightForestMod.prefix("block/potted_thorn_template")))
               .texture("thorn_top", TwilightForestMod.prefix("block/burnt_thorns_top")))
            .texture("thorn_side", TwilightForestMod.prefix("block/burnt_thorns_side"))
      );
      this.builtinEntity((Block)TFBlocks.TWILIGHT_OAK_SIGN.get(), "twilightforest:block/wood/planks_twilight_oak_0");
      this.builtinEntity((Block)TFBlocks.TWILIGHT_WALL_SIGN.get(), "twilightforest:block/wood/planks_twilight_oak_0");
      this.builtinEntity((Block)TFBlocks.CANOPY_SIGN.get(), "twilightforest:block/wood/planks_canopy_0");
      this.builtinEntity((Block)TFBlocks.CANOPY_WALL_SIGN.get(), "twilightforest:block/wood/planks_canopy_0");
      this.builtinEntity((Block)TFBlocks.MANGROVE_SIGN.get(), "twilightforest:block/wood/planks_mangrove_0");
      this.builtinEntity((Block)TFBlocks.MANGROVE_WALL_SIGN.get(), "twilightforest:block/wood/planks_mangrove_0");
      this.builtinEntity((Block)TFBlocks.DARK_SIGN.get(), "twilightforest:block/wood/planks_darkwood_0");
      this.builtinEntity((Block)TFBlocks.DARK_WALL_SIGN.get(), "twilightforest:block/wood/planks_darkwood_0");
      this.builtinEntity((Block)TFBlocks.TIME_SIGN.get(), "twilightforest:block/wood/planks_time_0");
      this.builtinEntity((Block)TFBlocks.TIME_WALL_SIGN.get(), "twilightforest:block/wood/planks_time_0");
      this.builtinEntity((Block)TFBlocks.TRANSFORMATION_SIGN.get(), "twilightforest:block/wood/planks_trans_0");
      this.builtinEntity((Block)TFBlocks.TRANSFORMATION_WALL_SIGN.get(), "twilightforest:block/wood/planks_trans_0");
      this.builtinEntity((Block)TFBlocks.MINING_SIGN.get(), "twilightforest:block/wood/planks_mine_0");
      this.builtinEntity((Block)TFBlocks.MINING_WALL_SIGN.get(), "twilightforest:block/wood/planks_mine_0");
      this.builtinEntity((Block)TFBlocks.SORTING_SIGN.get(), "twilightforest:block/wood/planks_sort_0");
      this.builtinEntity((Block)TFBlocks.SORTING_WALL_SIGN.get(), "twilightforest:block/wood/planks_sort_0");
      this.builtinEntity((Block)TFBlocks.TWILIGHT_OAK_HANGING_SIGN.get(), "twilightforest:block/stripped_twilight_oak_log");
      this.builtinEntity((Block)TFBlocks.TWILIGHT_OAK_WALL_HANGING_SIGN.get(), "twilightforest:block/stripped_twilight_oak_log");
      this.builtinEntity((Block)TFBlocks.CANOPY_HANGING_SIGN.get(), "twilightforest:block/stripped_canopy_log");
      this.builtinEntity((Block)TFBlocks.CANOPY_WALL_HANGING_SIGN.get(), "twilightforest:block/stripped_canopy_log");
      this.builtinEntity((Block)TFBlocks.MANGROVE_HANGING_SIGN.get(), "twilightforest:block/stripped_mangrove_log");
      this.builtinEntity((Block)TFBlocks.MANGROVE_WALL_HANGING_SIGN.get(), "twilightforest:block/stripped_mangrove_log");
      this.builtinEntity((Block)TFBlocks.DARK_HANGING_SIGN.get(), "twilightforest:block/stripped_dark_log");
      this.builtinEntity((Block)TFBlocks.DARK_WALL_HANGING_SIGN.get(), "twilightforest:block/stripped_dark_log");
      this.builtinEntity((Block)TFBlocks.TIME_HANGING_SIGN.get(), "twilightforest:block/stripped_time_log");
      this.builtinEntity((Block)TFBlocks.TIME_WALL_HANGING_SIGN.get(), "twilightforest:block/stripped_time_log");
      this.builtinEntity((Block)TFBlocks.TRANSFORMATION_HANGING_SIGN.get(), "twilightforest:block/stripped_transformation_log");
      this.builtinEntity((Block)TFBlocks.TRANSFORMATION_WALL_HANGING_SIGN.get(), "twilightforest:block/stripped_transformation_log");
      this.builtinEntity((Block)TFBlocks.MINING_HANGING_SIGN.get(), "twilightforest:block/stripped_mining_log");
      this.builtinEntity((Block)TFBlocks.MINING_WALL_HANGING_SIGN.get(), "twilightforest:block/stripped_mining_log");
      this.builtinEntity((Block)TFBlocks.SORTING_HANGING_SIGN.get(), "twilightforest:block/stripped_sorting_log");
      this.builtinEntity((Block)TFBlocks.SORTING_WALL_HANGING_SIGN.get(), "twilightforest:block/stripped_sorting_log");
      this.builtinEntity((Block)TFBlocks.TWILIGHT_OAK_CHEST.get(), "twilightforest:block/wood/planks_twilight_oak_0");
      this.builtinEntity((Block)TFBlocks.CANOPY_CHEST.get(), "twilightforest:block/wood/planks_canopy_0");
      this.builtinEntity((Block)TFBlocks.MANGROVE_CHEST.get(), "twilightforest:block/wood/planks_mangrove_0");
      this.builtinEntity((Block)TFBlocks.DARK_CHEST.get(), "twilightforest:block/wood/planks_darkwood_0");
      this.builtinEntity((Block)TFBlocks.TIME_CHEST.get(), "twilightforest:block/wood/planks_time_0");
      this.builtinEntity((Block)TFBlocks.TRANSFORMATION_CHEST.get(), "twilightforest:block/wood/planks_trans_0");
      this.builtinEntity((Block)TFBlocks.MINING_CHEST.get(), "twilightforest:block/wood/planks_mine_0");
      this.builtinEntity((Block)TFBlocks.SORTING_CHEST.get(), "twilightforest:block/wood/planks_sort_0");
      this.builtinEntity((Block)TFBlocks.TWILIGHT_OAK_TRAPPED_CHEST.get(), "twilightforest:block/wood/planks_twilight_oak_0");
      this.builtinEntity((Block)TFBlocks.CANOPY_TRAPPED_CHEST.get(), "twilightforest:block/wood/planks_canopy_0");
      this.builtinEntity((Block)TFBlocks.MANGROVE_TRAPPED_CHEST.get(), "twilightforest:block/wood/planks_mangrove_0");
      this.builtinEntity((Block)TFBlocks.DARK_TRAPPED_CHEST.get(), "twilightforest:block/wood/planks_darkwood_0");
      this.builtinEntity((Block)TFBlocks.TIME_TRAPPED_CHEST.get(), "twilightforest:block/wood/planks_time_0");
      this.builtinEntity((Block)TFBlocks.TRANSFORMATION_TRAPPED_CHEST.get(), "twilightforest:block/wood/planks_trans_0");
      this.builtinEntity((Block)TFBlocks.MINING_TRAPPED_CHEST.get(), "twilightforest:block/wood/planks_mine_0");
      this.builtinEntity((Block)TFBlocks.SORTING_TRAPPED_CHEST.get(), "twilightforest:block/wood/planks_sort_0");
      this.casketStuff((Block)TFBlocks.SKULL_CHEST.value());
      this.casketStuff((Block)TFBlocks.KEEPSAKE_CASKET.value());
      this.stonePillar();
      this.candelabra();
      this.terrorcotta();
      this.simpleBlock(
         (Block)TFBlocks.SINISTER_SPAWNER.value(),
         ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent("sinister_spawner", "block/spawner"))
               .texture("all", TwilightForestMod.prefix("block/sinister_spawner")))
            .renderType(CUTOUT)
      );
      this.builtinEntity((Block)TFBlocks.OMINOUS_CANDLE.get(), "minecraft:block/candle_lit");
      this.builtinEntity((Block)TFBlocks.OMINOUS_WHITE_CANDLE.get(), "minecraft:block/candle_lit");
      this.builtinEntity((Block)TFBlocks.OMINOUS_ORANGE_CANDLE.get(), "minecraft:block/candle_lit");
      this.builtinEntity((Block)TFBlocks.OMINOUS_MAGENTA_CANDLE.get(), "minecraft:block/candle_lit");
      this.builtinEntity((Block)TFBlocks.OMINOUS_LIGHT_BLUE_CANDLE.get(), "minecraft:block/candle_lit");
      this.builtinEntity((Block)TFBlocks.OMINOUS_YELLOW_CANDLE.get(), "minecraft:block/candle_lit");
      this.builtinEntity((Block)TFBlocks.OMINOUS_LIME_CANDLE.get(), "minecraft:block/candle_lit");
      this.builtinEntity((Block)TFBlocks.OMINOUS_PINK_CANDLE.get(), "minecraft:block/candle_lit");
      this.builtinEntity((Block)TFBlocks.OMINOUS_GRAY_CANDLE.get(), "minecraft:block/candle_lit");
      this.builtinEntity((Block)TFBlocks.OMINOUS_LIGHT_GRAY_CANDLE.get(), "minecraft:block/candle_lit");
      this.builtinEntity((Block)TFBlocks.OMINOUS_CYAN_CANDLE.get(), "minecraft:block/candle_lit");
      this.builtinEntity((Block)TFBlocks.OMINOUS_PURPLE_CANDLE.get(), "minecraft:block/candle_lit");
      this.builtinEntity((Block)TFBlocks.OMINOUS_BLUE_CANDLE.get(), "minecraft:block/candle_lit");
      this.builtinEntity((Block)TFBlocks.OMINOUS_BROWN_CANDLE.get(), "minecraft:block/candle_lit");
      this.builtinEntity((Block)TFBlocks.OMINOUS_GREEN_CANDLE.get(), "minecraft:block/candle_lit");
      this.builtinEntity((Block)TFBlocks.OMINOUS_RED_CANDLE.get(), "minecraft:block/candle_lit");
      this.builtinEntity((Block)TFBlocks.OMINOUS_BLACK_CANDLE.get(), "minecraft:block/candle_lit");
      ModelFile floor0 = ((BlockModelBuilder)((BlockModelBuilder)this.models()
               .withExistingParent(TFBlocks.OMINOUS_FIRE.getId().getPath() + "_floor0", ResourceLocation.withDefaultNamespace("block/template_fire_floor")))
            .renderType(CUTOUT))
         .texture("fire", this.blockTexture((Block)TFBlocks.OMINOUS_FIRE.get()) + "_0");
      ModelFile floor1 = ((BlockModelBuilder)((BlockModelBuilder)this.models()
               .withExistingParent(TFBlocks.OMINOUS_FIRE.getId().getPath() + "_floor1", ResourceLocation.withDefaultNamespace("block/template_fire_floor")))
            .renderType(CUTOUT))
         .texture("fire", this.blockTexture((Block)TFBlocks.OMINOUS_FIRE.get()) + "_1");
      ModelFile side0 = ((BlockModelBuilder)((BlockModelBuilder)this.models()
               .withExistingParent(TFBlocks.OMINOUS_FIRE.getId().getPath() + "_side0", ResourceLocation.withDefaultNamespace("block/template_fire_side")))
            .renderType(CUTOUT))
         .texture("fire", this.blockTexture((Block)TFBlocks.OMINOUS_FIRE.get()) + "_0");
      ModelFile side1 = ((BlockModelBuilder)((BlockModelBuilder)this.models()
               .withExistingParent(TFBlocks.OMINOUS_FIRE.getId().getPath() + "_side1", ResourceLocation.withDefaultNamespace("block/template_fire_side")))
            .renderType(CUTOUT))
         .texture("fire", this.blockTexture((Block)TFBlocks.OMINOUS_FIRE.get()) + "_1");
      ModelFile side_alt0 = ((BlockModelBuilder)((BlockModelBuilder)this.models()
               .withExistingParent(
                  TFBlocks.OMINOUS_FIRE.getId().getPath() + "_side_alt0", ResourceLocation.withDefaultNamespace("block/template_fire_side_alt")
               ))
            .renderType(CUTOUT))
         .texture("fire", this.blockTexture((Block)TFBlocks.OMINOUS_FIRE.get()) + "_0");
      ModelFile side_alt1 = ((BlockModelBuilder)((BlockModelBuilder)this.models()
               .withExistingParent(
                  TFBlocks.OMINOUS_FIRE.getId().getPath() + "_side_alt1", ResourceLocation.withDefaultNamespace("block/template_fire_side_alt")
               ))
            .renderType(CUTOUT))
         .texture("fire", this.blockTexture((Block)TFBlocks.OMINOUS_FIRE.get()) + "_1");
      ((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)this.getMultipartBuilder((Block)TFBlocks.OMINOUS_FIRE.get())
                        .part()
                        .modelFile(floor0)
                        .nextModel()
                        .modelFile(floor1)
                        .addModel())
                     .end()
                     .part()
                     .modelFile(side0)
                     .nextModel()
                     .modelFile(side1)
                     .nextModel()
                     .modelFile(side_alt0)
                     .nextModel()
                     .modelFile(side_alt1)
                     .addModel())
                  .end()
                  .part()
                  .modelFile(side0)
                  .rotationY(90)
                  .nextModel()
                  .modelFile(side1)
                  .rotationY(90)
                  .nextModel()
                  .modelFile(side_alt0)
                  .rotationY(90)
                  .nextModel()
                  .modelFile(side_alt1)
                  .rotationY(90)
                  .addModel())
               .end()
               .part()
               .modelFile(side0)
               .rotationY(180)
               .nextModel()
               .modelFile(side1)
               .rotationY(180)
               .nextModel()
               .modelFile(side_alt0)
               .rotationY(180)
               .nextModel()
               .modelFile(side_alt1)
               .rotationY(180)
               .addModel())
            .end()
            .part()
            .modelFile(side0)
            .rotationY(270)
            .nextModel()
            .modelFile(side1)
            .rotationY(270)
            .nextModel()
            .modelFile(side_alt0)
            .rotationY(270)
            .nextModel()
            .modelFile(side_alt1)
            .rotationY(270)
            .addModel())
         .end();
   }

   private void registerCoronationCarpet() {
      ResourceLocation carpetTexture = TFBlocks.CORONATION_CARPET.getId().withPrefix("block/");
      ResourceLocation carpetCTM = carpetTexture.withSuffix("_ctm");
      this.simpleBlock(
         (Block)TFBlocks.CORONATION_CARPET.value(),
         ((RoyalRagsBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models().carpet(TFBlocks.CORONATION_CARPET.getRegisteredName(), carpetTexture))
                  .texture("wool_ctm", carpetCTM))
               .customLoader(RoyalRagsBuilder::begin))
            .end()
      );
   }

   private void registerForceFields() {
      UnmodifiableIterator var1 = ImmutableList.of(
            TFBlocks.PINK_FORCE_FIELD, TFBlocks.BLUE_FORCE_FIELD, TFBlocks.GREEN_FORCE_FIELD, TFBlocks.VIOLET_FORCE_FIELD, TFBlocks.ORANGE_FORCE_FIELD
         )
         .iterator();

      while (var1.hasNext()) {
         DeferredHolder<Block, Block> block = (DeferredHolder<Block, Block>)var1.next();
         ResourceLocation textureLocation = TwilightForestMod.prefix("block/" + block.getId().getPath());
         this.simpleBlock(
            (Block)block.get(),
            ((ForceFieldModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                                 .withExistingParent(block.getId().getPath(), ResourceLocation.withDefaultNamespace("block/block")))
                              .texture("particle", textureLocation))
                           .texture("pane", textureLocation))
                        .ao(false))
                     .renderType(TRANSLUCENT))
                  .customLoader(ForceFieldModelBuilder::begin))
               .forceFieldElement()
               .ifState(ForceFieldModel.ExtraDirection.WEST, true)
               .from(0.0F, 7.0F, 7.0F)
               .to(7.0F, 9.0F, 9.0F)
               .shade(false)
               .face(Direction.WEST)
               .cullface(Direction.WEST)
               .uvs(7.0F, 7.0F, 9.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .ifElse()
               .from(7.0F, 7.0F, 7.0F)
               .to(9.0F, 9.0F, 9.0F)
               .shade(false)
               .face(Direction.WEST)
               .uvs(7.0F, 7.0F, 9.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .end()
               .forceFieldElement()
               .ifState(ForceFieldModel.ExtraDirection.EAST, true)
               .from(9.0F, 7.0F, 7.0F)
               .to(16.0F, 9.0F, 9.0F)
               .shade(false)
               .face(Direction.EAST)
               .cullface(Direction.EAST)
               .uvs(7.0F, 7.0F, 9.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .ifElse()
               .from(7.0F, 7.0F, 7.0F)
               .to(9.0F, 9.0F, 9.0F)
               .shade(false)
               .face(Direction.EAST)
               .uvs(7.0F, 7.0F, 9.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .end()
               .forceFieldElement()
               .ifState(ForceFieldModel.ExtraDirection.DOWN, true)
               .from(7.0F, 0.0F, 7.0F)
               .to(9.0F, 7.0F, 9.0F)
               .shade(false)
               .face(Direction.DOWN)
               .cullface(Direction.DOWN)
               .uvs(7.0F, 7.0F, 9.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .ifElse()
               .from(7.0F, 7.0F, 7.0F)
               .to(9.0F, 9.0F, 9.0F)
               .shade(false)
               .face(Direction.DOWN)
               .uvs(7.0F, 7.0F, 9.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .end()
               .forceFieldElement()
               .ifState(ForceFieldModel.ExtraDirection.UP, true)
               .from(7.0F, 9.0F, 7.0F)
               .to(9.0F, 16.0F, 9.0F)
               .shade(false)
               .face(Direction.UP)
               .cullface(Direction.UP)
               .uvs(7.0F, 7.0F, 9.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .ifElse()
               .from(7.0F, 7.0F, 7.0F)
               .to(9.0F, 9.0F, 9.0F)
               .shade(false)
               .face(Direction.UP)
               .uvs(7.0F, 7.0F, 9.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .end()
               .forceFieldElement()
               .ifState(ForceFieldModel.ExtraDirection.NORTH, true)
               .from(7.0F, 7.0F, 0.0F)
               .to(9.0F, 9.0F, 7.0F)
               .shade(false)
               .face(Direction.NORTH)
               .cullface(Direction.NORTH)
               .uvs(7.0F, 7.0F, 9.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .ifElse()
               .from(7.0F, 7.0F, 7.0F)
               .to(9.0F, 9.0F, 9.0F)
               .shade(false)
               .face(Direction.NORTH)
               .uvs(7.0F, 7.0F, 9.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .end()
               .forceFieldElement()
               .ifState(ForceFieldModel.ExtraDirection.SOUTH, true)
               .from(7.0F, 7.0F, 9.0F)
               .to(9.0F, 9.0F, 16.0F)
               .shade(false)
               .face(Direction.SOUTH)
               .cullface(Direction.SOUTH)
               .uvs(7.0F, 7.0F, 9.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .ifElse()
               .from(7.0F, 7.0F, 7.0F)
               .to(9.0F, 9.0F, 9.0F)
               .shade(false)
               .face(Direction.SOUTH)
               .uvs(7.0F, 7.0F, 9.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .end()
               .forceFieldElement()
               .ifState(ForceFieldModel.ExtraDirection.DOWN_WEST, true)
               .parents(ForceFieldModel.ExtraDirection.DOWN, ForceFieldModel.ExtraDirection.WEST)
               .from(0.0F, 0.0F, 7.0F)
               .to(7.0F, 7.0F, 9.0F)
               .shade(false)
               .face(Direction.DOWN)
               .cullface(Direction.DOWN)
               .uvs(0.0F, 7.0F, 7.0F, 9.0F)
               .end()
               .face(Direction.WEST)
               .cullface(Direction.WEST)
               .uvs(7.0F, 0.0F, 9.0F, 7.0F)
               .end()
               .face(Direction.NORTH)
               .uvs(0.0F, 0.0F, 7.0F, 7.0F)
               .end()
               .face(Direction.SOUTH)
               .uvs(9.0F, 0.0F, 16.0F, 7.0F)
               .end()
               .faces((direction, builder) -> builder.texture("#pane").emissivity(15, 15))
               .ifElse()
               .from(7.0F, 0.0F, 7.0F)
               .to(9.0F, 7.0F, 9.0F)
               .parents(ForceFieldModel.ExtraDirection.DOWN)
               .shade(false)
               .face(Direction.WEST)
               .uvs(7.0F, 0.0F, 9.0F, 7.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .ifSame()
               .from(0.0F, 7.0F, 7.0F)
               .to(7.0F, 9.0F, 9.0F)
               .parents(ForceFieldModel.ExtraDirection.WEST)
               .shade(false)
               .face(Direction.DOWN)
               .uvs(0.0F, 7.0F, 7.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .end()
               .forceFieldElement()
               .ifState(ForceFieldModel.ExtraDirection.DOWN_EAST, true)
               .parents(ForceFieldModel.ExtraDirection.DOWN, ForceFieldModel.ExtraDirection.EAST)
               .from(9.0F, 0.0F, 7.0F)
               .to(16.0F, 7.0F, 9.0F)
               .shade(false)
               .face(Direction.DOWN)
               .cullface(Direction.DOWN)
               .uvs(9.0F, 7.0F, 16.0F, 9.0F)
               .end()
               .face(Direction.EAST)
               .cullface(Direction.EAST)
               .uvs(7.0F, 0.0F, 9.0F, 7.0F)
               .end()
               .face(Direction.NORTH)
               .uvs(9.0F, 0.0F, 16.0F, 7.0F)
               .end()
               .face(Direction.SOUTH)
               .uvs(0.0F, 0.0F, 7.0F, 7.0F)
               .end()
               .faces((direction, builder) -> builder.texture("#pane").emissivity(15, 15))
               .ifElse()
               .from(7.0F, 0.0F, 7.0F)
               .to(9.0F, 7.0F, 9.0F)
               .parents(ForceFieldModel.ExtraDirection.DOWN)
               .shade(false)
               .face(Direction.EAST)
               .uvs(7.0F, 0.0F, 9.0F, 7.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .ifSame()
               .from(9.0F, 7.0F, 7.0F)
               .to(16.0F, 9.0F, 9.0F)
               .parents(ForceFieldModel.ExtraDirection.EAST)
               .shade(false)
               .face(Direction.DOWN)
               .uvs(9.0F, 7.0F, 16.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .end()
               .forceFieldElement()
               .ifState(ForceFieldModel.ExtraDirection.DOWN_NORTH, true)
               .from(7.0F, 0.0F, 0.0F)
               .to(9.0F, 7.0F, 7.0F)
               .parents(ForceFieldModel.ExtraDirection.DOWN, ForceFieldModel.ExtraDirection.NORTH)
               .shade(false)
               .face(Direction.DOWN)
               .cullface(Direction.DOWN)
               .uvs(7.0F, 0.0F, 9.0F, 7.0F)
               .end()
               .face(Direction.NORTH)
               .cullface(Direction.NORTH)
               .uvs(7.0F, 0.0F, 9.0F, 7.0F)
               .end()
               .face(Direction.WEST)
               .uvs(0.0F, 0.0F, 7.0F, 7.0F)
               .end()
               .face(Direction.EAST)
               .uvs(9.0F, 9.0F, 16.0F, 16.0F)
               .end()
               .faces((direction, builder) -> builder.texture("#pane").emissivity(15, 15))
               .ifElse()
               .from(7.0F, 0.0F, 7.0F)
               .to(9.0F, 7.0F, 9.0F)
               .parents(ForceFieldModel.ExtraDirection.DOWN)
               .shade(false)
               .face(Direction.NORTH)
               .uvs(7.0F, 0.0F, 9.0F, 7.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .ifSame()
               .from(7.0F, 7.0F, 0.0F)
               .to(9.0F, 9.0F, 7.0F)
               .parents(ForceFieldModel.ExtraDirection.NORTH)
               .shade(false)
               .face(Direction.DOWN)
               .uvs(7.0F, 0.0F, 9.0F, 7.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .end()
               .forceFieldElement()
               .ifState(ForceFieldModel.ExtraDirection.DOWN_SOUTH, true)
               .from(7.0F, 0.0F, 9.0F)
               .to(9.0F, 7.0F, 16.0F)
               .parents(ForceFieldModel.ExtraDirection.DOWN, ForceFieldModel.ExtraDirection.SOUTH)
               .shade(false)
               .face(Direction.DOWN)
               .cullface(Direction.DOWN)
               .uvs(7.0F, 9.0F, 9.0F, 16.0F)
               .end()
               .face(Direction.SOUTH)
               .cullface(Direction.SOUTH)
               .uvs(7.0F, 0.0F, 9.0F, 7.0F)
               .end()
               .face(Direction.WEST)
               .uvs(9.0F, 0.0F, 16.0F, 7.0F)
               .end()
               .face(Direction.EAST)
               .uvs(0.0F, 0.0F, 7.0F, 7.0F)
               .end()
               .faces((direction, builder) -> builder.texture("#pane").emissivity(15, 15))
               .ifElse()
               .from(7.0F, 0.0F, 7.0F)
               .to(9.0F, 7.0F, 9.0F)
               .parents(ForceFieldModel.ExtraDirection.DOWN)
               .shade(false)
               .face(Direction.SOUTH)
               .uvs(7.0F, 0.0F, 9.0F, 7.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .ifSame()
               .from(7.0F, 7.0F, 9.0F)
               .to(9.0F, 9.0F, 16.0F)
               .parents(ForceFieldModel.ExtraDirection.SOUTH)
               .shade(false)
               .face(Direction.DOWN)
               .uvs(7.0F, 9.0F, 9.0F, 16.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .end()
               .forceFieldElement()
               .ifState(ForceFieldModel.ExtraDirection.UP_WEST, true)
               .from(0.0F, 9.0F, 7.0F)
               .to(7.0F, 16.0F, 9.0F)
               .parents(ForceFieldModel.ExtraDirection.UP, ForceFieldModel.ExtraDirection.WEST)
               .shade(false)
               .face(Direction.UP)
               .cullface(Direction.UP)
               .uvs(0.0F, 7.0F, 7.0F, 9.0F)
               .end()
               .face(Direction.WEST)
               .cullface(Direction.WEST)
               .uvs(7.0F, 9.0F, 9.0F, 16.0F)
               .end()
               .face(Direction.NORTH)
               .uvs(0.0F, 9.0F, 7.0F, 16.0F)
               .end()
               .face(Direction.SOUTH)
               .uvs(9.0F, 9.0F, 16.0F, 16.0F)
               .end()
               .faces((direction, builder) -> builder.texture("#pane").emissivity(15, 15))
               .ifElse()
               .from(7.0F, 9.0F, 7.0F)
               .to(9.0F, 16.0F, 9.0F)
               .parents(ForceFieldModel.ExtraDirection.UP)
               .shade(false)
               .face(Direction.WEST)
               .uvs(7.0F, 9.0F, 9.0F, 16.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .ifSame()
               .from(0.0F, 7.0F, 7.0F)
               .to(7.0F, 9.0F, 9.0F)
               .parents(ForceFieldModel.ExtraDirection.WEST)
               .shade(false)
               .face(Direction.UP)
               .uvs(0.0F, 7.0F, 7.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .end()
               .forceFieldElement()
               .ifState(ForceFieldModel.ExtraDirection.UP_EAST, true)
               .from(9.0F, 9.0F, 7.0F)
               .to(16.0F, 16.0F, 9.0F)
               .parents(ForceFieldModel.ExtraDirection.UP, ForceFieldModel.ExtraDirection.EAST)
               .shade(false)
               .face(Direction.UP)
               .cullface(Direction.UP)
               .uvs(9.0F, 7.0F, 16.0F, 9.0F)
               .end()
               .face(Direction.EAST)
               .cullface(Direction.EAST)
               .uvs(7.0F, 9.0F, 9.0F, 16.0F)
               .end()
               .face(Direction.NORTH)
               .uvs(9.0F, 9.0F, 16.0F, 16.0F)
               .end()
               .face(Direction.SOUTH)
               .uvs(0.0F, 9.0F, 7.0F, 16.0F)
               .end()
               .faces((direction, builder) -> builder.texture("#pane").emissivity(15, 15))
               .ifElse()
               .from(7.0F, 9.0F, 7.0F)
               .to(9.0F, 16.0F, 9.0F)
               .parents(ForceFieldModel.ExtraDirection.UP)
               .shade(false)
               .face(Direction.EAST)
               .uvs(7.0F, 9.0F, 9.0F, 16.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .ifSame()
               .from(9.0F, 7.0F, 7.0F)
               .to(16.0F, 9.0F, 9.0F)
               .parents(ForceFieldModel.ExtraDirection.EAST)
               .shade(false)
               .face(Direction.UP)
               .uvs(9.0F, 7.0F, 16.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .end()
               .forceFieldElement()
               .ifState(ForceFieldModel.ExtraDirection.UP_NORTH, true)
               .from(7.0F, 9.0F, 0.0F)
               .to(9.0F, 16.0F, 7.0F)
               .parents(ForceFieldModel.ExtraDirection.UP, ForceFieldModel.ExtraDirection.NORTH)
               .shade(false)
               .face(Direction.UP)
               .cullface(Direction.UP)
               .uvs(7.0F, 0.0F, 9.0F, 7.0F)
               .end()
               .face(Direction.NORTH)
               .cullface(Direction.NORTH)
               .uvs(7.0F, 9.0F, 9.0F, 16.0F)
               .end()
               .face(Direction.WEST)
               .uvs(0.0F, 9.0F, 7.0F, 16.0F)
               .end()
               .face(Direction.EAST)
               .uvs(9.0F, 9.0F, 16.0F, 16.0F)
               .end()
               .faces((direction, builder) -> builder.texture("#pane").emissivity(15, 15))
               .ifElse()
               .from(7.0F, 9.0F, 7.0F)
               .to(9.0F, 16.0F, 9.0F)
               .parents(ForceFieldModel.ExtraDirection.UP)
               .shade(false)
               .face(Direction.NORTH)
               .uvs(7.0F, 9.0F, 9.0F, 16.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .ifSame()
               .from(7.0F, 7.0F, 0.0F)
               .to(9.0F, 9.0F, 7.0F)
               .parents(ForceFieldModel.ExtraDirection.NORTH)
               .shade(false)
               .face(Direction.UP)
               .uvs(7.0F, 0.0F, 9.0F, 7.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .end()
               .forceFieldElement()
               .ifState(ForceFieldModel.ExtraDirection.UP_SOUTH, true)
               .from(7.0F, 9.0F, 9.0F)
               .to(9.0F, 16.0F, 16.0F)
               .parents(ForceFieldModel.ExtraDirection.UP, ForceFieldModel.ExtraDirection.SOUTH)
               .shade(false)
               .face(Direction.UP)
               .cullface(Direction.UP)
               .uvs(7.0F, 9.0F, 9.0F, 16.0F)
               .end()
               .face(Direction.SOUTH)
               .cullface(Direction.SOUTH)
               .uvs(7.0F, 9.0F, 9.0F, 16.0F)
               .end()
               .face(Direction.WEST)
               .uvs(9.0F, 9.0F, 16.0F, 16.0F)
               .end()
               .face(Direction.EAST)
               .uvs(0.0F, 9.0F, 7.0F, 16.0F)
               .end()
               .faces((direction, builder) -> builder.texture("#pane").emissivity(15, 15))
               .ifElse()
               .from(7.0F, 9.0F, 7.0F)
               .to(9.0F, 16.0F, 9.0F)
               .parents(ForceFieldModel.ExtraDirection.UP)
               .shade(false)
               .face(Direction.SOUTH)
               .uvs(7.0F, 9.0F, 9.0F, 16.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .ifSame()
               .from(7.0F, 7.0F, 9.0F)
               .to(9.0F, 9.0F, 16.0F)
               .parents(ForceFieldModel.ExtraDirection.SOUTH)
               .shade(false)
               .face(Direction.UP)
               .uvs(7.0F, 9.0F, 9.0F, 16.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .end()
               .forceFieldElement()
               .ifState(ForceFieldModel.ExtraDirection.NORTH_WEST, true)
               .from(0.0F, 7.0F, 0.0F)
               .to(7.0F, 9.0F, 7.0F)
               .parents(ForceFieldModel.ExtraDirection.NORTH, ForceFieldModel.ExtraDirection.WEST)
               .shade(false)
               .face(Direction.NORTH)
               .cullface(Direction.NORTH)
               .uvs(0.0F, 7.0F, 7.0F, 9.0F)
               .end()
               .face(Direction.WEST)
               .cullface(Direction.WEST)
               .uvs(9.0F, 7.0F, 16.0F, 9.0F)
               .end()
               .face(Direction.DOWN)
               .uvs(0.0F, 9.0F, 7.0F, 16.0F)
               .end()
               .face(Direction.UP)
               .uvs(9.0F, 9.0F, 16.0F, 16.0F)
               .end()
               .faces((direction, builder) -> builder.texture("#pane").emissivity(15, 15))
               .ifElse()
               .from(7.0F, 7.0F, 0.0F)
               .to(9.0F, 9.0F, 7.0F)
               .parents(ForceFieldModel.ExtraDirection.NORTH)
               .shade(false)
               .face(Direction.WEST)
               .uvs(9.0F, 7.0F, 16.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .ifSame()
               .from(0.0F, 7.0F, 7.0F)
               .to(7.0F, 9.0F, 9.0F)
               .parents(ForceFieldModel.ExtraDirection.WEST)
               .shade(false)
               .face(Direction.NORTH)
               .uvs(0.0F, 7.0F, 7.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .end()
               .forceFieldElement()
               .ifState(ForceFieldModel.ExtraDirection.NORTH_EAST, true)
               .from(9.0F, 7.0F, 0.0F)
               .to(16.0F, 9.0F, 7.0F)
               .parents(ForceFieldModel.ExtraDirection.NORTH, ForceFieldModel.ExtraDirection.EAST)
               .shade(false)
               .face(Direction.NORTH)
               .cullface(Direction.NORTH)
               .uvs(9.0F, 7.0F, 16.0F, 9.0F)
               .end()
               .face(Direction.EAST)
               .cullface(Direction.EAST)
               .uvs(0.0F, 7.0F, 7.0F, 9.0F)
               .end()
               .face(Direction.DOWN)
               .uvs(9.0F, 9.0F, 16.0F, 16.0F)
               .end()
               .face(Direction.UP)
               .uvs(0.0F, 9.0F, 7.0F, 16.0F)
               .end()
               .faces((direction, builder) -> builder.texture("#pane").emissivity(15, 15))
               .ifElse()
               .from(7.0F, 7.0F, 0.0F)
               .to(9.0F, 9.0F, 7.0F)
               .parents(ForceFieldModel.ExtraDirection.NORTH)
               .shade(false)
               .face(Direction.EAST)
               .uvs(0.0F, 7.0F, 7.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .ifSame()
               .from(9.0F, 7.0F, 7.0F)
               .to(16.0F, 9.0F, 9.0F)
               .parents(ForceFieldModel.ExtraDirection.EAST)
               .shade(false)
               .face(Direction.NORTH)
               .uvs(9.0F, 7.0F, 16.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .end()
               .forceFieldElement()
               .ifState(ForceFieldModel.ExtraDirection.SOUTH_WEST, true)
               .from(0.0F, 7.0F, 9.0F)
               .to(7.0F, 9.0F, 16.0F)
               .parents(ForceFieldModel.ExtraDirection.SOUTH, ForceFieldModel.ExtraDirection.WEST)
               .shade(false)
               .face(Direction.SOUTH)
               .cullface(Direction.SOUTH)
               .uvs(0.0F, 7.0F, 7.0F, 9.0F)
               .end()
               .face(Direction.WEST)
               .cullface(Direction.WEST)
               .uvs(9.0F, 7.0F, 16.0F, 9.0F)
               .end()
               .face(Direction.DOWN)
               .uvs(0.0F, 9.0F, 7.0F, 16.0F)
               .end()
               .face(Direction.UP)
               .uvs(9.0F, 9.0F, 16.0F, 16.0F)
               .end()
               .faces((direction, builder) -> builder.texture("#pane").emissivity(15, 15))
               .ifElse()
               .from(7.0F, 7.0F, 9.0F)
               .to(9.0F, 9.0F, 16.0F)
               .parents(ForceFieldModel.ExtraDirection.SOUTH)
               .shade(false)
               .face(Direction.WEST)
               .uvs(9.0F, 7.0F, 16.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .ifSame()
               .from(0.0F, 7.0F, 7.0F)
               .to(7.0F, 9.0F, 9.0F)
               .parents(ForceFieldModel.ExtraDirection.WEST)
               .shade(false)
               .face(Direction.SOUTH)
               .uvs(0.0F, 7.0F, 7.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .end()
               .forceFieldElement()
               .ifState(ForceFieldModel.ExtraDirection.SOUTH_EAST, true)
               .from(9.0F, 7.0F, 9.0F)
               .to(16.0F, 9.0F, 16.0F)
               .parents(ForceFieldModel.ExtraDirection.SOUTH, ForceFieldModel.ExtraDirection.EAST)
               .shade(false)
               .face(Direction.SOUTH)
               .cullface(Direction.SOUTH)
               .uvs(0.0F, 7.0F, 7.0F, 9.0F)
               .end()
               .face(Direction.EAST)
               .cullface(Direction.EAST)
               .uvs(9.0F, 7.0F, 16.0F, 9.0F)
               .end()
               .face(Direction.DOWN)
               .uvs(9.0F, 9.0F, 16.0F, 16.0F)
               .end()
               .face(Direction.UP)
               .uvs(0.0F, 9.0F, 7.0F, 16.0F)
               .end()
               .faces((direction, builder) -> builder.texture("#pane").emissivity(15, 15))
               .ifElse()
               .from(7.0F, 7.0F, 9.0F)
               .to(9.0F, 9.0F, 16.0F)
               .parents(ForceFieldModel.ExtraDirection.SOUTH)
               .shade(false)
               .face(Direction.EAST)
               .uvs(9.0F, 7.0F, 16.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .ifSame()
               .from(9.0F, 7.0F, 7.0F)
               .to(16.0F, 9.0F, 9.0F)
               .parents(ForceFieldModel.ExtraDirection.EAST)
               .shade(false)
               .face(Direction.SOUTH)
               .uvs(0.0F, 7.0F, 7.0F, 9.0F)
               .texture("#pane")
               .emissivity(15, 15)
               .end()
               .end()
               .end()
         );
      }
   }

   private void registerNagastone() {
      String baseName = TFBlocks.NAGASTONE.getId().getPath();
      ModelFile solid = this.models()
         .cubeBottomTop(
            baseName,
            TwilightForestMod.prefix("block/nagastone_long_side"),
            TwilightForestMod.prefix("block/nagastone_bottom_long"),
            TwilightForestMod.prefix("block/nagastone_turn_top")
         );
      ModelFile down = this.models().getExistingFile(TwilightForestMod.prefix("block/naga_segment/down"));
      ModelFile up = this.models().getExistingFile(TwilightForestMod.prefix("block/naga_segment/up"));
      ModelFile horizontal = this.models().getExistingFile(TwilightForestMod.prefix("block/naga_segment/horizontal"));
      ModelFile vertical = this.models().getExistingFile(TwilightForestMod.prefix("block/naga_segment/vertical"));
      this.getVariantBuilder((Block)TFBlocks.NAGASTONE.get()).forAllStates(s -> {
         return switch ((NagastoneVariant)s.getValue(NagastoneBlock.VARIANT)) {
            case NORTH_DOWN -> ConfiguredModel.builder().modelFile(down).rotationY(270).build();
            case SOUTH_DOWN -> ConfiguredModel.builder().modelFile(down).rotationY(90).build();
            case WEST_DOWN -> ConfiguredModel.builder().modelFile(down).rotationY(180).build();
            case EAST_DOWN -> ConfiguredModel.builder().modelFile(down).build();
            case NORTH_UP -> ConfiguredModel.builder().modelFile(up).rotationY(270).build();
            case SOUTH_UP -> ConfiguredModel.builder().modelFile(up).rotationY(90).build();
            case EAST_UP -> ConfiguredModel.builder().modelFile(up).build();
            case WEST_UP -> ConfiguredModel.builder().modelFile(up).rotationY(180).build();
            case AXIS_X -> ConfiguredModel.builder().modelFile(horizontal).build();
            case AXIS_Y -> ConfiguredModel.builder().modelFile(vertical).build();
            case AXIS_Z -> ConfiguredModel.builder().modelFile(horizontal).rotationY(90).build();
            case SOLID -> ConfiguredModel.builder().modelFile(solid).build();
         };
      });
      this.horizontalBlock(
         (Block)TFBlocks.NAGASTONE_HEAD.get(), this.models().getExistingFile(TwilightForestMod.prefix("block/" + TFBlocks.NAGASTONE_HEAD.getId().getPath()))
      );
      this.nagastonePillar((Block)TFBlocks.NAGASTONE_PILLAR.get(), "");
      this.nagastonePillar((Block)TFBlocks.MOSSY_NAGASTONE_PILLAR.get(), "_mossy");
      this.nagastonePillar((Block)TFBlocks.CRACKED_NAGASTONE_PILLAR.get(), "_weathered");
      this.etchedNagastone((Block)TFBlocks.ETCHED_NAGASTONE.get(), "");
      this.etchedNagastone((Block)TFBlocks.MOSSY_ETCHED_NAGASTONE.get(), "_mossy");
      this.etchedNagastone((Block)TFBlocks.CRACKED_ETCHED_NAGASTONE.get(), "_weathered");
      this.bisectedStairsBlock(
         TFBlocks.NAGASTONE_STAIRS_LEFT,
         TwilightForestMod.prefix("block/etched_nagastone_left"),
         TwilightForestMod.prefix("block/stone_tiles"),
         TwilightForestMod.prefix("block/nagastone_bare")
      );
      this.bisectedStairsBlock(
         TFBlocks.NAGASTONE_STAIRS_RIGHT,
         TwilightForestMod.prefix("block/etched_nagastone_right"),
         TwilightForestMod.prefix("block/stone_tiles"),
         TwilightForestMod.prefix("block/nagastone_bare")
      );
      this.bisectedStairsBlock(
         TFBlocks.MOSSY_NAGASTONE_STAIRS_LEFT,
         TwilightForestMod.prefix("block/etched_nagastone_left_mossy"),
         TwilightForestMod.prefix("block/stone_tiles_mossy"),
         TwilightForestMod.prefix("block/nagastone_bare_mossy")
      );
      this.bisectedStairsBlock(
         TFBlocks.MOSSY_NAGASTONE_STAIRS_RIGHT,
         TwilightForestMod.prefix("block/etched_nagastone_right_mossy"),
         TwilightForestMod.prefix("block/stone_tiles_mossy"),
         TwilightForestMod.prefix("block/nagastone_bare_mossy")
      );
      this.bisectedStairsBlock(
         TFBlocks.CRACKED_NAGASTONE_STAIRS_LEFT,
         TwilightForestMod.prefix("block/etched_nagastone_left_weathered"),
         TwilightForestMod.prefix("block/stone_tiles_weathered"),
         TwilightForestMod.prefix("block/nagastone_bare_weathered")
      );
      this.bisectedStairsBlock(
         TFBlocks.CRACKED_NAGASTONE_STAIRS_RIGHT,
         TwilightForestMod.prefix("block/etched_nagastone_right_weathered"),
         TwilightForestMod.prefix("block/stone_tiles_weathered"),
         TwilightForestMod.prefix("block/nagastone_bare_weathered")
      );
   }

   private void nagastonePillar(Block b, String suffix) {
      ResourceLocation side = TwilightForestMod.prefix("block/nagastone_pillar_side" + suffix);
      ResourceLocation end = TwilightForestMod.prefix("block/nagastone_pillar_end" + suffix);
      ResourceLocation alt = TwilightForestMod.prefix("block/nagastone_pillar_side" + suffix + "_alt");
      ModelFile model = this.models().cubeColumn(this.name(b), side, end);
      ModelFile reversed = this.models().cubeColumn(this.name(b) + "_reversed", alt, end);
      this.getVariantBuilder(b).forAllStates(state -> {
         int rotX = 0;
         int rotY = 0;
         switch ((Axis)state.getValue(RotatedPillarBlock.AXIS)) {
            case X:
            default:
               rotY = 270;
               rotX = 270;
               break;
            case Z:
               rotX = 270;
            case Y:
         }

         ModelFile m = state.getValue(DirectionalRotatedPillarBlock.REVERSED) ? reversed : model;
         return ConfiguredModel.builder().rotationX(rotX).rotationY(rotY).modelFile(m).build();
      });
   }

   private void etchedNagastone(Block b, String suffix) {
      ResourceLocation stoneTiles = TwilightForestMod.prefix("block/stone_tiles" + suffix);
      ResourceLocation upTex = TwilightForestMod.prefix("block/etched_nagastone_up" + suffix);
      ResourceLocation downTex = TwilightForestMod.prefix("block/etched_nagastone_down" + suffix);
      ResourceLocation rightTex = TwilightForestMod.prefix("block/etched_nagastone_right" + suffix);
      ResourceLocation leftTex = TwilightForestMod.prefix("block/etched_nagastone_left" + suffix);
      ModelFile down = this.models().cubeColumn(this.name(b), downTex, stoneTiles);
      ModelFile up = this.models().cubeColumn(this.name(b) + "_up", upTex, stoneTiles);
      ModelFile north = ((BlockModelBuilder)this.models().cube(this.name(b) + "_north", upTex, upTex, stoneTiles, stoneTiles, rightTex, leftTex))
         .texture("particle", "#down");
      ModelFile south = ((BlockModelBuilder)this.models().cube(this.name(b) + "_south", downTex, downTex, stoneTiles, stoneTiles, leftTex, rightTex))
         .texture("particle", "#down");
      ModelFile west = ((BlockModelBuilder)this.models().cube(this.name(b) + "_west", leftTex, rightTex, rightTex, leftTex, stoneTiles, stoneTiles))
         .texture("particle", "#down");
      ModelFile east = ((BlockModelBuilder)this.models().cube(this.name(b) + "_east", rightTex, leftTex, leftTex, rightTex, stoneTiles, stoneTiles))
         .texture("particle", "#down");
      this.getVariantBuilder(b).partialState().with(DirectionalBlock.FACING, Direction.DOWN).setModels(new ConfiguredModel[]{new ConfiguredModel(down)});
      this.getVariantBuilder(b).partialState().with(DirectionalBlock.FACING, Direction.UP).setModels(new ConfiguredModel[]{new ConfiguredModel(up)});
      this.getVariantBuilder(b).partialState().with(DirectionalBlock.FACING, Direction.NORTH).setModels(new ConfiguredModel[]{new ConfiguredModel(north)});
      this.getVariantBuilder(b).partialState().with(DirectionalBlock.FACING, Direction.SOUTH).setModels(new ConfiguredModel[]{new ConfiguredModel(south)});
      this.getVariantBuilder(b).partialState().with(DirectionalBlock.FACING, Direction.WEST).setModels(new ConfiguredModel[]{new ConfiguredModel(west)});
      this.getVariantBuilder(b).partialState().with(DirectionalBlock.FACING, Direction.EAST).setModels(new ConfiguredModel[]{new ConfiguredModel(east)});
   }

   private void casketStuff(Block block) {
      VariantBlockStateBuilder builder = this.getVariantBuilder(block);
      BlockModelBuilder empty = (BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models().getBuilder(this.name(block)))
            .parent(new UncheckedModelFile("builtin/entity")))
         .texture("particle", "minecraft:block/netherite_block");
      BlockModelBuilder obsidian = (BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
               .withExistingParent("casket_obsidian", TwilightForestMod.prefix("block/casket_solid_template")))
            .texture("top", ResourceLocation.withDefaultNamespace("block/obsidian")))
         .texture("side", ResourceLocation.withDefaultNamespace("block/obsidian"));
      BlockModelBuilder stone = (BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
               .withExistingParent("casket_stone", TwilightForestMod.prefix("block/casket_solid_template")))
            .texture("top", ResourceLocation.withDefaultNamespace("block/stone")))
         .texture("side", ResourceLocation.withDefaultNamespace("block/stone"));
      BlockModelBuilder basalt = (BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
               .withExistingParent("casket_basalt", TwilightForestMod.prefix("block/casket_solid_template")))
            .texture("top", ResourceLocation.withDefaultNamespace("block/basalt_top")))
         .texture("side", ResourceLocation.withDefaultNamespace("block/basalt_side"));
      builder.partialState().with(BlockLoggingEnum.MULTILOGGED, BlockLoggingEnum.AIR).setModels(new ConfiguredModel[]{new ConfiguredModel(empty)});
      builder.partialState().with(BlockLoggingEnum.MULTILOGGED, BlockLoggingEnum.WATER).setModels(new ConfiguredModel[]{new ConfiguredModel(empty)});
      builder.partialState().with(BlockLoggingEnum.MULTILOGGED, BlockLoggingEnum.LAVA).setModels(new ConfiguredModel[]{new ConfiguredModel(empty)});
      builder.partialState().with(BlockLoggingEnum.MULTILOGGED, BlockLoggingEnum.OBSIDIAN).setModels(new ConfiguredModel[]{new ConfiguredModel(obsidian)});
      builder.partialState().with(BlockLoggingEnum.MULTILOGGED, BlockLoggingEnum.STONE).setModels(new ConfiguredModel[]{new ConfiguredModel(stone)});
      builder.partialState().with(BlockLoggingEnum.MULTILOGGED, BlockLoggingEnum.BASALT).setModels(new ConfiguredModel[]{new ConfiguredModel(basalt)});
   }

   private void registerSmokersAndJets() {
      this.simpleBlock(
         (Block)TFBlocks.SMOKER.get(),
         ((BlockModelBuilder)((BlockModelBuilder)this.makeTintedBlockColumnUniqueBottom(TFBlocks.SMOKER.getId().getPath())
                  .texture("side", TwilightForestMod.prefix("block/firejet_side")))
               .texture("top", TwilightForestMod.prefix("block/firejet_top")))
            .texture("bottom", TextureMapping.getBlockTexture(Blocks.BLACK_CONCRETE_POWDER))
      );
      this.simpleBlock(
         (Block)TFBlocks.FIRE_JET.get(),
         ((BlockModelBuilder)((BlockModelBuilder)this.makeTintedBlockColumnUniqueBottom(TFBlocks.FIRE_JET.getId().getPath())
                  .texture("side", TwilightForestMod.prefix("block/firejet_side")))
               .texture("top", TwilightForestMod.prefix("block/firejet_top")))
            .texture("bottom", TextureMapping.getBlockTexture(Blocks.BLACK_CONCRETE_POWDER))
      );
      ModelFile smokerOff = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.make3LayerCubeIdenticalSides1Bottom(
                              TFBlocks.ENCASED_SMOKER.getId().getPath(), 0, 10, 15, 10, 10
                           )
                           .texture("top", TwilightForestMod.prefix("block/towerdev_ghasttraplid_off")))
                        .texture("side", TwilightForestMod.prefix("block/towerdev_smoker_off")))
                     .texture("bottom", this.blockTexture((Block)TFBlocks.ENCASED_TOWERWOOD.get())))
                  .texture("top2", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_ghasttraplid_off_1")))
               .texture("side2", TwilightForestMod.prefix("block/tower_device_level_1/towerdev_smoker_1")))
            .texture("top3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_ghasttraplid_off_1")))
         .texture("side3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_smoker_off_1"));
      ModelFile smokerOn = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.make3LayerCubeIdenticalSides1Bottom(
                              TFBlocks.ENCASED_SMOKER.getId().getPath() + "_on", 0, 10, 15, 7, 10
                           )
                           .texture("top", TwilightForestMod.prefix("block/towerdev_ghasttraplid_on")))
                        .texture("side", TwilightForestMod.prefix("block/towerdev_firejet_on")))
                     .texture("bottom", this.blockTexture((Block)TFBlocks.ENCASED_TOWERWOOD.get())))
                  .texture("top2", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_ghasttraplid_on_1")))
               .texture("side2", TwilightForestMod.prefix("block/tower_device_level_1/towerdev_smoker_1")))
            .texture("top3", TwilightForestMod.prefix("block/tower_device_level_3/towerdev_ghasttraplid_on_2")))
         .texture("side3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_smoker_on_1"));
      this.getVariantBuilder((Block)TFBlocks.ENCASED_SMOKER.get())
         .partialState()
         .with(EncasedSmokerBlock.ACTIVE, false)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(smokerOff)});
      this.getVariantBuilder((Block)TFBlocks.ENCASED_SMOKER.get())
         .partialState()
         .with(EncasedSmokerBlock.ACTIVE, true)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(smokerOn)});
      ModelFile encasedJetOff = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.make3LayerCubeIdenticalSides1Bottom(
                              TFBlocks.ENCASED_FIRE_JET.getId().getPath(), 0, 10, 15, 10, 10
                           )
                           .texture("top", TwilightForestMod.prefix("block/towerdev_ghasttraplid_off")))
                        .texture("side", TwilightForestMod.prefix("block/towerdev_firejet_off")))
                     .texture("bottom", this.blockTexture((Block)TFBlocks.ENCASED_TOWERWOOD.get())))
                  .texture("top2", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_ghasttraplid_off_1")))
               .texture("side2", TwilightForestMod.prefix("block/tower_device_level_1/towerdev_firejet_1")))
            .texture("top3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_ghasttraplid_off_1")))
         .texture("side3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_firejet_off_1"));
      ModelFile encasedJetOn = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.make3LayerCubeIdenticalSides1Bottom(
                              TFBlocks.ENCASED_FIRE_JET.getId().getPath() + "_on", 0, 10, 15, 7, 10
                           )
                           .texture("top", TwilightForestMod.prefix("block/towerdev_ghasttraplid_on")))
                        .texture("side", TwilightForestMod.prefix("block/towerdev_firejet_on")))
                     .texture("bottom", this.blockTexture((Block)TFBlocks.ENCASED_TOWERWOOD.get())))
                  .texture("top2", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_ghasttraplid_on_1")))
               .texture("side2", TwilightForestMod.prefix("block/tower_device_level_1/towerdev_firejet_1")))
            .texture("top3", TwilightForestMod.prefix("block/tower_device_level_3/towerdev_ghasttraplid_on_2")))
         .texture("side3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_firejet_on_1"));
      this.getVariantBuilder((Block)TFBlocks.ENCASED_FIRE_JET.get())
         .partialState()
         .with(FireJetBlock.STATE, FireJetVariant.IDLE)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(encasedJetOff)});
      this.getVariantBuilder((Block)TFBlocks.ENCASED_FIRE_JET.get())
         .partialState()
         .with(FireJetBlock.STATE, FireJetVariant.TIMEOUT)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(encasedJetOff)});
      this.getVariantBuilder((Block)TFBlocks.ENCASED_FIRE_JET.get())
         .partialState()
         .with(FireJetBlock.STATE, FireJetVariant.POPPING)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(encasedJetOn)});
      this.getVariantBuilder((Block)TFBlocks.ENCASED_FIRE_JET.get())
         .partialState()
         .with(FireJetBlock.STATE, FireJetVariant.FLAME)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(encasedJetOn)});
   }

   private void registerBush(Block block) {
      this.registerBush(block, 0, 0);
   }

   private void registerBush(Block block, int blockLight, int skyLight) {
      String blockName = this.name(block);
      ResourceLocation baseTexture = TwilightForestMod.prefix("block/" + blockName);
      ResourceLocation ripeTexture = TwilightForestMod.prefix("block/" + blockName + "_ripe");
      ModelFile[][] bushModels;
      if (blockLight == 0 && skyLight == 0) {
         bushModels = this.createBushStatesWithoutEmissivity(blockName, baseTexture, ripeTexture);
      } else {
         bushModels = this.createBushStates(blockName, blockLight, skyLight, baseTexture, ripeTexture);
      }

      this.getVariantBuilder(block).forAllStates(state -> {
         int age = (Integer)state.getValue(BlockStateProperties.AGE_3);
         int snowLayers = (Integer)state.getValue(SnowLoggable.SNOW_LAYERS);
         return new ConfiguredModel[]{new ConfiguredModel(bushModels[age][snowLayers], 0, 0, false)};
      });
   }

   private ModelFile[][] createBushStatesWithoutEmissivity(String blockName, ResourceLocation baseTexture, ResourceLocation ripeTexture) {
      ModelFile[][] bushModels = new ModelFile[4][9];

      for (int age = 0; age <= 3; age++) {
         ResourceLocation texture = age == 3 ? ripeTexture : baseTexture;

         for (int snowLayers = 0; snowLayers <= 8; snowLayers++) {
            bushModels[age][snowLayers] = ((BlockModelBuilder)((BlockModelBuilder)this.models()
                     .getBuilder("block/" + blockName + this.getSuffix(age, snowLayers)))
                  .parent(this.getAbstractBushesStates()[age][snowLayers]))
               .texture("all", texture);
         }
      }

      return bushModels;
   }

   private ModelFile[][] createBushStates(String blockName, int blockLight, int skyLight, ResourceLocation baseTexture, ResourceLocation ripeTexture) {
      ModelFile[][] bushModels = new ModelFile[4][9];

      for (int age = 0; age <= 3; age++) {
         ResourceLocation texture = age == 3 ? ripeTexture : baseTexture;

         for (int snowLayers = 0; snowLayers <= 8; snowLayers++) {
            bushModels[age][snowLayers] = this.createBushModel(blockName, age, snowLayers, texture, blockLight, skyLight);
         }
      }

      return bushModels;
   }

   private ModelFile createBushModel(String bushName, int age, int snowLayers, ResourceLocation texturePath, int blockLight, int skyLight) {
      String modelName = bushName + this.getSuffix(age, snowLayers);
      float snowHeight = 2.0F * snowLayers;
      ModelBuilder<BlockModelBuilder> builder = this.models().cubeAll(modelName, texturePath);
      this.addBushElements(builder, age, snowHeight, blockLight, skyLight);
      if (snowLayers > 0) {
         builder.texture("snow", ResourceLocation.withDefaultNamespace("block/snow"));
         this.addSnowElements(builder, age, snowHeight);
      }

      return builder.renderType(CUTOUT);
   }

   private void addBushElements(ModelBuilder<BlockModelBuilder> b, int age, float snowHeight, int blockLight, int skyLight) {
      switch (age) {
         case 0:
            this.addShrunkBox(
               b, 4.0F, 0.0F, 4.0F, 12.0F, 8.0F, 12.0F, snowHeight, blockLight, skyLight, (direction, faceBuilder) -> faceBuilder.texture("#all")
            );
            break;
         case 1:
            this.addShrunkBox(
               b, 2.0F, 0.0F, 2.0F, 14.0F, 12.0F, 14.0F, snowHeight, blockLight, skyLight, (direction, faceBuilder) -> faceBuilder.texture("#all")
            );
            break;
         case 2:
            this.addShrunkBox(
               b,
               0.0F,
               0.0F,
               0.0F,
               16.0F,
               16.0F,
               16.0F,
               snowHeight,
               blockLight,
               skyLight,
               (direction, faceBuilder) -> faceBuilder.cullface(direction).texture("#all")
            );
            break;
         case 3:
            this.addShrunkBox(
               b,
               0.0F,
               0.0F,
               0.0F,
               16.0F,
               16.0F,
               16.0F,
               snowHeight,
               blockLight,
               skyLight,
               (direction, faceBuilder) -> faceBuilder.cullface(direction).texture("#all")
            );
            break;
         default:
            throw new IllegalArgumentException("Age out of range: " + age);
      }
   }

   private void addSnowElements(ModelBuilder<BlockModelBuilder> b, int age, float snowHeight) {
      switch (age) {
         case 0:
            this.addSnowCap(b, 4.0F, 8.0F, 4.0F, 12.0F, 10.0F, 12.0F);
            break;
         case 1:
            this.addSnowCap(b, 2.0F, 12.0F, 2.0F, 14.0F, 14.0F, 14.0F);
      }

      this.addSnowCover(b, snowHeight);
   }

   private void addShrunkBox(
      ModelBuilder<BlockModelBuilder> b,
      float x1,
      float y1,
      float z1,
      float x2,
      float y2,
      float z2,
      float snowHeight,
      int blockLight,
      int skyLight,
      BiConsumer<Direction, ModelBuilder<BlockModelBuilder>.ElementBuilder.FaceBuilder> faces
   ) {
      if (!(snowHeight > y2 - 0.008F)) {
         b.element().from(x1, Math.max(y1, snowHeight + 0.008F), z1).to(x2, y2, z2).allFaces(faces).emissivity(blockLight, skyLight).end();
      }
   }

   private void addSnowCap(ModelBuilder<BlockModelBuilder> b, float x1, float y1, float z1, float x2, float y2, float z2) {
      b.element().from(x1, y1, z1).to(x2, y2, z2).allFaces((d, f) -> f.texture("#snow")).end();
   }

   private void addSnowCover(ModelBuilder<BlockModelBuilder> b, float snowHeight) {
      b.element().from(0.0F, 0.0F, 0.0F).to(16.0F, snowHeight, 16.0F).allFaces((d, f) -> f.texture("#snow")).end();
   }

   private ModelFile[][] getAbstractBushesStates() {
      if (abstractBushStates == null) {
         ResourceLocation defaultAbstractBushTexture = ResourceLocation.withDefaultNamespace("block/bedrock");
         abstractBushStates = this.createBushStates("abstract_bush", 0, 0, defaultAbstractBushTexture, defaultAbstractBushTexture);
      }

      return abstractBushStates;
   }

   private String getSuffix(int age, int snowLayer) {
      return switch (age) {
         case 0 -> "_small_" + snowLayer;
         case 1 -> "_" + snowLayer;
         case 2 -> "_large_" + snowLayer;
         case 3 -> "_grown_" + snowLayer;
         default -> throw new IllegalArgumentException("Age out of range: " + age);
      };
   }

   private void registerPlantBlocks() {
      this.simpleBlock(
         (Block)TFBlocks.MOSS_PATCH.get(), new ConfiguredModel[]{new ConfiguredModel(new UncheckedModelFile(TwilightForestMod.prefix("block/moss_patch")))}
      );
      this.simpleBlockExisting((Block)TFBlocks.MAYAPPLE.get());
      this.simpleBlock(
         (Block)TFBlocks.CLOVER_PATCH.get(), new ConfiguredModel[]{new ConfiguredModel(new UncheckedModelFile(TwilightForestMod.prefix("block/clover_patch")))}
      );
      this.simpleBlock(
         (Block)TFBlocks.FIDDLEHEAD.get(),
         ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(TFBlocks.FIDDLEHEAD.getId().getPath(), "block/tinted_cross"))
               .renderType(CUTOUT))
            .texture("cross", this.blockTexture((Block)TFBlocks.FIDDLEHEAD.get()))
      );
      this.simpleBlock(
         (Block)TFBlocks.MUSHGLOOM.get(),
         ((BlockModelBuilder)this.make2layerCross(TFBlocks.MUSHGLOOM.getId().getPath(), CUTOUT, 10, 6)
               .texture("cross", this.blockTexture((Block)TFBlocks.MUSHGLOOM.get())))
            .texture("cross2", TwilightForestMod.prefix("block/" + TFBlocks.MUSHGLOOM.getId().getPath() + "_head"))
      );
      ModelFile berry = ((BlockModelBuilder)this.make2layerCross(TFBlocks.TORCHBERRY_PLANT.getId().getPath(), CUTOUT, 0, 15)
            .texture("cross", this.blockTexture((Block)TFBlocks.TORCHBERRY_PLANT.get())))
         .texture("cross2", TwilightForestMod.prefix("block/" + TFBlocks.TORCHBERRY_PLANT.getId().getPath() + "_glow"));
      ModelFile noBerry = ((BlockModelBuilder)((BlockModelBuilder)this.models()
               .withExistingParent(TFBlocks.TORCHBERRY_PLANT.getId().getPath() + "_no_berries", ResourceLocation.withDefaultNamespace("block/cross")))
            .renderType(CUTOUT))
         .texture("cross", this.blockTexture((Block)TFBlocks.TORCHBERRY_PLANT.get()));
      this.getVariantBuilder((Block)TFBlocks.TORCHBERRY_PLANT.get())
         .forAllStates(s -> ConfiguredModel.builder().modelFile(s.getValue(TorchberryPlantBlock.HAS_BERRIES) ? berry : noBerry).build());
      this.simpleBlockExisting((Block)TFBlocks.ROOT_STRAND.get());
      this.getVariantBuilder((Block)TFBlocks.FALLEN_LEAVES.get())
         .partialState()
         .with(FallenLeavesBlock.LAYERS, 1)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(this.buildFallenLeaves(1))});
      this.getVariantBuilder((Block)TFBlocks.FALLEN_LEAVES.get())
         .partialState()
         .with(FallenLeavesBlock.LAYERS, 2)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(this.buildFallenLeaves(2))});
      this.getVariantBuilder((Block)TFBlocks.FALLEN_LEAVES.get())
         .partialState()
         .with(FallenLeavesBlock.LAYERS, 3)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(this.buildFallenLeaves(3))});
      this.getVariantBuilder((Block)TFBlocks.FALLEN_LEAVES.get())
         .partialState()
         .with(FallenLeavesBlock.LAYERS, 4)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(this.buildFallenLeaves(4))});
      this.getVariantBuilder((Block)TFBlocks.FALLEN_LEAVES.get())
         .partialState()
         .with(FallenLeavesBlock.LAYERS, 5)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(this.buildFallenLeaves(5))});
      this.getVariantBuilder((Block)TFBlocks.FALLEN_LEAVES.get())
         .partialState()
         .with(FallenLeavesBlock.LAYERS, 6)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(this.buildFallenLeaves(6))});
      this.getVariantBuilder((Block)TFBlocks.FALLEN_LEAVES.get())
         .partialState()
         .with(FallenLeavesBlock.LAYERS, 7)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(this.buildFallenLeaves(7))});
      this.getVariantBuilder((Block)TFBlocks.FALLEN_LEAVES.get())
         .partialState()
         .with(FallenLeavesBlock.LAYERS, 8)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(this.buildFallenLeaves(8))});
   }

   private void registerWoodBlocks() {
      this.logWoodSapling(
         (RotatedPillarBlock)TFBlocks.TWILIGHT_OAK_LOG.get(),
         (RotatedPillarBlock)TFBlocks.STRIPPED_TWILIGHT_OAK_LOG.get(),
         (RotatedPillarBlock)TFBlocks.TWILIGHT_OAK_WOOD.get(),
         (RotatedPillarBlock)TFBlocks.STRIPPED_TWILIGHT_OAK_WOOD.get(),
         (Block)TFBlocks.TWILIGHT_OAK_SAPLING.get()
      );
      this.plankBlocks(
         "twilight_oak",
         (Block)TFBlocks.TWILIGHT_OAK_PLANKS.get(),
         (Block)TFBlocks.TWILIGHT_OAK_SLAB.get(),
         (StairBlock)TFBlocks.TWILIGHT_OAK_STAIRS.get(),
         (Block)TFBlocks.TWILIGHT_OAK_BUTTON.get(),
         (Block)TFBlocks.TWILIGHT_OAK_FENCE.get(),
         (Block)TFBlocks.TWILIGHT_OAK_GATE.get(),
         (Block)TFBlocks.TWILIGHT_OAK_PLATE.get(),
         (DoorBlock)TFBlocks.TWILIGHT_OAK_DOOR.get(),
         (TrapDoorBlock)TFBlocks.TWILIGHT_OAK_TRAPDOOR.get(),
         (BanisterBlock)TFBlocks.TWILIGHT_OAK_BANISTER.get(),
         (DryingRackBlock)TFBlocks.TWILIGHT_OAK_DRYING_RACK.get()
      );
      this.singleBlockBoilerPlate((Block)TFBlocks.TWILIGHT_OAK_LEAVES.get(), "block/leaves", m -> m.texture("all", "minecraft:block/oak_leaves"));
      ResourceLocation rainboakSaplTex = TwilightForestMod.prefix("block/" + TFBlocks.RAINBOW_OAK_SAPLING.getId().getPath());
      this.simpleBlock(
         (Block)TFBlocks.RAINBOW_OAK_SAPLING.get(),
         ((BlockModelBuilder)this.models().cross(TFBlocks.RAINBOW_OAK_SAPLING.getId().getPath(), rainboakSaplTex)).renderType(CUTOUT)
      );
      this.singleBlockBoilerPlate((Block)TFBlocks.RAINBOW_OAK_LEAVES.get(), "block/leaves", m -> m.texture("all", "minecraft:block/oak_leaves"));
      this.logWoodSapling(
         (RotatedPillarBlock)TFBlocks.CANOPY_LOG.get(),
         (RotatedPillarBlock)TFBlocks.STRIPPED_CANOPY_LOG.get(),
         (RotatedPillarBlock)TFBlocks.CANOPY_WOOD.get(),
         (RotatedPillarBlock)TFBlocks.STRIPPED_CANOPY_WOOD.get(),
         (Block)TFBlocks.CANOPY_SAPLING.get()
      );
      this.plankBlocks(
         "canopy",
         (Block)TFBlocks.CANOPY_PLANKS.get(),
         (Block)TFBlocks.CANOPY_SLAB.get(),
         (StairBlock)TFBlocks.CANOPY_STAIRS.get(),
         (Block)TFBlocks.CANOPY_BUTTON.get(),
         (Block)TFBlocks.CANOPY_FENCE.get(),
         (Block)TFBlocks.CANOPY_GATE.get(),
         (Block)TFBlocks.CANOPY_PLATE.get(),
         (DoorBlock)TFBlocks.CANOPY_DOOR.get(),
         (TrapDoorBlock)TFBlocks.CANOPY_TRAPDOOR.get(),
         (BanisterBlock)TFBlocks.CANOPY_BANISTER.get(),
         (DryingRackBlock)TFBlocks.CANOPY_DRYING_RACK.get()
      );
      this.singleBlockBoilerPlate((Block)TFBlocks.CANOPY_LEAVES.get(), "block/leaves", m -> m.texture("all", "minecraft:block/spruce_leaves"));
      this.logWoodSapling(
         (RotatedPillarBlock)TFBlocks.MANGROVE_LOG.get(),
         (RotatedPillarBlock)TFBlocks.STRIPPED_MANGROVE_LOG.get(),
         (RotatedPillarBlock)TFBlocks.MANGROVE_WOOD.get(),
         (RotatedPillarBlock)TFBlocks.STRIPPED_MANGROVE_WOOD.get(),
         (Block)TFBlocks.MANGROVE_SAPLING.get()
      );
      this.plankBlocks(
         "mangrove",
         (Block)TFBlocks.MANGROVE_PLANKS.get(),
         (Block)TFBlocks.MANGROVE_SLAB.get(),
         (StairBlock)TFBlocks.MANGROVE_STAIRS.get(),
         (Block)TFBlocks.MANGROVE_BUTTON.get(),
         (Block)TFBlocks.MANGROVE_FENCE.get(),
         (Block)TFBlocks.MANGROVE_GATE.get(),
         (Block)TFBlocks.MANGROVE_PLATE.get(),
         (DoorBlock)TFBlocks.MANGROVE_DOOR.get(),
         (TrapDoorBlock)TFBlocks.MANGROVE_TRAPDOOR.get(),
         (BanisterBlock)TFBlocks.MANGROVE_BANISTER.get(),
         (DryingRackBlock)TFBlocks.MANGROVE_DRYING_RACK.get()
      );
      this.singleBlockBoilerPlate((Block)TFBlocks.MANGROVE_LEAVES.get(), "block/leaves", m -> m.texture("all", "minecraft:block/birch_leaves"));
      this.logWoodSapling(
         (RotatedPillarBlock)TFBlocks.DARK_LOG.get(),
         (RotatedPillarBlock)TFBlocks.STRIPPED_DARK_LOG.get(),
         (RotatedPillarBlock)TFBlocks.DARK_WOOD.get(),
         (RotatedPillarBlock)TFBlocks.STRIPPED_DARK_WOOD.get(),
         (Block)TFBlocks.DARKWOOD_SAPLING.get()
      );
      this.plankBlocks(
         "darkwood",
         (Block)TFBlocks.DARK_PLANKS.get(),
         (Block)TFBlocks.DARK_SLAB.get(),
         (StairBlock)TFBlocks.DARK_STAIRS.get(),
         (Block)TFBlocks.DARK_BUTTON.get(),
         (Block)TFBlocks.DARK_FENCE.get(),
         (Block)TFBlocks.DARK_GATE.get(),
         (Block)TFBlocks.DARK_PLATE.get(),
         (DoorBlock)TFBlocks.DARK_DOOR.get(),
         (TrapDoorBlock)TFBlocks.DARK_TRAPDOOR.get(),
         (BanisterBlock)TFBlocks.DARK_BANISTER.get(),
         (DryingRackBlock)TFBlocks.DARK_DRYING_RACK.get()
      );
      this.singleBlockBoilerPlate((Block)TFBlocks.DARK_LEAVES.get(), "block/leaves", m -> m.texture("all", "block/darkwood_leaves"));
      this.singleBlockBoilerPlate((Block)TFBlocks.HARDENED_DARK_LEAVES.get(), "block/leaves", m -> m.texture("all", "block/darkwood_leaves"));
      this.logWoodSapling(
         (RotatedPillarBlock)TFBlocks.TIME_LOG.get(),
         (RotatedPillarBlock)TFBlocks.STRIPPED_TIME_LOG.get(),
         (RotatedPillarBlock)TFBlocks.TIME_WOOD.get(),
         (RotatedPillarBlock)TFBlocks.STRIPPED_TIME_WOOD.get(),
         (Block)TFBlocks.TIME_SAPLING.get()
      );
      this.plankBlocks(
         "time",
         (Block)TFBlocks.TIME_PLANKS.get(),
         (Block)TFBlocks.TIME_SLAB.get(),
         (StairBlock)TFBlocks.TIME_STAIRS.get(),
         (Block)TFBlocks.TIME_BUTTON.get(),
         (Block)TFBlocks.TIME_FENCE.get(),
         (Block)TFBlocks.TIME_GATE.get(),
         (Block)TFBlocks.TIME_PLATE.get(),
         (DoorBlock)TFBlocks.TIME_DOOR.get(),
         (TrapDoorBlock)TFBlocks.TIME_TRAPDOOR.get(),
         true,
         false,
         (BanisterBlock)TFBlocks.TIME_BANISTER.get(),
         (DryingRackBlock)TFBlocks.TIME_DRYING_RACK.get()
      );
      this.makeTimeLeaves();
      this.magicLogCore((Block)TFBlocks.TIME_LOG_CORE.get());
      this.logWoodSapling(
         (RotatedPillarBlock)TFBlocks.TRANSFORMATION_LOG.get(),
         (RotatedPillarBlock)TFBlocks.STRIPPED_TRANSFORMATION_LOG.get(),
         (RotatedPillarBlock)TFBlocks.TRANSFORMATION_WOOD.get(),
         (RotatedPillarBlock)TFBlocks.STRIPPED_TRANSFORMATION_WOOD.get(),
         (Block)TFBlocks.TRANSFORMATION_SAPLING.get()
      );
      this.plankBlocks(
         "trans",
         (Block)TFBlocks.TRANSFORMATION_PLANKS.get(),
         (Block)TFBlocks.TRANSFORMATION_SLAB.get(),
         (StairBlock)TFBlocks.TRANSFORMATION_STAIRS.get(),
         (Block)TFBlocks.TRANSFORMATION_BUTTON.get(),
         (Block)TFBlocks.TRANSFORMATION_FENCE.get(),
         (Block)TFBlocks.TRANSFORMATION_GATE.get(),
         (Block)TFBlocks.TRANSFORMATION_PLATE.get(),
         (DoorBlock)TFBlocks.TRANSFORMATION_DOOR.get(),
         (TrapDoorBlock)TFBlocks.TRANSFORMATION_TRAPDOOR.get(),
         true,
         false,
         (BanisterBlock)TFBlocks.TRANSFORMATION_BANISTER.get(),
         (DryingRackBlock)TFBlocks.TRANSFORMATION_DRYING_RACK.get()
      );
      this.makeTransformationLeaves();
      this.magicLogCore((Block)TFBlocks.TRANSFORMATION_LOG_CORE.get());
      this.logWoodSapling(
         (RotatedPillarBlock)TFBlocks.MINING_LOG.get(),
         (RotatedPillarBlock)TFBlocks.STRIPPED_MINING_LOG.get(),
         (RotatedPillarBlock)TFBlocks.MINING_WOOD.get(),
         (RotatedPillarBlock)TFBlocks.STRIPPED_MINING_WOOD.get(),
         (Block)TFBlocks.MINING_SAPLING.get()
      );
      this.plankBlocks(
         "mine",
         (Block)TFBlocks.MINING_PLANKS.get(),
         (Block)TFBlocks.MINING_SLAB.get(),
         (StairBlock)TFBlocks.MINING_STAIRS.get(),
         (Block)TFBlocks.MINING_BUTTON.get(),
         (Block)TFBlocks.MINING_FENCE.get(),
         (Block)TFBlocks.MINING_GATE.get(),
         (Block)TFBlocks.MINING_PLATE.get(),
         (DoorBlock)TFBlocks.MINING_DOOR.get(),
         (TrapDoorBlock)TFBlocks.MINING_TRAPDOOR.get(),
         (BanisterBlock)TFBlocks.MINING_BANISTER.get(),
         (DryingRackBlock)TFBlocks.MINING_DRYING_RACK.get()
      );
      this.makeMiningLeaves();
      this.magicLogCore((Block)TFBlocks.MINING_LOG_CORE.get());
      this.logWoodSapling(
         (RotatedPillarBlock)TFBlocks.SORTING_LOG.get(),
         (RotatedPillarBlock)TFBlocks.STRIPPED_SORTING_LOG.get(),
         (RotatedPillarBlock)TFBlocks.SORTING_WOOD.get(),
         (RotatedPillarBlock)TFBlocks.STRIPPED_SORTING_WOOD.get(),
         (Block)TFBlocks.SORTING_SAPLING.get()
      );
      this.plankBlocks(
         "sort",
         (Block)TFBlocks.SORTING_PLANKS.get(),
         (Block)TFBlocks.SORTING_SLAB.get(),
         (StairBlock)TFBlocks.SORTING_STAIRS.get(),
         (Block)TFBlocks.SORTING_BUTTON.get(),
         (Block)TFBlocks.SORTING_FENCE.get(),
         (Block)TFBlocks.SORTING_GATE.get(),
         (Block)TFBlocks.SORTING_PLATE.get(),
         (DoorBlock)TFBlocks.SORTING_DOOR.get(),
         (TrapDoorBlock)TFBlocks.SORTING_TRAPDOOR.get(),
         true,
         true,
         (BanisterBlock)TFBlocks.SORTING_BANISTER.get(),
         (DryingRackBlock)TFBlocks.SORTING_DRYING_RACK.get()
      );
      this.buildSortingLeaves();
      this.magicLogCore((Block)TFBlocks.SORTING_LOG_CORE.get());
      this.banisterVanilla((BanisterBlock)TFBlocks.OAK_BANISTER.get(), "oak_planks", "oak");
      this.banisterVanilla((BanisterBlock)TFBlocks.SPRUCE_BANISTER.get(), "spruce_planks", "spruce");
      this.banisterVanilla((BanisterBlock)TFBlocks.BIRCH_BANISTER.get(), "birch_planks", "birch");
      this.banisterVanilla((BanisterBlock)TFBlocks.JUNGLE_BANISTER.get(), "jungle_planks", "jungle");
      this.banisterVanilla((BanisterBlock)TFBlocks.ACACIA_BANISTER.get(), "acacia_planks", "acacia");
      this.banisterVanilla((BanisterBlock)TFBlocks.DARK_OAK_BANISTER.get(), "dark_oak_planks", "dark_oak");
      this.banisterVanilla((BanisterBlock)TFBlocks.CRIMSON_BANISTER.get(), "crimson_planks", "crimson");
      this.banisterVanilla((BanisterBlock)TFBlocks.WARPED_BANISTER.get(), "warped_planks", "warped");
      this.banisterVanilla((BanisterBlock)TFBlocks.VANGROVE_BANISTER.get(), "mangrove_planks", "vanilla_mangrove");
      this.banisterVanilla((BanisterBlock)TFBlocks.BAMBOO_BANISTER.get(), "bamboo_planks", "bamboo");
      this.banisterVanilla((BanisterBlock)TFBlocks.CHERRY_BANISTER.get(), "cherry_planks", "cherry");
      this.dryingRackVanilla((DryingRackBlock)TFBlocks.OAK_DRYING_RACK.get(), "oak_planks", "oak");
      this.dryingRackVanilla((DryingRackBlock)TFBlocks.SPRUCE_DRYING_RACK.get(), "spruce_planks", "spruce");
      this.dryingRackVanilla((DryingRackBlock)TFBlocks.BIRCH_DRYING_RACK.get(), "birch_planks", "birch");
      this.dryingRackVanilla((DryingRackBlock)TFBlocks.JUNGLE_DRYING_RACK.get(), "jungle_planks", "jungle");
      this.dryingRackVanilla((DryingRackBlock)TFBlocks.ACACIA_DRYING_RACK.get(), "acacia_planks", "acacia");
      this.dryingRackVanilla((DryingRackBlock)TFBlocks.DARK_OAK_DRYING_RACK.get(), "dark_oak_planks", "dark_oak");
      this.dryingRackVanilla((DryingRackBlock)TFBlocks.CRIMSON_DRYING_RACK.get(), "crimson_planks", "crimson");
      this.dryingRackVanilla((DryingRackBlock)TFBlocks.WARPED_DRYING_RACK.get(), "warped_planks", "warped");
      this.dryingRackVanilla((DryingRackBlock)TFBlocks.VANGROVE_DRYING_RACK.get(), "mangrove_planks", "vanilla_mangrove");
      this.dryingRackVanilla((DryingRackBlock)TFBlocks.BAMBOO_DRYING_RACK.get(), "bamboo_planks", "bamboo");
      this.dryingRackVanilla((DryingRackBlock)TFBlocks.CHERRY_DRYING_RACK.get(), "cherry_planks", "cherry");
      ResourceLocation MOSS = TwilightForestMod.prefix("block/mosspatch");
      ResourceLocation MOSS_OVERHANG = TwilightForestMod.prefix("block/moss_overhang");
      ResourceLocation TALL_GRASS = ResourceLocation.withDefaultNamespace("block/short_grass");
      ResourceLocation SNOW = ResourceLocation.withDefaultNamespace("block/snow");
      ResourceLocation SNOW_OVERHANG = TwilightForestMod.prefix("block/snow_overhang");
      ModelFile EMPTY_LOG = this.buildHorizontalHollowLog(false, false);
      ModelFile LAYERED_LOG = this.buildHorizontalHollowLog(true, false);
      ModelFile MOSS_LOG_GRASS = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                        .getBuilder("hollow_log_moss_grass"))
                     .parent(this.buildHorizontalHollowLog(true, true)))
                  .renderType(CUTOUT))
               .texture("carpet", MOSS))
            .texture("overhang", MOSS_OVERHANG))
         .texture("plant", TALL_GRASS);
      ModelFile MOSS_LOG = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models().getBuilder("hollow_log_moss"))
                  .parent(LAYERED_LOG))
               .renderType(CUTOUT))
            .texture("carpet", MOSS))
         .texture("overhang", MOSS_OVERHANG);
      ModelFile SNOW_LOG = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models().getBuilder("hollow_log_snow"))
                  .parent(LAYERED_LOG))
               .renderType(CUTOUT))
            .texture("carpet", SNOW))
         .texture("overhang", SNOW_OVERHANG);
      ModelFile HOLLOW_LOG = this.buildVerticalLog(null);
      ModelFile VINE_LOG = this.buildVerticalLog(HollowLogVariants.Climbable.VINE);
      ModelFile LADDER_LOG = this.buildVerticalLog(HollowLogVariants.Climbable.LADDER);
      this.hollowLogs(
         Blocks.OAK_LOG,
         Blocks.STRIPPED_OAK_LOG,
         TFBlocks.HOLLOW_OAK_LOG_HORIZONTAL,
         TFBlocks.HOLLOW_OAK_LOG_VERTICAL,
         TFBlocks.HOLLOW_OAK_LOG_CLIMBABLE,
         EMPTY_LOG,
         MOSS_LOG,
         MOSS_LOG_GRASS,
         SNOW_LOG,
         HOLLOW_LOG,
         VINE_LOG,
         LADDER_LOG
      );
      this.hollowLogs(
         Blocks.SPRUCE_LOG,
         Blocks.STRIPPED_SPRUCE_LOG,
         TFBlocks.HOLLOW_SPRUCE_LOG_HORIZONTAL,
         TFBlocks.HOLLOW_SPRUCE_LOG_VERTICAL,
         TFBlocks.HOLLOW_SPRUCE_LOG_CLIMBABLE,
         EMPTY_LOG,
         MOSS_LOG,
         MOSS_LOG_GRASS,
         SNOW_LOG,
         HOLLOW_LOG,
         VINE_LOG,
         LADDER_LOG
      );
      this.hollowLogs(
         Blocks.BIRCH_LOG,
         Blocks.STRIPPED_BIRCH_LOG,
         TFBlocks.HOLLOW_BIRCH_LOG_HORIZONTAL,
         TFBlocks.HOLLOW_BIRCH_LOG_VERTICAL,
         TFBlocks.HOLLOW_BIRCH_LOG_CLIMBABLE,
         EMPTY_LOG,
         MOSS_LOG,
         MOSS_LOG_GRASS,
         SNOW_LOG,
         HOLLOW_LOG,
         VINE_LOG,
         LADDER_LOG
      );
      this.hollowLogs(
         Blocks.JUNGLE_LOG,
         Blocks.STRIPPED_JUNGLE_LOG,
         TFBlocks.HOLLOW_JUNGLE_LOG_HORIZONTAL,
         TFBlocks.HOLLOW_JUNGLE_LOG_VERTICAL,
         TFBlocks.HOLLOW_JUNGLE_LOG_CLIMBABLE,
         EMPTY_LOG,
         MOSS_LOG,
         MOSS_LOG_GRASS,
         SNOW_LOG,
         HOLLOW_LOG,
         VINE_LOG,
         LADDER_LOG
      );
      this.hollowLogs(
         Blocks.ACACIA_LOG,
         Blocks.STRIPPED_ACACIA_LOG,
         TFBlocks.HOLLOW_ACACIA_LOG_HORIZONTAL,
         TFBlocks.HOLLOW_ACACIA_LOG_VERTICAL,
         TFBlocks.HOLLOW_ACACIA_LOG_CLIMBABLE,
         EMPTY_LOG,
         MOSS_LOG,
         MOSS_LOG_GRASS,
         SNOW_LOG,
         HOLLOW_LOG,
         VINE_LOG,
         LADDER_LOG
      );
      this.hollowLogs(
         Blocks.DARK_OAK_LOG,
         Blocks.STRIPPED_DARK_OAK_LOG,
         TFBlocks.HOLLOW_DARK_OAK_LOG_HORIZONTAL,
         TFBlocks.HOLLOW_DARK_OAK_LOG_VERTICAL,
         TFBlocks.HOLLOW_DARK_OAK_LOG_CLIMBABLE,
         EMPTY_LOG,
         MOSS_LOG,
         MOSS_LOG_GRASS,
         SNOW_LOG,
         HOLLOW_LOG,
         VINE_LOG,
         LADDER_LOG
      );
      this.hollowLogs(
         Blocks.CRIMSON_STEM,
         Blocks.STRIPPED_CRIMSON_STEM,
         TFBlocks.HOLLOW_CRIMSON_STEM_HORIZONTAL,
         TFBlocks.HOLLOW_CRIMSON_STEM_VERTICAL,
         TFBlocks.HOLLOW_CRIMSON_STEM_CLIMBABLE,
         EMPTY_LOG,
         MOSS_LOG,
         MOSS_LOG_GRASS,
         SNOW_LOG,
         HOLLOW_LOG,
         VINE_LOG,
         LADDER_LOG
      );
      this.hollowLogs(
         Blocks.WARPED_STEM,
         Blocks.STRIPPED_WARPED_STEM,
         TFBlocks.HOLLOW_WARPED_STEM_HORIZONTAL,
         TFBlocks.HOLLOW_WARPED_STEM_VERTICAL,
         TFBlocks.HOLLOW_WARPED_STEM_CLIMBABLE,
         EMPTY_LOG,
         MOSS_LOG,
         MOSS_LOG_GRASS,
         SNOW_LOG,
         HOLLOW_LOG,
         VINE_LOG,
         LADDER_LOG
      );
      this.hollowLogs(
         Blocks.MANGROVE_LOG,
         Blocks.STRIPPED_MANGROVE_LOG,
         TFBlocks.HOLLOW_VANGROVE_LOG_HORIZONTAL,
         TFBlocks.HOLLOW_VANGROVE_LOG_VERTICAL,
         TFBlocks.HOLLOW_VANGROVE_LOG_CLIMBABLE,
         EMPTY_LOG,
         MOSS_LOG,
         MOSS_LOG_GRASS,
         SNOW_LOG,
         HOLLOW_LOG,
         VINE_LOG,
         LADDER_LOG
      );
      this.hollowLogs(
         Blocks.CHERRY_LOG,
         Blocks.STRIPPED_CHERRY_LOG,
         TFBlocks.HOLLOW_CHERRY_LOG_HORIZONTAL,
         TFBlocks.HOLLOW_CHERRY_LOG_VERTICAL,
         TFBlocks.HOLLOW_CHERRY_LOG_CLIMBABLE,
         EMPTY_LOG,
         MOSS_LOG,
         MOSS_LOG_GRASS,
         SNOW_LOG,
         HOLLOW_LOG,
         VINE_LOG,
         LADDER_LOG
      );
      this.hollowLogs(
         TFBlocks.TWILIGHT_OAK_LOG,
         TFBlocks.STRIPPED_TWILIGHT_OAK_LOG,
         TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_HORIZONTAL,
         TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_VERTICAL,
         TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_CLIMBABLE,
         EMPTY_LOG,
         MOSS_LOG,
         MOSS_LOG_GRASS,
         SNOW_LOG,
         HOLLOW_LOG,
         VINE_LOG,
         LADDER_LOG
      );
      this.hollowLogs(
         TFBlocks.CANOPY_LOG,
         TFBlocks.STRIPPED_CANOPY_LOG,
         TFBlocks.HOLLOW_CANOPY_LOG_HORIZONTAL,
         TFBlocks.HOLLOW_CANOPY_LOG_VERTICAL,
         TFBlocks.HOLLOW_CANOPY_LOG_CLIMBABLE,
         EMPTY_LOG,
         MOSS_LOG,
         MOSS_LOG_GRASS,
         SNOW_LOG,
         HOLLOW_LOG,
         VINE_LOG,
         LADDER_LOG
      );
      this.hollowLogs(
         TFBlocks.MANGROVE_LOG,
         TFBlocks.STRIPPED_MANGROVE_LOG,
         TFBlocks.HOLLOW_MANGROVE_LOG_HORIZONTAL,
         TFBlocks.HOLLOW_MANGROVE_LOG_VERTICAL,
         TFBlocks.HOLLOW_MANGROVE_LOG_CLIMBABLE,
         EMPTY_LOG,
         MOSS_LOG,
         MOSS_LOG_GRASS,
         SNOW_LOG,
         HOLLOW_LOG,
         VINE_LOG,
         LADDER_LOG
      );
      this.hollowLogs(
         TFBlocks.DARK_LOG,
         TFBlocks.STRIPPED_DARK_LOG,
         TFBlocks.HOLLOW_DARK_LOG_HORIZONTAL,
         TFBlocks.HOLLOW_DARK_LOG_VERTICAL,
         TFBlocks.HOLLOW_DARK_LOG_CLIMBABLE,
         EMPTY_LOG,
         MOSS_LOG,
         MOSS_LOG_GRASS,
         SNOW_LOG,
         HOLLOW_LOG,
         VINE_LOG,
         LADDER_LOG
      );
      this.hollowLogs(
         TFBlocks.TIME_LOG,
         TFBlocks.STRIPPED_TIME_LOG,
         TFBlocks.HOLLOW_TIME_LOG_HORIZONTAL,
         TFBlocks.HOLLOW_TIME_LOG_VERTICAL,
         TFBlocks.HOLLOW_TIME_LOG_CLIMBABLE,
         EMPTY_LOG,
         MOSS_LOG,
         MOSS_LOG_GRASS,
         SNOW_LOG,
         HOLLOW_LOG,
         VINE_LOG,
         LADDER_LOG
      );
      this.hollowLogs(
         TFBlocks.TRANSFORMATION_LOG,
         TFBlocks.STRIPPED_TRANSFORMATION_LOG,
         TFBlocks.HOLLOW_TRANSFORMATION_LOG_HORIZONTAL,
         TFBlocks.HOLLOW_TRANSFORMATION_LOG_VERTICAL,
         TFBlocks.HOLLOW_TRANSFORMATION_LOG_CLIMBABLE,
         EMPTY_LOG,
         MOSS_LOG,
         MOSS_LOG_GRASS,
         SNOW_LOG,
         HOLLOW_LOG,
         VINE_LOG,
         LADDER_LOG
      );
      this.hollowLogs(
         TFBlocks.MINING_LOG,
         TFBlocks.STRIPPED_MINING_LOG,
         TFBlocks.HOLLOW_MINING_LOG_HORIZONTAL,
         TFBlocks.HOLLOW_MINING_LOG_VERTICAL,
         TFBlocks.HOLLOW_MINING_LOG_CLIMBABLE,
         EMPTY_LOG,
         MOSS_LOG,
         MOSS_LOG_GRASS,
         SNOW_LOG,
         HOLLOW_LOG,
         VINE_LOG,
         LADDER_LOG
      );
      this.hollowLogs(
         TFBlocks.SORTING_LOG,
         TFBlocks.STRIPPED_SORTING_LOG,
         TFBlocks.HOLLOW_SORTING_LOG_HORIZONTAL,
         TFBlocks.HOLLOW_SORTING_LOG_VERTICAL,
         TFBlocks.HOLLOW_SORTING_LOG_CLIMBABLE,
         EMPTY_LOG,
         MOSS_LOG,
         MOSS_LOG_GRASS,
         SNOW_LOG,
         HOLLOW_LOG,
         VINE_LOG,
         LADDER_LOG
      );
   }

   private void buildSortingLeaves() {
      Block block = (Block)TFBlocks.SORTING_LEAVES.get();
      int[][] CHOSEN_VARIANTS = new int[][]{{0, 2, 2, 3, 0, 0}, {2, 0, 3, 0, 2, 1}, {3, 3, 1, 2, 3, 2}, {1, 1, 0, 1, 1, 3}};
      ModelFile[] modelFiles = new ModelFile[CHOSEN_VARIANTS.length];

      for (int i = 0; i < CHOSEN_VARIANTS.length; i++) {
         ModelBuilder<BlockModelBuilder> modelBuilder = this.models()
            .leaves(this.name(block) + (i > 0 ? i : ""), TwilightForestMod.prefix("block/" + this.name(block)));
         ModelBuilder<BlockModelBuilder>.ElementBuilder builder = modelBuilder.element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F);

         for (Direction dir : Direction.values()) {
            FaceRotation rotation = FaceRotation.values()[CHOSEN_VARIANTS[i][dir.ordinal()]];
            builder.face(dir).texture("#all").rotation(rotation).tintindex(0).end();
         }

         builder.end();
         modelFiles[i] = modelBuilder;
      }

      this.getVariantBuilder(block)
         .forAllStates(state -> Arrays.stream(modelFiles).map(builderx -> new ConfiguredModel(builderx, 0, 0, false)).toArray(ConfiguredModel[]::new));
   }

   private void makeTimeLeaves() {
      this.buildMagicLeaves((Block)TFBlocks.TIME_LEAVES.get(), 180);
   }

   private void makeTransformationLeaves() {
      this.buildMagicLeaves((Block)TFBlocks.TRANSFORMATION_LEAVES.get(), -90);
   }

   private void makeMiningLeaves() {
      this.buildMagicLeaves((Block)TFBlocks.MINING_LEAVES.get(), 90);
   }

   private void buildMagicLeaves(Block block, int rotation) {
      rotation = rotation == -90 ? 270 : rotation;
      FaceRotation faceRotation = rotation % 180 == 0 ? FaceRotation.ZERO : FaceRotation.values()[rotation / 90];
      boolean isRotation180 = rotation == 180;
      float u1 = isRotation180 ? 16.0F : 0.0F;
      float v1 = isRotation180 ? 16.0F : 0.0F;
      float u2 = isRotation180 ? 0.0F : 16.0F;
      float v2 = isRotation180 ? 0.0F : 16.0F;
      ModelFile modelFile = ((BlockModelBuilder)this.models().leaves(this.name(block), TwilightForestMod.prefix("block/" + this.name(block))))
         .element()
         .from(0.0F, 0.0F, 0.0F)
         .to(16.0F, 16.0F, 16.0F)
         .allFaces((dir, builder) -> builder.cullface(dir).uvs(u1, v1, u2, v2).tintindex(0).rotation(faceRotation).texture("#all"))
         .end();
      this.getVariantBuilder(block).forAllStates(state -> ConfiguredModel.allYRotations(modelFile, 0, false));
   }

   private void magicLogCore(Block b) {
      ResourceLocation topTex = TwilightForestMod.prefix("block/" + this.name(b).replace("_core", "_top"));
      ModelFile off = this.models().cubeColumn(this.name(b), this.blockTexture(b), topTex);
      ModelFile on = this.models().cubeColumn(this.name(b) + "_on", TwilightForestMod.prefix("block/" + this.name(b) + "_on"), topTex);
      this.getVariantBuilder(b).forAllStates(s -> {
         ModelFile f = s.getValue(SpecialMagicLogBlock.ACTIVE) ? on : off;
         Axis axis = (Axis)s.getValue(RotatedPillarBlock.AXIS);
         int rotX = axis != Axis.X && axis != Axis.Z ? 0 : 90;
         int rotY = axis == Axis.X ? 90 : 0;
         return ConfiguredModel.builder().modelFile(f).rotationX(rotX).rotationY(rotY).build();
      });
   }

   private void rotationallyCorrectColumn(Block b) {
      ResourceLocation side = TwilightForestMod.prefix("block/" + this.name(b) + "_side");
      ResourceLocation end = TwilightForestMod.prefix("block/" + this.name(b) + "_end");
      ConfiguredModel yModel = new ConfiguredModel(this.models().cubeColumn(this.name(b), side, end));
      ConfiguredModel xModel = ConfiguredModel.builder()
         .modelFile(
            ((BlockModelBuilder)((BlockModelBuilder)this.models()
                     .withExistingParent(this.name(b) + "_x", TwilightForestMod.prefix("block/util/cube_column_rotationally_correct_x")))
                  .texture("side", side))
               .texture("end", end)
         )
         .rotationX(90)
         .rotationY(90)
         .buildLast();
      ConfiguredModel zModel = ConfiguredModel.builder()
         .modelFile(
            ((BlockModelBuilder)((BlockModelBuilder)this.models()
                     .withExistingParent(this.name(b) + "_z", TwilightForestMod.prefix("block/util/cube_column_rotationally_correct_z")))
                  .texture("side", side))
               .texture("end", end)
         )
         .rotationX(90)
         .buildLast();
      this.getVariantBuilder(b)
         .partialState()
         .with(RotatedPillarBlock.AXIS, Axis.Y)
         .setModels(new ConfiguredModel[]{yModel})
         .partialState()
         .with(RotatedPillarBlock.AXIS, Axis.X)
         .setModels(new ConfiguredModel[]{xModel})
         .partialState()
         .with(RotatedPillarBlock.AXIS, Axis.Z)
         .setModels(new ConfiguredModel[]{zModel});
   }

   private void rotationallySpecialColumn(Block b) {
      ResourceLocation sideA = TwilightForestMod.prefix("block/" + this.name(b) + "_side_a");
      ResourceLocation sideB = TwilightForestMod.prefix("block/" + this.name(b) + "_side_b");
      ResourceLocation end = TwilightForestMod.prefix("block/" + this.name(b) + "_end");
      ConfiguredModel yModel = new ConfiguredModel(this.models().cubeColumn(this.name(b), sideA, end));
      ConfiguredModel xModel = ConfiguredModel.builder()
         .modelFile(
            ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                        .withExistingParent(this.name(b) + "_x", TwilightForestMod.prefix("block/util/cube_column_rotationally_special_x")))
                     .texture("side_a", sideA))
                  .texture("side_b", sideB))
               .texture("end", end)
         )
         .buildLast();
      ConfiguredModel zModel = ConfiguredModel.builder()
         .modelFile(
            ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                        .withExistingParent(this.name(b) + "_z", TwilightForestMod.prefix("block/util/cube_column_rotationally_special_z")))
                     .texture("side_a", sideA))
                  .texture("side_b", sideB))
               .texture("end", end)
         )
         .buildLast();
      this.getVariantBuilder(b)
         .partialState()
         .with(RotatedPillarBlock.AXIS, Axis.Y)
         .setModels(new ConfiguredModel[]{yModel})
         .partialState()
         .with(RotatedPillarBlock.AXIS, Axis.X)
         .setModels(new ConfiguredModel[]{xModel})
         .partialState()
         .with(RotatedPillarBlock.AXIS, Axis.Z)
         .setModels(new ConfiguredModel[]{zModel});
   }

   private void castleDoor(Block b) {
      ModelFile vanished = ((ConnectedTextureBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                              .withExistingParent(BuiltInRegistries.BLOCK.getKey(b).getPath() + "_vanished", "block/block"))
                           .texture("base_texture", TwilightForestMod.prefix("block/castle_door_vanished")))
                        .texture("particle", TwilightForestMod.prefix("block/castle_door_vanished")))
                     .texture("overlay_texture", TwilightForestMod.prefix("block/castle_door_rune_corners")))
                  .texture("overlay_connected", TwilightForestMod.prefix("block/castle_door_rune_ctm")))
               .renderType(CUTOUT))
            .customLoader(ConnectedTextureBuilder::begin))
         .connectsTo(
            (Block)TFBlocks.PINK_CASTLE_DOOR.get(),
            (Block)TFBlocks.YELLOW_CASTLE_DOOR.get(),
            (Block)TFBlocks.BLUE_CASTLE_DOOR.get(),
            (Block)TFBlocks.VIOLET_CASTLE_DOOR.get()
         )
         .setOverlayTintIndex(0)
         .setOverlayEmissivity(15)
         .end();
      ModelFile main = ((ConnectedTextureBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                              .withExistingParent(BuiltInRegistries.BLOCK.getKey(b).getPath(), "block/block"))
                           .texture("base_texture", TwilightForestMod.prefix("block/castle_door")))
                        .texture("particle", TwilightForestMod.prefix("block/castle_door")))
                     .texture("overlay_texture", TwilightForestMod.prefix("block/castle_door_rune_corners")))
                  .texture("overlay_connected", TwilightForestMod.prefix("block/castle_door_rune_ctm")))
               .renderType(CUTOUT))
            .customLoader(ConnectedTextureBuilder::begin))
         .connectsTo(
            (Block)TFBlocks.PINK_CASTLE_DOOR.get(),
            (Block)TFBlocks.YELLOW_CASTLE_DOOR.get(),
            (Block)TFBlocks.BLUE_CASTLE_DOOR.get(),
            (Block)TFBlocks.VIOLET_CASTLE_DOOR.get()
         )
         .setOverlayTintIndex(0)
         .setOverlayEmissivity(15)
         .end();
      this.getVariantBuilder(b).forAllStates(state -> ConfiguredModel.builder().modelFile(state.getValue(CastleDoorBlock.VANISHED) ? vanished : main).build());
   }

   private void allRotations(Block b, ModelFile model) {
      Builder<?> builder = ConfiguredModel.builder();
      int[] rots = new int[]{0, 90, 180, 270};

      for (int x : rots) {
         for (int y : rots) {
            builder = builder.modelFile(model).rotationX(x).rotationY(y);
            if (x != rots[rots.length - 1] && y != rots[rots.length - 1]) {
               builder = builder.nextModel();
            }
         }
      }

      this.simpleBlock(b, builder.build());
   }

   private void stonePillar() {
      ModelFile base = this.models().getExistingFile(TwilightForestMod.prefix("block/pillar/pillar_base"));
      ModelFile up = this.models().getExistingFile(TwilightForestMod.prefix("block/pillar/pillar_up"));
      ModelFile down = this.models().getExistingFile(TwilightForestMod.prefix("block/pillar/pillar_down"));
      ModelFile top = this.models().getExistingFile(TwilightForestMod.prefix("block/pillar/pillar_top"));
      ModelFile bottom = this.models().getExistingFile(TwilightForestMod.prefix("block/pillar/pillar_bottom"));
      ((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)this.getMultipartBuilder(
                                                         (Block)TFBlocks.TWISTED_STONE_PILLAR.get()
                                                      )
                                                      .part()
                                                      .modelFile(base)
                                                      .rotationX(90)
                                                      .rotationY(90)
                                                      .addModel())
                                                   .condition(WallPillarBlock.AXIS, new Axis[]{Axis.X})
                                                   .end()
                                                   .part()
                                                   .modelFile(top)
                                                   .rotationX(90)
                                                   .rotationY(90)
                                                   .addModel())
                                                .condition(WallPillarBlock.AXIS, new Axis[]{Axis.X})
                                                .condition(PipeBlock.EAST, new Boolean[]{false})
                                                .end()
                                                .part()
                                                .modelFile(bottom)
                                                .rotationX(90)
                                                .rotationY(90)
                                                .addModel())
                                             .condition(WallPillarBlock.AXIS, new Axis[]{Axis.X})
                                             .condition(PipeBlock.WEST, new Boolean[]{false})
                                             .end()
                                             .part()
                                             .modelFile(up)
                                             .rotationX(90)
                                             .rotationY(90)
                                             .addModel())
                                          .condition(WallPillarBlock.AXIS, new Axis[]{Axis.Y, Axis.Z})
                                          .condition(PipeBlock.EAST, new Boolean[]{true})
                                          .end()
                                          .part()
                                          .modelFile(down)
                                          .rotationX(90)
                                          .rotationY(90)
                                          .addModel())
                                       .condition(WallPillarBlock.AXIS, new Axis[]{Axis.Y, Axis.Z})
                                       .condition(PipeBlock.WEST, new Boolean[]{true})
                                       .end()
                                       .part()
                                       .modelFile(base)
                                       .addModel())
                                    .condition(WallPillarBlock.AXIS, new Axis[]{Axis.Y})
                                    .end()
                                    .part()
                                    .modelFile(top)
                                    .addModel())
                                 .condition(WallPillarBlock.AXIS, new Axis[]{Axis.Y})
                                 .condition(PipeBlock.UP, new Boolean[]{false})
                                 .end()
                                 .part()
                                 .modelFile(bottom)
                                 .addModel())
                              .condition(WallPillarBlock.AXIS, new Axis[]{Axis.Y})
                              .condition(PipeBlock.DOWN, new Boolean[]{false})
                              .end()
                              .part()
                              .modelFile(up)
                              .addModel())
                           .condition(WallPillarBlock.AXIS, new Axis[]{Axis.X, Axis.Z})
                           .condition(PipeBlock.UP, new Boolean[]{true})
                           .end()
                           .part()
                           .modelFile(down)
                           .addModel())
                        .condition(WallPillarBlock.AXIS, new Axis[]{Axis.X, Axis.Z})
                        .condition(PipeBlock.DOWN, new Boolean[]{true})
                        .end()
                        .part()
                        .modelFile(base)
                        .rotationX(90)
                        .addModel())
                     .condition(WallPillarBlock.AXIS, new Axis[]{Axis.Z})
                     .end()
                     .part()
                     .modelFile(top)
                     .rotationX(90)
                     .addModel())
                  .condition(WallPillarBlock.AXIS, new Axis[]{Axis.Z})
                  .condition(PipeBlock.NORTH, new Boolean[]{false})
                  .end()
                  .part()
                  .modelFile(bottom)
                  .rotationX(90)
                  .addModel())
               .condition(WallPillarBlock.AXIS, new Axis[]{Axis.Z})
               .condition(PipeBlock.SOUTH, new Boolean[]{false})
               .end()
               .part()
               .modelFile(up)
               .rotationX(90)
               .addModel())
            .condition(WallPillarBlock.AXIS, new Axis[]{Axis.X, Axis.Y})
            .condition(PipeBlock.NORTH, new Boolean[]{true})
            .end()
            .part()
            .modelFile(down)
            .rotationX(90)
            .addModel())
         .condition(WallPillarBlock.AXIS, new Axis[]{Axis.X, Axis.Y})
         .condition(PipeBlock.SOUTH, new Boolean[]{true})
         .end();
   }

   private void slider() {
      ModelFile slider = this.models().getExistingFile(TwilightForestMod.prefix("block/slider"));
      ModelFile horizSlider = this.models().getExistingFile(TwilightForestMod.prefix("block/slider_horiz"));
      this.getVariantBuilder((Block)TFBlocks.SLIDER.get()).forAllStates(state -> {
         return switch ((Axis)state.getValue(SliderBlock.AXIS)) {
            case X -> ConfiguredModel.builder().modelFile(horizSlider).rotationX(90).rotationY(90).build();
            case Z -> ConfiguredModel.builder().modelFile(horizSlider).rotationX(90).build();
            default -> ConfiguredModel.builder().modelFile(slider).build();
         };
      });
   }

   private void towerBlocks() {
      ModelFile reappear = ((BlockModelBuilder)((BlockModelBuilder)this.make3LayerCubeAllSidesSame(
                  TFBlocks.REAPPEARING_BLOCK.getId().getPath(), CUTOUT, 0, 15, 10
               )
               .texture("all", TwilightForestMod.prefix("block/towerdev_reappearing_off")))
            .texture("all2", TwilightForestMod.prefix("block/tower_device_level_1/towerdev_reappearing_off_1")))
         .texture("all3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_reappearing_off_2"));
      ModelFile reappearActive = ((BlockModelBuilder)((BlockModelBuilder)this.make3LayerCubeAllSidesSame(
                  TFBlocks.REAPPEARING_BLOCK.getId().getPath() + "_active", CUTOUT, 0, 15, 10
               )
               .texture("all", TwilightForestMod.prefix("block/towerdev_reappearing_on")))
            .texture("all2", TwilightForestMod.prefix("block/tower_device_level_1/towerdev_reappearing_on_1")))
         .texture("all3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_reappearing_on_2"));
      ModelFile reappearVanished = this.make4x4x4SmallCube(TFBlocks.REAPPEARING_BLOCK.getId().getPath() + "_vanished")
         .texture("all", TwilightForestMod.prefix("block/towerdev_reappearing_trace_off"));
      ModelFile reappearVanishedActive = this.make4x4x4SmallCube(TFBlocks.REAPPEARING_BLOCK.getId().getPath() + "_vanished_active")
         .texture("all", TwilightForestMod.prefix("block/towerdev_reappearing_trace_on"));
      this.getVariantBuilder((Block)TFBlocks.REAPPEARING_BLOCK.get()).forAllStates(s -> {
         ModelFile model;
         if ((Boolean)s.getValue(VanishingBlock.VANISHED)) {
            model = s.getValue(VanishingBlock.ACTIVE) ? reappearVanishedActive : reappearVanished;
         } else {
            model = s.getValue(VanishingBlock.ACTIVE) ? reappearActive : reappear;
         }

         return ConfiguredModel.builder().modelFile(model).build();
      });
      ModelFile vanish = ((BlockModelBuilder)((BlockModelBuilder)this.make3LayerCubeAllSidesSame("vanishing_block", CUTOUT, 0, 15, 10)
               .texture("all", TwilightForestMod.prefix("block/towerdev_vanish_off")))
            .texture("all2", TwilightForestMod.prefix("block/tower_device_level_1/towerdev_vanish_off_1")))
         .texture("all3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_vanish_off_2"));
      ModelFile vanishActive = ((BlockModelBuilder)((BlockModelBuilder)this.make3LayerCubeAllSidesSame("vanishing_block_active", CUTOUT, 0, 15, 10)
               .texture("all", TwilightForestMod.prefix("block/towerdev_vanish_on")))
            .texture("all2", TwilightForestMod.prefix("block/tower_device_level_1/towerdev_vanish_on_1")))
         .texture("all3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_vanish_on_2"));
      this.getVariantBuilder((Block)TFBlocks.UNBREAKABLE_VANISHING_BLOCK.get())
         .forAllStates(state -> ConfiguredModel.builder().modelFile(state.getValue(VanishingBlock.ACTIVE) ? vanishActive : vanish).build());
      this.getVariantBuilder((Block)TFBlocks.VANISHING_BLOCK.get())
         .forAllStates(state -> ConfiguredModel.builder().modelFile(state.getValue(VanishingBlock.ACTIVE) ? vanishActive : vanish).build());
      ModelFile vanishLocked = ((BlockModelBuilder)((BlockModelBuilder)this.make3LayerCubeAllSidesSame(
                  TFBlocks.LOCKED_VANISHING_BLOCK.getId().getPath(), CUTOUT, 0, 15, 10
               )
               .texture("all", TwilightForestMod.prefix("block/towerdev_lock_on")))
            .texture("all2", TwilightForestMod.prefix("block/tower_device_level_1/towerdev_lock_on_1")))
         .texture("all3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_lock_on_2"));
      ModelFile vanishUnlocked = ((BlockModelBuilder)((BlockModelBuilder)this.make3LayerCubeAllSidesSame(
                  TFBlocks.LOCKED_VANISHING_BLOCK.getId().getPath() + "_unlocked", CUTOUT, 0, 15, 10
               )
               .texture("all", TwilightForestMod.prefix("block/towerdev_lock_off")))
            .texture("all2", TwilightForestMod.prefix("block/tower_device_level_1/towerdev_lock_off_1")))
         .texture("all3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_lock_off_2"));
      this.getVariantBuilder((Block)TFBlocks.LOCKED_VANISHING_BLOCK.get())
         .partialState()
         .with(LockedVanishingBlock.LOCKED, true)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(vanishLocked)});
      this.getVariantBuilder((Block)TFBlocks.LOCKED_VANISHING_BLOCK.get())
         .partialState()
         .with(LockedVanishingBlock.LOCKED, false)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(vanishUnlocked)});
      ModelFile ghastTrap = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.make3LayerCubeIdenticalSides1Bottom(
                              TFBlocks.GHAST_TRAP.getId().getPath(), 0, 10, 15, 10, 10
                           )
                           .texture("top", TwilightForestMod.prefix("block/towerdev_ghasttraplid_off")))
                        .texture("side", TwilightForestMod.prefix("block/towerdev_ghasttrap_off")))
                     .texture("bottom", TwilightForestMod.prefix("block/encased_towerwood")))
                  .texture("top2", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_ghasttraplid_off_1")))
               .texture("side2", TwilightForestMod.prefix("block/tower_device_level_1/towerdev_ghasttrap_off_1")))
            .texture("top3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_ghasttraplid_off_1")))
         .texture("side3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_ghasttrap_off_2"));
      ModelFile ghastTrapActive = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.make3LayerCubeIdenticalSides1Bottom(
                              TFBlocks.GHAST_TRAP.getId().getPath() + "_active", 0, 10, 15, 7, 10
                           )
                           .texture("top", TwilightForestMod.prefix("block/towerdev_ghasttraplid_on")))
                        .texture("side", TwilightForestMod.prefix("block/towerdev_ghasttrap_on")))
                     .texture("bottom", TwilightForestMod.prefix("block/encased_towerwood")))
                  .texture("top2", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_ghasttraplid_on_1")))
               .texture("side2", TwilightForestMod.prefix("block/tower_device_level_1/towerdev_ghasttrap_on_1")))
            .texture("top3", TwilightForestMod.prefix("block/tower_device_level_3/towerdev_ghasttraplid_on_2")))
         .texture("side3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_ghasttrap_on_2"));
      this.getVariantBuilder((Block)TFBlocks.GHAST_TRAP.get())
         .partialState()
         .with(GhastTrapBlock.ACTIVE, false)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(ghastTrap)});
      this.getVariantBuilder((Block)TFBlocks.GHAST_TRAP.get())
         .partialState()
         .with(GhastTrapBlock.ACTIVE, true)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(ghastTrapActive)});
      ModelFile builder = ((BlockModelBuilder)((BlockModelBuilder)this.make3LayerCubeAllSidesSame(
                  TFBlocks.CARMINITE_BUILDER.getId().getPath(), CUTOUT, 0, 15, 10
               )
               .texture("all", TwilightForestMod.prefix("block/towerdev_builder_off")))
            .texture("all2", TwilightForestMod.prefix("block/tower_device_level_1/towerdev_builder_off_1")))
         .texture("all3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_builder_off_2"));
      ModelFile builderActive = ((BlockModelBuilder)((BlockModelBuilder)this.make3LayerCubeAllSidesSame(
                  TFBlocks.CARMINITE_BUILDER.getId().getPath() + "_active", CUTOUT, 0, 15, 10
               )
               .texture("all", TwilightForestMod.prefix("block/towerdev_builder_on")))
            .texture("all2", TwilightForestMod.prefix("block/tower_device_level_1/towerdev_builder_on_1")))
         .texture("all3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_builder_on_2"));
      ModelFile builderTimeout = ((BlockModelBuilder)((BlockModelBuilder)this.make3LayerCubeAllSidesSame(
                  TFBlocks.CARMINITE_BUILDER.getId().getPath() + "_timeout", CUTOUT, 0, 10, 7
               )
               .texture("all", TwilightForestMod.prefix("block/towerdev_builder_timeout")))
            .texture("all2", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_builder_timeout_1")))
         .texture("all3", TwilightForestMod.prefix("block/tower_device_level_3/towerdev_builder_timeout_2"));
      this.getVariantBuilder((Block)TFBlocks.CARMINITE_BUILDER.get())
         .partialState()
         .with(BuilderBlock.STATE, TowerDeviceVariant.BUILDER_INACTIVE)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(builder)});
      this.getVariantBuilder((Block)TFBlocks.CARMINITE_BUILDER.get())
         .partialState()
         .with(BuilderBlock.STATE, TowerDeviceVariant.BUILDER_ACTIVE)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(builderActive)});
      this.getVariantBuilder((Block)TFBlocks.CARMINITE_BUILDER.get())
         .partialState()
         .with(BuilderBlock.STATE, TowerDeviceVariant.BUILDER_TIMEOUT)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(builderTimeout)});
      ModelFile built = ((BlockModelBuilder)this.make2LayerCubeAllSidesSame(TFBlocks.BUILT_BLOCK.getId().getPath(), CUTOUT, 15, 15, false)
            .texture("all", TwilightForestMod.prefix("block/towerdev_built_off")))
         .texture("all2", TwilightForestMod.prefix("block/tower_device_level_1/towerdev_builder_off_1"));
      ModelFile builtActive = ((BlockModelBuilder)this.make2LayerCubeAllSidesSame(TFBlocks.BUILT_BLOCK.getId().getPath() + "_active", CUTOUT, 15, 15, false)
            .texture("all", TwilightForestMod.prefix("block/towerdev_built_on")))
         .texture("all2", TwilightForestMod.prefix("block/tower_device_level_1/towerdev_builder_on_1"));
      this.getVariantBuilder((Block)TFBlocks.BUILT_BLOCK.get())
         .partialState()
         .with(TranslucentBuiltBlock.ACTIVE, false)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(built)});
      this.getVariantBuilder((Block)TFBlocks.BUILT_BLOCK.get())
         .partialState()
         .with(TranslucentBuiltBlock.ACTIVE, true)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(builtActive)});
      ModelFile antibuilder = ((BlockModelBuilder)((BlockModelBuilder)this.make3LayerCubeAllSidesSame(TFBlocks.ANTIBUILDER.getId().getPath(), CUTOUT, 0, 15, 10)
               .texture("all", TwilightForestMod.prefix("block/towerdev_antibuilder")))
            .texture("all2", TwilightForestMod.prefix("block/tower_device_level_1/towerdev_antibuilder_1")))
         .texture("all3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_antibuilder_2"));
      this.simpleBlock((Block)TFBlocks.ANTIBUILDER.get(), antibuilder);
      ModelFile antibuilt = ((BlockModelBuilder)this.make2LayerCubeAllSidesSame(TFBlocks.ANTIBUILT_BLOCK.getId().getPath(), CUTOUT, 0, 10, false)
            .texture("all", TwilightForestMod.prefix("block/towerdev_antibuilt")))
         .texture("all2", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_antibuilt_1"));
      this.simpleBlock((Block)TFBlocks.ANTIBUILT_BLOCK.get(), antibuilt);
      ModelFile reactor = ((BlockModelBuilder)((BlockModelBuilder)this.make3LayerCubeAllSidesSame(
                  TFBlocks.CARMINITE_REACTOR.getId().getPath(), CUTOUT, 0, 15, 10
               )
               .texture("all", TwilightForestMod.prefix("block/towerdev_reactor_off")))
            .texture("all2", TwilightForestMod.prefix("block/tower_device_level_1/towerdev_reactor_off_1")))
         .texture("all3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_reactor_off_2"));
      ModelFile reactorActive = ((BlockModelBuilder)((BlockModelBuilder)this.make3LayerCubeAllSidesSame(
                  TFBlocks.CARMINITE_REACTOR.getId().getPath() + "_active", CUTOUT, 0, 15, 10
               )
               .texture("all", TwilightForestMod.prefix("block/towerdev_reactor_on")))
            .texture("all2", TwilightForestMod.prefix("block/tower_device_level_1/towerdev_reactor_on_1")))
         .texture("all3", TwilightForestMod.prefix("block/tower_device_level_2/towerdev_reactor_on_2"));
      this.getVariantBuilder((Block)TFBlocks.CARMINITE_REACTOR.get())
         .partialState()
         .with(CarminiteReactorBlock.ACTIVE, false)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(reactor)});
      this.getVariantBuilder((Block)TFBlocks.CARMINITE_REACTOR.get())
         .partialState()
         .with(CarminiteReactorBlock.ACTIVE, true)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(reactorActive)});
   }

   private ModelFile pedestalModel(String name, String north, String south, String west, String east, boolean active) {
      BlockModelBuilder ret = (BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.makePedestal(name, active)
                     .texture("end", TwilightForestMod.prefix("block/pedestal/top")))
                  .texture("north", TwilightForestMod.prefix("block/pedestal/" + north + "_latent")))
               .texture("south", TwilightForestMod.prefix("block/pedestal/" + south + "_latent")))
            .texture("west", TwilightForestMod.prefix("block/pedestal/" + west + "_latent")))
         .texture("east", TwilightForestMod.prefix("block/pedestal/" + east + "_latent"));
      if (active) {
         ret = (BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)ret.texture(
                                    "end2", TwilightForestMod.prefix("block/pedestal/top_glow")
                                 ))
                                 .texture("north2", TwilightForestMod.prefix("block/pedestal/" + north + "_glow")))
                              .texture("south2", TwilightForestMod.prefix("block/pedestal/" + south + "_glow")))
                           .texture("west2", TwilightForestMod.prefix("block/pedestal/" + west + "_glow")))
                        .texture("east2", TwilightForestMod.prefix("block/pedestal/" + east + "_glow")))
                     .texture("north3", TwilightForestMod.prefix("block/pedestal/" + north)))
                  .texture("south3", TwilightForestMod.prefix("block/pedestal/" + south)))
               .texture("west3", TwilightForestMod.prefix("block/pedestal/" + west)))
            .texture("east3", TwilightForestMod.prefix("block/pedestal/" + east));
      }

      return ret;
   }

   private void trophyPedestal() {
      String baseName = TFBlocks.TROPHY_PEDESTAL.getId().getPath();
      ModelFile latent0 = this.pedestalModel(baseName, "naga", "lich", "hydra", "ur-ghast", false);
      ModelFile latent1 = this.pedestalModel(baseName + "_1", "snow_queen", "naga", "lich", "hydra", false);
      ModelFile latent2 = this.pedestalModel(baseName + "_2", "ur-ghast", "snow_queen", "naga", "lich", false);
      ModelFile latent3 = this.pedestalModel(baseName + "_3", "hydra", "ur-ghast", "snow_queen", "naga", false);
      ModelFile latent4 = this.pedestalModel(baseName + "_4", "lich", "hydra", "ur-ghast", "snow_queen", false);
      List<ConfiguredModel> latentModels = new ArrayList<>();

      for (ModelFile f : Arrays.asList(latent0, latent1, latent2, latent3, latent4)) {
         latentModels.add(new ConfiguredModel(f, 0, 0, false));
         latentModels.add(new ConfiguredModel(f, 0, 90, false));
         latentModels.add(new ConfiguredModel(f, 0, 180, false));
         latentModels.add(new ConfiguredModel(f, 0, 270, false));
      }

      this.getVariantBuilder((Block)TFBlocks.TROPHY_PEDESTAL.get())
         .partialState()
         .with(TrophyPedestalBlock.ACTIVE, false)
         .setModels(latentModels.toArray(new ConfiguredModel[0]));
      ModelFile active0 = this.pedestalModel(baseName + "_active", "naga", "lich", "hydra", "ur-ghast", true);
      ModelFile active1 = this.pedestalModel(baseName + "_active_1", "snow_queen", "naga", "lich", "hydra", true);
      ModelFile active2 = this.pedestalModel(baseName + "_active_2", "ur-ghast", "snow_queen", "naga", "lich", true);
      ModelFile active3 = this.pedestalModel(baseName + "_active_3", "hydra", "ur-ghast", "snow_queen", "naga", true);
      ModelFile active4 = this.pedestalModel(baseName + "_active_4", "lich", "hydra", "ur-ghast", "snow_queen", true);
      List<ConfiguredModel> activeModels = new ArrayList<>();

      for (ModelFile f : Arrays.asList(active0, active1, active2, active3, active4)) {
         activeModels.add(new ConfiguredModel(f, 0, 0, false));
         activeModels.add(new ConfiguredModel(f, 0, 90, false));
         activeModels.add(new ConfiguredModel(f, 0, 180, false));
         activeModels.add(new ConfiguredModel(f, 0, 270, false));
      }

      this.getVariantBuilder((Block)TFBlocks.TROPHY_PEDESTAL.get())
         .partialState()
         .with(TrophyPedestalBlock.ACTIVE, true)
         .setModels(activeModels.toArray(new ConfiguredModel[0]));
   }

   private void thorns() {
      UnmodifiableIterator var1 = ImmutableList.of(TFBlocks.GREEN_THORNS, TFBlocks.BROWN_THORNS, TFBlocks.BURNT_THORNS).iterator();

      while (var1.hasNext()) {
         DeferredHolder<Block, Block> block = (DeferredHolder<Block, Block>)var1.next();
         String path = block.getId().getPath();
         ResourceLocation sideTexture = TwilightForestMod.prefix("block/" + path + "_side");
         ResourceLocation endTexture = TwilightForestMod.prefix("block/" + path + "_top");
         ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(path, TwilightForestMod.prefix("block/thorns_main")))
                  .renderType(CUTOUT))
               .texture("side", sideTexture))
            .texture("end", endTexture);
         ModelFile thorns = ((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(path + "_thorns", TwilightForestMod.prefix("block/thorns")))
               .renderType(CUTOUT))
            .texture("side", sideTexture);
         ModelFile top = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                     .withExistingParent(path + "_top", TwilightForestMod.prefix("block/thorns_section_top")))
                  .renderType(CUTOUT))
               .texture("side", sideTexture))
            .texture("end", endTexture);
         ModelFile bottom = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                     .withExistingParent(path + "_bottom", TwilightForestMod.prefix("block/thorns_section_bottom")))
                  .renderType(CUTOUT))
               .texture("side", sideTexture))
            .texture("end", endTexture);
         ModelFile section = ((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(path + "_no_section", TwilightForestMod.prefix("block/thorns_no_section")))
               .renderType(CUTOUT))
            .texture("side", sideTexture);
         ModelFile noSectionAlt = ((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(path + "_no_section_alt", TwilightForestMod.prefix("block/thorns_no_section_alt")))
               .renderType(CUTOUT))
            .texture("side", sideTexture);
         ((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)this.getMultipartBuilder(
                                                                              (Block)block.get()
                                                                           )
                                                                           .part()
                                                                           .modelFile(thorns)
                                                                           .addModel())
                                                                        .condition(RotatedPillarBlock.AXIS, new Axis[]{Axis.Y})
                                                                        .end()
                                                                        .part()
                                                                        .modelFile(thorns)
                                                                        .rotationX(90)
                                                                        .addModel())
                                                                     .condition(RotatedPillarBlock.AXIS, new Axis[]{Axis.Z})
                                                                     .end()
                                                                     .part()
                                                                     .modelFile(thorns)
                                                                     .rotationX(90)
                                                                     .rotationY(90)
                                                                     .addModel())
                                                                  .condition(RotatedPillarBlock.AXIS, new Axis[]{Axis.X})
                                                                  .end()
                                                                  .part()
                                                                  .modelFile(top)
                                                                  .rotationX(90)
                                                                  .addModel())
                                                               .condition(PipeBlock.UP, new Boolean[]{true})
                                                               .end()
                                                               .part()
                                                               .modelFile(section)
                                                               .rotationX(270)
                                                               .addModel())
                                                            .condition(PipeBlock.UP, new Boolean[]{false})
                                                            .condition(RotatedPillarBlock.AXIS, new Axis[]{Axis.Z, Axis.Y})
                                                            .end()
                                                            .part()
                                                            .modelFile(section)
                                                            .rotationX(270)
                                                            .rotationY(90)
                                                            .addModel())
                                                         .condition(PipeBlock.UP, new Boolean[]{false})
                                                         .condition(RotatedPillarBlock.AXIS, new Axis[]{Axis.X})
                                                         .end()
                                                         .part()
                                                         .modelFile(bottom)
                                                         .rotationX(90)
                                                         .addModel())
                                                      .condition(PipeBlock.DOWN, new Boolean[]{true})
                                                      .end()
                                                      .part()
                                                      .modelFile(section)
                                                      .rotationX(90)
                                                      .addModel())
                                                   .condition(PipeBlock.DOWN, new Boolean[]{false})
                                                   .condition(RotatedPillarBlock.AXIS, new Axis[]{Axis.Z, Axis.Y})
                                                   .end()
                                                   .part()
                                                   .modelFile(section)
                                                   .rotationX(90)
                                                   .rotationY(90)
                                                   .addModel())
                                                .condition(PipeBlock.DOWN, new Boolean[]{false})
                                                .condition(RotatedPillarBlock.AXIS, new Axis[]{Axis.X})
                                                .end()
                                                .part()
                                                .modelFile(top)
                                                .rotationY(270)
                                                .addModel())
                                             .condition(PipeBlock.EAST, new Boolean[]{true})
                                             .end()
                                             .part()
                                             .modelFile(section)
                                             .rotationY(90)
                                             .addModel())
                                          .condition(PipeBlock.EAST, new Boolean[]{false})
                                          .condition(RotatedPillarBlock.AXIS, new Axis[]{Axis.Y, Axis.X})
                                          .end()
                                          .part()
                                          .modelFile(noSectionAlt)
                                          .rotationY(90)
                                          .addModel())
                                       .condition(PipeBlock.EAST, new Boolean[]{false})
                                       .condition(RotatedPillarBlock.AXIS, new Axis[]{Axis.Z})
                                       .end()
                                       .part()
                                       .modelFile(bottom)
                                       .rotationY(270)
                                       .addModel())
                                    .condition(PipeBlock.WEST, new Boolean[]{true})
                                    .end()
                                    .part()
                                    .modelFile(section)
                                    .rotationY(270)
                                    .addModel())
                                 .condition(PipeBlock.WEST, new Boolean[]{false})
                                 .condition(RotatedPillarBlock.AXIS, new Axis[]{Axis.Y, Axis.X})
                                 .end()
                                 .part()
                                 .modelFile(noSectionAlt)
                                 .rotationY(270)
                                 .addModel())
                              .condition(PipeBlock.WEST, new Boolean[]{false})
                              .condition(RotatedPillarBlock.AXIS, new Axis[]{Axis.Z})
                              .end()
                              .part()
                              .modelFile(top)
                              .addModel())
                           .condition(PipeBlock.SOUTH, new Boolean[]{true})
                           .end()
                           .part()
                           .modelFile(section)
                           .rotationY(180)
                           .addModel())
                        .condition(PipeBlock.SOUTH, new Boolean[]{false})
                        .condition(RotatedPillarBlock.AXIS, new Axis[]{Axis.Y, Axis.Z})
                        .end()
                        .part()
                        .modelFile(noSectionAlt)
                        .rotationY(180)
                        .addModel())
                     .condition(PipeBlock.SOUTH, new Boolean[]{false})
                     .condition(RotatedPillarBlock.AXIS, new Axis[]{Axis.X})
                     .end()
                     .part()
                     .modelFile(bottom)
                     .addModel())
                  .condition(PipeBlock.NORTH, new Boolean[]{true})
                  .end()
                  .part()
                  .modelFile(section)
                  .addModel())
               .condition(PipeBlock.NORTH, new Boolean[]{false})
               .condition(RotatedPillarBlock.AXIS, new Axis[]{Axis.Y, Axis.Z})
               .end()
               .part()
               .modelFile(noSectionAlt)
               .addModel())
            .condition(PipeBlock.NORTH, new Boolean[]{false})
            .condition(RotatedPillarBlock.AXIS, new Axis[]{Axis.X})
            .end();
      }
   }

   private void thornRose() {
      ModelFile rose = ((BlockModelBuilder)this.models().cross(TFBlocks.THORN_ROSE.getId().getPath(), this.blockTexture((Block)TFBlocks.THORN_ROSE.get())))
         .renderType(CUTOUT);
      this.getVariantBuilder((Block)TFBlocks.THORN_ROSE.get()).forAllStates(state -> {
         return switch ((Direction)state.getValue(DirectionalBlock.FACING)) {
            case EAST -> ConfiguredModel.builder().modelFile(rose).rotationY(90).rotationX(90).build();
            case SOUTH -> ConfiguredModel.builder().modelFile(rose).rotationX(270).build();
            case WEST -> ConfiguredModel.builder().modelFile(rose).rotationY(90).rotationX(270).build();
            case UP -> ConfiguredModel.builder().modelFile(rose).build();
            case DOWN -> ConfiguredModel.builder().modelFile(rose).rotationX(180).build();
            case NORTH -> ConfiguredModel.builder().modelFile(rose).rotationX(90).build();
            default -> throw new MatchException(null, null);
         };
      });
   }

   private void auroraBlocks() {
      int variants = 16;
      BlockModelBuilder[] models = new BlockModelBuilder[variants];

      for (int i = 0; i < variants; i++) {
         models[i] = (BlockModelBuilder)this.makeTintedBlockAll(TFBlocks.AURORA_BLOCK.getId().getPath() + "_" + i, SOLID)
            .texture("all", TwilightForestMod.prefix("block/" + TFBlocks.AURORA_BLOCK.getId().getPath() + "_" + i));
      }

      this.simpleBlock(
         (Block)TFBlocks.AURORA_BLOCK.get(),
         ((NoiseVaryingModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(TFBlocks.AURORA_BLOCK.getId().getPath(), ResourceLocation.withDefaultNamespace("block/block")))
               .customLoader(NoiseVaryingModelBuilder::new))
            .addAll(models)
            .end()
      );
      ModelFile pillarModel = ((BlockModelBuilder)this.makeTintedBlockColumn(TFBlocks.AURORA_PILLAR.getId().getPath())
            .texture("end", TwilightForestMod.prefix("block/" + TFBlocks.AURORA_PILLAR.getId().getPath() + "_top")))
         .texture("side", this.blockTexture((Block)TFBlocks.AURORA_PILLAR.get()));
      this.axisBlock((RotatedPillarBlock)TFBlocks.AURORA_PILLAR.get(), pillarModel, pillarModel);
      ModelFile slabModel = ((BlockModelBuilder)((BlockModelBuilder)this.makeTintedSlab(TFBlocks.AURORA_SLAB.getId().getPath())
               .texture("bottom", TwilightForestMod.prefix("block/" + TFBlocks.AURORA_PILLAR.getId().getPath() + "_top")))
            .texture("top", TwilightForestMod.prefix("block/" + TFBlocks.AURORA_PILLAR.getId().getPath() + "_top")))
         .texture("side", TwilightForestMod.prefix("block/" + TFBlocks.AURORA_SLAB.getId().getPath() + "_side"));
      ModelFile doubleSlabModel = ((BlockModelBuilder)this.makeTintedBlockColumn(TFBlocks.AURORA_SLAB.getId().getPath() + "_double")
            .texture("end", TwilightForestMod.prefix("block/" + TFBlocks.AURORA_PILLAR.getId().getPath() + "_top")))
         .texture("side", TwilightForestMod.prefix("block/" + TFBlocks.AURORA_SLAB.getId().getPath() + "_side"));
      this.getVariantBuilder((Block)TFBlocks.AURORA_SLAB.get())
         .partialState()
         .with(SlabBlock.TYPE, SlabType.BOTTOM)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(slabModel)});
      this.getVariantBuilder((Block)TFBlocks.AURORA_SLAB.get())
         .partialState()
         .with(SlabBlock.TYPE, SlabType.TOP)
         .setModels(ConfiguredModel.builder().uvLock(true).rotationX(180).modelFile(slabModel).build());
      this.getVariantBuilder((Block)TFBlocks.AURORA_SLAB.get())
         .partialState()
         .with(SlabBlock.TYPE, SlabType.DOUBLE)
         .setModels(new ConfiguredModel[]{new ConfiguredModel(doubleSlabModel)});
      ModelFile auroraGlass = ((ConnectedTextureBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                        .withExistingParent(TFBlocks.AURORALIZED_GLASS.getId().getPath(), "block/block"))
                     .texture("overlay_texture", this.blockTexture((Block)TFBlocks.AURORALIZED_GLASS.get())))
                  .texture("overlay_connected", this.blockTexture((Block)TFBlocks.AURORALIZED_GLASS.get()).withSuffix("_ct")))
               .renderType(TRANSLUCENT))
            .customLoader(ConnectedTextureBuilder::begin))
         .connectsTo((Block)TFBlocks.AURORALIZED_GLASS.get())
         .setOverlayTintIndex(0)
         .end();
      this.simpleBlock((Block)TFBlocks.AURORALIZED_GLASS.get(), auroraGlass);
   }

   private void mazestone() {
      ResourceLocation plainTex = this.blockTexture((Block)TFBlocks.MAZESTONE.get());
      ModelFile mazeStone = this.models().cubeAll(TFBlocks.MAZESTONE.getId().getPath(), plainTex);
      this.simpleBlock(
         (Block)TFBlocks.MAZESTONE.get(),
         ConfiguredModel.builder().rotationX(90).rotationY(90).modelFile(mazeStone).nextModel().rotationX(270).rotationY(270).modelFile(mazeStone).build()
      );
      this.simpleBlock((Block)TFBlocks.MAZESTONE_BRICK.get());
      ModelFile chiseled = this.models().cubeColumn(TFBlocks.CUT_MAZESTONE.getId().getPath(), this.blockTexture((Block)TFBlocks.CUT_MAZESTONE.get()), plainTex);
      this.simpleBlock((Block)TFBlocks.CUT_MAZESTONE.get(), chiseled);
      ModelFile decorative = this.models()
         .cubeColumn(TFBlocks.DECORATIVE_MAZESTONE.getId().getPath(), this.blockTexture((Block)TFBlocks.DECORATIVE_MAZESTONE.get()), plainTex);
      this.simpleBlock((Block)TFBlocks.DECORATIVE_MAZESTONE.get(), decorative);
      this.simpleBlock((Block)TFBlocks.CRACKED_MAZESTONE.get());
      this.simpleBlock((Block)TFBlocks.MOSSY_MAZESTONE.get());
      ResourceLocation brickTex = this.blockTexture((Block)TFBlocks.MAZESTONE_BRICK.get());
      ModelFile mosaic = this.models()
         .cubeColumn(TFBlocks.MAZESTONE_MOSAIC.getId().getPath(), brickTex, this.blockTexture((Block)TFBlocks.MAZESTONE_MOSAIC.get()));
      this.simpleBlock((Block)TFBlocks.MAZESTONE_MOSAIC.get(), mosaic);
      ModelFile border = this.models()
         .cubeColumn(TFBlocks.MAZESTONE_BORDER.getId().getPath(), brickTex, this.blockTexture((Block)TFBlocks.MAZESTONE_BORDER.get()));
      this.simpleBlock((Block)TFBlocks.MAZESTONE_BORDER.get(), border);
      this.simpleBlock(
         (Block)TFBlocks.MAZE_SLIME_BLOCK.get(),
         ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                     .withExistingParent(TFBlocks.MAZE_SLIME_BLOCK.getId().getPath(), ResourceLocation.withDefaultNamespace("block/slime_block")))
                  .renderType(TRANSLUCENT))
               .texture("particle", this.blockTexture((Block)TFBlocks.MAZE_SLIME_BLOCK.get())))
            .texture("texture", this.blockTexture((Block)TFBlocks.MAZE_SLIME_BLOCK.get()))
      );
   }

   private void lilyPad(Block b) {
      String baseName = this.name(b);
      ResourceLocation parent = TwilightForestMod.prefix("block/huge_lily_pad");
      ModelFile[] models = new ModelFile[4];

      for (int i = 0; i < models.length; i++) {
         models[i] = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(baseName + "_" + i, parent))
                  .renderType(CUTOUT))
               .texture("texture", TwilightForestMod.prefix("block/huge_lily_pad_" + i)))
            .texture("particle", "#texture");
      }

      Map<HugeLilypadPiece, ModelFile> north = ImmutableMap.of(
         HugeLilypadPiece.NW, models[1], HugeLilypadPiece.NE, models[0], HugeLilypadPiece.SE, models[3], HugeLilypadPiece.SW, models[2]
      );
      Map<HugeLilypadPiece, ModelFile> south = ImmutableMap.of(
         HugeLilypadPiece.NW, models[3], HugeLilypadPiece.NE, models[2], HugeLilypadPiece.SE, models[1], HugeLilypadPiece.SW, models[0]
      );
      Map<HugeLilypadPiece, ModelFile> west = ImmutableMap.of(
         HugeLilypadPiece.NW, models[0], HugeLilypadPiece.NE, models[3], HugeLilypadPiece.SE, models[2], HugeLilypadPiece.SW, models[1]
      );
      Map<HugeLilypadPiece, ModelFile> east = ImmutableMap.of(
         HugeLilypadPiece.NW, models[2], HugeLilypadPiece.NE, models[1], HugeLilypadPiece.SE, models[0], HugeLilypadPiece.SW, models[3]
      );
      this.getVariantBuilder(b).forAllStates(state -> {
         int rotY;
         Map<HugeLilypadPiece, ModelFile> m;
         switch ((Direction)state.getValue(HugeLilyPadBlock.FACING)) {
            case EAST:
               rotY = 90;
               m = east;
               break;
            case SOUTH:
               rotY = 180;
               m = south;
               break;
            case WEST:
               rotY = 270;
               m = west;
               break;
            default:
               rotY = 0;
               m = north;
         }

         ModelFile model = m.get(state.getValue(HugeLilyPadBlock.PIECE));
         return ConfiguredModel.builder().rotationY(rotY).modelFile(model).build();
      });
   }

   private void candelabra() {
      ModelFile floorModel = this.buildCandelabra();
      ModelFile wallModel = this.buildWallCandelabra();
      this.getVariantBuilder((Block)TFBlocks.CANDELABRA.get())
         .forAllStatesExcept(
            state -> {
               Direction direction = (Direction)state.getValue(CandelabraBlock.FACING);
               boolean onWall = (Boolean)state.getValue(CandelabraBlock.ON_WALL);
               return ConfiguredModel.builder().modelFile(onWall ? wallModel : floorModel).rotationY((int)direction.toYRot()).build();
            },
            new Property[]{
               CandelabraBlock.LIGHTING,
               BlockStateProperties.WATERLOGGED,
               (Property)CandelabraBlock.CANDLES.get(0),
               (Property)CandelabraBlock.CANDLES.get(1),
               (Property)CandelabraBlock.CANDLES.get(2)
            }
         );
   }

   private void perFaceBlock(Block b, ResourceLocation inside, ResourceLocation outside) {
      ModelFile modelInside = ((BlockModelBuilder)this.models()
            .withExistingParent(this.name(b) + "_inside", ResourceLocation.withDefaultNamespace("block/template_single_face")))
         .texture("texture", inside);
      ModelFile modelOutside = ((BlockModelBuilder)this.models()
            .withExistingParent(this.name(b) + "_outside", ResourceLocation.withDefaultNamespace("block/template_single_face")))
         .texture("texture", outside);
      ((PartBuilder)this.getMultipartBuilder(b).part().modelFile(modelInside).addModel()).condition(HugeMushroomBlock.NORTH, new Boolean[]{false}).end();
      ((PartBuilder)this.getMultipartBuilder(b).part().modelFile(modelOutside).addModel()).condition(HugeMushroomBlock.NORTH, new Boolean[]{true}).end();
      ((PartBuilder)this.getMultipartBuilder(b).part().modelFile(modelInside).uvLock(true).rotationY(180).addModel())
         .condition(HugeMushroomBlock.SOUTH, new Boolean[]{false})
         .end();
      ((PartBuilder)this.getMultipartBuilder(b).part().modelFile(modelOutside).uvLock(true).rotationY(180).addModel())
         .condition(HugeMushroomBlock.SOUTH, new Boolean[]{true})
         .end();
      ((PartBuilder)this.getMultipartBuilder(b).part().modelFile(modelInside).uvLock(true).rotationY(270).addModel())
         .condition(HugeMushroomBlock.WEST, new Boolean[]{false})
         .end();
      ((PartBuilder)this.getMultipartBuilder(b).part().modelFile(modelOutside).uvLock(true).rotationY(270).addModel())
         .condition(HugeMushroomBlock.WEST, new Boolean[]{true})
         .end();
      ((PartBuilder)this.getMultipartBuilder(b).part().modelFile(modelInside).uvLock(true).rotationY(90).addModel())
         .condition(HugeMushroomBlock.EAST, new Boolean[]{false})
         .end();
      ((PartBuilder)this.getMultipartBuilder(b).part().modelFile(modelOutside).uvLock(true).rotationY(90).addModel())
         .condition(HugeMushroomBlock.EAST, new Boolean[]{true})
         .end();
      ((PartBuilder)this.getMultipartBuilder(b).part().modelFile(modelInside).uvLock(true).rotationX(270).addModel())
         .condition(HugeMushroomBlock.UP, new Boolean[]{false})
         .end();
      ((PartBuilder)this.getMultipartBuilder(b).part().modelFile(modelOutside).uvLock(true).rotationX(270).addModel())
         .condition(HugeMushroomBlock.UP, new Boolean[]{true})
         .end();
      ((PartBuilder)this.getMultipartBuilder(b).part().modelFile(modelInside).uvLock(true).rotationX(90).addModel())
         .condition(HugeMushroomBlock.DOWN, new Boolean[]{false})
         .end();
      ((PartBuilder)this.getMultipartBuilder(b).part().modelFile(modelOutside).uvLock(true).rotationX(90).addModel())
         .condition(HugeMushroomBlock.DOWN, new Boolean[]{true})
         .end();
   }

   private void terrorcotta() {
      this.rotationallySpecialColumn((Block)TFBlocks.TERRORCOTTA_ARCS.get());
      this.getVariantBuilder((Block)TFBlocks.TERRORCOTTA_CURVES.get())
         .forAllStates(
            state -> ConfiguredModel.builder()
               .modelFile(this.makeTerrorcottaCurvesModel("terrorcotta_curves", ((Direction)state.getValue(GlazedTerracottaBlock.FACING)).get2DDataValue()))
               .build()
         );
      this.getVariantBuilder((Block)TFBlocks.TERRORCOTTA_LINES.get())
         .forAllStates(
            state -> ConfiguredModel.builder()
               .modelFile(this.makeTerrorcottaLinesModel("terrorcotta_lines", (Boolean)state.getValue(BinaryRotatedBlock.ROTATED)))
               .build()
         );
   }

   private BlockModelBuilder makeTerrorcottaCurvesModel(String type, int rotation) {
      return (BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                              .withExistingParent(type + "_" + rotation * 90, ResourceLocation.withDefaultNamespace("block/cube")))
                           .texture("particle", TwilightForestMod.prefix("block/" + type + "_a")))
                        .texture("up", TwilightForestMod.prefix("block/" + type + curvesSuffixForFacing(rotation, Direction.UP))))
                     .texture("down", TwilightForestMod.prefix("block/" + type + curvesSuffixForFacing(rotation, Direction.DOWN))))
                  .texture("south", TwilightForestMod.prefix("block/" + type + curvesSuffixForFacing(rotation, Direction.SOUTH))))
               .texture("west", TwilightForestMod.prefix("block/" + type + curvesSuffixForFacing(rotation, Direction.WEST))))
            .texture("north", TwilightForestMod.prefix("block/" + type + curvesSuffixForFacing(rotation, Direction.NORTH))))
         .texture("east", TwilightForestMod.prefix("block/" + type + curvesSuffixForFacing(rotation, Direction.EAST)));
   }

   @NotNull
   private static String curvesSuffixForFacing(int blockRotation, Direction blockFace) {
      int rotationForFace = switch (blockFace) {
         case EAST -> {
            switch (blockRotation) {
               case 1:
                  yield 3;
               case 2:
                  yield 0;
               case 3:
                  yield 2;
               default:
                  yield 1;
            }
         }
         case SOUTH -> {
            switch (blockRotation) {
               case 1:
                  yield 1;
               case 2:
                  yield 3;
               case 3:
                  yield 0;
               default:
                  yield 2;
            }
         }
         case WEST -> {
            switch (blockRotation) {
               case 1:
                  yield 0;
               case 2:
                  yield 3;
               case 3:
                  yield 1;
               default:
                  yield 2;
            }
         }
         case UP -> 2 - blockRotation;
         case DOWN -> 3 + blockRotation;
         case NORTH -> {
            switch (blockRotation) {
               case 1:
                  yield 2;
               case 2:
                  yield 0;
               case 3:
                  yield 3;
               default:
                  yield 1;
            }
         }
         default -> throw new MatchException(null, null);
      };

      return switch (Math.floorMod(rotationForFace, 4)) {
         case 1 -> "_b";
         case 2 -> "_c";
         case 3 -> "_d";
         default -> "_a";
      };
   }

   private BlockModelBuilder makeTerrorcottaLinesModel(String type, boolean rotated) {
      return (BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                              .withExistingParent(type + "_" + (rotated ? 90 : 0), ResourceLocation.withDefaultNamespace("block/cube")))
                           .texture("particle", TwilightForestMod.prefix("block/" + type + "_a")))
                        .texture("up", TwilightForestMod.prefix("block/" + type + linesSuffixForFacing(rotated, Direction.UP))))
                     .texture("down", TwilightForestMod.prefix("block/" + type + linesSuffixForFacing(rotated, Direction.DOWN))))
                  .texture("south", TwilightForestMod.prefix("block/" + type + linesSuffixForFacing(rotated, Direction.SOUTH))))
               .texture("west", TwilightForestMod.prefix("block/" + type + linesSuffixForFacing(rotated, Direction.WEST))))
            .texture("north", TwilightForestMod.prefix("block/" + type + linesSuffixForFacing(rotated, Direction.NORTH))))
         .texture("east", TwilightForestMod.prefix("block/" + type + linesSuffixForFacing(rotated, Direction.EAST)));
   }

   @NotNull
   private static String linesSuffixForFacing(boolean blockRotation, Direction blockFace) {
      Vec3i normal = blockFace.getNormal();
      int axisDirection = normal.getX() + normal.getY() + normal.getZ();
      return axisDirection > 0 == (blockFace.getAxis() == Axis.Z != blockRotation) ? "_a" : "_b";
   }

   private void hollowLogs(
      Block originalLog,
      Block strippedLog,
      DeferredHolder<Block, HorizontalHollowLogBlock> horizontalHollowLog,
      DeferredHolder<Block, VerticalHollowLogBlock> verticalHollowLog,
      DeferredHolder<Block, ClimbableHollowLogBlock> climbableHollowLog,
      ModelFile emptyLog,
      ModelFile mossLog,
      ModelFile grassLog,
      ModelFile snowLog,
      ModelFile hollowLog,
      ModelFile vineLog,
      ModelFile ladderLog
   ) {
      ResourceLocation top = ResourceLocation.withDefaultNamespace("block/" + this.name(originalLog) + "_top");
      ResourceLocation side = ResourceLocation.withDefaultNamespace("block/" + this.name(originalLog));
      ResourceLocation inner = ResourceLocation.withDefaultNamespace("block/" + this.name(strippedLog));
      this.getVariantBuilder((Block)horizontalHollowLog.get())
         .forAllStates(
            state -> {
               return ConfiguredModel.builder()
                  .modelFile(
                     ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)(switch ((HollowLogVariants.Horizontal)state.getValue(
                                    HorizontalHollowLogBlock.VARIANT
                                 )) {
                                    case MOSS -> (BlockModelBuilder)((BlockModelBuilder)this.models()
                                          .getBuilder(horizontalHollowLog.getId().getPath() + "_moss"))
                                       .parent(mossLog);
                                    case MOSS_AND_GRASS -> (BlockModelBuilder)((BlockModelBuilder)this.models()
                                          .getBuilder(horizontalHollowLog.getId().getPath() + "_moss_grass"))
                                       .parent(grassLog);
                                    case SNOW -> (BlockModelBuilder)((BlockModelBuilder)this.models()
                                          .getBuilder(horizontalHollowLog.getId().getPath() + "_snow"))
                                       .parent(snowLog);
                                    default -> (BlockModelBuilder)((BlockModelBuilder)this.models().getBuilder(horizontalHollowLog.getId().getPath()))
                                       .parent(emptyLog);
                                 })
                                 .renderType(CUTOUT))
                              .texture("top", top))
                           .texture("side", side))
                        .texture("inner", inner)
                  )
                  .rotationY(state.getValue(HorizontalHollowLogBlock.HORIZONTAL_AXIS) == Axis.X ? 90 : 0)
                  .build();
            }
         );
      this.simpleBlock(
         (Block)verticalHollowLog.get(),
         ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models().getBuilder(verticalHollowLog.getId().getPath()))
                     .parent(hollowLog))
                  .texture("top", top))
               .texture("side", side))
            .texture("inner", inner)
      );
      this.getVariantBuilder((Block)climbableHollowLog.get())
         .forAllStates(
            state -> {
               return ConfiguredModel.builder()
                  .modelFile(
                     ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)(switch ((HollowLogVariants.Climbable)state.getValue(
                                    ClimbableHollowLogBlock.VARIANT
                                 )) {
                                    case VINE -> (BlockModelBuilder)((BlockModelBuilder)this.models()
                                          .getBuilder(climbableHollowLog.getId().getPath() + "_vine"))
                                       .parent(vineLog);
                                    case LADDER, LADDER_WATERLOGGED -> (BlockModelBuilder)((BlockModelBuilder)this.models()
                                          .getBuilder(climbableHollowLog.getId().getPath() + "_ladder"))
                                       .parent(ladderLog);
                                 })
                                 .renderType(CUTOUT))
                              .texture("top", top))
                           .texture("side", side))
                        .texture("inner", inner)
                  )
                  .rotationY((int)((Direction)state.getValue(ClimbableHollowLogBlock.FACING)).toYRot())
                  .uvLock(true)
                  .build();
            }
         );
   }

   private void hollowLogs(
      DeferredHolder<Block, RotatedPillarBlock> originalLog,
      DeferredHolder<Block, RotatedPillarBlock> strippedLog,
      DeferredHolder<Block, HorizontalHollowLogBlock> horizontalHollowLog,
      DeferredHolder<Block, VerticalHollowLogBlock> verticalHollowLog,
      DeferredHolder<Block, ClimbableHollowLogBlock> climbableHollowLog,
      ModelFile emptyLog,
      ModelFile mossLog,
      ModelFile grassLog,
      ModelFile snowLog,
      ModelFile hollowLog,
      ModelFile vineLog,
      ModelFile ladderLog
   ) {
      ResourceLocation top = TwilightForestMod.prefix("block/" + originalLog.getId().getPath() + "_top");
      ResourceLocation side = TwilightForestMod.prefix("block/" + originalLog.getId().getPath());
      ResourceLocation inner = TwilightForestMod.prefix("block/" + strippedLog.getId().getPath());
      this.getVariantBuilder((Block)horizontalHollowLog.get())
         .forAllStates(
            state -> {
               return ConfiguredModel.builder()
                  .modelFile(
                     ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)(switch ((HollowLogVariants.Horizontal)state.getValue(
                                    HorizontalHollowLogBlock.VARIANT
                                 )) {
                                    case MOSS -> (BlockModelBuilder)((BlockModelBuilder)this.models()
                                          .getBuilder(horizontalHollowLog.getId().getPath() + "_moss"))
                                       .parent(mossLog);
                                    case MOSS_AND_GRASS -> (BlockModelBuilder)((BlockModelBuilder)this.models()
                                          .getBuilder(horizontalHollowLog.getId().getPath() + "_moss_grass"))
                                       .parent(grassLog);
                                    case SNOW -> (BlockModelBuilder)((BlockModelBuilder)this.models()
                                          .getBuilder(horizontalHollowLog.getId().getPath() + "_snow"))
                                       .parent(snowLog);
                                    default -> (BlockModelBuilder)((BlockModelBuilder)this.models().getBuilder(horizontalHollowLog.getId().getPath()))
                                       .parent(emptyLog);
                                 })
                                 .renderType(CUTOUT))
                              .texture("top", top))
                           .texture("side", side))
                        .texture("inner", inner)
                  )
                  .rotationY(state.getValue(HorizontalHollowLogBlock.HORIZONTAL_AXIS) == Axis.X ? 90 : 0)
                  .build();
            }
         );
      this.simpleBlock(
         (Block)verticalHollowLog.get(),
         ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models().getBuilder(verticalHollowLog.getId().getPath()))
                     .parent(hollowLog))
                  .texture("top", top))
               .texture("side", side))
            .texture("inner", inner)
      );
      this.getVariantBuilder((Block)climbableHollowLog.get())
         .forAllStates(
            state -> {
               return ConfiguredModel.builder()
                  .modelFile(
                     ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)(switch ((HollowLogVariants.Climbable)state.getValue(
                                    ClimbableHollowLogBlock.VARIANT
                                 )) {
                                    case VINE -> (BlockModelBuilder)((BlockModelBuilder)this.models()
                                          .getBuilder(climbableHollowLog.getId().getPath() + "_vine"))
                                       .parent(vineLog);
                                    case LADDER, LADDER_WATERLOGGED -> (BlockModelBuilder)((BlockModelBuilder)this.models()
                                          .getBuilder(climbableHollowLog.getId().getPath() + "_ladder"))
                                       .parent(ladderLog);
                                 })
                                 .renderType(CUTOUT))
                              .texture("top", top))
                           .texture("side", side))
                        .texture("inner", inner)
                  )
                  .rotationY((int)((Direction)state.getValue(ClimbableHollowLogBlock.FACING)).toYRot())
                  .uvLock(true)
                  .build();
            }
         );
   }

   @Nonnull
   public String getName() {
      return "TwilightForest blockstates and block models";
   }
}
