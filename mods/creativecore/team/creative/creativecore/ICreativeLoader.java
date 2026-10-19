package team.creative.creativecore;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.Event;
import team.creative.creativecore.client.ClientLoader;
import team.creative.creativecore.common.CommonLoader;
import team.creative.creativecore.common.gui.dialog.GuiDialogHandler;

public interface ICreativeLoader {
   default void loadCommon() {
      CreativeCoreGuiRegistry.init();
      GuiDialogHandler.init();
   }

   Side getOverallSide();

   Side getEffectiveSide();

   void register(CommonLoader var1);

   void registerClient(ClientLoader var1);

   void registerClientTick(Runnable var1);

   void registerClientRenderStart(Runnable var1);

   void registerClientRenderGui(Consumer var1);

   void registerClientStarted(Runnable var1);

   void registerKeybind(Supplier<KeyMapping> var1);

   void registerLevelTick(Consumer<ServerLevel> var1);

   void registerLevelTickStart(Consumer<ServerLevel> var1);

   void registerLoadLevel(Consumer<LevelAccessor> var1);

   void registerUnloadLevel(Consumer<LevelAccessor> var1);

   <T> void registerListener(Consumer<T> var1);

   float getFluidViscosityMultiplier(Fluid var1, Level var2);

   float getFriction(LevelAccessor var1, BlockPos var2, Entity var3);

   void postForge(Event var1);

   boolean isModLoaded(String var1);
}
