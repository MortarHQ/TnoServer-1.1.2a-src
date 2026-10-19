package com.iafenvoy.iceandfire.data;

import com.iafenvoy.iceandfire.item.SeaSerpentScaleItem;
import com.iafenvoy.iceandfire.item.armor.SeaSerpentArmorItem;
import com.iafenvoy.iceandfire.item.block.SeaSerpentScalesBlock;
import com.iafenvoy.iceandfire.registry.IafArmorMaterials;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import com.iafenvoy.iceandfire.registry.IafItems;
import com.iafenvoy.iceandfire.registry.IafRegistries;
import com.iafenvoy.uranus.util.function.MemorizeSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import java.util.List;
import java.util.Locale;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class SeaSerpentType {
   private final String name;
   private final ChatFormatting color;
   public RegistrySupplier<Item> scale;
   public RegistrySupplier<Item> helmet;
   public RegistrySupplier<Item> chestplate;
   public RegistrySupplier<Item> leggings;
   public RegistrySupplier<Item> boots;

   public SeaSerpentType(String name, ChatFormatting color) {
      this.name = name;
      this.color = color;
   }

   public String getName() {
      return this.name;
   }

   public ChatFormatting getColor() {
      return this.color;
   }

   public ResourceLocation getTexture(boolean blink) {
      return ResourceLocation.fromNamespaceAndPath(
         "iceandfire", String.format("textures/entity/seaserpent/seaserpent_%s%s.png", this.name, blink ? "_blink" : "")
      );
   }

   public static List<SeaSerpentType> values() {
      return IafRegistries.SEA_SERPENT_TYPE.stream().toList();
   }

   public static void initArmors() {
      for (SeaSerpentType type : values()) {
         IafBlocks.register(String.format(Locale.ROOT, "sea_serpent_scale_block_%s", type.name), () -> new SeaSerpentScalesBlock(type.name, type.color));
         RegistrySupplier<ArmorMaterial> material = IafArmorMaterials.register(
            String.format(Locale.ROOT, "sea_serpent_scales_%s", type.name),
            new int[]{4, 7, 8, 4},
            25,
            SoundEvents.ARMOR_EQUIP_GOLD,
            2.5F,
            new MemorizeSupplier(() -> Ingredient.of(new ItemLike[]{(ItemLike)type.scale.get()}))
         );
         type.scale = IafItems.registerItem(String.format(Locale.ROOT, "sea_serpent_scales_%s", type.name), () -> new SeaSerpentScaleItem(type));
         type.helmet = IafItems.registerArmor(
            String.format(Locale.ROOT, "tide_%s_helmet", type.name), () -> new SeaSerpentArmorItem(type, material, Type.HELMET)
         );
         type.chestplate = IafItems.registerArmor(
            String.format(Locale.ROOT, "tide_%s_chestplate", type.name), () -> new SeaSerpentArmorItem(type, material, Type.CHESTPLATE)
         );
         type.leggings = IafItems.registerArmor(
            String.format(Locale.ROOT, "tide_%s_leggings", type.name), () -> new SeaSerpentArmorItem(type, material, Type.LEGGINGS)
         );
         type.boots = IafItems.registerArmor(String.format(Locale.ROOT, "tide_%s_boots", type.name), () -> new SeaSerpentArmorItem(type, material, Type.BOOTS));
      }
   }
}
