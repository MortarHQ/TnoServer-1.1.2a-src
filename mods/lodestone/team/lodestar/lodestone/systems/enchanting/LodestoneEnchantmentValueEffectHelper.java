package team.lodestar.lodestone.systems.enchanting;

import com.mojang.datafixers.util.Either;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.item.enchantment.effects.RemoveBinomial;
import org.apache.commons.lang3.mutable.MutableFloat;

public class LodestoneEnchantmentValueEffectHelper {
   private static final RandomSource FAKE_ASS_RANDOM_SOURCE = RandomSource.create();

   public static float getAccurateAttackDamage(LivingEntity entity, ItemStack stack) {
      return getComponentValue(entity, stack, DataComponents.DAMAGE, Attributes.ATTACK_DAMAGE);
   }

   public static float getComponentValue(LivingEntity entity, ItemStack stack, DataComponentType<?> component, Holder<Attribute> base) {
      float baseValue = (float)entity.getAttributeValue(base);
      return getComponentValue(stack, component, baseValue);
   }

   public static float getComponentValue(ItemStack item, DataComponentType<?> component, float baseValue) {
      return getComponentValue(item, null, component, baseValue);
   }

   public static float getComponentValue(ItemStack item, @Nullable Holder<Enchantment> filter, DataComponentType<?> componentType, float baseValue) {
      return getComponentValue(item, item, filter, componentType, baseValue);
   }

   public static float getComponentValue(
      ItemStack enchantmentHolder, ItemStack effectHolder, @Nullable Holder<Enchantment> filter, DataComponentType<?> componentType, float baseValue
   ) {
      MutableFloat mutable = new MutableFloat(baseValue);

      try {
         LodestoneEnchantmentDataHelper.runIterationOnItem(enchantmentHolder, filter, (enchantment, enchantmentLevel) -> {
            DataComponentMap componentMap = ((Enchantment)enchantment.value()).effects();

            for (EnchantmentValueEffect effect : getValueEffects(effectHolder, componentMap, componentType)) {
               processValueEffect(mutable, effect, enchantmentLevel);
            }
         });
      } catch (Exception var7) {
      }

      return mutable.getValue();
   }

   public static List<EnchantmentValueEffect> getValueEffects(ItemStack item, DataComponentMap map, DataComponentType<?> type) {
      return LodestoneEnchantmentDataHelper.getMatchingEffects(
         item, map, EnchantmentValueEffect.class, Either.left((Predicate<TypedDataComponent>)e -> e.type().equals(type))
      );
   }

   public static void processValueEffect(MutableFloat mutable, EnchantmentValueEffect effect, int enchantmentLevel) {
      if (effect instanceof RemoveBinomial removeBinomial) {
         float f = removeBinomial.chance().calculate(enchantmentLevel);
         if (f < 1.0F) {
            mutable.setValue(mutable.getValue() / (1.0F - f));
            return;
         }
      }

      mutable.setValue(effect.process(enchantmentLevel, FAKE_ASS_RANDOM_SOURCE, mutable.getValue()));
   }
}
