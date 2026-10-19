package com.blackgear.vanillabackport.common.api.leash;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class QuadLeashRegistry {
   private static final Map<Predicate<Entity>, Function<Entity, Vec3[]>> OFFSETS;

   private QuadLeashRegistry() {
   }

   public static boolean supports(Entity entity) {
      return OFFSETS.keySet().stream().anyMatch(p -> p.test(entity));
   }

   @Nullable
   public static Vec3[] getOffsets(Entity entity) {
      for (Entry<Predicate<Entity>, Function<Entity, Vec3[]>> entry : OFFSETS.entrySet()) {
         if (entry.getKey().test(entity)) {
            return entry.getValue().apply(entity);
         }
      }

      return null;
   }

   public static boolean supportsAsHolder(Entity entity) {
      return false;
   }

   static {
      Builder<Predicate<Entity>, Function<Entity, Vec3[]>> b = ImmutableMap.builder();
      b.put((Predicate<Entity>)e -> e instanceof Boat, (Function<Entity, Vec3[]>)e -> LeashPhysics.createQuadOffsets(e, 0.0, 0.64, 0.382, 0.88));
      b.put((Predicate<Entity>)e -> e instanceof Camel, (Function<Entity, Vec3[]>)e -> LeashPhysics.createQuadOffsets(e, 0.02, 0.48, 0.25, 0.82));
      b.put((Predicate<Entity>)e -> e instanceof AbstractChestedHorse, (Function<Entity, Vec3[]>)e -> LeashPhysics.createQuadOffsets(e, 0.04, 0.41, 0.18, 0.73));
      b.put((Predicate<Entity>)e -> e instanceof AbstractHorse, (Function<Entity, Vec3[]>)e -> LeashPhysics.createQuadOffsets(e, 0.04, 0.52, 0.23, 0.87));
      b.put((Predicate<Entity>)e -> e instanceof Sniffer, (Function<Entity, Vec3[]>)e -> LeashPhysics.createQuadOffsets(e, -0.01, 0.63, 0.38, 1.15));
      OFFSETS = b.build();
   }
}
