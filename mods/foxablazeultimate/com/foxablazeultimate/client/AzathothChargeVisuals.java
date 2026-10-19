package com.foxablazeultimate.client;

import com.foxablazeultimate.ability.skill.azathoth.AzathothChargeData;
import com.foxablazeultimate.ability.skill.azathoth.AzathothCombat;
import com.foxablazeultimate.client.render.FoxAblazeRenderTypes;
import com.foxablazeultimate.network.AzathothChargeVisualPayload;
import com.foxablazeultimate.registry.FoxAblazeUltimateDataComponents;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import software.bernie.geckolib.cache.texture.AnimatableTexture;

public final class AzathothChargeVisuals {
   private static final ResourceLocation LIGHTNING = ResourceLocation.fromNamespaceAndPath("tensura", "textures/entity/misc/black_lightning.png");
   private static final Map<Integer, AzathothChargeVisuals.State> ACTIVE = new HashMap<>();
   private static final Map<BakedModel, AzathothChargeVisuals.Axis> WEAPON_AXES = new WeakHashMap<>();
   private static final Map<BakedModel, AzathothChargeVisuals.Axis> SWORD_AXES = new WeakHashMap<>();
   private static final ThreadLocal<LivingEntity> ITEM_OWNER = new ThreadLocal<>();
   private static ClientLevel currentLevel;

   private AzathothChargeVisuals() {
   }

   private static void ensureLevel() {
      if (currentLevel != Minecraft.getInstance().level) {
         ACTIVE.clear();
         currentLevel = Minecraft.getInstance().level;
      }
   }

   public static void update(AzathothChargeVisualPayload payload) {
      ensureLevel();
      if (currentLevel != null) {
         ACTIVE.entrySet().removeIf(entry -> entry.getValue().expires < currentLevel.getGameTime());
         if (payload.active()) {
            ACTIVE.put(payload.entityId(), new AzathothChargeVisuals.State(payload, currentLevel.getGameTime() + 60L));
         } else {
            ACTIVE.remove(payload.entityId());
         }
      }
   }

   public static boolean active(LivingEntity entity) {
      ensureLevel();
      if (entity != null && entity.isAlive() && !entity.isInvisible()) {
         AzathothChargeVisuals.State state = ACTIVE.get(entity.getId());
         if (state == null) {
            return false;
         }

         if (state.expires < entity.level().getGameTime()) {
            ACTIVE.remove(entity.getId());
            return false;
         }

         AzathothChargeVisualPayload payload = state.payload;
         if (!entity.getUUID().equals(payload.ownerId())) {
            return false;
         }

         if (payload.charging()) {
            return true;
         }

         if (payload.weaponToken() == null) {
            return !AzathothCombat.isWeapon(entity.getMainHandItem());
         }

         AzathothChargeData charge = (AzathothChargeData)entity.getMainHandItem().get((DataComponentType)FoxAblazeUltimateDataComponents.AZATHOTH_CHARGE.get());
         return charge != null && entity.getUUID().equals(charge.owner()) && payload.weaponToken().equals(charge.token());
      } else {
         return false;
      }
   }

   public static LivingEntity pushItemOwner(LivingEntity owner) {
      LivingEntity previous = ITEM_OWNER.get();
      ITEM_OWNER.set(owner);
      return previous;
   }

   public static void restoreItemOwner(LivingEntity previous) {
      if (previous == null) {
         ITEM_OWNER.remove();
      } else {
         ITEM_OWNER.set(previous);
      }
   }

   public static boolean mainHandContext(LivingEntity owner, ItemDisplayContext context) {
      return owner != null
         && (
            owner.getMainArm() == HumanoidArm.RIGHT
               ? context == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND || context == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND
               : context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND
         );
   }

   private static float time(LivingEntity owner) {
      return owner.tickCount + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
   }

   public static void renderWeapon(ItemStack stack, ItemDisplayContext context, PoseStack pose, MultiBufferSource buffers, BakedModel model) {
      LivingEntity owner = ITEM_OWNER.get();
      if (mainHandContext(owner, context) && AzathothCombat.isWeapon(stack) && active(owner)) {
         AzathothChargeVisuals.State state = ACTIVE.get(owner.getId());
         AzathothChargeData charge = (AzathothChargeData)stack.get((DataComponentType)FoxAblazeUltimateDataComponents.AZATHOTH_CHARGE.get());
         if (state != null
            && (state.payload.charging() || charge != null && owner.getUUID().equals(charge.owner()) && charge.token().equals(state.payload.weaponToken()))) {
            boolean generatedSword = !model.isGui3d()
               && (stack.getItem() instanceof SwordItem || stack.is(ItemTags.SWORDS) || stack.canPerformAction(ItemAbilities.SWORD_SWEEP));
            AzathothChargeVisuals.Axis axis = generatedSword
               ? SWORD_AXES.computeIfAbsent(model, AzathothChargeVisuals::swordAxis)
               : WEAPON_AXES.computeIfAbsent(model, AzathothChargeVisuals::weaponAxis);
            renderWeaponCoil(pose, buffers, time(owner), axis);
         }
      }
   }

   private static AzathothChargeVisuals.Axis swordAxis(BakedModel model) {
      BakedQuad front = null;
      double largestArea = 0.0;
      double minDepth = Double.POSITIVE_INFINITY;
      double maxDepth = Double.NEGATIVE_INFINITY;
      RandomSource random = RandomSource.create(42L);

      for (int face = -1; face < 6; face++) {
         random.setSeed(42L);

         for (BakedQuad quad : model.getQuads(null, face < 0 ? null : Direction.values()[face], random)) {
            for (int vertex = 0; vertex < 4; vertex++) {
               double depth = vertexPosition(quad, vertex).z;
               minDepth = Math.min(minDepth, depth);
               maxDepth = Math.max(maxDepth, depth);
            }

            if (quad.getDirection() == Direction.SOUTH || quad.getDirection() == Direction.NORTH) {
               Vec3 a = vertexPosition(quad, 0);
               Vec3 b = vertexPosition(quad, 1);
               Vec3 d = vertexPosition(quad, 3);
               double area = b.subtract(a).cross(d.subtract(a)).lengthSqr();
               if (area > largestArea) {
                  largestArea = area;
                  front = quad;
               }
            }
         }
      }

      if (front == null) {
         return weaponAxis(model);
      }

      TextureAtlasSprite sprite = front.getSprite();
      SpriteContents contents = sprite.contents();
      int frame = contents.getUniqueFrames().findFirst().orElse(0);
      int[] vertices = front.getVertices();
      int stride = vertices.length / 4;
      double u0 = Float.intBitsToFloat(vertices[4]);
      double v0 = Float.intBitsToFloat(vertices[5]);
      double du1 = Float.intBitsToFloat(vertices[stride + 4]) - u0;
      double dv1 = Float.intBitsToFloat(vertices[stride + 5]) - v0;
      double du3 = Float.intBitsToFloat(vertices[3 * stride + 4]) - u0;
      double dv3 = Float.intBitsToFloat(vertices[3 * stride + 5]) - v0;
      double determinant = du1 * dv3 - du3 * dv1;
      if (Math.abs(determinant) < 1.0E-15) {
         return weaponAxis(model);
      }

      Vec3 origin = vertexPosition(front, 0);
      Vec3 side1 = vertexPosition(front, 1).subtract(origin);
      Vec3 side3 = vertexPosition(front, 3).subtract(origin);
      ArrayList<Vec3> pixels = new ArrayList<>();
      int step = Math.max(1, Math.max(contents.width(), contents.height()) / 64);

      for (int y = 0; y < contents.height(); y += step) {
         for (int x = 0; x < contents.width(); x += step) {
            if (!contents.isTransparent(frame, x, y)) {
               double u = sprite.getU((x + 0.5F) / contents.width()) - u0;
               double v = sprite.getV((y + 0.5F) / contents.height()) - v0;
               double s = (u * dv3 - du3 * v) / determinant;
               double t = (du1 * v - u * dv1) / determinant;
               pixels.add(origin.add(side1.scale(s)).add(side3.scale(t)));
            }
         }
      }

      AzathothWeaponGeometry.Axis blade = AzathothWeaponGeometry.swordBlade(pixels, minDepth, maxDepth);
      return new AzathothChargeVisuals.Axis(blade.start(), blade.end(), blade.radius());
   }

   private static Vec3 vertexPosition(BakedQuad quad, int index) {
      int[] vertices = quad.getVertices();
      int start = index * (vertices.length / 4);
      return new Vec3(Float.intBitsToFloat(vertices[start]), Float.intBitsToFloat(vertices[start + 1]), Float.intBitsToFloat(vertices[start + 2]));
   }

   private static AzathothChargeVisuals.Axis weaponAxis(BakedModel model) {
      ArrayList<Vec3> points = new ArrayList<>();
      RandomSource random = RandomSource.create(42L);

      for (int face = -1; face < 6; face++) {
         random.setSeed(42L);

         for (BakedQuad quad : model.getQuads(null, face < 0 ? null : Direction.values()[face], random)) {
            int[] vertices = quad.getVertices();
            int stride = vertices.length / 4;

            for (int i = 0; i < 4; i++) {
               points.add(
                  new Vec3(
                     Float.intBitsToFloat(vertices[i * stride]), Float.intBitsToFloat(vertices[i * stride + 1]), Float.intBitsToFloat(vertices[i * stride + 2])
                  )
               );
            }
         }
      }

      if (points.isEmpty()) {
         return new AzathothChargeVisuals.Axis(new Vec3(0.5, 0.1, 0.5), new Vec3(0.5, 1.0, 0.5), 0.16);
      }

      Vec3 center = Vec3.ZERO;

      for (Vec3 point : points) {
         center = center.add(point);
      }

      center = center.scale(1.0 / points.size());
      Vec3 direction = new Vec3(0.5, 1.0, 0.25).normalize();

      for (int iteration = 0; iteration < 16; iteration++) {
         Vec3 next = Vec3.ZERO;

         for (Vec3 point : points) {
            Vec3 offset = point.subtract(center);
            next = next.add(offset.scale(offset.dot(direction)));
         }

         if (next.lengthSqr() < 1.0E-9) {
            break;
         }

         direction = next.normalize();
      }

      double min = Double.POSITIVE_INFINITY;
      double max = Double.NEGATIVE_INFINITY;

      for (Vec3 point : points) {
         double projection = point.subtract(center).dot(direction);
         min = Math.min(min, projection);
         max = Math.max(max, projection);
      }

      double radius = 0.09;

      for (Vec3 point : points) {
         Vec3 offset = point.subtract(center);
         radius = Math.max(radius, offset.subtract(direction.scale(offset.dot(direction))).length());
      }

      return new AzathothChargeVisuals.Axis(center.add(direction.scale(min)), center.add(direction.scale(max)), radius + 0.03);
   }

   private static void renderWeaponCoil(PoseStack pose, MultiBufferSource buffers, float time, AzathothChargeVisuals.Axis axis) {
      VertexConsumer out = buffers.getBuffer(FoxAblazeRenderTypes.AZATHOTH_COIL);
      double thickness = Math.clamp(axis.radius * 0.09, 0.01, 0.02);

      for (int strand = 0; strand < 2; strand++) {
         double phase = time * 0.16 + strand * Math.PI;
         AzathothCoilGeometry.Section previous = AzathothCoilGeometry.section(axis.start, axis.end, axis.radius, 0.0, phase);

         for (int step = 1; step <= 40; step++) {
            AzathothCoilGeometry.Section next = AzathothCoilGeometry.section(axis.start, axis.end, axis.radius, step / 40.0, phase);

            for (int side = 0; side < 4; side++) {
               int color = (side + strand) % 2 == 0 ? 245 : 12;
               coilVertex(out, pose, previous.vertex(side, thickness), color);
               coilVertex(out, pose, next.vertex(side, thickness), color);
               coilVertex(out, pose, next.vertex(side + 1, thickness), color);
               coilVertex(out, pose, previous.vertex(side + 1, thickness), color);
            }

            previous = next;
         }
      }
   }

   private static void coilVertex(VertexConsumer out, PoseStack pose, Vec3 point, int color) {
      out.addVertex(pose.last().pose(), (float)point.x, (float)point.y, (float)point.z).setColor(color, color, color, 255);
   }

   public static void renderArm(LivingEntity owner, ModelPart arm, PoseStack pose, MultiBufferSource buffers, float partialTick) {
      if (active(owner) && !AzathothCombat.isWeapon(owner.getMainHandItem())) {
         pose.pushPose();
         arm.translateAndRotate(pose);
         float centerX = owner.getMainArm() == HumanoidArm.RIGHT ? -0.04F : 0.04F;
         render(pose, buffers, owner.tickCount + partialTick, new Vec3(centerX, 0.05, 0.0), new Vec3(centerX, 0.64, 0.0), 0.17F, 0.045F);
         pose.popPose();
      }
   }

   public static void renderGrip(LivingEntity owner, ItemStack stack, ItemDisplayContext context, PoseStack pose, MultiBufferSource buffers) {
      if (context.firstPerson() && mainHandContext(owner, context) && !stack.isEmpty() && !AzathothCombat.isWeapon(stack) && active(owner)) {
         render(pose, buffers, time(owner), new Vec3(0.0, -0.18, 0.15), new Vec3(0.0, 0.22, 0.15), 0.16F, 0.045F);
      }
   }

   private static void render(PoseStack pose, MultiBufferSource buffers, float time, Vec3 start, Vec3 end, float radius, float width) {
      AnimatableTexture.setAndUpdate(LIGHTNING);
      VertexConsumer out = buffers.getBuffer(RenderType.entityTranslucentEmissive(LIGHTNING));
      Vec3 axis = end.subtract(start).normalize();
      Vec3 side = axis.cross(Math.abs(axis.y) < 0.9 ? new Vec3(0.0, 1.0, 0.0) : new Vec3(1.0, 0.0, 0.0)).normalize();
      Vec3 normal = axis.cross(side).normalize();

      for (int strand = 0; strand < 2; strand++) {
         for (int step = 0; step < 32; step++) {
            float a = step / 32.0F;
            float b = (step + 1) / 32.0F;
            double phase = time * 0.16 + strand * Math.PI;
            Vec3 p = point(start, end, side, normal, a, phase, radius);
            Vec3 q = point(start, end, side, normal, b, phase, radius);
            Vec3 edge = axis.scale(width);
            vertex(out, pose, p.subtract(edge), 0.0F, a);
            vertex(out, pose, q.subtract(edge), 0.0F, b);
            vertex(out, pose, q.add(edge), 1.0F, b);
            vertex(out, pose, p.add(edge), 1.0F, a);
         }
      }
   }

   private static Vec3 point(Vec3 start, Vec3 end, Vec3 side, Vec3 normal, float t, double phase, float radius) {
      double angle = t * Math.PI * 4.0 + phase;
      return start.lerp(end, t).add(side.scale(Math.cos(angle) * radius)).add(normal.scale(Math.sin(angle) * radius));
   }

   private static void vertex(VertexConsumer out, PoseStack pose, Vec3 point, float u, float v) {
      out.addVertex(pose.last().pose(), (float)point.x, (float)point.y, (float)point.z)
         .setColor(255, 255, 255, 230)
         .setUv(u, v)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(pose.last(), 0.0F, 1.0F, 0.0F);
   }

   private record Axis(Vec3 start, Vec3 end, double radius) {
   }

   private record State(AzathothChargeVisualPayload payload, long expires) {
   }
}
