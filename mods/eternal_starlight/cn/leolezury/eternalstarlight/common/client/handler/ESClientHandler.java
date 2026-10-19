package cn.leolezury.eternalstarlight.common.client.handler;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.ESPortalBlock;
import cn.leolezury.eternalstarlight.common.block.EquipableCarvedLunarisCactusFruitBlock;
import cn.leolezury.eternalstarlight.common.client.resource.BookLoader;
import cn.leolezury.eternalstarlight.common.client.shader.ESShaders;
import cn.leolezury.eternalstarlight.common.client.sound.BossMusicSoundInstance;
import cn.leolezury.eternalstarlight.common.client.visual.DelayedMultiBufferSource;
import cn.leolezury.eternalstarlight.common.client.visual.ScreenShake;
import cn.leolezury.eternalstarlight.common.client.visual.WorldVisualEffect;
import cn.leolezury.eternalstarlight.common.client.weather.ClientWeatherState;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolem;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.LunarMonstrosity;
import cn.leolezury.eternalstarlight.common.entity.projectile.SoulitSpectator;
import cn.leolezury.eternalstarlight.common.item.combat.DualWieldingSwordItem;
import cn.leolezury.eternalstarlight.common.network.SimpleActionPacket;
import cn.leolezury.eternalstarlight.common.network.UpdateBookProgressionPacket;
import cn.leolezury.eternalstarlight.common.platform.ESClientPlatform;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESAttributes;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.registry.ESFluids;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import cn.leolezury.eternalstarlight.common.registry.ESWeathers;
import cn.leolezury.eternalstarlight.common.spell.SpellCastData;
import cn.leolezury.eternalstarlight.common.util.ESBlockUtil;
import cn.leolezury.eternalstarlight.common.util.ESGuiUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.FogRenderer.FogMode;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.BossEvent;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class ESClientHandler {
   public static BookLoader books;
   public static final Set<Mob> BOSSES = Collections.newSetFromMap(new WeakHashMap<>());
   public static final List<WorldVisualEffect> VISUAL_EFFECTS = new ArrayList<>();
   public static final List<ScreenShake> SCREEN_SHAKES = new ArrayList<>();
   private static final ResourceLocation[] BAR_BACKGROUND_SPRITES = new ResourceLocation[]{
      ResourceLocation.withDefaultNamespace("boss_bar/pink_background"),
      ResourceLocation.withDefaultNamespace("boss_bar/blue_background"),
      ResourceLocation.withDefaultNamespace("boss_bar/red_background"),
      ResourceLocation.withDefaultNamespace("boss_bar/green_background"),
      ResourceLocation.withDefaultNamespace("boss_bar/yellow_background"),
      ResourceLocation.withDefaultNamespace("boss_bar/purple_background"),
      ResourceLocation.withDefaultNamespace("boss_bar/white_background")
   };
   private static final ResourceLocation[] BAR_PROGRESS_SPRITES = new ResourceLocation[]{
      ResourceLocation.withDefaultNamespace("boss_bar/pink_progress"),
      ResourceLocation.withDefaultNamespace("boss_bar/blue_progress"),
      ResourceLocation.withDefaultNamespace("boss_bar/red_progress"),
      ResourceLocation.withDefaultNamespace("boss_bar/green_progress"),
      ResourceLocation.withDefaultNamespace("boss_bar/yellow_progress"),
      ResourceLocation.withDefaultNamespace("boss_bar/purple_progress"),
      ResourceLocation.withDefaultNamespace("boss_bar/white_progress")
   };
   private static final ResourceLocation[] OVERLAY_BACKGROUND_SPRITES = new ResourceLocation[]{
      ResourceLocation.withDefaultNamespace("boss_bar/notched_6_background"),
      ResourceLocation.withDefaultNamespace("boss_bar/notched_10_background"),
      ResourceLocation.withDefaultNamespace("boss_bar/notched_12_background"),
      ResourceLocation.withDefaultNamespace("boss_bar/notched_20_background")
   };
   private static final ResourceLocation[] OVERLAY_PROGRESS_SPRITES = new ResourceLocation[]{
      ResourceLocation.withDefaultNamespace("boss_bar/notched_6_progress"),
      ResourceLocation.withDefaultNamespace("boss_bar/notched_10_progress"),
      ResourceLocation.withDefaultNamespace("boss_bar/notched_12_progress"),
      ResourceLocation.withDefaultNamespace("boss_bar/notched_20_progress")
   };
   private static final ResourceLocation ETHER_EROSION_OVERLAY = EternalStarlight.id("textures/misc/ether_erosion.png");
   private static final ResourceLocation ETHER_ARMOR_EMPTY = EternalStarlight.id("textures/gui/hud/ether_armor_empty.png");
   private static final ResourceLocation ETHER_ARMOR_HALF = EternalStarlight.id("textures/gui/hud/ether_armor_half.png");
   private static final ResourceLocation ETHER_ARMOR_FULL = EternalStarlight.id("textures/gui/hud/ether_armor_full.png");
   private static final ResourceLocation ORB_OF_PROPHECY_USE = EternalStarlight.id("textures/misc/orb_of_prophecy_use.png");
   private static final ResourceLocation CROSSHAIR_ATTACK_INDICATOR_FULL_SPRITE = ResourceLocation.withDefaultNamespace("hud/crosshair_attack_indicator_full");
   private static final ResourceLocation CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace(
      "hud/crosshair_attack_indicator_background"
   );
   private static final ResourceLocation CROSSHAIR_ATTACK_INDICATOR_PROGRESS_SPRITE = ResourceLocation.withDefaultNamespace(
      "hud/crosshair_attack_indicator_progress"
   );
   private static final ResourceLocation HOTBAR_ATTACK_INDICATOR_BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace(
      "hud/hotbar_attack_indicator_background"
   );
   private static final ResourceLocation HOTBAR_ATTACK_INDICATOR_PROGRESS_SPRITE = ResourceLocation.withDefaultNamespace("hud/hotbar_attack_indicator_progress");
   private static final ResourceLocation LUNARIS_CACTUS_BLUR_LOCATION = EternalStarlight.id("textures/misc/lunaris_cactus_blur.png");
   public static final ResourceLocation WIP_LOCATION = EternalStarlight.id("textures/gui/wip.png");
   private static final Map<ResourceKey<Crest>, ESClientHandler.GuiCrest> GUI_CRESTS = new HashMap<>();
   private static final List<ESClientHandler.DreamCatcherText> DREAM_CATCHER_TEXTS = new ArrayList<>();
   private static final Set<EntityType<?>> CROSSHAIR_PICKED_ENTITIES = new HashSet<>();
   public static int clientTickCount = 0;
   public static BossMusicSoundInstance bossMusicInstance = null;
   private static float oldPortalTicks;
   private static float portalTicks;
   private static float oldAuroraIntensity;
   private static float auroraIntensity;
   public static int resetCameraIn;
   public static float fogStartDecrement;
   public static float fogEndDecrement;
   public static float oldAbyssalFogModifier = 1.0F;
   public static float abyssalFogModifier = 1.0F;
   public static boolean oldTearyEffect;
   public static boolean tearyEffect;
   public static int oldSeedsLauncherAnimTicks;
   public static int seedsLauncherAnimTicks;
   public static final BufferSource DELAYED_BUFFER_SOURCE = new DelayedMultiBufferSource(new ByteBufferBuilder(1536));
   public static final BufferSource AFTER_LEVEL_BUFFER_SOURCE = new DelayedMultiBufferSource(new ByteBufferBuilder(1536));
   private static Matrix4f modelViewMatrix = new Matrix4f();
   public static boolean isHalloween;

   public static void onClientTick() {
      if (Minecraft.getInstance().level == null) {
         clearWorldSpecificVars();
      }

      LocalPlayer player = Minecraft.getInstance().player;
      ClientWeatherState.tickRainLevel();
      if (Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()) {
         if (!Minecraft.getInstance().isPaused()) {
            clientTickCount++;
            if (clientTickCount % 1000 == 5) {
               Calendar calendar = Calendar.getInstance();
               isHalloween = calendar.get(2) == 10 && calendar.get(5) == 1;
            }

            oldSeedsLauncherAnimTicks = seedsLauncherAnimTicks;
            if (seedsLauncherAnimTicks > 0) {
               seedsLauncherAnimTicks--;
            }
         }

         for (WorldVisualEffect effect : VISUAL_EFFECTS) {
            if (!effect.shouldRemove() && !Minecraft.getInstance().isPaused()) {
               effect.worldTick();
            }
         }

         VISUAL_EFFECTS.removeIf(WorldVisualEffect::shouldRemove);

         for (ScreenShake effect : SCREEN_SHAKES) {
            if (!effect.shouldRemove() && !Minecraft.getInstance().isPaused()) {
               effect.tick();
            }
         }

         SCREEN_SHAKES.removeIf(ScreenShake::shouldRemove);
         if (!Minecraft.getInstance().isPaused()
            && ClientWeatherState.weather != null
            && Minecraft.getInstance().level.dimension().location().equals(ESDimensions.STARLIGHT_KEY.location())) {
            ClientWeatherState.weather.clientTick();
         }

         if (player != null) {
            if (player.tickCount % 20 == 0) {
               if (bossMusicInstance == null) {
                  List<ESBoss> bosses = Minecraft.getInstance().level.getEntitiesOfClass(ESBoss.class, player.getBoundingBox().inflate(50.0));
                  bosses.sort(Comparator.comparingDouble(b -> b.distanceToSqr(player)));
                  bosses = bosses.stream().filter(ESBoss::shouldPlayBossMusic).filter(b -> b.tickCount > 20 && player.hasLineOfSight(b)).toList();
                  if (!bosses.isEmpty()) {
                     ESBoss boss = bosses.getFirst();
                     bossMusicInstance = new BossMusicSoundInstance(boss.getBossMusic(), boss);
                  }
               } else if (bossMusicInstance.shouldStopMusic(player)) {
                  Minecraft.getInstance().getSoundManager().stop(bossMusicInstance);
                  bossMusicInstance.stopMusic();
                  bossMusicInstance = null;
               }

               if (bossMusicInstance != null && !Minecraft.getInstance().getSoundManager().isActive(bossMusicInstance)) {
                  Minecraft.getInstance().getSoundManager().play(bossMusicInstance);
               }
            }
         } else if (bossMusicInstance != null) {
            Minecraft.getInstance().getSoundManager().stop(bossMusicInstance);
            bossMusicInstance.stopMusic();
            bossMusicInstance = null;
         }
      }

      if (Minecraft.getInstance().level != null) {
         for (ESClientSetupHandler.WorldVisualEffectSpawnFunction function : ESClientSetupHandler.VISUAL_EFFECT_SPAWN_FUNCTIONS) {
            function.clientTick(Minecraft.getInstance().level, VISUAL_EFFECTS);
         }
      }

      if (player != null) {
         oldPortalTicks = portalTicks;
         if (player.portalProcess != null
            && player.portalProcess.isSamePortal((Portal)ESBlocks.STARLIGHT_PORTAL.get())
            && player.portalProcess.isInsidePortalThisTick()) {
            portalTicks++;
            player.portalProcess.setAsInsidePortalThisTick(false);
         } else {
            portalTicks -= 2.0F;
         }

         portalTicks = Mth.clamp(portalTicks, 0.0F, 80.0F);
         Entity entity = Minecraft.getInstance().crosshairPickEntity;
         if (entity != null && entity.getType().is(ESTags.EntityTypes.AFFECTS_PROGRESSION)) {
            CROSSHAIR_PICKED_ENTITIES.add(entity.getType());
         }

         if (clientTickCount % 60 == 0) {
            ESClientPlatform.INSTANCE
               .sendToServer(
                  new UpdateBookProgressionPacket(
                     CROSSHAIR_PICKED_ENTITIES.stream()
                        .map(type -> BuiltInRegistries.ENTITY_TYPE.getKey(type).withPrefix("entity_seen_"))
                        .collect(Collectors.toSet())
                  )
               );
            CROSSHAIR_PICKED_ENTITIES.clear();
         }

         oldAuroraIntensity = auroraIntensity;
         if (player.level().dimension() == ESDimensions.STARLIGHT_KEY) {
            if (ClientWeatherState.weather == ESWeathers.AURORA.get()) {
               auroraIntensity += 0.05F;
            } else {
               auroraIntensity -= 0.05F;
            }
         } else {
            oldAuroraIntensity = 0.0F;
            auroraIntensity = 0.0F;
         }

         auroraIntensity = Mth.clamp(auroraIntensity, 0.0F, 1.0F);
         List<ResourceKey<Crest>> crestsToRemove = new ArrayList<>();

         for (ResourceKey<Crest> key : GUI_CRESTS.keySet()) {
            ESClientHandler.GuiCrest crest = GUI_CRESTS.get(key);
            if (Math.abs(crest.angle + 135.0F) < 0.1 && !crest.shouldShow) {
               crestsToRemove.add(key);
            }
         }

         for (ResourceKey<Crest> key : crestsToRemove) {
            GUI_CRESTS.remove(key);
         }

         for (Entry<ResourceKey<Crest>, ESClientHandler.GuiCrest> entry : GUI_CRESTS.entrySet()) {
            entry.getValue().tick();
         }

         ItemStack mainHand = player.getMainHandItem();
         ItemStack offHand = player.getOffhandItem();
         Holder<Crest> component = null;
         if (mainHand.has(ESDataComponents.CURRENT_CREST.get())) {
            component = (Holder<Crest>)mainHand.get(ESDataComponents.CURRENT_CREST.get());
         } else if (offHand.has(ESDataComponents.CURRENT_CREST.get())) {
            component = (Holder<Crest>)offHand.get(ESDataComponents.CURRENT_CREST.get());
         }

         for (Entry<ResourceKey<Crest>, ESClientHandler.GuiCrest> entry : GUI_CRESTS.entrySet()) {
            entry.getValue().shouldShow = false;
         }

         if (component != null && component.isBound()) {
            Registry<Crest> registry = player.registryAccess().registryOrThrow(ESRegistries.CREST);
            Optional<ResourceKey<Crest>> key = registry.getResourceKey((Crest)component.value());
            if (key.isPresent()) {
               if (!GUI_CRESTS.containsKey(key.get())) {
                  GUI_CRESTS.put(key.get(), new ESClientHandler.GuiCrest());
               }

               GUI_CRESTS.get(key.get()).shouldShow = true;
            }
         }

         if (ESClientSetupHandler.KEY_MAPPINGS.get(EternalStarlight.id("switch_crest")).consumeClick()) {
            ESClientPlatform.INSTANCE.sendToServer(new SimpleActionPacket("switch_crest"));
         }

         if (resetCameraIn > 0) {
            resetCameraIn--;
            Minecraft.getInstance().options.hideGui = true;
            if (resetCameraIn <= 0) {
               Minecraft.getInstance().setCameraEntity(player);
               Minecraft.getInstance().options.hideGui = false;
               resetCameraIn = 0;
            }
         } else if (Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator) {
            Minecraft.getInstance().setCameraEntity(player);
            Minecraft.getInstance().options.hideGui = false;
         }

         Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
         fogStartDecrement -= 0.5F;
         fogEndDecrement -= 0.5F;
         Holder<Biome> biomeHolder = player.level().getBiome(player.blockPosition());
         if (camera.getFluidInCamera() == FogType.NONE
            && player.level().getBlockState(camera.getBlockPosition()).getFluidState().isEmpty()
            && biomeHolder.is(ESTags.Biomes.PERMAFROST)) {
            fogStartDecrement += 1.25F;
            fogEndDecrement++;
            fogEndDecrement = Mth.clamp(fogEndDecrement, 0.0F, 80.0F);
         }

         fogStartDecrement = Mth.clamp(fogStartDecrement, 0.0F, 100.0F);
         fogEndDecrement = Mth.clamp(fogEndDecrement, 0.0F, 100.0F);
         oldAbyssalFogModifier = abyssalFogModifier;
         if (biomeHolder.is(ESBiomes.THE_ABYSS)) {
            abyssalFogModifier -= 0.02F;
         } else {
            abyssalFogModifier += 0.02F;
         }

         abyssalFogModifier = Mth.clamp(abyssalFogModifier, 0.0F, 1.0F);
         oldTearyEffect = tearyEffect;
         tearyEffect = player.hasEffect(ESMobEffects.TEARY.asHolder());
         if (player.hasEffect(ESMobEffects.DREAM_CATCHER.asHolder())) {
            for (ESClientHandler.DreamCatcherText text : DREAM_CATCHER_TEXTS) {
               text.updatePosition();
            }

            DREAM_CATCHER_TEXTS.removeIf(
               textx -> textx.getX() - Minecraft.getInstance().font.width(textx.getText()) / 2 > Minecraft.getInstance().getWindow().getGuiScaledWidth()
                  || textx.getY() - 9 / 2 > Minecraft.getInstance().getWindow().getGuiScaledHeight()
            );
            if (player.getRandom().nextInt(40) == 0 && DREAM_CATCHER_TEXTS.size() < 20) {
               Component dreamCatcherText = Component.translatable("message.eternal_starlight.dream_catcher_" + player.getRandom().nextInt(7));
               DREAM_CATCHER_TEXTS.add(
                  new ESClientHandler.DreamCatcherText(
                     dreamCatcherText,
                     (int)(player.getRandom().nextFloat() * 5.0F + 1.0F),
                     -Minecraft.getInstance().font.width(dreamCatcherText) / 2,
                     player.getRandom().nextInt(Minecraft.getInstance().getWindow().getGuiScaledHeight())
                  )
               );
            }
         } else {
            DREAM_CATCHER_TEXTS.clear();
         }
      } else {
         DREAM_CATCHER_TEXTS.clear();
         tearyEffect = false;
      }

      if (oldTearyEffect != tearyEffect) {
         Minecraft.getInstance().gameRenderer.checkEntityPostEffect(Minecraft.getInstance().cameraEntity);
      }
   }

   private static void clearWorldSpecificVars() {
      BOSSES.clear();
      VISUAL_EFFECTS.clear();
      SCREEN_SHAKES.clear();
      GUI_CRESTS.clear();
      DREAM_CATCHER_TEXTS.clear();
      if (bossMusicInstance != null) {
         Minecraft.getInstance().getSoundManager().stop(bossMusicInstance);
         bossMusicInstance.stopMusic();
         bossMusicInstance = null;
      }

      oldPortalTicks = 0.0F;
      portalTicks = 0.0F;
      oldAuroraIntensity = 0.0F;
      auroraIntensity = 0.0F;
      resetCameraIn = 0;
      fogStartDecrement = 0.0F;
      fogEndDecrement = 0.0F;
      oldAbyssalFogModifier = 1.0F;
      abyssalFogModifier = 1.0F;
      oldTearyEffect = false;
      tearyEffect = false;
      oldSeedsLauncherAnimTicks = 0;
      seedsLauncherAnimTicks = 0;
   }

   public static float getScreenShakeYawOffset() {
      if (!ESConfig.INSTANCE.enableScreenShake) {
         return 0.0F;
      }

      float sum = 0.0F;

      for (ScreenShake screenShake : SCREEN_SHAKES) {
         sum += screenShake.getYawOffset();
      }

      return sum;
   }

   public static float getScreenShakePitchOffset() {
      if (!ESConfig.INSTANCE.enableScreenShake) {
         return 0.0F;
      }

      float sum = 0.0F;

      for (ScreenShake screenShake : SCREEN_SHAKES) {
         sum += screenShake.getPitchOffset();
      }

      return sum;
   }

   public static void onAfterRenderEntities(MultiBufferSource source, PoseStack stack, float partialTicks) {
      Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
      Vec3 cameraPos = camera.getPosition();
      stack.pushPose();
      stack.translate(-cameraPos.x(), -cameraPos.y(), -cameraPos.z());

      for (WorldVisualEffect effect : VISUAL_EFFECTS) {
         if (!effect.shouldRemove()) {
            effect.render(source, stack, partialTicks);
         }
      }

      stack.popPose();
   }

   public static void onAfterRenderParticles() {
      modelViewMatrix = RenderSystem.getModelViewMatrix();
   }

   public static void onAfterRenderWeather(float partialTicks) {
      RenderSystem.enableCull();
      RenderSystem.enableBlend();
      RenderSystem.enableDepthTest();
      RenderSystem.depthMask(false);
      Matrix4f matrix4f = new Matrix4f(RenderSystem.getModelViewMatrix());
      RenderSystem.getModelViewMatrix().set(modelViewMatrix);
      if (Minecraft.useShaderTransparency()) {
         Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
      }

      DELAYED_BUFFER_SOURCE.endBatch();
      RenderSystem.getModelViewMatrix().set(matrix4f);
      if (Minecraft.useShaderTransparency() && Minecraft.getInstance().levelRenderer.getCloudsTarget() != null) {
         Minecraft.getInstance().levelRenderer.getCloudsTarget().bindWrite(false);
      }

      float aurora = Mth.lerp(partialTicks, oldAuroraIntensity, auroraIntensity);
      if (aurora > 0.0F && Minecraft.getInstance().level != null) {
         renderSkyShader(ESShaders.getAurora(), aurora);
      }
   }

   public static void onAfterRenderLevel() {
      Matrix4f matrix4f = new Matrix4f(RenderSystem.getModelViewMatrix());
      RenderSystem.getModelViewMatrix().set(modelViewMatrix);
      AFTER_LEVEL_BUFFER_SOURCE.endBatch();
      RenderSystem.getModelViewMatrix().set(matrix4f);
   }

   private static void renderSkyShader(ShaderInstance shader, float intensity) {
      Tesselator tesselator = Tesselator.getInstance();
      BufferBuilder buffer = tesselator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      float scale = 2048.0F * (Minecraft.getInstance().gameRenderer.getRenderDistance() / 32.0F);
      Vec3 pos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
      float height = (float)(pos.y() + 128.0);
      float cloud = Minecraft.getInstance().level.effects().getCloudHeight() + 64.0F;
      float y = (float)(Math.max(height, cloud) - pos.y());
      buffer.addVertex(-scale, y, scale).setColor(1.0F, 1.0F, 1.0F, 1.0F);
      buffer.addVertex(-scale, y, -scale).setColor(1.0F, 1.0F, 1.0F, 1.0F);
      buffer.addVertex(scale, y, -scale).setColor(1.0F, 1.0F, 1.0F, 1.0F);
      buffer.addVertex(scale, y, scale).setColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      RenderSystem.enableDepthTest();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, intensity);
      ShaderInstance last = RenderSystem.getShader();
      RenderSystem.setShader(() -> shader);
      shader.apply();
      BufferUploader.drawWithShader(buffer.buildOrThrow());
      shader.clear();
      RenderSystem.setShader(() -> last);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableDepthTest();
      RenderSystem.disableBlend();
   }

   public static Vec3 onComputeCameraAngles(Vec3 angles) {
      return angles.add(Mth.clamp(getScreenShakePitchOffset(), -0.5, 0.5), Mth.clamp(getScreenShakeYawOffset(), -0.5, 0.5), 0.0);
   }

   public static OptionalDouble onComputeFovModifier(float original) {
      float modified = original;
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && player.isUsingItem()) {
         ItemStack itemStack = player.getUseItem();
         if (BuiltInRegistries.ITEM.getKey(itemStack.getItem()).getNamespace().equals("eternal_starlight") && itemStack.getItem() instanceof BowItem) {
            float f = player.getTicksUsingItem() / 20.0F;
            f = f > 1.0F ? 1.0F : f * f;
            modified = (float)Mth.lerp((Double)Minecraft.getInstance().options.fovEffectScale().get(), 1.0, modified * (1.0F - f * 0.15F));
         }
      }

      float portal = Mth.lerp(
            Minecraft.getInstance()
               .getTimer()
               .getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()),
            oldPortalTicks,
            portalTicks
         )
         / 80.0F;
      if (portal > 0.0F) {
         modified = (float)Mth.lerp((Double)Minecraft.getInstance().options.fovEffectScale().get(), 1.0, modified * (1.0F - portal));
      }

      return modified == original ? OptionalDouble.empty() : OptionalDouble.of(modified);
   }

   public static void onRenderFog(Camera camera, FogMode fogMode) {
      Minecraft minecraft = Minecraft.getInstance();
      LocalPlayer player = minecraft.player;
      if (player != null) {
         if (ESPlatform.INSTANCE.getLoader() == ESPlatform.Loader.FABRIC) {
            FluidState fluidState = camera.getEntity().level().getFluidState(camera.getBlockPosition());
            if ((fluidState.is((Fluid)ESFluids.ETHER_STILL.get()) || fluidState.is((Fluid)ESFluids.ETHER_FLOWING.get()))
               && camera.getPosition().y < camera.getBlockPosition().getY() + fluidState.getHeight(camera.getEntity().level(), camera.getBlockPosition())) {
               RenderSystem.setShaderFogStart(0.0F);
               RenderSystem.setShaderFogEnd(3.0F);
               RenderSystem.setShaderFogColor(0.9098039F, 1.0F, 0.87058824F);
               RenderSystem.setShaderFogShape(FogShape.SPHERE);
            }
         }

         if (player.level().dimension() == ESDimensions.STARLIGHT_KEY
            && camera.getFluidInCamera() == FogType.NONE
            && player.level().getBlockState(camera.getBlockPosition()).getFluidState().isEmpty()
            && fogMode == FogMode.FOG_TERRAIN) {
            RenderSystem.setShaderFogStart(RenderSystem.getShaderFogStart() - Mth.clamp(fogStartDecrement, 0.0F, RenderSystem.getShaderFogStart() + 5.0F));
            RenderSystem.setShaderFogEnd(RenderSystem.getShaderFogEnd() - Mth.clamp(fogEndDecrement, 0.0F, RenderSystem.getShaderFogEnd() - 50.0F));
         }

         AttributeInstance fogVision = player.getAttribute(ESAttributes.FOG_VISION.asHolder());
         if (fogVision != null && fogMode == FogMode.FOG_TERRAIN) {
            RenderSystem.setShaderFogStart(RenderSystem.getShaderFogStart() + (float)fogVision.getValue());
            RenderSystem.setShaderFogEnd(RenderSystem.getShaderFogEnd() + (float)fogVision.getValue());
         }
      }
   }

   public static boolean onRenderBlockOverlay(Player player, BlockState state) {
      return !player.hasEffect(ESMobEffects.OBLIVION.asHolder()) || state.is(ESTags.Blocks.UNAFFECTED_BY_OBLIVION);
   }

   public static boolean renderBossBar(GuiGraphics guiGraphics, LerpingBossEvent bossEvent, int x, int y) {
      Mob boss = null;
      if (BOSSES.isEmpty()) {
         return false;
      }

      for (Mob mob : BOSSES) {
         if (mob.getUUID().equals(bossEvent.getId())) {
            boss = mob;
            break;
         }
      }

      ResourceLocation barLocation;
      switch (boss) {
         case TheGatekeeper ignored:
            barLocation = ResourceLocation.fromNamespaceAndPath("eternal_starlight", "textures/gui/bars/the_gatekeeper.png");
            break;
         case StarlightGolem ignored:
            barLocation = ResourceLocation.fromNamespaceAndPath("eternal_starlight", "textures/gui/bars/starlight_golem.png");
            break;
         case LunarMonstrosity lunarMonstrosity:
            barLocation = ResourceLocation.fromNamespaceAndPath(
               "eternal_starlight", "textures/gui/bars/lunar_monstrosity" + (lunarMonstrosity.getPhase() > 0 ? "_soul.png" : ".png")
            );
            break;
         case null:
         default:
            return false;
      }

      drawBar(guiGraphics, x, y, bossEvent, barLocation);
      Component component = bossEvent.getName();
      int textWidth = Minecraft.getInstance().font.width(component);
      int textX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - textWidth / 2;
      int textY = y - 9;
      guiGraphics.drawString(Minecraft.getInstance().font, component, textX, textY, 16777215);
      return true;
   }

   public static void drawBar(GuiGraphics guiGraphics, int x, int y, BossEvent event, ResourceLocation barLocation) {
      drawBar(guiGraphics, x, y, event, 182, BAR_BACKGROUND_SPRITES, OVERLAY_BACKGROUND_SPRITES);
      int k = Mth.lerpDiscrete(event.getProgress(), 0, 182);
      if (k > 0) {
         drawBar(guiGraphics, x, y, event, k, BAR_PROGRESS_SPRITES, OVERLAY_PROGRESS_SPRITES);
      }

      guiGraphics.blit(barLocation, x - 1, y - 5, 0.0F, 0.0F, 184, 16, 184, 16);
   }

   private static void drawBar(GuiGraphics guiGraphics, int x, int y, BossEvent bossEvent, int progress, ResourceLocation[] bars, ResourceLocation[] overlays) {
      guiGraphics.blitSprite(bars[bossEvent.getColor().ordinal()], 182, 5, 0, 0, x, y, progress, 5);
      if (bossEvent.getOverlay() != BossBarOverlay.PROGRESS) {
         RenderSystem.enableBlend();
         guiGraphics.blitSprite(overlays[bossEvent.getOverlay().ordinal() - 1], 182, 5, 0, 0, x, y, progress, 5);
         RenderSystem.disableBlend();
      }
   }

   public static int getEtherTint(BlockAndTintGetter getter, BlockPos pos) {
      double progress = getter != null && pos != null
         ? (ESClientSetupHandler.COLOR_NOISE.getValue(pos.getX() / 15.0, pos.getY() / 15.0, pos.getZ() / 15.0) + 1.0) / 2.0
         : (
               Math.sin(
                     (
                           clientTickCount
                              + Minecraft.getInstance()
                                 .getTimer()
                                 .getGameTimeDeltaPartialTick(
                                    Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()
                                 )
                        )
                        / 30.0
                  )
                  + 1.0
            )
            / 2.0;
      return ARGB32.color((int)Mth.lerp(progress, 190.0, 255.0), (int)Mth.lerp(progress, 240.0, 255.0), (int)Mth.lerp(progress, 230.0, 255.0));
   }

   public static void renderTextureOverlay(GuiGraphics guiGraphics, ResourceLocation resourceLocation, float f) {
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      guiGraphics.setColor(1.0F, 1.0F, 1.0F, f);
      guiGraphics.blit(
         resourceLocation, 0, 0, -90, 0.0F, 0.0F, guiGraphics.guiWidth(), guiGraphics.guiHeight(), guiGraphics.guiWidth(), guiGraphics.guiHeight()
      );
      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void renderPortalOverlay(GuiGraphics guiGraphics) {
      float alpha = Mth.lerp(
            Minecraft.getInstance()
               .getTimer()
               .getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()),
            oldPortalTicks,
            portalTicks
         )
         / 120.0F;
      if (!(alpha <= 0.0F)) {
         if (alpha < 1.0F) {
            alpha *= alpha;
            alpha *= alpha;
            alpha = alpha * 0.8F + 0.2F;
         }

         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.enableBlend();
         guiGraphics.setColor(1.0F, 1.0F, 1.0F, alpha);
         TextureAtlasSprite textureatlassprite = Minecraft.getInstance()
            .getBlockRenderer()
            .getBlockModelShaper()
            .getParticleIcon(((ESPortalBlock)ESBlocks.STARLIGHT_PORTAL.get()).defaultBlockState());
         guiGraphics.blit(0, 0, -90, guiGraphics.guiWidth(), guiGraphics.guiHeight(), textureatlassprite);
         RenderSystem.disableBlend();
         RenderSystem.depthMask(true);
         RenderSystem.enableDepthTest();
         guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   public static void renderOffhandAttackIndicator(GuiGraphics guiGraphics) {
      RenderSystem.enableBlend();
      if (Minecraft.getInstance().player != null) {
         ItemStack mainHand = Minecraft.getInstance().player.getMainHandItem();
         ItemStack offhand = Minecraft.getInstance().player.getOffhandItem();
         if (ItemStack.isSameItem(mainHand, offhand) && mainHand.getItem() instanceof DualWieldingSwordItem) {
            float attackStrengthScale = Mth.clamp(
               ESDataAttachments.OFFHAND_ATTACK_STRENGTH_TIMER.getData(Minecraft.getInstance().player).intValue()
                  / Minecraft.getInstance().player.getCurrentItemAttackStrengthDelay(),
               0.0F,
               1.0F
            );
            if (Minecraft.getInstance().options.getCameraType().isFirstPerson()
               && Minecraft.getInstance().options.attackIndicator().get() == AttackIndicatorStatus.CROSSHAIR) {
               RenderSystem.blendFuncSeparate(SourceFactor.ONE_MINUS_DST_COLOR, DestFactor.ONE_MINUS_SRC_COLOR, SourceFactor.ONE, DestFactor.ZERO);
               boolean full = false;
               if (Minecraft.getInstance().crosshairPickEntity != null
                  && Minecraft.getInstance().crosshairPickEntity instanceof LivingEntity
                  && attackStrengthScale >= 1.0F) {
                  full = Minecraft.getInstance().player.getCurrentItemAttackStrengthDelay() > 5.0F;
                  full &= Minecraft.getInstance().crosshairPickEntity.isAlive();
               }

               int x = guiGraphics.guiWidth() / 2 - 8;
               int y = guiGraphics.guiHeight() / 2 - 7 + 24;
               if (full) {
                  guiGraphics.blitSprite(CROSSHAIR_ATTACK_INDICATOR_FULL_SPRITE, x, y, 16, 16);
               } else if (attackStrengthScale < 1.0F) {
                  int progress = (int)(attackStrengthScale * 17.0F);
                  guiGraphics.blitSprite(CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_SPRITE, x, y, 16, 4);
                  guiGraphics.blitSprite(CROSSHAIR_ATTACK_INDICATOR_PROGRESS_SPRITE, 16, 4, 0, 0, x, y, progress, 4);
               }

               RenderSystem.defaultBlendFunc();
            }

            if (Minecraft.getInstance().options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
               HumanoidArm arm = Minecraft.getInstance().player.getMainArm();
               if (attackStrengthScale < 1.0F) {
                  int halfWidth = guiGraphics.guiWidth() / 2;
                  int x = halfWidth + 91 + 6 + 29;
                  if (arm == HumanoidArm.RIGHT) {
                     x = halfWidth - 91 - 22 - 29;
                  }

                  int y = guiGraphics.guiHeight() - 20;
                  int progress = (int)(attackStrengthScale * 19.0F);
                  guiGraphics.blitSprite(HOTBAR_ATTACK_INDICATOR_BACKGROUND_SPRITE, x, y, 18, 18);
                  guiGraphics.blitSprite(HOTBAR_ATTACK_INDICATOR_PROGRESS_SPRITE, 18, 18, 0, 18 - progress, x, y + 18 - progress, 18, progress);
               }
            }
         }
      }

      RenderSystem.disableBlend();
   }

   public static void renderSpellCrosshair(GuiGraphics guiGraphics, int screenWidth, int screenHeight) {
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(SourceFactor.ONE_MINUS_DST_COLOR, DestFactor.ONE_MINUS_SRC_COLOR, SourceFactor.ONE, DestFactor.ZERO);
      Options options = Minecraft.getInstance().options;
      if (options.getCameraType().isFirstPerson()) {
         LocalPlayer player = Minecraft.getInstance().player;
         if (player instanceof SpellCaster && ESDataAttachments.SPELL_CAST_DATA.getData(player).hasSpell()) {
            SpellCastData data = ESDataAttachments.SPELL_CAST_DATA.getData(player);
            if (Minecraft.getInstance().options.attackIndicator().get() == AttackIndicatorStatus.CROSSHAIR) {
               float f = Math.min(1.0F, (float)data.castTicks() / data.spell().spellProperties().preparationTicks());
               if (data.castTicks() > data.spell().spellProperties().preparationTicks()) {
                  f = Math.max(
                     0.0F, 1.0F - (float)(data.castTicks() - data.spell().spellProperties().preparationTicks()) / data.spell().spellProperties().spellTicks()
                  );
               }

               int j = screenHeight / 2 - 7 + 16;
               int k = screenWidth / 2 - 8;
               int l = (int)(f * 17.0F);
               guiGraphics.blitSprite(CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_SPRITE, k, j, 16, 4);
               guiGraphics.blitSprite(CROSSHAIR_ATTACK_INDICATOR_PROGRESS_SPRITE, 16, 4, 0, 0, k, j, l, 4);
            }
         }
      }

      RenderSystem.defaultBlendFunc();
      RenderSystem.disableBlend();
   }

   public static void renderEtherErosion(GuiGraphics guiGraphics) {
      float erosionProgress = ESDataAttachments.IN_ETHER_TICKS.getData(Minecraft.getInstance().player).intValue() / 140.0F;
      if (erosionProgress > 0.0F) {
         renderTextureOverlay(guiGraphics, ETHER_EROSION_OVERLAY, erosionProgress);
      }
   }

   public static void renderEtherArmor(GuiGraphics guiGraphics, int screenWidth, int screenHeight) {
      Minecraft minecraft = Minecraft.getInstance();
      if (ESBlockUtil.isEntityInBlock(minecraft.player, (Block)ESBlocks.ETHER.get())) {
         minecraft.getProfiler().push("armor");
         int initialX = screenWidth / 2 - 91;
         int initialY = screenHeight - 39;
         float maxHealth = (float)Math.max(minecraft.player.getAttributeValue(Attributes.MAX_HEALTH), Mth.ceil(minecraft.player.getHealth()));
         int absorptionAmount = Mth.ceil(minecraft.player.getAbsorptionAmount());
         int q = Mth.ceil((maxHealth + absorptionAmount) / 2.0F / 10.0F);
         int r = Math.max(10 - (q - 2), 3);
         int armorValue = minecraft.player.getArmorValue();
         int y = initialY - (q - 1) * r - 10;

         for (int i = 0; i < 10; i++) {
            if (armorValue > 0) {
               int x = initialX + i * 8;
               if (i * 2 + 1 < armorValue) {
                  guiGraphics.blit(ETHER_ARMOR_FULL, x, y, 0.0F, 0.0F, 9, 9, 9, 9);
               }

               if (i * 2 + 1 == armorValue) {
                  guiGraphics.blit(ETHER_ARMOR_HALF, x, y, 0.0F, 0.0F, 9, 9, 9, 9);
               }

               if (i * 2 + 1 > armorValue) {
                  guiGraphics.blit(ETHER_ARMOR_EMPTY, x, y, 0.0F, 0.0F, 9, 9, 9, 9);
               }
            }
         }
      }
   }

   public static void renderOrbOfProphecyUse(GuiGraphics guiGraphics) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null
         && player.isUsingItem()
         && player.getUseItem().is(ESItems.ORB_OF_PROPHECY.get())
         && !player.getUseItem().has(ESDataComponents.CURRENT_CREST.get())) {
         int usingTicks = player.getTicksUsingItem();
         float ticks = Math.min(
            usingTicks
               + Minecraft.getInstance()
                  .getTimer()
                  .getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()),
            150.0F
         );
         float progress = Math.min(ticks, 150.0F) / 150.0F;
         if (usingTicks < 150) {
            renderTextureOverlay(guiGraphics, ORB_OF_PROPHECY_USE, progress);
         }
      }
   }

   public static void renderCurrentCrest(GuiGraphics guiGraphics) {
      if (Minecraft.getInstance().player != null) {
         Registry<Crest> registry = Minecraft.getInstance().player.registryAccess().registryOrThrow(ESRegistries.CREST);
         float partialTicks = Minecraft.getInstance()
            .getTimer()
            .getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally());

         for (Entry<ResourceKey<Crest>, ESClientHandler.GuiCrest> entry : GUI_CRESTS.entrySet()) {
            ESClientHandler.GuiCrest guiCrest = entry.getValue();
            Crest crest = (Crest)registry.get(entry.getKey());
            if (crest != null) {
               ESGuiUtil.blitFloat(guiGraphics, crest.texture(), guiCrest.getX(partialTicks), guiCrest.getY(partialTicks), 72.0F, 72.0F, 72.0F, 72.0F);
            }
         }
      }
   }

   public static void renderCarvedLunarisCactusFruitBlur(GuiGraphics guiGraphics) {
      if (Minecraft.getInstance().options.getCameraType().isFirstPerson() && Minecraft.getInstance().player != null) {
         ItemStack itemStack = Minecraft.getInstance().player.getInventory().getArmor(3);
         if (itemStack.is(((EquipableCarvedLunarisCactusFruitBlock)ESBlocks.CARVED_LUNARIS_CACTUS_FRUIT.get()).asItem())) {
            renderTextureOverlay(guiGraphics, LUNARIS_CACTUS_BLUR_LOCATION, 1.0F);
         }
      }
   }

   public static void renderDreamCatcher(GuiGraphics guiGraphics) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && player.hasEffect(ESMobEffects.DREAM_CATCHER.asHolder())) {
         for (ESClientHandler.DreamCatcherText text : DREAM_CATCHER_TEXTS) {
            guiGraphics.drawCenteredString(Minecraft.getInstance().font, text.getText(), text.getX(), text.getY(), 5343172);
         }
      }
   }

   public static void setAirBubbleColor() {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && player.level().getBiome(player.blockPosition()).is(ESBiomes.THE_ABYSS)) {
         RenderSystem.setShaderColor(1.0F, (float)Mth.clamp(1.0 + player.getY() / 64.0, 0.0, 1.0), 1.0F, 1.0F);
      }
   }

   public static int getAirBubbleYOffset() {
      LocalPlayer player = Minecraft.getInstance().player;
      return player != null && player.level().getBiome(player.blockPosition()).is(ESBiomes.THE_ABYSS) && player.getY() < 0.0
         ? player.getRandom().nextInt(2)
         : 0;
   }

   private static class DreamCatcherText {
      private final Component text;
      private final int speed;
      private int x;
      private final int y;

      public DreamCatcherText(Component component, int speed, int x, int y) {
         this.text = component;
         this.speed = speed;
         this.x = x;
         this.y = y;
      }

      public Component getText() {
         return this.text;
      }

      public int getX() {
         return this.x;
      }

      public int getY() {
         return this.y;
      }

      public void updatePosition() {
         this.x = this.x + this.speed;
      }
   }

   private static class GuiCrest {
      private boolean shouldShow = false;
      private float prevAngle = -135.0F;
      private float angle = -135.0F;

      public void tick() {
         this.prevAngle = this.angle;
         this.angle = Mth.approachDegrees(this.angle, this.shouldShow ? 45.0F : -135.0F, 15.0F);
      }

      public float getX(float partialTicks) {
         return (float)Math.cos(Mth.lerp(partialTicks, this.prevAngle, this.angle) * (float) (Math.PI / 180.0)) * 36.0F * Mth.SQRT_OF_TWO - 36.0F;
      }

      public float getY(float partialTicks) {
         return (float)Math.sin(Mth.lerp(partialTicks, this.prevAngle, this.angle) * (float) (Math.PI / 180.0)) * 36.0F * Mth.SQRT_OF_TWO - 36.0F;
      }
   }
}
