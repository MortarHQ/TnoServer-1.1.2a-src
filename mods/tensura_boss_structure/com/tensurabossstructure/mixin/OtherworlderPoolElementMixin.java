package com.tensurabossstructure.mixin;

import com.mojang.datafixers.util.Either;
import com.tensurabossstructure.TensuraBossStructure;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SinglePoolElement.class)
public abstract class OtherworlderPoolElementMixin {
   @Unique
   private static final Set<BlockPos> tensura$placedOtherworlder = new HashSet<>();
   @Unique
   private static final int MIN_DISTANCE = 200;

   @Accessor("template")
   public abstract Either<ResourceLocation, StructureTemplate> tensura$getTemplate();

   @Unique
   private boolean tensura$isOtherworlder() {
      Either<ResourceLocation, StructureTemplate> template = this.tensura$getTemplate();
      if (!template.left().isPresent()) {
         return false;
      }

      String path = ((ResourceLocation)template.left().get()).getPath();
      return path.endsWith("/otherworlder") || path.equals("otherworlder");
   }

   @Inject(
      method = "place(Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager;Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Rotation;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/levelgen/structure/templatesystem/LiquidSettings;Z)Z",
      at = @At("HEAD"),
      cancellable = true
   )
   public void tensura$onPlace(
      StructureTemplateManager mgr,
      WorldGenLevel level,
      StructureManager sm,
      ChunkGenerator cg,
      BlockPos pos,
      BlockPos pos2,
      Rotation rot,
      BoundingBox bb,
      RandomSource rand,
      LiquidSettings liquid,
      boolean flag,
      CallbackInfoReturnable<Boolean> cir
   ) {
      if (this.tensura$isOtherworlder()) {
         for (BlockPos existing : tensura$placedOtherworlder) {
            if (pos != existing && existing.distSqr(pos) < 40000.0) {
               TensuraBossStructure.LOGGER.debug("[OTHERWORLDER] Blocked duplicate at {} (too close to existing at {})", pos, existing);
               cir.setReturnValue(false);
               return;
            }
         }

         tensura$placedOtherworlder.add(pos);
         TensuraBossStructure.LOGGER.debug("[OTHERWORLDER] Placed at {}", pos);
      }
   }
}
