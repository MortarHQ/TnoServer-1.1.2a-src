package org.openzen.zenscript.javashared.types;

import java.lang.reflect.Method;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.type.FunctionTypeID;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.javashared.JavaMethod;
import org.openzen.zenscript.javashared.expressions.JavaFunctionInterfaceCastExpression;

public class JavaFunctionalInterfaceTypeID extends FunctionTypeID {
   public final Method functionalInterfaceMethod;
   public final JavaMethod method;
   private final FunctionTypeID normalized;

   public JavaFunctionalInterfaceTypeID(GlobalTypeRegistry registry, FunctionHeader header, Method functionalInterfaceMethod, JavaMethod method) {
      super(registry, header);
      this.functionalInterfaceMethod = functionalInterfaceMethod;
      this.method = method;
      FunctionHeader normalizedHeader = header.normalize(registry);
      this.normalized = header == normalizedHeader ? this : this.internalizeHeaderChange(registry, normalizedHeader);
   }

   @Override
   public FunctionTypeID getNormalized() {
      return this.normalized;
   }

   @Override
   public TypeID instance(GenericMapper mapper) {
      return this.internalizeHeaderChange(mapper.registry, mapper.map(this.header));
   }

   @Override
   public boolean canCastImplicitTo(TypeID other) {
      return other instanceof FunctionTypeID && ((FunctionTypeID)other).header.isEquivalentTo(this.header);
   }

   @Override
   public boolean canCastImplicitFrom(TypeID other) {
      return other instanceof FunctionTypeID && ((FunctionTypeID)other).header.isEquivalentTo(this.header);
   }

   @Override
   public Expression castImplicitTo(CodePosition position, Expression value, TypeID other) {
      return other instanceof FunctionTypeID otherType && this.header.isEquivalentTo(otherType.header)
         ? new JavaFunctionInterfaceCastExpression(position, otherType, value)
         : null;
   }

   @Override
   public Expression castImplicitFrom(CodePosition position, Expression value) {
      return value.type instanceof FunctionTypeID otherType && this.header.isEquivalentTo(otherType.header)
         ? new JavaFunctionInterfaceCastExpression(position, this, value)
         : null;
   }

   private JavaFunctionalInterfaceTypeID internalizeHeaderChange(GlobalTypeRegistry registry, FunctionHeader header) {
      return registry.internalize(
         JavaFunctionalInterfaceTypeID.class,
         header,
         functionHeader -> new JavaFunctionalInterfaceTypeID(registry, functionHeader, this.functionalInterfaceMethod, this.method)
      );
   }
}
