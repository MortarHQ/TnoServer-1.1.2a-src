package net.unusual.block_factorys_bosses.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderGuiEvent.Pre;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeCameraAngles;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.neoforged.neoforge.event.level.LevelEvent.Load;
import net.neoforged.neoforge.event.level.LevelEvent.Unload;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.attachment.entity.RollAttachment;
import net.unusual.block_factorys_bosses.client.AnimationTickHolder;
import net.unusual.block_factorys_bosses.client.CinematicEntity;
import net.unusual.block_factorys_bosses.client.camera.BossesRiseClientCinematicCamera;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticleEmitter;
import net.unusual.block_factorys_bosses.client.renderer.DragonBonesFirstPersonRenderer;
import net.unusual.block_factorys_bosses.client.renderer.KnightFirstPersonRenderer;
import net.unusual.block_factorys_bosses.configuration.ClientConfiguration;
import net.unusual.block_factorys_bosses.configuration.ServerConfiguration;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.YetiEntity;
import net.unusual.block_factorys_bosses.entity.decoration.CannonEntity;
import net.unusual.block_factorys_bosses.geckolib.boss.knight.UnderworldKnightRenderer;
import net.unusual.block_factorys_bosses.geckolib.util.ParticleLocator;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {
   public static final Map<UnderworldKnightEntity, SubParticleEmitter> PARTICULARS = new HashMap<>();
   private static final ResourceLocation DASH_GREEN = BossesRise.prefix("textures/gui/roll/dash_green.png");
   private static final ResourceLocation DASH_YELLOW = BossesRise.prefix("textures/gui/roll/dash_yellow.png");
   private static final ResourceLocation DASH_ORANGE = BossesRise.prefix("textures/gui/roll/dash_orange.png");
   private static final ResourceLocation DASH_RED = BossesRise.prefix("textures/gui/roll/dash_red.png");
   private static final ResourceLocation DASH_GREY = BossesRise.prefix("textures/gui/roll/dash_grey.png");
   private static final int TEXTURE_SIZE = 18;

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void renderGUI(Pre event) {
      if ((Boolean)ClientConfiguration.ROLL_GUI.get()) {
         if ((Boolean)ServerConfiguration.CAN_USE_ROLL.get()) {
            Minecraft minecraft = Minecraft.getInstance();
            if (!minecraft.options.hideGui && minecraft.player != null && !minecraft.player.isSpectator()) {
               GuiGraphics guiGraphics = event.getGuiGraphics();
               guiGraphics.drawManaged(() -> renderGUIManaged(guiGraphics));
            }
         }
      }
   }

   public static void renderGUIManaged(GuiGraphics guiGraphics) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.player != null) {
         RenderSystem.enableBlend();
         RollAttachment attachment = RollAttachment.fromPlayer(minecraft.player);
         guiGraphics.guiWidth();
         guiGraphics.pose().pushPose();
         int count = attachment.rollCount();
         float ready = 0.0F;

         for (int i = 0; i < count; i++) {
            ready += 1.0F - attachment.getCooldown(i);
         }

         ResourceLocation location = DASH_GREY;
         int color = 3487029;
         if (ready - 1.0F >= (count - 1) * 0.75F) {
            location = DASH_GREEN;
            color = 7654656;
         } else if (ready - 1.0F >= (count - 1) * 0.5F) {
            location = DASH_YELLOW;
            color = 15250688;
         } else if (ready - 1.0F >= (count - 1) * 0.25F) {
            location = DASH_ORANGE;
            color = 15224064;
         } else if (ready >= 1.0F) {
            location = DASH_RED;
            color = 14221315;
         }

         int configX = (Integer)ClientConfiguration.ROLL_GUI_OFFSET_X.get();
         int configY = (Integer)ClientConfiguration.ROLL_GUI_OFFSET_Y.get();

         int x = switch ((ClientConfiguration.GUIAnchors)ClientConfiguration.ROLL_GUI_OFFSET_ANCHOR.get()) {
            case HOTBAR_RIGHT -> guiGraphics.guiWidth() / 2 + 92;
            case HOTBAR_LEFT -> guiGraphics.guiWidth() / 2 - 110 - Minecraft.getInstance().font.width(String.valueOf((int)ready));
            case TOP_LEFT, BOTTOM_LEFT -> 1;
            case TOP_RIGHT, BOTTOM_RIGHT -> guiGraphics.guiWidth() - 18 - Minecraft.getInstance().font.width(String.valueOf((int)ready)) - 1;
            case SCREEN_CENTER -> guiGraphics.guiWidth() / 2 - 9;
         };

         int y = switch ((ClientConfiguration.GUIAnchors)ClientConfiguration.ROLL_GUI_OFFSET_ANCHOR.get()) {
            case HOTBAR_RIGHT, HOTBAR_LEFT, BOTTOM_LEFT, BOTTOM_RIGHT -> guiGraphics.guiHeight() - 1;
            case TOP_LEFT, TOP_RIGHT -> 19;
            case SCREEN_CENTER -> guiGraphics.guiHeight() / 2 - 9 + 18;
         };
         guiGraphics.blit(location, x + configX, y - 18 + configY, 0.0F, 0.0F, 18, 18, 18, 18);
         guiGraphics.drawString(Minecraft.getInstance().font, String.valueOf((int)ready), x + 18 + configX + 1, y - 9 + configY, 0, false);
         guiGraphics.drawString(Minecraft.getInstance().font, String.valueOf((int)ready), x + 18 + configX - 1, y - 9 + configY, 0, false);
         guiGraphics.drawString(Minecraft.getInstance().font, String.valueOf((int)ready), x + 18 + configX, y - 9 + configY + 1, 0, false);
         guiGraphics.drawString(Minecraft.getInstance().font, String.valueOf((int)ready), x + 18 + configX, y - 9 + configY - 1, 0, false);
         guiGraphics.drawString(Minecraft.getInstance().font, String.valueOf((int)ready), x + 18 + configX, y - 9 + configY, color, false);
         guiGraphics.pose().popPose();
         RenderSystem.disableBlend();
      }
   }

   @SubscribeEvent
   public static void computeCameraAngles(ComputeCameraAngles event) {
      if ((Boolean)ClientConfiguration.FIRST_PERSON_ROLL.get()) {
         Minecraft minecraft = Minecraft.getInstance();
         ClientLevel level = minecraft.level;
         if (level != null && minecraft.options.getCameraType() == CameraType.FIRST_PERSON && minecraft.player != null) {
            RollAttachment attachment = RollAttachment.fromPlayer(minecraft.player);
            if (attachment.isRolling()) {
               float rollProgress = (float)((attachment.getRoll() - event.getPartialTick()) / attachment.getRollDuration());
               switch (attachment.getDirection()) {
                  case FORWARD:
                     event.setPitch(event.getPitch() - rollProgress * 360.0F);
                     break;
                  case BACKWARD:
                     event.setPitch(event.getPitch() + rollProgress * 360.0F);
                     break;
                  case LEFT:
                     event.setRoll(event.getRoll() + rollProgress * 360.0F);
                     break;
                  case RIGHT:
                     event.setRoll(event.getRoll() - rollProgress * 360.0F);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void serverQuit(ServerStoppingEvent event) {
      BossesRiseClientCinematicCamera.stopCinematicCamera();
   }

   @SubscribeEvent
   public static void playerQuit(PlayerLoggedOutEvent event) {
      BossesRiseClientCinematicCamera.stopCinematicCamera();
   }

   @SubscribeEvent
   public static void entityDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof CinematicEntity entity) {
         if (entity == BossesRiseClientCinematicCamera.getTrackedObject()) {
            BossesRiseClientCinematicCamera.stopCinematicCamera();
         }
      }
   }

   @SubscribeEvent
   public static void playerDeath(PlayerRespawnEvent event) {
      BossesRiseClientCinematicCamera.stopCinematicCamera();
   }

   @SubscribeEvent
   public static void updateMovementInput(MovementInputUpdateEvent event) {
      if (event.getEntity() instanceof LocalPlayer player) {
         player.level()
            .getEntitiesOfClass(YetiEntity.class, player.getBoundingBox().inflate(16.0), e -> true)
            .forEach(
               yeti -> {
                  if ((Integer)yeti.getEntityData().get(YetiEntity.DATA_GROUNDSMASH_ANIMTIME) > 10
                     && (Integer)yeti.getEntityData().get(YetiEntity.DATA_GROUNDSMASH_ANIMTIME) <= 109) {
                     event.getInput().leftImpulse *= 0.0F;
                     event.getInput().forwardImpulse *= 0.0F;
                  }
               }
            );
         if (player.isUsingItem() && player.getUseItem().is(BossesRiseItems.ICE_GAUNTLET)) {
            event.getInput().leftImpulse *= 2.0F;
            event.getInput().forwardImpulse *= 2.0F;
         }
      }
   }

   @SubscribeEvent
   public static void onTickPre(net.neoforged.neoforge.client.event.ClientTickEvent.Pre event) {
      for (UnderworldKnightEntity knight : PARTICULARS.keySet().stream().filter(Entity::isRemoved).toList()) {
         PARTICULARS.remove(knight);
      }

      if (BossesRiseClientCinematicCamera.isCameraActive()) {
         CinematicEntity tracked = BossesRiseClientCinematicCamera.getTrackedObject();
         if (tracked instanceof Entity entity && entity.isRemoved()) {
            BossesRiseClientCinematicCamera.stopCinematicCamera();
         }

         if (tracked instanceof BlockEntity blockEntity && blockEntity.isRemoved()) {
            BossesRiseClientCinematicCamera.stopCinematicCamera();
         }
      }

      onTick(true);
   }

   @SubscribeEvent
   public static void onTickPost(Post event) {
      onTick(false);
   }

   public static void onTick(boolean isPreEvent) {
      if (isGameActive()) {
         DragonBonesFirstPersonRenderer.clientTick();
         KnightFirstPersonRenderer.clientTick();
         AnimationTickHolder.tick();
      }
   }

   @SubscribeEvent
   public static void onLoadWorld(Load event) {
      LevelAccessor world = event.getLevel();
      if (world.isClientSide() && world instanceof ClientLevel) {
         AnimationTickHolder.reset();
      }
   }

   @SubscribeEvent
   public static void onUnloadWorld(Unload event) {
      if (event.getLevel().isClientSide()) {
         AnimationTickHolder.reset();
      }
   }

   @SubscribeEvent
   public static void onRenderPlayerEvent(net.neoforged.neoforge.client.event.RenderPlayerEvent.Pre event) {
      if (event.getEntity() instanceof LocalPlayer localPlayer && localPlayer.getControlledVehicle() instanceof CannonEntity cannon && cannon.bonePos != null) {
         PoseStack stack = event.getPoseStack();
         float partialTick = event.getPartialTick();
         Vec3 diff = localPlayer.getPosition(partialTick)
            .subtract(cannon.getPosition(partialTick))
            .add(
               localPlayer.getVehicleAttachmentPoint(cannon)
                  .xRot(cannon.getViewXRot(partialTick) * (float) (Math.PI / 180.0))
                  .yRot((-cannon.getViewYRot(partialTick) - 180.0F) * (float) (Math.PI / 180.0))
            );
         stack.translate(-diff.x, -diff.y, -diff.z);
         stack.translate(cannon.bonePos.x, cannon.bonePos.y, cannon.bonePos.z);
         stack.mulPose(Axis.YN.rotationDegrees(cannon.getYRot()));
         stack.mulPose(Axis.XN.rotationDegrees(-cannon.getViewXRot(event.getPartialTick())));
         stack.mulPose(Axis.YP.rotationDegrees(cannon.getYRot()));
      }
   }

   @SubscribeEvent
   public static void onClientTick(net.neoforged.neoforge.event.tick.EntityTickEvent.Post event) {
      if (event.getEntity() instanceof var knight && knight.level().isClientSide && !PARTICULARS.containsKey(knight) && knight.isTransformed()) {
         ParticleLocator locator = UnderworldKnightRenderer.BONER.get("head");
         if (locator != null) {
            SubParticleEmitter emitter = SubParticleEmitter.attachNewParticle(
               knight, (ParticleOptions)BossesRiseParticleTypes.PHASE_TRANSITION_10.get(), locator
            );
            if (emitter != null) {
               PARTICULARS.put(knight, emitter);
            }
         }
      }
   }

   protected static boolean isGameActive() {
      return Minecraft.getInstance().level != null && Minecraft.getInstance().player != null;
   }
}
