package com.finderfeed.fdlib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModFileInfo;
import net.neoforged.neoforgespi.language.ModFileScanData;
import net.neoforged.neoforgespi.language.ModFileScanData.AnnotationData;
import net.neoforged.neoforgespi.locating.IModFile;
import org.objectweb.asm.Type;

public class FDHelpers {
   public static final Gson GSON = new GsonBuilder().create();

   public static List<Entity> traceEntities(Level level, Vec3 start, Vec3 end, double boxInflate, Predicate<Entity> predicate) {
      List<Entity> list = level.getEntitiesOfClass(Entity.class, new AABB(start, end).inflate(boxInflate), predicate);
      Iterator<Entity> entityIterator = list.iterator();

      while (entityIterator.hasNext()) {
         Entity e = entityIterator.next();
         AABB box = e.getBoundingBox().inflate(boxInflate);
         Optional<Vec3> opt = box.clip(start, end);
         if (opt.isEmpty()) {
            Optional<Vec3> opt2 = box.clip(end, start);
            if (opt2.isEmpty()) {
               entityIterator.remove();
            }
         }
      }

      return list;
   }

   public static JsonElement readJsonFileFromAssets(ResourceLocation location, String format) {
      ResourceLocation rl = ResourceLocation.tryBuild(location.getNamespace(), location.getPath() + format);

      try {
         byte[] bytes = readFileFromAssets(rl);
         String s = new String(bytes);
         return (JsonElement)GSON.fromJson(s, JsonElement.class);
      } catch (IOException e) {
         e.printStackTrace();
         throw new RuntimeException(e);
      }
   }

   public static JsonElement readJsonFileFromAssets(ResourceLocation location) {
      return readJsonFileFromAssets(location, ".json");
   }

   public static byte[] readFileFromAssets(ResourceLocation rl) throws IOException {
      ModList list = ModList.get();
      String modid = rl.getNamespace();
      String location = rl.getPath();
      IModFileInfo info = list.getModFileById(modid);
      if (info == null) {
         throw new IOException("Cannot locate mod " + modid);
      } else {
         IModFile file = info.getFile();
         Path path = file.findResource(new String[]{"assets", modid, location});
         if (Files.exists(path) && !Files.isDirectory(path)) {
            return Files.readAllBytes(path);
         } else {
            throw new IOException("File not found: " + rl);
         }
      }
   }

   public static List<Field> getAnnotatedFieldsInClass(Class<?> clazz, Class<? extends Annotation> annotationClass) {
      Field[] fields = clazz.getDeclaredFields();
      List<Field> annotated = new ArrayList<>();

      for (Field field : fields) {
         if (field.getAnnotation(annotationClass) != null) {
            annotated.add(field);
         }
      }

      return annotated;
   }

   public static List<Field> getAllAnnotatedFieldsInClass(Class<?> clazz, Class<? extends Annotation> annotationClass) {
      List<Field> fields = new ArrayList<>(getAnnotatedFieldsInClass(clazz, annotationClass));

      for (Class<?> superclass = clazz.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
         fields.addAll(getAnnotatedFieldsInClass(superclass, annotationClass));
      }

      return fields;
   }

   public static List<FDHelpers.ClassFields> collectAllAnnotatedFieldsInClass(Class<?> clazz, Class<? extends Annotation> annotationClass) {
      List<FDHelpers.ClassFields> classFields = new ArrayList<>();
      List<Field> fields = getAnnotatedFieldsInClass(clazz, annotationClass);
      if (!fields.isEmpty()) {
         classFields.add(new FDHelpers.ClassFields(clazz, fields));
      }

      for (Class<?> superclass = clazz.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
         fields = getAnnotatedFieldsInClass(superclass, annotationClass);
         if (!fields.isEmpty()) {
            classFields.add(new FDHelpers.ClassFields(superclass, fields));
         }
      }

      return classFields;
   }

   public static <T> List<Class<?>> getAnnotatedClasses(Class<T> annotationClass) {
      Type type = Type.getType(annotationClass);
      ModList modList = ModList.get();
      List<ModFileScanData> datas = modList.getAllScanData();
      List<Class<?>> classes = new ArrayList<>();

      for (ModFileScanData data : datas) {
         for (AnnotationData adata : data.getAnnotations()) {
            if (adata.annotationType().equals(type)) {
               try {
                  Class<?> clazz = Class.forName(adata.clazz().getClassName());
                  classes.add(clazz);
               } catch (ClassNotFoundException e) {
                  throw new RuntimeException("Unexpected error - class not found: " + adata.clazz().getClassName());
               }
            }
         }
      }

      return classes;
   }

   public record ClassFields(Class<?> owner, List<Field> fields) {
   }
}
