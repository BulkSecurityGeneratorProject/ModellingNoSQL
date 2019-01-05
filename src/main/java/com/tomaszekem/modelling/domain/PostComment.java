package com.tomaszekem.modelling.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

public class PostComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Field("content")
    private String content;

    @Field("author")
    @JsonIgnoreProperties("")
    private User author;

    public String getContent() {
        return content;
    }

    public PostComment(@NotNull String content, User author) {
        this.content = content;
        this.author = author;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

}
