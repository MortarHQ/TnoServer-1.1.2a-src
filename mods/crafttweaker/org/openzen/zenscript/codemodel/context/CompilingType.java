package org.openzen.zenscript.codemodel.context;

import java.util.List;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;

public interface CompilingType {
   CompilingType getInner(String var1);

   HighLevelDefinition load();

   default DefinitionTypeID getInnerType(GlobalTypeRegistry registry, List<GenericName> name, int index, DefinitionTypeID outer) {
      DefinitionTypeID type = registry.getForDefinition(this.load(), name.get(index).arguments, outer);
      if (++index == name.size()) {
         return type;
      }

      CompilingType innerType = this.getInner(name.get(index).name);
      return innerType == null ? null : innerType.getInnerType(registry, name, index, type);
   }
}
