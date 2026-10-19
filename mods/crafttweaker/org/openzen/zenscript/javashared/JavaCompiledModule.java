package org.openzen.zenscript.javashared;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.Module;
import org.openzen.zenscript.codemodel.definition.ExpansionDefinition;
import org.openzen.zenscript.codemodel.definition.VariantDefinition;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.member.ImplementationMember;
import org.openzen.zenscript.codemodel.member.ref.DefinitionMemberRef;
import org.openzen.zenscript.codemodel.type.member.BuiltinID;

public class JavaCompiledModule {
   public final Module module;
   public final FunctionParameter[] scriptParameters;
   private final Map<HighLevelDefinition, JavaClass> classes = new HashMap<>();
   private final Map<HighLevelDefinition, JavaClass> expansionClasses = new HashMap<>();
   private final Map<HighLevelDefinition, JavaNativeClass> nativeClasses = new HashMap<>();
   private final Map<ImplementationMember, JavaImplementation> implementations = new HashMap<>();
   private final Map<IDefinitionMember, JavaField> fields = new HashMap<>();
   private final Map<IDefinitionMember, JavaMethod> methods = new HashMap<>();
   private final Map<TypeParameter, JavaTypeParameterInfo> typeParameters = new HashMap<>();
   private final Map<FunctionParameter, JavaParameterInfo> parameters = new IdentityHashMap<>();
   private final Map<VariantDefinition.Option, JavaVariantOption> variantOptions = new HashMap<>();
   private final JavaEnumMapper enumMapper = new JavaEnumMapper();

   public JavaCompiledModule(Module module, FunctionParameter[] scriptParameters) {
      this.module = module;
      this.scriptParameters = scriptParameters;
   }

   public void loadMappings(String mappings) {
   }

   public String generateMappings() {
      JavaMappingWriter writer = new JavaMappingWriter(this);

      for (HighLevelDefinition definition : this.classes.keySet()) {
         if (!(definition instanceof ExpansionDefinition)) {
            definition.accept(writer);
         }
      }

      for (HighLevelDefinition definition : this.expansionClasses.keySet()) {
         definition.accept(writer);
      }

      return writer.getOutput();
   }

   public void setClassInfo(HighLevelDefinition definition, JavaClass cls) {
      this.classes.put(definition, cls);
   }

   public void setExpansionClassInfo(HighLevelDefinition definition, JavaClass cls) {
      this.expansionClasses.put(definition, cls);
   }

   public JavaClass getClassInfo(HighLevelDefinition definition) {
      JavaClass cls = this.classes.get(definition);
      if (cls == null) {
         throw new IllegalStateException("Missing class info for class " + definition.name);
      } else {
         return cls;
      }
   }

   public JavaClass getExpansionClassInfo(HighLevelDefinition definition) {
      JavaClass cls = this.expansionClasses.get(definition);
      return cls == null ? this.getClassInfo(definition) : cls;
   }

   public JavaClass optClassInfo(HighLevelDefinition definition) {
      return this.classes.get(definition);
   }

   public boolean hasClassInfo(HighLevelDefinition definition) {
      return this.classes.containsKey(definition);
   }

   public void setNativeClassInfo(HighLevelDefinition definition, JavaNativeClass cls) {
      this.nativeClasses.put(definition, cls);
   }

   public JavaNativeClass getNativeClassInfo(HighLevelDefinition definition) {
      return this.nativeClasses.get(definition);
   }

   public void setVariantOption(VariantDefinition.Option option, JavaVariantOption value) {
      this.variantOptions.put(option, value);
   }

   public JavaVariantOption getVariantOption(VariantDefinition.Option option) {
      JavaVariantOption result = this.variantOptions.get(option);
      if (result == null) {
         throw new IllegalStateException("Variant option unknown: " + option.name);
      } else {
         return result;
      }
   }

   public JavaEnumMapper getEnumMapper() {
      return this.enumMapper;
   }

   public void setImplementationInfo(ImplementationMember member, JavaImplementation implementation) {
      this.implementations.put(member, implementation);
   }

   public JavaImplementation getImplementationInfo(ImplementationMember member) {
      JavaImplementation implementation = this.implementations.get(member);
      if (implementation == null) {
         throw new IllegalStateException("Implementation unknown: " + member.type);
      } else {
         return implementation;
      }
   }

   public void setFieldInfo(IDefinitionMember member, JavaField field) {
      this.fields.put(member, field);
   }

   public JavaField optFieldInfo(IDefinitionMember member) {
      return this.fields.get(member);
   }

   public JavaField getFieldInfo(IDefinitionMember member) {
      JavaField field = this.fields.get(member);
      if (field == null) {
         throw new IllegalStateException("Missing field info for field " + member.getDefinition().name + "." + member.describe());
      } else {
         return field;
      }
   }

   public void setMethodInfo(IDefinitionMember member, JavaMethod method) {
      this.methods.put(member, method);
   }

   public JavaMethod optMethodInfo(IDefinitionMember member) {
      return this.methods.get(member);
   }

   public JavaMethod getMethodInfo(DefinitionMemberRef member) {
      return this.getMethodInfo(member.getTarget());
   }

   public JavaMethod getMethodInfo(IDefinitionMember member) {
      JavaMethod method = this.methods.get(member);
      if (member.getBuiltin() == BuiltinID.CLASS_DEFAULT_CONSTRUCTOR) {
         return new JavaMethod(this.getClassInfo(member.getDefinition()), JavaMethod.Kind.CONSTRUCTOR, "<init>", true, "()V", 1, false);
      } else if (method == null) {
         throw new IllegalStateException("Missing method info for method " + member.getDefinition().name + "." + member.describe());
      } else {
         return method;
      }
   }

   public void setTypeParameterInfo(TypeParameter parameter, JavaTypeParameterInfo info) {
      this.typeParameters.put(parameter, info);
   }

   public JavaTypeParameterInfo getTypeParameterInfo(TypeParameter parameter) {
      JavaTypeParameterInfo info = this.typeParameters.get(parameter);
      if (info == null) {
         throw new IllegalStateException("Missing parameter info for type parameter " + parameter);
      } else {
         return info;
      }
   }

   public void setParameterInfo(FunctionParameter parameter, JavaParameterInfo info) {
      this.parameters.put(parameter, info);
   }

   public JavaParameterInfo getParameterInfo(FunctionParameter parameter) {
      JavaParameterInfo info = this.parameters.get(parameter);
      if (info == null) {
         throw new IllegalStateException("Missing parameter info for parameter " + parameter.name);
      } else {
         return info;
      }
   }

   public void addAllFrom(JavaCompiledModule compiled) {
      this.classes.putAll(compiled.classes);
      this.expansionClasses.putAll(compiled.expansionClasses);
      this.nativeClasses.putAll(compiled.nativeClasses);
      this.implementations.putAll(compiled.implementations);
      this.fields.putAll(compiled.fields);
      this.methods.putAll(compiled.methods);
      this.typeParameters.putAll(compiled.typeParameters);
      this.parameters.putAll(compiled.parameters);
      this.variantOptions.putAll(compiled.variantOptions);
      this.enumMapper.merge(compiled.enumMapper);
   }

   public List<ExpansionDefinition> getExpansions() {
      return this.expansionClasses
         .keySet()
         .stream()
         .filter(definition -> definition instanceof ExpansionDefinition)
         .map(definition -> (ExpansionDefinition)definition)
         .collect(Collectors.toList());
   }
}
