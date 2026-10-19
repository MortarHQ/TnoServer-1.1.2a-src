package net.blay09.mods.netherportalfix;

import java.util.UUID;
import net.blay09.mods.balm.api.Balm;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.PortalForcer;
import org.jetbrains.annotations.Nullable;

public class ReturnPortalManager {
   private static final int MAX_PORTAL_DISTANCE_SQ = 16;
   private static final String RETURN_PORTAL_LIST = "ReturnPortalList";
   private static final String RETURN_PORTAL_UID = "UID";
   private static final String FROM_DIM = "FromDim";
   private static final String FROM_POS = "FromPos";
   private static final String TO_POS = "ToPos";

   public static BlockPos findPortalAt(Player player, ResourceKey<Level> dim, BlockPos pos) {
      MinecraftServer server = player.level().getServer();
      if (server != null) {
         ServerLevel fromWorld = server.getLevel(dim);
         if (fromWorld != null) {
            PortalForcer portalForcer = fromWorld.getPortalForcer();
            return (BlockPos)portalForcer.findClosestPortalPosition(pos, false, fromWorld.getWorldBorder()).orElse(null);
         }
      }

      return null;
   }

   public static ListTag getPlayerPortalList(Entity entity) {
      CompoundTag data = Balm.getHooks().getPersistentData(entity);
      ListTag list = data.getList("ReturnPortalList", 10);
      data.put("ReturnPortalList", list);
      return list;
   }

   @Nullable
   public static ReturnPortal findReturnPortal(Entity entity, ResourceKey<Level> fromDim, BlockPos fromPos) {
      for (Tag entry : getPlayerPortalList(entity)) {
         CompoundTag portal = (CompoundTag)entry;
         ResourceKey<Level> entryFromDim = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(portal.getString("FromDim")));
         if (entryFromDim == fromDim) {
            BlockPos portalTrigger = BlockPos.of(portal.getLong("FromPos"));
            if (portalTrigger.distSqr(fromPos) <= 16.0) {
               UUID uid = portal.hasUUID("UID") ? portal.getUUID("UID") : UUID.randomUUID();
               BlockPos pos = BlockPos.of(portal.getLong("ToPos"));
               return new ReturnPortal(uid, pos);
            }
         }
      }

      return null;
   }

   public static void storeReturnPortal(ServerPlayer player, ResourceKey<Level> fromDim, BlockPos fromPos, BlockPos toPos) {
      ListTag portalList = getPlayerPortalList(player);
      ReturnPortal returnPortal = findReturnPortal(player, fromDim, fromPos);
      if (returnPortal != null) {
         removeReturnPortal(player, returnPortal);
      }

      CompoundTag portalCompound = new CompoundTag();
      portalCompound.putUUID("UID", UUID.randomUUID());
      portalCompound.putString("FromDim", String.valueOf(fromDim.location()));
      portalCompound.putLong("FromPos", fromPos.asLong());
      portalCompound.putLong("ToPos", toPos.asLong());
      portalList.add(portalCompound);
   }

   public static void removeReturnPortal(ServerPlayer player, ReturnPortal portal) {
      ListTag portalList = getPlayerPortalList(player);

      for (int i = 0; i < portalList.size(); i++) {
         CompoundTag entry = (CompoundTag)portalList.get(i);
         if (entry.hasUUID("UID") && entry.getUUID("UID").equals(portal.getUid())) {
            portalList.remove(i);
            break;
         }
      }
   }
}
