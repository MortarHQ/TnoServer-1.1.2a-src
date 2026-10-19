package org.anti_ad.mc.ipnext.item.rule.parameter;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KProperty;
import org.anti_ad.mc.common.extensions.AsDelegate;
import org.anti_ad.mc.common.extensions.ByPropertyName;
import org.anti_ad.mc.ipnext.item.rule.ArgumentType;
import org.anti_ad.mc.ipnext.item.rule.Parameter;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nNativeParameters.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NativeParameters.kt\norg/anti_ad/mc/ipnext/item/rule/parameter/NativeParametersKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,128:1\n39#1,2:130\n38#1,5:132\n39#1,2:137\n38#1,5:139\n39#1,2:144\n38#1,5:146\n39#1,2:151\n38#1,5:153\n39#1,2:158\n38#1,5:160\n1#2:129\n*S KotlinDebug\n*F\n+ 1 NativeParameters.kt\norg/anti_ad/mc/ipnext/item/rule/parameter/NativeParametersKt\n*L\n64#1:130,2\n64#1:132,5\n67#1:137,2\n67#1:139,5\n69#1:144,2\n69#1:146,5\n72#1:151,2\n72#1:153,5\n78#1:158,2\n78#1:160,5\n*E\n"
)
public final class NativeParametersKt {
   @NotNull
   private static final ByPropertyName any_identifier = param(IdentifierArgumentType.INSTANCE);
   @NotNull
   private static final ByPropertyName any_string = param(StringArgumentType.INSTANCE);
   @NotNull
   private static final ByPropertyName type_boolean = param(BooleanArgumentType.INSTANCE);
   @NotNull
   private static final ByPropertyName any_nbt = param(NbtArgumentType.INSTANCE);
   @NotNull
   private static final ByPropertyName any_rule = param(RuleArgumentType.INSTANCE);
   @NotNull
   private static final ByPropertyName any_tag_name = param(TagNameArgumentType.INSTANCE);
   @NotNull
   private static final ByPropertyName any_item_name = param(ItemNameArgumentType.INSTANCE);
   @NotNull
   private static final ByPropertyName any_nbt_path = param(NbtPathArgumentType.INSTANCE);
   @NotNull
   private static final Map PARAMETER_MAP = new LinkedHashMap();
   @NotNull
   private static final AsDelegate reverse$delegate;
   @NotNull
   private static final AsDelegate sub_rule$delegate;
   @NotNull
   private static final AsDelegate match$delegate;
   @NotNull
   private static final AsDelegate blank_string$delegate;
   @NotNull
   private static final AsDelegate string_compare$delegate;
   @NotNull
   private static final AsDelegate locale$delegate;
   @NotNull
   private static final AsDelegate strength$delegate;
   @NotNull
   private static final AsDelegate logical$delegate;
   @NotNull
   private static final AsDelegate number_order$delegate;
   @NotNull
   private static final AsDelegate sub_rule_match$delegate;
   @NotNull
   private static final AsDelegate sub_rule_not_match$delegate;
   @NotNull
   private static final AsDelegate nbt$delegate;
   @NotNull
   private static final AsDelegate allow_extra$delegate;
   @NotNull
   private static final AsDelegate require_nbt$delegate;
   @NotNull
   private static final AsDelegate tag_name$delegate;
   @NotNull
   private static final AsDelegate item_name$delegate;
   @NotNull
   private static final AsDelegate nbt_path$delegate;
   @NotNull
   private static final AsDelegate not_found$delegate;
   @NotNull
   private static final AsDelegate component_id$delegate;

   private static final ByPropertyName param(ArgumentType var0) {
      return new ByPropertyName(NativeParametersKt::param$lambda$1);
   }

   @NotNull
   public static final Parameter getReverse() {
      return (Parameter)reverse$delegate.getValue(null, $$delegatedProperties[0]);
   }

   @NotNull
   public static final Parameter getSub_rule() {
      return (Parameter)sub_rule$delegate.getValue(null, $$delegatedProperties[1]);
   }

   @NotNull
   public static final Parameter getMatch() {
      return (Parameter)match$delegate.getValue(null, $$delegatedProperties[2]);
   }

   @NotNull
   public static final Parameter getBlank_string() {
      return (Parameter)blank_string$delegate.getValue(null, $$delegatedProperties[3]);
   }

   @NotNull
   public static final Parameter getString_compare() {
      return (Parameter)string_compare$delegate.getValue(null, $$delegatedProperties[4]);
   }

   @NotNull
   public static final Parameter getLocale() {
      return (Parameter)locale$delegate.getValue(null, $$delegatedProperties[5]);
   }

   @NotNull
   public static final Parameter getStrength() {
      return (Parameter)strength$delegate.getValue(null, $$delegatedProperties[6]);
   }

   @NotNull
   public static final Parameter getLogical() {
      return (Parameter)logical$delegate.getValue(null, $$delegatedProperties[7]);
   }

   @NotNull
   public static final Parameter getNumber_order() {
      return (Parameter)number_order$delegate.getValue(null, $$delegatedProperties[8]);
   }

   @NotNull
   public static final Parameter getSub_rule_match() {
      return (Parameter)sub_rule_match$delegate.getValue(null, $$delegatedProperties[9]);
   }

   @NotNull
   public static final Parameter getSub_rule_not_match() {
      return (Parameter)sub_rule_not_match$delegate.getValue(null, $$delegatedProperties[10]);
   }

   @NotNull
   public static final Parameter getNbt() {
      return (Parameter)nbt$delegate.getValue(null, $$delegatedProperties[11]);
   }

   @NotNull
   public static final Parameter getAllow_extra() {
      return (Parameter)allow_extra$delegate.getValue(null, $$delegatedProperties[12]);
   }

   @NotNull
   public static final Parameter getRequire_nbt() {
      return (Parameter)require_nbt$delegate.getValue(null, $$delegatedProperties[13]);
   }

   @NotNull
   public static final Parameter getTag_name() {
      return (Parameter)tag_name$delegate.getValue(null, $$delegatedProperties[14]);
   }

   @NotNull
   public static final Parameter getItem_name() {
      return (Parameter)item_name$delegate.getValue(null, $$delegatedProperties[15]);
   }

   @NotNull
   public static final Parameter getNbt_path() {
      return (Parameter)nbt_path$delegate.getValue(null, $$delegatedProperties[16]);
   }

   @NotNull
   public static final Parameter getNot_found() {
      return (Parameter)not_found$delegate.getValue(null, $$delegatedProperties[17]);
   }

   @NotNull
   public static final Parameter getComponent_id() {
      return (Parameter)component_id$delegate.getValue(null, $$delegatedProperties[18]);
   }

   private static final Parameter param$lambda$1(ArgumentType var0, ByPropertyName var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Parameter var3;
      Parameter var4 = var3 = new Parameter(var2, var0);
      PARAMETER_MAP.put(var2, var4);
      return var3;
   }

   static {
      KProperty[] var0;
      (var0 = new KProperty[19])[0] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(NativeParametersKt.class, "reverse", "getReverse()Lorg/anti_ad/mc/ipnext/item/rule/Parameter;", 1))
      );
      var0[1] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(NativeParametersKt.class, "sub_rule", "getSub_rule()Lorg/anti_ad/mc/ipnext/item/rule/Parameter;", 1))
      );
      var0[2] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(NativeParametersKt.class, "match", "getMatch()Lorg/anti_ad/mc/ipnext/item/rule/Parameter;", 1))
      );
      var0[3] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(NativeParametersKt.class, "blank_string", "getBlank_string()Lorg/anti_ad/mc/ipnext/item/rule/Parameter;", 1)
         )
      );
      var0[4] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(NativeParametersKt.class, "string_compare", "getString_compare()Lorg/anti_ad/mc/ipnext/item/rule/Parameter;", 1)
         )
      );
      var0[5] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(NativeParametersKt.class, "locale", "getLocale()Lorg/anti_ad/mc/ipnext/item/rule/Parameter;", 1))
      );
      var0[6] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(NativeParametersKt.class, "strength", "getStrength()Lorg/anti_ad/mc/ipnext/item/rule/Parameter;", 1))
      );
      var0[7] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(NativeParametersKt.class, "logical", "getLogical()Lorg/anti_ad/mc/ipnext/item/rule/Parameter;", 1))
      );
      var0[8] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(NativeParametersKt.class, "number_order", "getNumber_order()Lorg/anti_ad/mc/ipnext/item/rule/Parameter;", 1)
         )
      );
      var0[9] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(NativeParametersKt.class, "sub_rule_match", "getSub_rule_match()Lorg/anti_ad/mc/ipnext/item/rule/Parameter;", 1)
         )
      );
      var0[10] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(NativeParametersKt.class, "sub_rule_not_match", "getSub_rule_not_match()Lorg/anti_ad/mc/ipnext/item/rule/Parameter;", 1)
         )
      );
      var0[11] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(NativeParametersKt.class, "nbt", "getNbt()Lorg/anti_ad/mc/ipnext/item/rule/Parameter;", 1))
      );
      var0[12] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(NativeParametersKt.class, "allow_extra", "getAllow_extra()Lorg/anti_ad/mc/ipnext/item/rule/Parameter;", 1)
         )
      );
      var0[13] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(NativeParametersKt.class, "require_nbt", "getRequire_nbt()Lorg/anti_ad/mc/ipnext/item/rule/Parameter;", 1)
         )
      );
      var0[14] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(NativeParametersKt.class, "tag_name", "getTag_name()Lorg/anti_ad/mc/ipnext/item/rule/Parameter;", 1))
      );
      var0[15] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(NativeParametersKt.class, "item_name", "getItem_name()Lorg/anti_ad/mc/ipnext/item/rule/Parameter;", 1))
      );
      var0[16] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(NativeParametersKt.class, "nbt_path", "getNbt_path()Lorg/anti_ad/mc/ipnext/item/rule/Parameter;", 1))
      );
      var0[17] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(NativeParametersKt.class, "not_found", "getNot_found()Lorg/anti_ad/mc/ipnext/item/rule/Parameter;", 1))
      );
      var0[18] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(NativeParametersKt.class, "component_id", "getComponent_id()Lorg/anti_ad/mc/ipnext/item/rule/Parameter;", 1)
         )
      );
      $$delegatedProperties = var0;
      reverse$delegate = type_boolean.provideDelegate(null, $$delegatedProperties[0]);
      sub_rule$delegate = any_rule.provideDelegate(null, $$delegatedProperties[1]);
      match$delegate = param(new EnumArgumentType(Match.class)).provideDelegate(null, $$delegatedProperties[2]);
      blank_string$delegate = param(getMatch().getArgumentType()).provideDelegate(null, $$delegatedProperties[3]);
      string_compare$delegate = param(new EnumArgumentType(StringCompare.class)).provideDelegate(null, $$delegatedProperties[4]);
      locale$delegate = any_string.provideDelegate(null, $$delegatedProperties[5]);
      strength$delegate = param(new EnumArgumentType(Strength.class)).provideDelegate(null, $$delegatedProperties[6]);
      logical$delegate = type_boolean.provideDelegate(null, $$delegatedProperties[7]);
      number_order$delegate = param(new EnumArgumentType(NumberOrder.class)).provideDelegate(null, $$delegatedProperties[8]);
      sub_rule_match$delegate = any_rule.provideDelegate(null, $$delegatedProperties[9]);
      sub_rule_not_match$delegate = any_rule.provideDelegate(null, $$delegatedProperties[10]);
      nbt$delegate = any_nbt.provideDelegate(null, $$delegatedProperties[11]);
      allow_extra$delegate = type_boolean.provideDelegate(null, $$delegatedProperties[12]);
      require_nbt$delegate = param(new EnumArgumentType(RequireNbt.class)).provideDelegate(null, $$delegatedProperties[13]);
      tag_name$delegate = any_tag_name.provideDelegate(null, $$delegatedProperties[14]);
      item_name$delegate = any_item_name.provideDelegate(null, $$delegatedProperties[15]);
      nbt_path$delegate = any_nbt_path.provideDelegate(null, $$delegatedProperties[16]);
      not_found$delegate = param(getMatch().getArgumentType()).provideDelegate(null, $$delegatedProperties[17]);
      component_id$delegate = any_identifier.provideDelegate(null, $$delegatedProperties[18]);
   }
}
