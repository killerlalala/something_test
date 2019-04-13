import cn.itcast.pojo.TbItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-solr.xml")
public class TestTemplate {

    @Autowired
    private SolrTemplate template;

    @Test
    public void add(){
        TbItem item=new TbItem();
        item.setId(1L);
        item.setBrand("华为");
        item.setCategory("手机");
        item.setGoodsId(1L);
        item.setSeller("华为2号专卖店");
        item.setTitle("华为Mate9");
        item.setPrice(new BigDecimal(2000));
        template.saveBean(item);
        template.commit();

    }


    @Test
    public void find(){
        TbItem item = template.getById(1L, TbItem.class);
        System.out.println(item.getTitle());
    }

    @Test
    public void delete(){
        template.deleteById("1");
        template.commit();
    }


    @Test
    public void addList(){

        List<TbItem> list=new ArrayList();
        for (int i = 0; i < 100; i++) {
                TbItem item=new TbItem();
                item.setId(i+1L);
                item.setBrand("华为"+i);
                item.setCategory("手机");
                item.setGoodsId(1L);
                item.setSeller("华为2号专卖店");
                item.setTitle("华为Mate"+i);
                item.setPrice(new BigDecimal(2000+i));
                list.add(item);
        }

        template.saveBeans(list);
        template.commit();
    }



    @Test
    public void pageQuery(){
        Query queue = new SimpleQuery("*:*");
        queue.setOffset(20);
        queue.setRows(20);

        ScoredPage<TbItem> page = template.queryForPage(queue, TbItem.class);
        for (TbItem tbItem : page.getContent()) {
            System.out.println(tbItem.getTitle()+"    "+tbItem.getPrice()+"   "+tbItem.getBrand());
        }

        System.out.println("总记录数"+page.getTotalElements());
        System.out.println("总页数"+page.getTotalPages());
    }



    @Test
    public void pageQuery2(){
        Query queue = new SimpleQuery("*:*");
        Criteria criteria = new Criteria("item_category").contains("手机");
        criteria=criteria.and("item_brand").contains("2");


        queue.addCriteria(criteria);


        /*queue.setOffset(20);
        queue.setRows(20);*/

        ScoredPage<TbItem> page = template.queryForPage(queue, TbItem.class);
        for (TbItem tbItem : page.getContent()) {
            System.out.println(tbItem.getTitle()+"    "+tbItem.getPrice()+"   "+tbItem.getBrand());
        }

        System.out.println("总记录数"+page.getTotalElements());
        System.out.println("总页数"+page.getTotalPages());
    }


    @Test
    public void dele(){
        Query query = new SimpleQuery("*:*");

        template.delete(query);
        template.commit();
    }
}
