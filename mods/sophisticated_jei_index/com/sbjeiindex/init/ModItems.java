package com.sbjeiindex.init;

import com.sbjeiindex.upgrade.JEIIndexUpgradeItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, "sophisticated_jei_index");
   public static final DeferredHolder<Item, JEIIndexUpgradeItem> JEI_INDEX_UPGRADE = ITEMS.register("jei_index_upgrade", JEIIndexUpgradeItem::new);
}
