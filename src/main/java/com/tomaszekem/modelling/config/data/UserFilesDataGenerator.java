package com.tomaszekem.modelling.config.data;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.tomaszekem.modelling.domain.User;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class UserFilesDataGenerator {

    private static final String VPP_FILE_PATH = "src/main/resources/class_diagram.vpp";
    private static final String USER_ID = "userId";
    private final GridFsTemplate gridFsTemplate;

    public UserFilesDataGenerator(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }

    void createUsersFiles(List<User> users) throws IOException {
        try (InputStream inputStream = new FileInputStream(VPP_FILE_PATH)) {
            for (User user : users) {
                gridFsTemplate.store(inputStream, "class_diagram.vpp", "vpp", createFileMetaData(user));
            }
        }
    }

    private DBObject createFileMetaData(User user) {
        DBObject metaData = new BasicDBObject();
        metaData.put(USER_ID, user.getId());
        return metaData;
    }

}
