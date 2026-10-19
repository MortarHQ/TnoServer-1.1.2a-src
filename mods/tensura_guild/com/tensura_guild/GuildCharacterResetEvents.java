package com.tensura_guild;

import com.tensura_guild.data.DwarfTaskData;
import com.tensura_guild.data.DwarfTaskProgressData;
import com.tensura_guild.data.GuildLevelData;
import com.tensura_guild.data.GuildMemberData;
import com.tensura_guild.data.GuildMemberPublicData;
import com.tensura_guild.data.GuildMemberService;
import com.tensura_guild.data.GuildPartyService;
import com.tensura_guild.data.GuildSignatureData;
import com.tensura_guild.data.GuildTaskBoardData;
import com.tensura_guild.data.GuildTitleData;
import com.tensura_guild.registry.GuildAttachments;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.data.TensuraBiomeTags;
import java.util.HashMap;
import java.util.HashSet;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Stop;

public final class GuildCharacterResetEvents {
   private static final ResourceLocation CHARACTER_RESET_SCROLL = ResourceLocation.fromNamespaceAndPath("tensura", "character_reset_scroll");

   private GuildCharacterResetEvents() {
   }

   public static void onUseItemStop(Stop event) {
      if (event.getEntity() instanceof ServerPlayer player && !player.level().isClientSide()) {
         ItemStack stack = event.getItem();
         if (CHARACTER_RESET_SCROLL.equals(BuiltInRegistries.ITEM.getKey(stack.getItem()))) {
            int useTicks = stack.getUseDuration(player) - event.getDuration();
            if (useTicks >= 10
               && !player.isSpectator()
               && !SkillUtils.inSpiritualWorld(player.level().dimension())
               && !player.level().getBiome(player.blockPosition()).is(TensuraBiomeTags.IS_UNSAFE_FOR_SPAWN)) {
               resetGuildProgress(player);
            }
         }
      }
   }

   private static void resetGuildProgress(ServerPlayer player) {
      GuildPartyService.resetPlayer(player);
      player.setData(GuildAttachments.SEEN_DIALOGUES, new HashSet());
      player.setData(GuildAttachments.GUILD_SIGNATURE, GuildSignatureData.EMPTY);
      player.setData(GuildAttachments.GUILD_LEVEL, GuildLevelData.DEFAULT);
      player.setData(GuildAttachments.GUILD_MEMBER, GuildMemberData.EMPTY);
      player.setData(GuildAttachments.GUILD_MEMBER_PUBLIC, GuildMemberPublicData.EMPTY);
      player.setData(GuildAttachments.GUILD_TITLES, GuildTitleData.EMPTY);
      player.setData(GuildAttachments.REPUTATION_COINS, 0);
      player.setData(GuildAttachments.REPUTATION_SHOP_PURCHASES, new HashMap());
      player.setData(GuildAttachments.GUILD_TASK_BOARD, GuildTaskBoardData.EMPTY);
      player.setData(GuildAttachments.DWARF_TASK, DwarfTaskData.EMPTY);
      player.setData(GuildAttachments.DWARF_TASK_PROGRESS, DwarfTaskProgressData.EMPTY);
      player.syncData(GuildAttachments.SEEN_DIALOGUES);
      player.syncData(GuildAttachments.GUILD_SIGNATURE);
      player.syncData(GuildAttachments.GUILD_LEVEL);
      player.syncData(GuildAttachments.GUILD_MEMBER);
      player.syncData(GuildAttachments.GUILD_MEMBER_PUBLIC);
      player.syncData(GuildAttachments.GUILD_TITLES);
      player.syncData(GuildAttachments.REPUTATION_COINS);
      player.syncData(GuildAttachments.GUILD_TASK_BOARD);
      player.syncData(GuildAttachments.DWARF_TASK);
      player.syncData(GuildAttachments.DWARF_TASK_PROGRESS);
      GuildMemberService.syncPublicData(player, GuildMemberData.EMPTY);
      GuildPlayerEvents.refreshNames(player);
      revokeGuildAdvancements(player);
   }

   private static void revokeGuildAdvancements(ServerPlayer player) {
      PlayerAdvancements playerAdvancements = player.getAdvancements();

      for (AdvancementHolder advancement : player.server.getAdvancements().getAllAdvancements()) {
         if ("tensura_guild".equals(advancement.id().getNamespace())) {
            AdvancementProgress progress = playerAdvancements.getOrStartProgress(advancement);

            for (String criterion : progress.getCompletedCriteria()) {
               playerAdvancements.revoke(advancement, criterion);
            }
         }
      }
   }
}
