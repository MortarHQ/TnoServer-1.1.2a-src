package com.cerbon.bosses_of_mass_destruction.entity.custom.lich;

import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public class MinionRageAction implements IActionWithCooldown {
   private final LichEntity entity;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> shouldCancel;
   private final MinionAction minionAction;
   private final List<Integer> delayTimes = new ArrayList<>();
   private final int totalMoveTime;
   public static final int numMobs = 9;
   public static final int initialSpawnTimeCooldown = 40;
   public static final int initialBetweenSpawnDelay = 40;
   public static final int spawnDelayDecrease = 3;

   public MinionRageAction(LichEntity entity, EventScheduler eventScheduler, Supplier<Boolean> shouldCancel, MinionAction minionAction) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.shouldCancel = shouldCancel;
      this.minionAction = minionAction;

      for (int i = 0; i < 9; i++) {
         this.delayTimes.add(40 + i * 40 - MathUtils.consecutiveSum(0, i) * 3);
      }

      this.totalMoveTime = this.delayTimes.get(this.delayTimes.size() - 1) + 40;
   }

   @Override
   public int perform() {
      LivingEntity target = this.entity.getTarget();
      if (!(target instanceof ServerPlayer)) {
         return this.totalMoveTime;
      }

      this.performMinionSummon((ServerPlayer)target);
      return this.totalMoveTime;
   }

   private void performMinionSummon(ServerPlayer target) {
      for (int delayTime : this.delayTimes) {
         this.eventScheduler.addEvent(new TimedEvent(() -> this.minionAction.beginSummonSingleMob(target), delayTime, 1, this.shouldCancel));
      }
   }
}
