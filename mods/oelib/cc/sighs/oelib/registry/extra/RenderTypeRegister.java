package cc.sighs.oelib.registry.extra;

import cc.sighs.oelib.registry.RegistrationDispatcher;
import cc.sighs.oelib.registry.action.RenderTypeBlocksAction;
import cc.sighs.oelib.registry.action.RenderTypeFluidsAction;
import java.util.function.Supplier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class RenderTypeRegister {
   private RenderTypeRegister() {
   }

   @SafeVarargs
   public static void registerBlocks(RenderType type, Supplier<? extends Block>... blocks) {
      RegistrationDispatcher.perform(new RenderTypeBlocksAction(type, blocks));
   }

   @SafeVarargs
   public static void registerFluids(RenderType type, Supplier<? extends Fluid>... fluids) {
      RegistrationDispatcher.perform(new RenderTypeFluidsAction(type, fluids));
   }
}
