package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.TensuraMoreSkills;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import java.io.IOException;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

public class PathsClientFX {
   public static ShaderInstance AURORA_SHADER;

   public static void onRegisterShaders(RegisterShadersEvent event) {
      try {
         event.registerShader(
            new ShaderInstance(
               event.getResourceProvider(), ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "aurora"), DefaultVertexFormat.POSITION_TEX
            ),
            shader -> AURORA_SHADER = shader
         );
      } catch (IOException e) {
         TensuraMoreSkills.LOGGER.error("Failed to register aurora shader", e);
         AURORA_SHADER = null;
      }
   }
}
