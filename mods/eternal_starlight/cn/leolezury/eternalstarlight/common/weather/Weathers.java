package cn.leolezury.eternalstarlight.common.weather;

import cn.leolezury.eternalstarlight.common.registry.ESWeathers;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;

public class Weathers extends SavedData {
   private static final String TAG_WEATHERS = "weathers";
   private final ServerLevel serverLevel;
   private final List<WeatherInstance> weathers = new ArrayList<>();

   public Weathers(ServerLevel serverLevel) {
      this.serverLevel = serverLevel;
      ESWeathers.WEATHERS.registry().forEach(weather -> {
         WeatherInstance instance = new WeatherInstance(serverLevel, weather);
         this.weathers.add(instance);
      });
   }

   public List<WeatherInstance> getWeathers() {
      return this.weathers;
   }

   public Optional<WeatherInstance> getActiveWeather() {
      for (WeatherInstance instance : this.getWeathers()) {
         if (instance.active) {
            return Optional.of(instance);
         }
      }

      return Optional.empty();
   }

   public void setActiveWeather(AbstractWeather weather, int duration) {
      for (WeatherInstance instance : this.getWeathers()) {
         if (!instance.active && instance.getWeather() == weather) {
            instance.start();
            instance.currentDuration = duration;
         }

         if (instance.active && instance.getWeather() != weather) {
            instance.stop();
         }
      }

      this.setDirty();
   }

   public void clearAllWeathers(int duration) {
      for (WeatherInstance instance : this.getWeathers()) {
         instance.stop();
         instance.ticksUntilNext = duration;
      }

      this.setDirty();
   }

   public static Factory<Weathers> factory(ServerLevel serverLevel) {
      return new Factory(() -> new Weathers(serverLevel), (compoundTag, lookup) -> load(serverLevel, compoundTag), null);
   }

   public void tick() {
      List<WeatherInstance> canStartWeathers = new ArrayList<>();
      boolean hasActive = false;

      for (WeatherInstance instance : this.weathers) {
         if (instance.tick()) {
            canStartWeathers.add(instance);
         }

         hasActive = hasActive || instance.active;
      }

      if (!hasActive && !canStartWeathers.isEmpty()) {
         canStartWeathers.get(this.serverLevel.getRandom().nextInt(canStartWeathers.size())).start();
      }

      this.setDirty();
   }

   public static Weathers load(ServerLevel serverLevel, CompoundTag compoundTag) {
      Weathers weathersData = new Weathers(serverLevel);
      if (compoundTag.contains("weathers", 10)) {
         CompoundTag weathersTag = compoundTag.getCompound("weathers");
         ESWeathers.WEATHERS.registry().forEach(weather -> {
            WeatherInstance instance = new WeatherInstance(serverLevel, weather);
            String id = Objects.requireNonNull(ESWeathers.WEATHERS.registry().getKey(weather)).toString();
            if (weathersTag.contains(id, 10)) {
               instance.load(weathersTag.getCompound(id));
            }

            weathersData.weathers.add(instance);
         });
      }

      return weathersData;
   }

   public CompoundTag save(CompoundTag compoundTag, Provider provider) {
      CompoundTag weathersTag = new CompoundTag();

      for (WeatherInstance instance : this.weathers) {
         String id = Objects.requireNonNull(ESWeathers.WEATHERS.registry().getKey(instance.getWeather())).toString();
         CompoundTag weatherTag = new CompoundTag();
         instance.save(weatherTag);
         weathersTag.put(id, weatherTag);
      }

      compoundTag.put("weathers", weathersTag);
      return compoundTag;
   }
}
