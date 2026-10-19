package twilightforest.entity.ai.goal;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.neoforged.neoforge.network.PacketDistributor;
import twilightforest.components.entity.YetiThrowAttachment;
import twilightforest.data.tags.EntityTagGenerator;
import twilightforest.events.HostileMountEvents;
import twilightforest.init.TFDataAttachments;
import twilightforest.network.MovePlayerPacket;

public class ThrowRiderGoal extends MeleeAttackGoal {
   private int throwTimer;
   private int timeout;
   private int cooldown;

   public ThrowRiderGoal(PathfinderMob creature, double speedIn, boolean useLongMemory) {
      super(creature, speedIn, useLongMemory);
   }

   public boolean canUse() {
      return this.mob.getPassengers().isEmpty()
         && this.mob.getTarget() != null
         && !this.mob.getTarget().getType().is(EntityTypes.BOSSES)
         && ((YetiThrowAttachment)this.mob.getTarget().getData(TFDataAttachments.YETI_THROWING)).getThrowCooldown() <= 0
         && super.canUse();
   }

   public void start() {
      this.throwTimer = 10 + this.mob.getRandom().nextInt(30);
      this.timeout = 80 + this.mob.getRandom().nextInt(40);
      super.start();
   }

   public void tick() {
      this.timeout--;
      if (!this.mob.getPassengers().isEmpty()) {
         this.throwTimer--;
      } else {
         super.tick();
      }
   }

   protected void checkAndPerformAttack(LivingEntity victim) {
      if (this.canPerformAttack(victim) && this.getTicksUntilNextAttack() <= 0 && this.mob.getPassengers().isEmpty() && this.cooldown-- == 0) {
         this.cooldown = 3;
         this.resetAttackCooldown();
         this.mob.swing(InteractionHand.MAIN_HAND);
         if (this.mob.getPassengers().isEmpty()) {
            Entity v = victim.getVehicle();
            if (v == null || !v.getType().is(EntityTagGenerator.RIDES_OBSTRUCT_SNATCHING)) {
               victim.stopRiding();
               victim.startRiding(this.mob, true);
            }
         }
      }
   }

   public void stop() {
      if (!this.mob.getPassengers().isEmpty()) {
         Entity rider = (Entity)this.mob.getPassengers().get(0);
         HostileMountEvents.hostileDismount(rider);
         Vec3 throwVec = new Vec3(this.mob.getLookAngle().x() * 2.0, 0.9, this.mob.getLookAngle().z() * 2.0);
         if (rider instanceof Player player) {
            YetiThrowAttachment attachment = (YetiThrowAttachment)player.getData(TFDataAttachments.YETI_THROWING);
            attachment.setThrown(player, true, this.mob);
            attachment.setThrowVector(throwVec);
            attachment.setThrowCooldown(player, 200);
            player.push(throwVec.x(), throwVec.y(), throwVec.z());
            if (player instanceof ServerPlayer server) {
               PacketDistributor.sendToPlayer(server, new MovePlayerPacket(throwVec.x(), throwVec.y(), throwVec.z()), new CustomPacketPayload[0]);
            }
         } else {
            rider.push(throwVec.x(), throwVec.y(), throwVec.z());
         }
      }

      super.stop();
   }

   public boolean canContinueToUse() {
      return this.throwTimer > 0 && !this.mob.getPassengers().isEmpty() || this.timeout > 0 && super.canContinueToUse() && this.mob.getPassengers().isEmpty();
   }
}
