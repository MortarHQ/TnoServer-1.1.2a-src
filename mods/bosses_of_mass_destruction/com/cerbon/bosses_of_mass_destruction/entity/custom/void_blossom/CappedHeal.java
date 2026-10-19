package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.LichUtils;
import com.cerbon.bosses_of_mass_destruction.entity.util.EntityAdapter;
import com.cerbon.bosses_of_mass_destruction.entity.util.EntityStats;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityTick;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;

public class CappedHeal implements IEntityTick<ServerLevel> {
   private final Mob entity;
   private final List<Float> hpMilestones;
   private final float healingPerTick;
   private final EntityAdapter adapter;
   private final EntityStats stats;

   public CappedHeal(Mob entity, List<Float> hpMilestones, float healingPerTick) {
      this.entity = entity;
      this.hpMilestones = hpMilestones;
      this.healingPerTick = healingPerTick;
      this.adapter = new EntityAdapter(entity);
      this.stats = new EntityStats(entity);
   }

   public void tick(ServerLevel level) {
      if (this.entity.getTarget() == null) {
         LichUtils.cappedHeal(this.adapter, this.stats, this.hpMilestones, this.healingPerTick, this.entity::heal);
      }
   }
}
