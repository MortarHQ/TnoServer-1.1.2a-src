package org.openzen.zenscript.codemodel;

import java.util.Arrays;
import java.util.Map;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.ArrayTypeID;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.InferenceBlockingTypeParameterVisitor;
import org.openzen.zenscript.codemodel.type.TypeID;

public class FunctionHeader {
   public final TypeParameter[] typeParameters;
   public final FunctionParameter[] parameters;
   public final TypeID thrownType;
   public final int minParameters;
   public final int maxParameters;
   public final boolean hasUnknowns;
   private TypeID returnType;

   public FunctionHeader(TypeID returnType) {
      if (returnType == null) {
         throw new NullPointerException();
      }

      this.typeParameters = TypeParameter.NONE;
      this.returnType = returnType;
      this.parameters = FunctionParameter.NONE;
      this.thrownType = null;
      this.minParameters = 0;
      this.maxParameters = 0;
      this.hasUnknowns = returnType == BasicTypeID.UNDETERMINED;
   }

   public FunctionHeader(TypeID returnType, TypeID... parameterTypes) {
      if (returnType == null) {
         throw new NullPointerException("The function needs a return type");
      }

      this.typeParameters = TypeParameter.NONE;
      this.returnType = returnType;
      this.parameters = new FunctionParameter[parameterTypes.length];
      this.thrownType = null;

      for (int i = 0; i < parameterTypes.length; i++) {
         this.parameters[i] = new FunctionParameter(parameterTypes[i], null);
      }

      this.minParameters = parameterTypes.length;
      this.maxParameters = parameterTypes.length;
      this.hasUnknowns = hasUnknowns(parameterTypes, returnType);
   }

   public FunctionHeader(TypeID returnType, FunctionParameter... parameters) {
      if (returnType == null) {
         throw new NullPointerException("The function needs a return type");
      }

      this.typeParameters = TypeParameter.NONE;
      this.returnType = returnType;
      this.parameters = parameters;
      this.thrownType = null;
      this.minParameters = getMinParameters(parameters);
      this.maxParameters = getMaxParameters(parameters);
      this.hasUnknowns = hasUnknowns(parameters, returnType);
   }

   public FunctionHeader(TypeParameter[] typeParameters, TypeID returnType, TypeID thrownType, FunctionParameter... parameters) {
      if (returnType == null) {
         throw new NullPointerException();
      }

      if (typeParameters == null) {
         throw new NullPointerException();
      }

      this.typeParameters = typeParameters;
      this.returnType = returnType;
      this.parameters = parameters;
      this.thrownType = thrownType;
      this.minParameters = getMinParameters(parameters);
      this.maxParameters = getMaxParameters(parameters);
      this.hasUnknowns = hasUnknowns(parameters, returnType);
   }

   private static int getMinParameters(FunctionParameter[] parameters) {
      for (int i = 0; i < parameters.length; i++) {
         if (parameters[i].defaultValue != null || parameters[i].variadic) {
            return i;
         }
      }

      return parameters.length;
   }

   private static int getMaxParameters(FunctionParameter[] parameters) {
      if (parameters.length == 0) {
         return 0;
      } else {
         return parameters[parameters.length - 1].variadic ? Integer.MAX_VALUE : parameters.length;
      }
   }

   private static boolean hasUnknowns(TypeID[] types, TypeID returnType) {
      if (returnType == BasicTypeID.UNDETERMINED) {
         return true;
      }

      for (TypeID type : types) {
         if (type == BasicTypeID.UNDETERMINED) {
            return true;
         }
      }

      return false;
   }

   private static boolean hasUnknowns(FunctionParameter[] parameters, TypeID returnType) {
      if (returnType == BasicTypeID.UNDETERMINED) {
         return true;
      }

      for (FunctionParameter parameter : parameters) {
         if (parameter.type == BasicTypeID.UNDETERMINED) {
            return true;
         }
      }

      return false;
   }

   public boolean isVariadic() {
      return this.parameters.length > 0 && this.parameters[this.parameters.length - 1].variadic;
   }

   public boolean isVariadicCall(CallArguments arguments, TypeScope scope) {
      if (!this.isVariadic()) {
         return false;
      } else if (arguments.arguments.length < this.parameters.length - 1) {
         return false;
      } else {
         return arguments.arguments.length != this.parameters.length
            ? true
            : !scope.getTypeMembers(arguments.arguments[arguments.arguments.length - 1].type).canCastImplicit(this.parameters[this.parameters.length - 1].type);
      }
   }

   public boolean isVariadicCall(CallArguments arguments) {
      if (!this.isVariadic()) {
         return false;
      } else if (arguments.arguments.length < this.parameters.length - 1) {
         return false;
      } else {
         return arguments.arguments.length != this.parameters.length
            ? true
            : !arguments.arguments[arguments.arguments.length - 1].type.equals(this.parameters[this.parameters.length - 1].type);
      }
   }

   public boolean[] useTypeParameters() {
      boolean[] useTypeParameters = new boolean[this.typeParameters.length];
      Arrays.fill(useTypeParameters, true);
      return useTypeParameters;
   }

   public TypeID getReturnType() {
      return this.returnType;
   }

   public void setReturnType(TypeID returnType) {
      if (returnType == null) {
         throw new NullPointerException("The function needs a return type");
      }

      this.returnType = returnType;
   }

   public TypeID getParameterType(boolean isVariadic, int index) {
      return this.getParameter(isVariadic, index).type;
   }

   public FunctionParameter getParameter(boolean isVariadic, int index) {
      if (isVariadic && index >= this.parameters.length - 1) {
         FunctionParameter parameter = this.parameters[this.parameters.length - 1];
         return parameter.type instanceof ArrayTypeID ? new FunctionParameter(((ArrayTypeID)parameter.type).elementType, parameter.name) : parameter;
      } else {
         return this.parameters[index];
      }
   }

   public boolean isDenormalized() {
      if (!this.returnType.getNormalized().equals(this.returnType)) {
         return true;
      }

      for (FunctionParameter parameter : this.parameters) {
         if (parameter.type.getNormalized() != parameter.type) {
            return true;
         }
      }

      return false;
   }

   public FunctionHeader normalize(GlobalTypeRegistry registry) {
      if (!this.isDenormalized()) {
         return this;
      }

      FunctionParameter[] normalizedParameters = new FunctionParameter[this.parameters.length];

      for (int i = 0; i < normalizedParameters.length; i++) {
         normalizedParameters[i] = this.parameters[i].normalize(registry);
      }

      return new FunctionHeader(
         this.typeParameters, this.returnType.getNormalized(), this.thrownType == null ? null : this.thrownType.getNormalized(), normalizedParameters
      );
   }

   public int getNumberOfTypeParameters() {
      return this.typeParameters.length;
   }

   public boolean hasAnyDefaultValues() {
      for (FunctionParameter parameter : this.parameters) {
         if (parameter.defaultValue != null) {
            return true;
         }
      }

      return false;
   }

   public FunctionHeader inferFromOverride(GlobalTypeRegistry registry, FunctionHeader overridden) {
      TypeParameter[] resultTypeParameters = this.typeParameters;
      TypeID resultReturnType = this.returnType;
      if (resultReturnType == BasicTypeID.UNDETERMINED) {
         resultReturnType = overridden.returnType;
      }

      TypeID resultThrownType = this.thrownType;
      if (resultThrownType == null && overridden.thrownType != null) {
         resultThrownType = overridden.thrownType;
      }

      FunctionParameter[] resultParameters = Arrays.copyOf(this.parameters, this.parameters.length);

      for (int i = 0; i < resultParameters.length; i++) {
         if (resultParameters[i].type == BasicTypeID.UNDETERMINED) {
            FunctionParameter parameter = resultParameters[i];
            FunctionParameter original = overridden.parameters[i];
            resultParameters[i] = new FunctionParameter(original.type, parameter.name, parameter.defaultValue, original.variadic);
         }
      }

      return new FunctionHeader(resultTypeParameters, resultReturnType, resultThrownType, resultParameters);
   }

   public boolean matchesExactly(CodePosition position, CallArguments arguments, TypeScope scope) {
      if (arguments.arguments.length >= this.minParameters && arguments.arguments.length <= this.maxParameters) {
         FunctionHeader header = this.fillGenericArguments(position, scope, arguments.typeArguments);
         boolean variadicCall = header.isVariadicCall(arguments, scope);

         for (int i = 0; i < arguments.arguments.length; i++) {
            if (!arguments.arguments[i].type.equals(header.getParameterType(variadicCall, i))) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean matchesImplicitly(CodePosition position, CallArguments arguments, TypeScope scope) {
      if (!this.accepts(arguments.arguments.length)) {
         return false;
      }

      FunctionHeader header = this.fillGenericArguments(position, scope, arguments.typeArguments);
      if (this.isVariadic()) {
         boolean matches = true;

         for (int i = 0; i < arguments.arguments.length; i++) {
            if (!scope.getTypeMembers(arguments.arguments[i].type).canCastImplicit(header.getParameterType(true, i))) {
               matches = false;
               break;
            }
         }

         if (matches) {
            return true;
         }
      }

      for (int i = 0; i < arguments.arguments.length; i++) {
         if (!scope.getTypeMembers(arguments.arguments[i].type).canCastImplicit(header.parameters[i].type)) {
            return false;
         }
      }

      return true;
   }

   public String getCanonicalWithoutReturnType() {
      StringBuilder result = new StringBuilder();
      if (this.getNumberOfTypeParameters() > 0) {
         result.append('<');

         for (int i = 0; i < this.typeParameters.length; i++) {
            if (i > 0) {
               result.append(',');
            }

            result.append(this.typeParameters[i].getCanonical());
         }

         result.append('>');
      }

      result.append('(');

      for (int i = 0; i < this.parameters.length; i++) {
         if (i > 0) {
            result.append(',');
         }

         result.append(this.parameters[i].type.toString());
      }

      result.append(')');
      return result.toString();
   }

   public String getCanonical() {
      return this.getCanonicalWithoutReturnType() + this.returnType.toString();
   }

   public boolean hasInferenceBlockingTypeParameters(TypeParameter[] parameters) {
      InferenceBlockingTypeParameterVisitor inferenceBlockingVisitor = new InferenceBlockingTypeParameterVisitor(parameters);

      for (int i = 0; i < this.parameters.length; i++) {
         if (this.parameters[i].type.accept(inferenceBlockingVisitor)) {
            return true;
         }
      }

      return false;
   }

   public boolean accepts(TypeScope scope, Expression... arguments) {
      if (this.parameters.length != arguments.length) {
         return false;
      }

      for (int i = 0; i < arguments.length; i++) {
         if (!scope.getTypeMembers(arguments[i].type).canCastImplicit(this.parameters[i].type)) {
            return false;
         }
      }

      return true;
   }

   public boolean canOverride(TypeScope scope, FunctionHeader other) {
      if (other == null) {
         throw new NullPointerException();
      }

      if (this.parameters.length != other.parameters.length) {
         return false;
      }

      if (this.returnType != BasicTypeID.UNDETERMINED && !scope.getTypeMembers(this.returnType).canCastImplicit(other.returnType)) {
         return false;
      }

      for (int i = 0; i < this.parameters.length; i++) {
         if (this.parameters[i].type != BasicTypeID.UNDETERMINED) {
            if (this.parameters[i].variadic != other.parameters[i].variadic) {
               return false;
            }

            if (!scope.getTypeMembers(other.parameters[i].type).canCastImplicit(this.parameters[i].type)) {
               return false;
            }
         }
      }

      return true;
   }

   public boolean isEquivalentTo(FunctionHeader other) {
      if (this.parameters.length != other.parameters.length) {
         return false;
      }

      for (int i = 0; i < this.parameters.length; i++) {
         if (!this.parameters[i].type.equals(other.parameters[i].type)) {
            return false;
         }
      }

      return true;
   }

   public boolean isSimilarTo(FunctionHeader other) {
      int common = Math.min(this.parameters.length, other.parameters.length);

      for (int i = 0; i < common; i++) {
         if (this.parameters[i].type != other.parameters[i].type) {
            return false;
         }
      }

      for (int i = common; i < this.parameters.length; i++) {
         if (this.parameters[i].defaultValue == null) {
            return false;
         }
      }

      for (int i = common; i < other.parameters.length; i++) {
         if (other.parameters[i].defaultValue == null) {
            return false;
         }
      }

      return true;
   }

   public FunctionHeader instanceForCall(CodePosition position, GlobalTypeRegistry registry, CallArguments arguments) {
      if (arguments.getNumberOfTypeArguments() > 0) {
         Map<TypeParameter, TypeID> typeParameters = TypeID.getMapping(this.typeParameters, arguments.typeArguments);
         return this.instance(new GenericMapper(position, registry, typeParameters));
      } else {
         return this;
      }
   }

   public FunctionHeader withGenericArguments(GenericMapper mapper) {
      if (this.typeParameters.length > 0) {
         mapper = mapper.getInner(mapper.position, mapper.registry, TypeID.getSelfMapping(mapper.registry, this.typeParameters));
      }

      return this.instance(mapper);
   }

   private FunctionHeader instance(GenericMapper mapper) {
      TypeID returnType = this.returnType.instance(mapper);
      FunctionParameter[] parameters = new FunctionParameter[this.parameters.length];

      for (int i = 0; i < parameters.length; i++) {
         parameters[i] = this.parameters[i].withGenericArguments(mapper);
      }

      return new FunctionHeader(this.typeParameters, returnType, this.thrownType == null ? null : this.thrownType.instance(mapper), parameters);
   }

   public FunctionHeader fillGenericArguments(CodePosition position, TypeScope scope, TypeID[] arguments) {
      if (arguments != null && arguments.length != 0) {
         Map<TypeParameter, TypeID> typeArguments = TypeID.getMapping(this.typeParameters, arguments);
         GenericMapper mapper = scope.getLocalTypeParameters().getInner(position, scope.getTypeRegistry(), typeArguments);
         TypeID returnType = this.returnType.instance(mapper);
         FunctionParameter[] parameters = new FunctionParameter[this.parameters.length];

         for (int i = 0; i < parameters.length; i++) {
            parameters[i] = this.parameters[i].withGenericArguments(mapper);
         }

         return new FunctionHeader(TypeParameter.NONE, returnType, this.thrownType == null ? null : this.thrownType.instance(mapper), parameters);
      } else {
         return this;
      }
   }

   public FunctionHeader forTypeParameterInference() {
      return new FunctionHeader(BasicTypeID.UNDETERMINED, this.parameters);
   }

   public FunctionHeader forLambda(FunctionHeader lambdaHeader) {
      FunctionParameter[] parameters = new FunctionParameter[lambdaHeader.parameters.length];

      for (int i = 0; i < lambdaHeader.parameters.length; i++) {
         parameters[i] = new FunctionParameter(this.parameters[i].type, lambdaHeader.parameters[i].name);
      }

      return new FunctionHeader(this.typeParameters, this.returnType, this.thrownType, parameters);
   }

   public FunctionParameter getVariadicParameter() {
      if (this.parameters.length == 0) {
         return null;
      } else {
         return this.parameters[this.parameters.length - 1].variadic ? this.parameters[this.parameters.length - 1] : null;
      }
   }

   public String explainWhyIncompatible(TypeScope scope, CallArguments arguments) {
      if (this.parameters.length != arguments.arguments.length) {
         return this.parameters.length + " parameters expected but " + arguments.arguments.length + " given.";
      }

      if (this.getNumberOfTypeParameters() != arguments.getNumberOfTypeArguments()) {
         return this.getNumberOfTypeParameters() + " type parameters expected but " + arguments.getNumberOfTypeArguments() + " given.";
      }

      for (int i = 0; i < this.parameters.length; i++) {
         if (!scope.getTypeMembers(arguments.arguments[i].type).canCastImplicit(this.parameters[i].type)) {
            return "Parameter " + i + ": cannot cast " + arguments.arguments[i].type + " to " + this.parameters[i].type;
         }
      }

      return "Method should be compatible";
   }

   @Override
   public String toString() {
      StringBuilder result = new StringBuilder();
      if (this.typeParameters.length > 0) {
         result.append("<");

         for (int i = 0; i < this.typeParameters.length; i++) {
            if (i > 0) {
               result.append(", ");
            }

            result.append(this.typeParameters[i].toString());
         }

         result.append(">");
      }

      result.append("(");

      for (int i = 0; i < this.parameters.length; i++) {
         if (i > 0) {
            result.append(", ");
         }

         result.append(this.parameters[i].toString());
      }

      result.append(") as ");
      result.append(this.returnType.toString());
      return result.toString();
   }

   public boolean accepts(int arguments) {
      return arguments >= this.minParameters && arguments <= this.maxParameters;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }

      if (o != null && this.getClass() == o.getClass()) {
         FunctionHeader that = (FunctionHeader)o;
         if (this.minParameters != that.minParameters) {
            return false;
         } else if (this.maxParameters != that.maxParameters) {
            return false;
         } else if (this.hasUnknowns != that.hasUnknowns) {
            return false;
         } else if (!Arrays.equals(this.typeParameters, that.typeParameters)) {
            return false;
         } else if (!this.returnType.equals(that.returnType)) {
            return false;
         } else {
            return !Arrays.equals(this.parameters, that.parameters) ? false : this.thrownType == that.thrownType;
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = Arrays.hashCode(this.typeParameters);
      result = 31 * result + this.returnType.hashCode();
      result = 31 * result + Arrays.hashCode(this.parameters);
      result = 31 * result + (this.thrownType != null ? this.thrownType.hashCode() : 0);
      result = 31 * result + this.minParameters;
      result = 31 * result + this.maxParameters;
      return 31 * result + (this.hasUnknowns ? 1 : 0);
   }
}
