package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.container.BasicAuraContainer;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class BlockEntityEndFlower extends BlockEntityImpl implements ITickableBlockEntity {
   public final BasicAuraContainer container = new BasicAuraContainer(null, 500000) {
      {
         this.aura = this.maxAura;
      }

      @Override
      public int storeAura(int amountToStore, boolean simulate) {
         return 0;
      }

      @Override
      public int drainAura(int amountToDrain, boolean simulate) {
         int amount = super.drainAura(amountToDrain, simulate);
         if (amount > 0 && !simulate) {
            BlockEntityEndFlower.this.sendToClients();
         }

         return amount;
      }

      @Override
      public int getAuraColor() {
         return 6956509;
      }
   };
   public boolean isDrainMode;

   public BlockEntityEndFlower(BlockPos pos, BlockState state) {
      super(ModBlockEntities.END_FLOWER, pos, state);
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide) {
         if (this.level.getGameTime() % 10L != 0L) {
            return;
         }

         if (!this.isDrainMode) {
            for (ItemEntity item : this.level.getEntitiesOfClass(ItemEntity.class, new AABB(this.worldPosition).inflate(1.0), Entity::isAlive)) {
               if (!item.hasPickUpDelay()) {
                  ItemStack stack = item.getItem();
                  if (stack.getCount() == 1 && stack.getItem() == Items.ENDER_EYE) {
                     this.isDrainMode = true;
                     item.kill();
                     PacketHandler.sendToAllAround(
                        this.level,
                        this.worldPosition,
                        32,
                        new PacketParticles(
                           (float)item.getX(), (float)item.getY(), (float)item.getZ(), PacketParticles.Type.END_FLOWER_CONSUME, this.container.getAuraColor()
                        )
                     );
                     break;
                  }
               }
            }
         } else {
            int toDrain = Math.min(5000, this.container.getStoredAura());
            this.container.drainAura(toDrain, false);
            this.generateAura(toDrain);
            if (this.container.getStoredAura() <= 0) {
               this.level.setBlockAndUpdate(this.worldPosition, Blocks.DEAD_BUSH.defaultBlockState());
               PacketHandler.sendToAllAround(
                  this.level,
                  this.worldPosition,
                  32,
                  new PacketParticles(
                     this.worldPosition.getX(),
                     this.worldPosition.getY(),
                     this.worldPosition.getZ(),
                     PacketParticles.Type.END_FLOWER_DECAY,
                     this.container.getAuraColor()
                  )
               );
            }
         }
      } else if (this.isDrainMode && this.level.getGameTime() % 5L == 0L) {
         NaturesAuraAPI.instance()
            .spawnMagicParticle(
               this.worldPosition.getX() + 0.25F + this.level.random.nextFloat() * 0.5F,
               this.worldPosition.getY() + 0.25F + this.level.random.nextFloat() * 0.5F,
               this.worldPosition.getZ() + 0.25F + this.level.random.nextFloat() * 0.5F,
               this.level.random.nextGaussian() * 0.05F,
               this.level.random.nextFloat() * 0.1F,
               this.level.random.nextGaussian() * 0.05F,
               this.container.getAuraColor(),
               this.level.random.nextFloat() * 2.0F + 1.0F,
               50,
               0.0F,
               false,
               true
            );
      }
   }

   @Override
   public void writeNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.writeNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         this.container.writeNBT(compound);
         compound.putBoolean("drain_mode", this.isDrainMode);
      }
   }

   @Override
   public void readNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.readNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         this.container.readNBT(compound);
         this.isDrainMode = compound.getBoolean("drain_mode");
      }
   }
}
