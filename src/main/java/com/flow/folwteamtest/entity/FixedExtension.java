package com.flow.folwteamtest.entity;

import com.flow.folwteamtest.common.ExtensionConstants;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fixed_extension")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FixedExtension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = ExtensionConstants.EXTENSION_MAX_LENGTH)
    private String name;

    @Column(nullable = false)
    private boolean blocked = false;

    @Builder
    public FixedExtension(String name, boolean blocked) {
        this.name = name;
        this.blocked = blocked;
    }

    public void updateBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
