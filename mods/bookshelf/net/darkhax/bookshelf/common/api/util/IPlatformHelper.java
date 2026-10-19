package net.darkhax.bookshelf.common.api.util;

import java.io.File;
import java.nio.file.Path;
import java.util.Set;
import net.darkhax.bookshelf.common.api.ModEntry;
import net.darkhax.bookshelf.common.api.PhysicalSide;
import net.darkhax.bookshelf.common.api.registry.register.MenuRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;

public interface IPlatformHelper {
   Path getGamePath();

   default File getGameDirectory() {
      return this.getGamePath().toFile();
   }

   Path getConfigPath();

   default File getConfigDirectory() {
      return this.getConfigPath().toFile();
   }

   Path getModsPath();

   default File getModsDirectory() {
      return this.getModsPath().toFile();
   }

   boolean isModLoaded(String var1);

   boolean isDevelopmentEnvironment();

   PhysicalSide getPhysicalSide();

   default boolean isPhysicalClient() {
      return this.getPhysicalSide().isClient();
   }

   Set<ModEntry> getLoadedMods();

   boolean isTestingEnvironment();

   String getName();

   @Deprecated
   <T extends AbstractContainerMenu> void unsafeRegisterMenu(ResourceLocation var1, MenuRegister.ClientMenuFactory<T> var2);
}
