package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.entity.ai.brain.CopperGolemBrain;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CrabBrain;
import com.faboslav.friendsandfoes.common.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.common.entity.ai.brain.sensor.CopperGolemSpecificSensor;
import com.faboslav.friendsandfoes.common.entity.ai.brain.sensor.GlareSpecificSensor;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;

public final class FriendsAndFoesSensorTypes {
   public static final ResourcefulRegistry<SensorType<?>> SENSOR_TYPES = ResourcefulRegistries.create(BuiltInRegistries.SENSOR_TYPE, "friendsandfoes");
   public static final RegistryEntry<SensorType<TemptingSensor>> COPPER_GOLEM_TEMPTATIONS = SENSOR_TYPES.register(
      "copper_golem_temptations", () -> new SensorType(() -> new TemptingSensor(CopperGolemBrain.getTemptItems()))
   );
   public static final RegistryEntry<SensorType<CopperGolemSpecificSensor>> COPPER_GOLEM_SPECIFIC_SENSOR = SENSOR_TYPES.register(
      "copper_golem_specific_sensor", () -> new SensorType(() -> new CopperGolemSpecificSensor())
   );
   public static final RegistryEntry<SensorType<TemptingSensor>> CRAB_TEMPTATIONS = SENSOR_TYPES.register(
      "crab_temptations", () -> new SensorType(() -> new TemptingSensor(CrabBrain.getTemptations()))
   );
   public static final RegistryEntry<SensorType<TemptingSensor>> GLARE_TEMPTATIONS = SENSOR_TYPES.register(
      "glare_temptations", () -> new SensorType(() -> new TemptingSensor(GlareBrain.getTemptations()))
   );
   public static final RegistryEntry<SensorType<GlareSpecificSensor>> GLARE_SPECIFIC_SENSOR = SENSOR_TYPES.register(
      "glare_specific_sensor", () -> new SensorType(() -> new GlareSpecificSensor())
   );

   private FriendsAndFoesSensorTypes() {
   }
}
