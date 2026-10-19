package com.bobmowzie.mowziesmobs.server.ability.abilities.player;

import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityAxeAttack;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.animation.Animation.LoopType;

public class WroughtAxeSwingAbility extends PlayerAbility {
   private EntityAxeAttack axeAttack;

   public WroughtAxeSwingAbility(AbilityType<Player, WroughtAxeSwingAbility> abilityType, Player user) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, EntityAxeAttack.SWING_DURATION_HOR / 2 - 2),
            new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, EntityAxeAttack.SWING_DURATION_HOR / 2 + 2 + 7)
         }
      );
   }

   @Override
   public void start() {
      super.start();
      if (!this.getUser().level().isClientSide()) {
         EntityAxeAttack axeAttack = new EntityAxeAttack(
            (EntityType<? extends EntityAxeAttack>)EntityHandler.AXE_ATTACK.get(), this.getUser().level(), this.getUser(), false
         );
         axeAttack.absMoveTo(this.getUser().getX(), this.getUser().getY(), this.getUser().getZ(), this.getUser().getYRot(), this.getUser().getXRot());
         this.getUser().level().addFreshEntity(axeAttack);
         this.axeAttack = axeAttack;
      } else {
         boolean handSide = this.getUser().getMainArm() == HumanoidArm.RIGHT;
         this.playAnimation("axe_swing_start_" + (handSide ? "right" : "left"), GeckoPlayer.Perspective.THIRD_PERSON, LoopType.PLAY_ONCE);
         this.playAnimation("axe_swing_start", GeckoPlayer.Perspective.FIRST_PERSON, LoopType.PLAY_ONCE);
         this.heldItemMainHandVisualOverride = this.getUser().getMainHandItem();
      }
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
      if (this.getTicksInUse() == EntityAxeAttack.SWING_DURATION_HOR && this.getUser() instanceof Player) {
         Player player = this.getUser();
         player.resetAttackStrengthTicker();
      }
   }

   @Override
   public void end() {
      super.end();
      if (this.axeAttack != null) {
         this.axeAttack.discard();
      }
   }

   @Override
   public boolean preventsAttacking() {
      return false;
   }
}
