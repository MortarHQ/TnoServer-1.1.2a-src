package team.creative.creativecore.client.render.model;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import team.creative.creativecore.client.render.box.RenderBox;

@OnlyIn(Dist.CLIENT)
public abstract class CreativeBlockModel {
   public abstract List<? extends RenderBox> getBoxes(BlockState var1, ModelData var2, RandomSource var3);

   @NotNull
   public abstract ModelData getModelData(@NotNull BlockAndTintGetter var1, @NotNull BlockPos var2, @NotNull BlockState var3, @NotNull ModelData var4);
}
