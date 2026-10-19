package com.tensura_tno.mixin.client;

import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "io.github.manasmods.tensura.race.template.EvolutionRequirement$EPRequirement", remap = false)
public class EPRequirementFormatMixin {
   @Shadow
   public double getRequirement() {
      throw new AssertionError();
   }

   @Inject(method = "getRequirementComponent", at = @At("HEAD"), cancellable = true, remap = false)
   private void tensuraTno$formatEPNumber(CallbackInfoReturnable<Component> cir) {
      String formatted = tensuraTno$formatEP(this.getRequirement());
      cir.setReturnValue(Component.translatable("tensura.evolution_menu.ep_requirement", new Object[]{formatted}));
   }

   private static String tensuraTno$formatEP(double value) {
      if (value >= 1.0E9) {
         double v = value / 1.0E9;
         return tensuraTno$stripTrailingZero(v) + "G";
      } else if (value >= 1000000.0) {
         double v = value / 1000000.0;
         return tensuraTno$stripTrailingZero(v) + "M";
      } else if (value >= 1000.0) {
         double v = value / 1000.0;
         return tensuraTno$stripTrailingZero(v) + "k";
      } else {
         long lv = (long)value;
         return lv == value ? String.valueOf(lv) : String.valueOf(value);
      }
   }

   private static String tensuraTno$stripTrailingZero(double v) {
      long lv = (long)v;
      return lv == v ? String.valueOf(lv) : String.format("%.1f", v);
   }
}
