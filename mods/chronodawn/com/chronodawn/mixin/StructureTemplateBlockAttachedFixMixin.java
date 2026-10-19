package com.chronodawn.mixin;

import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureEntityInfo;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(StructureTemplate.class)
public class StructureTemplateBlockAttachedFixMixin {
   @Inject(
      method = "placeEntities",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate;createEntityIgnoreException(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/nbt/CompoundTag;)Ljava/util/Optional;"
      ),
      locals = LocalCapture.CAPTURE_FAILHARD
   )
   private void chronodawn$rewriteTilePosForAttachedEntity(
      ServerLevelAccessor level,
      BlockPos origin,
      Mirror mirror,
      Rotation rotation,
      BlockPos pivot,
      BoundingBox boundingBox,
      boolean placeIgnoreEntities,
      CallbackInfo ci,
      Iterator<StructureEntityInfo> iter,
      StructureEntityInfo info,
      BlockPos blockpos,
      CompoundTag compoundtag,
      Vec3 relativePos,
      Vec3 worldPos,
      ListTag posListTag
   ) {
      if (compoundtag.contains("TileX")) {
         compoundtag.putInt("TileX", blockpos.getX());
         compoundtag.putInt("TileY", blockpos.getY());
         compoundtag.putInt("TileZ", blockpos.getZ());
      }
   }
}
