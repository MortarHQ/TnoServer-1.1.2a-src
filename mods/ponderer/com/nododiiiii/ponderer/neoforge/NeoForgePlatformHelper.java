package com.nododiiiii.ponderer.neoforge;

import com.nododiiiii.ponderer.neoforge.sticksnapshot.client.ClientInputHandler;
import com.nododiiiii.ponderer.neoforge.sticksnapshot.client.NeoForgeShowInterfaceClient;
import com.nododiiiii.ponderer.platform.services.PlatformHelper;
import com.nododiiiii.ponderer.ponder.DslScene;
import java.nio.file.Path;
import java.util.function.Supplier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;

public class NeoForgePlatformHelper implements PlatformHelper {
   @Override
   public String getPlatformName() {
      return "neoforge";
   }

   @Override
   public boolean isClient() {
      return FMLEnvironment.dist == Dist.CLIENT;
   }

   @Override
   public boolean isDevelopmentEnvironment() {
      return !FMLEnvironment.production;
   }

   @Override
   public boolean isModLoaded(String modId) {
      return ModList.get().isLoaded(modId);
   }

   @Override
   public Path getGameDir() {
      return FMLPaths.GAMEDIR.get();
   }

   @Override
   public Path getConfigDir() {
      return FMLPaths.CONFIGDIR.get();
   }

   @Override
   public void executeOnClient(Supplier<Runnable> runnable) {
      if (FMLEnvironment.dist == Dist.CLIENT) {
         runnable.get().run();
      }
   }

   @Override
   public void showInterfaceStep(DslScene.DslStep step) {
      this.executeOnClient(() -> () -> NeoForgeShowInterfaceClient.showInterfaceStep(step));
   }

   @Override
   public void clickInterfaceStep(DslScene.DslStep step) {
      this.executeOnClient(() -> () -> NeoForgeShowInterfaceClient.clickInterfaceStep(step));
   }

   @Override
   public void closeInterfaceStep(String reason) {
      this.executeOnClient(() -> () -> ClientInputHandler.closeEmbeddedMirrorFromPonder(reason));
   }

   @Override
   public boolean supportsEmbeddedInterfacePreview() {
      return true;
   }
}
