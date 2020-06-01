#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.demo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import ${package}.demo.domain.City;

/**
 * @author zhanglei
 */
@Mapper
public interface CityMapper {

  @Select("select id, name, state, country from city")
  List<City> getAll();

  @Select("select id, name, state, country from city where id=${symbol_pound}{id}")
  City getOne(@Param("id") String id);

  @Insert("insert into city(id, name, state, country) values (${symbol_pound}{id}, ${symbol_pound}{name}, ${symbol_pound}{state}, ${symbol_pound}{country})")
  void insert(City city);

  @Update("update city set name=${symbol_pound}{name},state=${symbol_pound}{state},country=${symbol_pound}{state} where id=${symbol_pound}{id}")
  void update(City city);

  @Delete("delete from city where id=${symbol_pound}{id}")
  void delete(String id);

  @Delete("delete from city")
  void deleteAll();
}