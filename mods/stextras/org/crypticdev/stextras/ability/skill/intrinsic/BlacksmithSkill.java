package org.crypticdev.stextras.ability.skill.intrinsic;

import dev.architectury.registry.menu.MenuRegistry;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.block.SmithingBenchBlock;
import io.github.manasmods.tensura.menu.SmithingBenchMenu;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import org.crypticdev.stextras.config.ability.skill.STExtrasIntrinsicSkillConfig;
import org.jetbrains.annotations.Nullable;

public class BlacksmithSkill extends Skill {
   public static final STExtrasIntrinsicSkillConfig.Blacksmith CONFIG = ((STExtrasIntrinsicSkillConfig)ConfigRegistry.getConfig(
         STExtrasIntrinsicSkillConfig.class
      ))
      .Blacksmith;

   public BlacksmithSkill() {
      super(SkillType.INTRINSIC);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("stextras", "textures/skill/intrinsic/blacksmith_skill.png");
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isToggled();
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (EnergyHelper.isOutOfEnergy(entity, instance, 0)) {
         if (entity instanceof Player player) {
            player.sendSystemMessage(
               Component.translatable("tensura.skill.lack_magicule.toggled_off", new Object[]{instance.getSkill().getName()})
                  .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))
            );
         }

         instance.setToggled(false);
      } else {
         entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 240, 1, false, false, false));
      }
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      this.onTick(instance, entity);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      MobEffectInstance effect = entity.getEffect(MobEffects.DIG_SPEED);
      if (effect != null && effect.getAmplifier() <= 1) {
         entity.removeEffect(MobEffects.DIG_SPEED);
      }
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isMastered(entity);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         MutableComponent title = SmithingBenchBlock.TITLE;
         MenuRegistry.openExtendedMenu(
            player, new SimpleMenuProvider((id, inv, p) -> new SmithingBenchMenu(id, inv, ContainerLevelAccess.NULL), title), buf -> {}
         );
         player.swing(InteractionHand.MAIN_HAND);
         instance.addMasteryPoint(entity);
         if (!instance.isMastered(entity)) {
            instance.setCoolDown(CONFIG.cooldown, 0);
         } else {
            instance.setCoolDown(CONFIG.cooldownMastered, 0);
         }
      }
   }
}
