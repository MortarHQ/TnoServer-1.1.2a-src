package gardensofthedead.neoforge.datagen;

import gardensofthedead.GardensOfTheDead;
import gardensofthedead.block.SoulSporeBaseBlock;
import gardensofthedead.block.SoulSporeBlock;
import gardensofthedead.neoforge.datagen.registry.ModBlockFamilies;
import gardensofthedead.registry.ModBlocks;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.BlockFamily.Variant;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder.PartBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockStateProvider extends net.neoforged.neoforge.client.model.generators.BlockStateProvider {
   private static final String CUTOUT = "cutout";
   private static final String BLOCK_MODEL = "block/block";
   private static final ResourceLocation WHISTLECANE_0 = GardensOfTheDead.id("block/" + getName((Block)ModBlocks.WHISTLECANE.get()) + "0");
   private static final ResourceLocation WHISTLECANE_1 = GardensOfTheDead.id("block/" + getName((Block)ModBlocks.WHISTLECANE.get()) + "1");

   public BlockStateProvider(PackOutput generator, ExistingFileHelper existingFileHelper) {
      super(generator, "gardens_of_the_dead", existingFileHelper);
   }

   protected void registerStatesAndModels() {
      ModBlockFamilies.getAllFamilies().forEach(this::generateModels);
      this.createCrossModels();
      this.addSoulSpore();
      this.addWhistlecane();
      this.simplePlantWithItem((Block)ModBlocks.SOULBLIGHT_FUNGUS.get());
      this.simplePlantWithItem((Block)ModBlocks.SOULBLIGHT_SPROUTS.get());
      this.simplePlantWithItem((Block)ModBlocks.BLISTERCROWN.get());
      this.randomlyMirroredDoublePlantWithItem((Block)ModBlocks.TALL_BLISTERCROWN.get());
      this.logWithItem((RotatedPillarBlock)ModBlocks.SOULBLIGHT_STEM.get());
      this.logWithItem((RotatedPillarBlock)ModBlocks.STRIPPED_SOULBLIGHT_STEM.get());
      this.woodWithItem((RotatedPillarBlock)ModBlocks.SOULBLIGHT_HYPHAE.get());
      this.woodWithItem((RotatedPillarBlock)ModBlocks.STRIPPED_SOULBLIGHT_HYPHAE.get());
      this.simpleBlockWithItem((Block)ModBlocks.BLIGHTWART_BLOCK.get());
      this.pottedPlantWithCustomTexture((Block)ModBlocks.POTTED_SOUL_SPORE.get());
      this.pottedPlantWithCustomTexture((Block)ModBlocks.POTTED_GLOWING_SOUL_SPORE.get());
      this.pottedPlant((Block)ModBlocks.POTTED_SOULBLIGHT_FUNGUS.get());
      this.pottedPlantWithCustomTexture((Block)ModBlocks.POTTED_SOULBLIGHT_SPROUTS.get());
      this.pottedPlant((Block)ModBlocks.POTTED_BLISTERCROWN.get());
      this.addPottedWhistleCane();
      this.axisBlockUvLocked((RotatedPillarBlock)ModBlocks.WHISTLECANE_BLOCK.get());
      this.addWhistlecaneFence();
      this.signWithItem(
         (Block)ModBlocks.SOULBLIGHT_HANGING_SIGN.get(),
         (Block)ModBlocks.SOULBLIGHT_WALL_HANGING_SIGN.get(),
         this.blockTexture((Block)ModBlocks.STRIPPED_SOULBLIGHT_STEM.get())
      );
      this.signWithItem(
         (Block)ModBlocks.WHISTLECANE_HANGING_SIGN.get(),
         (Block)ModBlocks.WHISTLECANE_WALL_HANGING_SIGN.get(),
         this.blockTexture((Block)ModBlocks.WHISTLECANE_BLOCK.get())
      );
   }

   private void generateModels(BlockFamily family) {
      Map<Variant, Block> variants = family.getVariants();
      ResourceLocation texture = this.blockTexture(family.getBaseBlock());
      this.simpleBlockWithItem(family.getBaseBlock());
      if (variants.containsKey(Variant.SLAB)) {
         this.slabWithItem((SlabBlock)variants.get(Variant.SLAB), texture);
      }

      if (variants.containsKey(Variant.STAIRS)) {
         this.stairsWithItem((StairBlock)variants.get(Variant.STAIRS), texture);
      }

      if (variants.containsKey(Variant.PRESSURE_PLATE)) {
         this.pressurePlateWithItem((PressurePlateBlock)variants.get(Variant.PRESSURE_PLATE), texture);
      }

      if (variants.containsKey(Variant.BUTTON)) {
         this.buttonWithItem((ButtonBlock)variants.get(Variant.BUTTON), texture);
      }

      if (variants.containsKey(Variant.FENCE)) {
         this.fenceWithItem((FenceBlock)variants.get(Variant.FENCE), texture);
      }

      if (variants.containsKey(Variant.FENCE_GATE)) {
         this.fenceGateWithItem((FenceGateBlock)variants.get(Variant.FENCE_GATE), texture);
      }

      if (variants.containsKey(Variant.SIGN) && variants.containsKey(Variant.WALL_SIGN)) {
         this.signWithItem(variants.get(Variant.SIGN), variants.get(Variant.WALL_SIGN), texture);
      }

      if (variants.containsKey(Variant.DOOR)) {
         this.doorWithItem((DoorBlock)variants.get(Variant.DOOR));
      }

      if (variants.containsKey(Variant.TRAPDOOR)) {
         this.trapdoorWithItem((TrapDoorBlock)variants.get(Variant.TRAPDOOR));
      }
   }

   private void addSoulSpore() {
      this.getVariantBuilder((Block)ModBlocks.SOUL_SPORE.get())
         .partialState()
         .with(SoulSporeBlock.TOP, true)
         .with(SoulSporeBlock.DIRECTION, Direction.UP)
         .addModels(new ConfiguredModel[]{this.cross("soul_spore_top"), this.crossMirrored("soul_spore_top")})
         .partialState()
         .with(SoulSporeBlock.TOP, true)
         .with(SoulSporeBlock.DIRECTION, Direction.DOWN)
         .addModels(new ConfiguredModel[]{this.crossFlipped("soul_spore_top"), this.crossMirroredFlipped("soul_spore_top")})
         .partialState()
         .with(SoulSporeBlock.TOP, false)
         .addModels(new ConfiguredModel[]{this.cross("soul_spore"), this.crossMirrored("soul_spore")});
      this.generatedItem((Block)ModBlocks.SOUL_SPORE.get());
      this.getVariantBuilder((Block)ModBlocks.GLOWING_SOUL_SPORE.get())
         .partialState()
         .with(SoulSporeBaseBlock.DIRECTION, Direction.UP)
         .addModels(new ConfiguredModel[]{this.cross("glowing_soul_spore"), this.crossMirrored("glowing_soul_spore")})
         .partialState()
         .with(SoulSporeBaseBlock.DIRECTION, Direction.DOWN)
         .addModels(new ConfiguredModel[]{this.crossFlipped("glowing_soul_spore"), this.crossMirroredFlipped("glowing_soul_spore")});
      this.generatedItem((Block)ModBlocks.GLOWING_SOUL_SPORE.get());
   }

   private void addWhistlecaneFence() {
      String whistlecane = getName((Block)ModBlocks.WHISTLECANE.get());
      String whistlecaneFenceBeams = getName((Block)ModBlocks.WHISTLECANE_FENCE.get()) + "_side";
      ResourceLocation beamTexture = GardensOfTheDead.id("block/" + whistlecaneFenceBeams);
      String fenceGate = getName((Block)ModBlocks.WHISTLECANE_FENCE_GATE.get());
      String gateParent = "template_custom_fence_gate";
      String gateTexture = "block/" + fenceGate;
      String planksTexture = "block/" + getName((Block)ModBlocks.WHISTLECANE_PLANKS.get());
      ModelFile gate = ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(fenceGate, ResourceLocation.parse(gateParent)))
            .texture("texture", gateTexture))
         .texture("particle", planksTexture);
      ModelFile gateOpen = ((BlockModelBuilder)((BlockModelBuilder)this.models()
               .withExistingParent(fenceGate + "_open", ResourceLocation.parse(gateParent + "_open")))
            .texture("texture", gateTexture))
         .texture("particle", planksTexture);
      ModelFile gateWall = ((BlockModelBuilder)((BlockModelBuilder)this.models()
               .withExistingParent(fenceGate + "_wall", ResourceLocation.parse(gateParent + "_wall")))
            .texture("texture", gateTexture))
         .texture("particle", planksTexture);
      ModelFile gateWallOpen = ((BlockModelBuilder)((BlockModelBuilder)this.models()
               .withExistingParent(fenceGate + "_wall_open", ResourceLocation.parse(gateParent + "_wall_open")))
            .texture("texture", gateTexture))
         .texture("particle", planksTexture);
      this.getVariantBuilder((Block)ModBlocks.WHISTLECANE_FENCE_GATE.get()).forAllStatesExcept(state -> {
         ModelFile model = gate;
         if ((Boolean)state.getValue(FenceGateBlock.IN_WALL)) {
            model = gateWall;
         }

         if ((Boolean)state.getValue(FenceGateBlock.OPEN)) {
            model = model == gateWall ? gateWallOpen : gateOpen;
         }

         return ConfiguredModel.builder().modelFile(model).rotationY((int)((Direction)state.getValue(FenceGateBlock.FACING)).toYRot()).build();
      }, new Property[]{FenceGateBlock.POWERED});
      this.simpleBlockItem((Block)ModBlocks.WHISTLECANE_FENCE_GATE.get());
      ModelFile post = this.whistlecaneModel(whistlecane + "_post", 0, WHISTLECANE_1);
      MultiPartBlockStateBuilder builder = ((PartBuilder)this.getMultipartBuilder((Block)ModBlocks.WHISTLECANE_FENCE.get()).part().modelFile(post).addModel())
         .end();
      PipeBlock.PROPERTY_BY_DIRECTION
         .forEach(
            (dir, value) -> {
               if (dir.getAxis().isHorizontal()) {
                  String directionName = dir.getName().toLowerCase();
                  String modelName = whistlecaneFenceBeams + "_" + directionName;
                  String parent = "custom_fence_side_" + directionName;
                  BlockModelBuilder model = (BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(modelName, parent))
                        .texture("texture", "#beams"))
                     .texture("beams", beamTexture);
                  ((PartBuilder)builder.part().modelFile(model).addModel()).condition(value, new Boolean[]{true});
               }
            }
         );
      this.createWhistlecaneFenceItemModel();
   }

   private void createWhistlecaneFenceItemModel() {
      ResourceLocation beamTexture = GardensOfTheDead.id("block/" + getName((Block)ModBlocks.WHISTLECANE_FENCE.get()) + "_side");
      ItemModelBuilder builder = (ItemModelBuilder)((ItemModelBuilder)((ItemModelBuilder)((ItemModelBuilder)((ItemModelBuilder)((ItemModelBuilder)this.itemModels()
                        .withExistingParent(getName((Block)ModBlocks.WHISTLECANE_FENCE.get()), "block/block"))
                     .transforms()
                     .transform(ItemDisplayContext.GUI)
                     .rotation(30.0F, 135.0F, 0.0F)
                     .scale(0.625F)
                     .end()
                     .transform(ItemDisplayContext.FIXED)
                     .rotation(0.0F, 90.0F, 0.0F)
                     .translation(0.0F, 0.0F, 0.0F)
                     .scale(0.5F)
                     .end()
                     .end())
                  .ao(false))
               .texture("side", WHISTLECANE_1))
            .texture("top", WHISTLECANE_0))
         .texture("beams", beamTexture);
      this.addWhistlecanePost(builder, 0, -6);
      this.addWhistlecanePost(builder, 0, 6);

      for (int height : Set.of(6, 12)) {
         builder.element()
            .from(7.0F, height, 4.0F)
            .to(9.0F, height + 3, 12.0F)
            .face(Direction.EAST)
            .end()
            .face(Direction.WEST)
            .end()
            .face(Direction.UP)
            .end()
            .face(Direction.DOWN)
            .end()
            .faces((direction, faceBuilder) -> {
               if (direction.getAxis() == Axis.X) {
                  faceBuilder.uvs(8.0F, 0.0F, 16.0F, 3.0F);
               } else {
                  int v1 = direction == Direction.UP ? 7 : 15;
                  int v2 = direction == Direction.UP ? 15 : 7;
                  faceBuilder.uvs(11.0F, v1, 13.0F, v2);
               }

               faceBuilder.texture("#beams");
            });

         for (boolean isLeft : Set.of(true, false)) {
            int start = isLeft ? -2 : 16;
            int end = isLeft ? 0 : 18;
            builder.element()
               .from(7.0F, height, start)
               .to(9.0F, height + 3, end)
               .face(Direction.EAST)
               .end()
               .face(Direction.WEST)
               .end()
               .face(Direction.UP)
               .end()
               .face(Direction.DOWN)
               .end()
               .faces((direction, faceBuilder) -> {
                  if (direction.getAxis() == Axis.X) {
                     int offSet = isLeft ? 0 : 6;
                     faceBuilder.uvs(8 + offSet, 0.0F, 10 + offSet, 3.0F);
                  } else {
                     faceBuilder.uvs(11.0F, 13.0F, 13.0F, 15.0F);
                  }
               })
               .face(isLeft ? Direction.NORTH : Direction.SOUTH)
               .uvs(13.0F, 4.0F, 15.0F, 7.0F)
               .end()
               .faces((direction, faceBuilder) -> faceBuilder.texture("#beams"));
         }
      }
   }

   private void addWhistlecane() {
      String name = getName((Block)ModBlocks.WHISTLECANE.get());
      ResourceLocation leafTexture0 = GardensOfTheDead.id("block/" + name + "_leaves0");
      ResourceLocation leafTexture1 = GardensOfTheDead.id("block/" + name + "_leaves1");
      String model0Name = name + "0";
      String model1Name = name + "1";
      String model2Name = name + "2";
      BlockModelBuilder model0 = this.whistlecaneModel(model0Name, 0, WHISTLECANE_0);
      BlockModelBuilder model1 = this.whistlecaneModel(model1Name, 0, WHISTLECANE_1);
      BlockModelBuilder model2 = this.whistlecaneModel(model2Name, 6, WHISTLECANE_1);
      model0.texture("leaf", leafTexture0);
      this.addWhistlecaneLeaf(model0, Direction.WEST, 5, 0);
      model1.texture("leaf", leafTexture0);
      this.addWhistlecaneLeaf(model1, Direction.NORTH, 9, 0);
      this.addWhistlecaneLeaf(model1, Direction.SOUTH, 15, 8);
      model2.texture("leaf", leafTexture1);
      this.addWhistlecaneLeaf(model2, Direction.NORTH, 8, 0);
      this.addWhistlecaneLeaf(model2, Direction.EAST, 11, 8);
      ConfiguredModel[] models = ConfiguredModel.builder()
         .modelFile(model0)
         .rotationY(0)
         .nextModel()
         .modelFile(model0)
         .rotationY(90)
         .nextModel()
         .modelFile(model0)
         .rotationY(180)
         .nextModel()
         .modelFile(model0)
         .rotationY(270)
         .nextModel()
         .modelFile(model1)
         .rotationY(0)
         .nextModel()
         .modelFile(model1)
         .rotationY(90)
         .nextModel()
         .modelFile(model1)
         .rotationY(180)
         .nextModel()
         .modelFile(model1)
         .rotationY(270)
         .nextModel()
         .modelFile(model2)
         .rotationY(0)
         .nextModel()
         .modelFile(model2)
         .rotationY(90)
         .nextModel()
         .modelFile(model2)
         .rotationY(180)
         .nextModel()
         .modelFile(model2)
         .rotationY(270)
         .build();
      this.simpleBlock((Block)ModBlocks.WHISTLECANE.get(), models);
      this.generatedItem(((Block)ModBlocks.WHISTLECANE.get()).asItem(), GardensOfTheDead.id("item/" + name));
   }

   private void addPottedWhistleCane() {
      String pottedWhistleCane = getName((Block)ModBlocks.POTTED_WHISTLECANE.get());
      ResourceLocation whistlecaneTexture = this.blockTexture(pottedWhistleCane);
      ResourceLocation flowerpotTexture = this.blockTexture(Blocks.FLOWER_POT);
      BlockModelBuilder model = (BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                           .getBuilder(pottedWhistleCane))
                        .texture("whistlecane", whistlecaneTexture))
                     .texture("flowerpot", flowerpotTexture))
                  .texture("particle", "#flowerpot"))
               .ao(false))
            .element()
            .from(5.0F, 0.0F, 5.0F)
            .to(11.0F, 6.0F, 11.0F)
            .face(Direction.DOWN)
            .uvs(5.0F, 5.0F, 6.0F, 11.0F)
            .cullface(Direction.DOWN)
            .end()
            .textureAll("#flowerpot")
            .end())
         .element()
         .from(6.0F, 6.0F, 6.0F)
         .to(10.0F, 16.0F, 10.0F)
         .face(Direction.NORTH)
         .end()
         .face(Direction.EAST)
         .end()
         .face(Direction.SOUTH)
         .end()
         .face(Direction.WEST)
         .end()
         .faces((direction, faceBuilder) -> faceBuilder.uvs(6.0F, 0.0F, 10.0F, 10.0F))
         .face(Direction.UP)
         .uvs(10.0F, 0.0F, 14.0F, 4.0F)
         .cullface(Direction.UP)
         .end()
         .faces((direction, faceBuilder) -> faceBuilder.texture("#whistlecane"))
         .end();
      model.texture("leaf", "#whistlecane");
      this.addWhistlecaneLeaf(model, Direction.NORTH, 9, 0, 2);
      model.renderType("cutout");
      this.simpleBlock((Block)ModBlocks.POTTED_WHISTLECANE.get(), model);
   }

   private BlockModelBuilder whistlecaneModel(String modelName, int u1, ResourceLocation sideTexture) {
      BlockModelBuilder model = (BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                     .withExistingParent(modelName, "block/block"))
                  .texture("top", WHISTLECANE_0))
               .texture("side", sideTexture))
            .texture("particle", "#side"))
         .renderType("cutout");
      this.addWhistlecanePost(model, u1, 0);
      return model;
   }

   private void addWhistlecanePost(ModelBuilder<?> builder, int u1, int zOffset) {
      builder.element().from(5.0F, 0.0F, 5 + zOffset).to(11.0F, 16.0F, 11 + zOffset).allFaces((direction, faceBuilder) -> {
         if (direction.getAxis() == Axis.Y) {
            faceBuilder.uvs(6.0F, 0.0F, 12.0F, 6.0F).cullface(direction).texture("#top");
         } else {
            faceBuilder.uvs(u1, 0.0F, u1 + 6, 16.0F).texture("#side");
         }
      }).end();
   }

   private void addWhistlecaneLeaf(BlockModelBuilder model, Direction direction, int height, int u1) {
      this.addWhistlecaneLeaf(model, direction, height, u1, 3);
   }

   private void addWhistlecaneLeaf(BlockModelBuilder model, Direction direction, int height, int u1, int caneWidth) {
      Axis axis = direction.getAxis();
      AxisDirection axisDirection = direction.getAxisDirection();
      float angle = 22.5F;
      int textureWidth = caneWidth + 1;
      int pivot = 8 + axisDirection.getStep() * caneWidth;
      int x1 = axis == Axis.Z ? 8 - textureWidth : pivot;
      int z1 = axis == Axis.X ? 8 - textureWidth : pivot;
      int x2 = axis == Axis.Z ? 8 + textureWidth : pivot;
      int z2 = axis == Axis.X ? 8 + textureWidth : pivot;
      model.element()
         .from(x1, height, z1)
         .to(x2, height + 16, z2)
         .face(direction)
         .texture("#leaf")
         .uvs(u1, 0.0F, u1 + textureWidth * 2, 16.0F)
         .ao(false)
         .end()
         .face(direction.getOpposite())
         .texture("#leaf")
         .uvs(u1 + textureWidth * 2, 0.0F, u1, 16.0F)
         .ao(false)
         .end()
         .rotation()
         .origin(x1, height, z1)
         .axis(direction.getClockWise().getAxis())
         .angle(axisDirection.getStep() * (axis == Axis.X ? -1 : 1) * angle)
         .end()
         .end();
   }

   private void createCrossModels() {
      ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models().getBuilder("cross_mirrored")).ao(false))
               .texture("particle", "#cross"))
            .element()
            .from(0.8F, 0.0F, 8.0F)
            .to(15.2F, 16.0F, 8.0F)
            .shade(false)
            .rotation()
            .origin(8.0F, 8.0F, 8.0F)
            .axis(Axis.Y)
            .angle(45.0F)
            .rescale(true)
            .end()
            .face(Direction.NORTH)
            .uvs(16.0F, 0.0F, 0.0F, 16.0F)
            .texture("#cross")
            .end()
            .face(Direction.SOUTH)
            .uvs(16.0F, 0.0F, 0.0F, 16.0F)
            .texture("#cross")
            .end()
            .end())
         .element()
         .from(8.0F, 0.0F, 0.8F)
         .to(8.0F, 16.0F, 15.2F)
         .shade(false)
         .rotation()
         .origin(8.0F, 8.0F, 8.0F)
         .axis(Axis.Y)
         .angle(45.0F)
         .rescale(true)
         .end()
         .face(Direction.WEST)
         .uvs(16.0F, 0.0F, 0.0F, 16.0F)
         .texture("#cross")
         .end()
         .face(Direction.EAST)
         .uvs(16.0F, 0.0F, 0.0F, 16.0F)
         .texture("#cross")
         .end()
         .end();
      ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models().getBuilder("cross_flipped")).ao(false))
               .texture("particle", "#cross"))
            .element()
            .from(0.8F, 0.0F, 8.0F)
            .to(15.2F, 16.0F, 8.0F)
            .shade(false)
            .rotation()
            .origin(8.0F, 8.0F, 8.0F)
            .axis(Axis.Y)
            .angle(45.0F)
            .rescale(true)
            .end()
            .face(Direction.NORTH)
            .uvs(0.0F, 16.0F, 16.0F, 0.0F)
            .texture("#cross")
            .end()
            .face(Direction.SOUTH)
            .uvs(0.0F, 16.0F, 16.0F, 0.0F)
            .texture("#cross")
            .end()
            .end())
         .element()
         .from(8.0F, 0.0F, 0.8F)
         .to(8.0F, 16.0F, 15.2F)
         .shade(false)
         .rotation()
         .origin(8.0F, 8.0F, 8.0F)
         .axis(Axis.Y)
         .angle(45.0F)
         .rescale(true)
         .end()
         .face(Direction.WEST)
         .uvs(0.0F, 16.0F, 16.0F, 0.0F)
         .texture("#cross")
         .end()
         .face(Direction.EAST)
         .uvs(0.0F, 16.0F, 16.0F, 0.0F)
         .texture("#cross")
         .end()
         .end();
      ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models().getBuilder("cross_mirrored_flipped")).ao(false))
               .texture("particle", "#cross"))
            .element()
            .from(0.8F, 0.0F, 8.0F)
            .to(15.2F, 16.0F, 8.0F)
            .shade(false)
            .rotation()
            .origin(8.0F, 8.0F, 8.0F)
            .axis(Axis.Y)
            .angle(45.0F)
            .rescale(true)
            .end()
            .face(Direction.NORTH)
            .uvs(16.0F, 16.0F, 0.0F, 0.0F)
            .texture("#cross")
            .end()
            .face(Direction.SOUTH)
            .uvs(16.0F, 16.0F, 0.0F, 0.0F)
            .texture("#cross")
            .end()
            .end())
         .element()
         .from(8.0F, 0.0F, 0.8F)
         .to(8.0F, 16.0F, 15.2F)
         .shade(false)
         .rotation()
         .origin(8.0F, 8.0F, 8.0F)
         .axis(Axis.Y)
         .angle(45.0F)
         .rescale(true)
         .end()
         .face(Direction.WEST)
         .uvs(16.0F, 16.0F, 0.0F, 0.0F)
         .texture("#cross")
         .end()
         .face(Direction.EAST)
         .uvs(16.0F, 16.0F, 0.0F, 0.0F)
         .texture("#cross")
         .end()
         .end();
   }

   public void slabWithItem(SlabBlock block, ResourceLocation texture) {
      this.slabWithItem(block, texture, texture);
   }

   public void slabWithItem(SlabBlock block, ResourceLocation sideTexture, ResourceLocation topTexture) {
      this.slabBlock(block, sideTexture, sideTexture, topTexture, topTexture);
      this.simpleBlockItem(block);
   }

   public void stairsWithItem(StairBlock block, ResourceLocation texture) {
      this.stairsWithItem(block, texture, texture);
   }

   public void stairsWithItem(StairBlock block, ResourceLocation sideTexture, ResourceLocation topTexture) {
      this.stairsBlock(block, sideTexture, topTexture, topTexture);
      this.simpleBlockItem(block);
   }

   public void doorWithItem(DoorBlock block) {
      this.doorWithItem(block, "cutout");
   }

   public void doorWithItem(DoorBlock block, String renderType) {
      this.doorBlockWithRenderType(block, this.blockTexture(getName(block) + "_bottom"), this.blockTexture(getName(block) + "_top"), renderType);
      this.itemModels().basicItem(block.asItem());
   }

   public void trapdoorWithItem(TrapDoorBlock block) {
      this.trapdoorWithItem(block, "cutout");
   }

   public void trapdoorWithItem(TrapDoorBlock trapDoor, String renderType) {
      this.trapdoorBlockWithRenderType(trapDoor, this.blockTexture(getName(trapDoor)), true, renderType);
      this.itemModels().withExistingParent(getName(trapDoor), this.modLoc("%s/%s_bottom".formatted("block", getName(trapDoor))));
   }

   public void buttonWithItem(ButtonBlock block, ResourceLocation texture) {
      this.buttonBlock(block, texture);
      this.itemModels().buttonInventory(getName(block), texture);
   }

   public void pressurePlateWithItem(PressurePlateBlock block, ResourceLocation texture) {
      this.pressurePlateBlock(block, texture);
      this.simpleBlockItem(block);
   }

   public void fenceWithItem(FenceBlock block, ResourceLocation texture) {
      this.fenceBlock(block, texture);
      this.itemModels().fenceInventory(getName(block), texture);
   }

   public void fenceGateWithItem(FenceGateBlock block, ResourceLocation texture) {
      this.fenceGateBlock(block, texture);
      this.simpleBlockItem(block);
   }

   public void signWithItem(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
      ModelFile sign = this.models().sign(getName(signBlock), texture);
      this.simpleBlock(signBlock, sign);
      this.simpleBlock(wallSignBlock, sign);
      this.itemModels().basicItem(signBlock.asItem());
   }

   private void logWithItem(RotatedPillarBlock wood) {
      String name = getName(wood);
      ResourceLocation side = this.blockTexture(name);
      ResourceLocation top = this.blockTexture(name + "_top");
      this.axisBlock(wood, side, top);
      this.simpleBlockItem(wood);
   }

   private ModelFile cubeColumnUvLocked(RotatedPillarBlock block, Axis axis) {
      String name = getName(block);
      String axisName = axis.getName().toLowerCase(Locale.ROOT);
      String parent = "%s/cube_column_uv_locked_%s".formatted("block", axisName);
      String modelName = name + "_" + axisName;
      ResourceLocation side = this.blockTexture(name);
      ResourceLocation end = this.blockTexture(name + "_top");
      return ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(modelName, parent)).texture("side", side)).texture("end", end);
   }

   private void axisBlockUvLocked(RotatedPillarBlock block) {
      String name = getName(block);
      ModelFile modelX = this.cubeColumnUvLocked(block, Axis.X);
      ModelFile modelY = this.cubeColumnUvLocked(block, Axis.Y);
      ModelFile modelZ = this.cubeColumnUvLocked(block, Axis.Z);
      ((VariantBlockStateBuilder)((VariantBlockStateBuilder)this.getVariantBuilder(block)
               .partialState()
               .with(RotatedPillarBlock.AXIS, Axis.X)
               .modelForState()
               .modelFile(modelX)
               .addModel())
            .partialState()
            .with(RotatedPillarBlock.AXIS, Axis.Y)
            .modelForState()
            .modelFile(modelY)
            .addModel())
         .partialState()
         .with(RotatedPillarBlock.AXIS, Axis.Z)
         .modelForState()
         .modelFile(modelZ)
         .addModel();
      this.itemModels().withExistingParent(name, modelY.getLocation());
   }

   private void woodWithItem(RotatedPillarBlock wood) {
      String name = getName(wood).replace("wood", "log").replace("hyphae", "stem");
      ResourceLocation side = this.blockTexture(name);
      this.axisBlock(wood, side, side);
      this.simpleBlockItem(wood);
   }

   private void pottedPlant(Block pottedPlant) {
      String id = getName(pottedPlant).replace("potted_", "");
      this.pottedPlant(pottedPlant, id);
   }

   private void pottedPlantWithCustomTexture(Block pottedPlant) {
      this.pottedPlant(pottedPlant, getName(pottedPlant));
   }

   private void pottedPlant(Block pottedPlant, String textureName) {
      this.simpleBlock(
         pottedPlant,
         ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(getName(pottedPlant), "flower_pot_cross"))
               .texture("plant", this.blockTexture(textureName)))
            .renderType("cutout")
      );
   }

   private void simplePlantWithItem(Block block) {
      this.simpleBlock(block, new ConfiguredModel[]{this.cross(getName(block))});
      this.generatedItem(block);
   }

   private void randomlyMirroredDoublePlantWithItem(Block block) {
      String topTexture = getName(block) + "_top";
      String bottomTexture = getName(block) + "_bottom";
      this.getVariantBuilder(block)
         .partialState()
         .with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)
         .addModels(new ConfiguredModel[]{this.cross(topTexture), this.crossMirrored(topTexture)})
         .partialState()
         .with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
         .addModels(new ConfiguredModel[]{this.cross(bottomTexture), this.crossMirrored(bottomTexture)});
      this.generatedItem(block.asItem(), GardensOfTheDead.id("block/" + topTexture));
   }

   private void simpleBlockWithItem(Block block) {
      this.simpleBlock(block);
      this.simpleBlockItem(block);
   }

   private void simpleBlockItem(Block block) {
      String name = getName(block);
      this.itemModels().withExistingParent(name, this.modLoc("block/" + name));
   }

   private void generatedItem(Block block) {
      String name = getName(block);
      this.generatedItem(block.asItem(), GardensOfTheDead.id("block/" + name));
   }

   private void generatedItem(Item item, ResourceLocation texture) {
      ((ItemModelBuilder)((ItemModelBuilder)this.itemModels().getBuilder(BuiltInRegistries.ITEM.getKey(item).toString()))
            .parent(new UncheckedModelFile("item/generated")))
         .texture("layer0", texture);
   }

   private ConfiguredModel cross(String textureName) {
      return new ConfiguredModel(((BlockModelBuilder)this.models().cross(textureName, this.blockTexture(textureName))).renderType("cutout"));
   }

   private ConfiguredModel crossMirrored(String textureName) {
      ResourceLocation cross = this.blockTexture(textureName);
      return new ConfiguredModel(
         ((BlockModelBuilder)this.models().singleTexture(textureName + "_mirrored", this.modLoc("block/cross_mirrored"), "cross", cross)).renderType("cutout")
      );
   }

   private ConfiguredModel crossFlipped(String textureName) {
      ResourceLocation cross = this.blockTexture(textureName);
      return new ConfiguredModel(
         ((BlockModelBuilder)this.models().singleTexture(textureName + "_flipped", this.modLoc("block/cross_flipped"), "cross", cross)).renderType("cutout")
      );
   }

   private ConfiguredModel crossMirroredFlipped(String textureName) {
      ResourceLocation cross = this.blockTexture(textureName);
      return new ConfiguredModel(
         ((BlockModelBuilder)this.models().singleTexture(textureName + "_mirrored_flipped", this.modLoc("block/cross_mirrored_flipped"), "cross", cross))
            .renderType("cutout")
      );
   }

   private ResourceLocation blockTexture(String textureName) {
      return this.modLoc("block/" + textureName);
   }

   private static String getName(Block block) {
      return BuiltInRegistries.BLOCK.getKey(block).getPath();
   }
}
