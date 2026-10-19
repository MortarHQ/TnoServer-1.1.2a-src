package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.init.ModSoundEvents;
import fuzs.puzzleslib.api.init.v3.registry.LookupHelper;
import fuzs.puzzleslib.api.item.v2.ToolTypeHelper;
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
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.entity.monster.AbstractIllager.IllagerArmPose;
import net.minecraft.world.entity.monster.AbstractIllager.RaiderOpenDoorGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class Basher extends AbstractIllager {
   private static final String TAG_STUN_TICKS = "Stunned";
   private static final EntityDataAccessor<Boolean> DATA_STUNNED = SynchedEntityData.defineId(Basher.class, EntityDataSerializers.BOOLEAN);
   private int stunTicks;
   private int blockedCount;

   public Basher(EntityType<? extends Basher> entityType, Level world) {
      super(entityType, world);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(2, new RaiderOpenDoorGoal(this, this));
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
         super.customServerAiStep();
      }
   }

   public boolean hasLineOfSight(Entity entity) {
      return this.getStunnedState() ? false : super.hasLineOfSight(entity);
   }

   public void addAdditionalSaveData(CompoundTag nbt) {
      nbt.putInt("Stunned", this.stunTicks);
      super.addAdditionalSaveData(nbt);
   }

   public void readAdditionalSaveData(CompoundTag nbt) {
      super.readAdditionalSaveData(nbt);
      this.setStunTicks(nbt.getInt("Stunned"));
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_STUNNED, false);
   }

   public boolean getStunnedState() {
      return (Boolean)this.entityData.get(DATA_STUNNED);
   }

   public void setStunTicks(int stunTicks) {
      this.stunTicks = stunTicks;
      this.entityData.set(DATA_STUNNED, stunTicks > 0);
   }

   public float getWalkTargetValue(BlockPos pos, LevelReader level) {
      return 0.0F;
   }

   public int getMaxSpawnClusterSize() {
      return 1;
   }

   public void tick() {
      super.tick();
      if (this.isAlive()) {
         if (this.stunTicks > 0) {
            this.setStunTicks(this.stunTicks - 1);
         }
      }
   }

   protected boolean isImmobile() {
      return super.isImmobile() || this.getStunnedState();
   }

   public IllagerArmPose getArmPose() {
      if (this.isCelebrating()) {
         return IllagerArmPose.CELEBRATING;
      } else {
         return this.isAggressive() ? IllagerArmPose.ATTACKING : IllagerArmPose.CROSSED;
      }
   }

   public SoundEvent getCelebrateSound() {
      return (SoundEvent)ModSoundEvents.BASHER_CELEBRATE_SOUND_EVENT.value();
   }

   public boolean hurt(DamageSource source, float amount) {
      Entity attacker = source.getEntity();
      boolean hasShield = this.getMainHandItem().is(Items.SHIELD);
      if (this.isAggressive()) {
         if (attacker instanceof LivingEntity) {
            ItemStack attackerMainHand = ((LivingEntity)attacker).getMainHandItem();
            ItemStack basherMainHand = this.getMainHandItem();
            if ((ToolTypeHelper.INSTANCE.isAxe(attackerMainHand) || attacker instanceof IronGolem || this.blockedCount >= 4) && basherMainHand.is(Items.SHIELD)
               )
             {
               this.playSound(SoundEvents.SHIELD_BREAK, 1.0F, 1.0F);
               this.setStunTicks(60);
               if (this.level() instanceof ServerLevel) {
                  ((ServerLevel)this.level())
                     .sendParticles(
                        new ItemParticleOption(ParticleTypes.ITEM, basherMainHand), this.getX(), this.getY() + 1.5, this.getZ(), 30, 0.3, 0.2, 0.3, 0.003
                     );
                  this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_AXE));
               }

               return super.hurt(source, amount);
            }
         }

         if (source.getDirectEntity() instanceof AbstractArrow && hasShield) {
            this.playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 1.0F);
            this.blockedCount++;
            return false;
         }

         if (source.getDirectEntity() instanceof LivingEntity && hasShield) {
            this.playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 1.0F);
            this.blockedCount++;
            return false;
         }
      }

      return super.hurt(source, amount);
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
         this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.SHIELD));
      }
   }

   public boolean isAlliedTo(Entity other) {
      if (super.isAlliedTo(other)) {
         return true;
      } else {
         return other instanceof LivingEntity livingEntity && livingEntity.getType().is(EntityTypeTags.ILLAGER_FRIENDS)
            ? this.getTeam() == null && other.getTeam() == null
            : false;
      }
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)ModSoundEvents.BASHER_AMBIENT_SOUND_EVENT.value();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSoundEvents.BASHER_DEATH_SOUND_EVENT.value();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)ModSoundEvents.BASHER_HURT_SOUND_EVENT.value();
   }

   public void applyRaidBuffs(ServerLevel level, int wave, boolean unused) {
      ItemStack itemStack = new ItemStack(Items.SHIELD);
      Raid raid = this.getCurrentRaid();
      int enchantmentLevel = wave > raid.getNumGroups(Difficulty.NORMAL) ? 2 : 1;
      if (this.random.nextFloat() <= raid.getEnchantOdds()) {
         Holder<Enchantment> enchantment = LookupHelper.lookupEnchantment(level, Enchantments.UNBREAKING);
         itemStack.enchant(enchantment, enchantmentLevel);
      }

      this.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
   }
}
