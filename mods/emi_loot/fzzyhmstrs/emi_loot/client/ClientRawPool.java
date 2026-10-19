package fzzyhmstrs.emi_loot.client;

import fzzyhmstrs.emi_loot.util.TextKey;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import java.util.List;
import java.util.Map;
import net.minecraft.world.item.ItemStack;

public record ClientRawPool(Map<List<TextKey>, Object2FloatMap<ItemStack>> map) {
}
