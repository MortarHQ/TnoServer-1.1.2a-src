package telepathicgrunt.structure_layout_optimizer.mixins;

import java.util.List;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import telepathicgrunt.structure_layout_optimizer.utils.GeneralUtils;

@Mixin(SinglePoolElement.class)
public class SinglePoolElementMixin {
   @Redirect(
      method = "getShuffledJigsawBlocks(Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Rotation;Lnet/minecraft/util/RandomSource;)Ljava/util/List;",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/Util;shuffle(Ljava/util/List;Lnet/minecraft/util/RandomSource;)V")
   )
   private void structureLayoutOptimizer$fasterJigsawListShuffling1(List<StructureBlockInfo> list, RandomSource randomSource) {
      GeneralUtils.shuffleAndPrioritize(list, randomSource);
   }

   @Redirect(
      method = "getShuffledJigsawBlocks(Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Rotation;Lnet/minecraft/util/RandomSource;)Ljava/util/List;",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/pools/SinglePoolElement;sortBySelectionPriority(Ljava/util/List;)V")
   )
   private void structureLayoutOptimizer$fasterJigsawListShuffling2(List<StructureBlockInfo> structureBlockInfos) {
   }
}
