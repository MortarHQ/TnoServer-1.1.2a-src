package org.openzen.zencode.java.module.converters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import org.objectweb.asm.Type;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.logging.IZSLogger;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.definition.ExpansionDefinition;
import org.openzen.zenscript.codemodel.member.CasterMember;
import org.openzen.zenscript.codemodel.member.GetterMember;
import org.openzen.zenscript.codemodel.member.MethodMember;
import org.openzen.zenscript.codemodel.member.OperatorMember;
import org.openzen.zenscript.codemodel.member.SetterMember;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.javashared.JavaClass;
import org.openzen.zenscript.javashared.JavaMethod;

public class JavaNativeExpansionConverter {
   private final JavaNativeTypeConverter typeConverter;
   private final IZSLogger logger;
   private final JavaNativePackageInfo packageInfo;
   private final JavaNativeMemberConverter memberConverter;
   private final JavaNativeTypeConversionContext typeConversionContext;
   private final JavaNativeHeaderConverter headerConverter;

   public JavaNativeExpansionConverter(
      JavaNativeTypeConverter typeConverter,
      IZSLogger logger,
      JavaNativePackageInfo packageInfo,
      JavaNativeMemberConverter memberConverter,
      JavaNativeTypeConversionContext typeConversionContext,
      JavaNativeHeaderConverter headerConverter
   ) {
      this.typeConverter = typeConverter;
      this.logger = logger;
      this.packageInfo = packageInfo;
      this.memberConverter = memberConverter;
      this.typeConversionContext = typeConversionContext;
      this.headerConverter = headerConverter;
   }

   public ExpansionDefinition convertExpansion(Class<?> cls) {
      if (this.doesClassNotHaveAnnotation(cls)) {
         throw new IllegalArgumentException("Cannot convert class " + cls + " as it does not have an Expansion annotation");
      }

      String expandedName = this.getExpandedName(cls);
      TypeID expandedType = this.typeConverter.getTypeFromName(expandedName);
      if (expandedType == null) {
         throw new IllegalArgumentException("Could not find definition for name " + expandedName);
      }

      ExpansionDefinition expansion = new ExpansionDefinition(CodePosition.NATIVE, this.packageInfo.getModule(), this.packageInfo.getPkg(), 1, null);
      JavaClass javaClass = JavaClass.fromInternalName(Type.getInternalName(cls), JavaClass.Kind.CLASS);
      expansion.target = expandedType;
      this.typeConversionContext.definitionByClass.put(cls, expansion);
      this.fillAnnotatedMethods(cls, expandedType, expansion, javaClass);
      if (!expansion.members.isEmpty()) {
         this.typeConversionContext.compiled.setExpansionClassInfo(expansion, javaClass);
         this.typeConversionContext.packageDefinitions.add(expansion);
      }

      return expansion;
   }

   private void fillAnnotatedMethods(Class<?> cls, TypeID expandedType, ExpansionDefinition expansion, JavaClass javaClass) {
      for (Method method : cls.getDeclaredMethods()) {
         if (Modifier.isStatic(method.getModifiers())) {
            Class<?> classFromType = this.typeConverter.getClassFromType(expandedType);
            if (classFromType == null) {
               this.logger.debug("Could not get class for type " + expandedType + " attempting to do stuff anyways");
            }

            ZenCodeType.Method methodAnnotation = this.getMethodAnnotation(method, ZenCodeType.Method.class);
            if (methodAnnotation != null) {
               this.fillMethod(expansion, javaClass, method, classFromType, methodAnnotation);
            }

            ZenCodeType.StaticExpansionMethod staticExpansionMethod = this.getMethodAnnotation(method, ZenCodeType.StaticExpansionMethod.class);
            if (staticExpansionMethod != null) {
               this.fillStaticMethod(expansion, javaClass, method, staticExpansionMethod);
            }

            ZenCodeType.Getter getterAnnotation = this.getMethodAnnotation(method, ZenCodeType.Getter.class);
            if (getterAnnotation != null) {
               this.fillGetter(expansion, javaClass, method, classFromType, getterAnnotation);
            }

            ZenCodeType.Setter setterAnnotation = this.getMethodAnnotation(method, ZenCodeType.Setter.class);
            if (setterAnnotation != null) {
               this.fillSetter(expansion, javaClass, method, classFromType, setterAnnotation);
            }

            ZenCodeType.Caster casterAnnotation = this.getMethodAnnotation(method, ZenCodeType.Caster.class);
            if (casterAnnotation != null) {
               this.fillCaster(expansion, javaClass, method, classFromType, casterAnnotation);
            }

            ZenCodeType.Operator operatorAnnotation = method.getAnnotation(ZenCodeType.Operator.class);
            if (operatorAnnotation != null) {
               this.fillOperator(expansion, javaClass, method, classFromType, operatorAnnotation);
            }
         }
      }
   }

   private void fillCaster(ExpansionDefinition expansion, JavaClass javaClass, Method method, Class<?> classFromType, ZenCodeType.Caster casterAnnotation) {
      this.checkExpandedType(classFromType, method);
      boolean implicit = casterAnnotation.implicit();
      int modifiers = this.headerConverter.getMethodModifiers(method) ^ 128;
      if (implicit) {
         modifiers |= 512;
      }

      TypeID toType = this.typeConverter.loadStoredType(this.typeConversionContext.context, method.getAnnotatedReturnType());
      CasterMember member = new CasterMember(CodePosition.NATIVE, expansion, modifiers, toType, null);
      expansion.addMember(member);
      this.typeConversionContext.compiled.setMethodInfo(member, this.memberConverter.getMethod(javaClass, method, member.toType));
   }

   private void fillGetter(ExpansionDefinition expansion, JavaClass javaClass, Method method, Class<?> classFromType, ZenCodeType.Getter getterAnnotation) {
      this.checkExpandedType(classFromType, method);
      TypeID type = this.typeConverter.loadStoredType(this.typeConversionContext.context, method.getAnnotatedReturnType());
      int modifiers = this.headerConverter.getMethodModifiers(method) ^ 128;
      String name = getterAnnotation.value().isEmpty() ? this.memberConverter.translateGetterName(method.getName()) : getterAnnotation.value();
      GetterMember member = new GetterMember(CodePosition.NATIVE, expansion, modifiers, name, type, null);
      expansion.addMember(member);
      this.typeConversionContext.compiled.setMethodInfo(member, this.memberConverter.getMethod(javaClass, method, type));
   }

   private void fillSetter(ExpansionDefinition expansion, JavaClass javaClass, Method method, Class<?> classFromType, ZenCodeType.Setter setterAnnotation) {
      this.checkExpandedType(classFromType, method);
      Parameter[] expansionParameters = this.getExpansionParameters(method);
      if (expansionParameters.length != 1) {
         throw new IllegalArgumentException("Cannot add extension setter " + method + " as it does not have a single parameter for the set value!");
      }

      TypeID type = this.typeConverter.loadStoredType(this.typeConversionContext.context, expansionParameters[0]);
      int modifiers = this.headerConverter.getMethodModifiers(method) ^ 128;
      String name = setterAnnotation.value().isEmpty() ? this.memberConverter.translateSetterName(method.getName()) : setterAnnotation.value();
      SetterMember member = new SetterMember(CodePosition.NATIVE, expansion, modifiers, name, type, null);
      expansion.addMember(member);
      this.typeConversionContext.compiled.setMethodInfo(member, this.memberConverter.getMethod(javaClass, method, type));
   }

   private void fillMethod(ExpansionDefinition expansion, JavaClass javaClass, Method method, Class<?> classFromType, ZenCodeType.Method methodAnnotation) {
      this.checkExpandedType(classFromType, method);
      String name = !methodAnnotation.value().isEmpty() ? methodAnnotation.value() : method.getName();
      Parameter[] parameters = this.getExpansionParameters(method);
      FunctionHeader header = this.headerConverter
         .getHeader(
            this.typeConversionContext.context, method.getAnnotatedReturnType(), parameters, method.getTypeParameters(), method.getAnnotatedExceptionTypes()
         );
      MethodMember member = new MethodMember(CodePosition.NATIVE, expansion, this.headerConverter.getMethodModifiers(method) ^ 128, name, header, null);
      expansion.addMember(member);
      this.typeConversionContext
         .compiled
         .setMethodInfo(
            member,
            JavaMethod.getStatic(
               javaClass,
               method.getName(),
               Type.getMethodDescriptor(method),
               this.headerConverter.getMethodModifiers(method),
               member.getHeader().getReturnType().isGeneric()
            )
         );
   }

   private void fillStaticMethod(ExpansionDefinition expansion, JavaClass javaClass, Method method, ZenCodeType.StaticExpansionMethod annotation) {
      String name = !annotation.value().isEmpty() ? annotation.value() : method.getName();
      Parameter[] parameters = method.getParameters();
      FunctionHeader header = this.headerConverter
         .getHeader(
            this.typeConversionContext.context, method.getAnnotatedReturnType(), parameters, method.getTypeParameters(), method.getAnnotatedExceptionTypes()
         );
      MethodMember member = new MethodMember(CodePosition.NATIVE, expansion, this.headerConverter.getMethodModifiers(method), name, header, null);
      expansion.addMember(member);
      this.typeConversionContext
         .compiled
         .setMethodInfo(
            member,
            JavaMethod.getStatic(
               javaClass,
               method.getName(),
               Type.getMethodDescriptor(method),
               this.headerConverter.getMethodModifiers(method),
               header.getReturnType().isGeneric()
            )
         );
   }

   private void fillOperator(ExpansionDefinition expansion, JavaClass javaClass, Method method, Class<?> classFromType, ZenCodeType.Operator operator) {
      this.checkExpandedType(classFromType, method);
      Parameter[] parameters = this.getExpansionParameters(method);
      OperatorType operatorType = this.getOperatorTypeFrom(operator);
      FunctionHeader header = this.headerConverter
         .getHeader(
            this.typeConversionContext.context, method.getAnnotatedReturnType(), parameters, method.getTypeParameters(), method.getAnnotatedExceptionTypes()
         );
      OperatorMember member = new OperatorMember(
         CodePosition.NATIVE, expansion, this.headerConverter.getMethodModifiers(method) ^ 128, operatorType, header, null
      );
      expansion.addMember(member);
      this.typeConversionContext
         .compiled
         .setMethodInfo(
            member,
            JavaMethod.getStatic(
               javaClass,
               method.getName(),
               Type.getMethodDescriptor(method),
               this.headerConverter.getMethodModifiers(method),
               header.getReturnType().isGeneric()
            )
         );
   }

   private OperatorType getOperatorTypeFrom(ZenCodeType.Operator operator) {
      switch (operator.value()) {
         case ADD:
            return OperatorType.ADD;
         case SUB:
            return OperatorType.SUB;
         case MUL:
            return OperatorType.MUL;
         case DIV:
            return OperatorType.DIV;
         case MOD:
            return OperatorType.MOD;
         case CAT:
            return OperatorType.CAT;
         case OR:
            return OperatorType.OR;
         case AND:
            return OperatorType.AND;
         case XOR:
            return OperatorType.XOR;
         case NEG:
            return OperatorType.NEG;
         case INVERT:
            return OperatorType.INVERT;
         case NOT:
            return OperatorType.NOT;
         case INDEXSET:
            return OperatorType.INDEXSET;
         case INDEXGET:
            return OperatorType.INDEXGET;
         case CONTAINS:
            return OperatorType.CONTAINS;
         case COMPARE:
            return OperatorType.COMPARE;
         case MEMBERGETTER:
            return OperatorType.MEMBERGETTER;
         case MEMBERSETTER:
            return OperatorType.MEMBERSETTER;
         case EQUALS:
            return OperatorType.EQUALS;
         case NOTEQUALS:
            return OperatorType.NOTEQUALS;
         case SHL:
            return OperatorType.SHL;
         case SHR:
            return OperatorType.SHR;
         case ADDASSIGN:
            return OperatorType.ADDASSIGN;
         case SUBASSIGN:
            return OperatorType.SUBASSIGN;
         case MULASSIGN:
            return OperatorType.MULASSIGN;
         case DIVASSIGN:
            return OperatorType.DIVASSIGN;
         case MODASSIGN:
            return OperatorType.MODASSIGN;
         case CATASSIGN:
            return OperatorType.CATASSIGN;
         case ORASSIGN:
            return OperatorType.ORASSIGN;
         case ANDASSIGN:
            return OperatorType.ANDASSIGN;
         case XORASSIGN:
            return OperatorType.XORASSIGN;
         case SHLASSIGN:
            return OperatorType.SHLASSIGN;
         case SHRASSIGN:
            return OperatorType.SHRASSIGN;
         default:
            throw new IllegalArgumentException("Unknown OperatorType: " + operator.value());
      }
   }

   protected String getExpandedName(Class<?> cls) {
      return cls.getAnnotation(ZenCodeType.Expansion.class).value();
   }

   protected boolean doesClassNotHaveAnnotation(Class<?> cls) {
      return !cls.isAnnotationPresent(ZenCodeType.Expansion.class);
   }

   private void checkExpandedType(Class<?> clsType, Method method) {
      if (method.getParameterTypes().length < 1) {
         throw new IllegalArgumentException("Cannot add extension method " + method + " as it does not have any parameters and is a normal expansion.");
      }

      if (clsType != null) {
         if (!method.getParameterTypes()[0].isAssignableFrom(clsType)) {
            throw new IllegalArgumentException("Cannot add extension method " + method + " as its first parameter does not match the extended type.");
         }
      }
   }

   private Parameter[] getExpansionParameters(Method method) {
      Parameter[] parameters = new Parameter[method.getParameterCount() - 1];
      System.arraycopy(method.getParameters(), 1, parameters, 0, method.getParameterCount() - 1);
      return parameters;
   }

   protected <T extends Annotation> T getMethodAnnotation(Method method, Class<T> annotationClass) {
      return method.getAnnotation(annotationClass);
   }
}
