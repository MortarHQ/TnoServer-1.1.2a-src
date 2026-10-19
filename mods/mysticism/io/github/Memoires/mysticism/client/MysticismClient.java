package io.github.Memoires.mysticism.client;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import io.github.Memoires.mysticism.client.model.BlackHoleModel;
import io.github.Memoires.mysticism.client.renderer.BlackHoleRenderer;
import io.github.Memoires.mysticism.client.renderer.MagicBoltRenderer;
import io.github.Memoires.mysticism.client.renderer.ModModelLayers;
import io.github.Memoires.mysticism.client.renderer.OkamiRenderer;
import io.github.Memoires.mysticism.client.renderer.entity.monster.MagisteelGolemRenderer;
import io.github.Memoires.mysticism.client.renderer.entity.skill.BlueFlareRenderer;
import io.github.Memoires.mysticism.client.renderer.entity.skill.BubbleShieldBarrierRenderer;
import io.github.Memoires.mysticism.client.renderer.entity.skill.DispenserRenderer;
import io.github.Memoires.mysticism.client.renderer.entity.skill.DraconicMeteorRenderer;
import io.github.Memoires.mysticism.client.renderer.entity.skill.DraconicPulseRenderer;
import io.github.Memoires.mysticism.client.renderer.entity.skill.EnkiduEntityRenderer;
import io.github.Memoires.mysticism.client.renderer.entity.skill.GateEntityRenderer;
import io.github.Memoires.mysticism.client.renderer.entity.skill.KamuiPortalRenderer;
import io.github.Memoires.mysticism.client.renderer.entity.skill.ProviderHaloRenderer;
import io.github.Memoires.mysticism.client.renderer.entity.skill.SentryBulletRenderer;
import io.github.Memoires.mysticism.client.renderer.entity.skill.SentryMissileProjectileRenderer;
import io.github.Memoires.mysticism.client.renderer.entity.skill.SentryRenderer;
import io.github.Memoires.mysticism.client.renderer.entity.skill.ShadowBallProjectileRenderer;
import io.github.Memoires.mysticism.client.renderer.entity.skill.ShadowImpEntityRenderer;
import io.github.Memoires.mysticism.client.renderer.entity.skill.TailedBeastBombProjectileRenderer;
import io.github.Memoires.mysticism.handler.client.JinchurikiInputHandler;
import io.github.Memoires.mysticism.handler.client.PlayerInputHandler;
import io.github.Memoires.mysticism.registry.entity.MysticismMonsterEntityTypes;
import io.github.Memoires.mysticism.registry.entity.MysticismProjectileEntityTypes;
import io.github.Memoires.mysticism.registry.entity.MysticismSkillEntityTypes;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.client.entity.human.CloneRenderer;
import io.github.manasmods.tensura.client.entity.human.OtherworlderRenderer;
import io.github.manasmods.tensura.client.entity.spike.MagicSpikeRenderer;
import io.github.manasmods.tensura.config.client.MiscClientConfig;
import net.minecraft.client.renderer.entity.NoopRenderer;

public class MysticismClient {
   public static MiscClientConfig CONFIG = (MiscClientConfig)ConfigRegistry.getConfig(MiscClientConfig.class);

   public static void init() {
      registerRenderer();
      registerLivingEntityRenderer();
      registerEntityRenderer();
      PlayerInputHandler.init();
      JinchurikiInputHandler.init();
   }

   public static void registerLivingEntityRenderer() {
      EntityRendererRegistry.register(MysticismMonsterEntityTypes.OKAMI, context -> new OkamiRenderer(context));
      EntityRendererRegistry.register(MysticismMonsterEntityTypes.CLAY_GOLEM, context -> new MagisteelGolemRenderer(context, true));
      EntityRendererRegistry.register(MysticismSkillEntityTypes.RELAPSE_CLONE, CloneRenderer::new);
      EntityRendererRegistry.register(MysticismSkillEntityTypes.UNCONTROLLED_JINCHURIKI, CloneRenderer::new);
      EntityRendererRegistry.register(MysticismMonsterEntityTypes.MEMOIRES, context -> new OtherworlderRenderer(context, false));
      EntityRendererRegistry.register(MysticismMonsterEntityTypes.SHADOW_IMP, ShadowImpEntityRenderer::new);
   }

   public static void registerEntityRenderer() {
      EntityRendererRegistry.register(MysticismSkillEntityTypes.SOLIDIFICATION_SHIELD, NoopRenderer::new);
      EntityRendererRegistry.register(MysticismSkillEntityTypes.ETERNAL_DOMAIN, NoopRenderer::new);
      EntityRendererRegistry.register(MysticismSkillEntityTypes.PROVIDER_HALO, ProviderHaloRenderer::new);
      EntityRendererRegistry.register(MysticismProjectileEntityTypes.STASIS_SHOT, NoopRenderer::new);
      EntityRendererRegistry.register(MysticismSkillEntityTypes.BLUE_FLARE, BlueFlareRenderer::new);
      EntityRendererRegistry.register(MysticismProjectileEntityTypes.BLUE_FLARE_PROJECTILE, MagicBoltRenderer::new);
      EntityRendererRegistry.register(MysticismProjectileEntityTypes.DRACONIC_PULSE_PROJECTILE, DraconicPulseRenderer::new);
      EntityRendererRegistry.register(MysticismProjectileEntityTypes.DRACONIC_BREATH_PROJECTILE, NoopRenderer::new);
      EntityRendererRegistry.register(MysticismProjectileEntityTypes.DRACONIC_METEOR_PROJECTILE, DraconicMeteorRenderer::new);
      EntityRendererRegistry.register(MysticismSkillEntityTypes.KAMUI_PORTAL, KamuiPortalRenderer::new);
      EntityRendererRegistry.register(MysticismSkillEntityTypes.BUBBLE_SHIELD_BARRIER, BubbleShieldBarrierRenderer::new);
      EntityRendererRegistry.register(MysticismSkillEntityTypes.SENTRY, SentryRenderer::new);
      EntityRendererRegistry.register(MysticismSkillEntityTypes.DISPENSER, DispenserRenderer::new);
      EntityRendererRegistry.register(MysticismSkillEntityTypes.BLACK_HOLE, BlackHoleRenderer::new);
      EntityRendererRegistry.register(MysticismProjectileEntityTypes.SENTRY_BULLET, SentryBulletRenderer::new);
      EntityRendererRegistry.register(MysticismProjectileEntityTypes.SENTRY_MISSILE, SentryMissileProjectileRenderer::new);
      EntityRendererRegistry.register(MysticismProjectileEntityTypes.SHADOW_BALL, ShadowBallProjectileRenderer::new);
      EntityRendererRegistry.register(MysticismProjectileEntityTypes.TAILED_BEAST_BOMB, TailedBeastBombProjectileRenderer::new);
      EntityRendererRegistry.register(MysticismProjectileEntityTypes.DARKNESS_SPIKE, MagicSpikeRenderer::new);
      EntityRendererRegistry.register(MysticismSkillEntityTypes.ENKIDU, EnkiduEntityRenderer::new);
      EntityRendererRegistry.register(MysticismSkillEntityTypes.GATE, GateEntityRenderer::new);
   }

   public static void registerRenderer() {
      EntityModelLayerRegistry.register(MagicBoltRenderer.MAGIC_BOLT, MagicBoltRenderer::createBodyLayer);
      EntityModelLayerRegistry.register(ModModelLayers.SPINNING_CUBE, BlackHoleModel::createBodyLayer);
   }
}
