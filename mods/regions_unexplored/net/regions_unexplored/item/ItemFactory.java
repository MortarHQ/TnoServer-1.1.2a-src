package net.regions_unexplored.item;

import java.util.function.Function;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public interface ItemFactory extends Function<Properties, Item> {
}
