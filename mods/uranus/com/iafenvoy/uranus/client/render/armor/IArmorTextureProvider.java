package com.iafenvoy.uranus.client.render.armor;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorMaterial.Layer;

@Deprecated(forRemoval = true)
public interface IArmorTextureProvider {
   ResourceLocation getArmorTexture(ItemStack var1, Entity var2, EquipmentSlot var3, Layer var4, boolean var5);
}
