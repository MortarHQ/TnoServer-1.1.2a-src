package com.blackgear.platform.client.event;

import com.blackgear.platform.core.util.event.CancellableResult;
import com.blackgear.platform.core.util.event.Event;
import com.mojang.blaze3d.shaders.FogShape;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer.FogMode;
import net.minecraft.world.level.material.FogType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface FogRendering {
   Event<FogRendering.FogDensity> FOG_DENSITY = Event.create(FogRendering.FogDensity.class, densities -> (camera, density) -> {
      FogRendering.FogDensity[] var3 = densities;
      int var4 = var3.length;
      byte var5 = 0;
      if (var5 < var4) {
         FogRendering.FogDensity callback = var3[var5];
         return callback.setDensity(camera, density);
      } else {
         return density;
      }
   });
   Event<FogRendering.FogColor> FOG_COLOR = Event.create(FogRendering.FogColor.class);
   Event<FogRendering.FogRender> FOG_RENDER = Event.cancellable(FogRendering.FogRender.class);

   class ColorData {
      private final Camera camera;
      private float red;
      private float green;
      private float blue;

      public ColorData(Camera camera, float red, float green, float blue) {
         this.camera = camera;
         this.red = red;
         this.green = green;
         this.blue = blue;
      }

      public Camera getCamera() {
         return this.camera;
      }

      public float getRed() {
         return this.red;
      }

      public float getGreen() {
         return this.green;
      }

      public float getBlue() {
         return this.blue;
      }

      public void setRed(float red) {
         this.red = red;
      }

      public void setGreen(float green) {
         this.green = green;
      }

      public void setBlue(float blue) {
         this.blue = blue;
      }
   }

   interface FogColor {
      void setColor(FogRendering.ColorData var1, float var2);
   }

   class FogData {
      private float farPlaneDistance;
      private float nearPlaneDistance;
      private FogShape shape;

      public FogData(float nearPlaneDistance, float farPlaneDistance, FogShape shape) {
         this.farPlaneDistance = farPlaneDistance;
         this.nearPlaneDistance = nearPlaneDistance;
         this.shape = shape;
      }

      public float getFarPlaneDistance() {
         return this.farPlaneDistance;
      }

      public float getNearPlaneDistance() {
         return this.nearPlaneDistance;
      }

      public FogShape getShape() {
         return this.shape;
      }

      public void setFarPlaneDistance(float farPlaneDistance) {
         this.farPlaneDistance = farPlaneDistance;
      }

      public void setNearPlaneDistance(float nearPlaneDistance) {
         this.nearPlaneDistance = nearPlaneDistance;
      }

      public void setShape(FogShape shape) {
         this.shape = shape;
      }
   }

   interface FogDensity {
      float setDensity(Camera var1, float var2);
   }

   interface FogRender {
      CancellableResult onFogRender(
         FogMode var1, FogType var2, Camera var3, float var4, float var5, float var6, float var7, FogShape var8, FogRendering.FogData var9
      );
   }
}
