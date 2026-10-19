package twilightforest.entity.boss;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;
import twilightforest.entity.IBreathAttacker;
import twilightforest.entity.TFPart;
import twilightforest.entity.ai.goal.HoverBeamGoal;
import twilightforest.entity.ai.goal.HoverSummonGoal;
import twilightforest.entity.ai.goal.HoverThenDropGoal;
import twilightforest.entity.monster.IceCrystal;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFEntities;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStructures;
import twilightforest.util.WorldUtil;
import twilightforest.util.entities.EntityUtil;

public class SnowQueen extends BaseTFBoss implements IBreathAttacker {
   private static final int MAX_SUMMONS = 6;
   private static final EntityDataAccessor<Boolean> BEAM_FLAG = SynchedEntityData.defineId(SnowQueen.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Byte> PHASE_FLAG = SynchedEntityData.defineId(SnowQueen.class, EntityDataSerializers.BYTE);
   private static final int MAX_DAMAGE_WHILE_BEAMING = 25;
   private static final float BREATH_DAMAGE = 4.0F;
   public final SnowQueenIceShield[] iceArray = new SnowQueenIceShield[7];
   private int summonsRemaining = 0;
   private int successfulDrops;
   private int maxDrops;
   private int damageWhileBeaming;

   public SnowQueen(EntityType<? extends SnowQueen> type, Level level) {
      super(type, level);

      for (int i = 0; i < this.iceArray.length; i++) {
         this.iceArray[i] = new SnowQueenIceShield(this);
      }

      this.setCurrentPhase(SnowQueen.Phase.SUMMON);
      this.xpReward = 317;
      this.moveControl = new FlyingMoveControl(this, 10, true);
      this.setNoGravity(true);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new HoverSummonGoal(this));
      this.goalSelector.addGoal(2, new HoverThenDropGoal(this, 80, 20));
      this.goalSelector.addGoal(3, new HoverBeamGoal(this, 80, 100));
      this.addRestrictionGoals(this, this.goalSelector);
      this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 1.0, true));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public boolean isPushable() {
      return false;
   }

   public static Builder registerAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MOVEMENT_SPEED, 0.23)
         .add(Attributes.FLYING_SPEED, 0.23)
         .add(Attributes.ATTACK_DAMAGE, 7.0)
         .add(Attributes.FOLLOW_RANGE, 40.0)
         .add(Attributes.MAX_HEALTH, 200.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.75);
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(BEAM_FLAG, false);
      builder.define(PHASE_FLAG, (byte)0);
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.SNOW_QUEEN_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.SNOW_QUEEN_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.SNOW_QUEEN_DEATH.get();
   }

   public void aiStep() {
      super.aiStep();
      if (this.level().isClientSide()) {
         this.spawnParticles();
      }
   }

   private void spawnParticles() {
      for (int i = 0; i < 3; i++) {
         float px = (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.3F;
         float py = this.getEyeHeight() + (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.5F;
         float pz = (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.3F;
         this.level().addParticle((ParticleOptions)TFParticleType.SNOW_GUARDIAN.get(), this.xOld + px, this.yOld + py, this.zOld + pz, 0.0, 0.0, 0.0);
      }

      if (this.getCurrentPhase() == SnowQueen.Phase.DROP) {
         for (Entity ice : this.iceArray) {
            float px = (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.5F;
            float py = (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.5F;
            float pz = (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.5F;
            this.level().addParticle((ParticleOptions)TFParticleType.SNOW_WARNING.get(), ice.xOld + px, ice.yOld + py, ice.zOld + pz, 0.0, 0.0, 0.0);
         }
      }

      if (this.isBreathing() && this.isAlive()) {
         Vec3 look = this.getLookAngle();
         double dist = 0.5;
         double px = this.getX() + look.x() * dist;
         double py = this.getY() + 1.7F + look.y() * dist;
         double pz = this.getZ() + look.z() * dist;

         for (int i = 0; i < 10; i++) {
            double dx = look.x();
            double dy = 0.0;
            double dz = look.z();
            double spread = 2.0 + this.getRandom().nextDouble() * 2.5;
            double velocity = 2.0 + this.getRandom().nextDouble() * 0.15;
            dx += this.getRandom().nextGaussian() * 0.0075 * spread;
            dy += this.getRandom().nextGaussian() * 0.0075 * spread;
            dz += this.getRandom().nextGaussian() * 0.0075 * spread;
            dx *= velocity;
            dy *= velocity;
            dz *= velocity;
            this.level().addParticle((ParticleOptions)TFParticleType.ICE_BEAM.get(), px, py, pz, dx, dy, dz);
         }
      }
   }

   public void tick() {
      this.setDeltaMovement(this.getDeltaMovement().x(), this.getDeltaMovement().y() - 0.05, this.getDeltaMovement().z());
      super.tick();

      for (int i = 0; i < this.iceArray.length; i++) {
         this.iceArray[i].tick();
         if (i < this.iceArray.length - 1) {
            Vec3 blockPos = this.getIceShieldPosition(i);
            this.iceArray[i].setPos(blockPos.x(), blockPos.y(), blockPos.z());
         } else {
            this.iceArray[i].setPos(this.getX(), this.getY() - 1.0, this.getZ());
         }

         this.iceArray[i].setYRot(this.getIceShieldAngle(i));
         if (!this.level().isClientSide()) {
            this.applyShieldCollisions(this.iceArray[i]);
         }
      }

      if (this.deathTime > 0) {
         for (int k = 0; k < 5; k++) {
            double d = this.getRandom().nextGaussian() * 0.02;
            double d1 = this.getRandom().nextGaussian() * 0.02;
            double d2 = this.getRandom().nextGaussian() * 0.02;
            this.level()
               .addParticle(
                  this.getRandom().nextBoolean() ? ParticleTypes.EXPLOSION : ParticleTypes.POOF,
                  this.getX() + this.getRandom().nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth(),
                  this.getY() + this.getRandom().nextFloat() * this.getBbHeight(),
                  this.getZ() + this.getRandom().nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth(),
                  d,
                  d1,
                  d2
               );
         }
      }
   }

   private void applyShieldCollisions(Entity collider) {
      for (Entity collided : this.level().getEntities(collider, collider.getBoundingBox().inflate(-0.2F, -0.2F, -0.2F))) {
         if (collided.isPushable()) {
            this.applyShieldCollision(collider, collided);
         }
      }
   }

   private void applyShieldCollision(Entity collider, Entity collided) {
      if (collided != this) {
         collided.push(collider);
         if (collided instanceof LivingEntity && this.doHurtTarget(collided)) {
            Vec3 motion = collided.getDeltaMovement();
            collided.setDeltaMovement(motion.x(), motion.y() + 0.4, motion.z());
            this.playSound((SoundEvent)TFSounds.SNOW_QUEEN_ATTACK.get(), 1.0F, 1.0F);
         }
      }
   }

   public boolean doHurtTarget(Entity entity) {
      DamageSource source = this.getCurrentPhase() == SnowQueen.Phase.DROP
         ? TFDamageTypes.getEntityDamageSource(this.level(), TFDamageTypes.SQUISH, this, (EntityType)TFEntities.SNOW_QUEEN.get())
         : this.level().damageSources().mobAttack(this);
      return EntityUtil.properlyApplyCustomDamageSource(this, entity, source, null);
   }

   @Override
   protected void customServerAiStep() {
      super.customServerAiStep();
      if (this.getCurrentPhase() == SnowQueen.Phase.SUMMON && this.getSummonsRemaining() == 0 && this.countMyMinions() <= 0) {
         this.setCurrentPhase(SnowQueen.Phase.DROP);
      }

      if (this.getCurrentPhase() == SnowQueen.Phase.DROP && this.successfulDrops >= this.maxDrops) {
         this.setCurrentPhase(SnowQueen.Phase.BEAM);
      }

      if (this.getCurrentPhase() == SnowQueen.Phase.BEAM && this.damageWhileBeaming >= 25) {
         this.setCurrentPhase(SnowQueen.Phase.SUMMON);
      }
   }

   public boolean hurt(DamageSource source, float damage) {
      boolean result = super.hurt(source, damage);
      if (result && this.getCurrentPhase() == SnowQueen.Phase.BEAM) {
         this.damageWhileBeaming += (int)damage;
      }

      return result;
   }

   private Vec3 getIceShieldPosition(int idx) {
      return this.getIceShieldPosition(this.getIceShieldAngle(idx), 1.0F);
   }

   private float getIceShieldAngle(int idx) {
      return 60.0F * idx + this.tickCount * 5.0F;
   }

   private Vec3 getIceShieldPosition(float angle, float distance) {
      double dx = Math.cos(angle * Math.PI / 180.0) * distance;
      double dz = Math.sin(angle * Math.PI / 180.0) * distance;
      return new Vec3(this.getX() + dx, this.getY() + this.getShieldYOffset(), this.getZ() + dz);
   }

   private double getShieldYOffset() {
      return 0.1F;
   }

   public void destroyBlocksInAABB(AABB box) {
      if (EventHooks.canEntityGrief(this.level(), this)) {
         for (BlockPos pos : WorldUtil.getAllInBB(box)) {
            BlockState state = this.level().getBlockState(pos);
            if (state.is(BlockTags.ICE)) {
               this.level().destroyBlock(pos, false);
               this.gameEvent(GameEvent.BLOCK_DESTROY);
            }
         }
      }
   }

   @Override
   public boolean isBreathing() {
      return (Boolean)this.getEntityData().get(BEAM_FLAG);
   }

   @Override
   public void setBreathing(boolean flag) {
      this.getEntityData().set(BEAM_FLAG, flag);
   }

   public SnowQueen.Phase getCurrentPhase() {
      return SnowQueen.Phase.values()[this.getEntityData().get(PHASE_FLAG)];
   }

   public void setCurrentPhase(SnowQueen.Phase currentPhase) {
      this.getEntityData().set(PHASE_FLAG, (byte)currentPhase.ordinal());
      if (currentPhase == SnowQueen.Phase.SUMMON) {
         this.setSummonsRemaining(6);
      }

      if (currentPhase == SnowQueen.Phase.DROP) {
         this.successfulDrops = 0;
         this.maxDrops = 2 + this.getRandom().nextInt(3);
      }

      if (currentPhase == SnowQueen.Phase.BEAM) {
         this.damageWhileBeaming = 0;
      }
   }

   public int getSummonsRemaining() {
      return this.summonsRemaining;
   }

   public void setSummonsRemaining(int summonsRemaining) {
      this.summonsRemaining = summonsRemaining;
   }

   public void summonMinionAt(LivingEntity targetedEntity) {
      IceCrystal minion = new IceCrystal(this.level());
      minion.absMoveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
      this.level().addFreshEntity(minion);

      for (int i = 0; i < 100; i++) {
         double attemptX;
         double attemptY;
         double attemptZ;
         if (this.isRestrictionPointValid(this.level().dimension())) {
            BlockPos home = this.getRestrictionPoint().pos();
            attemptX = home.getX() + this.getRandom().nextGaussian() * 3.0;
            attemptY = home.getY() + this.getRandom().nextGaussian() * 2.0;
            attemptZ = home.getZ() + this.getRandom().nextGaussian() * 3.0;
         } else {
            attemptX = targetedEntity.getX() + this.getRandom().nextGaussian() * 6.0;
            attemptY = targetedEntity.getY() + this.getRandom().nextGaussian() * 8.0;
            attemptZ = targetedEntity.getZ() + this.getRandom().nextGaussian() * 6.0;
         }

         if (minion.randomTeleport(attemptX, attemptY, attemptZ, true)) {
            this.gameEvent(GameEvent.ENTITY_PLACE, minion);
            break;
         }
      }

      minion.setTarget(targetedEntity);
      minion.setToDieIn30Seconds();
      this.summonsRemaining--;
   }

   public int countMyMinions() {
      return this.level()
         .getEntitiesOfClass(
            IceCrystal.class,
            new AABB(this.getX(), this.getY(), this.getZ(), this.getX() + 1.0, this.getY() + 1.0, this.getZ() + 1.0).inflate(32.0, 16.0, 32.0)
         )
         .size();
   }

   public void incrementSuccessfulDrops() {
      this.successfulDrops++;
   }

   @Override
   public void doBreathAttack(Entity target) {
      target.hurt(TFDamageTypes.getEntityDamageSource(this.level(), TFDamageTypes.CHILLING_BREATH, this, (EntityType)TFEntities.SNOW_QUEEN.get()), 4.0F);
   }

   public boolean isMultipartEntity() {
      return true;
   }

   public void recreateFromPacket(ClientboundAddEntityPacket packet) {
      super.recreateFromPacket(packet);
      TFPart.assignPartIDs(this);
   }

   @Nullable
   public PartEntity<?>[] getParts() {
      return this.iceArray;
   }

   @Override
   public int getHomeRadius() {
      return 20;
   }

   @Override
   public ResourceKey<Structure> getHomeStructure() {
      return TFStructures.AURORA_PALACE;
   }

   @Override
   public Block getDeathContainer(RandomSource random) {
      return (Block)TFBlocks.TWILIGHT_OAK_CHEST.get();
   }

   @Override
   public Block getBossSpawner() {
      return (Block)TFBlocks.SNOW_QUEEN_BOSS_SPAWNER.get();
   }

   @Override
   public int getBossBarColor() {
      return 9236720;
   }

   public enum Phase {
      SUMMON,
      DROP,
      BEAM;
   }
}
