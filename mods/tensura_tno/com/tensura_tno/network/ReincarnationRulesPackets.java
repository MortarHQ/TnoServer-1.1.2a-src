package com.tensura_tno.network;

import com.tensura_tno.TensuraTNOMod;
import com.tensura_tno.world.TensuraTNOGameRules;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.networking.NetworkManager.Side;
import io.github.manasmods.tensura.world.TensuraGameRules;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.GameRuleTypeVisitor;
import net.minecraft.world.level.GameRules.IntegerValue;
import net.minecraft.world.level.GameRules.Key;
import net.minecraft.world.level.GameRules.Type;

public final class ReincarnationRulesPackets {
   public static final String RULE_HARDCORE_RACE = "hardcoreRace";
   public static final String RULE_EP_DEATH_PENALTY = "epDeathPenalty";
   public static final String RULE_UNIQUE_SE_COST = "uniqueSECost";
   public static final String RULE_KEEP_INVENTORY = "keepInventory";
   public static final String RULE_SUBORDINATE_SKILL_AI = "subordinateSkillAi";
   public static volatile boolean clientHardcoreRace = false;
   public static volatile int clientEpDeathPenalty = 5;
   public static volatile boolean clientUniqueSECostAvailable = false;
   public static volatile boolean clientUniqueSECost = true;
   public static volatile boolean clientKeepInventory = false;
   public static volatile boolean clientSubordinateSkillAi = false;

   private ReincarnationRulesPackets() {
   }

   public static void registerC2S() {
      NetworkManager.registerReceiver(
         Side.C2S,
         ReincarnationRulesPackets.RequestRulesPayload.TYPE,
         ReincarnationRulesPackets.RequestRulesPayload.STREAM_CODEC,
         (payload, context) -> context.queue(() -> syncToRequester(context))
      );
      NetworkManager.registerReceiver(
         Side.C2S,
         ReincarnationRulesPackets.SetRulePayload.TYPE,
         ReincarnationRulesPackets.SetRulePayload.STREAM_CODEC,
         (payload, context) -> context.queue(() -> handleSetRule(payload, context))
      );
   }

   public static void registerS2C() {
      NetworkManager.registerReceiver(
         Side.S2C,
         ReincarnationRulesPackets.SyncRulesPayload.TYPE,
         ReincarnationRulesPackets.SyncRulesPayload.STREAM_CODEC,
         (payload, context) -> context.queue(() -> {
            clientHardcoreRace = payload.hardcoreRace();
            clientEpDeathPenalty = payload.epDeathPenalty();
            clientUniqueSECostAvailable = payload.uniqueSECostAvailable();
            clientUniqueSECost = payload.uniqueSECost();
            clientKeepInventory = payload.keepInventory();
            clientSubordinateSkillAi = payload.subordinateSkillAi();
         })
      );
   }

   public static void requestSync() {
      try {
         NetworkManager.sendToServer(new ReincarnationRulesPackets.RequestRulesPayload(0));
      } catch (Throwable t) {
         TensuraTNOMod.LOGGER.warn("[TensuraTNO] Failed to request reincarnation gamerules: {}", t.toString());
      }
   }

   private static void syncToRequester(PacketContext context) {
      if (context.getPlayer() instanceof ServerPlayer player) {
         syncToClient(player);
      }
   }

   private static void handleSetRule(ReincarnationRulesPackets.SetRulePayload payload, PacketContext context) {
      if (context.getPlayer() instanceof ServerPlayer player) {
         MinecraftServer server = player.getServer();
         if (server != null && canEditRules(player, server)) {
            GameRules rules = player.serverLevel().getGameRules();

            try {
               switch (payload.ruleId()) {
                  case "hardcoreRace":
                     ((BooleanValue)rules.getRule(TensuraGameRules.HARDCORE_RACE)).set(Boolean.parseBoolean(payload.value()), server);
                     break;
                  case "epDeathPenalty":
                     ((IntegerValue)rules.getRule(TensuraGameRules.EP_DEATH_PENALTY)).set(Math.max(0, Integer.parseInt(payload.value())), server);
                     break;
                  case "keepInventory":
                     ((BooleanValue)rules.getRule(GameRules.RULE_KEEPINVENTORY)).set(Boolean.parseBoolean(payload.value()), server);
                     break;
                  case "uniqueSECost":
                     Key<BooleanValue> key = findBooleanRuleKey("uniqueSECost");
                     if (key != null) {
                        ((BooleanValue)rules.getRule(key)).set(Boolean.parseBoolean(payload.value()), server);
                     }
                     break;
                  case "subordinateSkillAi":
                     ((BooleanValue)rules.getRule(TensuraTNOGameRules.SUBORDINATE_SKILL_AI)).set(Boolean.parseBoolean(payload.value()), server);
                     break;
                  default:
                     return;
               }
            } catch (RuntimeException e) {
               TensuraTNOMod.LOGGER.warn("[TensuraTNO] Failed to update gamerule {}={}: {}", new Object[]{payload.ruleId(), payload.value(), e.toString()});
            }

            syncToClient(player);
         } else {
            syncToClient(player);
         }
      }
   }

   private static void syncToClient(ServerPlayer player) {
      if (player != null) {
         GameRules rules = player.serverLevel().getGameRules();
         Key<BooleanValue> uniqueKey = findBooleanRuleKey("uniqueSECost");
         boolean uniqueAvailable = uniqueKey != null;
         boolean uniqueValue = uniqueAvailable && rules.getBoolean(uniqueKey);

         try {
            NetworkManager.sendToPlayer(
               player,
               new ReincarnationRulesPackets.SyncRulesPayload(
                  rules.getBoolean(TensuraGameRules.HARDCORE_RACE),
                  rules.getInt(TensuraGameRules.EP_DEATH_PENALTY),
                  uniqueAvailable,
                  uniqueValue,
                  rules.getBoolean(GameRules.RULE_KEEPINVENTORY),
                  rules.getBoolean(TensuraTNOGameRules.SUBORDINATE_SKILL_AI)
               )
            );
         } catch (Throwable t) {
            TensuraTNOMod.LOGGER.warn("[TensuraTNO] Failed to sync reincarnation gamerules to {}: {}", player.getGameProfile().getName(), t.toString());
         }
      }
   }

   private static Key<BooleanValue> findBooleanRuleKey(final String id) {
      final AtomicReference<Key<BooleanValue>> result = new AtomicReference<>();
      GameRules.visitGameRuleTypes(new GameRuleTypeVisitor() {
         public void visitBoolean(Key<BooleanValue> key, Type<BooleanValue> type) {
            if (key.getId().equals(id)) {
               result.set(key);
            }
         }
      });
      return result.get();
   }

   private static boolean canEditRules(ServerPlayer player, MinecraftServer server) {
      return server.isDedicatedServer() ? player.hasPermissions(2) : server.isSingleplayerOwner(player.getGameProfile());
   }

   public record RequestRulesPayload(int nonce) implements CustomPacketPayload {
      public static final net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type<ReincarnationRulesPackets.RequestRulesPayload> TYPE = new net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type(
         ResourceLocation.fromNamespaceAndPath("tensura_tno", "request_reincarnation_rules")
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, ReincarnationRulesPackets.RequestRulesPayload> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.VAR_INT, ReincarnationRulesPackets.RequestRulesPayload::nonce, ReincarnationRulesPackets.RequestRulesPayload::new
      );

      public net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }
   }

   public record SetRulePayload(String ruleId, String value) implements CustomPacketPayload {
      public static final net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type<ReincarnationRulesPackets.SetRulePayload> TYPE = new net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type(
         ResourceLocation.fromNamespaceAndPath("tensura_tno", "set_reincarnation_rule")
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, ReincarnationRulesPackets.SetRulePayload> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.STRING_UTF8,
         ReincarnationRulesPackets.SetRulePayload::ruleId,
         ByteBufCodecs.STRING_UTF8,
         ReincarnationRulesPackets.SetRulePayload::value,
         ReincarnationRulesPackets.SetRulePayload::new
      );

      public net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }
   }

   public record SyncRulesPayload(
      boolean hardcoreRace, int epDeathPenalty, boolean uniqueSECostAvailable, boolean uniqueSECost, boolean keepInventory, boolean subordinateSkillAi
   ) implements CustomPacketPayload {
      public static final net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type<ReincarnationRulesPackets.SyncRulesPayload> TYPE = new net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type(
         ResourceLocation.fromNamespaceAndPath("tensura_tno", "sync_reincarnation_rules")
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, ReincarnationRulesPackets.SyncRulesPayload> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.BOOL,
         ReincarnationRulesPackets.SyncRulesPayload::hardcoreRace,
         ByteBufCodecs.VAR_INT,
         ReincarnationRulesPackets.SyncRulesPayload::epDeathPenalty,
         ByteBufCodecs.BOOL,
         ReincarnationRulesPackets.SyncRulesPayload::uniqueSECostAvailable,
         ByteBufCodecs.BOOL,
         ReincarnationRulesPackets.SyncRulesPayload::uniqueSECost,
         ByteBufCodecs.BOOL,
         ReincarnationRulesPackets.SyncRulesPayload::keepInventory,
         ByteBufCodecs.BOOL,
         ReincarnationRulesPackets.SyncRulesPayload::subordinateSkillAi,
         ReincarnationRulesPackets.SyncRulesPayload::new
      );

      public net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }
   }
}
