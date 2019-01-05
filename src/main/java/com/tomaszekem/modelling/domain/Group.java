package com.tomaszekem.modelling.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.tomaszekem.modelling.domain.enumeration.Category;

@Document(collection = "group")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @Field("category")
    private Category category;

    @DBRef
    @Field("members")
    private Set<User> members = new HashSet<>();

    @DBRef
    @Field("posts")
    private Set<Post> posts = new HashSet<>();

    public Group(@NotNull String name, Category category) {
        this.name = name;
        this.category = category;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Group name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public Group category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void addMembers(Collection<User> users) {
        this.members.addAll(users);
    }

//    public Group addMembers(User user) {
//        this.members.add(user);
//        user.getGroups().add(this);
//        return this;
//    }
//
//    public Group removeMembers(User user) {
//        this.members.remove(user);
//        user.getGroups().remove(this);
//        return this;
//    }
//
//    public void setMembers(Set<User> users) {
//        this.members = users;
//    }
//
//    public Set<Post> getPosts() {
//        return posts;
//    }
//
//    public Group posts(Set<Post> posts) {
//        this.posts = posts;
//        return this;
//    }
//
//    public Group addPosts(Post post) {
//        this.posts.add(post);
//        post.setGroup(this);
//        return this;
//    }
//
//    public Group removePosts(Post post) {
//        this.posts.remove(post);
//        post.setGroup(null);
//        return this;
//    }

    public void addPosts(Collection<Post> posts) {
        this.posts.addAll(posts);
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Group group = (Group) o;
        if (group.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), group.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Group{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
