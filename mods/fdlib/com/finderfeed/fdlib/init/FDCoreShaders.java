package com.finderfeed.fdlib.init;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import java.io.IOException;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

@EventBusSubscriber(modid = "fdlib", bus = Bus.MOD, value = Dist.CLIENT)
public class FDCoreShaders {
   public static ShaderInstance NOISE = null;
   public static ShaderInstance PARTICLE_NO_DISCARD = null;

   @SubscribeEvent
   public static void registerShaders(RegisterShadersEvent event) throws IOException {
      event.registerShader(
         new ShaderInstance(event.getResourceProvider(), ResourceLocation.tryBuild("fdlib", "noise"), DefaultVertexFormat.POSITION_TEX_COLOR),
         inst -> NOISE = inst
      );
      event.registerShader(
         new ShaderInstance(event.getResourceProvider(), ResourceLocation.tryBuild("fdlib", "particle"), DefaultVertexFormat.PARTICLE),
         inst -> PARTICLE_NO_DISCARD = inst
      );
   }
}
