package fzzyhmstrs.emi_loot.client;

import fzzyhmstrs.emi_loot.util.ConditionalStack;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Tuple;

public record ClientBuiltPool(List<Tuple<Integer, Component>> conditions, List<ConditionalStack> stacks) {
}
