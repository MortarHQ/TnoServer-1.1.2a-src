package twilightforest.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import twilightforest.entity.boss.AlphaYeti;
import twilightforest.entity.projectile.IceBomb;
import twilightforest.init.TFEntities;
import twilightforest.init.TFSounds;

public class YetiRampageGoal extends Goal {
   private final AlphaYeti yeti;
   private int currentTimeOut;
   private int currentDuration;
   private final int maxTantrumTimeOut;
   private final int tantrumDuration;

   public YetiRampageGoal(AlphaYeti yeti, int timeout, int duration) {
      this.yeti = yeti;
      this.currentTimeOut = timeout;
      this.maxTantrumTimeOut = timeout;
      this.tantrumDuration = duration;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
   }

   public boolean canUse() {
      if (this.yeti.getTarget() != null && this.yeti.canRampage()) {
         this.currentTimeOut--;
      }

      return this.currentTimeOut <= 0;
   }

   public void start() {
      this.currentDuration = this.tantrumDuration;
      this.yeti.setRampaging(true);
      this.yeti.playSound((SoundEvent)TFSounds.ALPHA_YETI_ROAR.get(), 4.0F, 0.5F + this.yeti.getRandom().nextFloat() * 0.5F);
      this.yeti.gameEvent(GameEvent.ENTITY_ACTION);
   }

   public boolean canContinueToUse() {
      return this.currentDuration > 0;
   }

   public void tick() {
      this.currentDuration--;
      if (this.yeti.getTarget() != null) {
         this.yeti.getLookControl().setLookAt(this.yeti.getTarget(), 10.0F, this.yeti.getMaxHeadXRot());
      }

      if (this.yeti.onGround()) {
         this.yeti.setDeltaMovement(0.0, 0.4, 0.0);
         this.yeti.gameEvent(GameEvent.HIT_GROUND);
      }

      this.yeti.destroyBlocksInAABB(this.yeti.getBoundingBox().inflate(1.0, 2.0, 1.0).move(0.0, 2.0, 0.0));
      if (this.currentDuration % 10 == 0) {
         this.yeti.makeRandomBlockFall(30, 80);
      }

      if (this.currentDuration % 20 == 0) {
         this.yeti.makeBlockAboveTargetFall();
      }

      if (this.currentDuration < 40 && this.currentDuration % 10 == 0) {
         this.yeti.makeRandomBlockFall(15, 40);
      }

      if (this.currentDuration % 20 == 0) {
         IceBomb ice = new IceBomb((EntityType<? extends IceBomb>)TFEntities.THROWN_ICE.get(), this.yeti.level(), this.yeti);
         Vec3 vec = new Vec3(0.5F + this.yeti.getRandom().nextFloat() * 0.5F, 0.5F + this.yeti.getRandom().nextFloat() * 0.3F, 0.0)
            .yRot(this.yeti.getRandom().nextFloat() * 360.0F);
         ice.shoot(vec.x(), vec.y(), vec.z(), 0.4F + this.yeti.getRandom().nextFloat() * 0.3F, 0.0F);
         this.yeti.playSound((SoundEvent)TFSounds.ALPHA_YETI_ICE.get(), 1.0F, 1.0F / (this.yeti.getRandom().nextFloat() * 0.4F + 0.8F));
         this.yeti.gameEvent(GameEvent.PROJECTILE_SHOOT);
         this.yeti.level().addFreshEntity(ice);
      }
   }

   public void stop() {
      this.currentTimeOut = this.maxTantrumTimeOut;
      this.yeti.setRampaging(false);
      this.yeti.setTired(true);
   }
}
