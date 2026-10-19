package org.crypticdev.stextras.integration.FTB.Quests.task;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.AbstractBooleanTask;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public abstract class AbstractSTExtrasTask extends AbstractBooleanTask {
   private static final String TEAM_AWARE_KEY = "team_aware";
   protected boolean teamAware = true;

   protected AbstractSTExtrasTask(long id, Quest quest) {
      super(id, quest);
   }

   protected boolean matchesAnyMember(TeamData teamData, ServerPlayer player, Predicate<ServerPlayer> predicate) {
      if (this.teamAware && teamData != null) {
         Set<UUID> seen = new HashSet<>();

         for (ServerPlayer member : teamData.getOnlineMembers()) {
            if (member != null) {
               seen.add(member.getUUID());
               if (predicate.test(member)) {
                  return true;
               }
            }
         }

         return player != null && !seen.contains(player.getUUID()) && predicate.test(player);
      } else {
         return player != null && predicate.test(player);
      }
   }

   protected static ResourceLocation parseOptionalId(String raw) {
      return raw != null && !raw.isBlank() ? ResourceLocation.tryParse(raw.trim()) : null;
   }

   protected static boolean matchesConfiguredId(String configuredId, ResourceLocation actualId) {
      if (actualId == null) {
         return false;
      } else if (configuredId != null && !configuredId.isBlank()) {
         ResourceLocation parsed = parseOptionalId(configuredId);
         return parsed != null && actualId.equals(parsed);
      } else {
         return true;
      }
   }

   protected static boolean containsConfiguredId(Iterable<ResourceLocation> ids, String configuredId) {
      boolean allowAny = configuredId == null || configuredId.isBlank();
      ResourceLocation parsed = parseOptionalId(configuredId);
      boolean any = false;

      for (ResourceLocation id : ids) {
         any = true;
         if (parsed != null && parsed.equals(id)) {
            return true;
         }
      }

      return allowAny && any;
   }

   protected static String cleanConfiguredId(String raw) {
      return raw == null ? "" : raw.trim();
   }

   public void writeData(CompoundTag tag, Provider provider) {
      super.writeData(tag, provider);
      tag.putBoolean("team_aware", this.teamAware);
   }

   public void readData(CompoundTag tag, Provider provider) {
      super.readData(tag, provider);
      if (tag.contains("team_aware")) {
         this.teamAware = tag.getBoolean("team_aware");
      }
   }

   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeBoolean(this.teamAware);
   }

   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.teamAware = buffer.readBoolean();
   }

   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addBool("team_aware", this.teamAware, value -> this.teamAware = value, true).setNameKey("ftbquests.task.stextras.config.team_aware");
   }
}
