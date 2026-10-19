package io.github.Memoires.mysticism.item.weapon;

import io.github.Memoires.mysticism.item.client.RitualScytheRenderer;
import io.github.Memoires.mysticism.registry.item.MysticismWeaponItems;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.item.TensuraToolTiers;
import io.github.manasmods.tensura.item.weapon.SimpleScytheItem;
import io.github.manasmods.tensura.registry.item.misc.TensuraCreativeTabs;
import io.github.manasmods.tensura.storage.TensuraStorages;
import java.util.function.Consumer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;

public class RitualScytheItem extends SimpleScytheItem implements GeoItem {
   private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

   public RitualScytheItem() {
      super(
         TensuraToolTiers.HIGH_MAGISTEEL,
         13,
         -3.0F,
         2.0,
         0.75,
         20.0,
         0.0,
         3,
         -3.2F,
         2.0,
         0.75,
         20.0,
         0.0,
         new Properties().arch$tab(TensuraCreativeTabs.GEARS).durability(2000)
      );
   }

   @NotNull
   public UseAnim getUseAnimation(ItemStack pStack) {
      return UseAnim.BLOCK;
   }

   public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
      if (itemStack.is(MysticismWeaponItems.RITUAL_SCYTHE)) {
         if (attacker instanceof Player player) {
            float cooldown = player.getAttackStrengthScale(0.5F);
            if (cooldown >= 1.0F) {
               TensuraDamageHelper.directSpiritualHurt(target, player, calculateSHPDamage(player));
               itemStack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            } else {
               TensuraDamageHelper.directSpiritualHurt(target, player, calculateSHPDamage(player) / 10.0F);
               player.swing(InteractionHand.MAIN_HAND, true);
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public static boolean isCriticalHit(Player player) {
      return player.fallDistance > 0.0F
         && !player.onGround()
         && !player.onClimbable()
         && !player.isInWater()
         && !player.hasEffect(MobEffects.BLINDNESS)
         && !player.isSprinting();
   }

   public static float calculateSHPDamage(Player player) {
      int soulPoints = TensuraStorages.getExistenceFrom(player).getSoulPoints();
      float progress = Mth.clamp(soulPoints / 5.0E7F, 0.0F, 1.0F);
      return 30.0F + progress * 1470.0F;
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
      consumer.accept(new GeoRenderProvider() {
         private final RitualScytheRenderer renderer = new RitualScytheRenderer();

         @Nullable
         public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
            return this.renderer;
         }
      });
   }
}
