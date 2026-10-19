package com.mafuyu404.oneenoughitem.data;

import cc.sighs.oelib.data.api.DataDriven;
import com.mafuyu404.oneenoughitem.api.DomainRegistry;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@DataDriven(folder = "replacements", syncToClient = true, supportArray = true)
public record Replacements(List<String> match, String result, Optional<Replacements.Rules> rules) {
   public static final Codec<Replacements> CODEC = new Codec<Replacements>() {
      public <T> DataResult<Pair<Replacements, T>> decode(DynamicOps<T> ops, T input) {
         Dynamic<T> dyn = new Dynamic(ops, input);
         return dyn.getMapValues()
            .flatMap(
               map -> {
                  List<String> match = null;
                  String resultStr = null;
                  Optional<Replacements.Rules>[] rulesHolder = new Optional[]{Optional.empty()};

                  for (Entry<Dynamic<T>, Dynamic<T>> entry : map.entrySet()) {
                     String key = (String)entry.getKey().asString().result().orElse(null);
                     if (key != null) {
                        Dynamic<T> valueDyn = entry.getValue();

                        for (String mk : Replacements.matchKeys()) {
                           if (key.equals(mk)) {
                              match = valueDyn.asStreamOpt()
                                 .map(stream -> stream.<String>map(d -> (String)d.asString().result().orElse(null)).filter(Objects::nonNull).toList())
                                 .result()
                                 .orElse(Collections.emptyList());
                              break;
                           }
                        }

                        for (String rk : Replacements.resultKeys()) {
                           if (key.equals(rk)) {
                              resultStr = (String)valueDyn.asString().result().orElse(null);
                              break;
                           }
                        }

                        if ("rules".equals(key)) {
                           Replacements.Rules.CODEC
                              .decode(ops, valueDyn.getValue())
                              .result()
                              .ifPresent(p -> rulesHolder[0] = Optional.of((Replacements.Rules)p.getFirst()));
                        }
                     }
                  }

                  if (match != null && resultStr != null) {
                     Replacements replacements = new Replacements(match, resultStr, rulesHolder[0]);
                     return DataResult.success(Pair.of(replacements, dyn.getValue()));
                  } else {
                     return DataResult.error(() -> "Missing match or result field in Replacements");
                  }
               }
            );
      }

      public <T> DataResult<T> encode(Replacements value, DynamicOps<T> ops, T prefix) {
         try {
            String did = DomainRegistry.currentDataId();
            String mk = "match" + Replacements.cap(did);
            String rk = "result" + Replacements.cap(did);
            Map<T, T> map = new LinkedHashMap<>();
            T matchList = (T)ops.createList(value.match().stream().map(ops::createString));
            map.put((T)ops.createString(mk), matchList);
            map.put((T)ops.createString(rk), (T)ops.createString(value.result()));
            value.rules()
               .flatMap(r -> Replacements.Rules.CODEC.encodeStart(ops, r).result())
               .ifPresent(encodedRules -> map.put((T)ops.createString("rules"), (T)encodedRules));
            return DataResult.success(ops.createMap(map));
         } catch (Exception e) {
            return DataResult.error(() -> "Failed to encode Replacements: " + e.getMessage());
         }
      }
   };

   private static Set<String> matchKeys() {
      return DomainRegistry.all().values().stream().map(a -> "match" + cap(a.dataId())).collect(Collectors.toSet());
   }

   private static Set<String> resultKeys() {
      return DomainRegistry.all().values().stream().map(a -> "result" + cap(a.dataId())).collect(Collectors.toSet());
   }

   private static String cap(String s) {
      return s != null && !s.isEmpty() ? Character.toUpperCase(s.charAt(0)) + s.substring(1) : s;
   }

   public enum ProcessingMode {
      REPLACE,
      RETAIN;

      public static final Codec<Replacements.ProcessingMode> CODEC = Codec.STRING.flatXmap(name -> {
         try {
            return DataResult.success(valueOf(name.toUpperCase()));
         } catch (IllegalArgumentException e) {
            return DataResult.error(() -> "Invalid processing mode: " + name);
         }
      }, mode -> DataResult.success(mode.name().toLowerCase()));
   }

   public record Rules(Optional<Map<String, Replacements.ProcessingMode>> data, Optional<Map<String, Replacements.ProcessingMode>> tag) {
      public static final Codec<Replacements.Rules> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.unboundedMap(Codec.STRING, Replacements.ProcessingMode.CODEC).optionalFieldOf("data").forGetter(Replacements.Rules::data),
               Codec.unboundedMap(Codec.STRING, Replacements.ProcessingMode.CODEC).optionalFieldOf("tag").forGetter(Replacements.Rules::tag)
            )
            .apply(instance, Replacements.Rules::new)
      );
   }
}
