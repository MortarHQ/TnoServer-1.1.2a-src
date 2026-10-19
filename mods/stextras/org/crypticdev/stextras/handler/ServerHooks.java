package org.crypticdev.stextras.handler;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.event.events.common.PlayerEvent.ChangeDimension;
import dev.architectury.event.events.common.PlayerEvent.PlayerClone;
import dev.architectury.event.events.common.PlayerEvent.PlayerJoin;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.world.TensuraGameRules;
import java.util.List;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules.IntegerValue;
import org.crypticdev.stextras.net.S2C.STExtrasBossKillsSync;
import org.crypticdev.stextras.net.S2C.STExtrasPlayerStorageSync;
import org.crypticdev.stextras.quest.assignment.PlayerQuestAssignmentService;
import org.crypticdev.stextras.storage.STExtarsStorage;
import org.crypticdev.stextras.storage.player.ISTExtrasPlayer;
import org.crypticdev.stextras.storage.quest.ISTExtrasQuests;
import org.crypticdev.stextras.storage.quest.STExtrasQuestStorage;
import org.crypticdev.stextras.utils.PrestigeUtils;

public class ServerHooks {
   public static void register() {
      LifecycleEvent.SERVER_STARTED.register(ServerHooks::enforceFixedTensuraGameRules);
      TickEvent.SERVER_POST.register(ServerHooks::enforceFixedTensuraGameRules);
      PlayerEvent.PLAYER_JOIN.register((PlayerJoin)player -> {
         MinecraftServer server = player.server;
         enforceFixedTensuraGameRules(server);
         PlayerQuestAssignmentService.setupQuests(server, player);
         syncLockedSkills(player);
         syncAllToClient(player);
      });
      PlayerEvent.PLAYER_CLONE.register((PlayerClone)(oldP, newP, alive) -> {
         ISTExtrasPlayer oldData = (ISTExtrasPlayer)oldP;
         ISTExtrasPlayer newData = (ISTExtrasPlayer)newP;
         newData.setReincarnationEssence(oldData.getReincarnationEssence());
         newData.setSoulGrade(oldData.getSoulGrade());
         newData.clearLockedSkills();

         for (ResourceLocation rl : oldData.getLockedSkills()) {
            newData.addLockedSkill(rl);
         }

         newData.clearAdminLockedSkills();

         for (ResourceLocation rl : oldData.getAdminLockedSkills()) {
            newData.addAdminLockedSkill(rl);
         }

         STExtrasQuestStorage oldStore = ((ISTExtrasQuests)oldP).stextras$getQuests();
         STExtrasQuestStorage newStore = ((ISTExtrasQuests)newP).stextras$getQuests();
         newStore.copyFrom(oldStore);
         PlayerQuestAssignmentService.ensurePlayerMatchesSchedule(newP.server, newP);
         syncAllToClient(newP);
      });
      PlayerEvent.CHANGE_DIMENSION.register((ChangeDimension)(player, origin, destination) -> {
         enforceFixedTensuraGameRules(player.server);
         PlayerQuestAssignmentService.ensurePlayerMatchesSchedule(player.server, player);
         syncAllToClient(player);
      });
   }

   public static void syncAllToClient(ServerPlayer sp) {
      ISTExtrasPlayer data = (ISTExtrasPlayer)sp;
      CompoundTag full = STExtrasPlayerStorageSync.buildTagFrom(data);
      STExtrasPlayerStorageSync.sendTo(sp, full);
      data.stextras$clearDirty();
      STExtrasBossKillsSync.sendTo(sp, PrestigeUtils.getBossKillCount(sp));
   }

   private ServerHooks() {
   }

   private static void enforceFixedTensuraGameRules(MinecraftServer server) {
      IntegerValue resetPerSkillLock = (IntegerValue)server.getGameRules().getRule(TensuraGameRules.RESET_PER_SKILL_LOCK);
      if (resetPerSkillLock.get() != 0) {
         resetPerSkillLock.set(0, server);
      }
   }

   private static void syncLockedSkills(ServerPlayer player) {
      List<ResourceLocation> lockedSkills = STExtarsStorage.Player.getLockedSkills(player);
      SkillStorage skillsFrom = SkillAPI.getSkillsFrom(player);

      for (ResourceLocation skillId : lockedSkills) {
         ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(skillId);
         if (skill != null) {
            Optional<ManasSkillInstance> existingSkill = skillsFrom.getSkill(skillId);
            if (!existingSkill.isPresent()) {
               ManasSkillInstance freshInstance = skill.createDefaultInstance();
               freshInstance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
               freshInstance.markDirty();
               SkillHelper.learnSkill(player, freshInstance);
            }
         }
      }
   }
}
