package com.young.shortenerurl.url.infrastructures;

import com.young.shortenerurl.url.model.Url;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UrlRepositoryImpl implements UrlRepository {

    private final UrlJpaRepository urlJpaRepository;

    public UrlRepositoryImpl(UrlJpaRepository urlJpaRepository) {
        this.urlJpaRepository = urlJpaRepository;
    }

    @Override
    public Url save(Url url) {
        return urlJpaRepository.save(url);
    }

    @Override
    public Url getByEncodedUrl(String encodedUrl) {
        return urlJpaRepository.findByEncodedUrl(encodedUrl)
                .orElseThrow(() -> new EntityNotFoundException("해당 encodedUrl를 가진 url을 찾을 수 없습니다."));
    }

    @Override
    public Url findByEncodedUrlWithPessimisticLock(String encodedUrl) {
        return urlJpaRepository.findByEncodedUrlWithPessimisticLock(encodedUrl)
                .orElseThrow(() -> new EntityNotFoundException("해당 encodedUrl를 가진 url을 찾을 수 없습니다."));
    }

    @Override
    public Optional<Url> findByOriginUrl(String originUrl) {
        return urlJpaRepository.findUrlByOriginUrl(originUrl);
    }

}
