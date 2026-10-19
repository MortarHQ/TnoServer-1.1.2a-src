package com.tensura_guild;

import com.tensura_guild.data.DwarfTaskService;
import com.tensura_guild.data.GuildMemberData;
import com.tensura_guild.data.GuildMemberPublicData;
import com.tensura_guild.data.GuildMemberService;
import com.tensura_guild.data.GuildPartyService;
import com.tensura_guild.data.GuildSignatureData;
import com.tensura_guild.data.GuildTaskService;
import com.tensura_guild.data.GuildTitleFormatter;
import com.tensura_guild.registry.GuildAttachments;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.world.TensuraGameRules;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.NameFormat;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.TabListNameFormat;

public final class GuildPlayerEvents {
   private static final TextColor GUILD_PREFIX_COLOR = TextColor.fromRgb(8900331);

   private GuildPlayerEvents() {
   }

   public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         GuildSignatureData signature = (GuildSignatureData)player.getData(GuildAttachments.GUILD_SIGNATURE);
         if (signature.hasSignature()) {
            GuildMemberService.ensureRegistered(player);
         }

         GuildMemberService.ensureCurrentTitleAvailable(player);
         GuildMemberService.syncTitleData(player);
         GuildTaskService.syncToPlayer(player);
         DwarfTaskService.syncToPlayer(player);
         GuildPartyService.syncToPlayer(player);
      }
   }

   public static void onPlayerNameFormat(NameFormat event) {
      if (event.getEntity() instanceof Player player) {
         GuildMemberData var3 = (GuildMemberData)player.getData(GuildAttachments.GUILD_MEMBER);
         event.setDisplayname(buildDisplayName(player, var3, getTensuraAwareBaseName(player, event.getDisplayname())));
      }
   }

   public static void onPlayerTabListNameFormat(TabListNameFormat event) {
      if (event.getEntity() instanceof Player player) {
         GuildMemberData var4 = (GuildMemberData)player.getData(GuildAttachments.GUILD_MEMBER);
         Component baseName = event.getDisplayName() == null ? getTensuraAwareBaseName(player, player.getName()) : event.getDisplayName();
         event.setDisplayName(buildDisplayName(player, var4, baseName));
      }
   }

   public static void refreshNames(ServerPlayer player) {
      player.refreshDisplayName();
      player.refreshTabListName();
   }

   public static boolean hasTensuraDisplayName(Player player) {
      return getTensuraDisplayName(player) != null;
   }

   public static boolean hasNameDecoration(GuildMemberData memberData) {
      return hasNameDecoration(GuildMemberPublicData.from(memberData));
   }

   public static boolean hasNameDecoration(GuildMemberPublicData memberData) {
      return memberData.guildPrefixVisible() || memberData.title().isPresent();
   }

   public static Component getTensuraAwareBaseName(Player player, Component fallbackName) {
      String tensuraName = getTensuraDisplayName(player);
      return (Component)(tensuraName == null ? fallbackName : Component.literal(tensuraName).withStyle(fallbackName.getStyle()));
   }

   public static Component buildDisplayName(Player player, GuildMemberData memberData, Component baseName) {
      return buildDisplayName(player, GuildMemberPublicData.from(memberData, shouldShowGuildPrefix(player)), baseName);
   }

   public static Component buildDisplayName(Player player, GuildMemberPublicData memberData, Component baseName) {
      MutableComponent displayName = Component.empty();
      if (memberData.guildPrefixVisible()) {
         displayName.append(Component.literal("[").withStyle(style -> style.withColor(GUILD_PREFIX_COLOR)))
            .append(Component.translatable("screen.tensura_guild.guild.free_guild").withStyle(style -> style.withColor(GUILD_PREFIX_COLOR)))
            .append(Component.literal("]").withStyle(style -> style.withColor(GUILD_PREFIX_COLOR)));
      }

      displayName.append(baseName.copy());
      memberData.title()
         .ifPresent(title -> displayName.append(Component.literal("[")).append(GuildTitleFormatter.format(title)).append(Component.literal("]")));
      return displayName;
   }

   public static boolean shouldShowGuildPrefix(Player player) {
      return !(player instanceof ServerPlayer serverPlayer && !GuildGameRules.showGuildPrefix(serverPlayer));
   }

   private static String getTensuraDisplayName(Player player) {
      return !player.level().getGameRules().getBoolean(TensuraGameRules.TENSURA_DISPLAY_NAME) ? null : TensuraStorages.getExistenceFrom(player).getName();
   }
}
