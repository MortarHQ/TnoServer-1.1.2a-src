package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.goal.LonestarSkeletonShootBladeGoal;
import cn.leolezury.eternalstarlight.common.item.combat.ShatteredSwordItem;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class LonestarSkeleton extends Skeleton {
   private final LonestarSkeletonShootBladeGoal bladeGoal = new LonestarSkeletonShootBladeGoal(this, 1.0, 20, 15.0F);
   private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2, false) {
      public void stop() {
         super.stop();
         LonestarSkeleton.this.setAggressive(false);
      }

      public void start() {
         super.start();
         LonestarSkeleton.this.setAggressive(true);
      }
   };

   public LonestarSkeleton(EntityType<? extends LonestarSkeleton> type, Level level) {
      super(type, level);
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.lonestarSkeleton.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.lonestarSkeleton.armor())
         .add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.lonestarSkeleton.attackDamage())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.lonestarSkeleton.followRange())
         .add(Attributes.MOVEMENT_SPEED, 0.25);
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData
   ) {
      SpawnGroupData data = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
      Arrays.fill(this.handDropChances, 0.2F);
      if (!this.getItemBySlot(EquipmentSlot.HEAD).isEmpty() || this.random.nextInt(5) == 0) {
         this.setItemSlot(EquipmentSlot.HEAD, ESItems.AMARAMBER_MASK.get().getDefaultInstance());
      }

      if (!this.getItemBySlot(EquipmentSlot.CHEST).isEmpty() || this.random.nextInt(5) == 0) {
         this.setItemSlot(EquipmentSlot.CHEST, ESItems.AMARAMBER_CHESTPLATE.get().getDefaultInstance());
      }

      return data;
   }

   protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
      super.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
      this.setItemSlot(EquipmentSlot.MAINHAND, ESItems.SHATTERED_SWORD.get().getDefaultInstance());
   }

   protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean bl) {
      if (this.getMainHandItem().is(ESItems.SHATTERED_SWORD.get())) {
         ShatteredSwordItem.setHasBlade(this.getMainHandItem(), true);
      }

      super.dropCustomDeathLoot(serverLevel, damageSource, bl);
   }

   public boolean isLeftHanded() {
      return true;
   }

   public void reassessWeaponGoal() {
   }

   public void onSwitchWeapon() {
      this.goalSelector.removeGoal(this.bladeGoal);
      this.goalSelector.removeGoal(this.meleeGoal);
      if (this.getMainHandItem().is(ESItems.SHATTERED_SWORD.get())) {
         this.goalSelector.addGoal(4, this.bladeGoal);
      } else {
         this.goalSelector.addGoal(4, this.meleeGoal);
      }
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.onSwitchWeapon();
   }

   public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {
      super.setItemSlot(equipmentSlot, itemStack);
      if (!this.level().isClientSide) {
         this.onSwitchWeapon();
      }
   }

   public static boolean checkLonestarSkeletonSpawnRules(
      EntityType<? extends LonestarSkeleton> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return checkMonsterSpawnRules(type, level, spawnType, pos, random) && ESConfig.INSTANCE.mobsConfig.lonestarSkeleton.canSpawn();
   }
}
