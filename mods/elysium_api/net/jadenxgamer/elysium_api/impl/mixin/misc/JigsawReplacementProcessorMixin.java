package net.jadenxgamer.elysium_api.impl.mixin.misc;

import net.jadenxgamer.elysium_api.impl.util.StructureHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.JigsawReplacementProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(JigsawReplacementProcessor.class)
public class JigsawReplacementProcessorMixin {
   @Inject(method = "processBlock", at = @At("RETURN"), cancellable = true)
   private void elysium$skipSolidStructureVoid(
      LevelReader serverLevel,
      BlockPos offset,
      BlockPos pos,
      StructureBlockInfo original,
      StructureBlockInfo processed,
      StructurePlaceSettings settings,
      CallbackInfoReturnable<StructureBlockInfo> cir
   ) {
      StructureBlockInfo result = (StructureBlockInfo)cir.getReturnValue();
      if (result != null && StructureHelper.IGNORE_BLOCK_SUPPLIERS.contains(result.state().getBlock())) {
         cir.setReturnValue(null);
      }
   }
}
