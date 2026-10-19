package twilightforest.entity.monster;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.entity.projectile.ThrownBlock;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFEntities;
import twilightforest.init.TFSounds;
import twilightforest.util.WorldUtil;

public class Troll extends Monster implements RangedAttackMob {
   private static final EntityDataAccessor<Boolean> ROCK_FLAG = SynchedEntityData.defineId(Troll.class, EntityDataSerializers.BOOLEAN);
   private static final AttributeModifier ROCK_MODIFIER = new AttributeModifier(TwilightForestMod.prefix("rock_follow_boost"), 8.0, Operation.ADD_VALUE);
   private RangedAttackGoal aiArrowAttack;
   private MeleeAttackGoal aiAttackOnCollide;
   private int rockCooldown = 300 + this.getRandom().nextInt(100);
   @Nullable
   private BlockState rock;

   public Troll(EntityType<? extends Troll> type, Level level) {
      super(type, level);
   }

   public void registerGoals() {
      this.aiArrowAttack = new RangedAttackGoal(this, 1.0, 20, 60, 15.0F);
      this.aiAttackOnCollide = new MeleeAttackGoal(this, 1.2, false);
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(2, new RestrictSunGoal(this));
      this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{Troll.class}));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      if (!this.level().isClientSide()) {
         this.setCombatTask();
      }
   }

   public static Builder registerAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 30.0).add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.ATTACK_DAMAGE, 7.0);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide() && !this.hasRock() && this.getTarget() != null) {
         if (this.rockCooldown > 0) {
            this.rockCooldown--;
         } else {
            RandomSource random = this.getRandom();
            Level level = this.level();
            int i = Mth.floor(this.getX() - 2.0 + random.nextDouble() * 4.0);
            int j = Mth.floor(this.getY() + random.nextDouble() * 3.0);
            int k = Mth.floor(this.getZ() - 2.0 + random.nextDouble() * 4.0);
            BlockPos blockpos = new BlockPos(i, j, k);
            BlockState blockstate = level.getBlockState(blockpos);
            Vec3 vec3 = new Vec3(this.getBlockX() + 0.5, j + 0.5, this.getBlockZ() + 0.5);
            Vec3 vec31 = new Vec3(i + 0.5, j + 0.5, k + 0.5);
            BlockHitResult blockhitresult = level.clip(new ClipContext(vec3, vec31, Block.OUTLINE, Fluid.NONE, this));
            boolean flag = blockhitresult.getBlockPos().equals(blockpos);
            if (blockstate.is(BlockTags.BASE_STONE_OVERWORLD) && flag) {
               this.rock = level.getBlockState(blockpos);
               level.removeBlock(blockpos, false);
               level.gameEvent(this, GameEvent.BLOCK_DESTROY, blockpos);
            }

            if (this.rock != null) {
               this.setHasRock(true);
               this.playSound((SoundEvent)TFSounds.TROLL_GRABS_ROCK.get());
               ThrownBlock block = new ThrownBlock(level, this, this.rock);
               block.startRiding(this);
               level.addFreshEntity(block);
            }
         }
      }
   }

   protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float yRot) {
      return new Vec3(0.0, dimensions.height() * 1.25F, 0.0);
   }

   public void positionRider(Entity entity, MoveFunction callback) {
      super.positionRider(entity, callback);
      entity.setYRot(this.yBodyRot);
      entity.yRotO = this.yBodyRotO;
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(ROCK_FLAG, false);
   }

   public boolean hasRock() {
      return (Boolean)this.getEntityData().get(ROCK_FLAG);
   }

   public void setHasRock(boolean rock) {
      this.getEntityData().set(ROCK_FLAG, rock);
      if (!this.level().isClientSide()) {
         if (rock) {
            if (!Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).hasModifier(ROCK_MODIFIER.id())) {
               Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).addTransientModifier(ROCK_MODIFIER);
            }
         } else {
            Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).removeModifier(ROCK_MODIFIER.id());
         }

         this.setCombatTask();
      }
   }

   public boolean doHurtTarget(Entity entity) {
      return super.doHurtTarget(entity);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("HasRock", this.hasRock());
      compound.putInt("RockCooldown", this.rockCooldown);
      if (this.rock != null) {
         compound.put("RockState", NbtUtils.writeBlockState(this.rock));
      }
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setHasRock(compound.getBoolean("HasRock"));
      this.rockCooldown = compound.getInt("RockCooldown");
      this.rock = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), compound.getCompound("RockState"));
   }

   private void setCombatTask() {
      this.goalSelector.removeGoal(this.aiAttackOnCollide);
      this.goalSelector.removeGoal(this.aiArrowAttack);
      if (this.hasRock()) {
         this.goalSelector.addGoal(4, this.aiArrowAttack);
      } else {
         this.goalSelector.addGoal(4, this.aiAttackOnCollide);
      }
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.TROLL_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.TROLL_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.TROLL_DEATH.get();
   }

   protected void tickDeath() {
      super.tickDeath();
      if (this.deathTime % 5 == 0) {
         this.ripenTrollBerNearby(this.deathTime / 5);
      }
   }

   private void ripenTrollBerNearby(int offset) {
      int range = 12;

      for (BlockPos pos : WorldUtil.getAllAround(new BlockPos(this.blockPosition()), range)) {
         this.ripenBer(offset, pos);
      }
   }

   private void ripenBer(int offset, BlockPos pos) {
      if (this.level().getBlockState(pos).getBlock() == TFBlocks.UNRIPE_TROLLBER.get()
         && this.getRandom().nextBoolean()
         && Math.abs(pos.getX() + pos.getY() + pos.getZ()) % 5 == offset) {
         this.level().setBlockAndUpdate(pos, ((net.minecraft.world.level.block.Block)TFBlocks.TROLLBER.get()).defaultBlockState());
         this.level().levelEvent(2004, pos, 0);
      }
   }

   public void performRangedAttack(LivingEntity target, float distanceFactor) {
      if (this.hasRock()) {
         ThrownBlock blocc = new ThrownBlock(this.level(), this, this.rock);
         double d0 = target.getX() - this.getX();
         double d1 = target.getBoundingBox().minY + target.getBbHeight() / 3.0F - blocc.getY();
         double d2 = target.getZ() - this.getZ();
         double d3 = Mth.sqrt((float)(d0 * d0 + d2 * d2));
         blocc.shoot(d0, d1 + d3 * 0.2, d2, 1.6F, 4 - this.level().getDifficulty().getId());
         this.playSound((SoundEvent)TFSounds.TROLL_THROWS_ROCK.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
         this.gameEvent(GameEvent.PROJECTILE_SHOOT);
         this.level().addFreshEntity(blocc);
         this.setHasRock(false);
         if (!this.getPassengers().isEmpty() && Objects.requireNonNull(this.getFirstPassenger()).getType() == TFEntities.THROWN_BLOCK.get()) {
            this.getFirstPassenger().discard();
         }

         this.rockCooldown = 300 + this.getRandom().nextInt(100);
         this.rock = null;
      }
   }
}
