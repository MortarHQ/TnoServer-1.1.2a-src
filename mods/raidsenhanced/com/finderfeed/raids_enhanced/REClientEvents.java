package com.finderfeed.raids_enhanced;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.head.HeadBoneTransformation;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.renderer.FDEntityRenderLayerOptions;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.renderer.FDEntityRendererBuilder;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.renderer.FDEntityTransformation;
import com.finderfeed.fdlib.util.client.NullEntityRenderer;
import com.finderfeed.fdlib.util.rendering.FDRenderUtil;
import com.finderfeed.raids_enhanced.content.entities.ball_lightning.BallLightningRenderer;
import com.finderfeed.raids_enhanced.content.entities.engineer.ZapperIllagerRenderer;
import com.finderfeed.raids_enhanced.content.entities.falling_block.REFallingBlockRenderer;
import com.finderfeed.raids_enhanced.content.entities.raid_blimp.cannons.RaidBlimpCannonBonesController;
import com.finderfeed.raids_enhanced.content.entities.raid_blimp.raid_airship_parts.RaidBlimpPartRenderer;
import com.finderfeed.raids_enhanced.content.entities.raid_drill.RaidDrill;
import com.finderfeed.raids_enhanced.content.entities.raid_drill.RaidDrillRenderer;
import com.finderfeed.raids_enhanced.content.entities.vertical_lightning_strike.VerticalLightningStrikeAttackRenderer;
import com.finderfeed.raids_enhanced.content.items.handcannon.HandCannonClientItemExtensions;
import com.finderfeed.raids_enhanced.content.particles.ball_lightning_explosion.BallLightningExplosionParticle;
import com.finderfeed.raids_enhanced.content.particles.explosion_particle.RExplosionParticle;
import com.finderfeed.raids_enhanced.content.particles.lightning_explosion.LightningExplosionParticle;
import com.finderfeed.raids_enhanced.content.particles.lightning_strike.LightningStrikeParticle;
import com.finderfeed.raids_enhanced.content.particles.slash_particle.SlashParticle;
import com.finderfeed.raids_enhanced.content.particles.vertical_lightning.VerticalLightningParticle;
import com.finderfeed.raids_enhanced.init.REEntities;
import com.finderfeed.raids_enhanced.init.REItems;
import com.finderfeed.raids_enhanced.init.REModels;
import com.finderfeed.raids_enhanced.init.REParticles;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = "raidsenhanced", value = Dist.CLIENT)
public class REClientEvents {
   @SubscribeEvent
   public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
      event.registerItem(new HandCannonClientItemExtensions(), new Item[]{REItems.HANDCANNON.get()});
   }

   @SubscribeEvent
   public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
      event.registerSpriteSet(REParticles.EXPLOSION.get(), RExplosionParticle.Factory::new);
      event.registerSpriteSet(REParticles.LIGHTNING_EXPLOSION.get(), LightningExplosionParticle.Provider::new);
      event.registerSpriteSet(REParticles.LIGHTNING_STRIKE.get(), LightningStrikeParticle.Provider::new);
      event.registerSpriteSet(REParticles.BALL_LIGHTNING_EXPLOSION.get(), BallLightningExplosionParticle.Provider::new);
      event.registerSpriteSet(REParticles.ELECTRIC_SLASH.get(), SlashParticle.Provider::new);
      event.registerSpriteSet(REParticles.VERTICAL_LIGHTNING.get(), VerticalLightningParticle.Provider::new);
   }

   @SubscribeEvent
   public static void registerRenderers(RegisterRenderers event) {
      event.registerEntityRenderer(REEntities.FALLING_BLOCK.get(), REFallingBlockRenderer::new);
      event.registerEntityRenderer(REEntities.VERTICAL_LIGHTNING.get(), VerticalLightningStrikeAttackRenderer::new);
      event.registerEntityRenderer(REEntities.BALL_LIGHTNING.get(), BallLightningRenderer::new);
      event.registerEntityRenderer(REEntities.ENGINEER_STAFF_CAST_ENTTITY.get(), NullEntityRenderer::new);
      FDEntityTransformation<RaidDrill> drillTransform = (raidDrill, poseStack, v) -> {
         float time = raidDrill.tickCount + v;
         float p = Mth.clamp((raidDrill.hurtTime - v) / raidDrill.hurtDuration, 0.0F, 1.0F);
         poseStack.mulPose(Axis.YP.rotationDegrees((float)Math.sin(time * 2.0F) * 5.0F * p));
      };
      event.registerEntityRenderer(
         REEntities.RAID_DRILL.get(),
         FDEntityRendererBuilder.builder()
            .addLayer(
               FDEntityRenderLayerOptions.builder()
                  .model(REModels.RAID_DRILL)
                  .renderType(RenderType.entityCutoutNoCull(RaidsEnhanced.location("textures/entities/raider_drill.png")))
                  .transformation(drillTransform)
                  .build()
            )
            .addLayer(
               FDEntityRenderLayerOptions.builder()
                  .model(REModels.RAID_DRILL)
                  .renderType(RenderType.entityTranslucentCull(RaidsEnhanced.location("textures/entities/raider_drill_culled.png")))
                  .transformation(drillTransform)
                  .build()
            )
            .shouldRender((raidDrill, frustum, v, v1, v2) -> raidDrill.isVisible())
            .freeRender(new RaidDrillRenderer())
            .build()
      );
      event.registerEntityRenderer(
         REEntities.PLAYER_BLIMP.get(),
         FDEntityRendererBuilder.builder()
            .addLayer(
               FDEntityRenderLayerOptions.builder()
                  .model(REModels.PLAYER_BLIMP)
                  .renderType(RenderType.entityCutoutNoCull(RaidsEnhanced.location("textures/entities/player_blimp.png")))
                  .build()
            )
            .build()
      );
      event.registerEntityRenderer(
         REEntities.ZAPPER_ILLAGER.get(),
         FDEntityRendererBuilder.builder()
            .addLayer(
               FDEntityRenderLayerOptions.builder()
                  .model(REModels.ELECTROMANCER)
                  .renderType(RenderType.entityCutoutNoCull(RaidsEnhanced.location("textures/entities/electromancer.png")))
                  .addBoneController("head", new HeadBoneTransformation())
                  .transformation((engineerEntity, poseStack, v) -> {
                     if (engineerEntity.deathTime > 0) {
                        float f = (engineerEntity.deathTime + v - 1.0F) / 20.0F * 1.6F;
                        f = Mth.sqrt(f);
                        if (f > 1.0F) {
                           f = 1.0F;
                        }

                        poseStack.mulPose(Axis.ZP.rotationDegrees(f * 90.0F));
                     }
                  })
                  .build()
            )
            .shouldRender((electromancerEntity, frustum, v, v1, v2) -> true)
            .freeRender(new ZapperIllagerRenderer())
            .build()
      );
      event.registerEntityRenderer(
         REEntities.GOLEM_OF_LAST_RESORT.get(),
         FDEntityRendererBuilder.builder()
            .addLayer(
               FDEntityRenderLayerOptions.builder()
                  .model(REModels.GOLEM_OF_LAST_RESORT)
                  .renderType(RenderType.entityCutoutNoCull(RaidsEnhanced.location("textures/entities/golem_of_last_resort.png")))
                  .addBoneController("head", new HeadBoneTransformation())
                  .build()
            )
            .addLayer(
               FDEntityRenderLayerOptions.builder()
                  .model(REModels.GOLEM_OF_LAST_RESORT)
                  .renderType(RenderType.eyes(RaidsEnhanced.location("textures/entities/golem_of_last_resort_emissive.png")))
                  .addBoneController("head", new HeadBoneTransformation())
                  .build()
            )
            .build()
      );
      event.registerEntityRenderer(
         REEntities.RAID_BLIMP.get(),
         FDEntityRendererBuilder.builder()
            .addLayer(
               FDEntityRenderLayerOptions.builder()
                  .model(REModels.RAID_BLIMP)
                  .renderType(RenderType.entityCutoutNoCull(RaidsEnhanced.location("textures/entities/raid_airship.png")))
                  .addBoneController("airship", new RaidBlimpCannonBonesController())
                  .build()
            )
            .addLayer(
               FDEntityRenderLayerOptions.builder()
                  .model(REModels.RAID_BLIMP)
                  .renderType(RenderType.eyes(RaidsEnhanced.location("textures/entities/raid_airship_emissive.png")))
                  .build()
            )
            .build()
      );
      event.registerEntityRenderer(
         REEntities.RAID_BLIMP_CANNON_PROJECTILE.get(),
         FDEntityRendererBuilder.builder()
            .addLayer(
               FDEntityRenderLayerOptions.builder()
                  .model(REModels.RAID_BLIMP_CANNON_PROJECTILE)
                  .renderType(RenderType.entityCutoutNoCull(RaidsEnhanced.location("textures/entities/raid_blimp_cannon_projectile.png")))
                  .transformation((entity, poseStack, v) -> {
                     Vec3 deltaMovement = entity.getDeltaMovement();
                     poseStack.translate(0.0F, entity.getBbHeight() / 2.0F, 0.0F);
                     FDRenderUtil.applyMovementMatrixRotations(poseStack, deltaMovement);
                  })
                  .build()
            )
            .build()
      );
      event.registerEntityRenderer(
         REEntities.BOMB.get(),
         FDEntityRendererBuilder.builder()
            .addLayer(
               FDEntityRenderLayerOptions.builder()
                  .model(REModels.RAID_BLIMP_BOMB)
                  .renderType(RenderType.entityCutoutNoCull(RaidsEnhanced.location("textures/entities/raid_airship_bomb.png")))
                  .transformation((entity, poseStack, v) -> {
                     Vec3 deltaMovement = entity.getDeltaMovement();
                     float time = entity.tickCount + v;
                     poseStack.translate(0.0F, entity.getBbHeight() / 2.0F, 0.0F);
                     FDRenderUtil.applyMovementMatrixRotations(poseStack, deltaMovement);
                     poseStack.mulPose(Axis.XP.rotationDegrees(-time * 30.0F));
                  })
                  .build()
            )
            .build()
      );
      event.registerEntityRenderer(REEntities.RAID_AIRSHIP_PART.get(), RaidBlimpPartRenderer::new);
   }
}
