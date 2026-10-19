package net.unusual.block_factorys_bosses.init;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unusual.block_factorys_bosses.BossesRise;

public class BossesRiseArmorMaterials {
   public static final DeferredRegister<ArmorMaterial> REGISTRY = DeferredRegister.create(Registries.ARMOR_MATERIAL, "block_factorys_bosses");
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> DRAGON_ARMOR = REGISTRY.register(
      "dragon_bones",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 3);
            map.put(Type.LEGGINGS, 6);
            map.put(Type.CHESTPLATE, 8);
            map.put(Type.HELMET, 3);
            map.put(Type.BODY, 8);
         }),
         9,
         SoundEvents.ARMOR_EQUIP_NETHERITE,
         () -> Ingredient.of(new ItemStack[]{new ItemStack((ItemLike)BossesRiseItems.DRAGON_BONE.get())}),
         List.of(new Layer(BossesRise.prefix("dragon_armor"))),
         2.0F,
         0.0F
      )
   );
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> KNIGHT_ARMOR = REGISTRY.register(
      "knight", () -> new ArmorMaterial((Map)Util.make(new EnumMap(Type.class), map -> {
         map.put(Type.BOOTS, 2);
         map.put(Type.LEGGINGS, 5);
         map.put(Type.CHESTPLATE, 6);
         map.put(Type.HELMET, 2);
         map.put(Type.BODY, 6);
      }), 9, SoundEvents.ARMOR_EQUIP_NETHERITE, Ingredient::of, List.of(new Layer(BossesRise.prefix("invisible"))), 0.5F, 0.0F)
   );
}
