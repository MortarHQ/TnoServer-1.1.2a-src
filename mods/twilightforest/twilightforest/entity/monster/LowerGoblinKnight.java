package twilightforest.entity.monster;

import java.util.Objects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.entity.ai.goal.RiderSpearAttackGoal;
import twilightforest.init.TFEntities;
import twilightforest.init.TFSounds;

public class LowerGoblinKnight extends Monster {
   private static final EntityDataAccessor<Boolean> ARMOR = SynchedEntityData.defineId(LowerGoblinKnight.class, EntityDataSerializers.BOOLEAN);
   private static final AttributeModifier ARMOR_MODIFIER = new AttributeModifier(TwilightForestMod.prefix("armor_boost"), 17.0, Operation.ADD_VALUE);

   public LowerGoblinKnight(EntityType<? extends LowerGoblinKnight> type, Level world) {
      super(type, world);
      this.setHasArmor(true);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new RiderSpearAttackGoal(this));
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector
         .addGoal(
            3,
            new MeleeAttackGoal(this, 1.0, false) {
               public boolean canUse() {
                  return this.mob.isVehicle() && this.mob.getPassengers().get(0) instanceof UpperGoblinKnight knight && knight.heavySpearTimer > 0
                     ? false
                     : super.canUse();
               }
            }
         );
      this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, false));
   }

   public static Builder registerAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.28).add(Attributes.ATTACK_DAMAGE, 4.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(ARMOR, false);
   }

   public boolean hasArmor() {
      return (Boolean)this.getEntityData().get(ARMOR);
   }

   private void setHasArmor(boolean flag) {
      this.getEntityData().set(ARMOR, flag);
      if (!this.level().isClientSide()) {
         if (flag) {
            if (!Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).hasModifier(ARMOR_MODIFIER.id())) {
               Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).addTransientModifier(ARMOR_MODIFIER);
            }
         } else {
            Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).removeModifier(ARMOR_MODIFIER.id());
         }
      }
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("hasArmor", this.hasArmor());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setHasArmor(compound.getBoolean("hasArmor"));
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData data) {
      data = super.finalizeSpawn(accessor, difficulty, reason, data);
      UpperGoblinKnight upper = new UpperGoblinKnight((EntityType<? extends UpperGoblinKnight>)TFEntities.UPPER_GOBLIN_KNIGHT.get(), this.level());
      upper.moveTo(this.getX(), this.getY() + 1.0, this.getZ(), this.getYRot(), 0.0F);
      EventHooks.finalizeMobSpawn(upper, accessor, difficulty, MobSpawnType.NATURAL, data);
      upper.startRiding(this);
      return data;
   }

   protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float yRot) {
      return new Vec3(0.0, dimensions.height() * 0.91F, 0.0);
   }

   public boolean doHurtTarget(Entity entity) {
      return this.isVehicle() && this.getPassengers().get(0) instanceof LivingEntity living ? living.doHurtTarget(entity) : super.doHurtTarget(entity);
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return this.hasArmor() ? (SoundEvent)TFSounds.GOBLIN_KNIGHT_MUFFLED_AMBIENT.get() : (SoundEvent)TFSounds.GOBLIN_KNIGHT_AMBIENT.get();
   }

   protected SoundEvent getDeathSound() {
      return this.hasArmor() ? (SoundEvent)TFSounds.GOBLIN_KNIGHT_MUFFLED_DEATH.get() : (SoundEvent)TFSounds.GOBLIN_KNIGHT_DEATH.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return this.hasArmor() ? (SoundEvent)TFSounds.GOBLIN_KNIGHT_MUFFLED_HURT.get() : (SoundEvent)TFSounds.GOBLIN_KNIGHT_HURT.get();
   }

   public boolean hurt(DamageSource source, float amount) {
      Entity attacker = null;
      if (source.getEntity() != null) {
         attacker = source.getEntity();
      }

      if (source.getEntity() != null) {
         attacker = source.getEntity();
      }

      if (attacker != null) {
         double dx = this.getX() - attacker.getX();
         double dz = this.getZ() - attacker.getZ();
         float angle = (float)(Math.atan2(dz, dx) * 180.0 / Math.PI) - 90.0F;
         float difference = Mth.abs((this.yBodyRot - angle) % 360.0F);
         UpperGoblinKnight upper = null;
         if (this.isVehicle() && this.getPassengers().get(0) instanceof UpperGoblinKnight goblin) {
            upper = goblin;
         }

         if (upper != null && upper.hasShield() && difference > 150.0F && difference < 230.0F && upper.takeHitOnShield(source, amount)) {
            return false;
         }

         if (this.hasArmor() && (difference > 300.0F || difference < 60.0F)) {
            this.breakArmor();
         }
      }

      return super.hurt(source, amount);
   }

   public void positionRider(Entity entity, MoveFunction callback) {
      super.positionRider(entity, callback);
      if (entity instanceof UpperGoblinKnight goblin) {
         goblin.setYBodyRot(this.getYRot());
         goblin.setYHeadRot(this.getYRot());
         goblin.setYRot(this.getYRot());
      }
   }

   public void handleEntityEvent(byte id) {
      if (id == 5) {
         ItemStack broken = new ItemStack(Items.IRON_CHESTPLATE);
         this.breakItem(broken);
         this.breakItem(broken);
         this.breakItem(broken);
      } else {
         super.handleEntityEvent(id);
      }
   }

   private void breakArmor() {
      this.level().broadcastEntityEvent(this, (byte)5);
      this.setHasArmor(false);
   }
}
