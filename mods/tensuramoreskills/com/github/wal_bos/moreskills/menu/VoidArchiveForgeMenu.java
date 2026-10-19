package com.github.wal_bos.moreskills.menu;

import com.github.wal_bos.moreskills.ability.skill.unique.VoidArchiveForgeData;
import com.github.wal_bos.moreskills.ability.skill.unique.VoidArchiveSkill;
import com.github.wal_bos.moreskills.registry.menu.ModMenus;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class VoidArchiveForgeMenu extends AbstractContainerMenu {
   public static final int PAGE_HOME = 0;
   public static final int PAGE_SHAPE = 1;
   public static final int PAGE_CAST = 2;
   public static final int PAGE_DAMAGE = 3;
   public static final int PAGE_EFFECT = 4;
   public static final int PAGE_PARTICLE = 5;
   public static final int PAGE_SUMMON = 6;
   public static final int PAGE_SPECIAL = 7;
   public static final int PAGE_MELEE = 8;
   public static final int PAGE_REVIEW = 9;
   public static final int BUTTON_OPEN_SHAPE = 10;
   public static final int BUTTON_OPEN_CAST = 11;
   public static final int BUTTON_OPEN_DAMAGE = 12;
   public static final int BUTTON_OPEN_EFFECT = 13;
   public static final int BUTTON_OPEN_PARTICLE = 14;
   public static final int BUTTON_OPEN_SUMMON = 15;
   public static final int BUTTON_OPEN_SPECIAL = 16;
   public static final int BUTTON_OPEN_MELEE = 17;
   public static final int BUTTON_OPEN_REVIEW = 18;
   public static final int BUTTON_BACK_HOME = 19;
   public static final int BUTTON_RESET_ALL = 20;
   public static final int BUTTON_SHAPE_BASE = 100;
   public static final int BUTTON_CAST_BASE = 140;
   public static final int BUTTON_PARTICLE_BASE = 180;
   public static final int BUTTON_SECONDARY_DAMAGE_BASE = 240;
   public static final int BUTTON_BLOCK_ACTION_BASE = 300;
   public static final int BUTTON_RANGE_MINUS = 400;
   public static final int BUTTON_RANGE_PLUS = 401;
   public static final int BUTTON_RADIUS_MINUS = 402;
   public static final int BUTTON_RADIUS_PLUS = 403;
   public static final int BUTTON_POWER_MINUS = 404;
   public static final int BUTTON_POWER_PLUS = 405;
   public static final int BUTTON_SPEED_MINUS = 406;
   public static final int BUTTON_SPEED_PLUS = 407;
   public static final int BUTTON_BURST_MINUS = 408;
   public static final int BUTTON_BURST_PLUS = 409;
   public static final int BUTTON_DURATION_MINUS = 410;
   public static final int BUTTON_DURATION_PLUS = 411;
   public static final int BUTTON_MELEE_POWER_MINUS = 412;
   public static final int BUTTON_MELEE_POWER_PLUS = 413;
   public static final int BUTTON_MELEE_DURATION_MINUS = 414;
   public static final int BUTTON_MELEE_DURATION_PLUS = 415;
   public static final int BUTTON_SUMMON_COUNT_MINUS = 416;
   public static final int BUTTON_SUMMON_COUNT_PLUS = 417;
   public static final int BUTTON_TOGGLE_MULTIHIT = 500;
   public static final int BUTTON_TOGGLE_HEAL = 501;
   public static final int BUTTON_TOGGLE_PULL = 502;
   public static final int BUTTON_TOGGLE_LAUNCH = 503;
   public static final int BUTTON_TOGGLE_FOLLOW = 504;
   public static final int BUTTON_TOGGLE_GUARDBREAK = 505;
   public static final int BUTTON_TOGGLE_IGNITE = 506;
   public static final int BUTTON_TOGGLE_SHOCKSLOW = 507;
   public static final int BUTTON_TOGGLE_INVULNERABLE = 508;
   public static final int BUTTON_TOGGLE_GUARDED = 509;
   public static final int BUTTON_TOGGLE_TRAP = 510;
   public static final int BUTTON_TOGGLE_MELEE_ENABLE = 511;
   public static final int BUTTON_TOGGLE_MELEE_DAMAGE = 512;
   public static final int BUTTON_TOGGLE_MELEE_EFFECT = 513;
   public static final int BUTTON_TOGGLE_MELEE_LIFESTEAL = 514;
   public static final int BUTTON_TOGGLE_PROJECTILE_CAST = 515;
   public static final int BUTTON_TOGGLE_SELF_CAST = 516;
   public static final int BUTTON_TOGGLE_AOE_CAST = 517;
   public static final int BUTTON_BARRIER_MINUS = 520;
   public static final int BUTTON_BARRIER_PLUS = 521;
   public static final int BUTTON_RESIST_MINUS = 522;
   public static final int BUTTON_RESIST_PLUS = 523;
   public static final int BUTTON_STEAL_MINUS = 524;
   public static final int BUTTON_STEAL_PLUS = 525;
   public static final int BUTTON_NEXT_NAME = 600;
   public static final int BUTTON_CREATE_ATTACK = 601;
   public static final int BUTTON_CREATE_SUMMON = 602;
   public static final int BUTTON_CREATE_REVIVE = 603;
   public static final int BUTTON_CREATE_MELEE = 604;
   public static final int BUTTON_SCROLL_DAMAGE_UP = 700;
   public static final int BUTTON_SCROLL_DAMAGE_DOWN = 701;
   public static final int BUTTON_SCROLL_EFFECT_UP = 702;
   public static final int BUTTON_SCROLL_EFFECT_DOWN = 703;
   public static final int BUTTON_SCROLL_ENTITY_UP = 704;
   public static final int BUTTON_SCROLL_ENTITY_DOWN = 705;
   public static final int BUTTON_SELECT_ARCHIVED_DAMAGE_BASE = 800;
   public static final int BUTTON_SELECT_ARCHIVED_EFFECT_BASE = 900;
   public static final int BUTTON_SELECT_RECORDED_ENTITY_BASE = 1000;
   private static final int SELECTOR_ROWS = 6;
   private final Player player;
   private final boolean isServer;
   private final DataSlot page = DataSlot.standalone();
   private final DataSlot selectedShape = DataSlot.standalone();
   private final DataSlot selectedCastMode = DataSlot.standalone();
   private final DataSlot selectedParticle = DataSlot.standalone();
   private final DataSlot selectedSecondaryDamage = DataSlot.standalone();
   private final DataSlot selectedName = DataSlot.standalone();
   private final DataSlot selectedRecordedEntity = DataSlot.standalone();
   private final DataSlot selectedBlockAction = DataSlot.standalone();
   private final DataSlot selectedArchivedDamage = DataSlot.standalone();
   private final DataSlot selectedArchivedEffect = DataSlot.standalone();
   private final DataSlot damageScroll = DataSlot.standalone();
   private final DataSlot effectScroll = DataSlot.standalone();
   private final DataSlot entityScroll = DataSlot.standalone();
   private final DataSlot range = DataSlot.standalone();
   private final DataSlot radiusTenths = DataSlot.standalone();
   private final DataSlot powerTenths = DataSlot.standalone();
   private final DataSlot speedTenths = DataSlot.standalone();
   private final DataSlot burstCount = DataSlot.standalone();
   private final DataSlot barrierBypassTenths = DataSlot.standalone();
   private final DataSlot resistanceBypassTenths = DataSlot.standalone();
   private final DataSlot magiculeStealPercent = DataSlot.standalone();
   private final DataSlot selfBuffSeconds = DataSlot.standalone();
   private final DataSlot summonCount = DataSlot.standalone();
   private final DataSlot meleePowerTenths = DataSlot.standalone();
   private final DataSlot meleeDurationSeconds = DataSlot.standalone();
   private final DataSlot multiHit = DataSlot.standalone();
   private final DataSlot healCaster = DataSlot.standalone();
   private final DataSlot pullTarget = DataSlot.standalone();
   private final DataSlot launchTarget = DataSlot.standalone();
   private final DataSlot followAim = DataSlot.standalone();
   private final DataSlot guardBreak = DataSlot.standalone();
   private final DataSlot ignite = DataSlot.standalone();
   private final DataSlot shockSlow = DataSlot.standalone();
   private final DataSlot makeInvulnerable = DataSlot.standalone();
   private final DataSlot giveGuarded = DataSlot.standalone();
   private final DataSlot trapTargets = DataSlot.standalone();
   private final DataSlot meleeEnabled = DataSlot.standalone();
   private final DataSlot meleeApplyDamage = DataSlot.standalone();
   private final DataSlot meleeApplyEffect = DataSlot.standalone();
   private final DataSlot meleeLifeSteal = DataSlot.standalone();
   private final DataSlot projectileCast = DataSlot.standalone();
   private final DataSlot selfCast = DataSlot.standalone();
   private final DataSlot aoeCast = DataSlot.standalone();
   private final List<String> archivedDamageIds = new ArrayList<>();
   private final List<String> archivedEffectIds = new ArrayList<>();
   private final List<String> recordedEntityIds = new ArrayList<>();
   private final List<Integer> recordedEntityKills = new ArrayList<>();
   private final DataSlot archivedDamageSize = DataSlot.standalone();
   private final DataSlot archivedEffectSize = DataSlot.standalone();
   private final DataSlot recordedEntitySize = DataSlot.standalone();

   private VoidArchiveForgeMenu(int containerId, Inventory inventory, Player player) {
      super((MenuType)ModMenus.VOID_ARCHIVE_FORGE_MENU.get(), containerId);
      this.player = player;
      this.isServer = true;
      this.addAllSlots();
      this.applyDefaults();
   }

   public static VoidArchiveForgeMenu server(int containerId, Inventory inventory, ServerPlayer player) {
      VoidArchiveForgeMenu menu = new VoidArchiveForgeMenu(containerId, inventory, player);
      menu.refreshArchiveLists();
      return menu;
   }

   public VoidArchiveForgeMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf buf) {
      super((MenuType)ModMenus.VOID_ARCHIVE_FORGE_MENU.get(), containerId);
      this.player = inventory.player;
      this.isServer = false;
      this.addAllSlots();
      this.page.set(buf.readVarInt());
      this.selectedShape.set(buf.readVarInt());
      this.selectedCastMode.set(buf.readVarInt());
      this.selectedParticle.set(buf.readVarInt());
      this.selectedSecondaryDamage.set(buf.readVarInt());
      this.selectedName.set(buf.readVarInt());
      this.selectedRecordedEntity.set(buf.readVarInt());
      this.selectedBlockAction.set(buf.readVarInt());
      this.selectedArchivedDamage.set(buf.readVarInt());
      this.selectedArchivedEffect.set(buf.readVarInt());
      this.damageScroll.set(buf.readVarInt());
      this.effectScroll.set(buf.readVarInt());
      this.entityScroll.set(buf.readVarInt());
      this.range.set(buf.readVarInt());
      this.radiusTenths.set(buf.readVarInt());
      this.powerTenths.set(buf.readVarInt());
      this.speedTenths.set(buf.readVarInt());
      this.burstCount.set(buf.readVarInt());
      this.barrierBypassTenths.set(buf.readVarInt());
      this.resistanceBypassTenths.set(buf.readVarInt());
      this.magiculeStealPercent.set(buf.readVarInt());
      this.selfBuffSeconds.set(buf.readVarInt());
      this.summonCount.set(buf.readVarInt());
      this.meleePowerTenths.set(buf.readVarInt());
      this.meleeDurationSeconds.set(buf.readVarInt());
      this.multiHit.set(buf.readVarInt());
      this.healCaster.set(buf.readVarInt());
      this.pullTarget.set(buf.readVarInt());
      this.launchTarget.set(buf.readVarInt());
      this.followAim.set(buf.readVarInt());
      this.guardBreak.set(buf.readVarInt());
      this.ignite.set(buf.readVarInt());
      this.shockSlow.set(buf.readVarInt());
      this.makeInvulnerable.set(buf.readVarInt());
      this.giveGuarded.set(buf.readVarInt());
      this.trapTargets.set(buf.readVarInt());
      this.meleeEnabled.set(buf.readVarInt());
      this.meleeApplyDamage.set(buf.readVarInt());
      this.meleeApplyEffect.set(buf.readVarInt());
      this.meleeLifeSteal.set(buf.readVarInt());
      this.projectileCast.set(buf.readVarInt());
      this.selfCast.set(buf.readVarInt());
      this.aoeCast.set(buf.readVarInt());
      int damageCount = buf.readVarInt();

      for (int i = 0; i < damageCount; i++) {
         this.archivedDamageIds.add(buf.readUtf(32767));
      }

      int effectCount = buf.readVarInt();

      for (int i = 0; i < effectCount; i++) {
         this.archivedEffectIds.add(buf.readUtf(32767));
      }

      int entityCount = buf.readVarInt();

      for (int i = 0; i < entityCount; i++) {
         this.recordedEntityIds.add(buf.readUtf(32767));
         this.recordedEntityKills.add(buf.readVarInt());
      }

      this.archivedDamageSize.set(this.archivedDamageIds.size());
      this.archivedEffectSize.set(this.archivedEffectIds.size());
      this.recordedEntitySize.set(this.recordedEntityIds.size());
      this.normalizeSelectors();
   }

   public static void writeExtraData(ServerPlayer player, RegistryFriendlyByteBuf buf) {
      Optional<ManasSkillInstance> optional = getArchiveInstance(player);
      buf.writeVarInt(0);
      buf.writeVarInt(1);
      buf.writeVarInt(0);
      buf.writeVarInt(0);
      buf.writeVarInt(0);
      buf.writeVarInt(0);
      buf.writeVarInt(0);
      buf.writeVarInt(0);
      buf.writeVarInt(0);
      buf.writeVarInt(0);
      buf.writeVarInt(0);
      buf.writeVarInt(0);
      buf.writeVarInt(0);
      buf.writeVarInt(8);
      buf.writeVarInt(30);
      buf.writeVarInt(12);
      buf.writeVarInt(10);
      buf.writeVarInt(1);
      buf.writeVarInt(0);
      buf.writeVarInt(0);
      buf.writeVarInt(0);
      buf.writeVarInt(4);
      buf.writeVarInt(1);
      buf.writeVarInt(12);
      buf.writeVarInt(20);
      buf.writeVarInt(0);
      buf.writeVarInt(0);
      buf.writeVarInt(0);
      buf.writeVarInt(0);
      buf.writeVarInt(1);
      buf.writeVarInt(0);
      buf.writeVarInt(0);
      buf.writeVarInt(0);
      buf.writeVarInt(0);
      buf.writeVarInt(1);
      buf.writeVarInt(0);
      buf.writeVarInt(0);
      buf.writeVarInt(1);
      buf.writeVarInt(1);
      buf.writeVarInt(0);
      buf.writeVarInt(1);
      buf.writeVarInt(0);
      buf.writeVarInt(1);
      if (!optional.isEmpty() && optional.get().getSkill() instanceof VoidArchiveSkill holder) {
         ManasSkillInstance var10 = optional.get();
         List damages = holder.getDamageRecords(var10);
         buf.writeVarInt(damages.size());

         for (VoidArchiveSkill.DamageRecord record : damages) {
            buf.writeUtf(record.id());
         }

         List<VoidArchiveSkill.EffectRecord> effects = holder.getEffectRecords(var10);
         buf.writeVarInt(effects.size());

         for (VoidArchiveSkill.EffectRecord record : effects) {
            buf.writeUtf(record.effectId().toString());
         }

         List<VoidArchiveForgeData.RecordedEntity> recorded = holder.getRecordedEntities(var10);
         buf.writeVarInt(recorded.size());

         for (VoidArchiveForgeData.RecordedEntity entity : recorded) {
            buf.writeUtf(entity.entityId().toString());
            buf.writeVarInt(entity.kills());
         }
      } else {
         buf.writeVarInt(0);
         buf.writeVarInt(0);
         buf.writeVarInt(0);
      }
   }

   private void addAllSlots() {
      this.addDataSlot(this.page);
      this.addDataSlot(this.selectedShape);
      this.addDataSlot(this.selectedCastMode);
      this.addDataSlot(this.selectedParticle);
      this.addDataSlot(this.selectedSecondaryDamage);
      this.addDataSlot(this.selectedName);
      this.addDataSlot(this.selectedRecordedEntity);
      this.addDataSlot(this.selectedBlockAction);
      this.addDataSlot(this.selectedArchivedDamage);
      this.addDataSlot(this.selectedArchivedEffect);
      this.addDataSlot(this.damageScroll);
      this.addDataSlot(this.effectScroll);
      this.addDataSlot(this.entityScroll);
      this.addDataSlot(this.range);
      this.addDataSlot(this.radiusTenths);
      this.addDataSlot(this.powerTenths);
      this.addDataSlot(this.speedTenths);
      this.addDataSlot(this.burstCount);
      this.addDataSlot(this.barrierBypassTenths);
      this.addDataSlot(this.resistanceBypassTenths);
      this.addDataSlot(this.magiculeStealPercent);
      this.addDataSlot(this.selfBuffSeconds);
      this.addDataSlot(this.summonCount);
      this.addDataSlot(this.meleePowerTenths);
      this.addDataSlot(this.meleeDurationSeconds);
      this.addDataSlot(this.multiHit);
      this.addDataSlot(this.healCaster);
      this.addDataSlot(this.pullTarget);
      this.addDataSlot(this.launchTarget);
      this.addDataSlot(this.followAim);
      this.addDataSlot(this.guardBreak);
      this.addDataSlot(this.ignite);
      this.addDataSlot(this.shockSlow);
      this.addDataSlot(this.makeInvulnerable);
      this.addDataSlot(this.giveGuarded);
      this.addDataSlot(this.trapTargets);
      this.addDataSlot(this.meleeEnabled);
      this.addDataSlot(this.meleeApplyDamage);
      this.addDataSlot(this.meleeApplyEffect);
      this.addDataSlot(this.meleeLifeSteal);
      this.addDataSlot(this.projectileCast);
      this.addDataSlot(this.selfCast);
      this.addDataSlot(this.aoeCast);
      this.addDataSlot(this.archivedDamageSize);
      this.addDataSlot(this.archivedEffectSize);
      this.addDataSlot(this.recordedEntitySize);
   }

   private void applyDefaults() {
      this.page.set(0);
      this.selectedShape.set(1);
      this.selectedCastMode.set(0);
      this.selectedParticle.set(0);
      this.selectedSecondaryDamage.set(0);
      this.selectedName.set(0);
      this.selectedRecordedEntity.set(0);
      this.selectedBlockAction.set(0);
      this.selectedArchivedDamage.set(0);
      this.selectedArchivedEffect.set(0);
      this.damageScroll.set(0);
      this.effectScroll.set(0);
      this.entityScroll.set(0);
      this.range.set(8);
      this.radiusTenths.set(30);
      this.powerTenths.set(12);
      this.speedTenths.set(10);
      this.burstCount.set(1);
      this.barrierBypassTenths.set(0);
      this.resistanceBypassTenths.set(0);
      this.magiculeStealPercent.set(0);
      this.selfBuffSeconds.set(4);
      this.summonCount.set(1);
      this.meleePowerTenths.set(12);
      this.meleeDurationSeconds.set(20);
      this.multiHit.set(0);
      this.healCaster.set(0);
      this.pullTarget.set(0);
      this.launchTarget.set(0);
      this.followAim.set(1);
      this.guardBreak.set(0);
      this.ignite.set(0);
      this.shockSlow.set(0);
      this.makeInvulnerable.set(0);
      this.giveGuarded.set(1);
      this.trapTargets.set(0);
      this.meleeEnabled.set(0);
      this.meleeApplyDamage.set(1);
      this.meleeApplyEffect.set(1);
      this.meleeLifeSteal.set(0);
      this.projectileCast.set(1);
      this.selfCast.set(0);
      this.aoeCast.set(1);
   }

   private void resetAll() {
      this.applyDefaults();
      this.normalizeSelectors();
   }

   public boolean clickMenuButton(Player player, int buttonId) {
      Optional<ManasSkillInstance> optional = getArchiveInstance(player);
      if (!optional.isEmpty() && optional.get().getSkill() instanceof VoidArchiveSkill holder && player instanceof ServerPlayer sp) {
         ManasSkillInstance var8 = optional.get();
         switch (buttonId) {
            case 10:
               this.page.set(1);
               syncArchive(sp, var8);
               return true;
            case 11:
               this.page.set(2);
               syncArchive(sp, var8);
               return true;
            case 12:
               this.page.set(3);
               syncArchive(sp, var8);
               return true;
            case 13:
               this.page.set(4);
               syncArchive(sp, var8);
               return true;
            case 14:
               this.page.set(5);
               syncArchive(sp, var8);
               return true;
            case 15:
               this.page.set(6);
               syncArchive(sp, var8);
               return true;
            case 16:
               this.page.set(7);
               syncArchive(sp, var8);
               return true;
            case 17:
               this.page.set(8);
               syncArchive(sp, var8);
               return true;
            case 18:
               this.page.set(9);
               syncArchive(sp, var8);
               return true;
            case 19:
               this.page.set(0);
               syncArchive(sp, var8);
               return true;
            case 20:
               this.resetAll();
               syncArchive(sp, var8);
               return true;
            default:
               if (buttonId >= 100 && buttonId < 140) {
                  this.selectedShape.set(buttonId - 100);
                  syncArchive(sp, var8);
                  return true;
               } else if (buttonId >= 140 && buttonId < 180) {
                  this.selectedCastMode.set(buttonId - 140);
                  syncArchive(sp, var8);
                  return true;
               } else if (buttonId >= 180 && buttonId < 240) {
                  this.selectedParticle.set(buttonId - 180);
                  syncArchive(sp, var8);
                  return true;
               } else if (buttonId >= 240 && buttonId < 300) {
                  this.selectedSecondaryDamage.set(buttonId - 240);
                  syncArchive(sp, var8);
                  return true;
               } else if (buttonId >= 300 && buttonId < 400) {
                  this.selectedBlockAction.set(buttonId - 300);
                  syncArchive(sp, var8);
                  return true;
               } else if (buttonId >= 800 && buttonId < 900) {
                  int index = buttonId - 800;
                  if (index >= 0 && index < this.archivedDamageIds.size()) {
                     this.selectedArchivedDamage.set(index);
                  }

                  syncArchive(sp, var8);
                  return true;
               } else if (buttonId >= 900 && buttonId < 1000) {
                  int index = buttonId - 900;
                  if (index >= 0 && index < this.archivedEffectIds.size()) {
                     this.selectedArchivedEffect.set(index);
                  }

                  syncArchive(sp, var8);
                  return true;
               } else if (buttonId >= 1000) {
                  int index = buttonId - 1000;
                  if (index >= 0 && index < this.recordedEntityIds.size()) {
                     this.selectedRecordedEntity.set(index);
                  }

                  syncArchive(sp, var8);
                  return true;
               } else {
                  switch (buttonId) {
                     case 400:
                        this.range.set(Math.max(1, this.range.get() - 1));
                        break;
                     case 401:
                        this.range.set(Math.min(40, this.range.get() + 1));
                        break;
                     case 402:
                        this.radiusTenths.set(Math.max(5, this.radiusTenths.get() - 5));
                        break;
                     case 403:
                        this.radiusTenths.set(Math.min(240, this.radiusTenths.get() + 5));
                        break;
                     case 404:
                        this.powerTenths.set(Math.max(5, this.powerTenths.get() - 1));
                        break;
                     case 405:
                        this.powerTenths.set(Math.min(80, this.powerTenths.get() + 1));
                        break;
                     case 406:
                        this.speedTenths.set(Math.max(2, this.speedTenths.get() - 1));
                        break;
                     case 407:
                        this.speedTenths.set(Math.min(50, this.speedTenths.get() + 1));
                        break;
                     case 408:
                        this.burstCount.set(Math.max(1, this.burstCount.get() - 1));
                        break;
                     case 409:
                        this.burstCount.set(Math.min(12, this.burstCount.get() + 1));
                        break;
                     case 410:
                        this.selfBuffSeconds.set(Math.max(0, this.selfBuffSeconds.get() - 1));
                        break;
                     case 411:
                        this.selfBuffSeconds.set(Math.min(60, this.selfBuffSeconds.get() + 1));
                        break;
                     case 412:
                        this.meleePowerTenths.set(Math.max(5, this.meleePowerTenths.get() - 1));
                        break;
                     case 413:
                        this.meleePowerTenths.set(Math.min(80, this.meleePowerTenths.get() + 1));
                        break;
                     case 414:
                        this.meleeDurationSeconds.set(Math.max(5, this.meleeDurationSeconds.get() - 1));
                        break;
                     case 415:
                        this.meleeDurationSeconds.set(Math.min(120, this.meleeDurationSeconds.get() + 1));
                        break;
                     case 416:
                        this.summonCount.set(Math.max(1, this.summonCount.get() - 1));
                        break;
                     case 417:
                        this.summonCount.set(Math.min(16, this.summonCount.get() + 1));
                        break;
                     case 500:
                        this.multiHit.set(flip(this.multiHit));
                        break;
                     case 501:
                        this.healCaster.set(flip(this.healCaster));
                        break;
                     case 502:
                        this.pullTarget.set(flip(this.pullTarget));
                        break;
                     case 503:
                        this.launchTarget.set(flip(this.launchTarget));
                        break;
                     case 504:
                        this.followAim.set(flip(this.followAim));
                        break;
                     case 505:
                        this.guardBreak.set(flip(this.guardBreak));
                        break;
                     case 506:
                        this.ignite.set(flip(this.ignite));
                        break;
                     case 507:
                        this.shockSlow.set(flip(this.shockSlow));
                        break;
                     case 508:
                        this.makeInvulnerable.set(flip(this.makeInvulnerable));
                        break;
                     case 509:
                        this.giveGuarded.set(flip(this.giveGuarded));
                        break;
                     case 510:
                        this.trapTargets.set(flip(this.trapTargets));
                        break;
                     case 511:
                        this.meleeEnabled.set(flip(this.meleeEnabled));
                        break;
                     case 512:
                        this.meleeApplyDamage.set(flip(this.meleeApplyDamage));
                        break;
                     case 513:
                        this.meleeApplyEffect.set(flip(this.meleeApplyEffect));
                        break;
                     case 514:
                        this.meleeLifeSteal.set(flip(this.meleeLifeSteal));
                        break;
                     case 515:
                        this.projectileCast.set(flip(this.projectileCast));
                        break;
                     case 516:
                        this.selfCast.set(flip(this.selfCast));
                        break;
                     case 517:
                        this.aoeCast.set(flip(this.aoeCast));
                        break;
                     case 520:
                        this.barrierBypassTenths.set(Math.max(0, this.barrierBypassTenths.get() - 1));
                        break;
                     case 521:
                        this.barrierBypassTenths.set(Math.min(10, this.barrierBypassTenths.get() + 1));
                        break;
                     case 522:
                        this.resistanceBypassTenths.set(Math.max(0, this.resistanceBypassTenths.get() - 1));
                        break;
                     case 523:
                        this.resistanceBypassTenths.set(Math.min(10, this.resistanceBypassTenths.get() + 1));
                        break;
                     case 524:
                        this.magiculeStealPercent.set(Math.max(0, this.magiculeStealPercent.get() - 5));
                        break;
                     case 525:
                        this.magiculeStealPercent.set(Math.min(100, this.magiculeStealPercent.get() + 5));
                        break;
                     case 600:
                        this.selectedName.set((this.selectedName.get() + 1) % this.getNamePool().length);
                        break;
                     case 601: {
                        boolean ok = holder.createMoveFromSelection(
                           var8,
                           sp,
                           this.getCurrentMoveName(),
                           this.getShape(),
                           this.getParticle(),
                           null,
                           this.getSecondaryDamageId(),
                           this.range.get(),
                           this.getRadius(),
                           this.getPower(),
                           this.getSpeed(),
                           this.burstCount.get(),
                           this.getBarrierBypass(),
                           this.getResistanceBypass(),
                           this.getMagiculeSteal(),
                           this.getSelfBuffDurationTicks(),
                           this.isMultiHit(),
                           this.isHealCaster(),
                           this.isPullTarget(),
                           this.isLaunchTarget(),
                           this.isFollowAim(),
                           this.isGuardBreak(),
                           this.isIgnite(),
                           this.isShockSlow(),
                           this.isMakeInvulnerable(),
                           this.isGiveGuarded(),
                           this.isTrapTargets(),
                           this.getBlockAction(),
                           this.getCastMode(),
                           this.isProjectileCast(),
                           this.isSelfCast(),
                           this.isAoeCast()
                        );
                        syncArchive(sp, var8);
                        return ok;
                     }
                     case 602: {
                        boolean ok = holder.createSummonMove(
                           var8, sp, this.getCurrentMoveName(), this.selectedRecordedEntity.get(), false, this.summonCount.get(), this.getCastMode()
                        );
                        syncArchive(sp, var8);
                        return ok;
                     }
                     case 603: {
                        boolean ok = holder.createSummonMove(
                           var8, sp, this.getCurrentMoveName(), this.selectedRecordedEntity.get(), true, this.summonCount.get(), this.getCastMode()
                        );
                        syncArchive(sp, var8);
                        return ok;
                     }
                     case 604: {
                        boolean ok = holder.createMeleeInfusion(
                           var8,
                           sp,
                           this.getCurrentMoveName(),
                           this.getMeleePower(),
                           this.meleeDurationSeconds.get() * 20,
                           this.isMeleeApplyDamage(),
                           this.isMeleeApplyEffect(),
                           this.isMeleeLifeSteal()
                        );
                        syncArchive(sp, var8);
                        return ok;
                     }
                     case 700:
                        this.damageScroll.set(Math.max(0, this.damageScroll.get() - 1));
                        break;
                     case 701:
                        this.damageScroll.set(Math.min(Math.max(0, this.archivedDamageIds.size() - 6), this.damageScroll.get() + 1));
                        break;
                     case 702:
                        this.effectScroll.set(Math.max(0, this.effectScroll.get() - 1));
                        break;
                     case 703:
                        this.effectScroll.set(Math.min(Math.max(0, this.archivedEffectIds.size() - 6), this.effectScroll.get() + 1));
                        break;
                     case 704:
                        this.entityScroll.set(Math.max(0, this.entityScroll.get() - 1));
                        break;
                     case 705:
                        this.entityScroll.set(Math.min(Math.max(0, this.recordedEntityIds.size() - 6), this.entityScroll.get() + 1));
                  }

                  syncArchive(sp, var8);
                  return true;
               }
         }
      } else {
         return false;
      }
   }

   private static int flip(DataSlot slot) {
      return slot.get() == 0 ? 1 : 0;
   }

   private void refreshArchiveLists() {
      Optional<ManasSkillInstance> optional = getArchiveInstance(this.player);
      if (!optional.isEmpty() && optional.get().getSkill() instanceof VoidArchiveSkill holder) {
         ManasSkillInstance var6 = optional.get();
         this.archivedDamageIds.clear();

         for (VoidArchiveSkill.DamageRecord record : holder.getDamageRecords(var6)) {
            this.archivedDamageIds.add(record.id());
         }

         this.archivedEffectIds.clear();

         for (VoidArchiveSkill.EffectRecord record : holder.getEffectRecords(var6)) {
            this.archivedEffectIds.add(record.effectId().toString());
         }

         this.recordedEntityIds.clear();
         this.recordedEntityKills.clear();

         for (VoidArchiveForgeData.RecordedEntity data : holder.getRecordedEntities(var6)) {
            this.recordedEntityIds.add(data.entityId().toString());
            this.recordedEntityKills.add(data.kills());
         }

         this.archivedDamageSize.set(this.archivedDamageIds.size());
         this.archivedEffectSize.set(this.archivedEffectIds.size());
         this.recordedEntitySize.set(this.recordedEntityIds.size());
         this.normalizeSelectors();
      } else {
         this.archivedDamageIds.clear();
         this.archivedEffectIds.clear();
         this.recordedEntityIds.clear();
         this.recordedEntityKills.clear();
         this.archivedDamageSize.set(0);
         this.archivedEffectSize.set(0);
         this.recordedEntitySize.set(0);
      }
   }

   private void normalizeSelectors() {
      if (this.selectedArchivedDamage.get() >= this.archivedDamageIds.size()) {
         this.selectedArchivedDamage.set(Math.max(0, this.archivedDamageIds.size() - 1));
      }

      if (this.selectedArchivedEffect.get() >= this.archivedEffectIds.size()) {
         this.selectedArchivedEffect.set(Math.max(0, this.archivedEffectIds.size() - 1));
      }

      if (this.selectedRecordedEntity.get() >= this.recordedEntityIds.size()) {
         this.selectedRecordedEntity.set(Math.max(0, this.recordedEntityIds.size() - 1));
      }

      this.damageScroll.set(Math.min(this.damageScroll.get(), Math.max(0, this.archivedDamageIds.size() - 6)));
      this.effectScroll.set(Math.min(this.effectScroll.get(), Math.max(0, this.archivedEffectIds.size() - 6)));
      this.entityScroll.set(Math.min(this.entityScroll.get(), Math.max(0, this.recordedEntityIds.size() - 6)));
   }

   public String[] getNamePool() {
      return new String[]{
         "Void Strike",
         "Abyss Pulse",
         "Ruin Fang",
         "Eventide Beam",
         "Soul Breaker",
         "Null Bloom",
         "Archive Burst",
         "Night Crown",
         "Void Summon",
         "Mourning Star",
         "Gravity Bloom",
         "Rift Spear",
         "Starfall Cage",
         "Dark Current",
         "Lightning Grave",
         "Cathedral of Null",
         "Astral Ring",
         "Midnight Pillar",
         "Abyssal Bloom",
         "Velvet Ruin",
         "Sepulcher Rain",
         "Abyss Walker",
         "End World",
         "Soul Tyrant",
         "Void Edict",
         "Black Genesis"
      };
   }

   public String[] getSecondaryDamagePool() {
      return new String[]{
         "",
         "tensura:sever",
         "tensura:space",
         "tensura:dark",
         "tensura:light",
         "minecraft:fire",
         "tensura:water",
         "tensura:wind",
         "tensura:earth",
         "tensura:lightning",
         "tensura:drain",
         "tensura:magic",
         "minecraft:physical"
      };
   }

   public String getCurrentMoveName() {
      String[] pool = this.getNamePool();
      return pool[this.selectedName.get() % pool.length];
   }

   public String getSecondaryDamageId() {
      String[] pool = this.getSecondaryDamagePool();
      return pool[Math.max(0, Math.min(this.selectedSecondaryDamage.get(), pool.length - 1))];
   }

   public int getPage() {
      return this.page.get();
   }

   public int getDamageScroll() {
      return this.damageScroll.get();
   }

   public int getEffectScroll() {
      return this.effectScroll.get();
   }

   public int getEntityScroll() {
      return this.entityScroll.get();
   }

   public int getSelectorRows() {
      return 6;
   }

   public int getArchivedDamageCount() {
      return this.archivedDamageSize.get();
   }

   public int getArchivedEffectCount() {
      return this.archivedEffectSize.get();
   }

   public int getRecordedEntityCount() {
      return this.recordedEntitySize.get();
   }

   public int getSelectedArchivedDamageIndex() {
      return this.selectedArchivedDamage.get();
   }

   public int getSelectedArchivedEffectIndex() {
      return this.selectedArchivedEffect.get();
   }

   public int getSelectedRecordedEntityIndex() {
      return this.selectedRecordedEntity.get();
   }

   public Component getArchivedDamageName(int index) {
      return (Component)(index >= 0 && index < this.archivedDamageIds.size()
         ? VoidArchiveSkill.getDamageDisplay(this.archivedDamageIds.get(index))
         : Component.literal("None"));
   }

   public Component getArchivedEffectName(int index) {
      if (index >= 0 && index < this.archivedEffectIds.size()) {
         try {
            return VoidArchiveSkill.getEffectDisplay(new VoidArchiveSkill.EffectRecord(ResourceLocation.parse(this.archivedEffectIds.get(index)), 0, 0, 0));
         } catch (Exception ignored) {
            return Component.literal(this.archivedEffectIds.get(index));
         }
      } else {
         return Component.literal("None");
      }
   }

   public Component getRecordedEntityName(int index) {
      if (index >= 0 && index < this.recordedEntityIds.size()) {
         ResourceLocation id = ResourceLocation.parse(this.recordedEntityIds.get(index));
         String path = id.getPath().replace('_', ' ');
         return Component.literal(Character.toUpperCase(path.charAt(0)) + path.substring(1));
      } else {
         return Component.literal("None");
      }
   }

   public int getRecordedEntityKills(int index) {
      return index >= 0 && index < this.recordedEntityKills.size() ? this.recordedEntityKills.get(index) : 0;
   }

   public Component getSelectedDamageName() {
      int idx = Math.max(0, Math.min(this.selectedArchivedDamage.get(), this.archivedDamageIds.size() - 1));
      return this.getArchivedDamageName(idx);
   }

   public Component getSelectedEffectName() {
      int idx = Math.max(0, Math.min(this.selectedArchivedEffect.get(), this.archivedEffectIds.size() - 1));
      return this.getArchivedEffectName(idx);
   }

   public Component getSelectedRecordedEntityName() {
      int idx = Math.max(0, Math.min(this.selectedRecordedEntity.get(), this.recordedEntityIds.size() - 1));
      return this.getRecordedEntityName(idx);
   }

   public int getRangeValue() {
      return this.range.get();
   }

   public float getRadius() {
      return this.radiusTenths.get() / 10.0F;
   }

   public float getPower() {
      return this.powerTenths.get() / 10.0F;
   }

   public float getSpeed() {
      return this.speedTenths.get() / 10.0F;
   }

   public int getBurstCount() {
      return this.burstCount.get();
   }

   public float getBarrierBypass() {
      return this.barrierBypassTenths.get() / 10.0F;
   }

   public float getResistanceBypass() {
      return this.resistanceBypassTenths.get() / 10.0F;
   }

   public float getMagiculeSteal() {
      return this.magiculeStealPercent.get() / 100.0F;
   }

   public int getSelfBuffDurationTicks() {
      return this.selfBuffSeconds.get() * 20;
   }

   public int getSummonCount() {
      return this.summonCount.get();
   }

   public float getMeleePower() {
      return this.meleePowerTenths.get() / 10.0F;
   }

   public int getMeleeDurationSeconds() {
      return this.meleeDurationSeconds.get();
   }

   public boolean isMultiHit() {
      return this.multiHit.get() != 0;
   }

   public boolean isHealCaster() {
      return this.healCaster.get() != 0;
   }

   public boolean isPullTarget() {
      return this.pullTarget.get() != 0;
   }

   public boolean isLaunchTarget() {
      return this.launchTarget.get() != 0;
   }

   public boolean isFollowAim() {
      return this.followAim.get() != 0;
   }

   public boolean isGuardBreak() {
      return this.guardBreak.get() != 0;
   }

   public boolean isIgnite() {
      return this.ignite.get() != 0;
   }

   public boolean isShockSlow() {
      return this.shockSlow.get() != 0;
   }

   public boolean isMakeInvulnerable() {
      return this.makeInvulnerable.get() != 0;
   }

   public boolean isGiveGuarded() {
      return this.giveGuarded.get() != 0;
   }

   public boolean isTrapTargets() {
      return this.trapTargets.get() != 0;
   }

   public boolean isMeleeEnabled() {
      return this.meleeEnabled.get() != 0;
   }

   public boolean isMeleeApplyDamage() {
      return this.meleeApplyDamage.get() != 0;
   }

   public boolean isMeleeApplyEffect() {
      return this.meleeApplyEffect.get() != 0;
   }

   public boolean isMeleeLifeSteal() {
      return this.meleeLifeSteal.get() != 0;
   }

   public boolean isProjectileCast() {
      return this.projectileCast.get() != 0;
   }

   public boolean isSelfCast() {
      return this.selfCast.get() != 0;
   }

   public boolean isAoeCast() {
      return this.aoeCast.get() != 0;
   }

   public VoidArchiveForgeData.MoveShape getShape() {
      VoidArchiveForgeData.MoveShape[] values = VoidArchiveForgeData.MoveShape.values();
      return values[Math.max(0, Math.min(this.selectedShape.get(), values.length - 1))];
   }

   public VoidArchiveForgeData.MoveParticleStyle getParticle() {
      VoidArchiveForgeData.MoveParticleStyle[] values = VoidArchiveForgeData.MoveParticleStyle.values();
      return values[Math.max(0, Math.min(this.selectedParticle.get(), values.length - 1))];
   }

   public VoidArchiveForgeData.MoveBlockAction getBlockAction() {
      VoidArchiveForgeData.MoveBlockAction[] values = VoidArchiveForgeData.MoveBlockAction.values();
      return values[Math.max(0, Math.min(this.selectedBlockAction.get(), values.length - 1))];
   }

   public VoidArchiveForgeData.MoveCastMode getCastMode() {
      VoidArchiveForgeData.MoveCastMode[] values = VoidArchiveForgeData.MoveCastMode.values();
      return values[Math.max(0, Math.min(this.selectedCastMode.get(), values.length - 1))];
   }

   private static Optional<ManasSkillInstance> getArchiveInstance(Player player) {
      if (player == null) {
         return Optional.empty();
      }

      Skills skills = SkillAPI.getSkillsFrom(player);
      return skills == null ? Optional.empty() : skills.getSkill(VoidArchiveSkill.SKILL_ID);
   }

   private static void syncArchive(ServerPlayer sp, ManasSkillInstance instance) {
      instance.markDirty();
      Skills skills = SkillAPI.getSkillsFrom(sp);
      if (skills != null) {
         skills.markDirty();
      }

      sp.containerMenu.broadcastChanges();
   }

   public void broadcastChanges() {
      super.broadcastChanges();
      if (this.isServer) {
         this.refreshArchiveLists();
      }
   }

   public ItemStack quickMoveStack(Player player, int index) {
      return ItemStack.EMPTY;
   }

   public boolean stillValid(Player player) {
      return true;
   }
}
