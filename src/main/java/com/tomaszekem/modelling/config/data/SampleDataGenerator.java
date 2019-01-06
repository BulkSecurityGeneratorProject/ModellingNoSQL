package com.tomaszekem.modelling.config.data;

import com.google.common.collect.Lists;
import com.tomaszekem.modelling.domain.Group;
import com.tomaszekem.modelling.domain.Post;
import com.tomaszekem.modelling.domain.PostComment;
import com.tomaszekem.modelling.domain.User;
import com.tomaszekem.modelling.domain.enumeration.Category;
import com.tomaszekem.modelling.repository.GroupRepository;
import com.tomaszekem.modelling.repository.PostRepository;
import com.tomaszekem.modelling.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SampleDataGenerator {

    private static final Logger log = LoggerFactory.getLogger(SampleDataGenerator.class);

    private static final int GENERATED_RECORDS_SIZE = 10000;
    private static final int NUMBER_OF_COMMENTS = 100;

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserFilesDataGenerator userFilesDataGenerator;

    public SampleDataGenerator(PostRepository postRepository, UserRepository userRepository, GroupRepository groupRepository, UserFilesDataGenerator userFilesDataGenerator) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.userFilesDataGenerator = userFilesDataGenerator;
    }


    @Transactional
    public void insertData() {
        log.debug("Inserting data");
        List<User> users = userRepository.findAll();
        createGroups(users);
        try {
            userFilesDataGenerator.createUsersFiles(users);
        } catch (IOException e) {
            log.error("Error in inserting data", e);
        }
        log.debug("Inserted data");
    }



    private void createGroups(List<User> groupMembers) {
        List<Group> groups = Lists.newArrayList();

        List<Post> posts = createPostsOfUser(groupMembers.get(0));


        for (int i = 0; i < GENERATED_RECORDS_SIZE; i++) {
            Group newGroup = new Group("Test Group", Category.CULTURE);
            newGroup.addMembers(groupMembers);
            newGroup.addPosts(posts);
            groups.add(newGroup);
        }
        groupRepository.insert(groups);
    }

    private List<Post> createPostsOfUser(User user) {
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < GENERATED_RECORDS_SIZE; i++) {
            Post newPost = new Post("Title" + i, "Content" + i, Category.CULTURE);
            newPost.setUser(user);
            newPost.addComments(prepareCommentsForPost(user));
            posts.add(newPost);
        }

        return postRepository.insert(posts);
    }

    private List<PostComment> prepareCommentsForPost(User author) {
        List<PostComment> comments = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_COMMENTS; i++) {
            PostComment comment = new PostComment("Content" + i, author);
            comments.add(comment);
        }
        return comments;
    }

}
