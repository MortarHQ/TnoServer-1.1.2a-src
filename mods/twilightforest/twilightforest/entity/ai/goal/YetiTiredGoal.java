package twilightforest.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import twilightforest.entity.boss.AlphaYeti;
import twilightforest.init.TFSounds;

public class YetiTiredGoal extends Goal {
   private final AlphaYeti yeti;
   private final int tiredDuration;
   private int tiredTimer;

   public YetiTiredGoal(AlphaYeti alpha, int i) {
      this.yeti = alpha;
      this.tiredDuration = i;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
   }

   public boolean canUse() {
      return this.yeti.isTired();
   }

   public boolean canContinueToUse() {
      return this.tiredTimer < this.tiredDuration;
   }

   public boolean isInterruptable() {
      return false;
   }

   public void start() {
      this.tiredTimer = 0;
   }

   public void stop() {
      this.tiredTimer = 0;
      this.yeti.setTired(false);
   }

   public void tick() {
      if (++this.tiredTimer % 10 == 0) {
         this.yeti.playSound((SoundEvent)TFSounds.ALPHA_YETI_PANT.get(), 4.0F, 0.5F + this.yeti.getRandom().nextFloat() * 0.5F);
      }
   }
}
