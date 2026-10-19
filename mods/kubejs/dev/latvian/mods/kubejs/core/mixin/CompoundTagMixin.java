package dev.latvian.mods.kubejs.core.mixin;

import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.NativeJavaMap;
import dev.latvian.mods.rhino.Scriptable;
import dev.latvian.mods.rhino.type.TypeInfo;
import dev.latvian.mods.rhino.util.CustomJavaToJsWrapper;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(CompoundTag.class)
public abstract class CompoundTagMixin implements CustomJavaToJsWrapper {
   @Unique
   private static final TypeInfo KJS$MAP_TYPE = TypeInfo.RAW_MAP.withParams(new TypeInfo[]{TypeInfo.STRING, TypeInfo.of(Tag.class)});
   @Shadow
   @Final
   public Map<String, Tag> tags;

   public Scriptable convertJavaToJs(Context cx, Scriptable scope, TypeInfo target) {
      return new NativeJavaMap(cx, scope, this, this.tags, KJS$MAP_TYPE);
   }
}
