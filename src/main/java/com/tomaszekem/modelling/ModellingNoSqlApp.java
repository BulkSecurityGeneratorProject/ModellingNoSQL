package com.tomaszekem.modelling;

import com.google.common.collect.Lists;
import com.tomaszekem.modelling.config.ApplicationProperties;
import com.tomaszekem.modelling.config.DefaultProfileUtil;

import com.tomaszekem.modelling.domain.Group;
import com.tomaszekem.modelling.domain.Post;
import com.tomaszekem.modelling.domain.PostComment;
import com.tomaszekem.modelling.domain.User;
import com.tomaszekem.modelling.domain.enumeration.Category;
import com.tomaszekem.modelling.repository.GroupRepository;
import com.tomaszekem.modelling.repository.PostRepository;
import com.tomaszekem.modelling.repository.UserRepository;
import io.github.jhipster.config.JHipsterConstants;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class ModellingNoSqlApp {

    private static final Logger log = LoggerFactory.getLogger(ModellingNoSqlApp.class);
    private static final int GENERATED_RECORDS_SIZE = 10000;
    private static final int NUMBER_OF_COMMENTS = 100;

    private final Environment env;

    @Autowired
    public ModellingNoSqlApp(Environment env, PostRepository postRepository, UserRepository userRepository, GroupRepository groupRepository) {
        this.env = env;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;


    @Transactional
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not " +
                "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
        insertData();
    }

    private void insertData() {
        log.debug("Inserting data");
        createGroups();
        log.debug("Inserted data");
    }

    private void createGroups() {
        List<Group> groups = Lists.newArrayList();
        List<User> users = userRepository.findAll();
        List<Post> posts = createPostsOfUser(users.get(0));


        for (int i = 0; i < GENERATED_RECORDS_SIZE; i++) {
            Group newGroup = new Group("Test Group", Category.CULTURE);
            newGroup.addMembers(users);
            newGroup.addPosts(posts);
            groups.add(newGroup);
        }
        groupRepository.insert(groups);
    }

    private List<Post> createPostsOfUser(User user) {
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < GENERATED_RECORDS_SIZE; i++) {
            Post newPost = new Post("Title"+i, "Content"+i, Category.CULTURE);
            newPost.setUser(user);
            newPost.addComments(prepareCommentsForPost(user));
            posts.add(newPost);
        }

        return postRepository.insert(posts);
    }

    private List<PostComment> prepareCommentsForPost(User author) {
        List<PostComment> comments = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_COMMENTS; i++) {
            PostComment comment = new PostComment("Content"+i, author);
            comments.add(comment);
        }
        return comments;
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ModellingNoSqlApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}{}\n\t" +
                "External: \t{}://{}:{}{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            serverPort,
            contextPath,
            protocol,
            hostAddress,
            serverPort,
            contextPath,
            env.getActiveProfiles());
    }
}
