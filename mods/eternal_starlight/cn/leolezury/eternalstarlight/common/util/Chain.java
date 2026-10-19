package cn.leolezury.eternalstarlight.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public record Chain(List<Chain.Segment> segments) {
   private static final String TAG_X = "x";
   private static final String TAG_Y = "y";
   private static final String TAG_Z = "z";
   private static final String TAG_LENGTH = "length";
   private static final String TAG_PITCH = "pitch";
   private static final String TAG_YAW = "yaw";
   private static final String TAG_SEGMENTS_SIZE = "segments_size";
   private static final String TAG_SEGMENT = "segment";

   public Chain(Vec3 position, int numSegments, float segmentLength) {
      this(new ArrayList<>());

      for (int i = 0; i < numSegments; i++) {
         this.segments().add(new Chain.Segment(position.add(0.0, numSegments - i - 1, 0.0), segmentLength));
      }
   }

   public void update(Vec3 targetPos, Vec3 attachPos, float rotationSpeed) {
      Vec3 rotationTarget = targetPos;
      Vec3 lastLowerPos = targetPos;

      for (int i = 0; i < this.segments().size(); i++) {
         Chain.Segment segment = this.segments().get(i);
         segment.rotateTowards(rotationTarget, rotationSpeed);
         segment.setUpperPosition(lastLowerPos);
         rotationTarget = segment.getUpperPosition();
         lastLowerPos = segment.getLowerPosition();
      }

      if (attachPos != null) {
         Vec3 offset = attachPos.subtract(lastLowerPos);

         for (Chain.Segment segment : this.segments()) {
            segment.setLowerPosition(segment.getLowerPosition().add(offset));
         }
      }
   }

   public Optional<Vec3> getEndPos() {
      return this.segments().isEmpty() ? Optional.empty() : Optional.of(this.segments().getFirst().getUpperPosition());
   }

   public void save(CompoundTag tag) {
      tag.putInt("segments_size", this.segments().size());

      for (int i = 0; i < this.segments().size(); i++) {
         Chain.Segment segment = this.segments().get(i);
         CompoundTag segmentTag = new CompoundTag();
         segment.save(segmentTag);
         tag.put("segment" + i, segmentTag);
      }
   }

   public static Chain load(CompoundTag tag) {
      List<Chain.Segment> segments = new ArrayList<>();
      int segmentsSize = tag.getInt("segments_size");

      for (int i = 0; i < segmentsSize; i++) {
         if (tag.contains("segment" + i, 10)) {
            CompoundTag segmentTag = tag.getCompound("segment" + i);
            Chain.Segment segment = Chain.Segment.load(segmentTag);
            segments.add(segment);
         }
      }

      return new Chain(segments);
   }

   public static class Segment {
      private final float length;
      private Vec3 position;
      private float pitch;
      private float yaw;

      public float getLength() {
         return this.length;
      }

      public Vec3 getLowerPosition() {
         return this.position;
      }

      public Vec3 getMiddlePosition() {
         return ESMathUtil.rotationToPosition(this.position, this.length / 2.0F, this.pitch, this.yaw);
      }

      public Vec3 getUpperPosition() {
         return ESMathUtil.rotationToPosition(this.position, this.length, this.pitch, this.yaw);
      }

      public void setLowerPosition(Vec3 position) {
         this.position = position;
      }

      public void setUpperPosition(Vec3 position) {
         this.position = position.add(this.getLowerPosition()).subtract(this.getUpperPosition());
      }

      public float getPitch() {
         return this.pitch;
      }

      public void setPitch(float pitch) {
         this.pitch = pitch;
      }

      public float getYaw() {
         return this.yaw;
      }

      public void setYaw(float yaw) {
         this.yaw = yaw;
      }

      public Segment(Vec3 position, float length) {
         this.position = position;
         this.length = length;
      }

      public Segment(Vec3 position, float length, float pitch, float yaw) {
         this(position, length);
         this.pitch = pitch;
         this.yaw = yaw;
      }

      public void rotateTowards(Vec3 target, float maxSpeed) {
         float wantedPitch = ESMathUtil.positionToPitch(this.position, target);
         float wantedYaw = ESMathUtil.positionToYaw(this.position, target);
         this.setPitch(Mth.approachDegrees(this.pitch, wantedPitch, maxSpeed));
         this.setYaw(Mth.approachDegrees(this.yaw, wantedYaw, maxSpeed));
      }

      public void save(CompoundTag tag) {
         tag.putFloat("x", (float)this.getLowerPosition().x);
         tag.putFloat("y", (float)this.getLowerPosition().y);
         tag.putFloat("z", (float)this.getLowerPosition().z);
         tag.putFloat("length", this.getLength());
         tag.putFloat("pitch", this.getPitch());
         tag.putFloat("yaw", this.getYaw());
      }

      public static Chain.Segment load(CompoundTag tag) {
         Vec3 pos = new Vec3(tag.getFloat("x"), tag.getFloat("y"), tag.getFloat("z"));
         float length = tag.getFloat("length");
         float pitch = tag.getFloat("pitch");
         float yaw = tag.getFloat("yaw");
         return new Chain.Segment(pos, length, pitch, yaw);
      }
   }
}
