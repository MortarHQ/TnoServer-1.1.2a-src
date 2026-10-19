package net.unusual.block_factorys_bosses.client.camera.renderer;

import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import javax.annotation.Nullable;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent.OverlayType;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeCameraAngles;
import net.unusual.block_factorys_bosses.block.HugeDoorBlock;
import net.unusual.block_factorys_bosses.block.entity.HugeDoorBlockEntity;
import net.unusual.block_factorys_bosses.client.CameraInject;
import net.unusual.block_factorys_bosses.client.camera.BossesRiseClientCinematicCamera;
import net.unusual.block_factorys_bosses.client.camera.CinematicCameraTypes;
import net.unusual.block_factorys_bosses.client.camera.internal.CameraHandler;
import net.unusual.block_factorys_bosses.geckolib.boss.CinematicRenderer;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.GeoBone;

@EventBusSubscriber(Dist.CLIENT)
public class CameraRenderer implements CameraHandler {
   private static CameraInject camera;
   public static int viewBobCancel = 0;
   @Nullable
   private static CameraType previousCameraType = null;
   private static float yaw;
   private static float pitch;
   private static Vec3 pos = Vec3.ZERO;
   private static float yawO;
   private static float pitchO;
   private static Vec3 posO = Vec3.ZERO;

   @SubscribeEvent
   public static void onRenderBlockOverlay(RenderBlockScreenEffectEvent event) {
      if (event.getOverlayType() == OverlayType.FIRE) {
         if (BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.CAMERA)) {
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public static void computeCameraAngles(ComputeCameraAngles event) {
      ClientLevel level = Minecraft.getInstance().level;
      camera = (CameraInject)event.getCamera();
      if (level != null) {
         if (BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.CAMERA)) {
            float partialTick = (float)event.getPartialTick();
            Vec3 interpolatedPos = new Vec3(Mth.lerp(partialTick, posO.x, pos.x), Mth.lerp(partialTick, posO.y, pos.y), Mth.lerp(partialTick, posO.z, pos.z));
            if (!interpolatedPos.equals(Vec3.ZERO)) {
               camera.setCinematic(true);
               camera.setCinematicPosition(interpolatedPos);
            } else {
               camera.setCinematic(false);
               viewBobCancel = 3;
            }

            event.setYaw(Mth.lerp(partialTick, yawO, yaw));
            event.setPitch(Mth.lerp(partialTick, pitchO, pitch));
            event.setRoll(0.0F);
         }
      }
   }

   @SubscribeEvent
   public static void onTickPre(Post event) {
      if (BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.CAMERA)) {
         Minecraft.getInstance().options.setCameraType(CameraType.FIRST_PERSON);
         updateTickState();
      }
   }

   public static void updateTickState() {
      posO = pos;
      yawO = yaw;
      pitchO = pitch;
      GeoBone cameraBone = BossesRiseClientCinematicCamera.getCameraBone();
      if (cameraBone == null) {
         pos = Vec3.ZERO;
         posO = Vec3.ZERO;
         calculateAngles();
      } else {
         updateCameraBoneState(cameraBone);
      }
   }

   private static void calculateAngles() {
      Player player = Minecraft.getInstance().player;
      if (player != null) {
         Vec3 cameraPos = player.getEyePosition();
         Vec3 trackedPos = null;
         Object trackedObject = BossesRiseClientCinematicCamera.getTrackedObject();
         if (trackedObject instanceof BlockEntity blockEntity) {
            trackedPos = blockEntity.getBlockPos().getCenter();
         }

         if (trackedObject instanceof Entity entity) {
            trackedPos = entity.getEyePosition();
         }

         if (trackedPos != null) {
            double dx = cameraPos.x - trackedPos.x;
            double dy = cameraPos.y - trackedPos.y;
            double dz = cameraPos.z - trackedPos.z;
            yaw = (float)Math.atan2(dx, dz) * (-180.0F / (float)Math.PI) + 180.0F;
            pitch = (float)Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * (180.0F / (float)Math.PI);
         }
      }
   }

   private static Quaternionf rotateBlock(Direction facing) {
      return switch (facing) {
         case SOUTH -> Axis.YP.rotationDegrees(180.0F);
         case WEST -> Axis.YP.rotationDegrees(90.0F);
         case NORTH -> Axis.YP.rotationDegrees(0.0F);
         case EAST -> Axis.YP.rotationDegrees(270.0F);
         case UP -> Axis.XP.rotationDegrees(90.0F);
         case DOWN -> Axis.XN.rotationDegrees(90.0F);
         default -> throw new MatchException(null, null);
      };
   }

   private static void updateCameraBoneState(GeoBone cameraBone) {
      Vector3f bonePos;
      if (BossesRiseClientCinematicCamera.getTrackedObject() instanceof BlockEntity blockEntity) {
         BlockPos blockPos = blockEntity.getBlockPos();
         Direction facing = (Direction)blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
         Quaternionf blockRotation = rotateBlock(facing);
         bonePos = toVec3f(cameraBone.getLocalPosition()).rotate(blockRotation).add(blockPos.getX() + 0.5F, blockPos.getY(), blockPos.getZ() + 0.5F);
         Vector3f lookDir = cameraBone.getWorldSpaceMatrix().transformDirection(new Vector3f(0.0F, 0.0F, 1.0F)).rotate(blockRotation);
         if (blockEntity instanceof HugeDoorBlockEntity) {
            bonePos = bonePos.add(HugeDoorBlock.getHorizontalCenterOffset(blockEntity.getBlockState()));
         }

         yaw = (float)Mth.atan2(lookDir.x(), -lookDir.z()) * (180.0F / (float)Math.PI);
         pitch = (float)Mth.atan2(lookDir.y(), Mth.sqrt(lookDir.x() * lookDir.x() + lookDir.z() * lookDir.z())) * (180.0F / (float)Math.PI);
      } else {
         bonePos = toVec3f(cameraBone.getWorldPosition());
         Monster boss = (Monster)BossesRiseClientCinematicCamera.getTrackedObject();
         CinematicRenderer<Monster> render = (CinematicRenderer<Monster>)Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(boss);
         Pair<Float, Float> cinematicYawAndPitch = render.getCinematicYawAndPitch(boss, cameraBone, Minecraft.getInstance().player);
         yaw = (Float)cinematicYawAndPitch.getFirst();
         pitch = (Float)cinematicYawAndPitch.getSecond();
      }

      if (!bonePos.equals(0.0F, 0.0F, 0.0F)) {
         pos = new Vec3(bonePos.x, bonePos.y, bonePos.z);
      }
   }

   private static Vector3f toVec3f(Vector3d vec) {
      return new Vector3f((float)vec.x(), (float)vec.y(), (float)vec.z());
   }

   @Override
   public void init() {
      Minecraft mc = Minecraft.getInstance();
      previousCameraType = mc.options.getCameraType();
   }

   @Override
   public void cleanup() {
      if (previousCameraType != null) {
         Minecraft.getInstance().options.setCameraType(previousCameraType);
         previousCameraType = null;
      }

      camera.setCinematic(false);
      pos = Vec3.ZERO;
      posO = Vec3.ZERO;
      yawO = 0.0F;
      yaw = 0.0F;
      pitchO = 0.0F;
      pitch = 0.0F;
   }
}
