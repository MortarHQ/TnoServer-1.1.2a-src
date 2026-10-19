package me.decce.gnetum;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.resources.ResourceLocation;

public class Icons {
   public static final ResourceLocation TICK = ResourceLocation.fromNamespaceAndPath("gnetum", "icons/tick.png");
   public static final ResourceLocation EXCLAMATION = ResourceLocation.fromNamespaceAndPath("gnetum", "icons/exclamation.png");
   public static final ResourceLocation QUESTION = ResourceLocation.fromNamespaceAndPath("gnetum", "icons/question.png");

   static {
      Minecraft.getInstance().getTextureManager().register(TICK, new SimpleTexture(TICK));
      Minecraft.getInstance().getTextureManager().register(EXCLAMATION, new SimpleTexture(EXCLAMATION));
      Minecraft.getInstance().getTextureManager().register(QUESTION, new SimpleTexture(QUESTION));
   }
}
