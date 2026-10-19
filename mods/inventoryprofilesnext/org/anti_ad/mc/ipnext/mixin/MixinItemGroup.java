package org.anti_ad.mc.ipnext.mixin;

import net.minecraft.world.item.CreativeModeTab;
import org.anti_ad.mc.ipnext.mixinhelpers.IMixinItemGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(CreativeModeTab.class)
abstract class MixinItemGroup implements IMixinItemGroup {
   @Unique
   private int ipnPriorityIndex = -1;

   @Override
   public int getIPNPriorityIndex() {
      return this.ipnPriorityIndex;
   }

   @Override
   public void setIPNPriorityIndex(int var1) {
      this.ipnPriorityIndex = var1;
   }
}
