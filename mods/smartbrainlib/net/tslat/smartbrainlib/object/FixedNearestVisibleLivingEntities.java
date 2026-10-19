package net.tslat.smartbrainlib.object;

import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.tslat.smartbrainlib.util.SensoryUtils;

public class FixedNearestVisibleLivingEntities extends NearestVisibleLivingEntities {
   private FixedNearestVisibleLivingEntities() {
   }

   public FixedNearestVisibleLivingEntities(final LivingEntity entity, final List<LivingEntity> entities) {
      this.nearbyEntities = entities;
      this.lineOfSightTest = new Predicate<LivingEntity>() {
         final Object2BooleanOpenHashMap<LivingEntity> cache = new Object2BooleanOpenHashMap(entities.size());

         public boolean test(LivingEntity target) {
            return this.cache.computeIfAbsent(target, target1 -> SensoryUtils.isEntityTargetable(entity, target1));
         }
      };
   }

   public static FixedNearestVisibleLivingEntities empty() {
      return new FixedNearestVisibleLivingEntities();
   }
}
