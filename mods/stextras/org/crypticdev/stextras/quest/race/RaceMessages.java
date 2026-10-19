package org.crypticdev.stextras.quest.race;

import io.github.manasmods.manascore.config.ConfigRegistry;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.config.STPrestigeConfig;
import org.crypticdev.stextras.quest.registry.RacePrestigeMetadataRegistry;
import org.crypticdev.stextras.utils.TextUtil;
import org.jetbrains.annotations.Nullable;

public final class RaceMessages {
   public static final STPrestigeConfig CONFIG_PRESTIGE = (STPrestigeConfig)ConfigRegistry.getConfig(STPrestigeConfig.class);

   private RaceMessages() {
   }

   public static Optional<RacePrestigeText> get(ResourceLocation raceId, RaceMessages.Kind kind) {
      return switch (kind) {
         case LORE -> RacePrestigeMetadataRegistry.INSTANCE.lore(raceId);
         case START -> RacePrestigeMetadataRegistry.INSTANCE.start(raceId);
         case COMPLETE -> RacePrestigeMetadataRegistry.INSTANCE.complete(raceId);
      };
   }

   public static void send(ServerPlayer player, ResourceLocation raceId, RaceMessages.Kind kind) {
      get(raceId, kind).ifPresent(text -> send(player, raceId, text));
   }

   public static void send(ServerPlayer player, ResourceLocation raceId, RacePrestigeText text) {
      if (text != null) {
         String title = process(text.title, raceId);
         String subtitle = process(text.subtitle, raceId);
         if (title != null && !title.isEmpty()) {
            player.connection.send(new ClientboundSetTitleTextPacket(Component.literal(title)));
         }

         if (subtitle != null && !subtitle.isEmpty()) {
            player.connection.send(new ClientboundSetSubtitleTextPacket(Component.literal(subtitle)));
         }

         sendIfNotEmpty(player, process(text.chat, raceId));
      }
   }

   private static void sendIfNotEmpty(ServerPlayer player, @Nullable String text) {
      if (text != null && !text.isEmpty()) {
         player.sendSystemMessage(Component.literal(text));
      }
   }

   private static String process(String input, ResourceLocation raceId) {
      if (input != null && !input.isEmpty()) {
         String result = input.replace("%race%", TextUtil.formatedResourceName(raceId));
         if (CONFIG_PRESTIGE.questsUseSmallCaps) {
            result = TextUtil.toSmallCaps(result);
         }

         return result;
      } else {
         return input;
      }
   }

   public static void sendMany(ServerPlayer player, ResourceLocation raceId, RaceMessages.Kind... kinds) {
      for (RaceMessages.Kind kind : kinds) {
         send(player, raceId, kind);
      }
   }

   public enum Kind {
      LORE,
      START,
      COMPLETE;
   }
}
