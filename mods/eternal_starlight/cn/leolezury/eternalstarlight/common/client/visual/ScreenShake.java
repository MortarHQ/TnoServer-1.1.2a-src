package cn.leolezury.eternalstarlight.common.client.visual;

import cn.leolezury.eternalstarlight.common.util.Easing;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class ScreenShake {
   private final ResourceLocation dimension;
   private final Vec3 pos;
   private final float radius;
   private final int duration;
   private final float horizontalPower;
   private final float verticalPower;
   private final float horizontalFreq;
   private final float verticalFreq;
   private Easing fadeEasing = Easing.IN_QUART;
   private int age;

   public ScreenShake(
      ResourceLocation dimension, Vec3 pos, float radius, int duration, float horizontalPower, float verticalPower, float horizontalFreq, float verticalFreq
   ) {
      this.dimension = dimension;
      this.pos = pos;
      this.radius = radius;
      this.duration = duration;
      this.horizontalPower = horizontalPower;
      this.verticalPower = verticalPower;
      this.horizontalFreq = horizontalFreq;
      this.verticalFreq = verticalFreq;
   }

   public void setFadeEasing(Easing fadeEasing) {
      this.fadeEasing = fadeEasing;
   }

   public void tick() {
      this.age++;
   }

   public boolean shouldRemove() {
      return this.age > this.duration;
   }

   private float getPowerScale(float partialTicks) {
      float progress = Math.min((this.age + partialTicks) / this.duration, 1.0F);
      return this.fadeEasing.interpolate(progress, 1.0F, 0.0F);
   }

   public float getYawOffset() {
      Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
      LocalPlayer player = Minecraft.getInstance().player;
      float partialTicks = Minecraft.getInstance()
         .getTimer()
         .getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally());
      float time = player != null ? player.tickCount + partialTicks : 0.0F;
      float distance = (float)this.pos.distanceTo(camera.getPosition());
      if (!(distance > this.radius) && (player == null || player.level().dimension().location().equals(this.dimension))) {
         float powerScale = this.getPowerScale(partialTicks) * (1.0F - distance / this.radius);
         float power = this.horizontalPower * powerScale;
         return (float)(Math.sin(time * this.horizontalFreq) * power);
      } else {
         return 0.0F;
      }
   }

   public float getPitchOffset() {
      Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
      LocalPlayer player = Minecraft.getInstance().player;
      float partialTicks = Minecraft.getInstance()
         .getTimer()
         .getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally());
      float time = player != null ? player.tickCount + partialTicks : 0.0F;
      float distance = (float)this.pos.distanceTo(camera.getPosition());
      if (!(distance > this.radius) && (player == null || player.level().dimension().location().equals(this.dimension))) {
         float powerScale = this.getPowerScale(partialTicks) * (1.0F - distance / this.radius);
         float power = this.verticalPower * powerScale;
         return (float)(Math.sin(time * this.verticalFreq) * power);
      } else {
         return 0.0F;
      }
   }
}
