package earth.terrarium.handcrafted.common.entities;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import earth.terrarium.handcrafted.common.blocks.base.SittableBlock;
import earth.terrarium.handcrafted.common.registry.ModEntityTypes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityInLevelCallback;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@MethodsReturnNonnullByDefault
public class Seat extends Entity {
   public static final Multimap<ResourceKey<Level>, BlockPos> SITTING_POSITIONS = ArrayListMultimap.create();
   private AABB shape;
   private boolean remove;
   private boolean canRotate;

   public Seat(EntityType<? extends Entity> entityType, Level level) {
      super(entityType, level);
      this.setLevelCallback(EntityInLevelCallback.NULL);
   }

   public Seat(Level level, AABB shape) {
      super((EntityType)ModEntityTypes.SEAT.get(), level);
      this.shape = copyAABB(shape);
   }

   public static Seat of(Level level, BlockPos pos, Direction dir) {
      BlockState state = level.getBlockState(pos);
      AABB shape = new AABB(pos);
      if (state.getBlock() instanceof SittableBlock seat) {
         shape = seat.getSeatSize(state);
      }

      Seat entity = new Seat(level, shape);
      if (dir != null) {
         entity.setYRot(dir.toYRot());
      } else {
         entity.canRotate = true;
      }

      entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
      return entity;
   }

   private static AABB copyAABB(AABB aabb) {
      return new AABB(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ);
   }

   public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity serverEntity) {
      return new ClientboundAddEntityPacket(this, serverEntity, this.canRotate ? 1 : 0);
   }

   public void recreateFromPacket(ClientboundAddEntityPacket packet) {
      super.recreateFromPacket(packet);
      this.canRotate = packet.getData() == 1;
   }

   public boolean isInvulnerable() {
      return true;
   }

   public boolean isVehicle() {
      return true;
   }

   public boolean shouldRender(double x, double y, double z) {
      return false;
   }

   public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
      Direction facing = this.getDirection();

      for (Direction offset : new Direction[]{facing, facing.getClockWise(), facing.getCounterClockWise(), facing.getOpposite()}) {
         Vec3 safeLocation = DismountHelper.findSafeDismountLocation(passenger.getType(), this.level(), this.blockPosition().relative(offset), false);
         if (safeLocation != null) {
            return safeLocation.add(0.0, 0.25, 0.0);
         }
      }

      return super.getDismountLocationForPassenger(passenger);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide() && (!(this.level().getBlockState(this.blockPosition()).getBlock() instanceof SittableBlock) || this.remove)) {
         this.removeSeat();
      }
   }

   protected void removePassenger(Entity passenger) {
      super.removePassenger(passenger);
      if (!this.level().isClientSide() && this.getPassengers().isEmpty()) {
         this.remove = true;
      }
   }

   public void removeSeat() {
      SITTING_POSITIONS.get(this.level().dimension()).remove(this.blockPosition());
      this.discard();
   }

   protected AABB makeBoundingBox() {
      return this.shape == null ? super.makeBoundingBox() : this.shape.move(this.blockPosition());
   }

   protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float partialTick) {
      return this.shape == null
         ? super.getPassengerAttachmentPoint(entity, dimensions, partialTick)
         : new Vec3(0.0, (float)(this.shape.getYsize() * 0.75) + 0.2F, 0.0);
   }

   protected void clampRotation(Entity entityToUpdate) {
      entityToUpdate.setYBodyRot(this.getYRot());
      float f = Mth.wrapDegrees(entityToUpdate.getYRot() - this.getYRot());
      float g = Mth.clamp(f, -105.0F, 105.0F);
      entityToUpdate.yRotO += g - f;
      entityToUpdate.setYRot(entityToUpdate.getYRot() + g - f);
      entityToUpdate.setYHeadRot(entityToUpdate.getYRot());
   }

   public void onPassengerTurned(Entity entityToUpdate) {
      if (!this.canRotate) {
         this.clampRotation(entityToUpdate);
      }
   }

   public void setLevelCallback(EntityInLevelCallback levelCallback) {
      super.setLevelCallback(new Seat.WrappedLevelCallBack(levelCallback));
   }

   protected void defineSynchedData(Builder builder) {
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
   }

   private class WrappedLevelCallBack implements EntityInLevelCallback {
      private final EntityInLevelCallback callback;

      public WrappedLevelCallBack(EntityInLevelCallback callback) {
         this.callback = callback;
      }

      public void onMove() {
         if (this.callback != null) {
            this.callback.onMove();
            if (Seat.this.level().getBlockState(Seat.this.blockPosition()).getBlock() instanceof SittableBlock seat) {
               Seat.this.shape = seat.getSeatSize(Seat.this.level().getBlockState(Seat.this.blockPosition()));
            }
         } else {
            Seat.this.shape = null;
         }
      }

      public void onRemove(RemovalReason reason) {
         if (this.callback != null) {
            this.callback.onRemove(reason);
         }
      }
   }
}
