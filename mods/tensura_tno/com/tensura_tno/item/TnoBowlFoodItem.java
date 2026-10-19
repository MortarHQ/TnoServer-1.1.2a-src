package com.tensura_tno.item;

import java.util.Optional;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item.Properties;

public class TnoBowlFoodItem extends Item {
   public TnoBowlFoodItem(FoodProperties food) {
      super(
         new Properties()
            .stacksTo(16)
            .food(
               new FoodProperties(
                  food.nutrition(), food.saturation(), food.canAlwaysEat(), food.eatSeconds(), Optional.of(Items.BOWL.getDefaultInstance()), food.effects()
               )
            )
      );
   }
}
