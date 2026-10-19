package cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.entity.living.goal.AstralGolemRandomStrollNearVillageGoal;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AstralGolem extends AbstractGolem implements NeutralMob {
   private static final String TAG_MATERIAL = "material";
   private static final String TAG_HOME_X = "home_x";
   private static final String TAG_HOME_Y = "home_y";
   private static final String TAG_HOME_Z = "home_z";
   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
   private int remainingPersistentAngerTime;
   @Nullable
   private UUID persistentAngerTarget;
   private int attackAnimationTick;
   public BlockPos homePos = BlockPos.ZERO;
   protected static final EntityDataAccessor<String> MATERIAL = SynchedEntityData.defineId(AstralGolem.class, EntityDataSerializers.STRING);
   protected static final EntityDataAccessor<Boolean> BLOCKING = SynchedEntityData.defineId(AstralGolem.class, EntityDataSerializers.BOOLEAN);

   public AstralGolem(EntityType<? extends AstralGolem> golem, Level level) {
      super(golem, level);
      this.moveControl = new FlyingMoveControl(this, 20, true);
   }

   public int getAttackAnimationTick() {
      return this.attackAnimationTick;
   }

   public ResourceLocation getMaterialId() {
      return ResourceLocation.parse((String)this.getEntityData().get(MATERIAL));
   }

   public void setMaterialId(ResourceLocation material) {
      this.getEntityData().set(MATERIAL, material.toString());
   }

   public boolean isGolemBlocking() {
      return (Boolean)this.getEntityData().get(BLOCKING);
   }

   public void setBlocking(boolean blocking) {
      this.getEntityData().set(BLOCKING, blocking);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(MATERIAL, "null").define(BLOCKING, false);
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.setMaterialId((ResourceLocation)ResourceLocation.read(compoundTag.getString("material")).getOrThrow());
      this.homePos = new BlockPos(compoundTag.getInt("home_x"), compoundTag.getInt("home_y"), compoundTag.getInt("home_z"));
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putString("material", this.getMaterialId().toString());
      compoundTag.putInt("home_x", this.homePos.getX());
      compoundTag.putInt("home_y", this.homePos.getY());
      compoundTag.putInt("home_z", this.homePos.getZ());
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 2.0, true));
      this.goalSelector.addGoal(2, new AstralGolemRandomStrollNearVillageGoal(this));
      this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, LivingEntity.class, 32.0F));
      this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(0, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, 10, true, false, this::isAngryAt));
      this.targetSelector
         .addGoal(2, new NearestAttackableTargetGoal(this, Mob.class, 5, false, false, entity -> entity instanceof Enemy && !(entity instanceof Creeper)));
      this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal(this, false));
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.astralGolem.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.astralGolem.armor())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.astralGolem.followRange())
         .add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.astralGolem.attackDamage())
         .add(Attributes.ATTACK_KNOCKBACK, 3.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.FLYING_SPEED, 1.0)
         .add(Attributes.MOVEMENT_SPEED, 0.3);
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance instance, MobSpawnType spawnType, @Nullable SpawnGroupData data) {
      this.homePos = this.blockPosition();
      return super.finalizeSpawn(level, instance, spawnType, data);
   }

   public AstralGolemMaterial getMaterial() {
      return (AstralGolemMaterial)this.level().registryAccess().registryOrThrow(ESRegistries.ASTRAL_GOLEM_MATERIAL).get(this.getMaterialId());
   }

   public void setMaterial(AstralGolemMaterial material) {
      ResourceLocation key = this.level().registryAccess().registryOrThrow(ESRegistries.ASTRAL_GOLEM_MATERIAL).getKey(material);
      if (key != null) {
         this.setMaterialId(key);
      }
   }

   private boolean isValidRepairMaterial(ItemStack stack) {
      Item material = Items.IRON_INGOT;
      if (this.getMaterial() != null && this.getMaterial().material().isBound()) {
         material = (Item)this.getMaterial().material().value();
      }

      return stack.is(material);
   }

   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      if (!this.isValidRepairMaterial(itemstack)) {
         return InteractionResult.PASS;
      }

      float f = this.getHealth();
      this.heal(25.0F);
      if (this.getHealth() == f) {
         return InteractionResult.PASS;
      }

      float f1 = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
      this.playSound(ESSoundEvents.ASTRAL_GOLEM_REPAIR.get(), 1.0F, f1);
      if (!player.hasInfiniteMaterials()) {
         itemstack.shrink(1);
         Boarwarf.setBoarwarfCredit(player, Boarwarf.getBoarwarfCredit(player) + 10);
      }

      return InteractionResult.sidedSuccess(this.level().isClientSide);
   }

   protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
   }

   public void aiStep() {
      super.aiStep();
      if (this.isLeftHanded()) {
         this.setLeftHanded(false);
      }

      if (this.attackAnimationTick > 0) {
         this.attackAnimationTick--;
      }

      if (!this.level().isClientSide()) {
         if (this.getTarget() == null) {
            this.setBlocking(false);
            this.setNoGravity(false);
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -this.getDeltaMovement().y / 3.0, 0.0));
         } else {
            LivingEntity target = this.getTarget();
            double reachSqr = 3.0F * (this.getBbWidth() * 2.0F * this.getBbWidth() * 2.0F + this.getTarget().getBbWidth());
            this.setBlocking(ESPlatform.INSTANCE.isShield(this.getOffhandItem()) && this.distanceToSqr(target) >= reachSqr + 2.0);
         }

         if (this.isGolemBlocking() && !this.isBlocking()) {
            this.startUsingItem(InteractionHand.OFF_HAND);
         }

         if (!this.isGolemBlocking() && this.isBlocking()) {
            this.stopUsingItem();
         }
      } else {
         this.level()
            .addParticle(
               ParticleTypes.FLAME,
               this.getX() + (this.random.nextDouble() - 0.5) * 0.2,
               this.getY() + 0.5 + (this.random.nextDouble() - 0.5) * 0.2,
               this.getZ() + (this.random.nextDouble() - 0.5) * 0.2,
               0.0,
               -0.2,
               0.0
            );
      }
   }

   private float getAttackDamage() {
      return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * (this.getMaterial() == null ? 1.0F : this.getMaterial().attackDamageMultiplier());
   }

   public boolean doHurtTarget(Entity target) {
      if (this.isGolemBlocking()) {
         return false;
      }

      this.attackAnimationTick = 10;
      this.level().broadcastEntityEvent(this, (byte)4);
      float f = this.getAttackDamage();
      DamageSource damageSource = this.damageSources().mobAttack(this);
      if (this.level() instanceof ServerLevel serverLevel) {
         f = EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), target, damageSource, f);
      }

      boolean bl = target.hurt(damageSource, f);
      if (bl) {
         float g = this.getKnockback(target, damageSource);
         if (g > 0.0F && target instanceof LivingEntity livingEntity) {
            livingEntity.knockback(g * 0.5F, Mth.sin(this.getYRot() * (float) (Math.PI / 180.0)), -Mth.cos(this.getYRot() * (float) (Math.PI / 180.0)));
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
         }

         if (this.level() instanceof ServerLevel serverLevel2) {
            EnchantmentHelper.doPostAttackEffects(serverLevel2, target, damageSource);
         }

         this.setLastHurtMob(target);
         this.playAttackSound();
      }

      return bl;
   }

   public boolean hurt(DamageSource source, float amount) {
      float f = this.getMaterial() == null ? 1.0F : this.getMaterial().defenseMultiplier();
      if (source.getEntity() instanceof Player player && !player.hasInfiniteMaterials() && Boarwarf.getBoarwarfCredit(player) > -10000) {
         Boarwarf.setBoarwarfCredit(player, (int)(Boarwarf.getBoarwarfCredit(player) - amount));
      }

      return super.hurt(source, amount / f);
   }

   public void die(DamageSource source) {
      if (source.getEntity() instanceof Player player && !player.hasInfiniteMaterials() && Boarwarf.getBoarwarfCredit(player) > -10000) {
         Boarwarf.setBoarwarfCredit(player, Boarwarf.getBoarwarfCredit(player) - 10);
      }

      super.die(source);
   }

   public void handleEntityEvent(byte event) {
      if (event == 4) {
         this.attackAnimationTick = 10;
         this.playSound(ESSoundEvents.ASTRAL_GOLEM_ATTACK.get(), 1.0F, 1.0F);
      } else {
         super.handleEntityEvent(event);
      }
   }

   protected PathNavigation createNavigation(Level level) {
      FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);
      navigation.setCanOpenDoors(true);
      navigation.setCanFloat(true);
      navigation.setCanPassDoors(true);
      return navigation;
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return this.isDamageSourceBlocked(source) ? SoundEvents.SHIELD_BLOCK : ESSoundEvents.ASTRAL_GOLEM_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return ESSoundEvents.ASTRAL_GOLEM_DEATH.get();
   }

   public void startPersistentAngerTimer() {
      this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
   }

   public void setRemainingPersistentAngerTime(int time) {
      this.remainingPersistentAngerTime = time;
   }

   public int getRemainingPersistentAngerTime() {
      return this.remainingPersistentAngerTime;
   }

   public void setPersistentAngerTarget(@Nullable UUID target) {
      this.persistentAngerTarget = target;
   }

   @Nullable
   public UUID getPersistentAngerTarget() {
      return this.persistentAngerTarget;
   }

   public static boolean checkAstralGolemSpawnRules(
      EntityType<? extends AstralGolem> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return checkMobSpawnRules(type, level, spawnType, pos, random) && ESConfig.INSTANCE.mobsConfig.astralGolem.canSpawn();
   }
}
