package cn.leolezury.eternalstarlight.common.entity.living.phase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;

public class MeleeAttackPhase<T extends LivingEntity & MultiBehaviorUser> extends BehaviorPhase<T> {
   private final List<MeleeAttackPhase.Entry> entries = new ArrayList<>();

   public MeleeAttackPhase(int id, int priority, int duration, int cooldown) {
      super(id, priority, duration, cooldown);
   }

   @Override
   public boolean canStart(T entity, boolean cooldownOver) {
      return cooldownOver && !this.entries.isEmpty() && this.canReachTarget(entity, this.entries.getFirst().range());
   }

   @Override
   public void onStart(T entity) {
   }

   @Override
   public void tick(T entity) {
      for (MeleeAttackPhase.Entry entry : this.entries) {
         if (entity.getBehaviorTicks() == entry.tick()) {
            this.performDefaultMeleeAttack(entity, entry.range());
         }
      }
   }

   @Override
   public boolean canContinue(T entity) {
      return true;
   }

   @Override
   public void onStop(T entity) {
   }

   public MeleeAttackPhase<T> with(int range, int tick) {
      this.entries.add(new MeleeAttackPhase.Entry(range, tick));
      this.entries.sort(Comparator.comparingInt(MeleeAttackPhase.Entry::range));
      return this;
   }

   private record Entry(int range, int tick) {
   }
}
