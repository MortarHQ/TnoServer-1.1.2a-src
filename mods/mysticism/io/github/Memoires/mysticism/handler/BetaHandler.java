package io.github.Memoires.mysticism.handler;

import com.google.common.collect.ImmutableSet;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.PlayerEvent.PlayerJoin;

public class BetaHandler {
   private static final boolean released = true;
   public static final ImmutableSet<String> allowedUUIDs = ImmutableSet.of(
      "380df991-f603-344c-a090-369bad2a924a",
      "b39db5e2-8850-45f4-b24c-00983237e928",
      "bd8e5ba9-f1b5-412e-91f1-68d817683ddd",
      "f6df9042-af19-33b5-a3c7-1b67e5af02b0",
      "0acdcb0d-0c24-471e-b285-9ed151438b2a",
      "7cf5776d-ff75-4213-9690-1842127f84d0",
      new String[]{
         "72ef84c2-aad5-4441-87b5-9a78d47eb161",
         "bdeb5073-48b5-4800-8709-419520e68e60",
         "df268eb6-c076-4566-918c-3ce657f60f36",
         "dfcaacbe-26d5-4077-be07-1120ffcf2222",
         "ca6b2aea-4d68-43f2-b1d6-baf1865bb870",
         "a6ac928a-725b-45eb-8b96-f428be616b68",
         "459717c9-f6bb-4ce8-93ae-6edd626baca9",
         "c019b8ab-7511-48cf-864b-12f4d602ef42",
         "2aec7db3-a26a-4d78-ac60-6be420606e62",
         "c9e8adce-bc65-4d0d-90eb-c8c9c9fa0940",
         "4c0fc04b-26c9-402d-8182-4ed56bb6e6f3",
         "8221ecc1-3495-4dfe-b77c-06be5cd8177d",
         "d7d61a30-788a-4a3c-a746-1d531c2093d7",
         "f94b6c25-e166-40e0-b4ec-081d713e4e25",
         "aa5e547e-1498-4986-9179-2a3a2034b062",
         "bd353412-dee0-49f5-91da-569656e8ded1",
         "9cd48594-4f34-4780-8aad-eb2d4f0d6760",
         "69ce074b-1133-4704-88d5-acfbd90fb530",
         "3b876834-9270-4ccc-88f9-fc7f427bb8bf",
         "9a71ea4b-df64-4233-ab3f-5138b1668245",
         "e190ed1d-c21c-4030-b3bf-f834e7862fe2",
         "efcd628f-666d-4a60-9e1f-d2042516c56c",
         "f7be631c-f707-403b-88c5-39b6abb3e99a",
         "2f52d556-4c60-428a-a691-8d0dc96c4470",
         "0a134bb0-32ef-4c47-bd7f-71eef2cb8a97",
         "62b174f1-2289-4e1a-8fa3-a4764197891b",
         "7cb51a9d-36e3-4edc-9a26-5fe3a3c5e4b4",
         "1f0ef82a-b9cd-487e-8fe1-17958f1d14bd",
         "2b954863-7cc5-4495-a1e8-8229665675fa",
         "4837b75c-666f-4e61-9c2e-2201a6059a34",
         "d88df77a-ac16-48c8-84d1-471980c2cebf",
         "868d5b3d-d94b-410b-8068-859e6a7ae503",
         "ba44b973-f1c8-4515-abdf-d763653431b4",
         "349cc91e-440d-4c98-aa92-65719b881995",
         "235550a5-7fcb-438d-bb9c-3f06de111221",
         "9aad771d-f59f-4603-8c2a-52cbfd25e200",
         "b1b65df3-a5c4-42a3-9aa1-9ebdcd542d9b",
         "225f8f7c-68fc-4301-9a9f-0a5330175759",
         "c7a49e78-154e-4b6b-8513-fad78ce44aaa",
         "86bfa2d6-c717-4369-bb50-d44380516549",
         "4cc8186d-b088-4bf4-b4d1-b09111bd13f9",
         "d24f1877-b4b1-4eb6-99f8-2227d5aa95eb",
         "3ab8acdd-73aa-4fe0-b141-7c7dfbd0c699",
         "5b04bb20-f895-475b-a385-a5f8735d7934",
         "0283e65c-ec90-45e6-8434-fd1501f98ebb",
         "6914e96f-355c-44b6-bfcb-b9e933c5e037",
         "93602e05-11cd-453c-9a0f-211b6ff3bd03",
         "af603220-e9ef-4241-ab5f-990fbdd85a22",
         "d4d1e2ce-b15f-4be0-9975-6fb23c377bee",
         "eb0eeb2a-e189-4d92-beb9-309666bf4864",
         "64ec24ef-313e-4591-83ee-b4586cf39bfc",
         "9bc02fbf-8a2b-438f-8f85-dfa6ffc3ab94",
         "c0d692af-f678-4c1f-a010-7a89847bbcbb",
         "789673f0-711f-4fed-8f48-fb4a5d1d4db3",
         "0b77c8a5-f9f6-4381-95bc-f9795c6d6e3c",
         "0dd2ecea-be19-4f4d-ab97-e7b736bf64e9",
         "a61e04dc-7436-4afe-b1f0-67569d541aa2",
         "d94b2520-e209-4860-951e-3a765896b13b",
         "af8c3c36-f738-4206-bd71-db7875f0392e",
         "854416c5-d42d-4b16-a2d1-5f9060d104ea",
         "12327da8-ac7d-4226-880b-2a2631b7a3f4",
         "488b67bc-90b1-4b97-86bb-5b1acda742d7",
         "8484cf94-fc81-47a8-8f7e-4ff6343e58c8",
         "957975a5-c34e-4c83-897d-791e5d8517c5",
         "adaca3d6-9cce-4db8-a9b5-ce01b408d608",
         "0fcdd4bb-c6bd-4e0a-8ddf-ffc0912dce83",
         "6a53bd3d-19ce-4e07-b3ed-3a03098e0f29",
         "8079d103-6ff2-4205-9488-e56ae7720385",
         "03104da1-92cc-467c-8685-32938c07de5f",
         "2942f806-e4ff-495a-b214-f2afd242be60",
         "b59b3cd5-5645-4fc9-9ee8-480b3a0f30c8"
      }
   );

   private static String normalizeServerIP(String rawIP) {
      if (rawIP.contains("/")) {
         String[] parts = rawIP.split("/");
         rawIP = parts[parts.length - 1];
      }

      return rawIP;
   }

   public static void init() {
      PlayerEvent.PLAYER_JOIN.register((PlayerJoin)player -> {});
   }
}
