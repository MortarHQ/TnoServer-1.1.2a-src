package com.tensura_tno.client;

import com.tensura_tno.client.model.RimuruPlushItemModel;
import com.tensura_tno.client.renderer.FoxSpiritRenderer;
import com.tensura_tno.client.renderer.RimuruPlushItemRenderer;
import com.tensura_tno.client.renderer.RimuruPlushRenderTypes;
import com.tensura_tno.client.renderer.RimuruPlushRenderer;
import com.tensura_tno.registry.TensuraTNOBlockEntities;
import com.tensura_tno.registry.TensuraTNOBlocks;
import com.tensura_tno.registry.TensuraTNOEntities;
import com.tensura_tno.registry.TensuraTNOItems;
import java.io.IOException;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.util.Lazy;

@EventBusSubscriber(modid = "tensura_tno", bus = Bus.MOD, value = Dist.CLIENT)
public final class TensuraTNOClientEvents {
   private TensuraTNOClientEvents() {
   }

   @SubscribeEvent
   public static void onRegisterShaders(RegisterShadersEvent event) throws IOException {
      RimuruPlushRenderTypes.registerShaders(event);
   }

   @SubscribeEvent
   public static void onRegisterRenderers(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)TensuraTNOEntities.FOX_SPIRIT.get(), FoxSpiritRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)TensuraTNOBlockEntities.RIMURU_PLUSH.get(), RimuruPlushRenderer::new);
      event.registerBlockEntityRenderer(
         (BlockEntityType)TensuraTNOBlockEntities.LUMINOUS_PLUSH.get(), context -> new RimuruPlushRenderer(context, "luminous_plush", 0.56F)
      );
   }

   @SubscribeEvent
   public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
      event.registerBlock(new RimuruPlushParticles(), new Block[]{(Block)TensuraTNOBlocks.RIMURU_PLUSH.get()});
      event.registerBlock(new RimuruPlushParticles(Blocks.WHITE_WOOL, Blocks.BLACK_WOOL), new Block[]{(Block)TensuraTNOBlocks.LUMINOUS_PLUSH.get()});
      event.registerItem(
         new IClientItemExtensions() {
            private final Lazy<BlockEntityWithoutLevelRenderer> renderer = Lazy.of(
               () -> new RimuruPlushItemRenderer(new RimuruPlushItemModel("luminous_plush", "animation.luminous."), 0.5F, 11.044, 0.959, 14.044, 1.507)
            );

            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
               return (BlockEntityWithoutLevelRenderer)this.renderer.get();
            }
         },
         new Item[]{(Item)TensuraTNOItems.LUMINOUS_PLUSH.get()}
      );
      event.registerItem(new IClientItemExtensions() {
         private final Lazy<BlockEntityWithoutLevelRenderer> renderer = Lazy.of(RimuruPlushItemRenderer::new);

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return (BlockEntityWithoutLevelRenderer)this.renderer.get();
         }
      }, new Item[]{(Item)TensuraTNOItems.RIMURU_PLUSH.get()});
   }
}
