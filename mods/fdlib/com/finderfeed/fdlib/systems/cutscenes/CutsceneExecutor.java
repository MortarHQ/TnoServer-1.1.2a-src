package com.finderfeed.fdlib.systems.cutscenes;

import com.finderfeed.fdlib.data_structures.ObjectHolder;
import com.finderfeed.fdlib.systems.cutscenes.camera_motion.CameraLookProcessor;
import com.finderfeed.fdlib.systems.cutscenes.camera_motion.CameraMotion;
import com.finderfeed.fdlib.systems.cutscenes.camera_motion.CatmullRomCameraMotion;
import com.finderfeed.fdlib.systems.cutscenes.camera_motion.LinearCameraMotion;
import com.finderfeed.fdlib.systems.cutscenes.camera_motion.NormalLookProcessor;
import com.finderfeed.fdlib.systems.screen.screen_effect.ScreenEffect;
import com.finderfeed.fdlib.systems.screen.screen_effect.ScreenEffectData;
import com.finderfeed.fdlib.systems.screen.screen_effect.ScreenEffectFactory;
import com.finderfeed.fdlib.systems.screen.screen_effect.ScreenEffectOverlay;
import com.finderfeed.fdlib.systems.screen.screen_effect.ScreenEffectType;
import java.util.List;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class CutsceneExecutor {
   private CutsceneData data;
   private CameraLookProcessor lookProcessor;
   private CameraMotion cameraMotion;
   private int currentTime = 0;

   public CutsceneExecutor(CutsceneData data) {
      this.data = data;
      if (data.getMoveType() == CurveType.LINEAR) {
         this.cameraMotion = new LinearCameraMotion();
      } else {
         this.cameraMotion = new CatmullRomCameraMotion();
      }

      this.lookProcessor = new NormalLookProcessor();
   }

   public boolean tick(ClientCameraEntity camera) {
      camera.xo = camera.getX();
      camera.yo = camera.getY();
      camera.zo = camera.getZ();
      if (this.currentTime > this.data.getCutsceneTime()) {
         return true;
      }

      if (this.currentTime != 0) {
         useScreenEffectsOnTick(this.data.getScreenEffectData(), this.currentTime);
      }

      Vec3 newPos = this.getCameraPos();
      camera.setPos(newPos);
      this.currentTime = Mth.clamp(this.currentTime + 1, 0, this.data.getCutsceneTime());
      return false;
   }

   protected static void useScreenEffectsOnTick(CutsceneScreenEffectData screenEffectData, int tick) {
      List<CutsceneScreenEffectData.ScreenEffectInstance<?, ?>> effects = screenEffectData.getAllEffectsOnTick(tick);
      if (effects != null) {
         for (CutsceneScreenEffectData.ScreenEffectInstance<?, ?> effect : effects) {
            ScreenEffectData data = effect.data();
            ScreenEffectType<?, ? extends ScreenEffect<?>> type = (ScreenEffectType<?, ? extends ScreenEffect<?>>)effect.type();
            ScreenEffect<?> screenEffect = useScreenEffectFactory(type, data, effect.inTime(), effect.stayTime(), effect.outTime());
            ScreenEffectOverlay.addScreenEffect(screenEffect);
         }
      }
   }

   protected static <A extends ScreenEffectData, B extends ScreenEffect<A>> ScreenEffect<A> useScreenEffectFactory(
      ScreenEffectType<A, B> screenEffectType, ScreenEffectData data, int inTime, int stayTime, int outTime
   ) {
      ScreenEffectFactory<A, B> factory = screenEffectType.factory;
      return factory.create((A)data, inTime, stayTime, outTime);
   }

   public Vec3 getCameraPos() {
      return this.cameraMotion.calculateCameraPosition(this.data, this.currentTime, 0.0F);
   }

   public void setCameraRotation(float partialTick, ObjectHolder<Float> yaw, ObjectHolder<Float> pitch, ObjectHolder<Float> roll) {
      this.lookProcessor.rotate(this.data, this.currentTime, partialTick, yaw, pitch, roll);
   }

   public boolean hasEnded() {
      return this.currentTime >= this.data.getCutsceneTime();
   }

   public CutsceneData getData() {
      return this.data;
   }

   public int getCurrentTime() {
      return this.currentTime;
   }
}
