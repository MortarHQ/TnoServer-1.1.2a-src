package com.blackgear.vanillabackport.core.mixin.access;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.metadata.gui.GuiSpriteScaling.NineSlice;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GuiGraphics.class)
public interface GuiGraphicsAccessor {
   @Invoker
   void callBlitNineSlicedSprite(TextureAtlasSprite var1, NineSlice var2, int var3, int var4, int var5, int var6, int var7);
}
