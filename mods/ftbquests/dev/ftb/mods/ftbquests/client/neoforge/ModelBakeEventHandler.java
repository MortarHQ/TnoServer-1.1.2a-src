package dev.ftb.mods.ftbquests.client.neoforge;

import com.google.common.collect.UnmodifiableIterator;
import dev.ftb.mods.ftbquests.registry.ModBlocks;
import java.util.function.Function;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ModelEvent.ModifyBakingResult;

public class ModelBakeEventHandler {
   private ModelBakeEventHandler() {
   }

   @SubscribeEvent
   public static void onModelBake(ModifyBakingResult event) {
      override(event, (Block)ModBlocks.BARRIER.get(), CamouflagingModel::new);
      override(event, (Block)ModBlocks.STAGE_BARRIER.get(), CamouflagingModel::new);
   }

   private static void override(ModifyBakingResult event, Block block, Function<BakedModel, CamouflagingModel> f) {
      UnmodifiableIterator var3 = block.getStateDefinition().getPossibleStates().iterator();

      while (var3.hasNext()) {
         BlockState state = (BlockState)var3.next();
         ModelResourceLocation loc = BlockModelShaper.stateToModelLocation(state);
         BakedModel model = (BakedModel)event.getModels().get(loc);
         if (model != null) {
            event.getModels().put(loc, (BakedModel)f.apply(model));
         }
      }
   }
}
