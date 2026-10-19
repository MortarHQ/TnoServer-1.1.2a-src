package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveAnvilEvents;
import net.minecraft.class_1263;
import net.minecraft.class_1661;
import net.minecraft.class_1706;
import net.minecraft.class_1799;
import net.minecraft.class_3914;
import net.minecraft.class_3915;
import net.minecraft.class_3917;
import net.minecraft.class_4861;
import net.minecraft.class_9334;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import oshi.util.tuples.Triplet;

@Mixin(value = class_1706.class, priority = 1001)
public abstract class AnvilMenuMixin extends class_4861 {
   @Shadow
   private String field_7774;
   @Shadow
   private int field_7776;
   @Final
   @Shadow
   private class_3915 field_7770;

   public AnvilMenuMixin(class_3917<?> menuType, int i, class_1661 inventory, class_3914 containerLevelAccess) {
      super(menuType, i, inventory, containerLevelAccess);
   }

   @Inject(method = "createResult()V", at = @At("TAIL"))
   public void onCreateAnvilResult(CallbackInfo info) {
      class_1706 anvilmenu = (class_1706)this;
      class_1263 inputslots = this.field_22480;
      class_1799 left = inputslots.method_5438(0);
      class_1799 right = inputslots.method_5438(1);
      class_1799 output = this.field_22479.method_5438(0);
      int baseCost = (Integer)left.method_57825(class_9334.field_49639, 0) + (right.method_7960() ? 0 : (Integer)right.method_57825(class_9334.field_49639, 0));
      Triplet<Integer, Integer, class_1799> triple = ((CollectiveAnvilEvents.Anvil_Change)CollectiveAnvilEvents.ANVIL_CHANGE.invoker())
         .onAnvilChange(anvilmenu, left, right, output, this.field_7774, baseCost, this.field_22482);
      if (triple != null) {
         if ((Integer)triple.getA() >= 0) {
            this.field_7770.method_17404((Integer)triple.getA());
         }

         if ((Integer)triple.getB() >= 0) {
            this.field_7776 = (Integer)triple.getB();
         }

         if (triple.getC() != null) {
            this.field_22479.method_5447(0, (class_1799)triple.getC());
         }
      }
   }
}
