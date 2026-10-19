package com.mortal_cultivation.client;

import com.mortal_cultivation.client.renderer.AncientScholarTableRenderer;
import com.mortal_cultivation.init.BlockEntityRegistry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.MOD, value = Dist.CLIENT)
public final class AncientScholarTableClientEvents {
   private AncientScholarTableClientEvents() {
   }

   @SubscribeEvent
   public static void registerRenderers(RegisterRenderers event) {
      event.registerBlockEntityRenderer((BlockEntityType)BlockEntityRegistry.ANCIENT_SCHOLAR_TABLE.get(), AncientScholarTableRenderer::new);
   }
}
