package com.tensura_tno.client;

import com.tensura_tno.registry.TensuraTNOBackpacks;
import com.tensura_tno.registry.TensuraTNOBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Block;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackBlockEntity;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.client.render.BackpackItemStackRenderer;
import net.p3pp3rf1y.sophisticatedcore.util.WorldHelper;

@EventBusSubscriber(modid = "tensura_tno", bus = Bus.MOD, value = Dist.CLIENT)
public final class TensuraTNOBackpackClientEvents {
   private TensuraTNOBackpackClientEvents() {
   }

   @SubscribeEvent
   public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
      event.registerItem(
         new IClientItemExtensions() {
            private final Lazy<BlockEntityWithoutLevelRenderer> ister = Lazy.of(
               () -> new BackpackItemStackRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels())
            );

            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
               return (BlockEntityWithoutLevelRenderer)this.ister.get();
            }
         },
         new Item[]{
            (Item)TensuraTNOBackpacks.LOW_MAGISTEEL_BACKPACK.get(),
            (Item)TensuraTNOBackpacks.HIGH_MAGISTEEL_BACKPACK.get(),
            (Item)TensuraTNOBackpacks.PURE_MAGISTEEL_BACKPACK.get(),
            (Item)TensuraTNOBackpacks.ADAMANTITE_BACKPACK.get(),
            (Item)TensuraTNOBackpacks.HIHIIROKANE_BACKPACK.get()
         }
      );
   }

   @SubscribeEvent
   public static void registerItemColorHandlers(net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Item event) {
      event.register(
         (backpack, layer) -> {
            if (layer <= 1 && backpack.getItem() instanceof BackpackItem) {
               IBackpackWrapper backpackWrapper = BackpackWrapper.fromStack(backpack);
               if (layer == 0) {
                  return backpackWrapper.getMainColor();
               } else {
                  return layer == 1 ? backpackWrapper.getAccentColor() : -1;
               }
            } else {
               return -1;
            }
         },
         new ItemLike[]{
            (ItemLike)TensuraTNOBackpacks.LOW_MAGISTEEL_BACKPACK.get(),
            (ItemLike)TensuraTNOBackpacks.HIGH_MAGISTEEL_BACKPACK.get(),
            (ItemLike)TensuraTNOBackpacks.PURE_MAGISTEEL_BACKPACK.get(),
            (ItemLike)TensuraTNOBackpacks.ADAMANTITE_BACKPACK.get(),
            (ItemLike)TensuraTNOBackpacks.HIHIIROKANE_BACKPACK.get()
         }
      );
   }

   @SubscribeEvent
   public static void registerBlockColorHandlers(Block event) {
      event.register(
         (state, blockDisplayReader, pos, tintIndex) -> tintIndex >= 0 && tintIndex <= 1 && pos != null
            ? WorldHelper.getBlockEntity(blockDisplayReader, pos, BackpackBlockEntity.class)
               .map(be -> tintIndex == 0 ? be.getBackpackWrapper().getMainColor() : be.getBackpackWrapper().getAccentColor())
               .orElse(tintIndex == 0 ? -3382982 : -10342886)
            : -1,
         new net.minecraft.world.level.block.Block[]{
            (net.minecraft.world.level.block.Block)TensuraTNOBlocks.LOW_MAGISTEEL_BACKPACK.get(),
            (net.minecraft.world.level.block.Block)TensuraTNOBlocks.HIGH_MAGISTEEL_BACKPACK.get(),
            (net.minecraft.world.level.block.Block)TensuraTNOBlocks.PURE_MAGISTEEL_BACKPACK.get(),
            (net.minecraft.world.level.block.Block)TensuraTNOBlocks.ADAMANTITE_BACKPACK.get(),
            (net.minecraft.world.level.block.Block)TensuraTNOBlocks.HIHIIROKANE_BACKPACK.get()
         }
      );
   }
}
