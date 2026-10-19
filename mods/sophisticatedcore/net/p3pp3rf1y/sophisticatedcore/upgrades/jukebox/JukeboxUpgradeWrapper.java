package net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.IItemHandler;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.inventory.StatefulComponentItemHandler;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ITickableUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;

public class JukeboxUpgradeWrapper extends UpgradeWrapperBase<JukeboxUpgradeWrapper, JukeboxUpgradeItem> implements ITickableUpgrade {
   private static final int KEEP_ALIVE_SEND_INTERVAL = 5;
   private final StatefulComponentItemHandler discInventory;
   private long lastKeepAliveSendTime = 0L;
   private boolean isPlaying;
   private final LinkedList<Integer> playlist = new LinkedList<>();
   private final LinkedList<Integer> history = new LinkedList<>();
   private final Set<Integer> discsRemoved = new HashSet<>();
   private final Set<Integer> discsAdded = new HashSet<>();
   @Nullable
   private Entity entityPlaying = null;
   @Nullable
   private Level levelPlaying = null;
   @Nullable
   private BlockPos posPlaying = null;
   private final Runnable onFinishedCallback = this::onDiscFinished;

   protected JukeboxUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      super(storageWrapper, upgrade, upgradeSaveHandler);
      if (upgrade.has(DataComponents.CONTAINER)) {
         upgrade.set(ModCoreDataComponents.LENIENT_CONTAINER, (ItemContainerContents)upgrade.get(DataComponents.CONTAINER));
      }

      upgrade.remove(DataComponents.CONTAINER);
      this.discInventory = new StatefulComponentItemHandler(upgrade, ModCoreDataComponents.LENIENT_CONTAINER.get(), this.upgradeItem.getNumberOfSlots()) {
         @Override
         protected void onContentsChanged(int slot, ItemStack oldStack, ItemStack newStack) {
            super.onContentsChanged(slot, oldStack, newStack);
            JukeboxUpgradeWrapper.this.save();
            if (oldStack.isEmpty() && !newStack.isEmpty()) {
               JukeboxUpgradeWrapper.this.discsAdded.add(slot);
               JukeboxUpgradeWrapper.this.discsRemoved.remove(slot);
            } else if (!oldStack.isEmpty() && newStack.isEmpty()) {
               JukeboxUpgradeWrapper.this.discsRemoved.add(slot);
               JukeboxUpgradeWrapper.this.discsAdded.remove(slot);
            }
         }

         @Override
         public boolean isItemValid(int slot, ItemStack stack) {
            return stack.isEmpty() || DiscHandlerRegistry.isSupported(stack);
         }
      };
      this.isPlaying = (Boolean)upgrade.getOrDefault(ModCoreDataComponents.IS_PLAYING, false);
   }

   public boolean isShuffleEnabled() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.SHUFFLE, false);
   }

   public void setShuffleEnabled(boolean shuffleEnabled) {
      this.upgrade.set(ModCoreDataComponents.SHUFFLE, shuffleEnabled);
      this.save();
      this.initPlaylist(true);
   }

   public RepeatMode getRepeatMode() {
      return (RepeatMode)this.upgrade.getOrDefault(ModCoreDataComponents.REPEAT_MODE, RepeatMode.NO);
   }

   public void setRepeatMode(RepeatMode repeatMode) {
      this.upgrade.set(ModCoreDataComponents.REPEAT_MODE, repeatMode);
      this.save();
   }

   public ItemStack getDisc() {
      return this.getDiscSlotActive() > -1 ? this.discInventory.getStackInSlot(this.getDiscSlotActive()) : ItemStack.EMPTY;
   }

   public int getDiscSlotActive() {
      return (Integer)this.upgrade.getOrDefault(ModCoreDataComponents.DISC_SLOT_ACTIVE, -1);
   }

   private void setDiscSlotActive(int discSlotActive) {
      this.upgrade.set(ModCoreDataComponents.DISC_SLOT_ACTIVE, discSlotActive);
      this.save();
   }

   public void play(Level level, BlockPos pos) {
      if (!this.isPlaying) {
         this.levelPlaying = level;
         this.posPlaying = pos;
         this.playNext();
      }
   }

   public void play(Entity entity) {
      if (!this.isPlaying) {
         this.entityPlaying = entity;
         this.playNext();
      }
   }

   private void playDisc() {
      Level level = this.entityPlaying != null ? this.entityPlaying.level() : this.levelPlaying;
      if (level instanceof ServerLevel serverLevel && (this.posPlaying != null || this.entityPlaying != null)) {
         ItemStack disc = this.getDisc();
         if (!disc.isEmpty()) {
            this.storageWrapper
               .getContentsUuid()
               .ifPresent(
                  storageUuid -> DiscHandlerRegistry.findHandler(disc)
                     .ifPresent(
                        handler -> {
                           if (this.entityPlaying != null) {
                              handler.playDisc(
                                 serverLevel, this.entityPlaying.position(), storageUuid, disc, this.entityPlaying.getId(), this.onFinishedCallback
                              );
                           } else {
                              handler.playDisc(serverLevel, this.posPlaying, storageUuid, disc, this.onFinishedCallback);
                           }

                           handler.getMusicLengthInTicks(disc, level)
                              .ifPresent(
                                 lengthInTicks -> this.upgrade.set(ModCoreDataComponents.DISC_FINISH_TIME, level.getGameTime() + lengthInTicks.intValue())
                              );
                        }
                     )
               );
            this.setIsPlaying(true);
         }
      }
   }

   private void onDiscFinished() {
      if (this.getRepeatMode() == RepeatMode.ONE) {
         this.playDisc();
      } else if (this.getRepeatMode() == RepeatMode.ALL) {
         this.playNext();
      } else {
         this.playNext(false);
      }
   }

   private void setIsPlaying(boolean playing) {
      this.isPlaying = playing;
      this.upgrade.set(ModCoreDataComponents.IS_PLAYING, playing);
      if (this.isPlaying) {
         this.storageWrapper.getRenderInfo().setUpgradeRenderData(JukeboxUpgradeRenderData.TYPE, new JukeboxUpgradeRenderData(true));
      } else {
         this.removeRenderData();
         this.setDiscSlotActive(-1);
      }

      this.save();
   }

   private void removeRenderData() {
      this.storageWrapper.getRenderInfo().removeUpgradeRenderData(JukeboxUpgradeRenderData.TYPE);
   }

   public void stop(LivingEntity entity) {
      if (entity.level() instanceof ServerLevel) {
         this.storageWrapper
            .getContentsUuid()
            .ifPresent(storageUuid -> ServerStorageSoundHandler.stopPlayingDisc(entity.level(), entity.position(), storageUuid));
         this.setIsPlaying(false);
         this.upgrade.remove(ModCoreDataComponents.DISC_FINISH_TIME);
         this.setDiscSlotActive(-1);
         this.playlist.clear();
         this.history.clear();
      }
   }

   public IItemHandler getDiscInventory() {
      return this.discInventory;
   }

   @Override
   public void tick(@Nullable Entity entity, Level level, BlockPos pos) {
      if (!level.isClientSide()) {
         if (!this.discsRemoved.isEmpty()) {
            this.discsRemoved.forEach(index -> {
               this.playlist.remove(index);
               this.history.remove(index);
            });
            this.discsRemoved.clear();
         }

         if (!this.discsAdded.isEmpty()) {
            this.playlist.addAll(this.discsAdded);
            this.discsAdded.clear();
         }
      }

      if (this.isPlaying && this.lastKeepAliveSendTime < level.getGameTime() - 5L) {
         this.storageWrapper
            .getContentsUuid()
            .ifPresent(storageUuid -> ServerStorageSoundHandler.updateKeepAlive(storageUuid, level, Vec3.atCenterOf(pos), () -> this.setIsPlaying(false)));
         this.lastKeepAliveSendTime = level.getGameTime();
      }
   }

   public boolean isPlaying() {
      return this.isPlaying;
   }

   @Override
   public void onBeforeRemoved() {
      this.removeRenderData();
   }

   public void next() {
      if (this.isPlaying) {
         this.playNext();
      }
   }

   public void playNext() {
      this.playNext(true);
   }

   public void playNext(boolean startOverIfAtTheEnd) {
      if (this.playlist.isEmpty() && startOverIfAtTheEnd) {
         this.initPlaylist(false);
      }

      if (!this.playlist.isEmpty()) {
         if (this.getDiscSlotActive() != -1) {
            this.history.add(this.getDiscSlotActive());
            if (this.history.size() > this.discInventory.getSlots()) {
               this.history.poll();
            }
         }

         Integer discIndex = this.playlist.poll();
         if (discIndex != null) {
            this.setDiscSlotActive(discIndex);
            this.playDisc();
         }
      }
   }

   private void initPlaylist(boolean excludeActive) {
      this.playlist.clear();

      for (int i = 0; i < this.discInventory.getSlots(); i++) {
         if (!this.discInventory.getStackInSlot(i).isEmpty() && (!excludeActive || !this.isPlaying || i != this.getDiscSlotActive())) {
            this.playlist.add(i);
         }
      }

      if (this.isShuffleEnabled()) {
         Collections.shuffle(this.playlist);
      }
   }

   public void previous() {
      if (this.isPlaying) {
         this.playPrevious();
      }
   }

   public void playPrevious() {
      if (!this.history.isEmpty()) {
         this.playlist.addFirst(this.getDiscSlotActive());
         Integer discIndex = this.history.pollLast();
         if (discIndex != null) {
            this.setDiscSlotActive(discIndex);
            this.playDisc();
         }
      }
   }

   public long getDiscFinishTime() {
      return (Long)this.upgrade.getOrDefault(ModCoreDataComponents.DISC_FINISH_TIME, 0L);
   }
}
