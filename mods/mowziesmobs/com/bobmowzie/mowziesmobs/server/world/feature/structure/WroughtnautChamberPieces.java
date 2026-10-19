package com.bobmowzie.mowziesmobs.server.world.feature.structure;

import net.minecraft.core.BlockPos;
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

public class WroughtnautChamberPieces {
   private static final ResourceLocation PART = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "wroughtnaut_chamber");

   public static void start(StructureTemplateManager manager, BlockPos pos, Rotation rot, StructurePieceAccessor pieces) {
      pieces.addPiece(new WroughtnautChamberPieces.Piece(manager, PART, pos, rot));
   }

   public static class Piece extends TemplateStructurePiece {
      public Piece(StructureTemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn) {
         super(
            (StructurePieceType)StructureTypeHandler.WROUGHTNAUT_CHAMBER_PIECE.get(),
            0,
            templateManagerIn,
            resourceLocationIn,
            resourceLocationIn.toString(),
            makeSettings(rotationIn, resourceLocationIn),
            pos
         );
      }

      public Piece(StructurePieceSerializationContext context, CompoundTag tagCompound) {
         super(
            (StructurePieceType)StructureTypeHandler.WROUGHTNAUT_CHAMBER_PIECE.get(),
            tagCompound,
            context.structureTemplateManager(),
            resourceLocation -> makeSettings(
               Rotation.valueOf(tagCompound.contains("Rot") ? tagCompound.getString("Rot") : Rotation.NONE.name()), resourceLocation
            )
         );
      }

      private static StructurePlaceSettings makeSettings(Rotation rotation, ResourceLocation resourceLocation) {
         return new StructurePlaceSettings().setRotation(rotation).setMirror(Mirror.NONE).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
      }

      protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tagCompound) {
         super.addAdditionalSaveData(context, tagCompound);
         tagCompound.putString("Rot", this.placeSettings.getRotation().name());
      }

      protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor worldIn, RandomSource rand, BoundingBox sbb) {
      }
   }
}
