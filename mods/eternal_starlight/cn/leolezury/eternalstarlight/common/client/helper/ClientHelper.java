package cn.leolezury.eternalstarlight.common.client.helper;

import cn.leolezury.eternalstarlight.common.network.ClientDismountPacket;
import cn.leolezury.eternalstarlight.common.network.ClientMountPacket;
import cn.leolezury.eternalstarlight.common.network.OpenBookPacket;
import cn.leolezury.eternalstarlight.common.network.OpenCrestGuiPacket;
import cn.leolezury.eternalstarlight.common.network.OpenGatekeeperGuiPacket;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.network.SimpleActionPacket;
import cn.leolezury.eternalstarlight.common.network.UpdateBookPacket;
import cn.leolezury.eternalstarlight.common.network.UpdateCameraPacket;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import net.minecraft.world.phys.Vec3;

public interface ClientHelper {
   void handleServerToClientSimpleAction(SimpleActionPacket var1);

   void handleParticlePacket(ParticlePacket var1);

   void handleOpenCrestGui(OpenCrestGuiPacket var1);

   void handleUpdateCamera(UpdateCameraPacket var1);

   void handleClientMount(ClientMountPacket var1);

   void handleClientDismount(ClientDismountPacket var1);

   void handleOpenGatekeeperGui(OpenGatekeeperGuiPacket var1);

   void handleUpdateBook(UpdateBookPacket var1);

   void handleOpenBook(OpenBookPacket var1);

   void spawnStellarRackParticles(Vec3 var1);

   void spawnStellarRackItemParticles(Vec3 var1);

   void spawnManaCrystalItemParticles(ManaType var1, Vec3 var2);

   void handleMeteorShowerClientTick();

   float handleMeteorShowerRainLevel();
}
