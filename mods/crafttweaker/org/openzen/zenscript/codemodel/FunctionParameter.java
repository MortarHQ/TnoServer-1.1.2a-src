package org.openzen.zenscript.codemodel;

import java.util.Objects;
import org.openzen.zencode.shared.Taggable;
import org.openzen.zenscript.codemodel.annotations.ParameterAnnotation;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.TypeID;

public class FunctionParameter extends Taggable {
   public static final FunctionParameter[] NONE = new FunctionParameter[0];
   public final TypeID type;
   public final String name;
   public final boolean variadic;
   public ParameterAnnotation[] annotations = ParameterAnnotation.NONE;
   public Expression defaultValue;

   public FunctionParameter(TypeID type) {
      this.type = type;
      this.name = "";
      this.defaultValue = null;
      this.variadic = false;
   }

   public FunctionParameter(TypeID type, String name) {
      this.type = type;
      this.name = name;
      this.defaultValue = null;
      this.variadic = false;
   }

   public FunctionParameter(TypeID type, String name, Expression defaultValue, boolean variadic) {
      this.type = type;
      this.name = name;
      this.defaultValue = defaultValue;
      this.variadic = variadic;
   }

   public FunctionParameter(TypeID type, String name, boolean variadic) {
      this(type, name, null, variadic);
   }

   public FunctionParameter normalize(GlobalTypeRegistry registry) {
      FunctionParameter result = new FunctionParameter(this.type.getNormalized(), this.name, this.defaultValue, this.variadic);
      result.annotations = this.annotations;
      return result;
   }

   public FunctionParameter withGenericArguments(GenericMapper mapper) {
      TypeID instanced = this.type.instance(mapper);
      if (Objects.equals(instanced, this.type)) {
         return this;
      }

      FunctionParameter result = new FunctionParameter(instanced, this.name, this.defaultValue, this.variadic);
      result.annotations = this.annotations;
      result.addAllTagsFrom(this);
      return result;
   }

   @Override
   public String toString() {
      return this.name + " as " + this.type.toString();
   }

   @Override
   public int hashCode() {
      int hash = 5;
      hash = 17 * hash + Objects.hashCode(this.type);
      return 17 * hash + (this.variadic ? 1 : 0);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }

      if (obj == null) {
         return false;
      }

      if (this.getClass() != obj.getClass()) {
         return false;
      }

      FunctionParameter other = (FunctionParameter)obj;
      return this.variadic == other.variadic && this.type.equals(other.type);
   }
}
