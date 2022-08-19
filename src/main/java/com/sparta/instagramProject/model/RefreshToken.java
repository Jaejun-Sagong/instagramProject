package com.sparta.instagramProject.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
@Entity
public class RefreshToken {

    @Id
    private String kkey;
    private String vvalue;

    public RefreshToken updateValue(String token) {
        this.vvalue = token;
        return this;
    }

    @Builder
    public RefreshToken(String key, String value) {
        this.kkey = key;
        this.vvalue = value;
    }
}