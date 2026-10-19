package net.unusual.block_factorys_bosses.geckolib.util;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.loading.json.raw.Bone;
import software.bernie.geckolib.loading.json.raw.LocatorValue;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record ParticleLocator(Bone parent, LocatorValue locatorValue, GeoBone bone) {
   public Vec3 getLocalPosition() {
      Vector3d localPos = this.bone.getLocalPosition();
      if (this.locatorValue.locatorClass() == null) {
         return new Vec3(
            localPos.x + this.locatorValue.values()[0] / 16.0,
            localPos.y + this.locatorValue.values()[1] / 16.0,
            localPos.z + this.locatorValue.values()[2] / 16.0
         );
      }

      double[] offset = this.locatorValue.locatorClass().offset();
      return new Vec3(localPos.x + offset[0] / 16.0, localPos.y + offset[1] / 16.0, localPos.z + offset[2] / 16.0);
   }
}
