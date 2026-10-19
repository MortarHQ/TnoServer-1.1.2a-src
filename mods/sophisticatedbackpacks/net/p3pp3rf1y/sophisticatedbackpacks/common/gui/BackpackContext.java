package net.p3pp3rf1y.sophisticatedbackpacks.common.gui;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedbackpacks.SophisticatedBackpacks;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackBlockEntity;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.network.SyncClientInfoPayload;
import net.p3pp3rf1y.sophisticatedbackpacks.util.PlayerInventoryHandler;
import net.p3pp3rf1y.sophisticatedbackpacks.util.PlayerInventoryProvider;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.util.WorldHelper;

public abstract class BackpackContext {
   public static final String SUBBACKPACK_DISPLAY_NAME_PREFIX = "... > ";

   public abstract Optional<IStorageWrapper> getParentBackpackWrapper(Player var1);

   public abstract boolean shouldLockBackpackSlot(Player var1);

   public abstract IBackpackWrapper getBackpackWrapper(Player var1);

   public abstract int getBackpackSlotIndex();

   public abstract BackpackContext getSubBackpackContext(int var1, boolean var2);

   public abstract BackpackContext getParentBackpackContext();

   public abstract BackpackContext.ContextType getType();

   public void toBuffer(FriendlyByteBuf buffer) {
      this.getType().toBuffer(buffer);
      this.addToBuffer(buffer);
   }

   public abstract void addToBuffer(FriendlyByteBuf var1);

   public abstract boolean canInteractWith(Player var1);

   public BlockPos getBackpackPosition(Player playerEntity) {
      return playerEntity.blockPosition();
   }

   public Component getDisplayName(Player player) {
      return this.getBackpackWrapper(player).getBackpack().getHoverName();
   }

   public abstract void onUpgradeChanged(Player var1);

   public Optional<Entity> getOwnerPlayer(Player player) {
      return Optional.of(player);
   }

   public static BackpackContext fromBuffer(FriendlyByteBuf buffer, Level level) {
      BackpackContext.ContextType type = BackpackContext.ContextType.fromBuffer(buffer);

      return switch (type) {
         case BLOCK_BACKPACK -> BackpackContext.Block.fromBuffer(buffer);
         case BLOCK_SUB_BACKPACK -> BackpackContext.BlockSubBackpack.fromBuffer(buffer);
         case ITEM_BACKPACK -> BackpackContext.Item.fromBuffer(buffer);
         case ITEM_SUB_BACKPACK -> BackpackContext.ItemSubBackpack.fromBuffer(buffer);
         case ANOTHER_PLAYER_BACKPACK -> BackpackContext.AnotherPlayer.fromBuffer(buffer, level);
         case ANOTHER_PLAYER_SUB_BACKPACK -> BackpackContext.AnotherPlayerSubBackpack.fromBuffer(buffer, level);
      };
   }

   public boolean wasOpenFromInventory() {
      return false;
   }

   public boolean shouldSaveAfterOpen() {
      return false;
   }

   public void saveBackpackStack() {
   }

   public static class AnotherPlayer extends BackpackContext.Item {
      protected final Player otherPlayer;

      public AnotherPlayer(String handlerName, String identifier, int backpackSlotIndex, Player otherPlayer) {
         super(handlerName, identifier, backpackSlotIndex);
         this.otherPlayer = otherPlayer;
      }

      @Override
      public boolean shouldLockBackpackSlot(Player player) {
         return false;
      }

      @Override
      public IBackpackWrapper getBackpackWrapper(Player player) {
         return super.getBackpackWrapper(this.otherPlayer);
      }

      @Override
      public BackpackContext getSubBackpackContext(int subBackpackSlotIndex, boolean saveAfterOpen) {
         return new BackpackContext.AnotherPlayerSubBackpack(
            this.otherPlayer, this.handlerName, this.identifier, this.backpackSlotIndex, subBackpackSlotIndex, saveAfterOpen
         );
      }

      @Override
      public void addToBuffer(FriendlyByteBuf buffer) {
         buffer.writeInt(this.otherPlayer.getId());
         buffer.writeUtf(this.handlerName);
         buffer.writeUtf(this.identifier);
         buffer.writeInt(this.backpackSlotIndex);
      }

      @Override
      public boolean canInteractWith(Player player) {
         return player.distanceTo(this.otherPlayer) < 8.0F;
      }

      @Override
      public BackpackContext.ContextType getType() {
         return BackpackContext.ContextType.ANOTHER_PLAYER_BACKPACK;
      }

      @Override
      public Component getDisplayName(Player player) {
         return super.getDisplayName(this.otherPlayer);
      }

      public static BackpackContext fromBuffer(FriendlyByteBuf buffer, Level level) {
         int playerId = buffer.readInt();
         Player otherPlayer = (Player)level.getEntity(playerId);
         return new BackpackContext.AnotherPlayer(buffer.readUtf(), buffer.readUtf(), buffer.readInt(), Objects.requireNonNull(otherPlayer));
      }

      @Override
      public Optional<Entity> getOwnerPlayer(Player player) {
         return Optional.of(this.otherPlayer);
      }
   }

   public static class AnotherPlayerSubBackpack extends BackpackContext.AnotherPlayer {
      private final int subBackpackSlotIndex;
      private final boolean saveAfterOpen;
      @Nullable
      private IStorageWrapper parentWrapper;

      public AnotherPlayerSubBackpack(
         Player otherPlayer, String handlerName, String identifier, int backpackSlotIndex, int subBackpackSlotIndex, boolean saveAfterOpen
      ) {
         super(handlerName, identifier, backpackSlotIndex, otherPlayer);
         this.subBackpackSlotIndex = subBackpackSlotIndex;
         this.saveAfterOpen = saveAfterOpen;
      }

      @Override
      public Optional<IStorageWrapper> getParentBackpackWrapper(Player player) {
         if (this.parentWrapper == null) {
            this.parentWrapper = super.getBackpackWrapper(player);
         }

         return Optional.of(this.parentWrapper);
      }

      @Override
      public IBackpackWrapper getBackpackWrapper(Player player) {
         return this.getParentBackpackWrapper(this.otherPlayer)
            .map(
               parent -> BackpackWrapper.fromExistingData(parent.getInventoryHandler().getStackInSlot(this.subBackpackSlotIndex))
                  .orElse(IBackpackWrapper.Noop.INSTANCE)
            )
            .orElse(IBackpackWrapper.Noop.INSTANCE);
      }

      @Override
      public void addToBuffer(FriendlyByteBuf buffer) {
         super.addToBuffer(buffer);
         buffer.writeInt(this.subBackpackSlotIndex);
         buffer.writeBoolean(this.saveAfterOpen);
      }

      @Override
      public BackpackContext getParentBackpackContext() {
         return new BackpackContext.AnotherPlayer(this.handlerName, this.identifier, this.backpackSlotIndex, this.otherPlayer);
      }

      @Override
      public BackpackContext.ContextType getType() {
         return BackpackContext.ContextType.ANOTHER_PLAYER_SUB_BACKPACK;
      }

      @Override
      public Component getDisplayName(Player player) {
         return Component.literal("... > " + super.getDisplayName(player).getString());
      }

      public static BackpackContext fromBuffer(FriendlyByteBuf buffer, Level level) {
         int playerId = buffer.readInt();
         Player otherPlayer = (Player)level.getEntity(playerId);
         return new BackpackContext.AnotherPlayerSubBackpack(
            Objects.requireNonNull(otherPlayer), buffer.readUtf(), buffer.readUtf(), buffer.readInt(), buffer.readInt(), buffer.readBoolean()
         );
      }

      @Override
      public void onUpgradeChanged(Player player) {
      }

      @Override
      public boolean shouldSaveAfterOpen() {
         return this.saveAfterOpen;
      }

      @Override
      public void saveBackpackStack() {
         if (this.parentWrapper != null) {
            this.parentWrapper
               .getInventoryHandler()
               .setStackInSlot(this.subBackpackSlotIndex, this.parentWrapper.getInventoryHandler().getStackInSlot(this.subBackpackSlotIndex));
            this.parentWrapper.getInventoryHandler().saveInventory();
         }
      }
   }

   public static class Block extends BackpackContext {
      protected final BlockPos pos;

      public Block(BlockPos pos) {
         this.pos = pos;
      }

      @Override
      public BlockPos getBackpackPosition(Player playerEntity) {
         return this.pos;
      }

      @Override
      public void onUpgradeChanged(Player player) {
         if (!player.level().isClientSide) {
            WorldHelper.getBlockEntity(player.level(), this.pos, BackpackBlockEntity.class).ifPresent(BackpackBlockEntity::refreshRenderState);
         }
      }

      @Override
      public Optional<IStorageWrapper> getParentBackpackWrapper(Player player) {
         return Optional.empty();
      }

      @Override
      public boolean shouldLockBackpackSlot(Player player) {
         return false;
      }

      @Override
      public IBackpackWrapper getBackpackWrapper(Player player) {
         return WorldHelper.getBlockEntity(player.level(), this.pos, BackpackBlockEntity.class)
            .map(BackpackBlockEntity::getBackpackWrapper)
            .orElse(IBackpackWrapper.Noop.INSTANCE);
      }

      @Override
      public int getBackpackSlotIndex() {
         return -1;
      }

      @Override
      public BackpackContext getSubBackpackContext(int subBackpackSlotIndex, boolean saveAfterOpen) {
         return new BackpackContext.BlockSubBackpack(this.pos, subBackpackSlotIndex, saveAfterOpen);
      }

      @Override
      public BackpackContext getParentBackpackContext() {
         return this;
      }

      public static BackpackContext fromBuffer(FriendlyByteBuf buffer) {
         return new BackpackContext.Block(BlockPos.of(buffer.readLong()));
      }

      @Override
      public void addToBuffer(FriendlyByteBuf buffer) {
         buffer.writeLong(this.pos.asLong());
      }

      @Override
      public boolean canInteractWith(Player player) {
         return player.level().getBlockEntity(this.pos) instanceof BackpackBlockEntity
            && player.distanceToSqr(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0;
      }

      @Override
      public BackpackContext.ContextType getType() {
         return BackpackContext.ContextType.BLOCK_BACKPACK;
      }

      @Override
      public Optional<Entity> getOwnerPlayer(Player player) {
         return Optional.empty();
      }
   }

   public static class BlockSubBackpack extends BackpackContext.Block {
      private final int subBackpackSlotIndex;
      private final boolean saveAfterOpen;
      @Nullable
      private IStorageWrapper parentWrapper;

      public BlockSubBackpack(BlockPos pos, int subBackpackSlotIndex, boolean saveAfterOpen) {
         super(pos);
         this.subBackpackSlotIndex = subBackpackSlotIndex;
         this.saveAfterOpen = saveAfterOpen;
      }

      @Override
      public Optional<IStorageWrapper> getParentBackpackWrapper(Player player) {
         if (this.parentWrapper == null) {
            this.parentWrapper = super.getBackpackWrapper(player);
         }

         return Optional.of(this.parentWrapper);
      }

      @Override
      public IBackpackWrapper getBackpackWrapper(Player player) {
         return this.getParentBackpackWrapper(player).map(parent -> {
            ItemStack stackInSlot = parent.getInventoryHandler().getStackInSlot(this.subBackpackSlotIndex);
            return !(stackInSlot.getItem() instanceof BackpackItem) ? IBackpackWrapper.Noop.INSTANCE : BackpackWrapper.fromStack(stackInSlot);
         }).orElse(IBackpackWrapper.Noop.INSTANCE);
      }

      public static BackpackContext fromBuffer(FriendlyByteBuf buffer) {
         return new BackpackContext.BlockSubBackpack(BlockPos.of(buffer.readLong()), buffer.readInt(), buffer.readBoolean());
      }

      @Override
      public void addToBuffer(FriendlyByteBuf buffer) {
         super.addToBuffer(buffer);
         buffer.writeInt(this.subBackpackSlotIndex);
         buffer.writeBoolean(this.saveAfterOpen);
      }

      @Override
      public BackpackContext getParentBackpackContext() {
         return new BackpackContext.Block(this.pos);
      }

      @Override
      public BackpackContext.ContextType getType() {
         return BackpackContext.ContextType.BLOCK_SUB_BACKPACK;
      }

      @Override
      public Component getDisplayName(Player player) {
         return Component.literal("... > " + super.getDisplayName(player).getString());
      }

      @Override
      public void onUpgradeChanged(Player player) {
      }

      @Override
      public boolean shouldSaveAfterOpen() {
         return this.saveAfterOpen;
      }

      @Override
      public void saveBackpackStack() {
         if (this.parentWrapper != null) {
            this.parentWrapper
               .getInventoryHandler()
               .setStackInSlot(this.subBackpackSlotIndex, this.parentWrapper.getInventoryHandler().getStackInSlot(this.subBackpackSlotIndex));
            this.parentWrapper.getInventoryHandler().saveInventory();
         }
      }
   }

   public enum ContextType {
      BLOCK_BACKPACK(0),
      BLOCK_SUB_BACKPACK(1),
      ITEM_BACKPACK(2),
      ITEM_SUB_BACKPACK(3),
      ANOTHER_PLAYER_BACKPACK(4),
      ANOTHER_PLAYER_SUB_BACKPACK(5);

      private final int id;
      private static final Map<Integer, BackpackContext.ContextType> ID_CONTEXTS;

      ContextType(int id) {
         this.id = id;
      }

      public void toBuffer(FriendlyByteBuf buffer) {
         buffer.writeShort(this.id);
      }

      public static BackpackContext.ContextType fromBuffer(FriendlyByteBuf buffer) {
         return ID_CONTEXTS.getOrDefault(Integer.valueOf(buffer.readShort()), ITEM_BACKPACK);
      }

      static {
         Builder<Integer, BackpackContext.ContextType> builder = new Builder();

         for (BackpackContext.ContextType value : values()) {
            builder.put(value.id, value);
         }

         ID_CONTEXTS = builder.build();
      }
   }

   public static class Item extends BackpackContext {
      protected final String handlerName;
      protected final String identifier;
      protected final int backpackSlotIndex;
      private final boolean openFromInventory;

      public Item(String handlerName, int backpackSlotIndex) {
         this(handlerName, "", backpackSlotIndex);
      }

      public Item(String handlerName, String identifier, int backpackSlotIndex) {
         this(handlerName, identifier, backpackSlotIndex, false);
      }

      public Item(String handlerName, String identifier, int backpackSlotIndex, boolean openFromInventory) {
         this.handlerName = handlerName;
         this.identifier = identifier;
         this.backpackSlotIndex = backpackSlotIndex;
         this.openFromInventory = openFromInventory;
      }

      @Override
      public boolean wasOpenFromInventory() {
         return this.openFromInventory;
      }

      @Override
      public Optional<IStorageWrapper> getParentBackpackWrapper(Player player) {
         return Optional.empty();
      }

      @Override
      public boolean shouldLockBackpackSlot(Player player) {
         return PlayerInventoryProvider.get().getPlayerInventoryHandler(this.handlerName).map(PlayerInventoryHandler::isVisibleInGui).orElse(false);
      }

      @Override
      public IBackpackWrapper getBackpackWrapper(Player player) {
         Optional<PlayerInventoryHandler> inventoryHandler = PlayerInventoryProvider.get().getPlayerInventoryHandler(this.handlerName);
         if (inventoryHandler.isEmpty()) {
            SophisticatedBackpacks.LOGGER.error("Error getting backpack wrapper - Unable to find inventory handler for \"{}\"", this.handlerName);
            return IBackpackWrapper.Noop.INSTANCE;
         }

         ItemStack backpackStack = inventoryHandler.get().getStackInSlot(player, this.identifier, this.backpackSlotIndex);
         if (backpackStack.getItem() instanceof BackpackItem) {
            return BackpackWrapper.fromStack(backpackStack);
         }

         SophisticatedBackpacks.LOGGER.error("Error getting backpack wrapper - Stack isn't a backpack");
         return IBackpackWrapper.Noop.INSTANCE;
      }

      @Override
      public void onUpgradeChanged(Player player) {
         if (!player.level().isClientSide && this.handlerName.equals("main")) {
            IStorageWrapper backpackWrapper = this.getBackpackWrapper(player);
            CompoundTag modificationSafeRenderInfoNbt = backpackWrapper.getRenderInfo().getNbt().copy();
            if (player instanceof ServerPlayer serverPlayer) {
               PacketDistributor.sendToPlayer(
                  serverPlayer,
                  new SyncClientInfoPayload(this.backpackSlotIndex, modificationSafeRenderInfoNbt, backpackWrapper.getColumnsTaken()),
                  new CustomPacketPayload[0]
               );
            }
         }
      }

      @Override
      public int getBackpackSlotIndex() {
         return this.backpackSlotIndex;
      }

      @Override
      public BackpackContext getSubBackpackContext(int subBackpackSlotIndex, boolean saveAfterOpen) {
         return new BackpackContext.ItemSubBackpack(
            this.handlerName, this.identifier, this.backpackSlotIndex, this.openFromInventory, subBackpackSlotIndex, saveAfterOpen
         );
      }

      @Override
      public BackpackContext getParentBackpackContext() {
         return this;
      }

      @Override
      public BackpackContext.ContextType getType() {
         return BackpackContext.ContextType.ITEM_BACKPACK;
      }

      public static BackpackContext fromBuffer(FriendlyByteBuf buffer) {
         return new BackpackContext.Item(buffer.readUtf(), buffer.readUtf(), buffer.readInt(), buffer.readBoolean());
      }

      @Override
      public void addToBuffer(FriendlyByteBuf buffer) {
         buffer.writeUtf(this.handlerName);
         buffer.writeUtf(this.identifier);
         buffer.writeInt(this.backpackSlotIndex);
         buffer.writeBoolean(this.openFromInventory);
      }

      @Override
      public boolean canInteractWith(Player player) {
         return true;
      }
   }

   public static class ItemSubBackpack extends BackpackContext.Item {
      private final int subBackpackSlotIndex;
      private final boolean saveAfterOpen;
      @Nullable
      private IStorageWrapper parentWrapper;

      public ItemSubBackpack(
         String handlerName, String identifier, int backpackSlotIndex, boolean parentOpenFromInventory, int subBackpackSlotIndex, boolean saveAfterOpen
      ) {
         super(handlerName, identifier, backpackSlotIndex, parentOpenFromInventory);
         this.subBackpackSlotIndex = subBackpackSlotIndex;
         this.saveAfterOpen = saveAfterOpen;
      }

      @Override
      public Optional<IStorageWrapper> getParentBackpackWrapper(Player player) {
         if (this.parentWrapper == null) {
            this.parentWrapper = super.getBackpackWrapper(player);
         }

         return Optional.of(this.parentWrapper);
      }

      @Override
      public IBackpackWrapper getBackpackWrapper(Player player) {
         return this.getParentBackpackWrapper(player).map(parent -> {
            ItemStack stackInSlot = parent.getInventoryHandler().getStackInSlot(this.subBackpackSlotIndex);
            return !(stackInSlot.getItem() instanceof BackpackItem) ? IBackpackWrapper.Noop.INSTANCE : BackpackWrapper.fromStack(stackInSlot);
         }).orElse(IBackpackWrapper.Noop.INSTANCE);
      }

      public static BackpackContext fromBuffer(FriendlyByteBuf buffer) {
         return new BackpackContext.ItemSubBackpack(
            buffer.readUtf(), buffer.readUtf(), buffer.readInt(), buffer.readBoolean(), buffer.readInt(), buffer.readBoolean()
         );
      }

      @Override
      public void addToBuffer(FriendlyByteBuf buffer) {
         super.addToBuffer(buffer);
         buffer.writeInt(this.subBackpackSlotIndex);
         buffer.writeBoolean(this.saveAfterOpen);
      }

      @Override
      public BackpackContext getParentBackpackContext() {
         return new BackpackContext.Item(this.handlerName, this.identifier, this.backpackSlotIndex, super.wasOpenFromInventory());
      }

      @Override
      public BackpackContext.ContextType getType() {
         return BackpackContext.ContextType.ITEM_SUB_BACKPACK;
      }

      @Override
      public Component getDisplayName(Player player) {
         return Component.literal("... > " + super.getDisplayName(player).getString());
      }

      @Override
      public void onUpgradeChanged(Player player) {
      }

      @Override
      public boolean shouldSaveAfterOpen() {
         return this.saveAfterOpen;
      }

      @Override
      public void saveBackpackStack() {
         if (this.parentWrapper != null) {
            this.parentWrapper
               .getInventoryHandler()
               .setStackInSlot(this.subBackpackSlotIndex, this.parentWrapper.getInventoryHandler().getStackInSlot(this.subBackpackSlotIndex));
            this.parentWrapper.getInventoryHandler().saveInventory();
         }
      }
   }
}
