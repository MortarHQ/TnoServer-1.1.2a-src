package com.bobmowzie.mowziesmobs.server.ability.abilities.player.geomancy;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoModel;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;

public class BoulderRollAbility extends PlayerAbility {
   private static int START_UP = 15;
   float spinAmount = 0.0F;
   private RawAnimation ROLL_ANIM = RawAnimation.begin().thenLoop("boulder_roll_loop_still");

   public BoulderRollAbility(AbilityType<Player, ? extends Ability> abilityType, Player user) {
      super(abilityType, user, new AbilitySection[]{new AbilitySection.AbilitySectionInfinite(AbilitySection.AbilitySectionType.ACTIVE)});
   }

   @Override
   protected void beginSection(AbilitySection section) {
      super.beginSection(section);
   }

   @Override
   public <E extends GeoEntity> PlayState animationPredicate(AnimationState<E> e, GeckoPlayer.Perspective perspective) {
      e.getController().transitionLength(0);
      if (perspective == GeckoPlayer.Perspective.THIRD_PERSON) {
         e.getController().setAnimation(this.ROLL_ANIM);
      }

      return PlayState.CONTINUE;
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP) {
         this.getUser().setDeltaMovement(this.getUser().getViewVector(1.0F).normalize().multiply(0.3, 0.0, 0.3));
      }

      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
         this.getUser().setDeltaMovement(this.getUser().getViewVector(1.0F).normalize().multiply(1.0, 0.0, 1.0));
      }
   }

   @Override
   public boolean tryAbility() {
      return super.tryAbility();
   }

   @Override
   public void onRightClickEmpty(RightClickEmpty event) {
      super.onRightClickEmpty(event);
      AbilityHandler.INSTANCE.sendPlayerTryAbilityMessage(event.getEntity(), AbilityHandler.BOULDER_ROLL_ABILITY);
   }

   @Override
   public void onRightMouseUp(Player player) {
      super.onRightMouseUp(player);
      if (this.isUsing()) {
         this.nextSection();
      }
   }

   @Override
   public boolean canUse() {
      return this.getUser() != null && !this.getUser().getInventory().getSelected().isEmpty()
         ? false
         : this.getUser().hasEffect(EffectHandler.GEOMANCY) && this.getUser().isSprinting() && super.canUse();
   }

   @Override
   public void codeAnimations(MowzieGeoModel<? extends GeoEntity> model, float partialTick) {
      super.codeAnimations(model, partialTick);
      float spinSpeed = 0.35F;
      this.spinAmount += partialTick * spinSpeed;
      MowzieGeoBone centerOfMass = model.getMowzieBone("CenterOfMass");
      centerOfMass.addRotX(-this.spinAmount);
   }
}
