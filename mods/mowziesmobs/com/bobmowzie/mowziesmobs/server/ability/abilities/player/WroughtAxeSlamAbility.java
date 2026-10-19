package com.bobmowzie.mowziesmobs.server.ability.abilities.player;

import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityAxeAttack;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.animation.RawAnimation;

public class WroughtAxeSlamAbility extends PlayerAbility {
   private EntityAxeAttack axeAttack;
   private static final RawAnimation AXE_SWING_VERTICAL_ANIM = RawAnimation.begin().thenPlay("axe_swing_vertical");

   public WroughtAxeSlamAbility(AbilityType<Player, WroughtAxeSlamAbility> abilityType, Player user) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, EntityAxeAttack.SWING_DURATION_VER / 2 - 2),
            new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, EntityAxeAttack.SWING_DURATION_VER / 2 + 2)
         }
      );
   }

   @Override
   public void start() {
      super.start();
      if (!this.getUser().level().isClientSide()) {
         EntityAxeAttack axeAttack = new EntityAxeAttack(
            (EntityType<? extends EntityAxeAttack>)EntityHandler.AXE_ATTACK.get(), this.getUser().level(), this.getUser(), true
         );
         axeAttack.absMoveTo(this.getUser().getX(), this.getUser().getY(), this.getUser().getZ(), this.getUser().getYRot(), this.getUser().getXRot());
         this.getUser().level().addFreshEntity(axeAttack);
         this.axeAttack = axeAttack;
      } else {
         this.playAnimation(AXE_SWING_VERTICAL_ANIM);
         this.heldItemMainHandVisualOverride = this.getUser().getMainHandItem();
      }
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
      if (this.getTicksInUse() == EntityAxeAttack.SWING_DURATION_VER && this.getUser() instanceof Player) {
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
