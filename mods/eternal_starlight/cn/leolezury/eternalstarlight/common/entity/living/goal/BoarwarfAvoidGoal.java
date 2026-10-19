package cn.leolezury.eternalstarlight.common.entity.living.goal;

import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class BoarwarfAvoidGoal extends AvoidEntityGoal<LivingEntity> {
   public BoarwarfAvoidGoal(Boarwarf villager) {
      super(villager, LivingEntity.class, 10.0F, 1.0, 1.0);
   }

   public boolean canUse() {
      if (this.mob instanceof Boarwarf boarwarf) {
         this.toAvoid = boarwarf.getEntityToAvoid(this.maxDist);
         if (this.toAvoid == null) {
            return false;
         }

         Vec3 vec3 = DefaultRandomPos.getPosAway(this.mob, 16, 7, this.toAvoid.position());
         if (vec3 == null) {
            return false;
         }

         if (this.toAvoid.distanceToSqr(vec3.x, vec3.y, vec3.z) < this.toAvoid.distanceToSqr(this.mob)) {
            return false;
         }

         this.path = this.pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
         return this.path != null;
      } else {
         return false;
      }
   }
}
