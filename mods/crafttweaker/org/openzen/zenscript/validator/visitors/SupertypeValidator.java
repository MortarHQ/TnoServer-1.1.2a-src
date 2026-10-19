package org.openzen.zenscript.validator.visitors;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.Modifiers;
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
import org.openzen.zenscript.codemodel.type.TypeVisitor;
import org.openzen.zenscript.validator.ValidationLogEntry;
import org.openzen.zenscript.validator.Validator;

public class SupertypeValidator implements TypeVisitor<Void> {
   private final Validator validator;
   private final CodePosition position;
   private final HighLevelDefinition subtype;

   public SupertypeValidator(Validator validator, CodePosition position, HighLevelDefinition subtype) {
      this.validator = validator;
      this.position = position;
      this.subtype = subtype;
   }

   public Void visitBasic(BasicTypeID basic) {
      this.validator.logError(ValidationLogEntry.Code.SUPERCLASS_NOT_A_CLASS, this.position, "Superclass cannot be a basic type");
      return null;
   }

   public Void visitArray(ArrayTypeID array) {
      this.validator.logError(ValidationLogEntry.Code.SUPERCLASS_NOT_A_CLASS, this.position, "Superclass cannot be an array");
      return null;
   }

   public Void visitAssoc(AssocTypeID assoc) {
      this.validator.logError(ValidationLogEntry.Code.SUPERCLASS_NOT_A_CLASS, this.position, "Superclass cannot be an associative array");
      return null;
   }

   public Void visitIterator(IteratorTypeID iterator) {
      this.validator.logError(ValidationLogEntry.Code.SUPERCLASS_NOT_A_CLASS, this.position, "Superclass cannot be an iterator");
      return null;
   }

   public Void visitFunction(FunctionTypeID function) {
      this.validator.logError(ValidationLogEntry.Code.SUPERCLASS_NOT_A_CLASS, this.position, "Superclass cannot be a function");
      return null;
   }

   public Void visitDefinition(DefinitionTypeID definition) {
      if (!Modifiers.isVirtual(definition.definition.modifiers)) {
         this.validator.logError(ValidationLogEntry.Code.INVALID_SUPERTYPE, this.position, "Supertype must be virtual");
      }

      return null;
   }

   public Void visitGeneric(GenericTypeID generic) {
      this.validator.logError(ValidationLogEntry.Code.SUPERCLASS_NOT_A_CLASS, this.position, "Superclass cannot be a type parameter");
      return null;
   }

   public Void visitRange(RangeTypeID range) {
      this.validator.logError(ValidationLogEntry.Code.SUPERCLASS_NOT_A_CLASS, this.position, "Superclass cannot be a range");
      return null;
   }

   public Void visitOptional(OptionalTypeID type) {
      this.validator.logError(ValidationLogEntry.Code.SUPERCLASS_NOT_A_CLASS, this.position, "Superclass cannot be an optional type");
      return null;
   }

   public Void visitGenericMap(GenericMapTypeID map) {
      this.validator.logError(ValidationLogEntry.Code.SUPERCLASS_NOT_A_CLASS, this.position, "Superclass cannot be a generic map");
      return null;
   }
}
