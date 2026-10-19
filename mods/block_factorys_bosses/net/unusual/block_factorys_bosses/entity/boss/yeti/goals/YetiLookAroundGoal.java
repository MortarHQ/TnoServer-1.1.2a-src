package net.unusual.block_factorys_bosses.entity.boss.yeti.goals;

import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.unusual.block_factorys_bosses.entity.boss.yeti.YetiEntity;

public class YetiLookAroundGoal extends Goal {
   private final YetiEntity yeti;
   private double relX;
   private double relZ;
   private int lookTime;

   public YetiLookAroundGoal(YetiEntity yeti) {
      this.yeti = yeti;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      return this.yeti.getEntityData().get(YetiEntity.DATA_IS_ENRAGED) <= 1 ? false : this.yeti.getRandom().nextFloat() < 0.02F;
   }

   public boolean canContinueToUse() {
      return this.lookTime >= 0;
   }

   public void start() {
      double d0 = (Math.PI * 2) * this.yeti.getRandom().nextDouble();
      this.relX = Math.cos(d0);
      this.relZ = Math.sin(d0);
      this.lookTime = 20 + this.yeti.getRandom().nextInt(20);
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   public void tick() {
      this.lookTime--;
      this.yeti.getLookControl().setLookAt(this.yeti.getX() + this.relX, this.yeti.getEyeY(), this.yeti.getZ() + this.relZ);
   }
}
