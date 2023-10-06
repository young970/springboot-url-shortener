package com.young.shortenerurl.model;

import com.young.shortenerurl.model.encoder.Encoder;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.NoSuchElementException;

@Entity
@Table(name = "urls")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String originUrl;

    @Embedded
    private EncodedUrl encodedUrl;

    @Column(nullable = false)
    private Long visitCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EncodingType encodingType;

    @Transient
    private Encoder encoder;

    public Url(String originUrl, EncodingType encodingType, Long visitCount) {
        Assert.notNull(originUrl, "originUrl은 null값이 들어올 수 없습니다.");
        Assert.notNull(encodingType, "encodingType은 null값이 들어올 수 없습니다.");
        Assert.notNull(visitCount, "visitCount는 null값이 들어올 수 없습니다.");

        this.originUrl = originUrl;
        this.encodingType = encodingType;
        this.encoder = encodingType.getEncoder();
        this.visitCount = visitCount;
    }

    public String encode(){
        if (id == null){
            throw new NoSuchElementException("인코딩할 id값이 초기화 되어있지 않습니다. save를 먼저 하여 id값을 생성해주세요.");
        }

        String encoded = encoder.encode(this.id);
        this.encodedUrl = new EncodedUrl(encoded);

        return encoded;
    }

    public String getEncodedUrl() {
        if (encodedUrl == null){
            throw new NoSuchElementException("encodedUrl이 초기화 되어있지 않습니다.");
        }

        return encodedUrl.getEncodedUrl();
    }

}
