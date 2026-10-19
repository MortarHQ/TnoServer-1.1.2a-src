package net.unusual.block_factorys_bosses.item;

import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.unusual.block_factorys_bosses.geckolib.item.UnderworldArenaKeyRenderer;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

@EventBusSubscriber
public class UnderworldArenaKeyItem extends Item implements GeoItem {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public UnderworldArenaKeyItem(Properties properties) {
      super(properties.stacksTo(1).setNoRepair().rarity(Rarity.UNCOMMON));
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
      consumer.accept(new GeoRenderProvider() {
         @Nullable
         private UnderworldArenaKeyRenderer renderer;

         public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
            if (this.renderer == null) {
               this.renderer = new UnderworldArenaKeyRenderer();
            }

            return this.renderer;
         }
      });
   }

   @SubscribeEvent
   public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
      event.registerItem(new UnderworldArenaKeyItem.UnderworldArenaKeyClientExtensions(), new Item[]{(Item)BossesRiseItems.UNDERWORLD_ARENA_KEY.get()});
   }

   public static class UnderworldArenaKeyClientExtensions implements IClientItemExtensions {
      private final GeoRenderProvider renderProvider = new GeoRenderProvider() {
         private UnderworldArenaKeyRenderer renderer;

         public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
            if (this.renderer == null) {
               this.renderer = new UnderworldArenaKeyRenderer();
            }

            return this.renderer;
         }
      };

      public BlockEntityWithoutLevelRenderer getCustomRenderer() {
         return this.renderProvider.getGeoItemRenderer();
      }
   }
}
