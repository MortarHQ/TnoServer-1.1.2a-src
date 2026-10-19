package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.interfaces.Charger;
import cn.leolezury.eternalstarlight.common.entity.living.goal.ChargeAttackGoal;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AuroraDeer extends Animal implements Charger {
   private static final String TAG_LEFT_ANTLER = "left_antler";
   private static final String TAG_RIGHT_ANTLER = "right_antler";
   private static final String TAG_SNOW = "snow";
   private static final String TAG_SNOW_PROGRESS = "snow_progress";
   private static final Ingredient FOOD_ITEMS = Ingredient.of(ESTags.Items.AURORA_DEER_FOOD);
   protected static final EntityDataAccessor<Boolean> LEFT_ANTLER = SynchedEntityData.defineId(AuroraDeer.class, EntityDataSerializers.BOOLEAN);
   protected static final EntityDataAccessor<Boolean> RIGHT_ANTLER = SynchedEntityData.defineId(AuroraDeer.class, EntityDataSerializers.BOOLEAN);
   protected static final EntityDataAccessor<Boolean> SNOW = SynchedEntityData.defineId(AuroraDeer.class, EntityDataSerializers.BOOLEAN);
   public AnimationState idleAnimationState = new AnimationState();
   private boolean charging = false;
   private int notChargingTicks = 200;
   private int antlerBreakCooldown = 0;
   private int snowProgress = 2000;

   public boolean hasLeftAntler() {
      return (Boolean)this.getEntityData().get(LEFT_ANTLER);
   }

   public boolean hasRightAntler() {
      return (Boolean)this.getEntityData().get(RIGHT_ANTLER);
   }

   public boolean hasSnow() {
      return (Boolean)this.getEntityData().get(SNOW);
   }

   public void setHasSnow(boolean hasSnow) {
      this.getEntityData().set(SNOW, hasSnow);
   }

   public AuroraDeer(EntityType<? extends AuroraDeer> entityType, Level level) {
      super(entityType, level);
   }

   @Override
   public void setCharging(boolean charging) {
      this.charging = charging;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(LEFT_ANTLER, true).define(RIGHT_ANTLER, true).define(SNOW, true);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector
         .addGoal(
            1,
            new ChargeAttackGoal(this, false, 2.0F, 80, 80, 0.6F) {
               @Override
               public boolean canUse() {
                  return super.canUse()
                     && AuroraDeer.this.getHealth() / AuroraDeer.this.getMaxHealth() >= 0.5F
                     && (AuroraDeer.this.hasLeftAntler() || AuroraDeer.this.hasRightAntler());
               }
            }
         );
      this.goalSelector
         .addGoal(
            2,
            new PanicGoal(this, 1.25) {
               public boolean canUse() {
                  return super.canUse()
                     && (
                        AuroraDeer.this.getHealth() / AuroraDeer.this.getMaxHealth() < 0.5F
                           || !AuroraDeer.this.hasLeftAntler() && !AuroraDeer.this.hasRightAntler()
                     );
               }
            }
         );
      this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
      this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, FOOD_ITEMS, false));
      this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1));
      this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0) {
         public boolean canUse() {
            return super.canUse() && AuroraDeer.this.notChargingTicks >= 100;
         }
      });
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.targetSelector.addGoal(0, new HurtByTargetGoal(this, new Class[0]));
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.auroraDeer.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.auroraDeer.armor())
         .add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.auroraDeer.attackDamage())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.auroraDeer.followRange())
         .add(Attributes.MOVEMENT_SPEED, 0.25);
   }

   public boolean isFood(ItemStack stack) {
      return FOOD_ITEMS.test(stack);
   }

   public void aiStep() {
      super.aiStep();
      if (!this.level().isClientSide) {
         if (!this.charging) {
            this.notChargingTicks++;
         } else {
            this.notChargingTicks = 0;
            if (this.hasLeftAntler() || this.hasRightAntler()) {
               Vec3 movement = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(1.25);
               AABB box = this.getBoundingBox().expandTowards(movement).inflate(0.75);
               BlockPos fromPos = BlockPos.containing(box.minX + 1.0E-7, box.minY + 1.0E-7, box.minZ + 1.0E-7);
               BlockPos toPos = BlockPos.containing(box.maxX - 1.0E-7, box.maxY - 1.0E-7, box.maxZ - 1.0E-7);
               MutableBlockPos pos = new MutableBlockPos();

               for (int x = fromPos.getX(); x <= toPos.getX(); x++) {
                  for (int y = fromPos.getY(); y <= toPos.getY(); y++) {
                     for (int z = fromPos.getZ(); z <= toPos.getZ(); z++) {
                        pos.set(x, y, z);
                        BlockState state = this.level().getBlockState(pos);
                        if (state.is(BlockTags.LOGS) || state.is(BlockTags.SNAPS_GOAT_HORN) || state.is(ESTags.Blocks.BASE_STONE_STARLIGHT)) {
                           this.randomlyBreakAntler();
                        }
                     }
                  }
               }
            }
         }

         if (this.antlerBreakCooldown > 0) {
            this.antlerBreakCooldown--;
         }

         boolean snow = this.hasSnow();
         if (((Biome)this.level().getBiome(this.blockPosition()).value()).getBaseTemperature() < 0.15) {
            this.snowProgress = Math.min(this.snowProgress + 1, 2000);
         } else {
            this.snowProgress = Math.max(this.snowProgress - 1, 0);
         }

         if (snow && this.snowProgress < 1000) {
            this.setHasSnow(false);
         }

         if (!snow && this.snowProgress >= 1000) {
            this.setHasSnow(true);
         }
      } else {
         this.idleAnimationState.startIfStopped(this.tickCount);
      }
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (this.hasSnow() && stack.is(ItemTags.SHOVELS)) {
         this.setHasSnow(false);
         this.snowProgress = 0;
         int i = 1 + this.getRandom().nextInt(3);

         for (int j = 0; j < i; j++) {
            ItemEntity itemEntity = this.spawnAtLocation(Items.SNOWBALL, 1);
            if (itemEntity != null) {
               itemEntity.setDeltaMovement(
                  itemEntity.getDeltaMovement()
                     .add(
                        (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F,
                        this.getRandom().nextFloat() * 0.05F,
                        (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F
                     )
               );
            }
         }

         stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
         return InteractionResult.sidedSuccess(player.level().isClientSide);
      } else {
         return super.mobInteract(player, hand);
      }
   }

   public void randomlyBreakAntler() {
      if (this.getRandom().nextInt(8) == 0 && this.antlerBreakCooldown <= 0) {
         EntityDataAccessor<Boolean> accessor = this.getRandom().nextBoolean() ? LEFT_ANTLER : RIGHT_ANTLER;
         if (!this.hasLeftAntler()) {
            accessor = RIGHT_ANTLER;
         }

         if (!this.hasRightAntler()) {
            accessor = LEFT_ANTLER;
         }

         if (!this.hasLeftAntler() && !this.hasRightAntler()) {
            return;
         }

         this.getEntityData().set(accessor, false);
         this.antlerBreakCooldown = 200;
         if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY() + this.getBbHeight() / 2.0F, this.getZ(), 2, 0.2, 0.2, 0.2, 0.0);
         }

         this.spawnAtLocation((ItemLike)ESItems.AURORA_DEER_ANTLER.get());
      }
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      if (compoundTag.contains("left_antler", 1)) {
         this.getEntityData().set(LEFT_ANTLER, compoundTag.getBoolean("left_antler"));
      }

      if (compoundTag.contains("right_antler", 1)) {
         this.getEntityData().set(RIGHT_ANTLER, compoundTag.getBoolean("right_antler"));
      }

      if (compoundTag.contains("snow", 1)) {
         this.setHasSnow(compoundTag.getBoolean("snow"));
      }

      if (compoundTag.contains("snow_progress", 3)) {
         this.snowProgress = compoundTag.getInt("snow_progress");
      }
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putBoolean("left_antler", this.hasLeftAntler());
      compoundTag.putBoolean("right_antler", this.hasRightAntler());
      compoundTag.putBoolean("snow", this.hasSnow());
      compoundTag.putInt("snow_progress", this.snowProgress);
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return ESSoundEvents.AURORA_DEER_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return ESSoundEvents.AURORA_DEER_HURT.get();
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return ESSoundEvents.AURORA_DEER_DEATH.get();
   }

   @Nullable
   public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
      return (AgeableMob)ESEntities.AURORA_DEER.get().create(serverLevel);
   }

   public static boolean checkAuroraDeerSpawnRules(
      EntityType<? extends AuroraDeer> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && ESConfig.INSTANCE.mobsConfig.auroraDeer.canSpawn();
   }
}
