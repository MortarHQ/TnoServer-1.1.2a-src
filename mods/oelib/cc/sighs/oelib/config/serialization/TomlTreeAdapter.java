package cc.sighs.oelib.config.serialization;

import cc.sighs.oelib.config.model.ConfigValueMeta;
import com.electronwill.nightconfig.core.CommentedConfig;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class TomlTreeAdapter {
   private TomlTreeAdapter() {
   }

   public static Object readTree(CommentedConfig config) {
      Map<String, Object> result = new LinkedHashMap<>();

      for (com.electronwill.nightconfig.core.CommentedConfig.Entry entry : config.entrySet()) {
         String key = entry.getKey();
         Object value = entry.getValue();
         if (value instanceof CommentedConfig child) {
            result.put(key, readTree(child));
         } else {
            result.put(key, value);
         }
      }

      return result;
   }

   public static void writeTree(CommentedConfig config, Object tree, Iterable<ConfigValueMeta> fields) {
      if (tree instanceof Map<?, ?> map) {
         writeMap(config, map, "", fields);
      }
   }

   private static void writeMap(CommentedConfig config, Map<?, ?> map, String fullPrefix, Iterable<ConfigValueMeta> fields) {
      for (Entry<?, ?> entry : map.entrySet()) {
         String key = String.valueOf(entry.getKey());
         Object value = entry.getValue();
         String localPath = key;
         String fullPath = fullPrefix.isEmpty() ? key : fullPrefix + "." + key;
         if (value instanceof Map<?, ?> childMap) {
            CommentedConfig child = (CommentedConfig)config.get(localPath);
            if (child == null) {
               child = config.createSubConfig();
               config.set(localPath, child);
            }

            writeMap(child, childMap, fullPath, fields);
         } else if (value instanceof List<?> list) {
            config.set(localPath, list);
         } else {
            config.set(localPath, value);
         }

         for (ConfigValueMeta meta : fields) {
            if (meta.key().equals(fullPath) && meta.comment().isPresent()) {
               config.setComment(localPath, meta.comment().get());
               break;
            }
         }
      }
   }
}
