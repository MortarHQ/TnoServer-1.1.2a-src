package org.crypticdev.stextras.quest.assignment;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.crypticdev.stextras.quest.registry.QuestRegistry;
import org.crypticdev.stextras.storage.quest.ISTExtrasQuests;
import org.crypticdev.stextras.storage.quest.STExtrasQuestStorage;

public final class RepeatableQuestService {
   public static boolean canStartRepeatable(ServerPlayer player, ResourceLocation id) {
      QuestDefinition QuestDefinition = QuestRegistry.INSTANCE.byId(id);
      if (QuestDefinition != null && QuestDefinition.category == QuestCategory.REPEATABLE) {
         STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
         return store.active(QuestCategory.REPEATABLE).contains(id) ? false : !store.isOnCooldown(id);
      } else {
         return false;
      }
   }

   public static long repeatableCooldownRemaining(ServerPlayer player, ResourceLocation id) {
      STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
      return store.cooldownRemaining(id);
   }

   public static boolean tryStartRepeatable(ServerPlayer player, ResourceLocation id) {
      if (!canStartRepeatable(player, id)) {
         return false;
      }

      STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
      store.addActive(id);
      store.setProgress(id, 0);
      return true;
   }

   public static void ensureRepeatablesApplied(ServerPlayer player) {
      STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
      List<QuestDefinition> pool = QuestRegistry.INSTANCE.byCategory(QuestCategory.REPEATABLE);
      Set<ResourceLocation> poolIds = new HashSet<>(Math.max(16, pool.size() * 2));

      for (QuestDefinition QuestDefinition : pool) {
         poolIds.add(QuestDefinition.id);
      }

      long now = Instant.now().getEpochSecond();

      for (ResourceLocation id : new ArrayList<>(store.active(QuestCategory.REPEATABLE))) {
         if (!poolIds.contains(id)) {
            store.removeActive(id);
            store.clearCooldown(id);
         }
      }

      for (ResourceLocation id : new ArrayList<>(store.completed(QuestCategory.REPEATABLE))) {
         if (!poolIds.contains(id)) {
            store.resetQuest(id);
            store.clearCooldown(id);
         }
      }

      for (ResourceLocation id : new ArrayList<>(store.progressMap(QuestCategory.REPEATABLE).keySet())) {
         if (!poolIds.contains(id)) {
            store.resetQuest(id);
            store.clearCooldown(id);
         }
      }

      Map<ResourceLocation, Long> cooldowns = store.getCooldownsSnapshot();
      Set<ResourceLocation> active = new HashSet<>(store.active(QuestCategory.REPEATABLE));

      for (Entry<ResourceLocation, Long> entry : cooldowns.entrySet()) {
         ResourceLocation id = entry.getKey();
         long until = entry.getValue();
         if (until > 0L && until <= now) {
            store.clearCooldown(id);
            if (poolIds.contains(id) && !active.contains(id)) {
               store.addActive(id);
               store.setProgress(id, 0);
               active.add(id);
            }
         }
      }

      for (QuestDefinition QuestDefinition : pool) {
         ResourceLocation id = QuestDefinition.id;
         if (!active.contains(id)) {
            long until = cooldowns.getOrDefault(id, 0L);
            if (until <= 0L) {
               store.addActive(id);
               store.setProgress(id, 0);
               active.add(id);
            }
         }
      }
   }

   private RepeatableQuestService() {
   }
}
