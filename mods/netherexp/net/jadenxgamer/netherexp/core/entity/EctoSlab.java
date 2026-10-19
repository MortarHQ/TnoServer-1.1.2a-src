package net.jadenxgamer.netherexp.core.entity;

import java.awt.Color;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import net.jadenxgamer.elysium_api.api.util.LookupRegistryHelper;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.block.PetrifiedSwirlsBlock;
import net.jadenxgamer.netherexp.core.block.entity.PetrifiedSwirlsBlockEntity;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNECriteriaTriggers;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.AdvancementGranter;
import net.jadenxgamer.netherexp.util.CommonParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions.ParticleSpritePicker;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.behaviors.DirectionalParticleBehavior;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance.ScreenshakePositionData;

public class EctoSlab extends PossessedMob {
   private static final ResourceLocation STACK_SPEED_MODIFIER_ID = NetherExp.netherexpPath("stack_speed");
   private static final ResourceLocation STACK_KNOCKBACK_RESISTANCE_MODIFIER_ID = NetherExp.netherexpPath("stack_knockback_resistance");
   private static final ResourceLocation STACK_DAMAGE_MODIFIER_ID = NetherExp.netherexpPath("stack_damage");
   private static final ResourceLocation STACK_JUMP_STRENGTH_MODIFIER_ID = NetherExp.netherexpPath("stack_jump_strength");
   public final AnimationState idleAnimation = new AnimationState();
   public final AnimationState idleMirroredAnimation = new AnimationState();
   public final AnimationState idleBurrowedAnimation = new AnimationState();
   public final AnimationState idlePetrifiedAnimation = new AnimationState();
   public final AnimationState petrifiedHitAnimation = new AnimationState();
   public final AnimationState burrowAnimation = new AnimationState();
   public final AnimationState emergeAnimation = new AnimationState();
   private int maxStackSize = (Integer)JNEConfigs.ECTO_SLAB_MAX_STACK_SIZE.get();
   private double disturbed = 0.0;
   private boolean wasOnGround;
   private int burrowedCooldown = 40;
   private boolean lookingToStack = false;
   private boolean wasForcedOut = false;
   private static final EntityDimensions BURROWED_DIMENSIONS = EntityDimensions.scalable(1.375F, 0.2F);
   private static final EntityDataAccessor<Boolean> IS_BURROWED = SynchedEntityData.defineId(EctoSlab.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> IS_PETRIFIED = SynchedEntityData.defineId(EctoSlab.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> STACK_SIZE = SynchedEntityData.defineId(EctoSlab.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> STACK_COOLDOWN = SynchedEntityData.defineId(EctoSlab.class, EntityDataSerializers.INT);
   private int burrowAnimationAnimationTimer = 20;
   private int emergeAnimationAnimationTimer = 15;
   private boolean belowGroundAnimation = false;
   private boolean aboveGroundAnimation = true;
   public boolean showLight = false;
   public float currentShadowRadius = 0.8F;

   public EctoSlab(EntityType<? extends PathfinderMob> entityType, Level level) {
      super(entityType, level, NetherExp.minecraftPath("magma_cube"));
      this.setPathfindingMalus(PathType.WATER, -1.0F);
      this.setPathfindingMalus(PathType.LAVA, 8.0F);
      this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0F);
      this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0.0F);
      this.moveControl = new EctoSlab.EctoSlabMoveControl(this);
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 15.0)
         .add(Attributes.ATTACK_DAMAGE, 4.0)
         .add(Attributes.MOVEMENT_SPEED, 0.4)
         .add(Attributes.JUMP_STRENGTH, 0.5)
         .add(Attributes.FOLLOW_RANGE, 28.0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new EctoSlab.EctoSlabPetrifiedGoal(this));
      this.goalSelector.addGoal(1, new EctoSlab.EctoSlabFloatGoal(this));
      this.goalSelector.addGoal(2, new EctoSlab.EctoSlabStackGoal(this));
      this.goalSelector.addGoal(3, new EctoSlab.EctoSlabAttackGoal(this));
      this.goalSelector.addGoal(4, new EctoSlab.EctoSlabRandomDirectionGoal(this));
      this.goalSelector.addGoal(5, new EctoSlab.EctoSlabKeepOnJumpingGoal(this));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Piglin.class, true));
      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, IronGolem.class, true));
   }

   public boolean canBeCollidedWith() {
      return this.isPetrified();
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide) {
         this.setupAnimationStates();
      }

      if (this.isPetrified()) {
         this.setYHeadRot(0.0F);
         this.setYRot(0.0F);
         this.setYBodyRot(0.0F);
      } else {
         if (!this.isBurrowed() && this.onGround() && !this.wasOnGround) {
            float f = this.getDimensions(this.getPose()).width() * 2.0F;
            float f1 = f / 2.0F;
            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);

            for (int i = 0; i < f * 6.0F; i++) {
               float f2 = this.random.nextFloat() * (float) (Math.PI * 2);
               float f3 = this.random.nextFloat() * 0.5F + 0.5F;
               float f4 = Mth.sin(f2) * f1 * f3;
               float f5 = Mth.cos(f2) * f1 * f3;
               this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + f4, this.getY(), this.getZ() + f5, 0.0, 0.0, 0.0);
            }
         }

         this.wasOnGround = this.onGround();
      }
   }

   @Override
   public void aiStep() {
      if (!this.level().isClientSide()) {
         if (this.getStackCooldown() > 0) {
            this.setStackCooldown(this.getStackCooldown() - 1);
         }

         if (this.isPetrified()) {
            if (this.disturbed >= ((Integer)JNEConfigs.PETRIFIED_ECTO_SLAB_SHATTER_DISTURBANCE.get()).intValue()) {
               this.splitIntoIndividuals(false);
            }

            if (this.disturbed > 0.0 && this.level().getGameTime() % 40L == 0L) {
               this.disturbed--;
            }
         }
      }

      super.aiStep();
   }

   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if ((Boolean)JNEConfigs.ECTO_SLAB_PETRIFY_WITH_GHAST_TEAR.get() && !this.isPetrified() && !this.isBurrowed() && stack.is(Items.GHAST_TEAR)) {
         this.setPetrified(true);
         stack.shrink(1);
         this.playSound(SoundEvents.HONEYCOMB_WAX_ON, 1.0F, 1.0F);
         if (this.level().isClientSide()) {
            EctoSlab.Client.petrificationParticle(this, this.level(), this.random);
         }

         return InteractionResult.SUCCESS;
      } else {
         return super.mobInteract(player, hand);
      }
   }

   protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
   }

   public void jumpFromGround() {
      Vec3 vec3 = this.getDeltaMovement();
      this.setDeltaMovement(vec3.x, this.getJumpPower(), vec3.z);
      this.hasImpulse = true;
      CommonHooks.onLivingJump(this);
   }

   @Override
   public boolean hurt(DamageSource source, float amount) {
      if (source.getDirectEntity() instanceof ThrownPotion potion && this.hurtWithCleanWater(potion)) {
         this.doExorcism();
      }

      if (this.isPetrified()) {
         if (source.is(DamageTypes.IN_WALL)) {
            return false;
         }

         this.disturbed += amount;
         this.level().broadcastEntityEvent(this, (byte)89);
         amount = 0.0F;
      }

      if (this.isBurrowed()) {
         if ((Boolean)JNEConfigs.ECTO_SLAB_FORCE_OUT_WITH_SHOVEL.get() && this.isShovelHit(source)) {
            this.playSound(SoundEvents.SHOVEL_FLATTEN, 1.0F, 1.0F);
            this.wasForcedOut = true;
         }

         if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
         }
      }

      if ((Boolean)JNEConfigs.EXPLOSION_SHATTERS_ECTO_SLAB_STACK.get() && source.is(DamageTypeTags.IS_EXPLOSION) && this.getStackSize() > 1) {
         this.splitIntoIndividuals(true);
         return false;
      }

      if (source.getEntity() instanceof Player player && player.getMainHandItem().is(JNETags.Items.SILVER_WEAPONS)) {
         amount = (float)(amount * (Double)JNEConfigs.SILVER_PARANORMAL_DAMAGE_MULTIPLIER.get());
         if ((Boolean)JNEConfigs.SILVER_PARANORMAL_INFLICTS_SLOWNESS.get()) {
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 4));
         }

         this.level().broadcastEntityEvent(this, (byte)91);
      }

      return super.hurt(source, amount);
   }

   @Override
   public void die(DamageSource source) {
      if (this.getStackSize() > 1) {
         this.setStackSize(this.getStackSize() - 1);
         this.setHealth(this.getMaxHealth());
         this.level().playSound(null, this.blockPosition(), this.getDeathSound(), this.getSoundSource(), 1.0F, 1.0F);
         this.level().broadcastEntityEvent(this, (byte)86);
      } else {
         super.die(source);
      }
   }

   public boolean isPushable() {
      return !this.isBurrowed() && !this.isPetrified() && super.isPushable();
   }

   protected void pushEntities() {
      if (!this.isBurrowed() && !this.isPetrified()) {
         super.pushEntities();
      }
   }

   public void push(Entity entity) {
      if (!this.isBurrowed() && !this.isPetrified()) {
         super.push(entity);
         if (entity instanceof Piglin || entity instanceof IronGolem) {
            this.dealDamage((LivingEntity)entity);
         }
      }
   }

   public void playerTouch(Player entity) {
      if (!this.isBurrowed()) {
         this.dealDamage(entity);
      }
   }

   protected void dealDamage(LivingEntity victim) {
      if (!this.isPetrified()) {
         if (this.isAlive() && this.isWithinMeleeAttackRange(victim) && this.hasLineOfSight(victim)) {
            DamageSource source = this.damageSources().mobAttack(this);
            if (victim.hurt(source, this.getAttackDamage())) {
               this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
               if (this.level() instanceof ServerLevel serverlevel) {
                  EnchantmentHelper.doPostAttackEffects(serverlevel, victim, source);
               }
            }
         }
      }
   }

   private void doEmergenceBurst(boolean wasForcedOut) {
      if (this.level() instanceof ServerLevel serverLevel) {
         AABB var9 = this.getBoundingBox().inflate(1.2);
         List targets = this.level().getEntitiesOfClass(LivingEntity.class, var9, victimx -> victimx != this && victimx.isAlive());
         float damage = this.getAttackDamage() * ((Double)JNEConfigs.EMERGE_BURST_DAMAGE_MULTIPLIER.get()).floatValue();
         DamageSource source = this.damageSources().mobAttack(this);

         for (LivingEntity victim : targets) {
            if (!(victim instanceof EctoSlab)
               && (
                  wasForcedOut
                     || (Boolean)JNEConfigs.FORCED_OUT_ECTO_SLAB_FRIENDLY_FIRE.get()
                     || victim.getType().is(JNETags.EntityTypes.AFFECTED_BY_ECTO_SLAB_EMERGE_BURST)
               )
               && victim.hurt(source, damage)) {
               this.playSound(JNESoundEvents.ECTOPLASM_FREEZE.get(), 1.0F, 1.0F);
               EnchantmentHelper.doPostAttackEffects(serverLevel, victim, source);
            }
         }

         if ((Boolean)JNEConfigs.ECTO_SLAB_EMERGE_BURST_SCREENSHAKE.get()) {
            ScreenshakeHandler.addScreenshake(
               new ScreenshakeInstance(
                  10,
                  1.0F + this.getStackSize() / 2.0F,
                  0.0F,
                  0.0F,
                  Easing.LINEAR,
                  Easing.LINEAR,
                  1.0F,
                  Optional.of(new ScreenshakePositionData(this.position(), 6.0F, Easing.LINEAR))
               )
            );
         }

         this.wasForcedOut = false;
      }
   }

   private boolean isShovelHit(DamageSource source) {
      return source.getEntity() instanceof LivingEntity attacker
         && (attacker.getMainHandItem().canPerformAction(ItemAbilities.SHOVEL_DIG) || attacker.getMainHandItem().is(ItemTags.SHOVELS));
   }

   private void splitIntoIndividuals(boolean explosion) {
      if (this.level() instanceof ServerLevel) {
         int currentStack = this.getStackSize();

         for (int i = 0; i < currentStack; i++) {
            EctoSlab piece = new EctoSlab(JNEEntityType.ECTO_SLAB.get(), this.level());
            piece.setPos(this.getX(), this.getY() + i, this.getZ());
            piece.setStackSize(1);
            piece.setStackCooldown(explosion ? (Integer)JNEConfigs.EXPLOSION_SHATTER_COOLDOWN.get() : 20);
            piece.setMaxStackSize(this.getMaxStackSize());
            piece.setHealth(piece.getMaxHealth());
            piece.setNoAi(this.isNoAi());
            piece.setCustomName(this.getCustomName());
            if (this.isPersistenceRequired()) {
               piece.setPersistenceRequired();
            }

            RandomSource random = this.random;
            double vx;
            double vy;
            double vz;
            if (explosion) {
               double angle = random.nextDouble() * 2.0 * Math.PI;
               double speed = 0.3 + random.nextDouble() * 0.5;
               vx = Math.cos(angle) * speed;
               vz = Math.sin(angle) * speed;
               vy = 0.2 + random.nextDouble() * 0.4;
            } else {
               double angle = random.nextDouble() * 2.0 * Math.PI;
               double speed = 0.05 + random.nextDouble() * 0.01;
               vx = Math.cos(angle) * speed;
               vz = Math.sin(angle) * speed;
               vy = 0.05 + random.nextDouble() * 0.05;
            }

            piece.setDeltaMovement(vx, vy, vz);
            piece.hasImpulse = true;
            this.level().addFreshEntity(piece);
         }

         this.level().broadcastEntityEvent(this, (byte)88);
         this.playSound(JNESoundEvents.ECTO_SLAB_COLLAPSE.get(), 1.0F, 1.0F);
         if (!explosion) {
            AdvancementGranter.grantPlayersInRadius(this.level(), this.blockPosition(), JNECriteriaTriggers.DISMANTLE_PETRIFIED_ECTO_SLAB);
         }

         this.discard();
      }
   }

   private void petrifyNearbySwirls(int radius) {
      if ((Boolean)JNEConfigs.PETRIFICATION_PETRIFIES_SWIRLS.get()) {
         if (this.level() instanceof ServerLevel serverLevel) {
            BlockPos var12 = this.blockPosition();
            MutableBlockPos mutablePos = new MutableBlockPos();
            this.level().broadcastEntityEvent(this, (byte)120);
            this.playSound(JNESoundEvents.ECTO_SLAB_PETRIFY_SWIRLS.get(), 2.0F, 1.0F);
            if ((Boolean)JNEConfigs.ECTO_SLAB_PETRIFICATION_SCREENSHAKE.get()) {
               ScreenshakeHandler.addScreenshake(
                  new ScreenshakeInstance(
                     20,
                     1.0F,
                     0.0F,
                     0.0F,
                     Easing.LINEAR,
                     Easing.LINEAR,
                     1.0F,
                     Optional.of(new ScreenshakePositionData(this.position(), this.getPetrificationRadius() / 2.0F, Easing.LINEAR))
                  )
               );
            }

            for (int dx = -radius; dx <= radius; dx++) {
               for (int dy = -radius; dy <= radius; dy++) {
                  for (int dz = -radius; dz <= radius; dz++) {
                     mutablePos.set(var12.getX() + dx, var12.getY() + dy, var12.getZ() + dz);
                     BlockState oldState = serverLevel.getBlockState(mutablePos);
                     if (oldState.is(JNETags.Blocks.SOUL_SWIRLS)) {
                        BlockState newState = (BlockState)JNEBlocks.PETRIFIED_SWIRLS.get().defaultBlockState().setValue(PetrifiedSwirlsBlock.SALTED, false);
                        if (newState.hasProperty(BlockStateProperties.FACING) && oldState.hasProperty(BlockStateProperties.FACING)) {
                           newState = (BlockState)newState.setValue(BlockStateProperties.FACING, (Direction)oldState.getValue(BlockStateProperties.FACING));
                        }

                        if (newState.hasProperty(BlockStateProperties.WATERLOGGED) && oldState.hasProperty(BlockStateProperties.WATERLOGGED)) {
                           newState = (BlockState)newState.setValue(
                              BlockStateProperties.WATERLOGGED, (Boolean)oldState.getValue(BlockStateProperties.WATERLOGGED)
                           );
                        }

                        serverLevel.setBlock(mutablePos, newState, 3);
                        this.level().playSound(null, mutablePos, JNESoundEvents.SOUL_SWIRLS_PETRIFY.get(), SoundSource.BLOCKS, 0.2F, 1.0F);
                        if (serverLevel.getBlockEntity(mutablePos) instanceof PetrifiedSwirlsBlockEntity blockEntity) {
                           blockEntity.setPetrifier(this);
                           blockEntity.setUnpetrifiedBlock(BuiltInRegistries.BLOCK.getKey(oldState.getBlock()));
                        }

                        BlockPos pos = mutablePos.immutable();
                        serverLevel.getServer().execute(() -> {
                           if (serverLevel.getBlockState(pos).is(JNEBlocks.PETRIFIED_SWIRLS.get())) {
                              serverLevel.blockEvent(pos, JNEBlocks.PETRIFIED_SWIRLS.get(), 1, 0);
                           }
                        });
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void doExorcism() {
      if (!this.isDeadOrDying()) {
         BlockPos pos = this.blockPosition();
         this.level().playSound(null, pos, JNESoundEvents.APPARITION_DEATH.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
         this.level().broadcastEntityEvent(this, (byte)92);
         EntityType<?> possessionOf = this.getPossessionOf() == null
            ? null
            : LookupRegistryHelper.getEntityType(ResourceLocation.parse(this.getPossessionOf()));
         if (possessionOf == null) {
            this.discard();
         } else {
            EntityType<? extends Mob> possessionType = (EntityType<? extends Mob>)possessionOf;
            Mob convertTo = this.convertTo(possessionType, true);
            boolean slimeType = convertTo instanceof Slime;
            if (this.level() instanceof ServerLevel serverLevel) {
               if (slimeType) {
                  int stack = this.getStackSize();

                  for (int i = 0; i < stack / 3; i++) {
                     this.spawnSlimeOfSize(serverLevel, possessionType, 4);
                  }

                  for (int i = 0; i < stack % 3; i++) {
                     this.spawnSlimeOfSize(serverLevel, possessionType, 2);
                  }

                  this.discard();
               } else if (convertTo != null) {
                  convertTo.finalizeSpawn(serverLevel, this.level().getCurrentDifficultyAt(pos), MobSpawnType.CONVERSION, null);
                  convertTo.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
                  if (this.hasCustomName()) {
                     convertTo.setCustomName(convertTo.getCustomName());
                  }
               }
            }
         }
      }
   }

   private void spawnSlimeOfSize(ServerLevel level, EntityType<?> slimeType, int size) {
      if (slimeType.create(level) instanceof Slime slime) {
         slime.setPos(this.getX(), this.getY(), this.getZ());
         slime.finalizeSpawn(level, level.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.CONVERSION, null);
         slime.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
         if (this.hasCustomName()) {
            slime.setCustomName(slime.getCustomName());
         }

         slime.setSize(size, true);
         level.addFreshEntity(slime);
      }
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData
   ) {
      if (spawnType == MobSpawnType.NATURAL) {
         RandomSource random = level.getRandom();
         if (random.nextFloat() < ((Double)JNEConfigs.NATURAL_PETRIFIED_ECTO_SLAB_CHANCE.get()).floatValue()) {
            this.setPetrified(true);
            int stackSize = (Integer)JNEConfigs.NATURAL_PETRIFIED_ECTO_SLAB_MIN_STACK.get()
               + random.nextInt(
                  (Integer)JNEConfigs.NATURAL_PETRIFIED_ECTO_SLAB_MAX_STACK.get() - (Integer)JNEConfigs.NATURAL_PETRIFIED_ECTO_SLAB_MIN_STACK.get() + 1
               );
            this.setStackSize(stackSize);
         } else {
            int packSize = (Integer)JNEConfigs.NATURAL_ECTO_SLAB_PACK_MIN.get()
               + random.nextInt((Integer)JNEConfigs.NATURAL_ECTO_SLAB_PACK_MAX.get() - (Integer)JNEConfigs.NATURAL_ECTO_SLAB_PACK_MIN.get() + 1);
            this.setStackSize(1);

            for (int i = 1; i < packSize; i++) {
               EctoSlab additional = new EctoSlab(JNEEntityType.ECTO_SLAB.get(), this.level());
               double angle = random.nextDouble() * 2.0 * Math.PI;
               double distance = 0.8 + random.nextDouble() * 1.2;
               BlockPos pos = this.blockPosition();
               additional.setPos(pos.getX() + 0.5 + Math.cos(angle) * distance, this.getY(), pos.getZ() + 0.5 + Math.sin(angle) * distance);
               additional.setMaxStackSize(this.getMaxStackSize());
               additional.finalizeSpawn(level, difficulty, MobSpawnType.REINFORCEMENT, spawnGroupData);
               this.level().addFreshEntity(additional);
            }
         }
      }

      return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
   }

   public static boolean checkSpawnRules(
      EntityType<? extends ExorcismMob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      if (!ExorcismMob.checkSpawnRules(type, level, spawnType, pos, random)) {
         return false;
      }

      if (spawnType == MobSpawnType.NATURAL) {
         AABB area = new AABB(pos).inflate(48.0);
         List<EctoSlab> petrifiedNearby = level.getEntitiesOfClass(
            EctoSlab.class, area, aPotentialLesbian -> aPotentialLesbian.isPetrified() && aPotentialLesbian.isAlive()
         );
         if (!petrifiedNearby.isEmpty()) {
            return false;
         }

         int nearbyCount = level.getEntitiesOfClass(EctoSlab.class, area, LivingEntity::isAlive).size();
         return nearbyCount < 6;
      } else {
         return true;
      }
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
      super.onSyncedDataUpdated(key);
      if (IS_BURROWED.equals(key)) {
         this.refreshDimensions();
      }

      if (STACK_SIZE.equals(key)) {
         this.refreshDimensions();
      }
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(IS_BURROWED, false);
      builder.define(IS_PETRIFIED, false);
      builder.define(STACK_SIZE, 1);
      builder.define(STACK_COOLDOWN, 0);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag nbt) {
      super.addAdditionalSaveData(nbt);
      nbt.putBoolean("IsBurrowed", this.isBurrowed());
      nbt.putBoolean("IsPetrified", this.isPetrified());
      nbt.putInt("StackSize", this.getStackSize());
      nbt.putInt("MaxStackSize", this.getMaxStackSize());
      nbt.putInt("StackCooldown", this.getStackCooldown());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag nbt) {
      super.readAdditionalSaveData(nbt);
      this.setBurrowed(nbt.getBoolean("IsBurrowed"));
      this.setPetrified(nbt.getBoolean("IsPetrified"));
      this.setStackSize(nbt.getInt("StackSize"));
      this.setMaxStackSize(nbt.getInt("MaxStackSize"));
      this.setStackCooldown(nbt.getInt("StackCooldown"));
   }

   protected EntityDimensions getDefaultDimensions(Pose pose) {
      if (this.isBurrowed()) {
         return BURROWED_DIMENSIONS;
      }

      float height = this.getStackSize() * 0.75F;
      return EntityDimensions.scalable(1.375F, Math.max(0.2F, height));
   }

   protected float getAttackDamage() {
      return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
   }

   @Override
   public int apparitionPersonality() {
      return 3;
   }

   @Override
   protected double apparitionUnleashingOdds() {
      return (Double)JNEConfigs.ECTO_SLAB_UNLEASHING_ODDS.get();
   }

   @Override
   public void handleEntityEvent(byte id) {
      switch (id) {
         case 81:
            this.belowGroundAnimation = true;
            this.burrowAnimationAnimationTimer = 20;
            break;
         case 82:
            this.belowGroundAnimation = false;
            this.burrowAnimationAnimationTimer = 20;
            this.idleBurrowedAnimation.stop();
            this.burrowAnimation.stop();
            break;
         case 83:
            this.aboveGroundAnimation = true;
            this.emergeAnimationAnimationTimer = 15;
            break;
         case 84:
            this.aboveGroundAnimation = false;
            this.emergeAnimationAnimationTimer = 15;
            this.emergeAnimation.stop();
            this.idleAnimation.stop();
            break;
         case 85:
            EctoSlab.Client.warnParticle(this, this.level(), this.position());
            break;
         case 86:
            int stack = this.getStackSize();
            double yOffset = stack * 0.75;

            for (int i = 0; i < 20; i++) {
               double dx = this.random.nextGaussian() * 0.3;
               double dz = this.random.nextGaussian() * 0.3;
               double dy = this.random.nextDouble() * 0.5;
               this.level().addParticle(ParticleTypes.POOF, this.getX() + dx, this.getY() + yOffset + dy, this.getZ() + dz, 0.0, 0.0, 0.0);
            }
            break;
         case 87:
            EctoSlab.Client.forcedEmergeParticle(this, this.level(), this.position());
            break;
         case 88:
            EctoSlab.Client.shardParticle(this, this.level(), this.random);
            break;
         case 89:
            this.petrifiedHitAnimation.start(this.tickCount);
         case 90:
         case 91:
         case 92:
         case 93:
         case 94:
         case 95:
         case 96:
         case 97:
         case 98:
         case 99:
         case 100:
         case 101:
         case 102:
         case 103:
         case 104:
         case 105:
         case 106:
         case 107:
         case 108:
         case 109:
         case 110:
         case 111:
         case 112:
         case 113:
         case 114:
         case 115:
         case 116:
         case 117:
         case 118:
         case 119:
         default:
            break;
         case 120:
            EctoSlab.Client.petrifySwirlsBurstParticle(this, this.level(), this.position());
      }

      super.handleEntityEvent(id);
   }

   public boolean isBurrowed() {
      return (Boolean)this.entityData.get(IS_BURROWED);
   }

   public void setBurrowed(boolean burrowed) {
      if (burrowed != this.isBurrowed()) {
         this.entityData.set(IS_BURROWED, burrowed);
         this.refreshDimensions();
         if (burrowed) {
            this.playSound(JNESoundEvents.ECTO_SLAB_BURROW.get(), 1.0F, 1.0F);
            this.level().broadcastEntityEvent(this, (byte)81);
            this.level().broadcastEntityEvent(this, (byte)84);
         } else {
            this.level().broadcastEntityEvent(this, (byte)83);
            this.level().broadcastEntityEvent(this, (byte)82);
         }

         AttributeInstance stepHeight = this.getAttribute(Attributes.STEP_HEIGHT);
         if (stepHeight != null) {
            stepHeight.setBaseValue(burrowed ? 16.0 : 0.0);
         }
      }
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return this.isPetrified() ? false : super.removeWhenFarAway(distanceToClosestPlayer);
   }

   public boolean isPersistenceRequired() {
      return this.isPetrified() ? true : super.isPersistenceRequired();
   }

   public static int absoluteMaximumStackSize() {
      return (Integer)JNEConfigs.ABSOLUTE_MAXIMUM_STACK_SIZE.get();
   }

   private int getPetrificationRadius() {
      int baseRadius = (Integer)JNEConfigs.PETRIFICATION_SWIRLS_RANGE.get();
      int incrementPerStack = (Integer)JNEConfigs.PETRIFICATION_SWIRLS_RANGE_PER_STACK.get();
      return baseRadius + (this.getStackSize() - 1) * incrementPerStack;
   }

   public int getStackSize() {
      return (Integer)this.entityData.get(STACK_SIZE);
   }

   public void setStackSize(int stack) {
      stack = Mth.clamp(stack, 1, this.isPetrified() ? absoluteMaximumStackSize() : this.maxStackSize);
      this.entityData.set(STACK_SIZE, stack);
      this.updateAttributesForStack(stack);
      this.refreshDimensions();
      if (!this.level().isClientSide && this.isPetrified()) {
         this.petrifyNearbySwirls(this.getPetrificationRadius());
      }
   }

   public boolean isPetrified() {
      return (Boolean)this.entityData.get(IS_PETRIFIED);
   }

   public void setPetrified(boolean petrified) {
      if (petrified && !this.isPetrified() && !this.level().isClientSide) {
         this.petrifyNearbySwirls(this.getPetrificationRadius());
      }

      this.entityData.set(IS_PETRIFIED, petrified);
   }

   public int getStackCooldown() {
      return (Integer)this.entityData.get(STACK_COOLDOWN);
   }

   public void setStackCooldown(int stackCooldown) {
      this.entityData.set(STACK_COOLDOWN, stackCooldown);
   }

   protected int getJumpDelay() {
      int stack = this.getStackSize();

      int baseDelay = switch (stack) {
         case 1 -> 30;
         case 2 -> 20;
         case 3 -> 15;
         default -> 10;
      };
      return baseDelay + (Integer)JNEConfigs.ECTO_SLAB_EXTRA_JUMP_DELAY.get();
   }

   private int getBurrowCooldown() {
      int stack = this.getStackSize();

      int baseCooldown = switch (stack) {
         case 1 -> this.random.nextInt(60, 81);
         case 2 -> 60;
         default -> 50;
      };
      return baseCooldown + (Integer)JNEConfigs.ECTO_SLAB_EXTRA_BURROW_COOLDOWN.get();
   }

   private void updateAttributesForStack(int newStackSize) {
      AttributeInstance speed = this.getAttribute(Attributes.MOVEMENT_SPEED);
      AttributeInstance damage = this.getAttribute(Attributes.ATTACK_DAMAGE);
      AttributeInstance knockbackResistance = this.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
      AttributeInstance jumpStrength = this.getAttribute(Attributes.JUMP_STRENGTH);
      if (speed != null) {
         speed.removeModifier(STACK_SPEED_MODIFIER_ID);
         double speedBonus = (newStackSize - 1) * (Double)JNEConfigs.ECTO_SLAB_SPEED_PER_STACK.get();
         double speedCap = (Double)JNEConfigs.ECTO_SLAB_SPEED_CAP.get();
         if (speedCap >= 0.0) {
            speedBonus = Math.min(speedBonus, speedCap);
         }

         if (speedBonus != 0.0) {
            AttributeModifier speedMod = new AttributeModifier(STACK_SPEED_MODIFIER_ID, speedBonus, Operation.ADD_VALUE);
            speed.addTransientModifier(speedMod);
         }
      }

      if (damage != null) {
         damage.removeModifier(STACK_DAMAGE_MODIFIER_ID);
         double damageBonus = newStackSize * (Double)JNEConfigs.ECTO_SLAB_DAMAGE_PER_STACK.get();
         double damageCap = (Double)JNEConfigs.ECTO_SLAB_DAMAGE_CAP.get();
         if (damageCap >= 0.0) {
            damageBonus = Math.min(damageBonus, damageCap);
         }

         AttributeModifier damageMod = new AttributeModifier(STACK_DAMAGE_MODIFIER_ID, damageBonus, Operation.ADD_VALUE);
         damage.addTransientModifier(damageMod);
      }

      if (knockbackResistance != null) {
         knockbackResistance.removeModifier(STACK_KNOCKBACK_RESISTANCE_MODIFIER_ID);
         double kbBonus = (newStackSize - 1) * (Double)JNEConfigs.ECTO_SLAB_KNOCKBACK_RESISTANCE_PER_STACK.get();
         double kbCap = (Double)JNEConfigs.ECTO_SLAB_KNOCKBACK_RESISTANCE_CAP.get();
         if (kbCap >= 0.0) {
            kbBonus = Math.min(kbBonus, kbCap);
         }

         if (kbBonus != 0.0) {
            AttributeModifier kbMod = new AttributeModifier(STACK_KNOCKBACK_RESISTANCE_MODIFIER_ID, kbBonus, Operation.ADD_VALUE);
            knockbackResistance.addTransientModifier(kbMod);
         }
      }

      if (jumpStrength != null) {
         jumpStrength.removeModifier(STACK_JUMP_STRENGTH_MODIFIER_ID);
         double jumpBonus = (newStackSize - 1) * (Double)JNEConfigs.ECTO_SLAB_JUMP_STRENGTH_PER_STACK.get();
         double jumpCap = (Double)JNEConfigs.ECTO_SLAB_JUMP_STRENGTH_CAP.get();
         if (jumpCap >= 0.0) {
            jumpBonus = Math.min(jumpBonus, jumpCap);
         }

         if (jumpBonus != 0.0) {
            AttributeModifier jumpMod = new AttributeModifier(STACK_JUMP_STRENGTH_MODIFIER_ID, jumpBonus, Operation.ADD_VALUE);
            jumpStrength.addTransientModifier(jumpMod);
         }
      }
   }

   public int getMaxStackSize() {
      return this.maxStackSize;
   }

   public void setMaxStackSize(int maxStackSize) {
      maxStackSize = Mth.clamp(maxStackSize, 1, absoluteMaximumStackSize());
      this.maxStackSize = maxStackSize;
   }

   private void setupAnimationStates() {
      if (this.isPetrified()) {
         if (this.tickCount % 20 == 0) {
            EctoSlab.Client.petrificationParticle(this, this.level(), this.random);
         }

         this.idlePetrifiedAnimation.startIfStopped(this.tickCount);
         this.idleAnimation.stop();
         this.idleBurrowedAnimation.stop();
         this.burrowAnimation.stop();
         this.emergeAnimation.stop();
      } else {
         this.idlePetrifiedAnimation.stop();
         if (this.belowGroundAnimation) {
            if (this.burrowAnimationAnimationTimer == 20) {
               this.burrowAnimation.startIfStopped(this.tickCount);
               this.showLight = true;
            }

            if (this.burrowAnimationAnimationTimer < 18 && this.burrowAnimationAnimationTimer > 0) {
               EctoSlab.Client.burrowDustParticle(this, this.level(), this.random);

               for (int i = 0; i < 4; i++) {
                  this.level()
                     .addParticle(
                        new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockStateOn()),
                        this.getRandomX(0.7),
                        this.getY(),
                        this.getRandomZ(0.7),
                        0.0,
                        0.6,
                        0.0
                     );
               }
            } else if (this.burrowAnimationAnimationTimer == 0) {
               this.burrowAnimation.stop();
               this.idleBurrowedAnimation.startIfStopped(this.tickCount);
            }

            if (this.burrowAnimationAnimationTimer > 0) {
               this.burrowAnimationAnimationTimer--;
            }
         }

         if (this.aboveGroundAnimation) {
            if (this.emergeAnimationAnimationTimer == 15) {
               this.emergeAnimation.startIfStopped(this.tickCount);

               for (int i = 0; i < 32; i++) {
                  this.level()
                     .addParticle(
                        new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockStateOn()),
                        this.getRandomX(0.7),
                        this.getY(),
                        this.getRandomZ(0.7),
                        0.0,
                        0.6,
                        0.0
                     );
               }
            } else if (this.emergeAnimationAnimationTimer == 0) {
               this.emergeAnimation.stop();
               this.idleAnimation.startIfStopped(this.tickCount);
               this.showLight = false;
            }

            if (this.emergeAnimationAnimationTimer > 0) {
               this.emergeAnimationAnimationTimer--;
            }
         }

         if (this.isBurrowed()) {
            EctoSlab.Client.burrowedParticle(this, this.level(), this.random);
         }
      }
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return SoundEvents.MAGMA_CUBE_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.MAGMA_CUBE_DEATH;
   }

   @NotNull
   protected SoundEvent getSquishSound() {
      return JNESoundEvents.ECTO_SLAB_SQUISH.get();
   }

   @NotNull
   protected SoundEvent getJumpSound() {
      return JNESoundEvents.ECTO_SLAB_JUMP.get();
   }

   @NotNull
   protected SoundEvent getWarnSound() {
      return JNESoundEvents.ECTO_SLAB_WARN.get();
   }

   @OnlyIn(Dist.CLIENT)
   public static class Client {
      public static void warnParticle(EctoSlab ectoSlab, Level level, Vec3 pos) {
         Vec3 direction = new Vec3(0.0, 1.0, 0.0);
         int delay = 0;

         for (int i = 0; i < 3; i++) {
            WorldParticleBuilder.create(JNEParticleTypes.WIND_TRAIL.get())
               .setFullBrightLighting()
               .setColorData(ColorParticleData.create(new Color(7533823)).build())
               .setScaleData(GenericParticleData.create(0.1F, 1.0F).setEasing(Easing.SINE_OUT).build())
               .setBehavior(DirectionalParticleBehavior.directional(direction))
               .setTransparencyData(GenericParticleData.create(0.7F, 0.0F).build())
               .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
               .setLifetime(20)
               .disableNoClip()
               .setLifeDelay(delay)
               .addTickActor(actor -> {
                  if (ectoSlab.isBurrowed()) {
                     actor.setPos(ectoSlab.getX(), ectoSlab.getY() + 0.1, ectoSlab.getZ());
                  }
               })
               .spawn(level, pos.x, pos.y - 1.5, pos.z);
            delay += 7;
         }
      }

      public static void burrowedParticle(EctoSlab ectoSlab, Level level, RandomSource random) {
         double parX = ectoSlab.getRandomX(0.5);
         double parZ = ectoSlab.getRandomZ(0.5);
         WorldParticleBuilder.create(JNEParticleTypes.GLOWING_DOT.get())
            .setFullBrightLighting()
            .setSpinData(SpinParticleData.createRandomDirection(random, 0.0F, 1.0F).setCoefficient(0.7F).setEasing(Easing.SINE_IN).build())
            .setScaleData(GenericParticleData.create(0.0F, 0.28F, 0.0F).build())
            .setTransparencyData(GenericParticleData.create(0.5F).build())
            .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
            .setLifetime(random.nextInt(20))
            .disableNoClip()
            .setGravity(0.0F)
            .modifySpinData(consumer -> {})
            .setColorData(ColorParticleData.create(new Color(7533823)).build())
            .setMotion(0.0, 0.26, 0.0)
            .spawn(level, parX, ectoSlab.getY(), parZ);
      }

      public static void forcedEmergeParticle(EctoSlab ectoSlab, Level level, Vec3 pos) {
         Vec3 direction = new Vec3(0.0, 1.0, 0.0);
         WorldParticleBuilder.create(JNEParticleTypes.WIND_TRAIL.get())
            .setFullBrightLighting()
            .setSpinData(SpinParticleData.createRandomDirection(level.random, 0.0F, 1.0F).setCoefficient(0.25F).setEasing(Easing.SINE_IN).build())
            .setColorData(ColorParticleData.create(new Color(16777215)).build())
            .setScaleData(GenericParticleData.create(0.1F, 1.5F).setEasing(Easing.SINE_OUT).build())
            .setBehavior(DirectionalParticleBehavior.directional(direction))
            .setTransparencyData(GenericParticleData.create(0.5F, 0.0F).build())
            .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
            .setLifetime(10)
            .disableNoClip()
            .addTickActor(actor -> {
               if (ectoSlab.isBurrowed()) {
                  actor.setPos(ectoSlab.getX(), ectoSlab.getY() + 0.1, ectoSlab.getZ());
               }
            })
            .spawn(level, pos.x, pos.y - 1.5, pos.z);
      }

      public static void burrowDustParticle(EctoSlab ectoSlab, Level level, RandomSource random) {
         LodestoneWorldParticleType particle = CommonParticles.SMOKE_VARIANTS[random.nextInt(CommonParticles.SMOKE_VARIANTS.length)];
         WorldParticleBuilder.create(particle)
            .setNaturalLighting()
            .setScaleData(GenericParticleData.create(Mth.randomBetween(random, 0.33F, 0.4F), 0.95F).build())
            .setTransparencyData(GenericParticleData.create(0.75F, 0.5F, 0.0F).setEasing(Easing.BOUNCE_OUT).build())
            .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
            .setColorData(ColorParticleData.create(new Color(10395294)).setEasing(Easing.SINE_IN_OUT).build())
            .setSpritePicker(ParticleSpritePicker.WITH_AGE)
            .setLifetime(Mth.randomBetweenInclusive(random, 20, 30))
            .disableNoClip()
            .addMotion(0.0 + random.nextDouble() / 24.0, 0.09, 0.0 + random.nextDouble() / 24.0)
            .setGravity(0.3F)
            .spawn(level, ectoSlab.getRandomX(1.0), ectoSlab.getY() + 0.2, ectoSlab.getRandomZ(1.0));
      }

      public static void shardParticle(EctoSlab ectoSlab, Level level, RandomSource random) {
         LodestoneWorldParticleType particle = JNEParticleTypes.ECTO_SHARD.get();

         for (int i = 0; i < 64; i++) {
            double motionX = random.nextDouble() / 3.6 * (random.nextBoolean() ? 1 : -1);
            double motionY = random.nextDouble() / 2.6 * (random.nextBoolean() ? 1 : -1);
            double motionZ = random.nextDouble() / 3.6 * (random.nextBoolean() ? 1 : -1);
            WorldParticleBuilder.create(particle)
               .setNaturalLighting()
               .setSpinData(SpinParticleData.createRandomDirection(level.random, 0.0F, 1.0F).setCoefficient(0.0F).setEasing(Easing.SINE_IN).build())
               .setScaleData(GenericParticleData.create(0.13F).build())
               .setTransparencyData(GenericParticleData.create(1.0F).build())
               .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
               .setSpritePicker(ParticleSpritePicker.RANDOM_SPRITE)
               .setLifetime(random.nextInt(80, 120))
               .disableNoClip()
               .addMotion(motionX, motionY, motionZ)
               .setGravity(0.75F)
               .spawn(level, ectoSlab.getX(), ectoSlab.getY() + 0.2, ectoSlab.getZ());
         }
      }

      public static void petrificationParticle(EctoSlab ectoSlab, Level level, RandomSource random) {
         Color color = new Color(937550);

         for (int i = 0; i < 12; i++) {
            WorldParticleBuilder.create(JNEParticleTypes.SPARKLE.get())
               .setFullBrightLighting()
               .setColorData(ColorParticleData.create(color).build())
               .setSpinData(SpinParticleData.createRandomDirection(random, 0.0F, 2.0F).setCoefficient(1.0F).setEasing(Easing.SINE_IN).build())
               .setScaleData(GenericParticleData.create(0.05F, 0.13F, 0.0F).setEasing(Easing.BOUNCE_IN_OUT).build())
               .setTransparencyData(GenericParticleData.create(0.5F).build())
               .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
               .setSpritePicker(ParticleSpritePicker.WITH_AGE)
               .setLifetime(random.nextInt(5, 15) + ectoSlab.getStackSize() * 10)
               .enableNoClip()
               .setLifeDelay(random.nextInt(0, 20))
               .setGravity(0.0F)
               .setMotion(0.0, 0.08, 0.0)
               .spawn(level, ectoSlab.getRandomX(1.0), ectoSlab.getY() + 0.2, ectoSlab.getRandomZ(1.0));
         }
      }

      public static void petrifySwirlsBurstParticle(EctoSlab ectoSlab, Level level, Vec3 pos) {
         Vec3 direction = new Vec3(0.0, 1.0, 0.0);
         WorldParticleBuilder.create(JNEParticleTypes.LARGE_BURST.get())
            .setFullBrightLighting()
            .setSpinData(SpinParticleData.createRandomDirection(level.random, 0.0F, 1.0F).setCoefficient(0.25F).setEasing(Easing.SINE_IN).build())
            .setColorData(ColorParticleData.create(new Color(937550)).build())
            .setScaleData(GenericParticleData.create(0.1F, ectoSlab.getPetrificationRadius()).setEasing(Easing.SINE_OUT).build())
            .setBehavior(DirectionalParticleBehavior.directional(direction))
            .setTransparencyData(GenericParticleData.create(1.0F, 0.0F).build())
            .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
            .setLifetime(80)
            .disableNoClip()
            .spawn(level, pos.x, pos.y + 0.4, pos.z);
      }
   }

   static class EctoSlabAttackGoal extends Goal {
      private final EctoSlab ectoSlab;
      private int burrowedTime = 0;
      private int burrowAnimationDelay = 0;

      public EctoSlabAttackGoal(EctoSlab ectoSlab) {
         this.ectoSlab = ectoSlab;
         this.setFlags(EnumSet.of(Flag.LOOK));
      }

      public boolean canUse() {
         if (this.ectoSlab.lookingToStack) {
            return false;
         }

         LivingEntity target = this.ectoSlab.getTarget();
         return target == null ? false : this.ectoSlab.canAttack(target) && this.ectoSlab.getMoveControl() instanceof EctoSlab.EctoSlabMoveControl;
      }

      public void start() {
         super.start();
      }

      public void stop() {
         super.stop();
         this.burrowedTime = 0;
         this.burrowAnimationDelay = 0;
         this.ectoSlab.setBurrowed(false);
         this.ectoSlab.burrowedCooldown = 40;
         if (this.ectoSlab.getMoveControl() instanceof EctoSlab.EctoSlabMoveControl control) {
            control.setSeekTarget(null, 0.0);
         }
      }

      public boolean canContinueToUse() {
         if (this.ectoSlab.lookingToStack) {
            return false;
         }

         LivingEntity target = this.ectoSlab.getTarget();
         return target == null ? false : this.ectoSlab.canAttack(target);
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         LivingEntity target = this.ectoSlab.getTarget();
         if (target != null) {
            if (!this.ectoSlab.isBurrowed()) {
               this.ectoSlab.lookAt(target, 10.0F, 10.0F);
               if (this.ectoSlab.getMoveControl() instanceof EctoSlab.EctoSlabMoveControl control) {
                  double dx = target.getX() - this.ectoSlab.getX();
                  double dz = target.getZ() - this.ectoSlab.getZ();
                  float yaw = (float)(Mth.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
                  control.setDirection(yaw, true);
                  control.setWantedMovement(this.ectoSlab.getAttributeValue(Attributes.MOVEMENT_SPEED));
               }

               if (this.ectoSlab.onGround() && this.ectoSlab.burrowedCooldown-- <= 0) {
                  this.ectoSlab.setBurrowed(true);
                  this.burrowedTime = (Integer)JNEConfigs.ECTO_SLAB_MAX_DIG_TIME.get();
                  this.burrowAnimationDelay = 20;
               }
            } else {
               if (this.burrowAnimationDelay > 0) {
                  this.burrowAnimationDelay--;
                  if (this.ectoSlab.getMoveControl() instanceof EctoSlab.EctoSlabMoveControl control) {
                     control.setWantedPosition(this.ectoSlab.getX(), this.ectoSlab.getY(), this.ectoSlab.getZ(), 0.0);
                     this.ectoSlab.setDeltaMovement(Vec3.ZERO);
                     this.ectoSlab.setSpeed(0.0F);
                  }

                  return;
               }

               if (this.burrowedTime > 0) {
                  this.burrowedTime--;
               }

               if (this.ectoSlab.getMoveControl() instanceof EctoSlab.EctoSlabMoveControl control) {
                  if (this.ectoSlab.wasForcedOut && this.burrowedTime > 10) {
                     this.burrowedTime = 10;
                     this.ectoSlab.level().broadcastEntityEvent(this.ectoSlab, (byte)87);
                  }

                  if (this.burrowedTime >= 30) {
                     control.setSeekTarget(target, this.ectoSlab.getAttributeValue(Attributes.MOVEMENT_SPEED));
                  }

                  if (this.burrowedTime == 30) {
                     this.ectoSlab.playSound(this.ectoSlab.getWarnSound(), this.ectoSlab.getSoundVolume(), 1.0F);
                     this.ectoSlab.level().getServer().execute(() -> this.ectoSlab.level().broadcastEntityEvent(this.ectoSlab, (byte)85));
                  }

                  if (this.burrowedTime > 0 && this.burrowedTime < 30) {
                     control.setSeekTarget(null, 0.0);
                     control.setWantedPosition(this.ectoSlab.getX(), this.ectoSlab.getY(), this.ectoSlab.getZ(), 0.0);
                     this.ectoSlab.setDeltaMovement(Vec3.ZERO);
                     this.ectoSlab.setSpeed(0.0F);
                  }

                  if (this.burrowedTime == 0) {
                     this.ectoSlab.setBurrowed(false);
                     this.ectoSlab.burrowedCooldown = this.ectoSlab.getBurrowCooldown();
                     this.ectoSlab.doEmergenceBurst(this.ectoSlab.wasForcedOut);
                     control.setSeekTarget(null, 0.0);
                  }
               }
            }
         }
      }
   }

   static class EctoSlabFloatGoal extends Goal {
      private final EctoSlab ectoSlab;

      public EctoSlabFloatGoal(EctoSlab ectoSlab) {
         this.ectoSlab = ectoSlab;
         this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
         ectoSlab.getNavigation().setCanFloat(true);
      }

      public boolean canUse() {
         return (this.ectoSlab.isInWater() || this.ectoSlab.isInLava()) && this.ectoSlab.getMoveControl() instanceof EctoSlab.EctoSlabMoveControl;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         if (this.ectoSlab.getRandom().nextFloat() < 0.8F) {
            this.ectoSlab.getJumpControl().jump();
         }

         if (this.ectoSlab.getMoveControl() instanceof EctoSlab.EctoSlabMoveControl control) {
            control.setWantedMovement(1.2);
         }
      }
   }

   static class EctoSlabKeepOnJumpingGoal extends Goal {
      private final EctoSlab ectoSlab;

      public EctoSlabKeepOnJumpingGoal(EctoSlab ectoSlab) {
         this.ectoSlab = ectoSlab;
         this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
      }

      public boolean canUse() {
         return !this.ectoSlab.isPassenger();
      }

      public void tick() {
         if (this.ectoSlab.getMoveControl() instanceof EctoSlab.EctoSlabMoveControl control) {
            control.setWantedMovement(1.0);
         }
      }
   }

   static class EctoSlabMoveControl extends MoveControl {
      private float yRot;
      private int jumpDelay;
      private final EctoSlab ectoSlab;
      private boolean isAggressive;
      @Nullable
      private Entity movingTarget;
      private double seekSpeed = 0.0;

      public EctoSlabMoveControl(EctoSlab ectoSlab) {
         super(ectoSlab);
         this.ectoSlab = ectoSlab;
         this.yRot = 180.0F * ectoSlab.getYRot() / (float) Math.PI;
      }

      public void setDirection(float yRot, boolean aggressive) {
         this.yRot = yRot;
         this.isAggressive = aggressive;
      }

      public void setWantedMovement(double speed) {
         this.speedModifier = speed;
         this.operation = net.minecraft.world.entity.ai.control.MoveControl.Operation.MOVE_TO;
      }

      public void setSeekTarget(@Nullable Entity target, double speed) {
         this.movingTarget = target;
         this.seekSpeed = speed;
      }

      public void tick() {
         if (this.ectoSlab.isBurrowed()) {
            this.burrowBehavior();
         } else {
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();
            this.slimeBehavior();
         }

         if (!this.ectoSlab.isBurrowed() && this.movingTarget != null) {
            this.movingTarget = null;
         }
      }

      private void slimeBehavior() {
         if (this.operation != net.minecraft.world.entity.ai.control.MoveControl.Operation.MOVE_TO) {
            this.mob.setZza(0.0F);
         } else {
            this.operation = net.minecraft.world.entity.ai.control.MoveControl.Operation.WAIT;
            if (this.mob.onGround()) {
               this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
               if (this.jumpDelay-- <= 0) {
                  this.jumpDelay = this.ectoSlab.getJumpDelay();
                  if (this.isAggressive) {
                     this.jumpDelay /= 2;
                  }

                  this.ectoSlab.getJumpControl().jump();
                  this.ectoSlab.playSound(this.ectoSlab.getJumpSound(), this.ectoSlab.getSoundVolume(), 1.0F);
               } else {
                  this.ectoSlab.xxa = 0.0F;
                  this.ectoSlab.zza = 0.0F;
                  this.mob.setSpeed(0.0F);
               }
            } else {
               this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            }
         }
      }

      private void burrowBehavior() {
         if (this.movingTarget != null && this.movingTarget.isAlive()) {
            Vec3 toTarget = this.movingTarget.position().subtract(this.mob.position());
            double distance = toTarget.length();
            if (distance < 0.5) {
               this.mob.setPos(this.movingTarget.getX(), this.mob.getY(), this.movingTarget.getZ());
               this.mob.setDeltaMovement(Vec3.ZERO);
               this.mob.setSpeed(0.0F);
               return;
            }

            double maxSpeed = this.seekSpeed * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED) * 1.5;
            Vec3 desiredVelocity = toTarget.normalize().scale(maxSpeed);
            this.mob.setDeltaMovement(desiredVelocity.x, this.mob.getDeltaMovement().y, desiredVelocity.z);
            float yaw = (float)(Mth.atan2(desiredVelocity.z, desiredVelocity.x) * (180.0 / Math.PI)) - 90.0F;
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), yaw, 90.0F));
            this.mob.setSpeed((float)this.seekSpeed);
         } else {
            this.mob.setDeltaMovement(0.0, this.mob.getDeltaMovement().y, 0.0);
            this.mob.setSpeed(0.0F);
            this.mob.setZza(0.0F);
            this.operation = net.minecraft.world.entity.ai.control.MoveControl.Operation.WAIT;
         }
      }
   }

   static class EctoSlabPetrifiedGoal extends Goal {
      private final EctoSlab ectoSlab;

      public EctoSlabPetrifiedGoal(EctoSlab ectoSlab) {
         this.ectoSlab = ectoSlab;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public boolean canUse() {
         return this.ectoSlab.isPetrified();
      }

      public boolean canContinueToUse() {
         return this.ectoSlab.isPetrified();
      }

      public void start() {
         this.alignToBlockCenter();
      }

      public void tick() {
         this.ectoSlab.setDeltaMovement(0.0, this.ectoSlab.getDeltaMovement().y, 0.0);
         this.ectoSlab.setSpeed(0.0F);
         this.ectoSlab.xxa = 0.0F;
         this.ectoSlab.zza = 0.0F;
         if (this.ectoSlab.onGround()) {
            this.alignToBlockCenter();
         }
      }

      private void alignToBlockCenter() {
         if (!this.ectoSlab.level().isClientSide) {
            BlockPos blockPos = this.ectoSlab.blockPosition();
            double centerX = blockPos.getX() + 0.5;
            double centerZ = blockPos.getZ() + 0.5;
            this.ectoSlab.setPos(centerX, this.ectoSlab.getY(), centerZ);
         }
      }
   }

   static class EctoSlabRandomDirectionGoal extends Goal {
      private final EctoSlab ectoSlab;
      private float chosenDegrees;
      private int nextRandomizeTime;

      public EctoSlabRandomDirectionGoal(EctoSlab ectoSlab) {
         this.ectoSlab = ectoSlab;
         this.setFlags(EnumSet.of(Flag.LOOK));
      }

      public boolean canUse() {
         return this.ectoSlab.getTarget() == null
            && (this.ectoSlab.onGround() || this.ectoSlab.isInWater() || this.ectoSlab.isInLava() || this.ectoSlab.hasEffect(MobEffects.LEVITATION))
            && this.ectoSlab.getMoveControl() instanceof EctoSlab.EctoSlabMoveControl;
      }

      public void tick() {
         if (--this.nextRandomizeTime <= 0) {
            this.nextRandomizeTime = this.adjustedTickDelay(40 + this.ectoSlab.getRandom().nextInt(60));
            this.chosenDegrees = this.ectoSlab.getRandom().nextInt(360);
         }

         if (this.ectoSlab.getMoveControl() instanceof EctoSlab.EctoSlabMoveControl control) {
            control.setDirection(this.chosenDegrees, false);
         }
      }
   }

   static class EctoSlabStackGoal extends Goal {
      private static final double SEARCH_RADIUS = 24.0;
      private static final double STACK_MERGE_DISTANCE = 2.0;
      private int leapCooldown = 10;
      private final EctoSlab ectoSlab;
      @Nullable
      private EctoSlab mergeTarget;

      public EctoSlabStackGoal(EctoSlab ectoSlab) {
         this.ectoSlab = ectoSlab;
         this.setFlags(EnumSet.of(Flag.LOOK));
      }

      public boolean canUse() {
         if (this.ectoSlab.getStackCooldown() > 0) {
            return false;
         }

         if (this.ectoSlab.getTarget() == null) {
            return false;
         }

         if (this.ectoSlab.getStackSize() > 1) {
            return false;
         }

         this.mergeTarget = this.findBestMergeTarget();
         return this.ectoSlab.getMoveControl() instanceof EctoSlab.EctoSlabMoveControl && this.mergeTarget != null;
      }

      public boolean canContinueToUse() {
         if (this.ectoSlab.getStackCooldown() > 0) {
            return false;
         } else if (this.ectoSlab.getTarget() == null) {
            return false;
         } else if (this.ectoSlab.getStackSize() > 1) {
            return false;
         } else if (this.mergeTarget != null && this.mergeTarget.isAlive() && !this.mergeTarget.isDeadOrDying()) {
            return this.mergeTarget.isPetrified() && this.mergeTarget.getStackSize() >= EctoSlab.absoluteMaximumStackSize()
               ? false
               : this.ectoSlab.distanceToSqr(this.mergeTarget) > 4.0;
         } else {
            return false;
         }
      }

      public void start() {
         this.ectoSlab.lookingToStack = true;
         this.leapCooldown = 10;
      }

      public void stop() {
         this.mergeTarget = null;
         this.ectoSlab.lookingToStack = false;
         this.leapCooldown = 10;
      }

      public void tick() {
         if (this.mergeTarget != null) {
            if (this.ectoSlab.getMoveControl() instanceof EctoSlab.EctoSlabMoveControl control) {
               double dx = this.mergeTarget.getX() - this.ectoSlab.getX();
               double dz = this.mergeTarget.getZ() - this.ectoSlab.getZ();
               float yaw = (float)(Mth.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
               control.setDirection(yaw, true);
               control.setWantedPosition(
                  this.mergeTarget.getX(), this.mergeTarget.getY(), this.mergeTarget.getZ(), this.ectoSlab.getAttributeValue(Attributes.MOVEMENT_SPEED)
               );
               if (this.leapCooldown <= 0) {
                  if (!this.ectoSlab.onGround()) {
                     return;
                  }

                  this.ectoSlab.getJumpControl().jump();
                  Vec3 leap = new Vec3(this.ectoSlab.getLookAngle().x, this.ectoSlab.getLookAngle().y, this.ectoSlab.getLookAngle().z).normalize();
                  this.ectoSlab.playSound(JNESoundEvents.ECTO_SLAB_LEAP.get(), 1.0F, 1.0F);
                  this.ectoSlab.push(leap.scale(0.55));
                  this.leapCooldown = 20;
               } else {
                  this.leapCooldown--;
               }
            }

            if (this.ectoSlab.distanceToSqr(this.mergeTarget) <= 4.0) {
               this.mergeTarget.setStackSize(this.mergeTarget.getStackSize() + 1);
               this.ectoSlab.playSound(JNESoundEvents.ECTO_SLAB_STACK.get(), 2.0F, 1.0F);
               this.ectoSlab.discard();
            }
         }
      }

      @Nullable
      private EctoSlab findBestMergeTarget() {
         Level level = this.ectoSlab.level();
         AABB searchBox = AABB.ofSize(this.ectoSlab.position(), 24.0, 24.0, 24.0);
         List<EctoSlab> candidates = level.getEntitiesOfClass(
            EctoSlab.class,
            searchBox,
            candidate -> candidate != this.ectoSlab
               && !candidate.isBurrowed()
               && !candidate.isDeadOrDying()
               && (
                  candidate.isPetrified()
                     ? candidate.getStackSize() < EctoSlab.absoluteMaximumStackSize()
                     : candidate.getStackSize() < candidate.getMaxStackSize()
               )
         );
         if (candidates.isEmpty()) {
            return null;
         }

         candidates.sort(
            Comparator.<EctoSlab>comparingInt(c -> c.isPetrified() ? 0 : 1)
               .thenComparingInt(EctoSlab::getStackSize)
               .thenComparingDouble(e -> e.distanceToSqr(this.ectoSlab))
         );
         return candidates.getFirst();
      }
   }
}
