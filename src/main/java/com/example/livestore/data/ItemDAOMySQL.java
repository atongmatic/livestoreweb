package com.example.livestore.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

@Service
public class ItemDAOMySQL implements ItemDAO {
    
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Override
    public int insert(ItemDTO itemDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
       
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("type", itemDTO.getType());
        param.addValue("price", itemDTO.getPrice());
        param.addValue("cost", itemDTO.getCost());
        
        namedParameterJdbcTemplate.update("Insert into Item(type, price, cost) values(:type,:price,:cost)", param, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public ItemDTO get(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        String sql = "select * from mydb1.item where id=:id";
        ItemDTO dto = namedParameterJdbcTemplate.queryForObject(sql, params, new ItemMapper());
        return dto;
    }

    @Override
    public List<ItemDTO> getAll() {
        String sql = "select * from mydb1.item";
        List<ItemDTO> items = namedParameterJdbcTemplate.query(sql, new ItemMapper());
        return items;
    }

    @Override
    public void update(ItemDTO itemDTO) {
        
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("type", itemDTO.getType());
        param.addValue("price", itemDTO.getPrice());
        param.addValue("cost", itemDTO.getCost());
        param.addValue("id", itemDTO.getId());
        
        String sql = "Update mydb1.item set type=:type, "
                    + "price=:price, cost =:cost where id=:id";
        
        namedParameterJdbcTemplate.update(sql, param);
    }

    @Override
    public void delete(int i) {
         String sql = "delete from mydb1.item  where id=:id";
         MapSqlParameterSource param = new MapSqlParameterSource();
         param.addValue("id", i);
         namedParameterJdbcTemplate.update(sql, param);
    }

    private class ItemMapper implements RowMapper<ItemDTO> {

        @Override
        public ItemDTO mapRow(ResultSet rs, int i) throws SQLException {
            ItemDTO dto = new ItemDTO();
            dto.setId(rs.getInt("id"));
            dto.setType(rs.getString("type"));
            dto.setPrice(rs.getDouble("price"));
            dto.setCost(rs.getDouble("cost"));
            return dto;
        }
    }

}
