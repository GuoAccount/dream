import com.dream.pojo.TbItem;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TestFreemarker {
//    @Test
//    private void test1() throws IOException, TemplateException {
//        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/springmvc.xml");
//        FreeMarkerConfigurer freeMarkerConfigurer = ctx.getBean("freeMarkerConfigurer",FreeMarkerConfigurer.class);
//        Configuration configuration = freeMarkerConfigurer.getConfiguration();
//        Template template = configuration.getTemplate("demo.ftl");
//        //5.设置模板输出的目录以及输出的文件名（目录必须是已存在的文件名）
//        FileWriter fileWriter = new FileWriter(new File("C:\\Users\\34851\\Desktop\\Freemakerdemo\\demo.html"));
//        //绑定数据
//        HashMap<Object, Object> map = new HashMap<>();
//        //绑定String
//        map.put("msg","这是spring测试绑定数据");
//        //绑定指定的pojo对象
//        TbItem tbItem1 = new TbItem();
//        tbItem1.setTitle("测试绑定对象1");
//        TbItem tbItem2 = new TbItem();
//        tbItem2.setTitle("测试绑定对象2");
//        TbItem tbItem3 = new TbItem();
//        tbItem3.setTitle("测试绑定对象3");
//
//        List<Object> items = new ArrayList<>();
//        items.add(tbItem1);
//        items.add(tbItem2);
//        items.add(tbItem3);
//        map.put("items",items);
//
//        map.put("tbItem",tbItem1);
//        //6.生成文件 第一个是参数绑定的数据
//        template.process(map,fileWriter);
//        //7.关闭流
//        fileWriter.close();
//    }
    @Test
    public void testFreemarkDemo() throws IOException, TemplateException {
    //创建一个Configuration对象用来设置模板信息，构造方法中有一个freemarker的版本号参数
        Configuration configuration = new Configuration(Configuration.getVersion());
        //通过Configura来得到一个模板，通过Configuration来设置和读取模板的信息
        //2.设置模板所在目录
        configuration.setDirectoryForTemplateLoading(new File("C:\\Users\\34851\\IdeaProjects\\dream\\dream_item_web\\src\\Test\\resources"));
        //3.设置模板的字符编码
        configuration.setDefaultEncoding("UTF-8");
        //4.加载模板文件
        Template template = configuration.getTemplate("deni.ftl");
        //5.设置模板输出的目录以及输出的文件名（目录必须是已存在的文件名）
        FileWriter fileWriter = new FileWriter(new File("C:\\Users\\34851\\Desktop\\Freemakerdemo\\demo.html"));
        //绑定数据
        HashMap<Object, Object> map = new HashMap<>();
        //绑定String
        map.put("msg","这是测试绑定数据");
        //绑定指定的pojo对象
        TbItem tbItem1 = new TbItem();
        tbItem1.setTitle("测试绑定对象1");
        TbItem tbItem2 = new TbItem();
        tbItem2.setTitle("测试绑定对象2");
        TbItem tbItem3 = new TbItem();
        tbItem3.setTitle("测试绑定对象3");

        List<Object> items = new ArrayList<>();
        items.add(tbItem1);
        items.add(tbItem2);
        items.add(tbItem3);
        map.put("items",items);

        map.put("tbItem",tbItem1);
        //6.生成文件 第一个是参数绑定的数据
        template.process(map,fileWriter);
        //7.关闭流
        fileWriter.close();
    }
}
