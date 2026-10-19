package org.openzen.zenscript.validator;

public enum TypeContext {
   PARAMETER_TYPE("parameter type"),
   RETURN_TYPE("return type"),
   FIELD_TYPE("field type"),
   GETTER_TYPE("getter type"),
   SETTER_TYPE("setter type"),
   CASTER_TYPE("caster type"),
   ITERATOR_TYPE("iterator type"),
   EXPANSION_TARGET_TYPE("expansion target type"),
   OPTION_MEMBER_TYPE("option member type"),
   CAST_TARGET_TYPE("cast target type"),
   TYPE_CHECK_TYPE("type check type"),
   CONSTRUCTOR_TYPE("constructed object type");

   public final String display;

   TypeContext(String display) {
      this.display = display;
   }
}
