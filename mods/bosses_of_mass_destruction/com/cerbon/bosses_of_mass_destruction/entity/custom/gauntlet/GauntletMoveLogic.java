package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.entity.ai.TargetSwitcher;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.damage.DamageMemory;
import com.cerbon.cerbons_api.api.general.data.HistoricalData;
import com.cerbon.cerbons_api.api.general.random.WeightedRandom;
import java.util.Map;
import net.minecraft.world.entity.LivingEntity;

public class GauntletMoveLogic implements IActionWithCooldown {
   private final Map<Byte, IActionWithCooldown> actions;
   private final GauntletEntity entity;
   private final HistoricalData<Byte> moveHistory = new HistoricalData((byte)0, 4);
   private final TargetSwitcher targetSwitcher;
   public static final double laserPercentage = 0.85;
   public static final double swirlPunchPercentage = 0.7;
   public static final double blindnessPercentage = 0.5;

   public GauntletMoveLogic(Map<Byte, IActionWithCooldown> actions, GauntletEntity entity, DamageMemory damageMemory) {
      this.actions = actions;
      this.entity = entity;
      this.targetSwitcher = new TargetSwitcher(entity, damageMemory);
   }

   @Override
   public int perform() {
      this.targetSwitcher.trySwitchTarget();
      byte moveByte = this.chooseMove();
      IActionWithCooldown action = this.actions.get(moveByte);
      if (action == null) {
         throw new IllegalArgumentException(moveByte + " action not registered as an attack");
      }

      this.entity.level().broadcastEntityEvent(this.entity, moveByte);
      return action.perform();
   }

   private byte chooseMove() {
      LivingEntity target = this.entity.getTarget();
      if (target == null) {
         return 4;
      }

      float healthPercentage = this.entity.getHealth() / this.entity.getMaxHealth();
      WeightedRandom<Byte> random = new WeightedRandom();
      double punchWeight = 1.0;
      double laserWeight = this.moveHistory.get(0) != 8 && !(healthPercentage >= 0.85) ? 0.7 : 0.0;
      double swirlPunchWeight = this.moveHistory.get(0) != 10 && !(healthPercentage >= 0.7) ? 0.7 : 0.0;
      double blindnessWeight = !this.moveHistory.getAll().contains((byte)11) && !(healthPercentage >= 0.5) ? 1.0 : 0.0;
      random.add(punchWeight, (byte)4);
      random.add(laserWeight, (byte)8);
      random.add(swirlPunchWeight, (byte)10);
      random.add(blindnessWeight, (byte)11);
      byte nextMove = (Byte)random.next();
      this.moveHistory.add(nextMove);
      return nextMove;
   }
}
