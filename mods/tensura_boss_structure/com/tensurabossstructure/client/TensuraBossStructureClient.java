package com.tensurabossstructure.client;

import com.tensurabossstructure.registry.ModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = "tensura_boss_structure")
public class TensuraBossStructureClient {
   @SubscribeEvent
   public static void onClientSetup(FMLClientSetupEvent event) {
      event.enqueueWork(() -> {
         ItemBlockRenderTypes.setRenderLayer((Block)ModBlocks.RIMURU_OGRE_FIGHT_SPAWNER.get(), RenderType.cutout());
         ItemBlockRenderTypes.setRenderLayer((Block)ModBlocks.HINATA_SPAWNER.get(), RenderType.cutout());
         ItemBlockRenderTypes.setRenderLayer((Block)ModBlocks.ORC_DISASTER_SPAWNER.get(), RenderType.cutout());
         ItemBlockRenderTypes.setRenderLayer((Block)ModBlocks.LUMINOUS_SPAWNER.get(), RenderType.cutout());
         ItemBlockRenderTypes.setRenderLayer((Block)ModBlocks.CARRION_SPAWNER.get(), RenderType.cutout());
         ItemBlockRenderTypes.setRenderLayer((Block)ModBlocks.ELEMENTAL_COLOSSUS_SPAWNER.get(), RenderType.cutout());
         ItemBlockRenderTypes.setRenderLayer((Block)ModBlocks.OTHERWORLDER_SPAWNER.get(), RenderType.cutout());
      });
   }
}
