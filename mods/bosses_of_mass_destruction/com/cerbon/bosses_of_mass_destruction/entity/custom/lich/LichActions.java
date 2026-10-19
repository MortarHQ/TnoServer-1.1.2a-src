package com.cerbon.bosses_of_mass_destruction.entity.custom.lich;

import com.cerbon.bosses_of_mass_destruction.entity.ai.action.CooldownAction;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionStop;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.ai.goals.ActionGoal;
import java.util.function.Supplier;

public class LichActions {
   private final LichEntity entity;
   private final IActionWithCooldown attackAction;
   private final Supplier<Boolean> cancelAttackAction;
   public static final byte stopAttackAnimation = 4;
   public static final byte cometAttack = 5;
   public static final byte volleyAttack = 6;
   public static final byte minionAttack = 7;
   public static final byte minionRageAttack = 8;
   public static final byte teleportAction = 9;
   public static final byte endTeleport = 10;
   public static final byte volleyRageAttack = 11;
   public static final byte cometRageAttack = 12;
   public static final byte hpBelowThresholdStatus = 13;

   public LichActions(LichEntity entity, IActionWithCooldown attackAction) {
      this.entity = entity;
      this.attackAction = attackAction;
      this.cancelAttackAction = () -> entity.isDeadOrDying() || entity.getTarget() == null;
   }

   public ActionGoal buildAttackGoal() {
      CooldownAction attackAction = new CooldownAction(this.attackAction, 80);
      IActionStop onCancel = () -> {
         this.entity.level().broadcastEntityEvent(this.entity, (byte)6);
         attackAction.stop();
      };
      return new ActionGoal(() -> !this.cancelAttackAction.get(), null, attackAction, null, onCancel);
   }
}
