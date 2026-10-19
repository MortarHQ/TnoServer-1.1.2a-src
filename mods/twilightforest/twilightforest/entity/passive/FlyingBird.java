package twilightforest.entity.passive;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFSounds;

public abstract class FlyingBird extends Bird {
   private static final EntityDataAccessor<Byte> DATA_BIRDFLAGS = SynchedEntityData.defineId(FlyingBird.class, EntityDataSerializers.BYTE);
   @Nullable
   private BlockPos targetPosition;
   private int currentFlightTime;

   public FlyingBird(EntityType<? extends Bird> entity, Level world) {
      super(entity, world);
      this.setIsBirdLanded(true);
      this.setAge(0);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_BIRDFLAGS, (byte)0);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(0, new PanicGoal(this, 1.5));
      this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, Ingredient.of(this.getTemptItems()), true));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
   }

   public float getWalkTargetValue(BlockPos pos) {
      BlockState state = this.level().getBlockState(pos.below());
      if (state.is(BlockTags.LEAVES)) {
         return 200.0F;
      } else if (state.is(BlockTags.LOGS)) {
         return 15.0F;
      } else {
         return state.is(BlockTags.DIRT) ? 9.0F : this.level().getMaxLocalRawBrightness(pos) - 0.5F;
      }
   }

   public void tick() {
      super.tick();
      if (!this.isBirdLanded()) {
         this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.6, 1.0));
      }
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      if (this.isBirdLanded()) {
         this.currentFlightTime = 0;
         boolean flag = this.isSilent();
         if (this.isSpooked()
            || this.isInWater()
            || !this.level().getBlockState(this.blockPosition().below()).getFluidState().isEmpty()
            || this.getRandom().nextInt(200) == 0 && !this.isLandableBlock(this.blockPosition().below())) {
            this.setIsBirdLanded(false);
            if (!flag) {
               this.playSound((SoundEvent)TFSounds.TINY_BIRD_TAKEOFF.get(), 0.05F, this.getVoicePitch());
            }
         }
      } else {
         this.currentFlightTime++;
         if (this.targetPosition != null && (!this.level().isEmptyBlock(this.targetPosition) || this.targetPosition.getY() <= this.level().getMinBuildHeight())
            )
          {
            this.targetPosition = null;
         }

         if (this.isInWater() || !this.level().getBlockState(this.blockPosition().below()).getFluidState().isEmpty()) {
            this.currentFlightTime = 0;
            this.setDeltaMovement(this.getDeltaMovement().x(), 0.1F, this.getDeltaMovement().z());
         }

         if (this.targetPosition == null || this.getRandom().nextInt(30) == 0 || this.targetPosition.closerToCenterThan(this.position(), 2.0)) {
            int yTarget = this.currentFlightTime < 100 ? 2 : 4;
            this.targetPosition = BlockPos.containing(
               this.getX() + this.getRandom().nextInt(7) - this.getRandom().nextInt(7),
               this.getY() + this.getRandom().nextInt(6) - yTarget,
               this.getZ() + this.getRandom().nextInt(7) - this.getRandom().nextInt(7)
            );
         }

         double d2 = this.targetPosition.getX() + 0.5 - this.getX();
         double d0 = this.targetPosition.getY() + 0.1 - this.getY();
         double d1 = this.targetPosition.getZ() + 0.5 - this.getZ();
         Vec3 vec3 = this.getDeltaMovement();
         Vec3 vec31 = vec3.add((Math.signum(d2) * 0.5 - vec3.x()) * 0.1F, (Math.signum(d0) * 0.7F - vec3.y()) * 0.1F, (Math.signum(d1) * 0.5 - vec3.z()) * 0.1F);
         this.setDeltaMovement(vec31);
         float f = (float)(Mth.atan2(vec31.z(), vec31.x()) * 180.0F / (float)Math.PI) - 90.0F;
         float f1 = Mth.wrapDegrees(f - this.getYRot());
         this.zza = 0.5F;
         this.setYRot(this.getYRot() + f1);
         if (this.getRandom().nextInt(10) == 0 && this.isLandableBlock(this.blockPosition().below())) {
            this.setIsBirdLanded(true);
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.0, 1.0));
         }
      }
   }

   public boolean isLandableBlock(BlockPos pos) {
      BlockState state = this.level().getBlockState(pos);
      return !state.isAir() && (state.is(BlockTags.LEAVES) || state.isFaceSturdy(this.level(), pos, Direction.UP));
   }

   @Override
   public boolean isBirdLanded() {
      return ((Byte)this.getEntityData().get(DATA_BIRDFLAGS) & 1) != 0;
   }

   public void setIsBirdLanded(boolean landed) {
      byte flags = (Byte)this.getEntityData().get(DATA_BIRDFLAGS);
      this.getEntityData().set(DATA_BIRDFLAGS, (byte)(landed ? flags | 1 : flags & -2));
   }

   public boolean isPushable() {
      return false;
   }

   protected void doPush(Entity entity) {
   }

   public boolean isIgnoringBlockTriggers() {
      return true;
   }

   protected boolean canRide(Entity entity) {
      return false;
   }

   protected void pushEntities() {
   }

   public boolean isBaby() {
      return false;
   }

   public abstract boolean isSpooked();

   public abstract TagKey<Item> getTemptItems();
}
