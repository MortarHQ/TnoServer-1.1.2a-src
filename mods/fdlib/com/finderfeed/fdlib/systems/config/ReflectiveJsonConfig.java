package com.finderfeed.fdlib.systems.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Field;
import java.util.HashMap;
import net.minecraft.resources.ResourceLocation;

public abstract class ReflectiveJsonConfig extends JsonConfig {
   private HashMap<Field, Object> defaultValues = new HashMap<>();

   public ReflectiveJsonConfig(ResourceLocation name) {
      super(name);
   }

   public void memorizeDefaultValues(Class<?> clazz, Object value) {
      for (Field field : clazz.getFields()) {
         if (field.getAnnotation(ConfigValue.class) != null) {
            try {
               Object fieldValue = field.get(value);
               this.defaultValues.put(field, field.get(value));
               this.memorizeDefaultValues(field.getType(), fieldValue);
            } catch (IllegalAccessException e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   @Override
   public boolean parseJson(JsonObject object) {
      return this.processObject(this, object);
   }

   private boolean processObject(Object currentObject, JsonObject json) {
      boolean changesWereMade = false;
      Class<?> clazz = currentObject.getClass();
      Field[] fields = clazz.getFields();
      if (currentObject instanceof ManualSerializeable serializeable) {
         changesWereMade = serializeable.process(json);
      }

      for (Field field : fields) {
         if (field.getAnnotation(ConfigValue.class) != null) {
            Comment comment;
            if ((comment = field.getAnnotation(Comment.class)) != null && !json.has("_comment_" + field.getName())) {
               changesWereMade = true;
               json.addProperty("_comment_" + field.getName(), comment.value());
            }

            try {
               String fieldName = field.getName();
               if (json.has(fieldName)) {
                  changesWereMade = this.processFieldInJson(field, json, currentObject) || changesWereMade;
               } else {
                  changesWereMade = true;
                  this.processFieldNotInJson(field, json, currentObject);
               }
            } catch (IllegalAccessException e) {
               throw new RuntimeException(e);
            }
         }
      }

      return changesWereMade;
   }

   private void processFieldNotInJson(Field field, JsonObject json, Object currentObject) throws IllegalAccessException {
      String fieldName = field.getName();
      Class<?> fieldClass = field.getType();
      if (fieldClass.isEnum()) {
         Object en = this.defaultValues.get(field);
         field.set(currentObject, en);
         json.addProperty(fieldName, en.toString());
      } else if (int.class.isAssignableFrom(fieldClass)) {
         int data = (Integer)this.defaultValues.get(field);
         field.set(currentObject, data);
         json.addProperty(fieldName, data);
      } else if (float.class.isAssignableFrom(fieldClass)) {
         float data = (Float)this.defaultValues.get(field);
         field.set(currentObject, data);
         json.addProperty(fieldName, data);
      } else if (double.class.isAssignableFrom(fieldClass)) {
         double data = (Double)this.defaultValues.get(field);
         field.set(currentObject, data);
         json.addProperty(fieldName, data);
      } else if (String.class.isAssignableFrom(fieldClass)) {
         String data = (String)this.defaultValues.get(field);
         field.set(currentObject, data);
         json.addProperty(fieldName, data);
      } else if (boolean.class.isAssignableFrom(fieldClass)) {
         boolean data = (Boolean)this.defaultValues.get(field);
         field.set(currentObject, data);
         json.addProperty(fieldName, data);
      } else {
         Object data = this.defaultValues.get(field);
         JsonObject object = new JsonObject();
         this.processObject(data, object);
         json.add(fieldName, object);
      }
   }

   private boolean processFieldInJson(Field field, JsonObject json, Object currentObject) throws IllegalAccessException {
      boolean changesWereMade = false;
      String fieldName = field.getName();
      Class<?> fieldClass = field.getType();
      JsonElement element = json.get(fieldName);
      if (fieldClass.isEnum()) {
         String data = element.getAsString();
         Object[] enums = fieldClass.getEnumConstants();
         Object val = null;

         for (Object enumValue : enums) {
            String name = enumValue.toString();
            if (data.equals(name)) {
               val = enumValue;
               break;
            }
         }

         if (val != null) {
            field.set(currentObject, val);
         } else {
            Object defaultValue = this.defaultValues.get(field);
            json.addProperty(fieldName, defaultValue.toString());
            field.set(currentObject, defaultValue);
            changesWereMade = true;
         }
      } else if (int.class.isAssignableFrom(fieldClass)) {
         try {
            int data = element.getAsInt();
            field.set(currentObject, data);
         } catch (Exception e) {
            e.printStackTrace();
            int data = (Integer)this.defaultValues.get(field);
            json.addProperty(fieldName, data);
            field.set(currentObject, data);
            changesWereMade = true;
         }
      } else if (float.class.isAssignableFrom(fieldClass)) {
         try {
            float data = element.getAsFloat();
            field.set(currentObject, data);
         } catch (Exception e) {
            e.printStackTrace();
            float data = (Float)this.defaultValues.get(field);
            field.set(currentObject, data);
            json.addProperty(fieldName, data);
            changesWereMade = true;
         }
      } else if (double.class.isAssignableFrom(fieldClass)) {
         try {
            double data = element.getAsDouble();
            field.set(currentObject, data);
         } catch (Exception e) {
            e.printStackTrace();
            double data = (Double)this.defaultValues.get(field);
            field.set(currentObject, data);
            json.addProperty(fieldName, data);
            changesWereMade = true;
         }
      } else if (String.class.isAssignableFrom(fieldClass)) {
         try {
            String data = element.getAsString();
            field.set(currentObject, data);
         } catch (Exception e) {
            e.printStackTrace();
            String data = (String)this.defaultValues.get(field);
            field.set(currentObject, data);
            json.addProperty(fieldName, data);
            changesWereMade = true;
         }
      } else if (boolean.class.isAssignableFrom(fieldClass)) {
         try {
            boolean data = element.getAsBoolean();
            field.set(currentObject, data);
         } catch (Exception e) {
            e.printStackTrace();
            boolean data = (Boolean)this.defaultValues.get(field);
            field.set(currentObject, data);
            json.addProperty(fieldName, data);
            changesWereMade = true;
         }
      } else {
         Object fieldValue = field.get(currentObject);

         try {
            JsonObject newObject = element.getAsJsonObject();
            changesWereMade = this.processObject(fieldValue, newObject);
         } catch (Exception e) {
            changesWereMade = true;
            JsonObject newObject = new JsonObject();
            this.processObject(fieldValue, newObject);
            json.add(fieldName, newObject);
         }
      }

      return changesWereMade;
   }
}
