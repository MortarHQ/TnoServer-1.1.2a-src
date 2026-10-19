package org.crypticdev.stextras.mixin.tensura.menu;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.menu.ReincarnationMenu;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.crypticdev.stextras.config.STPrestigeConfig;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.crypticdev.stextras.net.STExtrasQuestSync;
import org.crypticdev.stextras.quest.assignment.RacePrestigeAssignmentService;
import org.crypticdev.stextras.storage.STExtarsStorage;
import org.crypticdev.stextras.storage.quest.ISTExtrasQuests;
import org.crypticdev.stextras.storage.quest.STExtrasQuestStorage;
import org.crypticdev.stextras.utils.PrestigeUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ReincarnationMenu.class)
public class ReincarnationMenuMixin {
   @Inject(method = "setRace(Lnet/minecraft/world/entity/player/Player;Lio/github/manasmods/manascore/race/api/ManasRace;ZZ)V", at = @At("TAIL"))
   private static void stextras$setRace(Player player, ManasRace race, boolean resetEP, boolean grantUnique, CallbackInfo ci) {
      if (player instanceof ServerPlayer serverPlayer) {
         PrestigeUtils.applyPrestigeQuests(serverPlayer);
         RacePrestigeAssignmentService.ensureForCurrentRace(serverPlayer, false, false);
         STPlayerEvents.firePrestigeLevel(serverPlayer, TensuraStorages.getPlayerDataFrom(serverPlayer).getResetCounter());
         STExtrasQuestStorage store = ((ISTExtrasQuests)serverPlayer).stextras$getQuests();
         STExtrasQuestSync.sendWithRaceIds(serverPlayer, store.toNet());
         store.clearDirty();
      }
   }

   @Inject(
      method = "randomUniqueSkill",
      at = @At(
         value = "FIELD",
         target = "Lio/github/manasmods/tensura/world/TensuraGameRules;RESET_COUNTER_BONUS_UNIQUE:Lnet/minecraft/world/level/GameRules$Key;"
      )
   )
   private static void stextras$applyCustomSkillLocks(
      Player player,
      boolean coverEP,
      CallbackInfo ci,
      @Local(ordinal = 0) List<ManasSkill> collection,
      @Local(ordinal = 1) List<ManasSkill> secondCollection,
      @Local(ordinal = 2) LocalIntRef skillsRef
   ) {
      if (player instanceof ServerPlayer serverPlayer) {
         int customLockedCount = 0;
         List customLocks = STExtarsStorage.Player.getLockedSkills(serverPlayer);
         List adminLocks = STExtarsStorage.Player.getAdminLockedSkills(serverPlayer);
         if (customLocks.contains(UniqueSkills.CHOSEN_ONE.getId()) || adminLocks.contains(UniqueSkills.CHOSEN_ONE.getId())) {
            handleBlessing(serverPlayer);
         }

         for (ResourceLocation lockId : customLocks) {
            ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(lockId);
            if (skill != null && collection.contains(skill)) {
               collection.remove(skill);
               secondCollection.remove(skill);
               customLockedCount++;
            }
         }

         for (ResourceLocation lockId : adminLocks) {
            ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(lockId);
            if (skill != null && collection.contains(skill)) {
               collection.remove(skill);
               secondCollection.remove(skill);
               customLockedCount++;
            }
         }

         if (!getConfig().ignoreLockedSkillsOnReset) {
            skillsRef.set(skillsRef.get() - customLockedCount);
         }
      }
   }

   private static void handleBlessing(ServerPlayer player) {
      if (SkillUtils.hasSkill(player, (ManasSkill)UniqueSkills.CHOSEN_ONE.get())) {
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         if (!existence.isBlessed()) {
            existence.setBlessed(true);
         }
      }
   }

   private static STPrestigeConfig getConfig() {
      return (STPrestigeConfig)ConfigRegistry.getConfig(STPrestigeConfig.class);
   }
}
