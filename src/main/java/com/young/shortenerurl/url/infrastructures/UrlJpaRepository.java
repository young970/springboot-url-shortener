package com.young.shortenerurl.url.infrastructures;

import com.young.shortenerurl.url.model.Url;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UrlJpaRepository extends JpaRepository<Url, Long> {

    @Query("SELECT u FROM Url u WHERE u.encodedUrl.encodedUrl = :encodedUrl")
    Optional<Url> findByEncodedUrl(@Param(value = "encodedUrl") String encodedUrl);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM Url u WHERE u.encodedUrl.encodedUrl = :encodedUrl")
    Optional<Url> findByEncodedUrlWithPessimisticLock(@Param(value = "encodedUrl") String encodedUrl);

    Optional<Url> findUrlByOriginUrl(String originUrl);
}
