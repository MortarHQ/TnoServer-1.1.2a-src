package com.moepus.byepregen.mixin;

import com.moepus.byepregen.MixinGate;
import net.minecraft.world.level.chunk.PalettedContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@MixinGate(conflictingMods = "lithium")
@Mixin(value = PalettedContainer.class, remap = false)
public class PalettedContainerNoLithiumMixin {
   @Overwrite
   public void acquire() {
   }

   @Overwrite
   public void release() {
   }
}
