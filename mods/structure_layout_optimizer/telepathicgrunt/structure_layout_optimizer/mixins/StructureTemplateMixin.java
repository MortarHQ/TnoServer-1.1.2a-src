package telepathicgrunt.structure_layout_optimizer.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.Palette;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import telepathicgrunt.structure_layout_optimizer.utils.StructureTemplateOptimizer;

@Mixin(StructureTemplate.class)
public class StructureTemplateMixin {
   @Redirect(
      method = "placeInWorld(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructurePlaceSettings;Lnet/minecraft/util/RandomSource;I)Z",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate$Palette;blocks()Ljava/util/List;")
   )
   private List<StructureBlockInfo> structureLayoutOptimizer$shrinkStructureTemplateBlocksList(
      Palette palette, @Local(ordinal = 0, argsOnly = true) BlockPos offset, @Local(ordinal = 0, argsOnly = true) StructurePlaceSettings settings
   ) {
      return StructureTemplateOptimizer.getStructureBlockInfosInBounds(palette, offset, settings);
   }
}
