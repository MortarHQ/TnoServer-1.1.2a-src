package org.openzen.zenscript.validator.visitors;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.AccessScope;
import org.openzen.zenscript.codemodel.type.ArrayTypeID;
import org.openzen.zenscript.codemodel.type.AssocTypeID;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.FunctionTypeID;
import org.openzen.zenscript.codemodel.type.GenericMapTypeID;
import org.openzen.zenscript.codemodel.type.GenericTypeID;
import org.openzen.zenscript.codemodel.type.InvalidTypeID;
import org.openzen.zenscript.codemodel.type.IteratorTypeID;
import org.openzen.zenscript.codemodel.type.OptionalTypeID;
import org.openzen.zenscript.codemodel.type.RangeTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.TypeVisitorWithContext;
import org.openzen.zenscript.validator.TypeContext;
import org.openzen.zenscript.validator.ValidationLogEntry;
import org.openzen.zenscript.validator.Validator;

public class TypeValidator implements TypeVisitorWithContext<TypeContext, Void, RuntimeException> {
   private final Validator validator;
   private final CodePosition position;

   public TypeValidator(Validator validator, CodePosition position) {
      this.validator = validator;
      this.position = position;
   }

   public void validate(TypeContext context, TypeID type) {
      type.accept(context, this);
   }

   public Void visitBasic(TypeContext context, BasicTypeID basic) {
      if (basic == BasicTypeID.UNDETERMINED) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_TYPE, this.position, context.display + " could not be determined");
      }

      return null;
   }

   public Void visitArray(TypeContext context, ArrayTypeID array) {
      if (array.dimension < 1) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_TYPE, this.position, "array dimension must be at least 1");
      } else if (array.dimension > 16) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_TYPE, this.position, "array dimension must be at most 16");
      }

      this.validate(context, array.elementType);
      return null;
   }

   public Void visitAssoc(TypeContext context, AssocTypeID assoc) {
      this.validate(context, assoc.keyType);
      this.validate(context, assoc.valueType);
      return null;
   }

   public Void visitInvalid(TypeContext context, InvalidTypeID type) {
      this.validator.logError(ValidationLogEntry.Code.INVALID_TYPE, type.position, type.message);
      return null;
   }

   public Void visitIterator(TypeContext context, IteratorTypeID iterator) {
      for (TypeID type : iterator.iteratorTypes) {
         this.validate(context, type);
      }

      return null;
   }

   public Void visitFunction(TypeContext context, FunctionTypeID function) {
      ValidationUtils.validateHeader(this.validator, this.position, function.header, new AccessScope(null, null));
      return null;
   }

   public Void visitDefinition(TypeContext context, DefinitionTypeID definition) {
      ValidationUtils.validateTypeArguments(this.validator, this.position, definition.definition.typeParameters, definition.typeArguments);
      return null;
   }

   public Void visitGeneric(TypeContext context, GenericTypeID generic) {
      return null;
   }

   public Void visitRange(TypeContext context, RangeTypeID range) {
      this.validate(context, range.baseType);
      return null;
   }

   public Void visitOptional(TypeContext context, OptionalTypeID type) {
      this.validate(context, type.baseType);
      return null;
   }

   public Void visitGenericMap(TypeContext context, GenericMapTypeID map) {
      this.validate(context, map.value);
      return null;
   }
}
