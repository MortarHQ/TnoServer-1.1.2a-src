package com.blackgear.platform.client.v2.render;

import com.blackgear.platform.client.v2.render.neoforge.BuiltinItemRendererRegistryImpl;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public abstract class BuiltinItemRendererRegistry {
   @ExpectPlatform
   @Transformed
   public static BuiltinItemRendererRegistry getInstance() {
      return BuiltinItemRendererRegistryImpl.getInstance();
   }

   public abstract void register(ItemLike var1, BuiltinItemRendererRegistry.DynamicItemRenderer var2);

   public abstract BuiltinItemRendererRegistry.DynamicItemRenderer get(ItemLike var1);

   @FunctionalInterface
   public interface DynamicItemRenderer {
      void render(ItemStack var1, ItemDisplayContext var2, PoseStack var3, MultiBufferSource var4, int var5, int var6);
   }
}
