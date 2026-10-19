package com.iafenvoy.iceandfire.render;

import com.iafenvoy.iceandfire.config.IafClientConfig;
import com.iafenvoy.iceandfire.registry.IafStatusEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SirenShaderRenderHelper {
   private static final ResourceLocation SIREN_SHADER = ResourceLocation.fromNamespaceAndPath("iceandfire", "shaders/post/siren.json");

   public static void tick(Minecraft client) {
      LocalPlayer player = client.player;
      if (player != null) {
         GameRenderer renderer = Minecraft.getInstance().gameRenderer;
         if ((Boolean)IafClientConfig.INSTANCE.sirenShader.getValue()
            && player.hasEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder((MobEffect)IafStatusEffects.SIREN_CHARM.get()))) {
            enableShader(renderer);
         } else {
            disableShader(renderer);
         }
      }
   }

   private static boolean enabled(GameRenderer renderer) {
      PostChain processor = renderer.currentEffect();
      return processor != null && SIREN_SHADER.toString().equals(processor.getName());
   }

   private static void enableShader(GameRenderer renderer) {
      if (!enabled(renderer)) {
         renderer.loadEffect(SIREN_SHADER);
      }
   }

   private static void disableShader(GameRenderer renderer) {
      if (enabled(renderer)) {
         renderer.shutdownEffect();
      }
   }
}
