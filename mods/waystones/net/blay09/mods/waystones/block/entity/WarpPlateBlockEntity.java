package net.blay09.mods.waystones.block.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import java.util.function.Consumer;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.api.MutableWaystone;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneOrigin;
import net.blay09.mods.waystones.api.WaystoneTeleportContext;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.api.WaystonesAPI;
import net.blay09.mods.waystones.api.error.WaystoneTeleportError;
import net.blay09.mods.waystones.block.WarpPlateBlock;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.blay09.mods.waystones.core.InvalidWaystone;
import net.blay09.mods.waystones.core.WaystonePermissionManager;
import net.blay09.mods.waystones.core.WaystoneSyncManager;
import net.blay09.mods.waystones.item.ModItems;
import net.blay09.mods.waystones.network.message.WarpPlateEjectEffectMessage;
import net.blay09.mods.waystones.tag.ModItemTags;
import net.blay09.mods.waystones.worldgen.namegen.NameGenerationMode;
import net.blay09.mods.waystones.worldgen.namegen.NameGeneratorManager;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class WarpPlateBlockEntity extends WaystoneBlockEntityBase {
   private final WeakHashMap<Entity, Integer> ticksPassedPerEntity = new WeakHashMap<>();
   private final Random random = new Random();
   private int lastAttunementSlot;
   protected int attunementTicks;

   public WarpPlateBlockEntity(BlockPos blockPos, BlockState blockState) {
      super((BlockEntityType<?>)ModBlockEntities.warpPlate.get(), blockPos, blockState);
   }

   @Override
   protected void onInventoryChanged() {
      if (this.level != null) {
         this.level.setBlock(this.worldPosition, this.getIdleState(), 3);
      }
   }

   @Override
   public void initializeWaystone(ServerLevelAccessor world, @Nullable LivingEntity player, WaystoneOrigin origin) {
      super.initializeWaystone(world, player, origin);
      Waystone waystone = this.getWaystone();
      if (waystone instanceof MutableWaystone) {
         Component name = NameGeneratorManager.get(world.getServer()).getName(world, waystone, world.getRandom(), NameGenerationMode.RANDOM_ONLY);
         ((MutableWaystone)waystone).setName(name);
      }

      WaystoneSyncManager.sendWaystoneUpdateToAll(world.getServer(), waystone);
      this.initializeInventory();
   }

   private void initializeInventory() {
      this.setShardItem(new ItemStack(ModItems.dormantShard));
   }

   @Override
   protected ResourceLocation getWaystoneType() {
      return WaystoneTypes.WARP_PLATE;
   }

   @Override
   public void saveAdditional(CompoundTag tag, Provider provider) {
      super.saveAdditional(tag, provider);
      tag.putInt("LastAttunementSlot", this.lastAttunementSlot);
   }

   @Override
   public void loadAdditional(CompoundTag compound, Provider provider) {
      super.loadAdditional(compound, provider);
      this.lastAttunementSlot = compound.getInt("LastAttunementSlot");
   }

   public boolean hasPotentialWarpTarget() {
      ItemStack shardItem = this.getShardItem();
      return !shardItem.isEmpty() && !shardItem.is(ModItems.deepslateShard);
   }

   @Override
   public Component getName() {
      return Component.translatable("container.waystones.warp_plate");
   }

   public void onEntityCollision(Entity entity) {
      if (!WaystonePermissionManager.isEntityDeniedTeleports(entity)) {
         Integer ticksPassed = this.ticksPassedPerEntity.putIfAbsent(entity, 0);
         if ((ticksPassed == null || ticksPassed != -1) && this.hasPotentialWarpTarget()) {
            Waystone targetWaystone = this.getTargetWaystone().orElse(InvalidWaystone.INSTANCE);
            WarpPlateBlock.WarpPlateStatus status = targetWaystone.isValid()
               ? WarpPlateBlock.WarpPlateStatus.WARPING
               : WarpPlateBlock.WarpPlateStatus.WARPING_INVALID;
            Boolean canAfford = WaystonesAPI.createDefaultTeleportContext(entity, targetWaystone, it -> it.setFromWaystone(this.getWaystone()))
               .mapLeft(WaystoneTeleportContext::getRequirements)
               .mapLeft(it -> !(entity instanceof Player player && !player.getAbilities().instabuild && !it.canAfford(player)))
               .left()
               .orElse(true);
            this.level
               .setBlock(
                  this.worldPosition,
                  (BlockState)this.getBlockState().setValue(WarpPlateBlock.STATUS, canAfford ? status : WarpPlateBlock.WarpPlateStatus.WARPING_INVALID),
                  3
               );
         }
      }
   }

   private boolean isEntityOnWarpPlate(Entity entity) {
      return entity.getX() >= this.worldPosition.getX()
         && entity.getX() < this.worldPosition.getX() + 1
         && entity.getY() >= this.worldPosition.getY()
         && entity.getY() < this.worldPosition.getY() + 1
         && entity.getZ() >= this.worldPosition.getZ()
         && entity.getZ() < this.worldPosition.getZ() + 1;
   }

   public BlockState getIdleState() {
      ItemStack shardItem = this.getShardItem();
      if (shardItem.isEmpty()) {
         return (BlockState)this.getBlockState().setValue(WarpPlateBlock.STATUS, WarpPlateBlock.WarpPlateStatus.EMPTY);
      } else if (shardItem.is(ModItems.deepslateShard)) {
         return (BlockState)this.getBlockState().setValue(WarpPlateBlock.STATUS, WarpPlateBlock.WarpPlateStatus.LOCKED);
      } else {
         return shardItem.is(ModItems.dormantShard)
            ? (BlockState)this.getBlockState().setValue(WarpPlateBlock.STATUS, WarpPlateBlock.WarpPlateStatus.ATTUNING)
            : (BlockState)this.getBlockState().setValue(WarpPlateBlock.STATUS, WarpPlateBlock.WarpPlateStatus.IDLE);
      }
   }

   public void serverTick() {
      this.attuneShard();
      WarpPlateBlock.WarpPlateStatus status = (WarpPlateBlock.WarpPlateStatus)this.getBlockState().getValue(WarpPlateBlock.STATUS);
      if (status == WarpPlateBlock.WarpPlateStatus.WARPING || status == WarpPlateBlock.WarpPlateStatus.WARPING_INVALID) {
         AABB boundsAbove = new AABB(
            this.worldPosition.getX(),
            this.worldPosition.getY(),
            this.worldPosition.getZ(),
            this.worldPosition.getX() + 1,
            this.worldPosition.getY() + 1,
            this.worldPosition.getZ() + 1
         );
         List<Entity> entities = this.level.getEntities((Entity)null, boundsAbove, EntitySelector.ENTITY_STILL_ALIVE);
         if (entities.isEmpty()) {
            this.level.setBlock(this.worldPosition, this.getIdleState(), 3);
            this.ticksPassedPerEntity.clear();
         }
      }

      if (this.hasPotentialWarpTarget()) {
         int useTime = this.getWarpPlateUseTime();
         Iterator<Entry<Entity, Integer>> iterator = this.ticksPassedPerEntity.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<Entity, Integer> entry = iterator.next();
            Entity entity = entry.getKey();
            Integer ticksPassed = entry.getValue();
            if (entity.isAlive() && this.isEntityOnWarpPlate(entity)) {
               if (ticksPassed > useTime) {
                  ItemStack targetAttunementStack = this.getTargetAttunementStack();
                  Waystone targetWaystone = WaystonesAPI.getBoundWaystone(null, targetAttunementStack).orElse(null);
                  if (targetWaystone != null && targetWaystone.isValid()) {
                     this.teleportToTarget(entity, targetWaystone, targetAttunementStack);
                  }

                  if (entity instanceof Player) {
                     if (targetWaystone == null) {
                        MutableComponent chatComponent = Component.translatable("chat.waystones.warp_plate_has_no_target");
                        chatComponent.withStyle(ChatFormatting.DARK_RED);
                        ((Player)entity).displayClientMessage(chatComponent, true);
                     } else if (!targetWaystone.isValid()) {
                        MutableComponent chatComponent = Component.translatable("chat.waystones.warp_plate_has_invalid_target");
                        chatComponent.withStyle(ChatFormatting.DARK_RED);
                        ((Player)entity).displayClientMessage(chatComponent, true);
                     }
                  }

                  iterator.remove();
               } else if (ticksPassed != -1) {
                  entry.setValue(ticksPassed + 1);
               }
            } else {
               iterator.remove();
            }
         }
      }
   }

   private int getWarpPlateUseTime() {
      float useTimeMultiplier = 1.0F;

      for (int i = 0; i < this.container.getContainerSize(); i++) {
         ItemStack itemStack = this.container.getItem(i);
         if (itemStack.getItem() == Items.AMETHYST_SHARD) {
            useTimeMultiplier -= 0.016F * itemStack.getCount();
         } else if (itemStack.getItem() == Items.SLIME_BALL) {
            useTimeMultiplier += 0.016F * itemStack.getCount();
         }
      }

      int configuredUseTime = WaystonesConfig.getActive().general.warpPlateUseTime;
      return Mth.clamp((int)(configuredUseTime * useTimeMultiplier), 1, configuredUseTime * 2);
   }

   private void teleportToTarget(Entity entity, Waystone targetWaystone, ItemStack targetAttunementStack) {
      WaystonesAPI.createDefaultTeleportContext(entity, targetWaystone, it -> {
         it.setFromWaystone(this.getWaystone());
         it.setWarpItem(targetAttunementStack);
      }).flatMap(WaystonesAPI::tryTeleport).ifRight(this.informRejectedTeleport(entity)).ifLeft(entities -> {
         if (targetAttunementStack.is(ModItemTags.SINGLE_USE_WARP_SHARDS) && !(entity instanceof Player player && player.getAbilities().instabuild)) {
            targetAttunementStack.shrink(1);
         }
      }).left();
   }

   private Consumer<WaystoneTeleportError> informRejectedTeleport(Entity entityToInform) {
      return error -> {
         if (error.getComponent() != null && entityToInform instanceof Player player) {
            MutableComponent chatComponent = error.getComponent().copy().withStyle(ChatFormatting.DARK_RED);
            player.displayClientMessage(chatComponent, true);
         }
      };
   }

   public ItemStack getTargetAttunementStack() {
      boolean shouldRoundRobin = false;
      boolean shouldPrioritizeSingleUseShards = false;
      List<ItemStack> attunedShards = new ArrayList<>();

      for (int i = 0; i < this.container.getContainerSize(); i++) {
         ItemStack itemStack = this.container.getItem(i);
         if (itemStack.is(ModItemTags.WARP_SHARDS)) {
            Waystone waystoneAttunedTo = WaystonesAPI.getBoundWaystone(null, itemStack).orElse(null);
            if (waystoneAttunedTo != null && !waystoneAttunedTo.getWaystoneUid().equals(this.getWaystone().getWaystoneUid())) {
               attunedShards.add(itemStack);
            }
         } else if (itemStack.getItem() == Items.QUARTZ) {
            shouldRoundRobin = true;
         } else if (itemStack.getItem() == Items.SPIDER_EYE) {
            shouldPrioritizeSingleUseShards = true;
         }
      }

      if (shouldPrioritizeSingleUseShards && attunedShards.stream().anyMatch(stack -> stack.is(ModItemTags.SINGLE_USE_WARP_SHARDS))) {
         attunedShards.removeIf(stack -> !stack.is(ModItemTags.SINGLE_USE_WARP_SHARDS));
      }

      if (!attunedShards.isEmpty()) {
         this.lastAttunementSlot = (this.lastAttunementSlot + 1) % attunedShards.size();
         return shouldRoundRobin ? attunedShards.get(this.lastAttunementSlot) : attunedShards.get(this.random.nextInt(attunedShards.size()));
      } else {
         return ItemStack.EMPTY;
      }
   }

   public Optional<Waystone> getTargetWaystone() {
      return WaystonesAPI.getBoundWaystone(null, this.getTargetAttunementStack());
   }

   public void markEntityForCooldown(Entity entity) {
      this.ticksPassedPerEntity.put(entity, -1);
   }

   public void setShardItem(ItemStack itemStack) {
      this.container.setItem(0, itemStack);
      if (this.level != null) {
         this.level.setBlock(this.worldPosition, this.getIdleState(), 3);
      }

      this.setChanged();
   }

   public ItemStack getShardItem() {
      return this.container.getItem(0);
   }

   public void attuneShard() {
      ItemStack shardItem = this.getShardItem();
      if (shardItem.is(ModItems.dormantShard)) {
         this.attunementTicks++;
         if (this.attunementTicks >= this.getMaxAttunementTicks()) {
            this.attunementTicks = 0;
            ItemStack attunedShard = new ItemStack(ModItems.attunedShard);
            WaystonesAPI.setBoundWaystone(attunedShard, this.getWaystone());
            this.setShardItem(attunedShard);
         }
      } else if (this.level != null && shardItem.is(ModItems.attunedShard)) {
         WaystonesAPI.getBoundWaystone(null, shardItem)
            .ifPresent(
               it -> {
                  if (it.getWaystoneUid().equals(this.getWaystone().getWaystoneUid())) {
                     ItemEntity shardEntity = new ItemEntity(
                        this.level, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5, shardItem
                     );
                     this.level.addFreshEntity(shardEntity);
                     this.setShardItem(ItemStack.EMPTY);
                     if (this.level instanceof ServerLevel serverLevel) {
                        Balm.getNetworking().sendToTracking(serverLevel, this.worldPosition, new WarpPlateEjectEffectMessage(this.worldPosition));
                        this.level.playSound(null, this.worldPosition, SoundEvents.CHICKEN_EGG, SoundSource.PLAYERS, 1.0F, 1.0F);
                     }
                  }
               }
            );
      } else {
         this.attunementTicks = 0;
      }
   }

   public int getMaxAttunementTicks() {
      return 30;
   }
}
