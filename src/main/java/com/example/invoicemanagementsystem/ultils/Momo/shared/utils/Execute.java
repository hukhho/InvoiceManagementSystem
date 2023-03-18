package com.example.invoicemanagementsystem.ultils.Momo.shared.utils;


import com.mservice.shared.sharedmodels.HttpRequest;
import com.mservice.shared.sharedmodels.HttpResponse;
import okhttp3.*;
import okio.Buffer;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j

public class Execute {

    OkHttpClient client = new OkHttpClient();

    public HttpResponse sendToMoMo(String endpoint, String payload) {

        try {

            HttpRequest httpRequest = new HttpRequest("POST", endpoint, payload, "application/json");

            Request request = createRequest(httpRequest);

            log.debug("[HttpPostToMoMo] Endpoint:: " + httpRequest.getEndpoint() + ", RequestBody:: " + httpRequest.getPayload());

            Response result = client.newCall(request).execute();
            HttpResponse response = new HttpResponse(result.code(), result.body().string(), result.headers());

            log.info("[HttpResponseFromMoMo] " + response.toString());

            return response;
        } catch (Exception e) {
            log.error("[ExecuteSendToMoMo] "+ e);
        }

        return null;
    }

    public static Request createRequest(HttpRequest request) {
        RequestBody body = RequestBody.create(MediaType.get(request.getContentType()), request.getPayload());
        return new Request.Builder()
                .method(request.getMethod(), body)
                .url(request.getEndpoint())
                .build();
    }

    public String getBodyAsString(Request request) throws IOException {
        Buffer buffer = new Buffer();
        RequestBody body = request.body();
        body.writeTo(buffer);
        return buffer.readUtf8();
    }
}
