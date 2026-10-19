package com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate;

import com.github.b4ndithelps.tennogamenolife.entity.DragonRoarCircle;
import com.github.b4ndithelps.tennogamenolife.registry.entity.TenreincarnationEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;

public final class SkyStrikeVfx {
   public static final int EXPANSION_DURATION_TICKS = 60;

   private SkyStrikeVfx() {
   }

   public static void spawnExplosionExpansion(ServerLevel level, Vec3 center, float radius) {
      DragonRoarCircle expansion = new DragonRoarCircle((EntityType<?>)TenreincarnationEntities.DRAGON_ROAR_CIRCLE.get(), level);
      expansion.setPos(center);
      expansion.setExpansionVfx();
      expansion.setSize(radius);
      expansion.setVfxSeed(level.random.nextInt());
      expansion.setVfxDuration(60);
      expansion.setVfxStartGameTime(level.getGameTime());
      expansion.setLife(63);
      level.addFreshEntity(expansion);
   }
}
