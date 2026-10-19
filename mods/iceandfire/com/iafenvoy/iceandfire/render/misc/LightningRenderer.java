package com.iafenvoy.iceandfire.render.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.apache.commons.lang3.tuple.Pair;
import org.joml.Matrix4f;

public class LightningRenderer {
   private static final float REFRESH_TIME = 3.0F;
   private static final double MAX_OWNER_TRACK_TIME = 100.0;
   private final Random random = new Random();
   private final Minecraft client = Minecraft.getInstance();
   private final Map<Object, LightningRenderer.BoltOwnerData> boltOwners = new Object2ObjectOpenHashMap();
   private LightningRenderer.Timestamp refreshTimestamp = new LightningRenderer.Timestamp();

   public void render(float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn) {
      VertexConsumer buffer = bufferIn.getBuffer(RenderType.lightning());
      Matrix4f matrix = matrixStackIn.last().pose();
      assert this.client.level != null;
      LightningRenderer.Timestamp timestamp = new LightningRenderer.Timestamp(this.client.level.getGameTime(), partialTicks);
      boolean refresh = timestamp.isPassed(this.refreshTimestamp, 0.33333334F);
      if (refresh) {
         this.refreshTimestamp = timestamp;
      }

      Iterator<Entry<Object, LightningRenderer.BoltOwnerData>> iter = this.boltOwners.entrySet().iterator();

      while (iter.hasNext()) {
         Entry<Object, LightningRenderer.BoltOwnerData> entry = iter.next();
         LightningRenderer.BoltOwnerData data = entry.getValue();
         if (refresh) {
            data.bolts.removeIf(bolt -> bolt.tick(timestamp));
         }

         if (data.bolts.isEmpty() && data.lastBolt != null && data.lastBolt.getSpawnFunction().isConsecutive()) {
            data.addBolt(new LightningRenderer.BoltInstance(data.lastBolt, timestamp), timestamp);
         }

         data.bolts.forEach(bolt -> bolt.render(matrix, buffer, timestamp));
         if (data.bolts.isEmpty() && timestamp.isPassed(data.lastUpdateTimestamp, 100.0)) {
            iter.remove();
         }
      }
   }

   public void update(Object owner, LightningBoltData newBoltData, float partialTicks) {
      if (this.client.level != null) {
         LightningRenderer.BoltOwnerData data = this.boltOwners.computeIfAbsent(owner, o -> new LightningRenderer.BoltOwnerData());
         data.lastBolt = newBoltData;
         LightningRenderer.Timestamp timestamp = new LightningRenderer.Timestamp(this.client.level.getGameTime(), partialTicks);
         if ((!data.lastBolt.getSpawnFunction().isConsecutive() || data.bolts.isEmpty()) && timestamp.isPassed(data.lastBoltTimestamp, data.lastBoltDelay)) {
            data.addBolt(new LightningRenderer.BoltInstance(newBoltData, timestamp), timestamp);
         }

         data.lastUpdateTimestamp = timestamp;
      }
   }

   public static class BoltInstance {
      private final LightningBoltData bolt;
      private final List<LightningBoltData.BoltQuads> renderQuads;
      private final LightningRenderer.Timestamp createdTimestamp;

      public BoltInstance(LightningBoltData bolt, LightningRenderer.Timestamp timestamp) {
         this.bolt = bolt;
         this.renderQuads = bolt.generate();
         this.createdTimestamp = timestamp;
      }

      public void render(Matrix4f matrix, VertexConsumer buffer, LightningRenderer.Timestamp timestamp) {
         float lifeScale = timestamp.subtract(this.createdTimestamp).value() / this.bolt.getLifespan();
         Pair<Integer, Integer> bounds = this.bolt.getFadeFunction().getRenderBounds(this.renderQuads.size(), lifeScale);

         for (int i = (Integer)bounds.getLeft(); i < bounds.getRight(); i++) {
            this.renderQuads
               .get(i)
               .getVecs()
               .forEach(
                  v -> buffer.addVertex(matrix, (float)v.x, (float)v.y, (float)v.z)
                     .setColor(this.bolt.getColor().x(), this.bolt.getColor().y(), this.bolt.getColor().z(), this.bolt.getColor().w())
               );
         }
      }

      public boolean tick(LightningRenderer.Timestamp timestamp) {
         return timestamp.isPassed(this.createdTimestamp, this.bolt.getLifespan());
      }
   }

   public class BoltOwnerData {
      private final Set<LightningRenderer.BoltInstance> bolts = new ObjectOpenHashSet();
      private LightningBoltData lastBolt;
      private LightningRenderer.Timestamp lastBoltTimestamp = new LightningRenderer.Timestamp();
      private LightningRenderer.Timestamp lastUpdateTimestamp = new LightningRenderer.Timestamp();
      private double lastBoltDelay;

      private void addBolt(LightningRenderer.BoltInstance instance, LightningRenderer.Timestamp timestamp) {
         this.bolts.add(instance);
         this.lastBoltDelay = instance.bolt.getSpawnFunction().getSpawnDelay(LightningRenderer.this.random);
         this.lastBoltTimestamp = timestamp;
      }
   }

   public static class Timestamp {
      private final long ticks;
      private final float partial;

      public Timestamp() {
         this(0L, 0.0F);
      }

      public Timestamp(long ticks, float partial) {
         this.ticks = ticks;
         this.partial = partial;
      }

      public LightningRenderer.Timestamp subtract(LightningRenderer.Timestamp other) {
         long newTicks = this.ticks - other.ticks;
         float newPartial = this.partial - other.partial;
         if (newPartial < 0.0F) {
            newPartial++;
            newTicks--;
         }

         return new LightningRenderer.Timestamp(newTicks, newPartial);
      }

      public float value() {
         return (float)this.ticks + this.partial;
      }

      public boolean isPassed(LightningRenderer.Timestamp prev, double duration) {
         long ticksPassed = this.ticks - prev.ticks;
         if (ticksPassed > duration) {
            return true;
         }

         duration -= ticksPassed;
         return duration >= 1.0 ? false : this.partial - prev.partial >= duration;
      }
   }
}
