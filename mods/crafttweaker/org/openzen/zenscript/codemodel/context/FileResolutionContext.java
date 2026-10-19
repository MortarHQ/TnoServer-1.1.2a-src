package org.openzen.zenscript.codemodel.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.InvalidTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;

public class FileResolutionContext implements TypeResolutionContext {
   private final ModuleTypeResolutionContext module;
   private final CompilingPackage modulePackage;
   private final Map<String, HighLevelDefinition> imports = new HashMap<>();
   private final ZSPackage root;

   public FileResolutionContext(ModuleTypeResolutionContext module, ZSPackage root, CompilingPackage modulePackage) {
      this.module = module;
      this.root = root;
      this.modulePackage = modulePackage;
   }

   public void addImport(String name, HighLevelDefinition definition) {
      if (definition == null) {
         throw new NullPointerException();
      }

      this.imports.put(name, definition);
   }

   @Override
   public ZSPackage getRootPackage() {
      return this.root;
   }

   @Override
   public GlobalTypeRegistry getTypeRegistry() {
      return this.module.getTypeRegistry();
   }

   @Override
   public AnnotationDefinition getAnnotation(String name) {
      return this.module.getAnnotation(name);
   }

   @Override
   public TypeID getType(CodePosition position, List<GenericName> name) {
      if (this.imports.containsKey(name.get(0).name)) {
         HighLevelDefinition definition = this.imports.get(name.get(0).name);
         return definition.getNumberOfGenericParameters() != name.get(0).getNumberOfArguments()
            ? new InvalidTypeID(position, CompileExceptionCode.INVALID_TYPE_ARGUMENTS, "Invalid number of type arguments")
            : GenericName.getInnerType(this.getTypeRegistry(), this.getTypeRegistry().getForDefinition(definition, name.get(0).arguments), name, 1);
      }

      TypeID typeFromRootAndParents = this.getTypeFromRootAndParents(this.root, position, name);
      if (typeFromRootAndParents != null) {
         return typeFromRootAndParents;
      }

      TypeID moduleType = this.modulePackage.getType(this, name);
      return moduleType != null ? moduleType : this.module.getType(position, name);
   }

   private TypeID getTypeFromRootAndParents(ZSPackage root, CodePosition position, List<GenericName> name) {
      if (root.contains(name.get(0).name)) {
         return root.getType(position, this, name);
      } else if (root.name.equals(name.get(0).name)) {
         return root.getType(position, this, name.subList(1, name.size()));
      } else {
         return root.parent != null ? this.getTypeFromRootAndParents(root.parent, position, name) : null;
      }
   }

   @Override
   public TypeID getThisType() {
      return null;
   }
}
