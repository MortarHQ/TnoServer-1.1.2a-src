package org.openzen.zenscript.codemodel.definition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.context.TypeResolutionContext;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.partial.PartialPackageExpression;
import org.openzen.zenscript.codemodel.partial.PartialTypeExpression;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.TypeID;

public class ZSPackage {
   public final String name;
   public final String fullName;
   public final ZSPackage parent;
   private final Map<String, ZSPackage> subPackages = new HashMap<>();
   private final Map<String, HighLevelDefinition> types = new HashMap<>();

   public ZSPackage(ZSPackage parent, String name) {
      this.parent = parent;
      this.name = name;
      this.fullName = parent != null && !parent.fullName.isEmpty() ? parent.fullName + "." + name : name;
   }

   public static ZSPackage createRoot() {
      return new ZSPackage(null, "");
   }

   public void add(String name, ZSPackage subPackage) {
      if (this.subPackages.containsKey(name)) {
         throw new RuntimeException("Such package already exists: " + name);
      }

      this.subPackages.put(name, subPackage);
   }

   public IPartialExpression getMember(CodePosition position, GlobalTypeRegistry registry, GenericName name) throws CompileException {
      if (this.subPackages.containsKey(name.name) && name.hasNoArguments()) {
         return new PartialPackageExpression(position, this.subPackages.get(name.name));
      }

      if (this.types.containsKey(name.name)) {
         if (this.types.get(name.name).typeParameters.length != name.getNumberOfArguments()) {
            throw new CompileException(position, CompileExceptionCode.TYPE_ARGUMENTS_INVALID_NUMBER, "Invalid number of type arguments");
         } else {
            return new PartialTypeExpression(position, registry.getForDefinition(this.types.get(name.name), name.arguments), name.arguments);
         }
      } else {
         return null;
      }
   }

   public boolean contains(String name) {
      return this.types.containsKey(name) || this.subPackages.containsKey(name);
   }

   public HighLevelDefinition getDefinition(String name) {
      return this.types.get(name);
   }

   public HighLevelDefinition getImport(List<String> name, int depth) {
      if (depth >= name.size()) {
         return null;
      } else if (this.subPackages.containsKey(name.get(depth))) {
         return this.subPackages.get(name.get(depth)).getImport(name, depth + 1);
      } else {
         return depth == name.size() - 1 && this.types.containsKey(name.get(depth)) ? this.types.get(name.get(depth)) : null;
      }
   }

   public TypeID getType(CodePosition position, TypeResolutionContext context, List<GenericName> nameParts) {
      return this.getType(position, context, nameParts, 0);
   }

   public TypeID getType(CodePosition position, TypeResolutionContext context, GenericName name) {
      return this.types.containsKey(name.name) ? context.getTypeRegistry().getForDefinition(this.types.get(name.name), name.arguments) : null;
   }

   private TypeID getType(CodePosition position, TypeResolutionContext context, List<GenericName> nameParts, int depth) {
      if (depth >= nameParts.size()) {
         return null;
      } else {
         GenericName name = nameParts.get(depth);
         if (this.subPackages.containsKey(name.name) && name.hasNoArguments()) {
            return this.subPackages.get(name.name).getType(position, context, nameParts, depth + 1);
         } else if (this.types.containsKey(name.name)) {
            DefinitionTypeID type = context.getTypeRegistry().getForDefinition(this.types.get(name.name), name.arguments);
            return GenericName.getInnerType(context.getTypeRegistry(), type, nameParts, depth + 1);
         } else {
            return null;
         }
      }
   }

   public Optional<ZSPackage> getOptionalRecursive(String name) {
      int dot = name.indexOf(46);
      return dot < 0
         ? this.getOptional(name)
         : this.getOptionalRecursive(name.substring(0, dot)).flatMap(pkg -> pkg.getOptionalRecursive(name.substring(dot + 1)));
   }

   public Optional<ZSPackage> getOptional(String name) {
      return Optional.ofNullable(this.subPackages.get(name));
   }

   public ZSPackage getRecursive(String name) {
      int dot = name.indexOf(46);
      return dot < 0 ? this.getOrCreatePackage(name) : this.getOrCreatePackage(name.substring(0, dot)).getRecursive(name.substring(dot + 1));
   }

   public ZSPackage getOrCreatePackage(String name) {
      if (this.subPackages.containsKey(name)) {
         return this.subPackages.get(name);
      }

      ZSPackage result = new ZSPackage(this, name);
      this.subPackages.put(name, result);
      return result;
   }

   public void register(HighLevelDefinition definition) {
      this.types.put(definition.name, definition);
   }

   public ZSPackage getRoot() {
      return this.parent == null ? this : this.parent.getRoot();
   }
}
