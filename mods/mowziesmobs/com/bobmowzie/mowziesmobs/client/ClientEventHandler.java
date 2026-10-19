package com.bobmowzie.mowziesmobs.client;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.client.gui.CustomBossBar;
import com.bobmowzie.mowziesmobs.client.model.entity.ModelGeckoPlayerFirstPerson;
import com.bobmowzie.mowziesmobs.client.model.entity.ModelGeckoPlayerThirdPerson;
import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.bobmowzie.mowziesmobs.client.render.block.SculptorBlockMarking;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoFirstPersonRenderer;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoRenderPlayer;
import com.bobmowzie.mowziesmobs.client.sound.BossMusicPlayer;
import com.bobmowzie.mowziesmobs.server.capability.AbilityData;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import com.bobmowzie.mowziesmobs.server.capability.FrozenData;
import com.bobmowzie.mowziesmobs.server.capability.PlayerData;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.entity.frostmaw.EntityFrozenController;
import com.bobmowzie.mowziesmobs.server.item.ItemBlowgun;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress;
import net.neoforged.neoforge.client.event.InputEvent.InteractionKeyMappingTriggered;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.client.event.RenderLivingEvent.Pre;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeCameraAngles;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(Dist.CLIENT)
public class ClientEventHandler {
   private static final ResourceLocation FROZEN_BLUR = ResourceLocation.withDefaultNamespace("textures/misc/powder_snow_outline.png");
   private static ResourceLocation SCULPTOR_BLOCK_GLOW = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/entity/sculptor_highlight.png");

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onHandRender(RenderHandEvent event) {
      if ((Boolean)ConfigHandler.CLIENT.customPlayerAnims.get()) {
         LocalPlayer player = Minecraft.getInstance().player;
         if (player != null) {
            boolean shouldAnimate = false;
            AbilityData data = DataHandler.getData(player, DataHandler.ABILITY_DATA);
            shouldAnimate = data.getActiveAbility() != null;
            if (shouldAnimate) {
               GeckoPlayer.GeckoPlayerFirstPerson geckoPlayer = GeckoFirstPersonRenderer.GECKO_PLAYER_FIRST_PERSON;
               if (geckoPlayer != null) {
                  ModelGeckoPlayerFirstPerson geckoFirstPersonModel = (ModelGeckoPlayerFirstPerson)geckoPlayer.getModel();
                  GeckoFirstPersonRenderer firstPersonRenderer = (GeckoFirstPersonRenderer)geckoPlayer.getPlayerRenderer();
                  if (geckoFirstPersonModel != null && firstPersonRenderer != null) {
                     if (!geckoFirstPersonModel.isUsingSmallArms() && player.getSkin().model().name().equals("slim")) {
                        firstPersonRenderer.setSmallArms();
                     }

                     event.setCanceled(true);
                     if (event.isCanceled()) {
                        float delta = event.getPartialTick();
                        float f1 = Mth.lerp(delta, player.xRotO, player.getXRot());
                        firstPersonRenderer.renderItemInFirstPerson(
                           player,
                           f1,
                           delta,
                           event.getHand(),
                           event.getSwingProgress(),
                           event.getItemStack(),
                           event.getEquipProgress(),
                           event.getPoseStack(),
                           event.getMultiBufferSource(),
                           event.getPackedLight(),
                           geckoPlayer
                        );
                     }
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void renderLivingEvent(Pre<? extends LivingEntity, ? extends EntityModel<? extends LivingEntity>> event) {
      if (event.getEntity() instanceof Player player) {
         if (!(Boolean)ConfigHandler.CLIENT.customPlayerAnims.get()) {
            return;
         }

         if ((Boolean)ConfigHandler.CLIENT.hidePlayerAnimsInFirstPerson.get()
            && player == Minecraft.getInstance().player
            && Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON) {
            return;
         }

         float delta = event.getPartialTick();
         AbilityData abilityData = DataHandler.getData(player, DataHandler.ABILITY_DATA);
         if (abilityData.getActiveAbility() != null) {
            GeckoPlayer.GeckoPlayerThirdPerson geckoPlayer = DataHandler.<PlayerData>getData(player, DataHandler.PLAYER_DATA).getGeckoPlayer();
            if (geckoPlayer != null) {
               ModelGeckoPlayerThirdPerson geckoPlayerModel = (ModelGeckoPlayerThirdPerson)geckoPlayer.getModel();
               GeckoRenderPlayer animatedPlayerRenderer = (GeckoRenderPlayer)geckoPlayer.getPlayerRenderer();
               if (geckoPlayerModel != null && animatedPlayerRenderer != null) {
                  event.setCanceled(true);
                  if (event.isCanceled()) {
                     animatedPlayerRenderer.render(
                        (AbstractClientPlayer)event.getEntity(),
                        event.getEntity().getYRot(),
                        delta,
                        event.getPoseStack(),
                        event.getMultiBufferSource(),
                        event.getPackedLight(),
                        geckoPlayer
                     );
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player player = event.getEntity();
      if (player.level().isClientSide()) {
         GeckoPlayer geckoPlayer = DataHandler.<PlayerData>getData(player, DataHandler.PLAYER_DATA).getGeckoPlayer();
         if (geckoPlayer != null) {
            geckoPlayer.tick();
         }

         if (player == Minecraft.getInstance().player) {
            GeckoFirstPersonRenderer.GECKO_PLAYER_FIRST_PERSON.tick();
         }
      }
   }

   @SubscribeEvent
   public static void onRenderTick(net.neoforged.neoforge.client.event.RenderFrameEvent.Pre event) {
      Player player = Minecraft.getInstance().player;
      if (player != null) {
         FrozenData data = DataHandler.getData(player, DataHandler.FROZEN_DATA);
         if (data.getFrozen() && data.getPrevFrozen()) {
            player.setYRot(data.getFrozenYaw());
            player.setXRot(data.getFrozenPitch());
            player.yHeadRot = data.getFrozenYawHead();
            player.yRotO = player.getYRot();
            player.xRotO = player.getXRot();
            player.yHeadRotO = player.yHeadRot;
         }
      }
   }

   @SubscribeEvent
   public static void onRenderLiving(Pre<?, ?> event) {
      LivingEntity entity = event.getEntity();
      FrozenData data = DataHandler.getData(entity, DataHandler.FROZEN_DATA);
      if (data.getFrozen() && data.getPrevFrozen()) {
         entity.setYRot(entity.yRotO = data.getFrozenYaw());
         entity.setXRot(entity.xRotO = data.getFrozenPitch());
         entity.yHeadRot = entity.yHeadRotO = data.getFrozenYawHead();
         entity.yBodyRot = entity.yBodyRotO = data.getFrozenRenderYawOffset();
         entity.attackAnim = entity.oAttackAnim = data.getFrozenSwingProgress();
         entity.walkAnimation.setSpeed(0.0F);
         entity.walkAnimation.update(0.0F, 0.0F);
         entity.setShiftKeyDown(false);
      }
   }

   @SubscribeEvent
   public static void onRenderOverlay(net.neoforged.neoforge.client.event.RenderGuiLayerEvent.Post event) {
      if (event.getName() == VanillaGuiLayers.CAMERA_OVERLAYS && Minecraft.getInstance().player != null) {
         FrozenData data = DataHandler.getData(Minecraft.getInstance().player, DataHandler.FROZEN_DATA);
         if (data.getFrozen() && Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON) {
            GuiGraphics graphics = event.getGuiGraphics();
            graphics.blit(FROZEN_BLUR, 0, 0, 0.0F, 0.0F, graphics.guiWidth(), graphics.guiHeight(), graphics.guiWidth(), graphics.guiHeight());
         }
      }
   }

   @SubscribeEvent
   public static void onRenderHUD(net.neoforged.neoforge.client.event.RenderGuiLayerEvent.Pre event) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && player.isPassenger() && player.getVehicle() instanceof EntityFrozenController) {
         if (event.getName() == VanillaGuiLayers.VEHICLE_HEALTH) {
            event.setCanceled(true);
         }

         Minecraft.getInstance().gui.setOverlayMessage(Component.empty(), false);
      }
   }

   @SubscribeEvent
   public static void updateFOV(ComputeFovModifierEvent event) {
      Player player = event.getPlayer();
      if (player.isUsingItem() && player.getUseItem().getItem() instanceof ItemBlowgun) {
         int i = player.getTicksUsingItem();
         float f1 = i / 5.0F;
         if (f1 > 1.0F) {
            f1 = 1.0F;
         } else {
            f1 *= f1;
         }

         event.setNewFovModifier(1.0F - f1 * 0.15F);
      }
   }

   @SubscribeEvent
   public static void onSetupCamera(ComputeCameraAngles event) {
      Player player = Minecraft.getInstance().player;
      float delta = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
      float ticksExistedDelta = player.tickCount + delta;
      if ((Boolean)ConfigHandler.CLIENT.doCameraShakes.get() && !Minecraft.getInstance().isPaused()) {
         float shakeAmplitude = 0.0F;

         for (EntityCameraShake cameraShake : player.level().getEntitiesOfClass(EntityCameraShake.class, player.getBoundingBox().inflate(20.0, 20.0, 20.0))) {
            if (cameraShake.distanceTo(player) < cameraShake.getRadius()) {
               shakeAmplitude += cameraShake.getShakeAmount(player, delta);
            }
         }

         if (shakeAmplitude > 1.0F) {
            shakeAmplitude = 1.0F;
         }

         event.setPitch((float)(event.getPitch() + shakeAmplitude * Math.cos(ticksExistedDelta * 3.0F + 2.0F) * 25.0));
         event.setYaw((float)(event.getYaw() + shakeAmplitude * Math.cos(ticksExistedDelta * 5.0F + 1.0F) * 25.0));
         event.setRoll((float)(event.getRoll() + shakeAmplitude * Math.cos(ticksExistedDelta * 4.0F) * 25.0));
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onRenderBossBar(BossEventProgress event) {
      if ((Boolean)ConfigHandler.CLIENT.customBossBars.get()) {
         ResourceLocation bossRegistryName = ClientProxy.bossBarRegistryNames.getOrDefault(event.getBossEvent().getId(), null);
         if (bossRegistryName != null) {
            CustomBossBar customBossBar = CustomBossBar.customBossBars.getOrDefault(bossRegistryName, null);
            if (customBossBar != null) {
               event.setCanceled(true);
               customBossBar.renderBossBar(event);
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onRenderLevelStage(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_BLOCK_ENTITIES) {
         ClientLevel level = Minecraft.getInstance().level;
         if (Minecraft.getInstance().player != null && level != null) {
            Vec3 cameraPos = event.getCamera().getPosition();
            double d0 = cameraPos.x();
            double d1 = cameraPos.y();
            double d2 = cameraPos.z();
            ObjectIterator var9 = ClientProxy.sculptorMarkedBlocks.long2ObjectEntrySet().iterator();

            while (var9.hasNext()) {
               Entry<SculptorBlockMarking> entry = (Entry<SculptorBlockMarking>)var9.next();
               BlockPos blockpos2 = BlockPos.of(entry.getLongKey());
               event.getPoseStack().pushPose();
               event.getPoseStack().translate(blockpos2.getX() - d0, blockpos2.getY() - d1, blockpos2.getZ() - d2);
               Pose posestack$pose1 = event.getPoseStack().last();
               float tick = event.getRenderTick();
               float blockOffset = (blockpos2.getX() + blockpos2.getY() + blockpos2.getZ()) * 0.25F;
               VertexConsumer vertexconsumer1 = new SheetedDecalTextureGenerator(
                  Minecraft.getInstance()
                     .renderBuffers()
                     .bufferSource()
                     .getBuffer(MMRenderType.highlight(SCULPTOR_BLOCK_GLOW, tick * 0.02F + blockOffset, tick * 0.01F + blockOffset)),
                  posestack$pose1,
                  0.25F
               );
               ModelData modelData = level.getModelDataManager().getAt(blockpos2);
               renderBreakingTexture(level.getBlockState(blockpos2), blockpos2, level, event.getPoseStack(), level.random, vertexconsumer1, modelData);
               event.getPoseStack().popPose();
            }
         }
      }
   }

   private static void renderBreakingTexture(
      BlockState state,
      BlockPos pos,
      BlockAndTintGetter blockAndTintGetter,
      PoseStack poseStack,
      RandomSource random,
      VertexConsumer vertexConsumer,
      ModelData modelData
   ) {
      if (state.getRenderShape() == RenderShape.MODEL) {
         BlockRenderDispatcher blockRenderDispatcher = Minecraft.getInstance().getBlockRenderer();
         BakedModel bakedmodel = blockRenderDispatcher.getBlockModel(state);
         long i = state.getSeed(pos);
         blockRenderDispatcher.getModelRenderer()
            .tesselateBlock(blockAndTintGetter, bakedmodel, state, pos, poseStack, vertexConsumer, true, random, i, OverlayTexture.NO_OVERLAY, modelData, null);
      }
   }

   @SubscribeEvent
   public static void onLevelTick(net.neoforged.neoforge.event.tick.LevelTickEvent.Post event) {
      if (event.getLevel().isClientSide()) {
         MMCommon.PROXY.updateMarkedBlocks();
         BossMusicPlayer.tick();
      }
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onInteractionKeyMappingTriggered(InteractionKeyMappingTriggered event) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null) {
         if (event.getKeyMapping() == Minecraft.getInstance().options.keyAttack) {
            PlayerData data = DataHandler.getData(player, DataHandler.PLAYER_DATA);
            if (data != null) {
               data.pressedAttackKey(player);
            }
         } else if (event.getKeyMapping() == Minecraft.getInstance().options.keyUse) {
            PlayerData data = DataHandler.getData(player, DataHandler.PLAYER_DATA);
            if (data != null) {
               data.pressedUseKey(player);
            }
         }
      }
   }
}
