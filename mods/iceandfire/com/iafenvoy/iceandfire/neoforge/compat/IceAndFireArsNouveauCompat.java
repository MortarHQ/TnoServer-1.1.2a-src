package com.iafenvoy.iceandfire.neoforge.compat;

import com.hollingsworth.arsnouveau.api.mob_jar.JarBehavior;
import com.hollingsworth.arsnouveau.api.registry.JarBehaviorRegistry;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import com.iafenvoy.iceandfire.entity.SeaSerpentEntity;
import com.iafenvoy.iceandfire.registry.IafEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;

public class IceAndFireArsNouveauCompat {
   public static void init() {
      JarBehaviorRegistry.register((EntityType)IafEntities.SEA_SERPENT.get(), new IceAndFireArsNouveauCompat.SeaSerpentBehavior());
   }

   private static class SeaSerpentBehavior extends JarBehavior<SeaSerpentEntity> {
      public Vec3 scaleOffset(MobJarTile pBlockEntity) {
         return new Vec3(-0.85, -0.85, -0.85);
      }

      public Vec3 translate(MobJarTile pBlockEntity) {
         return new Vec3(0.0, 0.4, 0.0);
      }
   }
}
