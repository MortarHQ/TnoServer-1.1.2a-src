package com.blackgear.vanillabackport.common.registries;

import com.blackgear.platform.core.helper.EntityRegistry;
import com.blackgear.vanillabackport.common.level.entities.happyghast.HappyGhast;
import com.blackgear.vanillabackport.common.level.entities.sensortypes.AdultSensorAnyType;
import java.util.function.Supplier;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;

public class ModSensorTypes {
   public static final EntityRegistry SENSOR_TYPES = EntityRegistry.create("minecraft");
   public static final Supplier<SensorType<AdultSensorAnyType>> NEAREST_ADULT_ANY_TYPE = SENSOR_TYPES.sensor("nearest_adult_any_type", AdultSensorAnyType::new);
   public static final Supplier<SensorType<TemptingSensor>> HAPPY_GHAST_TEMPTATIONS = SENSOR_TYPES.sensor(
      "happy_ghast_temptations", () -> new TemptingSensor(HappyGhast.IS_FOOD)
   );
}
