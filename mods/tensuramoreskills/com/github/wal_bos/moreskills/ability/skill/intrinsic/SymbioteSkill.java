package com.github.wal_bos.moreskills.ability.skill.intrinsic;

import com.github.wal_bos.moreskills.handler.ParasiteData;
import com.github.wal_bos.moreskills.handler.ParasiteHandler;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class SymbioteSkill extends Skill {
   public SymbioteSkill() {
      super(SkillType.INTRINSIC);
      ParasiteHandler.init();
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/intrinsic/symbiote.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 0.0;
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return true;
   }

   public boolean canInteractSkill(ManasSkillInstance instance, LivingEntity entity) {
      return ParasiteData.isHost(entity) || super.canInteractSkill(instance, entity);
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         if (!ParasiteData.isHost(entity)) {
            this.fail(entity, "tensuramoreskills.message.symbiote.no_parasite");
         } else if (!ParasiteHandler.symbiosisUse(entity)) {
            this.fail(entity, "tensuramoreskills.message.symbiote.offline");
         } else {
            instance.addMasteryPoint(entity);
            entity.level()
               .playSound(
                  null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 0.8F, 0.8F
               );
         }
      }
   }

   private void fail(LivingEntity entity, String key) {
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.translatable(key).withStyle(ChatFormatting.RED), true);
      }

      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
   }
}
