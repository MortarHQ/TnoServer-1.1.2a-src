package net.tslat.smartbrainlib;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import org.jetbrains.annotations.ApiStatus.Internal;

public interface SBLLoader {
   void init(Object var1);

   boolean isDevEnv();

   Pair<Collection<? extends Entity>, Function<Entity, ? extends Entity>> getPartEntities(Level var1);

   @Internal
   <T> Supplier<MemoryModuleType<T>> registerMemoryType(String var1);

   @Internal
   <T> Supplier<MemoryModuleType<T>> registerMemoryType(String var1, Optional<Codec<T>> var2);

   @Internal
   <T extends ExtendedSensor<?>> Supplier<SensorType<T>> registerSensorType(String var1, Supplier<T> var2);
}
