package team.creative.creativecore;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.util.thread.EffectiveSide;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.commons.lang3.ArrayUtils;
import team.creative.creativecore.client.ClientLoader;
import team.creative.creativecore.common.CommonLoader;

public class CreativeForgeLoader implements ICreativeLoader {
   @Override
   public Side getOverallSide() {
      return FMLEnvironment.dist.isClient() ? Side.CLIENT : Side.SERVER;
   }

   @Override
   public void register(CommonLoader loader) {
      ModLoadingContext.get().getActiveContainer().getEventBus().addListener(x -> loader.onInitialize());
   }

   @Override
   public void registerClient(ClientLoader loader) {
      if (FMLLoader.getDist() == Dist.CLIENT) {
         ModLoadingContext.get().getActiveContainer().getEventBus().addListener(x -> loader.onInitializeClient());
         NeoForge.EVENT_BUS.addListener(x -> loader.registerClientCommands(x.getDispatcher()));
      }
   }

   @Override
   public void registerClientTick(Runnable run) {
      NeoForge.EVENT_BUS.addListener(x -> run.run());
   }

   @Override
   public void registerClientRenderGui(Consumer run) {
      NeoForge.EVENT_BUS.addListener(x -> run.accept(x.getGuiGraphics()));
   }

   @Override
   public void registerClientRenderStart(Runnable run) {
      NeoForge.EVENT_BUS.addListener(x -> run.run());
   }

   @Override
   public void registerLevelTick(Consumer<ServerLevel> consumer) {
      NeoForge.EVENT_BUS.addListener(x -> {
         if (x.getLevel() instanceof ServerLevel level) {
            consumer.accept(level);
         }
      });
   }

   @Override
   public void registerLevelTickStart(Consumer<ServerLevel> consumer) {
      NeoForge.EVENT_BUS.addListener(x -> {
         if (x.getLevel() instanceof ServerLevel level) {
            consumer.accept(level);
         }
      });
   }

   @Override
   public void registerUnloadLevel(Consumer<LevelAccessor> consumer) {
      NeoForge.EVENT_BUS.addListener(x -> consumer.accept(x.getLevel()));
   }

   @Override
   public void registerLoadLevel(Consumer<LevelAccessor> consumer) {
      NeoForge.EVENT_BUS.addListener(x -> consumer.accept(x.getLevel()));
   }

   @Override
   public void registerListener(Consumer consumer) {
      NeoForge.EVENT_BUS.addListener(consumer);
   }

   @Override
   public void registerClientStarted(Runnable run) {
      run.run();
   }

   @Override
   public void postForge(Event event) {
      NeoForge.EVENT_BUS.post(event);
   }

   @Override
   public boolean isModLoaded(String modid) {
      return ModList.get().isLoaded(modid);
   }

   @Override
   public float getFluidViscosityMultiplier(Fluid fluid, Level level) {
      return fluid.getFluidType().getViscosity() / 1000.0F;
   }

   @Override
   public float getFriction(LevelAccessor level, BlockPos pos, Entity entity) {
      return level.getBlockState(pos).getFriction(level, pos, entity);
   }

   @Override
   public void registerKeybind(Supplier<KeyMapping> supplier) {
      Minecraft.getInstance().options.keyMappings = (KeyMapping[])ArrayUtils.add(Minecraft.getInstance().options.keyMappings, supplier.get());
   }

   @Override
   public Side getEffectiveSide() {
      return EffectiveSide.get().isClient() ? Side.CLIENT : Side.SERVER;
   }
}
