package com.tensura_tno.network;

import com.tensura_tno.registry.TensuraTNOSkills;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.networking.NetworkManager.Side;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public final class EdoTenseiPackets {
   private static final String TAG_ROOT = "tensura_tno_edo_tensei";
   private static final String TAG_UNLOCKED = "unlocked_entities";
   public static final Set<String> VALID_ENTITIES = Set.of(
      "tensura:aqua_frog",
      "tensura:arch_daemon",
      "tensura:armorsaurus",
      "tensura:barghest",
      "tensura:beast_gnome",
      "tensura:black_spider",
      "tensura:cattledeer",
      "tensura:direwolf",
      "tensura:evil_centipede",
      "tensura:feathered_serpent",
      "tensura:giant_bat",
      "tensura:giant_cod",
      "tensura:giant_salmon",
      "tensura:hell_moth",
      "tensura:hound_dog",
      "tensura:leech_lizard",
      "tensura:megalodon",
      "tensura:metal_slime",
      "tensura:phantaspore",
      "tensura:salamander",
      "tensura:sissie",
      "tensura:slime",
      "tensura:spear_toro",
      "tensura:tempest_serpent",
      "tensura:winged_cat",
      "tensura:dwarf",
      "tensura:goblin",
      "tensura:lizardman",
      "tensura:orc"
   );
   public static final Set<String> BOSS_ENTITIES = Set.of(
      "tensura:akash",
      "tensura:charybdis",
      "tensura:elemental_colossus",
      "tensura:ifrit",
      "tensura:orc_lord",
      "tensura:orc_disaster",
      "tensura:shizu",
      "tensura:supermassive_slime",
      "tensura:sylphide",
      "tensura:undine",
      "tensura:war_gnome"
   );
   public static final Set<String> ALL_ENTITIES;

   public static void registerC2S() {
      NetworkManager.registerReceiver(
         Side.C2S,
         EdoTenseiPackets.SelectEntityPayload.TYPE,
         EdoTenseiPackets.SelectEntityPayload.STREAM_CODEC,
         (payload, context) -> context.queue(() -> handleSelect(payload, context))
      );
   }

   public static void registerS2C() {
      NetworkManager.registerReceiver(
         Side.S2C, EdoTenseiPackets.OpenScreenPayload.TYPE, EdoTenseiPackets.OpenScreenPayload.STREAM_CODEC, (payload, context) -> context.queue(() -> {
            List<String> ids = payload.unlockedCsv.isBlank() ? List.of() : List.of(payload.unlockedCsv.split(","));
            openClientScreen(ids, payload.mastered);
         })
      );
   }

   private static void openClientScreen(List<String> ids, boolean mastered) {
      try {
         Class<?> minecraftClass = Class.forName("net.minecraft.client.Minecraft");
         Object minecraft = minecraftClass.getMethod("getInstance").invoke(null);
         Class<?> screenClass = Class.forName("com.tensura_tno.client.screen.EdoTenseiScreen");
         Object screen = screenClass.getConstructor(List.class, boolean.class).newInstance(ids, mastered);
         minecraftClass.getMethod("setScreen", Class.forName("net.minecraft.client.gui.screens.Screen")).invoke(minecraft, screen);
      } catch (Exception var6) {
      }
   }

   private static void handleSelect(EdoTenseiPackets.SelectEntityPayload payload, PacketContext context) {
      if (context.getPlayer() instanceof ServerPlayer sp) {
         String var8 = payload.entityId();
         if (ALL_ENTITIES.contains(var8)) {
            Skills skills = SkillAPI.getSkillsFrom(sp);
            Optional<ManasSkillInstance> opt = skills.getSkill((ManasSkill)TensuraTNOSkills.EDO_TENSEI.get());
            if (!opt.isEmpty()) {
               ManasSkillInstance instance = opt.get();
               boolean mastered = instance.isMastered(sp);
               if (!BOSS_ENTITIES.contains(var8) || mastered) {
                  if (isUnlockedForPlayer(sp, var8, mastered)) {
                     instance.getOrCreateTag().putString("SelectedEntity", var8);
                     skills.markDirty();
                  }
               }
            }
         }
      }
   }

   private static boolean isUnlockedForPlayer(ServerPlayer player, String entityId, boolean mastered) {
      if (player.isCreative()) {
         return true;
      } else {
         return BOSS_ENTITIES.contains(entityId) && !mastered ? false : getUnlockedEntityIds(player, mastered).contains(entityId);
      }
   }

   public static List<String> getUnlockedEntityIds(ServerPlayer player, boolean mastered) {
      LinkedHashSet<String> unlocked = new LinkedHashSet<>();
      Set<String> pool = mastered ? ALL_ENTITIES : VALID_ENTITIES;
      if (player.isCreative()) {
         unlocked.addAll(pool);
         return new ArrayList<>(unlocked);
      }

      CompoundTag root = player.getPersistentData().getCompound("tensura_tno_edo_tensei");

      for (Tag t : root.getList("unlocked_entities", 8)) {
         String id = t.getAsString();
         if (pool.contains(id)) {
            unlocked.add(id);
         }
      }

      for (String idStr : pool) {
         ResourceLocation id = ResourceLocation.parse(idStr);
         EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(id);
         if (type != null && player.getStats().getValue(Stats.ENTITY_KILLED.get(type)) > 0) {
            unlocked.add(idStr);
         }
      }

      return new ArrayList<>(unlocked);
   }

   private static void unlockEntity(ServerPlayer player, String entityId) {
      if (ALL_ENTITIES.contains(entityId)) {
         CompoundTag root = player.getPersistentData().getCompound("tensura_tno_edo_tensei");
         ListTag list = root.getList("unlocked_entities", 8);

         for (Tag t : list) {
            if (entityId.equals(t.getAsString())) {
               player.getPersistentData().put("tensura_tno_edo_tensei", root);
               return;
            }
         }

         list.add(StringTag.valueOf(entityId));
         root.put("unlocked_entities", list);
         player.getPersistentData().put("tensura_tno_edo_tensei", root);
      }
   }

   public static void onLivingDeath(LivingDeathEvent event) {
      LivingEntity target = event.getEntity();
      if (target != null) {
         if (event.getSource().getEntity() instanceof ServerPlayer player) {
            ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());
            if (id != null) {
               String idStr = id.toString();
               if (VALID_ENTITIES.contains(idStr)) {
                  unlockEntity(player, idStr);
               }

               if (BOSS_ENTITIES.contains(idStr)) {
                  Skills skills = SkillAPI.getSkillsFrom(player);
                  Optional<ManasSkillInstance> opt = skills.getSkill((ManasSkill)TensuraTNOSkills.EDO_TENSEI.get());
                  if (opt.isPresent()) {
                     unlockEntity(player, idStr);
                  }
               }
            }
         }
      }
   }

   private EdoTenseiPackets() {
   }

   static {
      LinkedHashSet<String> all = new LinkedHashSet<>(VALID_ENTITIES);
      all.addAll(BOSS_ENTITIES);
      ALL_ENTITIES = Set.copyOf(all);
   }

   public record OpenScreenPayload(String unlockedCsv, boolean mastered) implements CustomPacketPayload {
      public static final Type<EdoTenseiPackets.OpenScreenPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_tno", "edo_tensei_open"));
      public static final StreamCodec<RegistryFriendlyByteBuf, EdoTenseiPackets.OpenScreenPayload> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.STRING_UTF8,
         EdoTenseiPackets.OpenScreenPayload::unlockedCsv,
         ByteBufCodecs.BOOL,
         EdoTenseiPackets.OpenScreenPayload::mastered,
         EdoTenseiPackets.OpenScreenPayload::new
      );

      public Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }
   }

   public record SelectEntityPayload(String entityId) implements CustomPacketPayload {
      public static final Type<EdoTenseiPackets.SelectEntityPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_tno", "edo_tensei_select"));
      public static final StreamCodec<RegistryFriendlyByteBuf, EdoTenseiPackets.SelectEntityPayload> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.STRING_UTF8, EdoTenseiPackets.SelectEntityPayload::entityId, EdoTenseiPackets.SelectEntityPayload::new
      );

      public Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }
   }
}
