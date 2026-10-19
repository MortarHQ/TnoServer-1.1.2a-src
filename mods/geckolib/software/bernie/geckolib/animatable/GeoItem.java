package software.bernie.geckolib.animatable;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.GeckoLibConstants;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.ContextAwareAnimatableManager;
import software.bernie.geckolib.cache.AnimatableIdCache;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.util.RenderUtil;

public interface GeoItem extends SingletonGeoAnimatable {
   static void registerSyncedAnimatable(GeoAnimatable animatable) {
      SingletonGeoAnimatable.registerSyncedAnimatable(animatable);
   }

   static long getId(ItemStack stack) {
      return Optional.ofNullable(stack.getComponentsPatch().get(GeckoLibConstants.STACK_ANIMATABLE_ID_COMPONENT.get()))
         .filter(Optional::isPresent)
         .map(Optional::get)
         .orElse(Long.MAX_VALUE);
   }

   static long getOrAssignId(ItemStack stack, ServerLevel level) {
      if (stack.getComponents() instanceof PatchedDataComponentMap components) {
         Long id = (Long)components.get(GeckoLibConstants.STACK_ANIMATABLE_ID_COMPONENT.get());
         if (id == null) {
            components.set(GeckoLibConstants.STACK_ANIMATABLE_ID_COMPONENT.get(), id = AnimatableIdCache.getFreeId(level));
         }

         return id;
      } else {
         return Long.MAX_VALUE;
      }
   }

   @Override
   default double getTick(Object itemStack) {
      return RenderUtil.getCurrentTick();
   }

   default boolean isPerspectiveAware() {
      return false;
   }

   @Nullable
   @Override
   default AnimatableInstanceCache animatableCacheOverride() {
      return this.isPerspectiveAware() ? new GeoItem.ContextBasedAnimatableInstanceCache(this) : SingletonGeoAnimatable.super.animatableCacheOverride();
   }

   class ContextBasedAnimatableInstanceCache extends SingletonAnimatableInstanceCache {
      public ContextBasedAnimatableInstanceCache(GeoAnimatable animatable) {
         super(animatable);
      }

      @Override
      public AnimatableManager<?> getManagerForId(long uniqueId) {
         if (!this.managers.containsKey(uniqueId)) {
            this.managers.put(uniqueId, new ContextAwareAnimatableManager<GeoItem, ItemDisplayContext>(this.animatable) {
               @Override
               protected Map<ItemDisplayContext, AnimatableManager<GeoItem>> buildContextOptions(GeoAnimatable animatable) {
                  Map<ItemDisplayContext, AnimatableManager<GeoItem>> map = new EnumMap<>(ItemDisplayContext.class);

                  for (ItemDisplayContext context : ItemDisplayContext.values()) {
                     map.put(context, new AnimatableManager<>(animatable));
                  }

                  return map;
               }

               public ItemDisplayContext getCurrentContext() {
                  ItemDisplayContext context = this.getData(DataTickets.ITEM_RENDER_PERSPECTIVE);
                  return context == null ? ItemDisplayContext.NONE : context;
               }
            });
         }

         return (AnimatableManager<?>)this.managers.get(uniqueId);
      }
   }
}
