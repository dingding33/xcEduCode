package com.xuecheng.manage_cms;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ManageCmsApplicationTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    @Test
    public void restTemplate() {

        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getModel/5a791725dd573c3574ee333f", Map.class);

        System.out.println(forEntity.getBody());
    }

    @Test
    public void testGridFs() throws FileNotFoundException {

        // 需要存储的文件
        File file = new File("D:\\Projects19\\xcEdu\\xcEduUI\\xc-ui-pc-static-portal\\include/index_banner.html");

        // 定义输入流
        FileInputStream stream = new FileInputStream(file);

        ObjectId objectId = gridFsTemplate.store(stream, "轮播图测试文件01", "");

        // 文件id
        String fileId = objectId.toString();
        System.out.println(fileId);
    }

    @Test
    public void queryFile() throws IOException {
        String fileId = "5de65e4219dd534b30f83019";

        // 根据文件id查询文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));

        // 打开下载流对象
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());

        // 创建 gridFsResource，用于获取流对象
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);

        // 读取流中的数据
        System.out.println(IOUtils.toString(gridFsResource.getInputStream(), "UTF-8"));

    }

    @Test
    public void delFile() throws IOException {
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is("5de65e4219dd534b30f83019")));
    }




}