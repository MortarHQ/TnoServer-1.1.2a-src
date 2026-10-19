package team.creative.creativecore.common.util.text;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.network.chat.Component;
import team.creative.creativecore.client.render.text.CompiledText;
import team.creative.creativecore.common.util.type.list.TupleList;

public interface IComponentMap<K> {
   Set<Entry<K, List<Component>>> entrySet();

   Collection<List<Component>> values();

   List<K> keys();

   TupleList<K, CompiledText> build();
}
