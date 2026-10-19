package de.ellpeck.naturesaura.reg;

import de.ellpeck.naturesaura.items.ModItems;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public enum ModArmorMaterial {
   INFUSED(
      "naturesaura:infused_iron",
      19,
      new int[]{2, 5, 6, 2},
      16,
      SoundEvents.ARMOR_EQUIP_IRON,
      0.0F,
      0.0F,
      () -> Ingredient.of(new ItemLike[]{ModItems.INFUSED_IRON})
   ),
   SKY("naturesaura:sky", 33, new int[]{3, 6, 8, 3}, 12, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, () -> Ingredient.of(new ItemLike[]{ModItems.SKY_INGOT})),
   DEPTH(
      "naturesaura:depth",
      37,
      new int[]{3, 6, 8, 3},
      18,
      SoundEvents.ARMOR_EQUIP_NETHERITE,
      3.0F,
      1.0F,
      () -> Ingredient.of(new ItemLike[]{ModItems.DEPTH_INGOT})
   );

   public final Holder<ArmorMaterial> material;
   private final int maxDamageFactor;

   ModArmorMaterial(
      String nameIn,
      int maxDamageFactor,
      int[] damageReductionAmountsIn,
      int enchantabilityIn,
      Holder<SoundEvent> equipSoundIn,
      float toughness,
      float knockbackResistance,
      Supplier<Ingredient> repairMaterialSupplier
   ) {
      this.maxDamageFactor = maxDamageFactor;
      ResourceLocation res = ResourceLocation.parse(nameIn);
      EnumMap<Type, Integer> defense = new EnumMap<>(Type.class);
      defense.put(Type.BOOTS, damageReductionAmountsIn[0]);
      defense.put(Type.LEGGINGS, damageReductionAmountsIn[1]);
      defense.put(Type.CHESTPLATE, damageReductionAmountsIn[2]);
      defense.put(Type.HELMET, damageReductionAmountsIn[3]);
      List<Layer> layers = List.of(new Layer(res));
      this.material = Registry.registerForHolder(
         BuiltInRegistries.ARMOR_MATERIAL,
         res,
         new ArmorMaterial(defense, enchantabilityIn, equipSoundIn, repairMaterialSupplier, layers, toughness, knockbackResistance)
      );
   }

   public int getDurability(Type type) {
      return type.getDurability(this.maxDamageFactor);
   }
}
