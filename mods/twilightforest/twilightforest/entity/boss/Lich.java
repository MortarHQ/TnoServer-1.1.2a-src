package twilightforest.entity.boss;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import twilightforest.block.LightableBlock;
import twilightforest.block.OminousCandleBlock;
import twilightforest.components.entity.FortificationShieldAttachment;
import twilightforest.data.tags.DamageTypeTagGenerator;
import twilightforest.data.tags.EntityTagGenerator;
import twilightforest.entity.ai.goal.AlwaysWatchTargetGoal;
import twilightforest.entity.ai.goal.AttemptToGoHomeGoal;
import twilightforest.entity.ai.goal.LichAbsorbMinionsGoal;
import twilightforest.entity.ai.goal.LichMinionsGoal;
import twilightforest.entity.ai.goal.LichPopMobsGoal;
import twilightforest.entity.ai.goal.LichShadowsGoal;
import twilightforest.entity.ai.goal.RandomLookAroundIfBoredGoal;
import twilightforest.entity.monster.LichMinion;
import twilightforest.entity.projectile.LichBomb;
import twilightforest.init.TFAttributes;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFEntities;
import twilightforest.init.TFItems;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStructures;
import twilightforest.network.ParticlePacket;
import twilightforest.util.entities.EntityUtil;

public class Lich extends BaseTFBoss {
   public static final int PARTICLE_BURST_COOLDOWN = 23;
   public static final int DEATH_ANIMATION_POINT_A = 115;
   public static final int DEATH_ANIMATION_POINT_B = 131;
   public static final int DEATH_ANIMATION_POINT_C = 163;
   public static final int DEATH_ANIMATION_DURATION = 295;
   protected static final EntityDataAccessor<Optional<UUID>> MASTER_LICH = SynchedEntityData.defineId(Lich.class, EntityDataSerializers.OPTIONAL_UUID);
   protected static final EntityDataAccessor<Integer> SHIELD_STRENGTH = SynchedEntityData.defineId(Lich.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Integer> MINIONS_LEFT = SynchedEntityData.defineId(Lich.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Integer> ATTACK_TYPE = SynchedEntityData.defineId(Lich.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Integer> TELEPORT_INVISIBILITY = SynchedEntityData.defineId(Lich.class, EntityDataSerializers.INT);
   protected static final ItemParticleOption BONE_PARTICLE = new ItemParticleOption(ParticleTypes.ITEM, Items.BONE.getDefaultInstance());
   public static final int MAX_ACTIVE_MINIONS = 3;
   public static final int MAX_HEALTH = 100;
   public static final int MAX_SHADOW_CLONES = 2;
   public static final int MAX_SHIELD_STRENGTH = 6;
   public static final int MAX_MINIONS_TO_SUMMON = 9;
   protected int attackCooldown;
   protected int popCooldown;
   protected int heldScepterTime;
   protected int spawnTime;
   protected final List<UUID> summonedClones = new ArrayList<>();
   protected int previousPhase = 1;
   protected int babyMinionsSummoned = 0;
   protected int hitsWithoutTeleport = 0;

   public Lich(EntityType<? extends Lich> type, Level level) {
      super(type, level);
      this.xpReward = 217;
      this.setShieldStrength((int)this.getAttributeValue(TFAttributes.SHIELD_STRENGTH));
      this.setMinionsToSummon((int)this.getAttributeValue(TFAttributes.MINION_COUNT));
   }

   public Lich(Level level, Lich otherLich) {
      this((EntityType<? extends Lich>)TFEntities.LICH.get(), level);
      this.setMasterUUID(otherLich.getUUID());
      this.getBossBar().setVisible(false);
      this.setRestrictionPoint(otherLich.getRestrictionPoint());
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData
   ) {
      SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
      if (!this.isShadowClone()) {
         this.setItemInHand(InteractionHand.MAIN_HAND, TFItems.FORTIFICATION_SCEPTER.toStack());
         this.playSound((SoundEvent)TFSounds.SHIELD_ADD.get(), 1.5F, this.getVoicePitch());
         this.swing(InteractionHand.MAIN_HAND);
      }

      return data;
   }

   public static Builder registerAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 100.0)
         .add(Attributes.ATTACK_DAMAGE, 3.0)
         .add(Attributes.MOVEMENT_SPEED, 0.45)
         .add(Attributes.FOLLOW_RANGE, 35.0)
         .add(TFAttributes.CLONE_COUNT, 2.0)
         .add(TFAttributes.SHIELD_STRENGTH, 6.0)
         .add(TFAttributes.MINION_COUNT, 9.0);
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(MASTER_LICH, Optional.empty());
      builder.define(SHIELD_STRENGTH, 6);
      builder.define(MINIONS_LEFT, 9);
      builder.define(ATTACK_TYPE, 0);
      builder.define(TELEPORT_INVISIBILITY, 0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector
         .addGoal(
            0,
            new AttemptToGoHomeGoal<Lich>(this, 1.25) {
               @Override
               public boolean canUse() {
                  return super.canUse() && Lich.this.isOutsideHomeRange(Lich.this.position());
               }

               public boolean requiresUpdateEveryTick() {
                  return true;
               }

               public void tick() {
                  if (Lich.this.getTeleportInvisibility() <= 0) {
                     if ((Lich.this.getNavigation().getPath() == null || Lich.this.getNavigation().isStuck() || !Lich.this.getNavigation().getPath().canReach())
                        && !Lich.this.teleportToNewTarget(Lich.this.getTarget(), 20.0F, null)) {
                        Lich.this.teleportHome();
                     }
                  }
               }
            }
         );
      this.goalSelector.addGoal(1, new RandomLookAroundIfBoredGoal(this));
      this.goalSelector.addGoal(1, new AlwaysWatchTargetGoal(this));
      this.goalSelector.addGoal(1, new LichPopMobsGoal(this));
      this.goalSelector.addGoal(1, new LichAbsorbMinionsGoal(this));
      this.goalSelector.addGoal(2, new LichShadowsGoal(this, 30.0F));
      this.goalSelector.addGoal(3, new LichMinionsGoal(this));
      this.goalSelector
         .addGoal(
            4,
            new MeleeAttackGoal(this, 0.75, true) {
               public boolean canUse() {
                  return Lich.this.getPhase() == 3 && super.canUse();
               }

               public void tick() {
                  if (((Lich)this.mob).getTeleportInvisibility() <= 0) {
                     super.tick();
                     if (this.mob.getTarget() != null
                        && !this.mob.isWithinMeleeAttackRange(this.mob.getTarget())
                        && this.mob.getNavigation().isDone()
                        && !this.mob.getNavigation().moveTo(this.mob.getTarget(), this.speedModifier)) {
                        Lich.this.teleportToSightOfEntity(this.mob.getTarget());
                     }
                  }
               }

               public void start() {
                  super.start();
                  this.mob.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GOLDEN_SWORD));
               }
            }
         );
      this.addRestrictionGoals(this, this.goalSelector);
      this.targetSelector
         .addGoal(
            1,
            new HurtByTargetGoal(this) {
               public boolean canUse() {
                  return this.mob instanceof Lich main && this.mob.getLastHurtByMob() instanceof Lich lich && lich.getMaster() == main.getMaster()
                     ? false
                     : super.canUse();
               }
            }
         );
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, false));
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      if (this.getMasterUUID() != null) {
         compound.putUUID("MasterLich", this.getMasterUUID());
      }

      ListTag clonesTag = new ListTag();

      for (UUID uuid : this.summonedClones) {
         clonesTag.add(NbtUtils.createUUID(uuid));
      }

      if (!clonesTag.isEmpty()) {
         compound.put("SummonedClones", clonesTag);
      }

      compound.putInt("ShieldStrength", this.getShieldStrength());
      compound.putInt("MinionsToSummon", this.getMinionsToSummon());
      compound.putInt("BabyMinionsSummoned", this.babyMinionsSummoned);
      compound.putInt("HitsWithoutTeleport", this.hitsWithoutTeleport);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (compound.contains("MasterLich")) {
         this.setMasterUUID(compound.getUUID("MasterLich"));
      }

      if (compound.contains("SummonedClones", 9)) {
         this.summonedClones.clear();
         ListTag cloneList = compound.getList("SummonedClones", 11);
         cloneList.forEach(tag -> this.summonedClones.add(NbtUtils.loadUUID(tag)));
      }

      this.setShieldStrength(compound.getInt("ShieldStrength"));
      this.setMinionsToSummon(compound.getInt("MinionsToSummon"));
      this.babyMinionsSummoned = compound.getInt("BabyMinionsSummoned");
      this.hitsWithoutTeleport = compound.getInt("HitsWithoutTeleport");
   }

   public void aiStep() {
      super.aiStep();
      int tpInvisibility = this.getTeleportInvisibility();
      if (tpInvisibility > 0) {
         if (this.getTarget() != null) {
            this.getLookControl().setLookAt(this.getTarget());
            this.getLookControl().tick();
         }

         this.setTeleportInvisibility(tpInvisibility - 1);
         if (tpInvisibility - 1 <= 0) {
            this.lichTeleportParticles(true);
            this.playSound((SoundEvent)TFSounds.LICH_TELEPORT.get(), 1.125F, 1.125F);
         }
      } else if (!this.isDeadOrDying()) {
         if (this.getPhase() == 3) {
            this.level()
               .addParticle(
                  (ParticleOptions)TFParticleType.ANGRY_LICH.get(),
                  this.getRandomX(0.65F),
                  this.getEyeY() + 0.25 + this.getRandom().nextFloat() * 0.5,
                  this.getRandomZ(0.65F),
                  this.getRandom().nextGaussian() * 0.02,
                  this.getRandom().nextGaussian() * 0.02,
                  this.getRandom().nextGaussian() * 0.02
               );
         } else {
            float angle = this.yBodyRot * (float) Math.PI / 180.0F;
            double dx = this.getX() + Mth.cos(angle) * 0.65;
            double dy = this.getY() + this.getBbHeight() * 0.94;
            double dz = this.getZ() + Mth.sin(angle) * 0.65;
            int factor = (80 - this.getAttackCooldown()) / 10;
            int particles = factor > 0 ? this.getRandom().nextInt(factor) : 1;

            for (int j1 = 0; j1 < particles; j1++) {
               float sparkle = 1.0F - (this.getAttackCooldown() + 1.0F) / 60.0F;
               sparkle *= sparkle;
               float red = 0.37F * sparkle;
               float grn = 0.99F * sparkle;
               float blu = 0.89F * sparkle;
               if (this.getNextAttackType() != 0) {
                  red = 0.99F * sparkle;
                  grn = 0.47F * sparkle;
                  blu = 0.0F * sparkle;
               }

               this.level()
                  .addParticle(
                     ColorParticleOption.create((ParticleType)TFParticleType.MAGIC_EFFECT.get(), red, grn, blu),
                     dx + this.getRandom().nextGaussian() * 0.025,
                     dy + this.getRandom().nextGaussian() * 0.025,
                     dz + this.getRandom().nextGaussian() * 0.025,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }
      }
   }

   public boolean isOutsideHomeRange(Vec3 pos) {
      if (this.getRestrictionPoint() == null) {
         return false;
      }

      BlockPos point = this.getRestrictionPoint().pos();
      return this.getTarget() == null && this.getY() < point.getY() - 2
         ? true
         : point.distToCenterSqr(pos) > this.getHomeRadius() * this.getHomeRadius() || this.getPhase() == 3 && this.getY() < point.getY() - 3;
   }

   @Override
   protected void customServerAiStep() {
      super.customServerAiStep();
      if (this.isOutsideHomeRange(this.position()) && this.getTeleportInvisibility() <= 0) {
         this.teleportHome();
      }

      if (this.getAttackCooldown() > 0 && this.spawnTime <= 0) {
         this.attackCooldown--;
      }

      if (this.getPopCooldown() > 0 && this.getHealth() < this.getMaxHealth() && this.getScepterTimeLeft() <= 0) {
         this.popCooldown--;
      }

      if (this.getScepterTimeLeft() == 0
         && this.getPopCooldown() < 30
         && this.getItemInHand(InteractionHand.MAIN_HAND).is((Item)TFItems.LIFEDRAIN_SCEPTER.get())) {
         this.setItemInHand(
            InteractionHand.MAIN_HAND, new ItemStack((ItemLike)(this.getPhase() == 2 ? (ItemLike)TFItems.ZOMBIE_SCEPTER.get() : Items.GOLDEN_SWORD))
         );
      }

      if (this.getScepterTimeLeft() > 0) {
         this.heldScepterTime--;
      }

      if (this.getTarget() != null && this.spawnTime > 0 && this.spawnTime-- < 40) {
         this.turnNearbyCandles(40 - this.spawnTime, this.spawnTime - 1 <= 0 || this.spawnTime - 1 >= 40);
      }
   }

   public boolean hurt(DamageSource src, float damage) {
      if (this.getTeleportInvisibility() > 0 && !src.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         return false;
      }

      if (src.is(DamageTypes.IN_WALL) && this.getTarget() != null) {
         this.teleportToNewTarget(this.getTarget(), 20.0F, null);
      }

      if (this.isShadowClone() && !src.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         this.playSound((SoundEvent)TFSounds.LICH_CLONE_HURT.get(), 1.0F, this.getVoicePitch() * 2.0F);
         return false;
      }

      if (src.getEntity() instanceof Lich) {
         return false;
      }

      if (!src.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && this.getShieldStrength() > 0) {
         if (!src.is(DamageTypeTagGenerator.BREAKS_LICH_SHIELDS) || !(damage > 2.0F)) {
            this.playSound((SoundEvent)TFSounds.SHIELD_BLOCK.get(), 0.75F, this.getVoicePitch() * 1.75F);
            this.gameEvent(GameEvent.ENTITY_DAMAGE);
            if (src.getEntity() instanceof LivingEntity living) {
               this.setLastHurtByMob(living);
            }
         } else if (this.getShieldStrength() > 0) {
            int newShieldStrength = this.getShieldStrength() - 1;
            this.setShieldStrength(newShieldStrength);
            float volume = 1.5F;
            if (!this.level().isClientSide()) {
               FortificationShieldAttachment.addShieldBreakParticles(src, this);
            }

            if (newShieldStrength < 6) {
               volume += 0.25F * (6 - newShieldStrength);
            }

            if (newShieldStrength == 0) {
               volume += 0.5F;
            }

            this.playSound((SoundEvent)TFSounds.SHIELD_BREAK.get(), volume, this.getVoicePitch() * 1.25F);
            this.gameEvent(GameEvent.ENTITY_DAMAGE);
         }

         return false;
      } else if (super.hurt(src, damage)) {
         if (this.getRandom().nextInt(this.getPhase() == 3 ? 6 : 3) <= this.hitsWithoutTeleport++ && !this.isDeadOrDying()) {
            this.hitsWithoutTeleport = 0;
            this.teleportToNewTarget(this.getTarget(), 20.0F, null);
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void die(DamageSource cause) {
      super.die(cause);
      if (!this.isShadowClone()) {
         this.despawnClones();
         if (this.getShieldStrength() > 0) {
            this.setShieldStrength(0);
            this.playSound((SoundEvent)TFSounds.SHIELD_BREAK.get(), 1.2F, this.getVoicePitch() * 2.0F);
         }
      }
   }

   public void launchProjectileAt(ThrowableProjectile projectile) {
      if (this.getTarget() != null) {
         float bodyFacingAngle = this.yBodyRot * (float) Math.PI / 180.0F;
         double sx = this.getX() + Mth.cos(bodyFacingAngle) * 0.65;
         double sy = this.getY() + this.getBbHeight() * 0.82;
         double sz = this.getZ() + Mth.sin(bodyFacingAngle) * 0.65;
         double tx = this.getTarget().getX() - sx;
         double ty = this.getTarget().getBoundingBox().minY + this.getTarget().getBbHeight() / 2.0F - (this.getY() + this.getBbHeight() / 2.0F);
         double tz = this.getTarget().getZ() - sz;
         float pitch = (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F;
         if (projectile instanceof LichBomb) {
            pitch *= 0.85F;
         }

         this.playSound((SoundEvent)TFSounds.LICH_SHOOT.get(), this.getSoundVolume(), pitch);
         projectile.moveTo(sx, sy, sz, this.getYRot(), this.getXRot());
         projectile.shoot(tx, ty, tz, 0.5F, 1.0F);
         this.level().addFreshEntity(projectile);
      }
   }

   public void addClone(UUID uuid) {
      this.summonedClones.add(uuid);
   }

   public List<UUID> getClones() {
      return this.summonedClones;
   }

   public List<Lich> getAllClones() {
      if (this.isShadowClone()) {
         return List.of();
      }

      List<Lich> clones = new ArrayList<>();
      if (this.level() instanceof ServerLevel server) {
         for (UUID uuid : this.getClones()) {
            if (server.getEntity(uuid) instanceof Lich clone && clone.getMaster() == this) {
               clones.add(clone);
            }
         }
      }

      return clones;
   }

   public void despawnClones() {
      if (this.level() instanceof ServerLevel server) {
         for (UUID uuid : this.getClones()) {
            if (server.getEntity(uuid) instanceof Lich clone && clone.getMaster() == this) {
               ParticlePacket particlePacket = new ParticlePacket();

               for (int j = 0; j < 128; j++) {
                  double x = clone.getX(this.random.nextDouble() * this.random.nextDouble() * (this.random.nextBoolean() ? 1.0 : -1.0) * 1.5);
                  double y = clone.getY(this.random.nextDouble() * this.random.nextDouble() * 1.25);
                  double z = clone.getZ(this.random.nextDouble() * this.random.nextDouble() * (this.random.nextBoolean() ? 1.0 : -1.0) * 1.5);
                  particlePacket.queueParticle(ParticleTypes.SMOKE, false, x, y, z, 0.0, 0.0, 0.0);
               }

               PacketDistributor.sendToPlayersTrackingEntity(this, particlePacket, new CustomPacketPayload[0]);
               clone.remove(RemovalReason.DISCARDED);
            }
         }
      }
   }

   @Nullable
   public UUID getMasterUUID() {
      return (UUID)((Optional)this.getEntityData().get(MASTER_LICH)).orElse(null);
   }

   @Nullable
   public Lich getMaster() {
      return this.level() instanceof ServerLevel server && this.getMasterUUID() != null && server.getEntity(this.getMasterUUID()) instanceof Lich lich
         ? lich
         : null;
   }

   public void setMasterUUID(@Nullable UUID lich) {
      this.getBossBar().setVisible(lich != null);
      this.getEntityData().set(MASTER_LICH, Optional.ofNullable(lich));
   }

   public boolean wantsNewClone(Lich clone) {
      return clone.isShadowClone() && this.countMyClones() < this.getAttributeValue(TFAttributes.CLONE_COUNT);
   }

   public int countMyClones() {
      int count = 0;
      if (this.level() instanceof ServerLevel server) {
         for (UUID uuid : this.summonedClones) {
            if (server.getEntity(uuid) instanceof Lich lich && lich.getMaster() == this) {
               count++;
            }
         }
      }

      return count;
   }

   public boolean wantsNewMinion() {
      return this.countMyMinions() < 3;
   }

   public int countMyMinions() {
      return (int)this.level()
         .getEntitiesOfClass(
            LichMinion.class,
            new AABB(this.getX(), this.getY(), this.getZ(), this.getX() + 1.0, this.getY() + 1.0, this.getZ() + 1.0).inflate(32.0, 16.0, 32.0)
         )
         .stream()
         .filter(m -> m.master == this)
         .count();
   }

   public boolean isInvisible() {
      return super.isInvisible() || this.getTeleportInvisibility() > 0 || this.isShadowClone() && this.tickCount <= 10;
   }

   public ItemStack getMainHandItem() {
      return this.getTeleportInvisibility() > 0 && this.level().isClientSide ? ItemStack.EMPTY : super.getMainHandItem();
   }

   public boolean canBeSeenAsEnemy() {
      return this.getTeleportInvisibility() > 0 ? false : super.canBeSeenAsEnemy();
   }

   public void teleportHome() {
      if (this.getRestrictionPoint() != null) {
         BlockPos pos = this.getRestrictionPoint().pos();
         if (this.level().getBlockState(pos.below(2)).isAir()) {
            this.level().setBlockAndUpdate(pos.below(2), ((RotatedPillarBlock)TFBlocks.BOLD_STONE_PILLAR.get()).defaultBlockState());
         }

         if (this.level().getBlockState(pos.below()).isAir()) {
            pos = pos.below();
         }

         this.teleportToNoChecks(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
      }
   }

   public boolean teleportToNewTarget(@Nullable LivingEntity target, float range, @Nullable LichShadowsGoal lichShadowsGoal) {
      List<Player> possibleTargets = new ArrayList<>();

      for (Player player : this.level().players()) {
         if (player.distanceTo(this) < range && !player.isDeadOrDying()) {
            possibleTargets.add(player);
         }
      }

      if (!possibleTargets.isEmpty()) {
         target = (LivingEntity)possibleTargets.get(this.getRandom().nextInt(possibleTargets.size()));
      }

      if (target != null) {
         this.setTarget(target);
         if (this.teleportToSightOfEntity(target)) {
            for (Lich clone : this.getAllClones()) {
               clone.setTarget(target);
               clone.teleportToSightOfEntity(target);
            }

            if (lichShadowsGoal != null) {
               lichShadowsGoal.checkAndSpawnClones(target);
            }

            return true;
         }
      }

      return false;
   }

   public boolean teleportToSightOfEntity(@Nullable Entity entity) {
      if (entity == null) {
         return false;
      } else {
         Vec3 dest = this.findVecInLOSOf(entity);
         if (dest != null) {
            this.teleportToNoChecks(dest.x(), dest.y(), dest.z());
            this.getLookControl().setLookAt(entity, 100.0F, 100.0F);
            this.yBodyRot = this.getYRot();
            return true;
         } else {
            return false;
         }
      }
   }

   @Nullable
   public Vec3 findVecInLOSOf(@Nullable Entity targetEntity) {
      if (targetEntity == null) {
         return null;
      }

      double origX = this.getX();
      double origY = this.getY();
      double origZ = this.getZ();
      int tries = 100;

      for (int i = 0; i < tries; i++) {
         double tx = targetEntity.getX() + this.getRandom().nextGaussian() * 16.0;
         double ty = targetEntity.getY() + 2.0;
         double tz = targetEntity.getZ() + this.getRandom().nextGaussian() * 16.0;
         boolean destClear = this.randomTeleport(tx, ty, tz, false);
         if (destClear) {
            tx = this.getX();
            ty = this.getY();
            tz = this.getZ();
         }

         boolean canSeeTargetAtDest = this.hasLineOfSight(targetEntity);
         this.teleportTo(origX, origY, origZ);
         Vec3 tpPos = new Vec3(tx, ty, tz);
         if ((i >= 85 || !(ty < targetEntity.getY()))
            && destClear
            && canSeeTargetAtDest
            && !this.isOutsideHomeRange(tpPos)
            && tpPos.distanceToSqr(targetEntity.position()) >= 25.0) {
            return tpPos;
         }
      }

      return null;
   }

   private void teleportToNoChecks(double destX, double destY, double destZ) {
      this.lichTeleportParticles(false);
      this.teleportTo(destX, destY, destZ);
      this.playSound((SoundEvent)TFSounds.LICH_TELEPORT.get(), 0.75F, 0.75F);
      this.gameEvent(GameEvent.TELEPORT);
      if (this.level() instanceof ServerLevel serverLevel) {
         serverLevel.broadcastEntityEvent(this, (byte)46);
      }

      this.setTeleportInvisibility(20);
      this.jumping = false;
      this.clearFire();
   }

   public void handleEntityEvent(byte b) {
      if (b != 46) {
         super.handleEntityEvent(b);
      }
   }

   public void lichTeleportParticles(boolean appear) {
      if (this.level() instanceof ServerLevel) {
         ParticlePacket particlePacket = new ParticlePacket();
         if (appear) {
            for (int j = 0; j < 64; j++) {
               Vec3 pos = this.position().add(0.0, this.getBbHeight() * 0.5, 0.0);
               ParticleOptions options = (ParticleOptions)(this.isShadowClone() ? ParticleTypes.SMOKE : (ParticleOptions)TFParticleType.OMINOUS_FLAME.get());
               double x = this.getX(this.random.nextDouble() * this.random.nextDouble() * (this.random.nextBoolean() ? 1.0 : -1.0));
               double y = this.getY(this.random.nextDouble());
               double z = this.getZ(this.random.nextDouble() * this.random.nextDouble() * (this.random.nextBoolean() ? 1.0 : -1.0));
               particlePacket.queueParticle(options, false, x, y, z, (x - pos.x) * 0.0625, (y - pos.y) * 0.0625, (z - pos.z) * 0.0625);
            }
         } else {
            for (int j = 0; j < (!this.isShadowClone() ? 128 : 64); j++) {
               double x = this.getX(this.random.nextDouble() * this.random.nextDouble() * (this.random.nextBoolean() ? 1.0 : -1.0) * 2.0);
               double y = this.getY(this.random.nextDouble() * 1.125);
               double z = this.getZ(this.random.nextDouble() * this.random.nextDouble() * (this.random.nextBoolean() ? 1.0 : -1.0) * 2.0);
               particlePacket.queueParticle(ParticleTypes.SMOKE, false, x, y, z, 0.0, 0.0, 0.0);
            }
         }

         PacketDistributor.sendToPlayersTrackingEntity(this, particlePacket, new CustomPacketPayload[0]);
      }
   }

   public void makeMagicTrail(Vec3 source, Vec3 target, float red, float green, float blue) {
      int particles = 60;
      if (!this.level().isClientSide()) {
         for (ServerPlayer serverplayer : ((ServerLevel)this.level()).players()) {
            if (serverplayer.distanceToSqr(source) < 4096.0) {
               ParticlePacket packet = new ParticlePacket();

               for (int i = 0; i < particles; i++) {
                  double trailFactor = i / (particles - 1.0);
                  double tx = source.x() + (target.x() - source.x()) * trailFactor + this.getRandom().nextGaussian() * 0.005;
                  double ty = source.y() + 0.2 + (target.y() - source.y()) * trailFactor + this.getRandom().nextGaussian() * 0.005;
                  double tz = source.z() + (target.z() - source.z()) * trailFactor + this.getRandom().nextGaussian() * 0.005;
                  packet.queueParticle(
                     ColorParticleOption.create((ParticleType)TFParticleType.MAGIC_EFFECT.get(), red, green, blue), false, tx, ty, tz, 0.0, 0.0, 0.0
                  );
               }

               PacketDistributor.sendToPlayersTrackingEntity(this, packet, new CustomPacketPayload[0]);
            }
         }
      }
   }

   @Override
   public void placeSpawner(BlockPos pos) {
      super.placeSpawner(pos);
      this.lightNearbyCandles();
   }

   @Override
   protected void postRemoval(ServerLevel serverLevel, RemovalReason reason) {
      super.postRemoval(serverLevel, reason);
      if (!this.isShadowClone()) {
         this.lightNearbyCandles();
      }
   }

   protected void turnNearbyCandles(int tick, boolean done) {
      if (this.level() instanceof ServerLevel) {
         int range = (int)(tick / 2.5F + 2.0F);
         int yRange = (int)(tick / 2.0F + 2.0F);

         for (BlockPos pos : BlockPos.betweenClosed(this.homeOrElseCurrent().offset(-range, -3, -range), this.homeOrElseCurrent().offset(range, yRange, range))) {
            if (this.random.nextInt(Math.max(40 - tick, 2)) == 1 || done) {
               BlockState state = this.level().getBlockState(pos);
               if (state.getBlock() instanceof AbstractCandleBlock candleBlock
                  && OminousCandleBlock.CANDLE_MAP.containsKey(candleBlock)
                  && (Boolean)state.getValue(CandleBlock.LIT)) {
                  this.level()
                     .setBlockAndUpdate(
                        pos,
                        (BlockState)((OminousCandleBlock)OminousCandleBlock.CANDLE_MAP.get(candleBlock).get())
                           .defaultBlockState()
                           .setValue(OminousCandleBlock.CANDLES, (Integer)state.getValue(CandleBlock.CANDLES))
                     );
                  this.level()
                     .playSound(
                        null,
                        pos,
                        (SoundEvent)TFSounds.OMINOUS_FIRE.get(),
                        SoundSource.BLOCKS,
                        0.5F,
                        (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 0.75F
                     );
               } else if (state.getBlock() instanceof LightableBlock && state.getValue(LightableBlock.LIGHTING) == LightableBlock.Lighting.NORMAL) {
                  this.level().setBlockAndUpdate(pos, (BlockState)state.setValue(LightableBlock.LIGHTING, LightableBlock.Lighting.OMINOUS));
                  this.level()
                     .playSound(
                        null,
                        pos,
                        (SoundEvent)TFSounds.CANDELABRA_OMINOUS.get(),
                        SoundSource.BLOCKS,
                        0.5F,
                        (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 0.75F
                     );
               }
            }
         }
      }
   }

   protected void lightNearbyCandles() {
      for (BlockPos pos : BlockPos.betweenClosed(this.homeOrElseCurrent().offset(-40, -3, -40), this.homeOrElseCurrent().offset(40, 40, 40))) {
         BlockState state = this.level().getBlockState(pos);
         if (state.getBlock() instanceof OminousCandleBlock candleBlock) {
            this.level()
               .setBlockAndUpdate(
                  pos,
                  (BlockState)((BlockState)candleBlock.candle
                        .defaultBlockState()
                        .setValue(CandleBlock.CANDLES, (Integer)state.getValue(OminousCandleBlock.CANDLES)))
                     .setValue(BlockStateProperties.LIT, true)
               );
            this.level().playSound(null, pos, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 0.25F, 1.5F);
         } else if (state.getBlock() instanceof LightableBlock && state.getValue(LightableBlock.LIGHTING) == LightableBlock.Lighting.OMINOUS) {
            this.level().setBlockAndUpdate(pos, (BlockState)state.setValue(LightableBlock.LIGHTING, LightableBlock.Lighting.NORMAL));
            this.level().playSound(null, pos, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 0.25F, 1.15F);
         }
      }
   }

   protected BlockPos homeOrElseCurrent() {
      return this.getRestrictionPoint() == null ? this.blockPosition() : this.getRestrictionPoint().pos();
   }

   public void setExtinguishTimer() {
      this.spawnTime = 40;
   }

   public int getPhase() {
      if (this.isShadowClone() || this.getShieldStrength() > 0) {
         return 1;
      } else {
         return this.getMinionsToSummon() <= 0 && this.countMyMinions() <= 0 ? 3 : 2;
      }
   }

   public int getAttackCooldown() {
      return this.attackCooldown;
   }

   public void setAttackCooldown(int cooldown) {
      this.attackCooldown = cooldown;
   }

   public int getPopCooldown() {
      return this.popCooldown;
   }

   public void setPopCooldown(int cooldown) {
      this.popCooldown = cooldown;
   }

   public int getScepterTimeLeft() {
      return this.heldScepterTime;
   }

   public void setScepterTime() {
      this.heldScepterTime = 20 + this.getRandom().nextInt(20);
      this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack((ItemLike)TFItems.LIFEDRAIN_SCEPTER.get()));
   }

   public void resetScepterTime() {
      this.heldScepterTime = 0;
   }

   public boolean isShadowClone() {
      return ((Optional)this.getEntityData().get(MASTER_LICH)).isPresent();
   }

   public int getShieldStrength() {
      return this.isShadowClone() ? 0 : (Integer)this.getEntityData().get(SHIELD_STRENGTH);
   }

   public void setShieldStrength(int shieldStrength) {
      this.getEntityData().set(SHIELD_STRENGTH, shieldStrength);
   }

   public int getMinionsToSummon() {
      return (Integer)this.getEntityData().get(MINIONS_LEFT);
   }

   public void setMinionsToSummon(int minionsToSummon) {
      this.getEntityData().set(MINIONS_LEFT, minionsToSummon);
   }

   public int getNextAttackType() {
      return (Integer)this.getEntityData().get(ATTACK_TYPE);
   }

   public void setNextAttackType(int attackType) {
      this.getEntityData().set(ATTACK_TYPE, attackType);
   }

   public int getTeleportInvisibility() {
      return (Integer)this.getEntityData().get(TELEPORT_INVISIBILITY);
   }

   public void setTeleportInvisibility(int attackType) {
      this.getEntityData().set(TELEPORT_INVISIBILITY, attackType);
   }

   public int getBabyMinionsSummoned() {
      return this.babyMinionsSummoned;
   }

   public void setBabyMinionsSummoned(int babyMinionsSummoned) {
      this.babyMinionsSummoned = babyMinionsSummoned;
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return this.isShadowClone() ? null : (SoundEvent)TFSounds.LICH_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.LICH_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return this.deathTime <= 1 && !this.isShadowClone() ? (SoundEvent)TFSounds.LICH_HURT.get() : (SoundEvent)TFSounds.LICH_DEATH.get();
   }

   @Nullable
   public ResourceKey<LootTable> getDefaultLootTable() {
      return !this.isShadowClone() ? super.getDefaultLootTable() : null;
   }

   public boolean displayFireAnimation() {
      return this.deathTime <= 0 && super.displayFireAnimation();
   }

   public boolean isLeftHanded() {
      return false;
   }

   @Override
   public int getHomeRadius() {
      return 30;
   }

   @Override
   public ResourceKey<Structure> getHomeStructure() {
      return TFStructures.LICH_TOWER;
   }

   @Override
   public Block getDeathContainer(RandomSource random) {
      return this.getRandom().nextBoolean() ? (Block)TFBlocks.CANOPY_CHEST.get() : (Block)TFBlocks.TWILIGHT_OAK_CHEST.get();
   }

   @Override
   public Block getBossSpawner() {
      return (Block)TFBlocks.LICH_BOSS_SPAWNER.get();
   }

   @Override
   protected boolean shouldCreateSpawner() {
      return !this.isShadowClone();
   }

   @Override
   protected boolean shouldSpawnLoot() {
      return !this.isShadowClone() && super.shouldSpawnLoot();
   }

   @Override
   public boolean isDeathAnimationFinished() {
      return this.deathTime >= 295;
   }

   @Override
   public void tickDeathAnimation() {
      if (!this.isShadowClone()) {
         if (this.deathTime <= 115) {
            boolean done = this.deathTime == 115;
            boolean hurt = this.deathTime % 23 == 0;
            if (done) {
               SoundEvent soundevent = this.getDeathSound();
               if (soundevent != null) {
                  this.level().playLocalSound(this, soundevent, SoundSource.HOSTILE, this.getSoundVolume(), this.getVoicePitch());
               }
            } else if (hurt) {
               SoundEvent soundevent = this.getHurtSound(this.damageSources().generic());
               if (soundevent != null) {
                  this.level().playLocalSound(this, soundevent, SoundSource.HOSTILE, this.getSoundVolume(), this.getVoicePitch());
               }
            }

            Vec3 pos = this.position();

            for (int i = 0; i < (hurt ? 12 : 3); i++) {
               double x = (this.getRandom().nextDouble() - 0.5) * 0.7;
               double y = this.getRandom().nextDouble() * this.getBbHeight();
               double z = (this.getRandom().nextDouble() - 0.5) * 0.7;
               this.level()
                  .addParticle(
                     (ParticleOptions)(!this.getRandom().nextBoolean() && !hurt ? ParticleTypes.SMOKE : BONE_PARTICLE),
                     false,
                     pos.x() + x,
                     pos.y() + y,
                     pos.z() + z,
                     0.0,
                     0.0,
                     0.0
                  );
            }

            if (hurt) {
               double x = (this.getRandom().nextDouble() - 0.5) * 0.7;
               double y = this.getRandom().nextDouble() * this.getBbHeight();
               double z = (this.getRandom().nextDouble() - 0.5) * 0.7;

               for (int i = 0; i < 7; i++) {
                  double x1 = x + (this.getRandom().nextDouble() - 0.5) * 0.1;
                  double y1 = y + (this.getRandom().nextDouble() - 0.5) * 0.1;
                  double z1 = z + (this.getRandom().nextDouble() - 0.5) * 0.1;
                  this.level()
                     .addParticle(
                        (ParticleOptions)(this.getRandom().nextBoolean() ? BONE_PARTICLE : ParticleTypes.CLOUD),
                        false,
                        pos.x() + x1,
                        pos.y() + y1,
                        pos.z() + z1,
                        0.0,
                        0.0,
                        0.0
                     );
               }

               Vec3 added = this.position().add(0.0, this.getBbHeight() * 0.5, 0.0);

               for (int i = 0; i < (done ? 18 : 6); i++) {
                  double x1 = this.getX(this.random.nextDouble() * this.random.nextDouble() * (this.random.nextBoolean() ? 1.0 : -1.0));
                  double y1 = this.getY(this.random.nextDouble());
                  double z1 = this.getZ(this.random.nextDouble() * this.random.nextDouble() * (this.random.nextBoolean() ? 1.0 : -1.0));
                  this.level().addParticle(ParticleTypes.SMOKE, x1, y1, z1, (x1 - added.x) * 0.0125, (y1 - added.y) * 0.0125, (z1 - added.z) * 0.0125);
               }
            }

            if (done) {
               for (int i = 0; i < 32; i++) {
                  double x = (this.getRandom().nextDouble() - 0.5) * 0.7;
                  double y = this.getRandom().nextDouble() * this.getBbHeight();
                  double z = (this.getRandom().nextDouble() - 0.5) * 0.7;
                  this.level()
                     .addParticle(
                        (ParticleOptions)(this.getRandom().nextBoolean() ? BONE_PARTICLE : ParticleTypes.CLOUD),
                        false,
                        pos.x() + x,
                        pos.y() + y,
                        pos.z() + z,
                        0.0,
                        0.0,
                        0.0
                     );
               }
            }
         } else if (this.deathTime == 131) {
            Vec3 pos = this.position();

            for (int i = 0; i < 3; i++) {
               double x = (this.getRandom().nextDouble() - 0.5) * 0.75;
               double z = (this.getRandom().nextDouble() - 0.5) * 0.75;
               this.level().addParticle(ParticleTypes.CLOUD, false, pos.x() + x, pos.y(), pos.z() + z, 0.0, 0.0, 0.0);
            }
         } else if (this.deathTime > 163) {
            Vec3 start = this.position().add(0.0, 0.45F, 0.0);
            Vec3 end = Vec3.atCenterOf(EntityUtil.bossChestLocation(this));
            int deathTime2 = this.deathTime - 163;
            double factor = deathTime2 / 132.0;
            double powFactor = factor * factor * 2.0;
            double expandFactor = (Math.cos((factor + 0.5) * Math.PI * 2.0) + 1.0) * 0.5;
            Vec3 particlePos = start.add(end.subtract(start).scale(Math.min(factor * 2.0, 1.0)));

            for (double i = 0.0; i < 1.0; i += 0.2) {
               double x = Math.sin((powFactor + i) * Math.PI * 2.0) * expandFactor * 1.75;
               double z = Math.cos((powFactor + i) * Math.PI * 2.0) * expandFactor * 1.75;
               this.level()
                  .addParticle(
                     (ParticleOptions)TFParticleType.OMINOUS_FLAME.get(),
                     false,
                     particlePos.x() + x,
                     particlePos.y() - 0.25,
                     particlePos.z() + z,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }

         if (this.deathTime > 131 && this.random.nextFloat() <= 0.33F) {
            Vec3 start = this.position().add(0.0, 0.15F, 0.0);
            double x = (this.getRandom().nextDouble() - 0.5) * 0.25;
            double y = this.getRandom().nextDouble() * this.getBbHeight() * 0.1;
            double z = (this.getRandom().nextDouble() - 0.5) * 0.25;
            this.level().addParticle(ParticleTypes.SMOKE, false, start.x() + x, start.y() + y, start.z() + z, 0.0, 0.0, 0.0);
         }
      }
   }

   @Override
   protected void tickDeath() {
      super.tickDeath();
      if (this.deathTime >= 115) {
         this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
      } else if (this.lookAtUponDeath() instanceof LivingEntity living) {
         double d0 = living.getX() - this.getX();
         double d2 = living.getZ() - this.getZ();
         float f = (float)(Mth.atan2(d2, d0) * 180.0 / (float) Math.PI) - 90.0F;
         this.setYHeadRot(this.rotLerp(this.getYHeadRot(), f));
         this.setXRot(0.0F);
      }
   }

   private float rotLerp(float angle, float targetAngle) {
      float f = Mth.wrapDegrees(targetAngle - angle);
      if (f > 30.0F) {
         f = 30.0F;
      }

      if (f < -30.0F) {
         f = -30.0F;
      }

      return angle + f;
   }

   @Nullable
   protected Entity lookAtUponDeath() {
      if (this.getTarget() != null) {
         return this.getTarget();
      } else {
         return this.lastHurtByPlayer != null ? this.lastHurtByPlayer : this.level().getNearestPlayer(this, 20.0);
      }
   }

   @Override
   protected void tickBossBar() {
      this.getBossBar().setVisible(!this.isShadowClone());
      int phase = this.getPhase();
      if (phase == 1) {
         this.getBossBar().setProgress(this.getShieldStrength() / (float)this.getAttributeValue(TFAttributes.SHIELD_STRENGTH));
      } else {
         this.getBossBar().setProgress(this.getHealth() / this.getMaxHealth());
      }

      if (phase != this.previousPhase) {
         this.getBossBar().updateStyle(this.getBossBarColor(), this.getBossBarOverlay(), this.previousPhase != 1);
      }

      this.previousPhase = phase;
   }

   @Override
   public BossBarOverlay getBossBarOverlay() {
      return this.getShieldStrength() > 0 ? BossBarOverlay.NOTCHED_6 : super.getBossBarOverlay();
   }

   @Override
   public int getBossBarColor() {
      if (this.getShieldStrength() > 0) {
         return 16766976;
      } else {
         return this.getPhase() == 2 ? 12461055 : 16711680;
      }
   }

   public ProjectileDeflection deflection(Projectile projectile) {
      return projectile.getType().is(EntityTagGenerator.LICH_DEFLECTS_PHASE_2)
            && (projectile.getOwner() instanceof Player || projectile.getOwner() instanceof Lich || projectile.getOwner() == null)
            && this.getPhase() > 1
         ? (proj, entity, random) -> {
            proj.setDeltaMovement(
               this.getDeltaMovement().add(0.5 - this.getRandom().nextDouble(), 0.75, 0.5 - this.getRandom().nextDouble()).multiply(0.75, 1.5, 0.75)
            );
            proj.setOwner(this);
            this.playSound((SoundEvent)TFSounds.SHIELD_BLOCK.get(), 0.5F, this.getVoicePitch() * 1.5F);
            this.swing(InteractionHand.MAIN_HAND);
         }
         : super.deflection(projectile);
   }
}
