package com.young.shortenerurl.url.application;

import com.young.shortenerurl.url.application.dto.UrlCreateRequest;
import com.young.shortenerurl.url.application.dto.UrlInfoFindResponse;
import com.young.shortenerurl.url.infrastructures.UrlRepository;
import com.young.shortenerurl.url.model.EncodedUrl;
import com.young.shortenerurl.url.model.Url;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final EncodingExecutor encodingExecutor;

    public UrlService(UrlRepository urlRepository, EncodingExecutor encodingExecutor) {
        this.urlRepository = urlRepository;
        this.encodingExecutor = encodingExecutor;
    }

    @Transactional
    public String createUrl(UrlCreateRequest request) {
        String encodedUrl = encodingExecutor.encode(request.encodingType());

        if (urlRepository.existsByOriginUrl(request.originUrl())) {
            Url url = urlRepository.getByOriginUrl(request.originUrl());

            return url.getEncodedUrl();
        }

        Url savedUrl = request.toUrl(encodedUrl);
        return urlRepository.save(savedUrl).getEncodedUrl();

    }

    @Transactional
    public String findOriginUrl(String encodedUrl) {
        Url url = urlRepository.findByEncodedUrlWithPessimisticLock(encodedUrl);

        url.increaseVisitCount();

        return url.getOriginUrl();
    }

    @Transactional(readOnly = true)
    public UrlInfoFindResponse findUrlInfoByEncodedUrl(String encodedUrl) {
        Url findUrl = urlRepository.getByEncodedUrl(encodedUrl);

        return UrlInfoFindResponse.from(findUrl);
    }

}
