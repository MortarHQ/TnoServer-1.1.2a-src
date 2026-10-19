package com.bobmowzie.mowziesmobs.mixin;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.config.ConfigurableArmorMaterial;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.ArmorMaterial;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ArmorMaterial.class)
public abstract class ArmorMaterialMixin implements ConfigurableArmorMaterial {
   @Unique
   @Nullable
   private ConfigHandler.ArmorConfig mowziesmobs$config;

   @Override
   public void mowziesmobs$setConfig(ConfigHandler.ArmorConfig config) {
      this.mowziesmobs$config = config;
   }

   @ModifyReturnValue(method = "getDefense", at = @At("RETURN"))
   private int mowziesmobs$configurableDefense(int defense) {
      return this.mowziesmobs$config != null ? (int)(defense * ((Double)this.mowziesmobs$config.damageReductionMultiplier.get()).floatValue()) : defense;
   }

   @ModifyReturnValue(method = "toughness", at = @At("RETURN"))
   private float mowziesmobs$configurableToughness(float toughness) {
      return this.mowziesmobs$config != null ? toughness * ((Double)this.mowziesmobs$config.toughnessMultiplier.get()).floatValue() : toughness;
   }
}
