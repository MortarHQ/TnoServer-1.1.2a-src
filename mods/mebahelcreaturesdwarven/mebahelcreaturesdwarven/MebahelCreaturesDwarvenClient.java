package mebahelcreaturesdwarven;

import mebahelcreaturesdwarven.block.dwemer_chest.DwemerChestRenderer;
import mebahelcreaturesdwarven.block.dwemer_metal_pipe_gear.DwarvenMetalPipeGearRenderer;
import mebahelcreaturesdwarven.block.dwemer_spider_block.DwemerSpiderBlockRenderer;
import mebahelcreaturesdwarven.block.entity.ModBlockEntities;
import mebahelcreaturesdwarven.entity.ModEntities;
import mebahelcreaturesdwarven.entity.dwarven_centurion.DwarvenCenturionRenderer;
import mebahelcreaturesdwarven.entity.dwarven_sphere.DwarvenSphereRenderer;
import mebahelcreaturesdwarven.entity.dwarven_spider.DwarvenSpiderRenderer;
import mebahelcreaturesdwarven.entity.dwarven_spider_guardian.DwarvenSpiderGuardianRenderer;
import mebahelcreaturesdwarven.particle.ModParticles;
import mebahelcreaturesdwarven.particle.SteamParticle;
import mebahelcreaturesdwarven.projectiles.dwarven_arrow.DwarvenArrowRenderer;
import mebahelcreaturesdwarven.projectiles.dwemer_centurion.SteamProjectileRenderer;
import mebahelcreaturesdwarven.sound.DwemerSphereWalkSoundHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.class_5616;

@Environment(EnvType.CLIENT)
public class MebahelCreaturesDwarvenClient implements ClientModInitializer {
   public void onInitializeClient() {
      EntityRendererRegistry.register(ModEntities.STEAM_PROJECTILE, SteamProjectileRenderer::new);
      EntityRendererRegistry.register(ModEntities.DWARVEN_ARROW, DwarvenArrowRenderer::new);
      EntityRendererRegistry.register(ModEntities.DWARVEN_SPHERE, DwarvenSphereRenderer::new);
      EntityRendererRegistry.register(ModEntities.DWARVEN_CENTURION, DwarvenCenturionRenderer::new);
      EntityRendererRegistry.register(ModEntities.DWARVEN_SPIDER, DwarvenSpiderRenderer::new);
      EntityRendererRegistry.register(ModEntities.DWARVEN_SPIDER_GUARDIAN, DwarvenSpiderGuardianRenderer::new);
      class_5616.method_32144(ModBlockEntities.DWEMER_SPIDER_BLOCK_ENTITY, DwemerSpiderBlockRenderer::new);
      class_5616.method_32144(ModBlockEntities.DWEMER_CHEST_ENTITY, DwemerChestRenderer::new);
      class_5616.method_32144(ModBlockEntities.DWARVEN_METAL_PIPE_GEAR, DwarvenMetalPipeGearRenderer::new);
      ParticleFactoryRegistry.getInstance().register(ModParticles.STEAM_PARTICLE, SteamParticle.Factory::new);
      DwemerSphereWalkSoundHandler.init();
      Thread.setDefaultUncaughtExceptionHandler((t, e) -> e.printStackTrace());
   }
}
