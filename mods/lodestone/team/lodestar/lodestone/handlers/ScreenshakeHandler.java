package team.lodestar.lodestone.handlers;

import java.util.ArrayList;
import java.util.function.Consumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeCameraAngles;
import team.lodestar.lodestone.config.ClientConfig;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeBuilder;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

public class ScreenshakeHandler {
   private static final ArrayList<ScreenshakeInstance> INSTANCES = new ArrayList<>();
   private static float intensity;

   public static void computeAngles(ComputeCameraAngles event) {
      RandomSource random = Minecraft.getInstance().level.getRandom();
      if (ScreenshakeHandler.intensity > 0.0F) {
         float intensity = (float)(ScreenshakeHandler.intensity * ClientConfig.SCREENSHAKE_INTENSITY.getConfigValue());
         float yaw = RandomHelper.randomBetween(random, 0.0F, intensity * 2.0F) * (random.nextBoolean() ? 1 : -1);
         float pitch = RandomHelper.randomBetween(random, 0.0F, intensity * 2.0F) * (random.nextBoolean() ? 1 : -1);
         event.setYaw(event.getYaw() + yaw);
         event.setPitch(event.getPitch() + pitch);
      }
   }

   public static void clientTick(ClientLevel level, Camera camera) {
      float intensitySum = 0.0F;

      for (ScreenshakeInstance instance : INSTANCES) {
         instance.tick();
         intensitySum += instance.getStrength(camera);
      }

      intensity = intensitySum;
      INSTANCES.removeIf(ScreenshakeInstance::isExpired);
   }

   public static void addScreenshake(Consumer<ScreenshakeBuilder> constructor) {
      ScreenshakeBuilder builder = ScreenshakeBuilder.create();
      constructor.accept(builder);
      addScreenshake(builder.build());
   }

   public static void addScreenshake(ScreenshakeInstance instance) {
      INSTANCES.add(instance);
   }
}
