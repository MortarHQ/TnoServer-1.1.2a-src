package com.craftix.medieval_buildings.shared.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.List;
import net.minecraft.tags.TagLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = TagLoader.class, remap = false)
public abstract class TagLoaderMix {
   @WrapOperation(
      method = "build(Lnet/minecraft/tags/TagEntry$Lookup;Ljava/util/List;)Lcom/mojang/datafixers/util/Either;",
      at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z")
   )
   private boolean wrapOperation(List instance, Operation<Boolean> original) {
      return true;
   }
}
