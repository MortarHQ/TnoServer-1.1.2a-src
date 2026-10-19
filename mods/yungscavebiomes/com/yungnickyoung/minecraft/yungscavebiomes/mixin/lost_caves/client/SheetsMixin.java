package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves.client;

import com.yungnickyoung.minecraft.yungscavebiomes.module.DecoratedPotPatternsModule;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sheets.class)
public abstract class SheetsMixin {
   @Unique
   private static final Map<ResourceKey<String>, Material> CUSTOM_DECORATED_POT_MATERIALS = new HashMap<>();

   @Shadow
   private static Material createDecoratedPotMaterial(ResourceLocation $$0) {
      return null;
   }

   @Inject(method = "getDecoratedPotMaterial", at = @At("HEAD"), cancellable = true)
   private static void yungscavebiomes_createCustomPotteryMaterials(ResourceKey<String> key, CallbackInfoReturnable<Material> cir) {
      if (key != null) {
         if (DecoratedPotPatternsModule.isCustomRegisteredKey(key)) {
            if (!CUSTOM_DECORATED_POT_MATERIALS.containsKey(key)) {
               CUSTOM_DECORATED_POT_MATERIALS.put(key, createDecoratedPotMaterial(key.location()));
            }

            cir.setReturnValue(CUSTOM_DECORATED_POT_MATERIALS.get(key));
         }
      }
   }
}
