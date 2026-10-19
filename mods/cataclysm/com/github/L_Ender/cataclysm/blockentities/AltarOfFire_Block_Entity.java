package com.github.L_Ender.cataclysm.blockentities;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignis_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity.DataComponentInput;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.Vec3;

public class AltarOfFire_Block_Entity extends BlockEntity implements Clearable {
   public int tickCount;
   private static final int NUM_SLOTS = 1;
   private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
   public boolean summoningthis = false;
   public int summoningticks = 0;
   private final RandomSource rnd = RandomSource.create();

   public AltarOfFire_Block_Entity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModTileentites.ALTAR_OF_FIRE.get(), pos, state);
   }

   public static void commonTick(Level level, BlockPos pos, BlockState state, AltarOfFire_Block_Entity entity) {
      entity.tick(level, pos, state);
   }

   public void tick(Level level, BlockPos pos, BlockState state) {
      this.tickCount++;
      this.summoningthis = false;
      if (!this.getItem(0).isEmpty() && this.getItem(0).getItem() == ModItems.BURNING_ASHES.get()) {
         this.summoningthis = true;
         if (this.summoningticks == 1) {
            ScreenShake_Entity.ScreenShake(level, Vec3.atCenterOf(pos), 20.0F, 0.05F, 0, 150);
         }

         if (this.summoningticks > 118 && this.summoningticks < 121) {
            this.Sphereparticle(3.0F, 3.0F);
         }

         if (this.summoningticks > 121) {
            this.BlockBreaking(3, 3, 3);
            this.BasaltBreaking(16, 8, 16);
            Ignis_Entity ignis = (Ignis_Entity)((EntityType)ModEntities.IGNIS.get()).create(level);
            if (level instanceof ServerLevel serverLevel && ignis != null) {
               ignis.setPos(pos.getX() + 0.5F, pos.getY() + 3, pos.getZ() + 0.5F);
               ignis.setHomePos(GlobalPos.of(serverLevel.dimension(), pos));
               boolean flag = level.addFreshEntity(ignis);
               if (flag) {
                  this.items.set(0, ItemStack.EMPTY);
                  this.setChanged();
                  level.sendBlockUpdated(pos, state, state, 3);
               }
            }
         }
      }

      if (!this.summoningthis) {
         this.summoningticks = 0;
      } else {
         this.summoningticks++;
      }
   }

   private void BlockBreaking(int x, int y, int z) {
      int MthX = Mth.floor(this.getBlockPos().getX());
      int MthY = Mth.floor(this.getBlockPos().getY());
      int MthZ = Mth.floor(this.getBlockPos().getZ());

      for (int k2 = -x; k2 <= x; k2++) {
         for (int l2 = -z; l2 <= z; l2++) {
            for (int j = 0; j <= y; j++) {
               int i3 = MthX + k2;
               int k = MthY + j;
               int l = MthZ + l2;
               BlockPos blockpos = new BlockPos(i3, k, l);
               BlockState block = this.level.getBlockState(blockpos);
               if (block != Blocks.AIR.defaultBlockState() && !block.is(ModTag.ALTAR_DESTROY_IMMUNE)) {
                  this.level.destroyBlock(blockpos, false);
               }
            }
         }
      }
   }

   private void BasaltBreaking(int x, int y, int z) {
      int MthX = Mth.floor(this.getBlockPos().getX());
      int MthY = Mth.floor(this.getBlockPos().getY());
      int MthZ = Mth.floor(this.getBlockPos().getZ());

      for (int k2 = -x; k2 <= x; k2++) {
         for (int l2 = -z; l2 <= z; l2++) {
            for (int j = -1; j <= y; j++) {
               int i3 = MthX + k2;
               int k = MthY + j;
               int l = MthZ + l2;
               BlockPos blockpos = new BlockPos(i3, k, l);
               BlockState blockstate = this.level.getBlockState(blockpos);
               Block block = blockstate.getBlock();
               if (block != Blocks.AIR && block == Blocks.BASALT) {
                  this.level.destroyBlock(blockpos, false);
               }
            }
         }
      }
   }

   private void Sphereparticle(float height, float size) {
      double d0 = this.getBlockPos().getX() + 0.5F;
      double d1 = this.getBlockPos().getY() + height;
      double d2 = this.getBlockPos().getZ() + 0.5F;

      for (float i = -size; i <= size; i++) {
         for (float j = -size; j <= size; j++) {
            for (float k = -size; k <= size; k++) {
               double d3 = j + (this.rnd.nextDouble() - this.rnd.nextDouble()) * 0.5;
               double d4 = i + (this.rnd.nextDouble() - this.rnd.nextDouble()) * 0.5;
               double d5 = k + (this.rnd.nextDouble() - this.rnd.nextDouble()) * 0.5;
               double d6 = Mth.sqrt((float)(d3 * d3 + d4 * d4 + d5 * d5)) / 0.5 + this.rnd.nextGaussian() * 0.05;
               this.level.addParticle(ParticleTypes.FLAME, d0, d1, d2, d3 / d6, d4 / d6, d5 / d6);
               if (i != -size && i != size && j != -size && j != size) {
                  k += size * 2.0F - 1.0F;
               }
            }
         }
      }
   }

   public NonNullList<ItemStack> getItems() {
      return this.items;
   }

   public int getContainerSize() {
      return this.items.size();
   }

   public ItemStack getItem(int index) {
      return (ItemStack)this.items.get(index);
   }

   public void placeItem(@Nullable LivingEntity entity, int index, ItemStack stack) {
      this.getItems().set(index, stack);
      if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
         stack.setCount(this.getMaxStackSize());
      }

      this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), Context.of(entity, this.getBlockState()));
      this.markUpdated();
   }

   public int getMaxStackSize() {
      return 1;
   }

   public void loadAdditional(CompoundTag p_155312_, Provider p_324612_) {
      super.loadAdditional(p_155312_, p_324612_);
      this.items.clear();
      ContainerHelper.loadAllItems(p_155312_, this.items, p_324612_);
   }

   protected void saveAdditional(CompoundTag p_187486_, Provider p_324612_) {
      super.saveAdditional(p_187486_, p_324612_);
      ContainerHelper.saveAllItems(p_187486_, this.items, true, p_324612_);
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider p_324612_) {
      CompoundTag compoundtag = new CompoundTag();
      ContainerHelper.saveAllItems(compoundtag, this.items, true, p_324612_);
      return compoundtag;
   }

   private void markUpdated() {
      this.setChanged();
      this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
   }

   public void clearContent() {
      this.items.clear();
   }

   public void dowse() {
      if (this.level != null) {
         this.markUpdated();
      }
   }

   protected void applyImplicitComponents(DataComponentInput p_338534_) {
      super.applyImplicitComponents(p_338534_);
      ((ItemContainerContents)p_338534_.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY)).copyInto(this.getItems());
   }

   protected void collectImplicitComponents(Builder p_338620_) {
      super.collectImplicitComponents(p_338620_);
      p_338620_.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(this.getItems()));
   }

   public void removeComponentsFromTag(CompoundTag p_332690_) {
      p_332690_.remove("Items");
   }
}
