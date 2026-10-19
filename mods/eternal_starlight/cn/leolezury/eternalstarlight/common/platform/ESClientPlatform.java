package cn.leolezury.eternalstarlight.common.platform;

import cn.leolezury.eternalstarlight.common.client.ESDimensionSpecialEffects;
import cn.leolezury.eternalstarlight.common.client.resource.BookLoader;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Iterator;
import java.util.ServiceLoader;
import net.minecraft.Util;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.DimensionSpecialEffects.SkyType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface ESClientPlatform {
   ESClientPlatform INSTANCE = (ESClientPlatform)Util.make(() -> {
      ServiceLoader<ESClientPlatform> loader = ServiceLoader.load(ESClientPlatform.class);
      Iterator<ESClientPlatform> iterator = loader.iterator();
      if (!iterator.hasNext()) {
         throw new RuntimeException("Client platform instance not found!");
      } else {
         ESClientPlatform platform = iterator.next();
         if (iterator.hasNext()) {
            throw new RuntimeException("More than one client platform instance was found!");
         } else {
            return platform;
         }
      }
   });

   default BookLoader createBookLoader() {
      return new BookLoader();
   }

   default DimensionSpecialEffects getDimEffect() {
      return new ESDimensionSpecialEffects(160.0F, false, SkyType.NONE, false, false);
   }

   BakedModel getGlowingBakedModel(BakedModel var1);

   default void renderBlock(
      BlockRenderDispatcher dispatcher, PoseStack stack, MultiBufferSource multiBufferSource, Level level, BlockState state, BlockPos pos, long seed
   ) {
      dispatcher.getModelRenderer()
         .tesselateBlock(
            level,
            dispatcher.getBlockModel(state),
            state,
            pos,
            stack,
            multiBufferSource.getBuffer(ItemBlockRenderTypes.getMovingBlockRenderType(state)),
            false,
            RandomSource.create(),
            seed,
            OverlayTexture.NO_OVERLAY
         );
   }

   void sendToServer(CustomPacketPayload var1);
}
