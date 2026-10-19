package com.iafenvoy.uranus.object.item;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

@Deprecated(forRemoval = true)
public abstract class ArmorWithTickItem extends ArmorItem {
   private static final List<ArmorWithTickItem> NEED_TO_TICK = new ArrayList<>();

   public ArmorWithTickItem(Holder<ArmorMaterial> material, Type slot, Properties settings) {
      super(material, slot, settings);
      NEED_TO_TICK.add(this);
   }

   public static void doTick(Level world, Player entity) {
      synchronized (NEED_TO_TICK) {
         for (ArmorWithTickItem item : NEED_TO_TICK) {
            item.onArmorTick(world, entity);
         }
      }
   }

   public abstract void onArmorTick(Level var1, Player var2);
}
