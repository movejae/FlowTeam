package com.flow.folwteamtest.entity;

import com.flow.folwteamtest.common.ExtensionConstants;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "custom_extension")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomExtension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = ExtensionConstants.EXTENSION_MAX_LENGTH)
    private String extension;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public CustomExtension(String extension) {
        this.extension = extension.toLowerCase().trim();
    }
}
