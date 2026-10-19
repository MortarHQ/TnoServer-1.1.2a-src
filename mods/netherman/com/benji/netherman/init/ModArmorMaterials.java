package com.benji.netherman.init;

import com.benji.netherman.NetherExp;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModArmorMaterials {
   public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, "netherman");
   public static final Holder<ArmorMaterial> AZAZEL_MATERIAL = ARMOR_MATERIALS.register(
      "azazel_armor",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.HELMET, 3);
            map.put(Type.CHESTPLATE, 8);
            map.put(Type.LEGGINGS, 6);
            map.put(Type.BOOTS, 3);
         }),
         15,
         SoundEvents.ARMOR_EQUIP_NETHERITE,
         () -> Ingredient.of(new ItemLike[]{Items.NETHERITE_INGOT}),
         List.of(new Layer(NetherExp.location("azazel_armor"))),
         5.0F,
         0.1F
      )
   );

   public static void init(IEventBus bus) {
      ARMOR_MATERIALS.register(bus);
   }
}
