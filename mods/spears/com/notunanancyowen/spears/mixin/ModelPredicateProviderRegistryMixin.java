package com.notunanancyowen.spears.mixin;

import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemProperties.class)
public abstract class ModelPredicateProviderRegistryMixin {
   @Shadow
   private static ClampedItemPropertyFunction registerGeneric(ResourceLocation id, ClampedItemPropertyFunction provider) {
      return null;
   }

   static {
      registerGeneric(
         ResourceLocation.fromNamespaceAndPath("spears", "in_gui"),
         (stack, world, entity, seed) -> stack.getEntityRepresentation() != null && stack.getFrame() == null ? 0.0F : 1.0F
      );
   }
}
