package com.github.dominickwd04.traddon.client;

import com.github.dominickwd04.traddon.registry.entity.TrAddonMiscEntityTypes;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.NoopRenderer;

public class TrAddonClient {
   public static void init() {
      EntityRendererRegistry.register(TrAddonMiscEntityTypes.CRYSTAL_DUST_CLOUD, NoopRenderer::new);
   }
}
