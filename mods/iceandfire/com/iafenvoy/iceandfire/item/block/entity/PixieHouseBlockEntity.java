package com.iafenvoy.iceandfire.item.block.entity;

import com.iafenvoy.iceandfire.entity.PixieEntity;
import com.iafenvoy.iceandfire.network.payload.UpdatePixieHouseS2CPayload;
import com.iafenvoy.iceandfire.registry.IafBlockEntities;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.registry.IafParticles;
import com.iafenvoy.uranus.ServerHelper;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PixieHouseBlockEntity extends BlockEntity {
   private static final float PARTICLE_WIDTH = 0.3F;
   private static final float PARTICLE_HEIGHT = 0.6F;
   private final Random rand;
   public int houseType;
   public boolean hasPixie;
   public boolean tamedPixie;
   public UUID pixieOwnerUUID;
   public int pixieType;
   public NonNullList<ItemStack> pixieItems = NonNullList.withSize(1, ItemStack.EMPTY);

   public PixieHouseBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)IafBlockEntities.PIXIE_HOUSE.get(), pos, state);
      this.rand = new Random();
   }

   public static int getHouseTypeFromBlock(Block block) {
      if (block == IafBlocks.PIXIE_HOUSE_MUSHROOM_RED.get()) {
         return 1;
      } else if (block == IafBlocks.PIXIE_HOUSE_MUSHROOM_BROWN.get()) {
         return 0;
      } else if (block == IafBlocks.PIXIE_HOUSE_OAK.get()) {
         return 3;
      } else if (block == IafBlocks.PIXIE_HOUSE_BIRCH.get()) {
         return 2;
      } else if (block == IafBlocks.PIXIE_HOUSE_SPRUCE.get()) {
         return 5;
      } else {
         return block == IafBlocks.PIXIE_HOUSE_DARK_OAK.get() ? 4 : 0;
      }
   }

   public static void tickClient(Level level, BlockPos pos, BlockState state, PixieHouseBlockEntity entityPixieHouse) {
      if (entityPixieHouse.hasPixie) {
         level.addParticle(
            (ParticleOptions)IafParticles.PIXIE_DUST.get(),
            (double)(pos.getX() + 0.5F) + entityPixieHouse.rand.nextFloat() * 0.3F * 2.0F - 0.3F,
            (double)pos.getY() + entityPixieHouse.rand.nextFloat() * 0.6F,
            (double)(pos.getZ() + 0.5F) + entityPixieHouse.rand.nextFloat() * 0.3F * 2.0F - 0.3F,
            PixieEntity.PARTICLE_RGB[entityPixieHouse.pixieType][0],
            PixieEntity.PARTICLE_RGB[entityPixieHouse.pixieType][1],
            PixieEntity.PARTICLE_RGB[entityPixieHouse.pixieType][2]
         );
      }
   }

   public static void tickServer(Level level, BlockPos pos, BlockState state, PixieHouseBlockEntity entityPixieHouse) {
      if (entityPixieHouse.hasPixie && ThreadLocalRandom.current().nextInt(100) == 0) {
         entityPixieHouse.releasePixie();
      }
   }

   public void saveAdditional(CompoundTag nbt, Provider registryLookup) {
      super.saveAdditional(nbt, registryLookup);
      nbt.putInt("HouseType", this.houseType);
      nbt.putBoolean("HasPixie", this.hasPixie);
      nbt.putInt("PixieType", this.pixieType);
      nbt.putBoolean("TamedPixie", this.tamedPixie);
      if (this.pixieOwnerUUID != null) {
         nbt.putUUID("PixieOwnerUUID", this.pixieOwnerUUID);
      }

      ContainerHelper.saveAllItems(nbt, this.pixieItems, registryLookup);
   }

   public ClientboundBlockEntityDataPacket toUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider registryLookup) {
      return this.saveWithFullMetadata(registryLookup);
   }

   public void loadAdditional(CompoundTag nbt, Provider registryLookup) {
      super.loadAdditional(nbt, registryLookup);
      this.houseType = nbt.getInt("HouseType");
      this.hasPixie = nbt.getBoolean("HasPixie");
      this.pixieType = nbt.getInt("PixieType");
      this.tamedPixie = nbt.getBoolean("TamedPixie");
      if (nbt.hasUUID("PixieOwnerUUID")) {
         this.pixieOwnerUUID = nbt.getUUID("PixieOwnerUUID");
      }

      this.pixieItems = NonNullList.withSize(1, ItemStack.EMPTY);
      ContainerHelper.loadAllItems(nbt, this.pixieItems, registryLookup);
   }

   public void releasePixie() {
      PixieEntity pixie = new PixieEntity((EntityType<? extends PixieEntity>)IafEntities.PIXIE.get(), this.level);
      pixie.absMoveTo(
         this.worldPosition.getX() + 0.5F, this.worldPosition.getY() + 1.0F, this.worldPosition.getZ() + 0.5F, ThreadLocalRandom.current().nextInt(360), 0.0F
      );
      pixie.setItemInHand(InteractionHand.MAIN_HAND, (ItemStack)this.pixieItems.getFirst());
      pixie.setColor(this.pixieType);
      pixie.ticksUntilHouseAI = 500;
      pixie.setTame(this.tamedPixie, true);
      pixie.setOwnerUUID(this.pixieOwnerUUID);
      assert this.level != null;
      if (!this.level.isClientSide) {
         this.level.addFreshEntity(pixie);
      }

      this.hasPixie = false;
      this.pixieType = 0;
      if (!this.level.isClientSide) {
         ServerHelper.sendToAll(new UpdatePixieHouseS2CPayload(this.worldPosition, false, 0));
      }
   }
}
