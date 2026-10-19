package org.anti_ad.mc.ipnext.inventory;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.inventory.AbstractContainerMenu;

final class AreaType$minus$1 implements AreaType {
   AreaType$minus$1(AreaType var1, AreaType var2) {
      this.this$0 = var1;
      this.$other = var2;
   }

   @Override
   public final ItemArea getItemArea(AbstractContainerMenu var1, List var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return this.this$0.getItemArea(var1, var2).minus(this.$other.getItemArea(var1, var2));
   }

   @Override
   public final AreaType plus(AreaType var1) {
      return AreaType$DefaultImpls.plus(this, var1);
   }

   @Override
   public final AreaType minus(AreaType var1) {
      return AreaType$DefaultImpls.minus(this, var1);
   }
}
