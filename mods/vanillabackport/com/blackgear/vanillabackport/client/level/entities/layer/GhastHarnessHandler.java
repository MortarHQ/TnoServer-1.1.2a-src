package com.blackgear.vanillabackport.client.level.entities.layer;

import com.blackgear.vanillabackport.common.registries.ModItems;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GhastHarnessHandler {
   public static final Map<ItemStack, ResourceLocation> HARNESS_EQUIPMENT = new HashMap<>();

   public static void register(ItemStack stack, ResourceLocation texture) {
      HARNESS_EQUIPMENT.put(stack, texture);
   }

   static {
      register(new ItemStack((ItemLike)ModItems.WHITE_HARNESS.get()), ResourceLocation.withDefaultNamespace("textures/entity/ghast/harness/white_harness.png"));
      register(
         new ItemStack((ItemLike)ModItems.ORANGE_HARNESS.get()), ResourceLocation.withDefaultNamespace("textures/entity/ghast/harness/orange_harness.png")
      );
      register(
         new ItemStack((ItemLike)ModItems.MAGENTA_HARNESS.get()), ResourceLocation.withDefaultNamespace("textures/entity/ghast/harness/magenta_harness.png")
      );
      register(
         new ItemStack((ItemLike)ModItems.LIGHT_BLUE_HARNESS.get()),
         ResourceLocation.withDefaultNamespace("textures/entity/ghast/harness/light_blue_harness.png")
      );
      register(
         new ItemStack((ItemLike)ModItems.YELLOW_HARNESS.get()), ResourceLocation.withDefaultNamespace("textures/entity/ghast/harness/yellow_harness.png")
      );
      register(new ItemStack((ItemLike)ModItems.LIME_HARNESS.get()), ResourceLocation.withDefaultNamespace("textures/entity/ghast/harness/lime_harness.png"));
      register(new ItemStack((ItemLike)ModItems.PINK_HARNESS.get()), ResourceLocation.withDefaultNamespace("textures/entity/ghast/harness/pink_harness.png"));
      register(new ItemStack((ItemLike)ModItems.GRAY_HARNESS.get()), ResourceLocation.withDefaultNamespace("textures/entity/ghast/harness/gray_harness.png"));
      register(
         new ItemStack((ItemLike)ModItems.LIGHT_GRAY_HARNESS.get()),
         ResourceLocation.withDefaultNamespace("textures/entity/ghast/harness/light_gray_harness.png")
      );
      register(new ItemStack((ItemLike)ModItems.CYAN_HARNESS.get()), ResourceLocation.withDefaultNamespace("textures/entity/ghast/harness/cyan_harness.png"));
      register(
         new ItemStack((ItemLike)ModItems.PURPLE_HARNESS.get()), ResourceLocation.withDefaultNamespace("textures/entity/ghast/harness/purple_harness.png")
      );
      register(new ItemStack((ItemLike)ModItems.BLUE_HARNESS.get()), ResourceLocation.withDefaultNamespace("textures/entity/ghast/harness/blue_harness.png"));
      register(new ItemStack((ItemLike)ModItems.BROWN_HARNESS.get()), ResourceLocation.withDefaultNamespace("textures/entity/ghast/harness/brown_harness.png"));
      register(new ItemStack((ItemLike)ModItems.GREEN_HARNESS.get()), ResourceLocation.withDefaultNamespace("textures/entity/ghast/harness/green_harness.png"));
      register(new ItemStack((ItemLike)ModItems.RED_HARNESS.get()), ResourceLocation.withDefaultNamespace("textures/entity/ghast/harness/red_harness.png"));
      register(new ItemStack((ItemLike)ModItems.BLACK_HARNESS.get()), ResourceLocation.withDefaultNamespace("textures/entity/ghast/harness/black_harness.png"));
   }
}
