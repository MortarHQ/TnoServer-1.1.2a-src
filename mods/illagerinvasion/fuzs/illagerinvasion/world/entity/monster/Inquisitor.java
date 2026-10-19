package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.init.ModSoundEvents;
import fuzs.puzzleslib.api.init.v3.registry.LookupHelper;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.AbstractIllager.IllagerArmPose;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class Inquisitor extends AbstractIllager {
   private static final EntityDataAccessor<Boolean> STUNNED = SynchedEntityData.defineId(Inquisitor.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> FINAL_ROAR = SynchedEntityData.defineId(Inquisitor.class, EntityDataSerializers.BOOLEAN);
   public boolean finalRoar;
   public int stunTick = 40;
   public boolean isStunned;
   public int blockedCount;

   public Inquisitor(EntityType<? extends Inquisitor> entityType, Level world) {
      super(entityType, world);
      this.xpReward = 25;
      this.setPathfindingMalus(PathType.LEAVES, 0.0F);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, false));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{Raider.class}).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
      this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
      this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
   }

   protected void customServerAiStep() {
      if (!this.isNoAi() && GoalUtils.hasGroundPathNavigation(this)) {
         boolean bl = ((ServerLevel)this.level()).isRaided(this.blockPosition());
         ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(bl);
      }

      super.customServerAiStep();
   }

   public void aiStep() {
      if (this.horizontalCollision && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
         boolean bl = false;
         AABB box = this.getBoundingBox().inflate(1.0);

         for (BlockPos blockPos : BlockPos.betweenClosed(
            Mth.floor(box.minX), Mth.floor(box.minY), Mth.floor(box.minZ), Mth.floor(box.maxX), Mth.floor(box.maxY), Mth.floor(box.maxZ)
         )) {
            Block block = this.level().getBlockState(blockPos).getBlock();
            if (block instanceof LeavesBlock || block instanceof DoorBlock || block instanceof WebBlock) {
               bl = this.level().destroyBlock(blockPos, true, this) || bl;
               if (block instanceof DoorBlock) {
                  this.playSound(SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR, 1.0F, 1.0F);
               }
            }
         }
      }

      super.aiStep();
   }

   public boolean hasLineOfSight(Entity entity) {
      return !this.getStunnedState() && super.hasLineOfSight(entity);
   }

   protected boolean isImmobile() {
      return super.isImmobile() || this.getStunnedState();
   }

   public void addAdditionalSaveData(CompoundTag nbt) {
      nbt.putBoolean("Stunned", this.isStunned);
      nbt.putBoolean("FinalRoar", this.finalRoar);
      super.addAdditionalSaveData(nbt);
   }

   public void readAdditionalSaveData(CompoundTag nbt) {
      super.readAdditionalSaveData(nbt);
      this.setStunnedState(nbt.getBoolean("Stunned"));
      this.setFinalRoarState(nbt.getBoolean("FinalRoar"));
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(STUNNED, false);
      builder.define(FINAL_ROAR, false);
   }

   public boolean getStunnedState() {
      return (Boolean)this.entityData.get(STUNNED);
   }

   public void setStunnedState(boolean isStunned) {
      this.entityData.set(STUNNED, isStunned);
   }

   public boolean getFinalRoarState() {
      return (Boolean)this.entityData.get(FINAL_ROAR);
   }

   public void setFinalRoarState(boolean hasdoneRoar) {
      this.entityData.set(FINAL_ROAR, hasdoneRoar);
   }

   public IllagerArmPose getArmPose() {
      if (this.isCelebrating()) {
         return IllagerArmPose.CELEBRATING;
      } else {
         return this.isAggressive() ? IllagerArmPose.ATTACKING : IllagerArmPose.NEUTRAL;
      }
   }

   public SoundEvent getCelebrateSound() {
      return SoundEvents.VINDICATOR_CELEBRATE;
   }

   public void tick() {
      super.tick();
      if (this.isAlive()) {
         if (this.getStunnedState()) {
            this.stunTick--;
            if (this.stunTick <= 0) {
               this.setStunnedState(false);
               this.stunTick = 40;
            }
         }
      }
   }

   private List<LivingEntity> getTargets() {
      return this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(8.0), entity -> !(entity instanceof Monster));
   }

   private void knockBack(Entity entity) {
      double d = entity.getX() - this.getX();
      double e = entity.getZ() - this.getZ();
      double f = Math.max(d * d + e * e, 0.001);
      entity.push(d / f * 0.6, 0.4, e / f * 0.6);
   }

   protected void blockedByShield(LivingEntity target) {
      this.knockBack(target);
      target.hurtMarked = true;
      target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 0));
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
      SpawnGroupData entityData2 = super.finalizeSpawn(level, difficulty, spawnReason, entityData);
      ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
      this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
      this.populateDefaultEquipmentEnchantments(level, level.getRandom(), difficulty);
      return entityData2;
   }

   protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficulty) {
      if (this.getCurrentRaid() == null) {
         this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
         this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
      }
   }

   public boolean isAlliedTo(Entity other) {
      return super.isAlliedTo(other)
         || other instanceof LivingEntity livingEntity
            && livingEntity.getType().is(EntityTypeTags.ILLAGER_FRIENDS)
            && this.getTeam() == null
            && other.getTeam() == null;
   }

   public boolean hurt(DamageSource source, float amount) {
      Entity attacker = source.getEntity();
      boolean hasShield = this.getOffhandItem().is(Items.SHIELD);
      if (this.isAggressive()) {
         if (attacker instanceof LivingEntity) {
            ItemStack item = ((LivingEntity)attacker).getMainHandItem();
            ItemStack basherItem = this.getOffhandItem();
            boolean isShield = basherItem.is(Items.SHIELD);
            if ((item.is(ItemTags.AXES) || attacker instanceof IronGolem || this.blockedCount >= 4) && isShield) {
               this.playSound(SoundEvents.SHIELD_BREAK, 1.0F, 1.0F);
               this.setStunnedState(true);
               if (this.level() instanceof ServerLevel) {
                  ((ServerLevel)this.level())
                     .sendParticles(
                        new ItemParticleOption(ParticleTypes.ITEM, basherItem), this.getX(), this.getY() + 1.5, this.getZ(), 30, 0.3, 0.2, 0.3, 0.003
                     );
                  ((ServerLevel)this.level()).sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY() + 1.0, this.getZ(), 30, 0.3, 0.3, 0.3, 0.1);
                  this.playSound(SoundEvents.RAVAGER_ROAR, 1.0F, 1.0F);
                  this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
               }

               this.getTargets().forEach(this::blockedByShield);
               return super.hurt(source, amount);
            }
         }

         if (source.getDirectEntity() instanceof AbstractArrow && hasShield) {
            this.playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 1.0F);
            this.blockedCount++;
            return false;
         }

         if (source.getDirectEntity() instanceof LivingEntity && hasShield) {
            this.blockedCount++;
            this.playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 1.0F);
            return false;
         }
      }

      return super.hurt(source, amount);
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)ModSoundEvents.ILLAGER_BRUTE_AMBIENT_SOUND_EVENT.value();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSoundEvents.ILLAGER_BRUTE_DEATH_SOUND_EVENT.value();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)ModSoundEvents.ILLAGER_BRUTE_HURT_SOUND_EVENT.value();
   }

   public void applyRaidBuffs(ServerLevel level, int wave, boolean unused) {
      ItemStack mainHandItem = new ItemStack(Items.STONE_SWORD);
      ItemStack offHandItem = new ItemStack(Items.SHIELD);
      Raid raid = this.getCurrentRaid();
      if (this.random.nextFloat() <= raid.getEnchantOdds()) {
         int enchantmentLevel = wave > raid.getNumGroups(Difficulty.NORMAL) ? 2 : 1;
         Holder<Enchantment> enchantment = LookupHelper.lookupEnchantment(level, Enchantments.SHARPNESS);
         mainHandItem.enchant(enchantment, enchantmentLevel);
      }

      this.setItemSlot(EquipmentSlot.MAINHAND, mainHandItem);
      this.setItemSlot(EquipmentSlot.OFFHAND, offHandItem);
   }
}
