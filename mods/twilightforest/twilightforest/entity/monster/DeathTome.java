package twilightforest.entity.monster;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.Nullable;
import twilightforest.entity.projectile.TomeBolt;
import twilightforest.init.TFEntities;
import twilightforest.init.TFSounds;
import twilightforest.loot.TFLootTables;

public class DeathTome extends Monster implements RangedAttackMob {
   private static final EntityDataAccessor<Boolean> DATA_LECTERN = SynchedEntityData.defineId(DeathTome.class, EntityDataSerializers.BOOLEAN);
   public float flip;
   public float oFlip;
   public float flipT;
   public float flipA;

   public DeathTome(EntityType<? extends DeathTome> type, Level world) {
      super(type, world);
      this.moveControl = new FlyingMoveControl(this, 10, false);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(4, new RangedAttackGoal(this, 1.0, 100, 5.0F));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomFlyingGoal(this, 1.0));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector
         .addGoal(
            2,
            new NearestAttackableTargetGoal<Player>(this, Player.class, true) {
               protected void findTarget() {
                  if (this.mob instanceof DeathTome tome && tome.isOnLectern()) {
                     this.target = tome.level()
                        .getNearestPlayer(
                           tome.getX(),
                           tome.getY(),
                           tome.getZ(),
                           3.0,
                           entity -> entity instanceof Player player
                              && !player.isShiftKeyDown()
                              && tome.isInPlayersView(
                                 player, 0.25, false, true, tome.getEyeY(), tome.getY() + 0.5 * tome.getScale(), (tome.getEyeY() + tome.getY()) / 2.0
                              )
                              && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(player)
                        );
                  } else {
                     super.findTarget();
                  }
               }
            }
         );
   }

   public static Builder registerAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 20.0)
         .add(Attributes.MOVEMENT_SPEED, 0.25)
         .add(Attributes.FLYING_SPEED, 0.6)
         .add(Attributes.ATTACK_DAMAGE, 4.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_LECTERN, false);
   }

   public void setOnLectern(boolean hidden) {
      this.getEntityData().set(DATA_LECTERN, hidden);
   }

   public boolean isOnLectern() {
      return (Boolean)this.getEntityData().get(DATA_LECTERN);
   }

   protected PathNavigation createNavigation(Level level) {
      FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, level) {
         public boolean isStableDestination(BlockPos pos) {
            return !this.level.getBlockState(pos.below()).isAir();
         }
      };
      flyingpathnavigation.setCanOpenDoors(false);
      flyingpathnavigation.setCanFloat(false);
      flyingpathnavigation.setCanPassDoors(true);
      return flyingpathnavigation;
   }

   public boolean isPersistenceRequired() {
      return super.isPersistenceRequired() || this.isOnLectern();
   }

   public void aiStep() {
      if (this.isOnLectern()) {
         BlockState state = this.level().getBlockState(this.blockPosition());
         if (state.getBlock() instanceof LecternBlock) {
            Direction direction = (Direction)state.getValue(HorizontalDirectionalBlock.FACING);
            this.yRotO = direction.toYRot();
            this.setYRot(this.yRotO);
            this.yBodyRot = this.yRotO;
            this.yBodyRotO = this.yRotO;
            this.yHeadRot = this.yRotO;
            this.yHeadRotO = this.yRotO;
            if (!this.level().isClientSide()) {
               this.targetSelector.tick();
            }

            if (this.getTarget() != null) {
               this.setOnLectern(false);
               this.setDeltaMovement(0.0, 0.25, 0.0);
               this.performRangedAttack(this.getTarget(), 1.0F);
            }

            return;
         }

         this.setOnLectern(false);
      }

      super.aiStep();
      Vec3 vel = this.getDeltaMovement();
      if (!this.onGround() && vel.y() < 0.0) {
         this.setDeltaMovement(vel.multiply(1.0, 0.6, 1.0));
      }

      for (int i = 0; i < 1; i++) {
         this.level()
            .addParticle(
               ParticleTypes.ENCHANT,
               this.getX() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth(),
               this.getY() + this.getRandom().nextDouble() * (this.getBbHeight() - 0.75) + 0.5,
               this.getZ() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth(),
               0.0,
               0.5,
               0.0
            );
      }
   }

   private boolean isInPlayersView(LivingEntity target, double tolerance, boolean scaleByDistance, boolean visual, double... yValues) {
      Vec3 vec3 = target.getViewVector(1.0F).normalize();

      for (double d0 : yValues) {
         Vec3 vec31 = new Vec3(this.getX() - target.getX(), d0 - target.getEyeY(), this.getZ() - target.getZ());
         double d1 = vec31.length();
         vec31 = vec31.normalize();
         double d2 = vec3.dot(vec31);
         if (d2 > 1.0 - tolerance / (scaleByDistance ? d1 : 1.0) && this.hasLineOfSight(target, visual ? Block.VISUAL : Block.COLLIDER, Fluid.NONE, d0)) {
            return true;
         }
      }

      return false;
   }

   public boolean hasLineOfSight(Entity entity, Block block, Fluid fluid, double eyeHeight) {
      if (entity.level() != this.level()) {
         return false;
      }

      Vec3 vec3 = new Vec3(this.getX(), eyeHeight, this.getZ());
      Vec3 vec31 = new Vec3(entity.getX(), entity.getEyeY(), entity.getZ());
      return !(vec31.distanceTo(vec3) > 128.0) && this.level().clip(new ClipContext(vec3, vec31, block, fluid, this)).getType() == Type.MISS;
   }

   public void tick() {
      if (this.isOnLectern()) {
         this.ambientSoundTime = -1;
      }

      if (this.level().isClientSide) {
         float f1 = this.flipT;
         if (this.random.nextInt(this.isOnLectern() ? 120 : 30) == 0) {
            do {
               this.flipT = this.flipT + (this.random.nextInt(4) - this.random.nextInt(4));
            } while (f1 == this.flipT);
         }

         this.oFlip = this.flip;
         float f = (this.flipT - this.flip) * 0.4F;
         f = Mth.clamp(f, -0.2F, 0.2F);
         this.flipA = this.flipA + (f - this.flipA) * 0.9F;
         this.flip = this.flip + this.flipA;
      }

      super.tick();
   }

   public boolean hurt(DamageSource src, float damage) {
      if (src.is(DamageTypeTags.IS_FIRE)) {
         damage *= 2.0F;
      }

      if (super.hurt(src, damage)) {
         if (damage > 0.0F) {
            if (this.isOnLectern()) {
               this.setDeltaMovement(0.0, 0.25, 0.0);
               this.setOnLectern(false);
            }

            if (!this.level().isClientSide()) {
               LootParams ctx = TFLootTables.createLootParams(this, true, src).create(LootContextParamSets.ENTITY);
               Objects.requireNonNull(this.level().getServer())
                  .reloadableRegistries()
                  .getLootTable(TFLootTables.DEATH_TOME_HURT)
                  .getRandomItems(ctx, s -> this.spawnAtLocation(s, 1.0F));
            }
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean canRide(Entity entity) {
      return false;
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.DEATH_TOME_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.DEATH_TOME_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.DEATH_TOME_DEATH.get();
   }

   public void performRangedAttack(LivingEntity target, float distanceFactor) {
      ThrowableProjectile projectile = new TomeBolt((EntityType<? extends TomeBolt>)TFEntities.TOME_BOLT.get(), this.level(), this);
      double tx = target.getX() - this.getX();
      double ty = target.getY() + target.getEyeHeight() - 1.1 - projectile.getY();
      double tz = target.getZ() - this.getZ();
      float heightOffset = Mth.sqrt((float)(tx * tx + tz * tz)) * 0.2F;
      projectile.shoot(tx, ty + heightOffset, tz, 0.6F, 6.0F);
      this.gameEvent(GameEvent.PROJECTILE_SHOOT);
      this.level().addFreshEntity(projectile);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.entityData.set(DATA_LECTERN, tag.getBoolean("on_lectern"));
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putBoolean("on_lectern", (Boolean)this.entityData.get(DATA_LECTERN));
   }

   protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
   }
}
