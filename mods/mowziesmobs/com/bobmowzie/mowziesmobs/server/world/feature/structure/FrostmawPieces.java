package com.bobmowzie.mowziesmobs.server.world.feature.structure;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class FrostmawPieces {
   private static final ResourceLocation FROSTMAW = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "frostmaw_spawn");
   private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.of(FROSTMAW, new BlockPos(0, 1, 0));

   public static void addPieces(StructureTemplateManager manager, BlockPos pos, Rotation rot, StructurePieceAccessor pieces, RandomSource rand) {
      BlockPos rotationOffset = new BlockPos(0, 0, 0).rotate(rot);
      BlockPos blockPos = rotationOffset.offset(pos);
      pieces.addPiece(new FrostmawPieces.FrostmawPiece(manager, FROSTMAW, blockPos, rot));
   }

   public static class FrostmawPiece extends TemplateStructurePiece {
      private static StructurePlaceSettings makeSettings(Rotation rotation, ResourceLocation resourceLocation) {
         return new StructurePlaceSettings().setRotation(rotation).setMirror(Mirror.NONE).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
      }

      private static BlockPos makePosition(ResourceLocation resourceLocation, BlockPos pos) {
         return pos.offset((Vec3i)FrostmawPieces.OFFSET.get(resourceLocation));
      }

      public FrostmawPiece(StructureTemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn) {
         super(
            (StructurePieceType)StructureTypeHandler.FROSTMAW_PIECE.get(),
            0,
            templateManagerIn,
            resourceLocationIn,
            resourceLocationIn.toString(),
            makeSettings(rotationIn, resourceLocationIn),
            makePosition(resourceLocationIn, pos)
         );
      }

      public FrostmawPiece(StructurePieceSerializationContext context, CompoundTag tag) {
         super(
            (StructurePieceType)StructureTypeHandler.FROSTMAW_PIECE.get(),
            tag,
            context.structureTemplateManager(),
            resourceLocation -> makeSettings(Rotation.valueOf(tag.getString("Rot")), resourceLocation)
         );
      }

      protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tagCompound) {
         super.addAdditionalSaveData(context, tagCompound);
         tagCompound.putString("Rot", this.placeSettings.getRotation().name());
      }

      protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor worldIn, RandomSource rand, BoundingBox sbb) {
      }
   }
}
