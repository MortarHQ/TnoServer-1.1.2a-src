package com.blackgear.platform.core.mixin.access;

import java.util.Map;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemProperties.class)
public interface ItemPropertiesAccessor {
   @Accessor("GENERIC_PROPERTIES")
   static Map<ResourceLocation, ItemPropertyFunction> getGENERIC_PROPERTIES() {
      throw new UnsupportedOperationException();
   }
}
