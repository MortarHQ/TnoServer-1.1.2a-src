package mebahelcreaturesdwarven;

import java.io.File;
import mebahelcreaturesdwarven.block.block.ModBlocks;
import mebahelcreaturesdwarven.block.entity.ModBlockEntities;
import mebahelcreaturesdwarven.config.ModMultiplayerChest;
import mebahelcreaturesdwarven.entity.ModEntities;
import mebahelcreaturesdwarven.entity.custom.DwemerCenturionEntity;
import mebahelcreaturesdwarven.entity.custom.DwemerSphereEntity;
import mebahelcreaturesdwarven.entity.custom.DwemerSpiderEntity;
import mebahelcreaturesdwarven.entity.custom.DwemerSpiderGuardianEntity;
import mebahelcreaturesdwarven.item.ModItemGroups;
import mebahelcreaturesdwarven.item.ModItems;
import mebahelcreaturesdwarven.item.ModSpawnEggs;
import mebahelcreaturesdwarven.particle.ModParticles;
import mebahelcreaturesdwarven.sound.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MebahelCreaturesDwarven implements ModInitializer {
   public static final String MOD_ID = "mebahelcreaturesdwarven";
   public static final Logger LOGGER = LoggerFactory.getLogger("mebahelcreaturesdwarven");

   public void onInitialize() {
      File configDir = new File(FabricLoader.getInstance().getConfigDir().toFile(), "mebahelcreaturesdwarven");
      ModMultiplayerChest.loadConfig(configDir);
      FabricDefaultAttributeRegistry.register(ModEntities.DWARVEN_SPHERE, DwemerSphereEntity.setAttributes());
      FabricDefaultAttributeRegistry.register(ModEntities.DWARVEN_CENTURION, DwemerCenturionEntity.setAttributes());
      FabricDefaultAttributeRegistry.register(ModEntities.DWARVEN_SPIDER, DwemerSpiderEntity.setAttributes());
      FabricDefaultAttributeRegistry.register(ModEntities.DWARVEN_SPIDER_GUARDIAN, DwemerSpiderGuardianEntity.setAttributes());
      ModSounds.registerSounds();
      ModBlocks.registerModBlocks();
      ModBlockEntities.registerModBlockEntities();
      ModItems.registerModItems();
      ModSpawnEggs.registerModItems();
      ModItemGroups.registerItemGroups();
      ModParticles.registerParticles();
      Thread.setDefaultUncaughtExceptionHandler((t, e) -> e.printStackTrace());
   }
}
