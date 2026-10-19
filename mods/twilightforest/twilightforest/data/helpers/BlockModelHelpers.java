package twilightforest.data.helpers;

import java.util.function.Consumer;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder.PartBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import twilightforest.TwilightForestMod;
import twilightforest.block.BanisterBlock;
import twilightforest.block.DryingRackBlock;
import twilightforest.enums.BanisterShape;

public abstract class BlockModelHelpers extends BlockStateProvider {
   protected static final ResourceLocation SOLID = ResourceLocation.withDefaultNamespace("solid");
   protected static final ResourceLocation CUTOUT = ResourceLocation.withDefaultNamespace("cutout");
   protected static final ResourceLocation CUTOUT_MIPPED = ResourceLocation.withDefaultNamespace("cutout_mipped");
   protected static final ResourceLocation TRANSLUCENT = ResourceLocation.withDefaultNamespace("translucent");

   public BlockModelHelpers(PackOutput output, ExistingFileHelper exFileHelper) {
      super(output, "twilightforest", exFileHelper);
   }

   protected void builtinEntity(Block b, String particle) {
      this.simpleBlock(
         b,
         ((BlockModelBuilder)((BlockModelBuilder)this.models().getBuilder(this.name(b))).parent(new UncheckedModelFile("builtin/entity")))
            .texture("particle", particle)
      );
   }

   protected void simpleBlockExisting(Block b) {
      this.simpleBlock(b, new ConfiguredModel[]{new ConfiguredModel(this.models().getExistingFile(TwilightForestMod.prefix(this.name(b))))});
   }

   public void simpleBlockWithRenderType(Block block, ResourceLocation type) {
      this.simpleBlock(block, ((BlockModelBuilder)this.models().cubeAll(this.name(block), this.blockTexture(block))).renderType(type));
   }

   protected void singleBlockBoilerPlate(Block b, String parent, Consumer<BlockModelBuilder> modelCustomizer) {
      BlockModelBuilder bModel = (BlockModelBuilder)this.models().withExistingParent(this.name(b), parent);
      modelCustomizer.accept(bModel);
      this.simpleBlock(b, bModel);
   }

   protected void logWoodSapling(RotatedPillarBlock log, RotatedPillarBlock slog, RotatedPillarBlock wood, RotatedPillarBlock swood, Block sapling) {
      this.logBlock(log);
      this.logBlock(slog);
      ResourceLocation sideTex = this.blockTexture(log);
      this.axisBlock(wood, sideTex, sideTex);
      ResourceLocation sSideTex = this.blockTexture(slog);
      this.axisBlock(swood, sSideTex, sSideTex);
      ResourceLocation saplingTex = TwilightForestMod.prefix("block/" + this.name(sapling));
      this.simpleBlock(sapling, ((BlockModelBuilder)this.models().cross(this.name(sapling), saplingTex)).renderType(CUTOUT));
   }

   protected void plankBlocks(
      String variant,
      Block plank,
      Block slab,
      StairBlock stair,
      Block button,
      Block fence,
      Block gate,
      Block plate,
      DoorBlock door,
      TrapDoorBlock trapdoor,
      BanisterBlock banister,
      DryingRackBlock rack
   ) {
      this.plankBlocks(variant, plank, slab, stair, button, fence, gate, plate, door, trapdoor, false, false, banister, rack);
   }

   protected void plankBlocks(
      String variant,
      Block plank,
      Block slab,
      StairBlock stair,
      Block button,
      Block fence,
      Block gate,
      Block plate,
      DoorBlock door,
      TrapDoorBlock trapdoor,
      boolean cutoutDoors,
      boolean correctDoors,
      BanisterBlock banister,
      DryingRackBlock rack
   ) {
      String plankTexName = "planks_" + variant;
      String plankDir = "block/wood/planks/" + variant + "/";
      ResourceLocation tex0 = TwilightForestMod.prefix("block/wood/" + plankTexName + "_0");
      ResourceLocation tex1 = TwilightForestMod.prefix("block/wood/" + plankTexName + "_1");
      ResourceLocation tex2 = TwilightForestMod.prefix("block/wood/" + plankTexName + "_2");
      ResourceLocation tex3 = TwilightForestMod.prefix("block/wood/" + plankTexName + "_3");
      ConfiguredModel[] plankModels = ConfiguredModel.builder()
         .weight(10)
         .modelFile(this.models().cubeAll(plankDir + this.name(plank), tex0))
         .nextModel()
         .weight(10)
         .modelFile(this.models().cubeAll(plankDir + this.name(plank) + "_1", tex1))
         .nextModel()
         .weight(1)
         .modelFile(this.models().cubeAll(plankDir + this.name(plank) + "_2", tex2))
         .nextModel()
         .weight(1)
         .modelFile(this.models().cubeAll(plankDir + this.name(plank) + "_3", tex3))
         .build();
      this.simpleBlock(plank, plankModels);
      String slabDir = "block/wood/slab/" + variant + "/";
      ConfiguredModel[] bottomSlabModels = ConfiguredModel.builder()
         .weight(10)
         .modelFile(this.models().slab(slabDir + this.name(slab), tex0, tex0, tex0))
         .nextModel()
         .weight(10)
         .modelFile(this.models().slab(slabDir + this.name(slab) + "_1", tex1, tex1, tex1))
         .nextModel()
         .weight(1)
         .modelFile(this.models().slab(slabDir + this.name(slab) + "_2", tex2, tex2, tex2))
         .nextModel()
         .weight(1)
         .modelFile(this.models().slab(slabDir + this.name(slab) + "_3", tex3, tex3, tex3))
         .build();
      ConfiguredModel[] topSlabModels = ConfiguredModel.builder()
         .weight(10)
         .uvLock(true)
         .rotationX(180)
         .modelFile(bottomSlabModels[0].model)
         .nextModel()
         .weight(10)
         .uvLock(true)
         .rotationX(180)
         .modelFile(bottomSlabModels[1].model)
         .nextModel()
         .weight(1)
         .uvLock(true)
         .rotationX(180)
         .modelFile(bottomSlabModels[2].model)
         .nextModel()
         .weight(1)
         .uvLock(true)
         .rotationX(180)
         .modelFile(bottomSlabModels[3].model)
         .build();
      this.getVariantBuilder(slab).partialState().with(SlabBlock.TYPE, SlabType.BOTTOM).setModels(bottomSlabModels);
      this.getVariantBuilder(slab).partialState().with(SlabBlock.TYPE, SlabType.TOP).setModels(topSlabModels);
      this.getVariantBuilder(slab).partialState().with(SlabBlock.TYPE, SlabType.DOUBLE).setModels(plankModels);
      this.woodStairs(stair, plankTexName, variant);
      this.woodButton(button, plankTexName, variant);
      this.woodFence(fence, plankTexName, variant);
      this.woodGate(gate, plankTexName, variant);
      this.woodPlate(plate, plankTexName, variant);
      String doorDir = "block/wood/door/" + variant + "/";
      String trapdoorDir = "block/wood/trapdoor/" + variant + "/";
      if (correctDoors) {
         this.correctedDoorBlock(
            door,
            doorDir + this.name(door),
            TwilightForestMod.prefix("block/wood/door/" + variant + "_lower"),
            TwilightForestMod.prefix("block/wood/door/" + variant + "_upper"),
            TwilightForestMod.prefix("block/wood/door/" + variant + "_side"),
            cutoutDoors ? CUTOUT : SOLID
         );
      } else {
         this.doorBlockWithRenderType(
            door,
            doorDir + variant,
            TwilightForestMod.prefix("block/wood/door/" + variant + "_lower"),
            TwilightForestMod.prefix("block/wood/door/" + variant + "_upper"),
            cutoutDoors ? CUTOUT : SOLID
         );
      }

      this.trapdoorBlockWithRenderType(
         trapdoor, trapdoorDir + variant, TwilightForestMod.prefix("block/wood/trapdoor/" + variant + "_trapdoor"), true, cutoutDoors ? CUTOUT : SOLID
      );
      this.banister(banister, plankTexName, variant);
      this.dryingRack(rack, plankTexName, variant);
   }

   private void correctedDoorBlock(
      DoorBlock block, String baseName, ResourceLocation bottom, ResourceLocation top, ResourceLocation side, ResourceLocation renderType
   ) {
      ModelFile bottomLeft = this.door(baseName + "_bottom_left", "corrected_door_bottom_left", bottom, top, side).renderType(renderType);
      ModelFile bottomLeftOpen = this.door(baseName + "_bottom_left_open", "corrected_door_bottom_left_open", bottom, top, side).renderType(renderType);
      ModelFile bottomRight = this.door(baseName + "_bottom_right", "corrected_door_bottom_right", bottom, top, side).renderType(renderType);
      ModelFile bottomRightOpen = this.door(baseName + "_bottom_right_open", "corrected_door_bottom_right_open", bottom, top, side).renderType(renderType);
      ModelFile topLeft = this.door(baseName + "_top_left", "corrected_door_top_left", bottom, top, side).renderType(renderType);
      ModelFile topLeftOpen = this.door(baseName + "_top_left_open", "corrected_door_top_left_open", bottom, top, side).renderType(renderType);
      ModelFile topRight = this.door(baseName + "_top_right", "corrected_door_top_right", bottom, top, side).renderType(renderType);
      ModelFile topRightOpen = this.door(baseName + "_top_right_open", "corrected_door_top_right_open", bottom, top, side).renderType(renderType);
      this.doorBlock(block, bottomLeft, bottomLeftOpen, bottomRight, bottomRightOpen, topLeft, topLeftOpen, topRight, topRightOpen);
   }

   private BlockModelBuilder door(String name, String model, ResourceLocation bottom, ResourceLocation top, ResourceLocation side) {
      return (BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(name, TwilightForestMod.prefix("block/util/" + model)))
               .texture("bottom", bottom))
            .texture("top", top))
         .texture("side", side);
   }

   protected void woodGate(Block gate, String texName, String variant) {
      String gateDir = "block/wood/fence_gate/" + variant + "/";
      ResourceLocation tex0 = TwilightForestMod.prefix("block/wood/" + texName + "_0");
      ResourceLocation tex1 = TwilightForestMod.prefix("block/wood/" + texName + "_1");
      ResourceLocation tex2 = TwilightForestMod.prefix("block/wood/" + texName + "_2");
      ResourceLocation tex3 = TwilightForestMod.prefix("block/wood/" + texName + "_3");
      ModelFile gate0 = this.models().fenceGate(gateDir + this.name(gate), tex0);
      ModelFile gate1 = this.models().fenceGate(gateDir + this.name(gate) + "_1", tex1);
      ModelFile gate2 = this.models().fenceGate(gateDir + this.name(gate) + "_2", tex2);
      ModelFile gate3 = this.models().fenceGate(gateDir + this.name(gate) + "_3", tex3);
      ModelFile open0 = this.models().fenceGateOpen(gateDir + this.name(gate) + "_open", tex0);
      ModelFile open1 = this.models().fenceGateOpen(gateDir + this.name(gate) + "_open_1", tex1);
      ModelFile open2 = this.models().fenceGateOpen(gateDir + this.name(gate) + "_open_2", tex2);
      ModelFile open3 = this.models().fenceGateOpen(gateDir + this.name(gate) + "_open_3", tex3);
      ModelFile wall0 = this.models().fenceGateWall(gateDir + this.name(gate) + "_wall", tex0);
      ModelFile wall1 = this.models().fenceGateWall(gateDir + this.name(gate) + "_wall_1", tex1);
      ModelFile wall2 = this.models().fenceGateWall(gateDir + this.name(gate) + "_wall_2", tex2);
      ModelFile wall3 = this.models().fenceGateWall(gateDir + this.name(gate) + "_wall_3", tex3);
      ModelFile wallOpen0 = this.models().fenceGateWallOpen(gateDir + this.name(gate) + "_wall_open", tex0);
      ModelFile wallOpen1 = this.models().fenceGateWallOpen(gateDir + this.name(gate) + "_wall_open_1", tex1);
      ModelFile wallOpen2 = this.models().fenceGateWallOpen(gateDir + this.name(gate) + "_wall_open_2", tex2);
      ModelFile wallOpen3 = this.models().fenceGateWallOpen(gateDir + this.name(gate) + "_wall_open_3", tex3);
      this.getVariantBuilder(gate)
         .forAllStatesExcept(
            state -> {
               ModelFile model0 = gate0;
               ModelFile model1 = gate1;
               ModelFile model2 = gate2;
               ModelFile model3 = gate3;
               if ((Boolean)state.getValue(FenceGateBlock.IN_WALL)) {
                  model0 = wall0;
                  model1 = wall1;
                  model2 = wall2;
                  model3 = wall3;
               }

               if ((Boolean)state.getValue(FenceGateBlock.OPEN)) {
                  model0 = model0 == wall0 ? wallOpen0 : open0;
                  model1 = model1 == wall1 ? wallOpen1 : open1;
                  model2 = model2 == wall2 ? wallOpen2 : open2;
                  model3 = model3 == wall3 ? wallOpen3 : open3;
               }

               return ConfiguredModel.builder()
                  .weight(10)
                  .modelFile(model0)
                  .rotationY((int)((Direction)state.getValue(HorizontalDirectionalBlock.FACING)).toYRot())
                  .uvLock(true)
                  .nextModel()
                  .weight(10)
                  .modelFile(model1)
                  .rotationY((int)((Direction)state.getValue(HorizontalDirectionalBlock.FACING)).toYRot())
                  .uvLock(true)
                  .nextModel()
                  .weight(1)
                  .modelFile(model2)
                  .rotationY((int)((Direction)state.getValue(HorizontalDirectionalBlock.FACING)).toYRot())
                  .uvLock(true)
                  .nextModel()
                  .weight(1)
                  .modelFile(model3)
                  .rotationY((int)((Direction)state.getValue(HorizontalDirectionalBlock.FACING)).toYRot())
                  .uvLock(true)
                  .build();
            },
            new Property[]{FenceGateBlock.POWERED}
         );
   }

   protected void woodFence(Block fence, String texName, String variant) {
      String fenceDir = "block/wood/fence/" + variant + "/";
      ResourceLocation tex0 = TwilightForestMod.prefix("block/wood/" + texName + "_0");
      ResourceLocation tex1 = TwilightForestMod.prefix("block/wood/" + texName + "_1");
      ResourceLocation tex2 = TwilightForestMod.prefix("block/wood/" + texName + "_2");
      ResourceLocation tex3 = TwilightForestMod.prefix("block/wood/" + texName + "_3");
      ModelFile post0 = this.models().fencePost(fenceDir + this.name(fence) + "_post", tex0);
      ModelFile post1 = this.models().fencePost(fenceDir + this.name(fence) + "_post_1", tex1);
      ModelFile post2 = this.models().fencePost(fenceDir + this.name(fence) + "_post_2", tex2);
      ModelFile post3 = this.models().fencePost(fenceDir + this.name(fence) + "_post_3", tex3);
      ModelFile side0 = this.models().fenceSide(fenceDir + this.name(fence) + "_side", tex0);
      ModelFile side1 = this.models().fenceSide(fenceDir + this.name(fence) + "_side_1", tex1);
      ModelFile side2 = this.models().fenceSide(fenceDir + this.name(fence) + "_side_2", tex2);
      ModelFile side3 = this.models().fenceSide(fenceDir + this.name(fence) + "_side_3", tex3);
      MultiPartBlockStateBuilder builder = ((PartBuilder)this.getMultipartBuilder(fence)
            .part()
            .weight(10)
            .modelFile(post0)
            .nextModel()
            .weight(10)
            .modelFile(post1)
            .nextModel()
            .weight(1)
            .modelFile(post2)
            .nextModel()
            .weight(1)
            .modelFile(post3)
            .addModel())
         .end();
      PipeBlock.PROPERTY_BY_DIRECTION
         .forEach(
            (dir, value) -> {
               if (dir.getAxis().isHorizontal()) {
                  ((PartBuilder)builder.part()
                        .weight(10)
                        .modelFile(side0)
                        .rotationY(((int)dir.toYRot() + 180) % 360)
                        .uvLock(true)
                        .nextModel()
                        .weight(10)
                        .modelFile(side1)
                        .rotationY(((int)dir.toYRot() + 180) % 360)
                        .uvLock(true)
                        .nextModel()
                        .weight(1)
                        .modelFile(side2)
                        .rotationY(((int)dir.toYRot() + 180) % 360)
                        .uvLock(true)
                        .nextModel()
                        .weight(1)
                        .modelFile(side3)
                        .rotationY(((int)dir.toYRot() + 180) % 360)
                        .uvLock(true)
                        .addModel())
                     .condition(value, new Boolean[]{true});
               }
            }
         );
   }

   protected void woodPlate(Block plate, String texName, String variant) {
      String plateDir = "block/wood/pressure_plate/" + variant + "/";
      ResourceLocation tex0 = TwilightForestMod.prefix("block/wood/" + texName + "_0");
      ResourceLocation tex1 = TwilightForestMod.prefix("block/wood/" + texName + "_1");
      ResourceLocation tex2 = TwilightForestMod.prefix("block/wood/" + texName + "_2");
      ResourceLocation tex3 = TwilightForestMod.prefix("block/wood/" + texName + "_3");
      ConfiguredModel[] unpressed = ConfiguredModel.builder()
         .weight(10)
         .modelFile(((BlockModelBuilder)this.models().withExistingParent(plateDir + this.name(plate), "pressure_plate_up")).texture("texture", tex0))
         .nextModel()
         .weight(10)
         .modelFile(((BlockModelBuilder)this.models().withExistingParent(plateDir + this.name(plate) + "_1", "pressure_plate_up")).texture("texture", tex1))
         .nextModel()
         .weight(1)
         .modelFile(((BlockModelBuilder)this.models().withExistingParent(plateDir + this.name(plate) + "_2", "pressure_plate_up")).texture("texture", tex2))
         .nextModel()
         .weight(1)
         .modelFile(((BlockModelBuilder)this.models().withExistingParent(plateDir + this.name(plate) + "_3", "pressure_plate_up")).texture("texture", tex3))
         .build();
      ConfiguredModel[] pressed = ConfiguredModel.builder()
         .weight(10)
         .modelFile(
            ((BlockModelBuilder)this.models().withExistingParent(plateDir + this.name(plate) + "_down", "pressure_plate_down")).texture("texture", tex0)
         )
         .nextModel()
         .weight(10)
         .modelFile(
            ((BlockModelBuilder)this.models().withExistingParent(plateDir + this.name(plate) + "_down_1", "pressure_plate_down")).texture("texture", tex1)
         )
         .nextModel()
         .weight(1)
         .modelFile(
            ((BlockModelBuilder)this.models().withExistingParent(plateDir + this.name(plate) + "_down_2", "pressure_plate_down")).texture("texture", tex2)
         )
         .nextModel()
         .weight(1)
         .modelFile(
            ((BlockModelBuilder)this.models().withExistingParent(plateDir + this.name(plate) + "_down_3", "pressure_plate_down")).texture("texture", tex3)
         )
         .build();
      this.getVariantBuilder(plate).partialState().with(PressurePlateBlock.POWERED, false).setModels(unpressed);
      this.getVariantBuilder(plate).partialState().with(PressurePlateBlock.POWERED, true).setModels(pressed);
   }

   protected void woodButton(Block button, String texName, String variant) {
      String buttonDir = "block/wood/button/" + variant + "/";
      ResourceLocation tex0 = TwilightForestMod.prefix("block/wood/" + texName + "_0");
      ResourceLocation tex1 = TwilightForestMod.prefix("block/wood/" + texName + "_1");
      ResourceLocation tex2 = TwilightForestMod.prefix("block/wood/" + texName + "_2");
      ResourceLocation tex3 = TwilightForestMod.prefix("block/wood/" + texName + "_3");
      ModelFile unpressed0 = ((BlockModelBuilder)this.models().withExistingParent(buttonDir + this.name(button), "button")).texture("texture", tex0);
      ModelFile pressed0 = ((BlockModelBuilder)this.models().withExistingParent(buttonDir + this.name(button) + "_pressed", "button_pressed"))
         .texture("texture", tex0);
      ModelFile unpressed1 = ((BlockModelBuilder)this.models().withExistingParent(buttonDir + this.name(button) + "_1", "button")).texture("texture", tex1);
      ModelFile pressed1 = ((BlockModelBuilder)this.models().withExistingParent(buttonDir + this.name(button) + "_pressed_1", "button_pressed"))
         .texture("texture", tex1);
      ModelFile unpressed2 = ((BlockModelBuilder)this.models().withExistingParent(buttonDir + this.name(button) + "_2", "button")).texture("texture", tex2);
      ModelFile pressed2 = ((BlockModelBuilder)this.models().withExistingParent(buttonDir + this.name(button) + "_pressed_2", "button_pressed"))
         .texture("texture", tex2);
      ModelFile unpressed3 = ((BlockModelBuilder)this.models().withExistingParent(buttonDir + this.name(button) + "_3", "button")).texture("texture", tex3);
      ModelFile pressed3 = ((BlockModelBuilder)this.models().withExistingParent(buttonDir + this.name(button) + "_pressed_3", "button_pressed"))
         .texture("texture", tex3);
      this.getVariantBuilder(button)
         .forAllStates(
            state -> {
               ModelFile model0 = state.getValue(ButtonBlock.POWERED) ? pressed0 : unpressed0;
               ModelFile model1 = state.getValue(ButtonBlock.POWERED) ? pressed1 : unpressed1;
               ModelFile model2 = state.getValue(ButtonBlock.POWERED) ? pressed2 : unpressed2;
               ModelFile model3 = state.getValue(ButtonBlock.POWERED) ? pressed3 : unpressed3;

               int rotX = switch ((AttachFace)state.getValue(FaceAttachedHorizontalDirectionalBlock.FACE)) {
                  case WALL -> 90;
                  case FLOOR -> 0;
                  case CEILING -> 180;
                  default -> throw new MatchException(null, null);
               };
               int rotY = 0;
               if (state.getValue(FaceAttachedHorizontalDirectionalBlock.FACE) == AttachFace.CEILING) {
                  switch ((Direction)state.getValue(HorizontalDirectionalBlock.FACING)) {
                     case NORTH:
                        rotY = 180;
                        break;
                     case WEST:
                        rotY = 90;
                        break;
                     case EAST:
                        rotY = 270;
                  }
               } else {
                  switch ((Direction)state.getValue(HorizontalDirectionalBlock.FACING)) {
                     case WEST:
                        rotY = 270;
                        break;
                     case EAST:
                        rotY = 90;
                        break;
                     case SOUTH:
                        rotY = 180;
                  }
               }

               boolean uvlock = state.getValue(FaceAttachedHorizontalDirectionalBlock.FACE) == AttachFace.WALL;
               return ConfiguredModel.builder()
                  .weight(10)
                  .uvLock(uvlock)
                  .rotationX(rotX)
                  .rotationY(rotY)
                  .modelFile(model0)
                  .nextModel()
                  .weight(10)
                  .uvLock(uvlock)
                  .rotationX(rotX)
                  .rotationY(rotY)
                  .modelFile(model1)
                  .nextModel()
                  .weight(1)
                  .uvLock(uvlock)
                  .rotationX(rotX)
                  .rotationY(rotY)
                  .modelFile(model2)
                  .nextModel()
                  .weight(1)
                  .uvLock(uvlock)
                  .rotationX(rotX)
                  .rotationY(rotY)
                  .modelFile(model3)
                  .build();
            }
         );
   }

   protected void woodStairs(StairBlock block, String texName, String variant) {
      String stairsDir = "block/wood/stairs/" + variant + "/";
      ResourceLocation tex0 = TwilightForestMod.prefix("block/wood/" + texName + "_0");
      ResourceLocation tex1 = TwilightForestMod.prefix("block/wood/" + texName + "_1");
      ResourceLocation tex2 = TwilightForestMod.prefix("block/wood/" + texName + "_2");
      ResourceLocation tex3 = TwilightForestMod.prefix("block/wood/" + texName + "_3");
      ModelFile main0 = this.models().stairs(stairsDir + this.name(block), tex0, tex0, tex0);
      ModelFile main1 = this.models().stairs(stairsDir + this.name(block) + "_1", tex1, tex1, tex1);
      ModelFile main2 = this.models().stairs(stairsDir + this.name(block) + "_2", tex2, tex2, tex2);
      ModelFile main3 = this.models().stairs(stairsDir + this.name(block) + "_3", tex3, tex3, tex3);
      ModelFile inner0 = this.models().stairsInner(stairsDir + this.name(block) + "_inner", tex0, tex0, tex0);
      ModelFile inner1 = this.models().stairsInner(stairsDir + this.name(block) + "_inner_1", tex1, tex1, tex1);
      ModelFile inner2 = this.models().stairsInner(stairsDir + this.name(block) + "_inner_2", tex2, tex2, tex2);
      ModelFile inner3 = this.models().stairsInner(stairsDir + this.name(block) + "_inner_3", tex3, tex3, tex3);
      ModelFile outer0 = this.models().stairsOuter(stairsDir + this.name(block) + "_outer", tex0, tex0, tex0);
      ModelFile outer1 = this.models().stairsOuter(stairsDir + this.name(block) + "_outer_1", tex1, tex1, tex1);
      ModelFile outer2 = this.models().stairsOuter(stairsDir + this.name(block) + "_outer_2", tex2, tex2, tex2);
      ModelFile outer3 = this.models().stairsOuter(stairsDir + this.name(block) + "_outer_3", tex3, tex3, tex3);
      this.getVariantBuilder(block)
         .forAllStatesExcept(
            state -> {
               Direction facing = (Direction)state.getValue(StairBlock.FACING);
               Half half = (Half)state.getValue(StairBlock.HALF);
               StairsShape shape = (StairsShape)state.getValue(StairBlock.SHAPE);
               int yRot = (int)facing.getClockWise().toYRot();
               if (shape == StairsShape.INNER_LEFT || shape == StairsShape.OUTER_LEFT) {
                  yRot += 270;
               }

               if (shape != StairsShape.STRAIGHT && half == Half.TOP) {
                  yRot += 90;
               }

               yRot %= 360;
               boolean uvlock = yRot != 0 || half == Half.TOP;
               return ConfiguredModel.builder()
                  .weight(10)
                  .modelFile(shape == StairsShape.STRAIGHT ? main0 : (shape != StairsShape.INNER_LEFT && shape != StairsShape.INNER_RIGHT ? outer0 : inner0))
                  .rotationX(half == Half.BOTTOM ? 0 : 180)
                  .rotationY(yRot)
                  .uvLock(uvlock)
                  .nextModel()
                  .weight(10)
                  .modelFile(shape == StairsShape.STRAIGHT ? main1 : (shape != StairsShape.INNER_LEFT && shape != StairsShape.INNER_RIGHT ? outer1 : inner1))
                  .rotationX(half == Half.BOTTOM ? 0 : 180)
                  .rotationY(yRot)
                  .uvLock(uvlock)
                  .nextModel()
                  .weight(1)
                  .modelFile(shape == StairsShape.STRAIGHT ? main2 : (shape != StairsShape.INNER_LEFT && shape != StairsShape.INNER_RIGHT ? outer2 : inner2))
                  .rotationX(half == Half.BOTTOM ? 0 : 180)
                  .rotationY(yRot)
                  .uvLock(uvlock)
                  .nextModel()
                  .weight(1)
                  .modelFile(shape == StairsShape.STRAIGHT ? main3 : (shape != StairsShape.INNER_LEFT && shape != StairsShape.INNER_RIGHT ? outer3 : inner3))
                  .rotationX(half == Half.BOTTOM ? 0 : 180)
                  .rotationY(yRot)
                  .uvLock(uvlock)
                  .build();
            },
            new Property[]{StairBlock.WATERLOGGED}
         );
   }

   protected void banister(BanisterBlock banister, String texName, String woodVariant) {
      String banisterDir = "block/wood/banister/" + woodVariant + "/";
      ResourceLocation tex0 = TwilightForestMod.prefix("block/wood/" + texName + "_0");
      ResourceLocation tex1 = TwilightForestMod.prefix("block/wood/" + texName + "_1");
      ResourceLocation tex2 = TwilightForestMod.prefix("block/wood/" + texName + "_2");
      ResourceLocation tex3 = TwilightForestMod.prefix("block/wood/" + texName + "_3");
      this.getVariantBuilder(banister)
         .forAllStatesExcept(
            state -> {
               Direction facing = (Direction)state.getValue(BanisterBlock.FACING);
               int yRot = (int)facing.toYRot();
               String extended = state.getValue(BanisterBlock.EXTENDED) ? "_extended" : "";
               String variant = ((BanisterShape)state.getValue(BanisterBlock.SHAPE)).getSerializedName() + extended;
               String newModelName = banisterDir + this.name(banister) + "_" + variant;
               return ConfiguredModel.builder()
                  .weight(10)
                  .modelFile(
                     ((BlockModelBuilder)this.models().withExistingParent(newModelName, TwilightForestMod.prefix("banister_" + variant)))
                        .texture("texture", tex0)
                  )
                  .rotationY(yRot)
                  .nextModel()
                  .weight(10)
                  .modelFile(
                     ((BlockModelBuilder)this.models().withExistingParent(newModelName + "_1", TwilightForestMod.prefix("banister_" + variant)))
                        .texture("texture", tex1)
                  )
                  .rotationY(yRot)
                  .nextModel()
                  .weight(1)
                  .modelFile(
                     ((BlockModelBuilder)this.models().withExistingParent(newModelName + "_2", TwilightForestMod.prefix("banister_" + variant)))
                        .texture("texture", tex2)
                  )
                  .rotationY(yRot)
                  .nextModel()
                  .weight(1)
                  .modelFile(
                     ((BlockModelBuilder)this.models().withExistingParent(newModelName + "_3", TwilightForestMod.prefix("banister_" + variant)))
                        .texture("texture", tex3)
                  )
                  .rotationY(yRot)
                  .build();
            },
            new Property[]{BanisterBlock.WATERLOGGED}
         );
   }

   protected void dryingRack(DryingRackBlock rack, String texName, String woodVariant) {
      String banisterDir = "block/wood/rack/" + woodVariant + "/";
      ResourceLocation tex0 = TwilightForestMod.prefix("block/wood/" + texName + "_0");
      ResourceLocation tex1 = TwilightForestMod.prefix("block/wood/" + texName + "_1");
      ResourceLocation tex2 = TwilightForestMod.prefix("block/wood/" + texName + "_2");
      ResourceLocation tex3 = TwilightForestMod.prefix("block/wood/" + texName + "_3");
      this.getVariantBuilder(rack)
         .forAllStatesExcept(
            state -> {
               Direction facing = (Direction)state.getValue(BanisterBlock.FACING);
               int yRot = (int)facing.toYRot();
               return ConfiguredModel.builder()
                  .weight(10)
                  .modelFile(
                     ((BlockModelBuilder)this.models().withExistingParent(banisterDir + this.name(rack), TwilightForestMod.prefix("template_drying_rack")))
                        .texture("texture", tex0)
                  )
                  .rotationY(yRot)
                  .nextModel()
                  .weight(10)
                  .modelFile(
                     ((BlockModelBuilder)this.models()
                           .withExistingParent(banisterDir + this.name(rack) + "_1", TwilightForestMod.prefix("template_drying_rack")))
                        .texture("texture", tex1)
                  )
                  .rotationY(yRot)
                  .nextModel()
                  .weight(1)
                  .modelFile(
                     ((BlockModelBuilder)this.models()
                           .withExistingParent(banisterDir + this.name(rack) + "_2", TwilightForestMod.prefix("template_drying_rack")))
                        .texture("texture", tex2)
                  )
                  .rotationY(yRot)
                  .nextModel()
                  .weight(1)
                  .modelFile(
                     ((BlockModelBuilder)this.models()
                           .withExistingParent(banisterDir + this.name(rack) + "_3", TwilightForestMod.prefix("template_drying_rack")))
                        .texture("texture", tex3)
                  )
                  .rotationY(yRot)
                  .build();
            },
            new Property[]{DryingRackBlock.WATERLOGGED}
         );
   }

   protected void banisterVanilla(BanisterBlock banister, String texName, String woodVariant) {
      String banisterDir = "block/wood/banister/" + woodVariant + "/";
      ResourceLocation tex0 = ResourceLocation.withDefaultNamespace("block/" + texName);
      this.getVariantBuilder(banister)
         .forAllStatesExcept(
            state -> {
               Direction facing = (Direction)state.getValue(BanisterBlock.FACING);
               int yRot = (int)facing.toYRot();
               String extended = state.getValue(BanisterBlock.EXTENDED) ? "_extended" : "";
               String variant = ((BanisterShape)state.getValue(BanisterBlock.SHAPE)).getSerializedName() + extended;
               return ConfiguredModel.builder()
                  .modelFile(
                     ((BlockModelBuilder)this.models()
                           .withExistingParent(banisterDir + this.name(banister) + "_" + variant, TwilightForestMod.prefix("banister_" + variant)))
                        .texture("texture", tex0)
                  )
                  .rotationY(yRot)
                  .build();
            },
            new Property[]{BanisterBlock.WATERLOGGED}
         );
   }

   protected void dryingRackVanilla(DryingRackBlock rack, String texName, String woodVariant) {
      String banisterDir = "block/wood/rack/" + woodVariant + "/";
      ResourceLocation tex = ResourceLocation.withDefaultNamespace("block/" + texName);
      this.getVariantBuilder(rack)
         .forAllStatesExcept(
            state -> {
               Direction facing = (Direction)state.getValue(BanisterBlock.FACING);
               int yRot = (int)facing.toYRot();
               return ConfiguredModel.builder()
                  .modelFile(
                     ((BlockModelBuilder)this.models().withExistingParent(banisterDir + this.name(rack), TwilightForestMod.prefix("template_drying_rack")))
                        .texture("texture", tex)
                  )
                  .rotationY(yRot)
                  .build();
            },
            new Property[]{DryingRackBlock.WATERLOGGED}
         );
   }

   protected void bisectedStairsBlock(DeferredHolder<Block, StairBlock> block, ResourceLocation side, ResourceLocation end, ResourceLocation middle) {
      this.bisectedStairsBlock(block, block.getId().getPath(), side, end, middle);
   }

   protected void bisectedStairsBlock(
      DeferredHolder<Block, StairBlock> block, String name, ResourceLocation side, ResourceLocation end, ResourceLocation middle
   ) {
      ModelFile stairs = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(name, TwilightForestMod.prefix("block/util/bisected_stairs")))
               .texture("side", side))
            .texture("end", end))
         .texture("middle", middle);
      ModelFile stairsInner = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(name + "_inner", TwilightForestMod.prefix("block/util/bisected_inner_stairs")))
               .texture("side", side))
            .texture("end", end))
         .texture("middle", middle);
      ModelFile stairsOuter = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(name + "_outer", TwilightForestMod.prefix("block/util/bisected_outer_stairs")))
               .texture("side", side))
            .texture("end", end))
         .texture("middle", middle);
      this.stairsBlock((StairBlock)block.get(), stairs, stairsInner, stairsOuter);
   }

   protected ResourceLocation key(Block block) {
      return BuiltInRegistries.BLOCK.getKey(block);
   }

   protected String name(Block block) {
      return this.key(block).getPath();
   }
}
