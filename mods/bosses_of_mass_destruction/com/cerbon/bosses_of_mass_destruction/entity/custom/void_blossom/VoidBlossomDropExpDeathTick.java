package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityTick;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopiesServer;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.static_utilities.RandomUtils;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class VoidBlossomDropExpDeathTick implements IEntityTick<ServerLevel> {
   private final LivingEntity entity;
   private final EventScheduler eventScheduler;
   private final int exp;

   public VoidBlossomDropExpDeathTick(LivingEntity entity, EventScheduler eventScheduler, int exp) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.exp = exp;
   }

   public void tick(ServerLevel level) {
      if (this.entity.deathTime == 1) {
         this.scheduleExp();
      }
   }

   private void scheduleExp() {
      int expTicks = 20;
      int expPerTick = (int)((float)this.exp / expTicks);
      Vec3 fallDirection = VecUtils.planeProject(this.entity.getForward(), VecUtils.yAxis).yRot(180.0F);
      Vec3 originPos = this.entity.position().add(VecUtils.yAxis.scale(2.0));
      this.eventScheduler.addEvent(new TimedEvent(() -> {
         Vec3 pos = originPos.add(RandomUtils.randVec().scale(2.0)).add(fallDirection.scale(RandomUtils.randDouble(6.0) + 6.0));
         VanillaCopiesServer.awardExperience(expPerTick, pos, this.entity.level());
      }, (int)(70.0F - expTicks - 1.0F), expTicks, () -> false));
   }
}
