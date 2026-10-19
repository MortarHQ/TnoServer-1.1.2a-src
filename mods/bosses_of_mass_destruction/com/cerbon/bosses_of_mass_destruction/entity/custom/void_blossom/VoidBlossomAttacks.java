package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.entity.ai.TargetSwitcher;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.CooldownAction;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionStop;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.ai.goals.ActionGoal;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import java.util.Map;
import java.util.function.Supplier;

public class VoidBlossomAttacks {
   private final VoidBlossomEntity entity;
   private final Supplier<Boolean> cancelAttackAction;
   private final VoidBlossomMoveLogic moveLogic;
   public static final byte spikeAttack = 4;
   public static final byte spikeWaveAttack = 5;
   public static final byte stopAttackAnimation = 6;
   public static final byte sporeAttack = 7;
   public static final byte bladeAttack = 8;
   public static final byte blossomAction = 9;
   public static final byte spawnAction = 10;

   public VoidBlossomAttacks(VoidBlossomEntity entity, EventScheduler eventScheduler, Supplier<Boolean> doBlossom, TargetSwitcher targetSwitcher) {
      this.entity = entity;
      this.cancelAttackAction = () -> entity.isDeadOrDying() || entity.getTarget() == null;
      Map<Byte, IActionWithCooldown> statusRegistry = Map.of(
         (byte)4,
         new SpikeAction(entity, eventScheduler, this.cancelAttackAction),
         (byte)5,
         new SpikeWaveAction(entity, eventScheduler, this.cancelAttackAction),
         (byte)7,
         new SporeAction(entity, eventScheduler, this.cancelAttackAction),
         (byte)8,
         new BladeAction(entity, eventScheduler, this.cancelAttackAction),
         (byte)9,
         new BlossomAction(entity, eventScheduler, this.cancelAttackAction)
      );
      this.moveLogic = new VoidBlossomMoveLogic(statusRegistry, entity, doBlossom, targetSwitcher);
   }

   public ActionGoal buildAttackGoal() {
      CooldownAction attackAction = new CooldownAction(this.moveLogic, 80);
      IActionStop onCancel = () -> {
         this.entity.level().broadcastEntityEvent(this.entity, (byte)6);
         attackAction.stop();
      };
      return new ActionGoal(() -> !this.cancelAttackAction.get(), null, attackAction, null, onCancel);
   }
}
