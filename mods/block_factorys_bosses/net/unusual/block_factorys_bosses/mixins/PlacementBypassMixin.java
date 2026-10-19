package net.unusual.block_factorys_bosses.mixins;

import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = JigsawStructure.class, priority = 754)
public abstract class PlacementBypassMixin {
   @ModifyConstant(method = "lambda$static$10", constant = @Constant(intValue = 128))
   private static int bossesrise$codec$changeMaxDistance(int constant) {
      return 2048;
   }

   @ModifyConstant(method = "verifyRange", constant = @Constant(intValue = 128))
   private static int bossesrise$verifyRange$changeMaxDistance(int constant) {
      return 2048;
   }
}
