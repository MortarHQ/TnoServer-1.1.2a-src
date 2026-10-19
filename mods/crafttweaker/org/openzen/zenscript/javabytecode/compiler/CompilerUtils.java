package org.openzen.zenscript.javabytecode.compiler;

import java.util.List;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.Modifiers;
import org.openzen.zenscript.codemodel.expression.switchvalue.SwitchValue;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.member.FieldMember;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.javabytecode.JavaBytecodeContext;
import org.openzen.zenscript.javashared.JavaCompiledModule;
import org.openzen.zenscript.javashared.JavaField;
import org.openzen.zenscript.javashared.JavaParameterInfo;
import org.openzen.zenscript.javashared.JavaTypeParameterInfo;

public class CompilerUtils {
   private CompilerUtils() {
   }

   public static boolean isPrimitive(TypeID id) {
      return id instanceof BasicTypeID ? id != BasicTypeID.STRING : id.isOptional() && id.withoutOptional() == BasicTypeID.USIZE;
   }

   public static boolean isLarge(TypeID type) {
      return type == BasicTypeID.DOUBLE || type == BasicTypeID.LONG || type == BasicTypeID.ULONG;
   }

   public static int calcAccess(int modifiers) {
      int out = 0;
      if (Modifiers.isStatic(modifiers)) {
         out |= 8;
      }

      if (Modifiers.isFinal(modifiers)) {
         out |= 16;
      }

      if (Modifiers.isPublic(modifiers)) {
         out |= 1;
      }

      if (Modifiers.isPrivate(modifiers)) {
         out |= 2;
      }

      if (Modifiers.isProtected(modifiers)) {
         out |= 4;
      }

      if (Modifiers.isAbstract(modifiers)) {
         out |= 1024;
      }

      return out;
   }

   public static void tagMethodParameters(
      JavaBytecodeContext context, JavaCompiledModule module, FunctionHeader header, boolean isStatic, List<TypeParameter> baseTypeTypeParameters
   ) {
      int index = isStatic ? 0 : 1;

      for (TypeParameter baseTypeTypeParameter : baseTypeTypeParameters) {
         module.setTypeParameterInfo(baseTypeTypeParameter, new JavaTypeParameterInfo(index));
         index++;
      }

      for (int i = 0; i < header.typeParameters.length; i++) {
         TypeParameter parameter = header.typeParameters[i];
         module.setTypeParameterInfo(parameter, new JavaTypeParameterInfo(index));
         index++;
      }

      for (int i = 0; i < header.parameters.length; i++) {
         FunctionParameter parameter = header.parameters[i];
         String parameterType = context.getDescriptor(parameter.type);
         module.setParameterInfo(parameter, new JavaParameterInfo(index, parameterType));
         index += isLarge(parameter.type) ? 2 : 1;
      }
   }

   public static void tagConstructorParameters(
      JavaBytecodeContext context, JavaCompiledModule module, HighLevelDefinition definition, FunctionHeader header, boolean isEnum
   ) {
      int index = isEnum ? 3 : 1;

      for (TypeParameter typeParameter : definition.typeParameters) {
         JavaField field = new JavaField(
            context.getJavaClass(definition), "typeOf" + typeParameter.name, "Ljava/lang/Class;", "Ljava/lang/Class<T" + typeParameter.name + ";>;"
         );
         JavaTypeParameterInfo info = new JavaTypeParameterInfo(index, field);
         module.setTypeParameterInfo(typeParameter, info);
         index++;
      }

      for (int i = 0; i < header.typeParameters.length; i++) {
         TypeParameter typeParameter = header.typeParameters[i];
         JavaField field = new JavaField(
            context.getJavaClass(definition), "typeOf" + typeParameter.name, "Ljava/lang/Class;", "Ljava/lang/Class<T" + typeParameter.name + ";>;"
         );
         JavaTypeParameterInfo info = new JavaTypeParameterInfo(index, field);
         module.setTypeParameterInfo(typeParameter, info);
         index++;
      }

      for (int i = 0; i < header.parameters.length; i++) {
         FunctionParameter parameter = header.parameters[i];
         String parameterType = context.getDescriptor(parameter.type);
         module.setParameterInfo(parameter, new JavaParameterInfo(index, parameterType));
         index += isLarge(parameter.type) ? 2 : 1;
      }
   }

   public static void writeDefaultFieldInitializers(
      JavaBytecodeContext context, JavaWriter constructorWriter, HighLevelDefinition definition, boolean staticFields
   ) {
      JavaExpressionVisitor expressionVisitor = new JavaExpressionVisitor(context, context.getJavaModule(definition.module), constructorWriter);

      for (IDefinitionMember definitionMember : definition.members) {
         if (definitionMember instanceof FieldMember field && field.isStatic() == staticFields && field.initializer != null) {
            if (!staticFields) {
               constructorWriter.loadObject(0);
            }

            field.initializer.accept(expressionVisitor);
            if (staticFields) {
               constructorWriter.putStaticField(context.getJavaClass(definition).internalName, field.name, context.getDescriptor(field.getType()));
            } else {
               constructorWriter.putField(definition.name, field.name, context.getDescriptor(field.getType()));
            }
         }
      }
   }

   public static int getKeyForSwitch(SwitchValue expression) {
      return expression.accept(JavaSwitchKeyVisitor.INSTANCE);
   }
}
