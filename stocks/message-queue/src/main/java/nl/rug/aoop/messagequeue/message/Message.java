package nl.rug.aoop.messagequeue.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Classs Message that has a header, body, and a timestamp.
 *
 * @param header       Message header.
 * @param body     Content of the message.
 * @param timestamp Time at which message is created.
 */
public record Message(String header, String body, LocalDateTime timestamp) implements Comparable<Message> {
    /**
     * Gson that enables the Message to be converted from and to JSON format.
     */
    private static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(Message.class, new MessageAdapter().nullSafe())
        .create();

    /**
     * Message constructor.
     * @param header   Message header.
     * @param body  Content of the message.
     */
    public Message(String header, String body) {
        this(header, body, LocalDateTime.now());
    }

    /**
     * Method converts the Message to a JSON String.
     * @return String in JSON format.
     */
    public String toJson() {
        return GSON.toJson(this);
    }

    /**
     * Method converts the given JSON String back into a Message.
     * @param string String of Message in JSON format.
     * @return Message represented by the given JSON String.
     */
    public static Message fromJson(String string) {
        return GSON.fromJson(string, Message.class);
    }

    /**
     * Method compared self and passed Message.
     * @param o the object to be compared by its TimeStamp.
     * @return int 1, -1, or 0 depending on the TimeStamp.
     */
    @Override
    public int compareTo(Message o) {
        if (this.timestamp.isAfter(o.timestamp())) {
            return 1;
        } else if (this.timestamp.isBefore(o.timestamp())) {
            return -1;
        }
        return 0;
    }

    /**
     * Helper class for the GsonBuilder that allows the conversion of all Message class fields to Json.
     */
    private static final class MessageAdapter extends TypeAdapter<Message> {

        /**
         * The header field of the Message class.
         */
        public static final String HEADER_FIELD = "Header";
        /**
         * The body field of the Message class.
         */
        public static final String BODY_FIELD = "Body";
        /**
         * The timestamp field of the Message class.
         */
        public static final String TIMESTAMP_FIELD = "Timestamp";

        /**
         * Reads the message as Json String and converts it to a Message object.
         * @param reader Reader used to read the Json String
         * @return Message object.
         * @throws IOException IOException.
         */
        @Override
        public Message read(JsonReader reader) throws IOException {
            reader.beginObject();
            String header = null;
            String body = null;
            LocalDateTime timestamp = null;
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
                    case TIMESTAMP_FIELD:
                        timestamp = LocalDateTime.parse(reader.nextString());
                        break;
                }
            }
            reader.endObject();
            return new Message(header, body, timestamp);
        }

        /**
         * Takes a message Object and writes it into Json.
         * @param writer Writer used to write into Json.
         * @param jsonExample Message object.
         * @throws IOException IOException.
         */
        @Override
        public void write(JsonWriter writer, Message jsonExample) throws IOException {
            writer.beginObject();
            writer.name(HEADER_FIELD);
            writer.value(jsonExample.header());
            writer.name(BODY_FIELD);
            writer.value(jsonExample.body());
            writer.name(TIMESTAMP_FIELD);
            writer.value(jsonExample.timestamp().toString());
            writer.endObject();
        }
    }
}