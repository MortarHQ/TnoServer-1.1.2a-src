package com.mortal_cultivation.client.vfx;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mortal_cultivation.entity.AzureMossQilinEntity;
import com.mortal_cultivation.entity.AzureMossQilinPart;
import com.mortal_cultivation.entity.QilinEffectPayload;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Direction.Plane;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.RenderTypeHelper;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.entity.PartEntity;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME, value = Dist.CLIENT)
public final class QilinClientEffects {
   private static final List<QilinClientEffects.Effect> EFFECTS = new ArrayList<>();
   private static final ConcurrentHashMap<Long, QilinClientEffects.Debris> DISPLACED = new ConcurrentHashMap<>();
   private static final Set<Long> DIRTY_SECTIONS = new HashSet<>();
   private static final BlockState ROOT_BARK = Blocks.MANGROVE_LOG.defaultBlockState();
   private static final BlockState ROOT_TIP = Blocks.MOSS_BLOCK.defaultBlockState();
   private static final ResourceLocation JADE_RIBBON = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/entity/qilin_jade_ribbon.png");
   private static ClientLevel activeLevel;

   private QilinClientEffects() {
   }

   public static boolean hides(BlockPos pos, BlockState state) {
      QilinClientEffects.Debris piece = DISPLACED.get(pos.asLong());
      return piece != null && piece.state == state;
   }

   public static boolean consumeSectionRefresh(BlockPos origin) {
      return DIRTY_SECTIONS.remove(SectionPos.asLong(origin));
   }

   public static boolean hasSectionRefreshes() {
      return !DIRTY_SECTIONS.isEmpty();
   }

   public static void accept(QilinEffectPayload payload) {
      ClientLevel level = Minecraft.getInstance().level;
      if (level != null && level.dimension().location().equals(payload.dimension())) {
         if (payload.effect() >= 0 && payload.effect() <= 6) {
            if (level != activeLevel) {
               reset(level);
            }

            if (EFFECTS.size() >= 48) {
               EFFECTS.removeFirst().release();
            }

            long start = Math.max(level.getGameTime() - 10L, Math.min(level.getGameTime(), payload.gameTime()));
            if (payload.effect() == 1) {
               EFFECTS.removeIf(effect -> effect.type == 2 && effect.center.distanceToSqr(payload.center()) < 0.01);
            }

            EFFECTS.add(new QilinClientEffects.Effect(payload, start, level));
         }
      }
   }

   @SubscribeEvent
   public static void tick(Post event) {
      ClientLevel level = Minecraft.getInstance().level;
      if (level != null && level == activeLevel) {
         Iterator<QilinClientEffects.Effect> iterator = EFFECTS.iterator();

         while (iterator.hasNext()) {
            QilinClientEffects.Effect effect = iterator.next();
            if (level.getGameTime() - effect.start >= effect.duration()) {
               effect.release();
               iterator.remove();
            } else {
               Iterator<QilinClientEffects.Debris> pieces = effect.debris.iterator();

               while (pieces.hasNext()) {
                  QilinClientEffects.Debris piece = pieces.next();
                  if (!level.hasChunkAt(piece.pos) || level.getBlockState(piece.pos) != piece.state) {
                     release(piece);
                     pieces.remove();
                  }
               }
            }
         }
      } else {
         reset(level);
      }
   }

   @SubscribeEvent
   public static void logout(LoggingOut event) {
      reset(null);
   }

   private static void reset(ClientLevel newLevel) {
      for (QilinClientEffects.Effect effect : EFFECTS) {
         effect.release();
      }

      EFFECTS.clear();
      DISPLACED.clear();
      DIRTY_SECTIONS.clear();
      activeLevel = newLevel;
   }

   private static void release(QilinClientEffects.Debris piece) {
      if (DISPLACED.remove(piece.pos.asLong(), piece)) {
         dirty(piece.pos, piece.state);
      }
   }

   private static void dirty(BlockPos pos, BlockState state) {
      if (activeLevel != null && activeLevel == Minecraft.getInstance().level) {
         Minecraft.getInstance().levelRenderer.blockChanged(activeLevel, pos, state, state, 8);

         for (int x = pos.getX() - 1; x <= pos.getX() + 1; x++) {
            for (int y = pos.getY() - 1; y <= pos.getY() + 1; y++) {
               for (int z = pos.getZ() - 1; z <= pos.getZ() + 1; z++) {
                  DIRTY_SECTIONS.add(SectionPos.asLong(x >> 4, y >> 4, z >> 4));
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_ENTITIES && !EFFECTS.isEmpty()) {
         Minecraft minecraft = Minecraft.getInstance();
         ClientLevel level = minecraft.level;
         if (level != null && level == activeLevel) {
            Vec3 camera = event.getCamera().getPosition();
            float partial = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            PoseStack pose = event.getPoseStack();
            BufferSource buffers = minecraft.renderBuffers().bufferSource();
            pose.pushPose();
            pose.translate(-camera.x, -camera.y, -camera.z);

            for (QilinClientEffects.Effect effect : EFFECTS) {
               float age = (float)(level.getGameTime() - effect.start) + partial;
               if (!(age < 0.0F) && !(age >= effect.duration()) && event.getFrustum().isVisible(new AABB(effect.center, effect.center).inflate(5.0))) {
                  switch (effect.type) {
                     case 0:
                        renderDebris(effect, age, pose, buffers, level);
                        break;
                     case 1:
                        renderRoots(effect, age, pose, buffers, level);
                        break;
                     case 2:
                        renderRootWarning(effect, age, pose, buffers, level);
                        break;
                     case 3:
                        arc(
                           effect.center.add(0.0, 2.4, 0.0),
                           effect.yaw * (float) (Math.PI / 180.0),
                           (float) Math.PI,
                           2.0F + age * 0.045F,
                           0.36F * fade(effect, age),
                           14284479,
                           pose,
                           buffers
                        );
                        break;
                     case 4:
                        renderChargeWarning(effect, age, partial, pose, buffers, level);
                        break;
                     case 5:
                        renderTrail(effect, age, pose, buffers);
                        break;
                     case 6:
                        arc(
                           effect.center.add(0.0, 0.1, 0.0),
                           0.0F,
                           (float) (Math.PI * 2),
                           1.0F + age * 0.055F,
                           0.24F * fade(effect, age),
                           15364949,
                           pose,
                           buffers
                        );
                  }
               }
            }

            buffers.endBatch();
            pose.popPose();
         }
      }
   }

   private static float fade(QilinClientEffects.Effect effect, float age) {
      return Mth.clamp((effect.duration() - age) / 8.0F, 0.0F, 1.0F);
   }

   private static void renderDebris(QilinClientEffects.Effect effect, float age, PoseStack pose, MultiBufferSource buffers, ClientLevel level) {
      for (QilinClientEffects.Debris piece : effect.debris) {
         if (level.getBlockState(piece.pos) == piece.state) {
            float progress = Mth.clamp((age - piece.delay) / 28.0F, 0.0F, 1.0F);
            float arc = Mth.sin(progress * (float) Math.PI);
            pose.pushPose();
            pose.translate(piece.pos.getX() + 0.5, piece.pos.getY() + 0.5 + arc * piece.height, piece.pos.getZ() + 0.5);
            pose.mulPose(Axis.XP.rotationDegrees(piece.tilt * arc));
            pose.mulPose(Axis.ZP.rotationDegrees(-piece.tilt * arc * 0.7F));
            pose.translate(-0.5, -0.5, -0.5);
            renderBlock(piece.state, piece.pos, pose, buffers, level, exposedLight(level, piece.pos));
            pose.popPose();
         }
      }
   }

   private static int exposedLight(ClientLevel level, BlockPos ground) {
      int light = LevelRenderer.getLightColor(level, ground.above());

      for (Direction direction : Plane.HORIZONTAL) {
         int adjacent = LevelRenderer.getLightColor(level, ground.above().relative(direction));
         light = LightTexture.pack(
            Math.max(LightTexture.block(light), LightTexture.block(adjacent)), Math.max(LightTexture.sky(light), LightTexture.sky(adjacent))
         );
      }

      return light;
   }

   private static void renderRootWarning(QilinClientEffects.Effect effect, float age, PoseStack pose, MultiBufferSource buffers, ClientLevel level) {
      float growth = Mth.clamp(age / 18.0F, 0.0F, 1.0F);
      int light = exposedLight(level, BlockPos.containing(effect.center).below());

      for (int i = 0; i < 6; i++) {
         double angle = i * Math.PI / 3.0;
         Vec3 base = effect.center.add(Math.cos(angle) * 1.14, -0.08, Math.sin(angle) * 1.14);
         Vec3 tip = effect.center.add(Math.cos(angle) * (1.14 - growth * 0.38), 0.04, Math.sin(angle) * (1.14 - growth * 0.38));
         segment(base, tip, 0.09F, ROOT_BARK, pose, buffers, level, light);
      }
   }

   private static void renderRoots(QilinClientEffects.Effect effect, float age, PoseStack pose, MultiBufferSource buffers, ClientLevel level) {
      float visible = Mth.clamp(age / 4.0F, 0.0F, 1.0F) * Mth.clamp((effect.duration() - age) / 7.0F, 0.0F, 1.0F);
      int light = exposedLight(level, BlockPos.containing(effect.center).below());

      for (int i = 0; i < 7; i++) {
         double angle = i * Math.PI * 2.0 / 7.0 + (effect.seed & 0xFF) * 0.025;
         double x = Math.cos(angle);
         double z = Math.sin(angle);
         double height = (1.2 + 0.25 * Math.sin(i * 2.31)) * visible;
         Vec3 base = effect.center.add(x * 1.05, -0.15, z * 1.05);
         Vec3 bend = effect.center.add(x * 0.78, height * 0.45, z * 0.78);
         Vec3 upper = effect.center.add(x * 0.52 + z * 0.15, height * 0.9, z * 0.52 - x * 0.15);
         Vec3 tip = effect.center.add(x * 0.25 + z * 0.2, height, z * 0.25 - x * 0.2);
         segment(base, bend, 0.22F * visible, ROOT_BARK, pose, buffers, level, light);
         segment(bend, upper, 0.15F * visible, ROOT_BARK, pose, buffers, level, light);
         segment(upper, tip, 0.085F * visible, ROOT_BARK, pose, buffers, level, light);
         segment(bend, bend.add(z * 0.3, height * 0.27, -x * 0.3), 0.075F * visible, ROOT_BARK, pose, buffers, level, light);
         segment(tip, tip.add(0.0, 0.09 * visible, 0.0), 0.11F * visible, ROOT_TIP, pose, buffers, level, light);
      }
   }

   private static void renderChargeWarning(
      QilinClientEffects.Effect effect, float age, float partial, PoseStack pose, MultiBufferSource buffers, ClientLevel level
   ) {
      if (level.getEntity(effect.entityId) instanceof AzureMossQilinEntity boss) {
         float var15 = 0.1F + 0.045F * Mth.sin(age * 0.3F);
         Vec3 interpolation = boss.getPosition(partial).subtract(boss.position());

         for (PartEntity<?> part : boss.getParts()) {
            if (part instanceof AzureMossQilinPart horn && horn.boneName().startsWith("antler_tip_")) {
               Vec3 center = horn.getBoundingBox().getCenter().add(interpolation);
               arc(center, age * 0.1F, (float) (Math.PI * 2), 0.3F, var15, 12904865, pose, buffers);
            }
         }
      }
   }

   private static void renderTrail(QilinClientEffects.Effect effect, float age, PoseStack pose, MultiBufferSource buffers) {
      float width = 0.18F * fade(effect, age);
      double yaw = effect.yaw * (float) (Math.PI / 180.0);
      Vec3 forward = new Vec3(-Math.sin(yaw), 0.0, Math.cos(yaw));
      Vec3 side = new Vec3(forward.z, 0.0, -forward.x);

      for (int sign : new int[]{-1, 1}) {
         Vec3 from = effect.center.add(side.scale(sign * 0.9)).add(0.0, 0.5, 0.0);
         Vec3 to = from.subtract(forward.scale(2.7)).add(0.0, 0.3, 0.0);
         ribbon(from.add(0.0, -width, 0.0), to.add(0.0, -width * 0.2, 0.0), to.add(0.0, width * 0.2, 0.0), from.add(0.0, width, 0.0), 11920817, pose, buffers);
      }
   }

   private static void arc(Vec3 center, float start, float sweep, float radius, float width, int color, PoseStack pose, MultiBufferSource buffers) {
      if (!(width <= 0.001F)) {
         int segments = Math.max(12, (int)(sweep * 10.0F));

         for (int i = 0; i < segments; i++) {
            double a = start + sweep * i / segments;
            double b = start + sweep * (i + 1) / segments;
            Vec3 innerA = center.add(Math.cos(a) * (radius - width), 0.0, Math.sin(a) * (radius - width));
            Vec3 innerB = center.add(Math.cos(b) * (radius - width), 0.0, Math.sin(b) * (radius - width));
            Vec3 outerA = center.add(Math.cos(a) * radius, 0.0, Math.sin(a) * radius);
            Vec3 outerB = center.add(Math.cos(b) * radius, 0.0, Math.sin(b) * radius);
            ribbon(innerA, innerB, outerB, outerA, color, pose, buffers);
         }
      }
   }

   private static void ribbon(Vec3 a, Vec3 b, Vec3 c, Vec3 d, int color, PoseStack pose, MultiBufferSource buffers) {
      VertexConsumer consumer = buffers.getBuffer(RenderType.entityCutoutNoCull(JADE_RIBBON));
      vertex(consumer, pose, a, 0.0F, 1.0F, color);
      vertex(consumer, pose, b, 1.0F, 1.0F, color);
      vertex(consumer, pose, c, 1.0F, 0.0F, color);
      vertex(consumer, pose, d, 0.0F, 0.0F, color);
   }

   private static void vertex(VertexConsumer consumer, PoseStack pose, Vec3 pos, float u, float v, int color) {
      consumer.addVertex(pose.last(), (float)pos.x, (float)pos.y, (float)pos.z)
         .setColor(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, 255)
         .setUv(u, v)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(pose.last(), 0.0F, 1.0F, 0.0F);
   }

   private static void segment(Vec3 from, Vec3 to, float width, BlockState state, PoseStack pose, MultiBufferSource buffers, ClientLevel level, int light) {
      Vec3 direction = to.subtract(from);
      float length = (float)direction.length();
      if (!(length < 0.001F) && !(width < 0.001F)) {
         Vec3 midpoint = from.add(to).scale(0.5);
         pose.pushPose();
         pose.translate(midpoint.x, midpoint.y, midpoint.z);
         pose.mulPose(
            new Quaternionf()
               .rotationTo(new Vector3f(0.0F, 1.0F, 0.0F), new Vector3f((float)direction.x / length, (float)direction.y / length, (float)direction.z / length))
         );
         pose.scale(width, length, width);
         pose.translate(-0.5, -0.5, -0.5);
         renderBlock(state, BlockPos.containing(from), pose, buffers, level, light);
         pose.popPose();
      }
   }

   private static void renderBlock(BlockState state, BlockPos source, PoseStack pose, MultiBufferSource buffers, ClientLevel level, int light) {
      Minecraft minecraft = Minecraft.getInstance();
      BakedModel model = minecraft.getBlockRenderer().getBlockModel(state);
      ModelData data = model.getModelData(level, source, state, ModelData.EMPTY);
      RandomSource random = RandomSource.create(state.getSeed(source));

      for (RenderType layer : model.getRenderTypes(state, random, data)) {
         VertexConsumer consumer = buffers.getBuffer(RenderTypeHelper.getEntityRenderType(layer, false));

         for (int face = 0; face <= Direction.values().length; face++) {
            random.setSeed(state.getSeed(source));
            Direction direction = face == Direction.values().length ? null : Direction.values()[face];

            for (BakedQuad quad : model.getQuads(state, direction, random, data, layer)) {
               int color = quad.isTinted() ? minecraft.getBlockColors().getColor(state, level, source, quad.getTintIndex()) : 16777215;
               consumer.putBulkData(
                  pose.last(),
                  quad,
                  (color >> 16 & 0xFF) / 255.0F,
                  (color >> 8 & 0xFF) / 255.0F,
                  (color & 0xFF) / 255.0F,
                  1.0F,
                  light,
                  OverlayTexture.NO_OVERLAY
               );
            }
         }
      }
   }

   private record Debris(BlockPos pos, BlockState state, float height, float tilt, int delay) {
   }

   private static final class Effect {
      final int type;
      final Vec3 center;
      final long start;
      final int seed;
      final float yaw;
      final int entityId;
      final List<QilinClientEffects.Debris> debris = new ArrayList<>();

      Effect(QilinEffectPayload payload, long start, ClientLevel level) {
         this.type = payload.effect();
         this.center = payload.center();
         this.start = start;
         this.seed = payload.seed();
         this.yaw = payload.yaw();
         this.entityId = payload.entityId();
         if (this.type == 0) {
            RandomSource random = RandomSource.create(this.seed);
            BlockPos origin = BlockPos.containing(this.center);

            for (int x = -5; x <= 5; x++) {
               for (int z = -5; z <= 5; z++) {
                  double distance = Math.hypot(origin.getX() + x + 0.5 - this.center.x, origin.getZ() + z + 0.5 - this.center.z);
                  double farCorner = Math.hypot(
                     Math.abs(origin.getX() + x + 0.5 - this.center.x) + 0.5, Math.abs(origin.getZ() + z + 0.5 - this.center.z) + 0.5
                  );
                  if (!(distance < 0.85) && !(farCorner > 4.5)) {
                     BlockPos ground = origin.offset(x, 1, z);
                     if (level.hasChunkAt(ground)) {
                        for (int down = 0; down < 5; ground = ground.below()) {
                           BlockState state = level.getBlockState(ground);
                           if (state.getRenderShape() == RenderShape.MODEL
                              && !state.hasBlockEntity()
                              && state.getFluidState().isEmpty()
                              && state.isCollisionShapeFullBlock(level, ground)) {
                              if (level.getBlockState(ground.above()).canBeReplaced()) {
                                 QilinClientEffects.Debris piece = new QilinClientEffects.Debris(
                                    ground.immutable(), state, 0.5F + random.nextFloat() * 0.45F, (random.nextFloat() - 0.5F) * 16.0F, (int)(distance * 1.2)
                                 );
                                 if (QilinClientEffects.DISPLACED.putIfAbsent(ground.asLong(), piece) == null) {
                                    this.debris.add(piece);
                                    QilinClientEffects.dirty(ground, state);
                                 }
                              }
                              break;
                           }

                           down++;
                        }
                     }
                  }
               }
            }
         }
      }

      void release() {
         for (QilinClientEffects.Debris piece : this.debris) {
            QilinClientEffects.release(piece);
         }
      }

      int duration() {
         return switch (this.type) {
            case 0 -> 34;
            case 1 -> 40;
            case 2 -> 20;
            case 3, 5 -> 8;
            case 4 -> 24;
            case 6 -> 44;
            default -> 1;
         };
      }
   }
}
