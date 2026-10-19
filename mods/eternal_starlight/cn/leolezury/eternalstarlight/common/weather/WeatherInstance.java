package cn.leolezury.eternalstarlight.common.weather;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;

public class WeatherInstance {
   private static final String TAG_ACTIVE = "active";
   private static final String TAG_WEATHER_TICKS = "weather_ticks";
   private static final String TAG_DURATION = "duration";
   private static final String TAG_INTERVAL_TICKS = "interval_ticks";
   private static final String TAG_INTERVAL = "interval";
   private final ServerLevel serverLevel;
   private final AbstractWeather weather;
   public boolean active;
   public int ticksSinceStarted;
   public int currentDuration;
   public int ticksUntilNext;
   public int currentInterval;

   public AbstractWeather getWeather() {
      return this.weather;
   }

   public WeatherInstance(ServerLevel serverLevel, AbstractWeather weather) {
      this.serverLevel = serverLevel;
      this.weather = weather;
      this.active = false;
      this.ticksUntilNext = this.currentInterval = weather.weatherProperties().interval().sample(serverLevel.getRandom());
   }

   public void load(CompoundTag compoundTag) {
      this.active = compoundTag.getBoolean("active");
      this.ticksSinceStarted = compoundTag.getInt("weather_ticks");
      this.currentDuration = compoundTag.getInt("duration");
      this.ticksUntilNext = compoundTag.getInt("interval_ticks");
      this.currentInterval = compoundTag.getInt("interval");
   }

   public void save(CompoundTag compoundTag) {
      compoundTag.putBoolean("active", this.active);
      compoundTag.putInt("weather_ticks", this.ticksSinceStarted);
      compoundTag.putInt("duration", this.currentDuration);
      compoundTag.putInt("interval_ticks", this.ticksUntilNext);
      compoundTag.putInt("interval", this.currentInterval);
   }

   public void start() {
      this.weather.onStart(this.serverLevel);
      this.active = true;
      this.ticksSinceStarted = 0;
      this.ticksUntilNext = 0;
      this.currentDuration = this.weather.weatherProperties().duration().sample(this.serverLevel.getRandom());
   }

   public void stop() {
      this.weather.onStop(this.serverLevel, this.ticksSinceStarted);
      this.active = false;
      this.ticksSinceStarted = 0;
      this.ticksUntilNext = this.currentInterval = this.weather.weatherProperties().interval().sample(this.serverLevel.getRandom());
   }

   public boolean tick() {
      if (this.active) {
         this.ticksSinceStarted++;
         this.ticksUntilNext = 0;
         if (this.ticksSinceStarted <= this.currentDuration && this.weather.canContinue(this.serverLevel, this.ticksSinceStarted)) {
            this.weather.serverTick(this.serverLevel, this.ticksSinceStarted);
         } else {
            this.stop();
         }
      } else {
         this.ticksUntilNext--;
         if (this.ticksUntilNext <= 0) {
            this.ticksSinceStarted = 0;
            this.ticksUntilNext = 0;
            return this.weather.canStart(this.serverLevel);
         }
      }

      return false;
   }
}
