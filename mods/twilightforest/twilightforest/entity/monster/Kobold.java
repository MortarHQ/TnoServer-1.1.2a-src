package twilightforest.entity.monster;

import java.util.EnumSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.entity.ai.goal.FlockToSameKindGoal;
import twilightforest.entity.ai.goal.PanicOnFlockDeathGoal;
import twilightforest.init.TFSounds;
import twilightforest.network.ParticlePacket;

public class Kobold extends Monster {
   private static final EntityDataAccessor<Boolean> PANICKED = SynchedEntityData.defineId(Kobold.class, EntityDataSerializers.BOOLEAN);
   private int lastEatenBreadTicks;
   private int eatingTime;

   public Kobold(EntityType<? extends Kobold> type, Level world) {
      super(type, world);
      this.setCanPickUpLoot(true);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new PanicOnFlockDeathGoal(this, 2.0F));
      this.goalSelector.addGoal(2, new Kobold.SeekBreadGoal(this));
      this.goalSelector.addGoal(2, new Kobold.RunAwayWhileHoldingBreadGoal(this));
      this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.3F));
      this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(5, new FlockToSameKindGoal(this, 1.0));
      this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new Kobold.KoboldAttackPlayerTarget(this));
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(PANICKED, false);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder registerAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 13.0).add(Attributes.MOVEMENT_SPEED, 0.28).add(Attributes.ATTACK_DAMAGE, 4.0);
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.KOBOLD_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.KOBOLD_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.KOBOLD_DEATH.get();
   }

   public boolean isPanicked() {
      return (Boolean)this.getEntityData().get(PANICKED);
   }

   public void setPanicked(boolean flag) {
      this.getEntityData().set(PANICKED, flag);
   }

   public SoundEvent getEatingSound(ItemStack stack) {
      return (SoundEvent)TFSounds.KOBOLD_MUNCH.get();
   }

   public void aiStep() {
      super.aiStep();
      if (this.level().isClientSide() && this.isPanicked()) {
         for (int i = 0; i < 2; i++) {
            this.level()
               .addParticle(
                  ParticleTypes.SPLASH,
                  this.getX() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth() * 0.5,
                  this.getY() + this.getEyeHeight(),
                  this.getZ() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth() * 0.5,
                  0.0,
                  0.0,
                  0.0
               );
         }
      }

      if (!this.level().isClientSide() && this.isAlive() && this.getItemBySlot(EquipmentSlot.MAINHAND).is(ItemTagGenerator.KOBOLD_PACIFICATION_BREADS)) {
         this.lastEatenBreadTicks++;
         if (this.eatingTime > 0) {
            this.eatingTime--;
         }

         ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
         if (this.canEat(itemstack)) {
            if (this.eatingTime <= 0) {
               ItemStack itemstack1 = itemstack.finishUsingItem(this.level(), this);
               if (!itemstack1.isEmpty()) {
                  this.setItemSlot(EquipmentSlot.MAINHAND, itemstack1);
               }
            }

            if (this.lastEatenBreadTicks > 60 && this.getRandom().nextFloat() < 0.1F) {
               this.playSound(this.getEatingSound(itemstack), 0.75F, 0.9F);
               this.gameEvent(GameEvent.EAT);
               this.level().broadcastEntityEvent(this, (byte)45);
               this.lastEatenBreadTicks = 0;
            }
         }
      }
   }

   public void handleEntityEvent(byte pId) {
      if (pId == 45) {
         ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
         if (!itemstack.isEmpty()) {
            this.spawnItemParticles(itemstack, 8);
         }
      } else {
         super.handleEntityEvent(pId);
      }
   }

   private void spawnItemParticles(ItemStack stack, int amount) {
      ParticleOptions particleOptions = new ItemParticleOption(ParticleTypes.ITEM, stack);
      if (this.level().isClientSide()) {
         for (int i = 0; i < amount; i++) {
            this.getItemParticleVectors(
               (vec31, vec3) -> this.level().addParticle(particleOptions, vec31.x(), vec31.y(), vec31.z(), vec3.x(), vec3.y() + 0.05, vec3.z())
            );
         }
      } else {
         ParticlePacket particlePacket = new ParticlePacket();

         for (int i = 0; i < amount; i++) {
            this.getItemParticleVectors(
               (vec31, vec3) -> particlePacket.queueParticle(
                  particleOptions,
                  false,
                  vec31.x() + vec3.x() * this.random.nextGaussian(),
                  vec31.y() + (vec3.y() + 0.05) * this.random.nextGaussian(),
                  vec31.z() + vec3.z() * this.random.nextGaussian(),
                  0.0,
                  0.0,
                  0.0
               )
            );
         }

         PacketDistributor.sendToPlayersTrackingEntity(this, particlePacket, new CustomPacketPayload[0]);
      }
   }

   private void getItemParticleVectors(BiConsumer<Vec3, Vec3> consumer) {
      Vec3 vec3 = new Vec3((this.getRandom().nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
      vec3 = vec3.xRot(-this.getXRot() * (float) (Math.PI / 180.0));
      vec3 = vec3.yRot(-this.getYHeadRot() * (float) (Math.PI / 180.0));
      double d0 = -this.getRandom().nextFloat() * 0.6 - 0.3;
      Vec3 vec31 = new Vec3((this.getRandom().nextFloat() - 0.5) * 0.3, d0, 0.6);
      vec31 = vec31.xRot(-this.getXRot() * (float) (Math.PI / 180.0));
      vec31 = vec31.yRot(-this.getYHeadRot() * (float) (Math.PI / 180.0));
      vec31 = vec31.add(this.getX(), this.getEyeY(), this.getZ());
      consumer.accept(vec31, vec3);
   }

   private boolean canEat(ItemStack stack) {
      return stack.get(DataComponents.FOOD) != null && !this.isPanicked();
   }

   public boolean canTakeItem(ItemStack stack) {
      EquipmentSlot equipmentslot = this.getEquipmentSlotForItem(stack);
      return !this.getItemBySlot(equipmentslot).isEmpty() ? false : equipmentslot == EquipmentSlot.MAINHAND && super.canTakeItem(stack);
   }

   public boolean canHoldItem(ItemStack stack) {
      return this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && stack.is(ItemTagGenerator.KOBOLD_PACIFICATION_BREADS) && !this.isPanicked();
   }

   protected void pickUpItem(ItemEntity item) {
      ItemStack itemstack = item.getItem();
      if (this.canHoldItem(itemstack)) {
         int i = itemstack.getCount();
         if (i > 1) {
            this.dropItemStack(itemstack.split(i - 1));
         }

         this.onItemPickup(item);
         this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(1));
         this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0F;
         this.take(item, itemstack.getCount());
         this.gameEvent(GameEvent.EQUIP);
         item.discard();
         this.lastEatenBreadTicks = 1;
         this.eatingTime = this.difficultyTime() + this.getRandom().nextInt(600);
         this.setTarget(null);
      }
   }

   private int difficultyTime() {
      switch (this.level().getDifficulty()) {
         case EASY:
            return 400;
         case NORMAL:
            return 200;
         case HARD:
            return 100;
         default:
            return 200;
      }
   }

   private void dropItemStack(ItemStack stack) {
      ItemEntity itementity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), stack);
      this.level().addFreshEntity(itementity);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("EatingTimeLeft", this.eatingTime);
      tag.putInt("TimeSinceBreadLastEaten", this.lastEatenBreadTicks);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.eatingTime = tag.getInt("EatingTimeLeft");
      this.lastEatenBreadTicks = tag.getInt("TimeSinceBreadLastEaten");
   }

   public int getMaxSpawnClusterSize() {
      return 1;
   }

   private static class KoboldAttackPlayerTarget extends NearestAttackableTargetGoal<Player> {
      public KoboldAttackPlayerTarget(Kobold mob) {
         super(mob, Player.class, true);
      }

      public boolean canUse() {
         return this.mob.getItemBySlot(EquipmentSlot.MAINHAND).is(ItemTagGenerator.KOBOLD_PACIFICATION_BREADS) ? false : super.canUse();
      }
   }

   private static class RunAwayWhileHoldingBreadGoal extends AvoidEntityGoal<Player> {
      public RunAwayWhileHoldingBreadGoal(Kobold mob) {
         super(mob, Player.class, 8.0F, 1.5, 1.5);
      }

      public boolean canUse() {
         return this.mob.getItemBySlot(EquipmentSlot.MAINHAND).is(ItemTagGenerator.KOBOLD_PACIFICATION_BREADS) ? super.canUse() : false;
      }
   }

   private static class SeekBreadGoal extends Goal {
      private static final Predicate<ItemEntity> ALLOWED_ITEMS = item -> item.getItem().is(ItemTagGenerator.KOBOLD_PACIFICATION_BREADS);
      private final Kobold mob;

      public SeekBreadGoal(Kobold mob) {
         this.setFlags(EnumSet.of(Flag.MOVE));
         this.mob = mob;
      }

      public boolean canUse() {
         if (!this.mob.getUseItem().isEmpty()) {
            return false;
         }

         if (!this.mob.isPanicked()) {
            if (this.mob.getRandom().nextInt(10) != 0) {
               return false;
            }

            List<ItemEntity> list = this.mob.level().getEntitiesOfClass(ItemEntity.class, this.mob.getBoundingBox().inflate(8.0, 8.0, 8.0), ALLOWED_ITEMS);
            return !list.isEmpty() && this.mob.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
         } else {
            return false;
         }
      }

      public void tick() {
         List<ItemEntity> list = this.mob.level().getEntitiesOfClass(ItemEntity.class, this.mob.getBoundingBox().inflate(8.0, 8.0, 8.0), ALLOWED_ITEMS);
         ItemStack itemstack = this.mob.getItemBySlot(EquipmentSlot.MAINHAND);
         if (itemstack.isEmpty() && !list.isEmpty()) {
            this.mob.getNavigation().moveTo((Entity)list.get(0), 1.2F);
            this.mob.getLookControl().setLookAt(list.get(0).getX(), list.get(0).getY(), list.get(0).getZ());
         }
      }

      public void start() {
         List<ItemEntity> list = this.mob.level().getEntitiesOfClass(ItemEntity.class, this.mob.getBoundingBox().inflate(8.0, 8.0, 8.0), ALLOWED_ITEMS);
         if (!list.isEmpty()) {
            this.mob.getNavigation().moveTo((Entity)list.get(0), 1.2F);
         }
      }
   }
}
