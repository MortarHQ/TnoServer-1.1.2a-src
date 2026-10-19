package mebahelcreaturesdwarven.particle;

import mebahelcreaturesdwarven.MebahelCreaturesDwarven;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.class_2378;
import net.minecraft.class_2400;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public class ModParticles {
   public static final class_2400 STEAM_PARTICLE = FabricParticleTypes.simple();

   public static void registerParticles() {
      class_2378.method_10230(class_7923.field_41180, class_2960.method_60655("mebahelcreaturesdwarven", "steam_particle"), STEAM_PARTICLE);
      MebahelCreaturesDwarven.LOGGER.info("Registering particles for mebahelcreaturesdwarven.");
   }
}
