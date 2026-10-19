package net.jadenxgamer.netherexp.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class ParticleHelper {
   public static void surroundBlockParticle(Level level, BlockPos pos, SimpleParticleType particle) {
      RandomSource random = level.random;
      Direction[] directions = Direction.values();

      for (Direction direction : directions) {
         BlockPos relativePos = pos.relative(direction);
         if (!level.getBlockState(relativePos).isSolidRender(level, relativePos)) {
            Axis axis = direction.getAxis();
            double x = axis == Axis.X ? 0.5 + 0.5625 * direction.getStepX() : random.nextFloat();
            double y = axis == Axis.Y ? 0.5 + 0.5625 * direction.getStepY() : random.nextFloat();
            double z = axis == Axis.Z ? 0.5 + 0.5625 * direction.getStepZ() : random.nextFloat();
            level.addParticle(particle, pos.getX() + x, pos.getY() + y, pos.getZ() + z, 0.0, 0.0, 0.0);
         }
      }
   }

   public static Vec3 calculateBoneWorldPosition(LivingEntity entity, float partialTicks, ModelPart... bones) {
      PoseStack poseStack = new PoseStack();
      float yBodyRot = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
      poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(180.0F - yBodyRot));
      poseStack.scale(-1.0F, -1.0F, 1.0F);
      poseStack.translate(0.0F, -1.501F, 0.0F);

      for (ModelPart bone : bones) {
         bone.translateAndRotate(poseStack);
      }

      Vector3f offset = poseStack.last().pose().transformPosition(new Vector3f(0.0F, 0.0F, 0.0F));
      double x = Mth.lerp(partialTicks, entity.xo, entity.getX());
      double y = Mth.lerp(partialTicks, entity.yo, entity.getY());
      double z = Mth.lerp(partialTicks, entity.zo, entity.getZ());
      return new Vec3(x + offset.x(), y + offset.y(), z + offset.z());
   }
}
