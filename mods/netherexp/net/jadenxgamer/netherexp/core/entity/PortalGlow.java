package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class PortalGlow extends Entity {
   private static final EntityDataAccessor<Integer> DATA_WIDTH = SynchedEntityData.defineId(PortalGlow.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> DATA_HEIGHT = SynchedEntityData.defineId(PortalGlow.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<String> DATA_AXIS = SynchedEntityData.defineId(PortalGlow.class, EntityDataSerializers.STRING);

   public PortalGlow(EntityType<?> entityType, Level level) {
      super(entityType, level);
      this.noPhysics = true;
      this.setNoGravity(true);
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(DATA_WIDTH, 1);
      builder.define(DATA_HEIGHT, 1);
      builder.define(DATA_AXIS, Axis.X.name());
   }

   public void setPortalDimensions(int width, int height, Axis axis) {
      this.entityData.set(DATA_WIDTH, width);
      this.entityData.set(DATA_HEIGHT, height);
      this.entityData.set(DATA_AXIS, axis.name());
      this.updateBoundingBox();
   }

   private void updateBoundingBox() {
      this.setBoundingBox(this.makeBoundingBox());
   }

   protected AABB makeBoundingBox() {
      int width = (Integer)this.entityData.get(DATA_WIDTH);
      int height = (Integer)this.entityData.get(DATA_HEIGHT);
      Axis axis = Axis.valueOf((String)this.entityData.get(DATA_AXIS));
      double halfWidth = width / 2.0;
      double halfHeight = height / 2.0;
      double depth = 0.5;
      double minX;
      double minZ;
      double maxX;
      double maxZ;
      if (axis == Axis.X) {
         minX = this.getX() - halfWidth;
         maxX = this.getX() + halfWidth;
         minZ = this.getZ() - depth;
         maxZ = this.getZ() + depth;
      } else {
         minX = this.getX() - depth;
         maxX = this.getX() + depth;
         minZ = this.getZ() - halfWidth;
         maxZ = this.getZ() + halfWidth;
      }

      double minY = this.getY() - halfHeight;
      double maxY = this.getY() + halfHeight;
      return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
      if (key.equals(DATA_WIDTH) || key.equals(DATA_HEIGHT) || key.equals(DATA_AXIS)) {
         this.updateBoundingBox();
      }
   }

   public void tick() {
      if (!this.level().isClientSide) {
         int width = (Integer)this.entityData.get(DATA_WIDTH);
         int height = (Integer)this.entityData.get(DATA_HEIGHT);
         Axis axis = Axis.valueOf((String)this.entityData.get(DATA_AXIS));
         BlockPos topLeft = this.getTopLeftBlockPos(axis, width, height);
         if (!(this.level().getBlockState(topLeft).getBlock() instanceof NetherPortalBlock)) {
            this.discard();
         }
      }
   }

   @NotNull
   private BlockPos getTopLeftBlockPos(Axis axis, int width, int height) {
      double topLeftY = this.getY() + (height - 1) / 2.0;
      double topLeftX;
      double topLeftZ;
      if (axis == Axis.X) {
         topLeftX = this.getX() - (width - 1) / 2.0;
         topLeftZ = this.getZ();
      } else {
         topLeftX = this.getX();
         topLeftZ = this.getZ() - (width - 1) / 2.0;
      }

      return BlockPos.containing(topLeftX, topLeftY, topLeftZ);
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      this.entityData.set(DATA_WIDTH, compound.getInt("PortalWidth"));
      this.entityData.set(DATA_HEIGHT, compound.getInt("PortalHeight"));
      this.entityData.set(DATA_AXIS, compound.getString("PortalAxis"));
      this.updateBoundingBox();
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      compound.putInt("PortalWidth", (Integer)this.entityData.get(DATA_WIDTH));
      compound.putInt("PortalHeight", (Integer)this.entityData.get(DATA_HEIGHT));
      compound.putString("PortalAxis", (String)this.entityData.get(DATA_AXIS));
   }

   public PushReaction getPistonPushReaction() {
      return PushReaction.IGNORE;
   }

   public boolean isIgnoringBlockTriggers() {
      return true;
   }

   public boolean isPickable() {
      return false;
   }

   public boolean isAttackable() {
      return false;
   }

   public static void spawnForPortal(Level level, BlockPos portalBlockPos) {
      if (!level.isClientSide) {
         BlockState state = level.getBlockState(portalBlockPos);
         if (level.getBlockState(portalBlockPos).is(Blocks.NETHER_PORTAL)) {
            Axis axis = (Axis)state.getValue(NetherPortalBlock.AXIS);
            int minX = portalBlockPos.getX();
            int minY = portalBlockPos.getY();
            int minZ = portalBlockPos.getZ();
            int maxX = minX;
            int maxY = minY;
            int maxZ = minZ;
            if (axis == Axis.X) {
               while (level.getBlockState(new BlockPos(minX - 1, minY, minZ)).getBlock() instanceof NetherPortalBlock) {
                  minX--;
               }

               while (level.getBlockState(new BlockPos(maxX + 1, minY, minZ)).getBlock() instanceof NetherPortalBlock) {
                  maxX++;
               }
            } else {
               while (level.getBlockState(new BlockPos(minX, minY, minZ - 1)).getBlock() instanceof NetherPortalBlock) {
                  minZ--;
               }

               while (level.getBlockState(new BlockPos(minX, minY, maxZ + 1)).getBlock() instanceof NetherPortalBlock) {
                  maxZ++;
               }
            }

            while (level.getBlockState(new BlockPos(minX, maxY + 1, minZ)).getBlock() instanceof NetherPortalBlock) {
               maxY++;
            }

            while (level.getBlockState(new BlockPos(minX, minY - 1, minZ)).getBlock() instanceof NetherPortalBlock) {
               minY--;
            }

            int width = axis == Axis.X ? maxX - minX + 1 : maxZ - minZ + 1;
            int height = maxY - minY + 1;
            double centerX;
            double centerY;
            double centerZ;
            if (axis == Axis.X) {
               centerX = minX + (width - 1) / 2.0 + 0.5;
               centerY = minY + (height - 1) / 2.0 + 0.5;
               centerZ = minZ + 0.5;
            } else {
               centerX = minX + 0.5;
               centerY = minY + (height - 1) / 2.0 + 0.5;
               centerZ = minZ + (width - 1) / 2.0 + 0.5;
            }

            BlockPos topLeft = new BlockPos(minX, maxY, minZ);
            if (level.getEntitiesOfClass(PortalGlow.class, new AABB(topLeft).inflate(0.5)).isEmpty()) {
               PortalGlow glow = new PortalGlow(JNEEntityType.PORTAL_GLOW.get(), level);
               glow.setPos(centerX, centerY, centerZ);
               glow.setPortalDimensions(width, height, axis);
               level.addFreshEntity(glow);
            }
         }
      }
   }

   public int getPortalWidth() {
      return (Integer)this.entityData.get(DATA_WIDTH);
   }

   public int getPortalHeight() {
      return (Integer)this.entityData.get(DATA_HEIGHT);
   }

   public Axis getPortalAxis() {
      return Axis.valueOf((String)this.entityData.get(DATA_AXIS));
   }
}
