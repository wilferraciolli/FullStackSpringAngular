package com.wiltech.llm.ai.services;

public final class ChatConstants {
    public static final String QDRANT_API_KEY = "***";
    public static final String QDRANT_GRPC_HOST = "58b4901b-0ece-4632-878d-fd59236021e4.us-east4-0.gcp.cloud.qdrant.io";
//    public static final String QDRANT_GRPC_HOST = "https://node-0-58b4901b-0ece-4632-878d-fd59236021e4.us-east4-0.gcp.cloud.qdrant.io";
    public static final int QDRANT_GRPC_PORT = 6333;

    public static final Integer OPENAI_EMBEDDING_SIZE = 1536;
    public static final String OPENAI_API_KEY  = "***";

    public static final String COLLECTION_NAME = "world_history_collection";

    private ChatConstants() {
    }
}
