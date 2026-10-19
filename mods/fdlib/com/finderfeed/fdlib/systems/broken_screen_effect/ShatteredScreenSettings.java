package com.finderfeed.fdlib.systems.broken_screen_effect;

import com.finderfeed.fdlib.FDLib;
import com.finderfeed.fdlib.util.FDByteBufCodecs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public class ShatteredScreenSettings {
   public static final ResourceLocation NULL_LOCATION = FDLib.location("null");
   public static final ResourceLocation DATA_1_1 = FDLib.location("textures/gui/effects/broken_screen_1_data.png");
   public static final ResourceLocation DATA_1_GLASSY = FDLib.location("textures/gui/effects/broken_screen_1_data_2.png");
   public static final ResourceLocation DATA_2 = FDLib.location("textures/gui/effects/broken_screen_1_data_2.png");
   public static final ResourceLocation SCREEN_1 = FDLib.location("textures/gui/effects/broken_screen_1.png");
   public static final ResourceLocation SCREEN_2 = FDLib.location("textures/gui/effects/broken_screen_2.png");
   public static StreamCodec<FriendlyByteBuf, ShatteredScreenSettings> STREAM_CODEC = FDByteBufCodecs.composite(
      FDByteBufCodecs.RESOURCE_LOCATION,
      v -> v.shatteredScreenDataTexture,
      FDByteBufCodecs.RESOURCE_LOCATION,
      v -> v.shatteredScreenTexture,
      ByteBufCodecs.INT,
      v -> v.inTime,
      ByteBufCodecs.INT,
      v -> v.stayTime,
      ByteBufCodecs.INT,
      v -> v.outTime,
      ByteBufCodecs.FLOAT,
      v -> v.maxOffset,
      ByteBufCodecs.FLOAT,
      v -> v.chromaticAbberationStrength,
      ByteBufCodecs.BOOL,
      v -> v.onScreen,
      ShatteredScreenSettings::new
   );
   public ResourceLocation shatteredScreenDataTexture;
   public ResourceLocation shatteredScreenTexture;
   public int stayTime;
   public int inTime;
   public int outTime;
   public float maxOffset;
   public float chromaticAbberationStrength;
   public boolean onScreen;

   public ShatteredScreenSettings(
      ResourceLocation shatteredScreenDataTexture,
      ResourceLocation shatteredScreenTexture,
      int inTime,
      int stayTime,
      int outTime,
      float maxOffset,
      float chromaticAbberationStrength,
      boolean onScreen
   ) {
      this.shatteredScreenTexture = shatteredScreenTexture;
      this.shatteredScreenDataTexture = shatteredScreenDataTexture;
      this.inTime = inTime;
      this.outTime = outTime;
      this.maxOffset = maxOffset;
      this.stayTime = stayTime;
      this.chromaticAbberationStrength = chromaticAbberationStrength;
      this.onScreen = onScreen;
   }

   public ShatteredScreenSettings(
      ResourceLocation shatteredScreenDataTexture, int inTime, int stayTime, int outTime, float maxOffset, float chromaticAbberationStrength, boolean onScreen
   ) {
      this.shatteredScreenTexture = NULL_LOCATION;
      this.shatteredScreenDataTexture = shatteredScreenDataTexture;
      this.inTime = inTime;
      this.outTime = outTime;
      this.maxOffset = maxOffset;
      this.stayTime = stayTime;
      this.chromaticAbberationStrength = chromaticAbberationStrength;
      this.onScreen = onScreen;
   }

   public ShatteredScreenSettings() {
      this.shatteredScreenDataTexture = DATA_1_GLASSY;
      this.shatteredScreenTexture = NULL_LOCATION;
      this.stayTime = 0;
      this.inTime = 0;
      this.outTime = 20;
      this.maxOffset = 0.1F;
      this.chromaticAbberationStrength = 0.05F;
      this.onScreen = true;
   }

   public static ShatteredScreenSettings.Builder builder() {
      return new ShatteredScreenSettings.Builder();
   }

   public static class Builder {
      private ShatteredScreenSettings settings = new ShatteredScreenSettings();

      public ShatteredScreenSettings.Builder setDataTexture(ResourceLocation resourceLocation) {
         this.settings.shatteredScreenDataTexture = resourceLocation;
         return this;
      }

      public ShatteredScreenSettings.Builder setOverlayTexture(ResourceLocation resourceLocation) {
         this.settings.shatteredScreenTexture = resourceLocation;
         return this;
      }

      public ShatteredScreenSettings.Builder time(int inTime, int stayTime, int outTime) {
         this.settings.inTime = inTime;
         this.settings.stayTime = stayTime;
         this.settings.outTime = outTime;
         return this;
      }

      public ShatteredScreenSettings.Builder setMaxOffset(float maxOffset) {
         this.settings.maxOffset = maxOffset;
         return this;
      }

      public ShatteredScreenSettings.Builder setChromaticAbberationStrength(float strength) {
         this.settings.chromaticAbberationStrength = strength;
         return this;
      }

      public ShatteredScreenSettings.Builder onScreen(boolean onScreen) {
         this.settings.onScreen = onScreen;
         return this;
      }

      public ShatteredScreenSettings build() {
         return this.settings;
      }
   }
}
