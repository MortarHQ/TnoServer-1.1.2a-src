package com.mafuyu404.oneenoughitem.api;

import com.google.gson.JsonElement;
import com.mafuyu404.oneenoughitem.data.Replacements;
import com.mafuyu404.oneenoughitem.util.MixinUtils;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.server.packs.resources.ResourceManager;

public interface ReplacementStrategy {
   void replaceIdCommon(String var1, MixinUtils.ReplaceContext var2, BiConsumer<String, String> var3, Consumer<JsonElement> var4);

   MixinUtils.ReplacementLoader.CurrentSnapshot loadCurrentSnapshot(ResourceManager var1);

   void parseReplacementJson(JsonElement var1, Map<String, String> var2, Map<String, Replacements.Rules> var3, Map<String, Replacements.Rules> var4);

   MixinUtils.FieldRule getDataDirFieldRule(String var1);

   String resolveTarget(String var1, MixinUtils.ReplaceContext var2);

   boolean isSourceIdWithCurrent(String var1, MixinUtils.ReplaceContext var2);
}
