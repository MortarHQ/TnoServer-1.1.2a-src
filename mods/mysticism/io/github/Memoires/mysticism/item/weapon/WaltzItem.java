package io.github.Memoires.mysticism.item.weapon;

import io.github.Memoires.mysticism.item.client.WaltzRenderer;
import io.github.manasmods.tensura.item.TensuraToolTiers;
import io.github.manasmods.tensura.item.weapon.SimpleLongSwordItem;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.item.misc.TensuraCreativeTabs;
import java.util.function.Consumer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;

public class WaltzItem extends SimpleLongSwordItem implements GeoItem {
   private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

   public WaltzItem() {
      super(TensuraToolTiers.HIHIIROKANE, new Properties().arch$tab(TensuraCreativeTabs.GEARS).durability(30000));
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
      consumer.accept(new GeoRenderProvider() {
         private final WaltzRenderer renderer = new WaltzRenderer();

         @Nullable
         public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
            return this.renderer;
         }
      });
   }

   public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.CHILL), 200, 2, false, true, false));
      return true;
   }
}
