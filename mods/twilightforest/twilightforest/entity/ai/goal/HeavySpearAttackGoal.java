package twilightforest.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import twilightforest.entity.monster.UpperGoblinKnight;

public class HeavySpearAttackGoal extends Goal {
   private final UpperGoblinKnight entity;

   public HeavySpearAttackGoal(UpperGoblinKnight upperKnight) {
      this.entity = upperKnight;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public void tick() {
      if (this.entity.heavySpearTimer == 25) {
         this.entity.landHeavySpearAttack();
      }
   }

   public boolean canUse() {
      return this.entity.heavySpearTimer > 0
         && this.entity.heavySpearTimer < 60
         && this.entity.getTarget() != null
         && EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE).test(this.entity.getTarget());
   }
}
