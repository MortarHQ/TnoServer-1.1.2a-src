package com.tensura_guild.compat.ftbquests;

import com.tensura_guild.TensuraGuild;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import dev.ftb.mods.ftbquests.quest.task.TaskTypes;
import dev.ftb.mods.ftbquests.quest.task.TaskType.Provider;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public final class GuildFtbQuestTaskTypes {
   public static final TaskType COMPLETE_DWARF_TASK = register("complete_dwarf_task", CompleteDwarfTask::new, () -> Icons.CHECK);
   public static final TaskType COMPLETE_GUILD_TASK = register("complete_guild_task", CompleteGuildTask::new, () -> Icons.MAP);
   public static final TaskType JOIN_GUILD = register("join_guild", JoinGuildTask::new, () -> Icons.FRIENDS_GROUP);
   public static final TaskType ADVENTURE_EXPERIENCE = register("adventure_experience", AdventureExperienceTask::new, () -> Icons.STAR);

   private GuildFtbQuestTaskTypes() {
   }

   public static void init() {
      TensuraGuild.LOGGER.info("Registered Tensura Guild FTB Quests task types");
   }

   private static TaskType register(String path, Provider provider, Supplier<Icon> icon) {
      return TaskTypes.register(ResourceLocation.fromNamespaceAndPath("tensura_guild", path), provider, icon);
   }
}
