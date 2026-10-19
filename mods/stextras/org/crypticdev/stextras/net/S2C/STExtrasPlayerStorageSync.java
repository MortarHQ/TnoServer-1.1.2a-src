package org.crypticdev.stextras.net.S2C;

import dev.architectury.networking.NetworkManager;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.Stextras;
import org.crypticdev.stextras.net.STNetworkCompat;
import org.crypticdev.stextras.net.STNetworkGuards;
import org.crypticdev.stextras.storage.player.ISTExtrasPlayer;
import org.crypticdev.stextras.storage.player.client.ClientSTExtrasPlayerStorage;

public record STExtrasPlayerStorageSync(CompoundTag data) implements CustomPacketPayload {
   public static final Type<STExtrasPlayerStorageSync> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("stextras", "sync_player_data"));
   public static final StreamCodec<FriendlyByteBuf, STExtrasPlayerStorageSync> STREAM_CODEC = StreamCodec.of(
      (buf, p) -> buf.writeNbt(p.data()), buf -> new STExtrasPlayerStorageSync(buf.readNbt())
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void registerCommon() {
      STNetworkCompat.registerS2CType(TYPE, STREAM_CODEC);
   }

   public static void registerClient() {
      NetworkManager.registerReceiver(NetworkManager.s2c(), TYPE, STREAM_CODEC, (payload, context) -> {
         CompoundTag nbt = payload.data();
         if (nbt != null) {
            int re = nbt.getInt("reincarnation_essence");
            int soul = nbt.getInt("soul_grade");
            List<ResourceLocation> locked = new ArrayList<>();
            if (nbt.contains("locked_skills")) {
               ListTag list = nbt.getList("locked_skills", 8);

               for (int i = 0; i < list.size(); i++) {
                  String s = list.getString(i);

                  try {
                     ResourceLocation rl = ResourceLocation.tryParse(s);
                     if (rl != null) {
                        locked.add(rl);
                     }
                  } catch (Exception var12) {
                  }
               }
            }

            List<ResourceLocation> adminLocked = new ArrayList<>();
            if (nbt.contains("admin_locked_skills")) {
               ListTag list = nbt.getList("admin_locked_skills", 8);

               for (int i = 0; i < list.size(); i++) {
                  String s = list.getString(i);

                  try {
                     ResourceLocation rl = ResourceLocation.tryParse(s);
                     if (rl != null) {
                        adminLocked.add(rl);
                     }
                  } catch (Exception var11) {
                  }
               }
            }

            context.queue(() -> {
               if (Minecraft.getInstance().player instanceof ClientSTExtrasPlayerStorage access) {
                  access.setReincarnationEssence(re);
                  access.setSoulGrade(soul);
                  access.setLockedSkills(locked);
                  access.setAdminLockedSkills(adminLocked);
               }
            });
         }
      });
   }

   public static CompoundTag buildTagFrom(ISTExtrasPlayer data) {
      CompoundTag nbt = new CompoundTag();
      nbt.putInt("reincarnation_essence", data.getReincarnationEssence());
      nbt.putInt("soul_grade", data.getSoulGrade());
      List<ResourceLocation> locked = data.getLockedSkills();
      if (locked != null && !locked.isEmpty()) {
         ListTag list = new ListTag();

         for (ResourceLocation rl : locked) {
            list.add(StringTag.valueOf(rl.toString()));
         }

         nbt.put("locked_skills", list);
      }

      List<ResourceLocation> adminLocked = data.getAdminLockedSkills();
      if (adminLocked != null && !adminLocked.isEmpty()) {
         ListTag list = new ListTag();

         for (ResourceLocation rl : adminLocked) {
            list.add(StringTag.valueOf(rl.toString()));
         }

         nbt.put("admin_locked_skills", list);
      }

      return nbt;
   }

   public static void sync(ServerPlayer player) {
      if (player instanceof ISTExtrasPlayer data) {
         CompoundTag tag = buildTagFrom(data);
         sendTo(player, tag);
      }
   }

   public static void sendTo(ServerPlayer player, CompoundTag nbt) {
      if (!STNetworkGuards.canSendTo(player)) {
         Stextras.LOGGER
            .warn(
               "[NETWORK_GUARD] Skipped STExtrasPlayerStorageSync payload because player cannot receive packets | player={}",
               STNetworkGuards.playerName(player)
            );
      } else {
         NetworkManager.sendToPlayer(player, new STExtrasPlayerStorageSync(nbt));
      }
   }
}
