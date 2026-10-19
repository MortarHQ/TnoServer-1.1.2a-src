package dev.ftb.mods.ftbultimine.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.Shapes.DoubleLineConsumer;
import org.joml.Matrix4f;

public class EdgeCallback implements DoubleLineConsumer {
   private final BufferBuilder buffer;
   private final Matrix4f matrix;
   private final BlockPos pos;
   private final int alpha;

   public EdgeCallback(BufferBuilder b, Matrix4f m, BlockPos p, int a) {
      this.buffer = b;
      this.matrix = m;
      this.pos = p;
      this.alpha = a;
   }

   public void consume(double x1, double y1, double z1, double x2, double y2, double z2) {
      this.buffer
         .addVertex(this.matrix, (float)(x1 + this.pos.getX()), (float)(y1 + this.pos.getY()), (float)(z1 + this.pos.getZ()))
         .setColor(255, 255, 255, this.alpha);
      this.buffer
         .addVertex(this.matrix, (float)(x2 + this.pos.getX()), (float)(y2 + this.pos.getY()), (float)(z2 + this.pos.getZ()))
         .setColor(255, 255, 255, this.alpha);
   }
}
