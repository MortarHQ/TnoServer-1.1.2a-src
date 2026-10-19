package com.github.wal_bos.moreskills.ability.skill.unique;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.GodlyScienceSmitheryHandler;
import com.github.wal_bos.moreskills.menu.GodlyWorkMenu;
import com.github.wal_bos.moreskills.util.SkillObtainmentHelper;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.subclass.IResearcherEnchanter;
import io.github.manasmods.tensura.ability.subclass.ISpatialStorage;
import io.github.manasmods.tensura.menu.container.SpatialStorageContainer;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import java.util.List;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.NotNull;

public class GodlySmitherySkill extends Skill implements ISpatialStorage, IResearcherEnchanter {
   public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "godly_smithery");

   public GodlySmitherySkill() {
      super(SkillType.UNIQUE);
   }

   private static MoreSkillsConfigs.GodlySmitheryConfig cfg() {
      return MoreSkillsConfigs.GRAND.godlySmithery;
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/unique/godly_smithery.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return (Double)cfg().acquirementMagiculeCost.get();
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return (Integer)cfg().acquirementMastery.get();
   }

   public int getMaxMastery() {
      return (Integer)cfg().maxMastery.get();
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      if (!SkillObtainmentHelper.canAcquire(
         entity, (Double)cfg().acquirementMagiculeCost.get(), (String)cfg().requiredSkillId.get(), (Boolean)cfg().requiredSkillMustBeMastered.get()
      )) {
         return false;
      } else if (newEP < (Double)cfg().requiredEp.get()) {
         return false;
      } else {
         return !GodlyScienceSmitheryHandler.hasKilledEnderDragon(entity)
            ? false
            : entity.getMainHandItem().is(Items.ELYTRA) || entity.getOffhandItem().is(Items.ELYTRA);
      }
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return mode == 1 ? "godly_smithery.heavenly_forge" : "godly_smithery.divine_construction";
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return mode == 1 ? (Double)cfg().heavenlyForgeCost.get() : (Double)cfg().divineConstructionCost.get();
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      if (instance.getMastery() >= 0.0 && !instance.isTemporarySkill()) {
         GodlyScienceSmitheryHandler.applySoulBuff(entity);
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      GodlyScienceSmitheryHandler.removeSoulBuff(entity);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         if (mode == 0) {
            GodlyWorkMenu.open(player, GodlyWorkMenu.Kind.DIVINE_CONSTRUCTION);
         } else if (!instance.onCoolDown(mode) && !this.isOutOfEnergy(entity, instance, mode, 1.0F)) {
            IResearcherEnchanter.addSelectedEnchantments(entity, ItemEnchantments.EMPTY, this, true);
            player.closeContainer();
            this.openEnchantingMenu(player, instance);
            player.playNotifySound((SoundEvent)TensuraSoundEvents.SPATIAL_STORAGE.get(), SoundSource.PLAYERS, 0.75F, 1.0F);
            instance.addMasteryPoint(entity);
            instance.setCoolDown((Integer)cfg().heavenlyForgeCooldownTicks.get(), mode);
         }
      }
   }

   @NotNull
   public SpatialStorageContainer getSpatialStorage(ManasSkillInstance instance, Provider provider) {
      SpatialStorageContainer container = new SpatialStorageContainer(
         (Integer)cfg().heavenlyForgeStorageSlots.get(), (Integer)cfg().heavenlyForgeStorageStack.get()
      );
      container.fromTag(instance.getOrCreateTag().getList("SpatialStorage", 10), provider);
      return container;
   }

   public int getMaximumBonusLevel() {
      return (Integer)cfg().heavenlyForgeMaximumBonusLevel.get();
   }

   public List<String> getBlacklistEnchantments() {
      return List.of();
   }

   public List<String> getBlackListBonusLevelEnchantments() {
      return List.of();
   }

   public float getCurseChancePerEngraving() {
      return 0.0F;
   }

   public boolean isAllowedToCopyEnchantments(LivingEntity entity, ItemStack stack) {
      return true;
   }
}
