package ch.zhaw.statefulconversation.spi;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import ch.zhaw.statefulconversation.model.Utterance;
import ch.zhaw.statefulconversation.model.Utterances;

public class LMOpenAI {
    private static final Logger LOGGER = LoggerFactory.getLogger(LMOpenAI.class);

    private static final String REMINDER_DECISION = "Remember to respond with either true or false only so that it can be parsed with the Java programming language. Your respopnse needs to work with the Boolean.parseBoolean() method, which only accepts English true or false.";
    private static final String REMINDER_EXTRACTION = "Remember to respond with the extracted value in JSON format only so that it can be parsed with a Java program using the GSON library. If the value extracted is of type string, ensure it is enclosed in double quotes.";
    private static final String REMINDER_DETECTION = "Based on the above guidelines, your response should be one of the numbers -1, 0, or 1 so that it can be parsed with the Java programming language. Your response needs to work with the Integer.parseInt() method, which only acepts integer numbers.";
    private static final String REMINDER_SUMMARISATION = "Remember to reply with the summary in JSON format only so that it can be parsed with a Java program using the GSON library.";

    public static String complete(Utterances utterances, String systemPrepend) {
        List<Utterance> totalPrompt = LMOpenAI.composePrompt(utterances, systemPrepend);
        LMOpenAI.LOGGER.info("LMOpenAI.complete() with " + totalPrompt);
        String result = LMOpenAI.openai(totalPrompt);
        return result;
    }

    public static String complete(Utterances utterances, String systemPrepend, String systemAppend) {
        List<Utterance> totalPrompt = LMOpenAI.composePrompt(utterances, systemPrepend, systemAppend);
        LMOpenAI.LOGGER.info("LMOpenAI.complete() with " + totalPrompt);
        String result = LMOpenAI.openai(totalPrompt);
        return result;
    }

    public static boolean decide(Utterances utterances, String systemPrepend) {
        if (utterances.isEmpty()) {
            throw new RuntimeException("cannot decide about empty utterances");
        }
        List<Utterance> totalPrompt = LMOpenAI.composePromptCondensed(utterances, systemPrepend,
                LMOpenAI.REMINDER_DECISION);
        LMOpenAI.LOGGER.info("LMOpenAI.decide() with " + totalPrompt);
        String response = LMOpenAI.openai(totalPrompt, 0.0f, 0.0f);
        boolean result = Boolean.parseBoolean(response);
        return result;
    }

    public static JsonElement extract(Utterances utterances, String systemPrepend) {
        if (utterances.isEmpty()) {
            throw new RuntimeException("cannot extract from empty utterances");
        }
        List<Utterance> totalPrompt = LMOpenAI.composePromptCondensed(utterances, systemPrepend,
                LMOpenAI.REMINDER_EXTRACTION);
        LMOpenAI.LOGGER.info("LMOpenAI.extract() with " + totalPrompt);
        String response = LMOpenAI.openai(totalPrompt, 0.0f, 0.0f);
        Gson gson = new Gson();
        JsonElement result = gson.fromJson(response, JsonElement.class);
        return result;
    }

    public static JsonElement summarise(Utterances utterances, String systemPrepend) {
        if (utterances.isEmpty()) {
            throw new RuntimeException("cannot summarise from empty utterance");
        }
        List<Utterance> totalPrompt = LMOpenAI.composePromptCondensed(utterances, systemPrepend,
                LMOpenAI.REMINDER_SUMMARISATION);
        LMOpenAI.LOGGER.info("LMOpenAI.summarise() with " + totalPrompt);
        String response = LMOpenAI.openai(totalPrompt, 0.0f, 0.0f);
        Gson gson = new Gson();
        JsonElement result = gson.fromJson(response, JsonElement.class);
        return result;
    }

    public static String summariseOffline(Utterances utterances, String systemPrepend) {
        if (utterances.isEmpty()) {
            throw new RuntimeException("cannot summarise offline from empty utterance");
        }
        List<Utterance> totalPrompt = LMOpenAI.composePromptCondensed(utterances, systemPrepend);
        LMOpenAI.LOGGER.info("LMOpenAI.summariseOffline() with " + totalPrompt);
        String result = LMOpenAI.openai(totalPrompt, 0.0f, 0.0f);
        return result;
    }

    public static int detect(Utterances utterances, String systemPrepend) {
        if (utterances.isEmpty()) {
            throw new RuntimeException("cannot detect in an empty utterance");
        }
        List<Utterance> totalPrompt = LMOpenAI.composePromptCondensed(utterances, systemPrepend,
                LMOpenAI.REMINDER_DETECTION);
        LMOpenAI.LOGGER.info("LMOpenAI.detect() with " + totalPrompt);
        String response = LMOpenAI.openai(totalPrompt, 0.0f, 0.0f);
        int result = Integer.parseInt(response);
        if (result < -1 || result > 1) {
            throw new RuntimeException("detection returned " + result + " which is not in {-1, 0, 1}");
        }
        return result;
    }

    private static List<Utterance> composePrompt(Utterances utterances, String systemPrepend) {
        List<Utterance> result = new ArrayList<Utterance>();
        if (systemPrepend == null) {
            throw new NullPointerException(systemPrepend + " systemPrepend (Decision prompt) cannot be null.");
        }
        result.add(new Utterance("system", systemPrepend));
        result.addAll(utterances.toList());
        return result;
    }

    private static List<Utterance> composePrompt(Utterances utterances, String systemPrepend, String systemAppend) {
        if (systemPrepend == null) {
            throw new NullPointerException(systemPrepend + " systemPrepend (Decision prompt) cannot be null.");
        }
        List<Utterance> result = LMOpenAI.composePrompt(utterances, systemPrepend);
        if (systemAppend == null) {
            throw new NullPointerException(systemAppend + " systemAppend cannot be null.");
        }
        result.add(new Utterance("system", systemAppend));
        return result;
    }

    private static List<Utterance> composePromptCondensed(Utterances utterances, String systemPrepend) {
        List<Utterance> result = new ArrayList<Utterance>();
        if (systemPrepend == null) {
            throw new NullPointerException(systemPrepend + " systemPrepend (Decision prompt) cannot be null.");
        }
        result.add(new Utterance("system", systemPrepend));
        result.add(new Utterance("system", "<conversation>" + utterances.toString() + "</conversation>"));
        return result;
    }

    private static List<Utterance> composePromptCondensed(Utterances utterances, String systemPrepend,
            String systemAppend) {
        List<Utterance> result = LMOpenAI.composePromptCondensed(utterances, systemPrepend);
        if (systemAppend == null) {
            throw new NullPointerException(systemAppend + " systemAppend cannot be null.");
        }
        result.add(new Utterance("system", systemAppend));
        return result;
    }

    private static String openai(List<Utterance> messages) {
        return LMOpenAI.openai(messages, 1, 1);
    }

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {

        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return f.getAnnotation(GsonExclude.class) != null;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return clazz == Instant.class;
        }

    }).create();

    public static String openai(List<Utterance> message, float temperature, float topP) {
        try {

            Instant start = Instant.now();

            JsonObject payload = OpenAIProperties.instance().payload();
            payload.addProperty("temperature", temperature);
            payload.addProperty("top_p", topP);
            payload.add("messages", LMOpenAI.GSON.toJsonTree(message));

            // @TODO seems to be available in azure.openai
            // payload.addProperty("max_tokens", 800);
            // payload.addProperty("frequency_penalty", 0);
            // payload.addProperty("presence_penalty", 0);
            // payload.addProperty("stop", null);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(OpenAIProperties.instance().getUrl()))
                    .header(OpenAIProperties.instance().headerKeyNameForAPIKey(), OpenAIProperties.instance().getKey())
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(LMOpenAI.GSON.toJson(payload)))
                    .build();
            HttpResponse<String> response = LMOpenAI.HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            Instant end = Instant.now();
            LMOpenAI.LOGGER.info(
                    "LMOpenAI.openai() http request took " + Duration.between(start, end).toMillis() + " milliseconds");

            // @todo: possibly do some more extensive testing here?
            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException(
                        "unable to use openai api - http request returned status code: " + response.statusCode()
                                + " (\n\t"
                                + response.body() + "\n\t" + response.toString() + "\n)");
            }

            JsonObject jsonResponse = LMOpenAI.GSON.fromJson(response.body(), JsonObject.class);
            String result = LMOpenAI.testAndObtainContent(jsonResponse);
            LMOpenAI.LOGGER.info("LMOpenAI.openai() returns " + result);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("unable to request openai :-(", e);
        }
    }

    private static String testAndObtainContent(JsonObject jsonResponse) {
        if (!jsonResponse.has("choices")) {
            throw new RuntimeException(
                    "unable to use openai api - json response has no choices: " + jsonResponse);
        }

        JsonArray jsonChoices = jsonResponse.getAsJsonArray("choices");

        if (jsonChoices.size() == 0) {
            throw new RuntimeException(
                    "unable to use openai api - json choices is empty: " + jsonResponse);
        }

        JsonObject jsonChoice = jsonChoices.get(0).getAsJsonObject();

        if (jsonChoice.has("finish_reason") && "content_filter".equals(jsonChoice.get("finish_reason").getAsString())) {
            throw new ContenFilterException(
                    "unable to use openai api - content of message was filtered: " + jsonResponse);

        }

        if (!jsonChoice.has("message")) {
            throw new RuntimeException(
                    "unable to use openai api - json choices is empty: " + jsonResponse);
        }

        JsonObject jsonMessage = jsonChoice.get("message").getAsJsonObject();

        if (!jsonMessage.has("content")) {
            throw new RuntimeException(
                    "unable to use openai api - json message has no content: " + jsonResponse);
        }

        return jsonMessage.get("content").getAsString();
    }
}