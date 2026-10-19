package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.data.ESShimmerLacewingVariants;
import cn.leolezury.eternalstarlight.common.entity.living.goal.RandomFlyGoal;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder.Reference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ShimmerLacewing extends Animal implements VariantHolder<Holder<ShimmerLacewingVariant>>, FlyingAnimal {
   private static final String TAG_VARIANT = "variant";
   protected static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(ShimmerLacewing.class, EntityDataSerializers.STRING);
   public AnimationState idleAnimationState = new AnimationState();

   public ResourceLocation getVariantId() {
      return ResourceLocation.parse((String)this.getEntityData().get(VARIANT));
   }

   public void setVariantId(ResourceLocation variant) {
      this.getEntityData().set(VARIANT, variant.toString());
   }

   public void setVariant(Holder<ShimmerLacewingVariant> variant) {
      if (variant.isBound()) {
         ResourceLocation key = this.level()
            .registryAccess()
            .registryOrThrow(ESRegistries.SHIMMER_LACEWING_VARIANT)
            .getKey((ShimmerLacewingVariant)variant.value());
         if (key != null) {
            this.setVariantId(key);
         }
      }
   }

   public Holder<ShimmerLacewingVariant> getVariant() {
      ResourceLocation key = this.getVariantId();
      Registry<ShimmerLacewingVariant> variants = this.level().registryAccess().registryOrThrow(ESRegistries.SHIMMER_LACEWING_VARIANT);
      Optional<Reference<ShimmerLacewingVariant>> optional = variants.getHolder(key);
      return (Holder<ShimmerLacewingVariant>)optional.orElse(
         (Reference<ShimmerLacewingVariant>)variants.getHolder(ESShimmerLacewingVariants.RIVER).orElseThrow()
      );
   }

   public ShimmerLacewing(EntityType<? extends ShimmerLacewing> entityType, Level level) {
      super(entityType, level);
      this.moveControl = new ShimmerLacewing.LacewingMoveControl();
      this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
      this.setPathfindingMalus(PathType.WATER, -1.0F);
      this.setPathfindingMalus(PathType.WATER_BORDER, -1.0F);
      this.setNoGravity(true);
   }

   protected PathNavigation createNavigation(Level level) {
      FlyingPathNavigation pathNavigation = new FlyingPathNavigation(this, level) {
         public boolean isStableDestination(BlockPos blockPos) {
            return this.level.getBlockState(blockPos).isAir();
         }
      };
      pathNavigation.setCanOpenDoors(false);
      pathNavigation.setCanFloat(true);
      pathNavigation.setCanPassDoors(true);
      return pathNavigation;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(VARIANT, ESShimmerLacewingVariants.RIVER.location().toString());
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new RandomFlyGoal(this));
      this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.shimmerLacewing.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.shimmerLacewing.armor())
         .add(Attributes.MOVEMENT_SPEED, 0.3)
         .add(Attributes.FLYING_SPEED, 0.6);
   }

   public void aiStep() {
      super.aiStep();
      if (this.level().isClientSide) {
         this.idleAnimationState.startIfStopped(this.tickCount);
      }
   }

   protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance instance, MobSpawnType spawnType, @Nullable SpawnGroupData data) {
      this.setVariant(ShimmerLacewingVariant.getSpawnVariant(level.registryAccess(), level.getBiome(this.blockPosition())));
      return super.finalizeSpawn(level, instance, spawnType, data);
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.setVariantId((ResourceLocation)ResourceLocation.read(compoundTag.getString("variant")).getOrThrow());
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putString("variant", this.getVariantId().toString());
   }

   public boolean isFood(ItemStack itemStack) {
      return false;
   }

   @Nullable
   public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
      return null;
   }

   public boolean isFlying() {
      return !this.onGround();
   }

   public static boolean checkLacewingSpawnRules(
      EntityType<? extends ShimmerLacewing> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return pos.getY() >= level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, pos).getY() && ESConfig.INSTANCE.mobsConfig.shimmerLacewing.canSpawn();
   }

   private class LacewingMoveControl extends MoveControl {
      public LacewingMoveControl() {
         super(ShimmerLacewing.this);
      }

      public void tick() {
         if (this.operation == Operation.MOVE_TO) {
            Vec3 vec3 = new Vec3(this.wantedX - this.mob.getX(), this.wantedY - this.mob.getY(), this.wantedZ - this.mob.getZ());
            double length = vec3.length();
            double size = this.mob.getBoundingBox().getSize();
            Vec3 delta = vec3.scale(this.speedModifier * 0.025 / length);
            this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(delta));
            if (length < size * 0.5) {
               this.operation = Operation.WAIT;
               this.mob.setDeltaMovement(this.mob.getDeltaMovement().scale(0.2));
            } else if (length >= size) {
               this.mob.setYRot(-((float)Mth.atan2(delta.x, delta.z)) * (180.0F / (float)Math.PI));
            }
         }

         this.mob.setNoGravity(true);
      }
   }
}
