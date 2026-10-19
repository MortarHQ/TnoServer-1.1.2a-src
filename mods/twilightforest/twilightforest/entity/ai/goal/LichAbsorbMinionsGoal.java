package twilightforest.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.gameevent.GameEvent;
import twilightforest.entity.boss.Lich;
import twilightforest.entity.monster.LichMinion;
import twilightforest.init.TFSounds;
import twilightforest.item.LifedrainScepterItem;
import twilightforest.util.entities.EntityUtil;

public class LichAbsorbMinionsGoal extends Goal {
   private final Lich lich;

   public LichAbsorbMinionsGoal(Lich lich) {
      this.lich = lich;
      this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
   }

   public boolean canUse() {
      return this.lich.getHealth() < this.lich.getMaxHealth() / 2.0F && this.lich.countMyMinions() > 0;
   }

   public void start() {
      super.start();
      this.lich.setScepterTime();
   }

   public void stop() {
      super.stop();
      this.lich.resetScepterTime();
   }

   public void tick() {
      if (this.lich.getTeleportInvisibility() <= 0) {
         super.tick();
         if (this.lich.getScepterTimeLeft() <= 0) {
            List<LichMinion> minions = this.lich
               .level()
               .getEntitiesOfClass(LichMinion.class, this.lich.getBoundingBox().inflate(32.0, 16.0, 32.0))
               .stream()
               .filter(m -> m.master == this.lich)
               .toList();
            if (!minions.isEmpty()) {
               LichMinion minion = minions.getFirst();
               minion.discard();
               LifedrainScepterItem.animateTargetShatter((ServerLevel)this.lich.level(), minion);
               SoundEvent deathSound = EntityUtil.getDeathSound(minion);
               if (deathSound != null) {
                  this.lich.level().playSound(null, minion.blockPosition(), deathSound, SoundSource.HOSTILE, 1.0F, minion.getVoicePitch());
               }

               this.lich.playSound((SoundEvent)TFSounds.LICH_POP_MOB.get(), 3.0F, 0.4F + this.lich.getRandom().nextFloat() * 0.2F);
               minion.playSound((SoundEvent)TFSounds.LICH_POP_MOB.get(), 3.0F, 0.4F + this.lich.getRandom().nextFloat() * 0.2F);
               this.lich.makeMagicTrail(minion.getEyePosition(), this.lich.getEyePosition(), 1.0F, 0.5F, 0.5F);
               this.lich.heal(minion.getHealth());
               this.lich.swing(InteractionHand.MAIN_HAND);
               this.lich.setPopCooldown(40);
               this.lich.gameEvent(GameEvent.ENTITY_DIE);
            }
         }
      }
   }
}
