package com.mortal_cultivation.ability.skill.unique;

import com.mortal_cultivation.ability.skill.base.LegacySkill;
import com.mortal_cultivation.gui.menu.TalismanMakingMenu;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

public class TalismanMasterSkill extends LegacySkill {
   public TalismanMasterSkill() {
      super(SkillType.EXTRA);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/skill/unique/talisman_master.png");
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return false;
   }

   @Override
   public void onPressed(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         this.openTalismanMakingGUI(player);
         player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0F, 1.0F);
      }
   }

   private void openTalismanMakingGUI(ServerPlayer player) {
      MenuProvider provider = new MenuProvider() {
         @NotNull
         public Component getDisplayName() {
            return Component.translatable("gui.mortal_cultivation.talisman_making");
         }

         public AbstractContainerMenu createMenu(int id, Inventory inventory, Player playerIn) {
            return new TalismanMakingMenu(id, inventory, playerIn);
         }
      };
      player.openMenu(provider);
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return 0.0;
   }
}
