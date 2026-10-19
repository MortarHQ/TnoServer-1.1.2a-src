package com.bobmowzie.mowziesmobs.server.potion;

import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class PotionTypeHandler {
   public static final DeferredRegister<Potion> REG = DeferredRegister.create(Registries.POTION, "mowziesmobs");
   public static final DeferredHolder<Potion, Potion> POISON_RESIST = REG.register(
      "poison_resist", () -> new Potion("poison_resist", new MobEffectInstance[]{new MobEffectInstance(EffectHandler.POISON_RESIST, 3600)})
   );
   public static final DeferredHolder<Potion, Potion> LONG_POISON_RESIST = REG.register(
      "long_poison_resist", () -> new Potion("long_poison_resist", new MobEffectInstance[]{new MobEffectInstance(EffectHandler.POISON_RESIST, 9600)})
   );

   private PotionTypeHandler() {
   }

   public static void addMixes(RegisterBrewingRecipesEvent event) {
      event.getBuilder().addMix(Potions.AWKWARD, (Item)ItemHandler.NAGA_FANG.get(), POISON_RESIST);
      event.getBuilder().addMix(POISON_RESIST, Items.REDSTONE, LONG_POISON_RESIST);
   }
}
