package mcjty.incontrol.events;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import mcjty.incontrol.ErrorHandler;

public class EventsRule {
   private final EventType eventType;
   private final EventsConditions conditions;
   private final SpawnEventAction action;
   private final PhaseAction phaseAction;
   private final NumberAction numberAction;
   private final CommandAction commandAction;
   private static final Map<String, EventsRule.Cmd> COMMANDS = new HashMap<>();

   private EventsRule(EventsRule.Builder builder) {
      this.conditions = builder.conditions;
      this.action = builder.action;
      this.eventType = builder.eventType;
      this.phaseAction = builder.phaseAction;
      this.numberAction = builder.numberAction;
      this.commandAction = builder.commandAction;
   }

   public static EventsRule.Builder create() {
      return new EventsRule.Builder();
   }

   public static void parse(JsonObject object, EventsRule.Builder builder) {
      EventType.Type type = null;
      JsonObject parameters = null;

      for (String attr : object.keySet()) {
         EventsRule.Cmd cmd = COMMANDS.get(attr);
         if (cmd == null) {
            ErrorHandler.error("Invalid command '" + attr + "' for events rule!");
            return;
         }

         switch (cmd) {
            case ON:
               JsonElement on = object.get("on");
               type = EventType.Type.getType(on.getAsString());
               if (type == null) {
                  ErrorHandler.error("Invalid 'on' value '" + on.getAsString() + "' for events rule!");
                  return;
               }
               break;
            case PARAMETERS:
               parameters = object.getAsJsonObject("parameters");
               break;
            case SPAWN:
               SpawnEventAction actionxxx = SpawnEventAction.parse(object);
               if (actionxxx != null) {
                  builder.action(actionxxx);
               }
               break;
            case PHASE:
               PhaseAction actionxx = PhaseAction.parse(object);
               if (actionxx != null) {
                  builder.action(actionxx);
               }
               break;
            case NUMBER:
               NumberAction actionx = NumberAction.parse(object);
               if (actionx != null) {
                  builder.action(actionx);
               }
               break;
            case CONDITIONS:
               JsonObject conditions = object.getAsJsonObject("conditions");
               EventsConditions.Builder conditionsBuilder = EventsConditions.create();
               EventsConditions.parse(conditions, conditionsBuilder);
               EventsConditions cnd = conditionsBuilder.build();
               cnd.validate();
               builder.conditions(cnd);
               break;
            case COMMAND:
               CommandAction action = CommandAction.parse(object);
               if (action != null) {
                  builder.action(action);
               }
         }
      }

      if (type == null) {
         ErrorHandler.error("No 'on' specified for events rule!");
      } else {
         EventType et;
         switch (type) {
            case MOB_KILLED:
               et = new EventTypeMobKilled();
               if (!et.parse(parameters)) {
                  return;
               }
               break;
            case BLOCK_BROKEN:
               et = new EventTypeBlockBroken();
               if (!et.parse(parameters)) {
                  return;
               }
               break;
            case CUSTOM:
               et = new EventTypeCustom();
               if (!et.parse(parameters)) {
                  return;
               }
               break;
            default:
               ErrorHandler.error("Unknown event type '" + type + "' for events rule!");
               return;
         }

         builder.eventType(et);
      }
   }

   public EventType getEventType() {
      return this.eventType;
   }

   public EventsConditions getConditions() {
      return this.conditions;
   }

   public SpawnEventAction getSpawnAction() {
      return this.action;
   }

   public PhaseAction getPhaseAction() {
      return this.phaseAction;
   }

   public NumberAction getNumberAction() {
      return this.numberAction;
   }

   public CommandAction getCommandAction() {
      return this.commandAction;
   }

   static {
      for (EventsRule.Cmd cmd : EventsRule.Cmd.values()) {
         COMMANDS.put(cmd.name().toLowerCase(), cmd);
      }
   }

   public static class Builder {
      private EventsConditions conditions = EventsConditions.DEFAULT;
      private SpawnEventAction action;
      private PhaseAction phaseAction;
      private NumberAction numberAction;
      private CommandAction commandAction;
      private EventType eventType;

      public EventsRule.Builder conditions(EventsConditions conditions) {
         this.conditions = conditions;
         return this;
      }

      public EventsRule.Builder action(CommandAction commandAction) {
         this.commandAction = commandAction;
         return this;
      }

      public EventsRule.Builder action(SpawnEventAction action) {
         this.action = action;
         return this;
      }

      public EventsRule.Builder action(PhaseAction phaseAction) {
         this.phaseAction = phaseAction;
         return this;
      }

      public EventsRule.Builder action(NumberAction numberAction) {
         this.numberAction = numberAction;
         return this;
      }

      public EventsRule.Builder eventType(EventType eventType) {
         this.eventType = eventType;
         return this;
      }

      public EventsRule build() {
         return new EventsRule(this);
      }
   }

   enum Cmd {
      ON,
      PARAMETERS,
      SPAWN,
      PHASE,
      NUMBER,
      CONDITIONS,
      COMMAND;
   }
}
