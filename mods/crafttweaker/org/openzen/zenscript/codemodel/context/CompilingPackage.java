package org.openzen.zenscript.codemodel.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.Module;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;

public class CompilingPackage {
   public final Module module;
   private final ZSPackage pkg;
   private final Map<String, CompilingPackage> packages = new HashMap<>();
   private final Map<String, CompilingType> types = new HashMap<>();

   public CompilingPackage(ZSPackage pkg, Module module) {
      this.pkg = pkg;
      this.module = module;
   }

   public ZSPackage getPackage() {
      return this.pkg;
   }

   public CompilingPackage getOrCreatePackage(String name) {
      if (this.packages.containsKey(name)) {
         return this.packages.get(name);
      }

      CompilingPackage newPackage = new CompilingPackage(this.pkg.getOrCreatePackage(name), this.module);
      this.packages.put(name, newPackage);
      return newPackage;
   }

   public void addPackage(String name, CompilingPackage package_) {
      this.packages.put(name, package_);
   }

   public void addType(String name, CompilingType type) {
      this.types.put(name, type);
   }

   public HighLevelDefinition getImport(TypeResolutionContext context, List<String> name) {
      return this.getImport(context, name, 0);
   }

   private HighLevelDefinition getImport(TypeResolutionContext context, List<String> name, int index) {
      if (this.packages.containsKey(name.get(index))) {
         return this.packages.get(name.get(index)).getImport(context, name, index + 1);
      } else {
         return this.types.containsKey(name.get(index)) ? this.getImportType(context, this.types.get(name.get(index)), name, index + 1) : null;
      }
   }

   private HighLevelDefinition getImportType(TypeResolutionContext context, CompilingType type, List<String> name, int index) {
      return index == name.size() ? type.load() : this.getImportType(context, type.getInner(name.get(index)), name, index + 1);
   }

   public TypeID getType(TypeResolutionContext context, List<GenericName> name) {
      return this.getType(context, name, 0);
   }

   private TypeID getType(TypeResolutionContext context, List<GenericName> name, int index) {
      if (index == name.size()) {
         return null;
      } else if (this.packages.containsKey(name.get(index).name)) {
         return this.packages.get(name.get(index).name).getType(context, name, index + 1);
      } else if (this.types.containsKey(name.get(index).name)) {
         CompilingType type = this.types.get(name.get(index).name);
         DefinitionTypeID result = context.getTypeRegistry().getForDefinition(type.load(), name.get(index).arguments);
         return this.getInner(context, name, index + 1, type, result);
      } else {
         return null;
      }
   }

   private TypeID getInner(TypeResolutionContext context, List<GenericName> name, int index, CompilingType type, DefinitionTypeID result) {
      if (index == name.size()) {
         return result;
      }

      CompilingType innerType = type.getInner(name.get(index).name);
      if (innerType == null) {
         return null;
      }

      DefinitionTypeID innerResult = context.getTypeRegistry().getForDefinition(innerType.load(), name.get(index).arguments, result);
      return this.getInner(context, name, index + 1, innerType, innerResult);
   }
}
