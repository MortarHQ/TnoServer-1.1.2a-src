package com.cerbon.bosses_of_mass_destruction.entity;

import com.cerbon.bosses_of_mass_destruction.animation.PauseAnimationTimer;
import com.cerbon.bosses_of_mass_destruction.client.render.BillboardRenderer;
import com.cerbon.bosses_of_mass_destruction.client.render.CompositeRenderer;
import com.cerbon.bosses_of_mass_destruction.client.render.ConditionalRenderer;
import com.cerbon.bosses_of_mass_destruction.client.render.FrameLimiter;
import com.cerbon.bosses_of_mass_destruction.client.render.FullRenderLight;
import com.cerbon.bosses_of_mass_destruction.client.render.LerpedPosRenderer;
import com.cerbon.bosses_of_mass_destruction.client.render.PetalBladeParticleRenderer;
import com.cerbon.bosses_of_mass_destruction.client.render.PetalBladeRenderer;
import com.cerbon.bosses_of_mass_destruction.client.render.SimpleEntityRenderer;
import com.cerbon.bosses_of_mass_destruction.config.BMDConfig;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.GauntletCodeAnimations;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.GauntletEnergyRenderer;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.GauntletEntity;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.GauntletLaserRenderer;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.GauntletOverlay;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.GauntletTextureProvider;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.LaserParticleRenderer;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.BoundedLighting;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.EternalNightRenderer;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.LichBoneLight;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.LichCodeAnimations;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.LichEntity;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.LichKillCounter;
import com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith.ObsidilithArmorRenderer;
import com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith.ObsidilithBoneLight;
import com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith.ObsidilithEntity;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.NoRedOnDeathOverlay;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.SporeBallOverlay;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.SporeBallSizeRenderer;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.SporeCodeAnimations;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.VoidBlossomBoneLight;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.VoidBlossomCodeAnimations;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.VoidBlossomEntity;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.VoidBlossomSpikeRenderer;
import com.cerbon.bosses_of_mass_destruction.entity.util.SimpleLivingGeoRenderer;
import com.cerbon.bosses_of_mass_destruction.item.custom.ChargedEnderPearlEntity;
import com.cerbon.bosses_of_mass_destruction.item.custom.SoulStarEntity;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ParticleFactories;
import com.cerbon.bosses_of_mass_destruction.projectile.MagicMissileProjectile;
import com.cerbon.bosses_of_mass_destruction.projectile.PetalBladeProjectile;
import com.cerbon.bosses_of_mass_destruction.projectile.SporeBallProjectile;
import com.cerbon.bosses_of_mass_destruction.projectile.comet.CometCodeAnimations;
import com.cerbon.bosses_of_mass_destruction.projectile.comet.CometProjectile;
import com.cerbon.cerbons_api.api.general.data.WeakHashPredicate;
import com.cerbon.cerbons_api.api.general.particle.ClientParticleBuilder;
import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistries;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistry;
import com.cerbon.cerbons_api.api.static_utilities.RandomUtils;
import com.cerbon.cerbons_api.api.static_utilities.Vec3Colors;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import com.mojang.blaze3d.Blaze3D;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BMDEntities {
   public static final BMDConfig mobConfig = (BMDConfig)AutoConfig.getConfigHolder(BMDConfig.class).getConfig();
   public static final ResourcefulRegistry<EntityType<?>> ENTITY_TYPES = ResourcefulRegistries.create(
      BuiltInRegistries.ENTITY_TYPE, "bosses_of_mass_destruction"
   );
   public static final RegistryEntry<EntityType<LichEntity>> LICH = ENTITY_TYPES.register(
      "lich",
      () -> Builder.of((entityType, level) -> new LichEntity(entityType, level, mobConfig.lichConfig), MobCategory.MONSTER)
         .sized(1.8F, 3.0F)
         .updateInterval(1)
         .build(ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "lich").toString())
   );
   public static final RegistryEntry<EntityType<MagicMissileProjectile>> MAGIC_MISSILE = ENTITY_TYPES.register(
      "blue_fireball",
      () -> Builder.of(MagicMissileProjectile::new, MobCategory.MISC)
         .sized(0.25F, 0.25F)
         .build(ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "blue_fireball").toString())
   );
   public static final RegistryEntry<EntityType<CometProjectile>> COMET = ENTITY_TYPES.register(
      "comet",
      () -> Builder.of(CometProjectile::new, MobCategory.MISC)
         .sized(0.25F, 0.25F)
         .build(ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "comet").toString())
   );
   public static final RegistryEntry<EntityType<SoulStarEntity>> SOUL_STAR = ENTITY_TYPES.register(
      "soul_star",
      () -> Builder.of(SoulStarEntity::new, MobCategory.MISC)
         .sized(0.25F, 0.25F)
         .build(ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "soul_star").toString())
   );
   public static final RegistryEntry<EntityType<ChargedEnderPearlEntity>> CHARGED_ENDER_PEARL = ENTITY_TYPES.register(
      "charged_ender_pearl",
      () -> Builder.of(ChargedEnderPearlEntity::new, MobCategory.MISC)
         .sized(0.25F, 0.25F)
         .build(ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "charged_ender_pearl").toString())
   );
   public static final RegistryEntry<EntityType<ObsidilithEntity>> OBSIDILITH = ENTITY_TYPES.register(
      "obsidilith",
      () -> Builder.of((entityType, level) -> new ObsidilithEntity(entityType, level, mobConfig.obsidilithConfig), MobCategory.MONSTER)
         .sized(2.0F, 4.4F)
         .fireImmune()
         .build(ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "obsidilith").toString())
   );
   public static final RegistryEntry<EntityType<GauntletEntity>> GAUNTLET = ENTITY_TYPES.register(
      "gauntlet",
      () -> Builder.of((entityType, level) -> new GauntletEntity(entityType, level, mobConfig.gauntletConfig), MobCategory.MONSTER)
         .sized(5.0F, 4.0F)
         .fireImmune()
         .build(ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "gauntlet").toString())
   );
   public static final RegistryEntry<EntityType<VoidBlossomEntity>> VOID_BLOSSOM = ENTITY_TYPES.register(
      "void_blossom",
      () -> Builder.of((entityType, level) -> new VoidBlossomEntity(entityType, level, mobConfig.voidBlossomConfig), MobCategory.MONSTER)
         .sized(8.0F, 10.0F)
         .fireImmune()
         .clientTrackingRange(3)
         .build(ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "void_blossom").toString())
   );
   public static final RegistryEntry<EntityType<SporeBallProjectile>> SPORE_BALL = ENTITY_TYPES.register(
      "spore_ball",
      () -> Builder.of(SporeBallProjectile::new, MobCategory.MISC)
         .sized(0.25F, 0.25F)
         .build(ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "spore_ball").toString())
   );
   public static final RegistryEntry<EntityType<PetalBladeProjectile>> PETAL_BLADE = ENTITY_TYPES.register(
      "petal_blade",
      () -> Builder.of(PetalBladeProjectile::new, MobCategory.MISC)
         .sized(0.25F, 0.25F)
         .build(ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "petal_blade").toString())
   );
   public static final LichKillCounter killCounter = new LichKillCounter(mobConfig.lichConfig.summonMechanic);

   @OnlyIn(Dist.CLIENT)
   public static void initClient() {
      PauseAnimationTimer pauseSecondTimer = new PauseAnimationTimer(Blaze3D::getTime, () -> Minecraft.getInstance().isPaused());
      EntityRenderers.register(
         (EntityType)LICH.get(),
         context -> {
            ResourceLocation texture = ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "textures/entity/lich.png");
            return new SimpleLivingGeoRenderer(
               context,
               new GeoModel(
                  lichEntity -> ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "geo/lich.geo.json"),
                  entity -> texture,
                  ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "animations/lich.animation.json"),
                  new LichCodeAnimations(),
                  RenderType::entityCutoutNoCull
               ),
               new BoundedLighting(5),
               new LichBoneLight(),
               new EternalNightRenderer(),
               null,
               null,
               true
            );
         }
      );
      EntityRenderers.register(
         (EntityType)OBSIDILITH.get(),
         context -> {
            ObsidilithBoneLight runeColorHandler = new ObsidilithBoneLight();
            GeoModel<ObsidilithEntity> modelProvider = new GeoModel(
               entity -> ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "geo/obsidilith.geo.json"),
               entity -> ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "textures/entity/obsidilith.png"),
               ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "animations/obsidilith.animation.json"),
               (animatable, data, geoModel) -> {},
               RenderType::entityCutout
            );
            ObsidilithArmorRenderer armorRenderer = new ObsidilithArmorRenderer(modelProvider, context);
            return new SimpleLivingGeoRenderer(
               context, modelProvider, null, runeColorHandler, new CompositeRenderer(armorRenderer, runeColorHandler), armorRenderer, null, false
            );
         }
      );
      EntityRenderers.register(
         (EntityType)COMET.get(),
         context -> new SimpleLivingGeoRenderer(
            context,
            new GeoModel(
               geoAnimatable -> ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "geo/comet.geo.json"),
               entity -> ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "textures/entity/comet.png"),
               ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "animations/comet.animation.json"),
               new CometCodeAnimations(),
               RenderType::entityCutout
            ),
            new FullRenderLight(),
            null,
            new ConditionalRenderer(
               new WeakHashPredicate(() -> new FrameLimiter(60.0F, pauseSecondTimer)::canDoFrame),
               new LerpedPosRenderer(vec3 -> ParticleFactories.cometTrail().build(vec3.add(RandomUtils.randVec().scale(0.5)), Vec3.ZERO))
            ),
            null,
            null,
            true
         )
      );
      EntityRenderers.register((EntityType)SOUL_STAR.get(), context -> new ThrownItemRenderer(context, 1.0F, true));
      EntityRenderers.register((EntityType)CHARGED_ENDER_PEARL.get(), ThrownItemRenderer::new);
      ResourceLocation missileTexture = ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "textures/entity/blue_magic_missile.png");
      RenderType magicMissileRenderType = RenderType.entityCutoutNoCull(missileTexture);
      EntityRenderers.register(
         (EntityType)MAGIC_MISSILE.get(),
         context -> new SimpleEntityRenderer(
            context,
            new CompositeRenderer(
               new BillboardRenderer(context.getEntityRenderDispatcher(), magicMissileRenderType, f -> 0.5F),
               new ConditionalRenderer(
                  new WeakHashPredicate(() -> new FrameLimiter(20.0F, pauseSecondTimer)::canDoFrame),
                  new LerpedPosRenderer(vec3 -> ParticleFactories.soulFlame().build(vec3.add(RandomUtils.randVec().scale(0.25)), Vec3.ZERO))
               )
            ),
            entity -> missileTexture,
            new FullRenderLight()
         )
      );
      EntityRenderers.register(
         (EntityType)GAUNTLET.get(),
         context -> {
            GeoModel<GauntletEntity> modelProvider = new GeoModel(
               entity -> ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "geo/gauntlet.geo.json"),
               new GauntletTextureProvider(),
               ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "animations/gauntlet.animation.json"),
               new GauntletCodeAnimations(),
               RenderType::entityCutout
            );
            GauntletEnergyRenderer energyRenderer = new GauntletEnergyRenderer(modelProvider, context);
            GauntletOverlay overlayOverride = new GauntletOverlay();
            return new SimpleLivingGeoRenderer(
               context,
               modelProvider,
               null,
               null,
               new CompositeRenderer(
                  new GauntletLaserRenderer(),
                  new ConditionalRenderer(new WeakHashPredicate(() -> new FrameLimiter(20.0F, pauseSecondTimer)::canDoFrame), new LaserParticleRenderer()),
                  energyRenderer,
                  overlayOverride
               ),
               energyRenderer,
               overlayOverride,
               false
            );
         }
      );
      EntityRenderers.register(
         (EntityType)VOID_BLOSSOM.get(),
         context -> {
            ResourceLocation texture = ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "textures/entity/void_blossom.png");
            GeoModel<VoidBlossomEntity> modelProvider = new GeoModel(
               entity -> ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "geo/void_blossom.geo.json"),
               entity -> texture,
               ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "animations/void_blossom.animation.json"),
               new VoidBlossomCodeAnimations(),
               RenderType::entityCutout
            );
            VoidBlossomBoneLight boneLight = new VoidBlossomBoneLight();
            NoRedOnDeathOverlay overlay = new NoRedOnDeathOverlay();
            return new SimpleLivingGeoRenderer(
               context, modelProvider, null, boneLight, new CompositeRenderer(new VoidBlossomSpikeRenderer(), boneLight, overlay), null, overlay, false
            );
         }
      );
      EntityRenderers.register(
         (EntityType)SPORE_BALL.get(),
         context -> {
            SporeBallOverlay explosionFlasher = new SporeBallOverlay();
            return new SimpleLivingGeoRenderer(
               context,
               new GeoModel(
                  geoAnimatable -> ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "geo/comet.geo.json"),
                  entity -> ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "textures/entity/spore.png"),
                  ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "animations/comet.animation.json"),
                  new SporeCodeAnimations(),
                  RenderType::entityCutout
               ),
               new FullRenderLight(),
               null,
               new CompositeRenderer(
                  new ConditionalRenderer(
                     new WeakHashPredicate(() -> new FrameLimiter(60.0F, pauseSecondTimer)::canDoFrame),
                     new LerpedPosRenderer(
                        vec3 -> {
                           ClientParticleBuilder projectileParticles = new ClientParticleBuilder((ParticleOptions)BMDParticles.OBSIDILITH_BURST.get())
                              .color(Vec3Colors.GREEN)
                              .colorVariation(0.4)
                              .scale(0.5F)
                              .brightness(15728880);
                           projectileParticles.build(vec3.add(RandomUtils.randVec().scale(0.25)), VecUtils.yAxis.scale(0.1));
                        }
                     )
                  ),
                  explosionFlasher,
                  new SporeBallSizeRenderer()
               ),
               null,
               explosionFlasher,
               true
            );
         }
      );
      ResourceLocation petalTexture = ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "textures/entity/petal_blade.png");
      RenderType petalBladeRenderType = RenderType.entityCutoutNoCull(petalTexture);
      EntityRenderers.register(
         (EntityType)PETAL_BLADE.get(),
         context -> new SimpleEntityRenderer(
            context,
            new CompositeRenderer(
               new PetalBladeRenderer(context.getEntityRenderDispatcher(), petalBladeRenderType),
               new ConditionalRenderer(new WeakHashPredicate(() -> new FrameLimiter(30.0F, pauseSecondTimer)::canDoFrame), new PetalBladeParticleRenderer())
            ),
            entity -> petalTexture,
            new FullRenderLight()
         )
      );
   }

   public static void register() {
      ENTITY_TYPES.register();
   }
}
