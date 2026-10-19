package org.openzen.zencode.java.module.converters;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.objectweb.asm.Type;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.expression.ExpressionSymbol;
import org.openzen.zenscript.codemodel.expression.StaticGetterExpression;
import org.openzen.zenscript.codemodel.member.FieldMember;
import org.openzen.zenscript.codemodel.member.MethodMember;
import org.openzen.zenscript.codemodel.partial.PartialStaticMemberGroupExpression;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.TypeMembers;
import org.openzen.zenscript.javashared.JavaClass;
import org.openzen.zenscript.javashared.JavaField;

public class JavaNativeGlobalConverter {
   private final JavaNativeTypeConversionContext typeConversionContext;
   private final JavaNativeTypeConverter typeConverter;
   private final JavaNativeMemberConverter memberConverter;

   public JavaNativeGlobalConverter(
      JavaNativeTypeConversionContext typeConversionContext, JavaNativeTypeConverter typeConverter, JavaNativeMemberConverter memberConverter
   ) {
      this.typeConversionContext = typeConversionContext;
      this.typeConverter = typeConverter;
      this.memberConverter = memberConverter;
   }

   public void addGlobal(Class<?> cls, HighLevelDefinition definition) {
      JavaClass jcls;
      if (this.typeConversionContext.compiled.hasClassInfo(definition)) {
         jcls = this.typeConversionContext.compiled.getClassInfo(definition);
      } else {
         jcls = JavaClass.fromInternalName(Type.getInternalName(cls), JavaClass.Kind.CLASS);
         this.typeConversionContext.compiled.setClassInfo(definition, jcls);
      }

      TypeID thisType = this.typeConversionContext.registry.getForMyDefinition(definition);

      for (Field field : cls.getDeclaredFields()) {
         if (field.isAnnotationPresent(ZenCodeGlobals.Global.class) && Modifier.isStatic(field.getModifiers())) {
            ZenCodeGlobals.Global global = field.getAnnotation(ZenCodeGlobals.Global.class);
            TypeID type = this.typeConverter.loadStoredType(this.typeConversionContext.context, field.getAnnotatedType());
            String name = global.value().isEmpty() ? field.getName() : global.value();
            FieldMember fieldMember = new FieldMember(
               CodePosition.NATIVE, definition, 129, name, thisType, type, this.typeConversionContext.registry, 1, 0, null
            );
            definition.addMember(fieldMember);
            JavaField javaField = new JavaField(jcls, field.getName(), Type.getDescriptor(field.getType()));
            this.typeConversionContext.compiled.setFieldInfo(fieldMember, javaField);
            this.typeConversionContext.compiled.setFieldInfo(fieldMember.autoGetter, javaField);
            this.typeConversionContext
               .globals
               .put(
                  name,
                  new ExpressionSymbol(
                     (position, scope) -> new StaticGetterExpression(CodePosition.BUILTIN, fieldMember.autoGetter.ref(thisType, GenericMapper.EMPTY))
                  )
               );
         }
      }

      for (Method method : cls.getDeclaredMethods()) {
         if (method.isAnnotationPresent(ZenCodeGlobals.Global.class) && Modifier.isStatic(method.getModifiers())) {
            ZenCodeGlobals.Global global = method.getAnnotation(ZenCodeGlobals.Global.class);
            String name = global.value().isEmpty() ? method.getName() : global.value();
            MethodMember methodMember = this.memberConverter.asMethod(this.typeConversionContext.context, definition, method, name);
            definition.addMember(methodMember);
            this.typeConversionContext
               .compiled
               .setMethodInfo(
                  methodMember,
                  this.memberConverter
                     .getMethod(jcls, method, this.typeConverter.loadType(this.typeConversionContext.context, method.getAnnotatedReturnType()))
               );
            this.typeConversionContext.globals.put(name, new ExpressionSymbol((position, scope) -> {
               TypeMembers members = scope.getTypeMembers(thisType);
               return new PartialStaticMemberGroupExpression(position, scope, thisType, members.getGroup(name), TypeID.NONE);
            }));
         }
      }
   }
}
