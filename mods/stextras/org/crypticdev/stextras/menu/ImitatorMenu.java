package org.crypticdev.stextras.menu;

import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.stextras.ability.skill.unique.ImitatorSkill;
import org.crypticdev.stextras.config.ability.skill.STExtrasUniqueSkillConfig;
import org.crypticdev.stextras.registary.menu.STExtrasMenuTypes;

public class ImitatorMenu extends AbstractContainerMenu {
   private final List<ResourceLocation> entities;
   public static final STExtrasUniqueSkillConfig.Imitator CONFIG = ((STExtrasUniqueSkillConfig)ConfigRegistry.getConfig(STExtrasUniqueSkillConfig.class)).Imitator;

   public ImitatorMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
      this(containerId, inventory, buf.readList(FriendlyByteBuf::readResourceLocation));
   }

   public ImitatorMenu(int containerId, Inventory inventory, List<ResourceLocation> entities) {
      super((MenuType)STExtrasMenuTypes.IMITATOR_SKILL.get(), containerId);
      this.entities = entities;
   }

   public boolean stillValid(Player player) {
      return true;
   }

   public ItemStack quickMoveStack(Player player, int index) {
      return ItemStack.EMPTY;
   }

   public List<ResourceLocation> getEntities() {
      return this.entities;
   }

   public boolean clickMenuButton(Player player, int id) {
      if (id >= 0 && id < this.entities.size()) {
         if (player.level().isClientSide()) {
            return true;
         }

         Skills storage = SkillAPI.getSkillsFrom(player);
         Optional<ManasSkillInstance> imitatorInstanceOpt = storage.getLearnedSkills()
            .stream()
            .filter(instance -> instance.getSkill() instanceof ImitatorSkill)
            .findFirst();
         if (imitatorInstanceOpt.isEmpty()) {
            return false;
         }

         ManasSkillInstance imitatorInstance = imitatorInstanceOpt.get();
         if (imitatorInstance.onCoolDown(0)) {
            return false;
         }

         ResourceLocation selectedEntity = this.entities.get(id);
         List<ResourceLocation> skillIds = ImitatorSkill.getSkillsForEntity(selectedEntity);
         boolean learntAnything = false;

         for (ResourceLocation skillId : skillIds) {
            if (!storage.getSkill(skillId).isPresent()) {
               ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(skillId);
               if (skill != null) {
                  TensuraSkillInstance newInstance = new TensuraSkillInstance(skill);
                  newInstance.getOrCreateTag().putString("Source", "ImitatorSkill");
                  if (SkillHelper.learnSkill(player, newInstance, CONFIG.cooldown)) {
                     learntAnything = true;
                  }
               }
            }
         }

         if (learntAnything) {
            player.playNotifySound((SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            imitatorInstance.setCoolDown(CONFIG.cooldown, 0);
            storage.markDirty();
            player.closeContainer();
            return true;
         } else {
            return false;
         }
      } else {
         return super.clickMenuButton(player, id);
      }
   }
}
