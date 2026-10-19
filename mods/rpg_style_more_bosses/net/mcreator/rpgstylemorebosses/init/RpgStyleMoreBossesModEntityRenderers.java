package net.mcreator.rpgstylemorebosses.init;

import net.mcreator.rpgstylemorebosses.client.renderer.BeaconActiveRenderer;
import net.mcreator.rpgstylemorebosses.client.renderer.BeaconRenderer;
import net.mcreator.rpgstylemorebosses.client.renderer.BlockBarrierRenderer;
import net.mcreator.rpgstylemorebosses.client.renderer.BossCounterRenderer;
import net.mcreator.rpgstylemorebosses.client.renderer.ExoframeBeamRenderer;
import net.mcreator.rpgstylemorebosses.client.renderer.ExoframeTickRenderer;
import net.mcreator.rpgstylemorebosses.client.renderer.KriStrarzRenderer;
import net.mcreator.rpgstylemorebosses.client.renderer.RunicSpiderRenderer;
import net.mcreator.rpgstylemorebosses.client.renderer.TheExoframeRenderer;
import net.mcreator.rpgstylemorebosses.client.renderer.TheExoframeSpawnRenderer;
import net.mcreator.rpgstylemorebosses.client.renderer.TheExoframeVFX2Renderer;
import net.mcreator.rpgstylemorebosses.client.renderer.TheExoframeVFXRenderer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;

@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
public class RpgStyleMoreBossesModEntityRenderers {
   @SubscribeEvent
   public static void registerEntityRenderers(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)RpgStyleMoreBossesModEntities.RUNIC_SPIDER.get(), RunicSpiderRenderer::new);
      event.registerEntityRenderer((EntityType)RpgStyleMoreBossesModEntities.KRI_STRARZ.get(), KriStrarzRenderer::new);
      event.registerEntityRenderer((EntityType)RpgStyleMoreBossesModEntities.THE_EXOFRAME.get(), TheExoframeRenderer::new);
      event.registerEntityRenderer((EntityType)RpgStyleMoreBossesModEntities.THE_EXOFRAME_VFX.get(), TheExoframeVFXRenderer::new);
      event.registerEntityRenderer((EntityType)RpgStyleMoreBossesModEntities.THE_EXOFRAME_VFX_2.get(), TheExoframeVFX2Renderer::new);
      event.registerEntityRenderer((EntityType)RpgStyleMoreBossesModEntities.BEACON.get(), BeaconRenderer::new);
      event.registerEntityRenderer((EntityType)RpgStyleMoreBossesModEntities.BEACON_ACTIVE.get(), BeaconActiveRenderer::new);
      event.registerEntityRenderer((EntityType)RpgStyleMoreBossesModEntities.BOSS_COUNTER.get(), BossCounterRenderer::new);
      event.registerEntityRenderer((EntityType)RpgStyleMoreBossesModEntities.BLOCK_BARRIER.get(), BlockBarrierRenderer::new);
      event.registerEntityRenderer((EntityType)RpgStyleMoreBossesModEntities.THE_EXOFRAME_SPAWN.get(), TheExoframeSpawnRenderer::new);
      event.registerEntityRenderer((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get(), ExoframeBeamRenderer::new);
      event.registerEntityRenderer((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_TICK.get(), ExoframeTickRenderer::new);
   }
}
