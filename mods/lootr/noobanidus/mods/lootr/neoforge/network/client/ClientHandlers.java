package noobanidus.mods.lootr.neoforge.network.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.api.data.entity.ILootrEntity;
import noobanidus.mods.lootr.common.client.ClientHooks;

public class ClientHandlers {
   public static void handleCloseCart(int entityId) {
      Level level = Minecraft.getInstance().level;
      if (level == null) {
         LootrAPI.LOG.info("Unable to mark entity with id '" + entityId + "' as closed as level is null.");
      } else {
         Entity cart = level.getEntity(entityId);
         if (cart == null) {
            LootrAPI.LOG.info("Unable to mark entity with id '" + entityId + "' as closed as entity is null.");
         } else if (LootrAPI.resolveEntity(cart) instanceof ILootrEntity lootrCart) {
            lootrCart.setClientOpened(false);
         } else {
            LootrAPI.LOG.info("Unable to mark entity with id '" + entityId + "' as closed as entity is not a Lootr-compatible entity.");
         }
      }
   }

   public static void handleOpenCart(int entityId) {
      Level level = Minecraft.getInstance().level;
      if (level == null) {
         LootrAPI.LOG.info("Unable to mark entity with id '" + entityId + "' as opened as level is null.");
      } else {
         Entity cart = level.getEntity(entityId);
         if (cart == null) {
            LootrAPI.LOG.info("Unable to mark entity with id '" + entityId + "' as opened as entity is null.");
         } else if (LootrAPI.resolveEntity(cart) instanceof ILootrEntity lootrCart) {
            lootrCart.setClientOpened(true);
         } else {
            LootrAPI.LOG.info("Unable to mark entity with id '" + entityId + "' as open as entity is not a Lootr-compatible entity.");
         }
      }
   }

   public static void handleOpenContainer(BlockPos pos) {
      ClientLevel level = Minecraft.getInstance().level;
      if (level != null) {
         BlockEntity blockEntity = level.getBlockEntity(pos);
         if (LootrAPI.resolveBlockEntity(blockEntity) instanceof ILootrBlockEntity lootrBlockEntity) {
            lootrBlockEntity.setClientOpened(true);
            lootrBlockEntity.asBlockEntity().requestModelDataUpdate();
         }

         ClientHooks.clearCache(pos);
      }
   }

   public static void handleCloseContainer(BlockPos pos) {
      ClientLevel level = Minecraft.getInstance().level;
      if (level != null) {
         BlockEntity blockEntity = level.getBlockEntity(pos);
         if (LootrAPI.resolveBlockEntity(blockEntity) instanceof ILootrBlockEntity lootrBlockEntity) {
            lootrBlockEntity.setClientOpened(false);
            lootrBlockEntity.asBlockEntity().requestModelDataUpdate();
         }

         ClientHooks.clearCache(pos);
      }
   }

   public static void handleRefresh() {
      Player player = Minecraft.getInstance().player;
      if (player != null) {
         ClientHooks.clearCache(player.blockPosition());
      }
   }
}
