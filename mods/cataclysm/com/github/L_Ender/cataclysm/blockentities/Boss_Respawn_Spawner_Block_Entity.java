package com.github.L_Ender.cataclysm.blockentities;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.blocks.Boss_Respawn_Spawner_Block;
import com.github.L_Ender.cataclysm.entity.etc.IHomeEntity;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Boss_Respawn_Spawner_Block_Entity extends BlockEntity {
   public int Animaitonticks;
   public int tickCount;
   public int animation = 0;
   public AnimationState openingAnimationState = new AnimationState();
   protected static final int SHORT_RANGE = 9;
   protected static final int LONG_RANGE = 50;
   private Entity displayEntity;
   private EntityType<?> spawnType = null;
   protected boolean spawnedBoss = false;
   private ItemStack item = ItemStack.EMPTY;

   public Boss_Respawn_Spawner_Block_Entity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModTileentites.BOSS_RESPAWNER.get(), pos, state);
   }

   public AnimationState getAnimationState(String input) {
      return input == "opening" ? this.openingAnimationState : new AnimationState();
   }

   public boolean triggerEvent(int p_58837_, int p_58838_) {
      if (p_58837_ == 1) {
         this.openingAnimationState.start(this.tickCount);
         return true;
      } else {
         return super.triggerEvent(p_58837_, p_58838_);
      }
   }

   public static void tick(Level level, BlockPos pos, BlockState state, Boss_Respawn_Spawner_Block_Entity te) {
      te.tickCount++;
      if (!te.spawnedBoss && te.anyPlayerInRange(level)) {
         if (state.getBlock() instanceof Boss_Respawn_Spawner_Block) {
            if ((Boolean)state.getValue(Boss_Respawn_Spawner_Block.LIT)) {
               te.Animaitonticks++;
               if (level instanceof ServerLevel serverLevel
                  && level.getDifficulty() != Difficulty.PEACEFUL
                  && te.Animaitonticks >= 19
                  && te.spawnMyBoss(serverLevel, pos)) {
                  level.destroyBlock(pos, false);
                  te.spawnedBoss = true;
               }
            } else {
               te.Animaitonticks = 0;
            }
         }
      }
   }

   public boolean anyPlayerInRange(Level level) {
      return level.hasNearbyAlivePlayer(this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 0.5, this.getBlockPos().getZ() + 0.5, this.getRange());
   }

   protected boolean spawnMyBoss(ServerLevel serverLevel, BlockPos pos) {
      Vec3 vec3 = Vec3.atLowerCornerWithOffset(pos, 0.5, 0.0, 0.5);

      Entity entity;
      try {
         entity = this.spawnType.create(serverLevel);
      } catch (Exception exception) {
         Cataclysm.LOGGER.warn("Failed to create mob", exception);
         return false;
      }

      if (entity != null) {
         entity.setPos(vec3);
         if (entity instanceof Mob living) {
            living.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.worldPosition), MobSpawnType.SPAWNER, null);
            if (living instanceof IHomeEntity homeEntity) {
               homeEntity.setHomePos(GlobalPos.of(serverLevel.dimension(), BlockPos.containing(vec3)));
            }
         }

         return serverLevel.addFreshEntity(entity);
      } else {
         return false;
      }
   }

   protected int getRange() {
      return 9;
   }

   public ItemStack getTheItem() {
      return this.item;
   }

   public void setTheItem(ItemStack item) {
      this.item = item;
      this.setChanged();
   }

   public void setEntityId(EntityType<?> type) {
      this.spawnType = type;
      this.setChanged();
   }

   public void onHit(Level level) {
      BlockPos blockpos = this.getBlockPos();
      BlockState state = this.getBlockState();
      if (!(Boolean)state.getValue(Boss_Respawn_Spawner_Block.LIT)) {
         level.setBlock(blockpos, (BlockState)state.setValue(Boss_Respawn_Spawner_Block.LIT, true), 2);
         level.blockEvent(blockpos, this.getBlockState().getBlock(), 1, 0);
      }
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider registries) {
      return this.saveWithoutMetadata(registries);
   }

   public void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      if (tag.contains("EntityType")) {
         String str = tag.getString("EntityType");
         this.spawnType = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(str));
      }

      if (tag.contains("Item", 10)) {
         this.item = ItemStack.parse(registries, tag.getCompound("Item")).orElse(ItemStack.EMPTY);
      } else {
         this.item = ItemStack.EMPTY;
      }

      this.Animaitonticks = tag.getInt("animationTicks");
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      if (!this.getTheItem().isEmpty()) {
         tag.put("Item", this.getTheItem().save(registries));
      }

      tag.putInt("animationTicks", this.Animaitonticks);
      if (this.spawnType != null) {
         tag.putString("EntityType", BuiltInRegistries.ENTITY_TYPE.getKey(this.spawnType).toString());
      }
   }

   public Entity getDisplayEntity(Level level) {
      if (this.displayEntity == null && this.spawnType != null || this.displayEntity != null && this.displayEntity.getType() != this.spawnType) {
         this.displayEntity = this.spawnType.create(level);
      }

      return this.displayEntity;
   }
}
