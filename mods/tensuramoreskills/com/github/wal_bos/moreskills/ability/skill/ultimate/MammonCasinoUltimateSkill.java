package com.github.wal_bos.moreskills.ability.skill.ultimate;

import com.github.wal_bos.moreskills.casino.GreedCasinoProvider;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import com.github.wal_bos.moreskills.util.SkillObtainmentHelper;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class MammonCasinoUltimateSkill extends Skill {
   public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "mammon_casino");
   public static final String TAG_ROOT = "tms_mammon_casino";
   public static final String TAG_CHIPS = "chips";
   public static final String TAG_TOTAL_KILLS = "total_kills";
   public static final String TAG_LAST_KILL_UUID = "last_kill_uuid";
   public static final String TAG_LAST_KILL_GT = "last_kill_gt";
   private static final MoreSkillsConfigs.MammonCasino CONFIG = MoreSkillsConfigs.GRAND.mammonCasino;

   public MammonCasinoUltimateSkill() {
      super(SkillType.ULTIMATE);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/ultimate/mammon_casino.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return (Double)CONFIG.acquirementMagiculeCost.get();
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return (Integer)CONFIG.acquirementMastery.get();
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return SkillObtainmentHelper.canAcquire(
         entity, (Double)CONFIG.acquirementMagiculeCost.get(), (String)CONFIG.requiredSkillId.get(), (Boolean)CONFIG.requiredSkillMustBeMastered.get()
      );
   }

   public int getModes(ManasSkillInstance instance) {
      return 1;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return "mammon_casino.1";
   }

   public Component getModeName(ManasSkillInstance instance, int mode) {
      return Component.translatable("tensuramoreskills.skill.mode.mammon_casino.1");
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         GreedCasinoProvider.open(player);
      }
   }

   public static CompoundTag getRoot(ServerPlayer player) {
      CompoundTag root = player.getPersistentData().getCompound("tms_mammon_casino");
      player.getPersistentData().put("tms_mammon_casino", root);
      return root;
   }

   public static int getChips(ServerPlayer player) {
      return getRoot(player).getInt("chips");
   }

   public static void setChips(ServerPlayer player, int value) {
      CompoundTag root = getRoot(player);
      root.putInt("chips", Math.max(0, value));
      player.getPersistentData().put("tms_mammon_casino", root);
   }

   public static void addChips(ServerPlayer player, int delta) {
      long next = (long)getChips(player) + delta;
      if (next < 0L) {
         next = 0L;
      }

      if (next > 2147483647L) {
         next = 2147483647L;
      }

      setChips(player, (int)next);
   }

   public static int getTotalKills(ServerPlayer player) {
      return getRoot(player).getInt("total_kills");
   }

   public static void addTrackedKill(ServerPlayer player, LivingEntity killed) {
      CompoundTag root = getRoot(player);
      long gameTime = player.level().getGameTime();
      String currentUuid = killed.getUUID().toString();
      String lastUuid = root.getString("last_kill_uuid");
      long lastGt = root.getLong("last_kill_gt");
      if (!currentUuid.equals(lastUuid) || gameTime - lastGt >= ((Integer)CONFIG.duplicateKillProtectionTicks.get()).intValue()) {
         int kills = root.getInt("total_kills") + 1;
         root.putInt("total_kills", kills);
         root.putString("last_kill_uuid", currentUuid);
         root.putLong("last_kill_gt", gameTime);
         long chips = (long)root.getInt("chips") + ((Integer)CONFIG.chipsPerTrackedKill.get()).intValue();
         root.putInt("chips", chips > 2147483647L ? Integer.MAX_VALUE : Math.max(0, (int)chips));
         player.getPersistentData().put("tms_mammon_casino", root);
      }
   }

   public static boolean hasSkill(ServerPlayer player) {
      return SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)AllSkills.MAMMON_CASINO.get()).isPresent();
   }
}
