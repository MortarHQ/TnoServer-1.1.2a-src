package com.teamresourceful.resourcefullib.client.highlights.base;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public record Highlight(ResourceLocation id, List<HighlightLine> lines) {
   public static Highlight of(ResourceLocation id, List<HighlightLine> lines) {
      return new Highlight(id, Collections.unmodifiableList(lines));
   }

   public static Codec<Highlight> codec(ResourceLocation id) {
      return RecordCodecBuilder.create(
         instance -> instance.group(RecordCodecBuilder.point(id), HighlightLine.CODEC.listOf().fieldOf("lines").forGetter(Highlight::lines))
            .apply(instance, Highlight::of)
      );
   }

   public Highlight copy() {
      return new Highlight(null, this.lines.stream().map(HighlightLine::copy).toList());
   }

   public void render(VertexConsumer consumer, PoseStack poseStack, Vec3 cameraPos, Vec3 offset, BlockPos blockPos) {
      try (CloseablePoseStack ignored = new CloseablePoseStack(poseStack)) {
         float x = (float)(blockPos.getX() - cameraPos.x());
         float y = (float)(blockPos.getY() - cameraPos.y());
         float z = (float)(blockPos.getZ() - cameraPos.z());
         x += (float)offset.x();
         y += (float)offset.y();
         z += (float)offset.z();

         for (HighlightLine line : this.lines) {
            line.render(poseStack, consumer, x, y, z);
         }
      }
   }
}
