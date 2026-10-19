package com.blackgear.vanillabackport.common.api.variant;

import java.util.Optional;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.VariantHolder;

public interface VariantDataHolder<T> {
   static <T> VariantDataHolder<T> getHolder(LivingEntity entity) {
      return entity instanceof VariantDataHolder ? (VariantDataHolder)entity : null;
   }

   static <A, B> void trySetOffspringVariant(LivingEntity child, LivingEntity father, LivingEntity mother) {
      RandomSource random = child.getRandom();
      Optional<B> fromFather = getHolder(father).getVariantData();
      Optional<B> fromMother = getHolder(mother).getVariantData();
      Optional<B> dataVariant;
      if (fromFather.isPresent() && fromMother.isPresent()) {
         dataVariant = random.nextBoolean() ? fromFather : fromMother;
      } else if (random.nextBoolean()) {
         dataVariant = fromFather.or(() -> fromMother);
      } else {
         dataVariant = Optional.empty();
      }

      if (dataVariant.isPresent()) {
         dataVariant.ifPresent(variantx -> getHolder(child).setVariantData(variantx));
      } else {
         A variant = (A)(random.nextBoolean() ? ((VariantHolder)father).getVariant() : ((VariantHolder)mother).getVariant());
         ((VariantHolder)child).setVariant(variant);
      }
   }

   Optional<T> getVariantData();

   void setVariantData(T var1);
}
