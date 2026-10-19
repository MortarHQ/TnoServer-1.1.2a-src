package net.tslat.smartbrainlib.api.core.behaviour.custom.path;

import java.util.function.ToIntFunction;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SetRandomFlyingTarget<E extends PathfinderMob> extends SetRandomHoverTarget<E> {
   protected ToIntFunction<E> verticalWeight = entity -> -2;

   public SetRandomFlyingTarget() {
      this.setRadius(10.0, 10.0);
   }

   public SetRandomFlyingTarget<E> verticalWeight(ToIntFunction<E> function) {
      this.verticalWeight = function;
      return this;
   }

   @Nullable
   @Override
   protected Vec3 getTargetPos(E entity) {
      Vec3 entityFacing = entity.getViewVector(0.0F);
      Vec3 hoverPos = HoverRandomPos.getPos(
         entity, (int)Math.ceil(this.radius.xzRadius()), (int)Math.ceil(this.radius.yRadius()), entityFacing.x, entityFacing.z, (float) (Math.PI / 2), 3, 1
      );
      return hoverPos != null
         ? hoverPos
         : AirAndWaterRandomPos.getPos(
            entity,
            (int)Math.ceil(this.radius.xzRadius()),
            (int)Math.ceil(this.radius.yRadius()),
            this.verticalWeight.applyAsInt(entity),
            entityFacing.x,
            entityFacing.z,
            (float) (Math.PI / 2)
         );
   }
}
