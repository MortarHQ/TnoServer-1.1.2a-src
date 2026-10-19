package com.finderfeed.fdlib.systems.cutscenes;

import com.finderfeed.fdlib.FDClientHelpers;
import com.finderfeed.fdlib.data_structures.ObjectHolder;
import com.finderfeed.fdlib.init.FDClientModEvents;
import com.finderfeed.fdlib.systems.hud.FDHuds;
import com.finderfeed.fdlib.systems.screen.screen_particles.ScreenParticlesRenderEvent;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.neoforged.neoforge.client.event.ClientTickEvent.Pre;
import net.neoforged.neoforge.client.event.InputEvent.InteractionKeyMappingTriggered;
import net.neoforged.neoforge.client.event.RenderHighlightEvent.Block;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeCameraAngles;

@EventBusSubscriber(modid = "fdlib", value = Dist.CLIENT, bus = Bus.GAME)
public class CutsceneCameraHandler {
   private static ClientCameraEntity clientCameraEntity;
   private static CutsceneExecutor cutsceneExecutor;

   @SubscribeEvent
   private static void onLogoff(LoggingOut out) {
      clientCameraEntity = null;
      cutsceneExecutor = null;
   }

   @SubscribeEvent
   public static void renderScreenParticlesEvent(ScreenParticlesRenderEvent.Gui event) {
      if (isCutsceneActive()) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void renderScreenParticlesEvent2(ScreenParticlesRenderEvent.Screen event) {
      if (isCutsceneActive()) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void tickEvent(Pre event) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (!Minecraft.getInstance().isPaused()) {
         if (player == null) {
            stopCutscene();
         } else {
            boolean wasStoppedByPlayer = false;

            while (FDClientModEvents.END_CUTSCENE.consumeClick()) {
               wasStoppedByPlayer = true;
            }

            if (isCutsceneActive()) {
               nullifyInput(player);
               if (cutsceneExecutor.getData().getStopMode() == CutsceneData.StopMode.PLAYER && wasStoppedByPlayer) {
                  stopCutscene();
               } else {
                  if (Minecraft.getInstance().options.getCameraType() != CameraType.FIRST_PERSON) {
                     Minecraft.getInstance().options.setCameraType(CameraType.FIRST_PERSON);
                  }

                  ensurePlayerIsACamera();
                  cutsceneExecutor.tick(clientCameraEntity);
                  if (cutsceneExecutor.hasEnded()) {
                     CutsceneData nextCutscene = cutsceneExecutor.getData().getNextCutscene();
                     if (nextCutscene != null) {
                        startCutscene(nextCutscene);
                     } else if (cutsceneExecutor.getData().getStopMode() == CutsceneData.StopMode.AUTOMATIC) {
                        stopCutscene();
                     }
                  }
               }
            }
         }
      }
   }

   public static void nullifyInput(LocalPlayer player) {
      Input input = player.input;
      input.leftImpulse = 0.0F;
      input.forwardImpulse = 0.0F;
      input.up = false;
      input.down = false;
      input.left = false;
      input.right = false;
      input.jumping = false;
      input.shiftKeyDown = false;
   }

   @SubscribeEvent
   public static void renderHighlightEvent(Block event) {
      if (isCutsceneActive()) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void renderGuiLayers(net.neoforged.neoforge.client.event.RenderGuiLayerEvent.Pre event) {
      if (isCutsceneActive() && event.getLayer() != FDHuds.SCREEN_EFFECT_OVERLAY) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void renderHand(RenderHandEvent event) {
      if (isCutsceneActive()) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void cancelPlayerMouseUsage(InteractionKeyMappingTriggered event) {
      if (isCutsceneActive()) {
         event.setCanceled(true);
         event.setSwingHand(false);
      }
   }

   @SubscribeEvent
   public static void cameraAngles(ComputeCameraAngles event) {
      if (isCutsceneActive()) {
         ObjectHolder<Float> yaw = new ObjectHolder<>(event.getYaw());
         ObjectHolder<Float> pitch = new ObjectHolder<>(event.getPitch());
         ObjectHolder<Float> roll = new ObjectHolder<>(event.getRoll());
         cutsceneExecutor.setCameraRotation((float)event.getPartialTick(), yaw, pitch, roll);
         event.setPitch(pitch.getValue());
         event.setYaw(yaw.getValue());
         event.setRoll(roll.getValue());
      }
   }

   private static void ensurePlayerIsACamera() {
      if (cutsceneExecutor != null && clientCameraEntity != null) {
         Entity camera = Minecraft.getInstance().cameraEntity;
         if (!(camera instanceof ClientCameraEntity)) {
            Minecraft.getInstance().setCameraEntity(clientCameraEntity);
         }
      }
   }

   public static void startCutscene(CutsceneData data) {
      Level level = FDClientHelpers.getClientLevel();
      Entity currentCamera = Minecraft.getInstance().cameraEntity;
      CutsceneScreenEffectData cutsceneScreenEffectData = data.getScreenEffectData();
      CutsceneExecutor.useScreenEffectsOnTick(cutsceneScreenEffectData, 0);
      CameraPos pos = data.getCameraPositions().getFirst();
      if (currentCamera instanceof ClientCameraEntity && clientCameraEntity != null) {
         Vec3 p = pos.getPos();
         currentCamera.setPos(p);
         currentCamera.xo = p.x;
         currentCamera.yo = p.y;
         currentCamera.zo = p.z;
      } else {
         ClientCameraEntity camera = new ClientCameraEntity(level);
         Vec3 p = pos.getPos();
         camera.setPos(p);
         camera.xo = p.x;
         camera.yo = p.y;
         camera.zo = p.z;
         Minecraft.getInstance().setCameraEntity(camera);
         clientCameraEntity = camera;
      }

      cutsceneExecutor = new CutsceneExecutor(data);
   }

   public static void moveCamera(CutsceneData cutsceneData) {
      if (isCutsceneActive()) {
         cutsceneData = new CutsceneData(cutsceneData);
         CutsceneScreenEffectData screenEffects = cutsceneData.getScreenEffectData();
         CutsceneExecutor.useScreenEffectsOnTick(screenEffects, 0);
         Vec3 pos = cutsceneExecutor.getCameraPos();
         ObjectHolder<Float> yaw = new ObjectHolder<>(0.0F);
         ObjectHolder<Float> pitch = new ObjectHolder<>(0.0F);
         ObjectHolder<Float> roll = new ObjectHolder<>(0.0F);
         cutsceneExecutor.setCameraRotation(0.0F, yaw, pitch, roll);
         cutsceneData.addCameraPos(0, new CameraPos(pos, yaw.getValue(), pitch.getValue(), roll.getValue()));
         cutsceneExecutor = new CutsceneExecutor(cutsceneData);
      }
   }

   public static void stopCutscene() {
      if (isCutsceneActive()) {
         clientCameraEntity = null;
         cutsceneExecutor = null;
         MouseHandler mouseHandler = Minecraft.getInstance().mouseHandler;
         mouseHandler.accumulatedDX = 0.0;
         mouseHandler.accumulatedDY = 0.0;
         Minecraft.getInstance().setCameraEntity(Minecraft.getInstance().player);
      }
   }

   public static CutsceneExecutor getCutsceneExecutor() {
      return cutsceneExecutor;
   }

   public static boolean isCutsceneActive() {
      return clientCameraEntity != null && cutsceneExecutor != null;
   }
}
