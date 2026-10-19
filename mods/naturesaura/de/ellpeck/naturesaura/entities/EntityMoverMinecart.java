package de.ellpeck.naturesaura.entities;

import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.items.ModItems;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class EntityMoverMinecart extends Minecart {
   private final List<BlockPos> spotOffsets = new ArrayList<>();
   public boolean isActive;
   private BlockPos lastPosition = BlockPos.ZERO;

   public EntityMoverMinecart(EntityType<?> type, Level level) {
      super(type, level);
   }

   public EntityMoverMinecart(EntityType<?> type, Level level, double x, double y, double z) {
      super(type, level);
      this.setPos(x, y, z);
      this.xo = x;
      this.yo = y;
      this.zo = z;
   }

   public void moveMinecartOnRail(BlockPos railPos) {
      super.moveMinecartOnRail(railPos);
      if (this.isActive) {
         BlockPos pos = this.blockPosition();
         if (!this.spotOffsets.isEmpty() && this.level().getGameTime() % 10L == 0L) {
            PacketHandler.sendToAllAround(
               this.level(),
               pos,
               32,
               new PacketParticles(
                  (float)this.getX(),
                  (float)this.getY(),
                  (float)this.getZ(),
                  PacketParticles.Type.MOVER_CART,
                  Mth.floor(this.getDeltaMovement().x * 100.0),
                  Mth.floor(this.getDeltaMovement().y * 100.0),
                  Mth.floor(this.getDeltaMovement().z * 100.0)
               )
            );
         }

         if (!(pos.distSqr(this.lastPosition) < 64.0)) {
            this.moveAura(this.level(), this.lastPosition, this.level(), pos);
            this.lastPosition = pos;
         }
      }
   }

   private void moveAura(Level oldLevel, BlockPos oldPos, Level newLevel, BlockPos newPos) {
      for (BlockPos offset : this.spotOffsets) {
         BlockPos spot = oldPos.offset(offset);
         IAuraChunk chunk = IAuraChunk.getAuraChunk(oldLevel, spot);
         int amount = chunk.getDrainSpot(spot);
         if (amount > 0) {
            int toMove = Math.min(amount, 300000);
            int drained = chunk.drainAura(spot, toMove, false, false);
            if (drained > 0) {
               int toLose = Mth.ceil(drained / 250.0F);
               BlockPos newSpot = newPos.offset(offset);
               IAuraChunk.getAuraChunk(newLevel, newSpot).storeAura(newSpot, drained - toLose, false, false);
            }
         }
      }
   }

   public void activateMinecart(int x, int y, int z, boolean receivingPower) {
      if (this.isActive != receivingPower) {
         this.isActive = receivingPower;
         BlockPos pos = this.blockPosition();
         if (!this.isActive) {
            this.moveAura(this.level(), this.lastPosition, this.level(), pos);
            this.spotOffsets.clear();
            this.lastPosition = BlockPos.ZERO;
            return;
         }

         IAuraChunk.getSpotsInArea(this.level(), pos, 25, (spot, amount) -> {
            if (amount > 0) {
               this.spotOffsets.add(spot.subtract(pos));
            }
         });
         this.lastPosition = pos;
      }
   }

   public void destroy(DamageSource source) {
      this.kill();
      if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
         this.spawnAtLocation(new ItemStack(ModItems.MOVER_CART), 0.0F);
      }
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("active", this.isActive);
      compound.putLong("last_pos", this.lastPosition.asLong());
      ListTag list = new ListTag();

      for (BlockPos offset : this.spotOffsets) {
         list.add(LongTag.valueOf(offset.asLong()));
      }

      compound.put("offsets", list);
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.isActive = compound.getBoolean("active");
      this.lastPosition = BlockPos.of(compound.getLong("last_pos"));
      this.spotOffsets.clear();

      for (Tag base : compound.getList("offsets", 4)) {
         this.spotOffsets.add(BlockPos.of(((LongTag)base).getAsLong()));
      }
   }

   @Nullable
   public Entity changeDimension(DimensionTransition transition) {
      Entity entity = super.changeDimension(transition);
      if (entity instanceof EntityMoverMinecart) {
         BlockPos pos = entity.blockPosition();
         this.moveAura(this.level(), this.lastPosition, entity.level(), pos);
         ((EntityMoverMinecart)entity).lastPosition = pos;
      }

      return entity;
   }

   public BlockState getDisplayBlockState() {
      return Blocks.STONE.defaultBlockState();
   }

   public ItemStack getPickResult() {
      return new ItemStack(ModItems.MOVER_CART);
   }

   public ItemStack getPickedResult(HitResult target) {
      return new ItemStack(ModItems.MOVER_CART);
   }

   public boolean canBeRidden() {
      return false;
   }

   protected void applyNaturalSlowdown() {
      Vec3 motion = this.getDeltaMovement();
      this.setDeltaMovement(motion.x * 0.99F, 0.0, motion.z * 0.99F);
   }

   public InteractionResult interact(Player p_38483_, InteractionHand p_38484_) {
      return InteractionResult.PASS;
   }
}
