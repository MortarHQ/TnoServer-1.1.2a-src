package fzzyhmstrs.emi_loot.neoforge.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.event.ClientTickEvent.Pre;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class BlockRendererImpl {
   public static final Function<BlockState, ItemTransform> B2T = state -> {
      Block block = state.getBlock();
      return !(block instanceof FlowerBlock)
            && !(block instanceof SaplingBlock)
            && !(block instanceof WebBlock)
            && !(block instanceof SeagrassBlock)
            && !(block instanceof DoublePlantBlock)
         ? null
         : BlockRendererImpl.T_CROSS;
   };
   public static final ItemTransform T_CUBE = new ItemTransform(new Vector3f(30.0F, 210.0F, 0.0F), new Vector3f(0.925F, -0.8125F, 0.0F), new Vector3f(0.625F));
   public static final ItemTransform T_CROSS = new ItemTransform(new Vector3f(15.0F, 195.0F, 0.0F), new Vector3f(1.0F, -0.95F, 0.0F), new Vector3f(0.8F));
   public static final Set<BlockEntity> TICKED_BE = new HashSet<>();

   public static void onClientTick(Pre event) {
      TICKED_BE.clear();
   }

   public static void render(BlockState blockState, GuiGraphics draw, int x, int y, float delta) {
      PoseStack matrices = draw.pose();
      matrices.pushPose();
      matrices.translate(x, y, 150.0F);
      matrices.mulPose(new Matrix4f().scaling(1.0F, -1.0F, 1.0F));
      matrices.scale(16.0F, 16.0F, 16.0F);
      Level world = Minecraft.getInstance().level;
      LocalPlayer player = Minecraft.getInstance().player;
      BlockPos blockPos = player != null ? player.blockPosition() : BlockPos.ZERO;
      ItemTransform t = B2T.apply(blockState);
      Objects.requireNonNullElse(t, T_CUBE).apply(false, matrices);
      BlockRenderDispatcher brm = Minecraft.getInstance().getBlockRenderer();
      MultiBufferSource vcp = draw.bufferSource();
      VertexConsumer consumer = vcp.getBuffer(Sheets.translucentCullBlockSheet());
      if (blockState.getRenderShape() != RenderShape.INVISIBLE) {
         RandomSource random = RandomSource.create();
         random.setSeed(42L);
         consumer.setLight(15728880);
         brm.renderBatched(blockState, blockPos, world, matrices, consumer, false, random);
      }

      FluidState fluidState = blockState.getFluidState();
      if (!fluidState.isEmpty()) {
      }

      matrices.popPose();
      draw.flush();
   }
}
