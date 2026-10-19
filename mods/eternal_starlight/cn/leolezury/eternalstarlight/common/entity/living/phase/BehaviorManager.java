package cn.leolezury.eternalstarlight.common.entity.living.phase;

import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.entity.LivingEntity;

public class BehaviorManager<T extends LivingEntity & MultiBehaviorUser> {
   private final T entity;
   private final List<BehaviorPhase<T>> phaseList = new ArrayList<>();
   private final Int2ObjectArrayMap<List<BehaviorPhase<T>>> phases = new Int2ObjectArrayMap();
   private final IntArrayList priorities = new IntArrayList();
   private final Int2IntArrayMap cooldowns = new Int2IntArrayMap();

   public Int2IntArrayMap getCooldowns() {
      return this.cooldowns;
   }

   public BehaviorManager(T entity, List<BehaviorPhase<T>> phaseList) {
      this.entity = entity;
      this.phaseList.addAll(phaseList);

      for (BehaviorPhase<T> phase : phaseList) {
         if (!this.phases.containsKey(phase.getPriority())) {
            this.phases.put(phase.getPriority(), new ArrayList());
         }

         if (!this.priorities.contains(phase.getPriority())) {
            this.priorities.add(phase.getPriority());
         }

         ((List)this.phases.get(phase.getPriority())).add(phase);
      }

      this.priorities.sort((i1, i2) -> i1 - i2);
   }

   public void tick() {
      if (this.entity.getBehaviorState() == 0) {
         this.selectPhase().ifPresent(p -> p.start(this.entity, this));
      } else {
         this.getActivePhase().ifPresent(p -> {
            if (!this.canContinue((BehaviorPhase<T>)p)) {
               p.stop(this.entity, this);
            } else {
               p.tick(this.entity);
               this.entity.setBehaviorTicks(this.entity.getBehaviorTicks() + 1);
            }
         });
      }

      IntIterator var1 = this.cooldowns.keySet().iterator();

      while (var1.hasNext()) {
         int id = (Integer)var1.next();
         this.cooldowns.put(id, Math.max(0, this.cooldowns.get(id) - 1));
      }
   }

   public List<BehaviorPhase<T>> getAllPhases() {
      return this.phaseList;
   }

   private Optional<BehaviorPhase<T>> selectPhase() {
      IntListIterator var1 = this.priorities.iterator();

      while (var1.hasNext()) {
         int priority = (Integer)var1.next();
         List<BehaviorPhase<T>> phasesForPriority = ((List)this.phases.get(priority))
            .stream()
            .filter(p -> p.canStart(this.entity, this.cooldowns.getOrDefault(p.getId(), 0) <= 0))
            .toList();
         if (!phasesForPriority.isEmpty()) {
            return Optional.ofNullable(phasesForPriority.get(this.entity.getRandom().nextInt(phasesForPriority.size())));
         }
      }

      return Optional.empty();
   }

   private Optional<BehaviorPhase<T>> getActivePhase() {
      return this.phaseList.stream().filter(p -> this.entity.getBehaviorState() == p.getId()).findFirst();
   }

   private boolean canContinue(BehaviorPhase<T> phase) {
      return phase.canContinue(this.entity) && this.entity.getBehaviorTicks() <= phase.getDuration();
   }
}
