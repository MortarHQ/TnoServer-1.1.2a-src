package net.jadenxgamer.elysium_api.impl.mixin.misc;

import java.util.List;
import net.jadenxgamer.elysium_api.impl.util.StructureHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StructureTemplate.class)
public class StructureTemplateMixin {
   @Inject(
      method = "processBlockInfos(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructurePlaceSettings;Ljava/util/List;Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate;)Ljava/util/List;",
      at = @At("RETURN")
   )
   private static void filterSolidStructureVoid(
      ServerLevelAccessor serverLevel,
      BlockPos offset,
      BlockPos pos,
      StructurePlaceSettings settings,
      List<StructureBlockInfo> blockInfos,
      @Nullable StructureTemplate template,
      CallbackInfoReturnable<List<StructureBlockInfo>> cir
   ) {
      List<StructureBlockInfo> list = (List<StructureBlockInfo>)cir.getReturnValue();
      if (list != null) {
         list.removeIf(info -> StructureHelper.IGNORE_BLOCK_SUPPLIERS.contains(info.state().getBlock()));
      }
   }
}
