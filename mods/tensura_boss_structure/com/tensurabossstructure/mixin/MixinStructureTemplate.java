package com.tensurabossstructure.mixin;

import com.tensurabossstructure.registry.ModProcessors;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StructureTemplate.class)
public class MixinStructureTemplate {
   @Inject(
      method = "placeInWorld(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructurePlaceSettings;Lnet/minecraft/util/RandomSource;I)Z",
      at = @At("HEAD")
   )
   private void noWaterLoggingProcessor(
      ServerLevelAccessor serverLevelAccessor,
      BlockPos blockPos1,
      BlockPos blockPos2,
      StructurePlaceSettings structurePlaceSettings,
      RandomSource random,
      int flag,
      CallbackInfoReturnable<Boolean> cir
   ) {
      if (structurePlaceSettings.getProcessors()
         .stream()
         .anyMatch(processor -> ((AccessorStructureProcessor)processor).callGetType() == ModProcessors.WATERLOGGING_FIX.get())) {
         structurePlaceSettings.setLiquidSettings(LiquidSettings.IGNORE_WATERLOGGING);
      }
   }
}
