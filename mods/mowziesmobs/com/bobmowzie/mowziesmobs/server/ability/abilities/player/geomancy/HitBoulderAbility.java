package com.bobmowzie.mowziesmobs.server.ability.abilities.player.geomancy;

import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.animation.Animation.LoopType;

public class HitBoulderAbility extends PlayerAbility {
   public HitBoulderAbility(AbilityType<Player, HitBoulderAbility> abilityType, Player user) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 10)
         }
      );
   }

   @Override
   public InteractionHand getActiveHand() {
      if (this.getUser().getMainHandItem().is((Item)ItemHandler.EARTHREND_GAUNTLET.get())) {
         return InteractionHand.MAIN_HAND;
      } else {
         return this.getUser().getOffhandItem().is((Item)ItemHandler.EARTHREND_GAUNTLET.get()) ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
      }
   }

   @Override
   public void start() {
      super.start();
      boolean usingMainHand = this.getActiveHand() == InteractionHand.MAIN_HAND;
      this.playAnimation("hit_boulder", LoopType.DEFAULT, true, false);
      if (usingMainHand) {
         this.heldItemMainHandVisualOverride = this.getUser().getItemInHand(InteractionHand.MAIN_HAND);
      } else {
         this.heldItemOffHandVisualOverride = this.getUser().getItemInHand(InteractionHand.OFF_HAND);
      }
   }
}
