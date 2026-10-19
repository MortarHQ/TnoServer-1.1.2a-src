package net.tslat.smartbrainlib.example;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.BowAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.AvoidSun;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.AvoidEntity;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.EscapeSun;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.StrafeTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;

public class SBLSkeleton extends Skeleton implements SmartBrainOwner<SBLSkeleton> {
   public SBLSkeleton(EntityType<? extends SBLSkeleton> entityType, Level level) {
      super(entityType, level);
   }

   protected final void registerGoals() {
   }

   public final void reassessWeaponGoal() {
   }

   protected Provider<?> brainProvider() {
      return new SmartBrainProvider(this);
   }

   @Override
   public List<? extends ExtendedSensor<? extends SBLSkeleton>> getSensors() {
      return ObjectArrayList.of(
         new PredicateSensor[]{
            new NearbyPlayersSensor(),
            new NearbyLivingEntitySensor()
               .setPredicate(
                  (target, entity) -> target instanceof Player
                     || target instanceof IronGolem
                     || target instanceof Wolf
                     || target instanceof Turtle turtle && turtle.isBaby() && !turtle.isInWater()
               )
         }
      );
   }

   @Override
   public BrainActivityGroup<? extends SBLSkeleton> getCoreTasks() {
      return BrainActivityGroup.coreTasks(
         new AvoidSun(),
         new EscapeSun<PathfinderMob>().cooldownFor(entity -> 20),
         new AvoidEntity().avoiding(entity -> entity instanceof Wolf),
         new LookAtTarget<Mob>().runFor(entity -> entity.getRandom().nextIntBetweenInclusive(40, 300)),
         new StrafeTarget<PathfinderMob>().stopStrafingWhen(entity -> !isHoldingBow(entity)).startCondition(SBLSkeleton::isHoldingBow),
         new MoveToWalkTarget()
      );
   }

   @Override
   public BrainActivityGroup<? extends SBLSkeleton> getIdleTasks() {
      return BrainActivityGroup.idleTasks(
         new FirstApplicableBehaviour(new TargetOrRetaliate(), new SetPlayerLookTarget(), new SetRandomLookTarget()),
         new OneRandomBehaviour(new SetRandomWalkTarget().speedModifier(1.0F), new Idle<LivingEntity>().runFor(entity -> entity.getRandom().nextInt(30, 60)))
      );
   }

   @Override
   public BrainActivityGroup<? extends SBLSkeleton> getFightTasks() {
      return BrainActivityGroup.fightTasks(
         new InvalidateAttackTarget(),
         new SetWalkTargetToAttackTarget<Mob>()
            .startCondition(
               entity -> !isHoldingBow(entity) && (!entity.level().isDay() || entity.isOnFire() && entity.level().canSeeSky(entity.blockPosition()))
            ),
         new FirstApplicableBehaviour(
            new BowAttack(20).startCondition(x$0 -> isHoldingBow(x$0)),
            new AnimatableMeleeAttack<Mob>(0).whenStarting(entity -> this.setAggressive(true)).whenStopping(entity -> this.setAggressive(false))
         )
      );
   }

   protected void customServerAiStep() {
      this.tickBrain(this);
   }

   private static boolean isHoldingBow(LivingEntity livingEntity) {
      return livingEntity.isHolding(stack -> stack.getItem() instanceof BowItem);
   }
}
