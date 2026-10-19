package net.tslat.smartbrainlib.object;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.phys.Vec3;

public class DynamicPositionTracker implements PositionTracker {
   private final Supplier<Vec3> posSupplier;

   public DynamicPositionTracker(Supplier<Vec3> posSupplier) {
      this.posSupplier = posSupplier;
   }

   public Vec3 currentPosition() {
      return this.posSupplier.get();
   }

   public BlockPos currentBlockPosition() {
      return BlockPos.containing((Position)this.posSupplier.get());
   }

   public boolean isVisibleBy(LivingEntity entity) {
      return true;
   }
}
