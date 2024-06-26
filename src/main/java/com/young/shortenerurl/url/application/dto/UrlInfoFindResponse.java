package com.young.shortenerurl.url.application.dto;

import com.young.shortenerurl.url.model.Url;

public record UrlInfoFindResponse(
        String originUrl,
        String encodedUrl,
        Long visitCount) {
    public static UrlInfoFindResponse from(Url url) {
        return new UrlInfoFindResponse(
                url.getOriginUrl(),
                url.getEncodedUrl(),
                url.getVisitCount());
    }
}
