package cn.leolezury.eternalstarlight.common.critereon;

import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.handler.ESCommonHandler;
import cn.leolezury.eternalstarlight.common.registry.ESWeathers;
import cn.leolezury.eternalstarlight.common.weather.AbstractWeather;
import cn.leolezury.eternalstarlight.common.weather.WeatherInstance;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger.SimpleInstance;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class WitnessWeatherTrigger extends SimpleCriterionTrigger<WitnessWeatherTrigger.TriggerInstance> {
   public Codec<WitnessWeatherTrigger.TriggerInstance> codec() {
      return WitnessWeatherTrigger.TriggerInstance.CODEC;
   }

   public void trigger(ServerPlayer serverPlayer) {
      this.trigger(serverPlayer, triggerInstance -> triggerInstance.matches(serverPlayer.serverLevel()));
   }

   public record TriggerInstance(Optional<ContextAwarePredicate> player, Holder<AbstractWeather> weather) implements SimpleInstance {
      public static final Codec<WitnessWeatherTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(WitnessWeatherTrigger.TriggerInstance::player),
               ESWeathers.WEATHERS.registry().holderByNameCodec().fieldOf("weather").forGetter(WitnessWeatherTrigger.TriggerInstance::weather)
            )
            .apply(instance, WitnessWeatherTrigger.TriggerInstance::new)
      );

      public boolean matches(ServerLevel serverLevel) {
         Optional<WeatherInstance> active = ESCommonHandler.getActiveWeather();
         return serverLevel.dimension().location().equals(ESDimensions.STARLIGHT_KEY.location())
            && active.isPresent()
            && this.weather().isBound()
            && active.get().getWeather() == this.weather().value();
      }
   }
}
