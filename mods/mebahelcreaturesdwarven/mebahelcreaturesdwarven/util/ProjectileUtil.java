package mebahelcreaturesdwarven.util;

import mebahelcreaturesdwarven.entity.projectile.SteamProjectileEntity;
import net.minecraft.class_1309;
import net.minecraft.class_1937;
import net.minecraft.class_243;

public class ProjectileUtil {
   public void shootSteamProjectile(class_1937 world, class_1309 actor, class_243 shootDirection, float damage) {
      double speed = 1.5;
      SteamProjectileEntity projectile = new SteamProjectileEntity(world, actor, damage);
      class_243 nozzleOffset = new class_243(0.3, 0.3, -0.6);
      class_243 origin = actor.method_19538()
         .method_1019(nozzleOffset.method_1024(-actor.method_36454() * (float) (Math.PI / 180.0)))
         .method_1031(0.0, actor.method_17682() - 0.95F, 0.0);
      projectile.method_18799(shootDirection.method_1029().method_1021(speed));
      projectile.method_5814(origin.field_1352, origin.field_1351, origin.field_1350);
      world.method_8649(projectile);
   }
}
