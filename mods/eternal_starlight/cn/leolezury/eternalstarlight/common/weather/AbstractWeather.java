package cn.leolezury.eternalstarlight.common.weather;

import cn.leolezury.eternalstarlight.common.registry.ESWeathers;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.IntProvider;

public abstract class AbstractWeather {
   private final AbstractWeather.Properties properties;

   public AbstractWeather.Properties weatherProperties() {
      return this.properties;
   }

   public AbstractWeather(AbstractWeather.Properties properties) {
      this.properties = properties;
   }

   public abstract boolean canStart(ServerLevel var1);

   public abstract boolean canContinue(ServerLevel var1, int var2);

   public abstract void serverTick(ServerLevel var1, int var2);

   public abstract void tickBlock(ServerLevel var1, int var2, BlockPos var3);

   public abstract void onStart(ServerLevel var1);

   public abstract void onStop(ServerLevel var1, int var2);

   public abstract void clientTick();

   public abstract float modifyRainLevel(float var1);

   public String getDescriptionId() {
      return Util.makeDescriptionId("weather", ESWeathers.WEATHERS.registry().getKey(this));
   }

   public Component getDescription() {
      return Component.translatable(this.getDescriptionId());
   }

   public record Properties(IntProvider duration, IntProvider interval) {
   }
}
