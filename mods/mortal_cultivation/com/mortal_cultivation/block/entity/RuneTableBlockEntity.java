package com.mortal_cultivation.block.entity;

import com.mortal_cultivation.gui.menu.RuneTableMenu;
import com.mortal_cultivation.init.BlockEntityRegistry;
import com.mortal_cultivation.item.ZhaoYingZhuItem;
import com.mortal_cultivation.registry.item.CultivationItems;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class RuneTableBlockEntity extends BlockEntity implements GeoBlockEntity, MenuProvider {
   public static final int ORB_SLOT = 0;
   public static final int SLOT_COUNT = 1;
   public static final int ABSORPTION_TICKS = 6000;
   public static final int LANDING_TICKS = 40;
   public static final int RUNES_PER_ORB = 100;
   public static final int MAX_STORED_RUNES = 500;
   private static final long NO_LANDING = -1L;
   private static final String INVENTORY_TAG = "inventory";
   private static final String PROGRESS_TAG = "absorptionProgress";
   private static final String STORED_RUNES_TAG = "storedRuneCount";
   private static final String ACTIVE_STORED_ORB_TAG = "activeStoredRuneOrb";
   private static final String ORB_PRESENT_TAG = "orbPresent";
   private static final String LANDING_START_TAG = "landingStartGameTime";
   private static final String LANDING_YAW_TAG = "landingYaw";
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private final RuneTableBlockEntity.RuneTableItemStackHandler itemHandler = new RuneTableBlockEntity.RuneTableItemStackHandler();
   private int absorptionProgress;
   private int storedRuneCount;
   private boolean activeStoredRuneOrb;
   private boolean orbPresent;
   private long landingStartGameTime = -1L;
   private float landingYaw;

   public RuneTableBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)BlockEntityRegistry.RUNE_TABLE.get(), pos, state);
   }

   public ItemStackHandler getItemHandler() {
      return this.itemHandler;
   }

   public boolean hasStoredRuneOrb() {
      return isStoredRuneOrb(this.itemHandler.getStackInSlot(0));
   }

   public boolean shouldRenderOrb() {
      return this.orbPresent;
   }

   public float getAbsorptionProgress(float partialTick) {
      float progress = this.absorptionProgress;
      if (this.isAbsorbingStoredRuneOrb() && this.level != null && this.level.isClientSide) {
         progress += partialTick;
      }

      return Mth.clamp(progress / 6000.0F, 0.0F, 1.0F);
   }

   public float getLandingProgress(float partialTick) {
      if (this.isAbsorbingStoredRuneOrb() || !this.orbPresent) {
         return 0.0F;
      } else if (this.landingStartGameTime != -1L && this.level != null) {
         float elapsed = (float)(this.level.getGameTime() - this.landingStartGameTime) + partialTick;
         return Mth.clamp(elapsed / 40.0F, 0.0F, 1.0F);
      } else {
         return 1.0F;
      }
   }

   public float getLiftProgress(float partialTick) {
      if (!this.isAbsorbingStoredRuneOrb() || !this.orbPresent) {
         return 0.0F;
      } else if (this.landingStartGameTime != -1L && this.level != null) {
         float elapsed = (float)(this.level.getGameTime() - this.landingStartGameTime) + partialTick;
         return Mth.clamp(elapsed / 40.0F, 0.0F, 1.0F);
      } else {
         return 1.0F;
      }
   }

   public float getOrbRotationElapsed(float partialTick) {
      if (this.landingStartGameTime != -1L && this.level != null) {
         return Math.max(0.0F, (float)(this.level.getGameTime() - this.landingStartGameTime) + partialTick);
      } else {
         return this.level == null ? 0.0F : (float)this.level.getGameTime() + partialTick;
      }
   }

   public long getLandingStartGameTime() {
      return this.landingStartGameTime;
   }

   public float getLandingYaw() {
      return this.landingYaw;
   }

   public int getStoredRuneCount() {
      return this.storedRuneCount;
   }

   public void setStoredRuneCount(int storedRuneCount) {
      int clampedCount = Mth.clamp(storedRuneCount, 0, 500);
      if (this.storedRuneCount != clampedCount) {
         boolean wasAbsorbing = this.isAbsorbingStoredRuneOrb();
         this.storedRuneCount = clampedCount;
         boolean isAbsorbing = this.isAbsorbingStoredRuneOrb();
         if (wasAbsorbing && !isAbsorbing) {
            this.absorptionProgress = 0;
            this.beginLanding();
         } else if (!wasAbsorbing && isAbsorbing) {
            this.absorptionProgress = 0;
            this.beginLift(true);
         }

         this.syncToClient();
      }
   }

   public boolean hasRuneCapacity() {
      return this.storedRuneCount <= 400;
   }

   public boolean isAbsorbingStoredRuneOrb() {
      return this.activeStoredRuneOrb && this.hasStoredRuneOrb() && this.hasRuneCapacity();
   }

   public Component getDisplayName() {
      return Component.translatable("container.mortal_cultivation.rune_table");
   }

   @Nullable
   public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
      return new RuneTableMenu(id, inventory, this);
   }

   public static void serverTick(Level level, BlockPos pos, BlockState state, RuneTableBlockEntity table) {
      if (!level.isClientSide) {
         if (!table.isAbsorbingStoredRuneOrb()) {
            if (table.absorptionProgress != 0) {
               table.absorptionProgress = 0;
               table.syncToClient();
            }
         } else {
            table.absorptionProgress++;
            table.setChanged();
            if (table.absorptionProgress >= 6000) {
               table.finishAbsorption();
            }
         }
      }
   }

   public static void clientTick(Level level, BlockPos pos, BlockState state, RuneTableBlockEntity table) {
      if (level.isClientSide && table.isAbsorbingStoredRuneOrb()) {
         if (table.absorptionProgress < 6000) {
            table.absorptionProgress++;
         }
      }
   }

   private void finishAbsorption() {
      ItemStack result = this.itemHandler.getStackInSlot(0).copy();
      if (!isStoredRuneOrb(result)) {
         this.absorptionProgress = 0;
      } else if (!this.hasRuneCapacity()) {
         this.absorptionProgress = 0;
         this.syncToClient();
      } else {
         ZhaoYingZhuItem.clearStoredRunes(result);
         int nextDamage = result.getDamageValue() + 1;
         if (nextDamage >= result.getMaxDamage()) {
            result = ItemStack.EMPTY;
            this.resetLanding();
         } else {
            result.setDamageValue(nextDamage);
            this.beginLanding();
         }

         this.absorptionProgress = 0;
         this.storedRuneCount = Math.min(500, this.storedRuneCount + 100);
         this.itemHandler.setStackInSlot(0, result);
         this.syncToClient();
      }
   }

   public void dropContents(Level level) {
      ItemStack stack = this.itemHandler.extractItem(0, this.itemHandler.getStackInSlot(0).getCount(), false);
      if (!stack.isEmpty()) {
         Containers.dropItemStack(level, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5, stack);
      }
   }

   protected void saveAdditional(CompoundTag tag, Provider provider) {
      super.saveAdditional(tag, provider);
      tag.put("inventory", this.itemHandler.serializeNBT(provider));
      tag.putInt("absorptionProgress", this.absorptionProgress);
      tag.putInt("storedRuneCount", this.storedRuneCount);
      tag.putBoolean("activeStoredRuneOrb", this.hasStoredRuneOrb());
      tag.putBoolean("orbPresent", this.hasOrbInSlot());
      tag.putLong("landingStartGameTime", this.landingStartGameTime);
      tag.putFloat("landingYaw", this.landingYaw);
   }

   protected void loadAdditional(CompoundTag tag, Provider provider) {
      super.loadAdditional(tag, provider);
      if (tag.contains("inventory", 10)) {
         this.itemHandler.deserializeNBT(provider, tag.getCompound("inventory"));
      }

      this.absorptionProgress = Math.max(0, Math.min(tag.getInt("absorptionProgress"), 6000));
      this.storedRuneCount = Mth.clamp(tag.getInt("storedRuneCount"), 0, 500);
      this.activeStoredRuneOrb = tag.contains("activeStoredRuneOrb", 1) ? tag.getBoolean("activeStoredRuneOrb") : this.hasStoredRuneOrb();
      this.orbPresent = tag.contains("orbPresent", 1) ? tag.getBoolean("orbPresent") : this.hasOrbInSlot();
      this.landingStartGameTime = tag.contains("landingStartGameTime", 4) ? tag.getLong("landingStartGameTime") : -1L;
      this.landingYaw = tag.contains("landingYaw", 5) ? tag.getFloat("landingYaw") : 0.0F;
      if (!this.isAbsorbingStoredRuneOrb()) {
         this.absorptionProgress = 0;
      }

      if (!this.orbPresent) {
         this.resetLanding();
      }
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   @NotNull
   public CompoundTag getUpdateTag(Provider provider) {
      CompoundTag tag = super.getUpdateTag(provider);
      this.saveAdditional(tag, provider);
      return tag;
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   private void syncToClient() {
      this.setChanged();
      if (this.level != null && !this.level.isClientSide) {
         BlockState state = this.getBlockState();
         this.level.sendBlockUpdated(this.worldPosition, state, state, 2);
      }
   }

   public static boolean isStoredRuneOrb(ItemStack stack) {
      return isRuneOrb(stack) && ZhaoYingZhuItem.hasStoredRunes(stack);
   }

   public static boolean isRuneOrb(ItemStack stack) {
      return stack.is((Item)CultivationItems.ZHAO_YING_ZHU.get());
   }

   private boolean hasOrbInSlot() {
      return isRuneOrb(this.itemHandler.getStackInSlot(0));
   }

   private void beginLanding() {
      long gameTime = this.level == null ? 0L : this.level.getGameTime();
      if (this.landingStartGameTime != -1L && this.activeStoredRuneOrb) {
         this.landingYaw = this.landingYaw + (float)(gameTime - this.landingStartGameTime);
      } else if (this.activeStoredRuneOrb) {
         this.landingYaw = (float)Math.floorMod(gameTime, 360L);
      }

      this.landingStartGameTime = gameTime;
   }

   private void beginLift(boolean preserveYaw) {
      long gameTime = this.level == null ? 0L : this.level.getGameTime();
      this.landingStartGameTime = gameTime;
      if (!preserveYaw) {
         this.landingYaw = (float)Math.floorMod(gameTime, 360L);
      }
   }

   private void resetLanding() {
      this.landingStartGameTime = -1L;
      this.landingYaw = 0.0F;
   }

   private final class RuneTableItemStackHandler extends ItemStackHandler {
      private RuneTableItemStackHandler() {
         super(1);
      }

      public boolean isItemValid(int slot, ItemStack stack) {
         return slot == 0 && RuneTableBlockEntity.isRuneOrb(stack);
      }

      public int getSlotLimit(int slot) {
         return 1;
      }

      protected void onContentsChanged(int slot) {
         boolean wasActive = RuneTableBlockEntity.this.activeStoredRuneOrb;
         RuneTableBlockEntity.this.orbPresent = RuneTableBlockEntity.this.hasOrbInSlot();
         RuneTableBlockEntity.this.activeStoredRuneOrb = RuneTableBlockEntity.this.hasStoredRuneOrb();
         if (RuneTableBlockEntity.this.isAbsorbingStoredRuneOrb()) {
            RuneTableBlockEntity.this.absorptionProgress = 0;
            RuneTableBlockEntity.this.beginLift(wasActive);
         } else {
            RuneTableBlockEntity.this.absorptionProgress = 0;
            if (!RuneTableBlockEntity.this.orbPresent) {
               RuneTableBlockEntity.this.resetLanding();
            } else if (RuneTableBlockEntity.this.activeStoredRuneOrb || wasActive) {
               RuneTableBlockEntity.this.beginLanding();
            }
         }

         RuneTableBlockEntity.this.syncToClient();
      }
   }
}
