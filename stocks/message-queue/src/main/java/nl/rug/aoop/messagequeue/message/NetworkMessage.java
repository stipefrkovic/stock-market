package nl.rug.aoop.messagequeue.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Class NetworkMessage that acts as a wrapper for transmitting Objects such as Messages over the Network.
 * @param header String for header of NetworkMessage.
 * @param body String for body of NetworkMessage.
 */
public record NetworkMessage(String header, String body){
    /**
     * Gson that enables the NetworkMessage to be converted from and to JSON format.
     */
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(NetworkMessage.class, new NetworkMessage.NetworkMessageAdapter().nullSafe())
            .create();

    /**
     * Method converts the NetworkMessage to a JSON String.
     * @return String in JSON format.
     */
    public String toJson() {
        return GSON.toJson(this);
    }

    /**
     * Method converts the given JSON String back into a NetworkMessage.
     * @param string String of NetworkMessage in JSON format.
     * @return NetworkMessage represented by the given JSON String.
     */
    public static NetworkMessage fromJson(String string) {
        return GSON.fromJson(string, NetworkMessage.class);
    }

    /**
     * Helper class for the GsonBuilder that allows the conversion of all NetworkMessage class fields to Json.
     */
    private static final class NetworkMessageAdapter extends TypeAdapter<NetworkMessage> {
        /**
         * The header field of the NetworkMessage class.
         */
        public static final String HEADER_FIELD = "Header";
        /**
         * The body field of the NetworkMessage class.
         */
        public static final String BODY_FIELD = "Body";

        /**
         * Reads the message as Json String and converts it to a NetworkMessage object.
         * @param reader Reader used to read the Json String.
         * @return NetworkMessage object.
         * @throws IOException IOException.
         */
        @Override
        public NetworkMessage read(JsonReader reader) throws IOException {
            reader.beginObject();
            String header = null;
            String body = null;
            while (reader.hasNext()) {
                JsonToken token = reader.peek();
                String fieldName = null;
                if (token.equals(JsonToken.NAME)) {
                    fieldName = reader.nextName();
                }
                if (fieldName == null) {
                    continue;
                }
                switch (fieldName) {
                    case HEADER_FIELD:
                        header = reader.nextString();
                        break;
                    case BODY_FIELD:
                        body = reader.nextString();
                        break;
                }
            }
            reader.endObject();
            return new NetworkMessage(header, body);
        }

        /**
         * Takes a NetworkMessage Object and writes it into Json.
         * @param writer Writer used to write into Json
         * @param jsonExample NetworkMessage object.
         * @throws IOException IOException.
         */
        @Override
        public void write(JsonWriter writer, NetworkMessage jsonExample) throws IOException {
            writer.beginObject();
            writer.name(HEADER_FIELD);
            writer.value(jsonExample.header());
            writer.name(BODY_FIELD);
            writer.value(jsonExample.body());
            writer.endObject();
        }
    }
}
