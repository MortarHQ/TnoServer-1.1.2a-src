package org.openzen.zenscript.javashared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.generic.GenericParameterBoundVisitor;
import org.openzen.zenscript.codemodel.generic.ParameterSuperBound;
import org.openzen.zenscript.codemodel.generic.ParameterTypeBound;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.generic.TypeParameterBound;
import org.openzen.zenscript.codemodel.type.ArrayTypeID;
import org.openzen.zenscript.codemodel.type.AssocTypeID;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.FunctionTypeID;
import org.openzen.zenscript.codemodel.type.GenericMapTypeID;
import org.openzen.zenscript.codemodel.type.GenericTypeID;
import org.openzen.zenscript.codemodel.type.IteratorTypeID;
import org.openzen.zenscript.codemodel.type.OptionalTypeID;
import org.openzen.zenscript.codemodel.type.RangeTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.TypeVisitor;

public class JavaTypeGenericVisitor implements TypeVisitor<String> {
   private final JavaContext context;

   public JavaTypeGenericVisitor(JavaContext context) {
      this.context = context;
   }

   public String getGenericSignature(TypeID... types) {
      if (types != null && types.length != 0) {
         StringBuilder builder = new StringBuilder();

         for (TypeID type : types) {
            builder.append(type.accept(this));
         }

         return builder.toString();
      } else {
         return "";
      }
   }

   public String getGenericSignature(TypeParameter... parameters) {
      if (parameters != null && parameters.length != 0) {
         StringBuilder builder = new StringBuilder();

         for (TypeParameter parameter : parameters) {
            builder.append(parameter.name).append(":").append(this.getGenericBounds(parameter.bounds));
         }

         return builder.toString();
      } else {
         return "";
      }
   }

   public String getSignatureWithBound(TypeID type) {
      if (type instanceof GenericTypeID) {
         TypeParameter parameter = ((GenericTypeID)type).parameter;
         return parameter.name + ":" + this.getGenericBounds(parameter.bounds);
      } else {
         throw new IllegalStateException("Type " + type + " is of the wrong class");
      }
   }

   private String getGenericSignature(FunctionParameter... parameters) {
      if (parameters != null && parameters.length != 0) {
         StringBuilder builder = new StringBuilder();

         for (FunctionParameter parameter : parameters) {
            builder.append(parameter.type.accept(this));
         }

         return builder.toString();
      } else {
         return "";
      }
   }

   public String getGenericMethodSignature(FunctionHeader header) {
      return "(" + this.getGenericSignature(header.parameters) + ")" + this.getGenericSignature(header.getReturnType());
   }

   public String getGenericMethodSignature(FunctionHeader header, boolean addGenerics) {
      StringBuilder sb = new StringBuilder();
      boolean doGenerics = addGenerics && header.typeParameters.length > 0;
      if (doGenerics) {
         sb.append("<");

         for (TypeParameter typeParameter : header.typeParameters) {
            sb.append(typeParameter.name).append(":").append("Ljava/lang/Object;");
         }

         sb.append(">");
      }

      sb.append("(");
      if (doGenerics) {
         for (TypeParameter typeParameter : header.typeParameters) {
            sb.append("Ljava/lang/Class<T").append(typeParameter.name).append(";>;");
         }
      }

      sb.append(this.getGenericSignature(header.parameters));
      sb.append(")");
      sb.append(this.getGenericSignature(header.getReturnType()));
      return sb.toString();
   }

   public String getGenericBounds(Collection<TypeParameterBound> collection) {
      if (collection == null) {
         return "";
      }

      for (TypeParameterBound parameterBound : collection) {
         String s = parameterBound.accept(new GenericParameterBoundVisitor<String>() {
            public String visitSuper(ParameterSuperBound bound) {
               return null;
            }

            public String visitType(ParameterTypeBound bound) {
               return bound.type.accept(JavaTypeGenericVisitor.this);
            }
         });
         if (s != null) {
            return s;
         }
      }

      return "Ljava/lang/Object;";
   }

   public String visitBasic(BasicTypeID basic) {
      return this.context.getDescriptor(basic);
   }

   public String visitArray(ArrayTypeID array) {
      char[] dim = new char[array.dimension];
      Arrays.fill(dim, '[');
      return new String(dim) + this.context.getSignature(array.elementType);
   }

   public String visitAssoc(AssocTypeID assoc) {
      return "Ljava/util/Map<" + assoc.keyType.accept(this) + assoc.valueType.accept(this) + ">;";
   }

   public String visitGenericMap(GenericMapTypeID map) {
      return this.context.getDescriptor(map);
   }

   public String visitIterator(IteratorTypeID iterator) {
      return this.context.getDescriptor(iterator);
   }

   public String visitFunction(FunctionTypeID function) {
      JavaSynthesizedFunctionInstance function1 = this.context.getFunction(function);
      if (function1.typeArguments != null && function1.typeArguments.length != 0) {
         StringBuilder sb = new StringBuilder("L").append(function1.getCls().internalName).append("<");

         for (TypeID typeArgument : function1.typeArguments) {
            String n = typeArgument instanceof GenericTypeID ? ((GenericTypeID)typeArgument).parameter.name : "Ljava/lang/Object";
            sb.append("T").append(n).append(";");
         }

         return sb.append(">;").toString();
      } else {
         return this.context.getDescriptor(function);
      }
   }

   public String visitDefinition(DefinitionTypeID definition) {
      JavaClass cls = this.context.getJavaClass(definition.definition);
      StringBuilder builder = new StringBuilder("L").append(cls.internalName);
      if (definition.typeArguments.length > 0) {
         builder.append("<");

         for (TypeID typeParameter : definition.typeArguments) {
            builder.append(typeParameter.accept(this));
         }

         builder.append(">");
      }

      return builder.append(";").toString();
   }

   public String visitGeneric(GenericTypeID generic) {
      return "T" + generic.parameter.name + ";";
   }

   public String visitRange(RangeTypeID range) {
      return this.context.getDescriptor(range);
   }

   public String visitOptional(OptionalTypeID type) {
      return type.baseType.accept(this);
   }

   public String getMethodSignatureExpansion(FunctionHeader header, TypeID expandedClass) {
      StringBuilder stringBuilder = new StringBuilder();
      ArrayList<TypeParameter> typeParameters = new ArrayList<>();
      expandedClass.extractTypeParameters(typeParameters);

      for (TypeParameter typeParameter : header.typeParameters) {
         if (!typeParameters.contains(typeParameter)) {
            typeParameters.add(typeParameter);
         }
      }

      if (typeParameters.size() != 0) {
         stringBuilder.append("<");

         for (TypeParameter typeParameter : typeParameters) {
            stringBuilder.append(typeParameter.name);
            stringBuilder.append(":Ljava/lang/Object;");
         }

         stringBuilder.append(">");
      }

      stringBuilder.append("(");
      stringBuilder.append(this.context.getSignature(expandedClass));

      for (TypeParameter typeParameter : typeParameters) {
         stringBuilder.append("Ljava/lang/Class<T");
         stringBuilder.append(typeParameter.name);
         stringBuilder.append(";>;");
      }

      stringBuilder.append(this.getGenericSignature(header.parameters));
      stringBuilder.append(")");
      stringBuilder.append(this.context.getSignature(header.getReturnType()));
      return stringBuilder.toString();
   }
}
