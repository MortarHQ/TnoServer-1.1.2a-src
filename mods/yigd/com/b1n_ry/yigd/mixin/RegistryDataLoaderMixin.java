package com.b1n_ry.yigd.mixin;

import com.b1n_ry.yigd.config.ExtraFeaturesConfig;
import com.b1n_ry.yigd.config.YigdConfig;
import com.google.gson.JsonElement;
import com.mojang.serialization.Decoder;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RegistryDataLoader.class)
public class RegistryDataLoaderMixin {
   @Inject(method = "loadElementFromResource", at = @At("HEAD"), cancellable = true)
   private static <E> void maybeCancelResourceLoad(
      WritableRegistry<E> registry,
      Decoder<E> decoder,
      RegistryOps<JsonElement> ops,
      ResourceKey<E> key,
      Resource resource,
      RegistrationInfo entryInfo,
      CallbackInfo ci
   ) {
      ExtraFeaturesConfig extraFeaturesConfig = YigdConfig.getConfig().extraFeatures;
      if (key.equals(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath("yigd", "soulbound")))
         && !extraFeaturesConfig.enableSoulbound) {
         ci.cancel();
      }

      if (key.equals(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath("yigd", "death_sight")))
         && !extraFeaturesConfig.deathSightEnchant.enabled) {
         ci.cancel();
      }
   }
}
