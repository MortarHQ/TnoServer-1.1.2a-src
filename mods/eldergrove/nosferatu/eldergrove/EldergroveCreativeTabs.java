package nosferatu.eldergrove;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EldergroveCreativeTabs {
   public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "eldergrove");
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ELDERGROVE_TAB = CREATIVE_TABS.register(
      "eldergrove",
      () -> CreativeModeTab.builder()
         .title(Component.translatable("itemGroup.eldergrove"))
         .withTabsBefore(new ResourceKey[]{CreativeModeTabs.SPAWN_EGGS})
         .icon(() -> ((BlockItem)EldergroveItems.GROVE_HEART.get()).getDefaultInstance())
         .displayItems((parameters, output) -> {
            output.accept((ItemLike)EldergroveItems.ELDERWOOD_LOG.get());
            output.accept((ItemLike)EldergroveItems.ELDERWOOD_PLANKS.get());
            output.accept((ItemLike)EldergroveItems.ELDERWOOD_STAIRS.get());
            output.accept((ItemLike)EldergroveItems.ELDERWOOD_SLAB.get());
            output.accept((ItemLike)EldergroveItems.ELDERWOOD_LEAVES.get());
            output.accept((ItemLike)EldergroveItems.ELDERWOOD_SAPLING.get());
            output.accept((ItemLike)EldergroveItems.GREATWOOD_LOG.get());
            output.accept((ItemLike)EldergroveItems.GREATWOOD_PLANKS.get());
            output.accept((ItemLike)EldergroveItems.GREATWOOD_STAIRS.get());
            output.accept((ItemLike)EldergroveItems.GREATWOOD_SLAB.get());
            output.accept((ItemLike)EldergroveItems.GREATWOOD_LEAVES.get());
            output.accept((ItemLike)EldergroveItems.GREATWOOD_SAPLING.get());
            output.accept((ItemLike)EldergroveItems.TAINTED_SOIL.get());
            output.accept((ItemLike)EldergroveItems.TAINTED_CRUST.get());
            output.accept((ItemLike)EldergroveItems.TAINTED_ROCK.get());
            output.accept((ItemLike)EldergroveItems.TAINTED_HEART.get());
            output.accept((ItemLike)EldergroveItems.TAINTED_GROWTH.get());
            output.accept((ItemLike)EldergroveItems.TAINTED_TENDRIL.get());
            output.accept((ItemLike)EldergroveItems.TAINTED_FIBRE.get());
            output.accept((ItemLike)EldergroveItems.SHIMMERLEAF.get());
            output.accept((ItemLike)EldergroveItems.VISHROOM.get());
            output.accept((ItemLike)EldergroveItems.GROVE_HEART.get());
         })
         .build()
   );

   private EldergroveCreativeTabs() {
   }

   public static void register(IEventBus modBus) {
      CREATIVE_TABS.register(modBus);
   }
}
