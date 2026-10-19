package com.playernbtftb.tasks;

import com.playernbtftb.PlayerNbtFtbMod;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import dev.ftb.mods.ftbquests.quest.task.TaskTypes;
import net.minecraft.resources.ResourceLocation;

public class ModTaskTypes {
   public static final TaskType PLAYER_NBT = TaskTypes.register(
      ResourceLocation.fromNamespaceAndPath("playernbtftb", "player_nbt"), PlayerNbtTask::new, () -> Icons.FRIENDS
   );

   public static void init() {
      PlayerNbtFtbMod.LOGGER.info("Registering PlayerNBT task type");
   }
}
