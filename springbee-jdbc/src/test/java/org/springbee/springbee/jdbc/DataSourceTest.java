package org.springbee.springbee.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springbee.springbee.jdbc.domain.City;
import org.springbee.springbee.jdbc.mapper.MasterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DataSourceTest {

  @Autowired
  private MasterMapper masterMapper;

  @BeforeEach
  public void setup() {
    masterMapper.insert(City.builder().id("10").name("北京").country("中国").state("北京").build());
    masterMapper.insert(City.builder().id("21").name("上海").country("中国").state("上海").build());
    masterMapper.insert(City.builder().id("335").name("秦皇岛").country("中国").state("河北").build());
  }

  @AfterEach
  public void unsetup() {
    masterMapper.deleteAll();
  }

  @Test
  public void testInsert() {
    assertEquals(3, masterMapper.getAll().size());
  }

  @Test
  public void testQuery() {
    City city = masterMapper.getOne("21");
    assertTrue(("上海".equals(city.getName())));
  }

  @Test
  public void testUpdate() {
    City city = masterMapper.getOne("10");
    city.setName("beijing");
    masterMapper.update(city);
    assertTrue(("beijing".equals(masterMapper.getOne("10").getName())));
  }

  @Test
  public void testDelete() {
    masterMapper.delete("335");
    assertEquals(2, masterMapper.getAll().size());
  }

  @Test
  public void testPage() {
    PageHelper.startPage(1, 2);
    List<City> cities = masterMapper.getAll();
    assertEquals(2, cities.size());
    assertEquals(3, ((Page) cities).getTotal());
  }
}