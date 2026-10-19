package net.blay09.mods.waystones.compat;

import java.util.function.Function;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.block.entity.WarpPlateBlockEntity;
import net.blay09.mods.waystones.block.entity.WaystoneBlockEntityBase;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.InterModComms;
import org.jetbrains.annotations.Nullable;

public class TheOneProbeIntegration {
   public TheOneProbeIntegration() {
      InterModComms.sendTo("theoneprobe", "getTheOneProbe", TheOneProbeIntegration.TopInitializer::new);
   }

   public static class ProbeInfoProvider implements IProbeInfoProvider {
      public ResourceLocation getID() {
         return ResourceLocation.fromNamespaceAndPath("waystones", "top");
      }

      public void addProbeInfo(ProbeMode mode, IProbeInfo info, Player player, Level level, BlockState state, IProbeHitData data) {
         BlockEntity tileEntity = level.getBlockEntity(data.getPos());
         if (!(tileEntity instanceof WarpPlateBlockEntity) && tileEntity instanceof WaystoneBlockEntityBase) {
            Waystone waystone = ((WaystoneBlockEntityBase)tileEntity).getWaystone();
            boolean isActivated = !waystone.getWaystoneType().equals(WaystoneTypes.WAYSTONE) || PlayerWaystoneManager.isWaystoneActivated(player, waystone);
            if (isActivated && waystone.hasName() && waystone.isValid()) {
               info.text(waystone.getName());
            } else {
               info.text(Component.translatable("tooltip.waystones.undiscovered"));
            }
         }
      }
   }

   public static class TopInitializer implements Function<ITheOneProbe, Void> {
      @Nullable
      public Void apply(@Nullable ITheOneProbe top) {
         if (top != null) {
            top.registerProvider(new TheOneProbeIntegration.ProbeInfoProvider());
         }

         return null;
      }
   }
}
