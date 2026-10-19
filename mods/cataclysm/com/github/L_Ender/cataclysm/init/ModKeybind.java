package com.github.L_Ender.cataclysm.init;

import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.KeyMapping;

public class ModKeybind {
   public static final KeyMapping KEY_ABILITY = new KeyMapping("key.cataclysm.ability", Type.KEYSYM, 86, "key.categories.cataclysm");
   public static final KeyMapping HELMET_KEY_ABILITY = new KeyMapping("key.cataclysm.helmet_ability", Type.KEYSYM, 67, "key.categories.cataclysm");
   public static final KeyMapping CHESTPLATE_KEY_ABILITY = new KeyMapping("key.cataclysm.chestplate_ability", Type.KEYSYM, 89, "key.categories.cataclysm");
   public static final KeyMapping BOOTS_KEY_ABILITY = new KeyMapping("key.cataclysm.boots_ability", Type.KEYSYM, 86, "key.categories.cataclysm");
}
