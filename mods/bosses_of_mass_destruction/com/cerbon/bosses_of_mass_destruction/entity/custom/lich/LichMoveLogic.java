package com.cerbon.bosses_of_mass_destruction.entity.custom.lich;

import com.cerbon.bosses_of_mass_destruction.entity.ai.TargetSwitcher;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.damage.DamageMemory;
import com.cerbon.bosses_of_mass_destruction.entity.damage.IDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.damage.StagedDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityStats;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityTick;
import com.cerbon.cerbons_api.api.general.data.HistoricalData;
import com.cerbon.cerbons_api.api.general.random.WeightedRandom;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class LichMoveLogic implements IDamageHandler, IActionWithCooldown, IEntityTick<ServerLevel> {
   private final Map<Byte, IActionWithCooldown> actions;
   private final LichEntity actor;
   private final HistoricalData<Byte> moveHistory = new HistoricalData((byte)0, 4);
   private final HistoricalData<Vec3> positionalHistory = new HistoricalData(Vec3.ZERO, 10);
   private final List<Byte> priorityMoves = new ArrayList<>();
   private final StagedDamageHandler stagedDamageHandler;
   private final TargetSwitcher targetSwitcher;

   public LichMoveLogic(Map<Byte, IActionWithCooldown> actions, LichEntity actor, DamageMemory damageMemory) {
      this.actions = actions;
      this.actor = actor;
      this.stagedDamageHandler = new StagedDamageHandler(LichUtils.hpPercentRageModes, () -> this.priorityMoves.addAll(List.of((byte)12, (byte)11, (byte)8)));
      this.targetSwitcher = new TargetSwitcher(actor, damageMemory);
   }

   @Override
   public void afterDamage(IEntityStats stats, DamageSource damageSource, float amount, boolean result) {
      this.stagedDamageHandler.afterDamage(stats, damageSource, amount, result);
   }

   @Override
   public boolean shouldDamage(LivingEntity actor, DamageSource damageSource, float amount) {
      return true;
   }

   @Override
   public void beforeDamage(IEntityStats stats, DamageSource damageSource, float amount) {
      this.stagedDamageHandler.beforeDamage(stats, damageSource, amount);
   }

   @Override
   public int perform() {
      Byte moveByte = !this.priorityMoves.isEmpty() ? this.priorityMoves.remove(0) : this.chooseRegularMove();
      IActionWithCooldown action = this.actions.get(moveByte);
      if (action == null) {
         throw new RuntimeException(moveByte + " action not registered as an attack");
      }

      this.actor.level().broadcastEntityEvent(this.actor, moveByte);
      return action.perform();
   }

   private Byte chooseRegularMove() {
      this.targetSwitcher.trySwitchTarget();
      WeightedRandom<Byte> random = new WeightedRandom();
      double teleportWeight = this.getTeleportWeight();
      double minionWeight = this.moveHistory.getAll().contains((byte)7) ? 0.0 : 2.0;
      random.addAll(Arrays.asList(new Pair(1.0, (byte)5), new Pair(1.0, (byte)6), new Pair(minionWeight, (byte)7), new Pair(teleportWeight, (byte)9)));
      Byte nextMove = (Byte)random.next();
      this.moveHistory.add(nextMove);
      return nextMove;
   }

   private double getTeleportWeight() {
      List<Vec3> positions = this.positionalHistory.getAll();
      double distanceTraveled = IntStream.range(0, positions.size() - 1).mapToDouble(i -> positions.get(i).distanceTo(positions.get(i + 1))).sum();
      Entity target = this.actor.getTarget();
      return target == null
         ? 0.0
         : (this.actor.inLineOfSight(target) ? 0.0 : 4.0)
            + (distanceTraveled > 0.25 ? 0.0 : 8.0)
            + (this.actor.position().distanceTo(target.position()) < 6.0 ? 8.0 : 0.0);
   }

   public void tick(ServerLevel level) {
      this.positionalHistory.add(this.actor.position());
   }
}
