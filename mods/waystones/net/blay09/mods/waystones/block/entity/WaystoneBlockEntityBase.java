package net.blay09.mods.waystones.block.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.block.entity.CustomRenderBoundingBox;
import net.blay09.mods.balm.api.block.entity.OnLoadHandler;
import net.blay09.mods.balm.api.container.BalmContainerProvider;
import net.blay09.mods.balm.api.container.DefaultContainer;
import net.blay09.mods.balm.api.menu.BalmMenuProvider;
import net.blay09.mods.balm.common.BalmBlockEntity;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneOrigin;
import net.blay09.mods.waystones.api.WaystoneVisibility;
import net.blay09.mods.waystones.api.WaystonesAPI;
import net.blay09.mods.waystones.api.error.WaystoneEditError;
import net.blay09.mods.waystones.block.WaystoneBlock;
import net.blay09.mods.waystones.block.WaystoneBlockBase;
import net.blay09.mods.waystones.component.ModComponents;
import net.blay09.mods.waystones.core.InvalidWaystone;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.core.WaystoneImpl;
import net.blay09.mods.waystones.core.WaystoneManagerImpl;
import net.blay09.mods.waystones.core.WaystonePermissionManager;
import net.blay09.mods.waystones.core.WaystoneProxy;
import net.blay09.mods.waystones.core.WaystoneSyncManager;
import net.blay09.mods.waystones.core.WaystoneVisibilities;
import net.blay09.mods.waystones.item.ModItems;
import net.blay09.mods.waystones.menu.WaystoneEditMenu;
import net.blay09.mods.waystones.menu.WaystoneModifierMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity.DataComponentInput;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public abstract class WaystoneBlockEntityBase extends BalmBlockEntity implements OnLoadHandler, CustomRenderBoundingBox, BalmContainerProvider {
   protected final DefaultContainer container = new DefaultContainer(5) {
      public int getMaxStackSize(ItemStack itemStack) {
         return itemStack.is(ModItems.dormantShard) ? 1 : super.getMaxStackSize(itemStack);
      }

      public boolean canPlaceItem(int slot, ItemStack itemStack) {
         return itemStack.is(ModItems.dormantShard) ? slot == 0 : super.canPlaceItem(slot, itemStack);
      }

      public void setChanged() {
         WaystoneBlockEntityBase.this.onInventoryChanged();
      }

      public boolean stillValid(Player player) {
         return Container.stillValidBlockEntity(WaystoneBlockEntityBase.this, player);
      }
   };
   private Waystone waystone = InvalidWaystone.INSTANCE;
   private UUID waystoneUid;
   private boolean shouldNotInitialize;
   private boolean silkTouched;

   protected void onInventoryChanged() {
   }

   public WaystoneBlockEntityBase(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
      super(blockEntityType, blockPos, blockState);
   }

   protected void saveAdditional(CompoundTag tag, Provider provider) {
      tag.put("Items", this.container.serialize(provider));
      if (this.waystone.isValid()) {
         tag.put("UUID", NbtUtils.createUUID(this.waystone.getWaystoneUid()));
      } else if (this.waystoneUid != null) {
         tag.put("UUID", NbtUtils.createUUID(this.waystoneUid));
      }
   }

   public void loadAdditional(CompoundTag compound, Provider provider) {
      if (compound.contains("Items")) {
         this.container.deserialize(compound.getCompound("Items"), provider);
      }

      if (compound.contains("UUID", 11)) {
         this.waystoneUid = NbtUtils.loadUUID(Objects.requireNonNull(compound.get("UUID")));
      }

      if (compound.contains("Waystone", 10)) {
         Waystone syncedWaystone = WaystoneImpl.read(compound.getCompound("Waystone"), provider);
         WaystoneManagerImpl.get(null).updateWaystone(syncedWaystone);
         this.waystone = new WaystoneProxy(null, syncedWaystone.getWaystoneUid());
      }
   }

   protected void applyImplicitComponents(DataComponentInput input) {
      UUID waystoneUidComponent = (UUID)input.get((DataComponentType)ModComponents.waystone.get());
      if (waystoneUidComponent != null) {
         this.waystoneUid = waystoneUidComponent;
      }
   }

   protected void collectImplicitComponents(Builder builder) {
      builder.set((DataComponentType)ModComponents.waystone.get(), this.waystone.isValid() ? this.waystone.getWaystoneUid() : this.waystoneUid);
   }

   public void writeUpdateTag(CompoundTag tag) {
      tag.put("Waystone", WaystoneImpl.write(this.getWaystone(), new CompoundTag(), this.level.registryAccess()));
   }

   public void onLoad() {
      Waystone backingWaystone = this.loadBackingWaystone();
      if (backingWaystone instanceof WaystoneImpl && this.level != null) {
         ((WaystoneImpl)backingWaystone).setDimension(this.level.dimension());
         ((WaystoneImpl)backingWaystone).setPos(this.worldPosition);
      }

      this.sync();
   }

   public AABB getRenderBoundingBox() {
      return new AABB(
         this.worldPosition.getX(),
         this.worldPosition.getY(),
         this.worldPosition.getZ(),
         this.worldPosition.getX() + 1,
         this.worldPosition.getY() + 2,
         this.worldPosition.getZ() + 1
      );
   }

   private Waystone loadBackingWaystone() {
      if (!this.waystone.isValid() && this.level != null && !this.level.isClientSide() && !this.shouldNotInitialize) {
         if (this.waystoneUid != null) {
            this.waystone = new WaystoneProxy(this.level.getServer(), this.waystoneUid);
         }

         if (!this.waystone.isValid()) {
            BlockState state = this.getBlockState();
            if (state.getBlock() instanceof WaystoneBlockBase) {
               DoubleBlockHalf half = state.hasProperty(WaystoneBlockBase.HALF)
                  ? (DoubleBlockHalf)state.getValue(WaystoneBlockBase.HALF)
                  : DoubleBlockHalf.LOWER;
               WaystoneOrigin origin = state.hasProperty(WaystoneBlockBase.ORIGIN)
                  ? (WaystoneOrigin)state.getValue(WaystoneBlockBase.ORIGIN)
                  : WaystoneOrigin.UNKNOWN;
               if (half == DoubleBlockHalf.LOWER) {
                  this.initializeWaystone(Objects.requireNonNull(this.level), null, origin);
               } else if (half == DoubleBlockHalf.UPPER) {
                  BlockEntity blockEntity = this.level.getBlockEntity(this.worldPosition.below());
                  if (blockEntity instanceof WaystoneBlockEntityBase) {
                     this.initializeFromBase((WaystoneBlockEntityBase)blockEntity);
                  }
               }
            }
         }

         if (this.waystone.isValid()) {
            this.waystoneUid = this.waystone.getWaystoneUid();
            this.setChanged();
         }
      }

      return this.waystone;
   }

   public Waystone getWaystone() {
      return this.waystone;
   }

   protected abstract ResourceLocation getWaystoneType();

   public void initializeWaystone(ServerLevelAccessor world, @Nullable LivingEntity player, WaystoneOrigin origin) {
      if (!this.waystone.isValid()) {
         WaystoneImpl waystone = new WaystoneImpl(
            this.getWaystoneType(), UUID.randomUUID(), world.getLevel().dimension(), this.worldPosition, origin, player != null ? player.getUUID() : null
         );
         WaystoneManagerImpl.get(world.getServer()).addWaystone(waystone);
         this.waystone = waystone;
         this.setChanged();
         this.sync();
      }
   }

   public void initializeFromExisting(ServerLevelAccessor world, WaystoneImpl existingWaystone, ItemStack itemStack) {
      this.waystone = existingWaystone;
      existingWaystone.setDimension(world.getLevel().dimension());
      existingWaystone.setPos(this.worldPosition);
      existingWaystone.setTransient(false);
      WaystoneManagerImpl.get(world.getServer()).updateWaystone(this.waystone);
      this.setChanged();
      this.sync();
   }

   public void initializeFromBase(WaystoneBlockEntityBase tileEntity) {
      this.waystone = tileEntity.loadBackingWaystone();
      this.setChanged();
      this.sync();
   }

   public void uninitializeWaystone() {
      if (this.waystone.isValid()) {
         WaystoneManagerImpl.get(this.level.getServer()).removeWaystone(this.waystone);
         PlayerWaystoneManager.removeKnownWaystone(this.level.getServer(), this.waystone);
         WaystoneSyncManager.sendWaystoneRemovalToAll(this.level.getServer(), this.waystone, true);
      }

      this.waystone = InvalidWaystone.INSTANCE;
      this.shouldNotInitialize = true;
      DoubleBlockHalf half = (DoubleBlockHalf)this.getBlockState().getValue(WaystoneBlock.HALF);
      BlockPos otherPos = half == DoubleBlockHalf.UPPER ? this.worldPosition.below() : this.worldPosition.above();
      if (Objects.requireNonNull(this.level).getBlockEntity(otherPos) instanceof WaystoneBlockEntityBase waystoneTile) {
         waystoneTile.waystone = InvalidWaystone.INSTANCE;
         waystoneTile.shouldNotInitialize = true;
      }

      this.setChanged();
      this.sync();
   }

   public void setSilkTouched(boolean silkTouched) {
      this.silkTouched = silkTouched;
   }

   public boolean isSilkTouched() {
      return this.silkTouched;
   }

   public Optional<MenuProvider> getSelectionMenuProvider() {
      return Optional.empty();
   }

   public abstract Component getName();

   public Optional<MenuProvider> getSettingsMenuProvider() {
      return Optional.of(
         new BalmMenuProvider<WaystoneEditMenu.Data>() {
            public Component getDisplayName() {
               return Component.translatable("container.waystones.waystone_settings", new Object[]{WaystoneBlockEntityBase.this.getName()});
            }

            public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player player) {
               Optional<WaystoneEditError> error = WaystonePermissionManager.mayEditWaystone((ServerPlayer)player, WaystoneBlockEntityBase.this.getWaystone());
               List<WaystoneVisibility> visibilityOptions = WaystoneVisibilities.getVisibilityOptions(
                  (ServerPlayer)player, WaystoneBlockEntityBase.this.waystone
               );
               return new WaystoneEditMenu(
                  i,
                  WaystoneBlockEntityBase.this.getWaystone(),
                  WaystoneBlockEntityBase.this.getModifierCount(),
                  error.map(WaystoneEditError::getTranslationKey).<Component>map(Component::translatable).orElse(null),
                  visibilityOptions,
                  WaystoneBlockEntityBase.this.getContainer()
               );
            }

            public WaystoneEditMenu.Data getScreenOpeningData(ServerPlayer player) {
               Optional<WaystoneEditError> error = WaystonePermissionManager.mayEditWaystone(player, WaystoneBlockEntityBase.this.getWaystone());
               List<WaystoneVisibility> visibilityOptions = WaystoneVisibilities.getVisibilityOptions(player, WaystoneBlockEntityBase.this.waystone);
               return new WaystoneEditMenu.Data(
                  WaystoneBlockEntityBase.this.worldPosition,
                  WaystoneBlockEntityBase.this.getWaystone(),
                  WaystoneBlockEntityBase.this.getModifierCount(),
                  error.map(WaystoneEditError::getTranslationKey).map(Component::translatable),
                  visibilityOptions
               );
            }

            public StreamCodec<RegistryFriendlyByteBuf, WaystoneEditMenu.Data> getScreenStreamCodec() {
               return WaystoneEditMenu.STREAM_CODEC;
            }
         }
      );
   }

   public Optional<MenuProvider> getModifierMenuProvider() {
      return Optional.of(new BalmMenuProvider<Waystone>() {
         public Component getDisplayName() {
            return Component.translatable("container.waystones.waystone_modifiers");
         }

         public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player player) {
            return new WaystoneModifierMenu(i, playerInventory, WaystoneBlockEntityBase.this.getWaystone(), WaystoneBlockEntityBase.this.getContainer());
         }

         public Waystone getScreenOpeningData(ServerPlayer serverPlayer) {
            return WaystoneBlockEntityBase.this.getWaystone();
         }

         public StreamCodec<RegistryFriendlyByteBuf, Waystone> getScreenStreamCodec() {
            return WaystoneImpl.STREAM_CODEC;
         }
      });
   }

   public Collection<? extends Waystone> getAuxiliaryTargets() {
      ArrayList<Waystone> result = new ArrayList<>();

      for (int i = 0; i < this.container.getContainerSize(); i++) {
         ItemStack item = this.container.getItem(i);
         WaystonesAPI.getBoundWaystone(null, item).ifPresent(result::add);
      }

      return result;
   }

   public Container getContainer() {
      return this.container;
   }

   public void applyModifierEffects(Entity entity) {
      int fireSeconds = 0;
      int poisonSeconds = 0;
      int blindSeconds = 0;
      int featherFallSeconds = 0;
      int fireResistanceSeconds = 0;
      int witherSeconds = 0;
      int potency = 1;
      List<ItemStack> curativeItems = new ArrayList<>();

      for (int i = 0; i < this.container.getContainerSize(); i++) {
         ItemStack itemStack = this.container.getItem(i);
         if (itemStack.getItem() == Items.BLAZE_POWDER) {
            fireSeconds += itemStack.getCount();
         } else if (itemStack.getItem() == Items.POISONOUS_POTATO) {
            poisonSeconds += itemStack.getCount();
         } else if (itemStack.getItem() == Items.INK_SAC) {
            blindSeconds += itemStack.getCount();
         } else if (itemStack.getItem() == Items.MILK_BUCKET || itemStack.getItem() == Items.HONEY_BLOCK) {
            curativeItems.add(itemStack);
         } else if (itemStack.getItem() == Items.DIAMOND) {
            potency = Math.min(4, potency + itemStack.getCount());
         } else if (itemStack.getItem() == Items.FEATHER) {
            featherFallSeconds = Math.min(8, featherFallSeconds + itemStack.getCount());
         } else if (itemStack.getItem() == Items.MAGMA_CREAM) {
            fireResistanceSeconds = Math.min(8, fireResistanceSeconds + itemStack.getCount());
         } else if (itemStack.getItem() == Items.WITHER_ROSE) {
            witherSeconds += itemStack.getCount();
         }
      }

      if (entity instanceof LivingEntity) {
         if (fireSeconds > 0) {
            entity.setRemainingFireTicks(fireSeconds * 20);
         }

         if (poisonSeconds > 0) {
            ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.POISON, poisonSeconds * 20, potency));
         }

         if (blindSeconds > 0) {
            ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, blindSeconds * 20, potency));
         }

         if (featherFallSeconds > 0) {
            ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, featherFallSeconds * 20, potency));
         }

         if (fireResistanceSeconds > 0) {
            ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, fireResistanceSeconds * 20, potency));
         }

         if (witherSeconds > 0) {
            ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.WITHER, witherSeconds * 20, potency));
         }

         for (ItemStack curativeItem : curativeItems) {
            Balm.getHooks().curePotionEffects((LivingEntity)entity, curativeItem);
         }
      }
   }

   private int getModifierCount() {
      int modifiers = 0;

      for (int i = 0; i < this.container.getContainerSize(); i++) {
         ItemStack itemStack = this.container.getItem(i);
         if (itemStack.getItem() == Items.BLAZE_POWDER) {
            modifiers++;
         } else if (itemStack.getItem() == Items.POISONOUS_POTATO) {
            modifiers++;
         } else if (itemStack.getItem() == Items.INK_SAC) {
            modifiers++;
         } else if (itemStack.getItem() == Items.MILK_BUCKET || itemStack.getItem() == Items.HONEY_BLOCK) {
            modifiers++;
         } else if (itemStack.getItem() == Items.DIAMOND) {
            modifiers++;
         } else if (itemStack.getItem() == Items.FEATHER) {
            modifiers++;
         } else if (itemStack.getItem() == Items.MAGMA_CREAM) {
            modifiers++;
         } else if (itemStack.getItem() == Items.WITHER_ROSE) {
            modifiers++;
         } else if (itemStack.getItem() == Items.QUARTZ) {
            modifiers++;
         } else if (itemStack.getItem() == Items.SPIDER_EYE) {
            modifiers++;
         }
      }

      return modifiers;
   }
}
