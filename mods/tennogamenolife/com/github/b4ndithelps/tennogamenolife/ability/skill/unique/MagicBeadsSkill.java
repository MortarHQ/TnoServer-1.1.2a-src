package com.github.b4ndithelps.tennogamenolife.ability.skill.unique;

import com.github.b4ndithelps.tennogamenolife.registry.item.Tenreincarnationitems;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class MagicBeadsSkill extends Skill {
   private final double skillCastCost = 20000.0;
   private final double learnCost = 3.0;
   private final double epUnlockCost = 400000.0;

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tennogamenolife", "textures/skill/unique/magic_beads.png");
   }

   public MagicBeadsSkill() {
      super(SkillType.UNIQUE);
   }

   public boolean checkAcquiringRequirement(Player entity, double curEP) {
      return curEP >= 400000.0;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return 20000.0;
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 3.0;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!this.isOutOfEnergy(entity, instance, mode, 1.0F)) {
         if (entity instanceof Player player) {
            ItemStack itemStack = new ItemStack((ItemLike)Tenreincarnationitems.MAG.get());
            player.addItem(itemStack);
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
            instance.setCoolDown(600, mode);
         }
      }
   }
}
