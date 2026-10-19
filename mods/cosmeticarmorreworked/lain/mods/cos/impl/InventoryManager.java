package lain.mods.cos.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.io.File;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nonnull;
import lain.mods.cos.api.event.CosArmorDeathDrops;
import lain.mods.cos.impl.inventory.ContainerCosArmor;
import lain.mods.cos.impl.inventory.InventoryCosArmor;
import lain.mods.cos.impl.network.payload.PayloadSyncCosArmor;
import lain.mods.cos.impl.network.payload.PayloadSyncHiddenFlags;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.storage.LevelResource;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.SaveToFile;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class InventoryManager {
   protected static final InventoryCosArmor Dummy = new InventoryCosArmor() {
      @Nonnull
      public ItemStack extractItem(int slot, int amount, boolean simulate) {
         return ItemStack.EMPTY;
      }

      @Nonnull
      public ItemStack getStackInSlot(int slot) {
         return ItemStack.EMPTY;
      }

      @Nonnull
      public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
         return stack;
      }

      @Override
      public boolean isHidden(String modid, String identifier) {
         return false;
      }

      @Override
      public boolean isSkinArmor(int slot) {
         return false;
      }

      @Override
      protected void onContentsChanged(int slot) {
      }

      @Override
      protected void onLoad() {
      }

      @Override
      public boolean setHidden(String modid, String identifier, boolean set) {
         return false;
      }

      @Override
      public void setSkinArmor(int slot, boolean enabled) {
      }

      public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
      }

      @Override
      public boolean setUpdateListener(InventoryCosArmor.ContentsChangeListener listener) {
         return false;
      }

      @Override
      public boolean setUpdateListener(InventoryCosArmor.HiddenFlagsChangeListener listener) {
         return false;
      }
   };
   protected static final Random RANDOM = new Random();
   protected final LoadingCache<UUID, InventoryCosArmor> CommonCache = CacheBuilder.newBuilder().build(new CacheLoader<UUID, InventoryCosArmor>() {
      public InventoryCosArmor load(UUID key) throws Exception {
         InventoryCosArmor inventory = new InventoryCosArmor();
         inventory.setUpdateListener((inv, slot) -> InventoryManager.this.onInventoryChanged(key, inv, slot));
         inventory.setUpdateListener((inv, modid, identifier) -> InventoryManager.this.onHiddenFlagsChanged(key, inv, modid, identifier));
         InventoryManager.this.loadInventory(key, inventory);
         return inventory;
      }
   });

   public static boolean checkIdentifier(String modid, String identifier) {
      return modid != null && !modid.isEmpty() && identifier != null && !identifier.isEmpty() && ModList.get().isLoaded(modid) ? false : false;
   }

   public ContainerCosArmor createContainerClient(int windowId, Inventory invPlayer) {
      throw new UnsupportedOperationException();
   }

   @Nonnull
   public InventoryCosArmor getCosArmorInventory(UUID uuid) {
      return uuid == null ? Dummy : (InventoryCosArmor)this.CommonCache.getUnchecked(uuid);
   }

   @Nonnull
   public InventoryCosArmor getCosArmorInventoryClient(UUID uuid) {
      throw new UnsupportedOperationException();
   }

   protected File getDataFile(UUID uuid) {
      return new File(ServerLifecycleHooks.getCurrentServer().getWorldPath(LevelResource.PLAYER_DATA_DIR).toFile(), uuid + ".cosarmor");
   }

   private void handlePlayerDrops(LivingDropsEvent event) {
      if (event.getEntity() instanceof Player
         && event.getEntity().isEffectiveAi()
         && !event.getEntity().getCommandSenderWorld().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)
         && !(Boolean)ModConfigs.CosArmorKeepThroughDeath.get()) {
         InventoryCosArmor inv = this.getCosArmorInventory(event.getEntity().getUUID());
         if (((CosArmorDeathDrops)NeoForge.EVENT_BUS.post(new CosArmorDeathDrops((Player)event.getEntity(), inv))).isCanceled()) {
            return;
         }

         for (int i = 0; i < inv.getSlots(); i++) {
            ItemStack stack = inv.getStackInSlot(i).copy();
            if (!stack.isEmpty()) {
               float fX = RANDOM.nextFloat() * 0.75F + 0.125F;
               float fY = RANDOM.nextFloat() * 0.75F;
               float fZ = RANDOM.nextFloat() * 0.75F + 0.125F;

               while (!stack.isEmpty()) {
                  ItemEntity entity = new ItemEntity(
                     event.getEntity().getCommandSenderWorld(),
                     event.getEntity().getX() + fX,
                     event.getEntity().getY() + fY,
                     event.getEntity().getZ() + fZ,
                     stack.split(RANDOM.nextInt(21) + 10)
                  );
                  entity.setDeltaMovement(RANDOM.nextGaussian() * 0.05F, RANDOM.nextGaussian() * 0.05F + 0.2F, RANDOM.nextGaussian() * 0.05F);
                  event.getDrops().add(entity);
               }

               inv.setStackInSlot(i, ItemStack.EMPTY);
            }
         }
      }
   }

   private void handlePlayerLoggedIn(PlayerLoggedInEvent event) {
      this.CommonCache.invalidate(event.getEntity().getUUID());
      this.getCosArmorInventory(event.getEntity().getUUID());
      if (event.getEntity() instanceof ServerPlayer) {
         ServerPlayer player = (ServerPlayer)event.getEntity();

         for (ServerPlayer other : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
            if (other != player) {
               UUID uuid = other.getUUID();
               InventoryCosArmor inv = this.getCosArmorInventory(uuid);

               for (int i = 0; i < inv.getSlots(); i++) {
                  PacketDistributor.sendToPlayer(player, new PayloadSyncCosArmor(uuid, inv, i), new CustomPacketPayload[0]);
               }

               inv.forEachHidden(
                  (modid, identifier) -> PacketDistributor.sendToPlayer(
                     player, new PayloadSyncHiddenFlags(uuid, inv, modid, identifier), new CustomPacketPayload[0]
                  )
               );
            }
         }
      }
   }

   private void handlePlayerLoggedOut(PlayerLoggedOutEvent event) {
      UUID uuid;
      InventoryCosArmor inv;
      if ((inv = (InventoryCosArmor)this.CommonCache.getIfPresent(uuid = event.getEntity().getUUID())) != null) {
         this.saveInventory(uuid, inv);
         this.CommonCache.invalidate(uuid);
      }
   }

   private void handleRegisterCommands(RegisterCommandsEvent event) {
      event.getDispatcher()
         .register(
            (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("clearcosarmor").requires(s -> s.hasPermission(2)))
                  .executes(
                     s -> {
                        int count = 0;
                        ServerPlayer player = ((CommandSourceStack)s.getSource()).getPlayerOrException();
                        InventoryCosArmor inv = this.getCosArmorInventory(player.getUUID());

                        for (int i = 0; i < inv.getSlots(); i++) {
                           count += inv.extractItem(i, Integer.MAX_VALUE, false).getCount();
                        }

                        int result = count;
                        ((CommandSourceStack)s.getSource())
                           .sendSuccess(
                              () -> Component.translatable("cos.command.clearcosarmor.success.single", new Object[]{result, player.getDisplayName()}), true
                           );
                        return result;
                     }
                  ))
               .then(
                  Commands.argument("targets", EntityArgument.players())
                     .executes(
                        s -> {
                           int count = 0;
                           Collection<ServerPlayer> players = EntityArgument.getPlayers(s, "targets");

                           for (ServerPlayer player : players) {
                              InventoryCosArmor inv = this.getCosArmorInventory(player.getUUID());

                              for (int i = 0; i < inv.getSlots(); i++) {
                                 count += inv.extractItem(i, Integer.MAX_VALUE, false).getCount();
                              }
                           }

                           int result = count;
                           if (players.size() == 1) {
                              ((CommandSourceStack)s.getSource())
                                 .sendSuccess(
                                    () -> Component.translatable(
                                       "cos.command.clearcosarmor.success.single", new Object[]{result, players.iterator().next().getDisplayName()}
                                    ),
                                    true
                                 );
                           } else {
                              ((CommandSourceStack)s.getSource())
                                 .sendSuccess(
                                    () -> Component.translatable("cos.command.clearcosarmor.success.multiple", new Object[]{result, players.size()}), true
                                 );
                           }

                           return result;
                        }
                     )
               )
         );
      if (!(Boolean)ModConfigs.CosArmorDisableCosHatCommand.get()) {
         event.getDispatcher()
            .register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("coshat").requires(s -> s.hasPermission(0))).executes(s -> {
               ServerPlayer player = ((CommandSourceStack)s.getSource()).getPlayerOrException();
               InventoryCosArmor inv = this.getCosArmorInventory(player.getUUID());
               ItemStack stack1 = player.getItemBySlot(EquipmentSlot.MAINHAND);
               ItemStack stack2 = inv.getStackInSlot(3);
               player.setItemSlot(EquipmentSlot.MAINHAND, stack2);
               inv.setStackInSlot(3, stack1);
               return 0;
            }));
      }
   }

   private void handleSaveToFile(SaveToFile event) {
      UUID uuid;
      InventoryCosArmor inv;
      if ((inv = (InventoryCosArmor)this.CommonCache.getIfPresent(uuid = UUID.fromString(event.getPlayerUUID()))) != null) {
         this.saveInventory(uuid, inv);
      }
   }

   private void handleServerStopping(ServerStoppingEvent event) {
      ModObjects.logger.debug("Server is stopping... try to save all still loaded CosmeticArmor data");
      this.CommonCache.asMap().entrySet().forEach(e -> {
         ModObjects.logger.debug(e.getKey());
         this.saveInventory((UUID)e.getKey(), (InventoryCosArmor)e.getValue());
      });
      this.CommonCache.invalidateAll();
   }

   protected void loadInventory(UUID uuid, InventoryCosArmor inventory) {
      if (inventory != Dummy) {
         try {
            File file;
            if ((file = this.getDataFile(uuid)).exists()) {
               inventory.deserializeNBT(ServerLifecycleHooks.getCurrentServer().registryAccess(), NbtIo.read(file.toPath()));
            }
         } catch (Throwable t) {
            ModObjects.logger.fatal("Failed to load CosmeticArmor data", t);
         }
      }
   }

   protected void onHiddenFlagsChanged(UUID uuid, InventoryCosArmor inventory, String modid, String identifier) {
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      if (server.isDedicatedServer()) {
         PacketDistributor.sendToAllPlayers(new PayloadSyncHiddenFlags(uuid, inventory, modid, identifier), new CustomPacketPayload[0]);
      } else {
         server.getPlayerList()
            .getPlayers()
            .forEach(
               player -> PacketDistributor.sendToPlayer(player, new PayloadSyncHiddenFlags(uuid, inventory, modid, identifier), new CustomPacketPayload[0])
            );
      }
   }

   protected void onInventoryChanged(UUID uuid, InventoryCosArmor inventory, int slot) {
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      if (server.isDedicatedServer()) {
         PacketDistributor.sendToAllPlayers(new PayloadSyncCosArmor(uuid, inventory, slot), new CustomPacketPayload[0]);
      } else {
         server.getPlayerList()
            .getPlayers()
            .forEach(player -> PacketDistributor.sendToPlayer(player, new PayloadSyncCosArmor(uuid, inventory, slot), new CustomPacketPayload[0]));
      }
   }

   public void registerEvents() {
      NeoForge.EVENT_BUS.addListener(this::handlePlayerDrops);
      NeoForge.EVENT_BUS.addListener(this::handlePlayerLoggedIn);
      NeoForge.EVENT_BUS.addListener(this::handlePlayerLoggedOut);
      NeoForge.EVENT_BUS.addListener(this::handleSaveToFile);
      NeoForge.EVENT_BUS.addListener(this::handleRegisterCommands);
      NeoForge.EVENT_BUS.addListener(this::handleServerStopping);
   }

   public void registerEventsClient() {
      throw new UnsupportedOperationException();
   }

   protected void saveInventory(UUID uuid, InventoryCosArmor inventory) {
      if (inventory != Dummy) {
         try {
            NbtIo.write(inventory.serializeNBT(ServerLifecycleHooks.getCurrentServer().registryAccess()), this.getDataFile(uuid).toPath());
         } catch (Throwable t) {
            ModObjects.logger.fatal("Failed to save CosmeticArmor data", t);
         }
      }
   }
}
