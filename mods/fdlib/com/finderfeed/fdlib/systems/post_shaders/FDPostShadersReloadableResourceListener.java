package com.finderfeed.fdlib.systems.post_shaders;

import com.finderfeed.fdlib.FDLib;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.neoforge.common.NeoForge;

public class FDPostShadersReloadableResourceListener extends SimplePreparableReloadListener<Integer> {
   protected Integer prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
      return 1;
   }

   protected void apply(Integer lolwhat, ResourceManager manager, ProfilerFiller profiler) {
      initializeShaders();
   }

   public static void loadPostChain(FDPostShaderInitializeEvent.PostChainShaderLoadInstance shader) {
      PostChainLoad postChainLoad = shader.postChainLoad();
      PostChain postChain = postChainLoad.loadPostChain(
         Minecraft.getInstance().getTextureManager(), Minecraft.getInstance().getResourceManager(), Minecraft.getInstance().getMainRenderTarget()
      );
      FDPostShadersHandler.POST_SHADERS.add(postChain);
      postChain.resize(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());
      shader.onLoad().accept(postChain);
   }

   public static void initializeShaders() {
      FDLib.LOGGER.info("Initializing shaders...");

      for (PostChain shader : FDPostShadersHandler.POST_SHADERS) {
         shader.close();
      }

      FDPostShadersHandler.POST_SHADERS.clear();
      FDPostShaderInitializeEvent event = new FDPostShaderInitializeEvent();
      NeoForge.EVENT_BUS.post(event);
      List<Runnable> errors = new ArrayList<>();

      for (FDPostShaderInitializeEvent.PostChainShaderLoadInstance shader : event.getPostChainRegistry()) {
         try {
            loadPostChain(shader);
         } catch (Exception e) {
            errors.add(e::printStackTrace);
         }
      }

      if (errors.isEmpty()) {
         FDLib.LOGGER.info("Finished shader initialization.");
      } else {
         for (Runnable error : errors) {
            error.run();
         }

         throw new RuntimeException("Failed to load shaders");
      }
   }
}
