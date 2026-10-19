package twilightforest.entity;

import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFSounds;

public class SlideBlock extends Entity {
   private static final int WARMUP_TIME = 20;
   private static final EntityDataAccessor<Direction> MOVE_DIRECTION = SynchedEntityData.defineId(SlideBlock.class, EntityDataSerializers.DIRECTION);
   private BlockState myState;
   private int slideTime;

   public SlideBlock(EntityType<? extends SlideBlock> type, Level world) {
      super(type, world);
      this.blocksBuilding = true;
      this.myState = ((Block)TFBlocks.SLIDER.get()).defaultBlockState();
   }

   public SlideBlock(EntityType<? extends SlideBlock> type, Level world, double x, double y, double z, BlockState state) {
      super(type, world);
      this.myState = state;
      this.blocksBuilding = true;
      this.setPos(x, y, z);
      this.setDeltaMovement(new Vec3(0.0, 0.0, 0.0));
      this.xo = x;
      this.yo = y;
      this.zo = z;
      this.determineMoveDirection();
   }

   private void determineMoveDirection() {
      BlockPos pos = new BlockPos(this.blockPosition());

      Direction[] toCheck = switch ((Axis)this.myState.getValue(RotatedPillarBlock.AXIS)) {
         case X -> new Direction[]{Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH};
         case Z -> new Direction[]{Direction.DOWN, Direction.UP, Direction.WEST, Direction.EAST};
         case Y -> new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH};
         default -> throw new MatchException(null, null);
      };

      for (Direction e : toCheck) {
         if (this.level().isEmptyBlock(pos.relative(e)) && !this.level().isEmptyBlock(pos.relative(e.getOpposite()))) {
            this.getEntityData().set(MOVE_DIRECTION, e);
            return;
         }
      }

      for (Direction e : toCheck) {
         if (this.level().isEmptyBlock(pos.relative(e))) {
            this.getEntityData().set(MOVE_DIRECTION, e);
            return;
         }
      }
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(MOVE_DIRECTION, Direction.DOWN);
   }

   public boolean isSteppingCarefully() {
      return false;
   }

   public boolean isPickable() {
      return this.isAlive();
   }

   public void tick() {
      if (this.myState != null && !this.myState.isAir()) {
         this.xo = this.getX();
         this.yo = this.getY();
         this.zo = this.getZ();
         this.slideTime++;
         if (this.slideTime > 20) {
            double moveAcceleration = 0.04;
            Direction moveDirection = (Direction)this.getEntityData().get(MOVE_DIRECTION);
            this.setDeltaMovement(
               this.getDeltaMovement().add(moveDirection.getStepX() * 0.04, moveDirection.getStepY() * 0.04, moveDirection.getStepZ() * 0.04)
            );
            this.move(MoverType.SELF, new Vec3(this.getDeltaMovement().x(), this.getDeltaMovement().y(), this.getDeltaMovement().z()));
         }

         this.getDeltaMovement().multiply(0.98, 0.98, 0.98);
         if (!this.level().isClientSide()) {
            if (this.slideTime % 5 == 0) {
               this.playSound((SoundEvent)TFSounds.SLIDER.get(), 1.0F, 0.9F + this.random.nextFloat() * 0.4F);
            }

            BlockPos pos = new BlockPos(this.blockPosition());
            if (this.slideTime == 1) {
               if (this.level().getBlockState(pos) != this.myState) {
                  this.discard();
                  return;
               }

               this.level().removeBlock(pos, false);
            }

            if (this.slideTime == 60) {
               this.setDeltaMovement(new Vec3(0.0, 0.0, 0.0));
               this.getEntityData().set(MOVE_DIRECTION, ((Direction)this.getEntityData().get(MOVE_DIRECTION)).getOpposite());
            }

            if (!this.verticalCollision && !this.horizontalCollision) {
               if (this.slideTime > 100 && (pos.getY() < this.level().getMinBuildHeight() + 1 || pos.getY() > this.level().getMaxBuildHeight())
                  || this.slideTime > 600) {
                  this.spawnAtLocation(new ItemStack(this.myState.getBlock()), 0.0F);
                  this.discard();
               }
            } else {
               this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, 0.7, 0.7));
               this.discard();
               if (this.level().isUnobstructed(this.myState, pos, CollisionContext.empty())) {
                  this.level().setBlockAndUpdate(pos, this.myState);
               } else {
                  this.spawnAtLocation(new ItemStack(this.myState.getBlock()), 0.0F);
               }
            }

            this.damageKnockbackEntities(this.level().getEntities(this, this.getBoundingBox()));
         }
      } else {
         this.discard();
      }
   }

   private void damageKnockbackEntities(List<Entity> entities) {
      for (Entity entity : entities) {
         if (entity instanceof LivingEntity living) {
            living.hurt(TFDamageTypes.getDamageSource(this.level(), TFDamageTypes.SLIDER), 5.0F);
            double kx = (this.getX() - entity.getX()) * 2.0;
            double kz = (this.getZ() - entity.getZ()) * 2.0;
            living.knockback(2.0, kx, kz);
         }
      }
   }

   public boolean displayFireAnimation() {
      return false;
   }

   protected void readAdditionalSaveData(@Nonnull CompoundTag compound) {
      this.slideTime = compound.getInt("Time");
      this.getEntityData().set(MOVE_DIRECTION, Direction.from3DDataValue(compound.getByte("Direction")));
      this.myState = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), compound.getCompound("BlockState"));
   }

   protected void addAdditionalSaveData(@Nonnull CompoundTag compound) {
      compound.putInt("Time", this.slideTime);
      compound.putByte("Direction", (byte)((Direction)this.getEntityData().get(MOVE_DIRECTION)).get3DDataValue());
      compound.put("BlockState", NbtUtils.writeBlockState(this.myState));
   }

   public boolean isPushable() {
      return false;
   }

   public boolean isPushedByFluid() {
      return false;
   }

   protected boolean canRide(Entity entityIn) {
      return false;
   }

   public BlockState getBlockState() {
      return this.myState;
   }
}
