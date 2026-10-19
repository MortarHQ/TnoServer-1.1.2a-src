package com.bobmowzie.mowziesmobs.server.ability.abilities.player;

import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.animation.Animation.LoopType;

public class SimplePlayerAnimationAbility extends PlayerAbility {
   private String animationName;
   private boolean separateLeftAndRight3rdPerson;
   private boolean separateLeftAndRight1stPerson;
   private boolean lockHeldItemActiveHand;

   public SimplePlayerAnimationAbility(AbilityType<Player, SimplePlayerAnimationAbility> abilityType, Player user, String animationName, int duration) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, duration)
         }
      );
      this.animationName = animationName;
   }

   public SimplePlayerAnimationAbility(
      AbilityType<Player, SimplePlayerAnimationAbility> abilityType,
      Player user,
      String animationName,
      int duration,
      boolean separateLeftAndRight1stPerson,
      boolean separateLeftAndRight3rdPerson,
      boolean lockHeldItemMainHand
   ) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, duration)
         }
      );
      this.animationName = animationName;
      this.separateLeftAndRight3rdPerson = separateLeftAndRight3rdPerson;
      this.separateLeftAndRight1stPerson = separateLeftAndRight1stPerson;
      this.lockHeldItemActiveHand = lockHeldItemMainHand;
   }

   @Override
   public void start() {
      super.start();
      boolean usingSide = this.getActiveHand() == InteractionHand.MAIN_HAND;
      boolean mainSide = this.getUser().getMainArm() == HumanoidArm.RIGHT;
      this.playAnimation(this.animationName, LoopType.DEFAULT, this.separateLeftAndRight1stPerson, this.separateLeftAndRight3rdPerson);
      if (this.lockHeldItemActiveHand) {
         if (usingSide) {
            if (this.getUser().isUsingItem()) {
               this.heldItemMainHandVisualOverride = this.getUser().getUseItem();
            } else {
               this.heldItemMainHandVisualOverride = this.getUser().getMainHandItem();
            }
         } else if (this.getUser().isUsingItem()) {
            this.heldItemOffHandVisualOverride = this.getUser().getUseItem();
         } else {
            this.heldItemOffHandVisualOverride = this.getUser().getOffhandItem();
         }
      }
   }
}
